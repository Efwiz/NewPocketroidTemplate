package org.catrobat.catroid.web;

import android.util.Log;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.ErrorLog;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/WebConnection.class
  classes14.dex
 */
/* JADX INFO: compiled from: WebConnection.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00142\u00020\u0001:\u0002\u0014\u0015B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\n\u0010\u0012\u001a\u0004\u0018\u00010\u0003H\u0002J\u0006\u0010\u0013\u001a\u00020\u000fR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/web/WebConnection;", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/catroid/web/WebConnection$WebRequestListener;", "url", "", "(Lorg/catrobat/catroid/web/WebConnection$WebRequestListener;Ljava/lang/String;)V", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;Lorg/catrobat/catroid/web/WebConnection$WebRequestListener;Ljava/lang/String;)V", "call", "Lokhttp3/Call;", "weakListenerReference", "Ljava/lang/ref/WeakReference;", "cancelCall", "", "createCallback", "Lokhttp3/Callback;", "popListener", "sendWebRequest", "Companion", "WebRequestListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WebConnection {
    private static final String EXCEPTION_MESSAGE_CANCELED = "Canceled";
    private static final String EXCEPTION_MESSAGE_TIMEOUT = "timeout";
    private Call call;
    private final OkHttpClient okHttpClient;
    private final String url;
    private WeakReference<WebRequestListener> weakListenerReference;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/WebConnection$WebRequestListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: WebConnection.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/web/WebConnection$WebRequestListener;", "", "onCancelledCall", "", "onRequestError", "httpError", "", "onRequestSuccess", "httpResponse", "Lokhttp3/Response;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface WebRequestListener {
        void onCancelledCall();

        void onRequestError(String httpError);

        void onRequestSuccess(Response httpResponse);
    }

    public WebConnection(OkHttpClient okHttpClient, WebRequestListener listener, String url) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(url, "url");
        this.okHttpClient = okHttpClient;
        this.url = url;
        this.weakListenerReference = new WeakReference<>(listener);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public WebConnection(WebRequestListener listener, String url) {
        StageListener stageListener;
        WebConnectionHolder webConnectionHolder;
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(url, "url");
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        this((stageActivity == null || (stageListener = stageActivity.stageListener) == null || (webConnectionHolder = stageListener.webConnectionHolder) == null) ? null : webConnectionHolder.getOkHttpClient(), listener, url);
        Log.d("WebConnection", "[" + System.identityHashCode(this) + "] Constructor called. URL: '" + url + "', Listener: " + System.identityHashCode(listener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized WebRequestListener popListener() {
        WebRequestListener webRequestListener;
        WeakReference<WebRequestListener> weakReference = this.weakListenerReference;
        webRequestListener = weakReference != null ? weakReference.get() : null;
        this.weakListenerReference = null;
        return webRequestListener;
    }

    public final synchronized void sendWebRequest() {
        Log.d("WebConnection", "[" + System.identityHashCode(this) + "] sendWebRequest CALLED. URL: '" + this.url + FormatHelper.QUOTE);
        try {
            try {
                Request requestBuild = new Request.Builder().url(this.url).header("User-Agent", "Mozilla/5.0 (Android 10; Mobile; rv:91.0) Gecko/91.0 Firefox/91.0").build();
                Log.d("WebConnection", "[" + System.identityHashCode(this) + "] Request object created. Starting enqueue...");
                OkHttpClient okHttpClient = this.okHttpClient;
                Call callNewCall = okHttpClient != null ? okHttpClient.newCall(requestBuild) : null;
                this.call = callNewCall;
                if (callNewCall != null) {
                    callNewCall.enqueue(createCallback());
                }
                Log.d("WebConnection", "[" + System.identityHashCode(this) + "] Enqueue called.");
            } catch (Exception e) {
                ErrorLog errorLog = ErrorLog.INSTANCE;
                String message = e.getMessage();
                if (message == null) {
                    message = "**message not provided :(**";
                }
                errorLog.log(message);
                Log.e("WebConnection", "[" + System.identityHashCode(this) + "] Generic Exception in sendWebRequest for URL: '" + this.url + FormatHelper.QUOTE, e);
                WebRequestListener webRequestListenerPopListener = popListener();
                if (webRequestListenerPopListener != null) {
                    webRequestListenerPopListener.onRequestError("500");
                }
            }
        } catch (IllegalArgumentException e2) {
            Log.e("WebConnection", "[" + System.identityHashCode(this) + "] IllegalArgumentException in sendWebRequest for URL: '" + this.url + FormatHelper.QUOTE, e2);
            WebRequestListener webRequestListenerPopListener2 = popListener();
            if (webRequestListenerPopListener2 != null) {
                webRequestListenerPopListener2.onRequestError("400");
            }
        }
    }

    private final Callback createCallback() {
        return new Callback() { // from class: org.catrobat.catroid.web.WebConnection.createCallback.1
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException e) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(e, "e");
                int iIdentityHashCode = System.identityHashCode(WebConnection.this);
                String str = WebConnection.this.url;
                WeakReference weakReference = WebConnection.this.weakListenerReference;
                Log.d("WebConnectionCallback", "[" + iIdentityHashCode + "] onFailure ENTERED. URL: " + str + ". Listener ref: " + (weakReference != null ? (WebRequestListener) weakReference.get() : null));
                WebRequestListener webRequestListenerPopListener = WebConnection.this.popListener();
                if (webRequestListenerPopListener == null) {
                    Log.w("WebConnectionCallback", "onFailure: Listener was null for URL: " + WebConnection.this.url);
                    return;
                }
                String message = e.getMessage();
                if (Intrinsics.areEqual(message, WebConnection.EXCEPTION_MESSAGE_TIMEOUT)) {
                    webRequestListenerPopListener.onRequestError("504");
                } else if (Intrinsics.areEqual(message, WebConnection.EXCEPTION_MESSAGE_CANCELED)) {
                    webRequestListenerPopListener.onCancelledCall();
                } else {
                    webRequestListenerPopListener.onRequestError("500");
                }
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(response, "response");
                int iIdentityHashCode = System.identityHashCode(WebConnection.this);
                String str = WebConnection.this.url;
                int iCode = response.code();
                WeakReference weakReference = WebConnection.this.weakListenerReference;
                Log.d("WebConnectionCallback", "[" + iIdentityHashCode + "] onResponse ENTERED. URL: " + str + ". Code: " + iCode + ". Listener ref: " + (weakReference != null ? (WebRequestListener) weakReference.get() : null));
                WebRequestListener webRequestListenerPopListener = WebConnection.this.popListener();
                if (webRequestListenerPopListener == null) {
                    Log.w("WebConnectionCallback", "onResponse: Listener was null for URL: " + WebConnection.this.url + ". Closing response body.");
                    response.close();
                } else {
                    if (response.isSuccessful()) {
                        webRequestListenerPopListener.onRequestSuccess(response);
                        return;
                    }
                    webRequestListenerPopListener.onRequestError(String.valueOf(response.code()));
                    Log.d("WebConnectionCallback", "Closing response body for unsuccessful request. URL: " + WebConnection.this.url + ", Code: " + response.code());
                    response.close();
                }
            }
        };
    }

    public final void cancelCall() {
        Dispatcher dispatcher;
        ExecutorService executorService;
        OkHttpClient okHttpClient = this.okHttpClient;
        if (okHttpClient == null || (dispatcher = okHttpClient.dispatcher()) == null || (executorService = dispatcher.executorService()) == null) {
            return;
        }
        executorService.execute(new Runnable() { // from class: org.catrobat.catroid.web.WebConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WebConnection.cancelCall$lambda$0(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cancelCall$lambda$0(WebConnection this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Call call = this$0.call;
        if (call != null) {
            call.cancel();
        }
    }
}
