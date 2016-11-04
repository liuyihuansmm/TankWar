import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 游戏主界面
 * @author LYH
 *
 */
public class TankClient extends Frame{
	
	
	public static final int FRAME_WIDTH = 800;
	
	public static final int FRAME_HEIGHT =600;
	
	public TankClient(){
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("坦克大战");
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args){
		new TankClient();
	}
}
