package com.mountain.url.controller;

import com.mountain.url.bean.DataResult;
import com.mountain.url.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther kejiefu
 * @Date 2019/4/25 0025
 */
@RestController
@Slf4j
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @Value("${short-host-name}")
    private String SHORT_HOST_NAME;

    /**
     * 将长网址转换成短网址
     * RequestParam required=true 表示如果没有传递参数，则会报 400参数异常。value=”name”  表示参数名称。
     *
     * @param url               长网址
     * @param expireTime        过期时间
     * @param statisticalPeriod 统计周期,默认为0
     * @return DataResult
     */
    @RequestMapping(value = "/long-url/convert")
    public DataResult<String> getShortUrlByLongUrl(@RequestParam(value = "url") String url, @RequestParam(value = "expireTime") Integer expireTime, Integer statisticalPeriod) {
        return shortUrlService.getShortUrlByLongUrl(url, expireTime, statisticalPeriod);
    }

    /**
     * 将短网址转换成长网址
     *
     * @param url 短网址
     * @return DataResult
     */
    @RequestMapping(value = "/short-url/convert")
    public DataResult<String> getLongUrlByShortUrl(String url) {
        return shortUrlService.getLongUrlByShortUrl(url);
    }


    /**
     * 短链接访问跳转到长链接
     *
     * @param tag 短网址
     *            tag是没有意义的,只是个定义,后面加了正则表达式,只能输入1到16位的长度的字母或者数字
     *            想去掉限制的话,直接是@GetMapping("/{tag}")
     *            http://localhost:18082/12,获取到tag=12
     * @return DataResult
     */
    @GetMapping("/{tag:[0-9a-zA-Z]{1,16}}")
    public ModelAndView all(@PathVariable String tag) {
        String url = SHORT_HOST_NAME + "/" + tag;
        DataResult<String> dataResult = shortUrlService.getLongUrlByShortUrl(url);
        if (dataResult.getCode() == DataResult.CODE_SUCCESS && dataResult.getData() != null) {
            log.info("url:{},返回参数:{}", tag, dataResult);
            return new ModelAndView("redirect:" + dataResult.getData());
        }
        return new ModelAndView("forward:/html/index.html");
    }

}
