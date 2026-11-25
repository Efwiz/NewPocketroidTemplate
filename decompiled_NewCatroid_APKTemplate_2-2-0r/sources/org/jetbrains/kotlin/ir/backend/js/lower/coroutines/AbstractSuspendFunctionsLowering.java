package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KProperty;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CollectionUtilKt;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.UtilsKt;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.lower.CallableReferenceLowering;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.TransformKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¾\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0005TUVWXB\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J,\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\b2\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020,0*H$J\u0010\u0010-\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\bH$J\u0018\u0010.\u001a\u00020/2\u0006\u0010#\u001a\u00020$2\u0006\u00100\u001a\u000201H\u0002J\u001e\u00102\u001a\u00020&2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u0019042\u0006\u00105\u001a\u000206H\u0004J\u0018\u00107\u001a\u00020&2\u0006\u00108\u001a\u0002012\u0006\u00109\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\bH$J\u0018\u0010<\u001a\u00020&2\u0006\u0010=\u001a\u00020\b2\u0006\u0010>\u001a\u00020?H\u0002J\u001a\u0010@\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020$2\u0006\u00100\u001a\u000201H\u0002J\"\u0010A\u001a\u00020,*\u00020\"2\u0006\u0010B\u001a\u00020\u00132\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FJ*\u0010G\u001a\u00020H*\u00020I2\u0006\u0010#\u001a\u00020$2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020+0K2\u0006\u0010L\u001a\u00020\tH\u0002J\u001c\u0010M\u001a\u00020&*\u00020I2\u0006\u0010N\u001a\u00020\b2\u0006\u0010O\u001a\u00020HH$J\u001c\u0010P\u001a\u00020H*\u00020Q2\u0006\u0010R\u001a\u00020D2\u0006\u0010>\u001a\u00020HH\u0014J\f\u0010S\u001a\u00020F*\u00020?H\u0004R*\u0010\u0006\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00020\u0013X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R3\u0010\u001a\u001a\u0004\u0018\u00010\u0019*\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00198B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006Y"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering;", "C", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "builtCoroutines", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$BuiltCoroutine;", "Lkotlin/collections/HashMap;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "continuationClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getContinuationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "stateMachineMethodName", "Lorg/jetbrains/kotlin/name/Name;", "getStateMachineMethodName", "()Lorg/jetbrains/kotlin/name/Name;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "coroutineConstructor", "getCoroutineConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setCoroutineConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "coroutineConstructor$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "buildCoroutine", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "buildStateMachine", "", "stateMachineFunction", "transformingFunction", "argumentToPropertiesMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getCoroutineBaseClass", "getSuspendFunctionKind", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind;", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "initializeStateMachine", "coroutineConstructors", "", "coroutineClassThis", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "lower", "irBody", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "nameForCoroutineClass", "removeReturnIfSuspendedCallAndSimplifyDelegatingCall", "irFunction", "delegatingCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "transformSuspendFunction", "addField", "name", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "isMutable", "", "createCoroutineInstance", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "parameters", "", "coroutine", "generateCoroutineStart", "invokeSuspendFunction", "receiver", "generateDelegatedCall", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "expectedType", "isReturnIfSuspendedCall", "BuiltCoroutine", "CoroutineBuilder", "DECLARATION_ORIGIN_COROUTINE_IMPL", "SuspendFunctionKind", "VariablesScopeTracker", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractSuspendFunctionsLowering<C extends CommonBackendContext> implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(AbstractSuspendFunctionsLowering.class), "coroutineConstructor", "getCoroutineConstructor(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;"))};
    private final HashMap<IrFunction, BuiltCoroutine> builtCoroutines;
    private final C context;
    private final IrClassSymbol continuationClassSymbol;

    /* JADX INFO: renamed from: coroutineConstructor$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate coroutineConstructor;
    private final IrSimpleFunctionSymbol getContinuationSymbol;
    private final Symbols symbols;

    protected abstract void buildStateMachine(IrFunction stateMachineFunction, IrFunction transformingFunction, Map<IrValueParameter, ? extends IrField> argumentToPropertiesMap);

    protected abstract void generateCoroutineStart(IrBlockBodyBuilder irBlockBodyBuilder, IrFunction irFunction, IrExpression irExpression);

    protected IrExpression generateDelegatedCall(IrBuilderWithScope irBuilderWithScope, IrType expectedType, IrExpression delegatingCall) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(delegatingCall, "delegatingCall");
        return delegatingCall;
    }

    protected abstract IrClassSymbol getCoroutineBaseClass(IrFunction function);

    protected abstract Name getStateMachineMethodName();

    protected final void initializeStateMachine(List<? extends IrConstructor> coroutineConstructors, IrValueDeclaration coroutineClassThis) {
        Intrinsics.checkNotNullParameter(coroutineConstructors, "coroutineConstructors");
        Intrinsics.checkNotNullParameter(coroutineClassThis, "coroutineClassThis");
    }

    protected abstract Name nameForCoroutineClass(IrFunction function);

    public AbstractSuspendFunctionsLowering(C context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.coroutineConstructor = context.getMapping().getSuspendFunctionToCoroutineConstructor();
        this.builtCoroutines = new HashMap<>();
        Symbols symbols = context.getIr().getSymbols();
        this.symbols = symbols;
        IrSimpleFunctionSymbol getContinuation = symbols.getGetContinuation();
        this.getContinuationSymbol = getContinuation;
        IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(getContinuation.getOwner().getReturnType());
        Intrinsics.checkNotNull(classifierOrFail, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
        this.continuationClassSymbol = (IrClassSymbol) classifierOrFail;
    }

    public final C getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÄ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$DECLARATION_ORIGIN_COROUTINE_IMPL;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class DECLARATION_ORIGIN_COROUTINE_IMPL extends IrDeclarationOriginImpl {
        public static final DECLARATION_ORIGIN_COROUTINE_IMPL INSTANCE = new DECLARATION_ORIGIN_COROUTINE_IMPL();

        private DECLARATION_ORIGIN_COROUTINE_IMPL() {
            super("COROUTINE_IMPL", false, 2, null);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        IrClass irClassTransformSuspendFunction;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (container instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) container;
            if (!irSimpleFunction.getIsSuspend() || (irClassTransformSuspendFunction = transformSuspendFunction(irSimpleFunction, irBody)) == null) {
                return;
            }
            IrDeclarationParent parent = container.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
            IrUtilsKt.addChild((IrDeclarationContainer) parent, irClassTransformSuspendFunction);
        }
    }

    private static final boolean getSuspendFunctionKind$isSuspendLambda(IrSimpleFunction irSimpleFunction) {
        IrClass parentClassOrNull;
        return Intrinsics.areEqual(irSimpleFunction.getName().asString(), "invoke") && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irSimpleFunction)) != null && parentClassOrNull.getOrigin() == CallableReferenceLowering.Companion.LAMBDA_IMPL.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x006d  */
    private final SuspendFunctionKind getSuspendFunctionKind(IrSimpleFunction function, IrBody body) {
        IrElement irElement;
        IrCall irCall;
        if (getSuspendFunctionKind$isSuspendLambda(function)) {
            return SuspendFunctionKind.NEEDS_STATE_MACHINE.INSTANCE;
        }
        final Ref.IntRef intRef = new Ref.IntRef();
        IrVisitorsKt.acceptVoid(body, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.coroutines.AbstractSuspendFunctionsLowering.getSuspendFunctionKind.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
                visitBlock(irBlock, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(IrBlock irBlock, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
                visitBlockBody(irBlockBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                visitBody(irBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
                visitBranch(irBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(IrBranch irBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
                visitBreak(irBreak, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(IrBreak irBreak, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                visitBreakContinue(irBreakContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall2, Object obj) {
                visitCall(irCall2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall irCall2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall2, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
                visitCatch(irCatch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(IrCatch irCatch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                visitClass(irClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass irClass) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClass(IrClass irClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
                visitClassReference(irClassReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(IrClassReference irClassReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
                visitComposite(irComposite, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(IrComposite irComposite, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
                visitConst((IrConst<?>) irConst, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(IrConst<?> irConst, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                visitConstantArray(irConstantArray, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                visitConstantObject(irConstantObject, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                visitConstantValue(irConstantValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
                visitConstructor(irConstructor, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(IrConstructor irConstructor, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
                visitConstructorCall(irConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                visitContainerExpression(irContainerExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
                visitContinue(irContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(IrContinue irContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
                visitDeclaration(irDeclarationBase, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                visitDeclarationReference(irDeclarationReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                visitDynamicExpression(irDynamicExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement2, Object obj) {
                visitElement(irElement2, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement2, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement2, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                visitElseBranch(irElseBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
                visitEnumEntry(irEnumEntry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
                visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
                visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
                visitErrorExpression(irErrorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
                visitExpression(irExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(IrExpression irExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                visitExpressionBody(irExpressionBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                visitField(irField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                visitFieldAccess(irFieldAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                visitFile(irFile, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                visitFunction(irFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
                visitFunctionExpression(irFunctionExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
                visitFunctionReference(irFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
                visitGetClass(irGetClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(IrGetClass irGetClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
                visitGetEnumValue(irGetEnumValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
                visitGetField(irGetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(IrGetField irGetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                visitGetObjectValue(irGetObjectValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
                visitGetValue(irGetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(IrGetValue irGetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                visitLoop(irLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                visitModuleFragment(irModuleFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                visitPackageFragment(irPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                visitProperty(irProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
                visitPropertyReference(irPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                visitReturn(irReturn, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                visitScript(irScript, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
                visitSetField(irSetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(IrSetField irSetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
                visitSetValue(irSetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(IrSetValue irSetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
                visitSimpleFunction(irSimpleFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                visitSingletonReference(irGetSingletonValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
                visitSpreadElement(irSpreadElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                visitStringConcatenation(irStringConcatenation, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
                visitSyntheticBody(irSyntheticBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
                visitThrow(irThrow, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(IrThrow irThrow, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
                visitTry(irTry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(IrTry irTry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
                visitTypeAlias(irTypeAlias, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                visitTypeOperator(irTypeOperatorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
                visitTypeParameter(irTypeParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                visitValueAccess(irValueAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
                visitValueParameter(irValueParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
                visitVararg(irVararg, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(IrVararg irVararg, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
                visitVariable(irVariable, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(IrVariable irVariable, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
                visitWhen(irWhen, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(IrWhen irWhen, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                visitWhileLoop(irWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                IrVisitorsKt.acceptChildrenVoid(element, this);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrVisitorsKt.acceptChildrenVoid(expression, this);
                if (IrUtilsKt.isSuspend(expression)) {
                    intRef.element++;
                    int i = intRef.element;
                }
            }
        });
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        Object value = (IrStatement) CollectionsKt.lastOrNull((List) ((IrBlockBody) body).getStatements());
        if (value instanceof IrCall) {
            irCall = (IrCall) value;
        } else if (value instanceof IrReturn) {
            while (true) {
                irElement = (IrElement) value;
                if (irElement instanceof IrBlock) {
                    IrBlock irBlock = (IrBlock) irElement;
                    if (irBlock.getStatements().size() == 1) {
                        value = CollectionsKt.first((List<? extends Object>) irBlock.getStatements());
                    }
                }
                if (!(irElement instanceof IrReturn)) {
                    break;
                }
                value = ((IrReturn) irElement).getValue();
            }
            if (irElement instanceof IrCall) {
                irCall = (IrCall) irElement;
            } else {
                irCall = null;
            }
        } else {
            irCall = null;
        }
        boolean z = irCall != null && IrUtilsKt.isSuspend(irCall);
        if (intRef.element == 0) {
            return SuspendFunctionKind.NO_SUSPEND_CALLS.INSTANCE;
        }
        if (intRef.element == 1 && z) {
            Intrinsics.checkNotNull(irCall);
            return new SuspendFunctionKind.DELEGATING(irCall);
        }
        return SuspendFunctionKind.NEEDS_STATE_MACHINE.INSTANCE;
    }

    private final IrClass transformSuspendFunction(IrSimpleFunction function, IrBody body) {
        function.getIsSuspend();
        SuspendFunctionKind suspendFunctionKind = getSuspendFunctionKind(function, body);
        if (suspendFunctionKind instanceof SuspendFunctionKind.NO_SUSPEND_CALLS) {
            return null;
        }
        if (suspendFunctionKind instanceof SuspendFunctionKind.DELEGATING) {
            removeReturnIfSuspendedCallAndSimplifyDelegatingCall(function, ((SuspendFunctionKind.DELEGATING) suspendFunctionKind).getDelegatingCall());
            return null;
        }
        if (suspendFunctionKind instanceof SuspendFunctionKind.NEEDS_STATE_MACHINE) {
            IrClass irClassBuildCoroutine = buildCoroutine(function);
            if (irClassBuildCoroutine == function.getParent()) {
                return null;
            }
            return irClassBuildCoroutine;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final IrExpression createCoroutineInstance(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction, Collection<? extends IrValueParameter> collection, BuiltCoroutine builtCoroutine) {
        IrConstructor coroutineConstructor = builtCoroutine.getCoroutineConstructor();
        List<IrTypeParameter> typeParameters = irSimpleFunction.getTypeParameters();
        ArrayList arrayList = new ArrayList(typeParameters.size());
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new IrSimpleTypeImpl((IrClassifierSymbol) ((IrTypeParameter) it.next()).getSymbol(), true, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
        }
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(irBlockBodyBuilder2, coroutineConstructor.getSymbol(), org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        int i = 0;
        for (Object obj : collection) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irConstructorCallIrCallConstructor.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueParameter) obj));
            i = i2;
        }
        int size = collection.size();
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.getContinuationSymbol;
        irConstructorCallIrCallConstructor.putValueArgument(size, ExpressionHelpersKt.irCall(irBlockBodyBuilder2, irSimpleFunctionSymbol, irSimpleFunctionSymbol.getOwner().getReturnType(), (List<? extends IrType>) CollectionsKt.listOf(irSimpleFunction.getReturnType())));
        return irConstructorCallIrCallConstructor;
    }

    private final IrClass buildCoroutine(IrSimpleFunction function) {
        BuiltCoroutine builtCoroutineBuild = new CoroutineBuilder(this, function).build();
        if (builtCoroutineBuild.getCoroutineClass() == function.getParent()) {
            return builtCoroutineBuild.getCoroutineClass();
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, function.getSymbol(), -1, -1);
        IrBody body = function.getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockBody irBlockBody = (IrBlockBody) body;
        irBlockBody.getStatements().clear();
        List<IrStatement> statements = irBlockBody.getStatements();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), declarationIrBuilder.getStartOffset(), declarationIrBuilder.getEndOffset());
        generateCoroutineStart(irBlockBodyBuilder, builtCoroutineBuild.getStateMachineFunction(), createCoroutineInstance(irBlockBodyBuilder, function, IrUtilsKt.getExplicitParameters(function), builtCoroutineBuild));
        statements.addAll(irBlockBodyBuilder.getIrBlockBody().getStatements());
        return builtCoroutineBuild.getCoroutineClass();
    }

    /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u001b\u001a\u00020\u001cJ\b\u0010\u001d\u001a\u00020\u000eH\u0002J\u001a\u0010\u001e\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u00032\u0006\u0010 \u001a\u00020\u000eH\u0002J\u0010\u0010!\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u0003H\u0002J\u0010\u0010#\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010$\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0018\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\u0003H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$CoroutineBuilder;", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "argumentToPropertiesMap", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "continuationType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "coroutineBaseClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "coroutineBaseClassConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "coroutineClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "coroutineClassThis", "create1CompletionParameter", "create1Function", "endOffset", "", "functionParameters", "", "isSuspendLambda", "", "startOffset", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$BuiltCoroutine;", "buildConstructor", "buildCreateMethod", "superCreateFunction", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "buildInvokeSuspendMethod", "stateMachineFunction", "buildNewCoroutineClass", "getCoroutineClass", "transformInvokeMethod", "", "createFunction", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class CoroutineBuilder {
        private final Map<IrValueParameter, IrField> argumentToPropertiesMap;
        private final IrSimpleType continuationType;
        private final IrClassSymbol coroutineBaseClass;
        private final IrConstructor coroutineBaseClassConstructor;
        private final IrClass coroutineClass;
        private final IrValueParameter coroutineClassThis;
        private final IrValueParameter create1CompletionParameter;
        private final IrSimpleFunction create1Function;
        private final int endOffset;
        private final IrSimpleFunction function;
        private final List<IrValueParameter> functionParameters;
        private final boolean isSuspendLambda;
        private final int startOffset;
        final /* synthetic */ AbstractSuspendFunctionsLowering<C> this$0;

        public CoroutineBuilder(AbstractSuspendFunctionsLowering abstractSuspendFunctionsLowering, IrSimpleFunction function) {
            IrClass parentClassOrNull;
            Intrinsics.checkNotNullParameter(function, "function");
            this.this$0 = abstractSuspendFunctionsLowering;
            this.function = function;
            this.startOffset = function.getStartOffset();
            this.endOffset = function.getEndOffset();
            boolean z = function.getIsOperator() && Intrinsics.areEqual(function.getName().asString(), "invoke") && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(function)) != null && parentClassOrNull.getOrigin() == CallableReferenceLowering.Companion.LAMBDA_IMPL.INSTANCE;
            this.isSuspendLambda = z;
            List<IrValueParameter> valueParameters = z ? function.getValueParameters() : IrUtilsKt.getExplicitParameters(function);
            this.functionParameters = valueParameters;
            IrClass coroutineClass = getCoroutineClass(function);
            this.coroutineClass = coroutineClass;
            IrValueParameter thisReceiver = coroutineClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            this.coroutineClassThis = thisReceiver;
            this.continuationType = IrTypesKt.typeWith(abstractSuspendFunctionsLowering.continuationClassSymbol, function.getReturnType());
            List<IrValueParameter> list = valueParameters;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
            for (Object obj : list) {
                IrValueParameter irValueParameter = (IrValueParameter) obj;
                linkedHashMap.put(obj, abstractSuspendFunctionsLowering.addField(this.coroutineClass, irValueParameter.getName(), irValueParameter.getType(), false));
            }
            this.argumentToPropertiesMap = linkedHashMap;
            IrClassSymbol coroutineBaseClass = this.this$0.getCoroutineBaseClass(this.function);
            this.coroutineBaseClass = coroutineBaseClass;
            Object obj2 = null;
            IrConstructor irConstructor = null;
            boolean z2 = false;
            for (IrConstructor irConstructor2 : IrUtilsKt.getConstructors(coroutineBaseClass.getOwner())) {
                if (irConstructor2.getValueParameters().size() == 1) {
                    if (z2) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    z2 = true;
                    irConstructor = irConstructor2;
                }
            }
            if (z2) {
                this.coroutineBaseClassConstructor = irConstructor;
                boolean z3 = false;
                for (Object obj3 : IrUtilsKt.simpleFunctions(this.coroutineBaseClass.getOwner())) {
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj3;
                    if (Intrinsics.areEqual(irSimpleFunction.getName().asString(), CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME) && irSimpleFunction.getValueParameters().size() == 1) {
                        if (z3) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z3 = true;
                        obj2 = obj3;
                    }
                }
                if (!z3) {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) obj2;
                this.create1Function = irSimpleFunction2;
                this.create1CompletionParameter = irSimpleFunction2.getValueParameters().get(0);
                return;
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }

        private final IrClass getCoroutineClass(IrSimpleFunction function) {
            return this.isSuspendLambda ? IrUtilsKt.getParentAsClass(function) : buildNewCoroutineClass(function);
        }

        private final IrClass buildNewCoroutineClass(IrSimpleFunction function) {
            IrFactory irFactory = this.this$0.getContext().getIrFactory();
            AbstractSuspendFunctionsLowering<C> abstractSuspendFunctionsLowering = this.this$0;
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            irClassBuilder.setOrigin(DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE);
            irClassBuilder.setName(abstractSuspendFunctionsLowering.nameForCoroutineClass(function));
            irClassBuilder.setVisibility(function.getVisibility());
            IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            irClassBuildClass.setParent(function.getParent());
            IrUtilsKt.createParameterDeclarations(irClassBuildClass);
            List<IrTypeParameter> typeParameters = function.getTypeParameters();
            ArrayList arrayList = new ArrayList(typeParameters.size());
            for (IrTypeParameter irTypeParameter : typeParameters) {
                IrTypeParameter irTypeParameterCopyToWithoutSuperTypes$default = IrUtilsKt.copyToWithoutSuperTypes$default(irTypeParameter, irClassBuildClass, 0, null, 6, null);
                irTypeParameterCopyToWithoutSuperTypes$default.setSuperTypes(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irTypeParameterCopyToWithoutSuperTypes$default.getSuperTypes(), (List) irTypeParameter.getSuperTypes()));
                arrayList.add(irTypeParameterCopyToWithoutSuperTypes$default);
            }
            irClassBuildClass.setTypeParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            return irClassBuildClass;
        }

        private final IrConstructor buildConstructor() {
            if (this.isSuspendLambda) {
                List<IrDeclaration> declarations = this.coroutineClass.getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrConstructor) {
                        arrayList.add(obj);
                    }
                }
                IrConstructor irConstructor = (IrConstructor) CollectionsKt.single((List) arrayList);
                IrConstructor irConstructor2 = this.this$0.getContext().getMapping().getCapturedConstructors().get(irConstructor);
                return irConstructor2 == null ? irConstructor : irConstructor2;
            }
            IrFactory irFactory = this.this$0.getContext().getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setOrigin(DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE);
            irFunctionBuilder.setName(this.coroutineBaseClassConstructor.getName());
            irFunctionBuilder.setVisibility(this.function.getVisibility());
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(this.coroutineClass));
            irFunctionBuilder.setPrimary(true);
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
            AbstractSuspendFunctionsLowering<C> abstractSuspendFunctionsLowering = this.this$0;
            irConstructorBuildConstructor.setParent(this.coroutineClass);
            IrUtilsKt.addChild(this.coroutineClass, irConstructorBuildConstructor);
            List<IrValueParameter> list = this.functionParameters;
            ArrayList arrayList2 = new ArrayList(list.size());
            int i = 0;
            int i2 = 0;
            for (Object obj2 : list) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) obj2, irConstructorBuildConstructor, DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE, i2, 0, 0, null, null, null, null, null, false, false, false, 7672, null));
                i2 = i3;
            }
            irConstructorBuildConstructor.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
            irConstructorBuildConstructor.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irConstructorBuildConstructor.getValueParameters(), IrUtilsKt.copyTo$default(this.coroutineBaseClassConstructor.getValueParameters().get(0), irConstructorBuildConstructor, DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE, irConstructorBuildConstructor.getValueParameters().size(), this.function.getStartOffset(), this.function.getEndOffset(), null, null, this.continuationType, null, null, false, false, false, 7520, null)));
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(abstractSuspendFunctionsLowering.getContext(), irConstructorBuildConstructor.getSymbol(), irConstructorBuildConstructor.getStartOffset(), irConstructorBuildConstructor.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset());
            IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.last((List) irConstructorBuildConstructor.getValueParameters());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrDelegatingConstructorCall irDelegatingConstructorCall = ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder2, this.coroutineBaseClassConstructor);
            irDelegatingConstructorCall.putValueArgument(0, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter));
            irBlockBodyBuilder.unaryPlus(irDelegatingConstructorCall);
            irBlockBodyBuilder.unaryPlus(new IrInstanceInitializerCallImpl(irBlockBodyBuilder.getStartOffset(), irBlockBodyBuilder.getEndOffset(), this.coroutineClass.getSymbol(), irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType()));
            for (Object obj3 : this.functionParameters) {
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, this.coroutineClassThis), (IrField) MapsKt.getValue(this.argumentToPropertiesMap, (IrValueParameter) obj3), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irConstructorBuildConstructor.getValueParameters().get(i)), null, 8, null));
                i = i4;
            }
            irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
            return irConstructorBuildConstructor;
        }

        private final IrSimpleFunction buildInvokeSuspendMethod(IrSimpleFunction stateMachineFunction) {
            IrFactory irFactory = this.this$0.getContext().getIrFactory();
            AbstractSuspendFunctionsLowering<C> abstractSuspendFunctionsLowering = this.this$0;
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setStartOffset(this.function.getStartOffset());
            irFunctionBuilder.setEndOffset(this.function.getEndOffset());
            irFunctionBuilder.setOrigin(DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE);
            irFunctionBuilder.setName(stateMachineFunction.getName());
            irFunctionBuilder.setVisibility(stateMachineFunction.getVisibility());
            irFunctionBuilder.setModality(Modality.FINAL);
            irFunctionBuilder.setReturnType(abstractSuspendFunctionsLowering.getContext().getIrBuiltIns().getAnyNType());
            irFunctionBuilder.setInline(stateMachineFunction.getIsInline());
            irFunctionBuilder.setExternal(stateMachineFunction.getIsExternal());
            irFunctionBuilder.setTailrec(stateMachineFunction.getIsTailrec());
            irFunctionBuilder.setSuspend(stateMachineFunction.getIsSuspend());
            irFunctionBuilder.setOperator(false);
            irFunctionBuilder.setExpect(stateMachineFunction.getIsExpect());
            irFunctionBuilder.setFakeOverride(false);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            irSimpleFunctionBuildFunction.setParent(this.coroutineClass);
            IrUtilsKt.addChild(this.coroutineClass, irSimpleFunctionBuildFunction);
            List<IrTypeParameter> typeParameters = stateMachineFunction.getTypeParameters();
            ArrayList arrayList = new ArrayList(typeParameters.size());
            for (IrTypeParameter irTypeParameter : typeParameters) {
                IrTypeParameter irTypeParameterCopyToWithoutSuperTypes$default = IrUtilsKt.copyToWithoutSuperTypes$default(irTypeParameter, irSimpleFunctionBuildFunction, 0, DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE, 2, null);
                irTypeParameterCopyToWithoutSuperTypes$default.setSuperTypes(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irTypeParameterCopyToWithoutSuperTypes$default.getSuperTypes(), (List) irTypeParameter.getSuperTypes()));
                arrayList.add(irTypeParameterCopyToWithoutSuperTypes$default);
            }
            irSimpleFunctionBuildFunction.setTypeParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            List<IrValueParameter> valueParameters = stateMachineFunction.getValueParameters();
            ArrayList arrayList2 = new ArrayList(valueParameters.size());
            int i = 0;
            for (Object obj : valueParameters) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) obj, irSimpleFunctionBuildFunction, DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE, i, 0, 0, null, null, null, null, null, false, false, false, 8184, null));
                i = i2;
            }
            irSimpleFunctionBuildFunction.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2));
            IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
            IrUtilsKt.createDispatchReceiverParameter$default(irSimpleFunction, null, 1, null);
            irSimpleFunctionBuildFunction.setOverriddenSymbols(CollectionsKt.listOf(stateMachineFunction.getSymbol()));
            this.this$0.buildStateMachine(irSimpleFunction, this.function, this.argumentToPropertiesMap);
            return irSimpleFunctionBuildFunction;
        }

        private final IrSimpleFunction buildCreateMethod(IrSimpleFunction superCreateFunction, IrConstructor constructor) {
            IrFactory irFactory = this.this$0.getContext().getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setStartOffset(-1);
            irFunctionBuilder.setEndOffset(-1);
            irFunctionBuilder.setOrigin(DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE);
            Name nameIdentifier = Name.identifier(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
            Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
            irFunctionBuilder.setVisibility(PROTECTED);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(this.coroutineClass));
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            AbstractSuspendFunctionsLowering<C> abstractSuspendFunctionsLowering = this.this$0;
            irSimpleFunctionBuildFunction.setParent(this.coroutineClass);
            IrUtilsKt.addChild(this.coroutineClass, irSimpleFunctionBuildFunction);
            List<IrTypeParameter> typeParameters = this.function.getTypeParameters();
            ArrayList arrayList = new ArrayList(typeParameters.size());
            for (IrTypeParameter irTypeParameter : typeParameters) {
                IrTypeParameter irTypeParameterCopyToWithoutSuperTypes$default = IrUtilsKt.copyToWithoutSuperTypes$default(irTypeParameter, irSimpleFunctionBuildFunction, 0, DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE, 2, null);
                irTypeParameterCopyToWithoutSuperTypes$default.setSuperTypes(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irTypeParameterCopyToWithoutSuperTypes$default.getSuperTypes(), (List) irTypeParameter.getSuperTypes()));
                arrayList.add(irTypeParameterCopyToWithoutSuperTypes$default);
            }
            irSimpleFunctionBuildFunction.setTypeParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
            List listPlus = CollectionsKt.plus((Collection<? extends IrValueParameter>) this.function.getValueParameters(), this.create1CompletionParameter);
            ArrayList arrayList2 = new ArrayList(listPlus.size());
            int i = 0;
            int i2 = 0;
            for (Object obj : listPlus) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                arrayList2.add(IrUtilsKt.copyTo$default((IrValueParameter) obj, irSimpleFunctionBuildFunction, DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE, i2, 0, 0, null, null, null, null, null, false, false, false, 8184, null));
                i2 = i3;
            }
            List<? extends IrValueParameter> listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2);
            irSimpleFunctionBuildFunction.setValueParameters(listCompactIfPossible);
            IrUtilsKt.createDispatchReceiverParameter$default(irSimpleFunctionBuildFunction, null, 1, null);
            if (superCreateFunction != null) {
                ArrayList arrayList3 = new ArrayList(superCreateFunction.getOverriddenSymbols().size() + 1);
                arrayList3.addAll(superCreateFunction.getOverriddenSymbols());
                arrayList3.add(superCreateFunction.getSymbol());
                irSimpleFunctionBuildFunction.setOverriddenSymbols(arrayList3);
            }
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            Collection<IrField> collection = abstractSuspendFunctionsLowering.getContext().getMapping().getCapturedFields().get(this.coroutineClass);
            if (collection == null) {
                CompilationExceptionKt.compilationException("No captured values", (IrDeclaration) this.coroutineClass);
                throw null;
            }
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(abstractSuspendFunctionsLowering.getContext(), irSimpleFunctionBuildFunction.getSymbol(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, constructor);
            Iterator<IrField> it = collection.iterator();
            while (it.hasNext()) {
                irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter), it.next(), null, 4, null));
                i++;
            }
            irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueDeclaration) CollectionsKt.last((List) listCompactIfPossible)));
            constructor.getValueParameters().size();
            IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(irBlockBodyBuilder.getScope(), (IrExpression) irFunctionAccessExpressionIrCall, ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, false, (IrDeclarationOrigin) null, (IrType) null, 28, (Object) null);
            irBlockBodyBuilder.unaryPlus(irVariableCreateTmpVariable$default);
            listCompactIfPossible.size();
            this.argumentToPropertiesMap.size();
            for (Pair pair : CollectionsKt.zip(listCompactIfPossible, this.argumentToPropertiesMap.values())) {
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariableCreateTmpVariable$default), (IrField) pair.component2(), ExpressionHelpersKt.irGet(irBlockBodyBuilder2, (IrValueParameter) pair.component1()), null, 8, null));
            }
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irVariableCreateTmpVariable$default)));
            irSimpleFunctionBuildFunction.setBody(irBlockBodyBuilder.getIrBlockBody());
            return irSimpleFunctionBuildFunction;
        }

        private final void transformInvokeMethod(IrSimpleFunction createFunction, IrSimpleFunction stateMachineFunction) {
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.getContext(), this.function.getSymbol(), -1, -1);
            IrValueParameter dispatchReceiverParameter = this.function.getDispatchReceiverParameter();
            if (dispatchReceiverParameter == null) {
                CompilationExceptionKt.compilationException("Expected dispatch receiver for invoke", (IrDeclaration) this.function);
                throw null;
            }
            IrBody body = this.function.getBody();
            Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
            IrBlockBody irBlockBody = (IrBlockBody) body;
            irBlockBody.getStatements().clear();
            List<IrStatement> statements = irBlockBody.getStatements();
            DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
            AbstractSuspendFunctionsLowering<C> abstractSuspendFunctionsLowering = this.this$0;
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilder.getContext(), declarationIrBuilder.getScope(), -1, -1);
            IrSimpleFunction irSimpleFunction = stateMachineFunction;
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, createFunction);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter));
            Iterator<IrValueParameter> it = this.function.getValueParameters().iterator();
            int i = 0;
            while (it.hasNext()) {
                irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, it.next()));
                i++;
            }
            irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irCall(irBlockBodyBuilder2, ((AbstractSuspendFunctionsLowering) abstractSuspendFunctionsLowering).getContinuationSymbol, ((AbstractSuspendFunctionsLowering) abstractSuspendFunctionsLowering).getContinuationSymbol.getOwner().getReturnType(), (List<? extends IrType>) CollectionsKt.listOf(this.function.getReturnType())));
            createFunction.getValueParameters().size();
            Unit unit = Unit.INSTANCE;
            abstractSuspendFunctionsLowering.generateCoroutineStart(irBlockBodyBuilder, irSimpleFunction, irFunctionAccessExpressionIrCall);
            statements.addAll(irBlockBodyBuilder.getIrBlockBody().getStatements());
        }

        /* JADX WARN: Code duplicated, block: B:32:0x011e  */
        public final BuiltCoroutine build() {
            List listListOfNotNull;
            IrConstructor irConstructorBuildConstructor = buildConstructor();
            ArrayList arrayList = new ArrayList(2);
            List<IrSimpleFunction> listSimpleFunctions = IrUtilsKt.simpleFunctions(this.coroutineBaseClass.getOwner());
            AbstractSuspendFunctionsLowering<C> abstractSuspendFunctionsLowering = this.this$0;
            Object obj = null;
            boolean z = false;
            for (Object obj2 : listSimpleFunctions) {
                if (Intrinsics.areEqual(((IrSimpleFunction) obj2).getName(), abstractSuspendFunctionsLowering.getStateMachineMethodName())) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    obj = obj2;
                    z = true;
                }
            }
            if (z) {
                IrSimpleFunction irSimpleFunctionBuildInvokeSuspendMethod = buildInvokeSuspendMethod((IrSimpleFunction) obj);
                arrayList.add(irSimpleFunctionBuildInvokeSuspendMethod);
                if (this.isSuspendLambda) {
                    List<IrSimpleFunction> listSimpleFunctions2 = IrUtilsKt.simpleFunctions(this.coroutineBaseClass.getOwner());
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj3 : listSimpleFunctions2) {
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj3;
                        if (Intrinsics.areEqual(irSimpleFunction.getName().asString(), CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME) && irSimpleFunction.getValueParameters().size() == this.function.getValueParameters().size() + 1) {
                            arrayList2.add(obj3);
                        }
                    }
                    IrSimpleFunction irSimpleFunctionBuildCreateMethod = buildCreateMethod((IrSimpleFunction) CollectionUtilKt.atMostOne(arrayList2), irConstructorBuildConstructor);
                    arrayList.add(irSimpleFunctionBuildCreateMethod);
                    transformInvokeMethod(irSimpleFunctionBuildCreateMethod, irSimpleFunctionBuildInvokeSuspendMethod);
                } else {
                    IrClass irClass = this.coroutineClass;
                    irClass.setSuperTypes(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irClass.getSuperTypes(), IrTypesKt.getDefaultType(this.coroutineBaseClass)));
                }
                IrUtilsKt.addFakeOverrides$default(this.coroutineClass, this.this$0.getContext().getTypeSystem(), arrayList, null, 4, null);
                List<IrDeclaration> declarations = this.coroutineClass.getDeclarations();
                int iReplaceInPlace = 0;
                while (iReplaceInPlace < declarations.size()) {
                    IrDeclaration irDeclaration = declarations.get(iReplaceInPlace);
                    if (irDeclaration instanceof IrProperty) {
                        IrProperty irProperty = (IrProperty) irDeclaration;
                        if (irProperty.getIsFakeOverride()) {
                            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new IrSimpleFunction[]{irProperty.getGetter(), irProperty.getSetter()});
                        } else {
                            listListOfNotNull = null;
                        }
                    } else {
                        listListOfNotNull = null;
                    }
                    iReplaceInPlace = TransformKt.replaceInPlace(declarations, listListOfNotNull, iReplaceInPlace);
                }
                this.this$0.initializeStateMachine(CollectionsKt.listOf(irConstructorBuildConstructor), this.coroutineClassThis);
                return new BuiltCoroutine(this.coroutineClass, irConstructorBuildConstructor, irSimpleFunctionBuildInvokeSuspendMethod);
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
    }

    protected final boolean isReturnIfSuspendedCall(IrCall irCall) {
        Intrinsics.checkNotNullParameter(irCall, "<this>");
        return Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(irCall.getSymbol().getOwner()), this.context.getInternalPackageFqn().child(Name.identifier("returnIfSuspended")));
    }

    /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind;", "", "()V", "DELEGATING", "NEEDS_STATE_MACHINE", "NO_SUSPEND_CALLS", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind$DELEGATING;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind$NEEDS_STATE_MACHINE;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind$NO_SUSPEND_CALLS;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class SuspendFunctionKind {
        public /* synthetic */ SuspendFunctionKind(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind$NO_SUSPEND_CALLS;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NO_SUSPEND_CALLS extends SuspendFunctionKind {
            public static final NO_SUSPEND_CALLS INSTANCE = new NO_SUSPEND_CALLS();

            private NO_SUSPEND_CALLS() {
                super(null);
            }
        }

        private SuspendFunctionKind() {
        }

        /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind$DELEGATING;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind;", "delegatingCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)V", "getDelegatingCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DELEGATING extends SuspendFunctionKind {
            private final IrCall delegatingCall;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public DELEGATING(IrCall delegatingCall) {
                super(null);
                Intrinsics.checkNotNullParameter(delegatingCall, "delegatingCall");
                this.delegatingCall = delegatingCall;
            }

            public final IrCall getDelegatingCall() {
                return this.delegatingCall;
            }
        }

        /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind$NEEDS_STATE_MACHINE;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$SuspendFunctionKind;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NEEDS_STATE_MACHINE extends SuspendFunctionKind {
            public static final NEEDS_STATE_MACHINE INSTANCE = new NEEDS_STATE_MACHINE();

            private NEEDS_STATE_MACHINE() {
                super(null);
            }
        }
    }

    private final void removeReturnIfSuspendedCallAndSimplifyDelegatingCall(IrFunction irFunction, IrCall delegatingCall) {
        IrCall valueArgument;
        if (isReturnIfSuspendedCall(delegatingCall)) {
            valueArgument = delegatingCall.getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument);
        } else {
            valueArgument = delegatingCall;
        }
        IrBody body = irFunction.getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        IrBlockBody irBlockBody = (IrBlockBody) body;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, irFunction.getSymbol(), IrUtilsKt.getPreviousOffset(irBlockBody.getEndOffset()), IrUtilsKt.getPreviousOffset(irBlockBody.getEndOffset()));
        List<IrStatement> statements = irBlockBody.getStatements();
        IrStatement irStatement = (IrStatement) CollectionsKt.last((List) statements);
        if (!Intrinsics.areEqual(irStatement, delegatingCall)) {
            boolean z = irStatement instanceof IrReturn;
        }
        Scope scope = declarationIrBuilderCreateIrBuilder.getScope();
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
        IrExpression irExpressionGenerateDelegatedCall = generateDelegatedCall(declarationIrBuilder, irFunction.getReturnType(), valueArgument);
        IrVariable irVariableCreateTemporaryVariable = scope.createTemporaryVariable(irExpressionGenerateDelegatedCall, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getAnyType() : null, (108 & 32) != 0 ? irExpressionGenerateDelegatedCall.getStartOffset() : 0, (108 & 64) != 0 ? irExpressionGenerateDelegatedCall.getEndOffset() : 0);
        statements.set(CollectionsKt.getLastIndex(statements), irVariableCreateTemporaryVariable);
        statements.add(ExpressionHelpersKt.irReturn(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, irVariableCreateTemporaryVariable)));
    }

    /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$BuiltCoroutine;", "", "coroutineClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "coroutineConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "stateMachineFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "getCoroutineClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getCoroutineConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getStateMachineFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class BuiltCoroutine {
        private final IrClass coroutineClass;
        private final IrConstructor coroutineConstructor;
        private final IrFunction stateMachineFunction;

        public BuiltCoroutine(IrClass coroutineClass, IrConstructor coroutineConstructor, IrFunction stateMachineFunction) {
            Intrinsics.checkNotNullParameter(coroutineClass, "coroutineClass");
            Intrinsics.checkNotNullParameter(coroutineConstructor, "coroutineConstructor");
            Intrinsics.checkNotNullParameter(stateMachineFunction, "stateMachineFunction");
            this.coroutineClass = coroutineClass;
            this.coroutineConstructor = coroutineConstructor;
            this.stateMachineFunction = stateMachineFunction;
        }

        public final IrClass getCoroutineClass() {
            return this.coroutineClass;
        }

        public final IrConstructor getCoroutineConstructor() {
            return this.coroutineConstructor;
        }

        public final IrFunction getStateMachineFunction() {
            return this.stateMachineFunction;
        }
    }

    /* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0014\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0006H\u0016R \u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AbstractSuspendFunctionsLowering$VariablesScopeTracker;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitorVoid;", "()V", "scopeStack", "", "", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getScopeStack", "()Ljava/util/List;", "visitCatch", "", "aCatch", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "visitContainerExpression", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitVariable", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static class VariablesScopeTracker implements IrElementVisitorVoid {
        private final List<Set<IrVariable>> scopeStack = CollectionsKt.mutableListOf(new LinkedHashSet());

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitAnonymousInitializer */
        public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
            visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
            IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlock */
        public /* bridge */ /* synthetic */ Object visitBlock2(IrBlock irBlock, Object obj) {
            visitBlock(irBlock, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlock(IrBlock irBlock) {
            IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlock(IrBlock irBlock, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBlockBody */
        public /* bridge */ /* synthetic */ Object visitBlockBody2(IrBlockBody irBlockBody, Object obj) {
            visitBlockBody(irBlockBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlockBody(IrBlockBody irBlockBody) {
            IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBlockBody(IrBlockBody irBlockBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBody */
        public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
            visitBody(irBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBody(IrBody irBody) {
            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBody(IrBody irBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBranch */
        public /* bridge */ /* synthetic */ Object visitBranch2(IrBranch irBranch, Object obj) {
            visitBranch(irBranch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBranch(IrBranch irBranch) {
            IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBranch(IrBranch irBranch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreak */
        public /* bridge */ /* synthetic */ Object visitBreak2(IrBreak irBreak, Object obj) {
            visitBreak(irBreak, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreak(IrBreak irBreak) {
            IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreak(IrBreak irBreak, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitBreakContinue */
        public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
            visitBreakContinue(irBreakContinue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreakContinue(IrBreakContinue irBreakContinue) {
            IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCall */
        public /* bridge */ /* synthetic */ Object visitCall2(IrCall irCall, Object obj) {
            visitCall(irCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCall(IrCall irCall) {
            IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCall(IrCall irCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
            visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCallableReference(IrCallableReference<?> irCallableReference) {
            IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitCatch */
        public /* bridge */ /* synthetic */ Object visitCatch2(IrCatch irCatch, Object obj) {
            visitCatch(irCatch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCatch(IrCatch irCatch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClass */
        public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
            visitClass(irClass, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitClass */
        public void mo6112visitClass(IrClass irClass) {
            IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClass(IrClass irClass, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitClassReference */
        public /* bridge */ /* synthetic */ Object visitClassReference2(IrClassReference irClassReference, Object obj) {
            visitClassReference(irClassReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClassReference(IrClassReference irClassReference) {
            IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitClassReference(IrClassReference irClassReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitComposite */
        public /* bridge */ /* synthetic */ Object visitComposite2(IrComposite irComposite, Object obj) {
            visitComposite(irComposite, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitComposite(IrComposite irComposite) {
            IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitComposite(IrComposite irComposite, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
            visitConst((IrConst<?>) irConst, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConst(IrConst<?> irConst) {
            IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConst(IrConst<?> irConst, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantArray */
        public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
            visitConstantArray(irConstantArray, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantArray(IrConstantArray irConstantArray) {
            IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantObject */
        public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
            visitConstantObject(irConstantObject, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantObject(IrConstantObject irConstantObject) {
            IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantPrimitive */
        public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
            visitConstantPrimitive(irConstantPrimitive, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
            IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstantValue */
        public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
            visitConstantValue(irConstantValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantValue(IrConstantValue irConstantValue) {
            IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructor */
        public /* bridge */ /* synthetic */ Object visitConstructor2(IrConstructor irConstructor, Object obj) {
            visitConstructor(irConstructor, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor irConstructor) {
            IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructor(IrConstructor irConstructor, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitConstructorCall */
        public /* bridge */ /* synthetic */ Object visitConstructorCall2(IrConstructorCall irConstructorCall, Object obj) {
            visitConstructorCall(irConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall irConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitConstructorCall(IrConstructorCall irConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContainerExpression */
        public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
            visitContainerExpression(irContainerExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitContinue */
        public /* bridge */ /* synthetic */ Object visitContinue2(IrContinue irContinue, Object obj) {
            visitContinue(irContinue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContinue(IrContinue irContinue) {
            IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContinue(IrContinue irContinue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclaration */
        public /* bridge */ /* synthetic */ Object visitDeclaration2(IrDeclarationBase irDeclarationBase, Object obj) {
            visitDeclaration(irDeclarationBase, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclaration(IrDeclarationBase irDeclarationBase) {
            IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclaration(IrDeclarationBase irDeclarationBase, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDeclarationReference */
        public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
            visitDeclarationReference(irDeclarationReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
            IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDelegatingConstructorCall */
        public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
            visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDoWhileLoop */
        public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
            visitDoWhileLoop(irDoWhileLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
            IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
            visitDynamicExpression(irDynamicExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicMemberExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
            visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicMemberExpression(IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitDynamicOperatorExpression */
        public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
            visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitDynamicOperatorExpression(IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElement */
        public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
            visitElement(irElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElement(IrElement irElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitElseBranch */
        public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
            visitElseBranch(irElseBranch, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElseBranch(IrElseBranch irElseBranch) {
            IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumConstructorCall */
        public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, Object obj) {
            visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
            IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitEnumEntry */
        public /* bridge */ /* synthetic */ Object visitEnumEntry2(IrEnumEntry irEnumEntry, Object obj) {
            visitEnumEntry(irEnumEntry, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry irEnumEntry) {
            IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitEnumEntry(IrEnumEntry irEnumEntry, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorCallExpression */
        public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, Object obj) {
            visitErrorCallExpression(irErrorCallExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
            IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorCallExpression(IrErrorCallExpression irErrorCallExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorDeclaration */
        public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, Object obj) {
            visitErrorDeclaration(irErrorDeclaration, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
            IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorDeclaration(IrErrorDeclaration irErrorDeclaration, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitErrorExpression */
        public /* bridge */ /* synthetic */ Object visitErrorExpression2(IrErrorExpression irErrorExpression, Object obj) {
            visitErrorExpression(irErrorExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorExpression(IrErrorExpression irErrorExpression) {
            IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitErrorExpression(IrErrorExpression irErrorExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpression */
        public /* bridge */ /* synthetic */ Object visitExpression2(IrExpression irExpression, Object obj) {
            visitExpression(irExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpression(IrExpression irExpression) {
            IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpression(IrExpression irExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExpressionBody */
        public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
            visitExpressionBody(irExpressionBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpressionBody(IrExpressionBody irExpressionBody) {
            IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitExternalPackageFragment */
        public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
            visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
            IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitField */
        public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
            visitField(irField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField irField) {
            IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitField(IrField irField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFieldAccess */
        public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
            visitFieldAccess(irFieldAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFile */
        public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
            visitFile(irFile, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFile(IrFile irFile) {
            IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFile(IrFile irFile, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunction */
        public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
            visitFunction(irFunction, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunction(IrFunction irFunction) {
            IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunction(IrFunction irFunction, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionAccess */
        public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
            visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionExpression */
        public /* bridge */ /* synthetic */ Object visitFunctionExpression2(IrFunctionExpression irFunctionExpression, Object obj) {
            visitFunctionExpression(irFunctionExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionExpression(IrFunctionExpression irFunctionExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitFunctionReference */
        public /* bridge */ /* synthetic */ Object visitFunctionReference2(IrFunctionReference irFunctionReference, Object obj) {
            visitFunctionReference(irFunctionReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionReference(IrFunctionReference irFunctionReference) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitFunctionReference(IrFunctionReference irFunctionReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetClass */
        public /* bridge */ /* synthetic */ Object visitGetClass2(IrGetClass irGetClass, Object obj) {
            visitGetClass(irGetClass, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetClass(IrGetClass irGetClass) {
            IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetClass(IrGetClass irGetClass, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetEnumValue */
        public /* bridge */ /* synthetic */ Object visitGetEnumValue2(IrGetEnumValue irGetEnumValue, Object obj) {
            visitGetEnumValue(irGetEnumValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetEnumValue(IrGetEnumValue irGetEnumValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetField */
        public /* bridge */ /* synthetic */ Object visitGetField2(IrGetField irGetField, Object obj) {
            visitGetField(irGetField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetField(IrGetField irGetField) {
            IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetField(IrGetField irGetField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetObjectValue */
        public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
            visitGetObjectValue(irGetObjectValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitGetValue */
        public /* bridge */ /* synthetic */ Object visitGetValue2(IrGetValue irGetValue, Object obj) {
            visitGetValue(irGetValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetValue(IrGetValue irGetValue) {
            IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitGetValue(IrGetValue irGetValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitInstanceInitializerCall */
        public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
            visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
            IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedProperty */
        public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
            visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
        public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
            visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitLoop */
        public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
            visitLoop(irLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLoop(IrLoop irLoop) {
            IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitLoop(IrLoop irLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
            visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitModuleFragment */
        public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
            visitModuleFragment(irModuleFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitModuleFragment(IrModuleFragment irModuleFragment) {
            IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPackageFragment */
        public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
            visitPackageFragment(irPackageFragment, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment irPackageFragment) {
            IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitProperty */
        public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
            visitProperty(irProperty, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty irProperty) {
            IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitProperty(IrProperty irProperty, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitPropertyReference */
        public /* bridge */ /* synthetic */ Object visitPropertyReference2(IrPropertyReference irPropertyReference, Object obj) {
            visitPropertyReference(irPropertyReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPropertyReference(IrPropertyReference irPropertyReference) {
            IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitPropertyReference(IrPropertyReference irPropertyReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitRawFunctionReference */
        public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
            visitRawFunctionReference(irRawFunctionReference, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
            IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitReturn */
        public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
            visitReturn(irReturn, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitReturn(IrReturn irReturn) {
            IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitReturn(IrReturn irReturn, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitScript */
        public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
            visitScript(irScript, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript irScript) {
            IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitScript(IrScript irScript, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetField */
        public /* bridge */ /* synthetic */ Object visitSetField2(IrSetField irSetField, Object obj) {
            visitSetField(irSetField, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetField(IrSetField irSetField) {
            IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetField(IrSetField irSetField, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSetValue */
        public /* bridge */ /* synthetic */ Object visitSetValue2(IrSetValue irSetValue, Object obj) {
            visitSetValue(irSetValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetValue(IrSetValue irSetValue) {
            IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSetValue(IrSetValue irSetValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSimpleFunction */
        public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
            visitSimpleFunction(irSimpleFunction, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
            IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSingletonReference */
        public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
            visitSingletonReference(irGetSingletonValue, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
            IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSpreadElement */
        public /* bridge */ /* synthetic */ Object visitSpreadElement2(IrSpreadElement irSpreadElement, Object obj) {
            visitSpreadElement(irSpreadElement, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSpreadElement(IrSpreadElement irSpreadElement) {
            IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSpreadElement(IrSpreadElement irSpreadElement, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitStringConcatenation */
        public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
            visitStringConcatenation(irStringConcatenation, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
            IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspendableExpression */
        public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
            visitSuspendableExpression(irSuspendableExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
            IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSuspensionPoint */
        public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
            visitSuspensionPoint(irSuspensionPoint, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
            IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitSyntheticBody */
        public /* bridge */ /* synthetic */ Object visitSyntheticBody2(IrSyntheticBody irSyntheticBody, Object obj) {
            visitSyntheticBody(irSyntheticBody, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody) {
            IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitSyntheticBody(IrSyntheticBody irSyntheticBody, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitThrow */
        public /* bridge */ /* synthetic */ Object visitThrow2(IrThrow irThrow, Object obj) {
            visitThrow(irThrow, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitThrow(IrThrow irThrow) {
            IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitThrow(IrThrow irThrow, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTry */
        public /* bridge */ /* synthetic */ Object visitTry2(IrTry irTry, Object obj) {
            visitTry(irTry, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTry(IrTry irTry) {
            IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTry(IrTry irTry, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeAlias */
        public /* bridge */ /* synthetic */ Object visitTypeAlias2(IrTypeAlias irTypeAlias, Object obj) {
            visitTypeAlias(irTypeAlias, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias irTypeAlias) {
            IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeAlias(IrTypeAlias irTypeAlias, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeOperator */
        public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
            visitTypeOperator(irTypeOperatorCall, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
            IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitTypeParameter */
        public /* bridge */ /* synthetic */ Object visitTypeParameter2(IrTypeParameter irTypeParameter, Object obj) {
            visitTypeParameter(irTypeParameter, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter irTypeParameter) {
            IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitTypeParameter(IrTypeParameter irTypeParameter, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueAccess */
        public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
            visitValueAccess(irValueAccessExpression, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
            IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitValueParameter */
        public /* bridge */ /* synthetic */ Object visitValueParameter2(IrValueParameter irValueParameter, Object obj) {
            visitValueParameter(irValueParameter, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueParameter(IrValueParameter irValueParameter) {
            IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitValueParameter(IrValueParameter irValueParameter, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVararg */
        public /* bridge */ /* synthetic */ Object visitVararg2(IrVararg irVararg, Object obj) {
            visitVararg(irVararg, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVararg(IrVararg irVararg) {
            IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVararg(IrVararg irVararg, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitVariable */
        public /* bridge */ /* synthetic */ Object visitVariable2(IrVariable irVariable, Object obj) {
            visitVariable(irVariable, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable irVariable, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhen */
        public /* bridge */ /* synthetic */ Object visitWhen2(IrWhen irWhen, Object obj) {
            visitWhen(irWhen, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhen(IrWhen irWhen) {
            IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhen(IrWhen irWhen, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
        /* JADX INFO: renamed from: visitWhileLoop */
        public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
            visitWhileLoop(irWhileLoop, (Void) obj);
            return Unit.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhileLoop(IrWhileLoop irWhileLoop) {
            IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
            IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
        }

        protected final List<Set<IrVariable>> getScopeStack() {
            return this.scopeStack;
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        /* JADX INFO: renamed from: visitElement */
        public void mo6335visitElement(IrElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            IrVisitorsKt.acceptChildrenVoid(element, this);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitContainerExpression(IrContainerExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (!IrExpressionsKt.isTransparentScope(expression)) {
                UtilsKt.push(this.scopeStack, new LinkedHashSet());
            }
            IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, expression);
            if (IrExpressionsKt.isTransparentScope(expression)) {
                return;
            }
            UtilsKt.pop(this.scopeStack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitCatch(IrCatch aCatch) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            UtilsKt.push(this.scopeStack, new LinkedHashSet());
            IrElementVisitorVoid.DefaultImpls.visitCatch(this, aCatch);
            UtilsKt.pop(this.scopeStack);
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
        public void visitVariable(IrVariable declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            IrElementVisitorVoid.DefaultImpls.visitVariable(this, declaration);
            Object objPeek = UtilsKt.peek(this.scopeStack);
            Intrinsics.checkNotNull(objPeek);
            ((Set) objPeek).add(declaration);
        }
    }

    public final IrField addField(IrClass irClass, Name name2, IrType type, boolean z) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        IrFactory factory = irClass.getFactory();
        IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
        irFieldBuilder.setStartOffset(irClass.getStartOffset());
        irFieldBuilder.setEndOffset(irClass.getEndOffset());
        irFieldBuilder.setOrigin(DECLARATION_ORIGIN_COROUTINE_IMPL.INSTANCE);
        irFieldBuilder.setName(name2);
        irFieldBuilder.setType(type);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFieldBuilder.setVisibility(PRIVATE);
        irFieldBuilder.setFinal(!z);
        IrField irFieldBuildField = DeclarationBuildersKt.buildField(factory, irFieldBuilder);
        irFieldBuildField.setParent(irClass);
        IrUtilsKt.addChild(irClass, irFieldBuildField);
        return irFieldBuildField;
    }
}
