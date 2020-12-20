package com.renjian.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder; 
import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 创建线程池
 */
public class RExecutorUtil {

    //核心池数

    private static int CORE_POOL_SIZE = 10;

    // 线程池最大线程数

    private static int MAX_POOL_SIZE = 30;

    // 额外线程空状态生存时间

    private static int KEEP_ALIVE_TIME = 30*1000;

    //队列数

    private static int ARRAY_NUM = 20;

    private static ThreadPoolExecutor threaPool;

    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(ARRAY_NUM);

    private static void  createExecutorPool(){
        threaPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                MILLISECONDS,
                workQueue,
                new ThreadFactoryBuilder().setNameFormat("myExecutor-task-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
                );
    }

    public static ThreadPoolExecutor getExecutor(){
        if(threaPool==null){
            createExecutorPool();
            System.out.println("创建线程池完毕");
        }
        return threaPool;
    }



}
