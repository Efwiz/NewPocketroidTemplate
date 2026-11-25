package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.util.objectTree.ThrowableInterner;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
final class ObjectTree {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ThreadLocal<Throwable> ourTopmostDisposeTrace = new ThreadLocal<>();
    private final Set<Disposable> myRootObjects = new ReferenceOpenHashSet();
    private final Map<Disposable, ObjectNode> myObject2NodeMap = new Reference2ObjectOpenHashMap();
    private final Map<Disposable, Object> myDisposedObjects = CollectionFactory.createWeakIdentityMap(100, 0.5f);
    private final Object treeLock = new Object();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 8 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 8 || i == 14) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "parent";
                break;
            case 3:
                objArr[0] = "child";
                break;
            case 4:
            case 7:
            case 10:
            case 11:
            case 15:
            case 17:
            default:
                objArr[0] = "object";
                break;
            case 5:
                objArr[0] = "childNode";
                break;
            case 6:
                objArr[0] = "parentNode";
                break;
            case 8:
            case 14:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ObjectTree";
                break;
            case 9:
                objArr[0] = "removeFromTreeAction";
                break;
            case 12:
                objArr[0] = "exceptions";
                break;
            case 13:
                objArr[0] = "disposable";
                break;
            case 16:
                objArr[0] = "parentDisposable";
                break;
            case 18:
                objArr[0] = "node";
                break;
        }
        if (i == 8) {
            objArr[1] = "createNodeFor";
        } else if (i != 14) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ObjectTree";
        } else {
            objArr[1] = "getLogger";
        }
        switch (i) {
            case 1:
                objArr[2] = "putNode";
                break;
            case 2:
            case 3:
                objArr[2] = "register";
                break;
            case 4:
                objArr[2] = "getDisposalInfo";
                break;
            case 5:
            case 6:
                objArr[2] = "checkWasNotAddedAlreadyAsChild";
                break;
            case 7:
                objArr[2] = "createNodeFor";
                break;
            case 8:
            case 14:
                break;
            case 9:
                objArr[2] = "runWithTrace";
                break;
            case 10:
                objArr[2] = "executeAllChildren";
                break;
            case 11:
                objArr[2] = "executeAll";
                break;
            case 12:
                objArr[2] = "handleExceptions";
                break;
            case 13:
                objArr[2] = "assertNoReferenceKeptInTree";
                break;
            case 15:
                objArr[2] = "rememberDisposedTrace";
                break;
            case 16:
            case 17:
                objArr[2] = "findRegisteredObject";
                break;
            case 18:
                objArr[2] = "removeObjectFromTree";
                break;
            default:
                objArr[2] = "getNode";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 8 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    ObjectTree() {
    }

    private ObjectNode getNode(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
        return this.myObject2NodeMap.get(disposable);
    }

    private void putNode(Disposable disposable, ObjectNode objectNode) {
        if (disposable == null) {
            $$$reportNull$$$0(1);
        }
        if (objectNode == null) {
            this.myObject2NodeMap.remove(disposable);
        } else {
            this.myObject2NodeMap.put(disposable, objectNode);
        }
    }

    RuntimeException register(Disposable disposable, Disposable disposable2) {
        if (disposable == null) {
            $$$reportNull$$$0(2);
        }
        if (disposable2 == null) {
            $$$reportNull$$$0(3);
        }
        if (disposable == disposable2) {
            return new IllegalArgumentException("Cannot register to itself: " + disposable);
        }
        synchronized (this.treeLock) {
            Object disposalInfo = getDisposalInfo(disposable);
            if (disposalInfo != null) {
                return new IncorrectOperationException("Sorry but parent: " + disposable + " (" + disposable.getClass() + ") has already been disposed (see the cause for stacktrace) so the child: " + disposable2 + " (" + disposable2.getClass() + ") will never be disposed", disposalInfo instanceof Throwable ? (Throwable) disposalInfo : null);
            }
            this.myDisposedObjects.remove(disposable2);
            if (disposable2 instanceof Disposer.CheckedDisposableImpl) {
                ((Disposer.CheckedDisposableImpl) disposable2).isDisposed = false;
            }
            ObjectNode node = getNode(disposable);
            if (node == null) {
                node = createNodeFor(disposable, null);
            }
            ObjectNode node2 = getNode(disposable2);
            if (node2 == null) {
                node2 = createNodeFor(disposable2, node);
            } else {
                ObjectNode parent = node2.getParent();
                if (parent != null) {
                    parent.removeChild(node2);
                }
            }
            this.myRootObjects.remove(disposable2);
            RuntimeException runtimeExceptionCheckWasNotAddedAlreadyAsChild = checkWasNotAddedAlreadyAsChild(node, node2);
            if (runtimeExceptionCheckWasNotAddedAlreadyAsChild != null) {
                return runtimeExceptionCheckWasNotAddedAlreadyAsChild;
            }
            node.addChild(node2);
            return null;
        }
    }

    Object getDisposalInfo(Disposable disposable) {
        Object obj;
        if (disposable == null) {
            $$$reportNull$$$0(4);
        }
        synchronized (this.treeLock) {
            obj = this.myDisposedObjects.get(disposable);
        }
        return obj;
    }

    private static RuntimeException checkWasNotAddedAlreadyAsChild(ObjectNode objectNode, ObjectNode objectNode2) {
        if (objectNode == null) {
            $$$reportNull$$$0(5);
        }
        if (objectNode2 == null) {
            $$$reportNull$$$0(6);
        }
        for (ObjectNode parent = objectNode; parent != null; parent = parent.getParent()) {
            if (parent == objectNode2) {
                return new IncorrectOperationException(FormatHelper.QUOTE + objectNode.getObject() + "' was already added as a child of '" + objectNode2.getObject() + FormatHelper.QUOTE);
            }
        }
        return null;
    }

    private ObjectNode createNodeFor(Disposable disposable, ObjectNode objectNode) {
        if (disposable == null) {
            $$$reportNull$$$0(7);
        }
        ObjectNode objectNode2 = new ObjectNode(this, objectNode, disposable);
        if (objectNode == null) {
            this.myRootObjects.add(disposable);
        }
        putNode(disposable, objectNode2);
        return objectNode2;
    }

    private void runWithTrace(Supplier<? extends List<Disposable>> supplier) {
        List<Disposable> list;
        if (supplier == null) {
            $$$reportNull$$$0(9);
        }
        boolean z = Disposer.isDebugMode() && ourTopmostDisposeTrace.get() == null;
        if (z) {
            ourTopmostDisposeTrace.set(ThrowableInterner.intern(new Throwable()));
        }
        synchronized (this.treeLock) {
            list = supplier.get();
        }
        SmartList smartList = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            Disposable disposable = list.get(size);
            if (disposable instanceof Disposable.Parent) {
                try {
                    ((Disposable.Parent) disposable).beforeTreeDispose();
                } catch (Throwable th) {
                    if (smartList == null) {
                        smartList = new SmartList();
                    }
                    smartList.add(th);
                }
            }
        }
        Iterator<Disposable> it = list.iterator();
        while (it.hasNext()) {
            try {
                it.next().dispose();
            } catch (Throwable th2) {
                if (smartList == null) {
                    smartList = new SmartList();
                }
                smartList.add(th2);
            }
        }
        if (z) {
            ourTopmostDisposeTrace.remove();
        }
        if (smartList != null) {
            handleExceptions(smartList);
        }
    }

    void executeAll(final Disposable disposable, final boolean z) {
        if (disposable == null) {
            $$$reportNull$$$0(11);
        }
        runWithTrace(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.util.ObjectTree$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6503xdf2274ff(disposable, z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$executeAll$1$org-jetbrains-kotlin-com-intellij-openapi-util-ObjectTree, reason: not valid java name */
    /* synthetic */ List m6503xdf2274ff(Disposable disposable, boolean z) {
        ObjectNode node = getNode(disposable);
        if (node == null && !z) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        if (node == null) {
            if (rememberDisposedTrace(disposable) == null) {
                arrayList.add(disposable);
            }
        } else {
            node.getAndRemoveRecursively(arrayList);
        }
        return arrayList;
    }

    private static void handleExceptions(List<? extends Throwable> list) {
        if (list == null) {
            $$$reportNull$$$0(12);
        }
        if (list.isEmpty()) {
            return;
        }
        ProcessCanceledException processCanceledException = null;
        for (Throwable th : list) {
            if (!(th instanceof ProcessCanceledException)) {
                getLogger().error(th);
            } else if (processCanceledException == null) {
                processCanceledException = (ProcessCanceledException) th;
            }
        }
        if (processCanceledException != null) {
            throw processCanceledException;
        }
    }

    private static Logger getLogger() {
        Logger logger = Logger.getInstance((Class<?>) ObjectTree.class);
        if (logger == null) {
            $$$reportNull$$$0(14);
        }
        return logger;
    }

    Object rememberDisposedTrace(Disposable disposable) {
        Object objPut;
        if (disposable == null) {
            $$$reportNull$$$0(15);
        }
        synchronized (this.treeLock) {
            Throwable th = ourTopmostDisposeTrace.get();
            Map<Disposable, Object> map = this.myDisposedObjects;
            if (th == null) {
                th = Boolean.TRUE;
            }
            objPut = map.put(disposable, th);
        }
        return objPut;
    }

    void removeObjectFromTree(ObjectNode objectNode) {
        if (objectNode == null) {
            $$$reportNull$$$0(18);
        }
        synchronized (this.treeLock) {
            Disposable object = objectNode.getObject();
            putNode(object, null);
            ObjectNode parent = objectNode.getParent();
            if (parent == null) {
                this.myRootObjects.remove(object);
            } else {
                parent.removeChild(objectNode);
            }
            objectNode.myParent = null;
        }
    }
}
