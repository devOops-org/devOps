# Elastic Search

## 수정이 필요한 설정 파일
- docker container 접근

```shell
docker exec -it keen_banzai /bin/bash
```
- 경로 보기
```shell
config
  jvm.options
    # 최소 Heap 사이즈 
    Xms1g
    # 최대 Heap 사이즈
    Xmx1g

  elasticsearch.yml
    # 클러스터 이름
    cluster.name 
    # 노드 이름
    node.name 
    # 인덱스 경로 지정, 미설정 시에 기본 값은 ES밑 data 디렉토리에 인덱스 생성
    path.data
    # 로그를 저장할 경로 지정
    path.logs
    # ES 인덱스 백업을 위한 스냅샷의 경로 지정
    path.repo
    # 접속 허용할 IP를 설정 / 0.0.0.0 은 모든 접근을 허용 | 127.0.0.1은 개발모드에서 프로덕트 모드로 변경
    network.host
    # 엘라스틱서치 서버에 접근할 수 있는 HTTP API 호출을 위한 포트번호 설정 / 기본 값은 9200
    http.port
    # 엘라스틱서치 클라이언트가 접근할 수 있는 TCP 포트, 기본 값은 9300
    transport.tcp.port
    # 노드가 여러개인 경우 유니캐스트로 활성화된 다른 서버를 찾음. 노드의 아이피를 [1.1.1.1, 2.2.2.2]와 같은 형식으로 넣어주면 된다.
    discovery.zen.ping.unicast.hosts
    # 마스터 노드의 선출 기준이 되는 노드의 수를 지정.
    discovery.zen.minimum_master_nodes
    # 마스터 노드로 동작 여부를 지정
    node.master
    # 데이터 노드로 동작 여부를 지정
    node.data
    
  log4j2.properties
    status
    logger
    appender
```
