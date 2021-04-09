package com.epam.esm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchAndSortParams {
    private String tag;
    private String name;
    private String description;
    private String sort;
}
