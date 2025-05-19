package com.example.jpaboard.controller;

import com.example.jpaboard.dto.MemberResponseDto;
import com.example.jpaboard.dto.SignUpRequestDto;
import com.example.jpaboard.dto.SignUpResponseDto;
import com.example.jpaboard.dto.UpdatePasswordRequestDto;
import com.example.jpaboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                memberService.signUp(
                        requestDto.getUsername(),
                        requestDto.getPassword(),
                        requestDto.getAge()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {

        MemberResponseDto memberResponseDto = memberService.findById(id);

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequestDto requestDto
    ) {

        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
