//package edu.csumb.jofausto.otterairways;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//import edu.csumb.jofausto.otterairways.AppDatabase.AppDatabase;

//@Entity(tableName = AppDatabase.ACCOUNT_TABLE)
//public class Account {
//
//    @PrimaryKey(autoGenerate = true)
//    private String username;
//    private String password;
//
//    public Account(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//}
package edu.csumb.jofausto.otterairways;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
//import edu.csumb.jofausto.otterairways.AppDatabase.AppDatabase;

//@Entity(tableName = AppDatabase.ACCOUNT_TABLE)
public class Account {

    //@PrimaryKey(autoGenerate = true)
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}