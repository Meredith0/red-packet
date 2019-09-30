## 高并发抢红包系统

[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)
<p align="left">
  <a href="https://circleci.com/gh/vuejs/vue/tree/dev"><img src="https://img.shields.io/circleci/project/github/vuejs/vue/dev.svg" alt="Build Status"></a>
  <a href="https://www.npmjs.com/package/vue"><img src="https://img.shields.io/npm/l/vue.svg" alt="License"></a>
</p>

## 技术栈
    * Spring Boot
    * Redis
    * RabbitMQ
    
## 简介
__发红包__: 

    1. 将红包拆分后缓存进Redis的list

__抢红包__: 

    1. 登记用户id到RabbitMQ, 返回
    2. 处理RabbitMQ的消息, 把用户id和红包绑定, 存入Redis的map, 并放入RabbitMQ的持久化队列
    3. 前端通过用户id和红包id轮询
    4. 将RabbitMQ的持久化队列异步存入MySQL

## 压力测试
测试环境: JMeter

250条线程循环50次, CPU已满载。测得结果QPS：877.1；Avg：159；ERROR：0%


![image](https://github.com/Meredith0/red-packet/blob/master/src/doc/images/test.png)

