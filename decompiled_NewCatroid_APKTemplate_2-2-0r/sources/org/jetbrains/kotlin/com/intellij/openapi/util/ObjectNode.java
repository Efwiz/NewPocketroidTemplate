package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.List;
import java.util.function.Predicate;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.objectTree.ThrowableInterner;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
final class ObjectNode {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private List<ObjectNode> myChildren;
    private final Disposable myObject;
    ObjectNode myParent;
    private Throwable myTrace;
    private final ObjectTree myTree;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 6 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 6 ? 3 : 2];
        switch (i) {
            case 1:
            case 8:
                objArr[0] = "object";
                break;
            case 2:
            case 3:
                objArr[0] = "child";
                break;
            case 4:
            case 5:
                objArr[0] = "result";
                break;
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ObjectNode";
                break;
            case 7:
                objArr[0] = "aDisposable";
                break;
            default:
                objArr[0] = Constants.TYPE_TREE;
                break;
        }
        if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ObjectNode";
        } else {
            objArr[1] = "getObject";
        }
        switch (i) {
            case 2:
                objArr[2] = "addChild";
                break;
            case 3:
                objArr[2] = "removeChild";
                break;
            case 4:
                objArr[2] = "getAndRemoveRecursively";
                break;
            case 5:
                objArr[2] = "getAndRemoveChildrenRecursively";
                break;
            case 6:
                break;
            case 7:
                objArr[2] = "assertNoReferencesKept";
                break;
            case 8:
                objArr[2] = "findChildEqualTo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 6) {
            throw new IllegalStateException(str2);
        }
    }

    ObjectNode(ObjectTree objectTree, ObjectNode objectNode, Disposable disposable) {
        if (objectTree == null) {
            $$$reportNull$$$0(0);
        }
        if (disposable == null) {
            $$$reportNull$$$0(1);
        }
        this.myTree = objectTree;
        this.myParent = objectNode;
        this.myObject = disposable;
        this.myTrace = (objectNode == null && Disposer.isDebugMode()) ? ThrowableInterner.intern(new Throwable()) : null;
    }

    void addChild(ObjectNode objectNode) {
        if (objectNode == null) {
            $$$reportNull$$$0(2);
        }
        List<ObjectNode> list = this.myChildren;
        if (list == null) {
            this.myChildren = new SmartList(objectNode);
        } else {
            list.add(objectNode);
        }
        objectNode.myParent = this;
    }

    void removeChild(ObjectNode objectNode) {
        if (objectNode == null) {
            $$$reportNull$$$0(3);
        }
        List<ObjectNode> list = this.myChildren;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                if (list.get(size).equals(objectNode)) {
                    list.remove(size);
                    break;
                }
            }
        }
        objectNode.myParent = null;
    }

    ObjectNode getParent() {
        return this.myParent;
    }

    void getAndRemoveRecursively(List<? super Disposable> list) {
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        getAndRemoveChildrenRecursively(list, null);
        this.myTree.removeObjectFromTree(this);
        if (this.myTree.rememberDisposedTrace(this.myObject) == null) {
            list.add(this.myObject);
        }
        this.myChildren = null;
        this.myParent = null;
    }

    void getAndRemoveChildrenRecursively(List<? super Disposable> list, Predicate<? super Disposable> predicate) {
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        List<ObjectNode> list2 = this.myChildren;
        if (list2 != null) {
            for (int size = list2.size() - 1; size >= 0; size--) {
                ObjectNode objectNode = this.myChildren.get(size);
                if (predicate == null || predicate.test(objectNode.getObject())) {
                    objectNode.getAndRemoveRecursively(list);
                }
            }
        }
    }

    Disposable getObject() {
        Disposable disposable = this.myObject;
        if (disposable == null) {
            $$$reportNull$$$0(6);
        }
        return disposable;
    }

    public String toString() {
        return "Node: " + this.myObject;
    }
}
