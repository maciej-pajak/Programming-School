package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.model.standards.AbstractDao;

public class ExerciseDao extends AbstractDao<Exercise> {

    private static final String LOAD_BY_ID_QUERY = "SELECT * FROM exercise WHERE id=?;";
    private static final String LOAD_ALL_QUERY = "SELECT * FROM exercise;";
    private static final String LOAD_BY_USER_ID_QUERY = "SELECT exercise.id, title, exercise.description FROM exercise JOIN solution ON exercise.id=solution.exercise_id JOIN user ON solution.user_id=user.id WHERE user.id=?;";
    private static final String DELETE_QUERY = "DELETE FROM exercise WHERE id=?;";
    private static final String CREATE_QUERY = "INSERT INTO exercise (title, description) VALUES(?, ?);";
    private static final String UPDATE_QUERY = "UPDATE exercise SET title=?, description=? WHERE id=?;";
    private static final String LOAD_WITH_LIMIT_ASC = "SELECT * FROM exercise ORDER BY ? ASC LIMIT ? OFFSET ?;";
    private static final String LOAD_WITH_LIMIT_DESC = "SELECT * FROM exercise ORDER BY ? DESC LIMIT ? OFFSET ?;";
    private static final String GET_EXERCISE_COUNT = "SELECT COUNT(*) FROM exercise;";
    
    public Exercise[] loadAllByUserId(Connection con, int id) throws SQLException {
        List<Exercise> solutionList = new ArrayList<Exercise>();
        try (PreparedStatement ps = con.prepareStatement(LOAD_BY_USER_ID_QUERY)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    solutionList.add(newItemFromRs(rs));
                }
            }
        }
        return solutionList.toArray(new Exercise[solutionList.size()]);
    }
    
    @Override
    protected PreparedStatement saveNewStatement(Connection con, Exercise exercise) throws SQLException {
        String[] genereatedColumns = { Exercise.Column.ID.getName() };
        PreparedStatement ps = con.prepareStatement(CREATE_QUERY, genereatedColumns);
        ps.setString(1, exercise.getTitle());
        ps.setString(2, exercise.getDescription());
        return ps;
    }
   
    @Override
    protected PreparedStatement updateExistingStatement(Connection con, Exercise exercise) throws SQLException {
        PreparedStatement ps = con.prepareStatement(UPDATE_QUERY);
        ps.setString(1, exercise.getTitle());
        ps.setString(2, exercise.getDescription());
        ps.setInt(3, exercise.getId());
        return ps;
    }
    
    @Override
    protected PreparedStatement deleteStatement(Connection con, Exercise exercise) throws SQLException {
        PreparedStatement ps = con.prepareStatement(DELETE_QUERY);
        ps.setInt(1, exercise.getId());
        return ps;
    }
    
    @Override
    protected Exercise newItemFromRs(ResultSet rs) throws SQLException {
        return new Exercise(rs.getInt("id"), rs.getString("title"), rs.getString("description"));
    }

    @Override
    protected Exercise[] getNewArray(int size) {
        return new Exercise[size];
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
        return GET_EXERCISE_COUNT;
    }
    
}
