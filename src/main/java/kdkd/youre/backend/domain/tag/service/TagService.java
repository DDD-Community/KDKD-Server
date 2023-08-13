package kdkd.youre.backend.domain.tag.service;

import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.domain.tag.domain.Tag;
import kdkd.youre.backend.domain.tag.domain.repository.TagRepository;
import kdkd.youre.backend.domain.url.domain.Url;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public void saveTagList(List<String> tagNameList, Url url, Member member) {

        if(tagNameList == null){
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
}
