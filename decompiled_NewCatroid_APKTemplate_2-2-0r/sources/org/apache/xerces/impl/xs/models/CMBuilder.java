package org.apache.xerces.impl.xs.models;

import org.apache.xerces.impl.dtd.models.CMNode;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.xs.XSTerm;

/* JADX INFO: loaded from: classes13.dex */
public class CMBuilder {
    private static final XSEmptyCM fEmptyCM = new XSEmptyCM();
    private XSDeclarationPool fDeclPool = null;
    private int fLeafCount;
    private final CMNodeFactory fNodeFactory;
    private int fParticleCount;

    public CMBuilder(CMNodeFactory cMNodeFactory) {
        this.fNodeFactory = cMNodeFactory;
    }

    private CMNode buildCompactSyntaxTree(XSParticleDecl xSParticleDecl) {
        int i = xSParticleDecl.fMaxOccurs;
        int i2 = xSParticleDecl.fMinOccurs;
        short s = xSParticleDecl.fType;
        if (s == 2 || s == 1) {
            return buildCompactSyntaxTree2(xSParticleDecl, i2, i);
        }
        CMNode cMNode = null;
        if (s != 3) {
            return null;
        }
        XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSParticleDecl.fValue;
        if (xSModelGroupImpl.fParticleCount == 1 && (i2 != 1 || i != 1)) {
            return buildCompactSyntaxTree2(xSModelGroupImpl.fParticles[0], i2, i);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < xSModelGroupImpl.fParticleCount; i4++) {
            CMNode cMNodeBuildCompactSyntaxTree = buildCompactSyntaxTree(xSModelGroupImpl.fParticles[i4]);
            if (cMNodeBuildCompactSyntaxTree != null) {
                i3++;
                if (cMNode != null) {
                    cMNodeBuildCompactSyntaxTree = this.fNodeFactory.getCMBinOpNode(xSModelGroupImpl.fCompositor, cMNode, cMNodeBuildCompactSyntaxTree);
                }
                cMNode = cMNodeBuildCompactSyntaxTree;
            }
        }
        return (cMNode == null || xSModelGroupImpl.fCompositor != 101 || i3 >= xSModelGroupImpl.fParticleCount) ? cMNode : this.fNodeFactory.getCMUniOpNode(5, cMNode);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0096, code lost:
    
        if (r13 == 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.apache.xerces.impl.dtd.models.CMNode buildCompactSyntaxTree2(org.apache.xerces.impl.xs.XSParticleDecl r12, int r13, int r14) {
        /*
            r11 = this;
            r0 = 1
            if (r13 != r0) goto L1d
            if (r14 != r0) goto L1d
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            short r14 = r12.fType
            org.apache.xerces.xs.XSTerm r12 = r12.fValue
            int r0 = r11.fParticleCount
            int r1 = r0 + 1
            r11.fParticleCount = r1
            int r1 = r11.fLeafCount
            int r2 = r1 + 1
            r11.fLeafCount = r2
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMLeafNode(r14, r12, r0, r1)
            goto L9f
        L1d:
            if (r13 != 0) goto L3f
            if (r14 != r0) goto L3f
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            short r14 = r12.fType
            org.apache.xerces.xs.XSTerm r12 = r12.fValue
            int r0 = r11.fParticleCount
            int r1 = r0 + 1
            r11.fParticleCount = r1
            int r1 = r11.fLeafCount
            int r2 = r1 + 1
            r11.fLeafCount = r2
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMLeafNode(r14, r12, r0, r1)
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            r14 = 5
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMUniOpNode(r14, r12)
            goto L9f
        L3f:
            r1 = 4
            r2 = -1
            if (r13 != 0) goto L62
            if (r14 != r2) goto L62
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            short r14 = r12.fType
            org.apache.xerces.xs.XSTerm r12 = r12.fValue
            int r0 = r11.fParticleCount
            int r2 = r0 + 1
            r11.fParticleCount = r2
            int r2 = r11.fLeafCount
            int r3 = r2 + 1
            r11.fLeafCount = r3
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMLeafNode(r14, r12, r0, r2)
        L5b:
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMUniOpNode(r1, r12)
            goto L9f
        L62:
            r3 = 6
            if (r13 != r0) goto L7e
            if (r14 != r2) goto L7e
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            short r14 = r12.fType
            org.apache.xerces.xs.XSTerm r12 = r12.fValue
            int r0 = r11.fParticleCount
            int r1 = r0 + 1
            r11.fParticleCount = r1
            int r1 = r11.fLeafCount
            int r2 = r1 + 1
            r11.fLeafCount = r2
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMLeafNode(r14, r12, r0, r1)
            goto L99
        L7e:
            org.apache.xerces.impl.xs.models.CMNodeFactory r4 = r11.fNodeFactory
            short r5 = r12.fType
            org.apache.xerces.xs.XSTerm r6 = r12.fValue
            int r9 = r11.fParticleCount
            int r12 = r9 + 1
            r11.fParticleCount = r12
            int r10 = r11.fLeafCount
            int r12 = r10 + 1
            r11.fLeafCount = r12
            r7 = r13
            r8 = r14
            org.apache.xerces.impl.dtd.models.CMNode r12 = r4.getCMRepeatingLeafNode(r5, r6, r7, r8, r9, r10)
            if (r13 != 0) goto L99
            goto L5b
        L99:
            org.apache.xerces.impl.xs.models.CMNodeFactory r13 = r11.fNodeFactory
            org.apache.xerces.impl.dtd.models.CMNode r12 = r13.getCMUniOpNode(r3, r12)
        L9f:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.xs.models.CMBuilder.buildCompactSyntaxTree2(org.apache.xerces.impl.xs.XSParticleDecl, int, int):org.apache.xerces.impl.dtd.models.CMNode");
    }

    private CMNode buildSyntaxTree(XSParticleDecl xSParticleDecl, boolean z) {
        boolean zIsCompactedForUPA;
        CMNode cMNodeExpandContentModel;
        int i = xSParticleDecl.fMaxOccurs;
        int i2 = xSParticleDecl.fMinOccurs;
        if (z) {
            if (i2 <= 1) {
                zIsCompactedForUPA = false;
            } else if (i > i2 || xSParticleDecl.getMaxOccursUnbounded()) {
                i2 = 1;
                zIsCompactedForUPA = true;
            } else {
                i2 = 2;
                zIsCompactedForUPA = true;
            }
            if (i > 1) {
                i = 2;
                zIsCompactedForUPA = true;
            }
        } else {
            zIsCompactedForUPA = false;
        }
        short s = xSParticleDecl.fType;
        if (s == 2 || s == 1) {
            CMNodeFactory cMNodeFactory = this.fNodeFactory;
            short s2 = xSParticleDecl.fType;
            XSTerm xSTerm = xSParticleDecl.fValue;
            int i3 = this.fParticleCount;
            this.fParticleCount = i3 + 1;
            int i4 = this.fLeafCount;
            this.fLeafCount = i4 + 1;
            cMNodeExpandContentModel = expandContentModel(cMNodeFactory.getCMLeafNode(s2, xSTerm, i3, i4), i2, i);
            if (cMNodeExpandContentModel != null) {
                cMNodeExpandContentModel.setIsCompactUPAModel(zIsCompactedForUPA);
            }
        } else {
            cMNodeExpandContentModel = null;
            if (s == 3) {
                XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSParticleDecl.fValue;
                int i5 = 0;
                for (int i6 = 0; i6 < xSModelGroupImpl.fParticleCount; i6++) {
                    CMNode cMNodeBuildSyntaxTree = buildSyntaxTree(xSModelGroupImpl.fParticles[i6], z);
                    if (cMNodeBuildSyntaxTree != null) {
                        zIsCompactedForUPA |= cMNodeBuildSyntaxTree.isCompactedForUPA();
                        i5++;
                        cMNodeExpandContentModel = cMNodeExpandContentModel == null ? cMNodeBuildSyntaxTree : this.fNodeFactory.getCMBinOpNode(xSModelGroupImpl.fCompositor, cMNodeExpandContentModel, cMNodeBuildSyntaxTree);
                    }
                }
                if (cMNodeExpandContentModel != null) {
                    if (xSModelGroupImpl.fCompositor == 101 && i5 < xSModelGroupImpl.fParticleCount) {
                        cMNodeExpandContentModel = this.fNodeFactory.getCMUniOpNode(5, cMNodeExpandContentModel);
                    }
                    cMNodeExpandContentModel = expandContentModel(cMNodeExpandContentModel, i2, i);
                    cMNodeExpandContentModel.setIsCompactUPAModel(zIsCompactedForUPA);
                }
            }
        }
        return cMNodeExpandContentModel;
    }

    private CMNode copyNode(CMNode cMNode) {
        int iType = cMNode.type();
        if (iType == 101 || iType == 102) {
            XSCMBinOp xSCMBinOp = (XSCMBinOp) cMNode;
            return this.fNodeFactory.getCMBinOpNode(iType, copyNode(xSCMBinOp.getLeft()), copyNode(xSCMBinOp.getRight()));
        }
        if (iType == 4 || iType == 6 || iType == 5) {
            return this.fNodeFactory.getCMUniOpNode(iType, copyNode(((XSCMUniOp) cMNode).getChild()));
        }
        if (iType != 1 && iType != 2) {
            return cMNode;
        }
        XSCMLeaf xSCMLeaf = (XSCMLeaf) cMNode;
        CMNodeFactory cMNodeFactory = this.fNodeFactory;
        int iType2 = xSCMLeaf.type();
        Object leaf = xSCMLeaf.getLeaf();
        int particleId = xSCMLeaf.getParticleId();
        int i = this.fLeafCount;
        this.fLeafCount = i + 1;
        return cMNodeFactory.getCMLeafNode(iType2, leaf, particleId, i);
    }

    private CMNode expandContentModel(CMNode cMNode, int i, int i2) {
        if (i == 1 && i2 == 1) {
            return cMNode;
        }
        if (i == 0 && i2 == 1) {
            return this.fNodeFactory.getCMUniOpNode(5, cMNode);
        }
        if (i == 0 && i2 == -1) {
            return this.fNodeFactory.getCMUniOpNode(4, cMNode);
        }
        if (i == 1 && i2 == -1) {
            return this.fNodeFactory.getCMUniOpNode(6, cMNode);
        }
        if (i2 == -1) {
            return this.fNodeFactory.getCMBinOpNode(102, multiNodes(cMNode, i - 1, true), this.fNodeFactory.getCMUniOpNode(6, cMNode));
        }
        CMNode cMNodeMultiNodes = i > 0 ? multiNodes(cMNode, i, false) : null;
        if (i2 <= i) {
            return cMNodeMultiNodes;
        }
        CMNode cMUniOpNode = this.fNodeFactory.getCMUniOpNode(5, cMNode);
        return cMNodeMultiNodes == null ? multiNodes(cMUniOpNode, i2 - i, false) : this.fNodeFactory.getCMBinOpNode(102, cMNodeMultiNodes, multiNodes(cMUniOpNode, i2 - i, true));
    }

    private CMNode multiNodes(CMNode cMNode, int i, boolean z) {
        if (i == 0) {
            return null;
        }
        if (i == 1) {
            return z ? copyNode(cMNode) : cMNode;
        }
        int i2 = i / 2;
        return this.fNodeFactory.getCMBinOpNode(102, multiNodes(cMNode, i2, z), multiNodes(cMNode, i - i2, true));
    }

    private boolean useRepeatingLeafNodes(XSParticleDecl xSParticleDecl) {
        int i = xSParticleDecl.fMaxOccurs;
        int i2 = xSParticleDecl.fMinOccurs;
        if (xSParticleDecl.fType != 3) {
            return true;
        }
        XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSParticleDecl.fValue;
        if (i2 != 1 || i != 1) {
            if (xSModelGroupImpl.fParticleCount != 1) {
                return xSModelGroupImpl.fParticleCount == 0;
            }
            XSParticleDecl xSParticleDecl2 = xSModelGroupImpl.fParticles[0];
            short s = xSParticleDecl2.fType;
            return (s == 1 || s == 2) && xSParticleDecl2.fMinOccurs == 1 && xSParticleDecl2.fMaxOccurs == 1;
        }
        for (int i3 = 0; i3 < xSModelGroupImpl.fParticleCount; i3++) {
            if (!useRepeatingLeafNodes(xSModelGroupImpl.fParticles[i3])) {
                return false;
            }
        }
        return true;
    }

    XSCMValidator createAllCM(XSParticleDecl xSParticleDecl) {
        if (xSParticleDecl.fMaxOccurs == 0) {
            return null;
        }
        XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSParticleDecl.fValue;
        XSAllCM xSAllCM = new XSAllCM(xSParticleDecl.fMinOccurs == 0, xSModelGroupImpl.fParticleCount);
        for (int i = 0; i < xSModelGroupImpl.fParticleCount; i++) {
            xSAllCM.addElement((XSElementDecl) xSModelGroupImpl.fParticles[i].fValue, xSModelGroupImpl.fParticles[i].fMinOccurs == 0);
        }
        return xSAllCM;
    }

    XSCMValidator createDFACM(XSParticleDecl xSParticleDecl, boolean z) {
        this.fLeafCount = 0;
        this.fParticleCount = 0;
        CMNode cMNodeBuildCompactSyntaxTree = useRepeatingLeafNodes(xSParticleDecl) ? buildCompactSyntaxTree(xSParticleDecl) : buildSyntaxTree(xSParticleDecl, z);
        if (cMNodeBuildCompactSyntaxTree == null) {
            return null;
        }
        return new XSDFACM(cMNodeBuildCompactSyntaxTree, this.fLeafCount);
    }

    public XSCMValidator getContentModel(XSComplexTypeDecl xSComplexTypeDecl, boolean z) {
        short contentType = xSComplexTypeDecl.getContentType();
        if (contentType == 1 || contentType == 0) {
            return null;
        }
        XSParticleDecl xSParticleDecl = (XSParticleDecl) xSComplexTypeDecl.getParticle();
        if (xSParticleDecl == null) {
            return fEmptyCM;
        }
        XSCMValidator xSCMValidatorCreateAllCM = (xSParticleDecl.fType == 3 && ((XSModelGroupImpl) xSParticleDecl.fValue).fCompositor == 103) ? createAllCM(xSParticleDecl) : createDFACM(xSParticleDecl, z);
        this.fNodeFactory.resetNodeCount();
        return xSCMValidatorCreateAllCM == null ? fEmptyCM : xSCMValidatorCreateAllCM;
    }

    public void setDeclPool(XSDeclarationPool xSDeclarationPool) {
        this.fDeclPool = xSDeclarationPool;
    }
}
