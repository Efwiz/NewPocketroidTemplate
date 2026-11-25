package org.catrobat.catroid.ui;

import com.google.android.material.tabs.TabLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SpriteActivityOnTabSelectedListener.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpriteActivityOnTabSelectedListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0012\u0010\t\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\f\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\r\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/ui/SpriteActivityOnTabSelectedListener;", "Lcom/google/android/material/tabs/TabLayout$OnTabSelectedListener;", "loadFragment", "Lkotlin/reflect/KFunction1;", "", "", "(Lkotlin/reflect/KFunction;)V", "getLoadFragment", "()Lkotlin/reflect/KFunction;", "onTabReselected", "tab", "Lcom/google/android/material/tabs/TabLayout$Tab;", "onTabSelected", "onTabUnselected", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SpriteActivityOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
    private final KFunction<Unit> loadFragment;

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    public SpriteActivityOnTabSelectedListener(KFunction<Unit> loadFragment) {
        Intrinsics.checkNotNullParameter(loadFragment, "loadFragment");
        this.loadFragment = loadFragment;
    }

    public final KFunction<Unit> getLoadFragment() {
        return this.loadFragment;
    }

    @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
    public void onTabSelected(TabLayout.Tab tab) {
        ((Function1) this.loadFragment).invoke(Integer.valueOf(tab != null ? tab.getPosition() : 0));
    }
}
