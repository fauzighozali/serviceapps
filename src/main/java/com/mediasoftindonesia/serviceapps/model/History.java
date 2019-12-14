package com.mediasoftindonesia.serviceapps.model;

import java.util.List;

public class History {

    private String tgl_service;
    private String catatan_service;
    private Store store;
    private Status status;

    public String getTgl_service() {
        return tgl_service;
    }

    public String getCatatan_service() {
        return catatan_service;
    }

    public Store getStore() {
        return store;
    }

    public Status getStatus() {
        return status;
    }
}
