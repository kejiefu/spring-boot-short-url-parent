package com.mountain.url.util;

import org.junit.Test;

/**
 * @Auther kejiefu
 * @Date 2019/10/11 0011
 */
public class UrlUtilsTest {

    @Test
    public void getShortUrlByLongNumber() throws Exception {
        String string = UrlUtils.getShortUrlByNumber(63);
        System.out.println(string);
    }

    @Test
    public void getLongNumberByShortUrl() throws Exception {
    }

    @Test
    public void getParamValueByUrl() throws Exception {
    }

}