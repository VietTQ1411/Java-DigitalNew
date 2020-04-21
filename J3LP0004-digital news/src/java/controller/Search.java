package controller;

import entity.Article;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modal.ArticleDAO;

/**
 * Purpose : Search Article with the value user input into 
 * AM Date : Feb 21, 2020, 9:59:13
 *
 * @author viettqhe130524
 */
public class Search extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            //number of article in the page
            final int ARTICLE_PAGE = 2;
            HttpSession session = request.getSession();

            //get data 
            int pageCurrent = Integer.parseInt(request.getParameter("page"));
            String keyword = (String) session.getAttribute("keyword");
            
            // get most and five recent article
            ArticleDAO articles = new ArticleDAO();
            Article mostRecentArticle = articles.getRecentArticle(1).get(0);
            request.setAttribute("mostRecentArticle", mostRecentArticle);
            List<Article> fiveRecentAticle = articles.getRecentArticle(5);
            request.setAttribute("fiveRecentAticle", fiveRecentAticle);

            // get list article found
            List<Article> listSearch = articles.getListAticleSearch(ARTICLE_PAGE, pageCurrent, keyword);
            //not found record
            if (listSearch.isEmpty()) {
                request.setAttribute("result", false);
                request.getRequestDispatcher("/search.jsp").forward(request, response);
            }
            // get number page to paging
            int numberPage = articles.getNumberPage(ARTICLE_PAGE, keyword);
            request.setAttribute("numberPage", numberPage);

            // get page current
            request.setAttribute("pageCurrent", pageCurrent);

            //return when found record
            request.setAttribute("listSearch", listSearch);
            
            request.setAttribute("result", true);
            request.getRequestDispatcher("/search.jsp").forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String keyword = request.getParameter("keyword");
            // check keyword empty
            if (keyword == null || keyword.isEmpty()) {
                //delete the keyword
                HttpSession session = request.getSession();
                session.setAttribute("keyword", null);
                // refresh page when search string not found
                String servletPrev = request.getHeader("referer");
                String nameServletPrev = servletPrev.substring(servletPrev.lastIndexOf("/") + 1);
                
                response.sendRedirect(nameServletPrev);
            } else {
                //set the value of search string into session
                HttpSession session = request.getSession();
                session.setAttribute("keyword", keyword);
                //return search page when have string
                response.sendRedirect("Search?page=1");
            }
        } catch (IOException e) {
            request.setAttribute("error", "Sorry! Error occurred");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
