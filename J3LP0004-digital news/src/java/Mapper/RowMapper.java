/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapper;

import java.sql.ResultSet;

/**
 * purpose : Mapping with T object value
 * Date : Feb 21, 2020, 9:59:13 AM
 * @author viettqhe130524
 * @param <T> object mapping
 */
public interface RowMapper<T> {
 

    /**
     * purpose: mapping with T object with the result set 
     * Date : Feb 21, 2020, 9:59:14 AM
     * @param rs Result set corresponding an object
     * @return T object 
     */
   T mapRow(ResultSet rs);
}
