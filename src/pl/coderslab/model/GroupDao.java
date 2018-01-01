package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.coderslab.model.standards.AbstractDao;
import pl.coderslab.model.standards.ColumnsEnumInterface;

public class GroupDao extends AbstractDao<Group> {

    private static final String LOAD_ALL_QUERY = "SELECT * FROM user_group;";
    private static final String LOAD_BY_ID_QUERY = "SELECT * FROM user_group WHERE id=?;";
    private static final String CREATE_QUERY = "INSERT INTO user_group(name) VALUES(?);";
    private static final String UPDATE_QUERY = "UPDATE user_group SET name=? WHERE id=?;";
    private static final String DELETE_QUERY = "DELETE FROM user_group WHERE id=?;";
    private static final String LOAD_WITH_LIMIT_ASC = "SELECT * FROM user_group ORDER BY ? ASC LIMIT ? OFFSET ?;";
    private static final String LOAD_WITH_LIMIT_DESC = "SELECT * FROM user_group ORDER BY ? DESC LIMIT ? OFFSET ?;";
    private static final String GET_GROUP_COUNT = "SELECT COUNT(*) FROM user_group;";

    public static enum Column implements ColumnsEnumInterface {
        ID("id"), NAME("name");
        
        private String name;
        
        private Column(String column) {
            this.name = column;
        }
        public String getName() {
            return name;
        }
    }
    
    @Override
    protected PreparedStatement saveNewStatement(Connection con, Group group) throws SQLException {
        String[] genereatedColumns = { GroupDao.Column.ID.getName() };
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
    protected String getLoadWithLimitAscQuery() {
        return LOAD_WITH_LIMIT_ASC;
    }

    @Override
    protected String getLoadWithLimitDescQuery() {
        return LOAD_WITH_LIMIT_DESC;
    }

    @Override
    protected String getCountQuery() {
        return GET_GROUP_COUNT;
    }
}
