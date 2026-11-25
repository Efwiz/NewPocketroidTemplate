package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrPropertyReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: ScriptRemoveReceiverLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\fJ\u0014\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000f*\u00020\u0011H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ScriptRemoveReceiverLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "script", "nullConst", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptRemoveReceiverLowering implements FileLoweringPass {
    private final CommonBackendContext context;

    public ScriptRemoveReceiverLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        if (this.context.getScriptMode()) {
            List<IrDeclaration> declarations = irFile.getDeclarations();
            int iReplaceInPlace = 0;
            while (iReplaceInPlace < declarations.size()) {
                IrDeclaration irDeclaration = declarations.get(iReplaceInPlace);
                iReplaceInPlace = TransformKt.replaceInPlace(declarations, irDeclaration instanceof IrScript ? lower((IrScript) irDeclaration) : null, iReplaceInPlace);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl nullConst(IrExpression irExpression) {
        return IrConstImpl.INSTANCE.constNull(irExpression.getStartOffset(), irExpression.getEndOffset(), IrTypesKt.makeNullable(irExpression.getType()));
    }

    public final List<IrScript> lower(final IrScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        IrElementTransformerVoidKt.transformChildrenVoid(script, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ScriptRemoveReceiverLowering$lower$transformer$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (expression.getSymbol().getOwner().getParent() instanceof IrScript) {
                    expression.setDispatchReceiver(null);
                }
                return super.visitCall(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrValueSymbol symbol = expression.getSymbol();
                IrValueParameter thisReceiver = script.getThisReceiver();
                if (symbol == (thisReceiver != null ? thisReceiver.getSymbol() : null)) {
                    return this.nullConst(expression);
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitFieldAccess(IrFieldAccessExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (expression.getSymbol().getOwner().getParent() instanceof IrScript) {
                    expression.setReceiver(null);
                }
                return super.visitFieldAccess(expression);
            }

            private final boolean isScript(IrTypeArgument it) {
                IrType typeOrNull = IrTypesKt.getTypeOrNull(it);
                return (typeOrNull != null ? IrTypesKt.getClassifierOrNull(typeOrNull) : null) instanceof IrScriptSymbol;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitFunctionReference(IrFunctionReference expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                if (expression.getSymbol().getOwner().getParent() instanceof IrScript) {
                    expression.setDispatchReceiver(null);
                    IrExpression irExpressionVisitFunctionReference = super.visitFunctionReference(expression);
                    Intrinsics.checkNotNull(irExpressionVisitFunctionReference, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                    IrFunctionReference irFunctionReference = (IrFunctionReference) irExpressionVisitFunctionReference;
                    ScriptRemoveReceiverLowering scriptRemoveReceiverLowering = this;
                    IrType type = irFunctionReference.getType();
                    Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                    List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : arguments) {
                        if (!isScript((IrTypeArgument) obj)) {
                            arrayList.add(obj);
                        }
                    }
                    List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
                    int size = listCompactIfPossible.size() - 1;
                    int startOffset = irFunctionReference.getStartOffset();
                    int endOffset = irFunctionReference.getEndOffset();
                    IrClassSymbol irClassSymbolFunctionN = scriptRemoveReceiverLowering.getContext().getIr().getSymbols().functionN(size);
                    IrType type2 = irFunctionReference.getType();
                    Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                    IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(startOffset, endOffset, new IrSimpleTypeImpl(irClassSymbolFunctionN, ((IrSimpleType) type2).getNullability(), listCompactIfPossible, irFunctionReference.getType().getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), irFunctionReference.getSymbol(), irFunctionReference.getTypeArgumentsCount(), irFunctionReference.getValueArgumentsCount(), irFunctionReference.getReflectionTarget(), irFunctionReference.getOrigin());
                    irFunctionReferenceImpl.setDispatchReceiver(irFunctionReference.getDispatchReceiver());
                    irFunctionReferenceImpl.setExtensionReceiver(irFunctionReference.getExtensionReceiver());
                    return irFunctionReferenceImpl;
                }
                return expression;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitPropertyReference(IrPropertyReference expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                if (expression.getSymbol().getOwner().getParent() instanceof IrScript) {
                    expression.setDispatchReceiver(null);
                    IrExpression irExpressionVisitPropertyReference = super.visitPropertyReference(expression);
                    Intrinsics.checkNotNull(irExpressionVisitPropertyReference, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrPropertyReference");
                    IrPropertyReference irPropertyReference = (IrPropertyReference) irExpressionVisitPropertyReference;
                    IrType type = irPropertyReference.getType();
                    Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                    List<IrTypeArgument> arguments = ((IrSimpleType) type).getArguments();
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : arguments) {
                        if (!isScript((IrTypeArgument) obj)) {
                            arrayList.add(obj);
                        }
                    }
                    List listCompactIfPossible = CollectionsKt.compactIfPossible(arrayList);
                    int size = listCompactIfPossible.size() - 1;
                    int startOffset = irPropertyReference.getStartOffset();
                    int endOffset = irPropertyReference.getEndOffset();
                    IrClassSymbol propertyN = irPropertyReference.getSetter() == null ? getPropertyN(size) : getMutablePropertyN(size);
                    IrType type2 = irPropertyReference.getType();
                    Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                    IrPropertyReferenceImpl irPropertyReferenceImpl = new IrPropertyReferenceImpl(startOffset, endOffset, new IrSimpleTypeImpl(propertyN, ((IrSimpleType) type2).getNullability(), listCompactIfPossible, irPropertyReference.getType().getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null), irPropertyReference.getSymbol(), irPropertyReference.getTypeArgumentsCount(), irPropertyReference.getField(), irPropertyReference.getGetter(), irPropertyReference.getSetter(), irPropertyReference.getOrigin());
                    irPropertyReferenceImpl.setDispatchReceiver(irPropertyReference.getDispatchReceiver());
                    irPropertyReferenceImpl.setExtensionReceiver(irPropertyReference.getExtensionReceiver());
                    return irPropertyReferenceImpl;
                }
                return expression;
            }

            private final IrClassSymbol getPropertyN(int n) {
                if (n == 0) {
                    return this.getContext().getIr().getSymbols().kproperty0();
                }
                if (n == 1) {
                    return this.getContext().getIr().getSymbols().kproperty1();
                }
                if (n == 2) {
                    return this.getContext().getIr().getSymbols().kproperty2();
                }
                throw new IllegalArgumentException();
            }

            private final IrClassSymbol getMutablePropertyN(int n) {
                if (n == 0) {
                    return this.getContext().getIr().getSymbols().kmutableproperty0();
                }
                if (n == 1) {
                    return this.getContext().getIr().getSymbols().kmutableproperty1();
                }
                if (n == 2) {
                    return this.getContext().getIr().getSymbols().kmutableproperty2();
                }
                throw new IllegalArgumentException();
            }
        });
        for (IrStatement irStatement : script.getStatements()) {
            if (irStatement instanceof IrSimpleFunction) {
                ((IrSimpleFunction) irStatement).setDispatchReceiverParameter(null);
            } else if (irStatement instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irStatement;
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null) {
                    getter.setDispatchReceiverParameter(null);
                }
                IrSimpleFunction setter = irProperty.getSetter();
                if (setter != null) {
                    setter.setDispatchReceiverParameter(null);
                }
            }
        }
        return kotlin.collections.CollectionsKt.listOf(script);
    }
}
