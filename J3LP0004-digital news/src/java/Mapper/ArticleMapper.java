/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapper;

import db.DBContext;
import entity.Article;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Purpose: Mapping data of Article
 *
 * Feb 21, 2020, 9:59:13 AM
 *
 * @author viettqhe130524
 */
public class ArticleMapper implements RowMapper<Article> {

    /**
     * Purpose: Mapping data of Article with Result set 
     * Return Article if mapping success and null if error 
     * 
     * Date : Feb 21, 2020, 9:59:13 AM
     *
     * @author viettqhe130524
     */
    @Override
    public Article mapRow(ResultSet rs) {
        try {
            //create object with data from database
            int id = rs.getInt(1);
            String title = rs.getString(2);
            String image = new DBContext().getResource() + rs.getString(3);
            String content = rs.getString(4);
            Date date = rs.getDate(5);
            String author = rs.getString(6);
            Article article = new Article(id, title, image, content, date, author);

            return article;
        } catch (SQLException e) {
            System.out.println("Can't take Article");
            return null;
        }
    }

}
