package kdkd.youre.backend.domain.url.presentation.dto.response;

import lombok.*;

@Getter
@Builder
public class UrlAddressCheckResponse {

    private boolean isDuplicated;
}
