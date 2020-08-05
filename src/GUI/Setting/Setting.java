package GUI.Setting;

import GUI.MultiGamePanels.CreateNewServer;
import GUI.MultiGamePanels.ServerButtonPanel;
import GUI.MultiGamePanels.ServerListPanel;
import GUI.Music;
import GUI.PictureJLabel;
import GameData.ServerInformationStorage;
import GameData.User;
import Login_SignUp_Logout.LogConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class Setting extends JPanel
{
    private ColorJLabel temp = null;

    public JPanel getPanel()
    {
        return this;
    }

    private JFrame frame;
    private JPanel pre;
    private User user;

    public void setPre(JPanel pre)
    {
        this.pre = pre;
    }

    private PictureJLabel pictureJLabel = new PictureJLabel("Images/Setting.jpg");
    private ColorJLabel userInfo;
    private ColorJLabel defaults;
    private ColorJLabel server;
    private JPanel userInfoPanel;
    private JPanel defaultsPanel;
    private JPanel serversListPanelInSetting;
    private JPanel serverListPanel;

    private JSlider sliderTank = new JSlider(10,100,100);
    private JPanel tempTank;
    private JSlider sliderCanon;
    private JPanel tempCanon;
    private JSlider sliderWall;
    private JPanel tempWall;
    private JLabel setDefault;
    private JLabel save;
    private JLabel back;
    private JButton createNewServer;
    private JButton removeServer;




    private MouseHandler mouse = new MouseHandler();

    public Setting(JFrame frame, User user)
    {
        this.frame = frame;
        this.serverListPanel = new ServerListPanel (null,
                frame,user);
        JScrollPane scrollPane1 = new JScrollPane (serverListPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        serversListPanelInSetting = new JPanel (new BorderLayout ());
        serversListPanelInSetting.add (scrollPane1,BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800,600));
        this.user = user;
        createLeft();
        createMain();
    }





    public void createMain()
    {
        userInfoPanel = new JPanel(new GridLayout(9,2,5,5));
        userInfoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,7));
        userInfoPanel.setBackground(Color.GRAY);
        userInfoPanel.setOpaque(true);
        userInfoPanel.add(new LeftPartLabel("User Name: ",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel(user.getUserName (),18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("Join Time:  ",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel(
                ((System.currentTimeMillis () - user.getSignedUpTime ()) / (1000L*60))
                + " Minutes",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("Total Single Games:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel(user.getNumOfSingleGames () + "",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("Total multiPlayer Games:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel(user.getNumOfMultiGames () + "",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("winning numbers of SinglePlayer mode:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel(user.getNumOfWinSingleGames () +"",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("winning numbers of MultiPlayer mode:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel(user.getNumOfWinMultiGames () +"",18,Color.CYAN));


        ////////////////////////////////////////
        ////////////////////////////////////////

        defaultsPanel = new JPanel(new GridLayout(7,1,5,5));
        defaultsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,7));
        defaultsPanel.setBackground(Color.GRAY);
        defaultsPanel.setOpaque(true);
        JLabel tankStamina = new JLabel("Tank Stamina:");
        tankStamina.setFont(new Font("Arial",Font.BOLD,20));
        sliderTank = new JSlider(10,100,user.getDefaultTankStamina ());
        sliderTank.setMajorTickSpacing(10);
        sliderTank.setPaintLabels(true);
        sliderTank.setSnapToTicks(true);
        tempTank = new JPanel(new FlowLayout());
        sliderTank.setPreferredSize(new Dimension(500,100));
        tempTank.add(sliderTank);

        JLabel canonPower = new JLabel("Canon Power:");
        canonPower.setFont(new Font("Arial",Font.BOLD,20));
        sliderCanon = new JSlider(10,100,user.getDefaultCanonPower ());
        sliderCanon.setMajorTickSpacing(10);
        sliderCanon.setPaintLabels(true);
        sliderCanon.setSnapToTicks(true);
        tempCanon = new JPanel(new FlowLayout());
        sliderCanon.setPreferredSize(new Dimension(500,100));
        tempCanon.add(sliderCanon);

        JLabel wall = new JLabel("Destroyable Walls Stamina:");
        wall.setFont(new Font("Arial",Font.BOLD,20));
        sliderWall = new JSlider(10,100,user.getDefaultWallStamina ());
        sliderWall.setMajorTickSpacing(10);
        sliderWall.setPaintLabels(true);
        sliderWall.setSnapToTicks(true);
        tempWall = new JPanel(new FlowLayout());
        sliderWall.setPreferredSize(new Dimension(500,100));
        tempWall.add(sliderWall);

        setDefault = new JLabel("Set as Default",JLabel.CENTER);
        setDefault.addMouseListener(mouse);
        setDefault.setBackground(Color.PINK);
        setDefault.setOpaque(true);
        setDefault.setPreferredSize(new Dimension(250,50));
        setDefault.setFont(new Font("Arial",Font.PLAIN,20));


        save = new JLabel("save",JLabel.CENTER);
        save.addMouseListener(mouse);
        save.setBackground(Color.PINK);
        save.setOpaque(true);
        save.setPreferredSize(new Dimension(250,50));
        save.setFont(new Font("Arial",Font.PLAIN,20));

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setOpaque(false);
        buttons.add(setDefault);
        buttons.add(save);

        defaultsPanel.add(tankStamina);
        defaultsPanel.add(tempTank);
        defaultsPanel.add(canonPower);
        defaultsPanel.add(tempCanon);
        defaultsPanel.add(wall);
        defaultsPanel.add(tempWall);
        defaultsPanel.add(buttons);


        JPanel buttonsPanel = new JPanel (new GridLayout (1,2));
        createNewServer = new JButton ("Create New ServerInformation");
        removeServer = new JButton ("Remove ServerInformation");
        buttonsPanel.add (createNewServer);
        buttonsPanel.add (removeServer);
        serversListPanelInSetting.add (buttonsPanel,BorderLayout.SOUTH);
        createNewServer.addMouseListener (mouse);
        removeServer.addMouseListener (mouse);

        this.add(pictureJLabel,BorderLayout.CENTER);
    }

    private void createLeft()
    {
        JPanel left = new JPanel(new GridLayout(10,1,10,10));
        left.setPreferredSize(new Dimension(200,600));
        left.setBackground(new Color(74,201,255));
        this.add(left,BorderLayout.WEST);

        back = new JLabel("        Back");
        back.setFont(new Font("Arial",Font.BOLD,22));
        back.setBackground(Color.ORANGE);
        back.addMouseListener(mouse);
        back.setOpaque(true);

        userInfo = new ColorJLabel("    User info");
        userInfo.addMouseListener(mouse);

        defaults = new ColorJLabel("    MultiGame Defaults");
        defaults.addMouseListener(mouse);

        server = new ColorJLabel("    ServerInformation");
        server.addMouseListener(mouse);

        left.add(back);
        left.add(userInfo);
        left.add(defaults);
        left.add(server);
    }


    private class MouseHandler implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e)
        {

        }

        @Override
        public void mousePressed(MouseEvent e)
        {

        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(e.getSource().equals(userInfo))
            {
                userInfo.setBackground(new Color(78,35,78));
                defaults.setBackground(new Color(163,73,164));
                server.setBackground(new Color(163,73,164));
                Music music = new Music();
                music.execute();
                getPanel().remove(pictureJLabel);
                getPanel().remove(userInfo);
                getPanel().remove(defaultsPanel);
                getPanel ().remove (serversListPanelInSetting);
                getPanel().add(userInfoPanel,BorderLayout.CENTER);
                getPanel().setVisible(false);
                getPanel().setVisible(true);
            }
            if(e.getSource().equals(defaults))
            {
                defaults.setBackground(new Color(78,35,78));
                userInfo.setBackground(new Color(163,73,164));
                server.setBackground(new Color(163,73,164));
                Music music = new Music();
                music.execute();
                getPanel().remove(pictureJLabel);
                getPanel().remove(defaultsPanel);
                getPanel().remove(userInfoPanel);
                getPanel ().remove (serversListPanelInSetting);
                getPanel().add(defaultsPanel,BorderLayout.CENTER);
                getPanel().setVisible(false);
                getPanel().setVisible(true);
            }
            if(e.getSource().equals(server))
            {
                server.setBackground(new Color(78,35,78));
                defaults.setBackground(new Color(163,73,164));
                userInfo.setBackground(new Color(163,73,164));
                Music music = new Music();
                music.execute();
                getPanel().remove(pictureJLabel);
                getPanel().remove(defaultsPanel);
                getPanel().remove(userInfoPanel);
                getPanel ().remove (serversListPanelInSetting);
                getPanel().add(serversListPanelInSetting,BorderLayout.CENTER);
                getPanel().setVisible(false);
                getPanel().setVisible(true);
            }
            if(e.getSource().equals(setDefault))
            {
                Music music = new Music();
                music.execute();
                sliderTank.setValue(user.getDefaultTankStamina ());
                sliderCanon.setValue(user.getDefaultCanonPower ());
                sliderWall.setValue(user.getDefaultWallStamina ());
            }
            if(e.getSource().equals(save))
            {
                Music music = new Music();
                music.execute();
                user.setDefaultTankStamina (sliderTank.getValue ());
                user.setDefaultCanonPower (sliderCanon.getValue ());
                user.setDefaultWallStamina (sliderWall.getValue ());
            }
            if(e.getSource().equals(back))
            {
                Music music = new Music();
                music.execute();
                frame.setContentPane(pre);
                frame.setVisible(false);
                frame.setVisible(true);
            }
            if (e.getSource () == createNewServer)
            {
                Music music = new Music();
                music.execute();
                frame.setContentPane (new CreateNewServer (frame, (ServerListPanel)serverListPanel,
                        getPanel (),user));
                frame.setVisible(false);
                frame.setVisible(true);
            }

            if (e.getSource () == removeServer)
            {
                Music music = new Music();
                music.execute();
                ServerListPanel serverListPanel2 = (ServerListPanel)serverListPanel;
                for (ServerButtonPanel serverButtonPanel : serverListPanel2.getServerButtonPanels ())
                    if (serverButtonPanel.isSelected ())
                    {
                        String ans = JOptionPane.showInputDialog ("Enter ServerInformation Password");
                        if (ans != null)
                        {
                            if (Arrays.equals (serverButtonPanel.getServerInformation ().getPassword (),
                                    ans.toCharArray ()))
                            {
                                serverListPanel2.removeServer (serverButtonPanel);
                                connect ();
                                return;
                            }
                        }
                        JOptionPane.showMessageDialog (getPanel (),"Password is Wrong",
                                "Error",JOptionPane.ERROR_MESSAGE);
                    }
            }

        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
//            if(e.getSource().equals(userInfo) || e.getSource().equals(server) || e.getSource().equals(defaults))
//            {
//                if(e.getSource().equals(userInfo))
//                    temp = userInfo;
//                if(e.getSource().equals(server))
//                    temp = server;
//                if(e.getSource().equals(defaults))
//                    temp = defaults;
//                Thread a = new Thread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        for(int i=0;i<4;i++)
//                        {
//
//                            switch (i)
//                            {
//                                case 0 : temp.setBackground(new Color(141,63,141));
//                                    break;
//                                case 1 : temp.setBackground(new Color(118,52,118));
//                                    break;
//                                case 2 : temp.setBackground(new Color(78,35,78));
//                                    break;
//                                case 3 : temp.setBackground(new Color(63,29,63));
//                                    break;
//                            }
//                            try
//                            {
//                                Thread.sleep(50);
//                            }
//                            catch (InterruptedException ex)
//                            {
//                                ex.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                a.start();
//            }
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
//            if(e.getSource().equals(userInfo) || e.getSource().equals(server) || e.getSource().equals(defaults))
//            {
//                if(e.getSource().equals(userInfo))
//                    temp = userInfo;
//                if(e.getSource().equals(server))
//                    temp = server;
//                if(e.getSource().equals(defaults))
//                    temp = defaults;
//                Thread a = new Thread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        for(int i=0;i<4;i++)
//                        {
//
//                            switch (i)
//                            {
//                                case 0 : temp.setBackground(new Color(78,35,78));
//                                    break;
//                                case 1 : temp.setBackground(new Color(118,52,118));
//                                    break;
//                                case 2 : temp.setBackground(new Color(141,63,141));
//                                    break;
//                                case 3 : temp.setBackground(new Color(163,73,164));
//                                    break;
//                            }
//                            try
//                            {
//                                Thread.sleep(50);
//                            }
//                            catch (InterruptedException ex)
//                            {
//                                ex.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                a.start();
//            }
        }
    }

    private void connect ()
    {
        LogConnector logConnector = new LogConnector ("127.0.0.1","Logout",user);
        new Thread (logConnector).start ();
    }


}
