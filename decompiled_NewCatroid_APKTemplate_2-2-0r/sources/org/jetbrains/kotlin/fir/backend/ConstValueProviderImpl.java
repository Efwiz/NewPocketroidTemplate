package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ConstValueProviderImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/ConstValueProviderImpl;", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "components", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "getEvaluatedConstTracker", "()Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "findConstantValueFor", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "firExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstValueProviderImpl extends ConstValueProvider {
    private final EvaluatedConstTracker evaluatedConstTracker;
    private final FirSession session;

    public ConstValueProviderImpl(Fir2IrComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.session = components.getSession();
        this.evaluatedConstTracker = components.getConfiguration().getEvaluatedConstTracker();
    }

    @Override // org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider
    public FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider
    public EvaluatedConstTracker getEvaluatedConstTracker() {
        return this.evaluatedConstTracker;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider
    public ConstantValue<?> findConstantValueFor(FirExpression firExpression) {
        int startOffset;
        Integer numStartOffsetSkippingComments;
        FirFile processingFirFile = getProcessingFirFile();
        if (firExpression == null || processingFirFile == null || (firExpression instanceof FirVarargArgumentsExpression)) {
            return null;
        }
        String strAsString = UtilsKt.getPackageFqName(processingFirFile).child(Name.identifier(processingFirFile.getName())).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (firExpression instanceof FirQualifiedAccessExpression) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) firExpression;
            FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
            KtSourceElement source = calleeReference.getSource();
            if (source == null || (numStartOffsetSkippingComments = ConversionUtilsKt.startOffsetSkippingComments(source)) == null) {
                KtSourceElement source2 = calleeReference.getSource();
                startOffset = source2 != null ? source2.getStartOffset() : -1;
            } else {
                startOffset = numStartOffsetSkippingComments.intValue();
            }
            KtSourceElement source3 = firQualifiedAccessExpression.getSource();
            if (source3 != null) {
                return getEvaluatedConstTracker().load(startOffset, source3.getEndOffset(), strAsString);
            }
            return null;
        }
        KtSourceElement source4 = firExpression.getSource();
        if (source4 != null) {
            int startOffset2 = source4.getStartOffset();
            KtSourceElement source5 = firExpression.getSource();
            if (source5 != null) {
                return getEvaluatedConstTracker().load(startOffset2, source5.getEndOffset(), strAsString);
            }
        }
        return null;
    }
}
