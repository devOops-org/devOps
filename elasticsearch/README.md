# Elastic Search

## Elastic Stack Study

```text

주제: 엘라스틱 서치 스터디
목표: 엘라스틱 서치의 목적과 필요성을 이애하고 활용방안에 대해 학습
스케줄: 2020 12 ~ 2021 01
일시: 매주 토요일 오전 10시 ~ 12시
장소: 홍대 셀스스터디룸
준비물: 개인 노트북, 스터디 비용
총 인원: 5명
참고자료: https://www.elastic.co/kr/

최종 목표
    1. Ealsticsearch 클러스터 설치, Kibana 설치
    2. ES 쿼리
    3. Java Client
    4. Spring boot batch
    5. Serving API - Elastic APM
```

- [20201205](/docs/20201205.md)
- [20201212](/docs/20201212.md)

## ISSUE

- Multi Node로 구성 중에 일어나는 이슈
    - [세 노드 중 하나가 계속 up 되지 않는 오류](/issue/errlog/elastic-err.log)
    - 원인
        - docker-compose에 설정된 각 노드의 힙 사이즈가 512로 설정되어 있어 최소 1.5g가 필요한 상황
        - docker desktop에서 resource memory를 확인해보니 2g 로 잡혀있어 메모리가 부족하여 노드 하나가 실행될 수 없었음
    - 메모리를 세개의 노드가 모두 실행될 수 있는 사이즈로 수정
