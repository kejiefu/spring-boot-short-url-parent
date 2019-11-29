package com.mountain.url.util;

import com.mountain.url.thread.CounterThread;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther kejiefu
 * @Date 2019/4/26 0026
 * https://blog.csdn.net/ted_cs/article/details/82969093
 * 回顾我们new对象的3个步骤
  ① 分配内存空间
  ② 初始化对象
  ③ 将对象指向刚分配的内存空间
   但jvm在指令优化时，会出现步骤②和③对调的情况，比如线程1在经过俩层为null判断后，进入new的动作，
   在还没有初始化对象时，就返加了地址值，线程2在第一个为null判断时，因为对象已经不为空，那么就直接返回了对象。
 */
@Slf4j
public class CounterUtils {

    private volatile static CounterUtils singleton = null;

    private static CounterThread counterThread = null;

    private CounterUtils() {
    }

    public static CounterUtils getInstance() {
        if (singleton == null) {
            synchronized (CounterUtils.class) {
                if (singleton == null) {
                    counterThread = new CounterThread();
                    counterThread.start();
                    singleton = new CounterUtils();
                }
            }
        }
        return singleton;
    }

    /**
     * 获取生成随机数
     *
     * @return
     */
    public Integer getNumber() {
        try {
            return counterThread.getNumber();
        } catch (Exception ex) {
            log.error("getNumber", ex);
        }
        return 0;
    }

}
