package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;

/* JADX INFO: compiled from: ES6ConstructorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0003\"\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b\"\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0003\"\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0003\"\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\b¨\u0006\f"}, d2 = {"isEs6ConstructorReplacement", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isEs6DelegatingConstructorCallReplacement", "isEs6PrimaryConstructorReplacement", "isInitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)Z", "isInitFunction", "isSyntheticConstructorForExport", "isSyntheticDelegatingReplacement", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ES6ConstructorLoweringKt {
    public static final boolean isEs6ConstructorReplacement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), ES6_CONSTRUCTOR_REPLACEMENT.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), ES6_PRIMARY_CONSTRUCTOR_REPLACEMENT.INSTANCE);
    }

    public static final boolean isEs6PrimaryConstructorReplacement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), ES6_PRIMARY_CONSTRUCTOR_REPLACEMENT.INSTANCE);
    }

    public static final boolean isSyntheticDelegatingReplacement(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        return Intrinsics.areEqual(irFunctionAccessExpression.getOrigin(), ES6_DELEGATING_CONSTRUCTOR_REPLACEMENT.INSTANCE);
    }

    public static final boolean isInitFunction(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), ES6_INIT_FUNCTION.INSTANCE);
    }

    public static final boolean isInitCall(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        return Intrinsics.areEqual(irFunctionAccessExpression.getOrigin(), ES6_INIT_CALL.INSTANCE);
    }

    public static final boolean isSyntheticConstructorForExport(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), ES6_SYNTHETIC_EXPORT_CONSTRUCTOR.INSTANCE);
    }

    public static final boolean isEs6DelegatingConstructorCallReplacement(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), ES6_DELEGATING_CONSTRUCTOR_CALL_REPLACEMENT.INSTANCE);
    }
}
