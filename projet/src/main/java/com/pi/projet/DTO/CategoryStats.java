package com.pi.projet.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryStats {
    private String categoryName;
    private long projectCount;

    public CategoryStats(String categoryName, long projectCount) {
        this.categoryName = categoryName;
        this.projectCount = projectCount;
    }
}
