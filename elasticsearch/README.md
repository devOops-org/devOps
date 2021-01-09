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
- [20201226](/docs/20201226.md)
- [20210109](/docs/20210109.md)

## ISSUE

- Multi Node로 구성 중에 일어나는 이슈
    - [세 노드 중 하나가 계속 up 되지 않는 오류](/issue/errlog/elastic-err.log)
    - 원인
        - docker-compose에 설정된 각 노드의 힙 사이즈가 512로 설정되어 있어 최소 1.5g가 필요한 상황
        - docker desktop에서 resource memory를 확인해보니 2g 로 잡혀있어 메모리가 부족하여 노드 하나가 실행될 수 없었음
    - 메모리를 세개의 노드가 모두 실행될 수 있는 사이즈로 수정

- [Kibana csv 파일 업로드](https://www.elastic.co/kr/blog/importing-csv-and-log-data-into-elasticsearch-with-file-data-visualizer)
    - csv 파일 전체를 import 하는 방법
        - kibana > machine learning > data visualizer 를 활용하여 업로드 가능
    - 엘라스틱 서치가 자동으로 필드에 대한 데이터 타입을 읽을 수도 있으나 권장사항은 아님

- [multi node 환경 구축 시 키바나는 어떻게 구성해야 하는지?](https://www.elastic.co/guide/en/kibana/current/production.html#high-availability)
    - 하나의 키바나에 모든 노드를 추가하여 각 노드별로 데이터를 관리할 수 있게 하는건가?

- [`reindex`를 해야 하는 상황?](/docs/learn/ReIndexAPI활용.md)

- @timestamp, date 타입 값 저장하는 방법?

## 참고 사이트
- [curl 명령어 참고](https://www.lesstif.com/software-architect/curl-http-get-post-rest-api-14745703.html)
- [Elastic 가이드북](https://esbook.kimjmin.net/)

## 참고도서
- 엘라스틱 서치 실무 가이드
- [x] 01. 검색 시스템 이해하기
- [x] 02. 엘라스틱 서치 살펴보기
- [x] 03. 데이터 모델링
- [x] 04. 데이터 검색
- [ ] 05. 데이터 집계
- [ ] 06. 고급 검색
- [ ] 07. 한글 검색 확장기능
- [x] 08. 엘라스틱 서치 클라이언트
- [ ] 09. 엘라스틱 서치와 루씬 이야기
- [ ] 10. 대용량 처리를 위한 시스템 최적화
- [ ] 11. 장애 방지를 위한 실시간 모니터링
- [ ] 12. 안정적인 클러스터 운영 노하우
- [ ] 13. 클러스터 성능 측정
