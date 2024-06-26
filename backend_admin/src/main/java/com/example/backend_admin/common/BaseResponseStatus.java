package com.example.backend_admin.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public enum BaseResponseStatus {

    INTERNAL_SERVER_ERROR(false,500, "알 수 없는 문제가 발생하였습니다."),
    NOT_FOUND(false,404, "페이지를 찾을 수 없습니다."),
    UNAUTHORIZED(false,401, "로그인을 해주세요."),
    FORBIDDEN(false, 403, "해당 요청을 수행할 권한이 없습니다."),
    BAD_REQUEST(false, 400, "잘못된 요청입니다."),
    NOT_SUPPORT_METHOD(false,405,"해당 요청을 지원하지 않습니다."),
    UNEXPECTED_ERROR(false,999, "알 수 없는 에러가 발생하였습니다."),

    NOT_AVAILABLE_EMAIL(false,1501,"이메일을 올바르게 입력해주세요."),
    NOT_AVALIABLE_PASSWORD(false,1502,"비밀번호를 올바르게 입력해주세요."),
    CAN_NOT_FIND_TARGETLIST(false,1503,"고객 리스트를 찾을 수 없습니다."),
    /*
     * 1000 : 요청 성공
     */

    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /*
     * 2000 : Customer
     */

    CUSTOMER_SIGNUP_EMPTY_EMAIL(false,2001, "이메일을 입력해주세요"),
    CUSTOMER_SIGNUP_EMPTY_PASSWORD(false, 2002, "비밀번호를 입력해주세요."),
    CUSTOMER_CONFIRM_INCOMPLETE_AUTHENTICATION_EMAIL(false,2003, "이메일 인증을 해주세요."),
    CUSTOMER_LOGIN_EMPTY_EMAIL(false, 2004, "이메일을 입력해주세요"),
    CUSTOMER_LOGIN_EMPTY_PASSWORD(false, 2005, "비밀번호를 입력해주세요."),
    CUSTOMER_LOGIN_INCORRECT_ACCOUNT(false, 2006, "이메일 혹은 비밀번호가 잘못되었거나 없습니다."),     // ID, PW 혼용하여 사용
    CUSTOMER_LIST_EMPTY(false, 2007, "존재하는 회원 리스트가 없습니다."),
    CUSTOMER_READ_FAIL(false, 2008, "회원 정보를 불러오지 못했습니다."),
    CUSTOMER_DELETE_FAIL(false, 2009, "회원 탈퇴에 실패하였습니다."),
    CUSTOMER_SIGNUP_DUPLICATE_EMAIL(false, 2010, "이미 존재하는 계정입니다."),
    CUSTOMER_SIGNUP_INCORRECT_EMAIL(false,2011,"올바른 형태의 이메일을 입력해주세요."),
    CUSTOMER_SIGNUP_INCORRECT_PASSWORD(false,2012,"올바른 형태의 비밀번호를 입력해주세요."),
    CUSTOMER_READ_EMPTY(false, 2013,"존재하는 회원 정보가 없습니다."),
    /*
     * 3000 : haveCoupon
     */

    HAVECOUPON_CREATE_EMPTY_COUNT(false, 3001, "개수를 입력해주세요."),
    HAVECOUPON_CREATE_EMPTY_CUSTOMER(false, 3002,"대상을 입력해주세요."),
    HAVECOUPON_CREATE_EMPTY_COUPON(false, 3003, "쿠폰을 입력해주세요."),
    HAVECOUPON_LIST_EMPTY(false, 3004, "쿠폰함에 쿠폰이 없습니다."),   //read, list 혼용
    HAVECOUPON_CREATE_EMPTY_FAIL(false,3005,"쿠폰 발급에 실패하였습니다."),
    HAVE_COUPON_LIST_EMPTY_IDX(false,3006,"읽어올 대상의 정보가 없습니다."),

    /*
     * 4000 : orders
     */

    ORDERS_VALIDATION_AUTHENTICATION_FAIL(false,4001, "로그인을 해주세요."),
    ORDERS_VALIDATION_INCORRECT_INFOMATION(false, 4002, "주문정보가 일치하지 않습니다."),
    ORDERS_VALIDATION_EMPTY_IMPUID(false,4003,"주문정보를 불러올 수 없습니다."),
    ORDERS_CREATEORDER_FAIL(false,4004,"고객정보를 불러올 수 없습니다."),
    ORDERS_VALIDATION_EMPTY_PRODUCT(false,4005,"상품이 존재하지 않습니다."),
    /*
     * 5000 : product
     */

    PRODUCT_LIST_FAIL(false, 5001, "상품 리스트를 불러올 수 없습니다."),
    PRODUCT_READ_FAIL(false, 5002, "상품 정보를 불러올 수 없습니다."),
    PRODUCT_KEYWORD_EMPTY(false,5003, "검색어를 입력해주세요."),
    PRODUCT_RECOMMEND_FAIL(),   //TODO: RECOMMEND 관련 예외
    PRODUCT_READ_EMPTY_IDX(false,5004,"회원 정보를 읽어올 수 없습니다."),

    /*
     * 6000 : qna
     */

    QNA_REGISTER_UNAUTHORIZED(false, 6001,"게시글 작성을 위해서 로그인을 해주세요."),
    QNA_REGISTER_EMPTY_TITLE(false,6001,"제목을 입력해 주십시오."),
    QNA_REGISTER_EMPTY_QNACONTENT(false,6003,"본문을 입력해 주십시오."),
    QNA_REGISTER_EMPTY_PASSWORD(false, 6004,"비밀번호를 입력해주세요."),
    QNA_REGISTER_INCORRECT_PASSWORD(false, 6005,"올바른 비밀번호를 입력해주세요."),
    QNA_REGISTER_NOT_EXIST_QNA(false,6006,"존재하지 않는 게시글 입니다."),

    /*
     * 7000 : ADMIN
     */

    ADMIN_SIGNUP_EMPTY_EMAIL(false,7001, "이메일을 입력해주세요"),
    ADMIN_SIGNUP_EMPTY_PASSWORD(false, 7002, "비밀번호를 입력해주세요."),
    ADMIN_LOGIN_EMPTY_EMAIL(false, 7003, "이메일을 입력해주세요"),
    ADMIN_LOGIN_EMPTY_PASSWORD(false, 7004, "비밀번호를 입력해주세요."),
    ADMIN_LOGIN_INCORRECT_ACCOUNT(false, 7005, "이메일 혹은 비밀번호가 잘못되었거나 없습니다."),     // ID, PW 혼용하여 사용+
    ADMIN_SIGNUP_DUPLICATE_EMAIL(false, 7006, "이미 존재하는 계정입니다."),
    ADMIN_SIGNUP_INCORRECT_EMAIL(false,7007,"올바른 형태의 이메일을 입력해주세요."),
    ADMIN_SIGNUP_INCORRECT_PASSWORD(false,7008,"올바른 형태의 비밀번호를 입력해주세요."),

    /*
     * 8000 : ANSWER
     */

    ADMIN_ANSWER_EMPTY_TOKEN(false,8001, "로그인 해주세요."),
    ADMIN_ANSWER_EMPTY_IDX(false, 8002, "게시글을 찾지 못했습니다."),
    ADMIN_ANSWER_EMPTY_ANSWERCONTENT(false, 8003, "답변이 비어있습니다."),
    ADMIN_ANSWER_NOT_FOUND(false,8004,"존재하지 않는 게시물입니다."),
    ADMIN_ANSWER_ISPRESENT(false,8005,"이미 답변이 존재하는 게시물입니다."),

    /*
     * 9000 : rating
     */

    /*
     * 10000 : coupon
     */

    COUPON_CREATE_EMPTY_CATEGORY(false,10001,"카테고리를 입력해주세요"),
    COUPON_CREATE_EMPTY_DISCOUNT(false,10001,"할인을 입력해주세요"),
    COUPON_CREATE_EMPTY_ADMINIDX(false,10001,"로그인을 해주세요"),
    ;
    /*
     * 11000 :
     */

    /*
     * 8000 : ANSWER
     */
    private boolean isSuccess;
    private int code;
    private String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
