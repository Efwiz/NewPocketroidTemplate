package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.backend.common.descriptors.DescriptorUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.FinallyBlocksLowering;
import org.jetbrains.kotlin.backend.common.lower.ReturnableBlockTransformer;
import org.jetbrains.kotlin.backend.common.lower.coroutines.AddContinuationToFunctionsLoweringKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsCommonCoroutineSymbols;
import org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDoWhileLoopImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: JsSuspendFunctionsLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J(\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0002J,\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020)0'H\u0014J\"\u0010*\u001a\u0014\u0012\u0004\u0012\u00020+\u0012\n\u0012\b\u0012\u0004\u0012\u00020-0,0'2\u0006\u0010.\u001a\u00020/H\u0002J\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u00020$H\u0014J\u0010\u00103\u001a\u00020\u00152\u0006\u00102\u001a\u00020$H\u0014J\u0018\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u000207H\u0002J\u001c\u00109\u001a\u00020\u0019*\u00020:2\u0006\u0010;\u001a\u00020$2\u0006\u0010<\u001a\u00020=H\u0014J\u001c\u0010>\u001a\u00020=*\u00020?2\u0006\u0010@\u001a\u0002072\u0006\u0010A\u001a\u00020=H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0015X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/JsSuspendFunctionsLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "ctx", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "coroutineId", "", "coroutineImplExceptionPropertyGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "coroutineImplExceptionPropertySetter", "coroutineImplExceptionStatePropertyGetter", "coroutineImplExceptionStatePropertySetter", "coroutineImplLabelPropertyGetter", "coroutineImplLabelPropertySetter", "coroutineImplResultSymbolGetter", "coroutineImplResultSymbolSetter", "coroutineSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "getCoroutineSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "stateMachineMethodName", "Lorg/jetbrains/kotlin/name/Name;", "getStateMachineMethodName", "()Lorg/jetbrains/kotlin/name/Name;", "assignStateIds", "", "entryState", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "subject", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", PsiKeyword.SWITCH, "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "rootLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "buildStateMachine", "stateMachineFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "transformingFunction", "argumentToPropertiesMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "computeLivenessAtSuspensionPoints", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getCoroutineBaseClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "function", "nameForCoroutineClass", "needUnboxingOrUnit", "", "fromType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "toType", "generateCoroutineStart", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "invokeSuspendFunction", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "generateDelegatedCall", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "expectedType", "delegatingCall", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsSuspendFunctionsLowering extends AbstractSuspendFunctionsLowering<JsCommonBackendContext> {
    private int coroutineId;
    private final IrSimpleFunction coroutineImplExceptionPropertyGetter;
    private final IrSimpleFunction coroutineImplExceptionPropertySetter;
    private final IrSimpleFunction coroutineImplExceptionStatePropertyGetter;
    private final IrSimpleFunction coroutineImplExceptionStatePropertySetter;
    private final IrSimpleFunction coroutineImplLabelPropertyGetter;
    private final IrSimpleFunction coroutineImplLabelPropertySetter;
    private final IrSimpleFunction coroutineImplResultSymbolGetter;
    private final IrSimpleFunction coroutineImplResultSymbolSetter;
    private final JsCommonCoroutineSymbols coroutineSymbols;
    private final Name stateMachineMethodName;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsSuspendFunctionsLowering(JsCommonBackendContext ctx) {
        super(ctx);
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        JsCommonCoroutineSymbols coroutineSymbols = ctx.getCoroutineSymbols();
        this.coroutineSymbols = coroutineSymbols;
        this.coroutineImplExceptionPropertyGetter = coroutineSymbols.getCoroutineImplExceptionPropertyGetter();
        this.coroutineImplExceptionPropertySetter = coroutineSymbols.getCoroutineImplExceptionPropertySetter();
        this.coroutineImplExceptionStatePropertyGetter = coroutineSymbols.getCoroutineImplExceptionStatePropertyGetter();
        this.coroutineImplExceptionStatePropertySetter = coroutineSymbols.getCoroutineImplExceptionStatePropertySetter();
        this.coroutineImplLabelPropertySetter = coroutineSymbols.getCoroutineImplLabelPropertySetter();
        this.coroutineImplLabelPropertyGetter = coroutineSymbols.getCoroutineImplLabelPropertyGetter();
        this.coroutineImplResultSymbolGetter = coroutineSymbols.getCoroutineImplResultSymbolGetter();
        this.coroutineImplResultSymbolSetter = coroutineSymbols.getCoroutineImplResultSymbolSetter();
        Name nameIdentifier = Name.identifier("doResume");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        this.stateMachineMethodName = nameIdentifier;
    }

    public final JsCommonCoroutineSymbols getCoroutineSymbols() {
        return this.coroutineSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering
    protected Name getStateMachineMethodName() {
        return this.stateMachineMethodName;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering
    protected IrClassSymbol getCoroutineBaseClass(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return getContext().getIr().getSymbols().getCoroutineImpl();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering
    protected Name nameForCoroutineClass(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        StringBuilder sbAppend = new StringBuilder().append(function.getName()).append("COROUTINE$");
        int i = this.coroutineId;
        this.coroutineId = i + 1;
        return DescriptorUtilsKt.getSynthesizedName(sbAppend.append(i).toString());
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0375  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering
    protected void buildStateMachine(final IrFunction stateMachineFunction, IrFunction transformingFunction, Map<IrValueParameter, ? extends IrField> argumentToPropertiesMap) {
        Intrinsics.checkNotNullParameter(stateMachineFunction, "stateMachineFunction");
        Intrinsics.checkNotNullParameter(transformingFunction, "transformingFunction");
        Intrinsics.checkNotNullParameter(argumentToPropertiesMap, "argumentToPropertiesMap");
        IrElement irElementTransform = transformingFunction.transform(new FinallyBlocksLowering(getContext(), getContext().getCatchAllThrowableType()), null).transform(new ReturnableBlockTransformer(getContext(), null, 2, 0 == true ? 1 : 0), null);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
        final IrFunction irFunction = (IrFunction) irElementTransform;
        IrBody body = irFunction.getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockImpl irBlockImpl = new IrBlockImpl(irFunction.getStartOffset(), irFunction.getEndOffset(), getContext().getIrBuiltIns().getUnitType(), JsStatementOrigins.STATEMENT_ORIGIN_COROUTINE_IMPL.INSTANCE, ((IrBlockBody) body).getStatements());
        IrDeclarationParent parent = stateMachineFunction.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrType anyNType = getContext().getIrBuiltIns().getAnyNType();
        IrFunction irFunction2 = stateMachineFunction;
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.coroutineImplResultSymbolGetter.getSymbol(), null, null, null, null, 30, null);
        JsIrBuilder jsIrBuilder2 = JsIrBuilder.INSTANCE;
        IrValueParameter dispatchReceiverParameter = stateMachineFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irCallBuildCall$default.setDispatchReceiver(JsIrBuilder.buildGetValue$default(jsIrBuilder2, dispatchReceiverParameter.getSymbol(), null, 2, null));
        Unit unit = Unit.INSTANCE;
        final IrVariable irVariableBuildVar = jsIrBuilder.buildVar(anyNType, irFunction2, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : "suspendResult", (180 & 8) != 0 ? false : true, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : irCallBuildCall$default, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        IrVariable irVariableBuildVar2 = JsIrBuilder.INSTANCE.buildVar(this.coroutineImplLabelPropertyGetter.getReturnType(), irFunction2, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : "suspendState", (180 & 8) != 0 ? false : true, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : null, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        final IrType unitType = getContext().getIrBuiltIns().getUnitType();
        IrWhenImpl irWhenImpl = new IrWhenImpl(-1, -1, unitType, JsStatementOrigins.COROUTINE_SWITCH.INSTANCE);
        IrVariable irVariableBuildVar3 = JsIrBuilder.INSTANCE.buildVar(getContext().getIrBuiltIns().getIntType(), irFunction2, (180 & 4) != 0 ? Constants.TMP_DIRECTORY_NAME : null, (180 & 8) != 0 ? false : false, (180 & 16) != 0 ? false : false, (180 & 32) != 0 ? false : false, (180 & 64) != 0 ? null : null, (180 & 128) != 0 ? JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE : null);
        IrBlockImpl irBlockImpl2 = new IrBlockImpl(irWhenImpl.getStartOffset(), irWhenImpl.getEndOffset(), irWhenImpl.getType(), null, 8, null);
        irBlockImpl2.getStatements().add(irVariableBuildVar3);
        irBlockImpl2.getStatements().add(irWhenImpl);
        IrTryImpl irTryImpl = new IrTryImpl(irBlockImpl.getStartOffset(), irBlockImpl.getEndOffset(), unitType);
        irTryImpl.setTryResult(irBlockImpl2);
        IrDoWhileLoopImpl irDoWhileLoopImpl = new IrDoWhileLoopImpl(irBlockImpl.getStartOffset(), irBlockImpl.getEndOffset(), unitType, JsStatementOrigins.COROUTINE_ROOT_LOOP.INSTANCE);
        irDoWhileLoopImpl.setCondition(JsIrBuilder.INSTANCE.buildBoolean(getContext().getIrBuiltIns().getBooleanType(), true));
        irDoWhileLoopImpl.setBody(irTryImpl);
        irDoWhileLoopImpl.setLabel("$sm");
        Set<IrElement> setCollectSuspendableNodes = SuspendLoweringUtilsKt.collectSuspendableNodes(irBlockImpl);
        IrValueParameter dispatchReceiverParameter2 = stateMachineFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
        final IrValueParameterSymbol symbol = dispatchReceiverParameter2.getSymbol();
        IrCall irCallBuildCall$default2 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.coroutineImplLabelPropertyGetter.getSymbol(), null, null, null, null, 30, null);
        IrVariable irVariable = irVariableBuildVar2;
        irCallBuildCall$default2.setDispatchReceiver(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, symbol, null, 2, null));
        irVariableBuildVar3.setInitializer(irCallBuildCall$default2);
        IrDoWhileLoopImpl irDoWhileLoopImpl2 = irDoWhileLoopImpl;
        IrClass irClass = (IrClass) parent;
        StateMachineBuilder stateMachineBuilder = new StateMachineBuilder(setCollectSuspendableNodes, getContext(), stateMachineFunction.getSymbol(), irDoWhileLoopImpl2, this.coroutineImplExceptionPropertyGetter, this.coroutineImplExceptionPropertySetter, this.coroutineImplExceptionStatePropertyGetter, this.coroutineImplExceptionStatePropertySetter, this.coroutineImplLabelPropertySetter, symbol, new Function1<IrType, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering$buildStateMachine$stateMachineBuilder$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(IrType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return JsIrBuilder.INSTANCE.buildImplicitCast(JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, irVariableBuildVar.getSymbol(), null, 2, null), type);
            }
        }, new Function1<IrExpression, IrStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering$buildStateMachine$stateMachineBuilder$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrStatement invoke(IrExpression value) {
                Intrinsics.checkNotNullParameter(value, "value");
                return JsIrBuilder.INSTANCE.buildSetVariable(irVariableBuildVar.getSymbol(), JsIrBuilder.INSTANCE.buildImplicitCast(value, this.getContext().getIrBuiltIns().getAnyNType()), unitType);
            }
        });
        IrVisitorsKt.acceptVoid(irBlockImpl, stateMachineBuilder);
        stateMachineBuilder.finalizeStateMachine();
        irTryImpl.getCatches().add(stateMachineBuilder.getGlobalCatch());
        assignStateIds(stateMachineBuilder.getEntryState(), irVariableBuildVar3.getSymbol(), irWhenImpl, irDoWhileLoopImpl2);
        IrContainerExpression entryBlock = stateMachineBuilder.getEntryState().getEntryBlock();
        JsIrBuilder jsIrBuilder3 = JsIrBuilder.INSTANCE;
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrGetValueImpl irGetValueImplBuildGetValue$default = JsIrBuilder.buildGetValue$default(jsIrBuilder3, thisReceiver.getSymbol(), null, 2, null);
        IrConstImpl<Integer> irConstImplBuildInt = JsIrBuilder.INSTANCE.buildInt(getContext().getIrBuiltIns().getIntType(), stateMachineBuilder.getRootExceptionTrap().getId());
        List<IrStatement> statements = entryBlock.getStatements();
        IrCall irCallBuildCall$default3 = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.coroutineImplExceptionStatePropertySetter.getSymbol(), null, null, null, null, 30, null);
        irCallBuildCall$default3.setDispatchReceiver(irGetValueImplBuildGetValue$default);
        irCallBuildCall$default3.putValueArgument(0, irConstImplBuildInt);
        Unit unit2 = Unit.INSTANCE;
        statements.add(0, irCallBuildCall$default3);
        IrBlockBody irBlockBodyCreateBlockBody = IrFactoryHelpersKt.createBlockBody(getContext().getIrFactory(), stateMachineFunction.getStartOffset(), stateMachineFunction.getEndOffset(), (List<? extends IrStatement>) CollectionsKt.listOf((Object[]) new IrElement[]{irVariableBuildVar, irDoWhileLoopImpl}));
        IrBlockBody irBlockBody = irBlockBodyCreateBlockBody;
        stateMachineFunction.setBody(irBlockBody);
        IrElementTransformerVoidKt.transformChildrenVoid(irBlockBodyCreateBlockBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering.buildStateMachine.3
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitReturn(IrReturn expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
                if (!Intrinsics.areEqual(expression.getReturnTargetSymbol(), irFunction.getSymbol())) {
                    return expression;
                }
                return JsIrBuilder.INSTANCE.buildReturn(stateMachineFunction.getSymbol(), expression.getValue(), expression.getType());
            }
        });
        Set set = CollectionsKt.toSet(CollectionsKt.flatten(computeLivenessAtSuspensionPoints(irBlockBody).values()));
        HashMap map = new HashMap();
        int i = 0;
        for (Iterator it = set.iterator(); it.hasNext(); it = it) {
            IrValueDeclaration irValueDeclaration = (IrValueDeclaration) it.next();
            IrVariable irVariable2 = irVariable;
            if (irValueDeclaration == irVariable2 || irValueDeclaration == irVariableBuildVar || irValueDeclaration == irVariableBuildVar3) {
                irClass = irClass;
            } else {
                HashMap map2 = map;
                IrValueSymbol symbol2 = irValueDeclaration.getSymbol();
                if (map2.get(symbol2) == null) {
                    int i2 = i + 1;
                    Name nameIdentifier = Name.identifier(new StringBuilder().append(irValueDeclaration.getName()).append(i).toString());
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    IrType type = irValueDeclaration.getType();
                    IrVariable irVariable3 = irValueDeclaration instanceof IrVariable ? (IrVariable) irValueDeclaration : null;
                    map2.put(symbol2, addField(irClass, nameIdentifier, type, irVariable3 != null ? irVariable3.getIsVar() : false).getSymbol());
                    i = i2;
                } else {
                    irClass = irClass;
                }
            }
            irClass = irClass;
            irVariable = irVariable2;
        }
        for (IrValueParameter irValueParameter : transformingFunction.getParent() == irClass ? irFunction.getValueParameters() : IrUtilsKt.getExplicitParameters(irFunction)) {
            HashMap map3 = map;
            IrValueParameterSymbol symbol3 = irValueParameter.getSymbol();
            if (map3.get(symbol3) == null) {
                map3.put(symbol3, ((IrField) MapsKt.getValue(argumentToPropertiesMap, irValueParameter)).getSymbol());
            }
        }
        IrBody body2 = stateMachineFunction.getBody();
        Intrinsics.checkNotNull(body2);
        PatchDeclarationParentsKt.patchDeclarationParents(body2, irFunction2);
        stateMachineFunction.transform(new LiveLocalsTransformer(map, new Function0<IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering.buildStateMachine.6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrExpression invoke() {
                return JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, symbol, null, 2, null);
            }
        }, unitType), null);
    }

    private final void assignStateIds(SuspendState entryState, IrVariableSymbol subject, IrWhen irWhen, IrLoop rootLoop) {
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        List<SuspendState> list = DFS.topologicalOrder(CollectionsKt.listOf(entryState), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return JsSuspendFunctionsLowering.assignStateIds$lambda$11((SuspendState) obj);
            }
        }, new DFS.Visited() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.utils.DFS.Visited
            public final boolean checkAndMarkVisited(Object obj) {
                return JsSuspendFunctionsLowering.assignStateIds$lambda$12(linkedHashSet, (SuspendState) obj);
            }
        });
        Intrinsics.checkNotNull(list);
        for (IndexedValue indexedValue : CollectionsKt.withIndex(list)) {
            ((SuspendState) indexedValue.getValue()).setId(indexedValue.getIndex());
        }
        IrSimpleFunctionSymbol eqeqeqSymbol = getContext().getIrBuiltIns().getEqeqeqSymbol();
        for (SuspendState suspendState : list) {
            IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, eqeqeqSymbol, null, null, null, null, 30, null);
            irCallBuildCall$default.putValueArgument(0, JsIrBuilder.buildGetValue$default(JsIrBuilder.INSTANCE, subject, null, 2, null));
            irCallBuildCall$default.putValueArgument(1, JsIrBuilder.INSTANCE.buildInt(getContext().getIrBuiltIns().getIntType(), suspendState.getId()));
            irWhen.getBranches().add(new IrBranchImpl(suspendState.getEntryBlock().getStartOffset(), suspendState.getEntryBlock().getEndOffset(), irCallBuildCall$default, suspendState.getEntryBlock()));
        }
        IrElementTransformerVoidKt.transformChildrenVoid(rootLoop, new DispatchPointTransformer(new Function1<SuspendState, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering$assignStateIds$dispatchPointTransformer$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrExpression invoke(SuspendState it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.getId();
                return JsIrBuilder.INSTANCE.buildInt(this.this$0.getContext().getIrBuiltIns().getIntType(), it.getId());
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable assignStateIds$lambda$11(SuspendState suspendState) {
        return suspendState.getSuccessors();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean assignStateIds$lambda$12(Set visited, SuspendState suspendState) {
        Intrinsics.checkNotNullParameter(visited, "$visited");
        Intrinsics.checkNotNull(suspendState);
        return visited.add(suspendState);
    }

    private final Map<IrCall, List<IrValueDeclaration>> computeLivenessAtSuspensionPoints(IrBody body) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        IrVisitorsKt.acceptChildrenVoid(body, new AbstractSuspendFunctionsLowering.VariablesScopeTracker() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.JsSuspendFunctionsLowering.computeLivenessAtSuspensionPoints.1
            @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering.VariablesScopeTracker, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (!IrUtilsKt.isSuspend(expression)) {
                    super.visitCall(expression);
                    return;
                }
                IrVisitorsKt.acceptChildrenVoid(expression, this);
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = getScopeStack().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, (Set) it.next());
                }
                linkedHashMap.put(expression, arrayList);
            }
        });
        return linkedHashMap;
    }

    private final boolean needUnboxingOrUnit(IrType fromType, IrType toType) {
        JsCommonInlineClassesUtils inlineClassesUtils = getContext().getInlineClassesUtils();
        return (inlineClassesUtils.getInlinedClass(fromType) == null && inlineClassesUtils.getInlinedClass(toType) != null) || (IrTypePredicatesKt.isUnit(fromType) && !IrTypePredicatesKt.isUnit(toType));
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering
    protected IrExpression generateDelegatedCall(IrBuilderWithScope irBuilderWithScope, IrType expectedType, IrExpression delegatingCall) {
        IrType type;
        IrSimpleFunctionSymbol symbol;
        IrSimpleFunction owner;
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(delegatingCall, "delegatingCall");
        IrCall irCall = delegatingCall instanceof IrCall ? (IrCall) delegatingCall : null;
        if (irCall == null || (symbol = irCall.getSymbol()) == null || (owner = symbol.getOwner()) == null || (type = AddContinuationToFunctionsLoweringKt.loweredSuspendFunctionReturnType(owner, irBuilderWithScope.getContext().getIrBuiltIns())) == null) {
            type = delegatingCall.getType();
        }
        IrType irType = type;
        if (!needUnboxingOrUnit(irType, expectedType)) {
            return delegatingCall;
        }
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(irBuilderWithScope.getContext(), irBuilderWithScope.getScope(), irBuilderWithScope.getStartOffset(), irBuilderWithScope.getEndOffset(), null, expectedType, true);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, delegatingCall, (String) null, false, (IrDeclarationOrigin) null, irType, 14, (Object) null);
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfThen$default(irBlockBuilder2, irBlockBuilder.getContext().getIrBuiltIns().getUnitType(), ExpressionHelpersKt.irEqeqeq(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default), ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, this.coroutineSymbols.getCoroutineSuspendedGetter())), ExpressionHelpersKt.irReturn(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default)), null, 8, null));
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irImplicitCast(irBlockBuilder2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default), expectedType));
        return irBlockBuilder.getIrBlockBody();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering
    protected void generateCoroutineStart(IrBlockBodyBuilder irBlockBodyBuilder, IrFunction invokeSuspendFunction, IrExpression receiver) {
        Intrinsics.checkNotNullParameter(irBlockBodyBuilder, "<this>");
        Intrinsics.checkNotNullParameter(invokeSuspendFunction, "invokeSuspendFunction");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBodyBuilder, receiver, (String) null, false, (IrDeclarationOrigin) null, receiver.getType(), 14, (Object) null);
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, this.coroutineImplResultSymbolSetter);
        IrVariable irVariable = irVariableCreateTmpVariable$default;
        irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariable));
        irFunctionAccessExpressionIrCall.putValueArgument(0, IrBuilderKt.irGetObject(irBlockBodyBuilder2, irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitClass()));
        irBlockBodyBuilder.unaryPlus(irFunctionAccessExpressionIrCall);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, this.coroutineImplExceptionPropertySetter);
        irFunctionAccessExpressionIrCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariable));
        irFunctionAccessExpressionIrCall2.putValueArgument(0, ExpressionHelpersKt.irNull(irBlockBodyBuilder2));
        irBlockBodyBuilder.unaryPlus(irFunctionAccessExpressionIrCall2);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall3 = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, invokeSuspendFunction.getSymbol());
        irFunctionAccessExpressionIrCall3.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariable));
        IrSymbol scopeOwnerSymbol = irBlockBodyBuilder.getScope().getScopeOwnerSymbol();
        if (!(scopeOwnerSymbol instanceof IrSimpleFunctionSymbol)) {
            scopeOwnerSymbol = null;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) scopeOwnerSymbol;
        if (irSimpleFunctionSymbol != null) {
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, generateDelegatedCall(irBlockBodyBuilder2, irSimpleFunctionSymbol.getOwner().getReturnType(), irFunctionAccessExpressionIrCall3)));
            return;
        }
        throw new AssertionError("Expected function symbol");
    }
}
