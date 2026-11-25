package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class FloatLiteral extends NumberLiteral {
    float value;

    public FloatLiteral(char[] cArr, int i, int i2) {
        super(cArr, i, i2);
    }

    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1093)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:419)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:399)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:31)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:21)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public void computeConstant() {
        /*
            r5 = this;
            char[] r0 = r5.source
            r1 = 95
            int r0 = org.eclipse.jdt.core.compiler.CharOperation.indexOf(r1, r0)
            if (r0 <= 0) goto L12
            char[] r0 = r5.source
            char[] r0 = org.eclipse.jdt.core.compiler.CharOperation.remove(r0, r1)
            r5.source = r0
        L12:
            char[] r0 = r5.source     // Catch: java.lang.NumberFormatException -> L68
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch: java.lang.NumberFormatException -> L68
            java.lang.Float r0 = java.lang.Float.valueOf(r0)     // Catch: java.lang.NumberFormatException -> L68
            float r0 = r0.floatValue()
            r1 = 2139095039(0x7f7fffff, float:3.4028235E38)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 <= 0) goto L28
            return
        L28:
            r1 = 1
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 >= 0) goto L5f
            r1 = 0
            r2 = r1
        L2f:
            char[] r3 = r5.source
            int r3 = r3.length
            if (r1 < r3) goto L35
            goto L5f
        L35:
            char[] r3 = r5.source
            char r3 = r3[r1]
            r4 = 46
            if (r3 == r4) goto L5c
            r4 = 48
            if (r3 == r4) goto L5c
            r4 = 80
            if (r3 == r4) goto L5f
            r4 = 88
            if (r3 == r4) goto L5b
            r4 = 112(0x70, float:1.57E-43)
            if (r3 == r4) goto L5f
            r4 = 120(0x78, float:1.68E-43)
            if (r3 == r4) goto L5b
            switch(r3) {
                case 68: goto L58;
                case 69: goto L58;
                case 70: goto L58;
                default: goto L54;
            }
        L54:
            switch(r3) {
                case 100: goto L58;
                case 101: goto L58;
                case 102: goto L58;
                default: goto L57;
            }
        L57:
            return
        L58:
            if (r2 == 0) goto L5f
            return
        L5b:
            r2 = 1
        L5c:
            int r1 = r1 + 1
            goto L2f
        L5f:
            r5.value = r0
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = org.eclipse.jdt.internal.compiler.impl.FloatConstant.fromValue(r0)
            r5.constant = r0
            return
        L68:
            char[] r0 = r5.source     // Catch: java.lang.NumberFormatException -> L84
            float r0 = org.eclipse.jdt.internal.compiler.util.FloatUtil.valueOfHexFloatLiteral(r0)     // Catch: java.lang.NumberFormatException -> L84
            r1 = 2139095040(0x7f800000, float:Infinity)
            int r1 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r1 != 0) goto L75
            return
        L75:
            boolean r1 = java.lang.Float.isNaN(r0)     // Catch: java.lang.NumberFormatException -> L84
            if (r1 == 0) goto L7c
            return
        L7c:
            r5.value = r0     // Catch: java.lang.NumberFormatException -> L84
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = org.eclipse.jdt.internal.compiler.impl.FloatConstant.fromValue(r0)     // Catch: java.lang.NumberFormatException -> L84
            r5.constant = r0     // Catch: java.lang.NumberFormatException -> L84
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ast.FloatLiteral.computeConstant():void");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        int i = codeStream.position;
        if (z) {
            codeStream.generateConstant(this.constant, this.implicitConversion);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Literal
    public TypeBinding literalType(BlockScope blockScope) {
        return TypeBinding.FLOAT;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
