package com.epam.esm.controller;

import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
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
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
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
    @PostMapping("/tags")
    public ResponseEntity<Long> create(@Valid @RequestBody GiftTag tag) throws CreateResourceException {
        return ResponseEntity.ok(tagService.create(tag));
    }

    /**
     * Delete tag
     *
     * @param giftTag the GiftTag
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/tags")
    public ResponseEntity<Object> delete(@RequestBody GiftTag giftTag) throws DeleteResourceException, ResourceNotFoundException {
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
    @GetMapping("/tags/{id}")
    public ResponseEntity<GiftTag> getTagById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(tagService.findById(id));
    }

    /**
     * Get all tags
     *
     * @return List of GiftTags
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/tags")
    public ResponseEntity<List<GiftTag>> getAll() throws ResourceNotFoundException {
        return ResponseEntity.ok(tagService.findAll());
    }
}
