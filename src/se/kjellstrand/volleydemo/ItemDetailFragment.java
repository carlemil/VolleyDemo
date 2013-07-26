package se.kjellstrand.volleydemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment implements Response.ErrorListener, Listener<DemoItem> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_PATH = "item_path";

    private ImageLoader mImageLoader = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
        mImageLoader = VolleyDemoApplication.get().getImageLoader();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_PATH)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            VolleyDemoApplication.get().getApi().getDemoItemDetails(getArguments().getString(ARG_ITEM_PATH), this, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        return rootView;
    }

    @Override
    public void onResponse(DemoItem demoItem) {
        
        ((TextView) getActivity().findViewById(R.id.demo_item_details_title)).setText(demoItem.getTitle());
        ((TextView) getActivity().findViewById(R.id.demo_item_details_author)).setText(demoItem.getAuthor());
        ((TextView) getActivity().findViewById(R.id.demo_item_details_price)).setText(demoItem.getPrice());
        NetworkImageView niw = ((NetworkImageView)getActivity().findViewById(R.id.demo_item_details_image));
        niw.setImageDrawable(null);
        niw.setImageUrl(demoItem.getImage(), mImageLoader);
    }

    @Override
    public void onErrorResponse(VolleyError arg0) {
        
    }
}
