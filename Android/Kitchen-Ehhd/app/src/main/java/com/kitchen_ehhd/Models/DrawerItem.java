package com.kitchen_ehhd.Models;

/**
 * Created by yisen_000 on 2015-09-19.
 */
public class DrawerItem {
    private String name;
    private int drawerNum;

    public DrawerItem(String name, int drawerNum) {
        this.name = name;
        this.drawerNum = drawerNum;
    }

    public String getName() {
        return name;
    }

    public int getDrawerNum() {
        return drawerNum;
    }
}
