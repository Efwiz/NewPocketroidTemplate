package org.catrobat.catroid.ui.fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ui.adapter.BrickCategoryAdapter;
import org.catrobat.catroid.ui.settingsfragments.AccessibilityProfile;
import org.catrobat.catroid.ui.settingsfragments.RaspberryPiSettingsFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/BrickCategoryListBuilder.class
  classes14.dex
 */
/* JADX INFO: compiled from: BrickCategoryListBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\b\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/BrickCategoryListBuilder;", "", "activity", "Landroidx/fragment/app/FragmentActivity;", "(Landroidx/fragment/app/FragmentActivity;)V", "getBrickCategoryViews", "", "Landroid/view/View;", "getCategoryNames", "", "", "onlyBeginnerBricks", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BrickCategoryListBuilder {
    private final FragmentActivity activity;

    public BrickCategoryListBuilder(FragmentActivity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.activity = activity;
    }

    private final boolean onlyBeginnerBricks() {
        return PreferenceManager.getDefaultSharedPreferences(this.activity).getBoolean(AccessibilityProfile.BEGINNER_BRICKS, false);
    }

    public final List<View> getBrickCategoryViews() {
        ArrayList arrayList = new ArrayList();
        LayoutInflater layoutInflater = this.activity.getLayoutInflater();
        Intrinsics.checkNotNullExpressionValue(layoutInflater, "getLayoutInflater(...)");
        View viewInflate = layoutInflater.inflate(R.layout.brick_category_favorites, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(...)");
        arrayList.add(viewInflate);
        View viewInflate2 = layoutInflater.inflate(R.layout.brick_category_recently_used, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate2, "inflate(...)");
        arrayList.add(viewInflate2);
        if (SettingsFragment.isEmroiderySharedPreferenceEnabled(this.activity)) {
            View viewInflate3 = layoutInflater.inflate(R.layout.brick_category_embroidery, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate3, "inflate(...)");
            arrayList.add(viewInflate3);
        }
        if (SettingsFragment.isPlotSharedPreferenceEnabled(this.activity)) {
            View viewInflate4 = layoutInflater.inflate(R.layout.brick_category_plot, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate4, "inflate(...)");
            arrayList.add(viewInflate4);
        }
        if (SettingsFragment.isNeuralSharedPreferenceEnabled(this.activity)) {
            View viewInflate5 = layoutInflater.inflate(R.layout.brick_category_neural, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate5, "inflate(...)");
            arrayList.add(viewInflate5);
        }
        if (SettingsFragment.isPocketensorSharedPreferenceEnabled(this.activity)) {
            View viewInflate6 = layoutInflater.inflate(R.layout.brick_category_pocketensor, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate6, "inflate(...)");
            arrayList.add(viewInflate6);
        }
        if (SettingsFragment.isFast2dSharedPreferenceEnabled(this.activity)) {
            View viewInflate7 = layoutInflater.inflate(R.layout.brick_category_fast2d, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate7, "inflate(...)");
            arrayList.add(viewInflate7);
        }
        if (SettingsFragment.isThreedSharedPreferenceEnabled(this.activity)) {
            View viewInflate8 = layoutInflater.inflate(R.layout.brick_category_threed, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate8, "inflate(...)");
            arrayList.add(viewInflate8);
        }
        if (SettingsFragment.isInternetSharedPreferenceEnabled(this.activity)) {
            View viewInflate9 = layoutInflater.inflate(R.layout.brick_category_internet, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate9, "inflate(...)");
            arrayList.add(viewInflate9);
        }
        if (SettingsFragment.isMindstormsNXTSharedPreferenceEnabled(this.activity)) {
            View viewInflate10 = layoutInflater.inflate(R.layout.brick_category_lego_nxt, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate10, "inflate(...)");
            arrayList.add(viewInflate10);
        }
        if (SettingsFragment.isMindstormsEV3SharedPreferenceEnabled(this.activity)) {
            View viewInflate11 = layoutInflater.inflate(R.layout.brick_category_lego_ev3, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate11, "inflate(...)");
            arrayList.add(viewInflate11);
        }
        if (SettingsFragment.isDroneSharedPreferenceEnabled(this.activity)) {
            View viewInflate12 = layoutInflater.inflate(R.layout.brick_category_drone, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate12, "inflate(...)");
            arrayList.add(viewInflate12);
        }
        if (SettingsFragment.isJSSharedPreferenceEnabled(this.activity)) {
            View viewInflate13 = layoutInflater.inflate(R.layout.brick_category_drone_js, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate13, "inflate(...)");
            arrayList.add(viewInflate13);
        }
        if (SettingsFragment.isArduinoSharedPreferenceEnabled(this.activity)) {
            View viewInflate14 = layoutInflater.inflate(R.layout.brick_category_arduino, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate14, "inflate(...)");
            arrayList.add(viewInflate14);
        }
        if (RaspberryPiSettingsFragment.isRaspiSharedPreferenceEnabled(this.activity)) {
            View viewInflate15 = layoutInflater.inflate(R.layout.brick_category_raspi, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate15, "inflate(...)");
            arrayList.add(viewInflate15);
        }
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(this.activity)) {
            View viewInflate16 = layoutInflater.inflate(R.layout.brick_category_phiro, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate16, "inflate(...)");
            arrayList.add(viewInflate16);
        }
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            View viewInflate17 = layoutInflater.inflate(R.layout.brick_category_chromecast, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate17, "inflate(...)");
            arrayList.add(viewInflate17);
        }
        View viewInflate18 = layoutInflater.inflate(R.layout.brick_category_event, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate18, "inflate(...)");
        arrayList.add(viewInflate18);
        View viewInflate19 = layoutInflater.inflate(R.layout.brick_category_control, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate19, "inflate(...)");
        arrayList.add(viewInflate19);
        View viewInflate20 = layoutInflater.inflate(R.layout.brick_category_motion, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate20, "inflate(...)");
        arrayList.add(viewInflate20);
        View viewInflate21 = layoutInflater.inflate(R.layout.brick_category_sound, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate21, "inflate(...)");
        arrayList.add(viewInflate21);
        View viewInflate22 = layoutInflater.inflate(R.layout.brick_category_looks, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate22, "inflate(...)");
        arrayList.add(viewInflate22);
        if (!onlyBeginnerBricks()) {
            View viewInflate23 = layoutInflater.inflate(R.layout.brick_category_pen, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate23, "inflate(...)");
            arrayList.add(viewInflate23);
        }
        View viewInflate24 = layoutInflater.inflate(R.layout.brick_category_data, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate24, "inflate(...)");
        arrayList.add(viewInflate24);
        View viewInflate25 = layoutInflater.inflate(R.layout.brick_category_device, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate25, "inflate(...)");
        arrayList.add(viewInflate25);
        View viewInflate26 = layoutInflater.inflate(R.layout.brick_category_file, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate26, "inflate(...)");
        arrayList.add(viewInflate26);
        if (!onlyBeginnerBricks()) {
            View viewInflate27 = layoutInflater.inflate(R.layout.brick_category_userbrick, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate27, "inflate(...)");
            arrayList.add(viewInflate27);
        }
        View viewInflate28 = layoutInflater.inflate(R.layout.brick_category_libs, (ViewGroup) null);
        Intrinsics.checkNotNullExpressionValue(viewInflate28, "inflate(...)");
        arrayList.add(viewInflate28);
        if (SettingsFragment.isTestSharedPreferenceEnabled(this.activity)) {
            View viewInflate29 = layoutInflater.inflate(R.layout.brick_category_assert, (ViewGroup) null);
            Intrinsics.checkNotNullExpressionValue(viewInflate29, "inflate(...)");
            arrayList.add(viewInflate29);
        }
        return arrayList;
    }

    public final List<String> getCategoryNames() {
        ArrayList arrayList = new ArrayList();
        BrickCategoryAdapter brickCategoryAdapter = new BrickCategoryAdapter(getBrickCategoryViews());
        int count = brickCategoryAdapter.getCount();
        for (int i = 0; i < count; i++) {
            arrayList.add(brickCategoryAdapter.getItem(i));
        }
        return arrayList;
    }
}
