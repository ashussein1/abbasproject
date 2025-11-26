package abbas.project.hotel.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Build DI container once for the whole app
        injector = Guice.createInjector(new AppConfig());

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/abbas/project/hotel/view/launcher-view.fxml")
        );
        // Let Guice create controllers
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        URL cssUrl = getClass().getResource("/abbas/project/hotel/style/dark-theme.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("WARNING: dark-theme.css not found.");
        }

        primaryStage.setTitle("Hotel Reservation System - Launcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
