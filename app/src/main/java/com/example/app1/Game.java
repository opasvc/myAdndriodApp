package com.example.app1;

public class Game {
    private String name;
    private int iconResId;
    private Class<?> activityClass;

    public Game(String name, int iconResId, Class<?> activityClass) {
        this.name = name;
        this.iconResId = iconResId;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}
