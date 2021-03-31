package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
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
    public ResponseEntity<Long> create(@RequestBody GiftCertificateDTO giftCertificateDTO) throws CreateResourceException {
        return ResponseEntity.ok(giftCertificateService.create(giftCertificateDTO));
    }

    @PatchMapping("/certificates")
    public ResponseEntity<GiftCertificateDTO> update(@RequestBody GiftCertificateDTO giftCertificateDTO) throws UpdateResourceException {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificateDTO));
    }

    @DeleteMapping("/certificates")
    public ResponseEntity<Object> delete(@RequestBody GiftCertificateDTO certificateDTO) throws DeleteResourceException {
        giftCertificateService.delete(certificateDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/certificates/{id}")
    public ResponseEntity<GiftCertificateDTO> getCertificateById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @GetMapping("/certificates")
    public ResponseEntity<List<GiftCertificateDTO>> getCertificatesWithParameters(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sort", required = false) String sort) throws ResourceNotFoundException {
        return ResponseEntity.ok(giftCertificateService.findCertificateByParams(tag, name, description, sort));
    }
}
