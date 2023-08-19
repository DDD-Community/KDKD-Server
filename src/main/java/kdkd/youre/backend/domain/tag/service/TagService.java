package kdkd.youre.backend.domain.tag.service;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.tag.domain.Tag;
import kdkd.youre.backend.domain.tag.domain.repository.TagRepository;
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

    public void saveTagList(List<String> tagNameList, Url url, Member member) {

        if (tagNameList == null) {
            return;
        }

        List<Tag> tagList = tagNameList.stream()
                .map(name -> Tag.builder()
                        .name(name)
                        .url(url)
                        .member(member)
                        .build())
                .collect(Collectors.toList());

        tagRepository.saveAll(tagList);
    }

    public void updateTagList(List<String> tagNames, Url url, Member member) {

        // 현재 태그 목록 조회
        List<Tag> currentTags = tagRepository.findByUrl(url);

        // 들어온 태그 이름 리스트가 빈 배열인 경우 저장된 태그를 모두 삭제하고 early return
        if(tagNames.isEmpty()) {
            tagRepository.deleteAll(currentTags);
            return;
        }

        // 현재 태그 이름 집합 (A)
        Set<String> currentTagNames = Optional.ofNullable(currentTags)
                .map(tags -> tags.stream()
                        .map(Tag::getName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        // 들어온 태그 이름 집합 (B)
        Set<String> incomingTagNames = new HashSet<>(tagNames);

        // 존재하는 모든 태그 이름 집합 -> A ∩ B
        Set<String> existingTagNames = new HashSet<>(currentTagNames);
        existingTagNames.retainAll(incomingTagNames);

        // 삭제되는 태그 이름 집합 -> A - B -> A - (A ∩ B)
        Set<String> tagNamesToDelete = new HashSet<>(currentTagNames);
        tagNamesToDelete.removeAll(existingTagNames);

        // 추가되는 태그 이름 집합 -> B - A -> B - (A ∩ B)
        Set<String> tagNamesToAdd = new HashSet<>(incomingTagNames);
        tagNamesToAdd.removeAll(existingTagNames);

        // 삭제
        tagRepository.deleteAll(
                currentTags.stream()
                        .filter(tag -> tagNamesToDelete.contains(tag.getName()))
                        .collect(Collectors.toList())
        );

        // 추가
        saveTagList((List<String>) tagNamesToAdd, url, member);
    }
}
