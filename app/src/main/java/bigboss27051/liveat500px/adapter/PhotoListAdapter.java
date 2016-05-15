package bigboss27051.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import bigboss27051.liveat500px.dao.PhotoItemCollectionDao;
import bigboss27051.liveat500px.dao.PhotoItemDao;
import bigboss27051.liveat500px.manager.PhotoListManager;
import bigboss27051.liveat500px.view.PhotoListItem;
import bigboss27051.liveat500px.R;

/**
 * Created by a2g01 on 12/05/2559.
 */
public class PhotoListAdapter extends BaseAdapter {
    int lastPosition = 0;
    PhotoItemCollectionDao dao;

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null) {
            return 1;
        }
        if (dao.getData() == null) {
            return 1;
        }

        return dao.getData().size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // count type of view
    }

    @Override
    public int getItemViewType(int position) {
        return position == getCount() - 1 ? 1 : 0;  //return view type
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        if (getItemViewType(position) == 0) {
//            PhotoListItem item;
//            if (convertView != null) {
//                item = (PhotoListItem) convertView;
//            } else {
//                item = new PhotoListItem(parent.getContext());
//            }
//            return item;
//        } else {
//            TextView item;
//            if (convertView != null) {
//                item = (TextView) convertView;
//            } else {
//                item = new TextView(parent.getContext());
//            }
//            item.setText("Position : " + position);
//            return item;
//        }

        if (position == getCount() - 1) {
            ProgressBar item;
            if (convertView != null) {
                item = (ProgressBar) convertView;
            } else {
                item = new ProgressBar(parent.getContext());
            }
            return item;
        }

        PhotoListItem item;
        if (convertView != null) {
            item = (PhotoListItem) convertView;
        } else {
            item = new PhotoListItem(parent.getContext());
        }

        PhotoItemDao dao = (PhotoItemDao) getItem(position);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUsername() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImgUrl());


        if (position > lastPosition) {

            Animation amin = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(amin);

            lastPosition = position;
        }

        return item;

    }

    public void increaseLastPosition(int amount) {
        lastPosition += amount;
    }


}
