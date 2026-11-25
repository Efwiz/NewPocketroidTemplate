package org.apache.xerces.xs;

/* JADX INFO: loaded from: classes13.dex */
public interface XSFacet extends XSObject {
    Object getActualFacetValue();

    XSAnnotation getAnnotation();

    XSObjectList getAnnotations();

    short getFacetKind();

    boolean getFixed();

    int getIntFacetValue();

    String getLexicalFacetValue();
}
