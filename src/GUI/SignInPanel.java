package GUI;

import GUI.MainPage.Main;
import GameData.NullUser;
import GameData.RememberMeData;
import GameData.User;
import Login_SignUp_Logout.LogConnector;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * this class represents the Login Panel of MultiGame
 *
 * @author Amir Naziri
 *
 */
public class SignInPanel extends JPanel
{

    public JPanel getSignIn()
    {
        return this;
    }

    private User user; // user who signIn
    private JTextField username; // user name
    private boolean usernameTyped;
    private JPasswordField password; // pass word
    private boolean passwordTyped;
    private JCheckBox rememberMe; // by clicking it , the game will save username and password
    private JButton signIn;
    private JLabel signUp;
    private JLabel errorMessage;
    private JPanel nex;
    private JFrame frame;
    private RememberMeData rememberMeData;

    /**
     * creates new SignInPanel
     * @param frame frame
     */
    public SignInPanel (JFrame frame)
    {
        super();
        user = null;
        this.frame = frame;
        frame.addWindowListener (new FrameHandler ());
        setBorder (new EmptyBorder (10,10,10,10));
        setLayout (new FlowLayout (FlowLayout.CENTER));
        createBasePanel ();
        usernameTyped = false;
        passwordTyped = false;
        addComponentListener (new ComponentAdapter () {
            @Override
            public void componentResized (ComponentEvent e) {
                repaint ();
            }
        });
        loadDataForRemember ();
        rememberMe.setSelected (rememberMeData.isTickOn ());
        if (rememberMeData.getUserName () != null && rememberMeData.getPassword () != null)
        {
            if (rememberMeData.isTickOn ())
            {
                username.setText (rememberMeData.getUserName ());
                username.setForeground (Color.BLACK);
                StringBuilder stringBuilder = new StringBuilder ();
                for (char c : rememberMeData.getPassword ())
                    stringBuilder.append (c);
                password.setText (stringBuilder.toString ());
            }
        }
    }

    /**
     * sets next panel
     * @param nex next panel
     */
    public void setNex (JPanel nex) {
        this.nex = nex;
    }



    /**
     * creates base panel
     */
    private void createBasePanel ()
    {
        KeyHandler keyHandler = new KeyHandler ();
        ActionHandler actionHandler = new ActionHandler ();
        FocusHandler focusHandler = new FocusHandler ();

        JPanel basePanel = new JPanel ();
        basePanel.setBackground (Color.WHITE);
        basePanel.setBorder (new LineBorder (Color.GRAY,6,true));

        GridBagLayout layout = new GridBagLayout ();
        GridBagConstraints constraints = new GridBagConstraints ();
        basePanel.setLayout (layout);


        JLabel header = new JLabel ("Login");
        header.setHorizontalTextPosition (SwingConstants.CENTER);
        header.setHorizontalAlignment (SwingConstants.CENTER);
        header.setOpaque (true);
        header.setBackground (Color.GRAY);
        header.setForeground (Color.WHITE);
        header.setFont (new Font ("DialogInput",Font.BOLD,17));


        username = new JTextField ("username");
        username.setBorder (BorderFactory.createCompoundBorder (new
                LineBorder (Color.LIGHT_GRAY,2,true),
                new EmptyBorder (3,5,5,5)));
        username.setForeground (Color.GRAY);
        username.addActionListener (actionHandler);
        username.addFocusListener (focusHandler);
        username.addKeyListener (keyHandler);

        password = new JPasswordField ("password");
        password.setBorder (BorderFactory.createCompoundBorder (new
                        LineBorder (Color.LIGHT_GRAY,2,true),
                new EmptyBorder (8,5,5,5)));
        password.addActionListener (actionHandler);
        password.addFocusListener (focusHandler);
        password.addKeyListener (keyHandler);



        rememberMe = new JCheckBox ("Remember Me");
        rememberMe.addItemListener (actionHandler);

        signIn = new JButton ("Log in");
        signIn.requestFocus ();
        signIn.addActionListener (actionHandler);
        signUp = new JLabel ("Sign up");
        signUp.setForeground (Color.GRAY);
        signUp.addMouseListener (new MouseHandler ());
        signUp.setCursor (new Cursor (Cursor.HAND_CURSOR));
        signUp.setHorizontalTextPosition (SwingConstants.CENTER);
        signUp.setHorizontalAlignment (SwingConstants.CENTER);

        errorMessage = new JLabel ("username or password is wrong or didn't fill ");
        errorMessage.setFont (new Font ("arial",Font.PLAIN,12));

        errorMessage.setForeground (Color.WHITE);



        constraints.fill = GridBagConstraints.BOTH;

        constraints.ipady = 15;
        constraints.ipadx = 30;
        constraints.insets = new Insets (0,0,4,0);
        GridBagSetter
                .addComponent (header,0,0,15,2,layout,constraints,basePanel);
        constraints.insets = new Insets (0,4,4,4);
        constraints.ipady = 3;
        GridBagSetter
                .addComponent (username,2,0,15,1,layout,constraints,basePanel);
        constraints.ipady = -2;
        GridBagSetter
                .addComponent (password,3,0,15,1,layout,constraints,basePanel);
        constraints.ipady = 0;
        GridBagSetter
                .addComponent (signIn,4,0,10,1,layout,constraints,basePanel);
        GridBagSetter
                .addComponent (signUp,4,6,5,1,layout,constraints,basePanel);
        GridBagSetter
                .addComponent (rememberMe,5,0,5,1,layout,constraints,basePanel);
        constraints.ipadx = 0;
        GridBagSetter
                .addComponent (errorMessage,6,0,15,1,layout,constraints,basePanel);

        add(basePanel);
    }

