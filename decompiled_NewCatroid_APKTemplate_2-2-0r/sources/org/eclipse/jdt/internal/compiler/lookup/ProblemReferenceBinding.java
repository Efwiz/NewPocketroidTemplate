package org.eclipse.jdt.internal.compiler.lookup;

import java.lang.reflect.Field;
import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemReferenceBinding extends ReferenceBinding {
    ReferenceBinding closestMatch;
    private int problemReason;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
    }

    public ProblemReferenceBinding(char[][] cArr, ReferenceBinding referenceBinding, int i) {
        this.compoundName = cArr;
        this.closestMatch = referenceBinding;
        this.problemReason = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        throw new IllegalStateException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding closestMatch() {
        return this.closestMatch;
    }

    public ReferenceBinding closestReferenceMatch() {
        return this.closestMatch;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        ReferenceBinding referenceBinding = this.closestMatch;
        if (referenceBinding != null) {
            return referenceBinding.hasTypeBit(i);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public int problemId() {
        return this.problemReason;
    }

    public static String problemReasonString(int i) {
        try {
            String name2 = ProblemReasons.class.getName();
            int iLastIndexOf = name2.lastIndexOf(46);
            if (iLastIndexOf >= 0) {
                name2 = name2.substring(iLastIndexOf + 1);
            }
            for (Field field : ProblemReasons.class.getFields()) {
                if (field.getType().equals(Integer.TYPE) && field.getInt(ProblemReasons.class) == i) {
                    return String.valueOf(name2) + '.' + field.getName();
                }
            }
            return "unknown";
        } catch (IllegalAccessException unused) {
            return "unknown";
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        return readableName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        if (this.compoundName.length == 0) {
            return null;
        }
        return this.compoundName[this.compoundName.length - 1];
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append("ProblemType:[compoundName=");
        stringBuffer.append(this.compoundName == null ? "<null>" : new String(CharOperation.concatWith(this.compoundName, '.')));
        stringBuffer.append("][problemID=").append(problemReasonString(this.problemReason));
        stringBuffer.append("][closestMatch=");
        ReferenceBinding referenceBinding = this.closestMatch;
        stringBuffer.append(referenceBinding != null ? referenceBinding.toString() : "<null>");
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
