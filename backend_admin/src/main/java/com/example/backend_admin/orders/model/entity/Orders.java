package com.example.backend_admin.orders.model.entity;

import com.example.backend_admin.common.BaseTimeEntity;
import com.example.backend_admin.customer.entity.Customer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable=false)
    private String impUid;

    private Long productIdx;

    private Integer productPrice;

    @ManyToOne
    @JoinColumn(name="customer_idx")
    private Customer customer;

    public static Orders dtoToEntity(String impUid, Customer customer, Long productIdx,  Integer productPrice) {
        return Orders.builder()
                .customer(customer)
                .impUid(impUid)
                .productIdx(productIdx)
                .productPrice(productPrice)
                .build();
    }

}
