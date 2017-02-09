package org.yashpalmeena.properties;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class Config {
	private final Properties 	prop 		= new Properties();
	public 	final static String LMS_URL 	= "lms_url";
	public 	final static String CMS_URL 	= "cms_url";
	public 	final static String SERVICE_URL = "service_url";
	public 	final static String TOKEN_URL 	= "token_url";
	public 	final static String GRANT_TYPE 	= "grant_type";
	public 	final static String USERNAME 	= "email";
	public 	final static String PASSWORD	= "password";
	public 	final static String TOKEN_NAME	= "access_token";
	
	
	private Config() {
		InputStream inputStream;
		String propFileName = "config.properties";
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
    private static class SingletonHelper {
        private static final Config INSTANCE = new Config();    
    }
	    
    public static Config getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public static String get(String name) {
    	return Config.getInstance().prop.getProperty(name);
	}
    
    public static void set(String name, String value) {
    	Config.getInstance().prop.setProperty(name, value);
	}
    
    public static String get(String name, Object...values ) {
    	return MessageFormat.format(Config.getInstance().prop.getProperty(name), values);
	}
    
    public static String get(String name, String...values) {
    	Object[] objects = values;
    	return MessageFormat.format(Config.getInstance().prop.getProperty(name), objects);
	}
}