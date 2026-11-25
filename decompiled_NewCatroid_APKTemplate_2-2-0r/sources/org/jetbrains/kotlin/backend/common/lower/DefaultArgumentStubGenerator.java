package org.jetbrains.kotlin.backend.common.lower;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.ir.ValueRemapper;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: DefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B3\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\u0016\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002J\u0018\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u0015H\u0002J\u0010\u0010 \u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020!H\u0014J\u0018\u0010\"\u001a\n\u0012\u0004\u0012\u00020#\u0018\u00010\u001e2\u0006\u0010$\u001a\u00020#H\u0016J\u0010\u0010%\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J*\u0010&\u001a\u00020'*\u00020(2\u0006\u0010\u001f\u001a\u00020!2\u0006\u0010)\u001a\u00020\u00152\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H\u0002J\u0014\u0010-\u001a\u00020.*\u00020\u00152\u0006\u0010/\u001a\u00020\u0015H\u0014J2\u00100\u001a\u00020'*\u00020(2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u00152\u0006\u0010)\u001a\u00020\u00152\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+H\u0014J\u001c\u00104\u001a\u00020\u0019*\u00020(2\u0006\u0010\u001f\u001a\u00020\u00152\u0006\u0010)\u001a\u00020\u0015H\u0014J\u0014\u00105\u001a\u00020'*\u00020'2\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0012\u00106\u001a\b\u0012\u0004\u0012\u0002070\u001e*\u00020\u0015H\u0014J$\u00108\u001a\u00020,*\u00020(2\u0006\u00109\u001a\u00020'2\u0006\u0010:\u001a\u0002022\u0006\u0010;\u001a\u00020'H\u0014R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentStubGenerator;", "TContext", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "factory", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;", "skipInlineMethods", "", "skipExternalMethods", "forceSetOverrideSymbols", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;ZZZ)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "withLocalDeclarations", "getWithLocalDeclarations", "()Z", "defaultArgumentStubVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getOriginForCallToImplementation", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "", NotificationCompat.CATEGORY_MESSAGE, "Lkotlin/Function0;", "", "lower", "", "irFunction", "needSpecialDispatch", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "transformFlat", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "useConstructorMarker", "dispatchToImplementation", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "newIrFunction", "params", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "generateDefaultStubBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "originalDeclaration", "generateHandleCall", "handlerDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "oldIrFunction", "generateSuperCallHandlerCheckIfNeeded", "prepareToBeUsedIn", "resolveAnnotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "selectArgumentOrDefault", "defaultFlag", "parameter", "default", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DefaultArgumentStubGenerator<TContext extends CommonBackendContext> implements DeclarationTransformer {
    private final TContext context;
    private final DefaultArgumentFunctionFactory factory;
    private final boolean forceSetOverrideSymbols;
    private final boolean skipExternalMethods;
    private final boolean skipInlineMethods;

    protected void generateSuperCallHandlerCheckIfNeeded(IrBlockBodyBuilder irBlockBodyBuilder, IrFunction irFunction, IrFunction newIrFunction) {
        Intrinsics.checkNotNullParameter(irBlockBodyBuilder, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(newIrFunction, "newIrFunction");
    }

    protected IrStatementOrigin getOriginForCallToImplementation() {
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public boolean getWithLocalDeclarations() {
        return true;
    }

    protected boolean needSpecialDispatch(IrSimpleFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        return false;
    }

    public DefaultArgumentStubGenerator(TContext context, DefaultArgumentFunctionFactory factory, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.context = context;
        this.factory = factory;
        this.skipInlineMethods = z;
        this.skipExternalMethods = z2;
        this.forceSetOverrideSymbols = z3;
    }

    public /* synthetic */ DefaultArgumentStubGenerator(CommonBackendContext commonBackendContext, DefaultArgumentFunctionFactory defaultArgumentFunctionFactory, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, defaultArgumentFunctionFactory, (i & 4) != 0 ? true : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? true : z3);
    }

    public final TContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrFunction) {
            return lower((IrFunction) declaration);
        }
        return null;
    }

    protected List<IrConstructorCall> resolveAnnotations(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return IrUtilsKt.copyAnnotations(irFunction);
    }

    protected IrBody generateDefaultStubBody(final IrFunction irFunction, final IrFunction originalDeclaration) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(originalDeclaration, "originalDeclaration");
        final DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irFunction.getSymbol(), 0, 0, 6, (Object) null);
        log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator.generateDefaultStubBody.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return originalDeclaration + " -> " + irFunction;
            }
        });
        return IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), -1, -1, new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator.generateDefaultStubBody.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Iterator<IrValueParameter> it;
                IrValueParameter irValueParameter;
                IrValueParameter irValueParameterSelectArgumentOrDefault;
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                List<IrStatement> statements = createBlockBody.getStatements();
                DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
                IrFunction irFunction2 = irFunction;
                IrFunction irFunction3 = irFunction2;
                IrFunction irFunction4 = originalDeclaration;
                DefaultArgumentStubGenerator<TContext> defaultArgumentStubGenerator = this;
                IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), irFunction3.getStartOffset(), irFunction3.getEndOffset());
                ArrayList arrayList = new ArrayList();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                IrValueParameter dispatchReceiverParameter = irFunction4.getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null) {
                    IrValueParameterSymbol symbol = dispatchReceiverParameter.getSymbol();
                    IrValueParameter dispatchReceiverParameter2 = irFunction2.getDispatchReceiverParameter();
                    IrValueParameterSymbol symbol2 = dispatchReceiverParameter2 != null ? dispatchReceiverParameter2.getSymbol() : null;
                    Intrinsics.checkNotNull(symbol2);
                    linkedHashMap.put(symbol, symbol2);
                }
                IrValueParameter extensionReceiverParameter = irFunction4.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null) {
                    IrValueParameterSymbol symbol3 = extensionReceiverParameter.getSymbol();
                    IrValueParameter extensionReceiverParameter2 = irFunction2.getExtensionReceiverParameter();
                    IrValueParameterSymbol symbol4 = extensionReceiverParameter2 != null ? extensionReceiverParameter2.getSymbol() : null;
                    Intrinsics.checkNotNull(symbol4);
                    linkedHashMap.put(symbol3, symbol4);
                }
                for (IrValueParameter irValueParameter2 : irFunction4.getValueParameters()) {
                    linkedHashMap.put(irValueParameter2.getSymbol(), irFunction2.getValueParameters().get(irValueParameter2.getIndex()).getSymbol());
                }
                defaultArgumentStubGenerator.generateSuperCallHandlerCheckIfNeeded(irBlockBodyBuilder, irFunction4, irFunction2);
                IrSimpleFunctionSymbol binaryOperator = defaultArgumentStubGenerator.getContext().getIr().getSymbols().getBinaryOperator(OperatorNameConventions.AND, irBlockBodyBuilder.getContext().getIrBuiltIns().getIntType(), irBlockBodyBuilder.getContext().getIrBuiltIns().getIntType());
                Iterator<IrValueParameter> it2 = irFunction4.getValueParameters().iterator();
                int i = -1;
                while (it2.hasNext()) {
                    IrValueParameter next = it2.next();
                    if (!DefaultArgumentStubGeneratorKt.isMovedReceiver(next)) {
                        i++;
                    }
                    IrValueParameter irValueParameter3 = irFunction2.getValueParameters().get(next.getIndex());
                    IrExpressionBody defaultValue = next.getDefaultValue();
                    if (defaultValue != null) {
                        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                        it = it2;
                        irValueParameter = irValueParameter3;
                        irValueParameterSelectArgumentOrDefault = defaultArgumentStubGenerator.selectArgumentOrDefault(irBlockBodyBuilder, ExpressionHelpersKt.irCallOp(irBlockBodyBuilder2, binaryOperator, irBlockBodyBuilder.getContext().getIrBuiltIns().getIntType(), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irFunction2.getValueParameters().get(irFunction4.getValueParameters().size() + (next.getIndex() / 32))), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, 1 << (i % 32), null, 2, null), (24 & 16) != 0 ? null : null), irValueParameter, defaultArgumentStubGenerator.prepareToBeUsedIn(defaultValue.getExpression(), irFunction2).transform((IrElementTransformer<? super Object>) new ValueRemapper(linkedHashMap), (Object) null));
                        if (irValueParameterSelectArgumentOrDefault == null) {
                        }
                        arrayList.add(irValueParameterSelectArgumentOrDefault);
                        linkedHashMap.put(next.getSymbol(), irValueParameterSelectArgumentOrDefault.getSymbol());
                        it2 = it;
                    } else {
                        it = it2;
                        irValueParameter = irValueParameter3;
                    }
                    irValueParameterSelectArgumentOrDefault = irValueParameter;
                    arrayList.add(irValueParameterSelectArgumentOrDefault);
                    linkedHashMap.put(next.getSymbol(), irValueParameterSelectArgumentOrDefault.getSymbol());
                    it2 = it;
                }
                if (irFunction4 instanceof IrConstructor) {
                    IrBlockBodyBuilder irBlockBodyBuilder3 = irBlockBodyBuilder;
                    IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder3, (IrConstructor) irFunction4);
                    IrDelegatingConstructorCall irDelegatingConstructorCall2 = irDelegatingConstructorCall;
                    IrFunction irFunction5 = irFunction2;
                    int i2 = 0;
                    IrUtilsKt.passTypeArgumentsFrom$default(irDelegatingConstructorCall2, IrUtilsKt.getParentAsClass(irFunction5), 0, 2, null);
                    IrUtilsKt.passTypeArgumentsFrom(irDelegatingConstructorCall2, irFunction2, IrUtilsKt.getParentAsClass(irFunction5).getTypeParameters().size());
                    IrValueParameter dispatchReceiverParameter3 = irFunction2.getDispatchReceiverParameter();
                    irDelegatingConstructorCall.setDispatchReceiver(dispatchReceiverParameter3 != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder3, dispatchReceiverParameter3) : null);
                    for (Object obj : arrayList) {
                        int i3 = i2 + 1;
                        if (i2 < 0) {
                            CollectionsKt.throwIndexOverflow();
                        }
                        irDelegatingConstructorCall.putValueArgument(i2, ExpressionHelpersKt.irGet(irBlockBodyBuilder3, (IrValueDeclaration) obj));
                        i2 = i3;
                    }
                    irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
                } else if (irFunction4 instanceof IrSimpleFunction) {
                    irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder, defaultArgumentStubGenerator.dispatchToImplementation(irBlockBodyBuilder, (IrSimpleFunction) irFunction4, irFunction2, arrayList)));
                } else {
                    throw new IllegalStateException("Unknown function declaration".toString());
                }
                CollectionsKt.addAll(statements, irBlockBodyBuilder.getIrBlockBody().getStatements());
            }
        });
    }

    private final List<IrFunction> lower(IrFunction irFunction) {
        IrFunction irFunctionGenerateDefaultsFunction = this.factory.generateDefaultsFunction(irFunction, this.skipInlineMethods, this.skipExternalMethods, this.forceSetOverrideSymbols, defaultArgumentStubVisibility(irFunction), useConstructorMarker(irFunction), resolveAnnotations(irFunction));
        if (irFunctionGenerateDefaultsFunction == null) {
            return null;
        }
        List<IrFunction> listListOf = CollectionsKt.listOf((Object[]) new IrFunction[]{irFunction, irFunctionGenerateDefaultsFunction});
        if (!IrFakeOverrideUtilsKt.isFakeOverride(irFunctionGenerateDefaultsFunction)) {
            irFunctionGenerateDefaultsFunction.setBody(generateDefaultStubBody(irFunctionGenerateDefaultsFunction, irFunction));
        }
        return listListOf;
    }

    protected IrExpression prepareToBeUsedIn(IrExpression irExpression, IrFunction function) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(function, "function");
        return (IrExpression) PatchDeclarationParentsKt.patchDeclarationParents(irExpression, function);
    }

    protected IrValueDeclaration selectArgumentOrDefault(IrBlockBodyBuilder irBlockBodyBuilder, IrExpression defaultFlag, IrValueParameter parameter, IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irBlockBodyBuilder, "<this>");
        Intrinsics.checkNotNullParameter(defaultFlag, "defaultFlag");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(irExpression, "default");
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(LowerUtilsKt.irIfThen(irBlockBodyBuilder2, ExpressionHelpersKt.irNotEquals(irBlockBodyBuilder2, defaultFlag, ExpressionHelpersKt.irInt$default(irBlockBodyBuilder2, 0, null, 2, null)), ExpressionHelpersKt.irSet$default(irBlockBodyBuilder2, parameter.getSymbol(), irExpression, (IrStatementOrigin) null, 4, (Object) null)));
        return parameter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression dispatchToImplementation(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction, IrFunction irFunction, List<IrValueDeclaration> list) {
        IrExpression irExpressionIrGet;
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBodyBuilder2, irSimpleFunction2, getOriginForCallToImplementation(), (IrClassSymbol) null, 4, (Object) null);
        int i = 0;
        IrUtilsKt.passTypeArgumentsFrom$default(irCallIrCall$default, irFunction, 0, 2, null);
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        irCallIrCall$default.setDispatchReceiver(dispatchReceiverParameter != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter) : null);
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        irCallIrCall$default.setExtensionReceiver(extensionReceiverParameter != null ? ExpressionHelpersKt.irGet(irBlockBodyBuilder2, extensionReceiverParameter) : null);
        for (IrValueDeclaration irValueDeclaration : list) {
            int i2 = i + 1;
            IrType type = irSimpleFunction.getValueParameters().get(i).getType();
            if (!IrTypeUtilsKt.isNullable(type) && IrTypeUtilsKt.isNullable(irValueDeclaration.getType())) {
                irExpressionIrGet = ExpressionHelpersKt.irImplicitCast(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueDeclaration), type);
            } else {
                irExpressionIrGet = ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueDeclaration);
            }
            irCallIrCall$default.putValueArgument(i, irExpressionIrGet);
            i = i2;
        }
        if (needSpecialDispatch(irSimpleFunction)) {
            IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.last((List) irFunction.getValueParameters());
            return ExpressionHelpersKt.irIfThenElse$default(irBlockBodyBuilder2, irSimpleFunction.getReturnType(), ExpressionHelpersKt.irEqualsNull(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter)), irCallIrCall$default, generateHandleCall(irBlockBodyBuilder, irValueParameter, irSimpleFunction2, irFunction, list), null, 16, null);
        }
        return irCallIrCall$default;
    }

    protected IrExpression generateHandleCall(IrBlockBodyBuilder irBlockBodyBuilder, IrValueParameter handlerDeclaration, IrFunction oldIrFunction, IrFunction newIrFunction, List<IrValueDeclaration> params) {
        Intrinsics.checkNotNullParameter(irBlockBodyBuilder, "<this>");
        Intrinsics.checkNotNullParameter(handlerDeclaration, "handlerDeclaration");
        Intrinsics.checkNotNullParameter(oldIrFunction, "oldIrFunction");
        Intrinsics.checkNotNullParameter(newIrFunction, "newIrFunction");
        Intrinsics.checkNotNullParameter(params, "params");
        needSpecialDispatch((IrSimpleFunction) oldIrFunction);
        throw new IllegalStateException("This method should be overridden".toString());
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
        this.context.log(new Function0<String>() { // from class: org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator.log.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "DEFAULT-REPLACER: " + msg.invoke();
            }
        });
    }
}
