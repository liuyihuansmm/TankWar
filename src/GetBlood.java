import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;

public class GetBlood {
	
	public static final int BLOOD_WIDTH = 36;
	
	public static final int BLOOD_HEIGHT = 36;
	
	private int x,y;
	
	private TankClient tc;
	
	private boolean live = false;
	
	private int step = 0;
	
	private Random r = new Random();
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] bloodImages = null;
	
	static{
		bloodImages = new Image[]{tk.getImage(GetBlood.class.getResource("Images/hp.png"))};
	}
	
	private int[][] poition = { { 155, 196 }, { 500, 58 }, { 80, 340 },
			{ 99, 199 }, { 345, 456 }, { 123, 321 }, { 258, 413 } };
	
	public GetBlood(){}
	
	public GetBlood(int _x,int _y,TankClient _tc){
		this.x = _x;
		this.y = _y;
		this.tc = _tc;
	}
	
	public void draw(Graphics g){
		//出现的概率
		if(r.nextInt(100)>98){
			this.live = true;
			move();
		}
		if(!live)
			return;
		
		g.drawImage(bloodImages[0], x, y, null);
	}
	
	public void move(){
		step++;
		if(step == poition.length){
			step = 0;
		}
		x = poition[step][0];
		y = poition[step][1];
	}
	
	
	public Rectangle getRect(){
		return new Rectangle(x,y,BLOOD_WIDTH,BLOOD_HEIGHT);
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}


}
