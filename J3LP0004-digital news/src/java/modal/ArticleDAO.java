package modal;

import Mapper.ArticleMapper;
import entity.Article;
import java.util.List;

/**
 * Connect data base for Article 
 * Date : Feb 21, 2020, 9:59:13 AM
 * GennericDAO : class base for sql
 * @author viettqhe130524
 */
public class ArticleDAO extends GennericDAO {

    /**
     * Get top of Article with number of Article will be get
     * 
     * Date : Feb 21, 2020, 9:59:13 AM
     * @param numberArticle - number will be get
     * @return list Article
     * @throws Exception
     */
    public List<Article> getRecentArticle(int numberArticle) throws Exception {

        String sql = "SELECT TOP (?) * "
                + "FROM Article \n"
                + "ORDER BY Date DESC";

        return query(sql, new ArticleMapper(), numberArticle);
    }

    /**
     * Get detail of one Article by ID
     * Date : Feb 21, 2020, 9:59:13 AM
     * @param id - id of Article 
     * @return Article object if id exist in database
     * @throws Exception
     */
    public Article getArticleById(int id) throws Exception {
        String sql = "SELECT id,title, image, content, date, author FROM Article WHERE id = ?";

        List<Article> Article = query(sql, new ArticleMapper(), id);
        return Article.isEmpty() ? null : Article.get(0);
    }

    /**
     *  Get List of Article with String key word and numberArticleInPage
     * Date : Feb 21, 2020, 9:59:13 AM
     * @param numberArticleInPage number max of Article in one page
     * @param pageCurrent 
     * @param keyword Search string
     * @return  list of search or null
     * @throws Exception
     */
    public List<Article> getListAticleSearch(int numberArticleInPage, int pageCurrent, String keyword) throws Exception {

        String sql = "SELECT id,title, image, content, date, author FROM (\n"
                + "SELECT ROW_NUMBER()\n"
                + "OVER(ORDER BY id) as Number,\n"
                + "* FROM Article \n"
                + "WHERE content LIKE ? OR title LIKE ? \n"
                + ") as DataSearch where Number >= ? and Number <= ?";

        int articleFrom = (pageCurrent - 1) * numberArticleInPage + 1 ;
        int articleTo = pageCurrent * numberArticleInPage;

        keyword = "%" + keyword + "%";

        return query(sql, new ArticleMapper(), keyword, keyword, articleFrom, articleTo);
    }

    /**
     *  Count the number of page with keyword string 
     * Date : Feb 21, 2020, 9:59:13 AM
     * @param numberArticleInPage
     * @param keyword
     * @return
     * @throws Exception
     */
    public int getNumberPage(int numberArticleInPage, String keyword) throws Exception {

        String sql = "SELECT COUNT(id) FROM Article \n"
                + "WHERE content\n"
                + "LIKE ? OR title LIKE ?";
        keyword = "%" + keyword + "%";
        int numberArticle = count(sql, keyword, keyword);
        if (numberArticle != -1) {
            return (numberArticle + (numberArticle % numberArticleInPage)) / numberArticleInPage;
        } else {
            return numberArticle;
        }
    }
}
