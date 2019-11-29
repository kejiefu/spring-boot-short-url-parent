package com.mountain.url.util;

import java.net.MalformedURLException;
import java.util.Stack;

/**
 * @Auther kejiefu
 * @Date 2019/4/25 0025
 */
public class UrlUtils {


    //62进制
    private static int BASE_NUM = 62;

    //62进制字母顺序
    private static final char[] array = {'G', 'q', 'w', '0', 'H', 'e', 'T', 'F', '9', 'r', 'V', 't', 'y', 'u', 'N', 'i', '6', 'D', 'o', 'p', 'L', 'a', 's', 'd', 'K', 'f', 'g', 'h', 'j', 'k', '4', 'l', 'z', 'x', 'c', 'v', 'b', 'S', 'n', 'm', '1', 'Z', '3', '5', 'Q', 'W', 'E', 'R', '7', 'Y', 'U', 'I', 'O', '2', 'P', 'A', 'J', 'X', 'C', 'B', '8', 'M'};


    /**
     * 将10进制数转为62进制字符串
     *
     * @param number
     * @return
     */
    public static String getShortUrlByNumber(Integer number) {
        Integer rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        if (0 == rest) {
            return String.valueOf(array[0]);
        }
        while (rest != 0) {
            int index = rest - (rest / BASE_NUM) * BASE_NUM;
            Character character = array[index];
            stack.add(character);
            rest = rest / BASE_NUM;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 通过短网址返回10进制数
     *
     * @param shortUrl
     * @return
     */
    public static Integer getNumberByShortUrl(String shortUrl) {
        Integer multiple = 1;
        Integer result = 0;
        Character c;
        for (int i = 0; i < shortUrl.length(); i++) {
            c = shortUrl.charAt(shortUrl.length() - i - 1);
            result += valueOfCharacter(c) * multiple;
            multiple = multiple * BASE_NUM;
        }
        return result;
    }

    /**
     * 字母对应的值 如array数组 G对应0 q对应1
     *
     * @param c
     * @return
     */
    private static int valueOfCharacter(Character c) {
        for (int i = 0; i < array.length; i++) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }

    public static String getParamValueByUrl(String url, String param) {
        String result = "";
        try {
            java.net.URL link = new java.net.URL(url);
            String query = link.getQuery();
            if (query != null && query.trim().length() > 0) {
                String[] array = query.split("&");
                if (array.length > 0) {
                    for (int i = 0; i < array.length; i++) {
                        String obj = array[i];
                        if (obj.contains(param)) {
                            int index = obj.indexOf("=");
                            result = obj.substring(index + 1, obj.length());
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
