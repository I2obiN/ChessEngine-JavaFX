import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Translate;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessBoard extends Pane {
	
	//private fields
	private int boardWidth = 8;
	private int boardHeight = 8;
	
	private Piece[][] pieces;
	private Rectangle[][] board;
	private Image[][] images;
	private ImageView[][] imageviews;

	
	private double cell_width;
	private double cell_height;
	private int current_player=1;
	private String clicklogic="false";
	
	private final int PlayerWhite = 1;
	private final int PlayerBlack = 2;
	private final int Empty = 0;
	private boolean pieceselect = false;
	private boolean winner = false;
	
	private Translate pos;
	
	public String clicklogic(){
		return clicklogic;
	}
	
	public void changeclicktrue(){
	clicklogic = "true";
	}
	
	public void changeclickfalse(){
	clicklogic = "false";
	}
	
	public void changeclicknull(){
	clicklogic = "null";
	}
	
	public int currentplayer(){
		return current_player;
	}
	
	public int otherplayer(){
		if(current_player==1){
			int otherplayer=2;
			return otherplayer;
		}
		else{
		int otherplayer=1;
		return otherplayer;}
	}
	
	public boolean pieceselect(){
		return pieceselect;
	}
	
	public void resetGame(){
		// Reset imageviews array
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
			imageviews[x][y].setImage(images[x][y]);
			}
		}
	}
	
