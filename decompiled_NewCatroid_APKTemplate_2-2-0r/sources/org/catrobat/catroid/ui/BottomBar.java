package org.catrobat.catroid.ui;

import android.app.Activity;
import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/BottomBar.class
  classes14.dex
 */
/* JADX INFO: compiled from: BottomBar.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\b\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\t\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\n\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\u000b\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\f\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\r\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/ui/BottomBar;", "", "()V", "hideAddButton", "", "activity", "Landroid/app/Activity;", "hideAiAssistButton", "hideBottomBar", "hidePlayButton", "showAddButton", "showAiAssistButton", "showBottomBar", "showPlayButton", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BottomBar {
    public static final BottomBar INSTANCE = new BottomBar();

    @JvmStatic
    public static final void hideAiAssistButton(Activity activity) {
    }

    @JvmStatic
    public static final void showAiAssistButton(Activity activity) {
    }

    private BottomBar() {
    }

    @JvmStatic
    public static final void showBottomBar(Activity activity) {
        View viewFindViewById = activity != null ? activity.findViewById(R.id.bottom_bar) : null;
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setVisibility(0);
    }

    @JvmStatic
    public static final void hideBottomBar(Activity activity) {
        View viewFindViewById = activity != null ? activity.findViewById(R.id.bottom_bar) : null;
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setVisibility(8);
    }

    @JvmStatic
    public static final void showAddButton(Activity activity) {
        View viewFindViewById = activity != null ? activity.findViewById(R.id.button_add) : null;
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setVisibility(0);
    }

    @JvmStatic
    public static final void hideAddButton(Activity activity) {
        View viewFindViewById = activity != null ? activity.findViewById(R.id.button_add) : null;
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setVisibility(8);
    }

    @JvmStatic
    public static final void showPlayButton(Activity activity) {
        View viewFindViewById = activity != null ? activity.findViewById(R.id.button_play) : null;
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setVisibility(0);
    }

    @JvmStatic
    public static final void hidePlayButton(Activity activity) {
        View viewFindViewById = activity != null ? activity.findViewById(R.id.button_play) : null;
        if (viewFindViewById == null) {
            return;
        }
        viewFindViewById.setVisibility(8);
    }
}
