package org.apache.xerces.impl.dtd.models;

import java.io.PrintStream;
import java.util.HashMap;
import org.apache.xerces.xni.QName;

/* JADX INFO: loaded from: classes13.dex */
public class DFAContentModel implements ContentModelValidator {
    private static final boolean DEBUG_VALIDATE_CONTENT = false;
    private int fLeafCount;
    private boolean fMixed;
    private static String fEpsilonString = "<<CMNODE_EPSILON>>".intern();
    private static String fEOCString = fEOCString.intern();
    private static String fEOCString = fEOCString.intern();
    private QName[] fElemMap = null;
    private int[] fElemMapType = null;
    private int fElemMapSize = 0;
    private int fEOCPos = 0;
    private boolean[] fFinalStateFlags = null;
    private CMStateSet[] fFollowList = null;
    private CMNode fHeadNode = null;
    private CMLeaf[] fLeafList = null;
    private int[] fLeafListType = null;
    private int[][] fTransTable = null;
    private int fTransTableSize = 0;
    private boolean fEmptyContentIsValid = false;
    private final QName fQName = new QName();

    public DFAContentModel(CMNode cMNode, int i, boolean z) {
        this.fLeafCount = 0;
        this.fLeafCount = i;
        this.fMixed = z;
        buildDFA(cMNode);
    }

    /* JADX WARN: Code duplicated, block: B:56:0x0190 A[PHI: r5 r13
      0x0190: PHI (r5v4 int) = (r5v3 int), (r5v6 int) binds: [B:46:0x0142, B:54:0x016b] A[DONT_GENERATE, DONT_INLINE]
      0x0190: PHI (r13v5 org.apache.xerces.impl.dtd.models.CMStateSet) = (r13v4 org.apache.xerces.impl.dtd.models.CMStateSet), (r13v7 org.apache.xerces.impl.dtd.models.CMStateSet) binds: [B:46:0x0142, B:54:0x016b] A[DONT_GENERATE, DONT_INLINE]] */
    private void buildDFA(CMNode cMNode) {
        int i;
        int i2;
        int i3;
        int i4;
        QName qName = this.fQName;
        String str = fEOCString;
        CMStateSet cMStateSet = null;
        qName.setValues(null, str, str, null);
        CMLeaf cMLeaf = new CMLeaf(this.fQName);
        this.fHeadNode = new CMBinOp(5, cMNode, cMLeaf);
        int i5 = this.fLeafCount;
        this.fEOCPos = i5;
        this.fLeafCount = i5 + 1;
        cMLeaf.setPosition(i5);
        int i6 = this.fLeafCount;
        this.fLeafList = new CMLeaf[i6];
        this.fLeafListType = new int[i6];
        int i7 = 0;
        postTreeBuildInit(this.fHeadNode, 0);
        this.fFollowList = new CMStateSet[this.fLeafCount];
        for (int i8 = 0; i8 < this.fLeafCount; i8++) {
            this.fFollowList[i8] = new CMStateSet(this.fLeafCount);
        }
        calcFollowList(this.fHeadNode);
        int i9 = this.fLeafCount;
        this.fElemMap = new QName[i9];
        this.fElemMapType = new int[i9];
        this.fElemMapSize = 0;
        int i10 = 0;
        while (true) {
            i = this.fLeafCount;
            i2 = 1;
            if (i10 >= i) {
                break;
            }
            this.fElemMap[i10] = new QName();
            QName element = this.fLeafList[i10].getElement();
            int i11 = 0;
            while (i11 < this.fElemMapSize && this.fElemMap[i11].rawname != element.rawname) {
                i11++;
            }
            int i12 = this.fElemMapSize;
            if (i11 == i12) {
                this.fElemMap[i12].setValues(element);
                int[] iArr = this.fElemMapType;
                int i13 = this.fElemMapSize;
                iArr[i13] = this.fLeafListType[i10];
                this.fElemMapSize = i13 + 1;
            }
            i10++;
        }
        int[] iArr2 = new int[i + this.fElemMapSize];
        int i14 = 0;
        int i15 = 0;
        while (true) {
            i3 = -1;
            if (i14 >= this.fElemMapSize) {
                break;
            }
            for (int i16 = 0; i16 < this.fLeafCount; i16++) {
                if (this.fLeafList[i16].getElement().rawname == this.fElemMap[i14].rawname) {
                    iArr2[i15] = i16;
                    i15++;
                }
            }
            iArr2[i15] = -1;
            i14++;
            i15++;
        }
        int i17 = this.fLeafCount * 4;
        CMStateSet[] cMStateSetArr = new CMStateSet[i17];
        this.fFinalStateFlags = new boolean[i17];
        this.fTransTable = new int[i17][];
        CMStateSet cMStateSetFirstPos = this.fHeadNode.firstPos();
        this.fTransTable[0] = makeDefStateList();
        cMStateSetArr[0] = cMStateSetFirstPos;
        HashMap map = new HashMap();
        int i18 = 0;
        while (i18 < i2) {
            CMStateSet cMStateSet2 = cMStateSetArr[i18];
            int[] iArr3 = this.fTransTable[i18];
            this.fFinalStateFlags[i18] = cMStateSet2.getBit(this.fEOCPos);
            i18++;
            int i19 = i7;
            int i20 = i19;
            CMStateSet cMStateSet3 = cMStateSet;
            while (i19 < this.fElemMapSize) {
                if (cMStateSet3 == null) {
                    cMStateSet3 = new CMStateSet(this.fLeafCount);
                } else {
                    cMStateSet3.zeroBits();
                }
                int i21 = i20 + 1;
                int i22 = iArr2[i20];
                while (i22 != i3) {
                    if (cMStateSet2.getBit(i22)) {
                        cMStateSet3.union(this.fFollowList[i22]);
                    }
                    i22 = iArr2[i21];
                    i21++;
                    i3 = -1;
                }
                if (cMStateSet3.isEmpty()) {
                    i4 = i7;
                } else {
                    Integer num = (Integer) map.get(cMStateSet3);
                    int iIntValue = num == null ? i2 : num.intValue();
                    if (iIntValue == i2) {
                        cMStateSetArr[i2] = cMStateSet3;
                        this.fTransTable[i2] = makeDefStateList();
                        map.put(cMStateSet3, new Integer(i2));
                        i2++;
                        cMStateSet3 = cMStateSet;
                    }
                    iArr3[i19] = iIntValue;
                    if (i2 == i17) {
                        int i23 = (int) (((double) i17) * 1.5d);
                        CMStateSet[] cMStateSetArr2 = new CMStateSet[i23];
                        boolean[] zArr = new boolean[i23];
                        int[][] iArr4 = new int[i23][];
                        i4 = 0;
                        System.arraycopy(cMStateSetArr, 0, cMStateSetArr2, 0, i17);
                        System.arraycopy(this.fFinalStateFlags, 0, zArr, 0, i17);
                        System.arraycopy(this.fTransTable, 0, iArr4, 0, i17);
                        this.fFinalStateFlags = zArr;
                        this.fTransTable = iArr4;
                        i17 = i23;
                        cMStateSetArr = cMStateSetArr2;
                    } else {
                        i4 = i7;
                    }
                }
                i19++;
                i7 = i4;
                i20 = i21;
                iArr2 = iArr2;
                cMStateSet = null;
                i3 = -1;
            }
        }
        this.fEmptyContentIsValid = ((CMBinOp) this.fHeadNode).getLeft().isNullable();
        this.fHeadNode = null;
        this.fLeafList = null;
        this.fFollowList = null;
    }

