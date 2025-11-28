package abbas.project.hotel.app;

import abbas.project.hotel.model.Room;
import abbas.project.hotel.model.RoomType;
import abbas.project.hotel.model.User;
import abbas.project.hotel.repository.RoomRepository;
import abbas.project.hotel.repository.UserRepository;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;

public class MainApp extends Application {

    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 1. Build DI container once for the whole app
        injector = Guice.createInjector(new AppConfig());

        RoomRepository roomRepo = injector.getInstance(RoomRepository.class);
        if (roomRepo.findAll().isEmpty()) {
            System.out.println("Seeding database with initial rooms...");
            roomRepo.save(new Room("101", RoomType.SINGLE, 100.0));
            roomRepo.save(new Room("102", RoomType.DOUBLE, 150.0));
            roomRepo.save(new Room("201", RoomType.DELUXE, 250.0));
            roomRepo.save(new Room("PH", RoomType.PENTHOUSE, 500.0));
        }

        UserRepository userRepo = injector.getInstance(UserRepository.class);

        // Check if the user table is empty. If yes, create the default admin.
        if (userRepo.isEmpty()) {
            System.out.println("Seeding default admin user...");

            // Hash the password "admin123" securely using BCrypt
            String salt = BCrypt.gensalt();
            String hash = BCrypt.hashpw("admin123", salt);

            // Create the admin user
            User admin = new User("admin", hash, "ADMIN");
            userRepo.save(admin);

            System.out.println("Admin user created: admin / admin123");
        }

        // 2. Load the Layout (View)
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/abbas/project/hotel/view/launcher-view.fxml")
        );
        // Let Guice create controllers
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        // 3. Load CSS
        URL cssUrl = getClass().getResource("/abbas/project/hotel/style/dark-theme.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("WARNING: dark-theme.css not found.");
        }

        // 4. Show the Window
        primaryStage.setTitle("Hotel Reservation System - Launcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}