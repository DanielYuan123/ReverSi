package PlayerInfo;

public class Player {
    private String name;
    private int Pid;
    private static int PlayerCounter=0;

    public Player(String name){
        Player.PlayerCounter++;
        this.name=name;
        this.Pid=PlayerCounter;
    }

    public String getName(){
        return this.name;
    }

    public int getPid(){
        return this.Pid;
    }

}
