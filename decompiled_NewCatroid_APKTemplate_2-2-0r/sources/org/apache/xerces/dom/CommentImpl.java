package org.apache.xerces.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;

/* JADX INFO: loaded from: classes13.dex */
public class CommentImpl extends CharacterDataImpl implements CharacterData, Comment {
    static final long serialVersionUID = -2685736833408134044L;

    public CommentImpl(CoreDocumentImpl coreDocumentImpl, String str) {
        super(coreDocumentImpl, str);
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public String getNodeName() {
        return "#comment";
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public short getNodeType() {
        return (short) 8;
    }
}
