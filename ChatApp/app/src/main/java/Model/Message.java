package Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject{

    public String getUserId(){
        return getString("UserId");
    }

    public String getBody(){
        return getString("body");
    }

    public void setUserId(String userId){
        put("userId", userId);
    }

    public void setBody(String body){
        put("body", body);
    }

}
