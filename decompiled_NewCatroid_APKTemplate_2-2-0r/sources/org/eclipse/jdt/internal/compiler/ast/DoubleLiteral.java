package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class DoubleLiteral extends NumberLiteral {
    double value;

    public DoubleLiteral(char[] cArr, int i, int i2) {
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
            r6 = this;
            char[] r0 = r6.source
            r1 = 95
            int r0 = org.eclipse.jdt.core.compiler.CharOperation.indexOf(r1, r0)
            if (r0 <= 0) goto L12
            char[] r0 = r6.source
            char[] r0 = org.eclipse.jdt.core.compiler.CharOperation.remove(r0, r1)
            r6.source = r0
        L12:
            char[] r0 = r6.source     // Catch: java.lang.NumberFormatException -> L6b
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch: java.lang.NumberFormatException -> L6b
            java.lang.Double r0 = java.lang.Double.valueOf(r0)     // Catch: java.lang.NumberFormatException -> L6b
            double r0 = r0.doubleValue()
            r2 = 9218868437227405311(0x7fefffffffffffff, double:1.7976931348623157E308)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto L2a
            return
        L2a:
            r2 = 1
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 >= 0) goto L62
            r2 = 0
            r3 = r2
        L32:
            char[] r4 = r6.source
            int r4 = r4.length
            if (r2 < r4) goto L38
            goto L62
        L38:
            char[] r4 = r6.source
            char r4 = r4[r2]
            r5 = 46
            if (r4 == r5) goto L5f
            r5 = 48
            if (r4 == r5) goto L5f
            r5 = 80
            if (r4 == r5) goto L62
            r5 = 88
            if (r4 == r5) goto L5e
            r5 = 112(0x70, float:1.57E-43)
            if (r4 == r5) goto L62
            r5 = 120(0x78, float:1.68E-43)
            if (r4 == r5) goto L5e
            switch(r4) {
                case 68: goto L5b;
                case 69: goto L5b;
                case 70: goto L5b;
                default: goto L57;
            }
        L57:
            switch(r4) {
                case 100: goto L5b;
                case 101: goto L5b;
                case 102: goto L5b;
                default: goto L5a;
            }
        L5a:
            return
        L5b:
            if (r3 == 0) goto L62
            return
        L5e:
            r3 = 1
        L5f:
            int r2 = r2 + 1
            goto L32
        L62:
            r6.value = r0
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = org.eclipse.jdt.internal.compiler.impl.DoubleConstant.fromValue(r0)
            r6.constant = r0
            return
        L6b:
            char[] r0 = r6.source     // Catch: java.lang.NumberFormatException -> L87
            double r0 = org.eclipse.jdt.internal.compiler.util.FloatUtil.valueOfHexDoubleLiteral(r0)     // Catch: java.lang.NumberFormatException -> L87
            r2 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L78
            return
        L78:
            boolean r2 = java.lang.Double.isNaN(r0)     // Catch: java.lang.NumberFormatException -> L87
            if (r2 == 0) goto L7f
            return
        L7f:
            r6.value = r0     // Catch: java.lang.NumberFormatException -> L87
            org.eclipse.jdt.internal.compiler.impl.Constant r0 = org.eclipse.jdt.internal.compiler.impl.DoubleConstant.fromValue(r0)     // Catch: java.lang.NumberFormatException -> L87
            r6.constant = r0     // Catch: java.lang.NumberFormatException -> L87
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.ast.DoubleLiteral.computeConstant():void");
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
        return TypeBinding.DOUBLE;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        aSTVisitor.visit(this, blockScope);
        aSTVisitor.endVisit(this, blockScope);
    }
}
