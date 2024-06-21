module mas.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;




    opens mas.lms.app to javafx.graphics;
    opens mas.lms.controller to javafx.fxml;
    opens mas.lms.model to javafx.base, org.hibernate.orm.core;

    exports mas.lms.app;
    exports mas.lms.controller;
    exports mas.lms.model;
}