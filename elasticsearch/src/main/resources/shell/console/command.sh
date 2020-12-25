# ElasticSearch curl Format
#curl -X{메서드} http://host:port/{인덱스}/{타입}/{문서ID} -d '{json 데이터}'

### curl healthCheck
#curl -XGET 'localhost:9200'

################################################################################ Index API
# 인덱스 생성
#curl -XPUT 'localhost:9200/test_index?pretty=true'

# 인덱스 정보(매핑 정보) 확인
#curl -XGET 'localhost:9200/test_index2/_mapping?pretty=true'

# 인덱스 삭제
#curl -X DELETE 'localhost:9200/test_index?pretty'

# 인덱스 리스트 확인
#curl -X GET 'localhost:9200/_cat/indices?v'

# 인덱스 선 생성 후 매핑 추가
#curl -X PUT 'localhost:9200/test_index2?pretty'
#curl -H 'content-type:application/json' \
#-X PUT 'localhost:9200/test_index2/_mapping?pretty' \
#-d '
#{
#  "properties" : {
#    "board_id" : { "type" : "integer" },
#    "subject" : {
#      "type" : "text",
#      "fields" : {
#        "keyword" : {
#          "type" : "keyword",
#          "ignore_above" : 256
#        }
#      }
#    },
#    "content" : {
#      "type" : "text",
#      "fields" : {
#        "keyword" : {
#          "type" : "keyword",
#          "ignore_above" : 256
#        }
#      }
#    },
#    "created_at" : {
#      "type" : "date",
#      "format" : "yyyy-MM-dd HH:mm:ss"
#    }
#  }
#}
#'

# 인덱스 설정 수정 API [인덱스 static / dynamic index settings 가 있음](https://www.elastic.co/guide/en/elasticsearch/reference/current/index-modules.html#index-modules-settings)
#curl -X PUT "localhost:9200/test_index2/_settings?pretty" \
#-H 'content-type:application/json' \
#-d '
#{
#  "index" : {
#    "number_of_replicas" : 2
#  }
#}
#'

# [매핑](https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html)
# PUT /{target}/_mapping
# PUT /{target1},{target2}/_mapping

# [매핑] 특정 인덱스의 특정 매핑 정보 확인하기
#curl -X GET "http://localhost:9200/test_index2/_mapping/field/board_id?pretty"

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
