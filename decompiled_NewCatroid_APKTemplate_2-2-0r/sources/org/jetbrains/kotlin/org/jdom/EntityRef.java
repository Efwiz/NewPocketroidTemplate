package org.jetbrains.kotlin.org.jdom;

/* JADX INFO: loaded from: classes4.dex */
public class EntityRef extends Content {
    private static final long serialVersionUID = 200;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f934name;
    protected String publicID;
    protected String systemID;

    protected EntityRef() {
        super(Content.CType.EntityRef);
    }

    public String getName() {
        return this.f934name;
    }

    public String getValue() {
        return "";
    }

    public String toString() {
        return "[EntityRef: &" + this.f934name + ";]";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.org.jdom.Content
    public EntityRef setParent(Parent parent) {
        return (EntityRef) super.setParent(parent);
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content
    public Element getParent() {
        return (Element) super.getParent();
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public EntityRef clone() {
        return (EntityRef) super.clone();
    }
}
