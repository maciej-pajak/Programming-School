package pl.coderslab.model;

import org.mindrot.jbcrypt.BCrypt;

import pl.coderslab.model.standards.ColumnsEnumInterface;
import pl.coderslab.model.standards.DataType;

public class User extends DataType<User> {
    
    private int id = 0;
    private String username;
    private String email;
    private String password;
    private int groupId;
    
    public User(String username, String email, String password, int groupId) {
        this(0, username, email, password, groupId);
    }
    
    public User(int id, String username, String email, String password, int groupId) {
        setUsername(username);
        setEmail(email);
        setId(id);
        setGroupId(groupId);
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
        return groupId;
    }
    
    public User setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public enum Column implements ColumnsEnumInterface {
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

