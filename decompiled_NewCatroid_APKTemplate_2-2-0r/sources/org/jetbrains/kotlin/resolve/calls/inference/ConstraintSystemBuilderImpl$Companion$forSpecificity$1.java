package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: ConstraintSystemBuilderImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000C\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\u00020\u0002J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0019"}, d2 = {"org/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl$Companion$forSpecificity$1", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl;", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "counter", "", "getCounter", "()I", "setCounter", "(I)V", "addSubtypeConstraint", "", "subType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "superType", "hasContradiction", "", "registerTypeVariables", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "typeParameters", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstraintSystemBuilderImpl$Companion$forSpecificity$1 extends ConstraintSystemBuilderImpl implements SimpleConstraintSystem {
    private int counter;

    ConstraintSystemBuilderImpl$Companion$forSpecificity$1(ConstraintSystemBuilderImpl.Mode mode) {
        super(mode);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public /* bridge */ /* synthetic */ TypeSubstitutorMarker registerTypeVariables(Collection collection) {
        return registerTypeVariables((Collection<? extends TypeParameterMarker>) collection);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public TypeSystemInferenceExtensionContext getContext() {
        return SimpleClassicTypeSystemContext.INSTANCE;
    }

    public final int getCounter() {
        return this.counter;
    }

    public final void setCounter(int i) {
        this.counter = i;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public TypeSubstitutor registerTypeVariables(Collection<? extends TypeParameterMarker> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        return ConstraintSystem.Builder.registerTypeVariables$default(this, CallHandle.NONE.INSTANCE, typeParameters, false, 4, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public void addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        TypeSystemContextKt.requireOrDescribe(subType instanceof UnwrappedType, subType);
        TypeSystemContextKt.requireOrDescribe(superType instanceof UnwrappedType, superType);
        ConstraintPositionKind constraintPositionKind = ConstraintPositionKind.VALUE_PARAMETER_POSITION;
        int i = this.counter;
        this.counter = i + 1;
        addSubtypeConstraint((KotlinType) subType, (KotlinType) superType, constraintPositionKind.position(i));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public boolean hasContradiction() {
        fixVariables();
        return build().getStatus().hasContradiction();
    }
}
