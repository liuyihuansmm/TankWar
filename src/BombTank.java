import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class BombTank {
	
	private int x,y;
	
	private boolean live = true;
	
	private TankClient tc;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	
	private static Image[] bombTankImages = null;
	
	int step = 0;
	
	//为什么要存这么多图片，一开始没想明白，后来想到是要模拟动态大小的爆炸图片
	static{
		bombTankImages = new Image[]{
				tk.getImage(BombTank.class.getResource("images/1.gif")),
				tk.getImage(BombTank.class.getResource("images/2.gif")),
				tk.getImage(BombTank.class.getResource("images/3.gif")),
				tk.getImage(BombTank.class.getResource("images/4.gif")),
				tk.getImage(BombTank.class.getResource("images/5.gif")),
				tk.getImage(BombTank.class.getResource("images/6.gif")),
				tk.getImage(BombTank.class.getResource("images/7.gif")),
				tk.getImage(BombTank.class.getResource("images/8.gif")),
				tk.getImage(BombTank.class.getResource("images/9.gif")),
				tk.getImage(BombTank.class.getResource("images/10.gif"))
		};
	}
	
	public BombTank(int _x,int _y,TankClient _tc){
		x = _x;
		y = _y;
		tc = _tc;
	}
	
	public void draw(Graphics g){
		if(!this.live){
			tc.bombTanks.remove(this);
			return;
		}
		
		//这里没懂为啥要把live置为false
		if(step > bombTankImages.length){
			this.live = false;
			step = 0;		
			return;
		}
		
		g.drawImage(bombTankImages[step], x, y, null);
		step++;
	}
	
}
