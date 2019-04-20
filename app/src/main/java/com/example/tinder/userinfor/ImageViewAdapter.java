package com.example.tinder.userinfor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tinder.R;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Bitmap> arrayImage;

    public ImageViewAdapter(Context mContext, List<Bitmap> arrayImage) {
        this.mContext = mContext;
        this.arrayImage = (ArrayList<Bitmap>) arrayImage;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_view_image_user, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewUser);
        imageView.setImageBitmap(arrayImage.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
