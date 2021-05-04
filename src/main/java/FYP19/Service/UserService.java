package FYP19.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    Map<String,String> loginCheck(Map<String,String> map, HttpServletRequest req);
}
