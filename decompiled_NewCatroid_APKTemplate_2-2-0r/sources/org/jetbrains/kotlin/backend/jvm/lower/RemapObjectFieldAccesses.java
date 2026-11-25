package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: MoveCompanionObjectFieldsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0016J\u000e\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u0013H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/RemapObjectFieldAccesses;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitGetField", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "remap", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class RemapObjectFieldAccesses extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext context;

    public RemapObjectFieldAccesses(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        transformChildrenVoid(irFile);
    }

    private final IrField remap(IrField irField) {
        IrProperty owner;
        IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return null;
        }
        return this.context.getCachedDeclarations().getStaticBackingField(owner);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetField(IrGetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrField irFieldRemap = remap(expression.getSymbol().getOwner());
        if (irFieldRemap != null) {
            return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), irFieldRemap.getSymbol(), expression.getType(), null, expression.getOrigin(), expression.getSuperQualifierSymbol());
        }
        return super.visitGetField(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrField irFieldRemap = remap(expression.getSymbol().getOwner());
        if (irFieldRemap != null) {
            return new IrSetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), irFieldRemap.getSymbol(), (IrExpression) null, expression.getValue().transform((IrElementTransformer<? super Object>) this, (Object) null), expression.getType(), expression.getOrigin(), expression.getSuperQualifierSymbol());
        }
        return super.visitSetField(expression);
    }
}
