package example.hello_security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
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
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if(traceId == null){
            traceId = UUID.randomUUID().toString();
        }
        MDC.put(MDC_TRACE_ID_KEY,traceId);
        //log.info("请求路径：" + request.getRequestURI() + "请求体：" + request.getReader());
        try{
            filterChain.doFilter(request,response);
        }finally {
            //log.info("running log end");
            MDC.remove(MDC_TRACE_ID_KEY);
        }
    }
}
