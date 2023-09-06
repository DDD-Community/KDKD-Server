package kdkd.youre.backend.domain.tag.service;

import kdkd.youre.backend.domain.category.presentation.dto.response.CategoryTreeResponse;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.tag.domain.Tag;
import kdkd.youre.backend.domain.tag.domain.repository.TagRepository;
import kdkd.youre.backend.domain.tag.presentation.dto.response.TagFindAllResponse;
import kdkd.youre.backend.domain.url.domain.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public void saveAllTag(List<String> tagNames, Url url, Member member) {

        if (tagNames == null) {
            return;
        }

        List<Tag> tags = tagNames.stream()
                .map(name -> Tag.builder()
                        .name(name)
                        .url(url)
                        .member(member)
                        .build())
                .collect(Collectors.toList());

        tagRepository.saveAll(tags);
    }

    public void deleteAllTag(Url url) {

        List<Tag> tags = tagRepository.findByUrl(url);
        tagRepository.deleteAll(tags);
    }


    public List<TagFindAllResponse> findAllTag(Member member) {
        List<String> tags = tagRepository.findDistinctNameByMember(member);

        return tags.stream()
                .map(TagFindAllResponse::from)
                .collect(Collectors.toList());
    }
}
