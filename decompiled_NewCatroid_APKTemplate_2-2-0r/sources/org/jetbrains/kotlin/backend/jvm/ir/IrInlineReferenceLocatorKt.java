package org.jetbrains.kotlin.backend.jvm.ir;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: IrInlineReferenceLocator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a@\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042&\b\u0004\u0010\u0007\u001a \u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00060\bH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, d2 = {"findInlineCallSites", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrInlineScopeResolver;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "findInlineLambdas", "", "onLambda", "Lkotlin/Function4;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrInlineReferenceLocatorKt {
    public static final void findInlineLambdas(IrFile irFile, JvmBackendContext context, final Function4<? super IrFunctionReference, ? super IrFunction, ? super IrValueParameter, ? super IrDeclaration, Unit> onLambda) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onLambda, "onLambda");
        irFile.accept(new IrInlineReferenceLocator(context) { // from class: org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocatorKt.findInlineLambdas.1
            @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
            public void visitInlineLambda(IrFunctionReference argument, IrFunction callee, IrValueParameter parameter, IrDeclaration scope) {
                Intrinsics.checkNotNullParameter(argument, "argument");
                Intrinsics.checkNotNullParameter(callee, "callee");
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                Intrinsics.checkNotNullParameter(scope, "scope");
                onLambda.invoke(argument, callee, parameter, scope);
            }
        }, null);
    }

    public static final IrInlineScopeResolver findInlineCallSites(IrFile irFile, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrInlineScopeResolver irInlineScopeResolver = new IrInlineScopeResolver(context);
        irFile.accept(irInlineScopeResolver, null);
        return irInlineScopeResolver;
    }
}
