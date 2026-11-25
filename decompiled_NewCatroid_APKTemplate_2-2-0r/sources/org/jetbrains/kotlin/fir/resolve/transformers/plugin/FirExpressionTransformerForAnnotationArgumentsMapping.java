package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;

/* JADX INFO: compiled from: FirAnnotationArgumentsMappingTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirExpressionTransformerForAnnotationArgumentsMapping;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "annotationArgumentsMappingTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirAnnotationArgumentsMappingTransformer;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirAnnotationArgumentsMappingTransformer;)V", "transformAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirExpressionTransformerForAnnotationArgumentsMapping extends FirExpressionsResolveTransformer {
    private final FirAnnotationArgumentsMappingTransformer annotationArgumentsMappingTransformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExpressionTransformerForAnnotationArgumentsMapping(FirAnnotationArgumentsMappingTransformer annotationArgumentsMappingTransformer) {
        super(annotationArgumentsMappingTransformer);
        Intrinsics.checkNotNullParameter(annotationArgumentsMappingTransformer, "annotationArgumentsMappingTransformer");
        this.annotationArgumentsMappingTransformer = annotationArgumentsMappingTransformer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirAnnotationArgumentsMappingTransformer firAnnotationArgumentsMappingTransformer = this.annotationArgumentsMappingTransformer;
        boolean isInsideAnnotationArgument = firAnnotationArgumentsMappingTransformer.getIsInsideAnnotationArgument();
        firAnnotationArgumentsMappingTransformer.setInsideAnnotationArgument(true);
        try {
            return super.transformAnnotationCall(annotationCall, data);
        } finally {
            firAnnotationArgumentsMappingTransformer.setInsideAnnotationArgument(isInsideAnnotationArgument);
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirAnnotationArgumentsMappingTransformer firAnnotationArgumentsMappingTransformer = this.annotationArgumentsMappingTransformer;
        boolean isInsideAnnotationArgument = firAnnotationArgumentsMappingTransformer.getIsInsideAnnotationArgument();
        firAnnotationArgumentsMappingTransformer.setInsideAnnotationArgument(true);
        try {
            return super.transformErrorAnnotationCall(errorAnnotationCall, data);
        } finally {
            firAnnotationArgumentsMappingTransformer.setInsideAnnotationArgument(isInsideAnnotationArgument);
        }
    }
}
