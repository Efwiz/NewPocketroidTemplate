package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class StubTree extends ObjectStubTree<StubElement<?>> {
    private final StubSpine mySpine;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        if (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTree";
        } else {
            objArr[0] = "root";
        }
        if (i == 3) {
            objArr[1] = "enumerateStubs";
        } else if (i == 4 || i == 5) {
            objArr[1] = "getPlainListFromAllRoots";
        } else if (i == 6) {
            objArr[1] = "getRoot";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/StubTree";
        } else {
            objArr[1] = "getSpine";
        }
        switch (i) {
            case 2:
                objArr[2] = "enumerateStubs";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public StubTree(PsiFileStub psiFileStub) {
        this(psiFileStub, true);
        if (psiFileStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public StubTree(PsiFileStub psiFileStub, boolean z) {
        super((ObjectStubBase) psiFileStub, z);
        if (psiFileStub == 0) {
            $$$reportNull$$$0(1);
        }
        this.mySpine = new StubSpine(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubTree
    protected List<StubElement<?>> enumerateStubs(Stub stub) {
        if (stub == null) {
            $$$reportNull$$$0(2);
        }
        List<StubElement<?>> plainList = ((StubBase) stub).myStubList.finalizeLoadingStage().toPlainList();
        if (plainList == null) {
            $$$reportNull$$$0(3);
        }
        return plainList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubTree
    final List<StubElement<?>> getPlainListFromAllRoots() {
        PsiFileStub[] stubRoots = ((PsiFileStubImpl) getRoot()).getStubRoots();
        if (stubRoots.length == 1) {
            List<StubElement<?>> plainListFromAllRoots = super.getPlainListFromAllRoots();
            if (plainListFromAllRoots == null) {
                $$$reportNull$$$0(4);
            }
            return plainListFromAllRoots;
        }
        List<StubElement<?>> listConcat = ContainerUtil.concat(stubRoots, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((PsiFileStubImpl) ((PsiFileStub) obj)).myStubList.toPlainList();
            }
        });
        if (listConcat == null) {
            $$$reportNull$$$0(5);
        }
        return listConcat;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubTree
    public PsiFileStub getRoot() {
        PsiFileStub psiFileStub = (PsiFileStub) this.myRoot;
        if (psiFileStub == null) {
            $$$reportNull$$$0(6);
        }
        return psiFileStub;
    }

    public StubbedSpine getSpine() {
        StubSpine stubSpine = this.mySpine;
        if (stubSpine == null) {
            $$$reportNull$$$0(7);
        }
        return stubSpine;
    }
}
