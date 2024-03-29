package com.example.backend.customer.service;




import com.example.backend.common.BaseException;
import com.example.backend.common.BaseResponseStatus;
import com.example.backend.customer.model.entity.Customer;
import com.example.backend.customer.model.request.PostCustomerLoginReq;
import com.example.backend.customer.model.request.PostCustomerSignupReq;
import com.example.backend.customer.model.response.*;
import com.example.backend.customer.repository.CustomerRepository;

import com.example.backend.havecoupon.model.entity.HaveCoupon;
import com.example.backend.havecoupon.model.response.GetHaveCouponBaseRes;
import com.example.backend.havecoupon.model.response.GetHaveCouponListRes;
import com.example.backend.havecoupon.model.response.GetHaveCouponReadRes;

import com.example.backend.log.service.LoginLogService;

import com.example.backend.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.backend.common.BaseResponseStatus.*;
import static com.example.backend.common.CustomerLevel.NEWBIE;


@Service
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerEmailVerifyService customerEmailVerifyService;
    private final TokenProvider tokenProvider;
    private final LoginLogService loginLogService;

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Integer expiredTimeMs;

    public Customer getMemberByEmail (String customerEmail){
        return customerRepository.findByCustomerEmail(customerEmail).get();
    }


    // Member CRUD

    // create
    public PostCustomerSignupRes signup(PostCustomerSignupReq postCustomerSignupReq)  throws BaseException {

        Optional<Customer> duplicatedMember = customerRepository.findByCustomerEmail(postCustomerSignupReq.getCustomerEmail());
        // 멤버 정보를 빌드로 저장
        if (duplicatedMember.isPresent()){
            throw new BaseException(BaseResponseStatus.CUSTOMER_SIGNUP_DUPLICATE_EMAIL);
        }
        Customer customer = Customer.builder()
                .customerEmail(postCustomerSignupReq.getCustomerEmail())
                .customerPwd(passwordEncoder.encode(postCustomerSignupReq.getCustomerPwd()))
                .authority("CUSTOMER")
                .level(NEWBIE)
                .totalAmount(0)
                .status(false)

                .name(postCustomerSignupReq.getName())
                .gender(postCustomerSignupReq.getGender())
                .address(postCustomerSignupReq.getAddress())
                .age(postCustomerSignupReq.getAge())

                .build();

        customerRepository.save(customer);

        Customer customer1 = customerRepository.findByCustomerEmail(customer.getCustomerEmail()).get();

        customerEmailVerifyService.sendCustomerMail(customer1);
        Map<String, String> result = new HashMap<>();
        result.put("customerEmail", customer.getCustomerEmail());

        PostCustomerSignupRes postCustomerSignupRes = PostCustomerSignupRes.builder()
                .isSuccess(true)
                .code(1000L)
                .message("회원가입 성공.")
                .result(result)
                .build();

        return postCustomerSignupRes;


    }
    public PostCustomerLoginRes customerLogin(PostCustomerLoginReq postCustomerLoginReq) throws BaseException{
        Optional<Customer> member = customerRepository.findByCustomerEmail(postCustomerLoginReq.getCustomerEmail());

        if (member.isPresent()) {
            if (passwordEncoder.matches(postCustomerLoginReq.getCustomerPwd(), member.get().getCustomerPwd())) {

                PostCustomerLoginRes postCustomerLoginRes = PostCustomerLoginRes.builder()
                        .accessToken(TokenProvider.generateAccessToken(member.get(), expiredTimeMs))
                        .idx(member.get().getIdx())
                        .build();

                loginLogService.loginLogging(member.get());
                String now = LocalDateTime.now().toString();
                System.out.println(now);
                System.out.println(now.split("T")[0]);

                member.get().setLastLogin(now.split("T")[0]);

                customerRepository.save(member.get());
                return postCustomerLoginRes;
            } else {
                throw new BaseException(CUSTOMER_LOGIN_INCORRECT_ACCOUNT);
            }
        }
        throw new BaseException(CUSTOMER_LOGIN_INCORRECT_ACCOUNT);
    }

    public DeleteCustomerDeleteRes delete(String token) throws BaseException{
        token = TokenProvider.replaceToken(token);

        Optional<Customer> result = customerRepository.findByCustomerEmail(TokenProvider.getUsername(token));

        if (result.isPresent()){
            DeleteCustomerDeleteRes deleteCustomerDeleteRes = DeleteCustomerDeleteRes.builder()
                    .customerEmail(result.get().getCustomerEmail())
                    .build();

            customerRepository.delete(Customer.builder()
                    .idx(TokenProvider.getIdx(token)).build());

            return deleteCustomerDeleteRes;
        }
        throw new BaseException(CUSTOMER_DELETE_FAIL);
    }

    public GetCustomerReadRes read(Long idx) throws BaseException{
        Optional<Customer> result = customerRepository.findById(idx);

        if (result.isPresent()){
            Customer customer = result.get();
            List<GetHaveCouponBaseRes> getHaveCouponBaseResList = new ArrayList<>();

            for (HaveCoupon haveCoupon: customer.getHaveCouponList()) {
                getHaveCouponBaseResList.add(GetHaveCouponBaseRes.builder()
                        .idx(haveCoupon.getIdx())
                        .count(haveCoupon.getCount())
                        .build());
            }
            return GetCustomerReadRes.builder()
                    .idx(customer.getIdx())
                    .name(customer.getName())
                    .customerEmail(customer.getCustomerEmail())
                    .authority(customer.getAuthority())
                    .getHaveCouponBaseResList(getHaveCouponBaseResList)
                    .totalAmount(customer.getTotalAmount())
                    .level(customer.getLevel())
                    .age(customer.getAge())
                    .address(customer.getAddress())
                    .gender(customer.getGender())
                    .lastLogin(customer.getLastLogin())
                    .build();
        }
        throw new BaseException(CUSTOMER_READ_EMPTY);
    }
    public List<GetCustomerListRes> list()throws BaseException{
        List<Customer> result = customerRepository.findAll();

        List<GetCustomerListRes> getCustomerListResList = new ArrayList<>();
        if (result.isEmpty()){
            throw new BaseException(CUSTOMER_LIST_EMPTY);
        }
        for (Customer customer: result) {
            List<GetHaveCouponBaseRes> getHaveCouponBaseResList = new ArrayList<>();


            for (HaveCoupon haveCoupon: customer.getHaveCouponList()) {
                GetHaveCouponBaseRes getHaveCouponBaseRes = GetHaveCouponBaseRes.builder()
                        .idx(haveCoupon.getIdx())
                        .count(haveCoupon.getCount())
                        .build();

                getHaveCouponBaseResList.add(getHaveCouponBaseRes);
            }

            GetCustomerListRes getCustomerListRes = GetCustomerListRes.builder()
                    .idx(customer.getIdx())
                    .name(customer.getName())
                    .customerEmail(customer.getCustomerEmail())
                    .authority(customer.getAuthority())
                    .getHaveCouponBaseResList(getHaveCouponBaseResList)
                    .totalAmount(customer.getTotalAmount())
                    .level(customer.getLevel())
                    .lastLogin(customer.getLastLogin())
                    .build();

            getCustomerListResList.add(getCustomerListRes);

        }
        return getCustomerListResList;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Optional<Customer> result = customerRepository.findByCustomerEmail(username);
        Customer customer = null;
        if(result.isEmpty()) {
            throw new UsernameNotFoundException("회원이 존재하지 않습니다.");
        }
        customer = result.get();
        return customer;
    }



}
