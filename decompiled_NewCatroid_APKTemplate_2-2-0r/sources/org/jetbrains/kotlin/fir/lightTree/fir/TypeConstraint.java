package org.jetbrains.kotlin.fir.lightTree.fir;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: TypeConstraint.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/TypeConstraint;", "", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "identifier", "", "firTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Ljava/util/List;Ljava/lang/String;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/KtSourceElement;)V", "getAnnotations", "()Ljava/util/List;", "getFirTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getIdentifier", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeConstraint {
    private final List<FirAnnotation> annotations;
    private final FirTypeRef firTypeRef;
    private final String identifier;
    private final KtSourceElement source;

    /* JADX WARN: Multi-variable type inference failed */
    public TypeConstraint(List<? extends FirAnnotation> annotations, String identifier, FirTypeRef firTypeRef, KtSourceElement source) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(identifier, "identifier");
        Intrinsics.checkNotNullParameter(firTypeRef, "firTypeRef");
        Intrinsics.checkNotNullParameter(source, "source");
        this.annotations = annotations;
        this.identifier = identifier;
        this.firTypeRef = firTypeRef;
        this.source = source;
    }

    public final List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final String getIdentifier() {
        return this.identifier;
    }

    public final FirTypeRef getFirTypeRef() {
        return this.firTypeRef;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }
}
