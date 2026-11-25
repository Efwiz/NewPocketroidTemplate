package org.apache.xerces.impl.xs;

import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
public class SubstitutionGroupHandler {
    private static final XSElementDecl[] EMPTY_GROUP = new XSElementDecl[0];
    private static final OneSubGroup[] EMPTY_VECTOR = new OneSubGroup[0];
    private final XSElementDeclHelper fXSElementDeclHelper;
    Hashtable fSubGroupsB = new Hashtable();
    Hashtable fSubGroups = new Hashtable();

    private static final class OneSubGroup {
        short bMethod;
        short dMethod;
        XSElementDecl sub;

        OneSubGroup() {
        }

        OneSubGroup(XSElementDecl xSElementDecl, short s, short s2) {
            this.sub = xSElementDecl;
            this.dMethod = s;
            this.bMethod = s2;
        }
    }

    public SubstitutionGroupHandler(XSElementDeclHelper xSElementDeclHelper) {
        this.fXSElementDeclHelper = xSElementDeclHelper;
    }

    private boolean getDBMethods(XSTypeDefinition xSTypeDefinition, XSTypeDefinition xSTypeDefinition2, OneSubGroup oneSubGroup) {
        short s = 0;
        short s2 = 0;
        while (xSTypeDefinition != xSTypeDefinition2 && xSTypeDefinition != SchemaGrammar.fAnyType) {
            s = (short) (xSTypeDefinition.getTypeCategory() == 15 ? s | ((XSComplexTypeDecl) xSTypeDefinition).fDerivedBy : s | 2);
            xSTypeDefinition = xSTypeDefinition.getBaseType();
            if (xSTypeDefinition == null) {
                xSTypeDefinition = SchemaGrammar.fAnyType;
            }
            if (xSTypeDefinition.getTypeCategory() == 15) {
                s2 = (short) (s2 | ((XSComplexTypeDecl) xSTypeDefinition).fBlock);
            }
        }
        if (xSTypeDefinition != xSTypeDefinition2 || (s & s2) != 0) {
            return false;
        }
        oneSubGroup.dMethod = s;
        oneSubGroup.bMethod = s2;
        return true;
    }

    private OneSubGroup[] getSubGroupB(XSElementDecl xSElementDecl, OneSubGroup oneSubGroup) {
        Object obj = this.fSubGroupsB.get(xSElementDecl);
        if (obj == null) {
            Hashtable hashtable = this.fSubGroupsB;
            OneSubGroup[] oneSubGroupArr = EMPTY_VECTOR;
            hashtable.put(xSElementDecl, oneSubGroupArr);
            return oneSubGroupArr;
        }
        if (obj instanceof OneSubGroup[]) {
            return (OneSubGroup[]) obj;
        }
        Vector vector = (Vector) obj;
        Vector vector2 = new Vector();
        for (int size = vector.size() - 1; size >= 0; size--) {
            XSElementDecl xSElementDecl2 = (XSElementDecl) vector.elementAt(size);
            if (getDBMethods(xSElementDecl2.fType, xSElementDecl.fType, oneSubGroup)) {
                short s = oneSubGroup.dMethod;
                short s2 = oneSubGroup.bMethod;
                vector2.addElement(new OneSubGroup(xSElementDecl2, oneSubGroup.dMethod, oneSubGroup.bMethod));
                OneSubGroup[] subGroupB = getSubGroupB(xSElementDecl2, oneSubGroup);
                for (int length = subGroupB.length - 1; length >= 0; length--) {
                    short s3 = (short) (subGroupB[length].dMethod | s);
                    short s4 = (short) (subGroupB[length].bMethod | s2);
                    if ((s3 & s4) == 0) {
                        vector2.addElement(new OneSubGroup(subGroupB[length].sub, s3, s4));
                    }
                }
            }
        }
        OneSubGroup[] oneSubGroupArr2 = new OneSubGroup[vector2.size()];
        for (int size2 = vector2.size() - 1; size2 >= 0; size2--) {
            oneSubGroupArr2[size2] = (OneSubGroup) vector2.elementAt(size2);
        }
        this.fSubGroupsB.put(xSElementDecl, oneSubGroupArr2);
        return oneSubGroupArr2;
    }

