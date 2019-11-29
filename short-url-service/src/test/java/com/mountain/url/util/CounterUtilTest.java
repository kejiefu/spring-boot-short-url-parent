package com.mountain.url.util;

import com.mountain.url.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther kejiefu
 * @Date 2019/4/26 0026
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CounterUtilTest {


    @Test
    public void getNumber() {
        Integer number = CounterUtils.getInstance().getNumber();
        System.err.println(number);
    }


}
