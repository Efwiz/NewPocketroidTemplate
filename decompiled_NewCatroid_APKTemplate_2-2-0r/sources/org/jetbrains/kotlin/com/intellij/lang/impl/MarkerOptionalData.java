package org.jetbrains.kotlin.com.intellij.lang.impl;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.BitSet;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesAndCommentsBinder;
import org.jetbrains.kotlin.com.intellij.lang.WhitespacesBinders;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet;

/* JADX INFO: loaded from: classes3.dex */
final class MarkerOptionalData extends BitSet {
    private final Int2ObjectMap<Throwable> myDebugAllocationPositions = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<String> myDoneErrors = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<WhitespacesAndCommentsBinder> myLeftBinders = new Int2ObjectOpenHashMap();
    private final Int2ObjectMap<WhitespacesAndCommentsBinder> myRightBinders = new Int2ObjectOpenHashMap();
    private final IntSet myCollapsed = new IntOpenHashSet();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        } else {
            objArr[0] = "binder";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/impl/MarkerOptionalData";
        if (i != 1) {
            objArr[2] = "setErrorMessage";
        } else {
            objArr[2] = "assignBinder";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    MarkerOptionalData() {
    }

    void clean(int i) {
        if (get(i)) {
            set(i, false);
            this.myLeftBinders.remove(i);
            this.myRightBinders.remove(i);
            this.myDoneErrors.remove(i);
            this.myCollapsed.remove(i);
            this.myDebugAllocationPositions.remove(i);
        }
    }

    String getDoneError(int i) {
        return this.myDoneErrors.get(i);
    }

    boolean isCollapsed(int i) {
        return this.myCollapsed.contains(i);
    }

    void setErrorMessage(int i, String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        markAsHavingOptionalData(i);
        this.myDoneErrors.put(i, str);
    }

    void markCollapsed(int i) {
        markAsHavingOptionalData(i);
        this.myCollapsed.add(i);
    }

    private void markAsHavingOptionalData(int i) {
        set(i);
    }

    void notifyAllocated(int i) {
        markAsHavingOptionalData(i);
        this.myDebugAllocationPositions.put(i, new Throwable("Created at the following trace."));
    }

    Throwable getAllocationTrace(PsiBuilderImpl.StartMarker startMarker) {
        return this.myDebugAllocationPositions.get(startMarker.markerId);
    }

    WhitespacesAndCommentsBinder getBinder(int i, boolean z) {
        WhitespacesAndCommentsBinder whitespacesAndCommentsBinder = get(i) ? getBinderMap(z).get(i) : null;
        return whitespacesAndCommentsBinder != null ? whitespacesAndCommentsBinder : getDefaultBinder(z);
    }

    void assignBinder(int i, WhitespacesAndCommentsBinder whitespacesAndCommentsBinder, boolean z) {
        if (whitespacesAndCommentsBinder == null) {
            $$$reportNull$$$0(1);
        }
        Int2ObjectMap<WhitespacesAndCommentsBinder> binderMap = getBinderMap(z);
        if (whitespacesAndCommentsBinder != getDefaultBinder(z)) {
            markAsHavingOptionalData(i);
            binderMap.put(i, whitespacesAndCommentsBinder);
        } else {
            binderMap.remove(i);
        }
    }

    private static WhitespacesAndCommentsBinder getDefaultBinder(boolean z) {
        return z ? WhitespacesBinders.DEFAULT_RIGHT_BINDER : WhitespacesBinders.DEFAULT_LEFT_BINDER;
    }

    private Int2ObjectMap<WhitespacesAndCommentsBinder> getBinderMap(boolean z) {
        return z ? this.myRightBinders : this.myLeftBinders;
    }
}
