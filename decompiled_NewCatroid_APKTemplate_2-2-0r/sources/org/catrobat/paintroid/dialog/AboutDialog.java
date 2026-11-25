package org.catrobat.paintroid.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.text.HtmlCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.R;

/* JADX INFO: compiled from: AboutDialog.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0017J&\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016¨\u0006\u0010"}, d2 = {"Lorg/catrobat/paintroid/dialog/AboutDialog;", "Landroidx/appcompat/app/AppCompatDialogFragment;", "()V", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "", Promotion.ACTION_VIEW, "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AboutDialog extends AppCompatDialogFragment {
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        if (getShowsDialog()) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        return inflater.inflate(R.layout.dialog_pocketpaint_about, container, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.pocketpaint_about_version);
        TextView textView2 = (TextView) view.findViewById(R.id.pocketpaint_about_content);
        TextView textView3 = (TextView) view.findViewById(R.id.pocketpaint_about_license_url);
        TextView textView4 = (TextView) view.findViewById(R.id.pocketpaint_about_catrobat_url);
        String string = getString(R.string.pocketpaint_about_version, ((MainActivity) requireActivity()).getVersionCode());
        Intrinsics.checkNotNullExpressionValue(string, "getString(R.string.pocke…ctivity.getVersionCode())");
        textView.setText(string);
        String string2 = getString(R.string.pocketpaint_about_content, getString(R.string.pocketpaint_about_license));
        Intrinsics.checkNotNullExpressionValue(string2, "getString(\n            R…_about_license)\n        )");
        textView2.setText(string2);
        String string3 = getString(R.string.pocketpaint_about_url_license, getString(R.string.pocketpaint_about_url_license_description));
        Intrinsics.checkNotNullExpressionValue(string3, "getString(\n            R…se_description)\n        )");
        textView3.setText(HtmlCompat.fromHtml(string3, 0));
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        String string4 = getString(R.string.pocketpaint_about_url_catrobat, getString(R.string.pocketpaint_about_url_catrobat_description));
        Intrinsics.checkNotNullExpressionValue(string4, "getString(\n            R…at_description)\n        )");
        textView4.setText(HtmlCompat.fromHtml(string4, 0));
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override // androidx.appcompat.app.AppCompatDialogFragment, androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "requireActivity().layoutInflater");
        View layout = layoutInflater.inflate(R.layout.dialog_pocketpaint_about, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(layout, "layout");
        onViewCreated(layout, savedInstanceState);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(requireContext(), R.style.PocketPaintAlertDialog).setTitle(R.string.pocketpaint_about_title).setView(layout).setPositiveButton(R.string.done, new DialogInterface.OnClickListener() { // from class: org.catrobat.paintroid.dialog.AboutDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AboutDialog.m5923onCreateDialog$lambda0(this.f$0, dialogInterface, i);
            }
        }).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "Builder(requireContext()…) }\n            .create()");
        return alertDialogCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onCreateDialog$lambda-0, reason: not valid java name */
    public static final void m5923onCreateDialog$lambda0(AboutDialog this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }
}
