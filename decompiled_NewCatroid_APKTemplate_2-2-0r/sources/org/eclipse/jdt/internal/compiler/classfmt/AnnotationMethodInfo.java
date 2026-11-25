package org.eclipse.jdt.internal.compiler.classfmt;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.codegen.AttributeNamesConstants;

/* JADX INFO: loaded from: classes14.dex */
public class AnnotationMethodInfo extends MethodInfo {
    protected Object defaultValue;

    public static MethodInfo createAnnotationMethod(byte[] bArr, int[] iArr, int i) {
        AnnotationInfo[] annotationInfoArrDecodeMethodAnnotations;
        MethodInfo methodInfo = new MethodInfo(bArr, iArr, i);
        int iU2At = methodInfo.u2At(6);
        int iU4At = 8;
        Object objDecodeDefaultValue = null;
        AnnotationInfo[] annotationInfoArr = null;
        for (int i2 = 0; i2 < iU2At; i2++) {
            int i3 = methodInfo.constantPoolOffsets[methodInfo.u2At(iU4At)] - methodInfo.structOffset;
            char[] cArrUtf8At = methodInfo.utf8At(i3 + 3, methodInfo.u2At(i3 + 1));
            if (cArrUtf8At.length > 0) {
                char c = cArrUtf8At[0];
                if (c != 'A') {
                    if (c != 'R') {
                        if (c == 'S' && CharOperation.equals(AttributeNamesConstants.SignatureName, cArrUtf8At)) {
                            methodInfo.signatureUtf8Offset = methodInfo.constantPoolOffsets[methodInfo.u2At(iU4At + 6)] - methodInfo.structOffset;
                        }
                    } else {
                        if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleAnnotationsName)) {
                            annotationInfoArrDecodeMethodAnnotations = decodeMethodAnnotations(iU4At, true, methodInfo);
                        } else {
                            annotationInfoArrDecodeMethodAnnotations = CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleAnnotationsName) ? decodeMethodAnnotations(iU4At, false, methodInfo) : null;
                        }
                        if (annotationInfoArrDecodeMethodAnnotations != null) {
                            if (annotationInfoArr == null) {
                                annotationInfoArr = annotationInfoArrDecodeMethodAnnotations;
                            } else {
                                int length = annotationInfoArr.length;
                                AnnotationInfo[] annotationInfoArr2 = new AnnotationInfo[annotationInfoArrDecodeMethodAnnotations.length + length];
                                System.arraycopy(annotationInfoArr, 0, annotationInfoArr2, 0, length);
                                System.arraycopy(annotationInfoArrDecodeMethodAnnotations, 0, annotationInfoArr2, length, annotationInfoArrDecodeMethodAnnotations.length);
                                annotationInfoArr = annotationInfoArr2;
                            }
                        }
                    }
                } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.AnnotationDefaultName)) {
                    objDecodeDefaultValue = new AnnotationInfo(methodInfo.reference, methodInfo.constantPoolOffsets, iU4At + 6 + methodInfo.structOffset).decodeDefaultValue();
                }
            }
            iU4At = (int) (((long) iU4At) + methodInfo.u4At(iU4At + 2) + 6);
        }
        methodInfo.attributeBytes = iU4At;
        if (objDecodeDefaultValue == null) {
            return annotationInfoArr != null ? new MethodInfoWithAnnotations(methodInfo, annotationInfoArr) : methodInfo;
        }
        if (annotationInfoArr != null) {
            return new AnnotationMethodInfoWithAnnotations(methodInfo, objDecodeDefaultValue, annotationInfoArr);
        }
        return new AnnotationMethodInfo(methodInfo, objDecodeDefaultValue);
    }

    AnnotationMethodInfo(MethodInfo methodInfo, Object obj) {
        super(methodInfo.reference, methodInfo.constantPoolOffsets, methodInfo.structOffset);
        this.defaultValue = obj;
        this.accessFlags = methodInfo.accessFlags;
        this.attributeBytes = methodInfo.attributeBytes;
        this.descriptor = methodInfo.descriptor;
        this.exceptionNames = methodInfo.exceptionNames;
        this.f622name = methodInfo.f622name;
        this.signature = methodInfo.signature;
        this.signatureUtf8Offset = methodInfo.signatureUtf8Offset;
        this.tagBits = methodInfo.tagBits;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo, org.eclipse.jdt.internal.compiler.env.IBinaryMethod
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override // org.eclipse.jdt.internal.compiler.classfmt.MethodInfo
    protected void toStringContent(StringBuffer stringBuffer) {
        super.toStringContent(stringBuffer);
        if (this.defaultValue != null) {
            stringBuffer.append(" default ");
            Object obj = this.defaultValue;
            if (obj instanceof Object[]) {
                stringBuffer.append('{');
                Object[] objArr = (Object[]) this.defaultValue;
                int length = objArr.length;
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    stringBuffer.append(objArr[i]);
                }
                stringBuffer.append('}');
            } else {
                stringBuffer.append(obj);
            }
            stringBuffer.append('\n');
        }
    }
}
