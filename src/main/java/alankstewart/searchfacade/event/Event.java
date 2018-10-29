package alankstewart.searchfacade.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "events")
public final class Event {

    @Id
    @Field("_id")
    @JsonProperty("_id")
    private final String id;

    private final String type;
    private final long time;
    private final String user;
    private final String ip;

    @JsonCreator
    public Event(@JsonProperty("_id") String id,
                 @JsonProperty("type") String type,
                 @JsonProperty("time") long time,
                 @JsonProperty("user") String user,
                 @JsonProperty("ip") String ip) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.user = user;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }
}
