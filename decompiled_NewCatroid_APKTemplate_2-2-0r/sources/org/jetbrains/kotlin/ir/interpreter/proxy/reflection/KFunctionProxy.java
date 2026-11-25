package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KFunctionProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00022\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ'\u00103\u001a\u0004\u0018\u00010\u00032\u0016\u00104\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000305\"\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00106J \u00107\u001a\u0004\u0018\u00010\u00032\u0014\u00104\u001a\u0010\u0012\u0004\u0012\u00020$\u0012\u0006\u0012\u0004\u0018\u00010\u000308H\u0016J\u0013\u00109\u001a\u00020\u00162\b\u0010:\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010;\u001a\u00020\u0010H\u0016J\b\u0010<\u001a\u00020 H\u0016J\u0014\u0010=\u001a\u00020\u0016*\u00020>2\u0006\u0010:\u001a\u00020>H\u0002J\u000e\u0010?\u001a\u0004\u0018\u00010@*\u00020>H\u0002J\f\u0010A\u001a\u00020\u0016*\u00020>H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0017R\u0014\u0010\u0019\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0017R\u0014\u0010\u001a\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u0014\u0010\u001b\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017R\u0014\u0010\u001c\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0017R\u0014\u0010\u001d\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0017R\u0014\u0010\u001e\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0017R\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\u000eR\u0014\u0010&\u001a\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\u000eR\u0016\u0010/\u001a\u0004\u0018\u0001008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u00102¨\u0006B"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KFunctionProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lkotlin/reflect/KFunction;", "", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/FunctionWithAllInvokes;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KFunctionState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KFunctionState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "arity", "", "getArity", "()I", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "isAbstract", "", "()Z", "isExternal", "isFinal", "isInfix", "isInline", "isOpen", "isOperator", "isSuspend", "name", "", "getName", "()Ljava/lang/String;", "parameters", "Lkotlin/reflect/KParameter;", "getParameters", "returnType", "Lkotlin/reflect/KType;", "getReturnType", "()Lkotlin/reflect/KType;", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KFunctionState;", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "call", "args", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_DESCRIPTOR, "callBy", "", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "equalsByAdapteeCall", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getAdapteeCallSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "isAdapter", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KFunctionProxy implements KFunction<Object>, FunctionWithAllInvokes, ReflectionProxy {
    private final int arity;
    private final CallInterceptor callInterceptor;
    private final KFunctionState state;

    public KFunctionProxy(KFunctionState state, CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        this.state = state;
        this.callInterceptor = callInterceptor;
        Integer arity = getState().getArity();
        this.arity = arity != null ? arity.intValue() : 23;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public CallInterceptor getCallInterceptor() {
        return this.callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public KFunctionState getState() {
        return this.state;
    }

    @Override // kotlin.jvm.functions.FunctionN, kotlin.jvm.internal.FunctionBase
    public int getArity() {
        return this.arity;
    }

    @Override // kotlin.reflect.KFunction
    /* JADX INFO: renamed from: isInline */
    public boolean getIsInline() {
        return getState().getIrFunction().getIsInline();
    }

    @Override // kotlin.reflect.KFunction
    /* JADX INFO: renamed from: isExternal */
    public boolean getIsExternal() {
        return getState().getIrFunction().getIsExternal();
    }

    @Override // kotlin.reflect.KFunction
    /* JADX INFO: renamed from: isOperator */
    public boolean getIsOperator() {
        return (getState().getIrFunction() instanceof IrSimpleFunction) && ((IrSimpleFunction) getState().getIrFunction()).getIsOperator();
    }

    @Override // kotlin.reflect.KFunction
    /* JADX INFO: renamed from: isInfix */
    public boolean getIsInfix() {
        return (getState().getIrFunction() instanceof IrSimpleFunction) && ((IrSimpleFunction) getState().getIrFunction()).getIsInfix();
    }

    @Override // kotlin.reflect.KCallable
    public String getName() {
        String strAsString = getState().getIrFunction().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // kotlin.reflect.KAnnotatedElement
    public List<Annotation> getAnnotations() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.reflect.KCallable
    public List<KParameter> getParameters() {
        return getState().getParameters(getCallInterceptor());
    }

    @Override // kotlin.reflect.KCallable
    public KType getReturnType() {
        return getState().getReturnType(getCallInterceptor());
    }

    @Override // kotlin.reflect.KCallable
    public List<KTypeParameter> getTypeParameters() {
        return getState().getTypeParameters(getCallInterceptor());
    }

    @Override // kotlin.reflect.KCallable
    public Object call(Object... args) {
        State stateConvertToState$ir_interpreter;
        int i;
        Intrinsics.checkNotNullParameter(args, "args");
        IrValueParameter dispatchReceiverParameter = getState().getIrFunction().getDispatchReceiverParameter();
        State stateConvertToState$ir_interpreter2 = null;
        if (dispatchReceiverParameter != null) {
            stateConvertToState$ir_interpreter = getEnvironment().convertToState$ir_interpreter(args[0], dispatchReceiverParameter.getType());
            i = 1;
        } else {
            stateConvertToState$ir_interpreter = null;
            i = 0;
        }
        IrValueParameter extensionReceiverParameter = getState().getIrFunction().getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            stateConvertToState$ir_interpreter2 = getEnvironment().convertToState$ir_interpreter(args[i], extensionReceiverParameter.getType());
            i++;
        }
        List<IrValueParameter> valueParameters = getState().getIrFunction().getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(getEnvironment().convertToState$ir_interpreter(args[i], ((IrValueParameter) it.next()).getType()));
            i++;
        }
        return CallInterceptor.interceptProxy$default(getCallInterceptor(), getState().getIrFunction(), CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull((Object[]) new State[]{stateConvertToState$ir_interpreter, stateConvertToState$ir_interpreter2}), (Iterable) arrayList), null, 4, null);
    }

    @Override // kotlin.reflect.KCallable
    public Object callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.reflect.KCallable
    public KVisibility getVisibility() {
        return toKVisibility(getState().getIrFunction().getVisibility());
    }

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isFinal */
    public boolean getIsFinal() {
        return (getState().getIrFunction() instanceof IrSimpleFunction) && ((IrSimpleFunction) getState().getIrFunction()).getModality() == Modality.FINAL;
    }

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isOpen */
    public boolean getIsOpen() {
        return (getState().getIrFunction() instanceof IrSimpleFunction) && ((IrSimpleFunction) getState().getIrFunction()).getModality() == Modality.OPEN;
    }

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isAbstract */
    public boolean getIsAbstract() {
        return (getState().getIrFunction() instanceof IrSimpleFunction) && ((IrSimpleFunction) getState().getIrFunction()).getModality() == Modality.ABSTRACT;
    }

    @Override // kotlin.reflect.KFunction, kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isSuspend */
    public boolean getIsSuspend() {
        return AdditionalIrUtilsKt.isSuspend(getState().getIrFunction());
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public boolean equals(Object other) {
        IrClassSymbol classOrNull;
        IrClass owner;
        if (this == other) {
            return true;
        }
        if (!(other instanceof KFunctionProxy)) {
            return false;
        }
        KFunctionProxy kFunctionProxy = (KFunctionProxy) other;
        if (getArity() != kFunctionProxy.getArity() || getIsSuspend() != kFunctionProxy.getIsSuspend()) {
            return false;
        }
        IrType funInterface = getState().getFunInterface();
        if (Intrinsics.areEqual((funInterface == null || (classOrNull = IrTypesKt.getClassOrNull(funInterface)) == null || (owner = classOrNull.getOwner()) == null) ? null : owner.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) {
            return getState() == kFunctionProxy.getState();
        }
        if (StateKt.hasTheSameFieldsWith(getState(), kFunctionProxy.getState())) {
            return (isAdapter(getState().getIrFunction()) && isAdapter(kFunctionProxy.getState().getIrFunction())) ? equalsByAdapteeCall(getState().getIrFunction(), kFunctionProxy.getState().getIrFunction()) : Intrinsics.areEqual(getState().getIrFunction(), kFunctionProxy.getState().getIrFunction());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public int hashCode() {
        if (!isAdapter(getState().getIrFunction())) {
            return getState().getIrFunction().hashCode();
        }
        IrFunctionSymbol adapteeCallSymbol = getAdapteeCallSymbol(getState().getIrFunction());
        Intrinsics.checkNotNull(adapteeCallSymbol);
        return adapteeCallSymbol.hashCode();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public String toString() {
        return getState().toString();
    }

    private final boolean isAdapter(IrFunction irFunction) {
        return Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE);
    }

    private final IrFunctionSymbol getAdapteeCallSymbol(IrFunction irFunction) {
        if (!isAdapter(irFunction)) {
            return null;
        }
        IrBody body = irFunction.getBody();
        Intrinsics.checkNotNull(body);
        IrExpression value = (IrStatement) CollectionsKt.single((List) IrUtilsKt.getStatements(body));
        if (value instanceof IrTypeOperatorCall) {
            value = ((IrTypeOperatorCall) value).getArgument();
        } else if (value instanceof IrReturn) {
            value = ((IrReturn) value).getValue();
        }
        IrFunctionAccessExpression irFunctionAccessExpression = value instanceof IrFunctionAccessExpression ? (IrFunctionAccessExpression) value : null;
        if (irFunctionAccessExpression != null) {
            return irFunctionAccessExpression.getSymbol();
        }
        return null;
    }

    private final boolean equalsByAdapteeCall(IrFunction irFunction, IrFunction irFunction2) {
        Pair pair;
        if (isAdapter(irFunction) && isAdapter(irFunction2)) {
            IrBody body = irFunction.getBody();
            Intrinsics.checkNotNull(body);
            IrStatement irStatement = (IrStatement) CollectionsKt.single((List) IrUtilsKt.getStatements(body));
            IrBody body2 = irFunction2.getBody();
            Intrinsics.checkNotNull(body2);
            IrStatement irStatement2 = (IrStatement) CollectionsKt.single((List) IrUtilsKt.getStatements(body2));
            if (irStatement instanceof IrTypeOperatorCall) {
                if (!(irStatement2 instanceof IrTypeOperatorCall)) {
                    return false;
                }
                pair = new Pair(((IrTypeOperatorCall) irStatement).getArgument(), ((IrTypeOperatorCall) irStatement2).getArgument());
            } else if (irStatement instanceof IrReturn) {
                if (!(irStatement2 instanceof IrReturn)) {
                    return false;
                }
                pair = new Pair(((IrReturn) irStatement).getValue(), ((IrReturn) irStatement2).getValue());
            } else {
                pair = new Pair(irStatement, irStatement2);
            }
            IrStatement irStatement3 = (IrStatement) pair.component1();
            IrStatement irStatement4 = (IrStatement) pair.component2();
            return (irStatement3 instanceof IrFunctionAccessExpression) && (irStatement4 instanceof IrFunctionAccessExpression) && Intrinsics.areEqual(((IrFunctionAccessExpression) irStatement3).getSymbol(), ((IrFunctionAccessExpression) irStatement4).getSymbol());
        }
        return false;
    }
}
