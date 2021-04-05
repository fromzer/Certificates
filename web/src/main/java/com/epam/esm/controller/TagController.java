package com.epam.esm.controller;

import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {

    private GiftTagServiceImpl tagService;
    private TagValidator tagValidator;

    @Autowired
    public TagController(GiftTagServiceImpl tagService, TagValidator tagValidator) {
        this.tagService = tagService;
        this.tagValidator = tagValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(tagValidator);
    }

    @PostMapping("/tags")
    public ResponseEntity<Long> create(@Valid @RequestBody GiftTag tag) throws CreateResourceException {
        return ResponseEntity.ok(tagService.create(tag));
    }

    @DeleteMapping("/tags")
    public ResponseEntity<Object> delete(@RequestBody GiftTag giftTag) throws DeleteResourceException {
        tagService.delete(giftTag);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<GiftTag> getTagById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @GetMapping("/tags")
    public ResponseEntity<List<GiftTag>> getAll() throws ResourceNotFoundException {
        return ResponseEntity.ok(tagService.findAll());
    }
}
