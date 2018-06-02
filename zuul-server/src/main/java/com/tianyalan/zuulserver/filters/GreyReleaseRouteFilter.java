package com.tianyalan.zuulserver.filters;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class GreyReleaseRouteFilter extends ZuulFilter {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	private ProxyRequestHelper helper = new ProxyRequestHelper();

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String greyFlag = request.getParameter("greyFlag");
		if (greyFlag == null)
			return null;

		if (ctx.get("serviceId") == null)
			return null;

		//获取灰度环境服务地址
		String serviceName = ctx.get("serviceId").toString();
		String greyServiceName = "grey_" + serviceName;
		String originalEndpoint = ctx.getRequest().getRequestURI();
		String newEndpoint = originalEndpoint.replace(serviceName, greyServiceName);

		//准备服务调用参数
		MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
		MultiValueMap<String, String> params = this.helper.buildZuulRequestQueryParams(request);
		String verb = request.getMethod().toUpperCase();
		InputStream requestEntity = getRequestBody(request);
		this.helper.addIgnoredHeaders();
		
		//发起服务调用
		CloseableHttpClient httpClient = null;
		HttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();
			response = invoke(httpClient, verb, newEndpoint, request, headers, params, requestEntity);

			this.helper.setResponse(response.getStatusLine().getStatusCode(),
					response.getEntity() == null ? null : response.getEntity().getContent(),
					revertHeaders(response.getAllHeaders()));
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				httpClient.close();
			} catch (IOException ex) {
			}
		}

		return null;
	}

	private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        }
        catch (IOException ex) {
        }
        return requestEntity;
    }
	
	private MultiValueMap<String, String> revertHeaders(Header[] headers) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		for (Header header : headers) {
			String name = header.getName();
			if (!map.containsKey(name)) {
				map.put(name, new ArrayList<String>());
			}
			map.get(name).add(header.getValue());
		}
		return map;
	}

	private Header[] convertHeaders(MultiValueMap<String, String> headers) {
		List<Header> list = new ArrayList<>();
		for (String name : headers.keySet()) {
			for (String value : headers.get(name)) {
				list.add(new BasicHeader(name, value));
			}
		}
		return list.toArray(new BasicHeader[0]);
	}

	private HttpResponse invoke(HttpClient httpclient, String verb, String uri, HttpServletRequest request,
			MultiValueMap<String, String> headers, MultiValueMap<String, String> params, InputStream requestEntity)
			throws Exception {
		
		URL host = new URL(uri);
		HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());

		HttpRequest httpRequest;
		int contentLength = request.getContentLength();
		InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength,
				request.getContentType() != null ? ContentType.create(request.getContentType()) : null);
		
		if(verb.toUpperCase().equals("POST")) {
			HttpPost httpPost = new HttpPost(uri);
			httpRequest = httpPost;
			httpPost.setEntity(entity);
		} else if(verb.toUpperCase().equals("PUT")) {
			HttpPut httpPut = new HttpPut(uri);
			httpRequest = httpPut;
			httpPut.setEntity(entity);
		} else {
			httpRequest = new BasicHttpRequest(verb, uri);
		}
		
		try {
			httpRequest.setHeaders(convertHeaders(headers));
			
			HttpResponse zuulResponse = httpclient.execute(httpHost, httpRequest);
			return zuulResponse;
		} finally {
		}
	}

}
