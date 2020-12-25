package kr.seok.elastic.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "author", createIndex = false)
public class Author {
    @Id
    private String id;
}
