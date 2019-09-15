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
测试环境: 
    
JMeter
测试450个线程同时访问, 错误率0%

![image](https://github.com/Meredith0/red-packet/blob/master/src/doc/images/450.png)

测试500个请求同时访问, 错误率4.58%, 重试48次
![image](https://github.com/Meredith0/red-packet/blob/master/src/doc/images/500.png)

600个并发请求时错误率高达80%

Tomcat在500个连接后性能急剧下降
正在用Spring Could重构成分布式的
