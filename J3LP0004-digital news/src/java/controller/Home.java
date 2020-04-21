package controller;

import entity.Article;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modal.ArticleDAO;

/**
 *  Purpose : Show home page and the Article newest into Home page
 * 
 * Feb 21, 2020, 9:59:13 AM
 * @author viettqhe130524
 */
public class Home extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // get most and five recent article
            ArticleDAO articles = new ArticleDAO();
            //Article latest
            Article mostRecentArticle = articles.getRecentArticle(1).get(0);
            request.setAttribute("mostRecentArticle", mostRecentArticle);
            request.setAttribute("articleCurrent", mostRecentArticle);
            List<Article> fiveRecentAticle = articles.getRecentArticle(5);
            request.setAttribute("fiveRecentAticle", fiveRecentAticle);

            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}
