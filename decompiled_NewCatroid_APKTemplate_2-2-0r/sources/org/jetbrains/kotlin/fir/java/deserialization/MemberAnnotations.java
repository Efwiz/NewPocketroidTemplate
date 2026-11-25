package org.jetbrains.kotlin.fir.java.deserialization;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;

/* JADX INFO: compiled from: JvmBinaryAnnotationDeserializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B3\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u001b\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003HÆ\u0003J\u0015\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J;\u0010\u0010\u001a\u00020\u00002\u001a\b\u0002\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00032\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR#\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/MemberAnnotations;", "", "memberAnnotations", "", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "annotationMethodsDefaultValues", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Ljava/util/Map;Ljava/util/Map;)V", "getAnnotationMethodsDefaultValues", "()Ljava/util/Map;", "getMemberAnnotations", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class MemberAnnotations {
    private final Map<MemberSignature, FirExpression> annotationMethodsDefaultValues;
    private final Map<MemberSignature, List<FirAnnotation>> memberAnnotations;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MemberAnnotations copy$default(MemberAnnotations memberAnnotations, Map map, Map map2, int i, Object obj) {
        if ((i & 1) != 0) {
            map = memberAnnotations.memberAnnotations;
        }
        if ((i & 2) != 0) {
            map2 = memberAnnotations.annotationMethodsDefaultValues;
        }
        return memberAnnotations.copy(map, map2);
    }

    public final Map<MemberSignature, List<FirAnnotation>> component1() {
        return this.memberAnnotations;
    }

    public final Map<MemberSignature, FirExpression> component2() {
        return this.annotationMethodsDefaultValues;
    }

    public final MemberAnnotations copy(Map<MemberSignature, List<FirAnnotation>> memberAnnotations, Map<MemberSignature, ? extends FirExpression> annotationMethodsDefaultValues) {
        Intrinsics.checkNotNullParameter(memberAnnotations, "memberAnnotations");
        Intrinsics.checkNotNullParameter(annotationMethodsDefaultValues, "annotationMethodsDefaultValues");
        return new MemberAnnotations(memberAnnotations, annotationMethodsDefaultValues);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MemberAnnotations)) {
            return false;
        }
        MemberAnnotations memberAnnotations = (MemberAnnotations) other;
        return Intrinsics.areEqual(this.memberAnnotations, memberAnnotations.memberAnnotations) && Intrinsics.areEqual(this.annotationMethodsDefaultValues, memberAnnotations.annotationMethodsDefaultValues);
    }

    public int hashCode() {
        return (this.memberAnnotations.hashCode() * 31) + this.annotationMethodsDefaultValues.hashCode();
    }

    public String toString() {
        return "MemberAnnotations(memberAnnotations=" + this.memberAnnotations + ", annotationMethodsDefaultValues=" + this.annotationMethodsDefaultValues + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MemberAnnotations(Map<MemberSignature, List<FirAnnotation>> memberAnnotations, Map<MemberSignature, ? extends FirExpression> annotationMethodsDefaultValues) {
        Intrinsics.checkNotNullParameter(memberAnnotations, "memberAnnotations");
        Intrinsics.checkNotNullParameter(annotationMethodsDefaultValues, "annotationMethodsDefaultValues");
        this.memberAnnotations = memberAnnotations;
        this.annotationMethodsDefaultValues = annotationMethodsDefaultValues;
    }

    public final Map<MemberSignature, List<FirAnnotation>> getMemberAnnotations() {
        return this.memberAnnotations;
    }

    public final Map<MemberSignature, FirExpression> getAnnotationMethodsDefaultValues() {
        return this.annotationMethodsDefaultValues;
    }
}
