package org.jetbrains.kotlin.com.intellij.psi.templateLanguages;

import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public interface TreePatcher {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "table";
        } else if (i != 2) {
            objArr[0] = "leaf";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/templateLanguages/TreePatcher";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/templateLanguages/TreePatcher";
        } else {
            objArr[1] = "split";
        }
        if (i != 2) {
            objArr[2] = "split";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    void insert(CompositeElement compositeElement, TreeElement treeElement, OuterLanguageElement outerLanguageElement);

    default LeafElement split(LeafElement leafElement, int i, CharTable charTable) {
        if (leafElement == null) {
            $$$reportNull$$$0(0);
        }
        if (charTable == null) {
            $$$reportNull$$$0(1);
        }
        CharSequence chars = leafElement.getChars();
        TreeElement treeElementLeaf = ASTFactory.leaf(leafElement.getElementType(), charTable.intern(chars, 0, i));
        LeafElement leafElementLeaf = ASTFactory.leaf(leafElement.getElementType(), charTable.intern(chars, i, chars.length()));
        leafElement.rawInsertAfterMe(treeElementLeaf);
        treeElementLeaf.rawInsertAfterMe(leafElementLeaf);
        leafElement.rawRemove();
        if (leafElementLeaf == null) {
            $$$reportNull$$$0(2);
        }
        return leafElementLeaf;
    }
}
