import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Home {
	
	public static final int HOME_WIDTH = 30;
	
	public static final int HOME_HEIGHT = 30;
	
	private int x,y;
	
	private boolean live = true;
	
	private TankClient tc;
	
	private static Toolkit tk =  Toolkit.getDefaultToolkit();
	
	private static Image[] homeImages;
	
	static{
		homeImages = new Image[]{tk.getImage(Home.class.getResource("Images/home.jpg"))};
	}
	
	public Home(int _x,int _y, TankClient _tc){
		this.x = _x;
		this.y = _y;
		this.tc = _tc;
	}
	
	
	public void draw(Graphics g){
		Color c = g.getColor();
		if(this.live){
			
		}else {
			gameOver(g);
		}
		g.setColor(c);
	}
	
	public void gameOver(Graphics g){
		g.drawImage(homeImages[0], x, y, null);
		//================家周围的墙========待续============
	}
	
	public boolean isLive() { // 判读是否还活着
		return live;
	}

	public void setLive(boolean live) { // 设置生命
		this.live = live;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, HOME_WIDTH, HOME_HEIGHT);
	}
}
