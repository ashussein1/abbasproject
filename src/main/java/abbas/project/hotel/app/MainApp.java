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

    @Override
    public void init() {
        injector = Guice.createInjector(new AppConfig());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/abbas/project/hotel/view/launcher-view.fxml")
        );
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        URL cssUrl = getClass().getResource("/abbas/project/hotel/style/dark-theme.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        primaryStage.setTitle("Hotel Reservation System - Launcher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
