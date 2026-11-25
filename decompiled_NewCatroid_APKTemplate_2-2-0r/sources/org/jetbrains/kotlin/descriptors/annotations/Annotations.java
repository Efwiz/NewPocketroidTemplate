package org.jetbrains.kotlin.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\fJ\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0017J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u000b\u001a\u00020\nH&¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "findAnnotation", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getUseSiteTargetedAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationWithTarget;", "hasAnnotation", "", "isEmpty", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Annotations extends Iterable<AnnotationDescriptor>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: renamed from: findAnnotation */
    AnnotationDescriptor mo6752findAnnotation(FqName fqName);

    @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
    List<AnnotationWithTarget> getUseSiteTargetedAnnotations();

    boolean hasAnnotation(FqName fqName);

    boolean isEmpty();

    /* JADX INFO: compiled from: Annotations.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static AnnotationDescriptor findAnnotation(Annotations annotations, FqName fqName) {
            AnnotationDescriptor next;
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Iterator<AnnotationDescriptor> it = annotations.iterator();
            while (it.hasNext()) {
                next = it.next();
                if (Intrinsics.areEqual(next.getFqName(), fqName)) {
                    return next;
                }
            }
            next = null;
            return next;
        }

        public static boolean hasAnnotation(Annotations annotations, FqName fqName) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return annotations.mo6752findAnnotation(fqName) != null;
        }

        @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
        public static List<AnnotationWithTarget> getUseSiteTargetedAnnotations(Annotations annotations) {
            return CollectionsKt.emptyList();
        }
    }

    /* JADX INFO: compiled from: Annotations.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/annotations/Annotations$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getEMPTY", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "annotations", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final Annotations EMPTY = new Annotations() { // from class: org.jetbrains.kotlin.descriptors.annotations.Annotations$Companion$EMPTY$1
            public Void findAnnotation(FqName fqName) {
                Intrinsics.checkNotNullParameter(fqName, "fqName");
                return null;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
            public boolean isEmpty() {
                return true;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
            /* JADX INFO: renamed from: findAnnotation, reason: collision with other method in class */
            public /* bridge */ /* synthetic */ AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
                return (AnnotationDescriptor) findAnnotation(fqName);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
            @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
            public List<AnnotationWithTarget> getUseSiteTargetedAnnotations() {
                return Annotations.DefaultImpls.getUseSiteTargetedAnnotations(this);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
            public boolean hasAnnotation(FqName fqName) {
                return Annotations.DefaultImpls.hasAnnotation(this, fqName);
            }

            @Override // java.lang.Iterable
            public Iterator<AnnotationDescriptor> iterator() {
                return CollectionsKt.emptyList().iterator();
            }

            public String toString() {
                return "EMPTY";
            }
        };

        private Companion() {
        }

        public final Annotations getEMPTY() {
            return EMPTY;
        }

        public final Annotations create(List<? extends AnnotationDescriptor> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return annotations.isEmpty() ? EMPTY : new AnnotationsImpl(annotations);
        }
    }
}
