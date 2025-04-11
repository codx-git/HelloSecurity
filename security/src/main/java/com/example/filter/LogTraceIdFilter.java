package com.example.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
@Slf4j
@Component
public class LogTraceIdFilter extends OncePerRequestFilter {
    private static final String TRACE_ID_HEADER = "trace-id";
    private static final String MDC_TRACE_ID_KEY = "traceId";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //log.info("running log start");
        long costStart = System.currentTimeMillis();
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if(traceId == null){
            traceId = UUID.randomUUID().toString();
        }
        MDC.put(MDC_TRACE_ID_KEY,traceId);
        log.info("request_url:" + request.getRequestURI() + ";request_method:" + request.getMethod()
                + ";request_body:" + (request.getParameterMap().isEmpty() ? "{}" : JSON.toJSONString(request.getParameterMap())));
        try{
            filterChain.doFilter(request,response);
        }finally {
            //log.info("running log end");
            //返回的结果带上trace_id
            long costTime = System.currentTimeMillis() - costStart;
            log.info("code:" + response.getStatus() + ";cost_time:" + costTime + "ms;response_body" + response.getOutputStream().toString());
            response.setHeader(TRACE_ID_HEADER,traceId);
            MDC.remove(MDC_TRACE_ID_KEY);
        }
    }
}
