package com.example.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Deprecated
//@Component
public class LogTraceIdInterceptor implements HandlerInterceptor {
    private static final String TRACE_ID_HEADER = "trace-id";
    private static final String MDC_TRACE_ID_KEY = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头获取 trace-id，若不存在则生成 UUID
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null){
            traceId = UUID.randomUUID().toString();
        }
        MDC.put(MDC_TRACE_ID_KEY,traceId);
        log.info("请求url:" + request.getRequestURI() + ";请求入参:" + request.getQueryString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        response.addHeader("TRACE_ID_HEADER",MDC.get(MDC_TRACE_ID_KEY));
        log.info("返回结果:" + response.getStatus() + response.getOutputStream().toString());
        // 请求完成后清除 MDC 中的 traceId
        MDC.remove(MDC_TRACE_ID_KEY);
    }
}
