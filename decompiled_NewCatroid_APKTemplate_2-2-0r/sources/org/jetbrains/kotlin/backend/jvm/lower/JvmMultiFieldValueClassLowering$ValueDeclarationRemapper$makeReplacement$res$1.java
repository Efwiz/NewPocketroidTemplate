package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$makeReplacement$res$1 extends FunctionReference implements Function0<Unit> {
    JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$makeReplacement$res$1(Object obj) {
        super(0, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "registerPossibleExtraBoxUsage()V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "registerPossibleExtraBoxUsage";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JvmMultiFieldValueClassLowering.class);
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        ((JvmMultiFieldValueClassLowering) this.receiver).registerPossibleExtraBoxUsage();
    }
}
