package bigboss27051.liveat500px.flagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import bigboss27051.liveat500px.adapter.PhotoListAdapter;
import bigboss27051.liveat500px.R;
import bigboss27051.liveat500px.dao.PhotoItemCollectionDao;
import bigboss27051.liveat500px.manager.HttpManager;
import bigboss27051.liveat500px.manager.PhotoListManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFlagment extends Fragment {
    /***
     * Variable Zone
     ****/

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    PhotoListAdapter listAdapter;
    PhotoListManager photoListManager;
    Button btnNewPhoto;
    boolean isLoadingMore = false;

    /***
     * Function Zone
     ***/

    public MainFlagment() {
        super();
    }

    public static MainFlagment newInstance() {
        MainFlagment fragment = new MainFlagment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flagment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        photoListManager = new PhotoListManager();
        listView = (ListView) rootView.findViewById(R.id.lvListView);
        listAdapter = new PhotoListAdapter();
        listView.setAdapter(listAdapter);
        btnNewPhoto = (Button) rootView.findViewById(R.id.btnNewPhoto);

        btnNewPhoto.setOnClickListener(buttonClickListener);


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(pullToRefresh);

        listView.setOnScrollListener(listViewScrollListener);

        refreshData();
    }

    private void refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        }

        reloadDataNewer();
    }


    private void reloadDataNewer() {
        int maxId = photoListManager.getIntMaximunId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));
    }

    private void loadMoreData() {
        if (isLoadingMore) {
            return;
        }
        isLoadingMore = true;
        int minId = photoListManager.getMinimumId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoListBeforeId(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_MORE));
    }

    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    private void showButtonNewPhoto() {
        btnNewPhoto.setVisibility(View.VISIBLE);
        Animation amin = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_in);
        btnNewPhoto.startAnimation(amin);
    }

    private void hideNewButtonPhoto() {
        btnNewPhoto.setVisibility(View.GONE);
        Animation amin = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(),
                R.anim.zoom_fade_out);
        btnNewPhoto.startAnimation(amin);
    }

    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

    /********
     * Listener Zone
     ************/

    final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnNewPhoto) {
                listView.smoothScrollToPosition(0);
                hideNewButtonPhoto();
            }
        }
    };

    final SwipeRefreshLayout.OnRefreshListener pullToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    final AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount,
                             int totalItemCount) {
            if (view == listView) {
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);

                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photoListManager.getCount() > 0) {
                        // Load More
                        loadMoreData();
                    }
                }
            }
        }
    };

    /*****
     * Inner Class
     *****/

    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDao> {

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_RELOAD_MORE = 3;

        int mode;

        PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccessful()) {
                PhotoItemCollectionDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao);
                } else if (mode == MODE_RELOAD_MORE) {
                    photoListManager.appendDaoAtTopPosition(dao);
                } else {
                    photoListManager.setDao(dao);
                }
                clearLoadingMoreFlagTfCapable(mode);
                listAdapter.setDao(photoListManager.getDao());
                listAdapter.notifyDataSetChanged();

                if (mode == MODE_RELOAD_NEWER) {
                    int additionalSize = (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    listAdapter.increaseLastPosition(additionalSize);
                    listView.setSelectionFromTop(firstVisiblePosition + additionalSize, top);

                    if (additionalSize > 0) {
                        showButtonNewPhoto();
                    }

                } else {

                }

                showToast("Load Completed");
            } else {

                clearLoadingMoreFlagTfCapable(mode);

                try {

                    showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            clearLoadingMoreFlagTfCapable(mode);
            swipeRefreshLayout.setRefreshing(false);
            showToast(t.toString());
        }

        private void clearLoadingMoreFlagTfCapable(int mode) {
            if (mode == MODE_RELOAD_MORE) {
                isLoadingMore = false;
            }
        }
    }

}