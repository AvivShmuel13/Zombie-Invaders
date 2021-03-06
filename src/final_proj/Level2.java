package final_proj;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Level2 extends JPanel implements KeyListener,ActionListener,Runnable{
	Application app;	
	private int width = 1400, height = 750;
	private Image bgImage = new ImageIcon("Images/level.png").getImage(); //background
	private JLabel  lifes = new JLabel("Life ");
	private JLabel t = new JLabel("Time ");
	private JLabel s = new JLabel("Score ");
	private JLabel level = new JLabel("Level 2");
	private Font f1 = new Font("David",Font.BOLD,30);
	private Font f2 = new Font("Copperplate Gothic Bold",Font.BOLD,42);
	private Dimension d = new Dimension(250,60);
	int zl2=6;
	Player play = new Player();  // player
	Bullet b; // bullet 
	private Controller2 c;
	
	// timer
	 TimeLeft tl = new TimeLeft(60);
	private Timer ts = new Timer();
	static String time="0";

	// score 
	static String score="";
	
	// lifes 
	static String lfs="";
	
	public Level2(Application app) {
		this.app = app;
		this.setLayout(new FlowLayout());
		setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		addKeyListener(this);
		add(lifes);
		add(t);
		add(s);
		add(level);
		c = new Controller2(this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, width, height, null); //background
		play.draw(g, this); //player
		c.render(g);
		
		// level label
		level.setForeground(Color.GREEN); 
		level.setFont(f2);
		level.setBounds(600, 50, d.width, d.height);
		
		// life label
		lifes.setForeground(Color.GREEN); // life text
		lifes.setFont(f1);
		lifes.setBounds(20, 0, d.width, d.height);
		lfs=""+play.getLife();
		g.setColor(Color.GREEN);
		g.setFont(new Font("David",Font.BOLD, 34));
		g.drawString(lfs,100, 37);
		
		// timer label 
		t.setForeground(Color.GREEN); // timer text
		t.setFont(f1);
		t.setBounds(600, 0, d.width, d.height);
		// timer text
		time=""+tl.geTimee();
		g.setColor(Color.GREEN);
		g.setFont(new Font("David",Font.BOLD, 34));
		g.drawString(time,700, 37);
		
		// score label
		s.setForeground(Color.GREEN); // timer text
		s.setFont(f1);
		s.setBounds(1200,0, d.width, d.height);
		
		// score text 
		score=""+play.getScore();
		g.setColor(Color.GREEN);
		g.setFont(new Font("David",Font.BOLD, 34));
		g.drawString(score,1300, 37); 
		
		// checks for level up and check life
		if(tl.res==1 || play.getLife()==0)
		{
			tl.timer.cancel();
			ts.cancel();
			app.changePanel(new Menu(app));
		}
		
		
		//move to level 3
		if(zl2==0)
		{
			tl.timer.cancel();
			app.changePanel(new Level3(app));
			
		}		
			run();
	}
	@Override
	public void keyPressed(KeyEvent e){
		//move left
		if(e.getKeyCode()==KeyEvent.VK_LEFT && play.getX()>=10)
		{
			play.setX(-10);
		}
		//move right
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT && play.getX()<=1200)
		{
			play.setX(10);
		}
		//move up
		else if(e.getKeyCode()==KeyEvent.VK_UP && play.getY()>=(height/2)-200)
		{
			play.setY(-10);
		}
		//move down
		else if(e.getKeyCode()==KeyEvent.VK_DOWN && play.getY()<=(height-150))
		{
						play.setY(10);
		}
		//bullet fire
		else if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			if(c.getB()==false)
			c.addBullet(new Bullet(play.getX()-20,play.getY()+20));
		}
		repaint();
	}
	
	
	public int getTl() 
	{
		return tl.geTimee();
	}
	
	public void setTL(int t)
	{
		t+=tl.geTimee(); 
		tl.setTimee(t);
		System.out.println("timee " +tl.geTimee() + "t = "+t);
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {}
	
	// function for messagebox 
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Player: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    } 
	
	//main thread
	Thread th = new Thread(new Runnable(){
	@Override
	public void run() {
		c.tick();
		repaint();
	}
	});
	
	@Override
	public void run() {
	th.run();
	}
	
	//time thread
	class MyTimerTsak extends TimerTask{
		@Override
		public void run() {
				repaint();
	
		}
	}
}