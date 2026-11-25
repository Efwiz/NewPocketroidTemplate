package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirNativeObjCRefinementAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\u000b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/ObjCExportMetaAnnotations;", "", "hidesFromObjCAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "refinesInSwiftAnnotation", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)V", "getHidesFromObjCAnnotation", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getRefinesInSwiftAnnotation", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ObjCExportMetaAnnotations {
    private final FirAnnotation hidesFromObjCAnnotation;
    private final FirAnnotation refinesInSwiftAnnotation;

    public static /* synthetic */ ObjCExportMetaAnnotations copy$default(ObjCExportMetaAnnotations objCExportMetaAnnotations, FirAnnotation firAnnotation, FirAnnotation firAnnotation2, int i, Object obj) {
        if ((i & 1) != 0) {
            firAnnotation = objCExportMetaAnnotations.hidesFromObjCAnnotation;
        }
        if ((i & 2) != 0) {
            firAnnotation2 = objCExportMetaAnnotations.refinesInSwiftAnnotation;
        }
        return objCExportMetaAnnotations.copy(firAnnotation, firAnnotation2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirAnnotation getHidesFromObjCAnnotation() {
        return this.hidesFromObjCAnnotation;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FirAnnotation getRefinesInSwiftAnnotation() {
        return this.refinesInSwiftAnnotation;
    }

    public final ObjCExportMetaAnnotations copy(FirAnnotation hidesFromObjCAnnotation, FirAnnotation refinesInSwiftAnnotation) {
        return new ObjCExportMetaAnnotations(hidesFromObjCAnnotation, refinesInSwiftAnnotation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ObjCExportMetaAnnotations)) {
            return false;
        }
        ObjCExportMetaAnnotations objCExportMetaAnnotations = (ObjCExportMetaAnnotations) other;
        return Intrinsics.areEqual(this.hidesFromObjCAnnotation, objCExportMetaAnnotations.hidesFromObjCAnnotation) && Intrinsics.areEqual(this.refinesInSwiftAnnotation, objCExportMetaAnnotations.refinesInSwiftAnnotation);
    }

    public int hashCode() {
        FirAnnotation firAnnotation = this.hidesFromObjCAnnotation;
        int iHashCode = (firAnnotation == null ? 0 : firAnnotation.hashCode()) * 31;
        FirAnnotation firAnnotation2 = this.refinesInSwiftAnnotation;
        return iHashCode + (firAnnotation2 != null ? firAnnotation2.hashCode() : 0);
    }

    public String toString() {
        return "ObjCExportMetaAnnotations(hidesFromObjCAnnotation=" + this.hidesFromObjCAnnotation + ", refinesInSwiftAnnotation=" + this.refinesInSwiftAnnotation + ')';
    }

    public ObjCExportMetaAnnotations(FirAnnotation firAnnotation, FirAnnotation firAnnotation2) {
        this.hidesFromObjCAnnotation = firAnnotation;
        this.refinesInSwiftAnnotation = firAnnotation2;
    }

    public final FirAnnotation getHidesFromObjCAnnotation() {
        return this.hidesFromObjCAnnotation;
    }

    public final FirAnnotation getRefinesInSwiftAnnotation() {
        return this.refinesInSwiftAnnotation;
    }
}
