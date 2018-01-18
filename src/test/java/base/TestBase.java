package base;

import hht.dragon.server.params.HttpRequestType;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

/**
 * Description.
 *
 * @author: huang
 * Date: 18-1-18
 */
public class TestBase {

    @Test
    public void testHttpType() {
        System.out.println(HttpRequestType.GET.equals("e"));
        HttpServletRequest request;
    }

}
