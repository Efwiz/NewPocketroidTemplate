package org.jetbrains.kotlin.ir.interpreter;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.interpreter.builtins.IrBuiltInsMapGeneratedKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterAssertionError;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterError;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterException;
import org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicEvaluator;
import org.jetbrains.kotlin.ir.interpreter.proxy.ProxyKt;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Complex;
import org.jetbrains.kotlin.ir.interpreter.state.ExceptionState;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.Wrapper;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.JsPlatformKt;

/* JADX INFO: compiled from: CallInterceptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001:\u0002?@B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J\u001e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J4\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00160%H\u0016J,\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020(2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00160%H\u0016J\u001e\u0010)\u001a\u00020\u00162\u0006\u0010*\u001a\u00020+2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00160%H\u0016J\u001e\u0010,\u001a\u00020\u00162\u0006\u0010-\u001a\u00020.2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00160%H\u0016J\u0010\u0010/\u001a\u00020\u00162\u0006\u0010-\u001a\u000200H\u0016J,\u00101\u001a\u0004\u0018\u0001022\u0006\u0010\u0017\u001a\u00020\u00182\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\n\u00104\u001a\u0006\u0012\u0002\b\u000305H\u0016J\u0012\u00106\u001a\u0004\u0018\u0001022\u0006\u00107\u001a\u000208H\u0002J\u0016\u00109\u001a\u00020\u001e*\u0004\u0018\u0001022\u0006\u0010:\u001a\u00020\u001eH\u0002J$\u0010;\u001a\u00020\u0016*\u0004\u0018\u00010<2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0002J\u000e\u0010=\u001a\u0004\u0018\u00010>*\u00020\u0018H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/DefaultCallInterceptor;", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "interpreter", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;)V", "bodyMap", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "callStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getEnvironment", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getInterpreter", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreter;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "calculateBuiltIns", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "args", "", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "calculateRangeTo", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "handleIntrinsicMethods", "", "interceptCall", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "defaultAction", "Lkotlin/Function0;", "interceptConstructor", "constructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "interceptEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "interceptGetObjectValue", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "interceptJavaStaticField", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "interceptProxy", "", "valueArguments", "expectedResultClass", "Ljava/lang/Class;", "interpretBuiltinFunction", "signature", "Lorg/jetbrains/kotlin/ir/interpreter/DefaultCallInterceptor$Signature;", "getType", "defaultType", "invokeMethod", "Ljava/lang/invoke/MethodHandle;", "trySubstituteFunctionBody", "Lorg/jetbrains/kotlin/ir/IrElement;", "Arg", "Signature", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultCallInterceptor implements CallInterceptor {
    private final Map<IdSignature, IrBody> bodyMap;
    private final CallStack callStack;
    private final IrInterpreterEnvironment environment;
    private final IrInterpreter interpreter;
    private final IrBuiltIns irBuiltIns;

    public DefaultCallInterceptor(IrInterpreter interpreter) {
        Intrinsics.checkNotNullParameter(interpreter, "interpreter");
        this.interpreter = interpreter;
        this.environment = getInterpreter().getEnvironment();
        this.callStack = getEnvironment().getCallStack();
        this.irBuiltIns = getEnvironment().getIrBuiltIns();
        this.bodyMap = getInterpreter().getBodyMap$ir_interpreter();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public IrInterpreter getInterpreter() {
        return this.interpreter;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public IrInterpreterEnvironment getEnvironment() {
        return this.environment;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public Object interceptProxy(IrFunction irFunction, final List<? extends State> valueArguments, Class<?> expectedResultClass) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        Intrinsics.checkNotNullParameter(expectedResultClass, "expectedResultClass");
        final IrCall irCallCreateCall$default = IrTreeBuildUtilsKt.createCall$default(irFunction, null, 1, null);
        return ProxyKt.wrap(getInterpreter().withNewCallStack$ir_interpreter(irCallCreateCall$default, new Function1<IrInterpreter, Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.DefaultCallInterceptor.interceptProxy.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrInterpreter irInterpreter) {
                invoke2(irInterpreter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrInterpreter withNewCallStack) {
                Intrinsics.checkNotNullParameter(withNewCallStack, "$this$withNewCallStack");
                InstructionKt.pushSimpleInstruction(withNewCallStack.getEnvironment().getCallStack(), irCallCreateCall$default);
                Iterator<T> it = valueArguments.iterator();
                while (it.hasNext()) {
                    withNewCallStack.getEnvironment().getCallStack().pushState((State) it.next());
                }
            }
        }), (CallInterceptor) this, false, expectedResultClass);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public void interceptCall(IrCall call, IrFunction irFunction, List<? extends State> args, Function0<Unit> defaultAction) throws InterpreterException {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(defaultAction, "defaultAction");
        boolean zHasAnnotation = IrUtilsKt.hasAnnotation(irFunction, new FqName("kotlin.internal.InlineOnly"));
        boolean zAreEqual = Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE);
        State state = irFunction.getDispatchReceiverParameter() != null ? args.get(0) : null;
        if ((state instanceof Wrapper) && !zHasAnnotation && !zAreEqual) {
            invokeMethod(((Wrapper) state).getMethod(irFunction), irFunction, args);
            return;
        }
        if (Wrapper.INSTANCE.mustBeHandledWithWrapper(irFunction)) {
            invokeMethod(Wrapper.INSTANCE.getStaticMethod(irFunction), irFunction, args);
            return;
        }
        if (handleIntrinsicMethods(irFunction)) {
            return;
        }
        if (StateKt.mustBeHandledAsReflection(state, call)) {
            invokeMethod(Wrapper.INSTANCE.getReflectionMethod(irFunction), irFunction, args);
            return;
        }
        if (state instanceof Primitive) {
            calculateBuiltIns(irFunction, args);
            return;
        }
        if (irFunction.getBody() == null && UtilsKt.isAccessorOfPropertyWithBackingField(irFunction)) {
            InstructionKt.pushCompoundInstruction(this.callStack, IrTreeBuildUtilsKt.createGetField(irFunction));
            return;
        }
        if (irFunction.getBody() != null) {
            defaultAction.invoke();
        } else if (trySubstituteFunctionBody(irFunction) == null) {
            calculateBuiltIns(irFunction, args);
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public void interceptConstructor(IrFunctionAccessExpression constructorCall, List<? extends State> args, Function0<Unit> defaultAction) throws InterpreterException {
        Intrinsics.checkNotNullParameter(constructorCall, "constructorCall");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(defaultAction, "defaultAction");
        State stateLoadState = this.callStack.loadState(UtilsKt.getThisReceiver(constructorCall));
        IrFunction owner = constructorCall.getSymbol().getOwner();
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(owner);
        if (Wrapper.INSTANCE.mustBeHandledWithWrapper(parentAsClass)) {
            invokeMethod(Wrapper.INSTANCE.getConstructorMethod(owner), owner, args);
            if (UtilsKt.isSubclassOfThrowable(parentAsClass)) {
                Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.ExceptionState");
                State statePopState = this.callStack.popState();
                Intrinsics.checkNotNull(statePopState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Wrapper");
                ((ExceptionState) stateLoadState).copyFieldsFrom((Wrapper) statePopState);
                return;
            }
            if (constructorCall instanceof IrConstructorCall) {
                this.callStack.rewriteState(UtilsKt.getThisReceiver(constructorCall), this.callStack.popState());
                return;
            }
            Intrinsics.checkNotNull(stateLoadState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Complex");
            State statePopState2 = this.callStack.popState();
            Intrinsics.checkNotNull(statePopState2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Wrapper");
            ((Complex) stateLoadState).setSuperWrapperClass((Wrapper) statePopState2);
            return;
        }
        if (IrTypePredicatesKt.isArray(IrUtilsKt.getDefaultType(parentAsClass)) || UtilsKt.isPrimitiveArray(IrUtilsKt.getDefaultType(parentAsClass))) {
            if (!handleIntrinsicMethods(owner)) {
                throw new InterpreterAssertionError(("Unsupported intrinsic constructor: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return;
        }
        boolean z = false;
        if (IrTypePredicatesKt.isUnsignedType$default(IrUtilsKt.getDefaultType(parentAsClass), false, 1, null)) {
            InlineClassRepresentation<IrSimpleType> inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(parentAsClass);
            Name underlyingPropertyName = inlineClassRepresentation != null ? inlineClassRepresentation.getUnderlyingPropertyName() : null;
            List<IrDeclaration> declarations = parentAsClass.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof IrProperty) {
                    arrayList.add(obj);
                }
            }
            Object obj2 = null;
            for (Object obj3 : arrayList) {
                IrProperty irProperty = (IrProperty) obj3;
                if (Intrinsics.areEqual(irProperty.getName(), underlyingPropertyName)) {
                    IrSimpleFunction getter = irProperty.getGetter();
                    if ((getter != null ? getter.getExtensionReceiverParameter() : null) != null) {
                        continue;
                    } else {
                        if (z) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z = true;
                        obj2 = obj3;
                    }
                }
            }
            if (z) {
                IrPropertySymbol symbol = ((IrProperty) obj2).getSymbol();
                CallStack callStack = this.callStack;
                stateLoadState.setField(symbol, (State) CollectionsKt.single((List) args));
                callStack.pushState(stateLoadState);
                return;
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        defaultAction.invoke();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public void interceptGetObjectValue(IrGetObjectValue expression, Function0<Unit> defaultAction) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(defaultAction, "defaultAction");
        IrClass owner = expression.getSymbol().getOwner();
        if (Wrapper.INSTANCE.mustBeHandledWithWrapper(owner)) {
            Wrapper companionObject = Wrapper.INSTANCE.getCompanionObject(owner, getEnvironment());
            getEnvironment().getMapOfObjects$ir_interpreter().put(expression.getSymbol(), companionObject);
            this.callStack.pushState(companionObject);
            return;
        }
        defaultAction.invoke();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public void interceptEnumEntry(IrEnumEntry enumEntry, Function0<Unit> defaultAction) throws InterpreterException {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(defaultAction, "defaultAction");
        IrClass parentAsClass = IrUtilsKt.getParentAsClass(enumEntry.getSymbol().getOwner());
        if (Wrapper.INSTANCE.mustBeHandledWithWrapper(parentAsClass)) {
            State stateConvertToState$ir_interpreter = getEnvironment().convertToState$ir_interpreter(enumEntry.getName().asString(), getEnvironment().getIrBuiltIns().getStringType());
            IrSimpleFunction irSimpleFunction = null;
            boolean z = false;
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(parentAsClass)) {
                if (Intrinsics.areEqual(irSimpleFunction2.getName().asString(), "valueOf")) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    z = true;
                    irSimpleFunction = irSimpleFunction2;
                }
            }
            if (z) {
                invokeMethod(Wrapper.INSTANCE.getEnumEntry(parentAsClass), irSimpleFunction, CollectionsKt.listOf(stateConvertToState$ir_interpreter));
                Map<IrSymbol, Complex> mapOfEnums$ir_interpreter = getEnvironment().getMapOfEnums$ir_interpreter();
                IrEnumEntrySymbol symbol = enumEntry.getSymbol();
                State statePopState = this.callStack.popState();
                Intrinsics.checkNotNull(statePopState, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Complex");
                mapOfEnums$ir_interpreter.put(symbol, (Complex) statePopState);
                return;
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        defaultAction.invoke();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.CallInterceptor
    public void interceptJavaStaticField(IrGetField expression) throws InterpreterAssertionError {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrField owner = expression.getSymbol().getOwner();
        ExceptionUtilsKt.verify(Intrinsics.areEqual(owner.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE) && owner.getIsStatic());
        IrExpressionBody initializer = owner.getInitializer();
        ExceptionUtilsKt.verify(!((initializer != null ? initializer.getExpression() : null) instanceof IrConst));
        this.callStack.pushState(getEnvironment().convertToState$ir_interpreter(Wrapper.INSTANCE.getStaticGetter(owner).invokeWithArguments(new Object[0]), owner.getType()));
    }

    private final void invokeMethod(MethodHandle methodHandle, IrFunction irFunction, List<? extends State> list) throws InterpreterException {
        if (methodHandle == null) {
            if (!handleIntrinsicMethods(irFunction)) {
                throw new InterpreterAssertionError(("Unsupported intrinsic function: " + RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            return;
        }
        MethodType methodTypeType = methodHandle.type();
        Intrinsics.checkNotNullExpressionValue(methodTypeType, "type(...)");
        List<?> argsForMethodInvocation = UtilsKt.getArgsForMethodInvocation(irFunction, this, methodTypeType, list);
        IrInterpreterEnvironment environment = getEnvironment();
        try {
            Object objInvokeWithArguments = methodHandle.invokeWithArguments(argsForMethodInvocation);
            this.callStack.pushState(getEnvironment().convertToState$ir_interpreter(objInvokeWithArguments, getType(objInvokeWithArguments, irFunction.getReturnType())));
        } catch (InterpreterException e) {
            throw e;
        } catch (Throwable th) {
            ExceptionUtilsKt.handleUserException(th, environment);
        }
    }

    private final boolean handleIntrinsicMethods(IrFunction irFunction) {
        List<Instruction> listUnwindInstructions = IntrinsicEvaluator.INSTANCE.unwindInstructions(irFunction, getEnvironment());
        if (listUnwindInstructions == null) {
            return false;
        }
        Iterator<T> it = listUnwindInstructions.iterator();
        while (it.hasNext()) {
            this.callStack.pushInstruction((Instruction) it.next());
        }
        return true;
    }

    /* JADX INFO: compiled from: CallInterceptor.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/DefaultCallInterceptor$Signature;", "", "name", "", "args", "", "Lorg/jetbrains/kotlin/ir/interpreter/DefaultCallInterceptor$Arg;", "(Ljava/lang/String;Ljava/util/List;)V", "getArgs", "()Ljava/util/List;", "setArgs", "(Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Signature {
        private List<Arg> args;
        private String name;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Signature copy$default(Signature signature, String str, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                str = signature.name;
            }
            if ((i & 2) != 0) {
                list = signature.args;
            }
            return signature.copy(str, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final List<Arg> component2() {
            return this.args;
        }

        public final Signature copy(String name2, List<Arg> args) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(args, "args");
            return new Signature(name2, args);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Signature)) {
                return false;
            }
            Signature signature = (Signature) other;
            return Intrinsics.areEqual(this.name, signature.name) && Intrinsics.areEqual(this.args, signature.args);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.args.hashCode();
        }

        public String toString() {
            return "Signature(name=" + this.name + ", args=" + this.args + ')';
        }

        public Signature(String name2, List<Arg> args) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(args, "args");
            this.name = name2;
            this.args = args;
        }

        public final List<Arg> getArgs() {
            return this.args;
        }

        public final String getName() {
            return this.name;
        }

        public final void setArgs(List<Arg> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.args = list;
        }

        public final void setName(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.name = str;
        }
    }

    /* JADX INFO: compiled from: CallInterceptor.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u001f\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/DefaultCallInterceptor$Arg;", "", ModuleXmlParser.TYPE, "", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "getType", "()Ljava/lang/String;", "setType", "(Ljava/lang/String;)V", "getValue", "()Ljava/lang/Object;", "setValue", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Arg {
        private String type;
        private Object value;

        public static /* synthetic */ Arg copy$default(Arg arg, String str, Object obj, int i, Object obj2) {
            if ((i & 1) != 0) {
                str = arg.type;
            }
            if ((i & 2) != 0) {
                obj = arg.value;
            }
            return arg.copy(str, obj);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Object getValue() {
            return this.value;
        }

        public final Arg copy(String type, Object value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new Arg(type, value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Arg)) {
                return false;
            }
            Arg arg = (Arg) other;
            return Intrinsics.areEqual(this.type, arg.type) && Intrinsics.areEqual(this.value, arg.value);
        }

        public int hashCode() {
            int iHashCode = this.type.hashCode() * 31;
            Object obj = this.value;
            return iHashCode + (obj == null ? 0 : obj.hashCode());
        }

        public String toString() {
            return "Arg(type=" + this.type + ", value=" + this.value + ')';
        }

        public Arg(String type, Object obj) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
            this.value = obj;
        }

        public final String getType() {
            return this.type;
        }

        public final Object getValue() {
            return this.value;
        }

        public final void setType(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.type = str;
        }

        public final void setValue(Object obj) {
            this.value = obj;
        }
    }

    private final void calculateBuiltIns(IrFunction irFunction, List<? extends State> args) throws InterpreterException {
        IrType type;
        IrProperty property = UtilsKt.getProperty(irFunction);
        String strAsString = property == null ? irFunction.getName().asString() : property.getName().asString();
        Intrinsics.checkNotNull(strAsString);
        IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null) {
            IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
            type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        }
        List listListOfNotNull = CollectionsKt.listOfNotNull(type);
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrValueParameter) it.next()).getType());
        }
        List listPlus = CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        Iterator it2 = listPlus.iterator();
        while (it2.hasNext()) {
            arrayList2.add(UtilsKt.fqNameWithNullability((IrType) it2.next()));
        }
        ArrayList arrayList3 = arrayList2;
        List listWrap$default = ProxyKt.wrap$default(args, this, irFunction, (MethodType) null, 4, (Object) null);
        IrInterpreterEnvironment environment = getEnvironment();
        try {
            if (Intrinsics.areEqual(strAsString, "rangeTo")) {
                calculateRangeTo(irFunction.getReturnType(), args);
                return;
            }
            List<Pair> listZip = CollectionsKt.zip(arrayList3, listWrap$default);
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
            for (Pair pair : listZip) {
                arrayList4.add(new Arg((String) pair.getFirst(), pair.getSecond()));
            }
            Object objInterpretBuiltinFunction = interpretBuiltinFunction(new Signature(strAsString, arrayList4));
            this.callStack.pushState(getEnvironment().convertToState$ir_interpreter(objInterpretBuiltinFunction, getType(objInterpretBuiltinFunction, irFunction.getReturnType())));
        } catch (InterpreterException e) {
            throw e;
        } catch (Throwable th) {
            ExceptionUtilsKt.handleUserException(th, environment);
        }
    }

    private final Object interpretBuiltinFunction(Signature signature) throws InterpreterError {
        String type;
        if (JsPlatformKt.isJs(getEnvironment().getConfiguration().getPlatform())) {
            if (Intrinsics.areEqual(signature.getName(), "toString")) {
                return UtilsKt.specialToStringForJs(signature.getArgs().get(0).getValue());
            }
            if (Intrinsics.areEqual(signature.getName(), "toFloat")) {
                signature.setName("toDouble");
            }
            List<Arg> args = signature.getArgs();
            ArrayList<Arg> arrayList = new ArrayList();
            for (Object obj : args) {
                Arg arg = (Arg) obj;
                if ((arg.getValue() instanceof Float) || Intrinsics.areEqual(arg.getType(), "kotlin.Float") || Intrinsics.areEqual(arg.getType(), "kotlin.Float?")) {
                    arrayList.add(obj);
                }
            }
            for (Arg arg2 : arrayList) {
                String type2 = arg2.getType();
                if (Intrinsics.areEqual(type2, "kotlin.Float")) {
                    type = "kotlin.Double";
                } else {
                    type = Intrinsics.areEqual(type2, "kotlin.Float?") ? "kotlin.Double?" : arg2.getType();
                }
                arg2.setType(type);
                arg2.setValue(Double.valueOf(Double.parseDouble(String.valueOf(arg2.getValue()))));
            }
        }
        String name2 = signature.getName();
        List<Arg> args2 = signature.getArgs();
        int size = args2.size();
        if (size == 1) {
            return IrBuiltInsMapGeneratedKt.interpretUnaryFunction(name2, args2.get(0).getType(), args2.get(0).getValue());
        }
        if (size == 2) {
            return IrBuiltInsMapGeneratedKt.interpretBinaryFunction(name2, args2.get(0).getType(), args2.get(1).getType(), args2.get(0).getValue(), args2.get(1).getValue());
        }
        if (size == 3) {
            return IrBuiltInsMapGeneratedKt.interpretTernaryFunction(name2, args2.get(0).getType(), args2.get(1).getType(), args2.get(2).getType(), args2.get(0).getValue(), args2.get(1).getValue(), args2.get(2).getValue());
        }
        throw new InterpreterError("Unsupported number of arguments for invocation as builtin function: " + name2);
    }

    private final void calculateRangeTo(IrType type, List<? extends State> args) {
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(type);
        Intrinsics.checkNotNull(classOrNull);
        IrConstructor irConstructor = (IrConstructor) SequencesKt.first(IrUtilsKt.getConstructors(classOrNull.getOwner()));
        IrConstructorCall irConstructorCallCreateConstructorCall$default = IrTreeBuildUtilsKt.createConstructorCall$default(irConstructor, null, 1, null);
        List<IrValueParameter> valueParameters = irConstructor.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        Iterator<T> it = valueParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrValueParameter) it.next()).getSymbol());
        }
        ArrayList arrayList2 = arrayList;
        List<? extends State> list = args;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (State state : list) {
            Intrinsics.checkNotNull(state, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
            arrayList3.add((Primitive) state);
        }
        int i = 0;
        for (Object obj : arrayList3) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irConstructorCallCreateConstructorCall$default.putValueArgument(i, IrUtilsKt.toIrConst$default(((Primitive) obj).getValue(), ((IrValueParameterSymbol) arrayList2.get(i)).getOwner().getType(), 0, 0, 6, null));
            i = i2;
        }
        InstructionKt.pushCompoundInstruction(this.callStack, irConstructorCallCreateConstructorCall$default);
    }

    private final IrType getType(Object obj, IrType irType) {
        if (obj instanceof Boolean) {
            return getIrBuiltIns().getBooleanType();
        }
        if (obj instanceof Character) {
            return getIrBuiltIns().getCharType();
        }
        if (obj instanceof Byte) {
            return getIrBuiltIns().getByteType();
        }
        if (obj instanceof Short) {
            return getIrBuiltIns().getShortType();
        }
        if (obj instanceof Integer) {
            return getIrBuiltIns().getIntType();
        }
        if (obj instanceof Long) {
            return getIrBuiltIns().getLongType();
        }
        if (obj instanceof String) {
            return getIrBuiltIns().getStringType();
        }
        if (obj instanceof Float) {
            return getIrBuiltIns().getFloatType();
        }
        if (obj instanceof Double) {
            return getIrBuiltIns().getDoubleType();
        }
        return obj == null ? getIrBuiltIns().getNothingNType() : irType;
    }

    private final IrElement trySubstituteFunctionBody(IrFunction irFunction) {
        IrBody irBody;
        IdSignature signature = irFunction.getSymbol().getSignature();
        if (signature == null || (irBody = this.bodyMap.get(signature)) == null) {
            return null;
        }
        irFunction.setBody(irBody);
        InstructionKt.pushCompoundInstruction(this.callStack, irFunction);
        return irFunction.getBody();
    }
}
