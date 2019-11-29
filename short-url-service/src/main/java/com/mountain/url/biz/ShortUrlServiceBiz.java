package com.mountain.url.biz;

import com.alibaba.fastjson.JSONObject;
import com.mountain.url.bean.DataResult;
import com.mountain.url.repository.LinkRepository;
import com.mountain.url.repository.model.LinkPo;
import com.mountain.url.util.CounterUtils;
import com.mountain.url.util.DateUtils;
import com.mountain.url.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Auther kejiefu
 * @Date 2019/4/25 0025
 */
@Slf4j
@Service
public class ShortUrlServiceBiz {

    @Autowired
    LinkRepository linkRepository;

    @Value("${short-host-name}")
    private String SHORT_HOST_NAME;

    //地址斜杠
    private final static String URL_SLASH = "/";

    /**
     * 将长网址转换成短网址
     *
     * @param url               长网址
     * @param expireTime        过期时间
     * @param statisticalPeriod 统计周期
     * @return 短网址
     */
    public DataResult<String> getShortUrlByLongUrl(String url, Integer expireTime, Integer statisticalPeriod) {
        DataResult<String> dataResult = new DataResult<>();
        if (StringUtils.isEmpty(url)) {
            dataResult.setMsg("url不能为空");
            dataResult.setCode(DataResult.CODE_FAIL);
            return dataResult;
        } else if (expireTime == null) {
            dataResult.setMsg("过期时间不能为空");
            dataResult.setCode(DataResult.CODE_FAIL);
            return dataResult;
        }
        try {
            java.net.URL link = new java.net.URL(url);
        } catch (Exception ex) {
            String text = "url:" + url + "将长网址转换成短网址失败:" + ex.getMessage();
            log.error(text, ex);
            dataResult.setMsg(text);
            dataResult.setCode(-99);
            return dataResult;
        }
        //获取的递增数
        Integer longUrlId = CounterUtils.getInstance().getNumber();
        LinkPo linkPo = new LinkPo();
        linkPo.setId(longUrlId);
        linkPo.setUrl(url);
        linkPo.setStatisticalPeriod(statisticalPeriod);
        linkPo.setExpireTime(expireTime);
        linkPo.setCreateTime(DateUtils.getCurrentTimeSeconds());
        linkPo.setUpdateTime(DateUtils.getCurrentTimeSeconds());
        linkRepository.saveLink(linkPo);
        //去转换短网址
        String cleanUrl = UrlUtils.getShortUrlByNumber(longUrlId);
        String generalUrl = SHORT_HOST_NAME + "/" + cleanUrl;
        dataResult.setCode(DataResult.CODE_SUCCESS);
        dataResult.setData(generalUrl);
        dataResult.setMsg("");
        log.info("将长网址转换成短网址成功,url:" + url + ",结果:" + generalUrl + ",获取的递增数是:" + longUrlId);
        return dataResult;
    }

    /**
     * 将短网址转换成长网址
     *
     * @param url 短网址
     * @return 长网址
     */
    public DataResult<String> getLongUrlByShortUrl(String url) {
        DataResult<String> dataResult = new DataResult<>();
        Integer longUrlId = 0;
        if (StringUtils.isEmpty(url)) {
            dataResult.setMsg("url不能为空");
            dataResult.setCode(DataResult.CODE_FAIL);
            return dataResult;
        }
        try {
            java.net.URL link = new java.net.URL(url);
            String file = link.getFile();
            String shortUrl = file.substring(file.indexOf(URL_SLASH) + 1);
            //将短网址转换成递增数
            longUrlId = UrlUtils.getNumberByShortUrl(shortUrl);
            LinkPo linkPo = linkRepository.getLinkById(longUrlId);
            if (linkPo != null) {
                if (linkPo.getExpireTime() < DateUtils.getCurrentTimeSeconds()) {
                    dataResult.setMsg("短网址已过期");
                    dataResult.setCode(DataResult.CODE_FAIL);
                    return dataResult;
                }
                else{
                    dataResult.setSuccessData(linkPo.getUrl());
                }
            } else {
                dataResult.setMsg("短网址不存在");
                dataResult.setCode(DataResult.CODE_FAIL);
                return dataResult;
            }
        } catch (Exception e) {
            log.error("将短网址转换成长网址异常:", e);
        } finally {
            log.info("将短网址转换成长网址,url:" + url + ",结果:" + JSONObject.toJSONString(dataResult) + ",获取的递增数是:" + longUrlId);
        }
        return dataResult;
    }


}
