package kr.seok.elastic.controller;

import kr.seok.elastic.domain.es.HospitalEsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/mapping")
@RestController
@RequiredArgsConstructor
public class MappingController {
    private final ElasticsearchRestTemplate restTemplate;

    /* 인덱스 Mapping 생성 */
    @GetMapping("/v1/create")
    public String createMapping() {
        return restTemplate.indexOps(HospitalEsEntity.class)
                .createMapping()
                .toJson();
    }

    /* 매핑 수정 document 설정하는 방법 찾아보기 */
    @GetMapping("/v1/update")
    public boolean updateMapping() {
        Document mapping = Document.create();
        return restTemplate.indexOps(HospitalEsEntity.class)
                .putMapping(mapping);
    }
}
