import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
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
	
	Image screenImage = null;
	/**
	 * 游戏中的河流River集合
	 */
	private List<River> riverList = new ArrayList<River>();
	
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
		
		/**
		 * 画出河流
		 */
		for(int i=0; i<riverList.size(); i++){
			River rTemp = riverList.get(i);
			rTemp.draw(g);
		}
		
		g.setColor(c);
	}
	
	public TankClient(){
		
		
		
		/**
		 * 添加河流
		 */
		riverList.add(new River(0, 0, this));
		
		
		
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
			while(true){
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
