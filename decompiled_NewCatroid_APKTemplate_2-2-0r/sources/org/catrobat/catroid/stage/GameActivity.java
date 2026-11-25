package org.catrobat.catroid.stage;

import android.os.Bundle;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/GameActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GameActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().setFlags(40, 40);
        WebView webView = (WebView) findViewById(R.id.myWebView);
        this.myWebView = webView;
        webView.setWebViewClient(new WebViewClient());
        this.myWebView.getLayoutParams().width = 300;
        this.myWebView.getLayoutParams().height = 400;
        this.myWebView.requestLayout();
        this.myWebView.loadUrl("https://uchi.ru/");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        WebView webView = this.myWebView;
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        onDestroy();
    }
}
