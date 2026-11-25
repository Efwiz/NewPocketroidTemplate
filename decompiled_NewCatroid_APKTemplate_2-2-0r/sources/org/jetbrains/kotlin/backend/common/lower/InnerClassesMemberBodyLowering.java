package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.LowerKt;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: InnerClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001c\u0010\u0016\u001a\u00020\u000f*\u00020\u00132\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesMemberBodyLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "classForImplicitThis", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "getClassForImplicitThis", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "fixThisReference", "irClass", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InnerClassesMemberBodyLowering implements BodyLoweringPass {
    private final BackendContext context;
    private final InnerClassesSupport innerClassesSupport;

    public InnerClassesMemberBodyLowering(BackendContext context, InnerClassesSupport innerClassesSupport) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(innerClassesSupport, "innerClassesSupport");
        this.context = context;
        this.innerClassesSupport = innerClassesSupport;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClass getClassForImplicitThis(IrValueSymbol irValueSymbol) {
        if (!(irValueSymbol instanceof IrValueParameterSymbol)) {
            return null;
        }
        IrValueParameterSymbol irValueParameterSymbol = (IrValueParameterSymbol) irValueSymbol;
        if (irValueParameterSymbol.getOwner().getIndex() != -1) {
            return null;
        }
        IrValueParameter owner = irValueParameterSymbol.getOwner();
        IrDeclarationParent parent = irValueParameterSymbol.getOwner().getParent();
        IrFunction irFunction = parent instanceof IrFunction ? (IrFunction) parent : null;
        if (!Intrinsics.areEqual(owner, irFunction != null ? irFunction.getDispatchReceiverParameter() : null)) {
            IrValueParameter owner2 = irValueParameterSymbol.getOwner();
            IrDeclarationParent parent2 = irValueParameterSymbol.getOwner().getParent();
            IrClass irClass = parent2 instanceof IrClass ? (IrClass) parent2 : null;
            if (!Intrinsics.areEqual(owner2, irClass != null ? irClass.getThisReceiver() : null)) {
                return null;
            }
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irValueParameterSymbol.getOwner().getType());
        if (classOrNull != null) {
            return classOrNull.getOwner();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        IrConstructor innerClassOriginalPrimaryConstructorOrNull;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrDeclarationParent parent = container.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null && irClass.getIsInner()) {
            if (((container instanceof IrField) || (container instanceof IrAnonymousInitializer) || (container instanceof IrValueParameter)) && (innerClassOriginalPrimaryConstructorOrNull = this.innerClassesSupport.getInnerClassOriginalPrimaryConstructorOrNull(irClass)) != null) {
                IrElementTransformerVoidKt.transformChildrenVoid(irBody, new VariableRemapper(InnerClassesLoweringKt.primaryConstructorParameterMap(this.innerClassesSupport, innerClassOriginalPrimaryConstructorOrNull)));
            }
            fixThisReference(irBody, irClass, container);
        }
    }

    private final void fixThisReference(IrBody irBody, final IrClass irClass, final IrDeclaration irDeclaration) {
        while (irDeclaration != null && !(irDeclaration instanceof IrFunction) && !(irDeclaration instanceof IrClass)) {
            IrDeclarationParent parent = irDeclaration.getParent();
            irDeclaration = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.common.lower.InnerClassesMemberBodyLowering.fixThisReference.1
            @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
            public IrStatement visitClassNew(IrClass declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return declaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                IrDeclaration irElement;
                IrValueParameter thisReceiver;
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                IrClass classForImplicitThis = InnerClassesMemberBodyLowering.this.getClassForImplicitThis(expression.getSymbol());
                if (classForImplicitThis == null || Intrinsics.areEqual(classForImplicitThis, irClass)) {
                    return expression;
                }
                int startOffset = expression.getStartOffset();
                int endOffset = expression.getEndOffset();
                IrStatementOrigin origin = expression.getOrigin();
                ScopeWithIr currentFunction = getCurrentFunction();
                if (currentFunction == null || (irElement = currentFunction.getIrElement()) == null) {
                    irElement = irDeclaration;
                }
                IrFunction irFunction = irElement instanceof IrFunction ? (IrFunction) irElement : null;
                if (irFunction == null || (thisReceiver = irFunction.getDispatchReceiverParameter()) == null) {
                    thisReceiver = irClass.getThisReceiver();
                    Intrinsics.checkNotNull(thisReceiver);
                }
                IrGetValueImpl irGetValueImpl = new IrGetValueImpl(startOffset, endOffset, thisReceiver.getSymbol(), origin);
                IrClass parentAsClass = irClass;
                IrExpression irGetFieldImpl = irGetValueImpl;
                while (!Intrinsics.areEqual(parentAsClass, classForImplicitThis)) {
                    if (!parentAsClass.getIsInner()) {
                        return expression;
                    }
                    if (!(irFunction instanceof IrConstructor) || !Intrinsics.areEqual(irClass, parentAsClass)) {
                        IrField outerThisField = InnerClassesMemberBodyLowering.this.innerClassesSupport.getOuterThisField(parentAsClass);
                        irGetFieldImpl = new IrGetFieldImpl(startOffset, endOffset, outerThisField.getSymbol(), outerThisField.getType(), irGetFieldImpl, origin, null, 64, null);
                    } else {
                        irGetFieldImpl = new IrGetValueImpl(startOffset, endOffset, ((IrConstructor) irFunction).getValueParameters().get(0).getSymbol(), origin);
                    }
                    parentAsClass = IrUtilsKt.getParentAsClass(parentAsClass);
                    irFunction = irFunction;
                }
                return irGetFieldImpl;
            }
        });
    }
}
