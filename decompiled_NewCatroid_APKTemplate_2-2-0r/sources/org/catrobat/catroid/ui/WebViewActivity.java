package org.catrobat.catroid.ui;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.BuildConfig;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.utils.MediaDownloader;
import org.catrobat.catroid.utils.ProjectDownloadUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.Cookie;
import org.catrobat.catroid.web.GlobalProjectDownloadQueue;
import org.catrobat.catroid.web.ProjectDownloader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/WebViewActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WebViewActivity extends AppCompatActivity {
    public static final String ANDROID_APPLICATION_EXTENSION = ".apk";
    public static final String INTENT_FORCE_OPEN_IN_APP = "openInApp";
    public static final String INTENT_PARAMETER_URL = "url";
    public static final String MEDIA_FILE_PATH = "media_file_path";
    private static final String PACKAGE_NAME_WHATSAPP = "com.whatsapp";
    private static final String TAG = "WebViewActivity";
    private ProgressDialog progressDialog;
    private WebView webView;
    private ProgressDialog webViewLoadingDialog;
    private boolean allowGoBack = false;
    private boolean forceOpenInApp = false;
    private Intent resultIntent = new Intent();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String stringExtra = getIntent().getStringExtra("url");
        if (stringExtra == null) {
            stringExtra = "https://t.me/NewCatroidDevs";
        }
        this.forceOpenInApp = getIntent().getBooleanExtra(INTENT_FORCE_OPEN_IN_APP, false);
        WebView webView = (WebView) findViewById(R.id.webView);
        this.webView = webView;
        webView.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.app_background, null));
        this.webView.setWebViewClient(new MyWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setUserAgentString("Catrobat/" + String.valueOf(1.18d) + " PocketCode/" + Utils.getVersionName(getApplicationContext()) + " Platform/Android BuildType/" + (BuildConfig.FLAVOR.equals("pocketCodeBeta") ? "debug" : BuildConfig.BUILD_TYPE));
        setLoginCookies(stringExtra, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()), CookieManager.getInstance());
        this.webView.loadUrl(stringExtra);
        this.webView.setDownloadListener(new DownloadListener() { // from class: org.catrobat.catroid.ui.WebViewActivity$$ExternalSyntheticLambda0
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                this.f$0.m5642lambda$onCreate$0$orgcatrobatcatroiduiWebViewActivity(str, str2, str3, str4, j);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$org-catrobat-catroid-ui-WebViewActivity, reason: not valid java name */
    /* synthetic */ void m5642lambda$onCreate$0$orgcatrobatcatroiduiWebViewActivity(String str, String str2, String str3, String str4, long j) {
        if (getExtensionFromContentDisposition(str3).contains(Constants.CATROBAT_EXTENSION) && !str.contains("https://t.me/NewCatroidDevs")) {
            new ProjectDownloader(GlobalProjectDownloadQueue.INSTANCE.getQueue(), str, ProjectDownloadUtil.INSTANCE).download(this);
            return;
        }
        if (str.contains("https://t.me/NewCatroidDevs")) {
            String strGuessFileName = URLUtil.guessFileName(str, str3, str4);
            Constants.MEDIA_LIBRARY_CACHE_DIRECTORY.mkdirs();
            if (!Constants.MEDIA_LIBRARY_CACHE_DIRECTORY.isDirectory()) {
                Log.e(TAG, "Cannot create " + Constants.MEDIA_LIBRARY_CACHE_DIRECTORY);
                return;
            }
            File file = new File(Constants.MEDIA_LIBRARY_CACHE_DIRECTORY, strGuessFileName);
            this.resultIntent.putExtra(MEDIA_FILE_PATH, file.getAbsolutePath());
            new MediaDownloader(this).startDownload(this, str, strGuessFileName, file.getAbsolutePath());
            return;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        String projectNameFromUrl = ProjectDownloader.INSTANCE.getProjectNameFromUrl(str);
        request.setTitle(getString(R.string.notification_download_title_pending) + " " + projectNameFromUrl);
        request.setDescription(getString(R.string.notification_download_pending));
        request.setNotificationVisibility(1);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, projectNameFromUrl + ".apk");
        request.setMimeType(str4);
        ((DownloadManager) getSystemService("download")).enqueue(request);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.webView.canGoBack()) {
            this.allowGoBack = false;
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/WebViewActivity$MyWebViewClient.class
      classes14.dex
     */
    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView view, String urlClient, Bitmap favicon) {
            if (WebViewActivity.this.webViewLoadingDialog == null && !WebViewActivity.this.allowGoBack) {
                WebViewActivity.this.webViewLoadingDialog = new ProgressDialog(view.getContext(), R.style.WebViewLoadingCircle);
                WebViewActivity.this.webViewLoadingDialog.setCancelable(true);
                WebViewActivity.this.webViewLoadingDialog.setCanceledOnTouchOutside(false);
                WebViewActivity.this.webViewLoadingDialog.setProgressStyle(android.R.style.Widget.ProgressBar.Small);
                WebViewActivity.this.webViewLoadingDialog.show();
                return;
            }
            if (WebViewActivity.this.allowGoBack) {
                if (urlClient.equals("https://t.me/NewCatroidDevs") || urlClient.equals(Constants.BASE_APP_URL_HTTPS)) {
                    WebViewActivity.this.allowGoBack = false;
                    WebViewActivity.this.onBackPressed();
                }
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            WebViewActivity.this.allowGoBack = true;
            if (WebViewActivity.this.webViewLoadingDialog != null) {
                WebViewActivity.this.webViewLoadingDialog.dismiss();
                WebViewActivity.this.webViewLoadingDialog = null;
            }
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request == null || request.getUrl() == null) {
                return false;
            }
            return shouldOverrideUrlLoading(view, request.getUrl().toString());
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String telegramFallbackUrl;
            if (url == null) {
                return false;
            }
            if (url.startsWith(Constants.WHATSAPP_URI)) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                    intent.setFlags(805306368);
                    WebViewActivity.this.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    ToastUtil.showError(WebViewActivity.this.getBaseContext(), R.string.error_no_whatsapp);
                }
                return true;
            }
            if (WebViewActivity.this.forceOpenInApp || !checkIfWebViewVisitExternalWebsite(url)) {
                return false;
            }
            try {
                WebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (ActivityNotFoundException unused2) {
                if (url.startsWith("tg://") && (telegramFallbackUrl = getTelegramFallbackUrl(url)) != null) {
                    try {
                        WebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(telegramFallbackUrl)));
                    } catch (ActivityNotFoundException unused3) {
                        ToastUtil.showError(WebViewActivity.this.getBaseContext(), R.string.error_unknown_error);
                    }
                } else {
                    ToastUtil.showError(WebViewActivity.this.getBaseContext(), R.string.error_unknown_error);
                }
            }
            return true;
        }

        private String getTelegramFallbackUrl(String tgUrl) {
            if (tgUrl == null) {
                return null;
            }
            if (tgUrl.startsWith("tg://resolve?domain=")) {
                return tgUrl.replace("tg://resolve?domain=", "https://t.me/");
            }
            if (tgUrl.startsWith("tg://resolve/?domain=")) {
                return tgUrl.replace("tg://resolve/?domain=", "https://t.me/");
            }
            if (tgUrl.startsWith("tg://join?invite=")) {
                return tgUrl.replace("tg://join?invite=", "https://t.me/joinchat/");
            }
            if (tgUrl.startsWith("tg://join/?invite=")) {
                return tgUrl.replace("tg://join/?invite=", "https://t.me/joinchat/");
            }
            return null;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (Utils.checkIsNetworkAvailableAndShowErrorMessage(WebViewActivity.this)) {
                ToastUtil.showError(WebViewActivity.this.getBaseContext(), R.string.error_unknown_error);
            }
            if (errorCode == -6 || errorCode == -14 || errorCode == -2 || errorCode == -8 || errorCode == -5 || errorCode == -1) {
                WebViewActivity.this.setContentView(R.layout.activity_network_error);
                WebViewActivity webViewActivity = WebViewActivity.this;
                webViewActivity.setSupportActionBar((Toolbar) webViewActivity.findViewById(R.id.toolbar));
                WebViewActivity.this.getSupportActionBar().setIcon(R.drawable.pc_toolbar_icon);
                WebViewActivity.this.getSupportActionBar().setTitle(R.string.app_name);
                return;
            }
            Log.e(WebViewActivity.TAG, "couldn't connect to the server! info: " + description + " : " + errorCode);
        }

        private boolean checkIfWebViewVisitExternalWebsite(String url) {
            return (!url.contains("https://t.me/NewCatroidDevs") || url.contains(FlavoredConstants.CATROBAT_HELP_URL)) && !url.contains("https://t.me/NewCatroidDevs");
        }
    }

    public void createProgressDialog(String mediaName) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.progressDialog = progressDialog;
        progressDialog.setTitle(getString(R.string.notification_download_title_pending) + mediaName);
        this.progressDialog.setMessage(getString(R.string.notification_download_pending));
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setProgress(0);
        this.progressDialog.setMax(100);
        this.progressDialog.setProgressNumberFormat(null);
        if (isFinishing()) {
            return;
        }
        this.progressDialog.show();
    }

    public void updateProgressDialog(long progress) {
        if (progress == 100) {
            if (this.progressDialog.isShowing() && !isFinishing()) {
                ProgressDialog progressDialog = this.progressDialog;
                progressDialog.setProgress(progressDialog.getMax());
                setResult(-1, this.resultIntent);
                this.progressDialog.dismiss();
            }
            finish();
            return;
        }
        this.progressDialog.setProgress((int) progress);
    }

    public void dismissProgressDialog() {
        this.progressDialog.dismiss();
    }

    public Intent getResultIntent() {
        return this.resultIntent;
    }

    public void setResultIntent(Intent intent) {
        this.resultIntent = intent;
    }

    private String getExtensionFromContentDisposition(String contentDisposition) {
        String strSubstring = contentDisposition.substring(contentDisposition.lastIndexOf(46));
        return strSubstring.substring(0, strSubstring.length() - 1);
    }

    public static void setLoginCookies(String url, SharedPreferences sharedPreferences, CookieManager cookieManager) {
        String string = sharedPreferences.getString("username", Constants.NO_USERNAME);
        String string2 = sharedPreferences.getString("token", Constants.NO_TOKEN);
        if (string.equals(Constants.NO_USERNAME) || string2.equals(Constants.NO_TOKEN)) {
            return;
        }
        try {
            Cookie cookie = new Cookie(Constants.USERNAME_COOKIE_NAME, URLEncoder.encode(string, "UTF-8"));
            Cookie cookie2 = new Cookie(Constants.TOKEN_COOKIE_NAME, string2);
            cookieManager.setCookie(url, cookie.generateCookieString());
            cookieManager.setCookie(url, cookie2.generateCookieString());
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public static void clearCookies() {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    private boolean isWhatsappInstalled() {
        try {
            getPackageManager().getPackageInfo(PACKAGE_NAME_WHATSAPP, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        this.webView.setDownloadListener(null);
        this.webView.destroy();
        if (MainMenuActivity.surveyCampaign != null) {
            MainMenuActivity.surveyCampaign.showSurvey(this);
        }
        super.onDestroy();
    }
}
