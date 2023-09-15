package irby.jaden.namepending.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NonNull
    private String email;

    @NonNull
    private String username;

    @NonNull
    private String password;

    //might need to store in different format
    @NonNull
    private String profilePicture;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private List<UUID> friends = new ArrayList<>();
    private List<UUID> sentFriendRequests = new ArrayList<>();
    private List<UUID> receivedFriendRequests = new ArrayList<>();

    private List<Chat> chats = new ArrayList<>();

}
