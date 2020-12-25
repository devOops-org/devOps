# [Elastic Search Sample Data](https://ikeptwalking.com/elasticsearch-sample-data/)

# 전체 인덱스 리스트 확인
#curl -X GET 'localhost:9200/_cat/indices?v'

# 특정 인덱스 매핑 정보 확인
#curl -XGET 'localhost:9200/companydatabase/_mapping?pretty'

#curl -XPUT 'localhost:9200/companydatabase?pretty'

#curl -XPUT 'localhost:9200/companydatabase?pretty' \
#-H 'Content-Type: application/json' \
#-d'
#{
#  "mappings" : {
#    "employees" : {
#      "properties" : {
#        "FirstName" : { "type" : "text" },
#        "LastName" : { "type" : "text" },
#        "Designation" : { "type" : "text" },
#        "Salary" : { "type" : "integer" },
#        "DateOfJoining" : {
#          "type" : "date",
#          "format": "yyyy-MM-dd"
#        },
#        "Address" : { "type" : "text" },
#        "Gender" : { "type" : "text" },
#        "Age" : { "type" : "integer" },
#        "MaritalStatus" : { "type" : "text" },
#        "Interests" : { "type" : "text" }
#      }
#    }
#  }
#}
#'

# 문서 bulk로 넣기
#curl -XPUT 'localhost:9200/companydatabase/_bulk' \
#-H 'Content-Type: application/json' \
#--data-binary @Employees100K.json

# 입력된 인덱스의 문서 건수 확인
#curl -X GET 'http://localhost:9200/companydatabase/_count?pretty'
