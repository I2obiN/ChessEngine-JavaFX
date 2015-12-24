import javafx.scene.image.Image;

public class PieceRook extends Piece {
	public String name = "Rook";
	public String imgname = "rook.png";
	private Piece[][] boardstate;
	private int i;
	private int j;
	private int te;

	public PieceRook(int type, int ii, int jj) {
		super(type);
		te=type;
		i = ii;
		j = jj;
	}
	
	@Override
	public Image image(){
	 if(te==1)
	 return new Image("whiterookcursor.png");
	 else
	 return new Image("blackrookcursor.png");
	}
	
	@Override
	public int icoord(){
		if(i > 8){
		return 7;}
		
		if(i < 0){
		return 0;}
		
		else{return i;}
	}
	
	@Override
	public int jcoord(){
		if(j > 8){
		return 7;}
		
		if(j < 0){
		return 0;}
		
		else{return j;}
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public String imagefilename(){
		return imgname;
	}
	
	public Piece[][] moverook(Piece r, Piece t, Piece[][] bs){
		boardstate = bs;
		// Move pawn
		boardstate[t.icoord()][t.jcoord()] = new PieceRook(r.type(), t.icoord(), t.jcoord());
		boardstate[r.icoord()][r.jcoord()] = new Empty(0, r.icoord(), r.jcoord());
		// Return the new board
		return boardstate;
	}
}
