package Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sot_e.chatapp.ChatActivity;
import com.example.sot_e.chatapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import Model.Message;

public class ChatAdaptor extends ArrayAdapter<Message> {

    private String mUserId;

    public ChatAdaptor(Context context, String userId , ArrayList<Model.Message> messages) {
        super(context, 0, messages);
        mUserId = userId;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_row, parent , false);

            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView) convertView.findViewById(R.id.ProfileLeftId);
            holder.imageRight = (ImageView) convertView.findViewById(R.id.ProfileRightId);
            holder.body = (TextView) convertView.findViewById(R.id.BodyId);
            convertView.setTag(holder);

        }

        final Message message = (Message) getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserId);

        if(isMe){

            holder.imageRight.setVisibility(View.VISIBLE);
            holder.imageLeft.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        }else{

            holder.imageRight.setVisibility(View.GONE);
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

        }
                                    //IF(?) isMe is true this is going to happen ELSE(:) the second statement is going to happen
        final ImageView profileView = isMe ? holder.imageRight : holder.imageLeft;
        Picasso.get().load(getProfileGravatar(message.getUserId())).into(profileView);
        holder.body.setText(message.getBody());


        return convertView;
    }

    private String getProfileGravatar(String userId) {

        String hex = "";

        try{
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userId.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return "http://www.gravatar.com/avatar/" + hex +"?d=identicon";
    }

    class ViewHolder{

        public ImageView imageLeft , imageRight;
        public TextView body;

    }
}
