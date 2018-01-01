package pl.coderslab.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.model.data.Solution;

public class SolutionDao extends AbstractDao<Solution> {

    private static final String LOAD_BY_ID_QUERY = "SELECT * FROM solution WHERE id=?;";
    private static final String LOAD_ALL_QUERY = "SELECT * FROM solution;";
    private static final String LOAD_BY_USER_ID_QUERY = "SELECT * FROM solution WHERE user_id=?;";
    private static final String LOAD_UNSOLVED_BY_USER_ID_QUERY = "SELECT * FROM solution WHERE user_id=? AND updated IS NULL;";
    private static final String DELETE_QUERY = "DELETE FROM solution WHERE id=?;";
    private static final String CREATE_QUERY = "INSERT INTO solution (created, user_id, exercise_id) VALUES(?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE solution SET updated=?, description=? WHERE id=?;";
    private static final String LOAD_BY_EXERCISE_ID_QUERY = "SELECT * FROM solution JOIN exercise ON solution.id=exercise.soultion_id WHERE exercise.id=?;";
    private static final String LOAD_WITH_LIMIT_ASC = "SELECT * FROM solution ORDER BY ? ASC LIMIT ? OFFSET ?;";
    private static final String LOAD_WITH_LIMIT_DESC = "SELECT * FROM solution ORDER BY ? DESC LIMIT ? OFFSET ?;";
    private static final String GET_SOLUTION_COUNT = "SELECT COUNT(*) FROM solution;";

    public Solution[] loadAllByUserId(Connection con, int id) throws SQLException {
        List<Solution> solutionList = new ArrayList<Solution>();
        
        try ( PreparedStatement ps = con.prepareStatement(LOAD_BY_USER_ID_QUERY) ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                solutionList.add(newItemFromRs(rs));
            }
            rs.close();
        }
        return solutionList.toArray(new Solution[solutionList.size()]);
    }
    
    public Solution[] loadUnsolvedByUserId(Connection con, int id) throws SQLException {
        List<Solution> exerciseList = new ArrayList<Solution>();
        try ( PreparedStatement ps = con.prepareStatement(LOAD_UNSOLVED_BY_USER_ID_QUERY) ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                exerciseList.add(newItemFromRs(rs));
            }
            rs.close();
        }
        return exerciseList.toArray(new Solution[exerciseList.size()]);
    }
    
    public Solution[] loadAllByExerciseId(Connection con, int id) throws SQLException {
        List<Solution> exerciseList = new ArrayList<Solution>();
        try ( PreparedStatement ps = con.prepareStatement(LOAD_BY_EXERCISE_ID_QUERY) ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                exerciseList.add(newItemFromRs(rs));
            }
            rs.close();
        }
        return exerciseList.toArray(new Solution[exerciseList.size()]);
    }
    
    @Override
    protected PreparedStatement saveNewStatement(Connection con, Solution solution) throws SQLException {
        String[] genereatedColumns = { Solution.Column.ID.getName() };
        PreparedStatement ps = con.prepareStatement(CREATE_QUERY, genereatedColumns);
        solution.setCreated(LocalDateTime.now());
        ps.setString(1, solution.getCreated());
        ps.setInt(2, solution.getUserId());
        ps.setInt(3, solution.getExerciseId());
        return ps;
    }
   
    @Override
    protected PreparedStatement updateExistingStatement(Connection con, Solution solution) throws SQLException {
        PreparedStatement ps = con.prepareStatement(UPDATE_QUERY);
        solution.setUpdated(LocalDateTime.now());
        ps.setString(1, solution.getUpdated());
        ps.setString(2, solution.getDescription());
        ps.setInt(3, solution.getId());
        return ps;
    }
    
    @Override
    protected PreparedStatement deleteStatement(Connection con, Solution solution) throws SQLException {
        PreparedStatement ps = con.prepareStatement(DELETE_QUERY);
        ps.setInt(1, solution.getId());
        return ps;
    }
    
    @Override
    protected Solution newItemFromRs(ResultSet rs) throws SQLException {
        return new Solution(    rs.getInt("id"),
                                rs.getString("created"),
                                rs.getString("updated"),
                                rs.getString("description"),
                                rs.getInt("user_id"),
                                rs.getInt("exercise_id"));
    }

    @Override
    protected Solution[] getNewArray(int size) {
        return new Solution[size];
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
        return GET_SOLUTION_COUNT;
    }
}
