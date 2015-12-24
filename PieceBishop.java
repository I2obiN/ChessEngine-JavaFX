import javafx.scene.image.Image;

public class PieceBishop extends Piece{
	public String name = "Bishop";
	public String imgname = "bishop.png";
	private Piece[][] boardstate;
	private int i;
	private int j;
	private int te;

	public PieceBishop(int type, int ii, int jj) {
		super(type);
		te=type;
		i = ii;
		j = jj;
	}
	
	@Override
	public Image image(){
	 if(te==1)
	 return new Image("whitebishopcursor.png");
	 else
	 return new Image("blackbishopcursor.png");
	}
	
	@Override
	public int icoord(){
		return i;
	}
	
	@Override
	public int jcoord(){
		return j;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public String imagefilename(){
		return imgname;
	}
	
	public Piece[][] movebishop(Piece b, Piece t, Piece[][] bs){
		boardstate = bs;
		// Move pawn
		boardstate[t.icoord()][t.jcoord()] = new PieceBishop(b.type(), t.icoord(), t.jcoord());
		boardstate[b.icoord()][b.jcoord()] = new Empty(0, b.icoord(), b.jcoord());
		// Return the new board
		return boardstate;
	}

}
