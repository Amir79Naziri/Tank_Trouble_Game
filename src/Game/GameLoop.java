package Game;

import GameData.User;
import Login_SignUp_Logout.LogConnector;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 *
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable
{

	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 54;
	private int type;
	private GameFrame canvas;
	private GameState state;
	private JFrame menuFrame;
	private int level, tankStamina,canonPower, wallStamina;
	private User user;
	private int PCScore = 0;
	private int playerScore = 0;
	private int[] kills;

	/**
	 * creates game loop
	 * @param frame frame
	 * @param menuFrame menuFrame
	 * @param level level
	 * @param tankStamina tankStamina
	 * @param canonPower canonPower
	 * @param wallStamina wallStamina
	 * @param type type
	 */
	public GameLoop(GameFrame frame , JFrame menuFrame,
					int level,int tankStamina,int canonPower,int wallStamina,int type)
	{
		kills = new int[level+1];
		this.type = type;
		this.menuFrame = menuFrame;
		this.level = level;
		this.tankStamina = tankStamina;
		this.canonPower = canonPower;
		this.wallStamina = wallStamina;
		canvas = frame;
	}

	/**
	 * This must be called before the game loop starts.
	 * @param user user
	 */
	public void init(User user)
	{
		this.user = user;
		state = new GameState(level, tankStamina,canonPower, wallStamina, user,kills);

		for(Tank tank:state.getTanks ())
		{
			canvas.addKeyListener(tank.getKeyHandler());
		}
	}

	@Override
	public void run()
	{
		int win = 0;
		for(int i=1;i<=type;i++)
		{
			if(i!=1)
				init(user);
			int gameOver = 0;
			while(gameOver == 0)
			{
				try
				{
					long start = System.currentTimeMillis();
					//
					state.update();
					gameOver = state.gameOver;
					canvas.render(state,kills,user);

					//
					long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
					if (delay > 0)
						Thread.sleep(delay);
				}
				catch (InterruptedException | IOException ex)
				{
					ex.printStackTrace();
				}
			}
			if (gameOver == 1)
				win++;

			if(i!=type)
			{
				try
				{
					canvas.render(state,kills,user);
					Thread.sleep(4000);
				}
				catch (InterruptedException | IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		if (type == 1 && win > 0)
			user.setNumOfWinSingleGames (user.getNumOfWinSingleGames () + 1);
		else if (type == 5 && win > 2)
			user.setNumOfWinSingleGames (user.getNumOfWinSingleGames () + 1);

		user.setScore (user.getScore () + kills[0]);

		try
		{
			canvas.render(state,kills,user);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					connect ();
					Thread.sleep(3000);
					canvas.setVisible(false);
					menuFrame.setVisible(true);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();


	}

	/**
	 * connect to save server
	 */
	private void connect ()
	{
		LogConnector logConnector = new LogConnector ("127.0.0.1","Logout",user);
		new Thread (logConnector).start ();
	}
}


