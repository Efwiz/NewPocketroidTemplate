package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: StubList.java */
/* JADX INFO: loaded from: classes3.dex */
final class MaterialStubList extends StubList {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ArrayList<StubBase<?>> myPlainList;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/MaterialStubList";
        } else {
            objArr[0] = "stub";
        }
        if (i == 1) {
            objArr[1] = "finalizeLoadingStage";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/MaterialStubList";
        } else {
            objArr[1] = "createOptimizedCopy";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "addStub";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    MaterialStubList(int i) {
        super(i);
        this.myPlainList = new ArrayList<>(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubList
    void addStub(StubBase<?> stubBase, StubBase<?> stubBase2, IStubElementType<?, ?> iStubElementType) {
        if (stubBase == null) {
            $$$reportNull$$$0(0);
        }
        super.addStub(stubBase, stubBase2, iStubElementType);
        this.myPlainList.add(stubBase);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubList
    StubList finalizeLoadingStage() {
        if (!isChildrenLayoutOptimal()) {
            return createOptimizedCopy();
        }
        this.myPlainList.trimToSize();
        StubList stubListFinalizeLoadingStage = super.finalizeLoadingStage();
        if (stubListFinalizeLoadingStage == null) {
            $$$reportNull$$$0(1);
        }
        return stubListFinalizeLoadingStage;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.com.intellij.psi.stubs.MaterialStubList$1] */
    private StubList createOptimizedCopy() {
        final MaterialStubList materialStubList = new MaterialStubList(size());
        new Object() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.MaterialStubList.1
            void visitStub(StubBase<?> stubBase, int i) {
                int size = materialStubList.size();
                materialStubList.addStub(size, i, MaterialStubList.this.getStubTypeIndex(stubBase.f717id));
                materialStubList.myPlainList.add(stubBase);
                List<StubBase<?>> childrenStubs = MaterialStubList.this.getChildrenStubs(stubBase.f717id);
                materialStubList.prepareForChildren(size, childrenStubs.size());
                Iterator<StubBase<?>> it = childrenStubs.iterator();
                while (it.hasNext()) {
                    visitStub(it.next(), size);
                }
            }
        }.visitStub(get(0), -1);
        for (int i = 0; i < materialStubList.size(); i++) {
            StubBase<?> stubBase = materialStubList.get(i);
            stubBase.myStubList = materialStubList;
            stubBase.f717id = i;
        }
        StubList stubListFinalizeLoadingStage = materialStubList.finalizeLoadingStage();
        if (stubListFinalizeLoadingStage == null) {
            $$$reportNull$$$0(2);
        }
        return stubListFinalizeLoadingStage;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.myPlainList.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public StubBase<?> get(int i) {
        return this.myPlainList.get(i);
    }
}
