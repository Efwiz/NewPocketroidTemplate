package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElementImpl;

/* JADX INFO: loaded from: classes3.dex */
public class OuterLanguageElementType extends IElementType implements ILeafElementType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = "debugName";
        } else {
            objArr[0] = "leafText";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/OuterLanguageElementType";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "createLeafNode";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OuterLanguageElementType(String str, Language language) {
        super(str, language);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected OuterLanguageElementType(String str, Language language, boolean z) {
        super(str, language, z);
        if (str == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILeafElementType
    public ASTNode createLeafNode(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        return new OuterLanguageElementImpl(this, charSequence);
    }
}
