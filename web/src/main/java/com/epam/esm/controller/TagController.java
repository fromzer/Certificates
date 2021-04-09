package com.epam.esm.controller;

import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller for Tags
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/tags", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {

    private GiftTagService tagService;
    private TagValidator tagValidator;

    @Autowired
    public TagController(GiftTagService tagService, TagValidator tagValidator) {
        this.tagService = tagService;
        this.tagValidator = tagValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(tagValidator);
    }

    /**
     * Create tag
     *
     * @param tag the GiftTag
     * @return the response entity
     * @throws CreateResourceException the service exception
     */
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody GiftTag tag) {
        return ResponseEntity.ok(tagService.create(tag));
    }

    /**
     * Delete tag
     *
     * @param giftTag the GiftTag
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody GiftTag giftTag) {
        tagService.delete(giftTag);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get tag by id
     *
     * @param id the GiftTag id
     * @return the tag
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<GiftTag> getTagById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    /**
     * Get all tags
     *
     * @return List of GiftTags
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<List<GiftTag>> getAll() {
        return ResponseEntity.ok(tagService.findAll());
    }
}
