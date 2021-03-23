package com.example.studentteachercollaborations.CommonFeatures;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.studentteachercollaborations.R;

@SuppressLint("SetJavaScriptEnabled")
public class NoticeBoard extends Fragment {
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView webView;

    public NoticeBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        requireActivity().setTitle("NOTICE BOARD");

        webView = view.findViewById(R.id.noticeWebView);
        progressBar = view.findViewById(R.id.noticeProgressBar);
        swipeRefreshLayout = view.findViewById(R.id.noticeSwipe);

        String url = "https://bubt.edu.bd/Home/all_notice";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl());
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }
}