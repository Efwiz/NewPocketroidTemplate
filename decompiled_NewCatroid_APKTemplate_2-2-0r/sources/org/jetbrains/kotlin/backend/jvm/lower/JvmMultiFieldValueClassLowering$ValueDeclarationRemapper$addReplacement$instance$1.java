package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrVariable;

/* JADX INFO: compiled from: JvmMultiFieldValueClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$instance$1 extends FunctionReference implements Function1<IrVariable, Unit> {
    JvmMultiFieldValueClassLowering$ValueDeclarationRemapper$addReplacement$instance$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "variablesSaver(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "variablesSaver";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JvmMultiFieldValueClassLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(IrVariable irVariable) {
        invoke2(irVariable);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(IrVariable p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        ((JvmMultiFieldValueClassLowering) this.receiver).variablesSaver(p0);
    }
}
