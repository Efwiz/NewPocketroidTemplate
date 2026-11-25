package org.jetbrains.kotlin.backend.common.lower;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrEnumConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: DefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\u00020\u0004B3\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t¢\u0006\u0002\u0010\fJ\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0014J\u0010\u0010!\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 H\u0014J\u0016\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%H\u0002J\u0018\u0010'\u001a\u00020#2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0012H\u0016J\u0010\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020 H\u0002J\"\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002002\u0006\u00102\u001a\u000203H\u0014J \u0010-\u001a\u00020.2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002002\u0006\u00104\u001a\u000205H\u0014J\u0010\u00106\u001a\u00020\t2\u0006\u00107\u001a\u00020\u001cH\u0014J\u0010\u00108\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 H\u0014J\u0010\u00109\u001a\u00020.2\u0006\u0010\u001b\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020.2\u0006\u0010\u001b\u001a\u00020<H\u0016J\u0010\u0010=\u001a\u00020.2\u0006\u0010\u001b\u001a\u00020>H\u0016J\u0010\u0010?\u001a\u00020.2\u0006\u0010\u001b\u001a\u00020@H\u0016J3\u0010A\u001a\u00020.\"\b\b\u0001\u0010B*\u00020\u001c2\u0006\u0010\u001b\u001a\u0002HB2\u0012\u0010C\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u0002HB0DH\u0002¢\u0006\u0002\u0010EJ*\u0010F\u001a\u0010\u0012\u0004\u0012\u000203\u0012\u0006\u0012\u0004\u0018\u00010.0G*\u00020H2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010I\u001a\u00020 H\u0014J\f\u0010J\u001a\u00020\t*\u000203H\u0004R\u0016\u0010\u0005\u001a\u00028\u0000X\u0084\u0004¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u000b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\n\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0014\u0010\b\u001a\u00020\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/DefaultParameterInjector;", "TContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "factory", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;", "skipInline", "", "skipExternalMethods", "forceSetOverrideSymbols", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;ZZZ)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "declarationStack", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getFactory", "()Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;", "getForceSetOverrideSymbols", "()Z", "getSkipExternalMethods", "getSkipInline", "createStubFunction", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "defaultArgumentStubVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isStatic", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "", NotificationCompat.CATEGORY_MESSAGE, "Lkotlin/Function0;", "", "lower", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "needsTypeArgumentOffset", "declaration", "nullConst", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "startOffset", "", "endOffset", "irParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "shouldReplaceWithSyntheticFunction", "functionAccess", "useConstructorMarker", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitEnumConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "visitFunctionAccessExpression", ExifInterface.GPS_DIRECTION_TRUE, "builder", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "argumentsForCall", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "stubFunction", "isMovedReceiver", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DefaultParameterInjector<TContext extends CommonBackendContext> extends IrElementTransformerVoid implements BodyLoweringPass {
    private final TContext context;
    private final List<IrDeclaration> declarationStack;
    private final DefaultArgumentFunctionFactory factory;
    private final boolean forceSetOverrideSymbols;
    private final boolean skipExternalMethods;
    private final boolean skipInline;

    protected boolean isStatic(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return false;
    }

    public /* synthetic */ DefaultParameterInjector(CommonBackendContext commonBackendContext, DefaultArgumentFunctionFactory defaultArgumentFunctionFactory, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, defaultArgumentFunctionFactory, (i & 4) != 0 ? true : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? true : z3);
    }

    protected final TContext getContext() {
        return this.context;
    }

    protected final DefaultArgumentFunctionFactory getFactory() {
        return this.factory;
    }

    protected final boolean getSkipInline() {
        return this.skipInline;
    }

    protected final boolean getSkipExternalMethods() {
        return this.skipExternalMethods;
    }

    protected final boolean getForceSetOverrideSymbols() {
        return this.forceSetOverrideSymbols;
    }

    public DefaultParameterInjector(TContext context, DefaultArgumentFunctionFactory factory, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.context = context;
        this.factory = factory;
        this.skipInline = z;
        this.skipExternalMethods = z2;
        this.forceSetOverrideSymbols = z3;
        this.declarationStack = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        UtilsKt.push(this.declarationStack, container);
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, this);
        UtilsKt.pop(this.declarationStack);
    }

    protected boolean shouldReplaceWithSyntheticFunction(IrFunctionAccessExpression functionAccess) {
        int i;
        Intrinsics.checkNotNullParameter(functionAccess, "functionAccess");
        Iterable iterableUntil = RangesKt.until(0, functionAccess.getValueArgumentsCount());
        if ((iterableUntil instanceof Collection) && ((Collection) iterableUntil).isEmpty()) {
            i = 0;
        } else {
            Iterator it = iterableUntil.iterator();
            i = 0;
            while (it.hasNext()) {
                if (functionAccess.getValueArgument(((IntIterator) it).nextInt()) != null && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i != functionAccess.getSymbol().getOwner().getValueParameters().size();
    }

    private final <T extends IrFunctionAccessExpression> IrExpression visitFunctionAccessExpression(final T expression, Function1<? super IrFunctionSymbol, ? extends T> builder) {
        if (!shouldReplaceWithSyntheticFunction(expression)) {
            return expression;
        }
        final IrFunctionSymbol irFunctionSymbolCreateStubFunction = createStubFunction(expression);
        if (irFunctionSymbolCreateStubFunction == null) {
            return expression;
        }
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (final int i = 0; i < typeArgumentsCount; i++) {
            log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector.visitFunctionAccessExpression.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;ITT;)V */
                /* JADX WARN: Unknown type variable: T in type: T */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return irFunctionSymbolCreateStubFunction + '[' + i + "]: " + expression.getTypeArgument(i);
                }
            });
        }
        final IrFunction owner = irFunctionSymbolCreateStubFunction.getOwner();
        for (final IrTypeParameter irTypeParameter : owner.getTypeParameters()) {
            log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector$visitFunctionAccessExpression$2$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final String invoke() {
                    return owner + '[' + irTypeParameter.getIndex() + "] : " + irTypeParameter;
                }
            });
        }
        IrFunction owner2 = ((IrFunctionSymbol) expression.getSymbol()).getOwner();
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, ((IrDeclaration) CollectionsKt.last((List) this.declarationStack)).getSymbol(), expression.getStartOffset(), expression.getEndOffset());
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset(), null, null, false, 64, null);
        T tInvoke = builder.invoke(irFunctionSymbolCreateStubFunction);
        int size = needsTypeArgumentOffset(owner2) ? IrUtilsKt.getParentAsClass(owner2).getTypeParameters().size() : 0;
        int typeArgumentsCount2 = tInvoke.getTypeArgumentsCount();
        for (int i2 = 0; i2 < typeArgumentsCount2; i2++) {
            tInvoke.putTypeArgument(i2, expression.getTypeArgument(i2 + size));
        }
        for (Map.Entry<IrValueParameter, IrExpression> entry : argumentsForCall(irBlockBuilder, expression, owner).entrySet()) {
            final IrValueParameter key = entry.getKey();
            final IrExpression value = entry.getValue();
            if (Intrinsics.areEqual(key, owner.getDispatchReceiverParameter())) {
                log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector$visitFunctionAccessExpression$3$newCall$1$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "call::dispatch@: " + IrUtilsKt.ir2string(value);
                    }
                });
            } else if (Intrinsics.areEqual(key, owner.getExtensionReceiverParameter())) {
                log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector$visitFunctionAccessExpression$3$newCall$1$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "call::extension@: " + IrUtilsKt.ir2string(value);
                    }
                });
            } else {
                log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector$visitFunctionAccessExpression$3$newCall$1$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final String invoke() {
                        return "call::params@$" + key.getIndex() + '/' + key.getName() + ": " + IrUtilsKt.ir2string(value);
                    }
                });
            }
            if (value != null) {
                IrExpressionsKt.putArgument(tInvoke, key, value);
            }
        }
        irBlockBuilder.unaryPlus(IrUtilsKt.irCastIfNeeded(irBlockBuilder, tInvoke, expression.getType()));
        return IrUtilsKt.unwrapBlock(irBlockBuilder.getIrBlockBody());
    }

    private final boolean needsTypeArgumentOffset(IrFunction declaration) {
        return isStatic(declaration) && IrDeclarationsKt.isMultiFieldValueClass(IrUtilsKt.getParentAsClass(declaration)) && (declaration instanceof IrSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitDelegatingConstructorCall(final IrDelegatingConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        return visitFunctionAccessExpression(expression, new Function1<IrFunctionSymbol, IrDelegatingConstructorCall>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector.visitDelegatingConstructorCall.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrDelegatingConstructorCall invoke(IrFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrDelegatingConstructorCall irDelegatingConstructorCall = expression;
                IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) it;
                return new IrDelegatingConstructorCallImpl(irDelegatingConstructorCall.getStartOffset(), irDelegatingConstructorCall.getEndOffset(), irDelegatingConstructorCall.getType(), irConstructorSymbol, irDelegatingConstructorCall.getTypeArgumentsCount(), irConstructorSymbol.getOwner().getValueParameters().size());
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(final IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        return visitFunctionAccessExpression(expression, new Function1<IrFunctionSymbol, IrConstructorCall>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector.visitConstructorCall.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrConstructorCall invoke(IrFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrConstructorCall irConstructorCall = expression;
                return IrConstructorCallImpl.INSTANCE.fromSymbolOwner(irConstructorCall.getStartOffset(), irConstructorCall.getEndOffset(), irConstructorCall.getType(), (IrConstructorSymbol) it, LoweredStatementOrigins.DEFAULT_DISPATCH_CALL.INSTANCE);
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitEnumConstructorCall(final IrEnumConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        return visitFunctionAccessExpression(expression, new Function1<IrFunctionSymbol, IrEnumConstructorCall>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector.visitEnumConstructorCall.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrEnumConstructorCall invoke(IrFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrEnumConstructorCall irEnumConstructorCall = expression;
                IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) it;
                return new IrEnumConstructorCallImpl(irEnumConstructorCall.getStartOffset(), irEnumConstructorCall.getEndOffset(), irEnumConstructorCall.getType(), irConstructorSymbol, irEnumConstructorCall.getTypeArgumentsCount(), irConstructorSymbol.getOwner().getValueParameters().size());
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(final IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        final int size = needsTypeArgumentOffset(owner) ? IrUtilsKt.getParentAsClass(owner).getTypeParameters().size() : 0;
        return visitFunctionAccessExpression(expression, new Function1<IrFunctionSymbol, IrCall>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector$visitCall$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrCall invoke(IrFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) it;
                return new IrCallImpl(expression.getStartOffset(), expression.getEndOffset(), irSimpleFunctionSymbol.getOwner().getReturnType(), irSimpleFunctionSymbol, expression.getTypeArgumentsCount() - size, irSimpleFunctionSymbol.getOwner().getValueParameters().size(), LoweredStatementOrigins.DEFAULT_DISPATCH_CALL.INSTANCE, expression.getSuperQualifierSymbol());
            }
        });
    }

    private final IrFunctionSymbol createStubFunction(IrFunctionAccessExpression expression) {
        final IrFunction irFunctionGenerateDefaultsFunction;
        final IrFunction owner = expression.getSymbol().getOwner();
        IrFunction irFunctionFindBaseFunctionWithDefaultArgumentsFor = this.factory.findBaseFunctionWithDefaultArgumentsFor(owner, this.skipInline, this.skipExternalMethods);
        if (irFunctionFindBaseFunctionWithDefaultArgumentsFor == null || (irFunctionGenerateDefaultsFunction = this.factory.generateDefaultsFunction(irFunctionFindBaseFunctionWithDefaultArgumentsFor, this.skipInline, this.skipExternalMethods, this.forceSetOverrideSymbols, defaultArgumentStubVisibility(owner), useConstructorMarker(owner), IrUtilsKt.copyAnnotations(irFunctionFindBaseFunctionWithDefaultArgumentsFor))) == null) {
            return null;
        }
        log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector.createStubFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return owner + " -> " + irFunctionGenerateDefaultsFunction;
            }
        });
        return irFunctionGenerateDefaultsFunction.getSymbol();
    }

    protected Map<IrValueParameter, IrExpression> argumentsForCall(IrBlockBuilder irBlockBuilder, IrFunctionAccessExpression expression, IrFunction stubFunction) {
        List listEmptyList;
        IrCompositeImpl valueArgument;
        boolean z;
        Intrinsics.checkNotNullParameter(irBlockBuilder, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(stubFunction, "stubFunction");
        int startOffset = expression.getStartOffset();
        int endOffset = expression.getEndOffset();
        IrFunction owner = expression.getSymbol().getOwner();
        List<IrValueParameter> valueParameters = owner.getValueParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (!isMovedReceiver((IrValueParameter) obj)) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = (size + 31) / 32;
        int[] iArr = new int[i];
        int i2 = 0;
        boolean z2 = true;
        CollectionsKt.listOf((Object[]) new Integer[]{0, 1}).contains(Integer.valueOf((IrUtilsKt.getExplicitParametersCount(stubFunction) - IrUtilsKt.getExplicitParametersCount(owner)) - i));
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        if (isStatic(owner)) {
            listEmptyList = CollectionsKt.listOfNotNull((Object[]) new IrValueParameter[]{stubFunction.getDispatchReceiverParameter(), stubFunction.getExtensionReceiverParameter()});
        } else {
            IrValueParameter dispatchReceiverParameter = stubFunction.getDispatchReceiverParameter();
            if (dispatchReceiverParameter != null) {
            }
            IrValueParameter extensionReceiverParameter = stubFunction.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
            }
            listEmptyList = CollectionsKt.emptyList();
        }
        int i3 = -1;
        for (IrValueParameter irValueParameter : CollectionsKt.plus((Collection) listEmptyList, (Iterable) stubFunction.getValueParameters())) {
            int i4 = i2 + 1;
            if (!isMovedReceiver(irValueParameter) && !Intrinsics.areEqual(irValueParameter, stubFunction.getDispatchReceiverParameter()) && !Intrinsics.areEqual(irValueParameter, stubFunction.getExtensionReceiverParameter())) {
                i3++;
            }
            if (i3 >= size + i) {
                valueArgument = IrConstImpl.INSTANCE.constNull(startOffset, endOffset, irValueParameter.getType());
                z = z2;
            } else if (i3 >= size) {
                valueArgument = IrConstImpl.INSTANCE.m7211int(startOffset, endOffset, irValueParameter.getType(), iArr[i3 - size]);
                z = true;
            } else {
                valueArgument = expression.getValueArgument(i2);
                if (valueArgument == null) {
                    int i5 = i3 / 32;
                    z = true;
                    iArr[i5] = iArr[i5] | (1 << (i3 % 32));
                } else {
                    z = true;
                }
                if (valueArgument == null) {
                    IrExpression irExpressionNullConst = nullConst(startOffset, endOffset, irValueParameter);
                    valueArgument = irExpressionNullConst != null ? new IrCompositeImpl(expression.getStartOffset(), expression.getEndOffset(), irValueParameter.getType(), IrStatementOrigin.DEFAULT_VALUE.INSTANCE, CollectionsKt.listOf(irExpressionNullConst)) : null;
                }
            }
            mapCreateMapBuilder.put(irValueParameter, valueArgument);
            i2 = i4;
            z2 = z;
        }
        return MapsKt.build(mapCreateMapBuilder);
    }

    protected IrExpression nullConst(int startOffset, int endOffset, IrValueParameter irParameter) {
        Intrinsics.checkNotNullParameter(irParameter, "irParameter");
        if (irParameter.getVarargElementType() != null) {
            return null;
        }
        return nullConst(startOffset, endOffset, irParameter.getType());
    }

    protected IrExpression nullConst(int startOffset, int endOffset, IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return IrConstImpl.INSTANCE.defaultValueForType(startOffset, endOffset, type);
    }

    protected DescriptorVisibility defaultArgumentStubVisibility(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        return PUBLIC;
    }

    protected boolean useConstructorMarker(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function instanceof IrConstructor;
    }

    private final void log(final Function0<String> msg) {
        this.context.log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultParameterInjector.log.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "DEFAULT-INJECTOR: " + msg.invoke();
            }
        });
    }

    protected final boolean isMovedReceiver(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        return DefaultArgumentStubGeneratorKt.isMovedReceiver(irValueParameter);
    }
}
