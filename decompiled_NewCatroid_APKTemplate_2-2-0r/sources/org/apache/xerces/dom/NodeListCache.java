package org.apache.xerces.dom;

import java.io.Serializable;

/* JADX INFO: loaded from: classes13.dex */
class NodeListCache implements Serializable {
    private static final long serialVersionUID = -7927529254918631002L;
    ChildNode fChild;
    ParentNode fOwner;
    NodeListCache next;
    int fLength = -1;
    int fChildIndex = -1;

    NodeListCache(ParentNode parentNode) {
        this.fOwner = parentNode;
    }
}
