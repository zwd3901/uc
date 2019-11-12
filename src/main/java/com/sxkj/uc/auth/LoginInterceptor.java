package com.sxkj.uc.auth;

import com.sxkj.uc.config.JwtParam;
import com.sxkj.uc.auth.jwt.JwtConfig;
import com.sxkj.uc.service.UserService;
import com.sxkj.uc.util.ExceptionHandler;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zwd
 * 许可检查，检查请求的用户是否登录
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private JwtParam jwtParam;


    /**
     * 进入业务方法前执行，可用于认证、授权
     * @param request
     * @param response
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) {
        // 从header中获取token
        String token = request.getHeader(jwtParam.getHeader());
        if (token == null || "".equals(token)) {
            // 从请求参数中获取token
            token = request.getParameter(jwtParam.getHeader());
            // todo 还应该对此进行完善
        }
        if (token == null || "".equals(token)) {
            try {
                result(response,jwtParam.getHeader() + "不能为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        Claims claims = jwtConfig.getTokenClaims(token);
        if (claims == null || jwtConfig.isExpired(claims.getExpiration())) {
            try {
                result(response,jwtParam.getHeader() + "失效，请重新登录");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        return true;

    }

    private void result(HttpServletResponse response,String message) throws Exception {
        //重置response
        response.reset();
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter pw = response.getWriter();

        pw.write(message);

        pw.flush();
        pw.close();
    }
}
