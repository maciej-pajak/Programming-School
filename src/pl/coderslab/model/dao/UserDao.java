package pl.coderslab.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.model.data.User;

public class UserDao extends AbstractDao<User> {

    private static final String CREATE_USER_QUERY = "INSERT INTO user VALUES(null, ?, ?, ?, ?);";
    private static final String UPDATE_USER_QUERY = "UPDATE user SET username=?, email=?, password=? user_group_id=? WHERE id=?;";
    private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE id=?;";
    private static final String LOAD_ALL_QUERY = "SELECT * FROM user;";
    private static final String LOAD_BY_GROUP_ID_QUERY = "SELECT * FROM user WHERE user_group_id=?;";
    private static final String LOAD_BY_ID_QUERY = "SELECT * FROM user WHERE id=?;";
    private static final String LOAD_WITH_LIMIT_ASC = "SELECT * FROM user ORDER BY ? ASC LIMIT ? OFFSET ?;";
    private static final String LOAD_WITH_LIMIT_DESC = "SELECT * FROM user ORDER BY ? DESC LIMIT ? OFFSET ?;";
    private static final String GET_USERS_COUNT = "SELECT COUNT(*) FROM user;";

    public User[] loadAllByGroupId(Connection con, int id) throws SQLException {
        List<User> usersList = new ArrayList<User>();
        
        try ( PreparedStatement ps = con.prepareStatement(LOAD_BY_GROUP_ID_QUERY) ) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery() ) {
                while ( rs.next() ) {
                    usersList.add(newItemFromRs(rs));
                }
            }
        }
        return usersList.toArray(new User[usersList.size()]);
    }
    
    @Override
    protected PreparedStatement saveNewStatement(Connection con, User user) throws SQLException {
        String[] genereatedColumns = { User.Column.ID.getName() };
        PreparedStatement ps = con.prepareStatement(CREATE_USER_QUERY, genereatedColumns);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getGroupId());
        return ps;
    }
   
    @Override
    protected PreparedStatement updateExistingStatement(Connection con, User user) throws SQLException {
        PreparedStatement ps = con.prepareStatement(UPDATE_USER_QUERY);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setInt(4, user.getGroupId());
        ps.setInt(5, user.getId());
        return ps;
    }
    
    @Override
    protected PreparedStatement deleteStatement(Connection con, User user) throws SQLException {
        PreparedStatement ps = con.prepareStatement(DELETE_USER_QUERY);
        ps.setInt(1, user.getId());
        return ps;
    }
    
    @Override
    protected User newItemFromRs(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getInt("user_group_id"));
    }

    @Override
    protected User[] getNewArray(int size) {
        return new User[size];
    }  

    @Override
    protected String getLoadAllQuery() {
        return LOAD_ALL_QUERY;
    }

    @Override
    protected String getLoadByIdQuery() {
        return LOAD_BY_ID_QUERY;
    }

    @Override
    protected String getLoadWithLimitAscQuery() {
        return LOAD_WITH_LIMIT_ASC;
    }

    @Override
    protected String getLoadWithLimitDescQuery() {
        return LOAD_WITH_LIMIT_DESC;
    }

    @Override
    protected String getCountQuery() {
        return GET_USERS_COUNT;
    }
}
