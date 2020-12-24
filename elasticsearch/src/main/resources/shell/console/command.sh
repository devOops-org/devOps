### curl healthCheck
#curl -XGET 'localhost:9200'

################################################################################ Index API
# 인덱스 생성
#curl -XPUT 'localhost:9200/test_index?pretty=true'

# 인덱스 정보(매핑 정보) 확인
#curl -XGET 'localhost:9200/test_index/_mapping?pretty=true'

# 인덱스 삭제
#curl -X DELETE 'localhost:9200/test_index?pretty'
################################################################################


################################################################################ Document API

# Single API
# 1. index
# 2. get
# 3. delete
# 4. update

# Multi
# 1. Multi get 다수의 문서를 조회
# 2. bulk 대량의 문서를 색인
# 3. delete by query 다수의 문서를 삭제
# 4. update by query 다수의 문서를 수정
# 5. reindex 인덱스의 문서를 다시 색인

# Document 단건 등록
#curl -X PUT "http://localhost:9200/movie/_doc/2?pretty=true" \
# -H "Content-Type: application/json" \
#-d @movie_document.json

# Document 단건 조회
#curl -XGET "http://localhost:9200/movie/_doc/1?pretty"

# Document 단건 삭제
#curl -XDELETE "http://localhost:9200/movie/_doc/1?pretty"

################################################################################ Search API

# 검색 시 time_out 적절한 설정이 중요

# Search - doc id로 검색
#curl -X GET "http://localhost:9200/movie/_doc/1?pretty"

# Search - URI 요청 TODO 왜 안됨 !!
#curl -X POST "http://localhost:9200/movie/_search?q=typeNm:장편"

# Search - Request Body를 이용한 검색
#curl -X POST "http://localhost:9200/movie/_search?pretty" \
#-H "Content-Type: application/json" \
#-d '
#{
#  /* 조회 요청 */
#  "query" : {
#    /* 검색 조건 정의 */
#    "term" : { "movieNm" : "살아남은"}
#  }
#}'
