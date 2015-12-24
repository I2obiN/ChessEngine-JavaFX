import javafx.scene.image.Image;

public class PieceKnight extends Piece{
	public String name = "Knight";
	public String imgname = "knight.png";
	private Piece[][] boardstate;
	private int i;
	private int j;
	private int te;

	public PieceKnight(int type, int ii, int jj) {
		super(type);
		te=type;
		i = ii;
		j = jj;
	}
	
	@Override
	public Image image(){
	 if(te==1)
	 return new Image("whiteknightcursor.png");
	 else
	 return new Image("blackknightcursor.png");
	}
	
	@Override
	public int icoord(){
		// Bounds correction
		if(i > 8){
		return 7;}
		
		if(i < 0){
		return 0;}
		
		else{return i;}
	}
	
	@Override
	public int jcoord(){
		// Bounds correction
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
	
	public Piece[][] moveknight(Piece kn, Piece t, Piece[][] bs){
		boardstate = bs;
		// Move pawn
		boardstate[t.icoord()][t.jcoord()] = new PieceKnight(kn.type(), t.icoord(), t.jcoord());
		boardstate[kn.icoord()][kn.jcoord()] = new Empty(0, kn.icoord(), kn.jcoord());
		// Return the new board
		return boardstate;
	}
}
