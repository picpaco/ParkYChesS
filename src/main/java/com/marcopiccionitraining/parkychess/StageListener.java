package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.controller.GUIController;
import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.moves.PawnPromotionMove;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import static com.marcopiccionitraining.parkychess.model.FENPositions.FEN_INITIAL_POSITION;

@Component
@Slf4j
public class StageListener implements ApplicationListener<ParkychessApplication.StageReadyEvent> {

    private String applicationTitle;
    private Resource fxmlUI;
    private ApplicationContext applicationContext;
    private static final double MAX_STAGE_WIDTH = 685.0;
    private static final double MAX_STAGE_HEIGHT = 718.0;
    private static final int CHESSBOARD_NUMBER_OF_ROWS = 8;
    private static final int CHESSBOARD_NUMBER_OF_COLUMNS = 8;
    private static final double CHESSBORD_SIDE_SIZE = 640.0;
    private static final double MENU_BAR_HEIGHT = 28.0;
    private static final Color HIGHLIGHT_COLOR = Color.rgb(255, 255, 125, 0.5);
    private static final double DIM_SQUARE_SIDE = CHESSBORD_SIDE_SIZE / 8;
    private static final double PROMOTION_PIECE_SIZE = 75.0;
    private final HashMap<Position, Move> movesCache = new HashMap<>();
    private final Rectangle[][] highlights = new Rectangle[64][64];
    private Board chessboard;
    private ObjectFactory objectFactory;
    private ChessGame gameState;
    private Position selectedPos = null;

    public StageListener (@Value("${spring.application.ui.title}") String applicationTitle,
                       //   @Value("${spring.application.ui.main.window.width}") String applicationWindowWidth,
                       //   @Value("${spring.application.ui.main.window.height}") String applicationWindowHeight,
                          @Value("${spring.application.exec.testsonly}") Boolean executeTestsOnly,
                          @Value("classpath:ui.fxml") Resource fxmlUIResource,
                          ApplicationContext applicationContext){
        if (executeTestsOnly) {
            log.info("Executing StageListener (Tests only)...");
        } else {
     //       log.trace("Executing StageListener (GUI app)...");
            this.applicationTitle = applicationTitle;
            this.fxmlUI = fxmlUIResource;
            this.applicationContext = applicationContext;
            objectFactory = applicationContext.getBean(ObjectFactory.class);
            chessboard = new Board(PlayerColor.WHITE);
            gameState = new ChessGame(FEN_INITIAL_POSITION);
        }
    }

