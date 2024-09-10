package com.marcopiccionitraining.parkychess.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component ("controller")
public class GUIController {
    private final Logger LOGGER = LoggerFactory.getLogger(GUIController.class);
    public MenuBar menuBar;
    public Menu fileMenu;
    public MenuItem loadFromFEN;
    public MenuItem exit;
    public Menu viewMenu;
    public MenuItem flipBoard;
    public MenuItem searchMenuItem;
    public MenuItem manualMenuItem;
    public StackPane centerPane;
    public GridPane piecesGrid;
    public GridPane highlightsGrid;
    public GridPane chessboardGrid;
    public GridPane alphaLabelsGrid;
    public GridPane leftNumericLabelsGrid;
    public Label moveToBlack;
    public Label moveToWhite;
    public MenuItem aboutMenuItem;
    public Menu infoMenu;

    public GUIController (){
        LOGGER.trace("In GUIController()");
        LOGGER.trace("Exiting GUIController()");
    }

    public void handleAboutAction(ActionEvent ignoredActionEvent) {
        Alert aboutAlert = new Alert(Alert.AlertType.NONE, "Parky chess", ButtonType.OK);
        aboutAlert.setTitle("PaRkYcHeSs");
        aboutAlert.setContentText("PaRkYcHeSs, a challenge to cognitive decline\n\n by Marco Piccioni 2023-24\n\n Version 0.1");
        aboutAlert.showAndWait();
    }

    public String handleLoadFromFENAction (ActionEvent ignoredActionEvent) {
        LOGGER.trace("Entering handleLoadFromFENAction");
        while (true) {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Custom position setup");
            textInputDialog.setHeaderText("Insert a FEN string below to set up a position:");
            Optional<String> result = textInputDialog.showAndWait();
            if (result.isPresent()) {
                String input = textInputDialog.getEditor().getText();
                if (!(input.trim().isEmpty())) {
                    return input;
                } else {
                    Alert emptyInputAlert = new Alert(Alert.AlertType.WARNING);
                    emptyInputAlert.setTitle("Empty input");
                    emptyInputAlert.setContentText("Insert a FEN string");
                    emptyInputAlert.showAndWait();
                }
            }
        }
    }

    public void handleExit(ActionEvent ignoredActionEvent) {
        System.exit(0);
    }
}

