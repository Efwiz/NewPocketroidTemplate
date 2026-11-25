package org.jetbrains.kotlin.backend.jvm.lower.indy;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0000\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/LambdaMetafactoryArguments;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Success;", "samMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "fakeInstanceMethod", "implMethodReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "extraOverriddenMethods", "", "shouldBeSerializable", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Ljava/util/List;Z)V", "getExtraOverriddenMethods", "()Ljava/util/List;", "getFakeInstanceMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getImplMethodReference", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "getSamMethod", "getShouldBeSerializable", "()Z", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LambdaMetafactoryArguments extends MetafactoryArgumentsResult.Success {
    private final List<IrSimpleFunction> extraOverriddenMethods;
    private final IrSimpleFunction fakeInstanceMethod;
    private final IrFunctionReference implMethodReference;
    private final IrSimpleFunction samMethod;
    private final boolean shouldBeSerializable;

    public final IrSimpleFunction getSamMethod() {
        return this.samMethod;
    }

    public final IrSimpleFunction getFakeInstanceMethod() {
        return this.fakeInstanceMethod;
    }

    public final IrFunctionReference getImplMethodReference() {
        return this.implMethodReference;
    }

    public final List<IrSimpleFunction> getExtraOverriddenMethods() {
        return this.extraOverriddenMethods;
    }

    public final boolean getShouldBeSerializable() {
        return this.shouldBeSerializable;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LambdaMetafactoryArguments(IrSimpleFunction samMethod, IrSimpleFunction fakeInstanceMethod, IrFunctionReference implMethodReference, List<? extends IrSimpleFunction> extraOverriddenMethods, boolean z) {
        Intrinsics.checkNotNullParameter(samMethod, "samMethod");
        Intrinsics.checkNotNullParameter(fakeInstanceMethod, "fakeInstanceMethod");
        Intrinsics.checkNotNullParameter(implMethodReference, "implMethodReference");
        Intrinsics.checkNotNullParameter(extraOverriddenMethods, "extraOverriddenMethods");
        this.samMethod = samMethod;
        this.fakeInstanceMethod = fakeInstanceMethod;
        this.implMethodReference = implMethodReference;
        this.extraOverriddenMethods = extraOverriddenMethods;
        this.shouldBeSerializable = z;
    }
}
