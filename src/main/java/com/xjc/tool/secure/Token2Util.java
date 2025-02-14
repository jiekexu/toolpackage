package com.xjc.tool.secure;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Token2Util.java
 *
 * @author Xujc
 * @date 2021/12/8
 */
public class Token2Util {

    private static final String SECRET = "jwt_secret";

    public static JSONObject generateToken(Map<String, Object> claims) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.SECOND, 60 * 60);
        Date expiresAt = c.getTime();
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        JSONObject json = new JSONObject();
        json.put("token", "Bearer".concat(" ".concat(token)));
        json.put("token_type", "Bearer");
        json.put("expire_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expiresAt));
        return json;
    }

    public static Map<String, Object> validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace("Bearer", "").trim())
                .getBody();
    }


}
