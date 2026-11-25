package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterAssertionError;
import org.jetbrains.kotlin.ir.interpreter.proxy.Proxy;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateKt;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KClassState;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: KClassProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010>\u001a\u00020\u00182\b\u0010?\u001a\u0004\u0018\u00010@H\u0096\u0002J\b\u0010A\u001a\u00020BH\u0016J\u0012\u0010C\u001a\u00020\u00182\b\u0010D\u001a\u0004\u0018\u00010@H\u0016J\b\u0010E\u001a\u00020+H\u0016R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR&\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00120\u00118VX\u0096\u0004¢\u0006\f\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0019R\u0014\u0010\u001b\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u0014\u0010\u001c\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0019R\u0014\u0010\u001d\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0019R\u0014\u0010\u001e\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0019R\u0014\u0010\u001f\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0019R\u0014\u0010 \u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0019R\u0014\u0010!\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0019R\u001e\u0010\"\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030#0\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u0016R\u001e\u0010%\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0016R\u0016\u0010'\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0016\u0010*\u001a\u0004\u0018\u00010+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\"\u0010.\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\rR\u0016\u00100\u001a\u0004\u0018\u00010+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010-R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u001a\u00104\u001a\b\u0012\u0004\u0012\u0002050\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u0010\rR\u001a\u00107\u001a\b\u0012\u0004\u0012\u0002080\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b9\u0010\rR\u0016\u0010:\u001a\u0004\u0018\u00010;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/KClassProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lkotlin/reflect/KClass;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/Proxy;", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KClassState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KClassState;Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;)V", "annotations", "", "", "getAnnotations", "()Ljava/util/List;", "getCallInterceptor", "()Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "constructors", "", "Lkotlin/reflect/KFunction;", "getConstructors$annotations", "()V", "getConstructors", "()Ljava/util/Collection;", "isAbstract", "", "()Z", "isCompanion", "isData", "isFinal", "isFun", "isInner", "isOpen", "isSealed", "isValue", "members", "Lkotlin/reflect/KCallable;", "getMembers", "nestedClasses", "getNestedClasses", "objectInstance", "getObjectInstance", "()Lorg/jetbrains/kotlin/ir/interpreter/proxy/Proxy;", "qualifiedName", "", "getQualifiedName", "()Ljava/lang/String;", "sealedSubclasses", "getSealedSubclasses", Namer.METADATA_SIMPLE_NAME, "getSimpleName", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KClassState;", "supertypes", "Lkotlin/reflect/KType;", "getSupertypes", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "isInstance", "value", "toString", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassProxy implements KClass<Proxy>, ReflectionProxy {
    private final CallInterceptor callInterceptor;
    private final KClassState state;

    public static /* synthetic */ void getConstructors$annotations() {
    }

    public KClassProxy(KClassState state, CallInterceptor callInterceptor) {
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
    public KClassState getState() {
        return this.state;
    }

    @Override // kotlin.reflect.KClass
    public String getSimpleName() {
        Name name2 = getState().getClassReference().getName();
        if (name2.isSpecial()) {
            name2 = null;
        }
        if (name2 != null) {
            return name2.asString();
        }
        return null;
    }

    @Override // kotlin.reflect.KClass
    public String getQualifiedName() {
        if (getState().getClassReference().getName().isSpecial()) {
            return null;
        }
        return UtilsKt.internalName(getState().getClassReference());
    }

    @Override // kotlin.reflect.KClass
    public Collection<KFunction<Proxy>> getConstructors() {
        Collection constructors = getState().getConstructors(getCallInterceptor());
        Intrinsics.checkNotNull(constructors, "null cannot be cast to non-null type kotlin.collections.Collection<kotlin.reflect.KFunction<org.jetbrains.kotlin.ir.interpreter.proxy.Proxy>>");
        return constructors;
    }

    @Override // kotlin.reflect.KClass, kotlin.reflect.KDeclarationContainer
    public Collection<KCallable<?>> getMembers() {
        return getState().getMembers(getCallInterceptor());
    }

    @Override // kotlin.reflect.KClass
    public Collection<KClass<?>> getNestedClasses() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.reflect.KClass
    public Proxy getObjectInstance() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.reflect.KClass
    public List<KTypeParameter> getTypeParameters() {
        return getState().getTypeParameters(getCallInterceptor());
    }

    @Override // kotlin.reflect.KClass
    public List<KType> getSupertypes() {
        return getState().getSupertypes(getCallInterceptor());
    }

    @Override // kotlin.reflect.KClass
    public List<KClass<? extends Proxy>> getSealedSubclasses() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.reflect.KAnnotatedElement
    public List<Annotation> getAnnotations() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // kotlin.reflect.KClass
    public KVisibility getVisibility() {
        return toKVisibility(getState().getClassReference().getVisibility());
    }

    @Override // kotlin.reflect.KClass
    public boolean isFinal() {
        return getState().getClassReference().getModality() == Modality.FINAL;
    }

    @Override // kotlin.reflect.KClass
    public boolean isOpen() {
        return getState().getClassReference().getModality() == Modality.OPEN;
    }

    @Override // kotlin.reflect.KClass
    public boolean isAbstract() {
        return getState().getClassReference().getModality() == Modality.ABSTRACT;
    }

    @Override // kotlin.reflect.KClass
    public boolean isSealed() {
        return getState().getClassReference().getModality() == Modality.SEALED;
    }

    @Override // kotlin.reflect.KClass
    public boolean isData() {
        return getState().getClassReference().getIsData();
    }

    @Override // kotlin.reflect.KClass
    public boolean isInner() {
        return getState().getClassReference().getIsInner();
    }

    @Override // kotlin.reflect.KClass
    public boolean isCompanion() {
        return getState().getClassReference().getIsCompanion();
    }

    @Override // kotlin.reflect.KClass
    public boolean isFun() {
        return getState().getClassReference().getIsFun();
    }

    @Override // kotlin.reflect.KClass
    public boolean isValue() {
        return getState().getClassReference().getIsValue();
    }

    @Override // kotlin.reflect.KClass
    public boolean isInstance(Object value) throws InterpreterAssertionError {
        if (value instanceof State) {
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.State");
            return StateKt.isSubtypeOf((State) value, IrUtilsKt.getDefaultType(getState().getClassReference()));
        }
        throw new InterpreterAssertionError(("Cannot interpret `isInstance` method for " + value).toString());
    }

    @Override // kotlin.reflect.KClass
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof KClassProxy) {
            return Intrinsics.areEqual(getState(), ((KClassProxy) other).getState());
        }
        return false;
    }

    @Override // kotlin.reflect.KClass
    public int hashCode() {
        return getState().hashCode();
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.proxy.Proxy
    public String toString() {
        return getState().toString();
    }
}
