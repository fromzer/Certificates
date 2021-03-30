package com.epam.esm.controller;

import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.service.impl.GiftTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Long create(@RequestBody GiftTagDTO tag) {
        return tagService.create(tag);
    }

    @DeleteMapping("/tags")
    public void delete(@RequestBody GiftTagDTO giftTagDTO) {
        tagService.delete(giftTagDTO);
    }

    @GetMapping("/tags/{id}")
    public GiftTagDTO getTagById(@PathVariable Long id) {
        return tagService.findById(id).get();
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
    public List<GiftTagDTO> getAll() {
        return tagService.findAll();
    }
}
