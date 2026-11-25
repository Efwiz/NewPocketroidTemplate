package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiTreeChangeEventImpl extends PsiTreeChangeEvent {
    private boolean isGenericChange;
    private PsiEventType myCode;

    public enum PsiEventType {
        BEFORE_CHILD_ADDITION,
        CHILD_ADDED,
        BEFORE_CHILD_REMOVAL,
        CHILD_REMOVED,
        BEFORE_CHILD_REPLACEMENT,
        CHILD_REPLACED,
        BEFORE_CHILD_MOVEMENT,
        CHILD_MOVED,
        BEFORE_CHILDREN_CHANGE,
        CHILDREN_CHANGED,
        BEFORE_PROPERTY_CHANGE,
        PROPERTY_CHANGED
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY;
        } else if (i == 2) {
            objArr[0] = "propertyName";
        } else if (i != 3) {
            objArr[0] = "manager";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiTreeChangeEventImpl";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiTreeChangeEventImpl";
        } else {
            objArr[1] = "toString";
        }
        if (i == 1) {
            objArr[2] = "setCode";
        } else if (i == 2) {
            objArr[2] = "setPropertyName";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiTreeChangeEventImpl(PsiManager psiManager) {
        super(psiManager);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
    }

    public PsiEventType getCode() {
        return this.myCode;
    }

    public void setCode(PsiEventType psiEventType) {
        if (psiEventType == null) {
            $$$reportNull$$$0(1);
        }
        this.myCode = psiEventType;
    }

    public void setParent(PsiElement psiElement) {
        this.myParent = psiElement;
    }

    public void setOldParent(PsiElement psiElement) {
        this.myOldParent = psiElement;
    }

    public void setNewParent(PsiElement psiElement) {
        this.myNewParent = psiElement;
    }

    public void setChild(PsiElement psiElement) {
        this.myChild = psiElement;
    }

    public void setOldChild(PsiElement psiElement) {
        this.myOldChild = psiElement;
    }

    public void setNewChild(PsiElement psiElement) {
        this.myNewChild = psiElement;
    }

    public void setElement(PsiElement psiElement) {
        this.myElement = psiElement;
    }

    public void setPropertyName(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        this.myPropertyName = str;
    }

    public void setOldValue(Object obj) {
        this.myOldValue = obj;
    }

    public void setNewValue(Object obj) {
        this.myNewValue = obj;
    }

    public void setFile(PsiFile psiFile) {
        this.myFile = psiFile;
    }

    public void setOffset(int i) {
        this.myOffset = i;
    }

    public int getOffset() {
        return this.myOffset;
    }

    public void setOldLength(int i) {
        this.myOldLength = i;
    }

    public int getOldLength() {
        return this.myOldLength;
    }

    public boolean isGenericChange() {
        return this.isGenericChange;
    }

    public void setGenericChange(boolean z) {
        this.isGenericChange = z;
    }

    @Override // java.util.EventObject
    public String toString() {
        String str = "PsiTreeChangeEventImpl{" + this.myCode + (this.isGenericChange ? " (generic)" : "") + (this.myPropertyName == null ? "" : " (" + this.myPropertyName + Constant.AFTER_QUTO) + (this.myFile != null ? " in file " + this.myFile.getName() : "") + '}';
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }
}
