package org.jetbrains.kotlin.com.intellij.pom.tree.events.impl;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangeEventImpl;

/* JADX INFO: loaded from: classes3.dex */
public class ChangeInfoImpl {
    private final ASTNode myNewChild;
    private final int myNewLength;
    private final int myOffset;
    private final ASTNode myOldChild;
    private final int myOldLength;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/pom/tree/events/impl/ChangeInfoImpl", "createEvent"));
    }

    ChangeInfoImpl(ASTNode aSTNode, ASTNode aSTNode2, int i, int i2) {
        this.myOldChild = aSTNode;
        this.myNewChild = aSTNode2;
        this.myOffset = i;
        this.myOldLength = i2;
        this.myNewLength = aSTNode2 != null ? aSTNode2.getTextLength() : 0;
    }

    public int getOffsetInParent() {
        return this.myOffset;
    }

    public int getOldLength() {
        return this.myOldLength;
    }

    public ASTNode getNewChild() {
        return this.myNewChild;
    }

    public String toString() {
        return this.myOldChild + "(" + this.myOldLength + ")->" + this.myNewChild + "(" + this.myNewLength + ") at " + this.myOffset;
    }

    int getLengthDelta() {
        return this.myNewLength - this.myOldLength;
    }

    ASTNode getAffectedChild() {
        ASTNode aSTNode = this.myNewChild;
        return aSTNode != null ? aSTNode : this.myOldChild;
    }

    void fireEvent(int i, PsiFile psiFile, ASTNode aSTNode) {
        PsiTreeChangeEventImpl psiTreeChangeEventImplCreateEvent = createEvent(psiFile, this.myOffset + i);
        ASTNode aSTNode2 = this.myOldChild;
        ASTNode aSTNode3 = this.myNewChild;
        if (aSTNode2 == aSTNode3 && aSTNode3 != null) {
            childrenChanged(psiTreeChangeEventImplCreateEvent, aSTNode3, this.myOldLength);
            return;
        }
        if (aSTNode2 != null && aSTNode3 != null) {
            childReplaced(psiTreeChangeEventImplCreateEvent, aSTNode2, aSTNode3, aSTNode);
        } else if (aSTNode2 != null) {
            childRemoved(psiTreeChangeEventImplCreateEvent, aSTNode2, aSTNode);
        } else if (aSTNode3 != null) {
            childAdded(psiTreeChangeEventImplCreateEvent, aSTNode3, aSTNode);
        }
    }

    static PsiTreeChangeEventImpl createEvent(PsiFile psiFile, int i) {
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(psiFile.getManager());
        psiTreeChangeEventImpl.setFile(psiFile);
        psiTreeChangeEventImpl.setOffset(i);
        return psiTreeChangeEventImpl;
    }

    boolean hasNoPsi() {
        ASTNode aSTNode;
        ASTNode aSTNode2 = this.myOldChild;
        return (aSTNode2 != null && aSTNode2.getPsi() == null) || ((aSTNode = this.myNewChild) != null && aSTNode.getPsi() == null);
    }

    private static void childAdded(PsiTreeChangeEventImpl psiTreeChangeEventImpl, ASTNode aSTNode, ASTNode aSTNode2) {
        psiTreeChangeEventImpl.setParent(aSTNode2.getPsi());
        psiTreeChangeEventImpl.setChild(aSTNode.getPsi());
        getPsiManagerImpl(psiTreeChangeEventImpl).childAdded(psiTreeChangeEventImpl);
    }

    private void childRemoved(PsiTreeChangeEventImpl psiTreeChangeEventImpl, ASTNode aSTNode, ASTNode aSTNode2) {
        psiTreeChangeEventImpl.setParent(aSTNode2.getPsi());
        psiTreeChangeEventImpl.setChild(aSTNode.getPsi());
        psiTreeChangeEventImpl.setOldLength(this.myOldLength);
        getPsiManagerImpl(psiTreeChangeEventImpl).childRemoved(psiTreeChangeEventImpl);
    }

    private void childReplaced(PsiTreeChangeEventImpl psiTreeChangeEventImpl, ASTNode aSTNode, ASTNode aSTNode2, ASTNode aSTNode3) {
        psiTreeChangeEventImpl.setParent(aSTNode3.getPsi());
        psiTreeChangeEventImpl.setOldChild(aSTNode.getPsi());
        psiTreeChangeEventImpl.setChild(aSTNode2.getPsi());
        psiTreeChangeEventImpl.setNewChild(aSTNode2.getPsi());
        psiTreeChangeEventImpl.setOldLength(this.myOldLength);
        getPsiManagerImpl(psiTreeChangeEventImpl).childReplaced(psiTreeChangeEventImpl);
    }

    static void childrenChanged(PsiTreeChangeEventImpl psiTreeChangeEventImpl, ASTNode aSTNode, int i) {
        psiTreeChangeEventImpl.setParent(aSTNode.getPsi());
        psiTreeChangeEventImpl.setOldLength(i);
        getPsiManagerImpl(psiTreeChangeEventImpl).childrenChanged(psiTreeChangeEventImpl);
    }

    private static PsiManagerImpl getPsiManagerImpl(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        return (PsiManagerImpl) psiTreeChangeEventImpl.getSource();
    }
}
