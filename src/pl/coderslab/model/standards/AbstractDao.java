package pl.coderslab.model.standards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.coderslab.model.DbUtil;

public abstract class AbstractDao<T extends DataType<T>> implements DaoInterface<T> {
    
    protected abstract T newItemFromRs(ResultSet rs) throws SQLException;
    protected abstract T[] getNewArray(int size);
    
    protected abstract String getLoadAllQuery();
    protected abstract String getCountQuery();
    protected abstract String getLoadByIdQuery();
    
    protected abstract PreparedStatement saveNewStatement(Connection con, T item) throws SQLException;
    protected abstract PreparedStatement updateExistingStatement(Connection con, T item) throws SQLException;
    protected abstract PreparedStatement deleteStatement(Connection con, T item) throws SQLException;
    
    public T[] loadAll() {
        List<T> list = new ArrayList<>();
        try (Connection con = DbUtil.getConn()) {
            try ( ResultSet rs = con.prepareStatement( getLoadAllQuery() ).executeQuery() ) {
                while ( rs.next() ) {
                    list.add( newItemFromRs(rs) );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }
        return list.toArray( getNewArray(list.size()) );
    }

    protected abstract String getLoadWithLimitFormatQuery();
    
    public T[] loadSortedWithLimit(ColumnsEnumInterface sortColumnName, DaoInterface.SortType sortType, int limit, int offset) {
        List<T> list = new ArrayList<>();
        String sqlFormat = getLoadWithLimitFormatQuery();
        try (Connection con = DbUtil.getConn()) {
            try ( PreparedStatement ps = con.prepareStatement(String.format(sqlFormat, sortColumnName.getName(), sortType.getName())) ) {
                ps.setInt(1, limit);
                ps.setInt(2, offset);
                try (ResultSet rs = ps.executeQuery()) {
                    while ( rs.next() ) {
                        list.add( newItemFromRs(rs) );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }
        return list.toArray( getNewArray(list.size()) );
    }
    
    public int getCount() { 
        int count = -1;
        try (Connection con = DbUtil.getConn()) {
            try ( ResultSet rs = con.prepareStatement( getCountQuery() ).executeQuery() ) {
                while ( rs.next() ) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }
        return count;
    }
    
    public T loadById(int id) {
        try (Connection con = DbUtil.getConn()) {
            try ( PreparedStatement ps = con.prepareStatement( getLoadByIdQuery() ) ) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                
                if ( rs.next() ) {
                    return newItemFromRs(rs);
                }
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }
        return null;
    }
    
    public void saveToDb(T item) {
        try (Connection con = DbUtil.getConn()) {
            if ( item.getId() == 0 ) {
                saveNewToDb(con, item);
            } else {
                updateExistingInDb(con, item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }
    }
    
    private void saveNewToDb(Connection con, T item) throws SQLException {
        try ( PreparedStatement ps = saveNewStatement(con, item) ) {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if ( rs.next() ) {
                item.setId(rs.getInt(1));
            }
            rs.close();
        }
    }
    
    private void updateExistingInDb(Connection con, T item) throws SQLException {
        try ( PreparedStatement ps = updateExistingStatement(con, item) ) {
            ps.executeUpdate();
        }
    }
    
    public void delete(T item) {
        try (Connection con = DbUtil.getConn()) {
            if ( item.getId() != 0 ) {
                try (PreparedStatement ps = deleteStatement(con, item) ) {
                    ps.executeUpdate();
                }
                item.setId(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error: " + e.getMessage());
        }
    }
 
}

