package Game;

import GameData.User;

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

	private GameFrame canvas;
	private GameState state;
	private JFrame menuFrame;
	private int level, tankStamina,canonPower, wallStamina;

	public GameLoop(GameFrame frame , JFrame menuFrame,
					int level,int tankStamina,int canonPower,int wallStamina)
	{
		this.menuFrame = menuFrame;
		this.level = level;
		this.tankStamina = tankStamina;
		this.canonPower = canonPower;
		this.wallStamina = wallStamina;
		canvas = frame;
	}

	/**
	 * This must be called before the game loop starts.
	 */
	public void init(ArrayList<User> users)
	{
		state = new GameState(level, tankStamina,canonPower, wallStamina, users);

		for(Tank tank:state.getTanks ())
		{
			canvas.addKeyListener(tank.getKeyHandler());
		}
	}

	@Override
	public void run()
	{
		int gameOver = 0;
		while(gameOver == 0)
		{
			try
			{
				long start = System.currentTimeMillis();
				//
				state.update();
				canvas.render(state);
				gameOver = state.gameOver;
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
			for (Tank tank : state.getTanks ())
				if (!(tank instanceof IntelligentTank))
					tank.getUser ().setNumOfWinSingleGames (tank.getUser ().getNumOfWinSingleGames () + 1);

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
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

		try
		{
			canvas.render(state);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
