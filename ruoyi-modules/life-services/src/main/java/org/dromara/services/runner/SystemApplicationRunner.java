//package org.dromara.services.runner;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.dromara.services.service.IChPayConfigService;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
///**
// * 初始化 system 模块对应业务数据
// *
// * @author Lion Li
// */
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class SystemApplicationRunner implements ApplicationRunner {
//
//    private final IChPayConfigService payConfigService;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        payConfigService.init();
//        log.info("初始化AliPay配置成功");
//    }
//
//}
