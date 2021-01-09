package kr.seok.elastic.controller;

import kr.seok.elastic.domain.es.HospitalEsEntity;
import kr.seok.elastic.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/hospital")
@RestController
@RequiredArgsConstructor
public class HospitalApiController {
    private final HospitalService hospitalService;

    /* 저장..? */
    @PostMapping("/save/{id}")
    public String saveHospital(@RequestBody HospitalEsEntity hospital, @PathVariable String id) {
        return hospitalService.save(hospital);
    }

    /* _doc/{id} */
    @GetMapping("/{id}")
    public HospitalEsEntity getHospital(@PathVariable String id) {
        return hospitalService.findById(id);
    }

    /* 페이징 하기 */
    @GetMapping("/page/{page}")
    public List<HospitalEsEntity> getHospitals(@PathVariable int page) {
        return hospitalService.findAll(page);
    }

    @GetMapping("/search/")
    public void searchGroup() {
        hospitalService.search();
    }
}
