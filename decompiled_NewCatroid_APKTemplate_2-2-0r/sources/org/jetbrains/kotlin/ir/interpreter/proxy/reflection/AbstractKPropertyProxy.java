package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: AbstractKPropertyProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b \u0018\u00002\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002:\u0002?@B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ'\u00100\u001a\u0004\u0018\u00010\u00032\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u000302\"\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u00103J \u00104\u001a\u0004\u0018\u00010\u00032\u0014\u00101\u001a\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u000305H\u0016J\u0018\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u000209H\u0004J\u0013\u0010;\u001a\u00020\u00112\b\u0010<\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010=\u001a\u000209H\u0016J\b\u0010>\u001a\u00020\u0019H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012R\u0014\u0010\u0016\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0012R\u0014\u0010\u0017\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0012R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\rR\u0014\u0010\u001f\u001a\u00020 8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020$8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\rR\u0016\u0010,\u001a\u0004\u0018\u00010-8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lkotlin/reflect/KProperty;", "", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "isAbstract", "", "()Z", "isConst", "isFinal", "isLateinit", "isOpen", "isSuspend", "name", "", "getName", "()Ljava/lang/String;", "parameters", "Lkotlin/reflect/KParameter;", "getParameters", "propertyType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getPropertyType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "returnType", "Lkotlin/reflect/KType;", "getReturnType", "()Lkotlin/reflect/KType;", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KPropertyState;", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "call", "args", "", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_DESCRIPTOR, "callBy", "", "checkArguments", "", "expected", "", "actual", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "Getter", "Setter", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKPropertyProxy implements KProperty<Object>, ReflectionProxy {
    private final CallInterceptor callInterceptor;
    private final KPropertyState state;

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isSuspend */
    public boolean getIsSuspend() {
        return false;
    }

    public AbstractKPropertyProxy(KPropertyState state, CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        this.state = state;
        this.callInterceptor = callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public CallInterceptor getCallInterceptor() {
        return this.callInterceptor;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public KPropertyState getState() {
        return this.state;
    }

    protected final IrType getPropertyType() {
        IrSimpleFunction getter = getState().getProperty().getGetter();
        Intrinsics.checkNotNull(getter);
        return getter.getReturnType();
    }

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isAbstract */
    public boolean getIsAbstract() {
        return getState().getProperty().getModality() == Modality.ABSTRACT;
    }

    @Override // kotlin.reflect.KProperty
    public boolean isConst() {
        return getState().getProperty().getIsConst();
    }

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isFinal */
    public boolean getIsFinal() {
        return getState().getProperty().getModality() == Modality.FINAL;
    }

    @Override // kotlin.reflect.KProperty
    public boolean isLateinit() {
        return getState().getProperty().getIsLateinit();
    }

    @Override // kotlin.reflect.KCallable
    /* JADX INFO: renamed from: isOpen */
    public boolean getIsOpen() {
        return getState().getProperty().getModality() == Modality.OPEN;
    }

    @Override // kotlin.reflect.KCallable
    public String getName() {
        String strAsString = getState().getProperty().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // kotlin.reflect.KAnnotatedElement
    public List<Annotation> getAnnotations() {
        throw new NotImplementedError("An operation is not implemented: not implemented");
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
        return CollectionsKt.emptyList();
    }

    @Override // kotlin.reflect.KCallable
    public KVisibility getVisibility() {
        return toKVisibility(getState().getProperty().getVisibility());
    }

    @Override // kotlin.reflect.KCallable
    public Object call(Object... args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return getGetter().call(Arrays.copyOf(args, args.length));
    }

    @Override // kotlin.reflect.KCallable
    public Object callBy(Map<KParameter, ? extends Object> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return getGetter().callBy(args);
    }

    protected final void checkArguments(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalArgumentException("Callable expects " + expected + " arguments, but " + actual + " were provided.");
        }
    }

    /* JADX INFO: compiled from: AbstractKPropertyProxy.kt */
    @Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b¦\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0014\u0010\u0014\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0014\u0010\u0015\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0014\u0010\u0016\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\nR\u001c\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u001fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\nR\u0016\u0010)\u001a\u0004\u0018\u00010*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Getter;", "Lkotlin/reflect/KProperty$Getter;", "", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isAbstract", "", "()Z", "isExternal", "isFinal", "isInfix", "isInline", "isOpen", "isOperator", "isSuspend", "name", "", "getName", "()Ljava/lang/String;", "parameters", "Lkotlin/reflect/KParameter;", "getParameters", "property", "Lkotlin/reflect/KProperty;", "getProperty", "()Lkotlin/reflect/KProperty;", "returnType", "Lkotlin/reflect/KType;", "getReturnType", "()Lkotlin/reflect/KType;", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public abstract class Getter implements KProperty.Getter<Object> {
        private final IrSimpleFunction getter;
        private final boolean isAbstract;
        private final boolean isExternal;
        private final boolean isFinal;
        private final boolean isInfix;
        private final boolean isInline;
        private final boolean isOpen;
        private final boolean isOperator;
        private final boolean isSuspend;
        private final String name;
        private final KProperty<Object> property;
        final /* synthetic */ AbstractKPropertyProxy this$0;
        private final KVisibility visibility;

        public Getter(AbstractKPropertyProxy abstractKPropertyProxy, IrSimpleFunction getter) {
            Intrinsics.checkNotNullParameter(getter, "getter");
            this.this$0 = abstractKPropertyProxy;
            this.getter = getter;
            this.property = abstractKPropertyProxy;
            this.name = "<get-" + CapitalizeDecapitalizeKt.capitalizeAsciiOnly(abstractKPropertyProxy.getName()) + '>';
            this.isInline = getter.getIsInline();
            this.isExternal = getter.getIsExternal();
            this.isOperator = getter.getIsOperator();
            this.isInfix = getter.getIsInfix();
            this.isSuspend = getter.getIsSuspend();
            this.visibility = abstractKPropertyProxy.toKVisibility(getter.getVisibility());
            this.isFinal = getter.getModality() == Modality.FINAL;
            this.isOpen = getter.getModality() == Modality.OPEN;
            this.isAbstract = getter.getModality() == Modality.ABSTRACT;
        }

        public final IrSimpleFunction getGetter() {
            return this.getter;
        }

        @Override // kotlin.reflect.KProperty.Accessor
        public KProperty<Object> getProperty() {
            return this.property;
        }

        @Override // kotlin.reflect.KCallable
        public String getName() {
            return this.name;
        }

        @Override // kotlin.reflect.KAnnotatedElement
        public List<Annotation> getAnnotations() {
            return this.this$0.getAnnotations();
        }

        @Override // kotlin.reflect.KCallable
        public List<KParameter> getParameters() {
            return this.this$0.getParameters();
        }

        @Override // kotlin.reflect.KCallable
        public KType getReturnType() {
            return this.this$0.getReturnType();
        }

        @Override // kotlin.reflect.KCallable
        public List<KTypeParameter> getTypeParameters() {
            return this.this$0.getTypeParameters();
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isInline, reason: from getter */
        public boolean getIsInline() {
            return this.isInline;
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isExternal, reason: from getter */
        public boolean getIsExternal() {
            return this.isExternal;
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isOperator, reason: from getter */
        public boolean getIsOperator() {
            return this.isOperator;
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isInfix, reason: from getter */
        public boolean getIsInfix() {
            return this.isInfix;
        }

        @Override // kotlin.reflect.KFunction, kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isSuspend, reason: from getter */
        public boolean getIsSuspend() {
            return this.isSuspend;
        }

        @Override // kotlin.reflect.KCallable
        public KVisibility getVisibility() {
            return this.visibility;
        }

        @Override // kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isFinal, reason: from getter */
        public boolean getIsFinal() {
            return this.isFinal;
        }

        @Override // kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isOpen, reason: from getter */
        public boolean getIsOpen() {
            return this.isOpen;
        }

        @Override // kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isAbstract, reason: from getter */
        public boolean getIsAbstract() {
            return this.isAbstract;
        }
    }

    /* JADX INFO: compiled from: AbstractKPropertyProxy.kt */
    @Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b¦\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0014\u0010\u000e\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u000f\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0014\u0010\u0011\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0014\u0010\u0012\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0014\u0010\u0013\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u0014\u0010\u0014\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\rR\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\nR\u001c\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\nR\u0016\u0010)\u001a\u0004\u0018\u00010*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy$Setter;", "Lkotlin/reflect/KMutableProperty$Setter;", "", "setter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/AbstractKPropertyProxy;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "isAbstract", "", "()Z", "isExternal", "isFinal", "isInfix", "isInline", "isOpen", "isOperator", "isSuspend", "name", "", "getName", "()Ljava/lang/String;", "parameters", "Lkotlin/reflect/KParameter;", "getParameters", "property", "Lkotlin/reflect/KProperty;", "getProperty", "()Lkotlin/reflect/KProperty;", "returnType", "Lkotlin/reflect/KType;", "getReturnType", "()Lkotlin/reflect/KType;", "getSetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public abstract class Setter implements KMutableProperty.Setter<Object> {
        private final boolean isAbstract;
        private final boolean isExternal;
        private final boolean isFinal;
        private final boolean isInfix;
        private final boolean isInline;
        private final boolean isOpen;
        private final boolean isOperator;
        private final boolean isSuspend;
        private final String name;
        private final KProperty<Object> property;
        private final IrSimpleFunction setter;
        final /* synthetic */ AbstractKPropertyProxy this$0;
        private final KVisibility visibility;

        public Setter(AbstractKPropertyProxy abstractKPropertyProxy, IrSimpleFunction setter) {
            Intrinsics.checkNotNullParameter(setter, "setter");
            this.this$0 = abstractKPropertyProxy;
            this.setter = setter;
            this.property = abstractKPropertyProxy;
            this.name = "<set-" + CapitalizeDecapitalizeKt.capitalizeAsciiOnly(abstractKPropertyProxy.getName()) + '>';
            this.isInline = setter.getIsInline();
            this.isExternal = setter.getIsExternal();
            this.isOperator = setter.getIsOperator();
            this.isInfix = setter.getIsInfix();
            this.isSuspend = setter.getIsSuspend();
            this.visibility = abstractKPropertyProxy.toKVisibility(setter.getVisibility());
            this.isFinal = setter.getModality() == Modality.FINAL;
            this.isOpen = setter.getModality() == Modality.OPEN;
            this.isAbstract = setter.getModality() == Modality.ABSTRACT;
        }

        public final IrSimpleFunction getSetter() {
            return this.setter;
        }

        @Override // kotlin.reflect.KProperty.Accessor
        public KProperty<Object> getProperty() {
            return this.property;
        }

        @Override // kotlin.reflect.KCallable
        public String getName() {
            return this.name;
        }

        @Override // kotlin.reflect.KAnnotatedElement
        public List<Annotation> getAnnotations() {
            return this.this$0.getAnnotations();
        }

        @Override // kotlin.reflect.KCallable
        public List<KParameter> getParameters() {
            return this.this$0.getParameters();
        }

        @Override // kotlin.reflect.KCallable
        public KType getReturnType() {
            return this.this$0.getReturnType();
        }

        @Override // kotlin.reflect.KCallable
        public List<KTypeParameter> getTypeParameters() {
            return this.this$0.getTypeParameters();
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isInline, reason: from getter */
        public boolean getIsInline() {
            return this.isInline;
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isExternal, reason: from getter */
        public boolean getIsExternal() {
            return this.isExternal;
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isOperator, reason: from getter */
        public boolean getIsOperator() {
            return this.isOperator;
        }

        @Override // kotlin.reflect.KFunction
        /* JADX INFO: renamed from: isInfix, reason: from getter */
        public boolean getIsInfix() {
            return this.isInfix;
        }

        @Override // kotlin.reflect.KFunction, kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isSuspend, reason: from getter */
        public boolean getIsSuspend() {
            return this.isSuspend;
        }

        @Override // kotlin.reflect.KCallable
        public KVisibility getVisibility() {
            return this.visibility;
        }

        @Override // kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isFinal, reason: from getter */
        public boolean getIsFinal() {
            return this.isFinal;
        }

        @Override // kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isOpen, reason: from getter */
        public boolean getIsOpen() {
            return this.isOpen;
        }

        @Override // kotlin.reflect.KCallable
        /* JADX INFO: renamed from: isAbstract, reason: from getter */
        public boolean getIsAbstract() {
            return this.isAbstract;
        }
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public boolean equals(Object other) {
        if (other instanceof AbstractKPropertyProxy) {
            return Intrinsics.areEqual(getState(), ((AbstractKPropertyProxy) other).getState());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public int hashCode() {
        return getState().hashCode();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public String toString() {
        return getState().toString();
    }
}
