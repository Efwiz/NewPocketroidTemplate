package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityCommunityWebviewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityCommunityWebviewBinding implements ViewBinding {
    public final ImageButton btnDismissNotification;
    public final MaterialButton btnOpenProject;
    public final MaterialCardView downloadNotificationCard;
    public final ProgressBar notificationProgressBar;
    private final ConstraintLayout rootView;
    public final LinearLayout textContainer;
    public final TextView tvNotificationStatus;
    public final TextView tvNotificationTitle;
    public final TextView tvWarningText;
    public final MaterialCardView warningNotificationCard;
    public final WebView webView;

    private ActivityCommunityWebviewBinding(ConstraintLayout rootView, ImageButton btnDismissNotification, MaterialButton btnOpenProject, MaterialCardView downloadNotificationCard, ProgressBar notificationProgressBar, LinearLayout textContainer, TextView tvNotificationStatus, TextView tvNotificationTitle, TextView tvWarningText, MaterialCardView warningNotificationCard, WebView webView) {
        this.rootView = rootView;
        this.btnDismissNotification = btnDismissNotification;
        this.btnOpenProject = btnOpenProject;
        this.downloadNotificationCard = downloadNotificationCard;
        this.notificationProgressBar = notificationProgressBar;
        this.textContainer = textContainer;
        this.tvNotificationStatus = tvNotificationStatus;
        this.tvNotificationTitle = tvNotificationTitle;
        this.tvWarningText = tvWarningText;
        this.warningNotificationCard = warningNotificationCard;
        this.webView = webView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActivityCommunityWebviewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityCommunityWebviewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_community_webview, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityCommunityWebviewBinding bind(View rootView) {
        int i = R.id.btnDismissNotification;
        ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.btnDismissNotification);
        if (imageButton != null) {
            i = R.id.btnOpenProject;
            MaterialButton materialButton = (MaterialButton) ViewBindings.findChildViewById(rootView, R.id.btnOpenProject);
            if (materialButton != null) {
                i = R.id.downloadNotificationCard;
                MaterialCardView materialCardView = (MaterialCardView) ViewBindings.findChildViewById(rootView, R.id.downloadNotificationCard);
                if (materialCardView != null) {
                    i = R.id.notificationProgressBar;
                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, R.id.notificationProgressBar);
                    if (progressBar != null) {
                        i = R.id.textContainer;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, R.id.textContainer);
                        if (linearLayout != null) {
                            i = R.id.tvNotificationStatus;
                            TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvNotificationStatus);
                            if (textView != null) {
                                i = R.id.tvNotificationTitle;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvNotificationTitle);
                                if (textView2 != null) {
                                    i = R.id.tvWarningText;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.tvWarningText);
                                    if (textView3 != null) {
                                        i = R.id.warningNotificationCard;
                                        MaterialCardView materialCardView2 = (MaterialCardView) ViewBindings.findChildViewById(rootView, R.id.warningNotificationCard);
                                        if (materialCardView2 != null) {
                                            i = R.id.webView;
                                            WebView webView = (WebView) ViewBindings.findChildViewById(rootView, R.id.webView);
                                            if (webView != null) {
                                                return new ActivityCommunityWebviewBinding((ConstraintLayout) rootView, imageButton, materialButton, materialCardView, progressBar, linearLayout, textView, textView2, textView3, materialCardView2, webView);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
