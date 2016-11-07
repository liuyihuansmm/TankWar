import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Tree {
	
	public static final int TREE_WIDTH = 30;
	
	public static final int TREE_HEIGHT = 30;
	
	private int x,y;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] treeImages = null;
	
	static{
		treeImages = new Image[]{tk.getImage(Tree.class.getResource("Images/tree.gif"))};
	}
	
	public Tree(int _x,int _y, TankClient _tc) {
		this.x = _x;
		this.y = _y;
		this.tc = _tc;
	}
	
	public void draw(Graphics g){
		g.drawImage(treeImages[0], x, y, null);
	
	}
	
	/*
	public Rectangle getRect(){
		return new Rectangle(x,y,TREE_WIDTH,TREE_HEIGHT);
	}
	*/
}
