package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.DefaultASTFactory;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocTagValueImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.PsiDocTokenImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiIdentifierImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiKeywordImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.java.IJavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.java.IJavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class JavaASTFactory extends ASTFactory {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final DefaultASTFactory myDefaultASTFactory = (DefaultASTFactory) ApplicationManager.getApplication().getService(DefaultASTFactory.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "text";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/JavaASTFactory";
        if (i != 2) {
            objArr[2] = "createLeaf";
        } else {
            objArr[2] = "createComposite";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTFactory
    public LeafElement createLeaf(IElementType iElementType, CharSequence charSequence) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (iElementType == JavaTokenType.C_STYLE_COMMENT || iElementType == JavaTokenType.END_OF_LINE_COMMENT) {
            return this.myDefaultASTFactory.createComment(iElementType, charSequence);
        }
        if (iElementType == JavaTokenType.IDENTIFIER) {
            return new PsiIdentifierImpl(charSequence);
        }
        if (ElementType.KEYWORD_BIT_SET.contains(iElementType)) {
            return new PsiKeywordImpl(iElementType, charSequence);
        }
        if (iElementType instanceof IJavaElementType) {
            return new PsiJavaTokenImpl(iElementType, charSequence);
        }
        if (iElementType instanceof IJavaDocElementType) {
            return new PsiDocTokenImpl(iElementType, charSequence);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTFactory
    public CompositeElement createComposite(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        if (iElementType == JavaDocElementType.DOC_TAG_VALUE_ELEMENT) {
            return new PsiDocTagValueImpl();
        }
        return null;
    }
}
