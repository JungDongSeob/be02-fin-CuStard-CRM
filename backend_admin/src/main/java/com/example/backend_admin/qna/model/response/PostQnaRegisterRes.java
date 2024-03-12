package com.example.backend_admin.qna.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostQnaRegisterRes {

    private String title; //제목
    private String qnaContent; //본문

}
