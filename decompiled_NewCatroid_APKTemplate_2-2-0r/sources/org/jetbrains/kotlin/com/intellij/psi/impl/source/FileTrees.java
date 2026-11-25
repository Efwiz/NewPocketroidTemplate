package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTree;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;

/* JADX INFO: loaded from: classes3.dex */
final class FileTrees {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) FileTrees.class);
    private final PsiFileImpl myFile;
    private final Reference<StubBasedPsiElementBase<?>>[] myRefToPsi;
    private final Reference<StubTree> myStub;
    private final Supplier<FileElement> myTreeElementPointer;

    /* JADX WARN: Code duplicated, block: B:17:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "spine";
        } else if (i == 2) {
            objArr[0] = "reason";
        } else if (i == 3) {
            objArr[0] = "ast";
        } else if (i == 4) {
            objArr[0] = "stub";
        } else if (i == 6) {
            objArr[0] = "spine";
        } else if (i != 7) {
            objArr[0] = "file";
        } else {
            objArr[0] = "srcSpine";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/FileTrees";
        switch (i) {
            case 1:
                objArr[2] = "switchToSpineRefs";
                break;
            case 2:
                objArr[2] = "clearStub";
                break;
            case 3:
                objArr[2] = "withAst";
                break;
            case 4:
                objArr[2] = "withStub";
                break;
            case 5:
                objArr[2] = "noStub";
                break;
            case 6:
                objArr[2] = "getAllSpinePsi";
                break;
            case 7:
                objArr[2] = "bindStubsWithAst";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ FileElement lambda$noStub$3(FileElement fileElement) {
        return fileElement;
    }

    private FileTrees(PsiFileImpl psiFileImpl, Reference<StubTree> reference, Supplier<FileElement> supplier, Reference<StubBasedPsiElementBase<?>>[] referenceArr) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(0);
        }
        this.myFile = psiFileImpl;
        this.myStub = reference;
        this.myTreeElementPointer = supplier;
        this.myRefToPsi = referenceArr;
    }

    StubTree derefStub() {
        return (StubTree) SoftReference.dereference(this.myStub);
    }

    FileElement derefTreeElement() {
        return (FileElement) SoftReference.deref(this.myTreeElementPointer);
    }

    FileTrees switchToStrongRefs() {
        if (this.myRefToPsi == null) {
            return this;
        }
        forEachCachedPsi(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileTrees$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FileTrees.lambda$switchToStrongRefs$0((StubBasedPsiElementBase) obj);
            }
        });
        return new FileTrees(this.myFile, this.myStub, this.myTreeElementPointer, null);
    }

    static /* synthetic */ void lambda$switchToStrongRefs$0(StubBasedPsiElementBase stubBasedPsiElementBase) {
        ASTNode node = stubBasedPsiElementBase.getNode();
        LOG.assertTrue(node.getPsi() == stubBasedPsiElementBase);
        stubBasedPsiElementBase.setSubstrateRef(SubstrateRef.createAstStrongRef(node));
    }

    private void forEachCachedPsi(Consumer<? super StubBasedPsiElementBase<?>> consumer) {
        Reference<StubBasedPsiElementBase<?>>[] referenceArr = this.myRefToPsi;
        int length = referenceArr.length;
        for (int i = 0; i < length; i++) {
            Reference<StubBasedPsiElementBase<?>> reference = referenceArr[i];
            StubBasedPsiElementBase<?> stubBasedPsiElementBase = reference == null ? null : reference.get();
            if (stubBasedPsiElementBase != null) {
                consumer.accept(stubBasedPsiElementBase);
            }
        }
    }

    private boolean hasCachedPsi() {
        Reference<StubBasedPsiElementBase<?>>[] referenceArr = this.myRefToPsi;
        if (referenceArr != null) {
            for (Reference<StubBasedPsiElementBase<?>> reference : referenceArr) {
                if (reference != null && reference.get() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean useSpineRefs() {
        return this.myRefToPsi != null;
    }

    FileTrees switchToSpineRefs(List<PsiElement> list) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        Reference<StubBasedPsiElementBase<?>>[] referenceArr = this.myRefToPsi;
        if (referenceArr == null) {
            referenceArr = new Reference[list.size()];
        }
        for (int i = 1; i < referenceArr.length; i++) {
            try {
                StubBasedPsiElementBase stubBasedPsiElementBase = (StubBasedPsiElementBase) Objects.requireNonNull(list.get(i));
                stubBasedPsiElementBase.setSubstrateRef(new SpineRef(this.myFile, i));
                if (((StubBasedPsiElementBase) SoftReference.dereference(referenceArr[i])) == null) {
                    referenceArr[i] = new WeakReference(stubBasedPsiElementBase);
                }
            } catch (Throwable th) {
                throw new RuntimeException("Exceptions aren't allowed here", th);
            }
        }
        return new FileTrees(this.myFile, this.myStub, this.myTreeElementPointer, referenceArr);
    }

    FileTrees clearStub(String str) throws Throwable {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        StubTree stubTreeDerefStub = derefStub();
        if (stubTreeDerefStub != null) {
            ((PsiFileStubImpl) stubTreeDerefStub.getRoot()).clearPsi(str);
        }
        if (this.myRefToPsi != null) {
            DebugUtil.performPsiModification("clearStub", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileTrees$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    this.f$0.m6644x45122e37();
                }
            });
        }
        return new FileTrees(this.myFile, null, this.myTreeElementPointer, null);
    }

    /* JADX INFO: renamed from: lambda$clearStub$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-FileTrees, reason: not valid java name */
    /* synthetic */ void m6644x45122e37() throws RuntimeException {
        forEachCachedPsi(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileTrees$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FileTrees.lambda$clearStub$1((StubBasedPsiElementBase) obj);
            }
        });
    }

    static /* synthetic */ void lambda$clearStub$1(StubBasedPsiElementBase stubBasedPsiElementBase) {
        DebugUtil.onInvalidated(stubBasedPsiElementBase);
        stubBasedPsiElementBase.setSubstrateRef(SubstrateRef.createInvalidRef(stubBasedPsiElementBase));
    }

    FileTrees withAst(Supplier<FileElement> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(3);
        }
        return new FileTrees(this.myFile, this.myStub, supplier, this.myRefToPsi).reconcilePsi(derefStub(), supplier.get(), true);
    }

    FileTrees withStub(StubTree stubTree, FileElement fileElement) {
        if (stubTree == null) {
            $$$reportNull$$$0(4);
        }
        return new FileTrees(this.myFile, new SoftReference(stubTree), this.myTreeElementPointer, this.myRefToPsi).reconcilePsi(stubTree, fileElement, false);
    }

    static FileTrees noStub(final FileElement fileElement, PsiFileImpl psiFileImpl) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(5);
        }
        return new FileTrees(psiFileImpl, null, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileTrees$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return FileTrees.lambda$noStub$3(fileElement);
            }
        }, null);
    }

    private FileTrees reconcilePsi(StubTree stubTree, FileElement fileElement, final boolean z) throws Throwable {
        List<PsiElement> allSpinePsi = null;
        if ((stubTree == null || fileElement == null) && !hasCachedPsi()) {
            return new FileTrees(this.myFile, this.myStub, this.myTreeElementPointer, null);
        }
        final List<StubElement<?>> plainList = stubTree == null ? null : stubTree.getPlainList();
        final List<CompositeElement> spineNodes = fileElement == null ? null : fileElement.getStubbedSpine().getSpineNodes();
        if (plainList != null && spineNodes != null) {
            allSpinePsi = getAllSpinePsi(z ? stubTree.getSpine() : fileElement.getStubbedSpine());
        }
        final List<PsiElement> list = allSpinePsi;
        try {
            return (FileTrees) DebugUtil.performPsiModification("reconcilePsi", new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileTrees$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                public final Object compute() {
                    return this.f$0.m6645xc34f8350(plainList, spineNodes, list, z);
                }
            });
        } catch (Throwable th) {
            this.myFile.clearContent("stub-psi mismatch");
            this.myFile.rebuildStub();
            throw StubTreeLoader.getInstance().stubTreeAndIndexDoNotMatch(stubTree, this.myFile, th);
        }
    }

    /* JADX INFO: renamed from: lambda$reconcilePsi$4$org-jetbrains-kotlin-com-intellij-psi-impl-source-FileTrees, reason: not valid java name */
    /* synthetic */ FileTrees m6645xc34f8350(List list, List list2, List list3, boolean z) throws RuntimeException {
        if (this.myRefToPsi != null) {
            bindSubstratesToCachedPsi(list, list2);
        }
        if (list == null || list2 == null) {
            return this;
        }
        FileTrees fileTreesSwitchToSpineRefs = switchToSpineRefs(list3);
        bindStubsWithAst(list3, list, list2, z);
        return fileTreesSwitchToSpineRefs;
    }

    static List<PsiElement> getAllSpinePsi(final StubbedSpine stubbedSpine) {
        if (stubbedSpine == null) {
            $$$reportNull$$$0(6);
        }
        IntStream intStreamRange = IntStream.range(0, stubbedSpine.getStubCount());
        Objects.requireNonNull(stubbedSpine);
        return (List) intStreamRange.mapToObj(new IntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.FileTrees$$ExternalSyntheticLambda3
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return stubbedSpine.getStubPsi(i);
            }
        }).collect(Collectors.toList());
    }

    private void bindSubstratesToCachedPsi(List<StubElement<?>> list, List<? extends CompositeElement> list2) {
        int i = 1;
        while (true) {
            Reference<StubBasedPsiElementBase<?>>[] referenceArr = this.myRefToPsi;
            if (i >= referenceArr.length) {
                return;
            }
            StubBasedPsiElementBase stubBasedPsiElementBase = (StubBasedPsiElementBase) SoftReference.dereference(referenceArr[i]);
            if (stubBasedPsiElementBase != null) {
                if (list != null) {
                    ((StubBase) list.get(i)).setPsi(stubBasedPsiElementBase);
                }
                if (list2 != null) {
                    list2.get(i).setPsi(stubBasedPsiElementBase);
                }
            }
            i++;
        }
    }

    private static void bindStubsWithAst(List<? extends PsiElement> list, List<? extends StubElement<?>> list2, List<? extends CompositeElement> list3, boolean z) {
        if (list == null) {
            $$$reportNull$$$0(7);
        }
        for (int i = 1; i < list2.size(); i++) {
            StubElement<?> stubElement = list2.get(i);
            CompositeElement compositeElement = list3.get(i);
            PsiElement psiElement = (PsiElement) Objects.requireNonNull(list.get(i));
            if (z) {
                compositeElement.setPsi(psiElement);
            } else {
                ((StubBase) stubElement).setPsi(psiElement);
            }
        }
    }

    public String toString() {
        return "FileTrees{stub=" + (this.myStub == null ? "noRef" : derefStub()) + ", AST=" + (this.myTreeElementPointer != null ? derefTreeElement() : "noRef") + ", useSpineRefs=" + useSpineRefs() + '}';
    }
}
