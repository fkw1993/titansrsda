package com.titansoft.utils.config;


import com.titansoft.service.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**定时任务配置
 * @Author: Kevin
 * @Date: 2019/8/4 18:11
 */
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class TimerTaskConfig {
    @Autowired
    MediaService mediaService;
    private static final Logger logger = LoggerFactory.getLogger(TimerTaskConfig.class);

    /**
     * @description 图片转换，定期扫描是否存在未转换成优化图像的图片，查看图片一般查看的是优化库的数据
     * @param
     * @return
     * @author Fkw
     * @date 2019/8/4
     */
    //异步调用
    @Async
    @Scheduled(fixedDelay = 30000)  //间隔1秒
    public void changeImages() throws InterruptedException {
        logger.info("************************开始扫描未优化的图片数据************************");
        mediaService.changeImages();
        logger.info("************************完成扫描未优化的图片数据************************");
    }

   /* @Async
    @Scheduled(fixedDelay = 2000)
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }*/
}
