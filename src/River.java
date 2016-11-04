import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class River {
	
	/**
	 * 河流模块宽度
	 */
	public static final int RIVER_WIDTH = 40;
	
	/**
	 * 河流模块高度
	 */
	public static final int RIVER_HEIGHT = 100;
	
	/**
	 * 河流出现在游戏界面的横坐标
	 */
	private int x;
	
	/**
	 * 河流出现在游戏界面的纵坐标
	 */
	private int y;
	
	/**
	 * 河流出现的界面
	 */
	private TankClient tc;
	
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private static Image[] riverImages = null;
	
	/**
	 * 初始化河流模块图片
	 */
	static{
		
		riverImages = new Image[]{toolkit.getImage(River.class.getResource("Images/river.jpg"))};
	}
	
	public River(int _x,int _y,TankClient _tc){
		this.x = _x;
		this.y = _y;
		this.tc = _tc;
	}
	
	/**
	 * 在游戏界面TankClient的x,y位置上画出图片
	 * @param g
	 */
	public void draw(Graphics g){
		g.drawImage(riverImages[0], x, y, null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public TankClient getTc() {
		return tc;
	}

	public void setTc(TankClient tc) {
		this.tc = tc;
	}
	
	
	
	
}
