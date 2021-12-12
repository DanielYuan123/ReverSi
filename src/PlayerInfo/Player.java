package PlayerInfo;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable {
    private String name;
    private int Pid;
    private static int PlayerCounter=0;
    private int WinTime=0;
    private int PlayTime=0;
    public boolean isNew = false;


    private static List<String> nameList = new ArrayList<String>(5);
    private static List<Player> playerList = new ArrayList<>(5);


    public static List<Player> getPlayerList(){return playerList;}
    public static List<String> getNameList(){return nameList;}


    public Player(String name){
            this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public int getPid(){
        return this.Pid;
    }

    public void Win(){
        this.WinTime++;
    }

    public void Play(){
        this.PlayTime++;
    }

    public String toString(){
        return "";
    }

    public int getWinTime(){
        return this.WinTime;
    }

    public int getPlayTime(){
        return this.PlayTime;
    }

    @Override
    public int compareTo(Object o) {
        Player otherPlayer = (Player)o;
        if(this.WinTime > otherPlayer.getWinTime()){
            return 1;
        }else if (this.WinTime < otherPlayer.getWinTime()){
            return -1;
        }else {
            return 0;
        }
    }
}


