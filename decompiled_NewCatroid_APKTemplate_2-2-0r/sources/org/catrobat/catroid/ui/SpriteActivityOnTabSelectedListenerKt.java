package org.catrobat.catroid.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.tabs.TabLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.recyclerview.fragment.CatblocksScriptFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.LookListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SpriteActivityOnTabSelectedListenerKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpriteActivityOnTabSelectedListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a\u0016\u0010\u0004\u001a\u00020\u0005*\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a\f\u0010\t\u001a\u00020\b*\u0004\u0018\u00010\u0003\u001a\f\u0010\n\u001a\u00020\u0001*\u0004\u0018\u00010\u0003\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\f2\u0006\u0010\r\u001a\u00020\b\u001a\f\u0010\u000e\u001a\u00020\u0005*\u0004\u0018\u00010\u0006\u001a\u0018\u0010\u000f\u001a\u00020\u0005*\u0004\u0018\u00010\f2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a\u0014\u0010\u0010\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¨\u0006\u0013"}, d2 = {"unableToSelectNewFragmentFromCurrent", "", "fragment", "Landroidx/fragment/app/Fragment;", "addTabLayout", "", "Landroid/app/Activity;", "selectedTabPosition", "", "getTabPositionInSpriteActivity", "isFragmentWithTablayout", "loadFragment", "Lorg/catrobat/catroid/ui/SpriteActivity;", "fragmentPosition", "removeTabLayout", "setTabSelection", "showScripts", "fragmentTransaction", "Landroidx/fragment/app/FragmentTransaction;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SpriteActivityOnTabSelectedListenerKt {
    public static final void removeTabLayout(Activity activity) {
        if (activity instanceof SpriteActivity) {
            View viewFindViewById = activity.findViewById(R.id.workspace_layout);
            if (viewFindViewById == null || viewFindViewById.getVisibility() != 0) {
                View viewFindViewById2 = activity.findViewById(R.id.tab_layout);
                ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.activity_sprite);
                if (viewGroup != null) {
                    viewGroup.removeView(viewFindViewById2);
                }
            }
        }
    }

    public static final void addTabLayout(Activity activity, int i) {
        TabLayout.Tab tabAt;
        if (activity instanceof SpriteActivity) {
            View viewFindViewById = activity.findViewById(R.id.workspace_layout);
            if (viewFindViewById == null || viewFindViewById.getVisibility() != 0) {
                View viewInflate = ((SpriteActivity) activity).getLayoutInflater().inflate(R.layout.layout_tabs_sprite_activity, (ViewGroup) null);
                ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.activity_sprite);
                if (viewGroup != null) {
                    viewGroup.addView(viewInflate, 1);
                }
                TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.tab_layout);
                if (tabLayout != null && (tabAt = tabLayout.getTabAt(i)) != null) {
                    tabAt.select();
                }
                if (tabLayout != null) {
                    tabLayout.addOnTabSelectedListener((TabLayout.OnTabSelectedListener) new SpriteActivityOnTabSelectedListener(new AnonymousClass1(activity)));
                }
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SpriteActivityOnTabSelectedListenerKt$addTabLayout$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.SpriteActivityOnTabSelectedListenerKt$addTabLayout$1, reason: invalid class name */
    /* JADX INFO: compiled from: SpriteActivityOnTabSelectedListener.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<Integer, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, SpriteActivityOnTabSelectedListenerKt.class, "loadFragment", "loadFragment(Lorg/catrobat/catroid/ui/SpriteActivity;I)V", 1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
            invoke(num.intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(int i) {
            SpriteActivityOnTabSelectedListenerKt.loadFragment((SpriteActivity) this.receiver, i);
        }
    }

    public static final void loadFragment(SpriteActivity spriteActivity, int i) {
        Intrinsics.checkNotNullParameter(spriteActivity, "<this>");
        FragmentTransaction fragmentTransactionBeginTransaction = spriteActivity.getSupportFragmentManager().beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        if (unableToSelectNewFragmentFromCurrent(spriteActivity.getCurrentFragment())) {
            setTabSelection(spriteActivity, spriteActivity.getCurrentFragment());
            return;
        }
        if (i == 0) {
            showScripts(spriteActivity, fragmentTransactionBeginTransaction);
        } else if (i == 1) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new LookListFragment(), LookListFragment.TAG);
        } else if (i == 2) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new SoundListFragment(), SoundListFragment.TAG);
        } else {
            throw new IllegalArgumentException("Invalid fragmentPosition in Activity.");
        }
        fragmentTransactionBeginTransaction.commit();
    }

    private static final void showScripts(SpriteActivity spriteActivity, FragmentTransaction fragmentTransaction) {
        if (!SettingsFragment.useCatBlocks(spriteActivity)) {
            fragmentTransaction.replace(R.id.fragment_container, new ScriptFragment(), ScriptFragment.TAG);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, new CatblocksScriptFragment(null), CatblocksScriptFragment.INSTANCE.getTAG());
        }
    }

    public static final boolean isFragmentWithTablayout(Fragment fragment) {
        return (fragment instanceof ScriptFragment) || (fragment instanceof LookListFragment) || (fragment instanceof SoundListFragment);
    }

    public static final int getTabPositionInSpriteActivity(Fragment fragment) {
        if (fragment instanceof ScriptFragment) {
            return 0;
        }
        if (fragment instanceof LookListFragment) {
            return 1;
        }
        return fragment instanceof SoundListFragment ? 2 : 0;
    }

    private static final boolean unableToSelectNewFragmentFromCurrent(Fragment fragment) {
        return (fragment instanceof ScriptFragment) && ((ScriptFragment) fragment).isCurrentlyMoving();
    }

    private static final void setTabSelection(SpriteActivity spriteActivity, Fragment fragment) {
        TabLayout.Tab tabAt;
        TabLayout tabLayout = spriteActivity != null ? (TabLayout) spriteActivity.findViewById(R.id.tab_layout) : null;
        if (tabLayout == null || (tabAt = tabLayout.getTabAt(getTabPositionInSpriteActivity(fragment))) == null) {
            return;
        }
        tabAt.select();
    }
}
