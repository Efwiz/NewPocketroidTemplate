package org.eclipse.jdt.internal.compiler.env;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class AccessRule {
    public static final int IgnoreIfBetter = 33554432;
    public char[] pattern;
    public int problemId;

    public AccessRule(char[] cArr, int i) {
        this(cArr, i, false);
    }

    public AccessRule(char[] cArr, int i, boolean z) {
        this.pattern = cArr;
        this.problemId = z ? i | 33554432 : i;
    }

    public int hashCode() {
        return (this.problemId * 17) + CharOperation.hashCode(this.pattern);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AccessRule)) {
            return false;
        }
        AccessRule accessRule = (AccessRule) obj;
        if (this.problemId != accessRule.problemId) {
            return false;
        }
        return CharOperation.equals(this.pattern, accessRule.pattern);
    }

    public int getProblemId() {
        return this.problemId & (-33554433);
    }

    public boolean ignoreIfBetter() {
        return (this.problemId & 33554432) != 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("pattern=");
        stringBuffer.append(this.pattern);
        int problemId = getProblemId();
        if (problemId == 16777496) {
            stringBuffer.append(" (DISCOURAGED");
        } else if (problemId == 16777523) {
            stringBuffer.append(" (NON ACCESSIBLE");
        } else {
            stringBuffer.append(" (ACCESSIBLE");
        }
        if (ignoreIfBetter()) {
            stringBuffer.append(" | IGNORE IF BETTER");
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }
}
