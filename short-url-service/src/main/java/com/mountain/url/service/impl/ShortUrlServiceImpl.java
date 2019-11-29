package com.mountain.url.service.impl;

import com.mountain.url.bean.DataResult;
import com.mountain.url.biz.ShortUrlServiceBiz;
import com.mountain.url.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther kejiefu
 * @Date 2019/4/25 0025
 */
@Service("shortUrlService")
public class ShortUrlServiceImpl implements ShortUrlService {

    @Autowired
    ShortUrlServiceBiz shortUrlServiceBiz;

    public DataResult<String> getShortUrlByLongUrl(String url, Integer expireTime, Integer statisticalPeriod) {
        return shortUrlServiceBiz.getShortUrlByLongUrl(url, expireTime, statisticalPeriod);
    }


    public DataResult<String> getLongUrlByShortUrl(String url) {
        return shortUrlServiceBiz.getLongUrlByShortUrl(url);
    }


    public DataResult<String> updateExpireTimeByUrl(String url, Integer expireTime) {
        return null;
    }
}
