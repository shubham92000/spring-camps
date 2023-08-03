package com.bootcamp.springcamp.utils;

public class ModeValue {
    public static Mode getMode(String modeType){
        return switch (modeType.toLowerCase()) {
            case "offline" -> Mode.OFFLINE;
            case "online" -> Mode.ONLINE;
            default -> null;
        };
    }
}
