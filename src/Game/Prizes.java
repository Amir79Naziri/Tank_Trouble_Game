package Game;

import GUI.Music;

import java.util.ArrayList;
import java.util.Random;

/**
 * this class handles all of the prizes
 */

public class Prizes implements Runnable
{
    private ArrayList<Prize> prizes;
    private ArrayList<Tank> tanks;
    private Maps maps;
    private boolean active;

    /**
     * constructor  the prize class
     * @param maps maps
     * @param tanks tanks
     */
    public Prizes(Maps maps , ArrayList<Tank> tanks)
    {
        active = true;
        prizes = new ArrayList<>();
        this.maps = maps;
        this.tanks = tanks;
    }

    /**
     * put the prize of the map
     */
    public void putPrize()
    {
        while(true)
        {
            try
            {
                Thread.sleep(4000);
                Random random = new Random();
                int r = random.nextInt(5) + 1;
                int x, y;

                do
                    {
                    x = random.nextInt(1240) + 20;
                    y = random.nextInt(650) + 60;
                } while(!isEmpty(x, y));

                Music music = new Music();
                music.setFilePath("Files/Sounds/NewGift.au",false);
                music.execute();
                prizes.add(new Prize(r, x, y));
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
    }

    /**
     * check if the place is empty of tge map
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if it is empty and false otherwise
     */
    public boolean isEmpty(int x ,int y)
    {
        for(int i=0;i<tanks.size();i++)
        {
            if( ((x-tanks.get(i).getLocX())*(x-tanks.get(i).getLocX()) +
                    (y-tanks.get(i).getLocY())*(y-tanks.get(i).getLocY())) < (50)*(50) )
            {
                return false;
            }
        }

        for(int i=0;i<maps.getWalls().size();i++)
        {
            int wallX = maps.getWalls().get(i).getX();
            int wallY = maps.getWalls().get(i).getY();
            int wallL = maps.getWalls().get(i).getLength();

            if(maps.getWalls().get(i).getType().equals("H"))
            {
                if(x>=wallX-50 && x<= (wallX + wallL + 50) )
                {
                    if( (y-wallY<=80 && y-wallY>=0) || (wallY-y<=80 && wallY-y>=0) )
                    {
                        return false;
                    }
                }
            }
            if(maps.getWalls().get(i).getType().equals("V"))
            {
                if(y>=wallY-50 && y<= (wallY + wallL + 50) )
                {
                    if((x-wallX<=80 && x-wallX>=0) || (wallX-x<=80 && wallX-x>=0))
                    {
                        return false;
                    }
                }
            }
        }

        for(int i= 0 ; i<prizes.size();i++)
        {
            if( ( (x-prizes.get(i).getX())*(x-prizes.get(i).getX()) +
                    (y-prizes.get(i).getY())*(y-prizes.get(i).getY()) ) <= 39*39 )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * deActive putting prize
     */
    public void deActive()
    {
        active = false;
    }

    /**
     * get list of all prizes
     * @return the prizes field
     */
    public ArrayList<Prize> getPrizes()
    {
        return prizes;
    }

    @Override
    public void run()
    {
        putPrize();
    }
}
