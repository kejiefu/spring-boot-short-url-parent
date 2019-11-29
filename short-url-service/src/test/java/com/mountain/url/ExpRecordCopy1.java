package com.mountain.url;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @Auther kejiefu
 * @Date 2019/7/5 0005
 */
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "exp_record_copy1")
public class ExpRecordCopy1 {

    private static final long serialVersionUID = -7373962522715313232L;

    /**
     * id
     */
    @Id
    private String id;
    /**
     * 描述
     */
    @Field("c")
    private Integer count;
    /**
     * 用户
     */
    @Field("uid")
    private Long uid;

}
