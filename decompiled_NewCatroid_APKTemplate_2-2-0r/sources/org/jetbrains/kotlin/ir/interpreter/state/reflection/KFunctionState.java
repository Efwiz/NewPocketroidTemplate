package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.IrTreeBuildUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KParameterProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeParameterProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeProxy;
import org.jetbrains.kotlin.ir.interpreter.stack.Variable;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: KFunctionState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0000\u0018\u0000 @2\u00020\u00012\u00020\u0002:\u0001@BO\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u001a\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bj\u0004\u0018\u0001`\u000b\u0012\u001a\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n\u0018\u00010\bj\u0004\u0018\u0001`\u000b¢\u0006\u0002\u0010\rB7\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0018\b\u0002\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0013j\u0002`\u0014¢\u0006\u0002\u0010\u0015J\r\u00101\u001a\u0004\u0018\u000102¢\u0006\u0002\u00103J\u0012\u00104\u001a\u0004\u0018\u00010\u000f2\u0006\u00105\u001a\u000206H\u0016J\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u00108\u001a\u000209J\u000e\u0010:\u001a\u00020\u001a2\u0006\u00108\u001a\u000209J\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00172\u0006\u00108\u001a\u000209J\b\u0010<\u001a\u00020=H\u0002J\b\u0010>\u001a\u00020?H\u0016R\u0016\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0013j\u0002`\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR(\u0010!\u001a\u0004\u0018\u00010 2\b\u0010\u001f\u001a\u0004\u0018\u00010 @FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020'¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R \u0010.\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020/0\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001e¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KFunctionState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/StateWithClosure;", "functionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "dispatchReceiver", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Field;", "extensionReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;Lkotlin/Pair;Lkotlin/Pair;)V", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fields", "", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Fields;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;Ljava/util/Map;)V", "_parameters", "", "Lkotlin/reflect/KParameter;", "_returnType", "Lkotlin/reflect/KType;", "_typeParameters", "Lkotlin/reflect/KTypeParameter;", "getFields", "()Ljava/util/Map;", "value", "Lorg/jetbrains/kotlin/ir/types/IrType;", "funInterface", "getFunInterface", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setFunInterface", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "invokeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getInvokeSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "upValues", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "getUpValues", "getArity", "", "()Ljava/lang/Integer;", "getIrFunctionByIrCall", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getParameters", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "getReturnType", "getTypeParameters", "isLambda", "", "toString", "", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KFunctionState extends ReflectionState implements StateWithClosure {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private List<? extends KParameter> _parameters;
    private KType _returnType;
    private List<? extends KTypeParameter> _typeParameters;
    private final Map<IrSymbol, State> fields;
    private IrType funInterface;
    private final IrFunctionSymbol invokeSymbol;
    private final IrClass irClass;
    private final IrFunction irFunction;
    private final Map<IrSymbol, Variable> upValues;

    public final IrFunction getIrFunction() {
        return this.irFunction;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    public /* synthetic */ KFunctionState(IrFunction irFunction, IrClass irClass, IrInterpreterEnvironment irInterpreterEnvironment, LinkedHashMap linkedHashMap, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irFunction, irClass, irInterpreterEnvironment, (Map<IrSymbol, State>) ((i & 8) != 0 ? new LinkedHashMap() : linkedHashMap));
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState, org.jetbrains.kotlin.ir.interpreter.state.State
    public Map<IrSymbol, State> getFields() {
        return this.fields;
    }

    public KFunctionState(IrFunction irFunction, IrClass irClass, IrInterpreterEnvironment environment, Map<IrSymbol, State> fields) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(fields, "fields");
        this.irFunction = irFunction;
        this.irClass = irClass;
        this.fields = fields;
        this.upValues = new LinkedHashMap();
        IrValueParameter dispatchReceiverParameter = this.irFunction.getDispatchReceiverParameter();
        boolean z = (dispatchReceiverParameter != null ? getField(dispatchReceiverParameter.getSymbol()) : null) != null;
        IrValueParameter extensionReceiverParameter = this.irFunction.getExtensionReceiverParameter();
        boolean z2 = (extensionReceiverParameter != null ? getField(extensionReceiverParameter.getSymbol()) : null) != null;
        IrFunctionSymbol cachedFunction$ir_interpreter$default = IrInterpreterEnvironment.getCachedFunction$ir_interpreter$default(environment, this.irFunction.getSymbol(), z, z2, false, 8, null);
        this.invokeSymbol = cachedFunction$ir_interpreter$default == null ? environment.setCachedFunction$ir_interpreter(this.irFunction.getSymbol(), (6 & 2) != 0 ? false : z, (6 & 4) != 0 ? false : z2, (6 & 8) != 0 ? false : false, INSTANCE.createInvokeFunction(this.irFunction, getIrClass(), z, z2).getSymbol()) : cachedFunction$ir_interpreter$default;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure
    public Map<IrSymbol, Variable> getUpValues() {
        return this.upValues;
    }

    public final IrType getFunInterface() {
        return this.funInterface;
    }

    public final void setFunInterface(IrType irType) {
        if (irType == null) {
            return;
        }
        this.funInterface = irType;
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        Intrinsics.checkNotNull(classOrNull);
        if (UtilsKt.getSingleAbstractMethod(classOrNull.getOwner()).getExtensionReceiverParameter() != null) {
            this.invokeSymbol.getOwner().setExtensionReceiverParameter(this.invokeSymbol.getOwner().getValueParameters().get(0));
            this.invokeSymbol.getOwner().setValueParameters(CollectionsKt.drop(this.invokeSymbol.getOwner().getValueParameters(), 1));
        }
    }

    public final IrFunctionSymbol getInvokeSymbol() {
        return this.invokeSymbol;
    }

    /* JADX INFO: compiled from: KFunctionState.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KFunctionState$Companion;", "", "()V", "createInvokeFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "hasDispatchReceiver", "", "hasExtensionReceiver", "isCallToInvokeOrMethodFromFunInterface", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "isCallToNonAbstractMethodOfFunInterface", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final IrSimpleFunction createInvokeFunction(IrFunction irFunction, IrClass irClass, boolean hasDispatchReceiver, boolean hasExtensionReceiver) {
            IrValueParameter irValueParameter;
            IrConstructorCall irConstructorCallCreateConstructorCall$default;
            List<IrDeclaration> declarations = irClass.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrSimpleFunction) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            DescriptorsRemapper descriptorsRemapper = null;
            Object[] objArr = 0;
            boolean z = false;
            Object obj2 = null;
            while (true) {
                int i = 1;
                if (!it.hasNext()) {
                    if (z) {
                        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj2;
                        Name nameIdentifier = Name.identifier("Function$0");
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                        IrClass irClassCreateTempClass$default = IrTreeBuildUtilsKt.createTempClass$default(nameIdentifier, null, 2, null);
                        irClassCreateTempClass$default.setParent(irFunction.getParent());
                        irClassCreateTempClass$default.setSuperTypes(CollectionsKt.plus((Collection<? extends IrSimpleType>) irClassCreateTempClass$default.getSuperTypes(), IrUtilsKt.getDefaultType(irClass)));
                        IrSimpleFunction irSimpleFunctionCreateTempFunction$default = IrTreeBuildUtilsKt.createTempFunction$default(OperatorNameConventions.INVOKE, irFunction.getReturnType(), IrTreeBuildUtilsKt.getTEMP_FUNCTION_FOR_INTERPRETER(), null, 8, null);
                        irSimpleFunctionCreateTempFunction$default.setParent(irClassCreateTempClass$default);
                        irSimpleFunctionCreateTempFunction$default.setOverriddenSymbols(CollectionsKt.listOf(irSimpleFunction.getSymbol()));
                        IrValueParameter dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
                        if (dispatchReceiverParameter != null) {
                            IrValueParameter irValueParameter2 = dispatchReceiverParameter;
                            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, i, objArr == true ? 1 : 0);
                            IrVisitorsKt.acceptVoid(irValueParameter2, deepCopySymbolRemapper);
                            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irValueParameter2.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irSimpleFunctionCreateTempFunction$default);
                            if (irElementPatchDeclarationParents != null) {
                                irValueParameter = (IrValueParameter) irElementPatchDeclarationParents;
                            } else {
                                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrValueParameter");
                            }
                        } else {
                            irValueParameter = null;
                        }
                        irSimpleFunctionCreateTempFunction$default.setDispatchReceiverParameter(irValueParameter);
                        ArrayList arrayList2 = new ArrayList();
                        if (irFunction instanceof IrSimpleFunction) {
                            irConstructorCallCreateConstructorCall$default = IrTreeBuildUtilsKt.createCall$default(irFunction, null, 1, null);
                        } else {
                            if (!(irFunction instanceof IrConstructor)) {
                                throw new NotImplementedError("An operation is not implemented: " + ("Unsupported symbol " + irSimpleFunctionCreateTempFunction$default.getSymbol() + " for invoke"));
                            }
                            irConstructorCallCreateConstructorCall$default = IrTreeBuildUtilsKt.createConstructorCall$default((IrConstructor) irFunction, null, 1, null);
                        }
                        IrValueParameter dispatchReceiverParameter2 = irFunction.getDispatchReceiverParameter();
                        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
                        if (dispatchReceiverParameter2 != null) {
                            irConstructorCallCreateConstructorCall$default.setDispatchReceiver(IrTreeBuildUtilsKt.createGetValue(dispatchReceiverParameter2));
                            if (!hasDispatchReceiver) {
                                arrayList2.add(dispatchReceiverParameter2);
                            }
                        }
                        if (extensionReceiverParameter != null) {
                            irConstructorCallCreateConstructorCall$default.setExtensionReceiver(IrTreeBuildUtilsKt.createGetValue(extensionReceiverParameter));
                            if (!hasExtensionReceiver) {
                                arrayList2.add(extensionReceiverParameter);
                            }
                        }
                        for (IrValueParameter irValueParameter3 : irFunction.getValueParameters()) {
                            IrExpressionsKt.putArgument(irConstructorCallCreateConstructorCall$default, irValueParameter3, IrTreeBuildUtilsKt.createGetValue(irValueParameter3));
                            arrayList2.add(irValueParameter3);
                        }
                        irSimpleFunctionCreateTempFunction$default.setValueParameters(arrayList2);
                        irSimpleFunctionCreateTempFunction$default.setBody(IrTreeBuildUtilsKt.wrapWithBlockBody(CollectionsKt.listOf(IrTreeBuildUtilsKt.createReturn(irSimpleFunctionCreateTempFunction$default, irConstructorCallCreateConstructorCall$default))));
                        irClassCreateTempClass$default.getDeclarations().add(irSimpleFunctionCreateTempFunction$default);
                        return irSimpleFunctionCreateTempFunction$default;
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                Object next = it.next();
                if (Intrinsics.areEqual(((IrSimpleFunction) next).getName(), OperatorNameConventions.INVOKE)) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    obj2 = next;
                    z = true;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isCallToNonAbstractMethodOfFunInterface(IrCall expression) {
            IrSimpleFunction owner = expression.getSymbol().getOwner();
            return UtilsKt.hasFunInterfaceParent(owner) && owner.getModality() != Modality.ABSTRACT;
        }

        public final boolean isCallToInvokeOrMethodFromFunInterface(IrCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrSimpleFunction owner = expression.getSymbol().getOwner();
            return Intrinsics.areEqual(owner.getName(), OperatorNameConventions.INVOKE) || UtilsKt.hasFunInterfaceParent(owner);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KFunctionState(IrFunctionReference functionReference, IrInterpreterEnvironment environment, Pair<? extends IrSymbol, ? extends State> pair, Pair<? extends IrSymbol, ? extends State> pair2) {
        Intrinsics.checkNotNullParameter(functionReference, "functionReference");
        Intrinsics.checkNotNullParameter(environment, "environment");
        IrFunction owner = functionReference.getSymbol().getOwner();
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(functionReference.getType());
        Intrinsics.checkNotNull(classOrNull);
        this(owner, classOrNull.getOwner(), environment, (Map<IrSymbol, State>) MapsKt.toMutableMap(MapsKt.toMap(CollectionsKt.listOfNotNull((Object[]) new Pair[]{pair, pair2}))));
        if (pair != null) {
            setField(pair.component1(), pair.component2());
        }
        if (pair2 != null) {
            setField(pair2.component1(), pair2.component2());
        }
        Map<IrSymbol, Variable> upValues = getUpValues();
        Map<IrSymbol, State> fields = getFields();
        ArrayList arrayList = new ArrayList(fields.size());
        for (Map.Entry<IrSymbol, State> entry : fields.entrySet()) {
            arrayList.add(TuplesKt.to(entry.getKey(), new Variable(entry.getValue())));
        }
        MapsKt.putAll(upValues, arrayList);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState, org.jetbrains.kotlin.ir.interpreter.state.State
    public IrFunction getIrFunctionByIrCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Companion companion = INSTANCE;
        if (companion.isCallToNonAbstractMethodOfFunInterface(expression)) {
            return (IrFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(expression.getSymbol().getOwner(), false, null, 3, null);
        }
        return companion.isCallToInvokeOrMethodFromFunInterface(expression) ? this.invokeSymbol.getOwner() : super.getIrFunctionByIrCall(expression);
    }

    public final List<KParameter> getParameters(CallInterceptor callInterceptor) {
        KParameterProxy kParameterProxy;
        int i;
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List list = this._parameters;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        IrClass owner = callInterceptor.getEnvironment().getKParameterClass$ir_interpreter().getOwner();
        IrValueParameter dispatchReceiverParameter = this.irFunction.getDispatchReceiverParameter();
        KParameterProxy kParameterProxy2 = null;
        if (dispatchReceiverParameter != null) {
            kParameterProxy = new KParameterProxy(new KParameterState(owner, dispatchReceiverParameter, 0, KParameter.Kind.INSTANCE), callInterceptor);
            i = 1;
        } else {
            kParameterProxy = null;
            i = 0;
        }
        IrValueParameter extensionReceiverParameter = this.irFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            kParameterProxy2 = new KParameterProxy(new KParameterState(owner, extensionReceiverParameter, i, KParameter.Kind.EXTENSION_RECEIVER), callInterceptor);
            i++;
        }
        List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) new KParameterProxy[]{kParameterProxy, kParameterProxy2});
        List<IrValueParameter> valueParameters = this.irFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        int i2 = i;
        while (it.hasNext()) {
            arrayList.add(new KParameterProxy(new KParameterState(owner, (IrValueParameter) it.next(), i2, null, 8, null), callInterceptor));
            i2++;
        }
        List<KParameter> listPlus = CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
        this._parameters = listPlus;
        Intrinsics.checkNotNull(listPlus);
        return listPlus;
    }

    public final KType getReturnType(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        KType kType = this._returnType;
        if (kType != null) {
            Intrinsics.checkNotNull(kType);
            return kType;
        }
        KTypeProxy kTypeProxy = new KTypeProxy(new KTypeState(this.irFunction.getReturnType(), callInterceptor.getEnvironment().getKTypeClass$ir_interpreter().getOwner()), callInterceptor);
        this._returnType = kTypeProxy;
        Intrinsics.checkNotNull(kTypeProxy);
        return kTypeProxy;
    }

    public final List<KTypeParameter> getTypeParameters(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List list = this._typeParameters;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        IrClass owner = callInterceptor.getEnvironment().getKTypeParameterClass$ir_interpreter().getOwner();
        List<IrTypeParameter> typeParameters = getIrClass().getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new KTypeParameterProxy(new KTypeParameterState((IrTypeParameter) it.next(), owner), callInterceptor));
        }
        ArrayList arrayList2 = arrayList;
        this._typeParameters = arrayList2;
        Intrinsics.checkNotNull(arrayList2);
        return arrayList2;
    }

    public final Integer getArity() {
        String strAsString = getIrClass().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return StringsKt.toIntOrNull(StringsKt.removePrefix(StringsKt.removePrefix(StringsKt.removePrefix(strAsString, (CharSequence) "Suspend"), (CharSequence) "Function"), (CharSequence) StandardNames.K_FUNCTION_PREFIX));
    }

    private final boolean isLambda() {
        Name name2 = this.irFunction.getName();
        return Intrinsics.areEqual(name2, SpecialNames.ANONYMOUS) || Intrinsics.areEqual(name2, SpecialNames.NO_NAME_PROVIDED);
    }

    public String toString() {
        return isLambda() ? renderLambda(this.irFunction) : renderFunction(this.irFunction);
    }
}
