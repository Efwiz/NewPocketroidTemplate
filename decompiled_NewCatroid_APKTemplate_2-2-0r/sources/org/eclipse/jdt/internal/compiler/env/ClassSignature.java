package org.eclipse.jdt.internal.compiler.env;

import java.util.Arrays;
import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class ClassSignature {
    char[] className;

    public ClassSignature(char[] cArr) {
        this.className = cArr;
    }

    public char[] getTypeName() {
        return this.className;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.className);
        stringBuffer.append(".class");
        return stringBuffer.toString();
    }

    public int hashCode() {
        return 31 + CharOperation.hashCode(this.className);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return Arrays.equals(this.className, ((ClassSignature) obj).className);
        }
        return false;
    }
}
