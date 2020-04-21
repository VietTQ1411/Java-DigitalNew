/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modal;

import Mapper.RowMapper;
import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: File Generic for connect database Date : Feb 21, 2020, 9:59:13 AM
 *
 * @author viettqhe130524
 */
public class GennericDAO {

    /**
     * Purpose: Set value for statement with list parameter input parameters -
     * value setting Date : Feb 21, 2020, 9:59:13 AM
     */
    private void setParameters(PreparedStatement ps, Object... paramesters) {
        try {
            for (int i = 0; i < paramesters.length; i++) {
                Object para = paramesters[i];
                ps.setObject(i + 1, para);
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    /**
     * Purpose: get list object with SQL string and return List object Date :
     * Feb 21, 2020, 9:59:13 AM
     *
     * @param <T> object have to get
     * @param sql Sql string
     * @param mapper To mapping data
     * @param paramesters value for sql string
     * @return list object or null
     */
    public <T> List<T> query(String sql, RowMapper<T> mapper, Object... paramesters) {
        List<T> result = new ArrayList<T>();
        try (Connection con = new DBContext().getConnection();
                PreparedStatement ps = (con != null) ? con.prepareStatement(sql) : null;) {
            if (ps != null) {
                // set parameters for query sql
                setParameters(ps, paramesters);
                // get data and add to list
                ResultSet rs = ps.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        result.add(mapper.mapRow(rs));
                    }
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
        return null;
    }

    /**
     * Purpose: Update database row with the sql query if update success save
     * that result and roll back if error occur Feb 21, 2020, 9:59:13 AM
     *
     * @param sql Sql string for update
     * @param paramesters value for sql string
     * @return true if update success and fail if error
     */
    public Boolean update(String sql, Object... paramesters) {
        try (Connection con = new DBContext().getConnection();) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = (con != null) ? con.prepareStatement(sql) : null;) {
                if (ps != null) {
                    //set parameters for sql string
                    setParameters(ps, paramesters);
                    ps.executeUpdate();
                    //save result if error not occur
                    con.commit();
                    con.setAutoCommit(true);
                    return true;
                }
            } catch (SQLException ex) {
                //roll back the database before update failed
                con.rollback();
                ex.printStackTrace();
                System.out.println("Statement failed!!");
            }
            con.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Purpose: inset into database and return id of record if insert success if
     * error return null
     *
     * Feb 21, 2020, 9:59:13 AM
     *
     * @param sql Sql string
     * @param paramesters value for sql string
     * @return id insert of data
     */
    public Long insert(String sql, Object... paramesters) {
        Long id = null;
        try (Connection con = new DBContext().getConnection();) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = (con != null) ? con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
                    : null;) {
                if (ps != null) {
                    //set parameters for sql string
                    setParameters(ps, paramesters);
                    ps.executeUpdate();
                    //get id
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                    //save data
                    con.commit();
                    con.setAutoCommit(true);
                    return id;
                }
            } catch (SQLException ex) {
                //delete fail command
                con.rollback();
                System.out.println("Statement failed!!");
                ex.printStackTrace();
            }
            con.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Purpose : Count number of T in database with sql string 
     * Date : Feb 21, 2020, 9:59:13 AM
     *
     * @param sql
     * @param paramesters
     * @return -1 if error and number of counting
     */
    public int count(String sql, Object... paramesters) {
        try (Connection con = new DBContext().getConnection();
                PreparedStatement ps = (con != null) ? con.prepareStatement(sql) : null;) {
            if (ps != null) {
                setParameters(ps, paramesters);
                // set parameters for query sql
                ResultSet rs = ps.executeQuery();
                // get the result
                if (rs != null) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return -1;
        }
        return -1;
    }
}
