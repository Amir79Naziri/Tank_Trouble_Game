
import GUI.CreateNewMultiGame;
import GUI.MultiGame.MultiGamePanel;
import GameData.GameFinishType;
import GameData.GameMemberShipType;
import GameData.MultiGame;
import GameData.Server;

import javax.swing.*;
import java.util.ArrayList;


public class TestAmir {
    public static void main (String[] args) {

        try { // "javax.swing.plaf.nimbus.NimbusLookAndFeel"
            UIManager.setLookAndFeel ("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace ();
        }

        JFrame frame = new JFrame ();
        frame.setLocation (0, 0);
        frame.setSize ((720 * 16) / 9, 720);


        ArrayList<MultiGame> multiGames = new ArrayList<> ();
        multiGames.add (new MultiGame ("google",GameFinishType.DEATH_MATCH,
                GameMemberShipType.SINGLE,22,30,30,30));
        multiGames.add (new MultiGame ("google2",GameFinishType.DEATH_MATCH,
                GameMemberShipType.SINGLE,22,30,30,30));
        multiGames.add (new MultiGame ("google3",GameFinishType.DEATH_MATCH,
                GameMemberShipType.SINGLE,22,30,30,30));
        multiGames.add (new MultiGame ("google4",GameFinishType.DEATH_MATCH,
                GameMemberShipType.SINGLE,22,30,30,30));
        Server server1 = new Server ("Haasd",multiGames);

        ArrayList<Server> servers = new ArrayList<> ();
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);
        servers.add (server1);


        frame.setContentPane (new MultiGamePanel (servers));
        frame.setVisible (true);
    }
}