package androidx.core.view;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/view/NestedScrollingChild2.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core.jar:androidx/core/view/NestedScrollingChild2.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public interface NestedScrollingChild2 extends NestedScrollingChild {
    boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2, int i3);

    boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr, int i5);

    boolean hasNestedScrollingParent(int i);

    boolean startNestedScroll(int i, int i2);

    void stopNestedScroll(int i);
}
