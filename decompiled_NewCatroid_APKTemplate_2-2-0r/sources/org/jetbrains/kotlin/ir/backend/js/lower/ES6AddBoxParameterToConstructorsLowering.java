package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.ir.ValueRemapper;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: ES6AddBoxParameterLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\f\u0010\u0013\u001a\u00020\n*\u00020\fH\u0002J\u0014\u0010\u0014\u001a\u00020\u0015*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\f\u0010\u0018\u001a\u00020\b*\u00020\u0015H\u0002J\u001c\u0010\u0019\u001a\u00020\u001a*\u00020\u001b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ES6AddBoxParameterToConstructorsLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "createJsObjectLiteral", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "hackEnums", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "hackExceptions", "hackSimpleClassWithCapturing", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "addBoxParameter", "generateBoxParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateDefaultResolution", "replaceThisWithBoxBeforeSuperCall", "", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "boxParameterSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ES6AddBoxParameterToConstructorsLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public ES6AddBoxParameterToConstructorsLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.context.getEs6mode() && (declaration instanceof IrConstructor)) {
            IrConstructor irConstructor = (IrConstructor) declaration;
            if (!IrJsUtilsKt.hasStrictSignature(irConstructor, this.context)) {
                hackEnums(irConstructor);
                hackExceptions(irConstructor);
                hackSimpleClassWithCapturing(irConstructor);
                if (!PrimaryConstructorLoweringKt.isSyntheticPrimaryConstructor(declaration)) {
                    addBoxParameter(irConstructor);
                }
            }
        }
        return null;
    }

    private final void addBoxParameter(IrConstructor irConstructor) {
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
        IrValueParameter irValueParameterGenerateBoxParameter = generateBoxParameter(irConstructor, parentAsClass);
        irConstructor.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irConstructor.getValueParameters(), irValueParameterGenerateBoxParameter));
        IrBody body = irConstructor.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody != null && replaceThisWithBoxBeforeSuperCall(irBlockBody, parentAsClass, irValueParameterGenerateBoxParameter.getSymbol())) {
            irBlockBody.getStatements().add(0, generateDefaultResolution(irValueParameterGenerateBoxParameter));
        }
    }

    private final IrExpression createJsObjectLiteral() {
        return JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIntrinsics().getJsEmptyObject(), null, null, null, null, 30, null);
    }

    private final IrValueParameter generateBoxParameter(IrConstructor irConstructor, IrClass irClass) {
        return JsIrBuilder.INSTANCE.buildValueParameter(irConstructor, Namer.INSTANCE.getES6_BOX_PARAMETER_NAME(), irConstructor.getValueParameters().size(), IrTypesKt.makeNullable(IrUtilsKt.getDefaultType(irClass)), true, ES6_BOX_PARAMETER.INSTANCE);
    }

    private final IrExpression generateDefaultResolution(IrValueParameter irValueParameter) {
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irValueParameter.getSymbol(), -1, -1);
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        return ExpressionHelpersKt.irIfThen(declarationIrBuilder, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getUnitType(), ExpressionHelpersKt.irEqeqeqWithoutBox(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter.getType(), irValueParameter.getSymbol()), IrJsUtilsKt.getVoid(this.context)), ExpressionHelpersKt.irSet$default(declarationIrBuilder, irValueParameter.getSymbol(), createJsObjectLiteral(), (IrStatementOrigin) null, 4, (Object) null), ES6_BOX_PARAMETER_DEFAULT_RESOLUTION.INSTANCE);
    }

    private final boolean replaceThisWithBoxBeforeSuperCall(IrBody irBody, IrClass irClass, final IrValueSymbol irValueSymbol) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new ValueRemapper(MapsKt.mapOf(TuplesKt.to(thisReceiver.getSymbol(), irValueSymbol))) { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6AddBoxParameterToConstructorsLowering.replaceThisWithBoxBeforeSuperCall.1
            @Override // org.jetbrains.kotlin.backend.common.ir.AbstractValueRemapper, org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetValue(IrGetValue expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (booleanRef2.element) {
                    return expression;
                }
                return super.visitGetValue(expression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitSetField(IrSetField expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (booleanRef2.element) {
                    return expression;
                }
                IrExpression irExpressionVisitSetField = super.visitSetField(expression);
                IrExpression receiver = expression.getReceiver();
                IrGetValue irGetValue = receiver instanceof IrGetValue ? (IrGetValue) receiver : null;
                if (Intrinsics.areEqual(irGetValue != null ? irGetValue.getSymbol() : null, irValueSymbol)) {
                    booleanRef.element = true;
                }
                return irExpressionVisitSetField;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitDelegatingConstructorCall(IrDelegatingConstructorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                booleanRef2.element = true;
                return super.visitDelegatingConstructorCall(expression);
            }
        });
        return booleanRef.element;
    }

    private final void hackEnums(IrConstructor constructor) {
        constructor.transformChildren(new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ES6AddBoxParameterToConstructorsLowering.hackEnums.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrExpression argument = expression.getArgument();
                IrDelegatingConstructorCall irDelegatingConstructorCall = argument instanceof IrDelegatingConstructorCall ? (IrDelegatingConstructorCall) argument : null;
                return irDelegatingConstructorCall != null ? irDelegatingConstructorCall : expression;
            }
        }, null);
    }

    private final void hackSimpleClassWithCapturing(IrConstructor constructor) {
        List<IrStatement> statements;
        IrExpression receiver;
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(constructor);
        if (IrUtilsKt.getSuperClass(parentAsClass) == null) {
            if (parentAsClass.getIsInner() || AdditionalIrUtilsKt.isLocal(parentAsClass)) {
                IrBody body = constructor.getBody();
                IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
                if (irBlockBody == null || (statements = irBlockBody.getStatements()) == null) {
                    return;
                }
                Iterator<IrStatement> it = statements.iterator();
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i2 = -1;
                        break;
                    } else if (it.next() instanceof IrDelegatingConstructorCall) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 == -1) {
                    return;
                }
                Iterator<IrStatement> it2 = statements.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        i = -1;
                        break;
                    }
                    IrStatement next = it2.next();
                    if ((next instanceof IrSetField) && (receiver = ((IrSetField) next).getReceiver()) != null && (receiver instanceof IrGetValue)) {
                        IrValueSymbol symbol = ((IrGetValue) receiver).getSymbol();
                        IrValueParameter thisReceiver = parentAsClass.getThisReceiver();
                        if (Intrinsics.areEqual(symbol, thisReceiver != null ? thisReceiver.getSymbol() : null)) {
                            break;
                        }
                    }
                    i++;
                }
                if (i == -1 || i > i2) {
                    return;
                }
                statements.add(i, statements.get(i2));
                statements.remove(i2 + 1);
            }
        }
    }

    private final void hackExceptions(IrConstructor constructor) {
        List<IrStatement> statements;
        IrSimpleFunctionSymbol setPropertiesToThrowableInstanceSymbol = this.context.getSetPropertiesToThrowableInstanceSymbol();
        IrBody body = constructor.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody == null || (statements = irBlockBody.getStatements()) == null) {
            return;
        }
        int size = statements.size();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < size; i3++) {
            IrStatement irStatement = statements.get(i3);
            if ((irStatement instanceof IrCall) && ((IrCall) irStatement).getSymbol() == setPropertiesToThrowableInstanceSymbol) {
                i = i3;
            }
            if ((irStatement instanceof IrDelegatingConstructorCall) && ((IrDelegatingConstructorCall) irStatement).getSymbol().getOwner().getOrigin() == PrimaryConstructorLowering.SYNTHETIC_PRIMARY_CONSTRUCTOR.INSTANCE) {
                i2 = i3;
            }
        }
        if (i == -1 || i2 == -1) {
            return;
        }
        IrStatement irStatement2 = statements.get(i);
        statements.set(i, statements.get(i2));
        statements.set(i2, irStatement2);
    }
}
