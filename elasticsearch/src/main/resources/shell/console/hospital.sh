# 인덱스 삭제
curl -X DELETE "localhost:9200/hospital_index?pretty"

# 인덱스 생성
curl -X PUT "localhost:9200/hospital_index?pretty" \
-H "Content-Type: application/json" \
-d @hospital_index.json

# 인덱스 확인
curl -X GET "localhost:9200/hospital_index?pretty"
#curl -X GET "localhost:9200/hospital?pretty"

# 인덱스 검색
#curl -X GET "localhost:9200/hospital_index/_search?pretty"

# 재인덱스
#curl -H 'Content-Type: application/json' \
#    -X POST "http://localhost:9200/_reindex?pretty" \
#    -d '
#    {
#      "source": {
#        "index": "hospital_index"
#      },
#      "dest" :{
#        "index" : "hospital_index"
#      }
#    }
#    '

# 문서 삭제
#curl -X DELETE "localhost:9200/hospital_index/_doc/A1119129?pretty"

# 문서 Match 조회
#curl -XGET "localhost:9200/hospital_index/_search?pretty" \
#-H "Content-Type: application/json" \
#-d '
#{
#  "query": {
#    "match": {
#      "org_id": "A1100001"
#    }
#  }
#}
#'

#curl -X GET "http://localhost:9200/hospital_index/_doc/1?pretty"

#curl -XPUT 'localhost:9200/hospital_index/_doc/A1119129?pretty' \
#-H "Content-Type: application/json" \
#-d '{
#  "org_id" : "A1119129"
#}
#'
