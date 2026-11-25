package org.jetbrains.kotlin.org.jdom;

/* JADX INFO: loaded from: classes4.dex */
class CloneBase implements Cloneable {
    protected CloneBase() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // 
    public CloneBase clone() {
        try {
            return (CloneBase) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(String.format("Unable to clone class %s which should always support it.", getClass().getName()), e);
        }
    }
}
