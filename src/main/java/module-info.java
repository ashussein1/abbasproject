module abbas.project.hotel {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    // Dependency Injection (Guice)
    requires com.google.guice;

    // Database & Persistence
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires net.bytebuddy;

    // --- NEW: Security / Password Hashing ---
    requires jbcrypt;

    // Opens for Reflection (Database & DI)
    opens abbas.project.hotel.model to com.google.guice, org.hibernate.orm.core;

    // Open other packages to Guice/JavaFX
    opens abbas.project.hotel.app to javafx.graphics, com.google.guice;
    opens abbas.project.hotel.controller to javafx.fxml, com.google.guice;
    opens abbas.project.hotel.repository to com.google.guice;
    opens abbas.project.hotel.service to com.google.guice;
    opens abbas.project.hotel.events to com.google.guice;
    opens abbas.project.hotel.security to com.google.guice;
    opens abbas.project.hotel.util to com.google.guice;

    // Exported packages
    exports abbas.project.hotel.app;
    exports abbas.project.hotel.controller;
}