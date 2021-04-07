package uwl.senate.coc.entities;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Data
@Component
@ConfigurationProperties(prefix = "github")
public class Oauth2Properties {
    private String clientId = "f0a1dad3443935a6a7b4";
    private String clientSecret = "01281acf3de7831001d15c077f9721bb13fda8a0";
    private String authorizeUrl = "https://github.com/login/oauth/authorize";
    private String redirectUrl = "http://localhost:8080/oauth2/callback";
    private String accessTokenUrl = "https://github.com/login/oauth/access_token";
    private String userInfoUrl = "https://api.github.com/user";
}
