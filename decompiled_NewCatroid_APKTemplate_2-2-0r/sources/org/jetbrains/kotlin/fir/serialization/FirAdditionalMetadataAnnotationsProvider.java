package org.jetbrains.kotlin.fir.serialization;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;

/* JADX INFO: compiled from: FirAdditionalMetadataAnnotationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "", "()V", "findGeneratedAnnotationsFor", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "hasGeneratedAnnotationsFor", "", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAdditionalMetadataAnnotationsProvider {
    public abstract List<FirAnnotation> findGeneratedAnnotationsFor(FirDeclaration declaration);

    public abstract boolean hasGeneratedAnnotationsFor(FirDeclaration declaration);
}
