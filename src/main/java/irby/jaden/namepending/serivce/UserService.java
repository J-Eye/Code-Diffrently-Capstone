package irby.jaden.namepending.serivce;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import irby.jaden.namepending.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Firestore dbFirestore;

    @Autowired
    public UserService(){

    }

    public UserRecord RegisterUser(User user) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setDisplayName(user.getUsername())
                .setPhotoUrl(user.getProfilePicture());

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
        saveUser(user);
        return userRecord;
    }



    private void saveUser(User user){
        dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("user")
                .document(user.getEmail())
                .set(user);
    }
}
