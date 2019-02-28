package org.grootsec.scubbs.util;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

import static org.grootsec.scubbs.util.ConstantValue.SALT;

public class StringUtils {

    public static String md5Encode(String password) {
        password = password + SALT;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String getCookie() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int num = random.nextInt(62);
            buffer.append(str.charAt(num));
        }
        return buffer.toString();
    }

    public static String getTeamId() {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int num = random.nextInt(10);
            buffer.append(str.charAt(num));
        }
        return "T" + buffer.toString();
    }

    public static String getRandom() {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10);
            buffer.append(str.charAt(num));
        }
        return buffer.toString();
    }

    public static String cleanXSS(String value)
    {
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }

}
