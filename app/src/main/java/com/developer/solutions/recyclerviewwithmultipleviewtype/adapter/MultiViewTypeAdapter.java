package com.developer.solutions.recyclerviewwithmultipleviewtype.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.solutions.recyclerviewwithmultipleviewtype.R;
import com.developer.solutions.recyclerviewwithmultipleviewtype.model.Data;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mukesh on 3/8/17.
 * himky02@gmail.com
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Data> dataSet;
    Context mContext;
    int total_types;
    ArrayList<String> slider_image_list;
    int page = 0;

    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_dots)
        LinearLayout ll_dots;
        @BindView(R.id.vp_slider)
        ViewPager mvViewPager;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type)
        TextView tvtype;
        @BindView(R.id.img)
        ImageView iv;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ButtonTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.btn)
        Button btn;

        public ButtonTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public MultiViewTypeAdapter(ArrayList<Data> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Data.VIEW_PAGER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager, parent, false);
                return new TextTypeViewHolder(view);
            case Data.IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_type, parent, false);
                return new ImageTypeViewHolder(view);
            case Data.AUDIO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button_type, parent, false);
                return new ButtonTypeViewHolder(view);
        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return Data.VIEW_PAGER;
            case 1:
                return Data.IMAGE_TYPE;
            case 2:
                return Data.AUDIO_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        Data object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case Data.VIEW_PAGER:
                    //((TextTypeViewHolder) holder).type.setText(object.text);
                    slider_image_list = new ArrayList<>();

                    //Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
                    // here i am adding few sample image links, you can add your own

                    slider_image_list.add("http://cdn.collider.com/wp-content/uploads/avengers-movie-banner-scarlett-johansson-jeremy-renner.jpg");
                    slider_image_list.add("http://www.officialterridwyer.com/wp-content/uploads/2015/04/Disneys-Cinderella-2015-Movie-Banner.jpg");
                    slider_image_list.add("http://igmedia.blob.core.windows.net/igmedia/hindi/gallery/movies/raabta/main1.jpg");
                    slider_image_list.add("http://fantoosy.com/wp-content/uploads/2015/11/tamasha.jpg");
                    final SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter((Activity) mContext, slider_image_list);
                    ((TextTypeViewHolder) holder).mvViewPager.setAdapter(sliderPagerAdapter);

                    ((TextTypeViewHolder) holder).mvViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            addBottomDots(position, ((TextTypeViewHolder) holder).ll_dots);
                            page = position;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    addBottomDots(0, ((TextTypeViewHolder) holder).ll_dots);
                    final Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (sliderPagerAdapter.getCount() == page) {
                                page = 0;
                            } else {
                                page++;
                            }
                            ((TextTypeViewHolder) holder).mvViewPager.setCurrentItem(page);
                            h.postDelayed(this, 2000);
                        }
                    }, 1000);

                    break;
                case Data.IMAGE_TYPE:
                    ((ImageTypeViewHolder) holder).tvtype.setText(object.text);
                    ((ImageTypeViewHolder) holder).iv.setImageResource(object.data);
                    break;
                case Data.AUDIO_TYPE:
                    ((ButtonTypeViewHolder) holder).type.setText(object.text);
                    ((ButtonTypeViewHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(mContext, "You clicked!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    //showing dots on screen
    private void addBottomDots(int currentPage, LinearLayout ll_dots) {
        TextView[] dots = new TextView[slider_image_list.size()];
        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(mContext);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#343434"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#A2A2A2"));
    }

}
