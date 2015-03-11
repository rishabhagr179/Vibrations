package com.vib15.vibrations.app;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.vib15.vibrations.app.data.EventsDbHelper;
import com.vib15.vibrations.app.data.SponsorContract;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SponsorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SponsorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SponsorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ImageView iView;
    // TODO: Rename and change types of parameters
    private static final int SPONSOR_LOADER = 0;
    private SponsorAdapter mSponsorAdapter;
    private ListView mListView;
    private EventsDbHelper mOpenHelper;
    private int mPosition = ListView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";
    private static final String[] SPONSOR_COLUMNS = {
            SponsorContract.SponsorEntry._ID,
            SponsorContract.SponsorEntry.COLUMN_NAME,
            SponsorContract.SponsorEntry.COLUMN_TYPE,
            SponsorContract.SponsorEntry.COLUMN_LOGO
    };
    static final int COL_ID = 0;
    static final int COL_NAME = 1;
    static final int COL_TYPE = 2;
    static final int COL_LOGO = 3;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SPONSOR_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SponsorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SponsorFragment newInstance(String param1, String param2) {
        SponsorFragment fragment = new SponsorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public interface Callback {
        public void onItemSelected(int resId,String name);
    }
    public SponsorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sponsor, container, false);
        mSponsorAdapter = new SponsorAdapter(getActivity(),null,0);
        mListView = (ListView) rootView.findViewById(R.id.listview_sponsor);
        mListView.setAdapter(mSponsorAdapter);
        EventsDbHelper dh = new EventsDbHelper(getActivity());
        int i = dh.getNo();
        Toast.makeText(getActivity(),"No of entries "+i,Toast.LENGTH_SHORT).show();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = getActivity().getContentResolver().query(SponsorContract.SponsorEntry.CONTENT_URI,
                        new String[]{SponsorContract.SponsorEntry.COLUMN_LOGO,SponsorContract.SponsorEntry.COLUMN_NAME},
                        SponsorContract.SponsorEntry._ID+" = ?",
                        new String[]{Integer.toString(position)},null);
                c.moveToFirst();
                mPosition=position;
                ((Callback) getActivity()).onItemSelected(c.getInt(c.getColumnIndex(SponsorContract.SponsorEntry.COLUMN_LOGO))
                        ,c.getString(c.getColumnIndex(SponsorContract.SponsorEntry.COLUMN_NAME)));
            }
        });
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
            mListView.smoothScrollToPosition(mPosition);
        }
        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = SponsorContract.SponsorEntry._ID + " ASC";
        return new CursorLoader(getActivity(),
                SponsorContract.SponsorEntry.CONTENT_URI,
                SPONSOR_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSponsorAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            mListView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSponsorAdapter.swapCursor(null);
    }
}
