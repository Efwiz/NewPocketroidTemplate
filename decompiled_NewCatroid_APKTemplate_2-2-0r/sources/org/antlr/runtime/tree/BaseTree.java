package org.antlr.runtime.tree;

import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public abstract class BaseTree implements Tree {
    protected List<Object> children;

    @Override // org.antlr.runtime.tree.Tree
    public int getCharPositionInLine() {
        return 0;
    }

    @Override // org.antlr.runtime.tree.Tree
    public int getChildIndex() {
        return 0;
    }

    @Override // org.antlr.runtime.tree.Tree
    public int getLine() {
        return 0;
    }

    @Override // org.antlr.runtime.tree.Tree
    public Tree getParent() {
        return null;
    }

    @Override // org.antlr.runtime.tree.Tree
    public boolean isNil() {
        return false;
    }

    @Override // org.antlr.runtime.tree.Tree
    public void setChildIndex(int i) {
    }

    @Override // org.antlr.runtime.tree.Tree
    public void setParent(Tree tree) {
    }

    @Override // org.antlr.runtime.tree.Tree
    public abstract String toString();

    public BaseTree() {
    }

    public BaseTree(Tree tree) {
    }

    @Override // org.antlr.runtime.tree.Tree
    public Tree getChild(int i) {
        List<Object> list = this.children;
        if (list == null || i >= list.size()) {
            return null;
        }
        return (Tree) this.children.get(i);
    }

    public List<? extends Object> getChildren() {
        return this.children;
    }

    public Tree getFirstChildWithType(int i) {
        int i2 = 0;
        while (true) {
            List<Object> list = this.children;
            if (list == null || i2 >= list.size()) {
                return null;
            }
            Tree tree = (Tree) this.children.get(i2);
            if (tree.getType() == i) {
                return tree;
            }
            i2++;
        }
    }

    @Override // org.antlr.runtime.tree.Tree
    public int getChildCount() {
        List<Object> list = this.children;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // org.antlr.runtime.tree.Tree
    public void addChild(Tree tree) {
        if (tree == null) {
            return;
        }
        BaseTree baseTree = (BaseTree) tree;
        if (baseTree.isNil()) {
            List<Object> list = this.children;
            if (list != null && list == baseTree.children) {
                throw new RuntimeException("attempt to add child list to itself");
            }
            List<Object> list2 = baseTree.children;
            if (list2 != null) {
                if (list != null) {
                    int size = list2.size();
                    for (int i = 0; i < size; i++) {
                        Tree tree2 = (Tree) baseTree.children.get(i);
                        this.children.add(tree2);
                        tree2.setParent(this);
                        tree2.setChildIndex(this.children.size() - 1);
                    }
                    return;
                }
                this.children = list2;
                freshenParentAndChildIndexes();
                return;
            }
            return;
        }
        if (this.children == null) {
            this.children = createChildrenList();
        }
        this.children.add(tree);
        baseTree.setParent(this);
        baseTree.setChildIndex(this.children.size() - 1);
    }

    public void addChildren(List<? extends Tree> list) {
        for (int i = 0; i < list.size(); i++) {
            addChild(list.get(i));
        }
    }

    @Override // org.antlr.runtime.tree.Tree
    public void setChild(int i, Tree tree) {
        if (tree == null) {
            return;
        }
        if (tree.isNil()) {
            throw new IllegalArgumentException("Can't set single child to a list");
        }
        if (this.children == null) {
            this.children = createChildrenList();
        }
        this.children.set(i, tree);
        tree.setParent(this);
        tree.setChildIndex(i);
    }

    public void insertChild(int i, Object obj) {
        if (i < 0 || i > getChildCount()) {
            throw new IndexOutOfBoundsException(i + " out or range");
        }
        if (this.children == null) {
            this.children = createChildrenList();
        }
        this.children.add(i, obj);
        freshenParentAndChildIndexes(i);
    }

    @Override // org.antlr.runtime.tree.Tree
    public Object deleteChild(int i) {
        List<Object> list = this.children;
        if (list == null) {
            return null;
        }
        Tree tree = (Tree) list.remove(i);
        freshenParentAndChildIndexes(i);
        return tree;
    }

    @Override // org.antlr.runtime.tree.Tree
    public void replaceChildren(int i, int i2, Object obj) {
        List<Object> list;
        if (this.children == null) {
            throw new IllegalArgumentException("indexes invalid; no children in list");
        }
        int i3 = (i2 - i) + 1;
        BaseTree baseTree = (BaseTree) obj;
        if (baseTree.isNil()) {
            list = baseTree.children;
        } else {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(baseTree);
            list = arrayList;
        }
        int size = list.size();
        int size2 = list.size();
        int i4 = i3 - size;
        int i5 = 0;
        if (i4 == 0) {
            while (i <= i2) {
                BaseTree baseTree2 = (BaseTree) list.get(i5);
                this.children.set(i, baseTree2);
                baseTree2.setParent(this);
                baseTree2.setChildIndex(i);
                i5++;
                i++;
            }
            return;
        }
        if (i4 > 0) {
            while (i5 < size2) {
                this.children.set(i + i5, list.get(i5));
                i5++;
            }
            int i6 = size2 + i;
            for (int i7 = i6; i7 <= i2; i7++) {
                this.children.remove(i6);
            }
            freshenParentAndChildIndexes(i);
            return;
        }
        while (i5 < i3) {
            this.children.set(i + i5, list.get(i5));
            i5++;
        }
        while (i3 < size) {
            this.children.add(i + i3, list.get(i3));
            i3++;
        }
        freshenParentAndChildIndexes(i);
    }

    protected List<Object> createChildrenList() {
        return new ArrayList();
    }

    @Override // org.antlr.runtime.tree.Tree
    public void freshenParentAndChildIndexes() {
        freshenParentAndChildIndexes(0);
    }

    public void freshenParentAndChildIndexes(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            Tree child = getChild(i);
            child.setChildIndex(i);
            child.setParent(this);
            i++;
        }
    }

    public void freshenParentAndChildIndexesDeeply() {
        freshenParentAndChildIndexesDeeply(0);
    }

    public void freshenParentAndChildIndexesDeeply(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            BaseTree baseTree = (BaseTree) getChild(i);
            baseTree.setChildIndex(i);
            baseTree.setParent(this);
            baseTree.freshenParentAndChildIndexesDeeply();
            i++;
        }
    }

    public void sanityCheckParentAndChildIndexes() {
        sanityCheckParentAndChildIndexes(null, -1);
    }

    public void sanityCheckParentAndChildIndexes(Tree tree, int i) {
        if (tree != getParent()) {
            throw new IllegalStateException("parents don't match; expected " + tree + " found " + getParent());
        }
        if (i != getChildIndex()) {
            throw new IllegalStateException("child indexes don't match; expected " + i + " found " + getChildIndex());
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((CommonTree) getChild(i2)).sanityCheckParentAndChildIndexes(this, i2);
        }
    }

    @Override // org.antlr.runtime.tree.Tree
    public boolean hasAncestor(int i) {
        return getAncestor(i) != null;
    }

    @Override // org.antlr.runtime.tree.Tree
    public Tree getAncestor(int i) {
        for (Tree parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent.getType() == i) {
                return parent;
            }
        }
        return null;
    }

    @Override // org.antlr.runtime.tree.Tree
    public List<? extends Tree> getAncestors() {
        if (getParent() == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Tree parent = getParent(); parent != null; parent = parent.getParent()) {
            arrayList.add(0, parent);
        }
        return arrayList;
    }

    @Override // org.antlr.runtime.tree.Tree
    public String toStringTree() {
        List<Object> list = this.children;
        if (list == null || list.isEmpty()) {
            return toString();
        }
        StringBuilder sb = new StringBuilder();
        if (!isNil()) {
            sb.append("(");
            sb.append(toString());
            sb.append(StringUtil.SPACE);
        }
        int i = 0;
        while (true) {
            List<Object> list2 = this.children;
            if (list2 == null || i >= list2.size()) {
                break;
            }
            Tree tree = (Tree) this.children.get(i);
            if (i > 0) {
                sb.append(StringUtil.SPACE);
            }
            sb.append(tree.toStringTree());
            i++;
        }
        if (!isNil()) {
            sb.append(Constant.AFTER_QUTO);
        }
        return sb.toString();
    }
}
