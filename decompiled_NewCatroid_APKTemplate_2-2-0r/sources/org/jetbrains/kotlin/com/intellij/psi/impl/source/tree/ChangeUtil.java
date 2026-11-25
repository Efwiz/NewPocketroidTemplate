package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.lang.ASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.pom.PomManager;
import org.jetbrains.kotlin.com.intellij.pom.PomModel;
import org.jetbrains.kotlin.com.intellij.pom.event.PomModelEvent;
import org.jetbrains.kotlin.com.intellij.pom.impl.PomTransactionBase;
import org.jetbrains.kotlin.com.intellij.pom.tree.TreeAspect;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.TreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolderFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;

/* JADX INFO: loaded from: classes3.dex */
public final class ChangeUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) ChangeUtil.class);

    @FunctionalInterface
    public interface ChangeAction {
        void makeChange(TreeChangeEvent treeChangeEvent);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x003f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 5 || i == 7) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "text";
        } else if (i != 2 && i != 5) {
            switch (i) {
                case 7:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ChangeUtil";
                    break;
                case 8:
                    objArr[0] = "table";
                    break;
                case 9:
                    objArr[0] = "manager";
                    break;
                case 10:
                    objArr[0] = "action";
                    break;
                case 11:
                    objArr[0] = "changedElement";
                    break;
                default:
                    objArr[0] = "original";
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ChangeUtil";
        }
        if (i == 2) {
            objArr[1] = "copyLeafWithText";
        } else if (i == 5) {
            objArr[1] = "copyElement";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ChangeUtil";
        } else {
            objArr[1] = "copyToElement";
        }
        switch (i) {
            case 2:
            case 5:
            case 7:
                break;
            case 3:
            case 4:
                objArr[2] = "copyElement";
                break;
            case 6:
                objArr[2] = "copyToElement";
                break;
            case 8:
            case 9:
                objArr[2] = "generateTreeElement";
                break;
            case 10:
            case 11:
                objArr[2] = "prepareAndRunChangeAction";
                break;
            default:
                objArr[2] = "copyLeafWithText";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static void encodeInformation(TreeElement treeElement) throws Throwable {
        encodeInformation(treeElement, treeElement);
    }

    private static void encodeInformation(final TreeElement treeElement, final ASTNode aSTNode) throws Throwable {
        DebugUtil.performPsiModification((String) null, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                ChangeUtil.encodeInformation(treeElement, aSTNode, new HashMap());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void encodeInformation(TreeElement treeElement, ASTNode aSTNode, Map<Object, Object> map) {
        Iterator<TreeCopyHandler> it = TreeCopyHandler.EP_NAME.getExtensionList().iterator();
        while (it.hasNext()) {
            it.next().encodeInformation(treeElement, aSTNode, map);
        }
        if (aSTNode instanceof CompositeElement) {
            TreeElement firstChildNode = treeElement.getFirstChildNode();
            ASTNode firstChildNode2 = aSTNode.getFirstChildNode();
            while (firstChildNode != null) {
                encodeInformation(firstChildNode, firstChildNode2, map);
                firstChildNode = firstChildNode.getTreeNext();
                firstChildNode2 = firstChildNode2.getTreeNext();
            }
        }
    }

    public static TreeElement decodeInformation(final TreeElement treeElement) {
        return (TreeElement) DebugUtil.performPsiModification((String) null, new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return ChangeUtil.decodeInformation(treeElement, new HashMap());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TreeElement decodeInformation(final TreeElement treeElement, final Map<Object, Object> map) {
        TreeElement firstChildNode = treeElement.getFirstChildNode();
        while (firstChildNode != null) {
            firstChildNode = decodeInformation(firstChildNode, map).getTreeNext();
        }
        return (TreeElement) TreeCopyHandler.EP_NAME.getExtensionList().stream().map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TreeCopyHandler) obj).decodeInformation(treeElement, map);
            }
        }).filter(new ChangeUtil$$ExternalSyntheticLambda2()).findFirst().orElse(treeElement);
    }

    public static LeafElement copyLeafWithText(LeafElement leafElement, String str) {
        if (leafElement == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        LeafElement leafElementLeaf = ASTFactory.leaf(leafElement.getElementType(), str);
        leafElement.copyCopyableDataTo(leafElementLeaf);
        encodeInformation(leafElementLeaf, leafElement);
        TreeUtil.clearCaches(leafElementLeaf);
        saveIndentationToCopy(leafElement, leafElementLeaf);
        if (leafElementLeaf == null) {
            $$$reportNull$$$0(2);
        }
        return leafElementLeaf;
    }

    public static TreeElement copyElement(TreeElement treeElement, CharTable charTable) {
        if (treeElement == null) {
            $$$reportNull$$$0(3);
        }
        CompositeElement treeParent = treeElement.getTreeParent();
        return copyElement(treeElement, treeParent == null ? null : treeParent.getPsi(), charTable);
    }

    public static TreeElement copyElement(TreeElement treeElement, PsiElement psiElement, CharTable charTable) throws Throwable {
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        TreeElement treeElement2 = (TreeElement) treeElement.clone();
        DummyHolderFactory.createHolder(treeElement.getManager(), treeElement2, psiElement, charTable).getTreeElement();
        encodeInformation(treeElement2, treeElement);
        TreeUtil.clearCaches(treeElement2);
        saveIndentationToCopy(treeElement, treeElement2);
        if (treeElement2 == null) {
            $$$reportNull$$$0(5);
        }
        return treeElement2;
    }

    private static void saveIndentationToCopy(TreeElement treeElement, TreeElement treeElement2) {
        if (treeElement == null || treeElement2 == null || CodeEditUtil.isNodeGenerated(treeElement)) {
            return;
        }
        int oldIndentation = CodeEditUtil.getOldIndentation(treeElement);
        if (oldIndentation < 0) {
            CodeEditUtil.saveWhitespacesInfo(treeElement);
        }
        CodeEditUtil.setOldIndentation(treeElement2, CodeEditUtil.getOldIndentation(treeElement));
        if (oldIndentation < 0) {
            CodeEditUtil.setOldIndentation(treeElement, -1);
        }
    }

    public static TreeElement copyToElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        FileElement treeElement = DummyHolderFactory.createHolder(psiElement.getManager(), (CharTable) null, psiElement.getLanguage()).getTreeElement();
        TreeElement treeElementGenerateTreeElement = generateTreeElement(psiElement, treeElement.getCharTable(), psiElement.getManager());
        LOG.assertTrue(treeElementGenerateTreeElement != null, "original element class: " + psiElement.getClass().getName() + ", language: " + psiElement.getLanguage());
        treeElement.rawAddChildren(treeElementGenerateTreeElement);
        TreeUtil.clearCaches(treeElement);
        saveIndentationToCopy((TreeElement) psiElement.getNode(), treeElementGenerateTreeElement);
        if (treeElementGenerateTreeElement == null) {
            $$$reportNull$$$0(7);
        }
        return treeElementGenerateTreeElement;
    }

    public static TreeElement generateTreeElement(final PsiElement psiElement, final CharTable charTable, final PsiManager psiManager) {
        if (charTable == null) {
            $$$reportNull$$$0(8);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(9);
        }
        if (psiElement == null) {
            return null;
        }
        PsiUtilCore.ensureValid(psiElement);
        if (SourceTreeToPsiMap.hasTreeElement(psiElement)) {
            return copyElement((TreeElement) SourceTreeToPsiMap.psiElementToTree(psiElement), charTable);
        }
        return (TreeElement) TreeGenerator.EP_NAME.getExtensionList().stream().map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TreeGenerator) obj).generateTreeFor(psiElement, charTable, psiManager);
            }
        }).filter(new ChangeUtil$$ExternalSyntheticLambda2()).findFirst().orElse(null);
    }

    public static void prepareAndRunChangeAction(final ChangeAction changeAction, final TreeElement treeElement) {
        if (changeAction == null) {
            $$$reportNull$$$0(10);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(11);
        }
        final FileElement fileElement = TreeUtil.getFileElement(treeElement);
        final PomModel model = PomManager.getModel(fileElement.getManager().getProject());
        model.runTransaction(new PomTransactionBase(treeElement.getPsi()) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/ChangeUtil$1", "runInner"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.pom.impl.PomTransactionBase
            public PomModelEvent runInner() {
                TreeChangeEventImpl treeChangeEventImpl = new TreeChangeEventImpl(model.getModelAspect(TreeAspect.class), fileElement);
                PomModelEvent pomModelEvent = new PomModelEvent(model, treeChangeEventImpl);
                changeAction.makeChange(treeChangeEventImpl);
                treeElement.clearCaches();
                TreeElement treeElement2 = treeElement;
                if (treeElement2 instanceof CompositeElement) {
                    ((CompositeElement) treeElement2).subtreeChanged();
                }
                return pomModelEvent;
            }
        });
    }
}
