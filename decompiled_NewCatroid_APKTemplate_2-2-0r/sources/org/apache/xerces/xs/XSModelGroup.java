package org.apache.xerces.xs;

/* JADX INFO: loaded from: classes13.dex */
public interface XSModelGroup extends XSTerm {
    public static final short COMPOSITOR_ALL = 3;
    public static final short COMPOSITOR_CHOICE = 2;
    public static final short COMPOSITOR_SEQUENCE = 1;

    XSAnnotation getAnnotation();

    XSObjectList getAnnotations();

    short getCompositor();

    XSObjectList getParticles();
}
