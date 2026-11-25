package org.jetbrains.kotlin.org.jdom;

import org.jetbrains.kotlin.org.jdom.output.XMLOutputter2;

/* JADX INFO: loaded from: classes4.dex */
public class Comment extends Content {
    private static final long serialVersionUID = 200;
    protected String text;

    protected Comment() {
        super(Content.CType.Comment);
    }

    public String getText() {
        return this.text;
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public Comment clone() {
        return (Comment) super.clone();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.org.jdom.Content
    public Comment setParent(Parent parent) {
        return (Comment) super.setParent(parent);
    }

    public String toString() {
        return "[Comment: " + new XMLOutputter2().outputString(this) + "]";
    }
}
