package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.CachedFieldsForObjectInstances;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: JvmStaticAnnotationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/SingletonObjectJvmStaticTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "cachedFields", "Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;)V", "visitClass", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitMemberAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SingletonObjectJvmStaticTransformer extends IrElementTransformerVoid {
    private final CachedFieldsForObjectInstances cachedFields;
    private final IrBuiltIns irBuiltIns;

    public SingletonObjectJvmStaticTransformer(IrBuiltIns irBuiltIns, CachedFieldsForObjectInstances cachedFields) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(cachedFields, "cachedFields");
        this.irBuiltIns = irBuiltIns;
        this.cachedFields = cachedFields;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitClass(IrClass declaration) {
        IrValueParameter dispatchReceiverParameter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (IrUtilsKt.isNonCompanionObject(declaration)) {
            for (IrSimpleFunction irSimpleFunction : IrUtilsKt.simpleFunctions(declaration)) {
                if (JvmStaticAnnotationLoweringKt.isJvmStaticDeclaration(irSimpleFunction) && (dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter()) != null) {
                    irSimpleFunction.setDispatchReceiverParameter(null);
                    JvmIrUtilsKt.replaceThisByStaticReference(irSimpleFunction, this.cachedFields, declaration, dispatchReceiverParameter);
                }
            }
        }
        return super.visitClass(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitMemberAccess(IrMemberAccessExpression<?> expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        IrSymbolOwner owner = expression.getSymbol().getOwner();
        if ((owner instanceof IrDeclaration) && JvmStaticAnnotationLoweringKt.isJvmStaticInObject((IrDeclaration) owner)) {
            return JvmStaticAnnotationLoweringKt.makeStatic(expression, this.irBuiltIns, null);
        }
        return expression;
    }
}
