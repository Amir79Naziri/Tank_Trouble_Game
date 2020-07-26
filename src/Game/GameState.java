/*** In The Name of Allah ***/
package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

	private ArrayList<Tank> tanks;
	private ArrayList<Bullet> bullets;

	public boolean gameOver;
	private Maps maps;
	private Prizes prizes;

	public Maps getMaps() {
		return maps;
	}

	public GameState()
	{
		maps = new Maps();
		bullets = new ArrayList<> ();
		tanks = new ArrayList<> ();
		prizes = new Prizes(maps,tanks);
		Tank tank1 = new Tank(bullets, maps.getWalls (), tanks, prizes);
		Tank tank2 = new IntelligentTank (bullets, maps.getWalls (),tanks, prizes);
		Tank tank3 = new Tank (bullets, maps.getWalls (),tanks, prizes);


		tanks.add (tank1);
		tanks.add (tank2);
//		tanks.add (tank3);
		gameOver = false;
		Thread t1 = new Thread(prizes);
		t1.start();
	}

	public Prizes getPrizes()
	{
		return prizes;
	}

	public ArrayList<Tank> getTanks () {
		return tanks;
	}



	/**
	 * The method which updates the game state.
	 */
	public void update()
	{
		if (tanks.size () == 0)
			gameOver = true;
		ExecutorService executorService = Executors.newCachedThreadPool ();

		Iterator<Bullet> bulletIterator = bullets.iterator ();
		while (bulletIterator.hasNext ())
		{
			Bullet bullet = bulletIterator.next ();
			if (bullet.hasExpired ())
				bulletIterator.remove ();
			else
				executorService.execute (bullet);
		}

		Iterator<Tank> tankIterator = tanks.iterator ();
		while (tankIterator.hasNext ())
		{
			Tank tank = tankIterator.next ();
			if (tank.isDestroyed ())
				tankIterator.remove ();
			else
				executorService.execute (tank);
		}
		executorService.shutdown();

		try {
			while (!executorService.isTerminated ())
			{
				Thread.sleep (1);
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace ();
		}
	}









	public ArrayList<Bullet> getBullets () {
		return bullets;
	}

}

