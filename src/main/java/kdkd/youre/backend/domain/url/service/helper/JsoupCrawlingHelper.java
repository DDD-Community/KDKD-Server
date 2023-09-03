package kdkd.youre.backend.domain.url.service.helper;

import kdkd.youre.backend.domain.url.presentation.dto.UrlCrawlingDto;
import kdkd.youre.backend.global.exception.CustomException;
import kdkd.youre.backend.global.exception.ErrorCode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JsoupCrawlingHelper {

    public UrlCrawlingDto getUrlCrawlingInfo(String address) {

        try {
            Document document = Jsoup.connect(address).get();

            Element imageMeta = document.select("meta[property=og:image]").first();
            Element titleMeta = document.select("meta[property=og:title]").first();
            Element urlMeta = document.select("meta[property=og:url]").first();

            String image = extractMetaContent(imageMeta);
            String title = extractMetaContent(titleMeta);
            String url = extractMetaContent(urlMeta);

            return UrlCrawlingDto.from(url, title, image);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractMetaContent(Element metaElement) {
        return Optional.ofNullable(metaElement)
                .map(meta -> meta.attr("content"))
                .orElse(null);
    }
}
