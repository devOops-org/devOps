package kr.seok.elastic.repository.elastic;

import kr.seok.elastic.domain.es.HospitalEsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HospitalRepository extends ElasticsearchRepository<HospitalEsEntity, String> {

    Page<HospitalEsEntity> findAll(Pageable page);

}
