package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.addRemoveModifier.AddRemoveModifierKt;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtModifierListOwnerStub<T extends StubElement<?>> extends KtElementImplStub<T> implements KtModifierListOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 3:
                objArr[0] = "annotationEntry";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/psi/KtModifierListOwnerStub";
                break;
            default:
                objArr[0] = "modifier";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "addAnnotationEntry";
                break;
            case 5:
            case 6:
                objArr[1] = "getAnnotationEntries";
                break;
            case 7:
            case 8:
                objArr[1] = "getAnnotations";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/psi/KtModifierListOwnerStub";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "addModifier";
                break;
            case 2:
                objArr[2] = "removeModifier";
                break;
            case 3:
                objArr[2] = "addAnnotationEntry";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            default:
                objArr[2] = "hasModifier";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public KtModifierListOwnerStub(ASTNode aSTNode) {
        super(aSTNode);
    }

    public KtModifierListOwnerStub(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwner
    public KtModifierList getModifierList() {
        return (KtModifierList) getStubOrPsiChild(KtStubElementTypes.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwner
    public boolean hasModifier(KtModifierKeywordToken ktModifierKeywordToken) {
        if (ktModifierKeywordToken == null) {
            $$$reportNull$$$0(0);
        }
        KtModifierList modifierList = getModifierList();
        return modifierList != null && modifierList.hasModifier(ktModifierKeywordToken);
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwner
    public void addModifier(KtModifierKeywordToken ktModifierKeywordToken) {
        if (ktModifierKeywordToken == null) {
            $$$reportNull$$$0(1);
        }
        AddRemoveModifierKt.addModifier(this, ktModifierKeywordToken);
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwner
    public void removeModifier(KtModifierKeywordToken ktModifierKeywordToken) {
        if (ktModifierKeywordToken == null) {
            $$$reportNull$$$0(2);
        }
        AddRemoveModifierKt.removeModifier(this, ktModifierKeywordToken);
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwner
    public KtAnnotationEntry addAnnotationEntry(KtAnnotationEntry ktAnnotationEntry) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(3);
        }
        KtAnnotationEntry ktAnnotationEntryAddAnnotationEntry = AddRemoveModifierKt.addAnnotationEntry(this, ktAnnotationEntry);
        if (ktAnnotationEntryAddAnnotationEntry == null) {
            $$$reportNull$$$0(4);
        }
        return ktAnnotationEntryAddAnnotationEntry;
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotationEntry> getAnnotationEntries() {
        KtModifierList modifierList = getModifierList();
        if (modifierList == null) {
            List<KtAnnotationEntry> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(5);
            }
            return listEmptyList;
        }
        List<KtAnnotationEntry> annotationEntries = modifierList.getAnnotationEntries();
        if (annotationEntries == null) {
            $$$reportNull$$$0(6);
        }
        return annotationEntries;
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotation> getAnnotations() {
        KtModifierList modifierList = getModifierList();
        if (modifierList == null) {
            List<KtAnnotation> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(7);
            }
            return listEmptyList;
        }
        List<KtAnnotation> annotations = modifierList.getAnnotations();
        if (annotations == null) {
            $$$reportNull$$$0(8);
        }
        return annotations;
    }
}
