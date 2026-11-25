package org.apache.xerces.impl.xs.models;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Vector;
import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.dtd.models.CMStateSet;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.SubstitutionGroupHandler;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.xni.QName;

/* JADX INFO: loaded from: classes13.dex */
public class XSDFACM implements XSCMValidator {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_VALIDATE_CONTENT = false;
    private static long time;
    private boolean fIsCompactedForUPA;
    private int fLeafCount;
    private Object[] fElemMap = null;
    private int[] fElemMapType = null;
    private int[] fElemMapId = null;
    private int fElemMapSize = 0;
    private boolean[] fFinalStateFlags = null;
    private CMStateSet[] fFollowList = null;
    private CMNode fHeadNode = null;
    private XSCMLeaf[] fLeafList = null;
    private int[] fLeafListType = null;
    private int[][] fTransTable = null;
    private Occurence[] fCountingStates = null;
    private int fTransTableSize = 0;

    static final class Occurence {
        final int elemIndex;
        final int maxOccurs;
        final int minOccurs;

        public Occurence(XSCMRepeatingLeaf xSCMRepeatingLeaf, int i) {
            this.minOccurs = xSCMRepeatingLeaf.getMinOccurs();
            this.maxOccurs = xSCMRepeatingLeaf.getMaxOccurs();
            this.elemIndex = i;
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder("minOccurs=").append(this.minOccurs).append(";maxOccurs=");
            int i = this.maxOccurs;
            return sbAppend.append(i != -1 ? Integer.toString(i) : SchemaSymbols.ATTVAL_UNBOUNDED).toString();
        }
    }

    public XSDFACM(CMNode cMNode, int i) {
        this.fLeafCount = 0;
        this.fLeafCount = i;
        this.fIsCompactedForUPA = cMNode.isCompactedForUPA();
        buildDFA(cMNode);
    }

