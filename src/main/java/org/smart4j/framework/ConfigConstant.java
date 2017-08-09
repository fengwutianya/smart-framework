package org.smart4j.framework;

/**
 * Created by xuan on 2017/8/5 0005.
 */
public interface ConfigConstant {
    String CONFIG_FILE = "smart.properties"; //interface public static final

    //database
    String JDBC_DRIVER = "smart.framework.jdbc.driver";
    String JDBC_URL = "smart.framework.jdbc.url";
    String JDBC_USERNAME = "smart.framework.jdbc.username";
    String JDBC_PASSWORD = "smart.framework.jdbc.password";

    String APP_BASE_PACKAGE = "smart.framework.app.base_package";
    String APP_JSP_PATH = "smart.framework.app.jsp_path";
    String APP_ASSERT_PATH = "smart.framework.app.assert_path";
}
