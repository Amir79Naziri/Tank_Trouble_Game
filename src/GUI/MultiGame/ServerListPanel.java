package GUI.MultiGame;


import GUI.Music;
import GameData.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.InputMismatchException;


public class ServerListPanel extends JPanel
{


    private MultiGamePanel mainPanel;
    private ArrayList<ServerButtonPanel> serverButtonPanels;

    public ServerListPanel (ArrayList<Server> servers, MultiGamePanel mainPanel)
    {
        super();
        if (servers == null)
            throw new InputMismatchException ("servers in Null");
        this.mainPanel = mainPanel;
        serverButtonPanels = new ArrayList<> ();
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (Color.WHITE);
        setBorder (new EmptyBorder (5,5,5,5));
        MouseHandler mouseHandler = new MouseHandler ();
        for (Server server : servers)
        {
            ServerButtonPanel serverButtonPanel = new ServerButtonPanel (server,mainPanel);
            add(serverButtonPanel);
            serverButtonPanel.addMouseListener (mouseHandler);
            serverButtonPanels.add (serverButtonPanel);
        }

    }

    public void addNewServer (Server server)
    {
        MouseHandler mouseHandler = new MouseHandler ();
        ServerButtonPanel serverButtonPanel = new ServerButtonPanel (server,mainPanel);
        serverButtonPanels.add (serverButtonPanel);
        serverButtonPanel.addMouseListener (mouseHandler);
        this.setVisible (false);
        add(serverButtonPanel);
        this.setVisible (true);
    }



    public MultiGamePanel getMainPanel () {
        return mainPanel;
    }

    private class MouseHandler extends MouseAdapter
    {

        @Override
        public void mouseEntered (MouseEvent e) {
            e.getComponent ().
                    setBackground (new Color (91, 131, 56));
        }

        @Override
        public void mouseExited (MouseEvent e) {
            e.getComponent ().setBackground (Color.WHITE);
        }

        @Override
        public void mouseClicked (MouseEvent e) {
            Music music = new Music ();
            music.execute ();
            for (ServerButtonPanel serverButtonPanel : serverButtonPanels)
            {
                if (e.getComponent () == serverButtonPanel)
                {
                    mainPanel.setSecondPanel (serverButtonPanel.getMultiGameListPanel (),serverButtonPanel);
                    serverButtonPanel.changeFontAndColor (
                            new Font ("Arial",Font.ITALIC,14),Color.BLACK);
                }
                else
                    serverButtonPanel.changeFontAndColor (new Font ("Arial",Font.PLAIN,14),
                            Color.DARK_GRAY);
            }
        }
    }
}
