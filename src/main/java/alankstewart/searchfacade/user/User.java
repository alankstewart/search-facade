package alankstewart.searchfacade.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public final class User {

    @Id
    @Field("_id")
    @JsonProperty("_id")
    private final String id;

    private final String user;
    private final String workstation;

    public User(@JsonProperty("_id") String id,
                @JsonProperty("user") String user,
                @JsonProperty("workstation") String workstation) {
        this.id = id;
        this.user = user;
        this.workstation = workstation;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getWorkstation() {
        return workstation;
    }
}
