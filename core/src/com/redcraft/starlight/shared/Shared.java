package com.redcraft.starlight.shared;

public class Shared {
    //Naming conventions:
    // object.field.subfield
    // object.aLongString
    public static SharedAssets CLIENT;
    public static SharedAssets SERVER;

    public static void initSharedClient() {
        CLIENT = new SharedAssets();
    }

    public static void initSharedServer() {
        SERVER = new SharedAssets();
    }
}
