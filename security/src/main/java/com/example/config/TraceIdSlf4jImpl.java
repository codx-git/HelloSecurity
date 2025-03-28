package com.example.config;
import  org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.slf4j.MDC;

public class TraceIdSlf4jImpl extends Slf4jImpl{
    private static final String MDC_TRACE_ID_KEY = "traceId";
    public TraceIdSlf4jImpl(String clazz) {
        super(clazz);
    }
    @Override
    public boolean isDebugEnabled() {
        return super.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return super.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        super.error(addTraceId(s), e);
    }

    @Override
    public void error(String s) {
        super.error(addTraceId(s));
    }

    @Override
    public void debug(String s) {
        super.debug(addTraceId(s));
    }

    @Override
    public void trace(String s) {
        super.trace(addTraceId(s));
    }

    @Override
    public void warn(String s) {
        super.warn(addTraceId(s));
    }

    private String addTraceId(String message) {
        String traceId = MDC.get(MDC_TRACE_ID_KEY);
        if (traceId != null) {
            return "[trace_id: " + traceId + "] " + message;
        }
        return message;
    }
}
