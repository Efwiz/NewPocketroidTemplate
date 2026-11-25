package org.jetbrains.kotlin.com.intellij.psi;

import java.util.EventObject;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiTreeChangeEvent extends EventObject {
    public static final String PROP_DIRECTORY_NAME = "directoryName";
    public static final String PROP_FILE_NAME = "fileName";
    public static final String PROP_FILE_TYPES = "propFileTypes";
    public static final String PROP_ROOTS = "roots";
    public static final String PROP_UNLOADED_PSI = "propUnloadedPsi";
    public static final String PROP_WRITABLE = "writable";
    protected PsiElement myChild;
    protected PsiElement myElement;
    protected PsiFile myFile;
    protected PsiElement myNewChild;
    protected PsiElement myNewParent;
    protected Object myNewValue;
    protected int myOffset;
    protected PsiElement myOldChild;
    protected int myOldLength;
    protected PsiElement myOldParent;
    protected Object myOldValue;
    protected PsiElement myParent;
    protected String myPropertyName;

    protected PsiTreeChangeEvent(PsiManager psiManager) {
        super(psiManager);
    }

    public PsiElement getParent() {
        return this.myParent;
    }

    public PsiElement getOldParent() {
        return this.myOldParent;
    }

    public PsiElement getNewParent() {
        return this.myNewParent;
    }

    public PsiElement getChild() {
        return this.myChild;
    }

    public PsiElement getOldChild() {
        return this.myOldChild;
    }

    public PsiElement getNewChild() {
        return this.myNewChild;
    }

    public PsiElement getElement() {
        return this.myElement;
    }

    public String getPropertyName() {
        return this.myPropertyName;
    }

    public Object getOldValue() {
        return this.myOldValue;
    }

    public Object getNewValue() {
        return this.myNewValue;
    }

    public PsiFile getFile() {
        return this.myFile;
    }
}
