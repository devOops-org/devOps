package kr.seok.elastic.controller;

import kr.seok.elastic.domain.es.HospitalEsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/idx")
@RestController
@RequiredArgsConstructor
public class IndexController {
    private final ElasticsearchRestTemplate restTemplate;

    /* 인덱스 생성 */
    @GetMapping("/v1/create")
    public boolean createIdx() {
        return restTemplate.indexOps(HospitalEsEntity.class)
                .create();
    }

    /* 인덱스 삭제 */
    @GetMapping("/v1/delete")
    public boolean deleteIdx() {
        return restTemplate.indexOps(HospitalEsEntity.class)
                .delete();
    }
}
