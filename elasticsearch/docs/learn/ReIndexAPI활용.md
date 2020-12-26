# Elastic Search
## `ReIndex`를 해야하는 상황
- mapping이 변경되어 기존과 조회 결과가 달라지는 경우
- es 버전 마이그레이션 하는 경우
- `index의` 이름 변경이 필요한 경우 -> `alias` 활용하는 경우 안해도 됨
- 이전 인덱스를 새로운 인덱스로 `reindex` 하는 경우

## ReIndex를 하는 여러 가지 방법
- 전체 데이터를 ReIndex하는 방법
    ```shell
    curl -H 'Content-Type: application/json' \
    -X POST http://localhost:9200/_reindex \
    -d '
    {
      "source": {
        "index": "{old_index}"
      },
      "dest" :{
        "index" : "{new_index}"
      }
    }
    '
    ```

- 기존 인덱스의 일부 필드만 `reindex` 하는 경우
    ```shell
    curl -H 'Content-Type: application/json' \
    -X POST http://localhost:9200/_reindex \
    -d '
    {
      "source": {
        "index": "{old_index}",
        "_source": {
          "includes": ["field1", "field2"]
        }
      },
      "dest" :{
        "index" : "{new_index}"
      }
    }
    '
    ```

- 전체 데이터가 아니라 일부 데이터만 `reindex` 하는 방법
    - ES 클러스터의 부하를 줄이기 위한 방법
    - `alias`를 활용하여 관리할 수 있다.
    ```shell
    curl -H 'Content-Type: application/json' \
    -X POST http://localhost:9200/_reindex \
    -d `
    {
      "source": {
        "index": "{old-index}",
        "_source": {
          "includes": ["field1", "field2"]
        },
        "size": 10000,
        "query": {
          "bool": {
            "filter": [
              {
                "match_phrase": {
                  "field1": "search_keyword"
                }
              },
              {
                "range": {
                  "datetime": {
                    "gte": "2020-07-05T05:05:54.000Z"
                  }
                }
              }
            ]
          }
        }
      },
      "dest": {
          "index": "{new-index}"
      }
    }`
    ```

## 참고
- [참고](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html)
