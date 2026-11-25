package org.jetbrains.kotlin.resolve.calls.model;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u001bH\u0016J\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001eJ\u001a\u0010\u001f\u001a\u00020\u001a2\u0010\u0010 \u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001b\u0018\u00010\u0013H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR2\u0010\u0014\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u00132\u0010\u0010\u0012\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0018\u00010\u0013@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\"\u0010\u0017\u001a\u0004\u0018\u00010\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000b¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/LambdaWithTypeVariableAsExpectedTypeAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaWithTypeVariableAsExpectedTypeMarker;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/LambdaKotlinCallArgument;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "<set-?>", "", "parameterTypesFromDeclaration", "getParameterTypesFromDeclaration", "()Ljava/util/List;", "revisedExpectedType", "getRevisedExpectedType", "reviseExpectedType", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "setAnalyzed", "resolvedLambdaAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "updateParameterTypesFromDeclaration", SpecTypePair.NAME_types, "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LambdaWithTypeVariableAsExpectedTypeAtom extends PostponedResolvedAtom implements LambdaWithTypeVariableAsExpectedTypeMarker {
    private final LambdaKotlinCallArgument atom;
    private final UnwrappedType expectedType;
    private List<? extends UnwrappedType> parameterTypesFromDeclaration;
    private UnwrappedType revisedExpectedType;

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType */
    public UnwrappedType getReturnType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public LambdaKotlinCallArgument getAtom() {
        return this.atom;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LambdaWithTypeVariableAsExpectedTypeAtom(LambdaKotlinCallArgument atom, UnwrappedType expectedType) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        this.atom = atom;
        this.expectedType = expectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<UnwrappedType> getInputTypes() {
        return CollectionsKt.listOf(getExpectedType());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public UnwrappedType getRevisedExpectedType() {
        return this.revisedExpectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeMarker
    public List<UnwrappedType> getParameterTypesFromDeclaration() {
        return this.parameterTypesFromDeclaration;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeMarker
    public void updateParameterTypesFromDeclaration(List<? extends KotlinTypeMarker> types) {
        this.parameterTypesFromDeclaration = types;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public void reviseExpectedType(KotlinTypeMarker expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (!(expectedType instanceof UnwrappedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.revisedExpectedType = (UnwrappedType) expectedType;
    }

    public final void setAnalyzed(ResolvedLambdaAtom resolvedLambdaAtom) {
        Intrinsics.checkNotNullParameter(resolvedLambdaAtom, "resolvedLambdaAtom");
        setAnalyzedResults(CollectionsKt.listOf(resolvedLambdaAtom));
    }
}