    /**
     * check if the given data for signing in is ok or nor
     * @return result
     */
    private boolean checkData ()
    {
        if (!usernameTyped || !passwordTyped)
        {
            errorMessage.setForeground (Color.RED);
            username.setBorder (BorderFactory.createCompoundBorder (new
                            LineBorder (Color.RED,2,true),
                    new EmptyBorder (3,5,5,5)));
            password.setBorder (BorderFactory.createCompoundBorder (new
                            LineBorder (Color.RED,2,true),
                    new EmptyBorder (8,5,5,5)));
            return false;
        }
        else
        {
            errorMessage.setForeground (Color.WHITE);
            username.setBorder (BorderFactory.createCompoundBorder (new
                            LineBorder (Color.LIGHT_GRAY,2,true),
                    new EmptyBorder (3,5,5,5)));
            password.setBorder (BorderFactory.createCompoundBorder (new
                            LineBorder (Color.LIGHT_GRAY,2,true),
                    new EmptyBorder (8,5,5,5)));
            return true;
        }
    }

    /**
     *
     * @return isRememberMe selected
     */
    public boolean isRememberMe ()
    {
        return rememberMe.isSelected ();
    }

    /**
     * clear username and password
     */
    public void clear ()
    {
        password.setText ("password");
        username.setText ("username");
        passwordTyped = false;
        usernameTyped = false;
        username.setForeground (Color.GRAY);
    }

    /**
     * this class handles mouse
     */
    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseEntered (MouseEvent e) {
            if (e.getSource () == signUp)
            {
                signUp.setForeground (Color.BLACK);
            }
        }

        @Override
        public void mouseExited (MouseEvent e) {
            if (e.getSource () == signUp)
            {
                signUp.setForeground (Color.GRAY);
            }
        }