    @Override
    public void onApplicationEvent(ParkychessApplication.StageReadyEvent stageReadyEvent) {
        try {
       //     log.trace("Entering onApplicationEvent.");
            Stage stage = stageReadyEvent.getStage();
            URL url = fxmlUI.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            GUIController controller = (GUIController) applicationContext.getBean("controller",
                    "com.marcopiccioni.parkychess.controller.GUIController");
            BorderPane root = fxmlLoader.load();
         //   log.debug("List of centerpane children: {}", controller.centerPane.getChildren().stream().toList());
            controller.centerPane.setPrefSize (CHESSBORD_SIDE_SIZE * CHESSBOARD_NUMBER_OF_ROWS,
                    CHESSBORD_SIDE_SIZE * CHESSBOARD_NUMBER_OF_COLUMNS);
            controller.centerPane.setMaxSize(controller.centerPane.getPrefWidth(), controller.centerPane.getPrefHeight());
            controller.centerPane.prefWidthProperty().bind(Bindings.min(controller.centerPane.widthProperty(), controller.centerPane.heightProperty()));
            controller.centerPane.prefHeightProperty().bind(Bindings.min(controller.centerPane.widthProperty(), controller.centerPane.heightProperty()));
            initializeBoard(controller);
            gameState.setPositionFromFEN(FEN_INITIAL_POSITION);
            displayChessboard(gameState.getChessboard(), controller);
            controller.piecesGrid.prefWidthProperty().bind(Bindings.min(controller.centerPane.widthProperty(), controller.centerPane.heightProperty()));
            controller.piecesGrid.prefHeightProperty().bind(Bindings.min(controller.centerPane.widthProperty(), controller.centerPane.heightProperty()));
            controller.chessboardGrid.prefWidthProperty().bind(Bindings.min(controller.centerPane.widthProperty(), controller.centerPane.heightProperty()));
            controller.chessboardGrid.prefHeightProperty().bind(Bindings.min(controller.centerPane.widthProperty(), controller.centerPane.heightProperty()));
            controller.piecesGrid.setOnMouseClicked ((mouseEvent -> {
           //     log.trace ("mouse button clicked");
                Point2D point = new Point2D (mouseEvent.getX(), mouseEvent.getY());
            //    log.debug("mouseEvent.getX(): {}", mouseEvent.getX());
            //    log.debug("mouseEvent.getY(): {}", mouseEvent.getY());
                Position pos = getPositionFromPoint(point, controller);
                if (selectedPos == null) {
                    controller.piecesGrid.setCursor(Cursor.CLOSED_HAND);
                    onFromPositionSelected (pos);
                } else {
                    controller.piecesGrid.setCursor(Cursor.OPEN_HAND);
                    OnToPositionSelected (pos, controller);
                }
            }));
            Scene scene = new Scene(root, MAX_STAGE_WIDTH, MAX_STAGE_HEIGHT);
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.setMaxHeight(MAX_STAGE_HEIGHT);
            stage.setMaxWidth(MAX_STAGE_WIDTH);
            stage.minWidthProperty().bind(scene.heightProperty());
            stage.minHeightProperty().bind(scene.widthProperty());
            stage.setTitle(applicationTitle);
            stage.show();
       //     log.debug("Scene width: {}", scene.getWidth());
       //     log.debug("Scene height: {}", scene.getHeight());
       //     log.debug("Stage width: {}", stage.getWidth());
       //     log.debug("Stage height: {}", stage.getHeight());
       //     log.trace("Exiting onApplicationEvent.");
        } catch (IOException e) {
        //    log.debug("An IOException was thrown while building the GUI. Message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Position getPositionFromPoint(Point2D point, GUIController guiController) {
       // log.trace("In getPositionFromPoint: {}", point);
        double squareSize = CHESSBORD_SIDE_SIZE / 8;
      //  log.debug("piecesGrid.getWidth() = {}", guiController.piecesGrid.getWidth());
     //   log.debug("piecesGrid.getHeight() = {}", guiController.piecesGrid.getHeight());
     //   log.debug("Expected squareSize in pixels: {}", squareSize);
        int row = (int)((point.getY()) / squareSize);
        int col = (int)((point.getX()) / squareSize);
     //   log.trace("In getPositionFromPoint(point); computed position: ({}, {})", row, col);
        return new Position(row, col);
    }

    private void onFromPositionSelected(Position pos) {
        Collection<Move> legalMoves = gameState.getLegalMovesForPieceAtPosition(pos);
        log.debug("getLegalMovesForPieceAtPosition(pos): {}", legalMoves);
        if (!legalMoves.isEmpty()) {
            selectedPos = pos;
            cacheMoves(legalMoves);
            showHighlights();
        }
    }

    private void cacheMoves(Collection<Move> moves) {
        movesCache.clear();
        for (Move move : moves){
            movesCache.put(move.getTo(), move);
        }
    }

    private void OnToPositionSelected(Position pos, GUIController guiController) {
     //   log.trace("Entering OnToPositionSelected (pos={}", pos);
        selectedPos = null;
        removeHighlights();
        Move cachedMove = movesCache.get(pos);
        if (cachedMove != null) {
            chessboard.switchCurrentPlayerColor();
            if (cachedMove.getName().equals(MoveNames.PAWN_PROMOTION)){
                handlePromotion(cachedMove.getFrom(), cachedMove.getTo(), guiController);
            } else {
                handleMove(cachedMove, guiController);
            }
        }
    }

    private void handleMove (Move aMove, GUIController guiController) {
     //   log.trace ("In handleMove({}", aMove);
        Collection<Move> generatedMoves = gameState.getLegalMovesForColor(chessboard.getCurrentPlayerColor());
        gameState.makeMove (aMove, generatedMoves);
        displayChessboard (gameState.getChessboard(), guiController);
        gameState.checkGameEnd(gameState.getLegalMovesForColor(chessboard.getCurrentPlayerColor()));
        if (gameState.isGameOver()) {
            log.debug("Game over!");
            showGameOver(guiController);
        }
       // setCursor(gameState.getCurrentPlayerColor());
        if (chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK)){
            guiController.moveToBlack.setVisible(true);
            guiController.moveToWhite.setVisible(false);
        } else {
            guiController.moveToBlack.setVisible(false);
            guiController.moveToWhite.setVisible(true);
        }
    }

