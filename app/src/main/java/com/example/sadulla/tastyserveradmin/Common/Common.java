package com.example.sadulla.tastyserveradmin.Common;

import com.example.sadulla.tastyserveradmin.Model.User;

public class Common {
    public static User currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static final int PICK_IMAGE_REQUEST = 71;

    public static String convertCodeToStatus(String code)
    {
        if (code.equals("0"))
            return "PLACED";
        else if (code.equals("1"))
            return "ON WAY";
        else return "SHIPPED";
    }
}
