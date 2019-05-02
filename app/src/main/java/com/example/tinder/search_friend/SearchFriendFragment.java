package com.example.tinder.search_friend;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.tinder.R;
import com.example.tinder.authentication.UserAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFriendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ViewPager pgSearchFriend;

    private ImageButton btnLike;

    public SearchFriendFragment() {
        // Required empty public constructor
    }

    // AsyncTask<Params, Progress, Result>
    public static class DownloadImageTask  extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public DownloadImageTask(ImageView imageView)  {
            this.imageView= imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageUrl = params[0];

            InputStream in = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                int resCode = httpConn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();
                } else {
                    return null;
                }

                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        // Khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi.
        // Download thành công, update kết quả lên giao diện.
        @Override
        protected void onPostExecute(Bitmap result) {
            if(result  != null){
                this.imageView.setImageBitmap(result);
            } else{
                Log.e("MyMessage", "Failed to fetch data!");
            }
        }
    }

    public static class DownloadJsonTask extends AsyncTask<String, Void, String> {

       private ArrayList databuffer;

        public DownloadJsonTask(ArrayList databuffer)  {
            this.databuffer = databuffer;
        }

        @Override
        protected String doInBackground(String... params) {
            String textUrl = params[0];
            InputStream in = null;
            BufferedReader br= null;
            try {
                URL url = new URL(textUrl);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                int resCode = httpConn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();
                    br= new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb= new StringBuilder();
                    String s= null;
                    while((s= br.readLine())!= null) {
                        sb.append(s);
                        sb.append("\n");
                    }
                    return sb.toString();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // Khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi.
        // Download thành công, update kết quả lên giao diện.
        @Override
        protected void onPostExecute(String result) {
            if(result  != null){
                String jsonText = new String(result);
                JSONObject userObj = null;
                try {
                    //đọc và chuyển về JSONObject
                    userObj = new JSONObject(jsonText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < userObj.length(); i++) {
                    if (userObj.has("user"+i)){
                        try {
                            this.databuffer.add(userObj.getJSONObject("user"+i));
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else{
                Log.e("MyMessage", "Failed to fetch data!");
            }
        }
    }

    public static boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            Log.d("check connect", "Connect true");
            return true;
        } catch (Exception e) {
            Log.e("check connect", e.toString());
            return false;
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFriendFragment newInstance(String param1, String param2) {
        SearchFriendFragment fragment = new SearchFriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        isConnectedToServer("http://167.99.69.92/upload/35_image2.jpg?dim=700x875", 10000 );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_search_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents(view);
    }

    private void addControls(View view) {
        pgSearchFriend = view.findViewById(R.id.pgSearchFriend);
        SearchFriendPagerAdapter adapter = new SearchFriendPagerAdapter(this.getContext());
        pgSearchFriend.setAdapter(adapter);
        pgSearchFriend.setCurrentItem(SearchFriendPagerAdapter.PAGE_NUM / 2, false);

        btnLike = view.findViewById(R.id.btnLike);
    }

    private void addEvents(final View view) {
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("like", "like");
                FriendView page = (FriendView) pgSearchFriend.findViewWithTag(pgSearchFriend.getCurrentItem());
                page.likeFriend(UserAuth.getInstance().getUser().getId());
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
