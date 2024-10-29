package xeat.blogservice.search.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "user")
@Mapping(mappingPath = "elastic/user-mapping.json")
@Setting(settingPath = "elastic/user-setting.json")
@Getter
public class ElasticUser {
    @Id
    private String id;
    @Field(name="nickname")
    private String nickname;
    @Field(name="profile_url")
    private String profileUrl;
    @Field(name="profile_message")
    private String profileMessage;
}
