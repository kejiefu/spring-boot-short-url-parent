package com.mountain.url.service;

import com.mountain.url.bean.DataResult;

/**
 * @Auther kejiefu
 * @Date 2019/4/25 0025
 */
public interface ShortUrlService {

    /**
     * 将长网址转换成短网址
     *
     * @param url               长网址
     * @param expireTime        过期时间
     * @param statisticalPeriod 统计周期,默认为0
     * @return DataResult
     */
    DataResult<String> getShortUrlByLongUrl(String url, Integer expireTime, Integer statisticalPeriod);

    /**
     * 将短网址转换成长网址
     *
     * @param url 短网址
     * @return DataResult
     */
    DataResult<String> getLongUrlByShortUrl(String url);

    /**
     * 更新过期时间
     *
     * @param url 短网址
     * @return DataResult
     */
    DataResult<String> updateExpireTimeByUrl(String url, Integer expireTime);

}
