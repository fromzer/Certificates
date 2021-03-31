package com.epam.esm.controller;

import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    private final GiftTagServiceImpl tagService;

    @Autowired
    public TagController(GiftTagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    public ResponseEntity<Long> create(@RequestBody GiftTagDTO tag) throws CreateResourceException {
        return ResponseEntity.ok(tagService.create(tag));
    }

    @DeleteMapping("/tags")
    public ResponseEntity<Object> delete(@RequestBody GiftTagDTO giftTagDTO) throws DeleteResourceException {
        tagService.delete(giftTagDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<GiftTagDTO> getTagById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(tagService.findById(id));
    }

//    @GetMapping("/tags")
//    public GiftTagDTO getTagByName(@RequestParam String name) {
//        try {
//            return tagService.findByName(name);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @GetMapping("/tags")
    public ResponseEntity<List<GiftTagDTO>> getAll() throws ResourceNotFoundException {
        return ResponseEntity.ok(tagService.findAll());
    }
}
