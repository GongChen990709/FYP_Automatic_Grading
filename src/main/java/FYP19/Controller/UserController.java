package FYP19.Controller;
import FYP19.Service.UserService;
import FYP19.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl")
    private UserService userService;

    @RequestMapping("/doLogin")
    @ResponseBody //return value in JSON format and will not be resolved as a path to view resolver when added this annotation
    public Map<String, String> doLogin(@RequestBody Map<String,String> map, HttpServletRequest req){
        Map<String, String> message = userService.loginCheck(map,req);
        System.out.println(req.getSession().getServletContext().getRealPath("/upload"));
        System.out.println("Controller===="+ message);
        return message;
    }

    @RequestMapping("/doLogOut")
    @ResponseBody
    public Map<String, String> logOut(HttpServletRequest request){
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("status","true");
        request.getSession().removeAttribute(Constants.USER_SESSION);
        return resultMap;
    }





}
