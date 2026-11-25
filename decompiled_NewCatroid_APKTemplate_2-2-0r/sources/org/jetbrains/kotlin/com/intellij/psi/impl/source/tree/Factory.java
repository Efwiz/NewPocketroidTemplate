package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.google.android.gms.cast.MediaTrack;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public final class Factory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 7 || i == 17) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 7 || i == 17) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 9:
            case 12:
                objArr[0] = "buffer";
                break;
            case 2:
            case 6:
            case 10:
            case 13:
            case 16:
                objArr[0] = "manager";
                break;
            case 3:
            case 7:
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/Factory";
                break;
            case 4:
            case 8:
            case 11:
            case 15:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 14:
                objArr[0] = MediaTrack.ROLE_DESCRIPTION;
                break;
        }
        if (i == 3 || i == 7) {
            objArr[1] = "createSingleLeafElement";
        } else if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/Factory";
        } else {
            objArr[1] = "createCompositeElement";
        }
        if (i != 3 && i != 7) {
            switch (i) {
                case 14:
                    objArr[2] = "createErrorElement";
                    break;
                case 15:
                case 16:
                    objArr[2] = "createCompositeElement";
                    break;
                case 17:
                    break;
                default:
                    objArr[2] = "createSingleLeafElement";
                    break;
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 7 && i != 17) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private Factory() {
    }

    public static LeafElement createSingleLeafElement(IElementType iElementType, CharSequence charSequence, int i, int i2, CharTable charTable, PsiManager psiManager, PsiFile psiFile) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(2);
        }
        DummyHolder dummyHolderCreateHolder = DummyHolderFactory.createHolder(psiManager, charTable, iElementType.getLanguage());
        dummyHolderCreateHolder.setOriginalFile(psiFile);
        FileElement treeElement = dummyHolderCreateHolder.getTreeElement();
        LeafElement leafElementLeaf = ASTFactory.leaf(iElementType, treeElement.getCharTable().intern(charSequence, i, i2));
        treeElement.rawAddChildren(leafElementLeaf);
        CodeEditUtil.setNodeGenerated(leafElementLeaf, true);
        if (leafElementLeaf == null) {
            $$$reportNull$$$0(3);
        }
        return leafElementLeaf;
    }

    public static LeafElement createSingleLeafElement(IElementType iElementType, CharSequence charSequence, int i, int i2, CharTable charTable, PsiManager psiManager, boolean z) {
        if (iElementType == null) {
            $$$reportNull$$$0(4);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(6);
        }
        FileElement treeElement = DummyHolderFactory.createHolder(psiManager, charTable, iElementType.getLanguage()).getTreeElement();
        LeafElement leafElementLeaf = ASTFactory.leaf(iElementType, treeElement.getCharTable().intern(charSequence, i, i2));
        treeElement.rawAddChildren(leafElementLeaf);
        if (z) {
            CodeEditUtil.setNodeGenerated(leafElementLeaf, true);
        }
        if (leafElementLeaf == null) {
            $$$reportNull$$$0(7);
        }
        return leafElementLeaf;
    }

    public static LeafElement createSingleLeafElement(IElementType iElementType, CharSequence charSequence, CharTable charTable, PsiManager psiManager) {
        if (iElementType == null) {
            $$$reportNull$$$0(8);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(9);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(10);
        }
        return createSingleLeafElement(iElementType, charSequence, 0, charSequence.length(), charTable, psiManager);
    }

    public static LeafElement createSingleLeafElement(IElementType iElementType, CharSequence charSequence, int i, int i2, CharTable charTable, PsiManager psiManager) {
        if (iElementType == null) {
            $$$reportNull$$$0(11);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(12);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(13);
        }
        return createSingleLeafElement(iElementType, charSequence, i, i2, charTable, psiManager, true);
    }

    public static CompositeElement createErrorElement(String str) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        return new PsiErrorElementImpl(str);
    }

    public static CompositeElement createCompositeElement(IElementType iElementType, CharTable charTable, PsiManager psiManager) {
        if (iElementType == null) {
            $$$reportNull$$$0(15);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(16);
        }
        FileElement treeElement = DummyHolderFactory.createHolder(psiManager, (PsiElement) null, charTable).getTreeElement();
        CompositeElement compositeElementComposite = ASTFactory.composite(iElementType);
        treeElement.rawAddChildren(compositeElementComposite);
        if (compositeElementComposite == null) {
            $$$reportNull$$$0(17);
        }
        return compositeElementComposite;
    }
}
