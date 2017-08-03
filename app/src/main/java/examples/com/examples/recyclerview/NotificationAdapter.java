package examples.com.examples.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import my.app.educationinstitutes.R;
import my.app.educationinstitutes.models.NotificationsModel;
import my.app.educationinstitutes.utils.OnLoadMoreListener;
import my.app.educationinstitutes.utils.UtilsConstants;

/**
 * Created by Rajesh on 12/15/2016.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NotificationsModel> list;
    Context mContext;
    Typeface FONT_LIGHT, FONT_REGULAR, FONT_LIGHT_ITALIC, FONT_BOLD;

    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    RecyclerView mRecyclerView;


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView noti_message, date;
        ImageView image;
        LinearLayout linearLayout1;
        HorizontalScrollView HSV;
        //RelativeLayout inst_layout;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            noti_message = (TextView) view.findViewById(R.id.noti_message);
            date = (TextView) view.findViewById(R.id.date);


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "rrr   " + getPosition(), Toast.LENGTH_SHORT).show();

        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public NotificationAdapter(Context mContext, ArrayList<NotificationsModel> list, RecyclerView mRecyclerView) {
        this.list = list;
        this.mRecyclerView = mRecyclerView;
        this.mContext = mContext;
        FONT_LIGHT = Typeface.createFromAsset(mContext.getAssets(), UtilsConstants.Custom_Font_Light);
        FONT_REGULAR = Typeface.createFromAsset(mContext.getAssets(), UtilsConstants.Custom_Font_Regular);
        FONT_BOLD = Typeface.createFromAsset(mContext.getAssets(), UtilsConstants.Custom_Font_bold);
        FONT_LIGHT_ITALIC = Typeface.createFromAsset(mContext.getAssets(), UtilsConstants.Custom_Font_Light_italic);

        //listener
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.custom_notifications_list, parent, false);

            // View view = inflater.from(mContext).inflate(R.layout.layout_user_item, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = inflater.inflate(R.layout.layout_loading_item, parent, false);
            //View view = inflater.from(mContext).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MyViewHolder) {

            NotificationsModel mm = list.get(position);
            MyViewHolder userViewHolder = (MyViewHolder) holder;
            userViewHolder.noti_message.setText(mm.getNoti_message());
            userViewHolder.date.setText(mm.getDate());

            setCustomFont(userViewHolder);
        } else if (holder instanceof LoadingViewHolder) {

            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setCustomFont(MyViewHolder holder) {

        holder.noti_message.setTypeface(FONT_REGULAR);
        holder.date.setTypeface(FONT_REGULAR);

    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}

