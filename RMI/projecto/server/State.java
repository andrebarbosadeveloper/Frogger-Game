package edu.ufp.inf.sd.rmi.projecto.server;

import java.io.Serializable;

public class State implements Serializable {

    private int id;
    private String msg;

    private String position;
    private String score;
    private String life;
    private Integer time;
    private String level;

    public State(String position, String score, String life, Integer time, String level) {
        this.position = position;
        this.score = score;
        this.life = life;
        this.time = time;
        this.level = level;
    }

    public State(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

