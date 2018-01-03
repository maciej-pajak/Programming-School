package pl.coderslab.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.coderslab.model.Group;
import pl.coderslab.model.standards.AbstractDao;

public class GroupDao extends AbstractDao<Group> {

    private static final String LOAD_ALL_QUERY = "SELECT * FROM user_group;";
    private static final String LOAD_BY_ID_QUERY = "SELECT * FROM user_group WHERE id=?;";
    private static final String CREATE_QUERY = "INSERT INTO user_group(name) VALUES(?);";
    private static final String UPDATE_QUERY = "UPDATE user_group SET name=? WHERE id=?;";
    private static final String DELETE_QUERY = "DELETE FROM user_group WHERE id=?;";
    private static final String GET_GROUP_COUNT = "SELECT COUNT(*) FROM user_group;";
    private static final String LOAD_WITH_LIMIT = "SELECT * FROM user_group ORDER BY %s %s LIMIT ? OFFSET ?;";
    
    protected String getLoadWithLimitFormatQuery() {
        return LOAD_WITH_LIMIT;
    }
    
    @Override
    protected PreparedStatement saveNewStatement(Connection con, Group group) throws SQLException {
        String[] genereatedColumns = { Group.Column.ID.getName() };
        PreparedStatement ps = con.prepareStatement(CREATE_QUERY, genereatedColumns);
        ps.setString(1, group.getName());
        return ps;
    }
   
    @Override
    protected PreparedStatement updateExistingStatement(Connection con, Group group) throws SQLException {
        PreparedStatement ps = con.prepareStatement(UPDATE_QUERY);
        ps.setString(1, group.getName());
        ps.setInt(2, group.getId());
        return ps;
    }
    
    @Override
    protected PreparedStatement deleteStatement(Connection con, Group group) throws SQLException {
        PreparedStatement ps = con.prepareStatement(DELETE_QUERY);
        ps.setInt(1, group.getId());
        return ps;
    }
    
    @Override
    protected Group newItemFromRs(ResultSet rs) throws SQLException {
        return new Group(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    protected Group[] getNewArray(int size) {
        return new Group[size];
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
    protected String getCountQuery() {
        return GET_GROUP_COUNT;
    }
}
