package com.example.demo.coupon.model.entity;

import com.example.demo.havecoupon.model.entity.HaveCoupon;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Integer discount;
    private Integer couponCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
    private List<HaveCoupon> haveCouponList = new ArrayList<>();

}
