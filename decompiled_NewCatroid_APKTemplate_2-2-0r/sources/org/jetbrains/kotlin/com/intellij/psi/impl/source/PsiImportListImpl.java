package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public class PsiImportListImpl extends JavaStubPsiElement<PsiImportListStub> implements PsiImportList {
    private volatile Map<String, PsiImportStatement> myClassNameToImportMap;
    private volatile Map<String, PsiImportStatementBase> myNameToSingleImportMap;
    private volatile Map<String, PsiImportStatement> myPackageNameToImportMap;
    private static final PsiImportStatementBase[] EMPTY_ARRAY = PsiImportStatementBase.EMPTY_ARRAY;
    private static final ArrayFactory<PsiImportStatementBase> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportListImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiImportListImpl.lambda$static$0(i);
        }
    };
    private static final TokenSet IMPORT_STATEMENT_BIT_SET = TokenSet.create(JavaElementType.IMPORT_STATEMENT);
    private static final TokenSet IMPORT_STATIC_STATEMENT_BIT_SET = TokenSet.create(JavaElementType.IMPORT_STATIC_STATEMENT);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 3 ? 2 : 3];
        if (i != 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportListImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getImportStaticStatements";
        } else if (i == 2) {
            objArr[1] = "getAllImportStatements";
        } else if (i != 3) {
            objArr[1] = "getImportStatements";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportListImpl";
        }
        if (i == 3) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalArgumentException(str2);
        }
    }

    static /* synthetic */ PsiImportStatementBase[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiImportStatementBase[i];
    }

    public PsiImportListImpl(PsiImportListStub psiImportListStub) {
        super(psiImportListStub, JavaStubElementTypes.IMPORT_LIST);
    }

    public PsiImportListImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    protected Object clone() {
        PsiImportListImpl psiImportListImpl = (PsiImportListImpl) super.clone();
        psiImportListImpl.myClassNameToImportMap = null;
        psiImportListImpl.myPackageNameToImportMap = null;
        psiImportListImpl.myNameToSingleImportMap = null;
        return psiImportListImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        this.myClassNameToImportMap = null;
        this.myPackageNameToImportMap = null;
        this.myNameToSingleImportMap = null;
        super.subtreeChanged();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public PsiImportStatement[] getImportStatements() {
        PsiImportStatement[] psiImportStatementArr = (PsiImportStatement[]) getStubOrPsiChildren(IMPORT_STATEMENT_BIT_SET, PsiImportStatementImpl.ARRAY_FACTORY);
        if (psiImportStatementArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiImportStatementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public PsiImportStaticStatement[] getImportStaticStatements() {
        PsiImportStaticStatement[] psiImportStaticStatementArr = (PsiImportStaticStatement[]) getStubOrPsiChildren(IMPORT_STATIC_STATEMENT_BIT_SET, PsiImportStaticStatementImpl.ARRAY_FACTORY);
        if (psiImportStaticStatementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiImportStaticStatementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public PsiImportStatementBase[] getAllImportStatements() {
        PsiImportStatementBase[] psiImportStatementBaseArr = (PsiImportStatementBase[]) getStubOrPsiChildren(ElementType.IMPORT_STATEMENT_BASE_BIT_SET, ARRAY_FACTORY);
        if (psiImportStatementBaseArr == null) {
            $$$reportNull$$$0(2);
        }
        return psiImportStatementBaseArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public PsiImportStatement findSingleClassImportStatement(String str) {
        while (true) {
            Map<String, PsiImportStatement> map = this.myClassNameToImportMap;
            if (map == null) {
                initializeMaps();
            } else {
                return map.get(str);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public PsiImportStatement findOnDemandImportStatement(String str) {
        while (true) {
            Map<String, PsiImportStatement> map = this.myPackageNameToImportMap;
            if (map == null) {
                initializeMaps();
            } else {
                return map.get(str);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public PsiImportStatementBase findSingleImportStatement(String str) {
        while (true) {
            Map<String, PsiImportStatementBase> map = this.myNameToSingleImportMap;
            if (map == null) {
                initializeMaps();
            } else {
                return map.get(str);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportList
    public boolean isReplaceEquivalent(PsiImportList psiImportList) {
        return getText().equals(psiImportList.getText());
    }

    private void initializeMaps() {
        String referenceName;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        for (PsiImportStatement psiImportStatement : getImportStatements()) {
            String qualifiedName = psiImportStatement.getQualifiedName();
            if (qualifiedName != null) {
                if (psiImportStatement.isOnDemand()) {
                    map2.put(qualifiedName, psiImportStatement);
                } else {
                    map.put(qualifiedName, psiImportStatement);
                    PsiJavaCodeReferenceElement importReference = psiImportStatement.getImportReference();
                    if (importReference != null) {
                        map3.put(importReference.getCustomReferenceName(), psiImportStatement);
                    }
                }
            }
        }
        for (PsiImportStaticStatement psiImportStaticStatement : getImportStaticStatements()) {
            if (!psiImportStaticStatement.isOnDemand() && (referenceName = psiImportStaticStatement.getReferenceName()) != null) {
                map3.put(referenceName, psiImportStaticStatement);
            }
        }
        this.myClassNameToImportMap = map;
        this.myPackageNameToImportMap = map2;
        this.myNameToSingleImportMap = map3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitImportList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiImportList";
    }
}
