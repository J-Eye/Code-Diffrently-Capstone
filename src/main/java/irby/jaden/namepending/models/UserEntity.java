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
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String email;

    @NonNull
    private String username;

    @NonNull
    private String password;

    //might need to store in different format
    @NonNull
    private String profilePictureUrl;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToMany
    private List<UserEntity> friends = new ArrayList<>();
    @ManyToMany
    private List<UserEntity> sentFriendRequests = new ArrayList<>();
    @ManyToMany
    private List<UserEntity> receivedFriendRequests = new ArrayList<>();

    @OneToMany
    private List<Chat> chats = new ArrayList<>();

    private boolean active = true;


}
