
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 描述.
 *
 * @author: huang
 * Date: 2018/4/20
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("运行servlet");
        Map<String, String[]> map = req.getParameterMap();
        for (String ss : map.keySet()) {
            System.out.println(ss + " : " + map.get(ss));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("运行servlet service");
        Map<String, String[]> map = req.getParameterMap();
        for (String ss : map.keySet()) {
            System.out.println(ss + " : " + map.get(ss));
        }
    }
}
