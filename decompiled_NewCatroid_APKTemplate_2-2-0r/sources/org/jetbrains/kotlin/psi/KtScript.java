package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NameUtils;
import org.jetbrains.kotlin.psi.stubs.KotlinScriptStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtScript extends KtNamedDeclarationStub<KotlinScriptStub> implements KtDeclarationContainer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/psi/KtScript";
                break;
            case 6:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getFqName";
        } else if (i == 4) {
            objArr[1] = "getBlockExpression";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtScript";
        } else {
            objArr[1] = "getDeclarations";
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

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamed
    public /* bridge */ /* synthetic */ Name getNameAsName() {
        return super.getNameAsName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamedDeclaration
    public /* bridge */ /* synthetic */ Name getNameAsSafeName() {
        return super.getNameAsSafeName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public /* bridge */ /* synthetic */ PsiElement mo7392getNameIdentifier() {
        return super.mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ int getTextOffset() {
        return super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ SearchScope getUseScope() {
        return super.getUseScope();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ PsiElement setName(String str) throws IncorrectOperationException {
        return super.setName(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtScript(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtScript(KotlinScriptStub kotlinScriptStub) {
        super(kotlinScriptStub, KtStubElementTypes.SCRIPT);
        if (kotlinScriptStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName */
    public FqName mo7391getFqName() {
        KotlinScriptStub kotlinScriptStub = (KotlinScriptStub) getStub();
        if (kotlinScriptStub != null) {
            FqName fqNameMo7402getFqName = kotlinScriptStub.mo7402getFqName();
            if (fqNameMo7402getFqName == null) {
                $$$reportNull$$$0(2);
            }
            return fqNameMo7402getFqName;
        }
        KtFile containingKtFile = getContainingKtFile();
        FqName fqNameChild = containingKtFile.getPackageFqName().child(NameUtils.getScriptNameForFile(containingKtFile.getName()));
        if (fqNameChild == null) {
            $$$reportNull$$$0(3);
        }
        return fqNameChild;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return mo7391getFqName().shortName().asString();
    }

    public KtBlockExpression getBlockExpression() {
        KtBlockExpression ktBlockExpression = (KtBlockExpression) findNotNullChildByClass(KtBlockExpression.class);
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(4);
        }
        return ktBlockExpression;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
    public List<KtDeclaration> getDeclarations() {
        List<KtDeclaration> childrenOfTypeAsList = PsiTreeUtil.getChildrenOfTypeAsList(getBlockExpression(), KtDeclaration.class);
        if (childrenOfTypeAsList == null) {
            $$$reportNull$$$0(5);
        }
        return childrenOfTypeAsList;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(6);
        }
        return ktVisitor.visitScript(this, d);
    }
}
