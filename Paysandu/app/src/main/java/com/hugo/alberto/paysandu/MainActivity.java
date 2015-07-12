package com.hugo.alberto.paysandu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
    ProgressBar progressBar;
    SectionsPagerAdapter mSectionsPagerAdapter;

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    ViewPager mViewPager;
    boolean loadingFinished = true;
    boolean redirect = false;

    private static final String baseURl = "https://twitter.com";
    private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/Paysandu\" data-widget-id=\"611196778197721088\"  data-chrome=\"noheader noborders nofooter\" ></a>" +
            "<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>";

    public WebView webview_tabela;
    public WebView webview_jogos;
    public WebView webview_gols;
    public WebView webview_noticias;
    public WebView webview_copadobrasil;
    public int tabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat);
        setContentView(R.layout.activity_main);



        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-64569785-1"); // Replace with actual tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#008FD2")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#3CA2D1")));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            if(tabPos == 0){
                setContentView(R.layout.tabela_view);
                getTabela();

                AdView mAdView_tabela = (AdView) findViewById(R.id.adView);
                AdRequest adRequest_tabela = new AdRequest.Builder().build();
                mAdView_tabela.loadAd(adRequest_tabela);
            }
            if(tabPos == 1){
                setContentView(R.layout.jogos_view);
                getJogos();

                AdView mAdView_jogos = (AdView) findViewById(R.id.adView);
                AdRequest adRequest_jogos = new AdRequest.Builder().build();
                mAdView_jogos.loadAd(adRequest_jogos);
            }
            if(tabPos == 2){
                setContentView(R.layout.gols_view);
                getGols();

                AdView mAdView_gols = (AdView) findViewById(R.id.adView);
                AdRequest adRequest_gols = new AdRequest.Builder().build();
                mAdView_gols.loadAd(adRequest_gols);
            }
            if(tabPos == 3){
                setContentView(R.layout.noticias_view);
                getNoticia();

                AdView mAdView_noticias = (AdView) findViewById(R.id.adView);
                AdRequest adRequest_noticias = new AdRequest.Builder().build();
                mAdView_noticias.loadAd(adRequest_noticias);
            }
            if(tabPos == 4){
                setContentView(R.layout.copa_brasil_view);
                getCopaDoBrasil();

                AdView mAdView_copa_do_brasil = (AdView) findViewById(R.id.adView);
                AdRequest adRequest_copa_do_brasil = new AdRequest.Builder().build();
                mAdView_copa_do_brasil.loadAd(adRequest_copa_do_brasil);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        tabPos = tab.getPosition();
        switch (tabPos) {
            case 0:
                    setContentView(R.layout.tabela_view);
                    getTabela();

                    AdView mAdView_tabela = (AdView) findViewById(R.id.adView);
                    AdRequest adRequest_tabela = new AdRequest.Builder().build();
                    mAdView_tabela.loadAd(adRequest_tabela);
                break;
            case 1:
                    setContentView(R.layout.jogos_view);
                    getJogos();

                    AdView mAdView_jogos = (AdView) findViewById(R.id.adView);
                    AdRequest adRequest_jogos = new AdRequest.Builder().build();
                    mAdView_jogos.loadAd(adRequest_jogos);
                break;
            case 2:
                    setContentView(R.layout.gols_view);
                    getGols();

                    AdView mAdView_gols = (AdView) findViewById(R.id.adView);
                    AdRequest adRequest_gols = new AdRequest.Builder().build();
                    mAdView_gols.loadAd(adRequest_gols);
                break;
            case 3:
                    setContentView(R.layout.noticias_view);
                    getNoticia();

                    AdView mAdView_noticias = (AdView) findViewById(R.id.adView);
                    AdRequest adRequest_noticias = new AdRequest.Builder().build();
                    mAdView_noticias.loadAd(adRequest_noticias);
                break;
            case 4:
                    setContentView(R.layout.copa_brasil_view);
                    getCopaDoBrasil();

                    AdView mAdView_copa_do_brasil = (AdView) findViewById(R.id.adView);
                    AdRequest adRequest_copa_do_brasil = new AdRequest.Builder().build();
                    mAdView_copa_do_brasil.loadAd(adRequest_copa_do_brasil);
                break;
        }
        mViewPager.setCurrentItem(tab.getPosition());
    }

    private void getTabela() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Paysandu App");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webview_tabela = (WebView) findViewById(R.id.tabela);

        webview_tabela.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (isNetworkAvailable(this)) {
            webview_tabela.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webview_tabela.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webview_tabela.getSettings().setJavaScriptEnabled(true);
        webview_tabela.setWebViewClient(new myWebClient() { //WebViewClient
            @Override
            public void onLoadResource(WebView view, String url) {
                webview_tabela.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('glb-topo')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('gui-text-section-title tabela-header-titulo')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('simulador simulador-extra-margin')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('legenda-regulamento')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('lista-de-jogos lista-de-jogos-fora-grupo')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('tabela-times-time-link')[0].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[1].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[2].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[3].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[4].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[5].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[6].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[7].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[8].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[9].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[10].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[11].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[12].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[13].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[14].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[15].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[16].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[17].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[18].removeAttribute('href');" +
                        "document.getElementsByClassName('tabela-times-time-link')[19].removeAttribute('href');" +

                        "document.getElementsByClassName('widget widget-plantao-semantico')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('publicidade publicidade-banner_mobile_fim')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('footer product-color')[0].style.display = 'none'; " +
                        "})()");
            }
        });


        webview_tabela.loadUrl("http://globoesporte.globo.com/futebol/brasileirao-serie-b/");
    }

    private void getJogos() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Paysandu App");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webview_jogos = (WebView) findViewById(R.id.jogos);
        webview_jogos.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (isNetworkAvailable(this)) {
            webview_jogos.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webview_jogos.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webview_jogos.getSettings().setJavaScriptEnabled(true);
        webview_jogos.setWebViewClient(new myWebClient() {
            @Override
            public void onLoadResource(WebView view, String url) {

                webview_jogos.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[0].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[1].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[2].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[3].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[4].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[5].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[6].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[7].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[8].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[9].removeAttribute('href'); " +

                        "})()");
                webview_jogos.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('placar-jogo-complemento')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[1].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[2].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[3].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[4].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[5].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[6].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[7].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[8].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[9].style.display = 'none'; " +
                        "})()");
                webview_jogos.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('glb-topo')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('gui-text-section-title tabela-header-titulo')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('simulador simulador-extra-margin')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('legenda-regulamento')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('tabela tabela-sem-jogos-por-grupo')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('legenda-grupos')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('gui-text-section-title tabela-header-titulo')[1].style.display = 'none'; " +

                        "document.getElementsByClassName('tabela-navegacao tabela-navegacao-jogos')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('widget widget-plantao-semantico')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('publicidade publicidade-banner_mobile_fim')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('footer product-color')[0].style.display = 'none'; " +
                        "})()");
            }

        });

        webview_jogos.loadUrl("http://globoesporte.globo.com/futebol/brasileirao-serie-b/");
    }

    private void getGols() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Paysandu App");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webview_gols = (WebView) findViewById(R.id.gols);
        webview_gols.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (isNetworkAvailable(this)) {
            webview_gols.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webview_gols.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webview_gols.getSettings().setJavaScriptEnabled(true);
        webview_gols.setWebViewClient(new myWebClient() {
            @Override
            public void onLoadResource(WebView view, String url) {

                webview_gols.loadUrl("javascript:(function() { " +

                        "document.getElementsByClassName('_meh')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('_mlf')[0].style.display = 'none'; " +
                       "document.getElementsByClassName('_mhv _miv')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('_myy')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('_mgz _mhi _mdj')[0].style.display = 'none'; " +

                        "})()");
                webview_gols.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('_mad')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('_mgz _mhi _mdj')[0].style.display = 'none';"+
                        "})()");
            }

        });

        webview_gols.loadUrl("https://m.youtube.com/channel/UCYEL0BXeHvw10bPq5xCON_Q/videos");
    }

    private void getNoticia() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Paysandu App");

        webview_noticias = (WebView) findViewById(R.id.noticias);

        webview_noticias.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (isNetworkAvailable(this)) {
            webview_noticias.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webview_noticias.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webview_noticias.getSettings().setDomStorageEnabled(true);
        webview_noticias.getSettings().setJavaScriptEnabled(true);
        webview_noticias.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);
    }

    public void getCopaDoBrasil() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Paysandu App");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webview_copadobrasil = (WebView) findViewById(R.id.copa_brasil);

        webview_copadobrasil.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (isNetworkAvailable(this)) {
            webview_copadobrasil.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webview_copadobrasil.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webview_copadobrasil.getSettings().setJavaScriptEnabled(true);

        webview_copadobrasil.setWebViewClient(new myWebClient() { //WebViewClient
            @Override
            public void onLoadResource(WebView view, String url) {
                webview_copadobrasil.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[0].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[1].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[2].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[3].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[4].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[5].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[6].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[7].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[8].removeAttribute('href'); " +
                        "document.getElementsByClassName('placar-jogo-link placar-jogo-link-confronto-js')[9].removeAttribute('href'); " +

                        "})()");
                webview_copadobrasil.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('placar-jogo-complemento')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[1].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[2].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[3].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[4].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[5].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[6].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[7].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[8].style.display = 'none'; " +
                        "document.getElementsByClassName('placar-jogo-complemento')[9].style.display = 'none'; " +
                        "})()");
                webview_copadobrasil.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('glb-topo')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('destaque destaque-publicidade')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('widget widget-plantao-semantico')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('gui-text-section-title tabela-header-titulo')[0].style.display = 'none'; " +

                        "document.getElementsByClassName('glb-publicidade-container')[0].style.display = 'none'; " +
                        "document.getElementsByClassName('footer product-color')[0].style.display = 'none'; " +

                        "})()");

            }
        });

        webview_copadobrasil.loadUrl("http://globoesporte.globo.com/futebol/copa-do-brasil/index.html#/classificacao-e-jogos");
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
                case 4:
                    return getString(R.string.title_section5).toUpperCase(l);
            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
            if(tabPos == 2 && webview_gols.canGoBack()) {
                webview_gols.goBack();
                return true;
            }
            if(tabPos == 3 && webview_noticias.canGoBack()) {
                webview_noticias.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            loadingFinished = false;
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!loadingFinished) {
                redirect = true;
            }
            loadingFinished = false;
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(!redirect){
                loadingFinished = true;

            }

            if(loadingFinished && !redirect){
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            } else{
                redirect = false;
            }
            // TODO Auto-generated method stub


        }
    }
}
