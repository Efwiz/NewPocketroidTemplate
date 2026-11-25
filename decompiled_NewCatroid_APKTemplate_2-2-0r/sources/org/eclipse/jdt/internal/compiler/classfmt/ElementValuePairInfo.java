package org.eclipse.jdt.internal.compiler.classfmt;

import java.util.Arrays;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair;

/* JADX INFO: loaded from: classes14.dex */
public class ElementValuePairInfo implements IBinaryElementValuePair {
    static final ElementValuePairInfo[] NoMembers = new ElementValuePairInfo[0];

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private char[] f620name;
    private Object value;

    ElementValuePairInfo(char[] cArr, Object obj) {
        this.f620name = cArr;
        this.value = obj;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair
    public char[] getName() {
        return this.f620name;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair
    public Object getValue() {
        return this.value;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.f620name);
        stringBuffer.append('=');
        Object obj = this.value;
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            stringBuffer.append('{');
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
        return stringBuffer.toString();
    }

    public int hashCode() {
        int iHashCode = (CharOperation.hashCode(this.f620name) + 31) * 31;
        Object obj = this.value;
        return iHashCode + (obj == null ? 0 : obj.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ElementValuePairInfo elementValuePairInfo = (ElementValuePairInfo) obj;
        if (!Arrays.equals(this.f620name, elementValuePairInfo.f620name)) {
            return false;
        }
        Object obj2 = this.value;
        if (obj2 == null) {
            if (elementValuePairInfo.value != null) {
                return false;
            }
        } else if (!obj2.equals(elementValuePairInfo.value)) {
            return false;
        }
        return true;
    }
}
