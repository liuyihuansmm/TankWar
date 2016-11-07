import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 特殊墙体
 * @author LYH
 *
 */
public class MetaWall {
	
	public static final int WALL_WIDTH = 30;
	
	public static final int WALL_HEIGHT = 30;
	
	private int x,y;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] wallImages = null;
	
	static {
		wallImages = new Image[]{tk.getImage(MetaWall.class.getResource("Images/metalWall.gif"))};
	}
	
	public MetaWall(int _x,int _y,TankClient _tc){
		this.x = _x;
		this.y = _y;
		this.tc = _tc;
	}
	
	public void draw(Graphics g){
		g.drawImage(wallImages[0], x, y, null);
	}
	
	/**
	 * 此函数是判断玩家坦克是否与之相交
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WALL_WIDTH,WALL_HEIGHT);
	}
}
