package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.HashMap;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ModifierListElement extends CompositeElement {
    private static final Logger LOG = Logger.getInstance((Class<?>) ModifierListElement.class);
    private static final HashMap<String, Integer> ourModifierToOrderMap;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "child", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/ModifierListElement", "getChildRole"));
    }

    static {
        HashMap<String, Integer> map = new HashMap<>();
        ourModifierToOrderMap = map;
        map.put("public", 1);
        map.put("private", 1);
        map.put("protected", 1);
        map.put("static", 2);
        map.put("abstract", 2);
        map.put("final", 3);
        map.put("synchronized", 4);
        map.put("transient", 4);
        map.put("volatile", 4);
        map.put("native", 5);
        map.put("strictfp", 6);
    }

    public ModifierListElement() {
        super(JavaElementType.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        if (bool == null && treeElement == aSTNode && ElementType.KEYWORD_BIT_SET.contains(treeElement.getElementType())) {
            aSTNode2 = getDefaultAnchor((PsiModifierList) SourceTreeToPsiMap.treeElementToPsi(this), (PsiKeyword) SourceTreeToPsiMap.treeElementToPsi(treeElement));
            bool = Boolean.TRUE;
        }
        return super.addInternal(treeElement, aSTNode, aSTNode2, bool);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        return aSTNode.getElementType() == JavaElementType.ANNOTATION ? 250 : 0;
    }

    private static ASTNode getDefaultAnchor(PsiModifierList psiModifierList, PsiKeyword psiKeyword) {
        Integer num = ourModifierToOrderMap.get(psiKeyword.getText());
        if (num == null) {
            return null;
        }
        boolean z = false;
        for (ASTNode firstChildNode = SourceTreeToPsiMap.psiToTreeNotNull(psiModifierList).getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            if (ElementType.KEYWORD_BIT_SET.contains(firstChildNode.getElementType())) {
                Integer num2 = ourModifierToOrderMap.get(firstChildNode.getText());
                if (num2 != null && num2.intValue() > num.intValue()) {
                    return firstChildNode;
                }
                z = true;
            } else if (firstChildNode.getElementType() == JavaElementType.ANNOTATION && z) {
                return firstChildNode;
            }
        }
        return null;
    }
}
