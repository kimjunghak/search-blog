# 블로그 검색 서비스

## 사전 준비
### 도커로 rabbit mq 실행
    - docker run -d --name blog-mq -p 5672:5672 -p 15672:15672 --restart=unless-stopped rabbitmq:management

### application.yml 파일 
- mq 설정을 확인해주세요

[API 명세서](API_SPEC.md)

[JAR파일](https://github.com/kimjunghak/search-blog/raw/master/blog-0.0.1-SNAPSHOT.jar)