public ChessBoard() {
	
	pos = new Translate();
	
	// Declares new board
	board = new Rectangle[boardWidth][boardHeight];
	
	// Initializes new board
	for(int x=0; x < 8; x++){
		for(int j=0; j < 8; j++){
			board[x][j] = new Rectangle();
			board[x][j].setWidth(50);
			board[x][j].setHeight(50);
			board[x][j].setStroke(Color.TRANSPARENT);
			board[x][j].setStrokeType(StrokeType.INSIDE);
			board[x][j].setStrokeWidth(1);
		}
	}
	
	// Generates colours for the chessboard backgrounds
	for(int x=0; x < 8; x++){
		for(int j=0; j < 8; j++){
			if(x%2==0 && j%2==1){
			board[x][j].setFill(Color.YELLOW);
			}
			else if(x%2==0 && j%2==0){
			board[x][j].setFill(Color.BROWN);
			}
			else if(x%2==1 && j%2==1){
			board[x][j].setFill(Color.BROWN);
			}
			else if(x%2==1 && j%2==0){
			board[x][j].setFill(Color.YELLOW);
			}
		}
	}
	
	// New image array
	images = new Image[8][8];
		
		// first row of renders (black)
		images[7][0] = new Image("blackrook.png"); images[6][0] = new Image("blackknight.png");
		images[5][0] = new Image("blackbishop.png"); images[4][0] = new Image("blackking.png");
		images[3][0] = new Image("blackqueen.png"); images[2][0] = new Image("blackbishop.png");
		images[1][0] = new Image("blackknight.png"); images[0][0] = new Image("blackrook.png");
		// second row (black)
		images[7][1] = new Image("blackpawn.png"); images[6][1] = new Image("blackpawn.png");
		images[5][1] = new Image("blackpawn.png"); images[4][1] = new Image("blackpawn.png");
		images[3][1] = new Image("blackpawn.png"); images[2][1] = new Image("blackpawn.png");
		images[1][1] = new Image("blackpawn.png"); images[0][1] = new Image("blackpawn.png");
		
		// empty squares
		for(int x = 0; x < 8; x++){
			for(int y = 2; y < 6; y++){
				images[x][y] = new Image("empty.png");
			}
		}
		
		// third row (white)
		images[7][6] = new Image("whitepawn.png"); images[6][6] = new Image("whitepawn.png");
		images[5][6] = new Image("whitepawn.png"); images[4][6] = new Image("whitepawn.png");
		images[3][6] = new Image("whitepawn.png"); images[2][6] = new Image("whitepawn.png");
		images[1][6] = new Image("whitepawn.png"); images[0][6] = new Image("whitepawn.png");
		// fourth row of renders (white)
		images[7][7] = new Image("whiterook.png"); images[6][7] = new Image("whiteknight.png");
		images[5][7] = new Image("whitebishop.png"); images[4][7] = new Image("whiteking.png");
		images[3][7] = new Image("whitequeen.png"); images[2][7] = new Image("whitebishop.png");
		images[1][7] = new Image("whiteknight.png"); images[0][7] = new Image("whiterook.png");
		
		// Viewers for each image
		imageviews = new ImageView[8][8];
		
		// Initializes imageviewers and windows
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				imageviews[x][y] = new ImageView();
			}
		}
		
		// Puts images into imageviewers
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				imageviews[x][y].setImage(images[x][y]);
				imageviews[x][y].setFitWidth(50);
				imageviews[x][y].setFitHeight(80);
				imageviews[x][y].setPreserveRatio(true);
				imageviews[x][y].setSmooth(true);
				imageviews[x][y].setCache(true);
				imageviews[x][y].setTranslateX(board[x][y].getWidth() / 8);	
			}
		}
	
		//initialize the board: background, data structures, inital layout of pieces
		pieces = new Piece[boardWidth][boardHeight];
		
		// White Pieces
		pieces[7][7] = new PieceRook(PlayerWhite, 7, 7);
		pieces[6][7] = new PieceKnight(PlayerWhite, 6, 7);
		pieces[5][7] = new PieceBishop(PlayerWhite, 5, 7);
		pieces[4][7] = new PieceKing(PlayerWhite, 4, 7);
		pieces[3][7] = new PieceQueen(PlayerWhite, 3, 7);
		pieces[2][7] = new PieceBishop(PlayerWhite, 2, 7);
		pieces[1][7] = new PieceKnight(PlayerWhite, 1, 7);
		pieces[0][7] = new PieceRook(PlayerWhite, 0, 7);
		// Pawns
		pieces[7][6] = new PiecePawn(PlayerWhite, 7, 6, true);
		pieces[6][6] = new PiecePawn(PlayerWhite, 6, 6, true);
		pieces[5][6] = new PiecePawn(PlayerWhite, 5, 6, true);
		pieces[4][6] = new PiecePawn(PlayerWhite, 4, 6, true);
		pieces[3][6] = new PiecePawn(PlayerWhite, 3, 6, true);
		pieces[2][6] = new PiecePawn(PlayerWhite, 2, 6, true);
		pieces[1][6] = new PiecePawn(PlayerWhite, 1, 6, true);
		pieces[0][6] = new PiecePawn(PlayerWhite, 0 ,6, true);
		
		// Empty Pieces
		for(int x = 5; x > 1; x--){
			for(int j = 0; j < 8; j++){
				pieces[j][x] = new Empty(Empty, j, x);
			}
		}
		
		// Black Pieces
		pieces[7][0] = new PieceRook(PlayerBlack, 7, 0);
		pieces[6][0] = new PieceKnight(PlayerBlack, 6, 0);
		pieces[5][0] = new PieceBishop(PlayerBlack, 5, 0);
		pieces[4][0] = new PieceKing(PlayerBlack, 4, 0);
		pieces[3][0] = new PieceQueen(PlayerBlack, 3, 0);
		pieces[2][0] = new PieceBishop(PlayerBlack, 2, 0);
		pieces[1][0] = new PieceKnight(PlayerBlack, 1, 0);
		pieces[0][0] = new PieceRook(PlayerBlack, 0, 0);
		// Pawns
		pieces[7][1] = new PiecePawn(PlayerBlack, 7, 1, true);
		pieces[6][1] = new PiecePawn(PlayerBlack, 6, 1, true);
		pieces[5][1] = new PiecePawn(PlayerBlack, 5, 1, true);
		pieces[4][1] = new PiecePawn(PlayerBlack, 4, 1, true);
		pieces[3][1] = new PiecePawn(PlayerBlack, 3, 1, true);
		pieces[2][1] = new PiecePawn(PlayerBlack, 2, 1, true);
		pieces[1][1] = new PiecePawn(PlayerBlack, 1, 1, true);
		pieces[0][1] = new PiecePawn(PlayerBlack, 0, 1, true);
		
		current_player = 1;
}

	public void placeboard(final int i, final int j){
		getChildren().add(board[i][j]);
	}
	
	public void placeimages(final int i, final int j){
		getChildren().addAll(imageviews[i][j]);
	}
	
	// Returns stroke of board piece
	public boolean getStroke(final int i, final int j, Paint color){
		if(board[i][j].getStroke() == color){
			return true;
		}
	else return false;
	}
	
	public void checkhighlight(int x, int y){
		board[x][y].setStroke(Color.RED);
	}
	
	//resize method
	@Override
	public void resize(double width, double height){
		super.resize(width, height);
		cell_width = width/8;
		cell_height = height/8;
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				board[i][j].resize(i*cell_width, j*cell_height);
				board[i][j].relocate(i*cell_width, j*cell_height);
				board[i][j].setStrokeWidth(cell_width/16);
				board[i][j].setWidth(cell_width);
				board[i][j].setHeight(cell_height);
			}
		}
		
		for(int j = 0; j < 8; j++){
			for(int i = 0; i < 8; i++){
				// Black Royalty
				if(j == 0){
				imageviews[i][j].resize(cell_width/8, cell_height/8);
				imageviews[i][j].relocate(i*cell_width, j*cell_height);
				imageviews[i][j].setFitWidth(cell_width/1.25);
				imageviews[i][j].setFitHeight(cell_height/1.25);
				imageviews[i][j].setTranslateX(board[i][j].getWidth() / 8);
				}
				
				//White Royalty
				else if(j == 7){
				imageviews[i][j].resize(cell_width/8, cell_height/8);
				imageviews[i][j].relocate(i*cell_width, j*cell_height);
				imageviews[i][j].setFitWidth(cell_width/1.25);
				imageviews[i][j].setFitHeight(cell_height/1.25);
				imageviews[i][j].setTranslateX(board[i][j].getWidth() / 8);
				}
				
				else{
				imageviews[i][j].resize(cell_width/8, cell_height/8);
				imageviews[i][j].relocate(i*cell_width, j*cell_height);
				imageviews[i][j].setFitWidth(cell_width/1.25);
				imageviews[i][j].setFitHeight(cell_height/1.25);
				imageviews[i][j].setTranslateX(board[i][j].getWidth() / 8);	
				}
			}
		}
	}
	
	@Override
	public void relocate(double x, double y){
		super.relocate(x, y);
		pos.setX(x);
		pos.setY(x);
	}
	
	public Piece selectTarget(int hash){
		
		int i=0; int j=0;
		
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				if(imageviews[x][y].hashCode() == hash || board[x][y].hashCode() == hash){
				i = x; j = y;
				}
			}
		}
	
		int enemyplayer = 0;
		
		if(current_player == 1){
			enemyplayer = 2;
		}
		else{enemyplayer = 1;}
		
		if(winner == false){
			if(pieces[i][j].type() == 0 || pieces[i][j].type() == enemyplayer){
			return pieces[i][j];	
			}
		}
		return pieces[i][j];
	}
	
	//select piece method
	public Piece selectPiece(int hash){
		// Determine what piece was selected and if it can be selected
		int i=0; int j=0;
		
		for(int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				if(imageviews[x][y].hashCode() == hash && imageviews[x][y]!=null){
				i = x; j = y;
				}
			}
		}

		if(current_player == 1 && winner == false){
			if(pieces[i][j].type() == 1){
				// If player has already selected the piece, deselect it
				if(board[i][j].getStroke() == Color.LIGHTCORAL && pieceselect == true){
					if(i%2==0 && j%2==1){
					board[i][j].setFill(Color.YELLOW);
					pieceselect = false;
					}
					if(i%2==0 && j%2==0){
					board[i][j].setFill(Color.BROWN);
					pieceselect = false;						
					}
					if(i%2==1 && j%2==1){
					board[i][j].setFill(Color.BROWN);
					pieceselect = false;
					}
					if(i%2==1 && j%2==0){
					board[i][j].setFill(Color.YELLOW);
					pieceselect = false;
					}
				}
				// Otherwise select it and work out moves
				else{
				board[i][j].setStroke(Color.LIGHTCORAL);
				pieceselect = true;
				return pieces[i][j];
				}
			}
		}
		
		// If current player is black
			else{
			if(pieces[i][j].type() == 2){
				if(board[i][j].getStroke() == Color.LIGHTCORAL && pieceselect == true){
						// Resets color
						if(i%2==0 && j%2==1){
						board[i][j].setFill(Color.YELLOW);
						pieceselect = false;						
						}
						if(i%2==0 && j%2==0){
						board[i][j].setFill(Color.BROWN);
						pieceselect = false;
						}
						if(i%2==1 && j%2==1){
						board[i][j].setFill(Color.BROWN);
						pieceselect = false;
						}
						if(i%2==1 && j%2==0){
						board[i][j].setFill(Color.YELLOW);
						pieceselect = false;
						}
				}
				else{
				board[i][j].setStroke(Color.LIGHTCORAL);
				pieceselect = true;
				return pieces[i][j];
				}
			}
		}
		
		// return something ..
		return new Empty(0,i,j);
	}
	
	// Draw the move and remove highlights
	public void drawmove(final int si, int sj, int ti, int tj){
		Image empty = new Image("empty.png");
		String piece = "???";
		piece = "black";
		if(pieces[ti][tj].type() == 1){
		piece = "white";}
		piece = piece + pieces[ti][tj].imagefilename();
		//System.out.println("The new piece image filename: " + piece);
		Image newimage = new Image(piece);
		imageviews[ti][tj].setImage(newimage);
		imageviews[si][sj].setImage(empty);
		// Remove highlight
		if(board[si][sj].getStroke() == Color.LIGHTCORAL && pieceselect == true){
			if(si%2==0 && sj%2==1){
				board[si][sj].setFill(Color.YELLOW);						
			}
			else if(si%2==0 && sj%2==0){
				board[si][sj].setFill(Color.BROWN);
			}
			else if(si%2==1 && sj%2==1){
				board[si][sj].setFill(Color.BROWN);
			}
			else if(si%2==1 && sj%2==0){
				board[si][sj].setFill(Color.YELLOW);
			}
		}
		else if(pieceselect == false){
		board[si][sj].setStroke(Color.LIGHTCORAL);
		pieceselect = true;
		}
	}
	
	// Returns state of the chess board ..
	public Piece[][] getState(){
		return pieces;
	}
	
	public void setBoard(Piece[][] newboard){
		for(int x = 0; x < 8; x++){
			for(int y = 0; y < 8; y++){
				pieces[x][y] = newboard[x][y];
				//System.out.println(pieces[y][x].toString() + " " + pieces[y][x].icoord() + "," + pieces[y][x].jcoord());
			}
		}
	}
	
	public void hoverhighlight(int x, int y){
		int i=x; int j=y;
		// Set highlight color
		board[i][j].setStroke(Color.CADETBLUE);
	}
	
	public void clearhighlights(){
		for(int x=0; x < 8; x++){
			for(int j=0; j < 8; j++){
				board[x][j].setStroke(Color.TRANSPARENT);
				if(x%2==0 && j%2==1){
				board[x][j].setFill(Color.YELLOW);
				}
				else if(x%2==0 && j%2==0){
				board[x][j].setFill(Color.BROWN);
				}
				else if(x%2==1 && j%2==1){
				board[x][j].setFill(Color.BROWN);
				}
				else if(x%2==1 && j%2==0){
				board[x][j].setFill(Color.YELLOW);
				}
			}
		}
	}
		
	public void changeplayer(){
		if(current_player == 1){current_player = 2;}
		else{current_player = 1;}
	}
	
	public void validMoves(Piece p){
		
		//_____________________________________WHITEPAWNS_____________________________________//
		if(p.toString() == "Pawn" && p.type() == 1){
			// LOOK ONE SQUARE AHEAD IF CLEAR HIGHLIGHT
			if(p.jcoord()-1 >= 0){ // Guard for bounds
			if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
			}}
			
			if(p.firstmove() == true){
			// LOOK TWO SQUARE AHEAD IF CLEAR HIGHLIGHT
			if(pieces[p.icoord()][p.jcoord()-2].toString().equals("Empty") && pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
			}}
	
			// LOOK ONE SQUARE LEFT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
			if(p.icoord()-1 >= 0 && p.jcoord()-1 >= 0){
			if(pieces[p.icoord()-1][p.jcoord()-1].type() == 2){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
			}}
		
			// LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
			if(p.icoord()+1 < 8 && p.jcoord()-1 >= 0){
			if(pieces[p.icoord()+1][p.jcoord()-1].type() == 2){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
			}}
		}
			
			//_____________________________________BLACKPAWNS_____________________________________//
			if(p.toString() == "Pawn" && p.type() == 2){
				// LOOK ONE SQUARE AHEAD IF CLEAR HIGHLIGHT
				if(p.jcoord()+1 < 8){ // Guard for bounds
				if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
					board[p.icoord()][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}}
				
				if(p.firstmove() == true){
				// LOOK TWO SQUARE AHEAD IF CLEAR HIGHLIGHT
				if(pieces[p.icoord()][p.jcoord()+2].toString().equals("Empty") && pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
					board[p.icoord()][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
				}}
		
				// LOOK ONE SQUARE LEFT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
				if(p.icoord()-1 >= 0 && p.jcoord()+1 < 8){
				if(pieces[p.icoord()-1][p.jcoord()+1].type() == 1){
					board[p.icoord()-1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}}
			
				// LOOK ONE SQUARE RIGHT DIAGONALLY IF ENEMY PRESENT HIGHLIGHT
				if(p.icoord()+1 < 8 && p.jcoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()+1].type() == 1){
					board[p.icoord()+1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}}
			}
		
			//__________________________________________WHITEROOK_____________________________________//
			if(p.toString() == "Rook" && p.type() == 1){	
				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==1){
					// Stop looking
					y=-1;
					}
				}
				
				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=8;
					}
				}
				
				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=-1;
					}
				}
				
				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==1 && y!=8){
					// Stop looking
					y=8;
					}
				}
			}
			
			//__________________________________________BLACKROOK_____________________________________//
			
			if(p.toString() == "Rook" && p.type() == 2){
				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==2){
					// Stop looking
					y=-1;
					}
				}
				
				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=8;
					}
				}
				
				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=-1;
					}
				}
				
				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==2){
					// Stop looking
					y=8;
					}
				}
			}
	
			//__________________________________________WHITEBISHOP_____________________________________//
			if(p.toString() == "Bishop" && p.type() == 1){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1; y=-1;
					}
				}
				
				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8; y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=8; y=-1;
					}
				}
				
				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1; y=8;
					}
				}
				
				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=8;y=8;
					}
				}
			}
			//__________________________________________BLACKBISHOP_____________________________________//
			if(p.toString() == "Bishop" && p.type() == 2){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=-1; y=-1;
					}
				}
				
				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y = -1;
					}
				}
				
				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x= -1; y = 8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x = -1; y = 8;
					}
				}
				
				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y=8;
					}
				}
			}
	
			//_________________________________WHITEKNIGHT_____________________________________//
			
			// Assuming knights can jump regardless of what pieces are in the way
			
			if(p.toString() == "Knight" && p.type() == 1){
				// Up and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()-1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()-2].type()==2){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Up and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()-1].type()==2){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Up and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()-2].type()==2){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Up and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()-1].type()==2){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()+2].type()==2){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()+1].type()==2){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()-1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()+2].type()==2){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()+1].type()==2){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}	
			}
			
			//_________________________________BLACKKNIGHT_____________________________________//
			if(p.toString() == "Knight" && p.type() == 2){
				// Up and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()-1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()-2].type()==1){
						board[p.icoord()-1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Up and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()-1].type()==1){
						board[p.icoord()-2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Up and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()-2 >= 0){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()-2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()-2].type()==1){
						board[p.icoord()+1][p.jcoord()-2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Up and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()-1 >= 0){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()-1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()-1].type()==1){
						board[p.icoord()+2][p.jcoord()-1].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and right (first)
				if(p.icoord()+1 < 8 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()+1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+1][p.jcoord()+2].type()==1){
						board[p.icoord()+1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and right (second)
				if(p.icoord()+2 < 8 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()+2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()+2][p.jcoord()+1].type()==1){
						board[p.icoord()+2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and left (first)
				if(p.icoord()-1 >= 0 && p.jcoord()+2 < 8){				// Bound check
					if(pieces[p.icoord()-1][p.jcoord()+2].toString().equals("Empty")){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-1][p.jcoord()+2].type()==1){
						board[p.icoord()-1][p.jcoord()+2].setStroke(Color.AQUAMARINE);
					}
				}
				
				// Bottom and left (second)
				if(p.icoord()-2 >= 0 && p.jcoord()+1 < 8){				// Bound check
					if(pieces[p.icoord()-2][p.jcoord()+1].toString().equals("Empty")){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[p.icoord()-2][p.jcoord()+1].type()==1){
						board[p.icoord()-2][p.jcoord()+1].setStroke(Color.AQUAMARINE);
					}
				}
				
			}
			
			//______________________________WHITEQUEEN_____________________________________//
			if(p.toString() == "Queen" && p.type() == 1){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1; y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1; y=-1;
					}
				}
				
				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8; y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==1){
					// Stop Looking
					x=8; y=-1;
					}
				}
				
				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1;y=8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=-1;y=8;
					}
				}
				
				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==2){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==1){
					// Stop Looking
					x=8;y=8;
					}
				}
				
				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);	
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==1){
					// Stop looking
					y=-1;
					}
				}
				
				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);	
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=8;
					}
				}
				
				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==2){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==1){
					// Stop looking
					x=-1;
					}
				}
				
				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);	
					}
					if(pieces[p.icoord()][y].type()==2){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==1 && y!=8){
					// Stop looking
					y=8;
					}
				}
			}
			
			//______________________________BLACKQUEEN_____________________________________//
			if(p.toString() == "Queen" && p.type() == 2){
				// Look up .. (left)
				for(int y=p.jcoord()-1, x=p.icoord()-1; y >= 0 && x >= 0; y--,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1;y=-1;
					}
					if(x!=-1 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=-1;y=-1;
					}
				}
				
				// Look up .. (right)
				for(int y=p.jcoord()-1, x=p.icoord()+1; y >= 0 && x < 8; y--,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=-1;
					}
					if(x!=8 && y!=-1 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y=-1;
					}
				}
				
				// Look down .. (left)
				for(int y=p.jcoord()+1, x=p.icoord()-1; y < 8 && x >= 0; y++,x--){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=-1;y=8;
					}
					if(x!=-1 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x=-1;y=8;
					}
				}
				
				// Look down .. (right)
				for(int y=p.jcoord()+1, x=p.icoord()+1; y < 8 && x < 8; y++,x++){
					if(pieces[x][y].toString().equals("Empty")){
					board[x][y].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][y].type()==1){
					board[x][y].setStroke(Color.AQUAMARINE);
					// Stop Looking
					x=8;y=8;
					}
					if(x!=8 && y!=8 && pieces[x][y].type()==2){
					// Stop Looking
					x=8;y=8;
					}
				}
				
				// Look Up ..
				for(int y = p.jcoord()-1; y >= 0; y--){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);	
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=-1;
					}
					if(y!=-1 && pieces[p.icoord()][y].type()==2){
					// Stop looking
					y=-1;
					}
				}
				
				// Look Right ..
				for(int x = p.icoord()+1; x < 8; x++){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);	
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=8;
					}
					if(x!=8 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=8;
					}
				}
				
				// Look Left ..
				for(int x = p.icoord()-1; x >= 0; x--){
					if(pieces[x][p.jcoord()].toString().equals("Empty")){
					board[x][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
					}
					if(pieces[x][p.jcoord()].type()==1){
					board[x][p.jcoord()].setStroke(Color.AQUAMARINE);
					// Stop looking
					x=-1;
					}
					if(x!=-1 && pieces[x][p.jcoord()].type()==2){
					// Stop looking
					x=-1;
					}
				}
				
				// Look Down ..
				for(int y = p.jcoord()+1; y < 8; y++){
					if(pieces[p.icoord()][y].toString().equals("Empty")){
					board[p.icoord()][y].setStroke(Color.CORNFLOWERBLUE);	
					}
					if(pieces[p.icoord()][y].type()==1){
					board[p.icoord()][y].setStroke(Color.AQUAMARINE);
					// Stop looking
					y=8;
					}
					if(y!=8 && pieces[p.icoord()][y].type()==2 && y!=8){
					// Stop looking
					y=8;
					}
				}
			}
	
		//_________________________________WHITEKING_____________________________________//
			if(p.toString() == "King" && p.type() == 1){
				// Up
				if(p.jcoord()-1 >= 0){
				if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()-1].type()==2){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Up - right
				if(p.jcoord()-1 >= 0 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()-1].type()==2){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Up - left
				if(p.jcoord()-1 >= 0 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()-1].type()==2){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Left
				if(p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()].type()==2){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Right
				if(p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()].type()==2){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Down
				if(p.jcoord()+1 < 8){
				if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()+1].type()==2){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Down - left
				if(p.jcoord()+1 < 8 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()+1].type()==2){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Down - right
				if(p.jcoord()+1 < 8 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()+1].type()==2){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}	
			}
			
			//_________________________________BLACKKING_____________________________________//
			if(p.toString() == "King" && p.type() == 2){
				// Up
				if(p.jcoord()-1 >= 0){
				if(pieces[p.icoord()][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()-1].type()==1){
				board[p.icoord()][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Up - right
				if(p.jcoord()-1 >= 0 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()-1].type()==1){
				board[p.icoord()+1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Up - left
				if(p.jcoord()-1 >= 0 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()-1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()-1].type()==1){
				board[p.icoord()-1][p.jcoord()-1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Left
				if(p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()].type()==1){
				board[p.icoord()-1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Right
				if(p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()].type()==1){
				board[p.icoord()+1][p.jcoord()].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Down
				if(p.jcoord()+1 < 8){
				if(pieces[p.icoord()][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()][p.jcoord()+1].type()==1){
				board[p.icoord()][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Down - left
				if(p.jcoord()+1 < 8 && p.icoord()-1 >= 0){
				if(pieces[p.icoord()-1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()-1][p.jcoord()+1].type()==1){
				board[p.icoord()-1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}
				
				// Down - right
				if(p.jcoord()+1 < 8 && p.icoord()+1 < 8){
				if(pieces[p.icoord()+1][p.jcoord()+1].toString().equals("Empty")){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.CORNFLOWERBLUE);
				}
				if(pieces[p.icoord()+1][p.jcoord()+1].type()==1){
				board[p.icoord()+1][p.jcoord()+1].setStroke(Color.AQUAMARINE);
				}
				}	
			}
	}
}
