package com.tianyalan.zuulserver.filters;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
public class TraceFilter extends ZuulFilter{
    
    @Autowired
    Tracer tracer;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        
        ctx.getResponse().addHeader("trace-id", tracer.getCurrentSpan().traceIdString());
         
        ctx.getResponse().addHeader("span-id", String.valueOf(tracer.getCurrentSpan().getSpanId()));
        
        return null;
    }
    
    
    void sample() {

    	String tagValue = null;

        Span newSpan = this.tracer.createSpan("newSpan");
        try {
        	
			// 给一个Span打Tag
        	this.tracer.addTag("tagName", tagValue);
        	// 在Span上记录事件
        	newSpan.logEvent("eventOccured");
        } finally {
        	
        	//关闭Span
        	this.tracer.close(newSpan);
        }
    }
}