package kr.seok.elastic.repository.elastic;

import kr.seok.elastic.domain.es.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonRepository extends ElasticsearchRepository<Person, String> {
}
