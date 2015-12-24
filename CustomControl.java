import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;

import javafx.scene.control.Control;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;


public class CustomControl extends Control {
	
	//similar to previous custom controls but must handle more
	//complex mouse interactions and key interactions

	private ChessBoard chessboard;
	private Translate pos;
	private Piece selectedpiece;
	private Piece targetpiece;
	private GameLogic gamelogic;
	private boolean junkselection;
	private boolean winner=false;
	private boolean stale=false;
	private int stalecountblack=8;
	private int stalecountwhite=8;
	
	public CustomControl(){
		pos = new Translate();
		setSkin(new CustomControlSkin(this));
		chessboard = new ChessBoard();
		gamelogic = new GameLogic();
		getChildren().addAll(chessboard);
		
		
		// Places background squares
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				chessboard.placeboard(i, j);
			}
		}
		
		// Places chess piece images
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				chessboard.placeimages(i, j);
			}
		}
		
		// Spacebar to reset game ..
		setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.SPACE)
				System.out.println("Game Reset!");
				chessboard = new ChessBoard();
				getChildren().addAll(chessboard);
				
				// Places background squares
				for(int i = 0; i < 8; i++){
					for(int j = 0; j < 8; j++){
						chessboard.placeboard(i, j);
					}
				}
				
				// Places chess piece images
				for(int i = 0; i < 8; i++){
					for(int j = 0; j < 8; j++){
						chessboard.placeimages(i, j);
					}
				}
				
				// Reset game variables
				stale=false;
				winner=false;
				stalecountwhite=8;
				stalecountblack=8;
				chessboard.changeclickfalse();
			}
		});
		
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				// Get has of what we clicked on ..
				int hash=event.getTarget().hashCode();
				
				// Checks if black timer has hit zero
				if(ChessApplication.getTimerBlack()==0){
					winner=true;
					System.out.println("Black time out: White Wins!");
				}
				
				// Checks if white timer has hit zero
				if(ChessApplication.getTimerWhite()==0){
					winner=true;
					System.out.println("White time out: Black Wins!");
				}
				
				// Print current player..
				if(chessboard.clicklogic()=="false" && winner==false && stale==false){
					if(chessboard.currentplayer()==1){
						System.out.print("Current player: White");
					}
					else{System.out.print("Current player: Black");}
				}
				
				System.out.println("\n");
				
				// Second click
				if(chessboard.clicklogic() == "true"){
				Piece[][] boardstate = chessboard.getState();
				
				//System.out.println("mousesec: " + xcoordmouse + "," + ycoordmouse);
				targetpiece = chessboard.selectTarget(hash);
							
				// If pawn selected ..
					if(selectedpiece.toString().equals("Pawn") && selectedpiece != null && targetpiece != null && selectedpiece.equals(targetpiece)==false){
						// Only executes if legal move ..
						if(chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.CORNFLOWERBLUE)==true || chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.AQUAMARINE)==true){
						// If check is false
						if(gamelogic.checkstatus()==false){
							Piece[][] oldstate = new Piece[8][8];
							// Transfer pieces to backup variable
							for(int x=0;x < 8; x++){
								for(int y=0; y < 8; y++){
									oldstate[x][y] = boardstate[x][y];
								}
							}
							// Do move
							boardstate = selectedpiece.movepawn(selectedpiece, targetpiece, boardstate);
							// If move results in no check, do move
							if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
							chessboard.setBoard(boardstate);
							chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
							chessboard.changeplayer();
							chessboard.changeclicknull();
							// Successful move
							stalecountwhite=8;
							stalecountblack=8;
							}
							// If check, reverse move
							else{
							chessboard.changeclicknull();
							chessboard.setBoard(oldstate);
							gamelogic.flipcheck();
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
							
							}
						}
						
						// If in check ..
						if(gamelogic.checkstatus()==true){
							// Do move
							Piece[][] oldstate = new Piece[8][8];
							for(int x=0;x < 8; x++){
								for(int y=0; y < 8; y++){
									oldstate[x][y] = boardstate[x][y];
								}
							}
							boardstate = selectedpiece.movepawn(selectedpiece, targetpiece, boardstate);
							// Check if still in check, if not, do move
							if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
								chessboard.setBoard(boardstate);
								chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
								chessboard.changeplayer();
								chessboard.changeclicknull();
								stalecountwhite=8;
								stalecountblack=8;
								// If still in check, undo move
							}
							else{
							chessboard.changeclicknull();
							chessboard.setBoard(oldstate);
							gamelogic.flipcheck();
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
							}
						}
						
						}
						else{
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
						}
					}
				
					// If bishop selected ..
					if(selectedpiece.toString().equals("Bishop") && selectedpiece != null && targetpiece != null && selectedpiece.equals(targetpiece)==false){
						if(chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.CORNFLOWERBLUE)==true || chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.AQUAMARINE)==true){
							// If check is false
							if(gamelogic.checkstatus()==false){
								Piece[][] oldstate = new Piece[8][8];
								// Transfer pieces to backup variable
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								// Do move
								boardstate = selectedpiece.movebishop(selectedpiece, targetpiece, boardstate);
								// If move results in no check, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
								chessboard.setBoard(boardstate);
								chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
								chessboard.changeplayer();
								chessboard.changeclicknull();
								stalecountwhite=8;
								stalecountblack=8;
								}
								// If check, reverse move
								else{
								chessboard.changeclicknull();
								chessboard.setBoard(oldstate);
								gamelogic.flipcheck();
								
								// Stalemate check
								if(selectedpiece.type()==1){
									stalecountwhite--;
									}
									if(selectedpiece.type()==2){
									stalecountblack--;	
									}
									if(stalecountwhite==0 || stalecountblack==0){
										System.out.println("STALEMATE!");
										stale=true;
									}
								}
							}
							
							// If in check ..
							if(gamelogic.checkstatus()==true){
								// Do move
								Piece[][] oldstate = new Piece[8][8];
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								boardstate = selectedpiece.movebishop(selectedpiece, targetpiece, boardstate);
								// Check if still in check, if not, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
									chessboard.setBoard(boardstate);
									chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
									chessboard.changeplayer();
									chessboard.changeclicknull();
									stalecountwhite=8;
									stalecountblack=8;
									// If still in check, undo move
								}
								else{
									chessboard.changeclicknull();
									chessboard.setBoard(oldstate);
									gamelogic.flipcheck();
									
									// Stalemate check
									if(selectedpiece.type()==1){
										stalecountwhite--;
										}
										if(selectedpiece.type()==2){
										stalecountblack--;	
										}
										if(stalecountwhite==0 || stalecountblack==0){
											System.out.println("STALEMATE!");
											stale=true;
										}
								}
							}
						}
						else{
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
						}
					}
				
						// If queen selected ..
					if(selectedpiece.toString().equals("Queen") && selectedpiece != null && targetpiece != null && selectedpiece.equals(targetpiece)==false){
						if(chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.CORNFLOWERBLUE)==true || chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.AQUAMARINE)==true){
							// If check is false
							if(gamelogic.checkstatus()==false){
								Piece[][] oldstate = new Piece[8][8];
								// Transfer pieces to backup variable
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								// Do move
								boardstate = selectedpiece.movequeen(selectedpiece, targetpiece, boardstate);
								// If move results in no check, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
								chessboard.setBoard(boardstate);
								chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
								chessboard.changeplayer();
								chessboard.changeclicknull();
								stalecountwhite=8;
								stalecountblack=8;
								}
								// If check, reverse move
								else{
								chessboard.changeclicknull();
								chessboard.setBoard(oldstate);
								gamelogic.flipcheck();
								if(selectedpiece.type()==1){
									stalecountwhite--;
									}
									if(selectedpiece.type()==2){
									stalecountblack--;	
									}
									if(stalecountwhite==0 || stalecountblack==0){
										System.out.println("STALEMATE!");
										stale=true;
									}
								}
							}
							
							// If in check ..
							if(gamelogic.checkstatus()==true){
								// Do move
								Piece[][] oldstate = new Piece[8][8];
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								boardstate = selectedpiece.movequeen(selectedpiece, targetpiece, boardstate);
								// Check if still in check, if not, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
									chessboard.setBoard(boardstate);
									chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
									chessboard.changeplayer();
									chessboard.changeclicknull();
									stalecountwhite=8;
									stalecountblack=8;
									// If still in check, undo move
								}
								else{
									chessboard.changeclicknull();
									chessboard.setBoard(oldstate);
									gamelogic.flipcheck();
									
									// Stalemate check
									if(selectedpiece.type()==1){
										stalecountwhite--;
										}
										if(selectedpiece.type()==2){
										stalecountblack--;	
										}
									}
									if(stalecountwhite==0 || stalecountblack==0){
									System.out.println("STALEMATE!");
									stale=true;
									}
								}
						}
						else{
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
						}
					}
				
					// If rook selected ..
					if(selectedpiece.toString().equals("Rook") && selectedpiece != null && targetpiece != null && selectedpiece.equals(targetpiece)==false){
						if(chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.CORNFLOWERBLUE)==true || chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.AQUAMARINE)==true){
							// If check is false
							if(gamelogic.checkstatus()==false){
								Piece[][] oldstate = new Piece[8][8];
								// Transfer pieces to backup variable
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								// Do move
								boardstate = selectedpiece.moverook(selectedpiece, targetpiece, boardstate);
								// If move results in no check, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
								chessboard.setBoard(boardstate);
								chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
								chessboard.changeplayer();
								chessboard.changeclicknull();
								stalecountwhite=8;
								stalecountblack=8;
								}
								// If check, reverse move
								else{
								chessboard.changeclicknull();
								chessboard.setBoard(oldstate);
								gamelogic.flipcheck();
								
								// Stalemate check
								if(selectedpiece.type()==1){
									stalecountwhite--;
								}
									if(selectedpiece.type()==2){
									stalecountblack--;	
									}
									if(stalecountwhite==0 || stalecountblack==0){
										System.out.println("STALEMATE!");
										stale=true;
									}
								}
							}
							
							// If in check ..
							if(gamelogic.checkstatus()==true){
								// Do move
								Piece[][] oldstate = new Piece[8][8];
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								boardstate = selectedpiece.moverook(selectedpiece, targetpiece, boardstate);
								// Check if still in check, if not, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
									chessboard.setBoard(boardstate);
									chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
									chessboard.changeplayer();
									chessboard.changeclicknull();
									stalecountwhite=8;
									stalecountblack=8;
									// If still in check, undo move
								}
								else{
									chessboard.changeclicknull();
									chessboard.setBoard(oldstate);
									gamelogic.flipcheck();
									
									// Stalemate check
									if(selectedpiece.type()==1){
										stalecountwhite--;
										}
										if(selectedpiece.type()==2){
										stalecountblack--;	
										}
										if(stalecountwhite==0 || stalecountblack==0){
											System.out.println("STALEMATE!");
											stale=true;
										}
									}
							}	
						}
						else{
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
						}
					}
				
					// If king selected ..
					if(selectedpiece.toString().equals("King") && selectedpiece != null && targetpiece != null && selectedpiece.equals(targetpiece)==false){
						if(chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.CORNFLOWERBLUE)==true || chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.AQUAMARINE)==true){
							// If check is false
							if(gamelogic.checkstatus()==false){
								Piece[][] oldstate = new Piece[8][8];
								// Transfer pieces to backup variable
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								// Do move
								boardstate = selectedpiece.moveking(selectedpiece, targetpiece, boardstate);
								// If move results in no check, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
								chessboard.setBoard(boardstate);
								chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
								chessboard.changeplayer();
								chessboard.changeclicknull();
								stalecountwhite=8;
								stalecountblack=8;
								}
								// If check, reverse move
								else{
								chessboard.changeclicknull();
								chessboard.setBoard(oldstate);
								gamelogic.flipcheck();
								
								// Stalemate check
								if(selectedpiece.type()==1){
									stalecountwhite--;
									}
									if(selectedpiece.type()==2){
									stalecountblack--;	
									}
									if(stalecountwhite==0 || stalecountblack==0){
										System.out.println("STALEMATE!");
										stale=true;
									}
								}
							}
							
							// If in check ..
							if(gamelogic.checkstatus()==true){
								// Do move
								Piece[][] oldstate = new Piece[8][8];
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								boardstate = selectedpiece.moveking(selectedpiece, targetpiece, boardstate);
								// Check if still in check, if not, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
									chessboard.setBoard(boardstate);
									chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
									chessboard.changeplayer();
									chessboard.changeclicknull();
									stalecountwhite=8;
									stalecountblack=8;
									// If still in check, undo move
								}
								else{
									chessboard.changeclicknull();
									chessboard.setBoard(oldstate);
									gamelogic.flipcheck();
									
									// Stalemate check
									if(selectedpiece.type()==1){
										stalecountwhite--;
										}
										if(selectedpiece.type()==2){
										stalecountblack--;	
										}
										if(stalecountwhite==0 || stalecountblack==0){
											System.out.println("STALEMATE!");
											stale=true;
										}
								}
							}
						}
						else{
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
						}
					}
				
					// If knight selected ..
					if(selectedpiece.toString().equals("Knight") && selectedpiece != null && targetpiece != null && selectedpiece.equals(targetpiece)==false){
						if(chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.CORNFLOWERBLUE)==true || chessboard.getStroke(targetpiece.icoord(), targetpiece.jcoord(), Color.AQUAMARINE)==true){
							// If check is false
							if(gamelogic.checkstatus()==false){
								Piece[][] oldstate = new Piece[8][8];
								// Transfer pieces to backup variable
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								// Do move
								boardstate = selectedpiece.moveknight(selectedpiece, targetpiece, boardstate);
								// If move results in no check, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
								chessboard.setBoard(boardstate);
								chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
								chessboard.changeplayer();
								chessboard.changeclicknull();
								stalecountwhite=8;
								stalecountblack=8;
								}
								// If check, reverse move
								else{
								chessboard.changeclicknull();
								chessboard.setBoard(oldstate);
								gamelogic.flipcheck();
								
								// Stalemate check
								if(selectedpiece.type()==1){
									stalecountwhite--;
									}
									if(selectedpiece.type()==2){
									stalecountblack--;	
									}
									if(stalecountwhite==0 || stalecountblack==0){
										System.out.println("STALEMATE!");
										stale=true;
									}
								}
							}
							
							// If in check ..
							if(gamelogic.checkstatus()==true){
								// Do move
								Piece[][] oldstate = new Piece[8][8];
								for(int x=0;x < 8; x++){
									for(int y=0; y < 8; y++){
										oldstate[x][y] = boardstate[x][y];
									}
								}
								boardstate = selectedpiece.moveknight(selectedpiece, targetpiece, boardstate);
								// Check if still in check, if not, do move
								if(gamelogic.check4check(chessboard.otherplayer(), boardstate)==false){
									chessboard.setBoard(boardstate);
									chessboard.drawmove(selectedpiece.icoord(), selectedpiece.jcoord(), targetpiece.icoord(), targetpiece.jcoord());
									chessboard.changeplayer();
									chessboard.changeclicknull();
									stalecountwhite=8;
									stalecountblack=8;
									// If still in check, undo move
								}
								else{
									chessboard.changeclicknull();
									chessboard.setBoard(oldstate);
									gamelogic.flipcheck();
									
									// Stalemate check
									if(selectedpiece.type()==1){
										stalecountwhite--;
										}
										if(selectedpiece.type()==2){
										stalecountblack--;	
										}
										if(stalecountwhite==0 || stalecountblack==0){
											System.out.println("STALEMATE!");
											stale=true;
										}
								}
							}
						}
						else{
							
							// Stalemate check
							if(selectedpiece.type()==1){
							stalecountwhite--;
							}
							if(selectedpiece.type()==2){
							stalecountblack--;	
							}
							if(stalecountwhite==0 || stalecountblack==0){
								System.out.println("STALEMATE!");
								stale=true;
							}
						}
					}
					
					// If screw up ..
					else{
					chessboard.changeclicknull();
					chessboard.clearhighlights();
					}
					
					chessboard.clearhighlights();
					chessboard.changeclicknull();
					
					// Check for checkmate ..
					if(gamelogic.check4checkmate(chessboard.otherplayer(), chessboard.getState())=="true"){
						winner=true;
					}
					
					getScene().setCursor(Cursor.DEFAULT);
					
					// highlight check..
					
					if(gamelogic.checkstatus()==true){
					chessboard.checkhighlight(gamelogic.checki(), gamelogic.checkj());
					if(winner!=true){
					System.out.println("CHECK!");}
					chessboard.changeclicknull();
					}
				}
				
				// First click
				if(chessboard.clicklogic() == "false" && stale==false && winner==false){
					
				selectedpiece = chessboard.selectPiece(hash);
				
				if(selectedpiece.toString().equals("Empty") || chessboard.pieceselect()==false){
					junkselection=true;
				}
				else{junkselection=false;}
				
				if(!selectedpiece.equals("Empty") && junkselection!=true){
				getScene().setCursor(new ImageCursor(selectedpiece.image()));
				chessboard.changeclicktrue();
				// Highlights valid moves..
				chessboard.validMoves(selectedpiece);}
				
				// Check 4 check ..
				if(gamelogic.checkstatus()!=true){
				gamelogic.check4check(chessboard.otherplayer(), chessboard.getState());}
				}
				
				if(chessboard.clicklogic()=="true"){
				System.out.print("Whitepieces: " + whitepieces(chessboard.getState()) + " Blackpieces: " + blackpieces(chessboard.getState()));
				System.out.println("\n====================");}
				
				// If completed move, return to first click ..
				if(chessboard.clicklogic() == "null"){
					chessboard.changeclickfalse();
				}
			}	
		});
	}
	
	public void highlightcheck(int x, int y){
		chessboard.checkhighlight(x,y);
	}
	
	// Piece counting -- could expand on this but only need total number
	public int whitepieces(Piece[][] boardstate){
		int whitepieces=0;
		
		// Count white pieces
		for(int x=0; x < 8; x++){
			for(int y=0; y < 8; y++){
				if(boardstate[x][y].type()==1){
					whitepieces++;
				}
			}
		}
		// Return int
		return whitepieces;
	}
	
	public int blackpieces(Piece[][] boardstate){
		int blackpieces=0;
		
		// Count white pieces
		for(int x=0; x < 8; x++){
			for(int y=0; y < 8; y++){
				if(boardstate[x][y].type()==2){
					blackpieces++;
				}
			}
		}
		// Return int
		return blackpieces;
	}
	
	public void blacktimeout(){
		winner=true;
		System.out.println("\nBlack timeout: White wins!");
	}
	
	public void whitetimeout(){
		winner=true;
		System.out.println("\nWhite timeout: Black wins!");
	}
	
	@Override
	public void resize(double width, double height){
		super.resize(width, height);
		chessboard.resize(width, height);
	}
	
	@Override
	public void relocate(double x, double y){
		super.relocate(x, y);
		pos.setX(x);
		pos.setY(x);
	}
}
