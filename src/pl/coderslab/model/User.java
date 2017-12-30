package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    
    private static final String CREATE_USER_QUERY = "INSERT INTO user VALUES(null, ?, ?, ?, ?);";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET username=?, email=?, password=? user_group_id=? WHERE id=?;";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id=?;";
    private static final String LOAD_ALL_QUERY = "SELECT * FROM user;";
    private static final String LOAD_BY_GROUP_ID_QUERY = "SELECT * FROM user WHERE user_group_id=?;";
    private static final String LOAD_BY_ID_QUERY = "SELECT * FROM user WHERE id=?;";
    private static final String LOAD_WITH_LIMIT_ASC = "SELECT * FROM user ORDER BY ? ASC LIMIT ? OFFSET ?;";
    private static final String LOAD_WITH_LIMIT_DESC = "SELECT * FROM user ORDER BY ? DESC LIMIT ? OFFSET ?;";
    private static final String GET_USERS_COUNT = "SELECT COUNT(*) FROM user;";
    
    private int id = 0;
    private String username;
    private String email;
    private String password;
    private int group_id;
    
    public User(String username, String email, String password, int group_id) {
        this(0, username, email, password, group_id);
    }
    
    private User(int id, String username, String email, String password, int group_id) {
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
    
    private User(ResultSet rs) throws SQLException {
        this( rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getInt("user_group_id") );
    }
    
    public int getId() {
        return this.id;
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

    private String getPassword() {
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
    
    public void saveToDb(Connection con) throws SQLException {
        if ( this.id == 0 ) {   // create new
            saveNewToDb(con);
        } else {                // update existing
            updateExistingInDb(con);
        }
    }
    
    private void saveNewToDb(Connection con) throws SQLException {
        String[] genereatedColumns = { "id" };
        try ( PreparedStatement ps = con.prepareStatement(CREATE_USER_QUERY, genereatedColumns) ) {
            ps.setString(1, this.getUsername());
            ps.setString(2, this.getEmail());
            ps.setString(3, this.getPassword());
            ps.setInt(4, this.getGroupId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if ( rs.next() ) {
                this.id = rs.getInt(1);
            }
            rs.close();
        }
    }
    
    private void updateExistingInDb(Connection con) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement(UPDATE_USER_QUERY) ) {
            ps.setString(1, this.getUsername());
            ps.setString(2, this.getEmail());
            ps.setString(3, this.getPassword());
            ps.setInt(4, this.getGroupId());
            ps.setInt(5, this.getId());
            ps.executeUpdate();
        }
    }  
    
    public void delete(Connection con) throws SQLException {
        if ( this.id != 0 ) {
            try ( PreparedStatement ps = con.prepareStatement(DELETE_USER_QUERY) ) {
                ps.setInt(1, this.id);
                ps.executeUpdate();
                this.id = 0;
            }
        }
    }
    
    public static User[] loadAll(Connection con) throws SQLException {
        List<User> usersList = new ArrayList<User>();
        
        try ( ResultSet rs = con.prepareStatement(LOAD_ALL_QUERY).executeQuery() ) {
            while ( rs.next() ) {
                usersList.add(new User(rs));
            }
        }
        return usersList.toArray(new User[usersList.size()]);
    }
    
    public static User loadById(Connection con, int id) throws SQLException {
        User u = null;
        try ( PreparedStatement ps = con.prepareStatement(LOAD_BY_ID_QUERY) ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if ( rs.next() ) {
                u = new User(rs);
            }
            rs.close();
        }
        return u;
    }
    
    public static User[] loadSortedWithLimit(Connection con, Column sortColumnName, SortType sortType, int limit, int offset) throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = sortType.equals(SortType.ASC) ? LOAD_WITH_LIMIT_ASC : LOAD_WITH_LIMIT_DESC;
        
        try ( PreparedStatement ps = con.prepareStatement(sql) ) {
            ps.setString(1, sortColumnName.getName());
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while ( rs.next() ) {
                    userList.add( new User(rs) );
                }
            }
        }
        return userList.toArray(new User[userList.size()]);
    }
    
    public static User[] loadAllByGroupId(Connection con, int id) throws SQLException {
        List<User> usersList = new ArrayList<User>();
        
        try ( PreparedStatement ps = con.prepareStatement(LOAD_BY_GROUP_ID_QUERY) ) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery() ) {
                while ( rs.next() ) {
                    usersList.add(new User(rs));
                }
            }
        }
        return usersList.toArray(new User[usersList.size()]);
    }
    
    public static int getUsersCount(Connection con) throws SQLException { 
        int count = -1;
        try ( ResultSet rs = con.prepareStatement(GET_USERS_COUNT).executeQuery() ) {
            while ( rs.next() ) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
    
    public static enum Column {
        ID("id"), USERNAME("username"), EMAIL("email"), GROUP_ID("user_group_id");
        
        private String name;
        
        private Column(String column) {
            this.name = column;
        }
        public String getName() {
            return name;
        }
    }
    
    public static enum SortType {
        ASC("ASC"), DESC("DESC");
        
        private String type;
        
        private SortType(String type) {
            this.type = type;
        }
        public String getName() {
            return type;
        }
    }
    
    @Override
    public String toString() { 
        return String.format( "[%d, %d, %s, %s]", getId(), getGroupId(), getUsername(), getEmail() );
    }

}

