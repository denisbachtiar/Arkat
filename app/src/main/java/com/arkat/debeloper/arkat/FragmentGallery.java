package com.arkat.debeloper.arkat;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.arkat.debeloper.arkat._sliders.FragmentSlider;
import com.arkat.debeloper.arkat._sliders.SliderIndicator;
import com.arkat.debeloper.arkat._sliders.SliderPagerAdapter;
import com.arkat.debeloper.arkat._sliders.SliderView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.google.android.gms.internal.zzagz.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGallery extends Fragment {

    //BLOG PORTAL
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> DaftarBerita = new ArrayList<HashMap<String, String>>();
    private static String url_berita = "http://watonmerch.co.id/android/blog.php";
    public static final String TAG_ID = "id";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_GAMBAR = "gambar";
    JSONArray string_json = null;
    ListView list;
    LazyAdapter adapter;
    //BLOG PORTAL

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private List<Slide> listGambar;


    public FragmentGallery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://watonmerch.co.id")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<Response> respon = service.listRepos();
        respon.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("SIZE","D " + response.body().getData_slider().size());
                listGambar=response.body().getData_slider();
                setupSlider();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("d","ddd"+t.getMessage());
            }
        });

    }



    public interface GitHubService {
        @GET("/android/data_slider.php")
        Call <Response> listRepos();
    }

    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        //PORTAL BLOG
        DaftarBerita = new ArrayList<HashMap<String, String>>();
        context = rootView.getContext();
        new AmbilData().execute();
        list = (ListView) rootView.findViewById(R.id.list);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = DaftarBerita.get(position);
                // Starting new intent
                Intent in = new Intent(getActivity().getApplicationContext(), DetailBerita.class);
                in.putExtra(TAG_ID, map.get(TAG_ID));
                in.putExtra(TAG_GAMBAR, map.get(TAG_GAMBAR));
                startActivity(in);
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Gallery");
        // Inflate the layout for this fragment
        sliderView = (SliderView) rootView.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.pagesContainer);

        return rootView;
    }
    private void setupSlider() {
        sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();
        for (Slide slide :
                listGambar) {
            fragments.add(FragmentSlider.newInstance("http://watonmerch.co.id/psimg/slide/" + slide.getGambar()));
        }

        mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
        sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    public void SetListViewAdapter(ArrayList<HashMap<String,
            String>> blog) {
        adapter = new LazyAdapter(getActivity(), blog);
        list.setAdapter(adapter);
    }

    class AmbilData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            List<NameValuePair> params = new
                    ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(url_berita,
                    "GET", params);
            Log.i("Ini nilai json ", ">" + json);
            try {
                string_json = json.getJSONArray("blog");
                for (int i = 0; i < string_json.length(); i++) {
                    JSONObject c = string_json.getJSONObject(i);
                    String id_berita = c.getString(TAG_ID);
                    String judull = c.getString(TAG_JUDUL);
                    String gambar = c.getString(TAG_GAMBAR);
                    HashMap<String, String> map = new HashMap<String,
                            String>();
                    map.put(TAG_ID, id_berita);
                    map.put(TAG_JUDUL, judull);
                    map.put(TAG_GAMBAR, gambar);
                    DaftarBerita.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    SetListViewAdapter(DaftarBerita);
                }
            });
        }





    }
}
