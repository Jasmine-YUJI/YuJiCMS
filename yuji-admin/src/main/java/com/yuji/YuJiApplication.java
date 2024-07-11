package com.yuji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author Liguoqiang
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class YuJiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(YuJiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  雨霁启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "              _ _                                  \n" +
                "             (_|_)                                 \n" +
                "  _   _ _   _ _ _ ______ ___ _ __ ___  ___         \n" +
                " | | | | | | | | |______/ __| '_ ` _ \\/ __|       \n" +
                " | |_| | |_| | | |     | (__| | | | | \\__ \\      \n" +
                "  \\__, |\\__,_| |_|      \\___|_| |_| |_|___/     \n" +
                "   __/ |    _/ |                                   \n" +
                "  |___/    |__/                                    \n");
    }
}
