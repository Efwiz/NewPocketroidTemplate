package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;

/* JADX INFO: compiled from: PostponedAtoms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010\u0082\u0001\u0003\u0017\u0018\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "()V", "analyzed", "", "getAnalyzed", "()Z", "setAnalyzed", "(Z)V", "atom", "Lorg/jetbrains/kotlin/fir/FirElement;", "getAtom", "()Lorg/jetbrains/kotlin/fir/FirElement;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getExpectedType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaWithTypeVariableAsExpectedTypeAtom;", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedCallableReferenceAtom;", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PostponedResolvedAtom implements PostponedResolvedAtomMarker {
    private boolean analyzed;

    public /* synthetic */ PostponedResolvedAtom(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract FirElement getAtom();

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public abstract ConeKotlinType getExpectedType();

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public abstract Collection<ConeKotlinType> getInputTypes();

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType */
    public abstract ConeKotlinType getReturnType();

    private PostponedResolvedAtom() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public boolean getAnalyzed() {
        return this.analyzed;
    }

    public void setAnalyzed(boolean z) {
        this.analyzed = z;
    }
}
