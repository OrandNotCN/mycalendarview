package com.lacuz.cherish.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Random;

/**
 * Created by lacuz on 2017/8/2.
 */

@Entity
public class Birth {
    private String name;
    private long birth_time;
    private boolean is_lunar;
    private String phone;
    private String address;
    @Generated(hash = 266183747)
    public Birth(String name, long birth_time, boolean is_lunar, String phone,
            String address) {
        this.name = name;
        this.birth_time = birth_time;
        this.is_lunar = is_lunar;
        this.phone = phone;
        this.address = address;
    }
    @Generated(hash = 1920853122)
    public Birth() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getBirth_time() {
        return this.birth_time;
    }
    public void setBirth_time(long birth_time) {
        this.birth_time = birth_time;
    }
    public boolean getIs_lunar() {
        return this.is_lunar;
    }
    public void setIs_lunar(boolean is_lunar) {
        this.is_lunar = is_lunar;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Birth{" +
                "name='" + name + '\'' +
                ", birth_time=" + birth_time +
                ", is_lunar=" + is_lunar +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static Birth createEntity() {
        Birth entity = new Birth();
        Random random = new Random();
        entity.setAddress("地址："+random.nextInt(100));
        entity.setBirth_time(random.nextInt(1009)+999);
        entity.setIs_lunar(false);
        entity.setName("周小纯"+random.nextInt(66));
        return entity;
    }

}
