# API 명세서

### 공통 Response

| Name    | Type    | Description        |    
|---------|---------|--------------------|
| success | Boolean | API 호출 성공여부        |
| data    | Object  | API 호출 성공했을 경우 데이터 |
| message | String  | API 호출 실패했을 경우 메시지 |


## 1. 블로그 검색하기

### 기본 정보

```http request
GET /search/blog
Host: {hostname}
```

### Request

#### Parameter

| Name    | Type    | Description                                                | Require |
|---------|---------|------------------------------------------------------------|---------|
| url     | String  | 검색을 원하는 블로그 URL                                            | O       |
| keyword | String  | 검색을 원하는 블로그의 키워드                                           | X       |
| sort    | String  | 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy | X       |
| page    | Integer | 결과 페이지 번호, 1~50 사이의 값, 기본 값 1                              | X       |
| size    | Integer | 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10                       | X       |


### Response

#### data

| Name             | Type   | Description    |
|------------------|--------|----------------|
| content          | List   | 검색한 블로그 목록     |
| content/title    | String | 글 제목           |
| content/contents | Sting  | 글 내용           |
| content/url      | String | 글 Url          |
| content/blogname | String | 블로그 이름         |
| pageable         | Object | pagination 데이터 |


#### ex

- Request
```http request
GET {hostname}/search/blog?url=https://yangbongsoo.tistory.com
Accept: application/json
```

- Response
```json
{
  "success": true,
  "message": null,
  "data": {
    "content": [
      {
        "title": "json error stack trace print 여부 커스텀 프로퍼티",
        "contents": "Status.BAD_REQUEST.getReasonPhrase()) .withStatus(Status.BAD_REQUEST) .withDetail(&#34;wrong request param&#34;) .with(&#34;name&#34;, &#34;ybs&#34;) .withType(URI.create(&#34;<b>https://yangbongsoo.tistory.com</b>&#34;)); return create(ex, builder.build(), request); } } cf) 예외가 발생했을 때 response body는 아래와 같다. { &#34;type...",
        "url": "http://yangbongsoo.tistory.com/63",
        "blogname": "나만의 인덱스",
        "datetime": "2021-11-21T16:16:08.000+00:00"
      },
      {
        "title": "WebClient 사용할때 주의 (1편)",
        "contents": "미리 webClient 를 생성해놓고 필요할 때마다 재사용을 할 때 this.webClient = WebClient.builder() .clientConnector(connector) .baseUrl(&#34;<b>https://yangbongsoo.tistory.com</b>&#34;) .build() .post(); 사용하고자 하는 쪽에서 아래와 같이 header 메서드를 쓴다면 reqest header 가 계속 누적되어 append 되는 문제가 발생...",
        "url": "http://yangbongsoo.tistory.com/9",
        "blogname": "나만의 인덱스",
        "datetime": "2021-01-18T14:13:10.000+00:00"
      }
    ],
    "pageable": {
      "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
      },
      "offset": 0,
      "pageNumber": 0,
      "pageSize": 10,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "first": true,
    "size": 10,
    "number": 0,
    "sort": {
      "empty": false,
      "unsorted": false,
      "sorted": true
    },
    "numberOfElements": 2,
    "empty": false
  }
}
```

## 2. 인기 검색어 조회

### 기본 정보

```http request
GET /search/popular-keyword
Host: {hostname}
```

### Request

#### Parameter

X


### Response

| Name    | Type    | Description |
|---------|---------|-------------|
| keyword | String  | 검색한 키워드     |
| count   | Integer | 검색한 횟수      |

### ex

- Request
```http request
GET {hostname}/search/popular-keyword
Accept: application/json
```

- Response
```json
{
  "success": true,
  "message": null,
  "data": [
    {
      "keyword": "유무선공유기",
      "count": 100
    },
    {
      "keyword": "스피커",
      "count": 90
    },
    {
      "keyword": "키보드",
      "count": 80
    },
    {
      "keyword": "공유기",
      "count": 70
    },
    {
      "keyword": "블루투스스피커",
      "count": 60
    },
    {
      "keyword": "로보성소기",
      "count": 50
    },
    {
      "keyword": "가습기",
      "count": 40
    },
    {
      "keyword": "에어프라이어",
      "count": 30
    },
    {
      "keyword": "무선AP",
      "count": 20
    },
    {
      "keyword": "크록스",
      "count": 11
    }
  ]
}
```