package cn.mbdoge.blog.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jyx
 */
@Slf4j
public final class Utils {
    private static final char[] SEED_CHAR = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9',
             'l', 'o', '0', '1', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static String randomCharacter(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("randomCharacter length < 0");
        }

        if (length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(SEED_CHAR[(int) Math.floor(Math.random() * SEED_CHAR.length)]);
        }

        return sb.toString();
    }

    /**
     * 获取异常深的 getCause
     *
     * @param e Throwable
     * @return Cause
     */
    public static Throwable getCause(Throwable e) {
        Throwable throwable = e;
        Throwable last = e;

        while (throwable != null) {
            last = throwable;
            throwable = throwable.getCause();
        }

        return last;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            // if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
            //     //根据网卡取本机配置的IP
            //     InetAddress inet = null;
            //     try {
            //         inet = InetAddress.getLocalHost();
            //     } catch (UnknownHostException e) {
            //         // e.printStackTrace();
            //     }
            //     ipAddress = inet.getHostAddress();
            // }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // "***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
