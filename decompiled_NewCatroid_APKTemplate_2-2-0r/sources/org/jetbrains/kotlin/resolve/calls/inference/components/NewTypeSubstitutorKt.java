package org.jetbrains.kotlin.resolve.calls.inference.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0001¨\u0006\u0007"}, d2 = {"createCompositeSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "appliedFirst", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "appliedLast", "composeWith", "appliedAfter", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NewTypeSubstitutorKt {
    public static final NewTypeSubstitutor createCompositeSubstitutor(final TypeSubstitutor appliedFirst, final NewTypeSubstitutor appliedLast) {
        Intrinsics.checkNotNullParameter(appliedFirst, "appliedFirst");
        Intrinsics.checkNotNullParameter(appliedLast, "appliedLast");
        return appliedFirst.isEmpty() ? appliedLast : new NewTypeSubstitutor() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorKt.createCompositeSubstitutor.1
            @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
            public TypeProjection substituteArgumentProjection(TypeProjection argument) {
                Intrinsics.checkNotNullParameter(argument, "argument");
                TypeProjection typeProjectionSubstitute = appliedFirst.substitute(argument);
                if (typeProjectionSubstitute == null || typeProjectionSubstitute == argument) {
                    return null;
                }
                if (typeProjectionSubstitute.isStarProjection()) {
                    return typeProjectionSubstitute;
                }
                return new TypeProjectionImpl(typeProjectionSubstitute.getProjectionKind(), appliedLast.safeSubstitute(typeProjectionSubstitute.getNullableAnyType().unwrap()));
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
            public UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor) {
                SimpleType defaultType;
                Intrinsics.checkNotNullParameter(constructor, "constructor");
                ClassifierDescriptor this$0 = constructor.getThis$0();
                UnwrappedType unwrappedTypeSubstitute = (this$0 == null || (defaultType = this$0.getDefaultType()) == null) ? null : DescriptorRelatedInferenceUtilsKt.substitute(appliedFirst, defaultType);
                if (unwrappedTypeSubstitute == null) {
                    return appliedLast.substituteNotNullTypeWithConstructor(constructor);
                }
                return appliedLast.safeSubstitute(unwrappedTypeSubstitute);
            }

            @Override // org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor
            public boolean isEmpty() {
                return appliedFirst.isEmpty() && appliedLast.isEmpty();
            }
        };
    }

    public static final NewTypeSubstitutor composeWith(TypeSubstitutor typeSubstitutor, NewTypeSubstitutor appliedAfter) {
        Intrinsics.checkNotNullParameter(typeSubstitutor, "<this>");
        Intrinsics.checkNotNullParameter(appliedAfter, "appliedAfter");
        return createCompositeSubstitutor(typeSubstitutor, appliedAfter);
    }
}
