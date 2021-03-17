package com.wallet.model;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "order")
public class Order {
    private Long id;
    private Category categoryId;
    private Integer amount;
    private String description;
    private Wallet walletId;
    private Boolean isExpense;
    private Date createdDate;
}
