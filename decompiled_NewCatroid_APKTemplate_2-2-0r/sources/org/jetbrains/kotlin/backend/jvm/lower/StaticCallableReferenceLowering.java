package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: StaticCallableReferenceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/StaticCallableReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "backendContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StaticCallableReferenceLowering extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext backendContext;

    public StaticCallableReferenceLowering(JvmBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    public final JvmBackendContext getBackendContext() {
        return this.backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        transformChildrenVoid(declaration);
        if (JvmIrUtilsKt.isSyntheticSingleton(declaration)) {
            List<IrDeclaration> declarations = declaration.getDeclarations();
            IrField fieldForObjectInstance = this.backendContext.getCachedDeclarations().getFieldForObjectInstance(declaration);
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.backendContext, fieldForObjectInstance.getSymbol(), 0, 0, 6, (Object) null);
            IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(declaration);
            Intrinsics.checkNotNull(primaryConstructor);
            fieldForObjectInstance.setInitializer(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irCall(declarationIrBuilderCreateIrBuilder$default, primaryConstructor)));
            declarations.add(fieldForObjectInstance);
        }
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClass constructedClass = AdditionalIrUtilsKt.getConstructedClass(expression.getSymbol().getOwner());
        if (!JvmIrUtilsKt.isSyntheticSingleton(constructedClass)) {
            return super.visitConstructorCall(expression);
        }
        return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), this.backendContext.getCachedDeclarations().getFieldForObjectInstance(constructedClass).getSymbol(), expression.getType(), null, null, 48, null);
    }
}
