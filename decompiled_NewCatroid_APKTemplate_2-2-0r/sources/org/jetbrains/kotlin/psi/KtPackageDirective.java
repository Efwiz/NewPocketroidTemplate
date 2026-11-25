package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;

/* JADX INFO: loaded from: classes6.dex */
public class KtPackageDirective extends KtModifierListOwnerStub<KotlinPlaceHolderStub<KtPackageDirective>> {
    private String qualifiedNameCache;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 7 || i == 8) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/psi/KtPackageDirective";
                break;
            case 6:
                objArr[0] = "fqName";
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getPackageNames";
        } else if (i == 4) {
            objArr[1] = "getName";
        } else if (i == 5) {
            objArr[1] = "getNameAsName";
        } else if (i == 7) {
            objArr[1] = "getQualifiedName";
        } else if (i != 8) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtPackageDirective";
        } else {
            objArr[1] = "getQualifiedNameOf";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
                break;
            case 6:
                objArr[2] = "setFqName";
                break;
            case 9:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 7 && i != 8) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPackageDirective(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        this.qualifiedNameCache = null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPackageDirective(KotlinPlaceHolderStub<KtPackageDirective> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.PACKAGE_DIRECTIVE);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
        this.qualifiedNameCache = null;
    }

    public KtExpression getPackageNameExpression() {
        return (KtExpression) KtStubbedPsiUtil.getStubOrPsiChild(this, KtTokenSets.INSIDE_DIRECTIVE_EXPRESSIONS, KtExpression.ARRAY_FACTORY);
    }

    public List<KtSimpleNameExpression> getPackageNames() {
        KtExpression packageNameExpression = getPackageNameExpression();
        if (packageNameExpression == null) {
            List<KtSimpleNameExpression> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(2);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList();
        while (packageNameExpression instanceof KtQualifiedExpression) {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) packageNameExpression;
            KtExpression selectorExpression = ktQualifiedExpression.getSelectorExpression();
            if (selectorExpression instanceof KtSimpleNameExpression) {
                arrayList.add((KtSimpleNameExpression) selectorExpression);
            }
            packageNameExpression = ktQualifiedExpression.getReceiverExpression();
        }
        if (packageNameExpression instanceof KtSimpleNameExpression) {
            arrayList.add((KtSimpleNameExpression) packageNameExpression);
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public KtSimpleNameExpression getLastReferenceExpression() {
        KtExpression packageNameExpression = getPackageNameExpression();
        if (packageNameExpression == null) {
            return null;
        }
        return (KtSimpleNameExpression) KtPsiUtilKt.getQualifiedElementSelector(packageNameExpression);
    }

    public PsiElement getNameIdentifier() {
        KtSimpleNameExpression lastReferenceExpression = getLastReferenceExpression();
        if (lastReferenceExpression != null) {
            return lastReferenceExpression.getIdentifier();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        PsiElement nameIdentifier = getNameIdentifier();
        String text = nameIdentifier == null ? "" : nameIdentifier.getText();
        if (text == null) {
            $$$reportNull$$$0(4);
        }
        return text;
    }

    public Name getNameAsName() {
        PsiElement nameIdentifier = getNameIdentifier();
        Name nameIdentifier2 = nameIdentifier == null ? SpecialNames.ROOT_PACKAGE : Name.identifier(nameIdentifier.getText());
        if (nameIdentifier2 == null) {
            $$$reportNull$$$0(5);
        }
        return nameIdentifier2;
    }

    public boolean isRoot() {
        return getName().length() == 0;
    }

    public FqName getFqName() {
        String qualifiedName = getQualifiedName();
        return qualifiedName.isEmpty() ? FqName.ROOT : new FqName(qualifiedName);
    }

    public FqName getFqName(KtSimpleNameExpression ktSimpleNameExpression) {
        return new FqName(getQualifiedNameOf(ktSimpleNameExpression));
    }

    public void setFqName(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(6);
        }
        if (fqName.isRoot()) {
            if (getFqName().isRoot()) {
                return;
            }
            replace(new KtPsiFactory(getProject()).createFile("").getPackageDirective());
            return;
        }
        KtPsiFactory ktPsiFactory = new KtPsiFactory(getProject());
        KtExpression ktExpressionCreateExpression = ktPsiFactory.createExpression(fqName.asString());
        KtExpression packageNameExpression = getPackageNameExpression();
        if (packageNameExpression != null) {
            packageNameExpression.replace(ktExpressionCreateExpression);
            return;
        }
        PsiElement packageKeyword = getPackageKeyword();
        if (packageKeyword != null) {
            addAfter(ktExpressionCreateExpression, packageKeyword);
            addAfter(ktPsiFactory.createWhiteSpace(), packageKeyword);
        } else {
            replace(ktPsiFactory.createPackageDirective(fqName));
        }
    }

    public String getQualifiedName() {
        if (this.qualifiedNameCache == null) {
            this.qualifiedNameCache = getQualifiedNameOf(null);
        }
        String str = this.qualifiedNameCache;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    private String getQualifiedNameOf(KtSimpleNameExpression ktSimpleNameExpression) {
        StringBuilder sb = new StringBuilder();
        for (KtSimpleNameExpression ktSimpleNameExpression2 : getPackageNames()) {
            if (sb.length() > 0) {
                sb.append(".");
            }
            sb.append(ktSimpleNameExpression2.getReferencedName());
            if (ktSimpleNameExpression2 == ktSimpleNameExpression) {
                break;
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(8);
        }
        return string;
    }

    public PsiElement getPackageKeyword() {
        return findChildByType(KtTokens.PACKAGE_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        this.qualifiedNameCache = null;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(9);
        }
        return ktVisitor.visitPackageDirective(this, d);
    }
}
