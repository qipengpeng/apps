package com.ugo.interceptor;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author qipeng 2018/8/22
 *  所以请求拦截
 */
public class HandleInterceptorALL extends HandlerInterceptorAdapter {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(HandleInterceptorALL.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            if (handler instanceof HandlerMethod) {
//                HandlerMethod handlerMethod = (HandlerMethod) handler;
//                String beanName = handlerMethod.getBean().getClass().toString();//类
//                String methodName = handlerMethod.getMethod().getName();//方法名称
//                if(methodName.equals("error") || methodName.equals("success")) {
//                    return super.preHandle(request, response, handler);
//                }
//                String uri = request.getRequestURI();//请求路径
                String remoteAddr = getIpAddr(request);//ip
//                String method = request.getMethod(); //请求方式
                Map<String,String[]> pramMap = request.getParameterMap();
                StringBuffer sbf = new StringBuffer();
                int count = 0;
                String forCunt = "";
                for(Map.Entry<String, String[]> entry:pramMap.entrySet()){
                    forCunt = "[" + count + "]" + " : " ;
                    sbf.append( "paramName" + forCunt + entry.getKey() + " - "+ "paramValue" +
                            forCunt + request.getParameter(entry.getKey()) + "\n");
                    count ++;
                }
//                logger.info(" { beanName : " + beanName + " | " + "methodName : " + methodName + " | " + "uri : "
//                        + uri + " | " + "remoteAddr : " + remoteAddr + " | " + "requestMethod : " +
//                        method + "\n" + "param : " + sbf + "}");
                StringBuilder sb = new StringBuilder(1000);
                HandlerMethod h = (HandlerMethod) handler;
                //Controller 的包名
                sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
                //方法名称
                sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
                //请求方式  post\put\get 等等
                sb.append("RequestMethod    : ").append(request.getMethod()).append("\n");
                //所有的请求参数
                sb.append("Params    : \n").append(sbf).append("\n");
                //部分请求链接
                sb.append("URI       : ").append(request.getRequestURI()).append("\n");
                //完整的请求链接
//                sb.append("AllURI    : ").append(reqURL).append("\n");
                //请求方的 ip地址
                sb.append("request IP: ").append(remoteAddr).append("\n");
                logger.info(sb.toString());

            }

        } catch (Exception e) {
            //出错
            logger.error(e+"用户操作日志记录异常" , "RequestParamInfoIntorceptor");
        }
        return super.preHandle(request, response, handler);
    }

    //获取客户端IP
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
