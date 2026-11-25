package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.LighterAST;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTTokenNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public final class LightTreeUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 11 || i == 15 || i == 19 || i == 22) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 11 || i == 15 || i == 19 || i == 22) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 14:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
            case 4:
            case 8:
            case 12:
            case 16:
            case 20:
            case 23:
            case 26:
            case 29:
            default:
                objArr[0] = Constants.TYPE_TREE;
                break;
            case 3:
            case 10:
            case 18:
            case 27:
                objArr[0] = SpecTypePair.NAME_types;
                break;
            case 5:
            case 9:
            case 13:
            case 17:
            case 21:
            case 24:
                objArr[0] = "node";
                break;
            case 7:
            case 11:
            case 15:
            case 19:
            case 22:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LightTreeUtil";
                break;
            case 25:
                objArr[0] = "buffer";
                break;
            case 28:
                objArr[0] = "stopAt";
                break;
            case 30:
                objArr[0] = "consumer";
                break;
        }
        if (i == 7 || i == 11) {
            objArr[1] = "requiredChildOfType";
        } else if (i == 15 || i == 19) {
            objArr[1] = "getChildrenOfType";
        } else if (i != 22) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LightTreeUtil";
        } else {
            objArr[1] = "toFilteredString";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
                objArr[2] = "requiredChildOfType";
                break;
            case 7:
            case 11:
            case 15:
            case 19:
            case 22:
                break;
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
                objArr[2] = "getChildrenOfType";
                break;
            case 20:
            case 21:
                objArr[2] = "toFilteredString";
                break;
            case 23:
            case 24:
            case 25:
                objArr[2] = "toBuffer";
                break;
            case 26:
            case 27:
            case 28:
                objArr[2] = "getParentOfType";
                break;
            case 29:
            case 30:
                objArr[2] = "processLeavesAtOffsets";
                break;
            default:
                objArr[2] = "firstChildOfType";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 11 && i != 15 && i != 19 && i != 22) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static LighterASTNode firstChildOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, IElementType iElementType) {
        if (lighterAST == null) {
            $$$reportNull$$$0(0);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        if (lighterASTNode == null) {
            return null;
        }
        List<LighterASTNode> children = lighterAST.getChildren(lighterASTNode);
        for (int i = 0; i < children.size(); i++) {
            LighterASTNode lighterASTNode2 = children.get(i);
            if (lighterASTNode2.getTokenType() == iElementType) {
                return lighterASTNode2;
            }
        }
        return null;
    }

    public static LighterASTNode firstChildOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, TokenSet tokenSet) {
        if (lighterAST == null) {
            $$$reportNull$$$0(2);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(3);
        }
        if (lighterASTNode == null) {
            return null;
        }
        List<LighterASTNode> children = lighterAST.getChildren(lighterASTNode);
        for (int i = 0; i < children.size(); i++) {
            LighterASTNode lighterASTNode2 = children.get(i);
            if (tokenSet.contains(lighterASTNode2.getTokenType())) {
                return lighterASTNode2;
            }
        }
        return null;
    }

    public static LighterASTNode requiredChildOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, IElementType iElementType) {
        if (lighterAST == null) {
            $$$reportNull$$$0(4);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(5);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(6);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = firstChildOfType(lighterAST, lighterASTNode, iElementType);
        if (lighterASTNodeFirstChildOfType == null) {
            $$$reportNull$$$0(7);
        }
        return lighterASTNodeFirstChildOfType;
    }

    public static LighterASTNode requiredChildOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, TokenSet tokenSet) {
        if (lighterAST == null) {
            $$$reportNull$$$0(8);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(9);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(10);
        }
        LighterASTNode lighterASTNodeFirstChildOfType = firstChildOfType(lighterAST, lighterASTNode, tokenSet);
        if (lighterASTNodeFirstChildOfType == null) {
            $$$reportNull$$$0(11);
        }
        return lighterASTNodeFirstChildOfType;
    }

    public static List<LighterASTNode> getChildrenOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, IElementType iElementType) {
        if (lighterAST == null) {
            $$$reportNull$$$0(12);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(13);
        }
        if (iElementType == null) {
            $$$reportNull$$$0(14);
        }
        List<LighterASTNode> children = lighterAST.getChildren(lighterASTNode);
        int size = children.size();
        List<LighterASTNode> listEmptyList = null;
        for (int i = 0; i < size; i++) {
            LighterASTNode lighterASTNode2 = children.get(i);
            if (lighterASTNode2.getTokenType() == iElementType) {
                if (listEmptyList == null) {
                    listEmptyList = new SmartList<>();
                }
                listEmptyList.add(lighterASTNode2);
            }
        }
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(15);
        }
        return listEmptyList;
    }

    public static List<LighterASTNode> getChildrenOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, TokenSet tokenSet) {
        if (lighterAST == null) {
            $$$reportNull$$$0(16);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(17);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(18);
        }
        List<LighterASTNode> children = lighterAST.getChildren(lighterASTNode);
        int size = children.size();
        List<LighterASTNode> listEmptyList = null;
        for (int i = 0; i < size; i++) {
            LighterASTNode lighterASTNode2 = children.get(i);
            if (tokenSet.contains(lighterASTNode2.getTokenType())) {
                if (listEmptyList == null) {
                    listEmptyList = new SmartList<>();
                }
                listEmptyList.add(lighterASTNode2);
            }
        }
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(19);
        }
        return listEmptyList;
    }

    public static String toFilteredString(LighterAST lighterAST, LighterASTNode lighterASTNode, TokenSet tokenSet) {
        if (lighterAST == null) {
            $$$reportNull$$$0(20);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(21);
        }
        int endOffset = lighterASTNode.getEndOffset() - lighterASTNode.getStartOffset();
        if (endOffset < 0) {
            Logger.getInstance((Class<?>) LightTreeUtil.class).error("tree=" + lighterAST + " node=" + lighterASTNode);
            endOffset = 0;
        }
        StringBuilder sb = new StringBuilder(endOffset);
        toBuffer(lighterAST, lighterASTNode, sb, tokenSet);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(22);
        }
        return string;
    }

    public static void toBuffer(LighterAST lighterAST, LighterASTNode lighterASTNode, StringBuilder sb, TokenSet tokenSet) {
        if (lighterAST == null) {
            $$$reportNull$$$0(23);
        }
        if (lighterASTNode == null) {
            $$$reportNull$$$0(24);
        }
        if (sb == null) {
            $$$reportNull$$$0(25);
        }
        if (tokenSet == null || !tokenSet.contains(lighterASTNode.getTokenType())) {
            if (lighterASTNode instanceof LighterASTTokenNode) {
                sb.append(((LighterASTTokenNode) lighterASTNode).getText());
                return;
            }
            if (lighterASTNode instanceof LighterLazyParseableNode) {
                sb.append(((LighterLazyParseableNode) lighterASTNode).getText());
                return;
            }
            List<LighterASTNode> children = lighterAST.getChildren(lighterASTNode);
            int size = children.size();
            for (int i = 0; i < size; i++) {
                toBuffer(lighterAST, children.get(i), sb, tokenSet);
            }
        }
    }

    public static LighterASTNode getParentOfType(LighterAST lighterAST, LighterASTNode lighterASTNode, TokenSet tokenSet, TokenSet tokenSet2) {
        if (lighterAST == null) {
            $$$reportNull$$$0(26);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(27);
        }
        if (tokenSet2 == null) {
            $$$reportNull$$$0(28);
        }
        if (lighterASTNode == null) {
            return null;
        }
        LighterASTNode parent = lighterAST.getParent(lighterASTNode);
        while (parent != null) {
            IElementType tokenType = parent.getTokenType();
            if (tokenSet.contains(tokenType)) {
                return parent;
            }
            if (tokenSet2.contains(tokenType)) {
                return null;
            }
            parent = lighterAST.getParent(parent);
        }
        return null;
    }

    public static void processLeavesAtOffsets(int[] iArr, LighterAST lighterAST, BiConsumer<? super LighterASTTokenNode, ? super Integer> biConsumer) {
        if (lighterAST == null) {
            $$$reportNull$$$0(29);
        }
        if (biConsumer == null) {
            $$$reportNull$$$0(30);
        }
        if (iArr.length == 0) {
            return;
        }
        int[] iArr2 = (int[]) iArr.clone();
        Arrays.sort(iArr2);
        new RecursiveLighterASTNodeWalkingVisitor(lighterAST, iArr2, biConsumer) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LightTreeUtil.1
            int nextIndex = 0;
            int nextOffset;
            final /* synthetic */ BiConsumer val$consumer;
            final /* synthetic */ int[] val$sortedOffsets;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                } else {
                    objArr[0] = "node";
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LightTreeUtil$1";
                if (i == 1) {
                    objArr[2] = "visitTokenNode";
                } else if (i != 2) {
                    objArr[2] = "visitNode";
                } else {
                    objArr[2] = "containsNextOffset";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            {
                this.val$sortedOffsets = iArr2;
                this.val$consumer = biConsumer;
                this.nextOffset = iArr2[0];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.RecursiveLighterASTNodeWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LighterASTNodeVisitor
            public void visitNode(LighterASTNode lighterASTNode) {
                if (lighterASTNode == null) {
                    $$$reportNull$$$0(0);
                }
                if (containsNextOffset(lighterASTNode)) {
                    super.visitNode(lighterASTNode);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LighterASTNodeVisitor
            public void visitTokenNode(LighterASTTokenNode lighterASTTokenNode) {
                if (lighterASTTokenNode == null) {
                    $$$reportNull$$$0(1);
                }
                if (containsNextOffset(lighterASTTokenNode)) {
                    this.val$consumer.accept(lighterASTTokenNode, Integer.valueOf(this.nextOffset));
                    while (containsNextOffset(lighterASTTokenNode)) {
                        advanceOffset();
                    }
                }
            }

            private boolean containsNextOffset(LighterASTNode lighterASTNode) {
                if (lighterASTNode == null) {
                    $$$reportNull$$$0(2);
                }
                ProgressManager.checkCanceled();
                if (this.nextIndex < this.val$sortedOffsets.length) {
                    int startOffset = lighterASTNode.getStartOffset();
                    int i = this.nextOffset;
                    if (startOffset <= i && i < lighterASTNode.getEndOffset()) {
                        return true;
                    }
                }
                return false;
            }

            private void advanceOffset() {
                int i = this.nextIndex + 1;
                this.nextIndex = i;
                int[] iArr3 = this.val$sortedOffsets;
                if (i < iArr3.length) {
                    this.nextOffset = iArr3[i];
                }
            }
        }.visitNode(lighterAST.getRoot());
    }
}
