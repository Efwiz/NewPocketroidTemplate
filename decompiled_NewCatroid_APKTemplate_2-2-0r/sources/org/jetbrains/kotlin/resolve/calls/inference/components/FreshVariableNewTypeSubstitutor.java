package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "freshVariables", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableFromCallableDescriptor;", "(Ljava/util/List;)V", "getFreshVariables", "()Ljava/util/List;", "isEmpty", "", "()Z", "substituteNotNullTypeWithConstructor", "Lorg/jetbrains/kotlin/types/UnwrappedType;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FreshVariableNewTypeSubstitutor implements NewTypeSubstitutor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FreshVariableNewTypeSubstitutor Empty = new FreshVariableNewTypeSubstitutor(CollectionsKt.emptyList());
    private final List<TypeVariableFromCallableDescriptor> freshVariables;

    public FreshVariableNewTypeSubstitutor(List<TypeVariableFromCallableDescriptor> freshVariables) {
        Intrinsics.checkNotNullParameter(freshVariables, "freshVariables");
        this.freshVariables = freshVariables;
    }

    public final List<TypeVariableFromCallableDescriptor> getFreshVariables() {
        return this.freshVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = constructor.getThis$0();
        TypeParameterDescriptor typeParameterDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        if (typeParameterDescriptor == null) {
            return null;
        }
        TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = (TypeVariableFromCallableDescriptor) CollectionsKt.getOrNull(this.freshVariables, typeParameterDescriptor.getIndex());
        if (typeVariableFromCallableDescriptor != null && Intrinsics.areEqual(typeVariableFromCallableDescriptor.getOriginalTypeParameter().getTypeConstructor(), constructor)) {
            return typeVariableFromCallableDescriptor.getDefaultType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
    public boolean isEmpty() {
        return this.freshVariables.isEmpty();
    }

    /* JADX INFO: compiled from: NewTypeSubstitutor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor$Companion;", "", "()V", "Empty", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getEmpty", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FreshVariableNewTypeSubstitutor getEmpty() {
            return FreshVariableNewTypeSubstitutor.Empty;
        }
    }
}
