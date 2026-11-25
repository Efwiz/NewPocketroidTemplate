package org.eclipse.jdt.internal.compiler.codegen;

import okhttp3.HttpUrl;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ExceptionLabel extends Label {
    private int count;
    public TypeBinding exceptionType;
    public TypeReference exceptionTypeReference;
    public int[] ranges;
    public Annotation[] se7Annotations;

    public ExceptionLabel(CodeStream codeStream, TypeBinding typeBinding, TypeReference typeReference, Annotation[] annotationArr) {
        super(codeStream);
        this.ranges = new int[]{-1, -1};
        this.count = 0;
        this.exceptionType = typeBinding;
        this.exceptionTypeReference = typeReference;
        this.se7Annotations = annotationArr;
    }

    public ExceptionLabel(CodeStream codeStream, TypeBinding typeBinding) {
        super(codeStream);
        this.ranges = new int[]{-1, -1};
        this.count = 0;
        this.exceptionType = typeBinding;
    }

    public int getCount() {
        return this.count;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.Label
    public void place() {
        this.codeStream.registerExceptionHandler(this);
        this.position = this.codeStream.getPosition();
    }

    public void placeEnd() {
        int i = this.codeStream.position;
        int[] iArr = this.ranges;
        int i2 = this.count;
        if (iArr[i2 - 1] == i) {
            this.count = i2 - 1;
        } else {
            this.count = i2 + 1;
            iArr[i2] = i;
        }
    }

    public void placeStart() {
        int i = this.codeStream.position;
        int i2 = this.count;
        if (i2 > 0 && this.ranges[i2 - 1] == i) {
            this.count = i2 - 1;
            return;
        }
        int[] iArr = this.ranges;
        int length = iArr.length;
        if (i2 == length) {
            int[] iArr2 = new int[length * 2];
            this.ranges = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        int[] iArr3 = this.ranges;
        int i3 = this.count;
        this.count = i3 + 1;
        iArr3[i3] = i;
    }

    public String toString() {
        int i;
        String name2 = getClass().getName();
        StringBuffer stringBuffer = new StringBuffer(name2.substring(name2.lastIndexOf(46) + 1));
        stringBuffer.append('@').append(Integer.toHexString(hashCode()));
        StringBuffer stringBufferAppend = stringBuffer.append("(type=");
        TypeBinding typeBinding = this.exceptionType;
        stringBufferAppend.append(typeBinding == null ? CharOperation.NO_CHAR : typeBinding.readableName());
        stringBuffer.append(", position=").append(this.position);
        stringBuffer.append(", ranges = ");
        if (this.count == 0) {
            stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        } else {
            int i2 = 0;
            while (true) {
                i = this.count;
                if (i2 >= i) {
                    break;
                }
                if ((i2 & 1) == 0) {
                    stringBuffer.append("[").append(this.ranges[i2]);
                } else {
                    stringBuffer.append(",").append(this.ranges[i2]).append("]");
                }
                i2++;
            }
            if ((i & 1) == 1) {
                stringBuffer.append(",?]");
            }
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }
}
