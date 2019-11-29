package com.mountain.url;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;



@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "exp_record")
public class ExpRecord implements Serializable {

    private static final long serialVersionUID = -7373962522715313232L;

    /**
     * id
     */
    @Id
    private long id;
    /**
     * 描述
     */
    @Field("a")
    private String remark;
    /**
     * 用户
     */
    @Field("uid")
    private Long uid;
    /**
     * 过期时间
     */
    @Field("c")
    private Integer date;
    /**
     * 创建时间
     */
    @Field("ct")
    private Integer createTime;


}
