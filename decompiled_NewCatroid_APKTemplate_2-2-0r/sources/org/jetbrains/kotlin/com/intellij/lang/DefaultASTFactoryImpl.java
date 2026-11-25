package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiCommentImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultASTFactoryImpl extends ASTFactory implements DefaultASTFactory {
    /* JADX WARN: Code duplicated, block: B:17:0x0023  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 3) {
            objArr[0] = "text";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/DefaultASTFactoryImpl";
        } else if (i != 6) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "text";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/DefaultASTFactoryImpl";
        } else {
            objArr[1] = "createLeaf";
        }
        switch (i) {
            case 1:
                objArr[2] = "createComposite";
                break;
            case 2:
            case 3:
                objArr[2] = "createLeaf";
                break;
            case 4:
                break;
            case 5:
            case 6:
                objArr[2] = "createComment";
                break;
            default:
                objArr[2] = "createLazy";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTFactory
    public LazyParseableElement createLazy(ILazyParseableElementType iLazyParseableElementType, CharSequence charSequence) {
        if (iLazyParseableElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (iLazyParseableElementType instanceof IFileElementType) {
            return new FileElement(iLazyParseableElementType, charSequence);
        }
        return new LazyParseableElement(iLazyParseableElementType, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTFactory
    public CompositeElement createComposite(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        if (iElementType instanceof IFileElementType) {
            return new FileElement(iElementType, null);
        }
        return new CompositeElement(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.ASTFactory
    public LeafElement createLeaf(IElementType iElementType, CharSequence charSequence) {
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(iElementType.getLanguage());
        if (parserDefinitionForLanguage != null && parserDefinitionForLanguage.getCommentTokens().contains(iElementType)) {
            LeafElement leafElementCreateComment = createComment(iElementType, charSequence);
            if (leafElementCreateComment == null) {
                $$$reportNull$$$0(4);
            }
            return leafElementCreateComment;
        }
        return new LeafPsiElement(iElementType, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.DefaultASTFactory
    public LeafElement createComment(IElementType iElementType, CharSequence charSequence) {
        if (iElementType == null) {
            $$$reportNull$$$0(5);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(6);
        }
        return new PsiCommentImpl(iElementType, charSequence);
    }
}
