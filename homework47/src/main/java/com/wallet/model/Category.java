package com.wallet.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@XmlRootElement(name = "category")
public class Category {
    private Long id;
    private String name;
    private User userId;
    private Category parentCategoryId;
    private Boolean isActive;
    private Date createdDate;
}
