An attempt at programming a chess bot using JavaFX integrated within the Spring framework.

To compile the app (Linux/Mac): 
javac --module-path <path_to_JDK_FX/lib> --add-modules javafx.controls,javafx.fxml,javafx.base com.marcopiccionitraining.javaspringfx.BootifulFxApplication.java  

To launch the app: Linux/Mac):
java --module-path <path_to_JDK_FX/lib> --add-modules javafx.controls,javafx.fxml,javafx.base com.marcopiccionitraining.javaspringfx.BootifulFxApplication

Stockfish basic command line commands;
To input a FEN position in stockfish: position fen <FENposition>
To display the chessboard: d
To compute the moves at depth n in a previously entered position: go perft <n>

Progress Log
2023-4-4: Now the GUI behaves reasonably well when resizing the app main window. 
GUI is made of:
- a chessboard;
- a text field to input positions in fen format;
- a button to load a position from a .fen file;
- clickable piece figurines to set up a position;
- clear board button;
- flip board button;
- initial position button;
2024
Programming tasks
1. Chessboard representation
   1.1 Object-oriented representation
   1.2 Bitboard representation
   2. Moves generation (depth 1, current color, no metadata)
      2.1 King moves generation
         A king cannot move to tiles controlled by enemy pieces or occupied by friendly pieces.
         A king cannot be captured.
      2.1.1 Exit generation if checkmate
      2.1.2 Exit generation if stalemate
      2.1.3 Restrict number of moves if in double check
      2.1.4 Restrict number of moves if in check
      2.1.5 Discovered checks and pins
        A pinned piece is a piece whose movement is restricted to certain tiles. In fact by moving to one of the 
        forbidden tiles it would discover a check to the enemy king and this would be against the rules.
        Pins can only be caused by the opponent's sliding pieces (rooks, bishops, and queens are therefore the only 
        possible pinners). Pawns and knights, in addition to sliding pieces, can be used to uncover a discovery 
        check by a friendly piece and act as pinned pieces when the pinner is an opponent's sliding piece.
        To detect pins (pinned pieces and the tiles they control) programmatically we can compute an "x-ray attack set",
        that is, extract all the possible tiles reachable from the king's tile as if it were a queen. The tiles occupied
        by friendly pieces must be excluded (but the tiles beyond those must be counted, hence x-ray attack), 
        and the tiles occupied by enemy sliding pieces (excluding enemy pieces in addition to the first). 
        Given an x-ray direction out of the 8 available, we then start from the current player's king tile and then 
        proceed with an outer loop for the 8 directions and an inner loop looking for max one pinner and max one pinned 
        piece for each direction. This is what can happen:
         1) A friendly piece is encountered first. 
            1.1 Record the friendly piece as potentially pinned piece.
            1.2 Continue searching in same direction until reaching chessboard border (break inner loop, nothing found).
            While searching, count the tiles and associate them to the potential pinned piece.
            1.3 If before reaching the border you find an enemy sliding piece lying on the same direction, then you have
            a pinner piece, and a pinned piece from 1).
            1.4 If before reaching the border you find a second friendly piece in the same direction instead of an enemy
            one, then you can break the inner loop (nothing found). 
            1.5 If after step 1.3 before reaching the border you find another friendly piece in the same direction, 
            then you can break the inner loop. The first friendly piece found is pinned and the first enemy piece found 
            is a pinner.  
         2) An enemy piece is encountered first. If it's a sliding piece then your king is under check.
         3) At this point you should have a data structure (likely a hash map) containing pinned pieces with their
            reachable tiles.
                     2.1.6 Consider castle
                     2.1.6.1 Kingside castle
                     2.1.6.2 Queenside castle
                     2.1.6.3 Castle restrictions
                     2.1.6.3.1 King never moved
                     2.1.6.3.2 Rook never moved
                     2.1.6.3.3 King not in check
                     2.1.6.3.4 Tiles between king and rook are empty
                     2.1.6.3.4 Tiles between king and rook are not controlled by enemy pieces
                     2.2 Sliding pieces move generation
                     2.2.1 Bishop moves generation
                     2.2.1.1 Restrict number of moves if pinned
                     2.2.1.2 Consider checks (direct or discovered)
                     2.2.2 Rook moves generation
                     2.2.2.1 Restrict number of moves if pinned
                     2.2.2.2 Consider checks (direct or discovered)
                     2.2.3 Queen moves generation
                     2.2.3.1 Restrict number of moves if pinned
                     2.2.3.2 Consider checks (direct or discovered)
                     2.3 Knight moves generation
                     2.3.1 Restrict number of moves if pinned
                     2.3.2 Consider checks (direct or discovered)
                     2.4 Pawn moves generation
                     2.4.1 Restrict number of moves if pinned
                     2.4.2 Consider en passant captures
                     2.4.3 Consider checks (direct or discovered)
                     2.4.4 Consider promotions
3. Moves generation (depth > 1, alternate colors)
   3.1 For each move generated at depth d: switch color and generate all possible moves for current (d+1) depth
   3.2 Exit current depth and go back to previous depth (d-1) if checkmate or stalemate
4. Remarks on move generation
   4.1 Execute each move and save board state; then undo each move and restore board state.
   4.2 Write as many unit tests as possible to test every aspect of your program in isolation.
   4.3 Check correctness using assert instructions (use Design by Contract for consistency)
   4.4 Check correctness using perft function and compare number of generated positions with Stockfish.
   4.5 Check performance (secondary to move evaluation performance)
5. Statistics
   5.1 Counters (have dependencies on each other):
   5.1.1 Moves: reflect leaves of the generated tree, aka nodes at max depth.
   5.1.2 Double checks: reflect the number of double checks. Each double check is NOT counted as 2 simple checks as well (TBC).
   5.1.3 Discovery checks: reflect the number of discovery checks. If applicable, reflect the number of double checks as well.
   Each discovery check is NOT counted as 2 simple checks as well (TBC).
   5.1.4 Checkmates: reflect the number of checkmates. Each checkmate is not counted as a check (TBC).
   5.1.5 Checks: single checks (no checkmates, no double checks), discovery checks which are not double checks.
   5.1.6 Stalemates: reflect the number of stalemates.
   5.1.7 Captures: reflect the number of captures at max depth multiplied by depth plus the number of capture at max depth - 1
   multiplied by depth -1 etc. until depth 0.
   5.1.8 En passant captures: reflect the number of en passant captures at max depth multiplied by depth plus the number of
   en passant capture at max depth - 1 multiplied by depth -1 etc. until depth 0. 
6. Move evaluation
   6.1 Add evaluation to each move based on certain rules
   6.2 Program rules based on human experience
   6.3 Let program find its own rules by learning them when playing games against itself (use reinforcement learning).
7.  Optimization
   7.1 