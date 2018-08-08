package com.moonshoter.librarypackageaar;


import android.util.Log;

public class WorldInstance {
    private Object mSomeImportant;

    public static WorldInstance mInstance;

    public static WorldInstance getInstance() {

        if (mInstance == null) {
            mInstance = new WorldInstance("You");
        }

        return mInstance;
    }

    private WorldInstance(Object someImportant) {
        mSomeImportant = someImportant;
    }

    public static void meetYou() {
        Log.i("moonshot", "always love you.♥ biu~biu~biu~");
    }
}
