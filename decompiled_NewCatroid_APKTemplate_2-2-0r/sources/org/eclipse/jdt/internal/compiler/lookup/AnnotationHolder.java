package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationHolder {
    AnnotationBinding[] annotations;

    Object getDefaultValue() {
        return null;
    }

    public AnnotationBinding[][] getParameterAnnotations() {
        return null;
    }

    static AnnotationHolder storeAnnotations(AnnotationBinding[] annotationBindingArr, AnnotationBinding[][] annotationBindingArr2, Object obj, LookupEnvironment lookupEnvironment) {
        if (annotationBindingArr2 != null) {
            int length = annotationBindingArr2.length;
            boolean z = true;
            while (z) {
                length--;
                if (length < 0) {
                    break;
                }
                AnnotationBinding[] annotationBindingArr3 = annotationBindingArr2[length];
                if (annotationBindingArr3 != null && annotationBindingArr3.length > 0) {
                    z = false;
                }
            }
            if (z) {
                annotationBindingArr2 = null;
            }
        }
        if (obj != null) {
            return new AnnotationMethodHolder(annotationBindingArr, annotationBindingArr2, obj, lookupEnvironment);
        }
        if (annotationBindingArr2 != null) {
            return new MethodHolder(annotationBindingArr, annotationBindingArr2);
        }
        return new AnnotationHolder().setAnnotations(annotationBindingArr);
    }

    AnnotationBinding[] getAnnotations() {
        return this.annotations;
    }

    AnnotationBinding[] getParameterAnnotations(int i) {
        return Binding.NO_ANNOTATIONS;
    }

    AnnotationHolder setAnnotations(AnnotationBinding[] annotationBindingArr) {
        this.annotations = annotationBindingArr;
        if (annotationBindingArr == null || annotationBindingArr.length == 0) {
            return null;
        }
        return this;
    }

    static class MethodHolder extends AnnotationHolder {
        AnnotationBinding[][] parameterAnnotations;

        MethodHolder(AnnotationBinding[] annotationBindingArr, AnnotationBinding[][] annotationBindingArr2) {
            setAnnotations(annotationBindingArr);
            this.parameterAnnotations = annotationBindingArr2;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationHolder
        public AnnotationBinding[][] getParameterAnnotations() {
            return this.parameterAnnotations;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationHolder
        AnnotationBinding[] getParameterAnnotations(int i) {
            AnnotationBinding[][] annotationBindingArr = this.parameterAnnotations;
            AnnotationBinding[] annotationBindingArr2 = annotationBindingArr == null ? null : annotationBindingArr[i];
            return annotationBindingArr2 == null ? Binding.NO_ANNOTATIONS : annotationBindingArr2;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationHolder
        AnnotationHolder setAnnotations(AnnotationBinding[] annotationBindingArr) {
            if (annotationBindingArr == null || annotationBindingArr.length == 0) {
                annotationBindingArr = Binding.NO_ANNOTATIONS;
            }
            this.annotations = annotationBindingArr;
            return this;
        }
    }

    static class AnnotationMethodHolder extends MethodHolder {
        Object defaultValue;
        LookupEnvironment env;

        AnnotationMethodHolder(AnnotationBinding[] annotationBindingArr, AnnotationBinding[][] annotationBindingArr2, Object obj, LookupEnvironment lookupEnvironment) {
            super(annotationBindingArr, annotationBindingArr2);
            this.defaultValue = obj;
            this.env = lookupEnvironment;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.AnnotationHolder
        Object getDefaultValue() {
            Object obj = this.defaultValue;
            if (obj instanceof UnresolvedReferenceBinding) {
                LookupEnvironment lookupEnvironment = this.env;
                if (lookupEnvironment == null) {
                    throw new IllegalStateException();
                }
                this.defaultValue = ((UnresolvedReferenceBinding) obj).resolve(lookupEnvironment, false);
            }
            return this.defaultValue;
        }
    }
}
