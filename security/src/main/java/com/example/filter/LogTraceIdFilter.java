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
        log.info("request_url:{};request_method:{};request_body:{}"
                ,request.getRequestURI(),request.getMethod(),
                (request.getParameterMap().isEmpty() ? "{}" : JSON.toJSONString(request.getParameterMap())));
        try{
            filterChain.doFilter(request,response);
        }finally {
            //log.info("running log end");
            //返回的结果带上trace_id
            long costTime = System.currentTimeMillis() - costStart;
            String body = "";
            if(response.getContentType() != null && response.getContentType().startsWith("application/json")){
                body = JSON.toJSONString(response.getOutputStream().toString());
            }else {
                body = "[BINARY_DATA]";
            }
            log.info("response_status:{};cost_time:{}ms;response_body:{}"
                    ,response.getStatus(),costTime, body);
            response.setHeader(TRACE_ID_HEADER,traceId);
            MDC.remove(MDC_TRACE_ID_KEY);
        }
    }

}
