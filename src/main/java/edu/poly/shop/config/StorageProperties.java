package edu.poly.shop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
@Data
public class StorageProperties {
    private String location;    //define nơi mà lưu file/image. hiện tại location define ở application.properties.
}
