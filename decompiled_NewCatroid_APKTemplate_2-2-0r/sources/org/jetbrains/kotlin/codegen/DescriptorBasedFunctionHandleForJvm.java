package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: JvmBridgesImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0003H\u0014J\u0013\u0010\u0017\u001a\u00020\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0011\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DescriptorBasedFunctionHandleForJvm;", "Lorg/jetbrains/kotlin/backend/common/bridges/DescriptorBasedFunctionHandle;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "asmMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getAsmMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "asmMethod$delegate", "Lkotlin/Lazy;", "isAbstract", "", "()Z", "isDeclaration", "mayBeUsedAsSuperImplementation", "getMayBeUsedAsSuperImplementation", "mightBeIncorrectCode", "getMightBeIncorrectCode", "createHandleForOverridden", "overridden", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorBasedFunctionHandleForJvm extends DescriptorBasedFunctionHandle {

    /* JADX INFO: renamed from: asmMethod$delegate, reason: from kotlin metadata */
    private final Lazy asmMethod;
    private final boolean isAbstract;
    private final boolean isDeclaration;
    private final boolean mayBeUsedAsSuperImplementation;
    private final GenerationState state;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DescriptorBasedFunctionHandleForJvm(final FunctionDescriptor descriptor, GenerationState state) {
        super(descriptor);
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
        this.isAbstract = super.getIsAbstract() || JvmBridgesImplKt.isAbstractOnJvmIgnoringActualModality(descriptor, state.getJvmDefaultMode());
        this.mayBeUsedAsSuperImplementation = super.getMayBeUsedAsSuperImplementation() || JvmBridgesImplKt.isJvmDefaultOrPlatformDependent(descriptor, state.getJvmDefaultMode());
        this.asmMethod = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Method>() { // from class: org.jetbrains.kotlin.codegen.DescriptorBasedFunctionHandleForJvm$asmMethod$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Method invoke() {
                return this.this$0.state.getTypeMapper().mapAsmMethod(descriptor);
            }
        });
        this.isDeclaration = descriptor.getKind().isReal() || JvmBridgesImplKt.needToGenerateDelegationToDefaultImpls(descriptor, state.getJvmDefaultMode());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle
    public DescriptorBasedFunctionHandleForJvm createHandleForOverridden(FunctionDescriptor overridden) {
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        return new DescriptorBasedFunctionHandleForJvm(overridden, this.state);
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle, org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    /* JADX INFO: renamed from: isAbstract, reason: from getter */
    public boolean getIsAbstract() {
        return this.isAbstract;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle, org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    public boolean getMayBeUsedAsSuperImplementation() {
        return this.mayBeUsedAsSuperImplementation;
    }

    private final Method getAsmMethod() {
        return (Method) this.asmMethod.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle, org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    /* JADX INFO: renamed from: isDeclaration, reason: from getter */
    public boolean getIsDeclaration() {
        return this.isDeclaration;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.FunctionHandle
    public boolean getMightBeIncorrectCode() {
        return this.state.getClassBuilderMode().mightBeIncorrectCode;
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle
    public int hashCode() {
        return (((JvmBridgesImplKt.containerEntityForEqualityAndHashCode(getDescriptor()).hashCode() * 31) + Boolean.hashCode(JvmBridgesImplKt.isJavaForKotlinOverrideProperty(getDescriptor()))) * 31) + getAsmMethod().hashCode();
    }

    @Override // org.jetbrains.kotlin.backend.common.bridges.DescriptorBasedFunctionHandle
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof DescriptorBasedFunctionHandleForJvm) {
            DescriptorBasedFunctionHandleForJvm descriptorBasedFunctionHandleForJvm = (DescriptorBasedFunctionHandleForJvm) other;
            if (Intrinsics.areEqual(getAsmMethod(), descriptorBasedFunctionHandleForJvm.getAsmMethod()) && Intrinsics.areEqual(JvmBridgesImplKt.containerEntityForEqualityAndHashCode(getDescriptor()), JvmBridgesImplKt.containerEntityForEqualityAndHashCode(descriptorBasedFunctionHandleForJvm.getDescriptor())) && JvmBridgesImplKt.isJavaForKotlinOverrideProperty(getDescriptor()) == JvmBridgesImplKt.isJavaForKotlinOverrideProperty(descriptorBasedFunctionHandleForJvm.getDescriptor())) {
                return true;
            }
        }
        return false;
    }
}
