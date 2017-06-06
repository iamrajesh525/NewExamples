package examples.com.examples.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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


/**
 * Created by Rajesh on 12/15/2016.
 */

public class InstituteViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<InstituteListModel> list;
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
        public TextView name, mobile, description, no_views;
        ImageView image;
        LinearLayout linearLayout1;
        HorizontalScrollView HSV;
        //RelativeLayout inst_layout;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            mobile = (TextView) view.findViewById(R.id.mobile);
            description = (TextView) view.findViewById(R.id.description);
            no_views = (TextView) view.findViewById(R.id.no_views);
            HSV = (HorizontalScrollView) view.findViewById(R.id.hsv);
            linearLayout1 = (LinearLayout) view.findViewById(R.id.hsv_l);


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "rrr   " + getPosition(), Toast.LENGTH_SHORT).show();
           /* Intent i = new Intent(v.getContext(), InstituteViewProfile.class);
            i.putExtra("key", "NO_EDIT");
            i.putExtra("id", list.get(getPosition()).getInstituteId());
            mContext.startActivity(i);*/
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public InstituteViewAdapter(Context mContext, ArrayList<InstituteListModel> list, RecyclerView mRecyclerView) {
        this.list = list;
        this.mRecyclerView = mRecyclerView;
        this.mContext = mContext;

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
            View view = inflater.inflate(R.layout.custom_institute_list, parent, false);

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

            InstituteListModel mm = list.get(position);
            MyViewHolder userViewHolder = (MyViewHolder) holder;
            userViewHolder.name.setText(mm.getInstituteName());
            userViewHolder.mobile.setText(mm.getPhoneNumber());
            userViewHolder.description.setText(mm.getDescription());
            userViewHolder.no_views.setText(mm.getView_count() + " Views");
            userViewHolder.linearLayout1.removeAllViews();
            for (int i = 0; i < mm.getCourses().size(); i++) {

                TextView course_name = new TextView(mContext);
                course_name.setFocusable(false);
                course_name.setTypeface(FONT_BOLD);
                course_name.setTextColor(mContext.getResources().getColor(R.color.white));
                course_name.setBackgroundResource(R.drawable.institute_course_textview);
                course_name.setText(mm.getCourses().get(i));
                course_name.setPadding(10, 10, 10, 10);
                course_name.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(course_name.getLayoutParams());
                marginParams.setMargins(2, 0, 2, 0);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
                course_name.setLayoutParams(layoutParams);
                course_name.setGravity(Gravity.CENTER);

                userViewHolder.linearLayout1.addView(course_name);

            }

            if (mm.getInstituteProfilePic().equals("")) {
                Picasso.with(mContext).load(R.mipmap.inst_pic).transform(new CircleTransform()).into(userViewHolder.image);

            } else {
                Picasso.with(mContext).load(UtilsConstants.API_PROFILE_IMAGE + mm.getInstituteProfilePic()).transform(new CircleTransform()).resize(UtilsConstants.IMAGE_SIZE, UtilsConstants.IMAGE_SIZE).into(userViewHolder.image);

            }

            setCustomFont(userViewHolder);
        } else if (holder instanceof LoadingViewHolder) {

            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setCustomFont(MyViewHolder holder) {

        holder.name.setTypeface(FONT_REGULAR);
        holder.mobile.setTypeface(FONT_REGULAR);
        holder.description.setTypeface(FONT_LIGHT);

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

