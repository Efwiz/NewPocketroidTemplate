package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.Stub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SubstrateRef {
    private static final Logger LOG = Logger.getInstance((Class<?>) SubstrateRef.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "psi";
        } else {
            objArr[0] = "node";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SubstrateRef";
        if (i != 1) {
            objArr[2] = "createInvalidRef";
        } else {
            objArr[2] = "createAstStrongRef";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract PsiFile getContainingFile();

    public abstract ASTNode getNode();

    public Stub getStub() {
        return null;
    }

    public abstract boolean isValid();

    public Stub getGreenStub() {
        return getStub();
    }

    static SubstrateRef createInvalidRef(final StubBasedPsiElementBase<?> stubBasedPsiElementBase) {
        if (stubBasedPsiElementBase == null) {
            $$$reportNull$$$0(0);
        }
        return new SubstrateRef() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
            public boolean isValid() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
            public ASTNode getNode() {
                throw new PsiInvalidElementAccessException(stubBasedPsiElementBase);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
            public PsiFile getContainingFile() {
                throw new PsiInvalidElementAccessException(stubBasedPsiElementBase);
            }
        };
    }

    public static SubstrateRef createAstStrongRef(final ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        return new SubstrateRef() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SubstrateRef$2";
                if (i != 1) {
                    objArr[1] = "getNode";
                } else {
                    objArr[1] = "getContainingFile";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
            public ASTNode getNode() {
                ASTNode aSTNode2 = aSTNode;
                if (aSTNode2 == null) {
                    $$$reportNull$$$0(0);
                }
                return aSTNode2;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
            public boolean isValid() {
                FileASTNode fileASTNodeFindFileElement = SharedImplUtil.findFileElement(aSTNode);
                PsiElement psi = fileASTNodeFindFileElement == null ? null : fileASTNodeFindFileElement.getPsi();
                return psi != null && psi.isValid();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
            public PsiFile getContainingFile() {
                PsiFile containingFile = SharedImplUtil.getContainingFile(aSTNode);
                if (containingFile == null) {
                    throw PsiInvalidElementAccessException.createByNode(aSTNode, null);
                }
                if (containingFile == null) {
                    $$$reportNull$$$0(1);
                }
                return containingFile;
            }
        };
    }

    public static class StubRef extends SubstrateRef {
        private final StubElement<?> myStub;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
            if (i == 1 || i == 2 || i == 3) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SubstrateRef$StubRef";
            } else {
                objArr[0] = "stub";
            }
            if (i == 1) {
                objArr[1] = "getStub";
            } else if (i == 2 || i == 3) {
                objArr[1] = "getContainingFile";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/SubstrateRef$StubRef";
            }
            if (i != 1 && i != 2 && i != 3) {
                if (i != 4) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "reportError";
                }
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 3) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public StubRef(StubElement<?> stubElement) {
            if (stubElement == null) {
                $$$reportNull$$$0(0);
            }
            this.myStub = stubElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
        public ASTNode getNode() {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
        public Stub getStub() {
            StubElement<?> stubElement = this.myStub;
            if (stubElement == null) {
                $$$reportNull$$$0(1);
            }
            return stubElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
        public boolean isValid() {
            StubElement parentStub = this.myStub.getParentStub();
            if (parentStub == null) {
                SubstrateRef.LOG.error("No parent for stub " + this.myStub + " of class " + this.myStub.getClass());
                return false;
            }
            PsiElement psi = parentStub.getPsi();
            return psi != null && psi.isValid();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.SubstrateRef
        public PsiFile getContainingFile() {
            StubElement<?> parentStub = this.myStub;
            while (!(parentStub instanceof PsiFileStub)) {
                parentStub = parentStub.getParentStub();
            }
            PsiFile psiFile = (PsiFile) parentStub.getPsi();
            if (psiFile != null) {
                if (psiFile == null) {
                    $$$reportNull$$$0(2);
                }
                return psiFile;
            }
            PsiFile psiFileReportError = reportError(parentStub);
            if (psiFileReportError == null) {
                $$$reportNull$$$0(3);
            }
            return psiFileReportError;
        }

        private PsiFile reportError(StubElement<?> stubElement) {
            if (stubElement == null) {
                $$$reportNull$$$0(4);
            }
            ApplicationManager.getApplication().assertReadAccessAllowed();
            String invalidationReason = ((PsiFileStubImpl) stubElement).getInvalidationReason();
            PsiInvalidElementAccessException psiInvalidElementAccessException = new PsiInvalidElementAccessException(this.myStub.getPsi(), "no psi for file stub " + stubElement + ", invalidation reason=" + invalidationReason, null);
            if ("stub-psi mismatch".equals(invalidationReason)) {
                throw new ProcessCanceledException(psiInvalidElementAccessException);
            }
            throw psiInvalidElementAccessException;
        }
    }
}
