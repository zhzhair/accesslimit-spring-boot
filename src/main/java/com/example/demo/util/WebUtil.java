package com.example.demo.util;

import com.example.demo.configuration.Constant;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class WebUtil {

    /**
     * 从druid源码复制过来的
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && isNotValidAddress(ip)) {
            ip = null;
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null && isNotValidAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null && isNotValidAddress(ip)) {
                ip = null;
            }
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip != null && isNotValidAddress(ip)) {
                ip = null;
            }
        }

        return ip;
    }

    private static boolean isNotValidAddress(String ip) {
        if (ip == null) {
            return true;
        } else {
            for(int i = 0; i < ip.length(); ++i) {
                char ch = ip.charAt(i);
                if ((ch < '0' || ch > '9') && (ch < 'A' || ch > 'F') && (ch < 'a' || ch > 'f') && ch != '.' && ch != ':') {
                    return true;
                }
            }

            return false;
        }
    }

    public static String getMACAddress(){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] bytes = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            for (int i = 0; i < bytes.length; i++) {
                if(i != 0){
                    stringBuilder.append("-");
                }
                String s = Integer.toHexString(bytes[i] & 0xFF);
                stringBuilder.append(s.length() == 1? 0 + s : s);
            }
        } catch (UnknownHostException |SocketException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().toUpperCase().replace("-","");
    }

    @Resource
    private Constant constant;
    private static Constant constants;
    @PostConstruct
    public void init(){
        constants = constant;
    }
}
