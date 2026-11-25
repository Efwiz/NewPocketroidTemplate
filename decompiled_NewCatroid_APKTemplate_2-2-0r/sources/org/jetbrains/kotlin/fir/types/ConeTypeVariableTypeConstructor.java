package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u0014\u001a\u00020\u0015R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "debugName", "", "originalTypeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;)V", "getDebugName", "()Ljava/lang/String;", "<set-?>", "", "isContainedInInvariantOrContravariantPositions", "()Z", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOriginalTypeParameter", "()Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeVariableTypeConstructor extends ConeClassifierLookupTag implements TypeVariableTypeConstructorMarker {
    private final String debugName;
    private boolean isContainedInInvariantOrContravariantPositions;
    private final TypeParameterMarker originalTypeParameter;

    public final String getDebugName() {
        return this.debugName;
    }

    public final TypeParameterMarker getOriginalTypeParameter() {
        return this.originalTypeParameter;
    }

    public ConeTypeVariableTypeConstructor(String debugName, TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        this.debugName = debugName;
        this.originalTypeParameter = typeParameterMarker;
    }

    @Override // org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag
    public Name getName() {
        Name nameIdentifier = Name.identifier(this.debugName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    /* JADX INFO: renamed from: isContainedInInvariantOrContravariantPositions, reason: from getter */
    public final boolean getIsContainedInInvariantOrContravariantPositions() {
        return this.isContainedInInvariantOrContravariantPositions;
    }

    public final void recordInfoAboutTypeVariableUsagesAsInvariantOrContravariantParameter() {
        this.isContainedInInvariantOrContravariantPositions = true;
    }
}
