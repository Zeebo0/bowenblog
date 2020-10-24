## 博客论坛

## 资料
[spring 文档](https://spring.io/guides)  
[GitHub App](https://docs.github.com/en/free-pro-team@latest/developers/apps/building-oauth-apps)


## 工具
[idea](https://www.jetbrains.com/idea/)  
[git](https://git-scm.com/)  
[vp](http://www.visual-paradigm.com)

## 脚本
~~~sql
create table user
(
    id int auto_increment primary key,
    account_id   varchar(100),
    name         varchar(50),
    token        char(36),
    gmt_create   bigint,
    gmt_modified bigint
);
~~~

## 项目部署
### 依赖
 - git  
 - jdk  
 - maven
### 步骤
 - yum update  
 - yum install git  
 - mkdir APP 
 - cd APP/
 - git clone https://github.com/Zeebo0/bowenblog.git
 - yum install maven
 - mvn -v
 - mvn clean compile package
 - cp src/main/resources/application.properties src/main/resources/application-production.properties
 - vim src/main/resources/application-production.properties
 - mvn package
 
