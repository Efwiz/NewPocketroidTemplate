package org.jetbrains.kotlin.com.intellij.psi.stubs;

import java.util.Arrays;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileWithStubSupport;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class PsiFileStubImpl<T extends PsiFile> extends StubBase<T> implements PsiFileStub<T> {
    public static final IStubFileElementType TYPE = new IStubFileElementType(Language.ANY);
    private volatile T myFile;
    private volatile String myInvalidationReason;
    private volatile PsiFileStub[] myStubRoots;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "reason";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/stubs/PsiFileStubImpl";
                break;
            case 7:
                objArr[0] = PsiTreeChangeEvent.PROP_ROOTS;
                break;
            default:
                objArr[0] = "psi";
                break;
        }
        if (i == 2) {
            objArr[1] = "getType";
        } else if (i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[1] = "getStubRoots";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/PsiFileStubImpl";
        }
        switch (i) {
            case 1:
                objArr[2] = "clearPsi";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            case 7:
                objArr[2] = "setStubRoots";
                break;
            default:
                objArr[2] = "setPsi";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, org.jetbrains.kotlin.com.intellij.psi.stubs.Stub, org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public IStubElementType getStubType() {
        return null;
    }

    public PsiFileStubImpl(T t) {
        super(null, null);
        this.myFile = t;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement
    public T getPsi() {
        return this.myFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase
    public void setPsi(T t) {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        this.myFile = t;
    }

    public void clearPsi(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myInvalidationReason = str;
        this.myFile = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub
    public String getInvalidationReason() {
        return this.myInvalidationReason;
    }

    public IStubFileElementType getType() {
        IStubFileElementType iStubFileElementType = TYPE;
        if (iStubFileElementType == null) {
            $$$reportNull$$$0(2);
        }
        return iStubFileElementType;
    }

    public PsiFileStub[] getStubRoots() {
        StubTree orCalcStubTree;
        if (this.myStubRoots != null) {
            PsiFileStub[] psiFileStubArr = this.myStubRoots;
            if (psiFileStubArr == null) {
                $$$reportNull$$$0(3);
            }
            return psiFileStubArr;
        }
        PsiFile psi = getPsi();
        if (psi == null) {
            return new PsiFileStub[]{this};
        }
        FileViewProvider viewProvider = psi.getViewProvider();
        PsiFile stubBindingRoot = viewProvider.getStubBindingRoot();
        StubTree orCalcStubTree2 = getOrCalcStubTree(stubBindingRoot);
        if (orCalcStubTree2 != null) {
            SmartList smartList = new SmartList(orCalcStubTree2.getRoot());
            for (Pair<IStubFileElementType, PsiFile> pair : StubTreeBuilder.getStubbedRoots(viewProvider)) {
                if (pair.second != stubBindingRoot && (orCalcStubTree = getOrCalcStubTree(pair.second)) != null) {
                    smartList.add(orCalcStubTree.getRoot());
                }
            }
            PsiFileStub[] psiFileStubArr2 = (PsiFileStub[]) smartList.toArray(PsiFileStub.EMPTY_ARRAY);
            for (PsiFileStub psiFileStub : psiFileStubArr2) {
                if (psiFileStub instanceof PsiFileStubImpl) {
                    ((PsiFileStubImpl) psiFileStub).setStubRoots(psiFileStubArr2);
                }
            }
            this.myStubRoots = psiFileStubArr2;
            if (psiFileStubArr2 == null) {
                $$$reportNull$$$0(5);
            }
            return psiFileStubArr2;
        }
        PsiFileStub[] psiFileStubArr3 = PsiFileStub.EMPTY_ARRAY;
        if (psiFileStubArr3 == null) {
            $$$reportNull$$$0(6);
        }
        return psiFileStubArr3;
    }

    private static StubTree getOrCalcStubTree(PsiFile psiFile) {
        if (!(psiFile instanceof PsiFileWithStubSupport)) {
            return null;
        }
        StubTree stubTree = ((PsiFileWithStubSupport) psiFile).getStubTree();
        return (stubTree == null && (psiFile instanceof PsiFileImpl)) ? ((PsiFileImpl) psiFile).calcStubTree() : stubTree;
    }

    public void setStubRoots(PsiFileStub[] psiFileStubArr) {
        if (psiFileStubArr == null) {
            $$$reportNull$$$0(7);
        }
        if (psiFileStubArr.length == 0) {
            Logger.getInstance(getClass()).error("Incorrect psi file stub roots count" + this + "," + getStubType());
        }
        this.myStubRoots = psiFileStubArr;
    }

    public boolean rootsAreSet() {
        return this.myStubRoots != null;
    }

    public final String getDiagnostics() {
        return this + "(file='" + this.myFile + "', invalidationReason=" + this.myInvalidationReason + ", stubRoots='" + Arrays.toString(this.myStubRoots) + "', stubTree='" + ObjectStubTree.getStubTree(this) + "')";
    }
}
