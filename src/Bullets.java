import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;

/**
 * 1.坐标、长宽、速度、敌我子弹
 * 2.子弹是否有效
 * 3.移动
 * 4.打到到家、打到墙、打到坦克时候
 * @author LYH
 *
 */
public class Bullets {
	
	public static final int SPEEDX = 10,SPEEDY = 10;
	
	public static final int BULLET_WIDTH = 10,BULLET_HEIGHT = 10;
	
	private int x,y;
	
	private Direction direction;
	
	private TankClient tc;
	
	private boolean live = true;
	
	private boolean good;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] bulletImages = null;
	
	static{
		bulletImages = new Image[]{
				tk.getImage(Bullets.class.getResource("images/bulletU.gif")),
				tk.getImage(Bullets.class.getResource("images/bulletD.gif")),
				tk.getImage(Bullets.class.getResource("images/bulletL.gif")),
				tk.getImage(Bullets.class.getResource("images/bulletR.gif"))	
		};
	}
	
	public Bullets(int _x,int _y,Direction _dir){
		this.x = _x;
		this.y = _y;
		this.direction = _dir;
	}
	
	public Bullets(int _x,int _y,Direction _dir,TankClient _tc,boolean _good){
		this(_x,_y,_dir);
		this.tc = _tc;
		this.good = _good;
	}
	
	public void move(){
		
		switch(direction){
		case U:
			y-=SPEEDY;
			break;
		case D:
			y+=SPEEDY;
			break;
		case L:
			x-=SPEEDX;
			break;
		case R:
			x+=SPEEDX;
			break;
		case STOP:
			break;
		}
		
		if(x<0 || y<0 || x+SPEEDX>tc.FRAME_WIDTH || y+SPEEDY>tc.FRAME_HEIGHT){
			this.live = false;
		}
	}
	
	public void draw(Graphics g){
		if(!live){
			this.tc.bullets.remove(this);
			return;
		}
		
		switch(direction){
		case U:
			g.drawImage(bulletImages[0], x, y, null);
			break;
		case D:
			g.drawImage(bulletImages[1], x, y, null);
			break;
		case L:
			g.drawImage(bulletImages[2], x, y, null);
			break;
		case R:
			g.drawImage(bulletImages[3], x, y, null);
			break;
		}
		move();
	}
	
	public boolean isLive(){
		return live;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,BULLET_WIDTH,BULLET_HEIGHT);
	}
	
	/**
	 * 子弹碰到各种物体的逻辑处理
	 */
	//子弹碰到家
	public boolean hitHome(){
		if(this.live && this.getRect().intersects(tc.home.getRect())){
			this.live = false;
			tc.home.setLive(false);
			return true;
		}
		return false;
	}
	
	//子弹碰到普通墙
	public boolean hitWall(CommonWall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			this.live = false;
			this.tc.commonwall.remove(w);
			this.tc.homewall.remove(w);
			return true;
		}
		
		return false;
	}
	
	//子弹碰到金属墙
	public boolean hitWall(MetaWall w){
		if(this.live && this.getRect().intersects(w.getRect())){
			this.live = false;
			//讲道理的话,金属墙应该会由高级子弹打穿，现不考虑
			//this.tc.metawall.remove(w);
			return true;
		}
		
		return false;
	}
	
	//子弹撞到坦克，分敌我
	public boolean hitTank(Tank t){
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() &&
				this.good != t.isGood()){
			BombTank bTank = new BombTank(t.getX(), t.getY(), tc);
			tc.bombTanks.add(bTank);		
			if(t.isGood()){
				t.setLife(t.getLife() - 50);
				if(t.getLife()<=0){
					t.setLive(false);
				}
			}else{
				t.setLive(false);
			}	
			this.live = false;
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			if(hitTank(tanks.get(i)))
				return true;
		}
		return false;
	}
}
