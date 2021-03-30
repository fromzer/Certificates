package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CertificateController {
    private GiftCertificateServiceImpl giftCertificateService;

    @Autowired
    public CertificateController(GiftCertificateServiceImpl giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping("/certificates")
    public Long create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        try {
            return giftCertificateService.create(giftCertificateDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificateDTO getCertificateById(@PathVariable Long id) {
        try {
            return giftCertificateService.findById(id).get();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/certificates")
    public List<GiftCertificateDTO> getCertificatesWithParameters(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sort", required = false) String sort) {
        return giftCertificateService.findCertificateByParams(tag, name, description, sort);
    }
}
