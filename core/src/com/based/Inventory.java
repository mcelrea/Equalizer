package com.based;

public class Inventory {
    boolean hasBasicSword;

    public static final int BASICSWORD = 1;

    int weaponEquipped = BASICSWORD;

    public Inventory() {
        hasBasicSword = true;
    }

    public int getWeaponEquipped() {
        return weaponEquipped;
    }
}
