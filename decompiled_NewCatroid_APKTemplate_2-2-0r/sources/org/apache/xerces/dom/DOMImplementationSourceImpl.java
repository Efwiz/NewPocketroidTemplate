package org.apache.xerces.dom;

import java.util.ArrayList;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementationSource;

/* JADX INFO: loaded from: classes13.dex */
public class DOMImplementationSourceImpl implements DOMImplementationSource {
    @Override // org.w3c.dom.DOMImplementationSource
    public DOMImplementation getDOMImplementation(String str) {
        DOMImplementation dOMImplementation = CoreDOMImplementationImpl.getDOMImplementation();
        if (testImpl(dOMImplementation, str)) {
            return dOMImplementation;
        }
        DOMImplementation dOMImplementation2 = DOMImplementationImpl.getDOMImplementation();
        if (testImpl(dOMImplementation2, str)) {
            return dOMImplementation2;
        }
        return null;
    }

    @Override // org.w3c.dom.DOMImplementationSource
    public DOMImplementationList getDOMImplementationList(String str) {
        DOMImplementation dOMImplementation = CoreDOMImplementationImpl.getDOMImplementation();
        ArrayList arrayList = new ArrayList();
        if (testImpl(dOMImplementation, str)) {
            arrayList.add(dOMImplementation);
        }
        DOMImplementation dOMImplementation2 = DOMImplementationImpl.getDOMImplementation();
        if (testImpl(dOMImplementation2, str)) {
            arrayList.add(dOMImplementation2);
        }
        return new DOMImplementationListImpl(arrayList);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c  */
    /* JADX WARN: Code duplicated, block: B:13:0x0028  */
    /* JADX WARN: Code duplicated, block: B:19:0x0033  */
    /* JADX WARN: Code duplicated, block: B:25:0x0041  */
    /* JADX WARN: Code duplicated, block: B:28:0x0043 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:29:0x003a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x0032 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:0x000c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x002c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:33:0x0040 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:5:0x0011  */
    /* JADX WARN: Code duplicated, block: B:8:0x0015  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x0037 -> B:4:0x000c). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:19:0x0033
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    boolean testImpl(org.w3c.dom.DOMImplementation r7, java.lang.String r8) {
        /*
            r6 = this;
            java.util.StringTokenizer r0 = new java.util.StringTokenizer
            r0.<init>(r8)
            boolean r8 = r0.hasMoreTokens()
            r1 = 0
            if (r8 == 0) goto L11
        Lc:
            java.lang.String r8 = r0.nextToken()
            goto L12
        L11:
            r8 = r1
        L12:
            r2 = 1
            if (r8 == 0) goto L43
            boolean r3 = r0.hasMoreTokens()
            r4 = 0
            if (r3 == 0) goto L28
            java.lang.String r3 = r0.nextToken()
            char r5 = r3.charAt(r4)
            switch(r5) {
                case 48: goto L2a;
                case 49: goto L2a;
                case 50: goto L2a;
                case 51: goto L2a;
                case 52: goto L2a;
                case 53: goto L2a;
                case 54: goto L2a;
                case 55: goto L2a;
                case 56: goto L2a;
                case 57: goto L2a;
                default: goto L27;
            }
        L27:
            goto L29
        L28:
            r3 = r1
        L29:
            r2 = r4
        L2a:
            if (r2 == 0) goto L3a
            boolean r8 = r7.hasFeature(r8, r3)
            if (r8 != 0) goto L33
            return r4
        L33:
            boolean r8 = r0.hasMoreTokens()
            if (r8 == 0) goto L11
            goto Lc
        L3a:
            boolean r8 = r7.hasFeature(r8, r1)
            if (r8 != 0) goto L41
            return r4
        L41:
            r8 = r3
            goto L12
        L43:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.dom.DOMImplementationSourceImpl.testImpl(org.w3c.dom.DOMImplementation, java.lang.String):boolean");
    }
}
