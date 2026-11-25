package org.jetbrains.kotlin.com.intellij.pom.tree.events.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect;
import org.jetbrains.kotlin.com.intellij.pom.event.PomChangeSet;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.TreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class TreeChangeEventImpl implements TreeChangeEvent {
    private final PomModelAspect myAspect;
    private final Map<ASTNode, TreeChangeImpl> myChangedElements;
    private final MultiMap<ASTNode, TreeChangeImpl> myChangesByAllParents;
    private final FileASTNode myFileElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 8 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 8 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "treeElement";
                break;
            case 2:
            case 3:
            case 8:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/tree/events/impl/TreeChangeEventImpl";
                break;
            case 4:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 5:
                objArr[0] = "parent";
                break;
            case 6:
                objArr[0] = "ancestor";
                break;
            case 7:
                objArr[0] = "change";
                break;
            case 9:
                objArr[0] = ES6Iterator.NEXT_METHOD;
                break;
            default:
                objArr[0] = "aspect";
                break;
        }
        if (i == 2) {
            objArr[1] = "getRootElement";
        } else if (i == 3) {
            objArr[1] = "getChangedElements";
        } else if (i == 8) {
            objArr[1] = "getAspect";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/tree/events/impl/TreeChangeEventImpl";
        } else {
            objArr[1] = "getSortedChanges";
        }
        switch (i) {
            case 2:
            case 3:
            case 8:
            case 10:
                break;
            case 4:
                objArr[2] = "getChangesByElement";
                break;
            case 5:
                objArr[2] = "addElementaryChange";
                break;
            case 6:
            case 7:
                objArr[2] = "findAncestorChild";
                break;
            case 9:
                objArr[2] = "merge";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 8 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public TreeChangeEventImpl(PomModelAspect pomModelAspect, FileASTNode fileASTNode) {
        if (pomModelAspect == null) {
            $$$reportNull$$$0(0);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(1);
        }
        this.myChangedElements = new LinkedHashMap();
        this.myChangesByAllParents = MultiMap.createSet();
        this.myAspect = pomModelAspect;
        this.myFileElement = fileASTNode;
    }

    public FileASTNode getRootElement() {
        FileASTNode fileASTNode = this.myFileElement;
        if (fileASTNode == null) {
            $$$reportNull$$$0(2);
        }
        return fileASTNode;
    }

    public void addElementaryChange(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        TreeChangeImpl treeChangeImpl = this.myChangedElements.get(aSTNode);
        if (treeChangeImpl != null) {
            treeChangeImpl.clearCache();
        } else {
            if (integrateIntoExistingChanges(aSTNode)) {
                return;
            }
            mergeChange(new TreeChangeImpl(aSTNode));
        }
    }

    private boolean integrateIntoExistingChanges(ASTNode aSTNode) {
        for (ASTNode aSTNode2 : JBIterable.generate(aSTNode, new TreeChangeEventImpl$$ExternalSyntheticLambda0())) {
            TreeChangeImpl treeChangeImpl = this.myChangedElements.get(aSTNode2.getTreeParent());
            if (treeChangeImpl != null) {
                treeChangeImpl.markChildChanged(aSTNode2, 0);
                return true;
            }
        }
        return false;
    }

    private void mergeChange(TreeChangeImpl treeChangeImpl) {
        ASTNode changedParent = treeChangeImpl.getChangedParent();
        for (TreeChangeImpl treeChangeImpl2 : new ArrayList(this.myChangesByAllParents.get(changedParent))) {
            ASTNode aSTNodeFindAncestorChild = findAncestorChild(changedParent, treeChangeImpl2);
            if (aSTNodeFindAncestorChild != null) {
                treeChangeImpl.markChildChanged(aSTNodeFindAncestorChild, treeChangeImpl2.getLengthDelta());
            }
            unregisterChange(treeChangeImpl2);
        }
        registerChange(treeChangeImpl);
    }

    private void registerChange(TreeChangeImpl treeChangeImpl) {
        this.myChangedElements.put(treeChangeImpl.getChangedParent(), treeChangeImpl);
        Iterator<ASTNode> it = treeChangeImpl.getSuperParents().iterator();
        while (it.hasNext()) {
            this.myChangesByAllParents.putValue(it.next(), treeChangeImpl);
        }
    }

    private void unregisterChange(TreeChangeImpl treeChangeImpl) {
        this.myChangedElements.remove(treeChangeImpl.getChangedParent());
        Iterator<ASTNode> it = treeChangeImpl.getSuperParents().iterator();
        while (it.hasNext()) {
            this.myChangesByAllParents.remove(it.next(), treeChangeImpl);
        }
    }

    private static ASTNode findAncestorChild(ASTNode aSTNode, TreeChangeImpl treeChangeImpl) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (treeChangeImpl == null) {
            $$$reportNull$$$0(7);
        }
        List<ASTNode> superParents = treeChangeImpl.getSuperParents();
        int iIndexOf = superParents.indexOf(aSTNode);
        if (iIndexOf < 0) {
            return null;
        }
        if (iIndexOf == 0) {
            return treeChangeImpl.getChangedParent();
        }
        return superParents.get(iIndexOf - 1);
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.event.PomChangeSet
    public PomModelAspect getAspect() {
        PomModelAspect pomModelAspect = this.myAspect;
        if (pomModelAspect == null) {
            $$$reportNull$$$0(8);
        }
        return pomModelAspect;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.event.PomChangeSet
    public void merge(PomChangeSet pomChangeSet) {
        if (pomChangeSet == null) {
            $$$reportNull$$$0(9);
        }
        for (TreeChangeImpl treeChangeImpl : ((TreeChangeEventImpl) pomChangeSet).myChangedElements.values()) {
            TreeChangeImpl treeChangeImpl2 = this.myChangedElements.get(treeChangeImpl.getChangedParent());
            if (treeChangeImpl2 != null) {
                treeChangeImpl2.appendChanges(treeChangeImpl);
            } else if (!integrateIntoExistingChanges(treeChangeImpl.getChangedParent())) {
                mergeChange(treeChangeImpl);
            }
        }
    }

    public void fireEvents() {
        Iterator<TreeChangeImpl> it = getSortedChanges().iterator();
        while (it.hasNext()) {
            it.next().fireEvents((PsiFile) this.myFileElement.getPsi());
        }
    }

    public List<TreeChangeImpl> getSortedChanges() {
        List<TreeChangeImpl> listSorted = ContainerUtil.sorted(this.myChangedElements.values());
        if (listSorted == null) {
            $$$reportNull$$$0(10);
        }
        return listSorted;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.event.PomChangeSet
    public void beforeNestedTransaction() {
        Iterator<TreeChangeImpl> it = this.myChangedElements.values().iterator();
        while (it.hasNext()) {
            it.next().getAffectedChildren();
        }
    }

    public String toString() {
        return new ArrayList(this.myChangedElements.values()).toString();
    }
}
