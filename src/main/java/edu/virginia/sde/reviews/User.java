package edu.virginia.sde.reviews;

public class User {
    private String username;
    private String password;
    private int id;

    public User(String username, String password, int id){
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setId(int id){
        this.id = id;
    }
}
