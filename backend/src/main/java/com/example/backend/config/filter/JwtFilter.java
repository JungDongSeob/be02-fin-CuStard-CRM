package com.example.backend.config.filter;


import com.example.backend.common.BaseException;
import com.example.backend.common.BaseResponse;
import com.example.backend.common.BaseResponseStatus;
import com.example.backend.customer.model.entity.Customer;
import com.example.backend.utils.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter   {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, MalformedJwtException, ExpiredJwtException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);



            String token;

            if (header != null && header.startsWith("Bearer ")) {
                token = header.split(" ")[1];
            } else {
                SecurityContextHolder.getContext().setAuthentication(null);

                filterChain.doFilter(request, response);
                return;
            }

            Long idx = TokenProvider.getIdx(token);


            String username = TokenProvider.getUsername(token);
            String role = TokenProvider.getRole(token);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(role));

            // 인증 과정 수행 회원 엔티티를 받아온다.
            // member.getUsername();
            // username = email이라고 정함

            if (!TokenProvider.validate(token, username)){
                filterChain.doFilter(request, response);
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    Customer.builder().idx(idx).customerEmail(username).build(), null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인가하는 코드
            filterChain.doFilter(request, response);

    }
}
