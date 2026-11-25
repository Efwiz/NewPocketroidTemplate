package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.CharTableImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.CodeFragmentElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseableElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import org.jetbrains.kotlin.com.intellij.psi.tree.ICompositeElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.ILeafElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ASTFactory {
    private static final CharTable WHITESPACES = new CharTableImpl();

    public static final class DefaultFactoryHolder {
        public static final DefaultASTFactoryImpl DEFAULT = new DefaultASTFactoryImpl();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 7:
            case 10:
            case 11:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                i2 = 2;
                break;
            case 7:
            case 10:
            case 11:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 3:
            case 11:
                objArr[0] = "text";
                break;
            case 4:
            case 7:
            case 10:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/ASTFactory";
                break;
        }
        switch (i) {
            case 5:
            case 6:
                objArr[1] = "lazy";
                break;
            case 7:
            case 10:
            case 11:
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/ASTFactory";
                break;
            case 8:
            case 9:
                objArr[1] = "composite";
                break;
            case 12:
            case 13:
                objArr[1] = "leaf";
                break;
            case 14:
                objArr[1] = "whitespace";
                break;
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
                objArr[2] = "lazy";
                break;
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                break;
            case 7:
                objArr[2] = "composite";
                break;
            case 10:
            case 11:
                objArr[2] = "leaf";
                break;
            default:
                objArr[2] = "createLazy";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
                throw new IllegalStateException(str2);
            case 7:
            case 10:
            case 11:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public CompositeElement createComposite(IElementType iElementType) {
        if (iElementType != null) {
            return null;
        }
        $$$reportNull$$$0(1);
        return null;
    }

    public LazyParseableElement createLazy(ILazyParseableElementType iLazyParseableElementType, CharSequence charSequence) {
        if (iLazyParseableElementType != null) {
            return null;
        }
        $$$reportNull$$$0(0);
        return null;
    }

    public LeafElement createLeaf(IElementType iElementType, CharSequence charSequence) {
        if (iElementType == null) {
            $$$reportNull$$$0(2);
        }
        if (charSequence != null) {
            return null;
        }
        $$$reportNull$$$0(3);
        return null;
    }

    public static LazyParseableElement lazy(ILazyParseableElementType iLazyParseableElementType, CharSequence charSequence) {
        if (iLazyParseableElementType == null) {
            $$$reportNull$$$0(4);
        }
        ASTNode aSTNodeCreateNode = iLazyParseableElementType.createNode(charSequence);
        if (aSTNodeCreateNode != null) {
            LazyParseableElement lazyParseableElement = (LazyParseableElement) aSTNodeCreateNode;
            if (lazyParseableElement == null) {
                $$$reportNull$$$0(5);
            }
            return lazyParseableElement;
        }
        if (iLazyParseableElementType == TokenType.CODE_FRAGMENT) {
            return new CodeFragmentElement(null);
        }
        if (iLazyParseableElementType == TokenType.DUMMY_HOLDER) {
            return new DummyHolderElement(charSequence);
        }
        LazyParseableElement lazyParseableElementCreateLazy = factory(iLazyParseableElementType).createLazy(iLazyParseableElementType, charSequence);
        if (lazyParseableElementCreateLazy == null) {
            lazyParseableElementCreateLazy = DefaultFactoryHolder.DEFAULT.createLazy(iLazyParseableElementType, charSequence);
        }
        if (lazyParseableElementCreateLazy == null) {
            $$$reportNull$$$0(6);
        }
        return lazyParseableElementCreateLazy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static CompositeElement composite(IElementType iElementType) {
        if (iElementType == 0) {
            $$$reportNull$$$0(7);
        }
        if (iElementType instanceof ICompositeElementType) {
            CompositeElement compositeElement = (CompositeElement) ((ICompositeElementType) iElementType).createCompositeNode();
            if (compositeElement == null) {
                $$$reportNull$$$0(8);
            }
            return compositeElement;
        }
        CompositeElement compositeElementCreateComposite = factory(iElementType).createComposite(iElementType);
        if (compositeElementCreateComposite == null) {
            compositeElementCreateComposite = DefaultFactoryHolder.DEFAULT.createComposite(iElementType);
        }
        if (compositeElementCreateComposite == null) {
            $$$reportNull$$$0(9);
        }
        return compositeElementCreateComposite;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static LeafElement leaf(IElementType iElementType, CharSequence charSequence) {
        if (iElementType == 0) {
            $$$reportNull$$$0(10);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(11);
        }
        if (iElementType == TokenType.WHITE_SPACE) {
            return new PsiWhiteSpaceImpl(charSequence);
        }
        if (iElementType instanceof ILeafElementType) {
            LeafElement leafElement = (LeafElement) ((ILeafElementType) iElementType).createLeafNode(charSequence);
            if (leafElement == null) {
                $$$reportNull$$$0(12);
            }
            return leafElement;
        }
        LeafElement leafElementCreateLeaf = factory(iElementType).createLeaf(iElementType, charSequence);
        if (leafElementCreateLeaf == null) {
            leafElementCreateLeaf = DefaultFactoryHolder.DEFAULT.createLeaf(iElementType, charSequence);
        }
        if (leafElementCreateLeaf == null) {
            $$$reportNull$$$0(13);
        }
        return leafElementCreateLeaf;
    }

    private static ASTFactory factory(IElementType iElementType) {
        return LanguageASTFactory.INSTANCE.forLanguage(iElementType.getLanguage());
    }

    public static LeafElement whitespace(CharSequence charSequence) {
        PsiWhiteSpaceImpl psiWhiteSpaceImpl = new PsiWhiteSpaceImpl(WHITESPACES.intern(charSequence));
        CodeEditUtil.setNodeGenerated(psiWhiteSpaceImpl, true);
        return psiWhiteSpaceImpl;
    }
}