    private void calcFollowList(CMNode cMNode) {
        CMNode child;
        if (cMNode.type() == 4) {
            CMBinOp cMBinOp = (CMBinOp) cMNode;
            calcFollowList(cMBinOp.getLeft());
            child = cMBinOp.getRight();
        } else {
            int i = 0;
            if (cMNode.type() == 5) {
                CMBinOp cMBinOp2 = (CMBinOp) cMNode;
                calcFollowList(cMBinOp2.getLeft());
                calcFollowList(cMBinOp2.getRight());
                CMStateSet cMStateSetLastPos = cMBinOp2.getLeft().lastPos();
                CMStateSet cMStateSetFirstPos = cMBinOp2.getRight().firstPos();
                while (i < this.fLeafCount) {
                    if (cMStateSetLastPos.getBit(i)) {
                        this.fFollowList[i].union(cMStateSetFirstPos);
                    }
                    i++;
                }
                return;
            }
            if (cMNode.type() == 2 || cMNode.type() == 3) {
                calcFollowList(((CMUniOp) cMNode).getChild());
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
            if (cMNode.type() != 1) {
                return;
            } else {
                child = ((CMUniOp) cMNode).getChild();
            }
        }
        calcFollowList(child);
    }

    private void dumpTree(CMNode cMNode, int i) {
        PrintStream printStream;
        String str;
        int i2;
        CMNode right;
        for (int i3 = 0; i3 < i; i3++) {
            System.out.print("   ");
        }
        int iType = cMNode.type();
        if (iType == 4 || iType == 5) {
            if (iType == 4) {
                printStream = System.out;
                str = "Choice Node ";
            } else {
                printStream = System.out;
                str = "Seq Node ";
            }
            printStream.print(str);
            if (cMNode.isNullable()) {
                System.out.print("Nullable ");
            }
            System.out.print("firstPos=");
            System.out.print(cMNode.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(cMNode.lastPos().toString());
            CMBinOp cMBinOp = (CMBinOp) cMNode;
            i2 = i + 1;
            dumpTree(cMBinOp.getLeft(), i2);
            right = cMBinOp.getRight();
        } else {
            if (cMNode.type() != 2) {
                if (cMNode.type() != 0) {
                    throw new RuntimeException("ImplementationMessages.VAL_NIICM");
                }
                CMLeaf cMLeaf = (CMLeaf) cMNode;
                System.out.print("Leaf: (pos=" + cMLeaf.getPosition() + "), " + cMLeaf.getElement() + "(elemIndex=" + cMLeaf.getElement() + ") ");
                if (cMNode.isNullable()) {
                    System.out.print(" Nullable ");
                }
                System.out.print("firstPos=");
                System.out.print(cMNode.firstPos().toString());
                System.out.print(" lastPos=");
                System.out.println(cMNode.lastPos().toString());
                return;
            }
            System.out.print("Rep Node ");
            if (cMNode.isNullable()) {
                System.out.print("Nullable ");
            }
            System.out.print("firstPos=");
            System.out.print(cMNode.firstPos().toString());
            System.out.print(" lastPos=");
            System.out.println(cMNode.lastPos().toString());
            right = ((CMUniOp) cMNode).getChild();
            i2 = i + 1;
        }
        dumpTree(right, i2);
    }

    private int[] makeDefStateList() {
        int[] iArr = new int[this.fElemMapSize];
        for (int i = 0; i < this.fElemMapSize; i++) {
            iArr[i] = -1;
        }
        return iArr;
    }

    private int postTreeBuildInit(CMNode cMNode, int i) {
        CMNode right;
        cMNode.setMaxStates(this.fLeafCount);
        if ((cMNode.type() & 15) == 6 || (cMNode.type() & 15) == 8 || (cMNode.type() & 15) == 7) {
            CMAny cMAny = (CMAny) cMNode;
            this.fLeafList[i] = new CMLeaf(new QName(null, null, null, cMAny.getURI()), cMAny.getPosition());
            this.fLeafListType[i] = cMNode.type();
            return i + 1;
        }
        if (cMNode.type() == 4 || cMNode.type() == 5) {
            CMBinOp cMBinOp = (CMBinOp) cMNode;
            i = postTreeBuildInit(cMBinOp.getLeft(), i);
            right = cMBinOp.getRight();
        } else {
            if (cMNode.type() != 2 && cMNode.type() != 3 && cMNode.type() != 1) {
                if (cMNode.type() != 0) {
                    throw new RuntimeException("ImplementationMessages.VAL_NIICM: type=" + cMNode.type());
                }
                CMLeaf cMLeaf = (CMLeaf) cMNode;
                if (cMLeaf.getElement().localpart == fEpsilonString) {
                    return i;
                }
                this.fLeafList[i] = cMLeaf;
                this.fLeafListType[i] = 0;
                return i + 1;
            }
            right = ((CMUniOp) cMNode).getChild();
        }
        return postTreeBuildInit(right, i);
    }

    @Override // org.apache.xerces.impl.dtd.models.ContentModelValidator
    public int validate(QName[] qNameArr, int i, int i2) {
        if (i2 == 0) {
            return this.fEmptyContentIsValid ? -1 : 0;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            QName qName = qNameArr[i + i3];
            if (!this.fMixed || qName.localpart != null) {
                int i5 = 0;
                while (i5 < this.fElemMapSize) {
                    int i6 = this.fElemMapType[i5] & 15;
                    if (i6 != 0) {
                        if (i6 != 6) {
                            if (i6 != 8) {
                                if (i6 == 7 && this.fElemMap[i5].uri != qName.uri) {
                                    break;
                                }
                                i5++;
                            } else {
                                if (qName.uri == null) {
                                    break;
                                }
                                i5++;
                            }
                        } else {
                            String str = this.fElemMap[i5].uri;
                            if (str == null || str == qName.uri) {
                                break;
                            }
                            i5++;
                        }
                    } else {
                        if (this.fElemMap[i5].rawname == qName.rawname) {
                            break;
                        }
                        i5++;
                    }
                }
                if (i5 == this.fElemMapSize || (i4 = this.fTransTable[i4][i5]) == -1) {
                    return i3;
                }
            }
            i3++;
        }
        if (this.fFinalStateFlags[i4]) {
            return -1;
        }
        return i2;
    }
}
