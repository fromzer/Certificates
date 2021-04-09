package com.epam.esm.controller;

import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
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
import java.util.Map;

/**
 * Rest controller for Certificates
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/certificates", produces = {MediaType.APPLICATION_JSON_VALUE})
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
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody GiftCertificate giftCertificate) {
        return ResponseEntity.ok(giftCertificateService.create(giftCertificate));
    }

    /**
     * Update certificate and optionally create received tags
     *
     * @param giftCertificate the certificate and optionally tags
     * @return certificate and tags
     * @throws UpdateResourceException the service exception
     */
    @PatchMapping
    public ResponseEntity<GiftCertificate> update(@Valid @RequestBody GiftCertificate giftCertificate) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificate));
    }

    /**
     * Delete certificate
     *
     * @param giftCertificate the certificate
     * @return response entity
     * @throws DeleteResourceException   the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody GiftCertificate giftCertificate) {
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
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> getCertificateById(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    /**
     * Get certificates by parameters
     *
     * @param params the search and sort params
     * @return list of giftCertificate
     * @throws ResourceNotFoundException
     */
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getCertificatesWithParameters(
            @RequestParam(required = false) Map<String, String> params) {
        if (params.isEmpty()) {
            return ResponseEntity.ok(giftCertificateService.findAll());
        }
        return ResponseEntity.ok(giftCertificateService.findCertificateByParams(params));
    }
}
