package com.marcopiccionitraining.parkychess.model;

public interface FENPositions {
    String FEN_SIMPLEST_LEGAL = "7k/8/8/8/8/8/6Q1/7K w - - 0 0";
    String FEN_SIMPLEST_PLAYABLE = "7k/8/8/8/8/8/7P/7K w - - 0 0";
    String FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE = "7k/pp6/8/8/8/8/PP6/7K w - - 0 0";
    String FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE = "7k/p7/8/8/8/8/P7/7K w - - 0 0";
    String FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS = "7k/p7/8/8/8/8/1P6/7K w - - 0 0";
    String FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BASIC ="7k/p7/8/1P6/8/8/8/7K b - - 0 0";
    String FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BLACK_PAWN_START_SQUARE = "8/p5k1/8/1P6/8/8/8/7K w - - 0 0";
    String FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_A5 = "7k/8/8/pP6/8/8/8/7K w - a6 0 0";
    String FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_H4 = "7k/8/8/8/6Pp/8/8/7K b - g3 0 0";
    String FEN_WHITE_PAWN_PROMOTION = "7k/3P4/8/8/8/8/8/7K w - - 0 0";
    String FEN_BLACK_PAWN_PROMOTION = "7k/8/8/8/8/8/p7/7K b - - 0 0";
    String FEN_WHITE_BISHOP_C1_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/2B1K3 w - - 0 0";
    String FEN_WHITE_BISHOP_F1_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/4KB2 w - - 0 0";
    String FEN_BLACK_BISHOP_C8_NO_CHECK_POSITION = "2b1k3/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_BLACK_BISHOP_F8_NO_CHECK_POSITION = "4kb2/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_BLACK_ROOK_A8_NO_CHECK_POSITION = "r3k3/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_BLACK_ROOK_H8_NO_CHECK_POSITION = "4k2r/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_WHITE_ROOK_A1_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/R3K3 w - - 0 0";
    String FEN_WHITE_ROOK_H1_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/4K2R w - - 0 0";
    String FEN_BLACK_QUEEN_D8_NO_CHECK_POSITION = "3qk3/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_WHITE_QUEEN_D1_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/3QK3 w - - 0 0";
    String FEN_BLACK_KNIGHT_D5_NO_CHECK_8_MIXED_JUMPS_POSITION = "4k3/2P1B3/1R3p2/3N4/1P3q2/2P1b3/8/4K3 w - - 0 0";
    String FEN_WHITE_KNIGHT_E5_NO_CHECK_NO_LEGAL_JUMPS_POSITION = "4k3/3R1B2/2P3K1/4N3/2P3P1/3B1Q2/8/8 w - - 0 0";
    String FEN_WHITE_KNIGHT_CORNER_2_JUMPS_POSITION = "4k3/8/8/8/8/1p6/2b5/N3K3 w - - 0 0";
    String FEN_WHITE_KNIGHT_RIM_1_JUMP_POSITION = "4k3/1p6/2P5/N7/2P5/1P6/8/4K3 w - - 0 0";
    String FEN_TWO_MUTUALLY_BLOCKING_PAWNS_POSITION = "4k3/8/8/p7/P7/8/8/4K3 w - - 0 0";
    String FEN_FOUR_MUTUALLY_BLOCKING_PAWNS_POSITION = "4k3/8/8/2pp4/2PP4/8/8/4K3 w - - 0 0";
    String FEN_SIX_MUTUALLY_BLOCKING_PAWNS_POSITION = "4k3/8/8/4ppp1/4PPP1/8/8/4K3 w - - 0 0";
    String FEN_KING_BISHOP_CHECK_POSITION = "4k3/8/8/1B6/8/8/8/4K3 b - - 0 0";
    String FEN_KING_BISHOP_CHECK_INTERPOSE_POSITION = "2b1k3/8/8/1B6/8/8/8/4K3 b - - 0 0";
    String FEN_KING_ROOK_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/R3K3 w - - 0 0";
    String FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION = "R3k3/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION = "4k2R/8/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION = "4k3/8/8/8/8/8/8/r3K3 w - - 0 0";
    String FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION = "4k3/8/8/8/8/8/8/4K2r w - - 0 0";
    String FEN_WHITE_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE = "4k3/8/8/8/8/8/8/R1B1K3 w Q - 0 0";
    String FEN_BLACK_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE = "rn2k3/8/8/8/8/8/8/4K3 b q - 0 0";
    String FEN_WHITE_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE = "4k3/8/8/8/8/8/8/4K1NR w K - 0 0";
    String FEN_BLACK_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE = "4kb1r/8/8/8/8/8/8/4K3 b k - 0 0";
    String FEN_BLACK_KING_NO_CASTLE_PATH_UNDER_CHECK = "r3k2r/8/8/8/8/8/8/3RK1R1 b kq - 0 0";
    String FEN_WHITE_KING_NO_CASTLE_PATH_UNDER_CHECK = "4k3/8/8/8/2q5/8/8/R3K2R w KQ - 0 0";
    String FEN_BLACK_KING_NO_CASTLE_UNDER_CHECK = "r3k2r/8/8/8/8/8/8/3KR3 b kq - 0 0";
    String FEN_WHITE_KING_NO_CASTLE_UNDER_CHECK = "3kr3/8/8/8/8/8/8/R3K2R w KQ - 0 0";
    String FEN_KING_QUEEN_CHECK_POSITION = "4k3/8/8/8/4Q3/8/8/4K3 b - - 0 0";
    String FEN_KING_QUEEN_NO_CHECK_POSITION = "4k3/8/8/8/8/8/8/3QK3 b - - 0 0";
    String FEN_KING_KNIGHT_NO_CHECK_POSITION = "8/8/8/3k4/8/8/8/1N5K w - - 0 0";
    String FEN_KING_WHITE_PAWN_NO_CHECK_POSITION = "4k3/4P3/8/8/8/8/8/7K w - - 0 0";
    String FEN_KING_BLACK_PAWN_NO_CHECK_POSITION = "4k3/4p3/8/8/8/8/8/7K w - - 0 0";
    String FEN_KING_PAWN_CHECK_LEFT_POSITION = "4k3/5P2/8/8/8/8/8/7K b - - 0 0";
    String FEN_KING_H8_PAWN_CHECK_LEFT_POSITION = "7k/p7/8/1P6/8/8/8/7K b - - 0 0";
    String FEN_KING_H8_PAWN_A6_CHECK_LEFT_POSITION = "7k/8/P7/8/8/8/8/7K b - - 0 0";
    String FEN_KING_PAWN_CHECK_RIGHT_POSITION = "4k3/3P4/8/8/8/8/8/7K b - - 0 0";
    String FEN_KING_BLACK_PAWN_CHECK_RIGHT_POSITION = "4k3/8/8/8/8/8/5p2/4K3 w - - 0 0";
    String FEN_KING_BLACK_PAWN_CHECK_LEFT_POSITION = "4k3/8/8/8/8/8/5p2/6K1 w - - 0 0";
    String FEN_KING_PE6_VS_KING_E3_NG1_POSITION = "4k3/8/3p4/8/8/4P3/8/4K1N1 w - - 0 0";
    String FEN_KING_ROOK_CLOSE_PAWNS_CHECK_POSITION = "4k3/pppppp2/8/8/8/8/8/3RK3 b - - 0 0";
    String FEN_KING_ROOK_CLOSE_VAR_PAWNS_CHECK_POSITION = "4k3/ppp1pp2/3p4/8/8/8/8/3RK3 w - - 0 0";
    String FEN_KING_ROOK_A4_PAWNS_CHECK_POSITION = "4k3/pppppp2/8/8/R7/8/8/4K3 b - - 0 0";
    String FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK = "4k3/8/4r3/8/8/8/8/4R2K b - - 0 0";
    String FEN_BLACK_ROOK_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK = "4k3/4r3/8/8/8/8/8/4R2K b - - 0 0";
    String FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK = "8/1k1r3R/8/8/8/8/8/7K b - - 0 0";
    String FEN_BLACK_ROOK_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK = "8/1kr4R/8/8/8/8/8/7K b - - 0 0";
    String FEN_BLACK_BISHOP_PINNED_BY_WHITE_BISHOP = "2k5/8/4b3/8/8/7B/8/7K b - - 0 0";
    String FEN_BLACK_BISHOP_NOT_PINNED_BY_WHITE_BISHOP_BECAUSE_OF_INTERPOSING_KNIGHT = "2k5/8/4b3/5N2/8/7B/8/7K b - - 0 0";
    String FEN_WHITE_BISHOP_PINNED_BY_BLACK_BISHOP = "1k6/2b5/8/8/8/6B1/7K/8 w - - 0 0";
    String FEN_WHITE_KNIGHT_PINNED_BY_BLACK_ROOK_ON_ROW = "8/8/8/1K1N2r1/8/8/8/7k w - - 0 0";
    String FEN_BLACK_KNIGHT_PINNED_BY_WHITE_QUEEN_ON_COLUMN = "1k6/8/8/1n6/8/8/8/1Q5K b - - 0 0";
    String FEN_WHITE_KNIGHT_PINNED_BY_BLACK_QUEEN_ON_DIAGONAL = "8/8/K7/1N6/8/8/8/5q1k w - - 0 0";
    String FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_DIAGONAL = "4k3/3p4/8/8/Q7/8/8/4K3 b - - 0 0";
    String FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_ROW = "8/Q2pk3/8/8/8/8/8/4K3 b - - 0 0";
    String FEN_WHITE_PAWN_PINNED_NO_EN_PASSANT = "4k3/8/8/r1pP1K2/8/8/8/8 w - c6 0 0";
    String FEN_WHITE_PAWNS_NOT_PINNED_BY_BLACK_QUEEN = "4k3/8/8/q7/8/2P5/3P4/4K3 w - - 0 0";
    String FEN_KING_DOUBLE_CHECKED_AND_MATED_BY_WHITE_KNIGHT = "R6k/6pp/6N1/8/8/8/8/7K b - - 0 0";
    String FEN_KNIGHT_DOUBLE_CHECKS_AND_MATES_WHITE_KING = "R4n1k/3P2pp/4P3/r7/8/8/8/7K w - - 0 0";
    String FEN_INITIAL_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_INITIAL_POSITION_NA3 = "rnbqkbnr/pppppppp/8/8/8/N7/PPPPPPPP/R1BQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3 = "rnbqkbnr/pppppppp/8/8/8/2P5/PP1PPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P5/PP1PPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_E3 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P1P3/PP1P1PPP/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_E4 = "rnbqkbnr/pp1ppppp/2p5/8/4P3/2P14/PP1P1PPP/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_F3 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P2P2/PP1PP1PP/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_F4 = "rnbqkbnr/pp1ppppp/2p5/8/5P2/2P5/PP1PP1PP/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_G4 = "rnbqkbnr/pp1ppppp/2p5/8/6P1/2P5/PP1PPP1P/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_QA4 = "rnbqkbnr/pp1ppppp/2p5/8/Q7/2P5/PP1PPPPP/RNB1KBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_QB3 = "rnbqkbnr/pp1ppppp/2p5/8/8/1QP5/PP1PPPPP/RNB1KBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_QC2 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P5/PPQPPPPP/RNB1KBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_G3 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P3P1/PP1PPP1P/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_H3 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P4P/PP1PPPP1/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_H4 = "rnbqkbnr/pp1ppppp/2p5/8/7P/2P5/PP1PPPP1/RNBQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_NF3 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P2N2/PP1PPPPP/RNBQKB1R b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_NH3 = "rnbqkbnr/pp1ppppp/2p5/8/8/2P4N/PP1PPPPP/RNBQKB1R b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_NA3 = "rnbqkbnr/pp1ppppp/2p5/8/8/N1P5/PP1PPPPP/R1BQKBNR b KQkq - 0 1";
    String FEN_INITIAL_POSITION_C3_C6_F3_QA5 = "rnb1kbnr/pp1ppppp/2p5/q7/8/2P2P2/PP1PP1PP/RNBQKBNR w KQkq - 0 1";
    String FEN_INITIAL_POSITION_REVERSED = "RNBKQBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbkqbnr w KQkq - 0 1";
    String FEN_POSITION_AFTER_E4 = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
    String FEN_POSITION_AFTER_G4 = "rnbqkbnr/pppppppp/8/8/6P1/8/PPPPPP1P/RNBQKBNR b KQkq g3 0 1";
    String FEN_POSITION_AFTER_E3 = "rnbqkbnr/pppppppp/8/8/8/4P3/PPPP1PPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_B5 = "rnbqkbnr/p1pppppp/8/1p6/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_B5_QH5 = "rnbqkbnr/p1pppppp/8/1p5Q/8/4P3/PPPP1PPP/RNB1KBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6 = "rnbqkbnr/ppp1pppp/3p4/8/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_QG4 = "rnbqkbnr/ppp1pppp/3p4/8/6Q1/4P3/PPPP1PPP/RNB1KBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_A4 = "rnbqkbnr/pppppppp/8/8/P7/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_C6 = "rnbqkbnr/pp1ppppp/2p5/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_C6_A5 = "rnbqkbnr/pp1ppppp/2p5/P7/8/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_E5 = "rnbqkbnr/pppp1ppp/8/4p3/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_E5_D4 = "rnbqkbnr/pppp1ppp/8/4p3/P2P4/8/1PP1PPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_A5 = "rnbqkbnr/1ppppppp/8/p7/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_NC6 = "r1bqkbnr/pppppppp/2n5/8/P7/8/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_NC6_A5 = "r1bqkbnr/pppppppp/2n5/P7/8/8/1PPPPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_A5_B4 = "rnbqkbnr/1ppppppp/8/p7/PP6/8/2PPPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_A5_B4_RA6 = "1nbqkbnr/1ppppppp/r7/p7/PP6/8/2PPPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_A4_A5_B4_RA6_PA4B5 = "1nbqkbnr/1ppppppp/r7/P7/P7/8/2PPPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_C3 = "rnbqkbnr/pppppppp/8/8/8/2P5/PP1PPPPP/RNBQKBNR b KQkq - 0 1";
    String FEN_POSITION_AFTER_C3_D6 = "rnbqkbnr/ppp1pppp/3p4/8/8/2P5/PP1PPPPP/RNBQKBNR w KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_BB5 = "rnbqkbnr/ppp1pppp/3p4/1B6/8/4P3/PPPP1PPP/RNBQK1NR b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_NF3 = "rnbqkbnr/ppp1pppp/3p4/8/8/4PN2/PPPP1PPP/RNBQKB1R b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_NH3 = "rnbqkbnr/ppp1pppp/3p4/8/8/4P2N/PPPP1PPP/RNBQKB1R b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_NE2 = "rnbqkbnr/ppp1pppp/3p4/8/8/4P3/PPPPNPPP/RNBQKB1R b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_BA6 = "rnbqkbnr/ppp1pppp/B2p4/8/8/4P3/PPPP1PPP/RNBQK1NR b KQkq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_KE2 = "rnbqkbnr/ppp1pppp/3p4/8/8/4P3/PPPPKPPP/RNBQ1BNR b kq - 0 1";
    String FEN_POSITION_AFTER_E3_D6_BB5_BD7 = "rn1qkbnr/pppbpppp/3p4/1B6/8/4P3/PPPP1PPP/RNBQK1NR w KQkq - 0 1";
    String FEN_POSITION_AFTER_G4_D6 = "rnbqkbnr/ppp1pppp/3p4/8/6P1/8/PPPPPP1P/RNBQKBNR w KQkq g3 0 1";
    String FEN_POSITION_AFTER_G4_D6_BH3 = "rnbqkbnr/ppp1pppp/3p4/8/6P1/7B/PPPPPP1P/RNBQK1NR b KQkq g3 0 1";
    String FEN_POSITION_AFTER_NH3 = "rnbqkbnr/pppppppp/8/8/8/7N/PPPPPPPP/RNBQKB1R b KQkq - 0 1";
    String FEN_POSITION_AFTER_NH3_F6 = "rnbqkbnr/ppppp1pp/5p2/8/8/7N/PPPPPPPP/RNBQKB1R w KQkq - 0 1";
    String FEN_LONDON_POSITION = "r1bq1rk1/ppp2ppp/2nbpn2/3p4/3P1B2/3BPN2/PPPN1PPP/R2QK2R w KQ - 4 5";
    String FEN_KING_KNIGHT_VS_KING = "8/8/8/8/8/2k5/8/1N5K b - - 0 0";
    String FEN_KING_KNIGHT_VS_KING_NO_CHECK = "8/8/8/8/8/2k5/8/2N4K b - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_KNIGHT_MOVE = "6k1/8/8/6N1/8/6R1/8/5K2 w - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_BISHOP_MOVE = "6k1/8/8/6B1/8/6R1/8/5K2 w - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_BISHOP_DUE_TO_ROOK_MOVE = "6k1/8/4R3/3B4/8/8/8/5K2 w - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_QUEEN_DUE_TO_BISHOP_MOVE = "6k1/8/8/6B1/8/6Q1/8/5K2 w - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_QUEEN_DUE_TO_ROOK_MOVE = "6k1/5R2/8/8/8/1Q6/8/5K2 w - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_PAWN_CAPTURE_MOVE = "6k1/5p2/6P1/8/8/6R1/8/5K2 w - - 0 0";
    String FEN_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_PAWN_EN_PASSANT_CAPTURE_MOVE = "8/6k1/8/5pP1/8/8/6R1/5K2 w - f6 0 0";
    String FEN_NO_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_PAWN_EN_PASSANT_CAPTURE_MOVE_D2 = "8/8/8/8/k3p2R/8/3P4/3K4 w - d3 0 0";
    String FEN_NO_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_PAWN_EN_PASSANT_CAPTURE_MOVE_D1 = "8/8/8/8/k2Pp2R/8/8/3K4 b - d3 0 0";
    String FEN_DISCOVERY_CHECK_FROM_ROOK_DUE_TO_PAWN_ADVANCE_MOVE = "8/8/8/8/R3P2k/8/8/5K2 w - - 0 0";
    String FEN_NO_DISCOVERY_CHECK_FROM_ROOK = "6k1/8/8/6N1/8/8/6R1/5K2 b - - 0 0";
    String FEN_PAWN_CAPTURE_CHECK_FROM_BISHOP = "4kb2/8/8/8/8/8/P7/4K3 w - - 0 0";
    String FEN_ROOK_A1_PSEUDO_LEGAL_MOVES = "7k/8/8/8/8/8/7K/R7 w - - 0 0";
}
