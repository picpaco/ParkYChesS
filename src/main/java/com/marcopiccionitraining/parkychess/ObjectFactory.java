package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.PieceName;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("objectFactory")
public class ObjectFactory {

    private static final HashMap<PieceName, String> blackPiecesImagePaths = new HashMap<>();
    private static final HashMap<PieceName, String> whitePiecesImagePaths = new HashMap<>();
    private static final String IMG_DIR = "";
    private static final String IMG_EXTENSION = ".png";
    private static final String BLACK_SQUARE_PATH = IMG_DIR + "blackSquare254" + IMG_EXTENSION;
    private static final String WHITE_SQUARE_PATH = IMG_DIR + "whiteSquare254" + IMG_EXTENSION;
 //   private static final Logger LOGGER = LoggerFactory.getLogger(ObjectFactory.class);

    public ObjectFactory() {
        blackPiecesImagePaths.put(PieceName.BISHOP, (IMG_DIR + "blackBishop" + IMG_EXTENSION));
        blackPiecesImagePaths.put(PieceName.KNIGHT, (IMG_DIR + "blackKnight" + IMG_EXTENSION));
        blackPiecesImagePaths.put(PieceName.ROOK, (IMG_DIR + "blackRook" + IMG_EXTENSION));
        blackPiecesImagePaths.put(PieceName.QUEEN, (IMG_DIR + "blackQueen" + IMG_EXTENSION));
        blackPiecesImagePaths.put(PieceName.KING, (IMG_DIR + "blackKing" + IMG_EXTENSION));
        blackPiecesImagePaths.put(PieceName.PAWN, (IMG_DIR + "blackPawn" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.BISHOP, (IMG_DIR + "whiteBishop" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.KNIGHT, (IMG_DIR + "whiteKnight" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.ROOK, (IMG_DIR + "whiteRook" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.QUEEN, (IMG_DIR + "whiteQueen" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.KING, (IMG_DIR + "whiteKing" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.PAWN, (IMG_DIR + "whitePawn" + IMG_EXTENSION));
        whitePiecesImagePaths.put(PieceName.NONE, (IMG_DIR + "empty" + IMG_EXTENSION));
        blackPiecesImagePaths.put(PieceName.NONE, (IMG_DIR + "empty" + IMG_EXTENSION));
    }

    public Image getSquareImage (PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.BLACK)) {
            return new Image(BLACK_SQUARE_PATH);
        } else {
            return new Image(WHITE_SQUARE_PATH);
        }
    }
    public Image getPieceImage(PlayerColor playerColor, PieceName pieceName) {
   //     LOGGER.debug("In getPieceImage(color={} piece name={}", playerColor, pieceName);
        Image resultImage;
        if (playerColor.equals(PlayerColor.BLACK)) {
            resultImage = new Image(blackPiecesImagePaths.get(pieceName));
     //       LOGGER.debug("resultImage url for black: {}", resultImage.getUrl());
        } else {
            String path = whitePiecesImagePaths.get(pieceName);
       //     LOGGER.debug("path: {}", path);
            resultImage = new Image(path);
         //   LOGGER.debug("resultImage url for white or empty square url when no color: {}", resultImage.getUrl());
        }
        return resultImage;
    }

    public Image getPieceImage(Piece piece) {
   //     LOGGER.trace("Entering getPieceImage (piece = {}", piece);
        if (piece == null) {
            return getPieceImage(PlayerColor.WHITE, PieceName.NONE);
        }
        return getPieceImage(piece.getColor(), piece.getName());
    }

    public static ImageCursor getWhiteCursor(){
        ImageView imageView = new ImageView(new Image(IMG_DIR + "whiteHandCursor" + IMG_EXTENSION));
        imageView.setFitHeight(25.0);
        imageView.setFitWidth(25.0);
        imageView.setCursor(imageView.getCursor());
        return new ImageCursor(imageView.getImage());

    }
    public static ImageCursor getBlackCursor(){
        return new ImageCursor(new Image(IMG_DIR + "blackHandCursor" + IMG_EXTENSION));
    }

    public static Piece getPieceFromFENSymbol(Character pieceSymbol) {
        return switch (pieceSymbol) {
            case 'k' -> new King(PlayerColor.BLACK);
            case 'K' -> new King(PlayerColor.WHITE);
            case 'q' -> new Queen(PlayerColor.BLACK);
            case 'Q' -> new Queen(PlayerColor.WHITE);
            case 'r' -> new Rook(PlayerColor.BLACK);
            case 'R' -> new Rook(PlayerColor.WHITE);
            case 'n' -> new Knight(PlayerColor.BLACK);
            case 'N' -> new Knight(PlayerColor.WHITE);
            case 'b' -> new Bishop(PlayerColor.BLACK);
            case 'B' -> new Bishop(PlayerColor.WHITE);
            case 'p' -> new Pawn(PlayerColor.BLACK);
            case 'P' -> new Pawn(PlayerColor.WHITE);
            default -> throw new IllegalArgumentException("The string " + pieceSymbol + " does not represent a piece!");
        };
    }
}