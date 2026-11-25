package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: InitializersLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0004J\u0010\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001a\u0010\u0013\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0014H\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/InitializersLoweringBase;", "", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "extractInitializers", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "filter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "", "handleAnonymousInitializer", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "handleField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InitializersLoweringBase {
    private final CommonBackendContext context;

    public InitializersLoweringBase(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public CommonBackendContext getContext() {
        return this.context;
    }

    protected final List<IrStatement> extractInitializers(IrClass irClass, Function1<? super IrDeclaration, Boolean> filter) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(filter, "filter");
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (IrField backingField : declarations) {
            if (backingField instanceof IrProperty) {
                backingField = ((IrProperty) backingField).getBackingField();
            }
            if (backingField != null) {
                arrayList.add(backingField);
            }
        }
        ArrayList<IrDeclaration> arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (filter.invoke(obj).booleanValue()) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (IrDeclaration irDeclaration : arrayList2) {
            IrStatement irStatementHandleField = irDeclaration instanceof IrField ? handleField(irClass, (IrField) irDeclaration) : irDeclaration instanceof IrAnonymousInitializer ? handleAnonymousInitializer((IrAnonymousInitializer) irDeclaration) : null;
            if (irStatementHandleField != null) {
                arrayList3.add(irStatementHandleField);
            }
        }
        return arrayList3;
    }

    private final IrStatement handleField(IrClass irClass, IrField declaration) {
        IrExpressionBody initializer = declaration.getInitializer();
        IrExpression irSetFieldImpl = null;
        if (initializer != null) {
            if (!declaration.getIsStatic()) {
                int startOffset = initializer.getStartOffset();
                int endOffset = initializer.getEndOffset();
                IrValueParameter thisReceiver = irClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                IrType type = thisReceiver.getType();
                IrValueParameter thisReceiver2 = irClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver2);
                irSetFieldImpl = new IrGetValueImpl(startOffset, endOffset, type, thisReceiver2.getSymbol(), null, 16, null);
            }
            irSetFieldImpl = new IrSetFieldImpl(initializer.getStartOffset(), initializer.getEndOffset(), declaration.getSymbol(), irSetFieldImpl, initializer.getExpression(), getContext().getIrBuiltIns().getUnitType(), IrStatementOrigin.INITIALIZE_FIELD.INSTANCE, null, 128, null);
        }
        return irSetFieldImpl;
    }

    private final IrStatement handleAnonymousInitializer(IrAnonymousInitializer declaration) {
        return new IrBlockImpl(declaration.getStartOffset(), declaration.getEndOffset(), getContext().getIrBuiltIns().getUnitType(), LoweredStatementOrigins.SYNTHESIZED_INIT_BLOCK.INSTANCE, declaration.getBody().getStatements());
    }
}
