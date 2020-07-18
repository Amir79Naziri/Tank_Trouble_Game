package Setting;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Setting extends JPanel
{

    public JPanel getPanel()
    {
        return this;
    }

    private ColorJLabel userInfo;
    private ColorJLabel defaults;
    private JPanel userInfoPanel;
    private JPanel defaultsPanel;
    private JSlider sliderTank = new JSlider(50,150,100);
    private JPanel tempTank;
    private JSlider sliderCanon;
    private JPanel tempCanon;
    private JSlider sliderWall;
    private JPanel tempWall;
    private JButton setDefault;
    private JButton save;

    private MouseHandler mouse = new MouseHandler();

    public Setting()
    {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1000,600));
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
        userInfoPanel.add(new LeftPartLabel("Faraz",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("Playing Time:  ",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel("528 minutes ",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("Total Single Games:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel("25",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("Total multiPlayer Games:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel("15",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("winning numbers of SinglePlayer mode:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel("10",18,Color.CYAN));
        userInfoPanel.add(new LeftPartLabel("winning numbers of MultiPlayer mode:",18,Color.WHITE));
        userInfoPanel.add(new LeftPartLabel("3",18,Color.CYAN));


        ////////////////////////////////////////
        ////////////////////////////////////////

        defaultsPanel = new JPanel(new GridLayout(7,1,5,5));
        defaultsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,7));
        defaultsPanel.setBackground(Color.GRAY);
        defaultsPanel.setOpaque(true);
        JLabel tankStamina = new JLabel("Tank Stamina:");
        tankStamina.setFont(new Font("Arial",Font.BOLD,20));
        sliderTank = new JSlider(50,150,100);
        sliderTank.setMajorTickSpacing(10);
        sliderTank.setPaintLabels(true);
        sliderTank.setSnapToTicks(true);
        tempTank = new JPanel(new FlowLayout());
        sliderTank.setPreferredSize(new Dimension(500,100));
        tempTank.add(sliderTank);

        JLabel canonPower = new JLabel("Canon Power:");
        canonPower.setFont(new Font("Arial",Font.BOLD,20));
        sliderCanon = new JSlider(50,150,100);
        sliderCanon.setMajorTickSpacing(10);
        sliderCanon.setPaintLabels(true);
        sliderCanon.setSnapToTicks(true);
        tempCanon = new JPanel(new FlowLayout());
        sliderCanon.setPreferredSize(new Dimension(500,100));
        tempCanon.add(sliderCanon);

        JLabel wall = new JLabel("Destroyable Walls Stamina:");
        wall.setFont(new Font("Arial",Font.BOLD,20));
        sliderWall = new JSlider(50,150,100);
        sliderWall.setMajorTickSpacing(10);
        sliderWall.setPaintLabels(true);
        sliderWall.setSnapToTicks(true);
        tempWall = new JPanel(new FlowLayout());
        sliderWall.setPreferredSize(new Dimension(500,100));
        tempWall.add(sliderWall);

        setDefault = new JButton("Set Default");
        setDefault.setPreferredSize(new Dimension(250,50));
        save = new JButton("save");

        JPanel buttons = new JPanel(new GridLayout(1,2));
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
    }

    private void createLeft()
    {
        JPanel left = new JPanel(new GridLayout(10,1,10,10));
        left.setPreferredSize(new Dimension(200,600));
        left.setBackground(Color.PINK);
        this.add(left,BorderLayout.WEST);

        userInfo = new ColorJLabel("    User info");
        userInfo.addMouseListener(mouse);

        defaults = new ColorJLabel("    Game Defaults");
        defaults.addMouseListener(mouse);

        left.add(userInfo);
        left.add(defaults);
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
                getPanel().remove(defaultsPanel);
                getPanel().add(userInfoPanel,BorderLayout.CENTER);
                getPanel().setVisible(false);
                getPanel().setVisible(true);
            }
            if(e.getSource().equals(defaults))
            {
                getPanel().remove(userInfoPanel);
                getPanel().add(defaultsPanel,BorderLayout.CENTER);
                getPanel().setVisible(false);
                getPanel().setVisible(true);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}