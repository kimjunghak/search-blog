package com.serach.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}

/*
과제 내용
-  오픈 API를 이용하여 "블로그 검색 서비스"를 만들려고 합니다.

- "과제 기능 요구 사항"을 구현해 주시기 바랍니다.

 요구 사항 및 제약사항이 충족되지 않은 결과물은 코드레벨 평가를 진행하지 않습니다.

- 아래의 "코드레벨 평가항목"으로 코드를 평가합니다.

- "블로그 검색 서비스"의 API 명세를 제출해주세요.
 */

/*
요구사항
1. 블로그 검색

  - 키워드를 통해 블로그를 검색할 수 있어야 합니다.

  - 검색 결과에서 Sorting(정확도순, 최신순) 기능을 지원해야 합니다.

  - 검색 결과는 Pagination 형태로 제공해야 합니다.

  - 검색 소스는 카카오 API의 키워드로 블로그 검색(https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.

  - 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려해야 합니다.


2. 인기 검색어 목록

  - 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.

  - 검색어 별로 검색된 횟수도 함께 표기해 주세요.

* 주어진 요구사항 이외의 추가 기능 구현에 대한 제약은 없으며, 새롭게 구현한 기능이 있을 경우 README 파일에 기재 바랍니다.
 */
