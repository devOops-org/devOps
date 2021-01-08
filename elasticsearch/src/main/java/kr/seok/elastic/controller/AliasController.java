package kr.seok.elastic.controller;

import kr.seok.elastic.domain.es.HospitalEsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/alias")
@RestController
@RequiredArgsConstructor
public class AliasController {

    private final ElasticsearchRestTemplate restTemplate;

    @GetMapping("/v1/create/{aliasNm}")
    public boolean setAlias(@PathVariable String aliasNm) {
        AliasQuery aliasQuery = getAliasQuery(aliasNm);
        return restTemplate.indexOps(HospitalEsEntity.class)
                .addAlias(aliasQuery);
    }

    @GetMapping("/v1/remove/{aliasNm}")
    public boolean removeAlias(@PathVariable String aliasNm) {
        AliasQuery aliasQuery = getAliasQuery(aliasNm);
        return restTemplate.indexOps(HospitalEsEntity.class)
                .removeAlias(aliasQuery);
    }

    private AliasQuery getAliasQuery(@PathVariable String aliasNm) {
        return new AliasQuery(aliasNm);
    }

}
