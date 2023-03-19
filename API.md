## API 명세서

#### 공통 Response

| Name    | Type    | Description        |    
|---------|---------|--------------------|
| success | Boolean | API 호출 성공여부        |
| data    | Object  | API 호출 성공했을 경우 데이터 |
| message | String  | API 호출 실패했을 경우 메시지 |


### 블로그 검색하기

#### 기본 정보

```http request
GET /search/blog
Host: localhost:8080
```

#### Request

##### Parameter

| Name    | Type    | Description                                                | Require |
|---------|---------|------------------------------------------------------------|---------|
| url     | String  | 검색을 원하는 블로그 URL                                            | O       |
| keyword | String  | 검색을 원하는 블로그의 키워드                                           | X       |
| sort    | String  | 결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy | X       |
| page    | Integer | 결과 페이지 번호, 1~50 사이의 값, 기본 값 1                              | X       |
| size    | Integer | 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10                       | X       |


#### Response

##### data

| Name             | Type   | Description    |
|------------------|--------|----------------|
| content          | List   | 검색한 블로그 목록     |
| content/title    | String | 글 제목           |
| content/contents | Sting  | 글 내용           |
| content/url      | String | 글 Url          |
| content/blogname | String | 블로그 이름         |
| pageable         | Object | pagination 데이터 |


##### ex

- Request
```http request
GET http://localhost:8080/search/blog?url=https://yangbongsoo.tistory.com&keyword=커스텀
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
        "title": "json error stack trace print 여부 <b>커스텀</b> 프로퍼티",
        "contents": "원하는 <b>커스텀</b> 프로퍼티를 추가한다. 보통 deploy phase 에 따라 다르게 설정된다. response: print-stack-trace: true 다음으로 /resources/META-INF/spring-configuration-metadata.json 파일에 프로퍼티에 대한 메타데이터를 추가한다. { &#34;groups&#34;: [ { &#34;name&#34;: &#34;response&#34;, &#34;type&#34;: &#34;com.toy.config...",
        "url": "http://yangbongsoo.tistory.com/63",
        "blogname": "나만의 인덱스"
      }
    ],
    "pageable": {
      "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
      },
      "offset": 10,
      "pageNumber": 1,
      "pageSize": 10,
      "paged": true,
      "unpaged": false
    },
    "last": true,
    "totalPages": 2,
    "totalElements": 11,
    "first": false,
    "size": 10,
    "number": 1,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "numberOfElements": 1,
    "empty": false
  }
}
```

### 인기 검색어 조회

#### 기본 정보

```http request
GET /search/popular-keyword
Host: localhost:8080
```

#### Request

##### Parameter

X


#### Response

| Name    | Type    | Description |
|---------|---------|-------------|
| keyword | String  | 검색한 키워드     |
| count   | Integer | 검색한 횟수      |

#### ex

- Request
```http request
GET http://localhost:8080/search/popular-keyword
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