    /* JADX WARN: Code duplicated, block: B:60:0x01a4 A[PHI: r4 r15
      0x01a4: PHI (r4v4 int) = (r4v3 int), (r4v6 int) binds: [B:50:0x0155, B:58:0x017e] A[DONT_GENERATE, DONT_INLINE]
      0x01a4: PHI (r15v4 org.apache.xerces.impl.dtd.models.CMStateSet) = (r15v3 org.apache.xerces.impl.dtd.models.CMStateSet), (r15v6 org.apache.xerces.impl.dtd.models.CMStateSet) binds: [B:50:0x0155, B:58:0x017e] A[DONT_GENERATE, DONT_INLINE]] */
    private void buildDFA(CMNode cMNode) {
        int i;
        int i2;
        int i3 = this.fLeafCount;
        int i4 = this.fLeafCount;
        this.fLeafCount = i4 + 1;
        int i5 = 1;
        CMStateSet cMStateSet = null;
        int i6 = -1;
        XSCMBinOp xSCMBinOp = new XSCMBinOp(102, cMNode, new XSCMLeaf(1, null, -1, i4));
        this.fHeadNode = xSCMBinOp;
        int i7 = this.fLeafCount;
        this.fLeafList = new XSCMLeaf[i7];
        this.fLeafListType = new int[i7];
        postTreeBuildInit(xSCMBinOp);
        this.fFollowList = new CMStateSet[this.fLeafCount];
        for (int i8 = 0; i8 < this.fLeafCount; i8++) {
            this.fFollowList[i8] = new CMStateSet(this.fLeafCount);
        }
        calcFollowList(this.fHeadNode);
        int i9 = this.fLeafCount;
        this.fElemMap = new Object[i9];
        this.fElemMapType = new int[i9];
        this.fElemMapId = new int[i9];
        this.fElemMapSize = 0;
        int i10 = 0;
        Occurence[] occurenceArr = null;
        while (true) {
            i = this.fLeafCount;
            if (i10 >= i) {
                break;
            }
            this.fElemMap[i10] = null;
            int particleId = this.fLeafList[i10].getParticleId();
            int i11 = 0;
            while (true) {
                i2 = this.fElemMapSize;
                if (i11 >= i2 || particleId == this.fElemMapId[i11]) {
                    break;
                } else {
                    i11++;
                }
            }
            if (i11 == i2) {
                XSCMLeaf xSCMLeaf = this.fLeafList[i10];
                this.fElemMap[i2] = xSCMLeaf.getLeaf();
                if (xSCMLeaf instanceof XSCMRepeatingLeaf) {
                    if (occurenceArr == null) {
                        occurenceArr = new Occurence[this.fLeafCount];
                    }
                    occurenceArr[this.fElemMapSize] = new Occurence((XSCMRepeatingLeaf) xSCMLeaf, this.fElemMapSize);
                }
                int[] iArr = this.fElemMapType;
                int i12 = this.fElemMapSize;
                iArr[i12] = this.fLeafListType[i10];
                this.fElemMapId[i12] = particleId;
                this.fElemMapSize = i12 + 1;
            }
            i10++;
        }
        int i13 = this.fElemMapSize - 1;
        this.fElemMapSize = i13;
        int[] iArr2 = new int[i + i13];
        int i14 = 0;
        int i15 = 0;
        while (i14 < this.fElemMapSize) {
            int i16 = this.fElemMapId[i14];
            for (int i17 = 0; i17 < this.fLeafCount; i17++) {
                if (i16 == this.fLeafList[i17].getParticleId()) {
                    iArr2[i15] = i17;
                    i15++;
                }
            }
            iArr2[i15] = -1;
            i14++;
            i15++;
        }
        int i18 = this.fLeafCount * 4;
        CMStateSet[] cMStateSetArr = new CMStateSet[i18];
        this.fFinalStateFlags = new boolean[i18];
        this.fTransTable = new int[i18][];
        CMStateSet cMStateSetFirstPos = this.fHeadNode.firstPos();
        this.fTransTable[0] = makeDefStateList();
        cMStateSetArr[0] = cMStateSetFirstPos;
        HashMap map = new HashMap();
        int i19 = 0;
        while (i19 < i5) {
            CMStateSet cMStateSet2 = cMStateSetArr[i19];
            int[] iArr3 = this.fTransTable[i19];
            this.fFinalStateFlags[i19] = cMStateSet2.getBit(i3);
            i19++;
            int i20 = 0;
            int i21 = 0;
            CMStateSet cMStateSet3 = cMStateSet;
            while (i20 < this.fElemMapSize) {
                if (cMStateSet3 == null) {
                    cMStateSet3 = new CMStateSet(this.fLeafCount);
                } else {
                    cMStateSet3.zeroBits();
                }
                int i22 = i21 + 1;
                int i23 = iArr2[i21];
                i21 = i22;
                int i24 = i23;
                while (i24 != i6) {
                    if (cMStateSet2.getBit(i24)) {
                        cMStateSet3.union(this.fFollowList[i24]);
                    }
                    int i25 = iArr2[i21];
                    i21++;
                    i24 = i25;
                    i6 = -1;
                }
                if (!cMStateSet3.isEmpty()) {
                    Integer num = (Integer) map.get(cMStateSet3);
                    int iIntValue = num == null ? i5 : num.intValue();
                    if (iIntValue == i5) {
                        cMStateSetArr[i5] = cMStateSet3;
                        this.fTransTable[i5] = makeDefStateList();
                        map.put(cMStateSet3, new Integer(i5));
                        i5++;
                        cMStateSet3 = null;
                    }
                    iArr3[i20] = iIntValue;
                    if (i5 == i18) {
                        int i26 = (int) (((double) i18) * 1.5d);
                        CMStateSet[] cMStateSetArr2 = new CMStateSet[i26];
                        boolean[] zArr = new boolean[i26];
                        int[][] iArr4 = new int[i26][];
                        System.arraycopy(cMStateSetArr, 0, cMStateSetArr2, 0, i18);
                        System.arraycopy(this.fFinalStateFlags, 0, zArr, 0, i18);
                        System.arraycopy(this.fTransTable, 0, iArr4, 0, i18);
                        this.fFinalStateFlags = zArr;
                        this.fTransTable = iArr4;
                        i18 = i26;
                        cMStateSetArr = cMStateSetArr2;
                    }
                }
                i20++;
                i3 = i3;
                iArr2 = iArr2;
                i6 = -1;
            }
            cMStateSet = null;
        }
        if (occurenceArr != null) {
            this.fCountingStates = new Occurence[i5];
            for (int i27 = 0; i27 < i5; i27++) {
                int[] iArr5 = this.fTransTable[i27];
                for (int i28 = 0; i28 < iArr5.length; i28++) {
                    if (i27 == iArr5[i28]) {
                        this.fCountingStates[i27] = occurenceArr[i28];
                        break;
                    }
                }
            }
        }
        this.fHeadNode = null;
        this.fLeafList = null;
        this.fFollowList = null;
        this.fLeafListType = null;
        this.fElemMapId = null;
    }

