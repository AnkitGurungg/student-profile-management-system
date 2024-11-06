package com.spms.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolController {

    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/schools")
    public SchoolDto create (
            @RequestBody SchoolDto schoolDto
    ) {
        return schoolService.create(schoolDto);
    }

    @GetMapping("/schools")
    public List<SchoolDto> findAll () {
        return schoolService.findAll();
    }
}