package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaPackageAccessibilityStatementElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiPackageAccessibilityStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes3.dex */
public class PsiPackageAccessibilityStatementImpl extends JavaStubPsiElement<PsiPackageAccessibilityStatementStub> implements PsiPackageAccessibilityStatement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "node";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiPackageAccessibilityStatementImpl";
                break;
            case 6:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 2) {
            objArr[1] = "getRole";
        } else if (i == 3) {
            objArr[1] = "getModuleReferences";
        } else if (i == 4 || i == 5) {
            objArr[1] = "getModuleNames";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiPackageAccessibilityStatementImpl";
        }
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            if (i != 6) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPackageAccessibilityStatementImpl(PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub) {
        super(psiPackageAccessibilityStatementStub, psiPackageAccessibilityStatementStub.getStubType());
        if (psiPackageAccessibilityStatementStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiPackageAccessibilityStatementImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public PsiPackageAccessibilityStatement.Role getRole() {
        PsiPackageAccessibilityStatement.Role roleTypeToRole = JavaPackageAccessibilityStatementElementType.typeToRole(getElementType());
        if (roleTypeToRole == null) {
            $$$reportNull$$$0(2);
        }
        return roleTypeToRole;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public PsiJavaCodeReferenceElement getPackageReference() {
        return (PsiJavaCodeReferenceElement) PsiTreeUtil.getChildOfType(this, PsiJavaCodeReferenceElement.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public String getPackageName() {
        PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub = (PsiPackageAccessibilityStatementStub) getGreenStub();
        if (psiPackageAccessibilityStatementStub != null) {
            return StringUtil.nullize(psiPackageAccessibilityStatementStub.getPackageName());
        }
        PsiJavaCodeReferenceElement packageReference = getPackageReference();
        if (packageReference != null) {
            return PsiNameHelper.getQualifiedClassName(packageReference.getText(), true);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public Iterable<PsiJavaModuleReferenceElement> getModuleReferences() {
        Iterable iterableFilter = SyntaxTraverser.psiTraverser().children(this).filter(PsiJavaModuleReferenceElement.class);
        if (iterableFilter == null) {
            $$$reportNull$$$0(3);
        }
        return iterableFilter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement
    public List<String> getModuleNames() {
        PsiPackageAccessibilityStatementStub psiPackageAccessibilityStatementStub = (PsiPackageAccessibilityStatementStub) getGreenStub();
        if (psiPackageAccessibilityStatementStub != null) {
            List<String> targets = psiPackageAccessibilityStatementStub.getTargets();
            if (targets == null) {
                $$$reportNull$$$0(4);
            }
            return targets;
        }
        SmartList smartList = new SmartList();
        Iterator<PsiJavaModuleReferenceElement> it = getModuleReferences().iterator();
        while (it.hasNext()) {
            smartList.add(it.next().getReferenceText());
        }
        return smartList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitPackageAccessibilityStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiPackageAccessibilityStatement";
    }
}
