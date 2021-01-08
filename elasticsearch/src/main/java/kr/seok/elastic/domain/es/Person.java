package kr.seok.elastic.domain.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Setter
@Getter
@Document(indexName = "person")
public class Person {
    @Id
    String id;
    String firstname;
    String lastname;

}
