/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/4/18 10:58
 */
package io.github.towelong.blog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.towelong.blog.BO.GroupBO;
import io.github.towelong.blog.BO.UserBO;
import io.github.towelong.blog.exception.http.ForbiddenException;
import io.github.towelong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    @Autowired
    private UserService userService;

    private final String secret = "OM4r1Sul";

    /**
     * access_token有效时间2小时
     * 1000 毫秒 = 1秒
     * 1000 毫秒 * 60 = 1分钟
     * 60分钟 * 24 = 1天
     * @param uid
     * @param scope
     * @return
     * @throws UnsupportedEncodingException
     */
    public String CreateAccessToken(Long uid, Long scope) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Date expireDate = this.getExpireDate(1000 * 60 * 60 * 2);
//        Date expireDate = this.getExpireDate(1000 * 60);
        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withClaim("type","access")
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * refresh_token有效时间7天
     * @param uid
     * @return
     * @throws UnsupportedEncodingException
     */
    public String CreateRefreshToken(Long uid) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Date expireDate = this.getExpireDate(1000 * 60 * 60 * 24 * 7);
        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("type", "refresh")
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    /**
     * 获得过期时间
     *
     * @param expire 过期时间
     * @return Date
     */
    private Date getExpireDate(long expire) {
        long nowTime = new Date().getTime();
        long expireTime = nowTime + expire;
        return new Date(expireTime);
    }


    /**
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public boolean VerifyToken(String token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .acceptExpiresAt(4)
                .build();
        DecodedJWT jwt = verifier.verify(token);

        Claim claim = jwt.getClaim("scope");
        Claim uid = jwt.getClaim("uid");
        if(uid.isNull()){
            throw new ForbiddenException(20002);
        }
        if(claim.isNull()){
            throw new ForbiddenException(10004);
        }
        Long scope = claim.asLong();
        Map<String, Object> user =  userService.getUserGroup(uid.asLong());
        UserBO userBO = (UserBO) user.get("user_info");
        GroupBO groupBO = (GroupBO) user.get("group");
        if(groupBO!= null && userBO != null){
            RequestHelper.getRequest().setAttribute("uid",uid.asLong());
            RequestHelper.getRequest().setAttribute("scope",scope);
            return true;
        }else {
            return false;
        }
    }

    public Long verifyRefreshTokenAndReturnUid(String refresh_token) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .acceptExpiresAt(4)
                .build();
        DecodedJWT jwt = verifier.verify(refresh_token);
        Claim uid = jwt.getClaim("uid");
        return uid.asLong();
    }

    public Long getInTokenUid(String token){
        Claim uid = this.decodeJWT(token).get("uid");
        return uid.asLong();
    }

    public Long getInTokenScope(String token) {
        Claim scope = this.decodeJWT(token).get("scope");
        return scope.asLong();
    }

    public Map<String,Claim> decodeJWT(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaims();
    }

}
