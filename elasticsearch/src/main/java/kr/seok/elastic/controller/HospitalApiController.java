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

    @PostMapping("/save/{id}")
    public String saveHospital(@RequestBody HospitalEsEntity hospital, @PathVariable String id) {
        return hospitalService.save(hospital);
    }

    @GetMapping("/{id}")
    public HospitalEsEntity getHospital(@PathVariable String id) {
        return hospitalService.findById(id);
    }

    @GetMapping("")
    public List<HospitalEsEntity> getHospitals() {
        return hospitalService.findAll();
    }

}
