package com.sushi.members;

import com.sushi.members.Services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("MyTaskScheduler")
@Log
public class MyTaskScheduler {
    @Autowired
    private UserService userService;
    @Scheduled(fixedDelay = 1000*60*60)
    public void initDatabase() {
        log.info("*********Start to run task initDatabase");
        userService.addRole(Long.valueOf(1),"USER");
        userService.addRole(Long.valueOf(2),"ADMIN");
        userService.addUser("root");
        userService.initData();
        log.info("*********Finished to run task initDatabase");
    }
}
