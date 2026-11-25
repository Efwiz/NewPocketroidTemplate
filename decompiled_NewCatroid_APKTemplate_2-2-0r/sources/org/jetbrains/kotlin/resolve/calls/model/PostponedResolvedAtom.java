package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0004¢\u0006\u0002\u0010\u0003R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007\u0082\u0001\u0003\u000e\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "()V", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "Lorg/jetbrains/kotlin/resolve/calls/model/LambdaWithTypeVariableAsExpectedTypeAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceArgumentAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PostponedResolvedAtom extends ResolvedAtom implements PostponedResolvedAtomMarker {
    public /* synthetic */ PostponedResolvedAtom(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract UnwrappedType getExpectedType();

    public abstract Collection<UnwrappedType> getInputTypes();

    public abstract UnwrappedType getOutputType();

    private PostponedResolvedAtom() {
        super(null);
    }
}
