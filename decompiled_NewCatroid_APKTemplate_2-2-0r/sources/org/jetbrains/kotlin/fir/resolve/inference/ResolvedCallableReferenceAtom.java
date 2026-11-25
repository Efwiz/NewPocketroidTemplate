package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.resolve.DoubleColonLHS;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.model.PostponedCallableReferenceMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: PostponedAtoms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u00103\u001a\u0002042\u0006\u0010\u000f\u001a\u000205H\u0016R\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\"\u0010\u0015R\u0016\u0010#\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u000eR\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001c\u0010,\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0011\"\u0004\b.\u0010/R$\u00101\u001a\u0004\u0018\u00010\u00062\b\u00100\u001a\u0004\u0018\u00010\u00068V@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b2\u0010\u0011R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedCallableReferenceAtom;", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedCallableReferenceMarker;", "reference", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "initialExpectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "lhs", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;Lorg/jetbrains/kotlin/fir/FirSession;)V", "atom", "getAtom", "()Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "expectedType", "getExpectedType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "hasBeenPostponed", "", "getHasBeenPostponed", "()Z", "setHasBeenPostponed", "(Z)V", "hasBeenResolvedOnce", "getHasBeenResolvedOnce", "setHasBeenResolvedOnce", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "getLhs", "()Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "mightNeedAdditionalResolution", "getMightNeedAdditionalResolution", "outputType", "getOutputType", "getReference", "resultingReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "getResultingReference", "()Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "setResultingReference", "(Lorg/jetbrains/kotlin/fir/references/FirNamedReference;)V", "resultingTypeForCallableReference", "getResultingTypeForCallableReference", "setResultingTypeForCallableReference", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "<set-?>", "revisedExpectedType", "getRevisedExpectedType", "reviseExpectedType", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedCallableReferenceAtom extends PostponedResolvedAtom implements PostponedCallableReferenceMarker {
    private boolean hasBeenPostponed;
    private boolean hasBeenResolvedOnce;
    private final ConeKotlinType initialExpectedType;
    private final DoubleColonLHS lhs;
    private final FirCallableReferenceAccess reference;
    private FirNamedReference resultingReference;
    private ConeKotlinType resultingTypeForCallableReference;
    private ConeKotlinType revisedExpectedType;
    private final FirSession session;

    public final FirCallableReferenceAccess getReference() {
        return this.reference;
    }

    public final DoubleColonLHS getLhs() {
        return this.lhs;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedCallableReferenceAtom(FirCallableReferenceAccess reference, ConeKotlinType coneKotlinType, DoubleColonLHS doubleColonLHS, FirSession session) {
        super(null);
        Intrinsics.checkNotNullParameter(reference, "reference");
        Intrinsics.checkNotNullParameter(session, "session");
        this.reference = reference;
        this.initialExpectedType = coneKotlinType;
        this.lhs = doubleColonLHS;
        this.session = session;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom
    /* JADX INFO: renamed from: getAtom, reason: from getter */
    public FirCallableReferenceAccess getReference() {
        return this.reference;
    }

    public final boolean getHasBeenResolvedOnce() {
        return this.hasBeenResolvedOnce;
    }

    public final void setHasBeenResolvedOnce(boolean z) {
        this.hasBeenResolvedOnce = z;
    }

    public final boolean getHasBeenPostponed() {
        return this.hasBeenPostponed;
    }

    public final void setHasBeenPostponed(boolean z) {
        this.hasBeenPostponed = z;
    }

    public final boolean getMightNeedAdditionalResolution() {
        return !this.hasBeenResolvedOnce || this.hasBeenPostponed;
    }

    public final FirNamedReference getResultingReference() {
        return this.resultingReference;
    }

    public final void setResultingReference(FirNamedReference firNamedReference) {
        this.resultingReference = firNamedReference;
    }

    public final ConeKotlinType getResultingTypeForCallableReference() {
        return this.resultingTypeForCallableReference;
    }

    public final void setResultingTypeForCallableReference(ConeKotlinType coneKotlinType) {
        this.resultingTypeForCallableReference = coneKotlinType;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<ConeKotlinType> getInputTypes() {
        List<ConeKotlinType> inputTypes;
        if (!this.hasBeenPostponed) {
            return CollectionsKt.emptyList();
        }
        InputOutputTypes inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType = PostponedAtomsKt.extractInputOutputTypesFromCallableReferenceExpectedType(getExpectedType(), this.session);
        return (inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType == null || (inputTypes = inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getInputTypes()) == null) ? CollectionsKt.listOfNotNull(getExpectedType()) : inputTypes;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType */
    public ConeKotlinType getReturnType() {
        InputOutputTypes inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType;
        if (this.hasBeenPostponed && (inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType = PostponedAtomsKt.extractInputOutputTypesFromCallableReferenceExpectedType(getExpectedType(), this.session)) != null) {
            return inputOutputTypesExtractInputOutputTypesFromCallableReferenceExpectedType.getOutputType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public ConeKotlinType getExpectedType() {
        if (!this.hasBeenPostponed) {
            return this.initialExpectedType;
        }
        ConeKotlinType revisedExpectedType = getRevisedExpectedType();
        return revisedExpectedType == null ? this.initialExpectedType : revisedExpectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public ConeKotlinType getRevisedExpectedType() {
        return this.hasBeenPostponed ? this.revisedExpectedType : getExpectedType();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public void reviseExpectedType(KotlinTypeMarker expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (getMightNeedAdditionalResolution()) {
            if (!(expectedType instanceof ConeKotlinType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            this.revisedExpectedType = (ConeKotlinType) expectedType;
        }
    }
}
