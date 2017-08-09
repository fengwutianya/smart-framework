package org.smart4j.framework;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 一个Servlet处理所有的请求 再调用其他Action就可以了呗
 * Created by xuan on 2017/8/6 0006.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{
    @Override
    public void init(ServletConfig config) throws ServletException {    //ServletConfig, ServletContext, ServletRegistration
        HelperLoader.init();
        //ServletContext的获取，用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理Jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //注册处理静态资源的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //跳过了组成Request对象 直接拿到handler
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {  //如果有handler可以处理这个请求
            Class<?> controllerClass = handler.getControllerClass();    //此handler的controller类
            Object controllerBean = BeanHelper.getBean(controllerClass);//根据controller类拿到bean对象
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }
            //method params 都从req对象获取到，body从req.getInputStream中获取到??
            //明白了 其实是所有的数据都可以设置到getParameters里面,需要满足一定条件,不满足的话 就被编码到inputstream里面了
            String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (array != null && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            //经过以上两个步骤,全部参数都被获取到了
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (path.startsWith("/")) {
                    //意思是如果含有路径信息,就进行重定向
                    resp.sendRedirect(req.getContextPath() + path); //重定向
                } else {    //不是以/开头 说明就直接是个jsp页面了
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                }
            } else if (result instanceof Data) {    //Json
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter out = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    out.write(json);
                    out.flush();
                    out.close();
                }
            }
        }
    }

    @Override
    public void destroy() {
        //todo
    }
}
