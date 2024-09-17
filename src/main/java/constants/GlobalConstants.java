package constants;

import java.io.File;
import java.util.regex.Pattern;

import helpers.PropertiesHelpers;

public class GlobalConstants {
	// Timeout variable
	public static long LONG_TIMEOUT=60;
    public static long NORMAL_TIMEOUT=30;
    public static long SHORT_TIMEOUT=15;
    public static long VERY_SHORT_TIMEOUT=5;
    public static String UPLOAD_FILE="";

    public static final long LONG_TIME = 60;
    public static final long NORMAL_TIME = 30;
    public static final long SHORT_TIME = 15;
    public static final int VERY_SHORT_TIME = 3;
    public static final long SLEEP_TIME = 3;
    public static final long LOADING_TIME = 3;
    
    public static final String URL = PropertiesHelpers.getValue("URL_LOGIN");
    public static final String TARGET = PropertiesHelpers.getValue("TARGET");
    public static final String BROWSER = PropertiesHelpers.getValue("BROWSER");
    public static final String HEADLESS = PropertiesHelpers.getValue("HEADLESS");
    public static final String REMOTE_URL = PropertiesHelpers.getValue("REMOTE_URL");
    public static final String REMOTE_PORT = PropertiesHelpers.getValue("REMOTE_PORT");
    public static final String RETRY_TEST_FAIL = PropertiesHelpers.getValue("RETRY_TEST_FAIL");
    public static final String ACTIVE_PAGE_LOADED = PropertiesHelpers.getValue("ACTIVE_PAGE_LOADED");
    
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String OS_NAME = System.getProperty("os.name");
    
    public static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    public static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    /*
    private static String extentReportFilePath = "";

    public static String getExtentReportFilePath() {
        if (extentReportFilePath.isEmpty()) {
            extentReportFilePath = createReportPath();
        }
        return extentReportFilePath;
    }

    private static String createReportPath() {
        if (System.getProperty("override_report").equalsIgnoreCase("no")) {
            return EXTENT_REPORTS_FOLDER_PATH + File.separator + System.currentTimeMillis() + File.separator + "index.html";
        } else {
            return EXTENT_REPORTS_FOLDER_PATH + File.separator + "index.html";
        }
    }
    */
}