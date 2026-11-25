package org.catrobat.paintroid.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.web.MediaGalleryWebViewClient;

/* JADX INFO: compiled from: CatroidMediaGalleryFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\b\u001a\u00020\tH\u0016J$\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\b\u0010\u0012\u001a\u00020\tH\u0016J\u001a\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0017J\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/ui/fragments/CatroidMediaGalleryFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/catrobat/paintroid/web/MediaGalleryWebViewClient$WebClientCallback;", "()V", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/paintroid/ui/fragments/CatroidMediaGalleryFragment$MediaGalleryListener;", "webView", "Landroid/webkit/WebView;", "finish", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onViewCreated", Promotion.ACTION_VIEW, "setMediaGalleryListener", "MediaGalleryListener", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CatroidMediaGalleryFragment extends Fragment implements MediaGalleryWebViewClient.WebClientCallback {
    private MediaGalleryListener listener;
    private WebView webView;

    /* JADX INFO: compiled from: CatroidMediaGalleryFragment.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&¨\u0006\b"}, d2 = {"Lorg/catrobat/paintroid/ui/fragments/CatroidMediaGalleryFragment$MediaGalleryListener;", "", "bitmapLoadedFromSource", "", "loadedBitmap", "Landroid/graphics/Bitmap;", "dismissProgressDialog", "showProgressDialog", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface MediaGalleryListener {
        void bitmapLoadedFromSource(Bitmap loadedBitmap);

        void dismissProgressDialog();

        void showProgressDialog();
    }

    public final void setMediaGalleryListener(MediaGalleryListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.listener = listener;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.dialog_pocketpaint_webview, container, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflater.inflate(R.layou…ebview, container, false)");
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        this.webView = webView;
        if (webView == null) {
            return;
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MediaGalleryWebViewClient(this));
        webView.getSettings().setUserAgentString(Constants.POCKET_CODE_EXTERNAL_EXPORT_STORAGE_FOLDER_NAME);
        webView.loadUrl(ConstantsKt.MEDIA_GALLEY_URL);
        webView.setDownloadListener(new DownloadListener() { // from class: org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment$$ExternalSyntheticLambda0
            @Override // android.webkit.DownloadListener
            public final void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                CatroidMediaGalleryFragment.m5979onViewCreated$lambda1$lambda0(this.f$0, str, str2, str3, str4, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onViewCreated$lambda-1$lambda-0, reason: not valid java name */
    public static final void m5979onViewCreated$lambda1$lambda0(final CatroidMediaGalleryFragment this$0, String str, String str2, String str3, String str4, long j) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this$0.getActivity()));
        MediaGalleryListener mediaGalleryListener = this$0.listener;
        if (mediaGalleryListener != null) {
            mediaGalleryListener.showProgressDialog();
        }
        imageLoader.loadImage(str, new SimpleImageLoadingListener() { // from class: org.catrobat.paintroid.ui.fragments.CatroidMediaGalleryFragment$onViewCreated$1$1$1
            @Override // com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener, com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                CatroidMediaGalleryFragment.MediaGalleryListener mediaGalleryListener2;
                Intrinsics.checkNotNullParameter(imageUri, "imageUri");
                if (loadedImage != null && (mediaGalleryListener2 = this.this$0.listener) != null) {
                    mediaGalleryListener2.bitmapLoadedFromSource(loadedImage);
                }
                CatroidMediaGalleryFragment.MediaGalleryListener mediaGalleryListener3 = this.this$0.listener;
                if (mediaGalleryListener3 == null) {
                    return;
                }
                mediaGalleryListener3.dismissProgressDialog();
            }

            @Override // com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener, com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingCancelled(String imageUri, View view) {
                Intrinsics.checkNotNullParameter(imageUri, "imageUri");
                CatroidMediaGalleryFragment.MediaGalleryListener mediaGalleryListener2 = this.this$0.listener;
                if (mediaGalleryListener2 == null) {
                    return;
                }
                mediaGalleryListener2.dismissProgressDialog();
            }

            @Override // com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener, com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Intrinsics.checkNotNullParameter(imageUri, "imageUri");
                Intrinsics.checkNotNullParameter(failReason, "failReason");
                CatroidMediaGalleryFragment.MediaGalleryListener mediaGalleryListener2 = this.this$0.listener;
                if (mediaGalleryListener2 == null) {
                    return;
                }
                mediaGalleryListener2.dismissProgressDialog();
            }
        });
        this$0.finish();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        WebView webView = this.webView;
        if (webView != null) {
            webView.setDownloadListener(null);
        }
        WebView webView2 = this.webView;
        if (webView2 != null) {
            webView2.destroy();
        }
        super.onDestroy();
    }

    @Override // org.catrobat.paintroid.web.MediaGalleryWebViewClient.WebClientCallback
    public void finish() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