    private boolean typeDerivationOK(XSTypeDefinition xSTypeDefinition, XSTypeDefinition xSTypeDefinition2, short s) {
        XSTypeDefinition baseType = xSTypeDefinition;
        short s2 = s;
        short s3 = 0;
        while (baseType != xSTypeDefinition2 && baseType != SchemaGrammar.fAnyType) {
            s3 = (short) (baseType.getTypeCategory() == 15 ? s3 | ((XSComplexTypeDecl) baseType).fDerivedBy : s3 | 2);
            baseType = baseType.getBaseType();
            if (baseType == null) {
                baseType = SchemaGrammar.fAnyType;
            }
            if (baseType.getTypeCategory() == 15) {
                s2 = (short) (s2 | ((XSComplexTypeDecl) baseType).fBlock);
            }
        }
        if (baseType == xSTypeDefinition2) {
            return (s3 & s2) == 0;
        }
        if (xSTypeDefinition2.getTypeCategory() == 16) {
            XSSimpleTypeDefinition xSSimpleTypeDefinition = (XSSimpleTypeDefinition) xSTypeDefinition2;
            if (xSSimpleTypeDefinition.getVariety() == 3) {
                XSObjectList memberTypes = xSSimpleTypeDefinition.getMemberTypes();
                int length = memberTypes.getLength();
                for (int i = 0; i < length; i++) {
                    if (typeDerivationOK(xSTypeDefinition, (XSTypeDefinition) memberTypes.item(i), s)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addSubstitutionGroup(XSElementDecl[] xSElementDeclArr) {
        for (int length = xSElementDeclArr.length - 1; length >= 0; length--) {
            XSElementDecl xSElementDecl = xSElementDeclArr[length];
            XSElementDecl xSElementDecl2 = xSElementDecl.fSubGroup;
            Vector vector = (Vector) this.fSubGroupsB.get(xSElementDecl2);
            if (vector == null) {
                vector = new Vector();
                this.fSubGroupsB.put(xSElementDecl2, vector);
            }
            vector.addElement(xSElementDecl);
        }
    }

    public XSElementDecl getMatchingElemDecl(QName qName, XSElementDecl xSElementDecl) {
        XSElementDecl globalElementDecl;
        if (qName.localpart == xSElementDecl.fName && qName.uri == xSElementDecl.fTargetNamespace) {
            return xSElementDecl;
        }
        if (xSElementDecl.fScope == 1 && (xSElementDecl.fBlock & 4) == 0 && (globalElementDecl = this.fXSElementDeclHelper.getGlobalElementDecl(qName)) != null && substitutionGroupOK(globalElementDecl, xSElementDecl, xSElementDecl.fBlock)) {
            return globalElementDecl;
        }
        return null;
    }

    public XSElementDecl[] getSubstitutionGroup(XSElementDecl xSElementDecl) {
        Object obj = this.fSubGroups.get(xSElementDecl);
        if (obj != null) {
            return (XSElementDecl[]) obj;
        }
        if ((xSElementDecl.fBlock & 4) != 0) {
            Hashtable hashtable = this.fSubGroups;
            XSElementDecl[] xSElementDeclArr = EMPTY_GROUP;
            hashtable.put(xSElementDecl, xSElementDeclArr);
            return xSElementDeclArr;
        }
        OneSubGroup[] subGroupB = getSubGroupB(xSElementDecl, new OneSubGroup());
        int length = subGroupB.length;
        XSElementDecl[] xSElementDeclArr2 = new XSElementDecl[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if ((xSElementDecl.fBlock & subGroupB[i2].dMethod) == 0) {
                xSElementDeclArr2[i] = subGroupB[i2].sub;
                i++;
            }
        }
        if (i < length) {
            XSElementDecl[] xSElementDeclArr3 = new XSElementDecl[i];
            System.arraycopy(xSElementDeclArr2, 0, xSElementDeclArr3, 0, i);
            xSElementDeclArr2 = xSElementDeclArr3;
        }
        this.fSubGroups.put(xSElementDecl, xSElementDeclArr2);
        return xSElementDeclArr2;
    }

    public boolean inSubstitutionGroup(XSElementDecl xSElementDecl, XSElementDecl xSElementDecl2) {
        return substitutionGroupOK(xSElementDecl, xSElementDecl2, xSElementDecl2.fBlock);
    }

    public void reset() {
        this.fSubGroupsB.clear();
        this.fSubGroups.clear();
    }

    protected boolean substitutionGroupOK(XSElementDecl xSElementDecl, XSElementDecl xSElementDecl2, short s) {
        if (xSElementDecl == xSElementDecl2) {
            return true;
        }
        if ((s & 4) != 0) {
            return false;
        }
        XSElementDecl xSElementDecl3 = xSElementDecl.fSubGroup;
        while (xSElementDecl3 != null && xSElementDecl3 != xSElementDecl2) {
            xSElementDecl3 = xSElementDecl3.fSubGroup;
        }
        if (xSElementDecl3 == null) {
            return false;
        }
        return typeDerivationOK(xSElementDecl.fType, xSElementDecl2.fType, s);
    }
}
