package com.lacuz.cherish.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private Long ids;

    private String nick;

    @Generated(hash = 2039444487)
    public User(Long ids, String nick) {
        this.ids = ids;
        this.nick = nick;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getIds() {
        return this.ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}