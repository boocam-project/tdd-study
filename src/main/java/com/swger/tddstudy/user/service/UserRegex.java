package com.swger.tddstudy.user.service;

import com.swger.tddstudy.user.domain.UserLevel;
import com.swger.tddstudy.user.domain.UserType;
import com.swger.tddstudy.user.domain.UserVO;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class UserRegex {

    public boolean isPassword(String str){
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,15}$", str);
    }

    public boolean isUserLevel(String str){
        for(UserLevel level : UserLevel.values()){
            if(str.equals(level.name())){
                return true;
            };
        }
        return false;
    }

    public boolean isType(String str){
        for(UserType type : UserType.values()){
            if(str.equals(type.name())){
                return true;
            };
        }
        return false;
    }

    public boolean isFitLength(String str, int min, int max){
        return Pattern.matches("{"+min+","+max+"}", str);
    }

    public Map<String, Object> isJoinRegex(UserVO userVO){
        String message;
        Map<String, Object> result = new HashMap<>();
        if(!isPassword(userVO.getPassword())){
            message = "비밀번호는 비밀번호 양식은 영문+특수문자+숫자 8~15자 입니다.";
            result.put("result", false);
            result.put("message", message);
            return result;
        }
        if(!isUserLevel(userVO.getUserLevel())){
            message = "유저 레벨은 BRONZE, SILVER, GOLD 가 있습니다.";
            result.put("result", false);
            result.put("message", message);
            return result;
        }
        if(!isType(userVO.getType())){
            message = "유저 타입은 USER, ADMIN 이 있습니다.";
            result.put("result", false);
            result.put("message", message);
            return result;
        }
        if(!isFitLength(userVO.getUsername(),2, 10)){
            message = "이름을 2~10자 로 입력하십시오.";
            result.put("result", false);
            result.put("message", message);
            return result;
        }
        if(!isFitLength(userVO.getNickname(),2, 10)){
            message = "닉네임을 2~10자 로 입력하십시오.";
            result.put("result", false);
            result.put("message", message);
            return result;
        }
        result.put("result", true);
        return result;
    }

}
