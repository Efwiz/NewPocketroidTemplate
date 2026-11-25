package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.ScriptFinder;
import org.catrobat.catroid.ui.dragndrop.BrickListView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/FragmentScriptBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class FragmentScriptBinding implements ViewBinding {
    public final CardView analysisStatusIndicator;
    public final RelativeLayout empty;
    public final TextView errorCountText;
    public final ImageView errorDot;
    public final ScriptFinder findview;
    public final LinearLayout fragmentScript;
    public final TextView fragmentScriptTextDescription;
    public final BrickListView list;
    private final LinearLayout rootView;
    public final TextView warningCountText;
    public final ImageView warningDot;

    private FragmentScriptBinding(LinearLayout rootView, CardView analysisStatusIndicator, RelativeLayout empty, TextView errorCountText, ImageView errorDot, ScriptFinder findview, LinearLayout fragmentScript, TextView fragmentScriptTextDescription, BrickListView list, TextView warningCountText, ImageView warningDot) {
        this.rootView = rootView;
        this.analysisStatusIndicator = analysisStatusIndicator;
        this.empty = empty;
        this.errorCountText = errorCountText;
        this.errorDot = errorDot;
        this.findview = findview;
        this.fragmentScript = fragmentScript;
        this.fragmentScriptTextDescription = fragmentScriptTextDescription;
        this.list = list;
        this.warningCountText = warningCountText;
        this.warningDot = warningDot;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static FragmentScriptBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentScriptBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.fragment_script, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static FragmentScriptBinding bind(View rootView) {
        int i = R.id.analysis_status_indicator;
        CardView cardView = (CardView) ViewBindings.findChildViewById(rootView, R.id.analysis_status_indicator);
        if (cardView != null) {
            i = android.R.id.empty;
            RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, android.R.id.empty);
            if (relativeLayout != null) {
                i = R.id.error_count_text;
                TextView textView = (TextView) ViewBindings.findChildViewById(rootView, R.id.error_count_text);
                if (textView != null) {
                    i = R.id.error_dot;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(rootView, R.id.error_dot);
                    if (imageView != null) {
                        i = R.id.findview;
                        ScriptFinder scriptFinder = (ScriptFinder) ViewBindings.findChildViewById(rootView, R.id.findview);
                        if (scriptFinder != null) {
                            LinearLayout linearLayout = (LinearLayout) rootView;
                            i = R.id.fragment_script_text_description;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(rootView, R.id.fragment_script_text_description);
                            if (textView2 != null) {
                                i = android.R.id.list;
                                BrickListView brickListView = (BrickListView) ViewBindings.findChildViewById(rootView, android.R.id.list);
                                if (brickListView != null) {
                                    i = R.id.warning_count_text;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(rootView, R.id.warning_count_text);
                                    if (textView3 != null) {
                                        i = R.id.warning_dot;
                                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(rootView, R.id.warning_dot);
                                        if (imageView2 != null) {
                                            return new FragmentScriptBinding(linearLayout, cardView, relativeLayout, textView, imageView, scriptFinder, linearLayout, textView2, brickListView, textView3, imageView2);
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
