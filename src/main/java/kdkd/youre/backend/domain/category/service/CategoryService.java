package kdkd.youre.backend.domain.category.service;

import kdkd.youre.backend.domain.category.domain.Category;
import kdkd.youre.backend.domain.category.domain.repository.CategoryRepository;
import kdkd.youre.backend.domain.category.presentation.dto.request.CategorySaveRequest;
import kdkd.youre.backend.domain.common.presentation.dto.response.IdResponse;
import kdkd.youre.backend.domain.member.domain.Member;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public IdResponse saveCategory(CategorySaveRequest request, Member member) {

        //카테고리 중복체크
        Boolean isDuplicated = categoryRepository.existsByNameAndMember(request.getName(), member);

        Category parentCategory = Optional.ofNullable(request.getParentId())
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY)))
                .orElse(null);

        if (isDuplicated) {
            throw new CustomException(ErrorCode.CONFLICT_CATEGORY);
        }

        // position 부여
        // 맨 아래에 가도록 설정
         // 10000 부터 시작하고 만약에 20000 30000 있다면 그다음인 40000으로 설정되어야함

//        Long position = categoryRepository.findDistinctMaxPositionByMember(member);
//        System.out.println(position);


        Category category = Category.builder()
                .name(request.getName())
                .parent(parentCategory)
                .member(member)
                .depth(1L)
                .isBookmarked(false)
                .build();

        categoryRepository.save(category);

        return IdResponse.builder()
                .id(category.getId())
                .build();
    }

}
