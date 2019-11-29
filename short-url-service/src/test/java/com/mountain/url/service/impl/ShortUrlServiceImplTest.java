package com.mountain.url.service.impl;

import com.mountain.url.Application;
import com.mountain.url.service.ShortUrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther kejiefu
 * @Date 2019/4/30 0030
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortUrlServiceImplTest {

    @Autowired
    ShortUrlService shortUrlService;

    @Test
    public void getShortUrlByLongUrl() throws Exception {
        String url = "https://www.cnblogs.com/xiandnc/p/10788401.html";
        Integer expireTime = 1559322061;
        Integer statisticalPeriod = 0;
        shortUrlService.getShortUrlByLongUrl(url, expireTime, statisticalPeriod);
    }

    @Test
    public void getLongUrlByShortUrl() throws Exception {
        String url = "http://127.0.0.1/Z";
        shortUrlService.getLongUrlByShortUrl(url);
    }

    @Test
    public void updateExpireTimeByUrl() throws Exception {
    }

}