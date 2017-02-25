package sigit.jadwal.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sigit.jadwal.R;
import sigit.jadwal.model.listtravel.Penumpang;
import sigit.jadwal.preference.Preference;
import sigit.jadwal.presenter.travel.TravelImp;
import sigit.jadwal.presenter.travel.TravelPresenter;
import sigit.jadwal.presenter.travel.TravelView;

public class TrvsurabayaFragment extends Fragment implements TravelView{
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> dataSet;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressDialog progressDialog;
    TravelPresenter presenter;
    Preference dtpref;
    private String mParam1;
    private String mParam2;

    public Button buttonts;
    public TrvsurabayaFragment() {
        // Required empty public constructor
    }

    public static TrvsurabayaFragment newInstance(String param1, String param2) {
        TrvsurabayaFragment fragment = new TrvsurabayaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_trvsurabaya, container, false);
        dataSet = new ArrayList<>();
        rvView = (RecyclerView) view.findViewById(R.id.rvtrvsurabaya);
        rvView.setHasFixedSize(true);
        presenter = new TravelImp(this);
        dtpref = new Preference(this.getActivity());
        presenter.getPenumpang(dtpref.getUserDetails().get("id"),"sby");
        return view;
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(getActivity(), "Mengambil Data","Silakan tunggu..",false,false);
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showPenumpang(List<Penumpang> penumpang) {
        layoutManager = new LinearLayoutManager(this.getActivity());
        rvView.setLayoutManager(layoutManager);
        adapter = new RvtrvAdapter(penumpang);
        rvView.setAdapter(adapter);
    }

    @Override
    public void errorMessage(String pesan) {
        new AlertDialog.Builder(this.getActivity()).setMessage("Penumpang Surabaya "+pesan).setNeutralButton("Close", null).show();
    }
}
