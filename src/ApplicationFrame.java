import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class ApplicationFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String APPTITLE = "Button Smasher";
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 450;
	
	private int xPosition, yPosition, xDim, yDim;
	private int score, level, delay;
	private double time;
	
	private Random generator;
	private Timer timer, positionTimer;
	
	private Container main;
	private CardLayout cardLayout;
	private JPanel menuPanel, gamePanel, endPanel;
	private JLabel scoreLabel, endScore, levelLabel, endLevel, timerLabel;
	private JButton start, highscore, modes, exit;
	private JButton buttonToClick, tryAgain, mainMenu;
	
	public ApplicationFrame() {
		//Setting main layout
    	main = getContentPane();
    	cardLayout = new CardLayout(5, 5);
    	setLayout(cardLayout);
    	
    	//Initializing important classes
    	generator = new Random();
    	timer = new Timer(50, this);
    	positionTimer = new Timer(1000, this);
    	  	
		//Create components
		createMenuPanel();
		createGamePanel();
		createEndPanel();
    	
    	//Frame properties
    	setTitle(APPTITLE);
    	setSize(SCREEN_WIDTH, SCREEN_HEIGHT);   
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setResizable(false);
    	setVisible(true);	
    }
    
    public void actionPerformed(ActionEvent e) {
    	/*	-----------------------------------
    	 *	Button related events
     	 *	----------------------------------- */
    	if(e.getSource() == start || e.getSource() == tryAgain)
    		start();
    	
    	if(e.getSource() == exit)
    		exit();
    		
    	if(e.getSource() == mainMenu)
    		cardLayout.show(main, "1");
    		
    	if(e.getSource() == buttonToClick)
			addScore();
		
		/*	-----------------------------------
    	 *	Timer related events
     	 *	----------------------------------- */
    	if(e.getSource() == timer)
    		countdown();
    	
    	if(e.getSource() == positionTimer)
    		randomizePosition();
    }
      
    /*	-----------------------------------
     *	GUI Related Methods
     *	----------------------------------- */
    private void createMenuPanel() {
   		menuPanel = new JPanel(new GridBagLayout());
   		main.add(menuPanel, "1");
   		
   		GridBagConstraints c = new GridBagConstraints();
   		c.fill = GridBagConstraints.NONE;
   		c.insets = new Insets(10, 10, 10 , 10);
   		c.gridx = 0;
   		
   		c.gridy = 0;
   		JLabel titleLabel = new JLabel("Button Smasher");
   		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
   		menuPanel.add(titleLabel, c);
   		
   		c.gridy = 1;
   		JPanel options = new JPanel(new GridLayout(4, 1, 10, 10));
   		menuPanel.add(options, c);
   		
   		start = new JButton("Start");
   		start.setFont(new Font("Tahoma", Font.PLAIN, 14));
   		start.setPreferredSize(new Dimension(160, 40));
   		start.addActionListener(this);
   		options.add(start);
   		
   		highscore = new JButton("Highscore");
   		highscore.setFont(new Font("Tahoma", Font.PLAIN, 14));
   		highscore.setPreferredSize(new Dimension(160, 40));
   		highscore.addActionListener(this);
   		options.add(highscore);
   		
   		modes = new JButton("Modes");
   		modes.setFont(new Font("Tahoma", Font.PLAIN, 14));
   		modes.setPreferredSize(new Dimension(160, 40));
   		modes.addActionListener(this);
   		options.add(modes);
   		
   		exit = new JButton("Exit");
   		exit.setFont(new Font("Tahoma", Font.PLAIN, 14));
   		exit.setPreferredSize(new Dimension(160, 40));
   		exit.addActionListener(this);
   		options.add(exit);				
    }
    
    private void createGamePanel() {
    	//Add components to Game Panel
    	gamePanel = new JPanel(new BorderLayout(5, 5));
    	main.add(gamePanel, "2");
    	
    	//Add labels
    	JPanel p1 = new JPanel();
    	p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
    	gamePanel.add(p1, BorderLayout.PAGE_START);
    	
    	scoreLabel = new JLabel("Score: " + score);
    	scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    	p1.add(scoreLabel);
    	
    	p1.add(Box.createHorizontalGlue());
    	
    	levelLabel = new JLabel("Level: " + level);
    	levelLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    	p1.add(levelLabel);
    	
    	p1.add(Box.createHorizontalGlue());
    	
    	timerLabel = new JLabel("Time: " + time);
    	timerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    	p1.add(timerLabel);
    	
    	//Add button to the Game Panel
    	JPanel p2 = new JPanel(null);
    	p2.setBackground(Color.WHITE);
    	gamePanel.add(p2, BorderLayout.CENTER);
    		
    	buttonToClick = new JButton(":)");
    	buttonToClick.setMargin(new Insets(0, 0, 0 ,0));
    	buttonToClick.addActionListener(this);
    	p2.add(buttonToClick);
    }
    
   	private void createEndPanel() {
   		endPanel = new JPanel(new GridBagLayout());
   		main.add(endPanel, "3");
   		
   		GridBagConstraints c = new GridBagConstraints();
   		c.fill = GridBagConstraints.NONE;
   		c.insets = new Insets(10, 10, 10 , 10);
   		c.gridx = 0;
   		
   		c.gridy = 0;
   		JLabel gameOver = new JLabel("Game Over");
   		gameOver.setFont(new Font("Tahoma", Font.BOLD, 35));
   		endPanel.add(gameOver, c);
   		
   		c.gridy = 1;
   		JPanel stats = new JPanel(new GridLayout(2, 1, 5, 5));
   		endPanel.add(stats, c);
   		
   		endScore = new JLabel();
    	endScore.setFont(new Font("Tahoma", Font.BOLD, 16));
   		stats.add(endScore);
   		
   		endLevel = new JLabel("Level: " + level);
    	endLevel.setFont(new Font("Tahoma", Font.BOLD, 16));
   		stats.add(endLevel);
   		
   		c.gridy = 3;
   		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
   		endPanel.add(buttonPanel, c);
   		
   		tryAgain = new JButton("Try Again?");
   		tryAgain.setPreferredSize(new Dimension(120, 40));
   		tryAgain.setFont(new Font("Tahoma", Font.PLAIN, 14));
   		tryAgain.addActionListener(this);
   		buttonPanel.add(tryAgain);
   		
   		mainMenu = new JButton("Main Menu");
   		mainMenu.setPreferredSize(new Dimension(120, 40));
   		mainMenu.setFont(new Font("Tahoma", Font.PLAIN, 14));
   		mainMenu.addActionListener(this);
   		buttonPanel.add(mainMenu);
   		
   	}
   	
   	/*	-----------------------------------
     *	ActionEvent Related Methods
     *	----------------------------------- */
	private void start() {		 	
    	score = 0;
    	level = 1;
    	time = 60;
    	
    	xDim = 90;
    	yDim = 60;
    	delay = 1000;
    	
    	xPosition = generator.nextInt(SCREEN_WIDTH - xDim);
    	yPosition = generator.nextInt(SCREEN_HEIGHT - 60 - yDim);
    	
    	buttonToClick.setBounds(xPosition, yPosition, xDim, yDim);
    	
    	timer.start();
		positionTimer.start();
		
		cardLayout.show(main, "2");
	}
	
	private void exit() {
		dispose();
		System.exit(0);
	}
	
    private void addScore() {
    	if(time > 0.0) {
	    	score++;
	    	scoreLabel.setText("Score: " + score);
	    	levelLabel.setText("Level: " + level);
	    	
	    	if(score % 5 == 0) {
    			delay -= 30;
    			//time += 10;
    			
    			if(xDim > 30)
    				xDim -= 3;
    			
    			if(yDim > 20)
    				yDim-= 2;
    				
    			level++;
	    	}
    	}
    }
    
    private void countdown() {
    	timerLabel.setText("Time: " + String.format("%.2f", time));
    	time -= 0.05;
    	
    	if(time <= 0.0) {
    		endScore.setText("Score: " + score);
    		endLevel.setText("Level: " + level);
    		
    		cardLayout.show(main, "3");
    		
    		timer.stop();
    		positionTimer.stop();	
    	}
    }
    
    private void randomizePosition() {
    	xPosition = generator.nextInt(SCREEN_WIDTH - xDim);
    	yPosition = generator.nextInt(SCREEN_HEIGHT - 60 - yDim);

    	buttonToClick.setBounds(xPosition, yPosition, xDim, yDim);
    	positionTimer.setDelay(delay);
    }
}