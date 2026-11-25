package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: NativeObjCRefinementAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\u000b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/ObjCExportMetaAnnotations;", "", "hidesFromObjCAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "refinesInSwiftAnnotation", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)V", "getHidesFromObjCAnnotation", "()Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getRefinesInSwiftAnnotation", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ObjCExportMetaAnnotations {
    private final AnnotationDescriptor hidesFromObjCAnnotation;
    private final AnnotationDescriptor refinesInSwiftAnnotation;

    public static /* synthetic */ ObjCExportMetaAnnotations copy$default(ObjCExportMetaAnnotations objCExportMetaAnnotations, AnnotationDescriptor annotationDescriptor, AnnotationDescriptor annotationDescriptor2, int i, Object obj) {
        if ((i & 1) != 0) {
            annotationDescriptor = objCExportMetaAnnotations.hidesFromObjCAnnotation;
        }
        if ((i & 2) != 0) {
            annotationDescriptor2 = objCExportMetaAnnotations.refinesInSwiftAnnotation;
        }
        return objCExportMetaAnnotations.copy(annotationDescriptor, annotationDescriptor2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final AnnotationDescriptor getHidesFromObjCAnnotation() {
        return this.hidesFromObjCAnnotation;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final AnnotationDescriptor getRefinesInSwiftAnnotation() {
        return this.refinesInSwiftAnnotation;
    }

    public final ObjCExportMetaAnnotations copy(AnnotationDescriptor hidesFromObjCAnnotation, AnnotationDescriptor refinesInSwiftAnnotation) {
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
        AnnotationDescriptor annotationDescriptor = this.hidesFromObjCAnnotation;
        int iHashCode = (annotationDescriptor == null ? 0 : annotationDescriptor.hashCode()) * 31;
        AnnotationDescriptor annotationDescriptor2 = this.refinesInSwiftAnnotation;
        return iHashCode + (annotationDescriptor2 != null ? annotationDescriptor2.hashCode() : 0);
    }

    public String toString() {
        return "ObjCExportMetaAnnotations(hidesFromObjCAnnotation=" + this.hidesFromObjCAnnotation + ", refinesInSwiftAnnotation=" + this.refinesInSwiftAnnotation + ')';
    }

    public ObjCExportMetaAnnotations(AnnotationDescriptor annotationDescriptor, AnnotationDescriptor annotationDescriptor2) {
        this.hidesFromObjCAnnotation = annotationDescriptor;
        this.refinesInSwiftAnnotation = annotationDescriptor2;
    }

    public final AnnotationDescriptor getHidesFromObjCAnnotation() {
        return this.hidesFromObjCAnnotation;
    }

    public final AnnotationDescriptor getRefinesInSwiftAnnotation() {
        return this.refinesInSwiftAnnotation;
    }
}
