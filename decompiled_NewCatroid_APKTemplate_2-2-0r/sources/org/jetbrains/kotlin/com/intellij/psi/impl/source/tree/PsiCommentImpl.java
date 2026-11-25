package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.injected.CommentLiteralEscaper;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiCommentImpl extends LeafPsiElement implements PsiComment, PsiLanguageInjectionHost {
    /* JADX WARN: Code duplicated, block: B:20:0x002e  */
    /* JADX WARN: Code duplicated, block: B:21:0x0031  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "text";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiCommentImpl";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiCommentImpl";
        } else if (i != 5) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "text";
        }
        if (i == 2) {
            objArr[1] = "getTokenType";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiCommentImpl";
        } else {
            objArr[1] = "getReferences";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "accept";
            } else if (i != 4) {
                if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "updateText";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public boolean isValidHost() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiCommentImpl(IElementType iElementType, CharSequence charSequence) {
        super(iElementType, charSequence);
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        IElementType elementType = getElementType();
        if (elementType == null) {
            $$$reportNull$$$0(2);
        }
        return elementType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        psiElementVisitor.visitComment(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiComment(" + getElementType().toString() + Constant.AFTER_QUTO;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] referencesFromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(this);
        if (referencesFromProviders == null) {
            $$$reportNull$$$0(4);
        }
        return referencesFromProviders;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public PsiLanguageInjectionHost updateText(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return (PsiCommentImpl) replaceWithText(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost
    public LiteralTextEscaper<PsiCommentImpl> createLiteralTextEscaper() {
        return new CommentLiteralEscaper(this);
    }
}
