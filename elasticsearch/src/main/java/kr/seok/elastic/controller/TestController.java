package kr.seok.elastic.controller;

import kr.seok.elastic.domain.es.Person;
import kr.seok.elastic.repository.elastic.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final PersonRepository repository;

    @PostMapping("/person")
    public String save(@RequestBody Person person) {

        boolean b = elasticsearchRestTemplate.indexOps(Person.class).exists();
        Person save;
        if (!b) {
            elasticsearchRestTemplate.indexOps(Person.class).create();
        }
        save = repository.save(person);
        return save.getId();
    }

    @GetMapping("/person/{id}")
    public Person findById(@PathVariable("id")  String id) {
        return elasticsearchRestTemplate.get(id, Person.class);
    }
}
