package ru.gumerbaev.shipbooking.server;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Айдар on 24.02.2016.
 */
public class SpringRPCDispatcherServlet extends RemoteServiceServlet {
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(SpringRPCDispatcherServlet.class);
    private static final String SERVICE_URL_MAPPER = "serviceURLMapper";
    private static final UrlPathHelper pathHelper = new UrlPathHelper();

    private WebApplicationContext applicationContext;
    private Map<String, RemoteService> springRPCServices = new HashMap<>();

    @Override
    public void init() {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        if (applicationContext == null) {
            throw new IllegalStateException("No Spring web application context found");
        }

        if (StringUtils.isEmpty(this.getInitParameter(SERVICE_URL_MAPPER))) {
            throw new IllegalArgumentException("The servlet SpringRPCDispatcherServlet should have a '" + SERVICE_URL_MAPPER + "' parameter defined");
        } else {
            String beanName = this.getInitParameter(SERVICE_URL_MAPPER);
            this.initServiceURLMapper(beanName);
        }
        logger.info("SpringRPCDispatcherServlet deployed");
    }

    @SuppressWarnings("unchecked")
    private void initServiceURLMapper(String beanName) {
        this.springRPCServices = (Map<String, RemoteService>) applicationContext.getBean(beanName, Map.class);
    }

    @Override
    public String processCall(String payload) throws SerializationException {
        try {
            RemoteService handler = retrieveSpringBean(getThreadLocalRequest());
            RPCRequest rpcRequest = RPC.decodeRequest(payload, handler.getClass(), this);
            onAfterRequestDeserialized(rpcRequest);
            if (logger.isDebugEnabled()) {
                logger.debug("Invoking " + handler.getClass().getName() + "." + rpcRequest.getMethod().getName());
            }
            return RPC.invokeAndEncodeResponse(handler, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy());
        } catch (IncompatibleRemoteServiceException ex) {
            logger.error("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
            return RPC.encodeResponseForFailure(null, ex);
        }
    }

    protected RemoteService retrieveSpringBean(HttpServletRequest request) {
        String serviceURL = extractServiceURL(request);
        RemoteService bean = getBeanByServiceURL(serviceURL);

        if (logger.isDebugEnabled()) {
            logger.debug("Bean for service " + serviceURL + " is " + bean.getClass());
        }
        return bean;
    }

    private String extractServiceURL(HttpServletRequest request) {
        String service = pathHelper.getPathWithinServletMapping(request);
        if (logger.isDebugEnabled()) {
            logger.debug("Service for URL " + request.getRequestURI() + " is " + service);
        }
        return service;
    }

    private RemoteService getBeanByServiceURL(String serviceURL) {
        if (!this.springRPCServices.containsKey(serviceURL)) {
            throw new IllegalArgumentException("Spring bean not found for service URL: " + serviceURL);
        }
        return this.springRPCServices.get(serviceURL);
    }
}
