package org.example.controller;/* 
 @author: Javohir
  Date: 5/26/2022
  Time: 2:06 PM*/

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@Slf4j
public class SseEmitterController {


    @GetMapping("/emit-data-sets")
    public SseEmitter sseEmitter() throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        SseEmitter sseEmitter=new SseEmitter(Long.MAX_VALUE);
        SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event();
        service.submit(
                () -> {
                    try {
                        for (int i = 0; i < 10; i++) {
                            randomDelay();
                            sseEmitter.send(
                                    eventBuilder
                                            .data("This is data")
                                            .name("dataSet-created")
                                            .id("This is id")
                                            .comment("This is comment")
                                            .reconnectTime(3L)
                                            .build()
                            );
                        }
                    } catch (IOException e) {
                        log.error(e.getLocalizedMessage());
                        throw new RuntimeException(e);
                    }
                }
        );
        sseEmitter.onCompletion(() -> log.info("SseEmitter is completed calculation indicator_values"));
        sseEmitter.onTimeout(() ->{
            log.info("SseEmitter is timed out");
            sseEmitter.complete();
        } );
        sseEmitter.onError((ex) -> log.info("SseEmitter got error:", ex));
        service.shutdown();
        return sseEmitter;
    }
    private void randomDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
