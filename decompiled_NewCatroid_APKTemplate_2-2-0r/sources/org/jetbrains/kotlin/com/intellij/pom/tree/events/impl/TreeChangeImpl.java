package org.jetbrains.kotlin.com.intellij.pom.tree.events.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class TreeChangeImpl implements Comparable<TreeChangeImpl> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Map<ASTNode, ChangeInfoImpl> myChanges;
    private final Set<ASTNode> myContentChangeChildren;
    private final LinkedHashMap<ASTNode, Integer> myInitialLengths;
    private final ASTNode myParent;
    private final List<ASTNode> mySuperParents;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/tree/events/impl/TreeChangeImpl";
                break;
            case 2:
                objArr[0] = "o";
                break;
            case 5:
                objArr[0] = ES6Iterator.NEXT_METHOD;
                break;
            case 6:
                objArr[0] = "child";
                break;
            default:
                objArr[0] = "parent";
                break;
        }
        if (i == 1) {
            objArr[1] = "getCurrentChildren";
        } else if (i == 3) {
            objArr[1] = "getChangedParent";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/tree/events/impl/TreeChangeImpl";
        } else {
            objArr[1] = "getAffectedChildren";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
                break;
            case 2:
                objArr[2] = "compareTo";
                break;
            case 5:
                objArr[2] = "appendChanges";
                break;
            case 6:
                objArr[2] = "markChildChanged";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public TreeChangeImpl(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.myInitialLengths = new LinkedHashMap<>();
        this.myContentChangeChildren = new HashSet();
        this.myParent = aSTNode;
        this.mySuperParents = JBIterable.generate(aSTNode.getTreeParent(), new TreeChangeEventImpl$$ExternalSyntheticLambda0()).toList();
        for (ASTNode aSTNode2 : getCurrentChildren()) {
            this.myInitialLengths.put(aSTNode2, Integer.valueOf(aSTNode2.getTextLength()));
        }
    }

    List<ASTNode> getSuperParents() {
        return this.mySuperParents;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JBIterable<ASTNode> getCurrentChildren() {
        JBIterable<ASTNode> jBIterableGenerate = JBIterable.generate(this.myParent.getFirstChildNode(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((ASTNode) obj).getTreeNext();
            }
        });
        if (jBIterableGenerate == null) {
            $$$reportNull$$$0(1);
        }
        return jBIterableGenerate;
    }

    @Override // java.lang.Comparable
    public int compareTo(TreeChangeImpl treeChangeImpl) {
        if (treeChangeImpl == null) {
            $$$reportNull$$$0(2);
        }
        List listReverse = ContainerUtil.reverse(getSuperParents());
        List listReverse2 = ContainerUtil.reverse(treeChangeImpl.getSuperParents());
        int i = 1;
        while (i <= listReverse.size() && i <= listReverse2.size()) {
            int iCompareNodePositions = compareNodePositions(i < listReverse.size() ? (ASTNode) listReverse.get(i) : this.myParent, i < listReverse2.size() ? (ASTNode) listReverse2.get(i) : treeChangeImpl.myParent);
            if (iCompareNodePositions != 0) {
                return iCompareNodePositions;
            }
            i++;
        }
        return 0;
    }

    private static int compareNodePositions(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == aSTNode2) {
            return 0;
        }
        int startOffsetInParent = aSTNode.getStartOffsetInParent();
        int startOffsetInParent2 = aSTNode2.getStartOffsetInParent();
        return startOffsetInParent != startOffsetInParent2 ? Integer.compare(startOffsetInParent, startOffsetInParent2) : Integer.compare(getChildIndex(aSTNode), getChildIndex(aSTNode2));
    }

    private static int getChildIndex(ASTNode aSTNode) {
        return ArrayUtil.indexOf(aSTNode.getTreeParent().getChildren(null), aSTNode);
    }

    int getLengthDelta() {
        return getAllChanges().values().stream().mapToInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((ChangeInfoImpl) obj).getLengthDelta();
            }
        }).sum();
    }

    void clearCache() {
        this.myChanges = null;
    }

    private Map<ASTNode, ChangeInfoImpl> getAllChanges() {
        Map<ASTNode, ChangeInfoImpl> map = this.myChanges;
        if (map != null) {
            return map;
        }
        Map<ASTNode, ChangeInfoImpl> mapCalcChanges = new ChildrenDiff().calcChanges();
        this.myChanges = mapCalcChanges;
        return mapCalcChanges;
    }

    private class ChildrenDiff {
        LinkedHashSet<ASTNode> currentChildren;
        Iterator<ASTNode> itNew;
        Iterator<ASTNode> itOld;
        ASTNode newChild;
        ASTNode oldChild;
        int oldOffset;
        LinkedHashMap<ASTNode, ChangeInfoImpl> result;

        private ChildrenDiff() {
            this.currentChildren = (LinkedHashSet) TreeChangeImpl.this.getCurrentChildren().addAllTo(new LinkedHashSet());
            this.itOld = TreeChangeImpl.this.myInitialLengths.keySet().iterator();
            this.itNew = this.currentChildren.iterator();
            this.oldOffset = 0;
            this.result = new LinkedHashMap<>();
        }

        void advanceOld() {
            this.oldOffset += this.oldChild == null ? 0 : ((Integer) TreeChangeImpl.this.myInitialLengths.get(this.oldChild)).intValue();
            this.oldChild = this.itOld.hasNext() ? this.itOld.next() : null;
        }

        void advanceNew() {
            this.newChild = this.itNew.hasNext() ? this.itNew.next() : null;
        }

        Map<ASTNode, ChangeInfoImpl> calcChanges() {
            advanceOld();
            advanceNew();
            while (true) {
                ASTNode aSTNode = this.oldChild;
                if (aSTNode != null || this.newChild != null) {
                    if (aSTNode == this.newChild) {
                        if (TreeChangeImpl.this.myContentChangeChildren.contains(this.oldChild)) {
                            ASTNode aSTNode2 = this.oldChild;
                            addChange(new ChangeInfoImpl(aSTNode2, aSTNode2, this.oldOffset, ((Integer) TreeChangeImpl.this.myInitialLengths.get(this.oldChild)).intValue()));
                        }
                        advanceOld();
                        advanceNew();
                    } else {
                        boolean z = (aSTNode == null || this.currentChildren.contains(aSTNode)) ? false : true;
                        boolean z2 = (this.newChild == null || TreeChangeImpl.this.myInitialLengths.containsKey(this.newChild)) ? false : true;
                        addChange(new ChangeInfoImpl(z ? this.oldChild : null, z2 ? this.newChild : null, this.oldOffset, z ? ((Integer) TreeChangeImpl.this.myInitialLengths.get(this.oldChild)).intValue() : 0));
                        if (z) {
                            advanceOld();
                        }
                        if (z2) {
                            advanceNew();
                        }
                    }
                } else {
                    return this.result;
                }
            }
        }

        private void addChange(ChangeInfoImpl changeInfoImpl) {
            this.result.put(changeInfoImpl.getAffectedChild(), changeInfoImpl);
            this.oldOffset += changeInfoImpl.getLengthDelta();
        }
    }

    public ASTNode getChangedParent() {
        ASTNode aSTNode = this.myParent;
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        return aSTNode;
    }

    void fireEvents(PsiFile psiFile) {
        int startOffset = this.myParent.getStartOffset();
        Collection<ChangeInfoImpl> collectionValues = getAllChanges().values();
        if (ContainerUtil.exists(collectionValues, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ((ChangeInfoImpl) obj).hasNoPsi();
            }
        })) {
            PsiTreeChangeEventImpl psiTreeChangeEventImplCreateEvent = ChangeInfoImpl.createEvent(psiFile, startOffset);
            ASTNode aSTNode = this.myParent;
            ChangeInfoImpl.childrenChanged(psiTreeChangeEventImplCreateEvent, aSTNode, aSTNode.getTextLength() - getLengthDelta());
        } else {
            Iterator<ChangeInfoImpl> it = collectionValues.iterator();
            while (it.hasNext()) {
                it.next().fireEvent(startOffset, psiFile, this.myParent);
            }
        }
    }

    public ASTNode[] getAffectedChildren() {
        ASTNode[] aSTNodeArr = (ASTNode[]) getAllChanges().keySet().toArray(ASTNode.EMPTY_ARRAY);
        if (aSTNodeArr == null) {
            $$$reportNull$$$0(4);
        }
        return aSTNodeArr;
    }

    public ChangeInfoImpl getChangeByChild(ASTNode aSTNode) {
        return getAllChanges().get(aSTNode);
    }

    public String toString() {
        return this.myParent + ": " + getAllChanges().values();
    }

    void appendChanges(TreeChangeImpl treeChangeImpl) {
        if (treeChangeImpl == null) {
            $$$reportNull$$$0(5);
        }
        this.myContentChangeChildren.addAll(treeChangeImpl.myContentChangeChildren);
        clearCache();
    }

    public void markChildChanged(ASTNode aSTNode, final int i) {
        if (aSTNode == null) {
            $$$reportNull$$$0(6);
        }
        this.myContentChangeChildren.add(aSTNode);
        if (i != 0) {
            this.myInitialLengths.computeIfPresent(aSTNode, new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Integer.valueOf(((Integer) obj2).intValue() - i);
                }
            });
        }
        clearCache();
    }
}
