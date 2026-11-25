package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirSyntheticCallGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J'\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u00052\u0006\u0010\b\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0002H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/UpdateReference;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", "()V", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "reference", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class UpdateReference extends FirTransformer<FirNamedReferenceWithCandidate> {
    public static final UpdateReference INSTANCE = new UpdateReference();

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, FirNamedReferenceWithCandidate data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        return element;
    }

    private UpdateReference() {
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirReference transformReference(FirReference reference, FirNamedReferenceWithCandidate data) {
        Intrinsics.checkNotNullParameter(reference, "reference");
        Intrinsics.checkNotNullParameter(data, "data");
        return data;
    }
}
