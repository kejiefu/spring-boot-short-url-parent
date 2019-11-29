package com.mountain.url.repository;

import com.mountain.url.repository.model.LinkPo;

/**
 * @Auther kejiefu
 * @Date 2019/4/30 0030
 */
public interface LinkRepository {


    LinkPo getLinkById(Integer id);

    LinkPo getLinkByUrl(String url);

    LinkPo saveLink(LinkPo linkVo);

    long updateLink(LinkPo linkVo);

    void deleteUserById(Long id);

}
