import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.List;

public class Tank {
	
	public static final int SPEEDX = 6,SPEEDY = 6;
	
	public static final int TANK_WIDTH = 35,TANK_HEIGHT = 35;
	
	private Direction direction =  Direction.STOP; //初始状态
	
	private Direction kDirection = Direction.U;	   //起始方向
	
	private TankClient tc;
	
	private boolean live = true;	//初始为活着
	
	private int life = 200;		//初始生命值
	
	private boolean good;		//是否敌我坦克
		
	private int x,y,oldX,oldY;	//现在的坐标x,y和上一步坐标oldX,oldY
	
	private boolean bU = false,bD = false,bL = false,bR = false; //保存方向的变量
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] tankImages = null;
	
	static {
		tankImages =  new Image[]{
				tk.getImage(Tank.class.getResource("Images/tankD.gif")),
				tk.getImage(Tank.class.getResource("Images/tankU.gif")),
				tk.getImage(Tank.class.getResource("Images/tankL.gif")),
				tk.getImage(Tank.class.getResource("Images/tankR.gif"))
		};
	}
	
	public Tank(int _x,int _y, boolean _good){
		this.x = _x;
		this.y = _y;
		this.good = _good;
		this.oldX = _x;
		this.oldY = _y;
	}
	
	public Tank(int _x,int _y, boolean _good,TankClient _tc,Direction _dir){
		this(_x,_y,_good);
		this.tc = _tc;
		this.direction = _dir;
	}
	
	/**
	 * 画出逻辑
	 * @param g
	 */
	public void draw(Graphics g){
		//如果没活
		if(!live){
			//且是敌军
			if(!good){
				//直接从TankClient移除
//============================代码还没实现
			}
		}
		
		//如果是友军坦克则显示血条
		if(good){
			new DrawBloodbBar().draw(g);
		}
		
		//画出对应方向的坦克
		switch(kDirection){	
		case D:
			g.drawImage(tankImages[0], x, y, null);
			break;
		case U:
			g.drawImage(tankImages[1], x, y, null);
			break;
		case L:
			g.drawImage(tankImages[2], x, y, null);
			break;
		case R:
			g.drawImage(tankImages[3], x, y, null);
			break;
		}
		move();
	}
	
	public void move(){
		
		//移动之期先保存现在的坐标
		oldX = x;
		oldY = y;
		
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
		}
		
		if(direction != Direction.STOP){
			kDirection = direction;
		}
		
		//超出主界面框时候的处理
		if(x<0)
			x = 0;
		if(y<40)
			y = 40;
		if(x+TANK_WIDTH>TankClient.FRAME_WIDTH)
			x = TankClient.FRAME_WIDTH - TANK_WIDTH;
		if(y+TANK_HEIGHT>TankClient.FRAME_HEIGHT)
			y = TankClient.FRAME_HEIGHT -TANK_HEIGHT;
		
		//如果是敌军,方向随机
		if(!good){
			
		}
	}
	
	public void changeToOldDir(){
		x = oldX;
		y = oldY;
	}
	
	/**
	 * 决定往哪个方向
	 */
	public void decideDircetion(){
		
		if(bU && !bD && !bR && !bL)
			direction = Direction.U;
		
		else if(!bU && bD && !bR && !bL)
			direction = Direction.D;
		
		else if(!bU && !bD && bR && !bL)
			direction = Direction.R;
		
		else if(!bU && !bD && !bR && bL)
			direction = Direction.L;
		
		else if(!bU && !bD && !bR && !bL)
			direction = Direction.STOP;
	}
	
	//按下键时，改变方向状态
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
		//按下R键重新开始游戏
		case KeyEvent.VK_R:
			//==========================逻辑待实现
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		}
		
		decideDircetion();
	}
	
	/**
	 * @topic	键盘释放后的状态
	 * @remark	一开始没明白为什么键盘释放也要改变状态，tank是按一下走一次，按一下走一次
	 * 	                       后来想明白按下时就把方向的状态确定，释放键盘后就重置以便后面改变方向;
	 * @param 	e
	 */
	public void keyRealsed(KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key){
		
		case KeyEvent.VK_UP:
			bU = false;
			break;
		
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		}
		
		decideDircetion();
	}
	
	/**
	 * 友军坦克的血条
	 */
	private class DrawBloodbBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, TANK_WIDTH, 10);
			int w = TANK_WIDTH*life/200;  //生命值百分比
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}
	
	/**
	 * @topic	撞墙(河流，墙)逻辑处理
	 * @param r
	 * @return
	 */
	//撞到河流
	public boolean collideWithRiver(River r){
		if(this.live && this.getRect().intersects(r.getRect())){
			changeToOldDir();
			return true;
		}
		return false;
	}
	
	//撞到普通墙
	public boolean collideWithWall(CommonWall r){
		if(this.live && this.getRect().intersects(r.getRect())){
			changeToOldDir();
			return true;
		}
		return false;
	}
	
	//撞到金属墙
	public boolean collideWithMetaWall(MetaWall r){
		if(this.live && this.getRect().intersects(r.getRect())){
			changeToOldDir();
			return true;
		}
		return false;
	}
	
	//撞到家
	//=================逻辑待处理
	public boolean collideHome(Home r){
		if(this.live && this.getRect().intersects(r.getRect())){
			changeToOldDir();
			return true;
		}
		return false;
	}
	
	//撞到Tank
	//=================逻辑待处理
	public boolean colldeTank(List<Tank> tanks){
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			if( this != t){
				if(t.live && this.live && this.getRect().intersects(t.getRect())){
					t.changeToOldDir();
					this.changeToOldDir();
					return true;
				}
			}
		}
		
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,TANK_WIDTH,TANK_WIDTH);
	}
	
	public boolean isLive(){
		return live;
	}
	
	public void setLive(boolean _live){
		this.live = _live;
	}
	
	public boolean isGood(){
		return good;
	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int _life) {
		this.life = _life;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
		
}
