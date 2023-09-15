package irby.jaden.namepending;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class NamePendingApplication {

	public static void main(String[] args) throws IOException {
		ClassLoader classLoader = NamePendingApplication.class.getClassLoader();

		File file = new File(Objects.requireNonNull(classLoader.getResource("ServiceAccountKey.json")).getFile());
		FileInputStream serviceAccount =
				new FileInputStream(file.getAbsolutePath());
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();


		FirebaseApp.initializeApp(options);

		SpringApplication.run(NamePendingApplication.class, args);
	}


}
