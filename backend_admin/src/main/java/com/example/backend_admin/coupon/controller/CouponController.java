package com.example.backend_admin.coupon.controller;

import com.example.backend_admin.common.BaseException;
import com.example.backend_admin.common.BaseResponse;
import com.example.backend_admin.coupon.model.request.PostCouponCreateReq;
import com.example.backend_admin.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.backend_admin.common.BaseResponseStatus.*;

@RestController
@RequestMapping("/admin/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@Valid @RequestBody PostCouponCreateReq postCouponCreateReq) {

        try {
            return ResponseEntity.ok().body(BaseResponse.successResponse(couponService.create(postCouponCreateReq)));
        }catch (BaseException exception){
            return ResponseEntity.ok().body(BaseResponse.failResponse(exception.getBaseResponseStatus()));
        }

    }
    @RequestMapping(method = RequestMethod.GET,value = "/read/{idx}")

    public ResponseEntity read(@PathVariable Long idx){
        try {
            return ResponseEntity.ok().body(BaseResponse.successResponse(couponService.read(idx)));
        }catch (BaseException exception){
            return ResponseEntity.ok().body(BaseResponse.failResponse( exception.getBaseResponseStatus()));
        }

    }
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public ResponseEntity list(){

        try {
            return ResponseEntity.ok().body(BaseResponse.successResponse(couponService.list()));
        }catch (BaseException exception){
            return ResponseEntity.ok().body(BaseResponse.failResponse(exception.getBaseResponseStatus()));
        }

    }
}
