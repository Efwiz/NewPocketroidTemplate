package org.jetbrains.kotlin.com.intellij.psi.stubs;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StubBase<T extends PsiElement> extends ObjectStubBase<StubElement<?>> implements StubElement<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final AtomicReferenceFieldUpdater<StubBase, PsiElement> myPsiUpdater = AtomicReferenceFieldUpdater.newUpdater(StubBase.class, PsiElement.class, "myPsi");
    private volatile T myPsi;
    StubList myStubList;

    /* JADX WARN: Code duplicated, block: B:36:0x0054  */
    /* JADX WARN: Code duplicated, block: B:38:0x005e  */
    /* JADX WARN: Code duplicated, block: B:40:0x0068  */
    /* JADX WARN: Code duplicated, block: B:57:0x0093  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 6 || i == 16 || i == 9 || i == 10 || i == 12 || i == 13) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 6 || i == 16 || i == 9 || i == 10 || i == 12 || i == 13) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "elementType";
        } else if (i == 2) {
            objArr[0] = "psi";
        } else if (i == 3) {
            objArr[0] = "elementType";
        } else if (i == 6) {
            objArr[0] = "filter";
        } else if (i == 16) {
            objArr[0] = "parentClass";
        } else if (i == 9) {
            objArr[0] = "elementType";
        } else if (i == 10) {
            objArr[0] = "f";
        } else if (i == 12) {
            objArr[0] = "filter";
        } else if (i != 13) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubBase";
        } else {
            objArr[0] = "f";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 9:
            case 10:
            case 12:
            case 13:
            case 16:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubBase";
                break;
            case 4:
            case 5:
            case 7:
            case 8:
            case 11:
            case 14:
            case 15:
                objArr[1] = "getChildrenByType";
                break;
            default:
                objArr[1] = "getChildrenStubs";
                break;
        }
        if (i == 1) {
            objArr[2] = "findChildStubByType";
        } else if (i == 2) {
            objArr[2] = "setPsi";
        } else if (i == 3 || i == 6) {
            objArr[2] = "getChildrenByType";
        } else if (i == 16) {
            objArr[2] = "getParentStubOfType";
        } else if (i == 9 || i == 10 || i == 12 || i == 13) {
            objArr[2] = "getChildrenByType";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 6 && i != 16 && i != 9 && i != 10 && i != 12 && i != 13) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    protected StubBase(StubElement stubElement, IStubElementType iStubElementType) {
        super(stubElement);
        StubList materialStubList = stubElement == null ? new MaterialStubList(10) : ((StubBase) stubElement).myStubList;
        this.myStubList = materialStubList;
        materialStubList.addStub((StubBase<?>) this, (StubBase<?>) stubElement, (IStubElementType<?, ?>) iStubElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubBase, org.jetbrains.kotlin.com.intellij.psi.stubs.Stub
    public StubElement getParentStub() {
        return (StubElement) this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.Stub, org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public List<StubElement> getChildrenStubs() {
        List<StubBase<?>> childrenStubs = this.myStubList.getChildrenStubs(this.f717id);
        if (childrenStubs == null) {
            $$$reportNull$$$0(0);
        }
        return childrenStubs;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public <P extends PsiElement, S extends StubElement<P>> S findChildStubByType(IStubElementType<S, P> iStubElementType) {
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
        return (S) this.myStubList.findChildStubByType(this.f717id, iStubElementType);
    }

    public void setPsi(T t) {
        if (t == null) {
            $$$reportNull$$$0(2);
        }
        this.myPsi = t;
    }

    public T getPsi() {
        T t = this.myPsi;
        if (t != null) {
            return t;
        }
        T t2 = (T) getStubType().createPsi(this);
        return AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(myPsiUpdater, this, null, t2) ? t2 : (T) Objects.requireNonNull(this.myPsi);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public <E extends PsiElement> E[] getChildrenByType(IElementType iElementType, E[] eArr) {
        if (iElementType == null) {
            $$$reportNull$$$0(3);
        }
        List<StubElement> childrenStubs = getChildrenStubs();
        int iCountChildren = countChildren(iElementType, childrenStubs);
        E[] eArr2 = (E[]) ((PsiElement[]) ArrayUtil.ensureExactSize(iCountChildren, eArr));
        if (iCountChildren == 0) {
            if (eArr2 == null) {
                $$$reportNull$$$0(4);
            }
            return eArr2;
        }
        fillFilteredChildren(iElementType, eArr2, childrenStubs);
        if (eArr2 == null) {
            $$$reportNull$$$0(5);
        }
        return eArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public <E extends PsiElement> E[] getChildrenByType(TokenSet tokenSet, E[] eArr) {
        if (tokenSet == null) {
            $$$reportNull$$$0(6);
        }
        List<StubElement> childrenStubs = getChildrenStubs();
        int iCountChildren = countChildren(tokenSet, childrenStubs);
        E[] eArr2 = (E[]) ((PsiElement[]) ArrayUtil.ensureExactSize(iCountChildren, eArr));
        if (iCountChildren == 0) {
            if (eArr2 == null) {
                $$$reportNull$$$0(7);
            }
            return eArr2;
        }
        fillFilteredChildren(tokenSet, eArr2, childrenStubs);
        if (eArr2 == null) {
            $$$reportNull$$$0(8);
        }
        return eArr2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public <E extends PsiElement> E[] getChildrenByType(IElementType iElementType, ArrayFactory<E> arrayFactory) {
        if (iElementType == null) {
            $$$reportNull$$$0(9);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(10);
        }
        List<StubElement> childrenStubs = getChildrenStubs();
        int iCountChildren = countChildren(iElementType, childrenStubs);
        E[] eArrCreate = arrayFactory.create(iCountChildren);
        if (iCountChildren > 0) {
            fillFilteredChildren(iElementType, eArrCreate, childrenStubs);
        }
        if (eArrCreate == null) {
            $$$reportNull$$$0(11);
        }
        return eArrCreate;
    }

    private static int countChildren(IElementType iElementType, List<? extends StubElement> list) {
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (list.get(i2).getStubType() == iElementType) {
                i++;
            }
        }
        return i;
    }

    private static int countChildren(TokenSet tokenSet, List<? extends StubElement> list) {
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (tokenSet.contains(list.get(i2).getStubType())) {
                i++;
            }
        }
        return i;
    }

    private static <E extends PsiElement> void fillFilteredChildren(IElementType iElementType, E[] eArr, List<? extends StubElement> list) {
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            StubElement stubElement = list.get(i2);
            if (stubElement.getStubType() == iElementType) {
                eArr[i] = stubElement.getPsi();
                i++;
            }
        }
    }

    private static <E extends PsiElement> void fillFilteredChildren(TokenSet tokenSet, E[] eArr, List<? extends StubElement> list) {
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            StubElement stubElement = list.get(i2);
            if (tokenSet.contains(stubElement.getStubType())) {
                eArr[i] = stubElement.getPsi();
                i++;
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public <E extends PsiElement> E[] getChildrenByType(TokenSet tokenSet, ArrayFactory<E> arrayFactory) {
        if (tokenSet == null) {
            $$$reportNull$$$0(12);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(13);
        }
        List<StubElement> childrenStubs = getChildrenStubs();
        int iCountChildren = countChildren(tokenSet, childrenStubs);
        E[] eArrCreate = arrayFactory.create(iCountChildren);
        if (iCountChildren == 0) {
            if (eArrCreate == null) {
                $$$reportNull$$$0(14);
            }
            return eArrCreate;
        }
        fillFilteredChildren(tokenSet, eArrCreate, childrenStubs);
        if (eArrCreate == null) {
            $$$reportNull$$$0(15);
        }
        return eArrCreate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public <E extends PsiElement> E getParentStubOfType(Class<E> cls) {
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        for (StubElement parentStub = (StubElement) this.myParent; parentStub != null; parentStub = parentStub.getParentStub()) {
            E e = (E) parentStub.getPsi();
            if (cls.isInstance(e)) {
                return e;
            }
        }
        return null;
    }

    public IStubElementType getStubType() {
        return this.myStubList.getStubType(this.f717id);
    }

    public Project getProject() {
        return getPsi().getProject();
    }

    public String printTree() {
        StringBuilder sb = new StringBuilder();
        printTree(sb, 0);
        return sb.toString();
    }

    private void printTree(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
        sb.append(this).append('\n');
        Iterator<StubElement> it = getChildrenStubs().iterator();
        while (it.hasNext()) {
            ((StubBase) it.next()).printTree(sb, i + 1);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return getClass().getSimpleName();
    }

    public int compareByOrderWith(ObjectStubBase<?> objectStubBase) {
        return Integer.compare(getStubId(), objectStubBase.getStubId());
    }
}
