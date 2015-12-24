import javafx.scene.image.Image;

public class PieceQueen extends Piece {
	public String name = "Queen";
	public String imgname = "queen.png";
	private Piece[][] boardstate;
	private int i;
	private int j;
	private int te;

	public PieceQueen(int type, int ii, int jj) {
		super(type);
		te=type;
		i = ii;
		j = jj;
	}
	
	@Override
	public Image image(){
	 if(te==1)
	 return new Image("whitequeencursor.png");
	 else
	 return new Image("blackqueencursor.png");
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
	
	public Piece[][] movequeen(Piece q, Piece t, Piece[][] bs){
		boardstate = bs;
		// Move pawn
		boardstate[t.icoord()][t.jcoord()] = new PieceQueen(q.type(), t.icoord(), t.jcoord());
		boardstate[q.icoord()][q.jcoord()] = new Empty(0, q.icoord(), q.jcoord());
		// Return the new board
		return boardstate;
	}
}
