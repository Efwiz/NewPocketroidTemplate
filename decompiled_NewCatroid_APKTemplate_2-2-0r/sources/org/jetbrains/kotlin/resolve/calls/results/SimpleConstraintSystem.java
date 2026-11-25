package org.jetbrains.kotlin.resolve.calls.results;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: FlatSignature.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH&J\b\u0010\u000f\u001a\u00020\u0003H&J\u0016\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H&R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "", "captureFromArgument", "", "getCaptureFromArgument", "()Z", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "addSubtypeConstraint", "", "subType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "superType", "hasContradiction", "registerTypeVariables", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "typeParameters", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SimpleConstraintSystem {
    void addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType);

    default boolean getCaptureFromArgument() {
        return false;
    }

    TypeSystemInferenceExtensionContext getContext();

    boolean hasContradiction();

    TypeSubstitutorMarker registerTypeVariables(Collection<? extends TypeParameterMarker> typeParameters);
}
