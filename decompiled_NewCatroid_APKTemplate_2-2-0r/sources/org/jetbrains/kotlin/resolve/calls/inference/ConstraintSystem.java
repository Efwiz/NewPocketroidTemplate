package org.jetbrains.kotlin.resolve.calls.inference;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: ConstraintSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u001aJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000eH&J\u001e\u0010\u0014\u001a\u00020\u00152\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u0017H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "", "currentSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getCurrentSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "resultingSubstitutor", "getResultingSubstitutor", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemStatus;", "getStatus", "()Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemStatus;", "typeVariables", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "getTypeVariables", "()Ljava/util/Set;", "getTypeBounds", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds;", "typeVariable", "toBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "filterConstraintPosition", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", "", "Builder", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConstraintSystem {
    TypeSubstitutor getCurrentSubstitutor();

    TypeSubstitutor getResultingSubstitutor();

    ConstraintSystemStatus getStatus();

    TypeBounds getTypeBounds(TypeVariable typeVariable);

    Set<TypeVariable> getTypeVariables();

    Builder toBuilder(Function1<? super ConstraintPosition, Boolean> filterConstraintPosition);

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Builder toBuilder$default(ConstraintSystem constraintSystem, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toBuilder");
        }
        if ((i & 1) != 0) {
            function1 = new Function1<ConstraintPosition, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem.toBuilder.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ConstraintPosition it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return true;
                }
            };
        }
        return constraintSystem.toBuilder(function1);
    }

    /* JADX INFO: compiled from: ConstraintSystem.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0000H&J$\u0010\u000b\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\tH&J(\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u00042\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\b\b\u0002\u0010\u0019\u001a\u00020\u001aH&R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "", "typeVariableSubstitutors", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/CallHandle;", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getTypeVariableSubstitutors", "()Ljava/util/Map;", ProductAction.ACTION_ADD, "", "other", "addSubtypeConstraint", "constrainingType", "Lorg/jetbrains/kotlin/types/KotlinType;", "subjectType", "constraintPosition", "Lorg/jetbrains/kotlin/resolve/calls/inference/constraintPosition/ConstraintPosition;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "fixVariables", "registerTypeVariables", "call", "typeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "external", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Builder {
        void add(Builder other);

        void addSubtypeConstraint(KotlinType constrainingType, KotlinType subjectType, ConstraintPosition constraintPosition);

        ConstraintSystem build();

        void fixVariables();

        Map<CallHandle, TypeSubstitutor> getTypeVariableSubstitutors();

        TypeSubstitutor registerTypeVariables(CallHandle call, Collection<? extends TypeParameterDescriptor> typeParameters, boolean external);

        static /* synthetic */ TypeSubstitutor registerTypeVariables$default(Builder builder, CallHandle callHandle, Collection collection, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerTypeVariables");
            }
            if ((i & 4) != 0) {
                z = false;
            }
            return builder.registerTypeVariables(callHandle, collection, z);
        }
    }
}