        @Override
        public void mouseReleased (MouseEvent e) {
            if (e.getSource () == signUp)
            {
                Music music = new Music ();
                music.execute ();
                // sign in

                frame.setContentPane (new SignUpPanel(frame,getSignIn()));
                frame.setVisible (false);
                frame.setVisible (true);
                rememberMeData.set (username.getText (),password.getPassword ());
                saveDataForRemember ();
            }
        }
    }

    /**
     * this class handles frame
     */
    private class FrameHandler extends WindowAdapter
    {
        @Override
        public void windowClosing (WindowEvent e) {
            rememberMeData.set (username.getText (),password.getPassword ());
            saveDataForRemember ();
            saveDataForRemember ();
        }
    }

    /**
     * this class handles key
     */
    private class KeyHandler extends KeyAdapter
    {
        @Override
        public void keyReleased (KeyEvent e) {
            if ((e.getSource () == username || e.getSource () == password ) &&
                    (e.getKeyCode () == KeyEvent.VK_ENTER))
            {
                Music music = new Music ();
                music.execute ();
                if (!checkData ())
                    return;
                if (!connect ())
                {
                    passwordTyped = false;
                    usernameTyped = true;
                    checkData ();
                    return;
                }
                frame.setVisible (false);
                frame.setContentPane (nex);
                frame.setVisible (true);
                rememberMeData.set (username.getText (),password.getPassword ());
                saveDataForRemember ();
            }
            else if (e.getSource () == username)
            {
                usernameTyped = username.getText ().length () != 0;
            }
            else if (e.getSource () == password)
            {
                passwordTyped = password.getPassword ().length != 0;
            }
        }
    }

    /**
     * login
     * @return res
     */
    private boolean connect ()
    {
        LogConnector logConnector = new LogConnector ("127.0.0.1",username.getText (),
                password.getPassword (),"Login");
        new Thread (logConnector).start ();
        while (!logConnector.isFinished ())
        {
            try {
                Thread.sleep (100);
            } catch (InterruptedException ex)
            {
                ex.printStackTrace ();
            }
        }
        user = logConnector.getLoginOrSignUpResult ();
        if (user instanceof NullUser) {
            user = null;
        }
        if (user != null)
            ((Main)nex).setUser (user);
        return user != null;
    }

    /**
     * this class handles actions
     */
    private class ActionHandler implements ActionListener , ItemListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == signIn) {
                Music music = new Music ();
                music.execute ();
                if (!connect ())
                {
                    passwordTyped = false;
                    usernameTyped = true;
                    checkData ();
                    return;
                }


                frame.setContentPane (nex);
                frame.setVisible (false);
                frame.setVisible (true);
                rememberMeData.set (username.getText (),password.getPassword ());
                saveDataForRemember ();
            }
        }

        @Override
        public void itemStateChanged (ItemEvent e) {
            Music music = new Music ();
            music.execute ();
            rememberMeData.setTickOn (rememberMe.isSelected ());
        }
    }

    /**
     * this class handles focus
     */
    private class FocusHandler implements FocusListener
    {
        @Override
        public void focusGained (FocusEvent e) {
            if (e.getSource () == password)
            {
                if (!passwordTyped)
                {
                    password.setText ("");
                }
            }
            else if (e.getSource () == username)
            {
                if (!usernameTyped)
                {
                    username.setForeground (Color.BLACK);
                    username.setText ("");
                }
            }
        }

        @Override
        public void focusLost (FocusEvent e) {
            if (e.getSource () == password)
            {

                if (!passwordTyped)
                {
                    password.setText ("password");
                }

            }
            else if (e.getSource () == username)
            {
                if (!usernameTyped)
                {
                    username.setText ("username");
                    username.setForeground (Color.GRAY);
                    username.setCaretPosition (0);
                }
            }
        }
    }

    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent (g);
        try {


            g.drawImage (ImageIO.read (new File ("./Images/login.jpg")).
                    getScaledInstance (getWidth (),getHeight (),Image.SCALE_FAST)
                    ,0,0,this);
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    /**
     * load remember data
     */
    private void loadDataForRemember ()
    {
        try (ObjectInputStream in = new ObjectInputStream (new FileInputStream (
                new File ("./Files/RememberMe/rememberMe.ser")))){
            rememberMeData = (RememberMeData) in.readObject ();
        }
        catch (IOException | ClassNotFoundException e)
        {
            rememberMeData = new RememberMeData ();
        }
    }

    /**
     * save remember data
     */
    private void saveDataForRemember ()
    {
        try (ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream (
                new File ("./Files/RememberMe/rememberMe.ser")))){
            out.writeObject (rememberMeData);
        } catch (IOException e)
        {
            System.out.println ("Some Thing went wrong in Save RememberMe");
        }
    }


}
