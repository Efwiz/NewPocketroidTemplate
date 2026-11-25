package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLowerKt;
import org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;

/* JADX INFO: compiled from: JvmLocalClassPopupLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmLocalClassPopupLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/LocalClassPopupLowering;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "inlineLambdaToScope", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "shouldPopUp", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "currentScope", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmLocalClassPopupLowering extends LocalClassPopupLowering {
    private final Map<IrFunction, IrDeclaration> inlineLambdaToScope;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmLocalClassPopupLowering(JvmBackendContext context) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
        this.inlineLambdaToScope = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering, org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        BackendContext context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.JvmBackendContext");
        final JvmBackendContext jvmBackendContext = (JvmBackendContext) context;
        irFile.accept(new IrInlineReferenceLocator(jvmBackendContext) { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmLocalClassPopupLowering$lower$$inlined$findInlineLambdas$1
            @Override // org.jetbrains.kotlin.backend.jvm.ir.IrInlineReferenceLocator
            public void visitInlineLambda(IrFunctionReference argument, IrFunction callee, IrValueParameter parameter, IrDeclaration scope) {
                Intrinsics.checkNotNullParameter(argument, "argument");
                Intrinsics.checkNotNullParameter(callee, "callee");
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                Intrinsics.checkNotNullParameter(scope, "scope");
                this.inlineLambdaToScope.put(argument.getSymbol().getOwner(), scope);
            }
        }, null);
        super.lower(irFile);
        this.inlineLambdaToScope.clear();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.LocalClassPopupLowering
    protected boolean shouldPopUp(IrClass klass, ScopeWithIr currentScope) {
        IrDeclaration irDeclaration;
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (!super.shouldPopUp(klass, currentScope) && !JvmLowerKt.isGeneratedLambdaClass(klass)) {
            return false;
        }
        IrDeclaration irElement = currentScope != null ? currentScope.getIrElement() : null;
        while ((irElement instanceof IrFunction) && (irDeclaration = this.inlineLambdaToScope.get(irElement)) != null) {
            irElement = irDeclaration;
        }
        if ((!(irElement instanceof IrAnonymousInitializer) || ((IrAnonymousInitializer) irElement).getIsStatic()) && (!(irElement instanceof IrField) || ((IrField) irElement).getIsStatic())) {
            return false;
        }
        BackendContext context = getContext();
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.JvmBackendContext");
        ((JvmBackendContext) context).isEnclosedInConstructor().add(klass.getAttributeOwnerId());
        return true;
    }
}
