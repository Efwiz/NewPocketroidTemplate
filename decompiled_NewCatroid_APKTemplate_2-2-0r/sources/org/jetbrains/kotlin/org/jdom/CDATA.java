package org.jetbrains.kotlin.org.jdom;

/* JADX INFO: loaded from: classes4.dex */
public class CDATA extends Text {
    private static final long serialVersionUID = 200;

    protected CDATA() {
        super(Content.CType.CDATA);
    }

    @Override // org.jetbrains.kotlin.org.jdom.Text
    public String toString() {
        return new StringBuilder(64).append("[CDATA: ").append(getText()).append("]").toString();
    }

    @Override // org.jetbrains.kotlin.org.jdom.Text, org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public CDATA clone() {
        return (CDATA) super.clone();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.org.jdom.Text, org.jetbrains.kotlin.org.jdom.Content
    public CDATA setParent(Parent parent) {
        return (CDATA) super.setParent(parent);
    }
}
