package kdkd.youre.backend.domain.url.presentation.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlRequest {

//    {
//        "url":"www.google.com",
//            "title":"테스트",
//            "categoryId":"1",
//            "categoryName":"구글테스트"
//    }

    private String url;
    private String title;
    private Long categoryId;
    private String categoryName;


}
