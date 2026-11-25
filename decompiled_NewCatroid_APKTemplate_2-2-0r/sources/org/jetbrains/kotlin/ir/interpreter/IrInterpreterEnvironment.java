package org.jetbrains.kotlin.ir.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.interpreter.proxy.Proxy;
import org.jetbrains.kotlin.ir.interpreter.stack.CallStack;
import org.jetbrains.kotlin.ir.interpreter.state.Common;
import org.jetbrains.kotlin.ir.interpreter.state.Complex;
import org.jetbrains.kotlin.ir.interpreter.state.ExceptionState;
import org.jetbrains.kotlin.ir.interpreter.state.Primitive;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.Wrapper;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.platform.JsPlatformKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrInterpreterEnvironment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001SB\u000f\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0000¢\u0006\u0002\u0010\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0017\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001f\u0010:\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010\u00012\u0006\u0010=\u001a\u00020>H\u0000¢\u0006\u0002\b?J\u0006\u0010@\u001a\u00020\u0000J5\u0010A\u001a\u0004\u0018\u00010\u00152\u0006\u0010B\u001a\u00020\u00152\b\b\u0002\u0010C\u001a\u00020D2\b\b\u0002\u0010E\u001a\u00020D2\b\b\u0002\u0010F\u001a\u00020DH\u0000¢\u0006\u0002\bGJ;\u0010H\u001a\u00020\u00152\u0006\u0010B\u001a\u00020\u00152\b\b\u0002\u0010C\u001a\u00020D2\b\b\u0002\u0010E\u001a\u00020D2\b\b\u0002\u0010F\u001a\u00020D2\u0006\u0010I\u001a\u00020\u0015H\u0000¢\u0006\u0002\bJJ\u001d\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020;2\u0006\u0010N\u001a\u00020LH\u0000¢\u0006\u0002\bOJ\u0016\u0010P\u001a\u0004\u0018\u00010$*\u00020$2\u0006\u0010Q\u001a\u00020RH\u0002R\u0014\u0010\f\u001a\u00020\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR*\u0010\u001d\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001e\u0012\u0004\u0012\u00020\u001a0\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001b\u0010#\u001a\u00020$8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b%\u0010&R\u001b\u0010)\u001a\u00020$8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b+\u0010(\u001a\u0004\b*\u0010&R\u001b\u0010,\u001a\u00020$8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b.\u0010(\u001a\u0004\b-\u0010&R\u001b\u0010/\u001a\u00020$8@X\u0080\u0084\u0002¢\u0006\f\n\u0004\b1\u0010(\u001a\u0004\b0\u0010&R&\u00102\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u0002040\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010 \"\u0004\b6\u0010\"R&\u00107\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u0002040\u0013X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010 \"\u0004\b9\u0010\"¨\u0006T"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "", "environment", "(Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;)V", "irModule", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;)V", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "configuration", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;)V", "callStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "getCallStack$ir_interpreter", "()Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "getConfiguration", "()Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterConfiguration;", "functionCache", "", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment$CacheFunctionSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irExceptions", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrExceptions$ir_interpreter", "()Ljava/util/List;", "javaClassToIrClass", "Ljava/lang/Class;", "getJavaClassToIrClass$ir_interpreter", "()Ljava/util/Map;", "setJavaClassToIrClass$ir_interpreter", "(Ljava/util/Map;)V", "kParameterClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getKParameterClass$ir_interpreter", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "kParameterClass$delegate", "Lkotlin/Lazy;", "kTypeClass", "getKTypeClass$ir_interpreter", "kTypeClass$delegate", "kTypeParameterClass", "getKTypeParameterClass$ir_interpreter", "kTypeParameterClass$delegate", "kTypeProjectionClass", "getKTypeProjectionClass$ir_interpreter", "kTypeProjectionClass$delegate", "mapOfEnums", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/state/Complex;", "getMapOfEnums$ir_interpreter", "setMapOfEnums$ir_interpreter", "mapOfObjects", "getMapOfObjects$ir_interpreter", "setMapOfObjects$ir_interpreter", "convertToState", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "value", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "convertToState$ir_interpreter", "copyWithNewCallStack", "getCachedFunction", NativeSymbol.TYPE_NAME, "hasDispatchReceiver", "", "hasExtensionReceiver", "fromDelegatingCall", "getCachedFunction$ir_interpreter", "setCachedFunction", "newFunction", "setCachedFunction$ir_interpreter", "stateToIrExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "state", "original", "stateToIrExpression$ir_interpreter", "getIrClassOfReflectionFromList", "name", "", "CacheFunctionSignature", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInterpreterEnvironment {
    private final CallStack callStack;
    private final IrInterpreterConfiguration configuration;
    private Map<CacheFunctionSignature, IrFunctionSymbol> functionCache;
    private final IrBuiltIns irBuiltIns;
    private final List<IrClass> irExceptions;
    private Map<Class<?>, IrClass> javaClassToIrClass;

    /* JADX INFO: renamed from: kParameterClass$delegate, reason: from kotlin metadata */
    private final Lazy kParameterClass;

    /* JADX INFO: renamed from: kTypeClass$delegate, reason: from kotlin metadata */
    private final Lazy kTypeClass;

    /* JADX INFO: renamed from: kTypeParameterClass$delegate, reason: from kotlin metadata */
    private final Lazy kTypeParameterClass;

    /* JADX INFO: renamed from: kTypeProjectionClass$delegate, reason: from kotlin metadata */
    private final Lazy kTypeProjectionClass;
    private Map<IrSymbol, Complex> mapOfEnums;
    private Map<IrSymbol, Complex> mapOfObjects;

    public IrInterpreterEnvironment(IrBuiltIns irBuiltIns, IrInterpreterConfiguration configuration) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.irBuiltIns = irBuiltIns;
        this.configuration = configuration;
        this.callStack = new CallStack();
        this.irExceptions = new ArrayList();
        this.mapOfEnums = new LinkedHashMap();
        this.mapOfObjects = new LinkedHashMap();
        this.javaClassToIrClass = new LinkedHashMap();
        this.functionCache = new LinkedHashMap();
        this.kTypeParameterClass = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment$kTypeParameterClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                IrInterpreterEnvironment irInterpreterEnvironment = this.this$0;
                IrClassSymbol irClassOfReflectionFromList = irInterpreterEnvironment.getIrClassOfReflectionFromList(irInterpreterEnvironment.getIrBuiltIns().getKClassClass(), "typeParameters");
                Intrinsics.checkNotNull(irClassOfReflectionFromList);
                return irClassOfReflectionFromList;
            }
        });
        this.kParameterClass = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment$kParameterClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                IrInterpreterEnvironment irInterpreterEnvironment = this.this$0;
                IrClassSymbol irClassOfReflectionFromList = irInterpreterEnvironment.getIrClassOfReflectionFromList(irInterpreterEnvironment.getIrBuiltIns().getKFunctionClass(), "parameters");
                Intrinsics.checkNotNull(irClassOfReflectionFromList);
                return irClassOfReflectionFromList;
            }
        });
        this.kTypeProjectionClass = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment$kTypeProjectionClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                IrInterpreterEnvironment irInterpreterEnvironment = this.this$0;
                IrClassSymbol irClassOfReflectionFromList = irInterpreterEnvironment.getIrClassOfReflectionFromList(irInterpreterEnvironment.getKTypeClass$ir_interpreter(), "arguments");
                Intrinsics.checkNotNull(irClassOfReflectionFromList);
                return irClassOfReflectionFromList;
            }
        });
        this.kTypeClass = LazyKt.lazy(new Function0<IrClassSymbol>() { // from class: org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment$kTypeClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrClassSymbol invoke() {
                return this.this$0.getIrBuiltIns().getKTypeClass();
            }
        });
        this.mapOfObjects.put(irBuiltIns.getUnitClass(), new Common(irBuiltIns.getUnitClass().getOwner()));
    }

    public final IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public /* synthetic */ IrInterpreterEnvironment(IrBuiltIns irBuiltIns, IrInterpreterConfiguration irInterpreterConfiguration, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irBuiltIns, (i & 2) != 0 ? new IrInterpreterConfiguration(null, 0, 0, false, false, false, false, 127, null) : irInterpreterConfiguration);
    }

    public final IrInterpreterConfiguration getConfiguration() {
        return this.configuration;
    }

    /* JADX INFO: renamed from: getCallStack$ir_interpreter, reason: from getter */
    public final CallStack getCallStack() {
        return this.callStack;
    }

    public final List<IrClass> getIrExceptions$ir_interpreter() {
        return this.irExceptions;
    }

    public final Map<IrSymbol, Complex> getMapOfEnums$ir_interpreter() {
        return this.mapOfEnums;
    }

    public final void setMapOfEnums$ir_interpreter(Map<IrSymbol, Complex> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.mapOfEnums = map;
    }

    public final Map<IrSymbol, Complex> getMapOfObjects$ir_interpreter() {
        return this.mapOfObjects;
    }

    public final void setMapOfObjects$ir_interpreter(Map<IrSymbol, Complex> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.mapOfObjects = map;
    }

    public final Map<Class<?>, IrClass> getJavaClassToIrClass$ir_interpreter() {
        return this.javaClassToIrClass;
    }

    public final void setJavaClassToIrClass$ir_interpreter(Map<Class<?>, IrClass> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.javaClassToIrClass = map;
    }

    public final IrClassSymbol getKTypeParameterClass$ir_interpreter() {
        return (IrClassSymbol) this.kTypeParameterClass.getValue();
    }

    public final IrClassSymbol getKParameterClass$ir_interpreter() {
        return (IrClassSymbol) this.kParameterClass.getValue();
    }

    public final IrClassSymbol getKTypeProjectionClass$ir_interpreter() {
        return (IrClassSymbol) this.kTypeProjectionClass.getValue();
    }

    public final IrClassSymbol getKTypeClass$ir_interpreter() {
        return (IrClassSymbol) this.kTypeClass.getValue();
    }

    /* JADX INFO: compiled from: IrInterpreterEnvironment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00052\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment$CacheFunctionSignature;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "hasDispatchReceiver", "", "hasExtensionReceiver", "fromDelegatingCall", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;ZZZ)V", "getFromDelegatingCall", "()Z", "getHasDispatchReceiver", "getHasExtensionReceiver", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class CacheFunctionSignature {
        private final boolean fromDelegatingCall;
        private final boolean hasDispatchReceiver;
        private final boolean hasExtensionReceiver;
        private final IrFunctionSymbol symbol;

        public static /* synthetic */ CacheFunctionSignature copy$default(CacheFunctionSignature cacheFunctionSignature, IrFunctionSymbol irFunctionSymbol, boolean z, boolean z2, boolean z3, int i, Object obj) {
            if ((i & 1) != 0) {
                irFunctionSymbol = cacheFunctionSignature.symbol;
            }
            if ((i & 2) != 0) {
                z = cacheFunctionSignature.hasDispatchReceiver;
            }
            if ((i & 4) != 0) {
                z2 = cacheFunctionSignature.hasExtensionReceiver;
            }
            if ((i & 8) != 0) {
                z3 = cacheFunctionSignature.fromDelegatingCall;
            }
            return cacheFunctionSignature.copy(irFunctionSymbol, z, z2, z3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrFunctionSymbol getSymbol() {
            return this.symbol;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasDispatchReceiver() {
            return this.hasDispatchReceiver;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getHasExtensionReceiver() {
            return this.hasExtensionReceiver;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getFromDelegatingCall() {
            return this.fromDelegatingCall;
        }

        public final CacheFunctionSignature copy(IrFunctionSymbol symbol, boolean hasDispatchReceiver, boolean hasExtensionReceiver, boolean fromDelegatingCall) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new CacheFunctionSignature(symbol, hasDispatchReceiver, hasExtensionReceiver, fromDelegatingCall);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CacheFunctionSignature)) {
                return false;
            }
            CacheFunctionSignature cacheFunctionSignature = (CacheFunctionSignature) other;
            return Intrinsics.areEqual(this.symbol, cacheFunctionSignature.symbol) && this.hasDispatchReceiver == cacheFunctionSignature.hasDispatchReceiver && this.hasExtensionReceiver == cacheFunctionSignature.hasExtensionReceiver && this.fromDelegatingCall == cacheFunctionSignature.fromDelegatingCall;
        }

        public int hashCode() {
            return (((((this.symbol.hashCode() * 31) + Boolean.hashCode(this.hasDispatchReceiver)) * 31) + Boolean.hashCode(this.hasExtensionReceiver)) * 31) + Boolean.hashCode(this.fromDelegatingCall);
        }

        public String toString() {
            return "CacheFunctionSignature(symbol=" + this.symbol + ", hasDispatchReceiver=" + this.hasDispatchReceiver + ", hasExtensionReceiver=" + this.hasExtensionReceiver + ", fromDelegatingCall=" + this.fromDelegatingCall + ')';
        }

        public CacheFunctionSignature(IrFunctionSymbol symbol, boolean z, boolean z2, boolean z3) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            this.symbol = symbol;
            this.hasDispatchReceiver = z;
            this.hasExtensionReceiver = z2;
            this.fromDelegatingCall = z3;
        }

        public final IrFunctionSymbol getSymbol() {
            return this.symbol;
        }

        public final boolean getHasDispatchReceiver() {
            return this.hasDispatchReceiver;
        }

        public final boolean getHasExtensionReceiver() {
            return this.hasExtensionReceiver;
        }

        public final boolean getFromDelegatingCall() {
            return this.fromDelegatingCall;
        }
    }

    private IrInterpreterEnvironment(IrInterpreterEnvironment irInterpreterEnvironment) {
        this(irInterpreterEnvironment.irBuiltIns, irInterpreterEnvironment.configuration);
        this.irExceptions.addAll(irInterpreterEnvironment.irExceptions);
        this.mapOfEnums = irInterpreterEnvironment.mapOfEnums;
        this.mapOfObjects = irInterpreterEnvironment.mapOfObjects;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public IrInterpreterEnvironment(IrModuleFragment irModule) {
        this(irModule.getIrBuiltins(), null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(irModule, "irModule");
        List<IrClass> list = this.irExceptions;
        List<IrFile> files = irModule.getFiles();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = files.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((IrFile) it.next()).getDeclarations());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof IrClass) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (IrUtilsKt.isSubclassOf((IrClass) obj2, this.irBuiltIns.getThrowableClass().getOwner())) {
                arrayList3.add(obj2);
            }
        }
        list.addAll(arrayList3);
    }

    public final IrInterpreterEnvironment copyWithNewCallStack() {
        return new IrInterpreterEnvironment(this);
    }

    public static /* synthetic */ IrFunctionSymbol getCachedFunction$ir_interpreter$default(IrInterpreterEnvironment irInterpreterEnvironment, IrFunctionSymbol irFunctionSymbol, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        if ((i & 8) != 0) {
            z3 = false;
        }
        return irInterpreterEnvironment.getCachedFunction$ir_interpreter(irFunctionSymbol, z, z2, z3);
    }

    public final IrFunctionSymbol getCachedFunction$ir_interpreter(IrFunctionSymbol symbol, boolean hasDispatchReceiver, boolean hasExtensionReceiver, boolean fromDelegatingCall) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.functionCache.get(new CacheFunctionSignature(symbol, hasDispatchReceiver, hasExtensionReceiver, fromDelegatingCall));
    }

    public final IrFunctionSymbol setCachedFunction$ir_interpreter(IrFunctionSymbol symbol, boolean hasDispatchReceiver, boolean hasExtensionReceiver, boolean fromDelegatingCall, IrFunctionSymbol newFunction) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(newFunction, "newFunction");
        this.functionCache.put(new CacheFunctionSignature(symbol, hasDispatchReceiver, hasExtensionReceiver, fromDelegatingCall), newFunction);
        return newFunction;
    }

    public final State convertToState$ir_interpreter(Object value, IrType irType) {
        Wrapper wrapper;
        IrClass owner;
        Intrinsics.checkNotNullParameter(irType, "irType");
        if (value instanceof Proxy) {
            return ((Proxy) value).getState();
        }
        if (value instanceof State) {
            return (State) value;
        }
        if (value instanceof Boolean ? true : value instanceof Character ? true : value instanceof Byte ? true : value instanceof Short ? true : value instanceof Integer ? true : value instanceof Long ? true : value instanceof String ? true : value instanceof Float ? true : value instanceof Double ? true : value instanceof Object[] ? true : value instanceof byte[] ? true : value instanceof char[] ? true : value instanceof short[] ? true : value instanceof int[] ? true : value instanceof long[] ? true : value instanceof float[] ? true : value instanceof double[] ? true : value instanceof boolean[]) {
            return new Primitive(value, irType);
        }
        if (value == null) {
            return Primitive.INSTANCE.nullStateOfType(irType);
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        if (classOrNull == null || (owner = classOrNull.getOwner()) == null) {
            IrClass irClass = this.javaClassToIrClass.get(value.getClass());
            Intrinsics.checkNotNull(irClass);
            wrapper = new Wrapper(value, irClass, this);
        } else {
            wrapper = new Wrapper(value, owner, this);
        }
        return wrapper;
    }

    public final IrExpression stateToIrExpression$ir_interpreter(State state, IrExpression original) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(original, "original");
        int startOffset = original.getStartOffset();
        int endOffset = original.getEndOffset();
        IrType irTypeMakeNotNull = IrTypesKt.makeNotNull(original.getType());
        if (state instanceof Primitive) {
            if (JsPlatformKt.isJs(this.configuration.getPlatform())) {
                Primitive primitive = (Primitive) state;
                if (primitive.getValue() instanceof Float) {
                    return IrConstImpl.INSTANCE.m7210float(startOffset, endOffset, irTypeMakeNotNull, ((Number) primitive.getValue()).floatValue());
                }
            }
            if (JsPlatformKt.isJs(this.configuration.getPlatform())) {
                Primitive primitive2 = (Primitive) state;
                if (primitive2.getValue() instanceof Double) {
                    return IrConstImpl.INSTANCE.m7209double(startOffset, endOffset, irTypeMakeNotNull, ((Number) primitive2.getValue()).doubleValue());
                }
            }
            Primitive primitive3 = (Primitive) state;
            return (primitive3.getValue() == null || IrTypePredicatesKt.isPrimitiveType$default(irTypeMakeNotNull, false, 1, null) || IrTypePredicatesKt.isString(irTypeMakeNotNull)) ? IrUtilsKt.toIrConst(primitive3.getValue(), irTypeMakeNotNull, startOffset, endOffset) : original;
        }
        if (state instanceof ExceptionState) {
            return new IrErrorExpressionImpl(original.getStartOffset(), original.getEndOffset(), original.getType(), this.configuration.getPrintOnlyExceptionMessage() ? ((ExceptionState) state).getShortDescription() : "\n" + ((ExceptionState) state).getFullDescription());
        }
        if (!(state instanceof Complex) || !IrTypePredicatesKt.isUnsignedType$default(IrUtilsKt.getDefaultType(state.getIrClass()), false, 1, null)) {
            return original;
        }
        Object objSingle = CollectionsKt.single(state.getFields().values());
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.Primitive<*>");
        return IrUtilsKt.toIrConst(((Primitive) objSingle).getValue(), irTypeMakeNotNull, startOffset, endOffset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClassSymbol getIrClassOfReflectionFromList(IrClassSymbol irClassSymbol, String str) {
        List<IrTypeArgument> arguments;
        IrTypeArgument irTypeArgument;
        IrType typeOrNull;
        IrSimpleFunction getter;
        Iterator<IrProperty> it = IrUtilsKt.getProperties(irClassSymbol.getOwner()).iterator();
        boolean z = false;
        IrProperty irProperty = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            IrProperty next = it.next();
            if (Intrinsics.areEqual(next.getName().asString(), str)) {
                if (!z) {
                    z = true;
                    irProperty = next;
                }
            }
            irProperty = null;
            break;
        }
        IrProperty irProperty2 = irProperty;
        IrType returnType = (irProperty2 == null || (getter = irProperty2.getGetter()) == null) ? null : getter.getReturnType();
        IrSimpleType irSimpleType = returnType instanceof IrSimpleType ? (IrSimpleType) returnType : null;
        if (irSimpleType == null || (arguments = irSimpleType.getArguments()) == null || (irTypeArgument = (IrTypeArgument) CollectionsKt.single((List) arguments)) == null || (typeOrNull = IrTypesKt.getTypeOrNull(irTypeArgument)) == null) {
            return null;
        }
        return IrTypesKt.getClassOrNull(typeOrNull);
    }
}
