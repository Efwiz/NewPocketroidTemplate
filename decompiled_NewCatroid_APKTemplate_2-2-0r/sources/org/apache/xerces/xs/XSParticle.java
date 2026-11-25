package org.apache.xerces.xs;

/* JADX INFO: loaded from: classes13.dex */
public interface XSParticle extends XSObject {
    XSObjectList getAnnotations();

    int getMaxOccurs();

    boolean getMaxOccursUnbounded();

    int getMinOccurs();

    XSTerm getTerm();
}
