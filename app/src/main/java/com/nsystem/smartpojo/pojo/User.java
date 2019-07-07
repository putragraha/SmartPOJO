package com.nsystem.smartpojo.pojo;

import com.nsystem.lib.annotation.Getter;
import com.nsystem.lib.annotation.Setter;
import com.nsystem.lib.annotation.Smart;

@Smart
public class User {
    @Getter @Setter int id;
    @Getter @Setter String username;
}
