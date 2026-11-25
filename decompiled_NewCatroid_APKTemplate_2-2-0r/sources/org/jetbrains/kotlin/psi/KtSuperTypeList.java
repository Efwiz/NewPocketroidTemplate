package org.jetbrains.kotlin.psi;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;

/* JADX INFO: loaded from: classes6.dex */
public class KtSuperTypeList extends KtElementImplStub<KotlinPlaceHolderStub<KtSuperTypeList>> {
    private final AtomicLong modificationStamp;

    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3) {
            objArr[0] = "entry";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtSuperTypeList";
        } else if (i != 5) {
            objArr[0] = "node";
        } else {
            objArr[0] = "entry";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtSuperTypeList";
        } else {
            objArr[1] = "addEntry";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i == 3) {
            objArr[2] = "addEntry";
        } else if (i != 4) {
            if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "removeEntry";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSuperTypeList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.modificationStamp = new AtomicLong();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSuperTypeList(KotlinPlaceHolderStub<KtSuperTypeList> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.SUPER_TYPE_LIST);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
        this.modificationStamp = new AtomicLong();
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitSuperTypeList(this, d);
    }

    public KtSuperTypeListEntry addEntry(KtSuperTypeListEntry ktSuperTypeListEntry) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(3);
        }
        KtSuperTypeListEntry ktSuperTypeListEntry2 = (KtSuperTypeListEntry) EditCommaSeparatedListHelper.INSTANCE.addItem(this, getEntries(), ktSuperTypeListEntry);
        if (ktSuperTypeListEntry2 == null) {
            $$$reportNull$$$0(4);
        }
        return ktSuperTypeListEntry2;
    }

    public void removeEntry(KtSuperTypeListEntry ktSuperTypeListEntry) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(5);
        }
        EditCommaSeparatedListHelper.INSTANCE.removeItem(ktSuperTypeListEntry);
        if (getEntries().isEmpty()) {
            delete();
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        PsiElement psiElementSkipSiblingsBackward = PsiTreeUtil.skipSiblingsBackward(this, PsiWhiteSpace.class, PsiComment.class);
        if (psiElementSkipSiblingsBackward == null || psiElementSkipSiblingsBackward.getNode().getElementType() != KtTokens.COLON) {
            psiElementSkipSiblingsBackward = this;
        }
        getMyParent().deleteChildRange(psiElementSkipSiblingsBackward, this);
    }

    public List<KtSuperTypeListEntry> getEntries() {
        return Arrays.asList(getStubOrPsiChildren(KtTokenSets.SUPER_TYPE_LIST_ENTRIES, KtSuperTypeListEntry.ARRAY_FACTORY));
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.modificationStamp.getAndIncrement();
    }

    public long getModificationStamp() {
        return this.modificationStamp.get();
    }
}
