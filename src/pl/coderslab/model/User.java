package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DataType;

public class User extends DataType<User> {
    
    private int id = 0;
    private String username;
    private String email;
    private String password;
    private int group_id;
    
    public User(String username, String email, String password, int group_id) {
        this(0, username, email, password, group_id);
    }
    
    public User(int id, String username, String email, String password, int group_id) {
        setUsername(username);
        setEmail(email);
        this.id = id;
        setGroupId(group_id);
        if ( id == 0 ) {
            setPassword(password);
        } else {
            this.password = password;
        }
    }
    
    public int getId() {
        return this.id;
    }
    
    protected User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        return this;
    }
    
    public int getGroupId() {
        return group_id;
    }
    
    public User setGroupId(int group_id) {
        this.group_id = group_id;
        return this;
    }
    
    public static enum Column implements ColumnsEnumInterface {
        ID("id"), USERNAME("username"), EMAIL("email"), PASSWORD("password"), GROUPID("user_group_id");
        
        private String name;
        
        private Column(String column) {
            this.name = column;
        }
        public String getName() {
            return name;
        }
    }
    
    @Override
    public String toString() { 
        return String.format( "[%d, %d, %s, %s]", getId(), getGroupId(), getUsername(), getEmail() );
    }

}

