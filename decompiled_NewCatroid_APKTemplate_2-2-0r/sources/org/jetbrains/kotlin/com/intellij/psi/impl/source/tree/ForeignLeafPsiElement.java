package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ForeignLeafType;
import org.jetbrains.kotlin.com.intellij.lang.TokenWrapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class ForeignLeafPsiElement extends LeafPsiElement {
    private final ForeignLeafType myForeignType;

    /* JADX WARN: Code duplicated, block: B:24:0x0038  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 6) ? 2 : 3];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ForeignLeafPsiElement";
        } else if (i == 3) {
            objArr[0] = "seq";
        } else if (i == 4) {
            objArr[0] = "buffer";
        } else if (i == 5) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 6) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ForeignLeafPsiElement";
        }
        if (i == 2) {
            objArr[1] = "dereferenceElementType";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ForeignLeafPsiElement";
        } else {
            objArr[1] = "getForeignType";
        }
        switch (i) {
            case 1:
                objArr[2] = "dereferenceElementType";
                break;
            case 2:
            case 6:
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "textMatches";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public LeafElement findLeafElementAt(int i) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public int getStartOffset() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement, org.jetbrains.kotlin.com.intellij.lang.ASTNode
    public int getTextLength() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    protected int textMatches(CharSequence charSequence, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence != null) {
            return false;
        }
        $$$reportNull$$$0(3);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement != null) {
            return false;
        }
        $$$reportNull$$$0(5);
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForeignLeafPsiElement(ForeignLeafType foreignLeafType, CharSequence charSequence) {
        super(dereferenceElementType(foreignLeafType.getDelegate()), charSequence);
        if (foreignLeafType == null) {
            $$$reportNull$$$0(0);
        }
        this.myForeignType = foreignLeafType;
    }

    private static IElementType dereferenceElementType(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        while (iElementType instanceof TokenWrapper) {
            iElementType = ((TokenWrapper) iElementType).getDelegate();
        }
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        return iElementType;
    }

    public ForeignLeafType getForeignType() {
        ForeignLeafType foreignLeafType = this.myForeignType;
        if (foreignLeafType == null) {
            $$$reportNull$$$0(6);
        }
        return foreignLeafType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "ForeignLeaf(" + getElementType() + ": " + getText() + Constant.AFTER_QUTO;
    }
}
