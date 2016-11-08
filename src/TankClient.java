import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主界面
 * @author LYH
 *
 */
public class TankClient extends Frame{
	
	/**
	 * 游戏界面宽度
	 */
	public static final int FRAME_WIDTH = 800;
	
	/**
	 * 游戏界面高度
	 */
	public static final int FRAME_HEIGHT =600;
	
	private boolean paintAble = true;
	
	Image screenImage = null;
	
	/**
	 * 游戏框上防的菜单选项
	 */
	MenuBar menuBar = null;
	
	Menu menu1 = null,menu2 = null,menu3 = null,menu4 = null;
	
	MenuItem menuItme1 = null,menuItme2 = null,menuItme3 = null,menuItme4 = null,
			 menuItme5 = null,menuItme6 = null,menuItme7 = null,menuItme8 = null,
			 menuItme9 = null;
	/**
	 * 游戏中各种组成元素的集合
	 * 1个玩家坦克
	 * 1个家
	 * 1个血条
	 * 多个河流
	 * 多个树木
	 * 多个敌方坦克
	 * 家周围的墙(普通墙)
	 * 多个普通墙
	 * 多个金属墙
	 * 多个子弹
	 * 多个爆炸
	 */
	Home home = new Home(373, 545, this);
	Tank aTank = new Tank(300, 560, true, this, Direction.STOP);
	GetBlood blood = new GetBlood();
	
	List<River> riverList = new ArrayList<River>();
	List<Tree> 	treeList = new ArrayList<Tree>();
	List<Tank>	eTanks = new ArrayList<Tank>();
	List<CommonWall> homewall = new ArrayList<CommonWall>();
	List<CommonWall> commonwall = new ArrayList<CommonWall>();
	List<MetaWall> metawall = new ArrayList<MetaWall>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	
	List<BombTank> bombTanks = new ArrayList<BombTank>();
	
	public void update(Graphics g){	
		screenImage = this.createImage(FRAME_WIDTH, FRAME_HEIGHT);
		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.GRAY);
		gps.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);	
		gps.setColor(c);
		drawFrame(g);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void drawFrame(Graphics g){
		Color c = g.getColor();
		Font f1 = g.getFont();
		
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("敌方剩余坦克:", 200, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString(String.valueOf(eTanks.size()), 400, 70);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("生命值:", 500, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString(String.valueOf(aTank.getLife()), 650, 70);
		
		if(eTanks.size() == 0 && home.isLive() && aTank.isLive()){
			g.setFont(new Font("TimesRoman", Font.BOLD, 60));
			//这里没懂！！！
			this.commonwall.clear();
			g.drawString("你赢了！ ", 310, 300);
		}
		
		if(!aTank.isLive()){
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			this.eTanks.clear();
			this.bullets.clear();
			g.drawString("别灰心，再来一局！ ", 310, 300);
		}
		
		/**
		 * 画出河流
		 */
		for(int i=0; i<riverList.size(); i++){
			River rTemp = riverList.get(i);
			rTemp.draw(g);
		}
		
		/**
		 * 画出玩家坦克和家、血条
		 */
		aTank.draw(g);
		home.draw(g);
		blood.draw(g);
		
		//遍历子弹
		for(int i=0; i<bullets.size(); i++){
			Bullets b = bullets.get(i);
			b.hitHome();
			b.hitTanks(eTanks);
			b.hitTank(aTank);
			
			for(int j=0;j<commonwall.size();j++){
				b.hitWall(commonwall.get(j));
			}
			
			for(int j=0;j<metawall.size();j++){
				b.hitWall(metawall.get(j));
			}
			
			for(int j=0;j<homewall.size();j++){
				b.hitWall(homewall.get(j));
			}
			b.draw(g);
		}
		
		//遍历敌方坦克
		for(int i=0; i<eTanks.size(); i++){
			Tank tTemp = eTanks.get(i);
			
			for(int j=0; j<riverList.size(); j++){
				tTemp.collideWithRiver(riverList.get(j));
			}
			
			for(int j=0; j<commonwall.size(); j++){
				tTemp.collideWithWall(commonwall.get(j));
			}
			
			for(int j=0; j<homewall.size(); j++){
				tTemp.collideWithWall(homewall.get(j));
			}
			
			for(int j=0; j<metawall.size(); j++){
				tTemp.collideWithMetaWall(metawall.get(j));
			}
			
			tTemp.colldeTank(eTanks);
			tTemp.collideHome(home);
			
			tTemp.draw(g);
		}
		
		//画出树木
		for(int i=0; i<treeList.size();i++)
			treeList.get(i).draw(g);
		
		//爆炸效果
		for(int i=0; i<bombTanks.size(); i++){
			bombTanks.get(i).draw(g);
		}
		
		//普通墙
		for(int i=0; i<commonwall.size(); i++){
			commonwall.get(i).draw(g);
		}
		
		//金属墙
		for(int i=0; i<metawall.size(); i++){
			metawall.get(i).draw(g);
		}
		
		//玩家的坦克
		aTank.collideHome(home);
		aTank.colldeTank(eTanks);
		for(int i=0; i<commonwall.size(); i++){
			aTank.collideWithWall(commonwall.get(i));
			commonwall.get(i).draw(g);
		}
		for(int i=0; i<metawall.size(); i++){
			aTank.collideWithMetaWall(metawall.get(i));
			metawall.get(i).draw(g);
		}
		for(int i=0; i<homewall.size(); i++){
			aTank.collideWithWall(homewall.get(i));
			homewall.get(i).draw(g);
		}
		for(int i=0; i<riverList.size(); i++){
			aTank.collideWithRiver(riverList.get(i));
			riverList.get(i).draw(g);
		}
		
		g.setFont(f1);	
		g.setColor(c);
	}
	
	public TankClient(){
		
		menuBar = new MenuBar();
		
		menu1 = new Menu("游戏");
		menu2 = new Menu("暂停/继续");
		menu3 = new Menu("帮助");
		menu4 = new Menu("游戏级别");
		menu1.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		menu2.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		menu3.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		menu4.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		
		menuItme1 = new MenuItem("开始新游戏");
		menuItme2 = new MenuItem("退出");
		menuItme3 = new MenuItem("暂停");
		menuItme4 = new MenuItem("继续");
		menuItme5 = new MenuItem("游戏说明");
		menuItme6 = new MenuItem("级别1");
		menuItme7 = new MenuItem("级别2");
		menuItme8 = new MenuItem("级别3");
		menuItme9 = new MenuItem("级别4");
		
		menu1.add(menuItme1);
		menu1.add(menuItme2);
		
		menu2.add(menuItme3);
		menu2.add(menuItme4);
		
		menu3.add(menuItme5);
		
		menu4.add(menuItme6);
		menu4.add(menuItme7);
		menu4.add(menuItme8);
		menu4.add(menuItme9);
		
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);
		
		/**
		 * 菜单监听处理
		 */
		
		
		this.setMenuBar(menuBar);
		
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("坦克大战");
		this.setVisible(true);
		
		this.setResizable(false);
		this.setBackground(Color.green);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		new Thread(new PaintThread()).start();
	}
	
	private class PaintThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(paintAble){
				repaint();			
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public static void main(String[] args){
		new TankClient();
	}
}
