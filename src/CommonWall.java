import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * 普通墙体
 * @author LYH
 *
 */
public class CommonWall {
	
	public static final int CWALL_WIDTH = 20;
	
	public static final int CWALL_HEIGHT = 20;
	
	private int x,y;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] imageCwalls = null;
	
	static {
		imageCwalls = new Image[]{tk.getImage(CommonWall.class.getResource("Images/commonWall.gif"))};
	}
	
	public CommonWall(int _x, int _y ,TankClient _tc){
		this.x = _x;
		this.y = _y;
		this.tc = _tc;
	}
	
	public void draw(Graphics g){
		g.drawImage(imageCwalls[0], x, y, null);
	}
	
	/**
	 * 此函数是判断玩家坦克是否与之相交
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, CWALL_WIDTH, CWALL_HEIGHT);
	}
}
