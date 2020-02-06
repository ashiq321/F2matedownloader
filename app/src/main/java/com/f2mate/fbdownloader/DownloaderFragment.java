package com.f2mate.fbdownloader;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.f2mate.fbdownloader.Model.FbVideoDownloader;


public class DownloaderFragment extends Fragment {


    AutoCompleteTextView inputURl;
    Button BtnDownload;



    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_downloader, container, false);

        inputURl = root.findViewById(R.id.editText);
        BtnDownload = root.findViewById(R.id.btndwd);
        final String URL = inputURl.getText().toString();
        BtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                   // mInterstitialAd.show();
                    download();
                    Toast.makeText(getContext(), "Downloading Start", Toast.LENGTH_SHORT).show();

                } else {
                    download();
                    Toast.makeText(getContext(), "Downloading Start", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        return root;



    }
    private void download() {
        final String URL = inputURl.getText().toString();
        FbVideoDownloader downloader = new FbVideoDownloader(getContext(),URL);
        downloader.DownloadVideo();
    }

}
