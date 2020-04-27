package pers.jyx.blog;

/**
 * @author jyx
 */
public class Constant {

    public static final String DOMAIN = "mbdoge.cn";
    public static final String DOMAIN_NAME = "mbdoge";

    public static final String API_SERVLET_URL_PREFIX = "/api/v2";
    public static final String API_SERVLET_URL_MATCH = "/api/v2/**";

    public static final String LOGIN_TOKEN_KEY = "mbdoge-user:";
    public static final String DEFAULT_CATEGORY_ID = "";

    public static final String GUEST_INFO_PREFIX = "#";
    public static final long GUEST_TOKEN_EXPIRE = 30 * 60 * 60 * 24;

    public static final int FETCHER_TIMEOUT = 5000;
}
