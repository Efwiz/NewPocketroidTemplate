package org.jetbrains.kotlin.org.jdom;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Content extends CloneBase implements Serializable {
    private static final long serialVersionUID = 200;
    protected final CType ctype;
    protected transient Parent parent = null;

    public enum CType {
        Comment,
        Element,
        ProcessingInstruction,
        EntityRef,
        Text,
        CDATA,
        DocType
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    protected Content(CType cType) {
        this.ctype = cType;
    }

    public Parent getParent() {
        return this.parent;
    }

    protected Content setParent(Parent parent) {
        this.parent = parent;
        return this;
    }

    @Override // org.jetbrains.kotlin.org.jdom.CloneBase
    public Content clone() {
        Content content = (Content) super.clone();
        content.parent = null;
        return content;
    }

    public final int hashCode() {
        return super.hashCode();
    }
}
