package com.epam.esm.controller;

import com.epam.esm.exception.*;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.CertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller for Certificates
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class CertificateController {
    private GiftCertificateService giftCertificateService;

    private CertificateValidator certificateValidator;

    @Autowired
    public CertificateController(GiftCertificateService giftCertificateService, CertificateValidator certificateValidator) {
        this.giftCertificateService = giftCertificateService;
        this.certificateValidator = certificateValidator;
    }

    @InitBinder
    protected void initBinderCreate(WebDataBinder binder) {
        binder.addValidators(certificateValidator);
    }

    /**
     * Create certificate
     *
     * @param giftCertificate the certificate
     * @return new certificate's id
     * @throws CreateResourceException the service exception
     */
    @PostMapping("/certificates")
    public ResponseEntity<Long> create(@Valid @RequestBody GiftCertificate giftCertificate) throws CreateResourceException {
        return ResponseEntity.ok(giftCertificateService.create(giftCertificate));
    }

    /**
     * Update certificate and optionally create received tags
     *
     * @param giftCertificate the certificate and optionally tags
     * @return certificate and tags
     * @throws UpdateResourceException the service exception
     */
    @PatchMapping("/certificates")
    public ResponseEntity<GiftCertificate> update(@Valid @RequestBody GiftCertificate giftCertificate) throws UpdateResourceException {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificate));
    }

    /**
     * Delete certificate
     *
     * @param giftCertificate the certificate
     * @return response entity
     * @throws DeleteResourceException the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/certificates")
    public ResponseEntity<Object> delete(@RequestBody GiftCertificate giftCertificate) throws DeleteResourceException, ResourceNotFoundException {
        giftCertificateService.delete(giftCertificate);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get certificate by id
     *
     * @param id the GiftCertificate id
     * @return the giftTag
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/certificates/{id}")
    public ResponseEntity<GiftCertificate> getCertificateById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    /**
     * Get certificates by parameters
     *
     * @param tag the GiftTag name
     * @param name the GiftCertificate name or partial name
     * @param description the GiftCertificate description or partial description
     * @param sort string in format 'column_name,order_by'
     * @return list of giftCertificate
     * @throws ResourceNotFoundException
     */
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
