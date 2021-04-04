package com.epam.esm.controller;

import com.epam.esm.exception.*;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.validation.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CertificateController {
    private GiftCertificateServiceImpl giftCertificateService;

    private CertificateValidator certificateValidator;

    @Autowired
    public CertificateController(GiftCertificateServiceImpl giftCertificateService, CertificateValidator certificateValidator) {
        this.giftCertificateService = giftCertificateService;
        this.certificateValidator = certificateValidator;
    }

    @InitBinder
    protected void initBinderCreate(WebDataBinder binder) {
        binder.addValidators(certificateValidator);
    }

    @PostMapping("/certificates")
    public ResponseEntity<Long> create(@Valid @RequestBody GiftCertificate giftCertificate) throws CreateResourceException, ValidationException {
        return ResponseEntity.ok(giftCertificateService.create(giftCertificate));
    }

    @PatchMapping("/certificates")
    public ResponseEntity<GiftCertificate> update(@Valid @RequestBody GiftCertificate giftCertificate) throws UpdateResourceException {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificate));
    }

    @DeleteMapping("/certificates")
    public ResponseEntity<Object> delete(@RequestBody GiftCertificate certificateDTO) throws DeleteResourceException {
        giftCertificateService.delete(certificateDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<GiftCertificate> getCertificateById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @GetMapping("/certificates")
    public ResponseEntity<List<GiftCertificate>> getCertificatesWithParameters(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sort", required = false) String sort) throws ResourceNotFoundException {
        if (tag == null && name == null && description == null && sort == null) {
            return ResponseEntity.ok(giftCertificateService.findAll());
        }
        return ResponseEntity.ok(giftCertificateService.findCertificateByParams(tag, name, description, sort));
    }
}
