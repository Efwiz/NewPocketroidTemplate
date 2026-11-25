package org.jetbrains.kotlin.org.jdom;

import org.jetbrains.kotlin.org.jdom.output.Format;

/* JADX INFO: loaded from: classes4.dex */
public class Text extends Content {
    private static final long serialVersionUID = 200;
    protected String value;

    protected Text(Content.CType cType) {
        super(cType);
    }

    protected Text() {
        this(Content.CType.Text);
    }

    public String getText() {
        return this.value;
    }

    public String getTextNormalize() {
        return normalizeString(getText());
    }

    public static String normalizeString(String str) {
        if (str == null) {
            return "";
        }
        return Format.compact(str);
    }

    public String toString() {
        return new StringBuilder(64).append("[Text: ").append(getText()).append("]").toString();
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public Text clone() {
        Text text = (Text) super.clone();
        text.value = this.value;
        return text;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.org.jdom.Content
    public Text setParent(Parent parent) {
        return (Text) super.setParent(parent);
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content
    public Element getParent() {
        return (Element) super.getParent();
    }
}
