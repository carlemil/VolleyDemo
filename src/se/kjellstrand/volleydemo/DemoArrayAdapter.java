package se.kjellstrand.volleydemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * Created by erbsman on 7/25/13.
 */
public class DemoArrayAdapter extends BaseAdapter implements Response.ErrorListener, Listener<DemoItem[]> {

    public static final int PER_PAGE = 5;

    private final List<DemoItem> mDemoItems = new ArrayList<DemoItem>();

    private final Context mContext;

    private boolean mLastPageLoaded = false;

    private Request<DemoItem[]> mInFlightRequest;

    public DemoArrayAdapter(Context context) {
        mContext = context;
        loadNextPage();
    }

    @Override
    public int getCount() {
        return mDemoItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mDemoItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_item_detail, viewGroup, false);
        }

        loadNextPage();
        Holder h = Holder.get(convertView);
        h.title.setText(((DemoItem) getItem(i)).getTitle());

        return convertView;
    }

    private void loadNextPage() {
        if (mInFlightRequest != null || mLastPageLoaded) {
            return;
        }

        int page = (int) (mDemoItems.size() / (double) PER_PAGE);
        mInFlightRequest = VolleySingleton.getInstance().getApi().getDemoItemsPage(page, this, this);
    }

    @Override
    public void onResponse(DemoItem[] demoItems) {
        mInFlightRequest = null;
        if (demoItems.length != PER_PAGE) {
            mLastPageLoaded = true;
        }
        for (DemoItem di : demoItems) {
            mDemoItems.add(di);
        }

        notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    private static final class Holder {
        public TextView title;

        private Holder(View v) {
            title = (TextView) v.findViewById(R.id.activity_item_detail_title);
            v.setTag(this);
        }

        public static Holder get(View v) {
            if (v.getTag() instanceof Holder) {
                return (Holder) v.getTag();
            }
            return new Holder(v);
        }
    }

}
