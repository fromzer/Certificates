package com.epam.esm.controller;

import com.epam.esm.dto.GiftTagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.entity.GiftTag;
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
    public Long create(@RequestBody GiftTag giftTag) {
        GiftTagDTO giftTagDTO = GiftTagDTO.builder()
                .giftTag(giftTag)
                .build();
        try {
            return tagService.create(giftTagDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/tags")
    public void delete(@RequestBody GiftTag giftTag) {
        GiftTagDTO giftTagDTO = GiftTagDTO.builder()
                .giftTag(giftTag)
                .build();
        try {
            tagService.delete(giftTagDTO);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/tags/{id}")
    public GiftTagDTO getTagById(@PathVariable Long id) {
        try {
            return tagService.findById(id).get();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
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
        try {
            return tagService.findAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
}
