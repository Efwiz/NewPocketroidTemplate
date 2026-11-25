package org.catrobat.catroid.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwnerKt;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.common.net.HttpHeaders;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import name.antonsmirnov.firmata.FormatHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.databinding.ActivityCommunityWebviewBinding;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.community.CommunityTokenManager;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityWebViewActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: CommunityWebViewActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0006H\u0002J\u001a\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0006H\u0002J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001a\u001a\u00020\u0016H\u0002J\u001a\u0010\u001b\u001a\u00020\u001c2\u0010\b\u0002\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u001cH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\u0010\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u0016H\u0002J\u0010\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u0006H\u0002J\b\u0010&\u001a\u00020\u001cH\u0016J\u0012\u0010'\u001a\u00020\u001c2\b\u0010(\u001a\u0004\u0018\u00010)H\u0014J\u0010\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020!H\u0016J\b\u0010,\u001a\u00020\u001cH\u0002J\b\u0010-\u001a\u00020\u001cH\u0003J \u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020!H\u0002J\u0010\u00102\u001a\u00020\u001c2\u0006\u00103\u001a\u00020\u0006H\u0002J\u000e\u00104\u001a\u00020\u001c2\u0006\u0010\u0017\u001a\u00020\u0006J\u0012\u00105\u001a\u00020\u001c2\b\u00106\u001a\u0004\u0018\u00010\u0012H\u0002J\u001a\u00107\u001a\u0004\u0018\u00010\u00062\u0006\u00108\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0006H\u0002R2\u0010\u0004\u001a&\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006 \u0007*\u0012\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/catrobat/catroid/ui/CommunityWebViewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "()V", "activeDownloads", "", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "binding", "Lorg/catrobat/catroid/databinding/ActivityCommunityWebviewBinding;", "client", "Lokhttp3/OkHttpClient;", "hideWarningRunnable", "Ljava/lang/Runnable;", "mainHandler", "Landroid/os/Handler;", "webView", "Landroid/webkit/WebView;", "decodeXmlEntities", "input", "downloadProjectFile", "Ljava/io/File;", "url", "gameId", "getProjectNameFromXml", "unzipDir", "hideNotificationCard", "", "onAnimationEnd", "Lkotlin/Function0;", "hideWarningNotificationCard", "isDownloadUrl", "", "isValidZipFile", "file", "loadImportedProject", "name", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onLoadFinished", FirebaseAnalytics.Param.SUCCESS, "setupNotificationListeners", "setupWebView", "showNotificationCard", "title", NotificationCompat.CATEGORY_STATUS, "isFinished", "showWarningNotificationCard", CrashHianalyticsData.MESSAGE, "startNativeDownloadAndImport", "syncSessionWithWeb", Promotion.ACTION_VIEW, "unzipAndImport", "zipFile", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommunityWebViewActivity extends AppCompatActivity implements ProjectLoader.ProjectLoadListener {
    private ActivityCommunityWebviewBinding binding;
    private Runnable hideWarningRunnable;
    private WebView webView;
    private final OkHttpClient client = new OkHttpClient();
    private final Set<String> activeDownloads = Collections.synchronizedSet(new HashSet());
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCommunityWebviewBinding activityCommunityWebviewBindingInflate = ActivityCommunityWebviewBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityCommunityWebviewBindingInflate, "inflate(...)");
        this.binding = activityCommunityWebviewBindingInflate;
        WebView webView = null;
        if (activityCommunityWebviewBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBindingInflate = null;
        }
        setContentView(activityCommunityWebviewBindingInflate.getRoot());
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this.binding;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        WebView webView2 = activityCommunityWebviewBinding.webView;
        Intrinsics.checkNotNullExpressionValue(webView2, "webView");
        this.webView = webView2;
        WebView.setWebContentsDebuggingEnabled(true);
        setupWebView();
        setupNotificationListeners();
        WebView webView3 = this.webView;
        if (webView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("webView");
        } else {
            webView = webView3;
        }
        webView.loadUrl("https://newcatroid.sois.site");
    }

    private final void setupWebView() {
        WebView webView = this.webView;
        WebView webView2 = null;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("webView");
            webView = null;
        }
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(false);
        WebView webView3 = this.webView;
        if (webView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("webView");
            webView3 = null;
        }
        webView3.setWebChromeClient(new WebChromeClient());
        WebView webView4 = this.webView;
        if (webView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("webView");
            webView4 = null;
        }
        webView4.setWebViewClient(new AnonymousClass2());
        WebView webView5 = this.webView;
        if (webView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("webView");
        } else {
            webView2 = webView5;
        }
        webView2.setDownloadListener(new DownloadListener() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda8
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                CommunityWebViewActivity.setupWebView$lambda$1(this.f$0, str, str2, str3, str4, j);
            }
        });
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityWebViewActivity$setupWebView$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.CommunityWebViewActivity$setupWebView$2, reason: invalid class name */
    /* JADX INFO: compiled from: CommunityWebViewActivity.kt */
    @Metadata(d1 = {"\u0000/\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u001e\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u001c\u0010\f\u001a\u00020\r2\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\u000e"}, d2 = {"org/catrobat/catroid/ui/CommunityWebViewActivity$setupWebView$2", "Landroid/webkit/WebViewClient;", "onPageFinished", "", Promotion.ACTION_VIEW, "Landroid/webkit/WebView;", "url", "", "shouldInterceptRequest", "Landroid/webkit/WebResourceResponse;", "request", "Landroid/webkit/WebResourceRequest;", "shouldOverrideUrlLoading", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass2 extends WebViewClient {
        AnonymousClass2() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            CommunityWebViewActivity.this.syncSessionWithWeb(view);
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri url;
            String string;
            if (request == null || (url = request.getUrl()) == null || (string = url.toString()) == null || !CommunityWebViewActivity.this.isDownloadUrl(string)) {
                return false;
            }
            CommunityWebViewActivity.this.startNativeDownloadAndImport(string);
            return true;
        }

        @Override // android.webkit.WebViewClient
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri url;
            final String string;
            if (request == null || (url = request.getUrl()) == null || (string = url.toString()) == null) {
                return null;
            }
            String method = request.getMethod();
            if (method == null) {
                method = "GET";
            }
            if (CommunityWebViewActivity.this.isDownloadUrl(string)) {
                if (StringsKt.equals(method, "GET", true)) {
                    final CommunityWebViewActivity communityWebViewActivity = CommunityWebViewActivity.this;
                    communityWebViewActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$setupWebView$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CommunityWebViewActivity.AnonymousClass2.shouldInterceptRequest$lambda$0(communityWebViewActivity, string);
                        }
                    });
                }
                WebResourceResponse webResourceResponse = new WebResourceResponse("text/plain", "UTF-8", new ByteArrayInputStream(new byte[0]));
                HashMap map = new HashMap();
                map.put("Access-Control-Allow-Origin", "*");
                map.put(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
                map.put(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, OPTIONS");
                webResourceResponse.setResponseHeaders(map);
                return webResourceResponse;
            }
            return super.shouldInterceptRequest(view, request);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void shouldInterceptRequest$lambda$0(CommunityWebViewActivity this$0, String url) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(url, "$url");
            this$0.startNativeDownloadAndImport(url);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupWebView$lambda$1(CommunityWebViewActivity this$0, String str, String str2, String str3, String str4, long j) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(str);
        if (!StringsKt.startsWith(str, "blob:", true) && this$0.isDownloadUrl(str)) {
            this$0.startNativeDownloadAndImport(str);
        }
    }

    private final void setupNotificationListeners() {
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this.binding;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        activityCommunityWebviewBinding.btnDismissNotification.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CommunityWebViewActivity.setupNotificationListeners$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNotificationListeners$lambda$2(CommunityWebViewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        hideNotificationCard$default(this$0, null, 1, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isDownloadUrl(String url) {
        String lowerCase = url.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        String str = lowerCase;
        return (StringsKt.contains$default((CharSequence) str, (CharSequence) "/file", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) Constants.CATROBAT_EXTENSION, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) Constants.NEW_CATROBAT_EXTENSION, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "/files/", false, 2, (Object) null)) && StringsKt.contains$default((CharSequence) str, (CharSequence) "backend.sois.site", false, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void syncSessionWithWeb(WebView view) {
        String str;
        CommunityWebViewActivity communityWebViewActivity = this;
        String token = CommunityTokenManager.getToken(communityWebViewActivity);
        String userJson = CommunityTokenManager.getUserJson(communityWebViewActivity);
        String str2 = token;
        if (str2 == null || str2.length() == 0 || (str = userJson) == null || str.length() == 0) {
            if (view != null) {
                view.evaluateJavascript("if (localStorage.getItem('meander_token')) {\n    localStorage.removeItem('meander_token');\n    localStorage.removeItem('meander_user');\n    window.location.reload();\n}", null);
            }
        } else {
            byte[] bytes = userJson.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            String strTrimIndent = StringsKt.trimIndent("\n                if (localStorage.getItem('meander_token') !== '" + token + "') {\n                    localStorage.setItem('meander_token', '" + token + "');\n                    localStorage.setItem('meander_user', atob('" + Base64.encodeToString(bytes, 2) + "'));\n                    window.location.reload();\n                }\n            ");
            if (view != null) {
                view.evaluateJavascript(strTrimIndent, null);
            }
        }
    }

    private final void showWarningNotificationCard(final String message) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                CommunityWebViewActivity.showWarningNotificationCard$lambda$5(this.f$0, message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showWarningNotificationCard$lambda$5(final CommunityWebViewActivity this$0, String message) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(message, "$message");
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this$0.binding;
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding2 = null;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        activityCommunityWebviewBinding.tvWarningText.setText(message);
        Runnable runnable = this$0.hideWarningRunnable;
        if (runnable != null) {
            this$0.mainHandler.removeCallbacks(runnable);
        }
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding3 = this$0.binding;
        if (activityCommunityWebviewBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding3 = null;
        }
        if (activityCommunityWebviewBinding3.warningNotificationCard.getVisibility() != 0) {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding4 = this$0.binding;
            if (activityCommunityWebviewBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding4 = null;
            }
            activityCommunityWebviewBinding4.warningNotificationCard.setTranslationY(-300.0f);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding5 = this$0.binding;
            if (activityCommunityWebviewBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding5 = null;
            }
            activityCommunityWebviewBinding5.warningNotificationCard.setAlpha(0.0f);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding6 = this$0.binding;
            if (activityCommunityWebviewBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding6 = null;
            }
            activityCommunityWebviewBinding6.warningNotificationCard.setVisibility(0);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding7 = this$0.binding;
            if (activityCommunityWebviewBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityCommunityWebviewBinding2 = activityCommunityWebviewBinding7;
            }
            activityCommunityWebviewBinding2.warningNotificationCard.animate().translationY(0.0f).alpha(1.0f).setDuration(300L).setInterpolator(new DecelerateInterpolator()).start();
        }
        Runnable runnable2 = new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CommunityWebViewActivity.showWarningNotificationCard$lambda$5$lambda$4(this.f$0);
            }
        };
        this$0.hideWarningRunnable = runnable2;
        this$0.mainHandler.postDelayed(runnable2, DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showWarningNotificationCard$lambda$5$lambda$4(CommunityWebViewActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideWarningNotificationCard();
    }

    private final void hideWarningNotificationCard() {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                CommunityWebViewActivity.hideWarningNotificationCard$lambda$7(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideWarningNotificationCard$lambda$7(final CommunityWebViewActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this$0.binding;
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding2 = null;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        if (activityCommunityWebviewBinding.warningNotificationCard.getVisibility() == 0) {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding3 = this$0.binding;
            if (activityCommunityWebviewBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityCommunityWebviewBinding2 = activityCommunityWebviewBinding3;
            }
            activityCommunityWebviewBinding2.warningNotificationCard.animate().translationY(-300.0f).alpha(0.0f).setDuration(250L).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    CommunityWebViewActivity.hideWarningNotificationCard$lambda$7$lambda$6(this.f$0);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideWarningNotificationCard$lambda$7$lambda$6(CommunityWebViewActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this$0.binding;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        activityCommunityWebviewBinding.warningNotificationCard.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showNotificationCard(final String title, final String status, final boolean isFinished) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                CommunityWebViewActivity.showNotificationCard$lambda$8(this.f$0, title, status, isFinished);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showNotificationCard$lambda$8(CommunityWebViewActivity this$0, String title, String status, boolean z) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(title, "$title");
        Intrinsics.checkNotNullParameter(status, "$status");
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this$0.binding;
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding2 = null;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        activityCommunityWebviewBinding.tvNotificationTitle.setText(title);
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding3 = this$0.binding;
        if (activityCommunityWebviewBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding3 = null;
        }
        activityCommunityWebviewBinding3.tvNotificationStatus.setText(status);
        if (z) {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding4 = this$0.binding;
            if (activityCommunityWebviewBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding4 = null;
            }
            activityCommunityWebviewBinding4.notificationProgressBar.setVisibility(8);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding5 = this$0.binding;
            if (activityCommunityWebviewBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding5 = null;
            }
            activityCommunityWebviewBinding5.btnOpenProject.setVisibility(0);
        } else {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding6 = this$0.binding;
            if (activityCommunityWebviewBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding6 = null;
            }
            activityCommunityWebviewBinding6.notificationProgressBar.setVisibility(0);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding7 = this$0.binding;
            if (activityCommunityWebviewBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding7 = null;
            }
            activityCommunityWebviewBinding7.btnOpenProject.setVisibility(8);
        }
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding8 = this$0.binding;
        if (activityCommunityWebviewBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding8 = null;
        }
        if (activityCommunityWebviewBinding8.downloadNotificationCard.getVisibility() != 0) {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding9 = this$0.binding;
            if (activityCommunityWebviewBinding9 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding9 = null;
            }
            activityCommunityWebviewBinding9.downloadNotificationCard.setTranslationY(300.0f);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding10 = this$0.binding;
            if (activityCommunityWebviewBinding10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding10 = null;
            }
            activityCommunityWebviewBinding10.downloadNotificationCard.setAlpha(0.0f);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding11 = this$0.binding;
            if (activityCommunityWebviewBinding11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding11 = null;
            }
            activityCommunityWebviewBinding11.downloadNotificationCard.setVisibility(0);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding12 = this$0.binding;
            if (activityCommunityWebviewBinding12 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityCommunityWebviewBinding2 = activityCommunityWebviewBinding12;
            }
            activityCommunityWebviewBinding2.downloadNotificationCard.animate().translationY(0.0f).alpha(1.0f).setDuration(400L).setInterpolator(new OvershootInterpolator(1.1f)).start();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void hideNotificationCard$default(CommunityWebViewActivity communityWebViewActivity, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = null;
        }
        communityWebViewActivity.hideNotificationCard(function0);
    }

    private final void hideNotificationCard(final Function0<Unit> onAnimationEnd) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CommunityWebViewActivity.hideNotificationCard$lambda$10(this.f$0, onAnimationEnd);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideNotificationCard$lambda$10(final CommunityWebViewActivity this$0, final Function0 function0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this$0.binding;
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding2 = null;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        if (activityCommunityWebviewBinding.downloadNotificationCard.getVisibility() != 0) {
            if (function0 != null) {
                function0.invoke();
            }
        } else {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding3 = this$0.binding;
            if (activityCommunityWebviewBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityCommunityWebviewBinding2 = activityCommunityWebviewBinding3;
            }
            activityCommunityWebviewBinding2.downloadNotificationCard.animate().translationY(300.0f).alpha(0.0f).setDuration(300L).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CommunityWebViewActivity.hideNotificationCard$lambda$10$lambda$9(this.f$0, function0);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideNotificationCard$lambda$10$lambda$9(CommunityWebViewActivity this$0, Function0 function0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ActivityCommunityWebviewBinding activityCommunityWebviewBinding = this$0.binding;
        if (activityCommunityWebviewBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityCommunityWebviewBinding = null;
        }
        activityCommunityWebviewBinding.downloadNotificationCard.setVisibility(8);
        if (function0 != null) {
            function0.invoke();
        }
    }

    public final void startNativeDownloadAndImport(String url) {
        String strSubstringAfterLast$default;
        Intrinsics.checkNotNullParameter(url, "url");
        if (StringsKt.startsWith(url, "http://", true) || StringsKt.startsWith(url, "https://", true)) {
            String str = url;
            if (StringsKt.contains$default((CharSequence) str, (CharSequence) "/file", false, 2, (Object) null)) {
                strSubstringAfterLast$default = StringsKt.substringAfterLast$default(StringsKt.substringBefore$default(url, "/file", (String) null, 2, (Object) null), "/", (String) null, 2, (Object) null);
            } else if (StringsKt.contains$default((CharSequence) str, (CharSequence) Constants.CATROBAT_EXTENSION, false, 2, (Object) null)) {
                strSubstringAfterLast$default = StringsKt.substringAfterLast$default(StringsKt.substringBefore$default(url, Constants.CATROBAT_EXTENSION, (String) null, 2, (Object) null), "_", (String) null, 2, (Object) null);
            } else if (StringsKt.contains$default((CharSequence) str, (CharSequence) Constants.NEW_CATROBAT_EXTENSION, false, 2, (Object) null)) {
                strSubstringAfterLast$default = StringsKt.substringAfterLast$default(StringsKt.substringBefore$default(url, Constants.NEW_CATROBAT_EXTENSION, (String) null, 2, (Object) null), "_", (String) null, 2, (Object) null);
            } else {
                strSubstringAfterLast$default = StringsKt.substringAfterLast$default(url, "/", (String) null, 2, (Object) null);
            }
            Set<String> activeDownloads = this.activeDownloads;
            Intrinsics.checkNotNullExpressionValue(activeDownloads, "activeDownloads");
            if (!activeDownloads.isEmpty()) {
                String string = getString(R.string.community_download_already_active);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                showWarningNotificationCard(string);
                return;
            }
            this.activeDownloads.add(strSubstringAfterLast$default);
            String string2 = getString(R.string.community_download_title);
            Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
            String string3 = getString(R.string.community_saving_to_cache);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            showNotificationCard(string2, string3, false);
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass1(strSubstringAfterLast$default, url, null), 3, null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/CommunityWebViewActivity$startNativeDownloadAndImport$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.CommunityWebViewActivity$startNativeDownloadAndImport$1, reason: invalid class name */
    /* JADX INFO: compiled from: CommunityWebViewActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.CommunityWebViewActivity$startNativeDownloadAndImport$1", f = "CommunityWebViewActivity.kt", i = {}, l = {277, 286}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $gameId;
        final /* synthetic */ String $url;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, String str2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$gameId = str;
            this.$url = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return CommunityWebViewActivity.this.new AnonymousClass1(this.$gameId, this.$url, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0076  */
        /* JADX WARN: Code duplicated, block: B:23:0x009e  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            final String str;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
                str = (String) obj;
                if (str != null) {
                    CommunityWebViewActivity communityWebViewActivity = CommunityWebViewActivity.this;
                    String string = communityWebViewActivity.getString(R.string.community_import_completed);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    String string2 = CommunityWebViewActivity.this.getString(R.string.community_project_ready);
                    Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                    communityWebViewActivity.showNotificationCard(string, string2, true);
                    final CommunityWebViewActivity communityWebViewActivity2 = CommunityWebViewActivity.this;
                    communityWebViewActivity2.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$startNativeDownloadAndImport$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            CommunityWebViewActivity.AnonymousClass1.invokeSuspend$lambda$2(communityWebViewActivity2, str);
                        }
                    });
                } else {
                    CommunityWebViewActivity.hideNotificationCard$default(CommunityWebViewActivity.this, null, 1, null);
                }
                CommunityWebViewActivity.this.activeDownloads.remove(this.$gameId);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            obj = BuildersKt.withContext(Dispatchers.getIO(), new CommunityWebViewActivity$startNativeDownloadAndImport$1$tempFile$1(CommunityWebViewActivity.this, this.$url, this.$gameId, null), this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
            File file = (File) obj;
            if (file != null && file.exists()) {
                final CommunityWebViewActivity communityWebViewActivity3 = CommunityWebViewActivity.this;
                communityWebViewActivity3.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$startNativeDownloadAndImport$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        CommunityWebViewActivity.AnonymousClass1.invokeSuspend$lambda$0(communityWebViewActivity3);
                    }
                });
                this.label = 2;
                obj = BuildersKt.withContext(Dispatchers.getIO(), new CommunityWebViewActivity$startNativeDownloadAndImport$1$projectName$1(CommunityWebViewActivity.this, file, this.$gameId, null), this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
                str = (String) obj;
                if (str != null) {
                    CommunityWebViewActivity communityWebViewActivity4 = CommunityWebViewActivity.this;
                    String string3 = communityWebViewActivity4.getString(R.string.community_import_completed);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    String string4 = CommunityWebViewActivity.this.getString(R.string.community_project_ready);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    communityWebViewActivity4.showNotificationCard(string3, string4, true);
                    final CommunityWebViewActivity communityWebViewActivity5 = CommunityWebViewActivity.this;
                    communityWebViewActivity5.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$startNativeDownloadAndImport$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            CommunityWebViewActivity.AnonymousClass1.invokeSuspend$lambda$2(communityWebViewActivity5, str);
                        }
                    });
                } else {
                    CommunityWebViewActivity.hideNotificationCard$default(CommunityWebViewActivity.this, null, 1, null);
                }
            } else {
                CommunityWebViewActivity.hideNotificationCard$default(CommunityWebViewActivity.this, null, 1, null);
            }
            CommunityWebViewActivity.this.activeDownloads.remove(this.$gameId);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(CommunityWebViewActivity communityWebViewActivity) {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding = communityWebViewActivity.binding;
            if (activityCommunityWebviewBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding = null;
            }
            activityCommunityWebviewBinding.tvNotificationStatus.setText(communityWebViewActivity.getString(R.string.community_importing_files));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$2(final CommunityWebViewActivity communityWebViewActivity, final String str) {
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding = communityWebViewActivity.binding;
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding2 = null;
            if (activityCommunityWebviewBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding = null;
            }
            activityCommunityWebviewBinding.btnOpenProject.setOnClickListener(null);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding3 = communityWebViewActivity.binding;
            if (activityCommunityWebviewBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
            } else {
                activityCommunityWebviewBinding2 = activityCommunityWebviewBinding3;
            }
            activityCommunityWebviewBinding2.btnOpenProject.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$startNativeDownloadAndImport$1$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CommunityWebViewActivity.AnonymousClass1.invokeSuspend$lambda$2$lambda$1(str, communityWebViewActivity, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$2$lambda$1(String str, CommunityWebViewActivity communityWebViewActivity, View view) {
            Log.d("CommunityWebViewDebug", "Кнопка 'Открыть' нажата! Запускаем проект: " + str);
            ActivityCommunityWebviewBinding activityCommunityWebviewBinding = communityWebViewActivity.binding;
            if (activityCommunityWebviewBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityCommunityWebviewBinding = null;
            }
            activityCommunityWebviewBinding.downloadNotificationCard.setVisibility(8);
            communityWebViewActivity.loadImportedProject(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File downloadProjectFile(String url, String gameId) {
        File file;
        String token = CommunityTokenManager.getToken(this);
        Request.Builder builderUrl = new Request.Builder().url(url);
        String str = token;
        if (str != null && str.length() != 0) {
            builderUrl.addHeader("Authorization", "Bearer " + token);
        }
        try {
            Response responseExecute = this.client.newCall(builderUrl.build()).execute();
            try {
                Response response = responseExecute;
                if (response.isSuccessful() && response.body() != null) {
                    file = new File(getCacheDir(), "temp_project_" + gameId + "_" + System.currentTimeMillis() + Constants.ZIP_EXTENSION);
                    ResponseBody responseBodyBody = response.body();
                    Intrinsics.checkNotNull(responseBodyBody);
                    InputStream inputStreamByteStream = responseBodyBody.byteStream();
                    try {
                        InputStream inputStream = inputStreamByteStream;
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                            CloseableKt.closeFinally(inputStreamByteStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            CloseableKt.closeFinally(inputStreamByteStream, th3);
                            throw th4;
                        }
                    }
                } else {
                    final String str2 = "HTTP " + response.code() + ": " + response.message();
                    runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            CommunityWebViewActivity.downloadProjectFile$lambda$14$lambda$13(this.f$0, str2);
                        }
                    });
                    file = null;
                }
                CloseableKt.closeFinally(responseExecute, null);
                return file;
            } catch (Throwable th5) {
                try {
                    throw th5;
                } catch (Throwable th6) {
                    CloseableKt.closeFinally(responseExecute, th5);
                    throw th6;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    CommunityWebViewActivity.downloadProjectFile$lambda$15(this.f$0, e);
                }
            });
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadProjectFile$lambda$14$lambda$13(CommunityWebViewActivity this$0, String errorMsg) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(errorMsg, "$errorMsg");
        String string = this$0.getString(R.string.community_error_server_rejected, new Object[]{errorMsg});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        Toast.makeText(this$0, string, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void downloadProjectFile$lambda$15(CommunityWebViewActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "$e");
        String message = e.getMessage();
        if (message == null) {
            message = "";
        }
        String string = this$0.getString(R.string.community_error_network, new Object[]{message});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        Toast.makeText(this$0, string, 1).show();
    }

    private final boolean isValidZipFile(File file) {
        if (!file.exists() || file.length() < 4) {
            return false;
        }
        byte[] bArr = new byte[4];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                fileInputStream.read(bArr);
                CloseableKt.closeFinally(fileInputStream, null);
                return bArr[0] == 80 && bArr[1] == 75 && bArr[2] == 3 && bArr[3] == 4;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileInputStream, th);
                    throw th2;
                }
            }
        } catch (Exception unused) {
            return false;
        }
    }

    private final String getProjectNameFromXml(File unzipDir) {
        List<String> groupValues;
        List<String> groupValues2;
        String string;
        List<String> groupValues3;
        File file = new File(unzipDir, Constants.CODE_XML_FILE_NAME);
        if (!file.exists()) {
            return null;
        }
        try {
            String text$default = FilesKt.readText$default(file, null, 1, null);
            MatchResult matchResultFind$default = Regex.find$default(new Regex("<programName>(.*?)</programName>"), text$default, 0, 2, null);
            String str = (matchResultFind$default == null || (groupValues3 = matchResultFind$default.getGroupValues()) == null) ? null : groupValues3.get(1);
            String str2 = str;
            if (str2 == null || str2.length() == 0) {
                MatchResult matchResultFind$default2 = Regex.find$default(new Regex("<projectName>(.*?)</projectName>"), text$default, 0, 2, null);
                str = (matchResultFind$default2 == null || (groupValues = matchResultFind$default2.getGroupValues()) == null) ? null : groupValues.get(1);
            }
            String str3 = str;
            if (str3 == null || str3.length() == 0) {
                MatchResult matchResultFind$default3 = Regex.find$default(new Regex("<name>(.*?)</name>"), text$default, 0, 2, null);
                str = (matchResultFind$default3 == null || (groupValues2 = matchResultFind$default3.getGroupValues()) == null) ? null : groupValues2.get(1);
            }
            if (str == null || (string = StringsKt.trim((CharSequence) str).toString()) == null) {
                return null;
            }
            return decodeXmlEntities(string);
        } catch (Exception unused) {
            return null;
        }
    }

    private final String decodeXmlEntities(String input) {
        return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(input, "&amp;", ContainerUtils.FIELD_DELIMITER, false, 4, (Object) null), "&lt;", "<", false, 4, (Object) null), "&gt;", ">", false, 4, (Object) null), "&quot;", "\"", false, 4, (Object) null), "&apos;", FormatHelper.QUOTE, false, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String unzipAndImport(File zipFile, String gameId) {
        if (!isValidZipFile(zipFile)) {
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CommunityWebViewActivity.unzipAndImport$lambda$18(this.f$0);
                }
            });
            zipFile.delete();
            return null;
        }
        File file = new File(getCacheDir(), "temp_unzip_" + gameId + "_" + System.currentTimeMillis());
        try {
            if (file.exists()) {
                FilesKt.deleteRecursively(file);
            }
            file.mkdirs();
            new ZipArchiver().unzip(new FileInputStream(zipFile), file);
            zipFile.delete();
            String projectNameFromXml = getProjectNameFromXml(file);
            String str = projectNameFromXml;
            if (str == null || str.length() == 0) {
                projectNameFromXml = "Imported_" + gameId;
            }
            File file2 = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(projectNameFromXml));
            if (file2.exists()) {
                FilesKt.deleteRecursively(file2);
            }
            if (file.renameTo(file2)) {
                Log.d("CommunityWebViewDebug", "Перемещено в: " + file2.getAbsolutePath());
            } else {
                file2.mkdirs();
                FilesKt.copyRecursively$default(file, file2, true, null, 4, null);
                FilesKt.deleteRecursively(file);
            }
            return projectNameFromXml;
        } catch (Exception e) {
            e.printStackTrace();
            if (file.exists()) {
                FilesKt.deleteRecursively(file);
            }
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.CommunityWebViewActivity$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    CommunityWebViewActivity.unzipAndImport$lambda$19(this.f$0, e);
                }
            });
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unzipAndImport$lambda$18(CommunityWebViewActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(this$0, R.string.community_error_zip_damaged, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void unzipAndImport$lambda$19(CommunityWebViewActivity this$0, Exception e) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(e, "$e");
        String message = e.getMessage();
        if (message == null) {
            message = "";
        }
        String string = this$0.getString(R.string.community_error_unzip, new Object[]{message});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        Toast.makeText(this$0, string, 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadImportedProject(String name2) {
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(name2));
        Log.d("CommunityWebViewDebug", "Загрузка импортированного проекта: " + file.getAbsolutePath());
        ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, this).setListener(this), null, 1, null);
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        Log.d("CommunityWebViewDebug", "Импорт завершен. Результат загрузки проекта: " + success);
        if (success) {
            Intent intent = new Intent(this, (Class<?>) ProjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            startActivity(intent);
            finish();
            return;
        }
        Toast.makeText(this, R.string.community_load_project_failed, 0).show();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        WebView webView = this.webView;
        WebView webView2 = null;
        if (webView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("webView");
            webView = null;
        }
        if (webView.canGoBack()) {
            WebView webView3 = this.webView;
            if (webView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("webView");
            } else {
                webView2 = webView3;
            }
            webView2.goBack();
            return;
        }
        super.onBackPressed();
    }
}
