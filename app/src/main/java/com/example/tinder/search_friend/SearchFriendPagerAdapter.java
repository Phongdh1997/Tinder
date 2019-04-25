package com.example.tinder.search_friend;

import android.arch.core.util.Function;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONException;
import java.io.Reader;
import java.net.URL;

import com.example.tinder.R;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

import androidx.navigation.Navigation;

public class SearchFriendPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> dataBuff;
    private boolean isLoading;

//    public static class MyJsonReader {
//        public static String readAll(Reader rd) throws IOException {
//            StringBuilder sb = new StringBuilder();
//            int cp;
//            while ((cp = rd.read()) != -1) {
//                sb.append((char) cp);
//            }
//            return sb.toString();
//        }
//        /**
//         * Hàm trả về JSONObject
//         * @param url - Truyền link URL có định dạng JSON
//         * @return - Trả về JSONOBject
//         * @throws IOException
//         * @throws JSONException
//         */
//        public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
//            InputStream is = new URL(url).openStream();
//            try {
//                //đọc nội dung với Unicode:
//                BufferedReader rd = new BufferedReader
//                        (new InputStreamReader(is, Charset.forName("UTF-8")));
//                String jsonText = readAll(rd);
//                JSONObject json = new JSONObject(jsonText);
//                return json;
//            } finally {
//                is.close();
//            }
//        }
//    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br= new BufferedReader(new InputStreamReader(is));
        StringBuilder sb= new StringBuilder();
        String s= null;
        while((  s = br.readLine())!=null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public SearchFriendPagerAdapter(Context context) {
        this.context = context;
        this.isLoading = false;
        dataBuff = new ArrayList<>();

        dataBuff = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            dataBuff.add("item" + i);
        }

//        String url = "https://jsoneditoronline.org/?id=f65ef4edea3b4feda2aba75b58ae12a2";
//        JSONObject jsonObject = new JSONObject();
//        JSONObject userObj = new JSONObject();
//        try {
//            //đọc và chuyển về JSONObject
//            jsonObject = MyJsonReader.readJsonFromUrl(url);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        for (int i = 0; i < jsonObject.length(); i++) {
//            try {
//                userObj = jsonObject.getJSONObject("user"+i);
//            } catch(JSONException e) {
//                e.printStackTrace();
//            }
//            if (userObj.has("user"+i)){
//                try {
//                    dataBuff.add(userObj.getJSONObject("user"+i).getString("name"));
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_search_friend_item, container, false);

        // add controls
        TextView txtName = view.findViewById(R.id.txtName);
        ImageButton btnDetailInfo = view.findViewById(R.id.btnDetailInfo);

        // add event
        btnDetailInfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userInforFragment, null));

        // set first item to view and remove it from buffer
        if (!this.isBufferEmpty()) {
            txtName.setText(this.dataBuff.get(0));
            this.dataBuff.remove(0);
        }

        if (this.isExhaustedBuff()) {
            this.loadData();
        }

        // add view to container
        container.addView(view);
        return view;
    }

    private boolean isExhaustedBuff() {
        return this.dataBuff.size() < 20;
    }

    public boolean isBufferEmpty() {
        return this.dataBuff.size() < 1;
    }

    private void loadData() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        Log.d("Load", "new item");
        String jsonText= "";
        try {
            //đọc và chuyển về JSONObject
            jsonText = readText(context, R.raw.usertest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject userObj = new JSONObject();
        try {
            //đọc và chuyển về JSONObject
            userObj = new JSONObject(jsonText);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < userObj.length(); i++) {
            if (userObj.has("user"+i)){
                try {
                    this.dataBuff.add(userObj.getJSONObject("user"+i).getString("name"));
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // update isLoading = false when load data success
        this.isLoading = false;
    }

    @Override
    public int getCount() {
        return 2000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        Log.d("remove", "item removed");
        collection.removeView((View) view);
    }
}