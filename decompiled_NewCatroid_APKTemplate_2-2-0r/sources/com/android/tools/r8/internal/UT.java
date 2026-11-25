package com.android.tools.r8.internal;

import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.utils.AbstractC3897g0;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class UT implements RetracedMethodReference {
    private static final Comparator b = Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.UT$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((RetracedMethodReference) obj).getMethodName();
        }
    }).thenComparing(new Function() { // from class: com.android.tools.r8.internal.UT$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Boolean.valueOf(((RetracedMethodReference) obj).isKnown());
        }
    }).thenComparing(new Function() { // from class: com.android.tools.r8.internal.UT$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((RetracedMethodReference) obj).asKnown();
        }
    }, Comparator.nullsFirst(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.UT$$ExternalSyntheticLambda3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return UT.a((RetracedMethodReference.KnownRetracedMethodReference) obj);
        }
    })).thenComparing(new Function() { // from class: com.android.tools.r8.internal.UT$$ExternalSyntheticLambda4
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((RetracedMethodReference.KnownRetracedMethodReference) obj).getFormalTypes();
        }
    }, AbstractC3897g0.b(Comparator.comparing(new UT$$ExternalSyntheticLambda5()))));
    protected final OptionalInt a;

    private UT(OptionalInt optionalInt) {
        this.a = optionalInt;
    }

    public ST a() {
        return null;
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final int getOriginalPositionOrDefault(int i) {
        return this.a.orElse(i);
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final boolean hasPosition() {
        return this.a.isPresent();
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final boolean isKnown() {
        return !isUnknown();
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public boolean isUnknown() {
        return !(this instanceof ST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetracedMethodReference.KnownRetracedMethodReference knownRetracedMethodReference) {
        if (knownRetracedMethodReference == null) {
            return null;
        }
        return knownRetracedMethodReference.isVoid() ? PsiKeyword.VOID : knownRetracedMethodReference.getReturnType().getTypeName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static UT a(GE ge) {
        ge.getClass();
        STIA stia = null;
        Object[] objArr = 0;
        if (ge instanceof FE) {
            return new ST(ge.a().b(), OptionalInt.empty());
        }
        return new TT(ge, OptionalInt.empty());
    }

    static ST a(MethodReference methodReference) {
        return new ST(methodReference, OptionalInt.empty());
    }

    static ST a(MethodReference methodReference, OptionalInt optionalInt) {
        return new ST(methodReference, optionalInt);
    }
}
