package com.example.jpaboard.service;

import com.example.jpaboard.dto.BoardResponseDto;
import com.example.jpaboard.dto.BoardWithAgeResponseDto;
import com.example.jpaboard.entity.Board;
import com.example.jpaboard.entity.Member;
import com.example.jpaboard.repository.BoardRepository;
import com.example.jpaboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public BoardResponseDto save(String title, String contents, String username) {

        Member findMember = memberRepository.findMemberByUsernameOrElseThrow(username);

        Board board = new Board(title, contents);
        board.setMember(findMember);

        boardRepository.save(board);

        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents());
    }

    public List<BoardResponseDto> findAll() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public BoardWithAgeResponseDto findById(Long id) {
        Board findBoard = boardRepository.findByIdOrElseThrow(id);
        Member writer = findBoard.getMember();

        return new BoardWithAgeResponseDto(findBoard.getTitle(), findBoard.getContents(), writer.getAge());
    }

    public void delete(Long id) {

        Board findBoard = boardRepository.findByIdOrElseThrow(id);

        boardRepository.delete(findBoard);
    }

}
