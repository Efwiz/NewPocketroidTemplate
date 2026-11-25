package org.jetbrains.kotlin.kdoc.parser;

import java.lang.reflect.Constructor;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.idea.KotlinLanguage;

/* JADX INFO: loaded from: classes5.dex */
public class KDocElementType extends IElementType {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Constructor<? extends PsiElement> psiFactory;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiClass", "org/jetbrains/kotlin/kdoc/parser/KDocElementType", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocElementType(String str, Class<? extends PsiElement> cls) {
        super(str, KotlinLanguage.INSTANCE);
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        try {
            this.psiFactory = cls.getConstructor(ASTNode.class);
        } catch (NoSuchMethodException unused) {
            throw new RuntimeException("Must have a constructor with ASTNode");
        }
    }

    public PsiElement createPsi(ASTNode aSTNode) {
        try {
            return this.psiFactory.newInstance(aSTNode);
        } catch (Exception e) {
            throw new RuntimeException("Error creating psi element for node", e);
        }
    }
}
