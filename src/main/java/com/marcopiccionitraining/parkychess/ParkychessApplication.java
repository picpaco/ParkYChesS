package com.marcopiccionitraining.parkychess;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.application.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class ParkychessApplication extends Application{
    private ConfigurableApplicationContext context;
    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                appContext -> {
                        appContext.registerBean(Application.class, () -> ParkychessApplication.this);
                        appContext.registerBean(Parameters.class, this::getParameters);
                        appContext.registerBean(HostServices.class, this::getHostServices);
                };
        this.context = new SpringApplicationBuilder()
                .sources(BootifulFxApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
        }
    @Override
    public void start(Stage primaryStage) throws ExceptionInInitializerError {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        this.context.close();
        Platform.exit();
    }
    public static class StageReadyEvent extends ApplicationEvent{
        public StageReadyEvent (Stage source){
            super(source);
        }
        public Stage getStage(){
            return (Stage) getSource();
        }
    }
}