    private void showGameOver(GUIController guiController) {
        Alert gameOverAlert = new Alert(Alert.AlertType.NONE);
        gameOverAlert.setTitle("Game over");
        gameOverAlert.getDialogPane().setContent(
                new Label(gameState.getGameResult().gameEndReason().toString().toLowerCase() + "! "));
        ButtonType okType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType newGameType = new ButtonType("New Game", ButtonBar.ButtonData.CANCEL_CLOSE);
        gameOverAlert.getButtonTypes().setAll (okType, newGameType);
        Optional<ButtonType> buttonPressed = gameOverAlert.showAndWait();
      //  log.debug("button pressed: {}", buttonPressed);
        if (buttonPressed.isPresent() && buttonPressed.get() == okType){
            System.exit(0);
        } else {
            if (buttonPressed.isPresent() && buttonPressed.get() == newGameType) {
                restartGame(guiController);
            }
        }
    }

    private void restartGame(GUIController guiController){
        selectedPos = null;
        removeHighlights();
        movesCache.clear();
        gameState = new ChessGame (FEN_INITIAL_POSITION);
        if (chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK)){
            guiController.moveToBlack.setVisible(true);
            guiController.moveToWhite.setVisible(false);
        } else {
            guiController.moveToBlack.setVisible(false);
            guiController.moveToWhite.setVisible(true);
        }
        displayChessboard(chessboard, guiController);
   //     setCursor(gameState.getCurrentPlayerColor());
    }
    private void handlePromotion(Position from, Position to, GUIController guiController) {
        Dialog<String> promotionAlert = new Dialog<>();
        promotionAlert.setTitle("Click on the piece you wish to promote to");
        Window window = promotionAlert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());
        GridPane promotionPiecesImagesGrid = new GridPane();
        ImageView queenImageView = new ImageView (objectFactory.getPieceImage(chessboard.getCurrentPlayerColor(), PieceNames.QUEEN));
        queenImageView.setPreserveRatio(true);
        queenImageView.setFitWidth(PROMOTION_PIECE_SIZE);
        queenImageView.setFitHeight(PROMOTION_PIECE_SIZE);
        Button queenButton = new Button("", queenImageView);
        promotionPiecesImagesGrid.add(queenButton, 1, 1);
        queenButton.setOnAction(e -> {
            Move promotionMove = new PawnPromotionMove(from, to, new Queen(chessboard.getCurrentPlayerColor()));
            handleMove(promotionMove, guiController);
            window.hide();
        });
        ImageView rookImageView = new ImageView (objectFactory.getPieceImage(chessboard.getCurrentPlayerColor(), PieceNames.ROOK));
        rookImageView.setPreserveRatio(true);
        rookImageView.setFitWidth(PROMOTION_PIECE_SIZE);
        rookImageView.setFitHeight(PROMOTION_PIECE_SIZE);
        Button rookButton = new Button("", rookImageView);
        promotionPiecesImagesGrid.add(rookButton, 2, 1);
        rookButton.setOnAction(e -> {
            Move promotionMove = new PawnPromotionMove(from, to, new Rook(chessboard.getCurrentPlayerColor()));
            handleMove(promotionMove, guiController);
            window.hide();
        });
        ImageView knightImageView = new ImageView (objectFactory.getPieceImage(chessboard.getCurrentPlayerColor(), PieceNames.KNIGHT));
        knightImageView.setPreserveRatio(true);
        knightImageView.setFitWidth(PROMOTION_PIECE_SIZE);
        knightImageView.setFitHeight(PROMOTION_PIECE_SIZE);
        Button knightButton = new Button("", knightImageView);
        promotionPiecesImagesGrid.add(knightButton, 3,1);
        knightButton.setOnAction(e -> {
            Move promotionMove = new PawnPromotionMove(from, to, new Knight(chessboard.getCurrentPlayerColor()));
            handleMove(promotionMove, guiController);
            window.hide();
        });
        ImageView bishopImageView = new ImageView (objectFactory.getPieceImage(chessboard.getCurrentPlayerColor(), PieceNames.BISHOP));
        bishopImageView.setPreserveRatio(true);
        bishopImageView.setFitWidth(PROMOTION_PIECE_SIZE);
        bishopImageView.setFitHeight(PROMOTION_PIECE_SIZE);
        Button bishopButton = new Button("", bishopImageView);
        promotionPiecesImagesGrid.add(bishopButton, 4, 1);
        bishopButton.setOnAction(e -> {
            Move promotionMove = new PawnPromotionMove(from, to, new Bishop(chessboard.getCurrentPlayerColor()));
            handleMove(promotionMove, guiController);
            window.hide();
        });
        promotionAlert.getDialogPane().setContent(promotionPiecesImagesGrid);
        promotionAlert.showAndWait();
    }

    private void showHighlights() {
     //   log.trace("In showHighlights");
        for (Position to : movesCache.keySet()) {
      //      log.debug("About to highlight square ({}, {}) from movecache: {}", to.row(), to.column(), movesCache);
            highlights[to.row()][to.column()].setFill(HIGHLIGHT_COLOR);
     //       log.debug("Just highlighted square {} from movecache: {}", to, movesCache);
        }
    }

    private void removeHighlights() {
             for (Position to : movesCache.keySet()) {
                 highlights[to.row()][to.column()].setFill(Color.TRANSPARENT);
        }
    }

    private void initializeBoard(GUIController guiController){
     //   log.trace("In initializeBoard()");
        for (int row = 0; row < CHESSBOARD_NUMBER_OF_ROWS; row++){
            for (int column = 0; column < CHESSBOARD_NUMBER_OF_COLUMNS; column++) {
                //First lay down an image of a square of the appropriate color
                ImageView squareImageView;
                if (((row + column) % 2) == 0) {
                    squareImageView = new ImageView(objectFactory.getSquareImage(PlayerColor.WHITE));
                } else {
                    squareImageView = new ImageView(objectFactory.getSquareImage(PlayerColor.BLACK));
                }
                squareImageView.setSmooth(true);
                squareImageView.setCache(true);
                squareImageView.setFitHeight(DIM_SQUARE_SIDE);
                squareImageView.setFitWidth(DIM_SQUARE_SIDE);
               // chessboardSquaresImages[row][column] = squareImageView;
                guiController.chessboardGrid.add(squareImageView, column, row);
                // Then initialize the highlightsgrid with transparent rectangles
                Rectangle transparentSquare = new Rectangle(DIM_SQUARE_SIDE,
                        DIM_SQUARE_SIDE, Color.TRANSPARENT);
                highlights[row][column] = transparentSquare;
                guiController.highlightsGrid.add(highlights[row][column], column, row);
       //         log.debug("After adding highlight at {},{} number of cells in highlightsgrid = {}",
                   //     row, column, guiController.highlightsGrid.getChildren().size());
                //Then initialize the pieces layer square with an empty image.
                ImageView imageView = new ImageView (objectFactory.getPieceImage(PlayerColor.WHITE, PieceNames.NONE));
                imageView.setSmooth(true);
                imageView.setCache(true);
                imageView.setFitHeight(DIM_SQUARE_SIDE);
                imageView.setFitWidth(DIM_SQUARE_SIDE);
                guiController.piecesGrid.add(imageView, column, row);
        //        log.debug("Number of piecesgrid cells after adding an empty square image: {}", guiController.piecesGrid.getChildren().size());
            }
            guiController.piecesGrid.setCursor(Cursor.OPEN_HAND);
            if (chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK)){
                guiController.moveToBlack.setVisible(true);
                guiController.moveToWhite.setVisible(false);
            } else {
                guiController.moveToBlack.setVisible(false);
                guiController.moveToWhite.setVisible(true);
            }
        }
  //      log.trace("Exiting initializeBoard()");
    }

    private void displayChessboard(Board chessBoard, GUIController guiController) {
 //       log.trace("In displayChessboard()");
        guiController.piecesGrid.getChildren().clear();
        for (int row = 0; row < CHESSBOARD_NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < CHESSBOARD_NUMBER_OF_COLUMNS; column++) {
                Piece piece = chessBoard.getPiece(row, column);
          //      log.debug("piece ({},{}): {}", row, column, piece);
                ImageView imageToDisplay = new ImageView(objectFactory.getPieceImage(piece));
                imageToDisplay.setPreserveRatio(true);
                imageToDisplay.setSmooth(true);
                imageToDisplay.setCache(true);
                imageToDisplay.setFitHeight (DIM_SQUARE_SIDE);
                imageToDisplay.setFitWidth (DIM_SQUARE_SIDE);
                guiController.piecesGrid.add(imageToDisplay, column, row);
          //      log.debug("Number of piecesgrid cells after adding: {}", guiController.piecesGrid.getChildren().size());
            }
        }
    }
}