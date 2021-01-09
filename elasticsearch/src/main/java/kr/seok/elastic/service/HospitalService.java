package kr.seok.elastic.service;

import kr.seok.elastic.domain.es.HospitalEsEntity;
import kr.seok.elastic.repository.elastic.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
//    private final ElasticsearchRestTemplate restTemplate;

    public String save(HospitalEsEntity hospital) {
        return hospitalRepository.findById(hospital.getId())
                .orElseGet(() -> hospitalRepository.save(hospital))
                .getId();
    }

    public HospitalEsEntity findById(String id) {
        /* id 값으로 조회*/
        return hospitalRepository.findById(id).orElseGet(HospitalEsEntity::new);
    }

    public List<HospitalEsEntity> findAll(int page) {
        Iterable<HospitalEsEntity> all = hospitalRepository.findAll(PageRequest.of(page, 1000));
        List<HospitalEsEntity> list = new ArrayList<>();
        all.forEach(list::add);
        return list;
    }

    public void search() {

    }
}
