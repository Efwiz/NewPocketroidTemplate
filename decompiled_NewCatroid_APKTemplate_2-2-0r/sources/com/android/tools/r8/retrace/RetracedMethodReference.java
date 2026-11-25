package com.android.tools.r8.retrace;

import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.TypeReference;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface RetracedMethodReference extends RetracedClassMemberReference, Comparable<RetracedMethodReference> {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface KnownRetracedMethodReference extends RetracedMethodReference {
        List<TypeReference> getFormalTypes();

        MethodReference getMethodReference();

        TypeReference getReturnType();

        boolean isVoid();
    }

    KnownRetracedMethodReference asKnown();

    String getMethodName();

    int getOriginalPositionOrDefault(int i);

    boolean hasPosition();

    boolean isKnown();

    boolean isUnknown();
}
