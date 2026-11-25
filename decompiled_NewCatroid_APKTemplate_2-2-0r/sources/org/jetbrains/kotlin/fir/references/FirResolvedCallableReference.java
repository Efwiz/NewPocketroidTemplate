package org.jetbrains.kotlin.fir.references;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirResolvedCallableReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c\"\u0004\b\u0001\u0010\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u0002H\u001c\u0012\u0004\u0012\u0002H\u001d0\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010!J3\u0010\"\u001a\u0002H#\"\b\b\u0000\u0010#*\u00020$\"\u0004\b\u0001\u0010\u001d2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u001d0&2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010'R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\"\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u0002`\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u0018X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/references/FirResolvedCallableReference;", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "()V", "inferredTypeArguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getInferredTypeArguments", "()Ljava/util/List;", "mappedArguments", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolvedCallArgument;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceMappedArguments;", "getMappedArguments", "()Ljava/util/Map;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "resolvedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getResolvedSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirResolvedCallableReference extends FirResolvedNamedReference {
    public abstract List<ConeKotlinType> getInferredTypeArguments();

    public abstract Map<FirValueParameter, ResolvedCallArgument> getMappedArguments();

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference
    public abstract Name getName();

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference
    public abstract FirBasedSymbol<?> getResolvedSymbol();

    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitResolvedCallableReference(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.references.FirResolvedNamedReference, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirReference firReferenceTransformResolvedCallableReference = transformer.transformResolvedCallableReference(this, data);
        Intrinsics.checkNotNull(firReferenceTransformResolvedCallableReference, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.references.FirResolvedCallableReference.transform");
        return firReferenceTransformResolvedCallableReference;
    }
}
