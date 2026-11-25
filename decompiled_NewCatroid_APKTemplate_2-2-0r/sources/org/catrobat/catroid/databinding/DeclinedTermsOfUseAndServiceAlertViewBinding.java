package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/DeclinedTermsOfUseAndServiceAlertViewBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DeclinedTermsOfUseAndServiceAlertViewBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView shareWebsiteView;

    private DeclinedTermsOfUseAndServiceAlertViewBinding(LinearLayout rootView, TextView shareWebsiteView) {
        this.rootView = rootView;
        this.shareWebsiteView = shareWebsiteView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DeclinedTermsOfUseAndServiceAlertViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DeclinedTermsOfUseAndServiceAlertViewBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.declined_terms_of_use_and_service_alert_view, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static DeclinedTermsOfUseAndServiceAlertViewBinding bind(View rootView) {
        TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.share_website_view);
        if (textView != null) {
            return new DeclinedTermsOfUseAndServiceAlertViewBinding((LinearLayout) rootView, textView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(R.id.share_website_view)));
    }
}