    private void calcFollowList(CMNode cMNode) {
        CMNode child;
        if (cMNode.type() == 101) {
            XSCMBinOp xSCMBinOp = (XSCMBinOp) cMNode;
            calcFollowList(xSCMBinOp.getLeft());
            child = xSCMBinOp.getRight();
        } else {
            int i = 0;
            if (cMNode.type() == 102) {
                XSCMBinOp xSCMBinOp2 = (XSCMBinOp) cMNode;
                calcFollowList(xSCMBinOp2.getLeft());
                calcFollowList(xSCMBinOp2.getRight());
                CMStateSet cMStateSetLastPos = xSCMBinOp2.getLeft().lastPos();
                CMStateSet cMStateSetFirstPos = xSCMBinOp2.getRight().firstPos();
                while (i < this.fLeafCount) {
                    if (cMStateSetLastPos.getBit(i)) {
                        this.fFollowList[i].union(cMStateSetFirstPos);
                    }
                    i++;
                }
                return;
            }
            if (cMNode.type() == 4 || cMNode.type() == 6) {
                calcFollowList(((XSCMUniOp) cMNode).getChild());
                CMStateSet cMStateSetFirstPos2 = cMNode.firstPos();
                CMStateSet cMStateSetLastPos2 = cMNode.lastPos();
                while (i < this.fLeafCount) {
                    if (cMStateSetLastPos2.getBit(i)) {
                        this.fFollowList[i].union(cMStateSetFirstPos2);
                    }
                    i++;
                }
                return;
            }
            if (cMNode.type() != 5) {
                return;
            } else {
                child = ((XSCMUniOp) cMNode).getChild();
            }
        }
        calcFollowList(child);
    }

