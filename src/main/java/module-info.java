module parkychess {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires java.desktop;
    requires static lombok;
    requires jdk.jshell;
    requires annotations;
    opens com.marcopiccionitraining.parkychess to spring.core, javafx.fxml;
    opens com.marcopiccionitraining.parkychess.controller to spring.core, javafx.fxml;
    exports com.marcopiccionitraining.parkychess to javafx.fxml, spring.core, spring.beans, spring.context, javafx.graphics;
    exports com.marcopiccionitraining.parkychess.model to spring.beans;
    exports com.marcopiccionitraining.parkychess.model.moves to spring.beans;
    exports com.marcopiccionitraining.parkychess.model.pieces to spring.beans;
    exports com.marcopiccionitraining.parkychess.controller to spring.core, spring.beans, javafx.fxml;
}