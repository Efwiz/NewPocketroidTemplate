package org.jetbrains.kotlin.backend.common.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: AbstractValueUsageTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\n2\u0006\u0010\f\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\n2\u0006\u0010\f\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\n2\u0006\u0010\f\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\n2\u0006\u0010\f\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020\n2\u0006\u0010\f\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020/H\u0016J\u0010\u00100\u001a\u00020\n2\u0006\u0010\f\u001a\u000201H\u0016J\u0010\u00102\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u00103\u001a\u0002042\u0006\u0010\u0016\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020\n2\u0006\u0010\f\u001a\u000206H\u0016J\u0014\u00107\u001a\u00020\n*\u00020\n2\u0006\u00108\u001a\u000209H\u0014J\u0014\u0010:\u001a\u00020\n*\u00020\n2\u0006\u0010;\u001a\u00020<H\u0014J\u0014\u0010=\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\u001bH\u0014J\u0014\u0010>\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\u001bH\u0014J\u0014\u0010?\u001a\u00020\n*\u00020\n2\u0006\u0010@\u001a\u00020\nH\u0014J\u0014\u0010A\u001a\u00020\n*\u00020\n2\u0006\u0010B\u001a\u00020CH\u0014J\f\u0010D\u001a\u00020\n*\u00020\nH\u0014J\u0014\u0010E\u001a\u00020\n*\u00020\n2\u0006\u0010F\u001a\u00020GH\u0014J\u001c\u0010H\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\u001b2\u0006\u0010;\u001a\u00020<H\u0014J\u0014\u0010I\u001a\u00020\n*\u00020\n2\u0006\u0010J\u001a\u00020\u0017H\u0002J\u0014\u0010K\u001a\u00020\n*\u00020\n2\u0006\u0010L\u001a\u000204H\u0002J\u001c\u0010M\u001a\u00020\n*\u00020\n2\u0006\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u000209H\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Q"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/AbstractValueUsageTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "replaceTypesInsideInlinedFunctionBlock", "", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Z)V", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "useAsVarargElement", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "visitBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitField", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "visitLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "visitLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "visitPropertyReference", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitStringConcatenation", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitTypeOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "visitVararg", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "useAs", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "useAsArgument", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "useAsDispatchReceiver", "useAsExtensionReceiver", "useAsResult", "enclosing", "useAsReturnValue", "returnTarget", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "useAsStatement", "useAsValue", "value", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "useAsValueArgument", "useForField", "field", "useForVariable", "variable", "useInTypeOperator", "operator", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "typeOperand", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractValueUsageTransformer extends IrElementTransformerVoid {
    private final IrBuiltIns irBuiltIns;
    private final boolean replaceTypesInsideInlinedFunctionBlock;

    protected IrExpression useAs(IrExpression irExpression, IrType type) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return irExpression;
    }

    protected IrExpression useAsStatement(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return irExpression;
    }

    protected IrExpression useAsVarargElement(IrExpression element, IrVararg expression) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(expression, "expression");
        return element;
    }

    protected IrExpression useInTypeOperator(IrExpression irExpression, IrTypeOperator operator, IrType typeOperand) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(typeOperand, "typeOperand");
        return irExpression;
    }

    public /* synthetic */ AbstractValueUsageTransformer(IrBuiltIns irBuiltIns, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irBuiltIns, (i & 2) != 0 ? false : z);
    }

    protected final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public AbstractValueUsageTransformer(IrBuiltIns irBuiltIns, boolean z) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        this.irBuiltIns = irBuiltIns;
        this.replaceTypesInsideInlinedFunctionBlock = z;
    }

    protected IrExpression useAsValue(IrExpression irExpression, IrValueDeclaration value) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        return useAs(irExpression, value.getType());
    }

    protected IrExpression useAsArgument(IrExpression irExpression, IrValueParameter parameter) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return useAsValue(irExpression, parameter);
    }

    protected IrExpression useAsDispatchReceiver(IrExpression irExpression, IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueParameter dispatchReceiverParameter = expression.getSymbol().getOwner().getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        return useAsArgument(irExpression, dispatchReceiverParameter);
    }

    protected IrExpression useAsExtensionReceiver(IrExpression irExpression, IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueParameter extensionReceiverParameter = expression.getSymbol().getOwner().getExtensionReceiverParameter();
        Intrinsics.checkNotNull(extensionReceiverParameter);
        return useAsArgument(irExpression, extensionReceiverParameter);
    }

    protected IrExpression useAsValueArgument(IrExpression irExpression, IrFunctionAccessExpression expression, IrValueParameter parameter) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return useAsArgument(irExpression, parameter);
    }

    private final IrExpression useForVariable(IrExpression irExpression, IrVariable irVariable) {
        return useAsValue(irExpression, irVariable);
    }

    private final IrExpression useForField(IrExpression irExpression, IrField irField) {
        return useAs(irExpression, irField.getType());
    }

    protected IrExpression useAsReturnValue(IrExpression irExpression, IrReturnTargetSymbol returnTarget) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(returnTarget, "returnTarget");
        if (returnTarget instanceof IrSimpleFunctionSymbol) {
            return useAs(irExpression, ((IrSimpleFunctionSymbol) returnTarget).getOwner().getReturnType());
        }
        if (returnTarget instanceof IrConstructorSymbol) {
            return useAs(irExpression, this.irBuiltIns.getUnitType());
        }
        if (returnTarget instanceof IrReturnableBlockSymbol) {
            return useAs(irExpression, ((IrReturnableBlockSymbol) returnTarget).getOwner().getType());
        }
        throw new NoWhenBranchMatchedException();
    }

    protected IrExpression useAsResult(IrExpression irExpression, IrExpression enclosing) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(enclosing, "enclosing");
        return useAs(irExpression, enclosing.getType());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitPropertyReference(IrPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        throw new NotImplementedError(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        throw new NotImplementedError(null, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionAccess(IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        expression.setDispatchReceiver(dispatchReceiver != null ? useAsDispatchReceiver(dispatchReceiver, expression) : null);
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        expression.setExtensionReceiver(extensionReceiver != null ? useAsExtensionReceiver(extensionReceiver, expression) : null);
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i = 0; i < valueArgumentsCount; i++) {
            IrExpression valueArgument = expression.getValueArgument(i);
            if (valueArgument != null) {
                expression.putValueArgument(i, useAsValueArgument(valueArgument, expression, expression.getSymbol().getOwner().getValueParameters().get(i)));
            }
        }
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrBody visitBlockBody(IrBlockBody body) {
        Intrinsics.checkNotNullParameter(body, "body");
        IrElementTransformerVoidKt.transformChildrenVoid(body, this);
        int i = 0;
        for (Object obj : body.getStatements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (irStatement instanceof IrExpression) {
                body.getStatements().set(i, useAsStatement((IrExpression) irStatement));
            }
            i = i2;
        }
        return body;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitContainerExpression(IrContainerExpression expression) {
        IrExpression irExpressionUseAsStatement;
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!this.replaceTypesInsideInlinedFunctionBlock && (expression instanceof IrInlinedFunctionBlock)) {
            IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
            return expression;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        if (expression.getStatements().isEmpty()) {
            return expression;
        }
        IrContainerExpression innerInlinedBlockOrThis = IrInlineUtilsKt.getInnerInlinedBlockOrThis(expression);
        int lastIndex = CollectionsKt.getLastIndex(innerInlinedBlockOrThis.getStatements());
        int i = 0;
        for (Object obj : innerInlinedBlockOrThis.getStatements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrStatement irStatement = (IrStatement) obj;
            if (irStatement instanceof IrExpression) {
                List<IrStatement> statements = innerInlinedBlockOrThis.getStatements();
                if (i == lastIndex) {
                    irExpressionUseAsStatement = useAsResult((IrExpression) irStatement, expression);
                } else {
                    irExpressionUseAsStatement = useAsStatement((IrExpression) irStatement);
                }
                statements.set(i, irExpressionUseAsStatement);
            }
            i = i2;
        }
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        expression.setValue(useAsReturnValue(expression.getValue(), expression.getReturnTargetSymbol()));
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        expression.setValue(useAsValue(expression.getValue(), expression.getSymbol().getOwner()));
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetField(IrSetField expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        expression.setValue(useForField(expression.getValue(), expression.getSymbol().getOwner()));
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitField(IrField declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        IrExpressionBody initializer = declaration.getInitializer();
        if (initializer != null) {
            initializer.setExpression(useForField(initializer.getExpression(), declaration));
        }
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrVariable visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        IrExpression initializer = declaration.getInitializer();
        declaration.setInitializer(initializer != null ? useForVariable(initializer, declaration) : null);
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitWhen(IrWhen expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        for (IrBranch irBranch : expression.getBranches()) {
            irBranch.setCondition(useAs(irBranch.getCondition(), this.irBuiltIns.getBooleanType()));
            irBranch.setResult(useAsResult(irBranch.getResult(), expression));
        }
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        IrElementTransformerVoidKt.transformChildrenVoid(loop, this);
        loop.setCondition(useAs(loop.getCondition(), this.irBuiltIns.getBooleanType()));
        IrExpression body = loop.getBody();
        loop.setBody(body != null ? useAsStatement(body) : null);
        return loop;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitThrow(IrThrow expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        expression.setValue(useAs(expression.getValue(), this.irBuiltIns.getThrowableType()));
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTry(IrTry aTry) {
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        IrElementTransformerVoidKt.transformChildrenVoid(aTry, this);
        IrTry irTry = aTry;
        aTry.setTryResult(useAsResult(aTry.getTryResult(), irTry));
        for (IrCatch irCatch : aTry.getCatches()) {
            irCatch.setResult(useAsResult(irCatch.getResult(), irTry));
        }
        IrExpression finallyExpression = aTry.getFinallyExpression();
        aTry.setFinallyExpression(finallyExpression != null ? useAsStatement(finallyExpression) : null);
        return irTry;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitVararg(IrVararg expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        int i = 0;
        for (Object obj : expression.getElements()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrVarargElement irVarargElement = (IrVarargElement) obj;
            if (irVarargElement instanceof IrSpreadElement) {
                IrSpreadElement irSpreadElement = (IrSpreadElement) irVarargElement;
                irSpreadElement.setExpression(useAs(irSpreadElement.getExpression(), expression.getType()));
            } else if (irVarargElement instanceof IrExpression) {
                IrExpressionsKt.putElement(expression, i, useAsVarargElement((IrExpression) irVarargElement, expression));
            }
            i = i2;
        }
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitTypeOperator(IrTypeOperatorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        expression.setArgument(useInTypeOperator(expression.getArgument(), expression.getOperator(), expression.getTypeOperand()));
        return expression;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitFunction(IrFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        for (IrValueParameter irValueParameter : declaration.getValueParameters()) {
            IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
            if (defaultValue != null) {
                defaultValue.setExpression(useAsArgument(defaultValue.getExpression(), irValueParameter));
            }
        }
        IrBody body = declaration.getBody();
        if (body != null && (body instanceof IrExpressionBody)) {
            IrExpressionBody irExpressionBody = (IrExpressionBody) body;
            irExpressionBody.setExpression(useAsReturnValue(irExpressionBody.getExpression(), declaration.getSymbol()));
        }
        return declaration;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitStringConcatenation(IrStringConcatenation expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        if (expression instanceof IrStringConcatenationImpl) {
            Iterator<IrExpression> it = expression.getArguments().iterator();
            int i = 0;
            while (it.hasNext()) {
                expression.getArguments().set(i, useAs(it.next(), this.irBuiltIns.getAnyNType()));
                i++;
            }
        }
        return expression;
    }
}
