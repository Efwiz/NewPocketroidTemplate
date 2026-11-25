package org.eclipse.jdt.internal.compiler.lookup;

import java.util.Arrays;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.Annotation;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationBinding {
    ElementValuePair[] pairs;
    ReferenceBinding type;

    public void resolve() {
    }

    public static AnnotationBinding[] addStandardAnnotations(AnnotationBinding[] annotationBindingArr, long j, LookupEnvironment lookupEnvironment) {
        if ((j & TagBits.AllStandardAnnotationsMask) == 0) {
            return annotationBindingArr;
        }
        long j2 = j & TagBits.AnnotationTargetMASK;
        int i = j2 != 0 ? 1 : 0;
        long j3 = j & 52776558133248L;
        if (j3 != 0) {
            i++;
        }
        long j4 = j & 70368744177664L;
        if (j4 != 0) {
            i++;
        }
        long j5 = j & 140737488355328L;
        if (j5 != 0) {
            i++;
        }
        long j6 = j & 281474976710656L;
        if (j6 != 0) {
            i++;
        }
        long j7 = j & 562949953421312L;
        if (j7 != 0) {
            i++;
        }
        long j8 = j & 1125899906842624L;
        if (j8 != 0) {
            i++;
        }
        long j9 = j & 4503599627370496L;
        if (j9 != 0) {
            i++;
        }
        long j10 = j & 2251799813685248L;
        if (j10 != 0) {
            i++;
        }
        if (i == 0) {
            return annotationBindingArr;
        }
        int length = annotationBindingArr.length;
        AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[i + length];
        System.arraycopy(annotationBindingArr, 0, annotationBindingArr2, 0, length);
        if (j2 != 0) {
            annotationBindingArr2[length] = buildTargetAnnotation(j, lookupEnvironment);
            length++;
        }
        if (j3 != 0) {
            annotationBindingArr2[length] = buildRetentionAnnotation(j, lookupEnvironment);
            length++;
        }
        if (j4 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_DEPRECATED, lookupEnvironment);
            length++;
        }
        if (j5 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_ANNOTATION_DOCUMENTED, lookupEnvironment);
            length++;
        }
        if (j6 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_ANNOTATION_INHERITED, lookupEnvironment);
            length++;
        }
        if (j7 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_OVERRIDE, lookupEnvironment);
            length++;
        }
        if (j8 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_SUPPRESSWARNINGS, lookupEnvironment);
            length++;
        }
        if (j9 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotationForMemberType(TypeConstants.JAVA_LANG_INVOKE_METHODHANDLE_$_POLYMORPHICSIGNATURE, lookupEnvironment);
            length++;
        }
        if (j10 != 0) {
            annotationBindingArr2[length] = buildMarkerAnnotation(TypeConstants.JAVA_LANG_SAFEVARARGS, lookupEnvironment);
        }
        return annotationBindingArr2;
    }

    private static AnnotationBinding buildMarkerAnnotationForMemberType(char[][] cArr, LookupEnvironment lookupEnvironment) {
        ReferenceBinding resolvedType = lookupEnvironment.getResolvedType(cArr, null);
        if (!resolvedType.isValidBinding()) {
            resolvedType = ((ProblemReferenceBinding) resolvedType).closestMatch;
        }
        return lookupEnvironment.createAnnotation(resolvedType, Binding.NO_ELEMENT_VALUE_PAIRS);
    }

    private static AnnotationBinding buildMarkerAnnotation(char[][] cArr, LookupEnvironment lookupEnvironment) {
        return lookupEnvironment.createAnnotation(lookupEnvironment.getResolvedType(cArr, null), Binding.NO_ELEMENT_VALUE_PAIRS);
    }

    private static AnnotationBinding buildRetentionAnnotation(long j, LookupEnvironment lookupEnvironment) {
        FieldBinding field;
        ReferenceBinding resolvedType = lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_ANNOTATION_RETENTIONPOLICY, null);
        if ((j & 52776558133248L) == 52776558133248L) {
            field = resolvedType.getField(TypeConstants.UPPER_RUNTIME, true);
        } else if ((35184372088832L & j) != 0) {
            field = resolvedType.getField(TypeConstants.UPPER_CLASS, true);
        } else {
            field = (j & 17592186044416L) != 0 ? resolvedType.getField(TypeConstants.UPPER_SOURCE, true) : null;
        }
        return lookupEnvironment.createAnnotation(lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_ANNOTATION_RETENTION, null), new ElementValuePair[]{new ElementValuePair(TypeConstants.VALUE, field, (MethodBinding) null)});
    }

    private static AnnotationBinding buildTargetAnnotation(long j, LookupEnvironment lookupEnvironment) {
        int i;
        ReferenceBinding resolvedType = lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_ANNOTATION_TARGET, null);
        if ((j & 34359738368L) != 0) {
            return new AnnotationBinding(resolvedType, Binding.NO_ELEMENT_VALUE_PAIRS);
        }
        long j2 = j & 4398046511104L;
        int i2 = j2 != 0 ? 1 : 0;
        long j3 = j & 1099511627776L;
        if (j3 != 0) {
            i2++;
        }
        long j4 = j & 137438953472L;
        if (j4 != 0) {
            i2++;
        }
        long j5 = j & 2199023255552L;
        if (j5 != 0) {
            i2++;
        }
        long j6 = j & 274877906944L;
        if (j6 != 0) {
            i2++;
        }
        long j7 = j & 8796093022208L;
        if (j7 != 0) {
            i2++;
        }
        long j8 = j & 549755813888L;
        if (j8 != 0) {
            i2++;
        }
        long j9 = j & 68719476736L;
        if (j9 != 0) {
            i2++;
        }
        long j10 = j & 9007199254740992L;
        if (j10 != 0) {
            i2++;
        }
        long j11 = j & 18014398509481984L;
        if (j11 != 0) {
            i2++;
        }
        Object[] objArr = new Object[i2];
        if (i2 > 0) {
            ReferenceBinding resolvedType2 = lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_ANNOTATION_ELEMENTTYPE, null);
            if (j2 != 0) {
                objArr[0] = resolvedType2.getField(TypeConstants.UPPER_ANNOTATION_TYPE, true);
                i = 1;
            } else {
                i = 0;
            }
            if (j3 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.UPPER_CONSTRUCTOR, true);
                i++;
            }
            if (j4 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.UPPER_FIELD, true);
                i++;
            }
            if (j6 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.UPPER_METHOD, true);
                i++;
            }
            if (j7 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.UPPER_PACKAGE, true);
                i++;
            }
            if (j8 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.UPPER_PARAMETER, true);
                i++;
            }
            if (j10 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.TYPE_USE_TARGET, true);
                i++;
            }
            if (j11 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.TYPE_PARAMETER_TARGET, true);
                i++;
            }
            if (j9 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.TYPE, true);
                i++;
            }
            if (j5 != 0) {
                objArr[i] = resolvedType2.getField(TypeConstants.UPPER_LOCAL_VARIABLE, true);
            }
        }
        return lookupEnvironment.createAnnotation(resolvedType, new ElementValuePair[]{new ElementValuePair(TypeConstants.VALUE, objArr, (MethodBinding) null)});
    }

    public AnnotationBinding(ReferenceBinding referenceBinding, ElementValuePair[] elementValuePairArr) {
        this.type = referenceBinding;
        this.pairs = elementValuePairArr;
    }

    AnnotationBinding(Annotation annotation) {
        this((ReferenceBinding) annotation.resolvedType, annotation.computeElementValuePairs());
    }

    public char[] computeUniqueKey(char[] cArr) {
        char[] cArrComputeUniqueKey = this.type.computeUniqueKey(false);
        int length = cArr.length;
        int i = length + 1;
        char[] cArr2 = new char[cArrComputeUniqueKey.length + i];
        System.arraycopy(cArr, 0, cArr2, 0, length);
        cArr2[length] = '@';
        System.arraycopy(cArrComputeUniqueKey, 0, cArr2, i, cArrComputeUniqueKey.length);
        return cArr2;
    }

    public ReferenceBinding getAnnotationType() {
        return this.type;
    }

    public ElementValuePair[] getElementValuePairs() {
        return this.pairs;
    }

    public static void setMethodBindings(ReferenceBinding referenceBinding, ElementValuePair[] elementValuePairArr) {
        int length = elementValuePairArr.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            }
            ElementValuePair elementValuePair = elementValuePairArr[length];
            MethodBinding[] methods = referenceBinding.getMethods(elementValuePair.getName());
            if (methods != null && methods.length == 1) {
                elementValuePair.setMethodBinding(methods[0]);
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(5);
        stringBuffer.append('@').append(this.type.sourceName);
        ElementValuePair[] elementValuePairArr = this.pairs;
        if (elementValuePairArr != null && elementValuePairArr.length > 0) {
            stringBuffer.append('(');
            ElementValuePair[] elementValuePairArr2 = this.pairs;
            if (elementValuePairArr2.length == 1 && CharOperation.equals(elementValuePairArr2[0].getName(), TypeConstants.VALUE)) {
                stringBuffer.append(this.pairs[0].value);
            } else {
                int length = this.pairs.length;
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append(this.pairs[i]);
                }
            }
            stringBuffer.append(')');
        }
        return stringBuffer.toString();
    }

    public int hashCode() {
        return ((527 + this.type.hashCode()) * 31) + Arrays.hashCode(this.pairs);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnnotationBinding)) {
            return false;
        }
        AnnotationBinding annotationBinding = (AnnotationBinding) obj;
        if (getAnnotationType() != annotationBinding.getAnnotationType()) {
            return false;
        }
        ElementValuePair[] elementValuePairs = getElementValuePairs();
        ElementValuePair[] elementValuePairs2 = annotationBinding.getElementValuePairs();
        int length = elementValuePairs.length;
        if (length != elementValuePairs2.length) {
            return false;
        }
        for (ElementValuePair elementValuePair : elementValuePairs) {
            for (int i = 0; i < length; i++) {
                ElementValuePair elementValuePair2 = elementValuePairs2[i];
                if (elementValuePair.binding == elementValuePair2.binding) {
                    if (elementValuePair.value == null) {
                        if (elementValuePair2.value != null) {
                            return false;
                        }
                    } else {
                        if (elementValuePair2.value == null) {
                            return false;
                        }
                        if ((elementValuePair2.value instanceof Object[]) && (elementValuePair.value instanceof Object[])) {
                            if (!Arrays.equals((Object[]) elementValuePair.value, (Object[]) elementValuePair2.value)) {
                                return false;
                            }
                        } else if (!elementValuePair2.value.equals(elementValuePair.value)) {
                            return false;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }
}
