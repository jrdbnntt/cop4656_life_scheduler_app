package com.jrdbnntt.android.std.api.data;

import com.google.gson.Gson;

/**
 * Standard Gson-compatible object
 */
public abstract class GsonObject {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this, this.getClass());
    }
}
