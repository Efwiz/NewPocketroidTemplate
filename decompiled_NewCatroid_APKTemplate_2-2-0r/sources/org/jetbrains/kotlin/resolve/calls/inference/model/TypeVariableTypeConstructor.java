package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\b\u0010\u0013\u001a\u00020\u0004H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0017H\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J\b\u0010\u001b\u001a\u00020\rH\u0016J\b\u0010\u001c\u001a\u00020\rH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0017J\b\u0010!\u001a\u00020\u0006H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableTypeConstructor;", "Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "debugName", "", "originalTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Ljava/lang/String;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", "getDebugName", "()Ljava/lang/String;", "isContainedInInvariantOrContravariantPositions", "", "()Z", "setContainedInInvariantOrContravariantPositions", "(Z)V", "getOriginalTypeParameter", "()Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getBuiltIns", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getParameters", "", "getSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "isDenotable", "isFinal", "refine", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "toString", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableTypeConstructor implements NewTypeVariableConstructor, TypeVariableTypeConstructorMarker {
    private final KotlinBuiltIns builtIns;
    private final String debugName;
    private boolean isContainedInInvariantOrContravariantPositions;
    private final TypeParameterDescriptor originalTypeParameter;

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public ClassifierDescriptor getThis$0() {
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isFinal() {
        return false;
    }

    public TypeVariableTypeConstructor(KotlinBuiltIns builtIns, String debugName, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        this.builtIns = builtIns;
        this.debugName = debugName;
        this.originalTypeParameter = typeParameterDescriptor;
    }

    public final String getDebugName() {
        return this.debugName;
    }

    @Override // org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor
    public TypeParameterDescriptor getOriginalTypeParameter() {
        return this.originalTypeParameter;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public Collection<KotlinType> mo7438getSupertypes() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public String toString() {
        return "TypeVariable(" + this.debugName + ')';
    }

    /* JADX INFO: renamed from: isContainedInInvariantOrContravariantPositions, reason: from getter */
    public final boolean getIsContainedInInvariantOrContravariantPositions() {
        return this.isContainedInInvariantOrContravariantPositions;
    }

    public final void setContainedInInvariantOrContravariantPositions(boolean z) {
        this.isContainedInInvariantOrContravariantPositions = z;
    }
}
