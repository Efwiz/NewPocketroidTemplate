package org.jetbrains.kotlin.fir.resolve.inference;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: PostponedAtoms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000e\u001a\u00020 H\u0016J\u001a\u0010!\u001a\u00020\u001f2\u0010\u0010\"\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010 \u0018\u00010\u0018H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R2\u0010\u0019\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00182\u0010\u0010\u0017\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0018@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\"\u0010\u001c\u001a\u0004\u0018\u00010\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0006@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0010¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaWithTypeVariableAsExpectedTypeAtom;", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaWithTypeVariableAsExpectedTypeMarker;", "atom", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "initialExpectedTypeType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "candidateOfOuterCall", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)V", "getAtom", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "getCandidateOfOuterCall", "()Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "expectedType", "getExpectedType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "<set-?>", "", "parameterTypesFromDeclaration", "getParameterTypesFromDeclaration", "()Ljava/util/List;", "revisedExpectedType", "getRevisedExpectedType", "reviseExpectedType", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "updateParameterTypesFromDeclaration", SpecTypePair.NAME_types, "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LambdaWithTypeVariableAsExpectedTypeAtom extends PostponedResolvedAtom implements LambdaWithTypeVariableAsExpectedTypeMarker {
    private final FirAnonymousFunctionExpression atom;
    private final Candidate candidateOfOuterCall;
    private final ConeKotlinType initialExpectedTypeType;
    private List<? extends ConeKotlinType> parameterTypesFromDeclaration;
    private ConeKotlinType revisedExpectedType;

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType */
    public ConeKotlinType getReturnType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom
    public FirAnonymousFunctionExpression getAtom() {
        return this.atom;
    }

    public final Candidate getCandidateOfOuterCall() {
        return this.candidateOfOuterCall;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LambdaWithTypeVariableAsExpectedTypeAtom(FirAnonymousFunctionExpression atom, ConeKotlinType initialExpectedTypeType, Candidate candidateOfOuterCall) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(initialExpectedTypeType, "initialExpectedTypeType");
        Intrinsics.checkNotNullParameter(candidateOfOuterCall, "candidateOfOuterCall");
        this.atom = atom;
        this.initialExpectedTypeType = initialExpectedTypeType;
        this.candidateOfOuterCall = candidateOfOuterCall;
        candidateOfOuterCall.getPostponedAtoms().add(this);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeMarker
    public List<ConeKotlinType> getParameterTypesFromDeclaration() {
        return this.parameterTypesFromDeclaration;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeMarker
    public void updateParameterTypesFromDeclaration(List<? extends KotlinTypeMarker> types) {
        this.parameterTypesFromDeclaration = types;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public ConeKotlinType getExpectedType() {
        ConeKotlinType revisedExpectedType = getRevisedExpectedType();
        return revisedExpectedType == null ? this.initialExpectedTypeType : revisedExpectedType;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<ConeKotlinType> getInputTypes() {
        return CollectionsKt.listOf(this.initialExpectedTypeType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public ConeKotlinType getRevisedExpectedType() {
        return this.revisedExpectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public void reviseExpectedType(KotlinTypeMarker expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (!(expectedType instanceof ConeKotlinType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.revisedExpectedType = (ConeKotlinType) expectedType;
    }
}
