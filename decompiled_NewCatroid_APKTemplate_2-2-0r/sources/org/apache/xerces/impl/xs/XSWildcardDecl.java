package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSWildcard;

/* JADX INFO: loaded from: classes13.dex */
public class XSWildcardDecl implements XSWildcard {
    public static final String ABSENT = null;
    public String[] fNamespaceList;
    public short fType = 1;
    public short fProcessContents = 1;
    public XSObjectList fAnnotations = null;
    private String fDescription = null;

    private boolean areSame(XSWildcardDecl xSWildcardDecl) {
        short s = this.fType;
        if (s == xSWildcardDecl.fType) {
            if (s == 1) {
                return true;
            }
            if (s == 2) {
                return this.fNamespaceList[0] == xSWildcardDecl.fNamespaceList[0];
            }
            if (this.fNamespaceList.length == xSWildcardDecl.fNamespaceList.length) {
                int i = 0;
                while (true) {
                    String[] strArr = this.fNamespaceList;
                    if (i >= strArr.length) {
                        return true;
                    }
                    if (!elementInSet(strArr[i], xSWildcardDecl.fNamespaceList)) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    public boolean allowNamespace(String str) {
        short s = this.fType;
        if (s == 1) {
            return true;
        }
        if (s == 2) {
            int length = this.fNamespaceList.length;
            boolean z = false;
            for (int i = 0; i < length && !z; i++) {
                if (str == this.fNamespaceList[i]) {
                    z = true;
                }
            }
            if (!z) {
                return true;
            }
        }
        if (this.fType == 3) {
            int length2 = this.fNamespaceList.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (str == this.fNamespaceList[i2]) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean elementInSet(String str, String[] strArr) {
        boolean z = false;
        for (int i = 0; i < strArr.length && !z; i++) {
            if (str == strArr[i]) {
                z = true;
            }
        }
        return z;
    }

    @Override // org.apache.xerces.xs.XSWildcard
    public XSAnnotation getAnnotation() {
        XSObjectList xSObjectList = this.fAnnotations;
        if (xSObjectList != null) {
            return (XSAnnotation) xSObjectList.item(0);
        }
        return null;
    }

    @Override // org.apache.xerces.xs.XSWildcard
    public XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        return xSObjectList != null ? xSObjectList : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSWildcard
    public short getConstraintType() {
        return this.fType;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getName() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getNamespace() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public XSNamespaceItem getNamespaceItem() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSWildcard
    public StringList getNsConstraintList() {
        String[] strArr = this.fNamespaceList;
        return new StringListImpl(strArr, strArr == null ? 0 : strArr.length);
    }

    @Override // org.apache.xerces.xs.XSWildcard
    public short getProcessContents() {
        return this.fProcessContents;
    }

    public String getProcessContentsAsString() {
        short s = this.fProcessContents;
        if (s == 1) {
            return SchemaSymbols.ATTVAL_STRICT;
        }
        if (s != 2) {
            return s != 3 ? "invalid value" : SchemaSymbols.ATTVAL_LAX;
        }
        return SchemaSymbols.ATTVAL_SKIP;
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 9;
    }

    String[] intersect2sets(String[] strArr, String[] strArr2) {
        String[] strArr3 = new String[Math.min(strArr.length, strArr2.length)];
        int i = 0;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (elementInSet(strArr[i2], strArr2)) {
                strArr3[i] = strArr[i2];
                i++;
            }
        }
        String[] strArr4 = new String[i];
        System.arraycopy(strArr3, 0, strArr4, 0, i);
        return strArr4;
    }

    public boolean isSubsetOf(XSWildcardDecl xSWildcardDecl) {
        if (xSWildcardDecl == null) {
            return false;
        }
        short s = xSWildcardDecl.fType;
        if (s == 1) {
            return true;
        }
        short s2 = this.fType;
        if (s2 == 2 && s == 2 && this.fNamespaceList[0] == xSWildcardDecl.fNamespaceList[0]) {
            return true;
        }
        if (s2 == 3) {
            if (s == 3 && subset2sets(this.fNamespaceList, xSWildcardDecl.fNamespaceList)) {
                return true;
            }
            if (xSWildcardDecl.fType == 2 && !elementInSet(xSWildcardDecl.fNamespaceList[0], this.fNamespaceList) && !elementInSet(ABSENT, this.fNamespaceList)) {
                return true;
            }
        }
        return false;
    }

    public XSWildcardDecl performIntersectionWith(XSWildcardDecl xSWildcardDecl, short s) {
        short s2;
        String[] strArr;
        String[] strArr2;
        if (xSWildcardDecl == null) {
            return null;
        }
        XSWildcardDecl xSWildcardDecl2 = new XSWildcardDecl();
        xSWildcardDecl2.fProcessContents = s;
        if (areSame(xSWildcardDecl)) {
            xSWildcardDecl2.fType = this.fType;
            xSWildcardDecl2.fNamespaceList = this.fNamespaceList;
        } else {
            short s3 = this.fType;
            if (s3 == 1 || (s2 = xSWildcardDecl.fType) == 1) {
                if (s3 != 1) {
                    xSWildcardDecl = this;
                }
                xSWildcardDecl2.fType = xSWildcardDecl.fType;
                xSWildcardDecl2.fNamespaceList = xSWildcardDecl.fNamespaceList;
            } else if ((s3 == 2 && s2 == 3) || (s3 == 3 && s2 == 2)) {
                if (s3 == 2) {
                    strArr = this.fNamespaceList;
                    strArr2 = xSWildcardDecl.fNamespaceList;
                } else {
                    strArr = xSWildcardDecl.fNamespaceList;
                    strArr2 = this.fNamespaceList;
                }
                String[] strArr3 = new String[strArr2.length];
                int i = 0;
                for (String str : strArr2) {
                    if (str != strArr[0] && str != ABSENT) {
                        strArr3[i] = str;
                        i++;
                    }
                }
                xSWildcardDecl2.fType = (short) 3;
                String[] strArr4 = new String[i];
                xSWildcardDecl2.fNamespaceList = strArr4;
                System.arraycopy(strArr3, 0, strArr4, 0, i);
            } else if (s3 == 3 && s2 == 3) {
                xSWildcardDecl2.fType = (short) 3;
                xSWildcardDecl2.fNamespaceList = intersect2sets(this.fNamespaceList, xSWildcardDecl.fNamespaceList);
            } else if (s3 == 2 && s2 == 2) {
                String str2 = this.fNamespaceList[0];
                String str3 = ABSENT;
                if (str2 != str3 && xSWildcardDecl.fNamespaceList[0] != str3) {
                    return null;
                }
                if (str2 != str3) {
                    xSWildcardDecl = this;
                }
                xSWildcardDecl2.fType = xSWildcardDecl.fType;
                xSWildcardDecl2.fNamespaceList = xSWildcardDecl.fNamespaceList;
            }
        }
        return xSWildcardDecl2;
    }

    /* JADX WARN: Code duplicated, block: B:43:0x008d  */
    public XSWildcardDecl performUnionWith(XSWildcardDecl xSWildcardDecl, short s) {
        short s2;
        String[] strArr;
        String[] strArr2;
        if (xSWildcardDecl == null) {
            return null;
        }
        XSWildcardDecl xSWildcardDecl2 = new XSWildcardDecl();
        xSWildcardDecl2.fProcessContents = s;
        if (areSame(xSWildcardDecl)) {
            xSWildcardDecl2.fType = this.fType;
            xSWildcardDecl2.fNamespaceList = this.fNamespaceList;
        } else {
            short s3 = this.fType;
            if (s3 == 1 || (s2 = xSWildcardDecl.fType) == 1) {
                xSWildcardDecl2.fType = (short) 1;
            } else if (s3 == 3 && s2 == 3) {
                xSWildcardDecl2.fType = (short) 3;
                xSWildcardDecl2.fNamespaceList = union2sets(this.fNamespaceList, xSWildcardDecl.fNamespaceList);
            } else if (s3 == 2 && s2 == 2) {
                xSWildcardDecl2.fType = (short) 2;
                xSWildcardDecl2.fNamespaceList = new String[]{str, str};
                String str = ABSENT;
            } else if ((s3 == 2 && s2 == 3) || (s3 == 3 && s2 == 2)) {
                if (s3 == 2) {
                    strArr = this.fNamespaceList;
                    strArr2 = xSWildcardDecl.fNamespaceList;
                } else {
                    strArr = xSWildcardDecl.fNamespaceList;
                    strArr2 = this.fNamespaceList;
                }
                String str2 = ABSENT;
                boolean zElementInSet = elementInSet(str2, strArr2);
                String str3 = strArr[0];
                if (str3 != str2) {
                    boolean zElementInSet2 = elementInSet(str3, strArr2);
                    if (zElementInSet2 && zElementInSet) {
                        xSWildcardDecl2.fType = (short) 1;
                    } else if (!zElementInSet2 || zElementInSet) {
                        if (!zElementInSet2 && zElementInSet) {
                            return null;
                        }
                        xSWildcardDecl2.fType = (short) 2;
                        xSWildcardDecl2.fNamespaceList = strArr;
                    } else {
                        xSWildcardDecl2.fType = (short) 2;
                        xSWildcardDecl2.fNamespaceList = new String[]{str2, str2};
                    }
                } else if (zElementInSet) {
                    xSWildcardDecl2.fType = (short) 1;
                } else {
                    xSWildcardDecl2.fType = (short) 2;
                    xSWildcardDecl2.fNamespaceList = strArr;
                }
            }
        }
        return xSWildcardDecl2;
    }

    boolean subset2sets(String[] strArr, String[] strArr2) {
        for (String str : strArr) {
            if (!elementInSet(str, strArr2)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (this.fDescription == null) {
            StringBuffer stringBuffer = new StringBuffer("WC[");
            short s = this.fType;
            if (s == 1) {
                stringBuffer.append(SchemaSymbols.ATTVAL_TWOPOUNDANY);
            } else if (s == 2) {
                stringBuffer.append("##other:\"");
                String str = this.fNamespaceList[0];
                if (str != null) {
                    stringBuffer.append(str);
                }
                stringBuffer.append("\"");
            } else if (s == 3 && this.fNamespaceList.length != 0) {
                stringBuffer.append("\"");
                String str2 = this.fNamespaceList[0];
                if (str2 != null) {
                    stringBuffer.append(str2);
                }
                stringBuffer.append("\"");
                for (int i = 1; i < this.fNamespaceList.length; i++) {
                    stringBuffer.append(",\"");
                    String str3 = this.fNamespaceList[i];
                    if (str3 != null) {
                        stringBuffer.append(str3);
                    }
                    stringBuffer.append("\"");
                }
            }
            stringBuffer.append(']');
            this.fDescription = stringBuffer.toString();
        }
        return this.fDescription;
    }

    String[] union2sets(String[] strArr, String[] strArr2) {
        String[] strArr3 = new String[strArr.length];
        int i = 0;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (!elementInSet(strArr[i2], strArr2)) {
                strArr3[i] = strArr[i2];
                i++;
            }
        }
        String[] strArr4 = new String[strArr2.length + i];
        System.arraycopy(strArr3, 0, strArr4, 0, i);
        System.arraycopy(strArr2, 0, strArr4, i, strArr2.length);
        return strArr4;
    }

    public boolean weakerProcessContents(XSWildcardDecl xSWildcardDecl) {
        short s = this.fProcessContents;
        if (s == 3 && xSWildcardDecl.fProcessContents == 1) {
            return true;
        }
        return s == 2 && xSWildcardDecl.fProcessContents != 2;
    }
}