    private void dumpTree(CMNode cMNode, int i) {
        PrintStream printStream;
        String str;
        CMNode child;
        int i2;
        PrintStream printStream2;
        String str2;
        for (int i3 = 0; i3 < i; i3++) {
            System.out.print("   ");
        }
        int iType = cMNode.type();
        if (iType == 1) {
            XSCMLeaf xSCMLeaf = (XSCMLeaf) cMNode;
            System.out.print("Leaf: (pos=" + xSCMLeaf.getPosition() + "), (elemIndex=" + xSCMLeaf.getLeaf() + ") ");
            if (cMNode.isNullable()) {
                printStream = System.out;
                str = " Nullable ";
            }
            System.out.print("firstPos=");
            System.out.print(cMNode.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(cMNode.lastPos().toString());
        }
        if (iType != 2) {
            if (iType == 4 || iType == 5 || iType == 6) {
                System.out.print("Rep Node ");
                if (cMNode.isNullable()) {
                    System.out.print("Nullable ");
                }
                System.out.print("firstPos=");
                System.out.print(cMNode.firstPos().toString());
                System.out.print(" lastPos=");
                System.out.println(cMNode.lastPos().toString());
                child = ((XSCMUniOp) cMNode).getChild();
                i2 = i + 1;
            } else {
                if (iType != 101 && iType != 102) {
                    throw new RuntimeException("ImplementationMessages.VAL_NIICM");
                }
                if (iType == 101) {
                    printStream2 = System.out;
                    str2 = "Choice Node ";
                } else {
                    printStream2 = System.out;
                    str2 = "Seq Node ";
                }
                printStream2.print(str2);
                if (cMNode.isNullable()) {
                    System.out.print("Nullable ");
                }
                System.out.print("firstPos=");
                System.out.print(cMNode.firstPos().toString());
                System.out.print(" lastPos=");
                System.out.println(cMNode.lastPos().toString());
                XSCMBinOp xSCMBinOp = (XSCMBinOp) cMNode;
                i2 = i + 1;
                dumpTree(xSCMBinOp.getLeft(), i2);
                child = xSCMBinOp.getRight();
            }
            dumpTree(child, i2);
            return;
        }
        printStream = System.out;
        str = "Any Node: ";
        printStream.print(str);
        System.out.print("firstPos=");
        System.out.print(cMNode.firstPos().toString());
        System.out.print(" lastPos=");
        System.out.println(cMNode.lastPos().toString());
    }

    private int[] makeDefStateList() {
        int[] iArr = new int[this.fElemMapSize];
        for (int i = 0; i < this.fElemMapSize; i++) {
            iArr[i] = -1;
        }
        return iArr;
    }

    private void postTreeBuildInit(CMNode cMNode) throws RuntimeException {
        CMNode right;
        cMNode.setMaxStates(this.fLeafCount);
        if (cMNode.type() == 2) {
            XSCMLeaf xSCMLeaf = (XSCMLeaf) cMNode;
            int position = xSCMLeaf.getPosition();
            this.fLeafList[position] = xSCMLeaf;
            this.fLeafListType[position] = 2;
            return;
        }
        if (cMNode.type() == 101 || cMNode.type() == 102) {
            XSCMBinOp xSCMBinOp = (XSCMBinOp) cMNode;
            postTreeBuildInit(xSCMBinOp.getLeft());
            right = xSCMBinOp.getRight();
        } else {
            if (cMNode.type() != 4 && cMNode.type() != 6 && cMNode.type() != 5) {
                if (cMNode.type() != 1) {
                    throw new RuntimeException("ImplementationMessages.VAL_NIICM");
                }
                XSCMLeaf xSCMLeaf2 = (XSCMLeaf) cMNode;
                int position2 = xSCMLeaf2.getPosition();
                this.fLeafList[position2] = xSCMLeaf2;
                this.fLeafListType[position2] = 1;
                return;
            }
            right = ((XSCMUniOp) cMNode).getChild();
        }
        postTreeBuildInit(right);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0070  */
    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public boolean checkUniqueParticleAttribution(SubstitutionGroupHandler substitutionGroupHandler) throws XMLSchemaException {
        Occurence occurence;
        int i = this.fElemMapSize;
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, i, i);
        int i2 = 0;
        while (true) {
            int[][] iArr = this.fTransTable;
            if (i2 >= iArr.length || iArr[i2] == null) {
                break;
            }
            int i3 = 0;
            while (i3 < this.fElemMapSize) {
                int i4 = i3 + 1;
                for (int i5 = i4; i5 < this.fElemMapSize; i5++) {
                    int[] iArr2 = this.fTransTable[i2];
                    if (iArr2[i3] != -1 && iArr2[i5] != -1 && bArr[i3][i5] == 0) {
                        Object[] objArr = this.fElemMap;
                        if (XSConstraints.overlapUPA(objArr[i3], objArr[i5], substitutionGroupHandler)) {
                            Occurence[] occurenceArr = this.fCountingStates;
                            if (occurenceArr == null || (occurence = occurenceArr[i2]) == null) {
                                bArr[i3][i5] = 1;
                            } else {
                                int[] iArr3 = this.fTransTable[i2];
                                if (((iArr3[i5] == i2) ^ (iArr3[i3] == i2)) && occurence.minOccurs == occurence.maxOccurs) {
                                    bArr[i3][i5] = -1;
                                } else {
                                    bArr[i3][i5] = 1;
                                }
                            }
                        } else {
                            bArr[i3][i5] = -1;
                        }
                    }
                }
                i3 = i4;
            }
            i2++;
        }
        for (int i6 = 0; i6 < this.fElemMapSize; i6++) {
            for (int i7 = 0; i7 < this.fElemMapSize; i7++) {
                if (bArr[i6][i7] == 1) {
                    throw new XMLSchemaException("cos-nonambig", new Object[]{this.fElemMap[i6].toString(), this.fElemMap[i7].toString()});
                }
            }
        }
        for (int i8 = 0; i8 < this.fElemMapSize; i8++) {
            if (this.fElemMapType[i8] == 2) {
                XSWildcardDecl xSWildcardDecl = (XSWildcardDecl) this.fElemMap[i8];
                if (xSWildcardDecl.fType == 3 || xSWildcardDecl.fType == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public boolean endContentModel(int[] iArr) {
        Occurence occurence;
        int i = iArr[0];
        if (!this.fFinalStateFlags[i]) {
            return false;
        }
        Occurence[] occurenceArr = this.fCountingStates;
        return occurenceArr == null || (occurence = occurenceArr[i]) == null || iArr[2] >= occurence.minOccurs;
    }

    Object findMatchingDecl(QName qName, SubstitutionGroupHandler substitutionGroupHandler) {
        for (int i = 0; i < this.fElemMapSize; i++) {
            int i2 = this.fElemMapType[i];
            if (i2 == 1) {
                XSElementDecl matchingElemDecl = substitutionGroupHandler.getMatchingElemDecl(qName, (XSElementDecl) this.fElemMap[i]);
                if (matchingElemDecl != null) {
                    return matchingElemDecl;
                }
            } else if (i2 == 2 && ((XSWildcardDecl) this.fElemMap[i]).allowNamespace(qName.uri)) {
                return this.fElemMap[i];
            }
        }
        return null;
    }

    Object findMatchingDecl(QName qName, int[] iArr, SubstitutionGroupHandler substitutionGroupHandler, int i) {
        int i2 = iArr[0];
        Object matchingElemDecl = null;
        int i3 = 0;
        while (true) {
            i++;
            if (i >= this.fElemMapSize) {
                break;
            }
            i3 = this.fTransTable[i2][i];
            if (i3 != -1) {
                int i4 = this.fElemMapType[i];
                if (i4 != 1) {
                    if (i4 == 2 && ((XSWildcardDecl) this.fElemMap[i]).allowNamespace(qName.uri)) {
                        matchingElemDecl = this.fElemMap[i];
                        break;
                    }
                } else {
                    matchingElemDecl = substitutionGroupHandler.getMatchingElemDecl(qName, (XSElementDecl) this.fElemMap[i]);
                    if (matchingElemDecl != null) {
                        break;
                    }
                }
            }
        }
        if (i == this.fElemMapSize) {
            iArr[1] = iArr[0];
            iArr[0] = -1;
            return findMatchingDecl(qName, substitutionGroupHandler);
        }
        iArr[0] = i3;
        Occurence occurence = this.fCountingStates[i3];
        if (occurence != null) {
            iArr[2] = i == occurence.elemIndex ? 1 : 0;
        }
        return matchingElemDecl;
    }

    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public String getTermName(int i) {
        Object obj = this.fElemMap[i];
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public boolean isCompactedForUPA() {
        return this.fIsCompactedForUPA;
    }

    public boolean isFinalState(int i) {
        if (i < 0) {
            return false;
        }
        return this.fFinalStateFlags[i];
    }

    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public int[] occurenceInfo(int[] iArr) {
        Occurence[] occurenceArr = this.fCountingStates;
        if (occurenceArr == null) {
            return null;
        }
        int i = iArr[0];
        if (i < 0) {
            i = iArr[1];
        }
        Occurence occurence = occurenceArr[i];
        if (occurence != null) {
            return new int[]{occurence.minOccurs, occurence.maxOccurs, iArr[2], occurence.elemIndex};
        }
        return null;
    }

    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public Object oneTransition(QName qName, int[] iArr, SubstitutionGroupHandler substitutionGroupHandler) {
        int i = iArr[0];
        if (i == -1 || i == -2) {
            if (i == -1) {
                iArr[0] = -2;
            }
            return findMatchingDecl(qName, substitutionGroupHandler);
        }
        Object matchingElemDecl = null;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= this.fElemMapSize) {
                break;
            }
            i3 = this.fTransTable[i][i2];
            if (i3 != -1) {
                int i4 = this.fElemMapType[i2];
                if (i4 != 1) {
                    if (i4 == 2 && ((XSWildcardDecl) this.fElemMap[i2]).allowNamespace(qName.uri)) {
                        matchingElemDecl = this.fElemMap[i2];
                        break;
                    }
                } else {
                    matchingElemDecl = substitutionGroupHandler.getMatchingElemDecl(qName, (XSElementDecl) this.fElemMap[i2]);
                    if (matchingElemDecl != null) {
                        break;
                    }
                }
            }
            i2++;
        }
        if (i2 != this.fElemMapSize) {
            Occurence[] occurenceArr = this.fCountingStates;
            if (occurenceArr != null) {
                Occurence occurence = occurenceArr[i];
                if (occurence == null) {
                    Occurence occurence2 = occurenceArr[i3];
                    if (occurence2 != null) {
                        iArr[2] = i2 != occurence2.elemIndex ? 0 : 1;
                    }
                } else if (i == i3) {
                    int i5 = iArr[2] + 1;
                    iArr[2] = i5;
                    if (i5 > occurence.maxOccurs && occurence.maxOccurs != -1) {
                        return findMatchingDecl(qName, iArr, substitutionGroupHandler, i2);
                    }
                } else if (iArr[2] < occurence.minOccurs) {
                    iArr[1] = iArr[0];
                    iArr[0] = -1;
                } else {
                    Occurence occurence3 = this.fCountingStates[i3];
                    if (occurence3 != null) {
                        iArr[2] = i2 != occurence3.elemIndex ? 0 : 1;
                    }
                }
            }
            iArr[0] = i3;
            return matchingElemDecl;
        }
        iArr[1] = iArr[0];
        iArr[0] = -1;
        return findMatchingDecl(qName, substitutionGroupHandler);
    }

    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public int[] startContentModel() {
        return new int[3];
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0037  */
    @Override // org.apache.xerces.impl.xs.models.XSCMValidator
    public Vector whatCanGoHere(int[] iArr) {
        int i = iArr[0];
        if (i < 0) {
            i = iArr[1];
        }
        Occurence[] occurenceArr = this.fCountingStates;
        Occurence occurence = occurenceArr != null ? occurenceArr[i] : null;
        int i2 = iArr[2];
        Vector vector = new Vector();
        for (int i3 = 0; i3 < this.fElemMapSize; i3++) {
            int i4 = this.fTransTable[i][i3];
            if (i4 != -1) {
                if (occurence == null) {
                    vector.addElement(this.fElemMap[i3]);
                } else if (i == i4) {
                    if (i2 < occurence.maxOccurs || occurence.maxOccurs == -1) {
                        vector.addElement(this.fElemMap[i3]);
                    }
                } else if (i2 >= occurence.minOccurs) {
                    vector.addElement(this.fElemMap[i3]);
                }
            }
        }
        return vector;
    }
}
