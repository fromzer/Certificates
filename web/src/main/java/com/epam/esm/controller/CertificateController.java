package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
        return giftCertificateService.create(giftCertificateDTO);
    }

    @PatchMapping("/certificates")
    public GiftCertificateDTO update(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.update(giftCertificateDTO);
    }

    @DeleteMapping("/certificates")
    public void delete(@RequestBody GiftCertificateDTO certificateDTO) {
        giftCertificateService.delete(certificateDTO);
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificateDTO getCertificateById(@PathVariable Long id) {
        return giftCertificateService.findById(id).get();
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
