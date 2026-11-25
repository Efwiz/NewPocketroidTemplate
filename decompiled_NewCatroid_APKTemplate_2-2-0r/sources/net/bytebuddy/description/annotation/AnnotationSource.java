package net.bytebuddy.description.annotation;

import java.util.Arrays;
import java.util.List;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface AnnotationSource {
    AnnotationList getDeclaredAnnotations();

    public enum Empty implements AnnotationSource {
        INSTANCE;

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Empty();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class Explicit implements AnnotationSource {
        private final List<? extends AnnotationDescription> annotations;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.annotations.equals(((Explicit) obj).annotations);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.annotations.hashCode();
        }

        public Explicit(AnnotationDescription... annotationDescriptionArr) {
            this((List<? extends AnnotationDescription>) Arrays.asList(annotationDescriptionArr));
        }

        public Explicit(List<? extends AnnotationDescription> list) {
            this.annotations = list;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Explicit(this.annotations);
        }
    }
}
