package com.board.service;

import com.board.common.file.FileStore;
import com.board.domain.UploadFile;
import com.board.domain.Member;
import com.board.domain.Posts;
import com.board.dto.PostDto;
import com.board.repository.MemberRepository;
import com.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;
    @Transactional
    public void postSave(Long memberId, PostDto.EditForm dto) throws IOException {
        Member member = memberRepository.findById(memberId);
        List<UploadFile> uploadFiles = fileStore.storeFiles(dto.getImageFiles());
        Posts post = Posts.builder()
                .member(member)
                .title(dto.getTitle())
                .text(dto.getText())
                .uploadFiles(uploadFiles)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        postRepository.save(post);
    }

    @Transactional
    public Long postUpdate(Long postId, PostDto.EditForm dto) throws IOException {
        Posts findPost = postRepository.findById(postId);
        List<UploadFile> updatedFiles = fileStore.updateFile(findPost.getUploadFiles(), dto.getImageFiles());

        findPost.modify(dto.getTitle(), dto.getText(),updatedFiles);
        return findPost.getId();
    }

    @Transactional
    public void postDelete(Long postId) {
        Posts findPost = postRepository.findById(postId);
        List<UploadFile> uploadFiles = findPost.getUploadFiles();
        for (UploadFile uploadFile : uploadFiles) {
            fileStore.deleteFile(uploadFile);
        }
        postRepository.remove(postId);
    }

    public List<PostDto.Response> list() {
        return postRepository.findAll()
                .stream()
                .map(o-> new PostDto.Response(o))
                .collect(Collectors.toList());
    }

    public PostDto.Response findOne(Long id) {
        return new PostDto.Response(postRepository.findById(id));
    }
}
