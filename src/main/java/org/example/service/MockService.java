package org.example.service;/* 
 @author: Javohir
  Date: 5/26/2022
  Time: 2:16 PM*/

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MockService {

    List<SseEmitter.SseEventBuilder> emitters=new ArrayList<>();



}
