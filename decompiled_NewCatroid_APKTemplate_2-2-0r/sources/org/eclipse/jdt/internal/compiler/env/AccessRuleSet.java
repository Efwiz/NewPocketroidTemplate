package org.eclipse.jdt.internal.compiler.env;

import org.eclipse.jdt.core.compiler.CharOperation;

/* JADX INFO: loaded from: classes14.dex */
public class AccessRuleSet {
    private AccessRule[] accessRules;
    public String classpathEntryName;
    public byte classpathEntryType;

    public AccessRuleSet(AccessRule[] accessRuleArr, byte b, String str) {
        this.accessRules = accessRuleArr;
        this.classpathEntryType = b;
        this.classpathEntryName = str;
    }

    public boolean equals(Object obj) {
        String str;
        int length;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AccessRuleSet)) {
            return false;
        }
        AccessRuleSet accessRuleSet = (AccessRuleSet) obj;
        if (this.classpathEntryType != accessRuleSet.classpathEntryType || (((str = this.classpathEntryName) == null && accessRuleSet.classpathEntryName != null) || !str.equals(accessRuleSet.classpathEntryName) || (length = this.accessRules.length) != accessRuleSet.accessRules.length)) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!this.accessRules[i].equals(accessRuleSet.accessRules[i])) {
                return false;
            }
        }
        return true;
    }

    public AccessRule[] getAccessRules() {
        return this.accessRules;
    }

    public AccessRestriction getViolatedRestriction(char[] cArr) {
        int length = this.accessRules.length;
        for (int i = 0; i < length; i++) {
            AccessRule accessRule = this.accessRules[i];
            if (CharOperation.pathMatch(accessRule.pattern, cArr, true, '/')) {
                int problemId = accessRule.getProblemId();
                if (problemId == 16777496 || problemId == 16777523) {
                    return new AccessRestriction(accessRule, this.classpathEntryType, this.classpathEntryName);
                }
                return null;
            }
        }
        return null;
    }

    public int hashCode() {
        int iHashCode = (hashCode(this.accessRules) + 31) * 31;
        String str = this.classpathEntryName;
        return ((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.classpathEntryType;
    }

    private int hashCode(AccessRule[] accessRuleArr) {
        if (accessRuleArr == null) {
            return 0;
        }
        int length = accessRuleArr.length;
        int iHashCode = 1;
        for (int i = 0; i < length; i++) {
            int i2 = iHashCode * 31;
            AccessRule accessRule = accessRuleArr[i];
            iHashCode = i2 + (accessRule == null ? 0 : accessRule.hashCode());
        }
        return iHashCode;
    }

    public String toString() {
        return toString(true);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("AccessRuleSet {");
        if (z) {
            stringBuffer.append('\n');
        }
        int length = this.accessRules.length;
        for (int i = 0; i < length; i++) {
            if (z) {
                stringBuffer.append('\t');
            }
            stringBuffer.append(this.accessRules[i]);
            if (z) {
                stringBuffer.append('\n');
            } else if (i < length - 1) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append("} [classpath entry: ");
        stringBuffer.append(this.classpathEntryName);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
