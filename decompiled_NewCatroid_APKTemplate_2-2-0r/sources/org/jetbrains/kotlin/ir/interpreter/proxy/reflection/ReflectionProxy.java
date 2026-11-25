package org.jetbrains.kotlin.ir.interpreter.proxy.reflection;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.proxy.Proxy;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KClassState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KFunctionState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KParameterState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KPropertyState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeParameterState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.KTypeState;
import org.jetbrains.kotlin.ir.interpreter.state.reflection.ReflectionState;

/* JADX INFO: compiled from: ReflectionProxy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b`\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005J\u000e\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u0004H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/Proxy;", "toKVisibility", "Lkotlin/reflect/KVisibility;", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ReflectionProxy extends Proxy {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default KVisibility toKVisibility(DescriptorVisibility descriptorVisibility) {
        Intrinsics.checkNotNullParameter(descriptorVisibility, "<this>");
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PUBLIC)) {
            return KVisibility.PUBLIC;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PROTECTED)) {
            return KVisibility.PROTECTED;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.INTERNAL)) {
            return KVisibility.INTERNAL;
        }
        if (Intrinsics.areEqual(descriptorVisibility, DescriptorVisibilities.PRIVATE)) {
            return KVisibility.PRIVATE;
        }
        return null;
    }

    /* JADX INFO: compiled from: ReflectionProxy.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000¢\u0006\u0002\b\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy$Companion;", "", "()V", "asProxy", "Lorg/jetbrains/kotlin/ir/interpreter/proxy/reflection/ReflectionProxy;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "asProxy$ir_interpreter", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ReflectionProxy asProxy$ir_interpreter(ReflectionState reflectionState, CallInterceptor callInterceptor) {
            ReflectionProxy kProperty2Proxy;
            Intrinsics.checkNotNullParameter(reflectionState, "<this>");
            Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
            if (reflectionState instanceof KPropertyState) {
                KPropertyState kPropertyState = (KPropertyState) reflectionState;
                if (kPropertyState.isKMutableProperty0()) {
                    kProperty2Proxy = new KMutableProperty0Proxy(kPropertyState, callInterceptor);
                } else if (kPropertyState.isKProperty0()) {
                    kProperty2Proxy = new KProperty0Proxy(kPropertyState, callInterceptor);
                } else if (kPropertyState.isKMutableProperty1()) {
                    kProperty2Proxy = new KMutableProperty1Proxy(kPropertyState, callInterceptor);
                } else if (kPropertyState.isKProperty1()) {
                    kProperty2Proxy = new KProperty1Proxy(kPropertyState, callInterceptor);
                } else if (kPropertyState.isKMutableProperty2()) {
                    kProperty2Proxy = new KMutableProperty2Proxy(kPropertyState, callInterceptor);
                } else {
                    if (!kPropertyState.isKProperty2()) {
                        throw new NotImplementedError(null, 1, null);
                    }
                    kProperty2Proxy = new KProperty2Proxy(kPropertyState, callInterceptor);
                }
                return kProperty2Proxy;
            }
            if (reflectionState instanceof KFunctionState) {
                return new KFunctionProxy((KFunctionState) reflectionState, callInterceptor);
            }
            if (reflectionState instanceof KClassState) {
                return new KClassProxy((KClassState) reflectionState, callInterceptor);
            }
            if (reflectionState instanceof KTypeState) {
                return new KTypeProxy((KTypeState) reflectionState, callInterceptor);
            }
            if (reflectionState instanceof KTypeParameterState) {
                return new KTypeParameterProxy((KTypeParameterState) reflectionState, callInterceptor);
            }
            if (reflectionState instanceof KParameterState) {
                return new KParameterProxy((KParameterState) reflectionState, callInterceptor);
            }
            throw new NotImplementedError("An operation is not implemented: not supported reference state");
        }
    }
}
