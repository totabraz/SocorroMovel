package totabraz.com.socorromovel.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import totabraz.com.socorromovel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchImeiFragment extends Fragment {


    public SearchImeiFragment() {
        // Required empty public constructor
    }


    public static SearchImeiFragment newInstance() {
        SearchImeiFragment fragment = new SearchImeiFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_imei, container, false);
    }

}
