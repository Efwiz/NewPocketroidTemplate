package org.jetbrains.kotlin.psi;

import java.util.Set;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.PackageScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes6.dex */
abstract class KtNamedDeclarationStub<T extends KotlinStubWithFqName<?>> extends KtDeclarationStub<T> implements KtNamedDeclaration {
    private static final Set<String> FUNCTIONLIKE_CONVENTIONS = ImmutableSet.of(OperatorNameConventions.INVOKE.asString(), OperatorNameConventions.GET.asString());

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtNamedDeclarationStub";
                break;
            case 4:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 3) {
            objArr[1] = "getNameAsSafeName";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[1] = "getUseScope";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtNamedDeclarationStub";
        }
        if (i != 3) {
            if (i == 4) {
                objArr[2] = "setName";
            } else if (i != 5 && i != 6 && i != 7) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNamedDeclarationStub(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNamedDeclarationStub(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        String text;
        T stub = getStub();
        if (stub != null) {
            return stub.getName();
        }
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiElementMo7392getNameIdentifier == null || (text = psiElementMo7392getNameIdentifier.getText()) == null) {
            return null;
        }
        return KtPsiUtil.unquoteIdentifier(text);
    }

    @Override // org.jetbrains.kotlin.psi.KtNamed
    public Name getNameAsName() {
        String name2 = get$name();
        if (name2 != null) {
            return Name.identifier(name2);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclaration
    public Name getNameAsSafeName() {
        Name nameSafeName = KtPsiUtil.safeName(get$name());
        if (nameSafeName == null) {
            $$$reportNull$$$0(3);
        }
        return nameSafeName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiElement mo7392getNameIdentifier() {
        return findChildByType(KtTokens.IDENTIFIER);
    }

    private static boolean shouldDropOperatorKeyword(String str, String str2) {
        if (OperatorConventions.isConventionName(Name.identifier(str2))) {
            Set<String> set = FUNCTIONLIKE_CONVENTIONS;
            if (set.contains(str) == set.contains(str2)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiElementMo7392getNameIdentifier == null) {
            return null;
        }
        KtModifierList modifierList = getModifierList();
        if (modifierList != null && modifierList.hasModifier(KtTokens.OPERATOR_KEYWORD) && shouldDropOperatorKeyword(get$name(), str)) {
            removeModifier(KtTokens.OPERATOR_KEYWORD);
        }
        PsiElement psiElementCreateNameIdentifierIfPossible = new KtPsiFactory(getProject()).createNameIdentifierIfPossible(KtPsiUtilKt.quoteIfNeeded(str));
        if (psiElementCreateNameIdentifierIfPossible != null) {
            KtPsiUtilKt.astReplace(psiElementMo7392getNameIdentifier, psiElementCreateNameIdentifierIfPossible);
        } else {
            psiElementMo7392getNameIdentifier.delete();
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        return (psiElementMo7392getNameIdentifier != null ? psiElementMo7392getNameIdentifier.getTextRange() : getTextRange()).getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        KtElement ktElement;
        KtExpression condition;
        KtDeclarationWithInitializer ktDeclarationWithInitializer;
        KtElement enclosingElementForLocalDeclaration = KtPsiUtil.getEnclosingElementForLocalDeclaration(this, false);
        if (enclosingElementForLocalDeclaration != null) {
            PsiElement parent = enclosingElementForLocalDeclaration.getLightParent();
            KtObjectDeclaration ktObjectDeclaration = (KtObjectDeclaration) PsiTreeUtil.getParentOfType(this, KtObjectDeclaration.class);
            if (ktObjectDeclaration != null && ktObjectDeclaration.isObjectLiteral() && (ktDeclarationWithInitializer = (KtDeclarationWithInitializer) PsiTreeUtil.getParentOfType(ktObjectDeclaration, KtDeclarationWithInitializer.class)) != null && ktDeclarationWithInitializer.hasModifier(KtTokens.PRIVATE_KEYWORD)) {
                if (parent instanceof KtFile) {
                    return new LocalSearchScope(parent);
                }
                if ((parent instanceof KtObjectDeclaration) && ((KtObjectDeclaration) parent).isCompanion()) {
                    return new LocalSearchScope(parent.getLightParent());
                }
            }
            if (parent instanceof KtContainerNode) {
                parent = parent.getLightParent();
            }
            if ((enclosingElementForLocalDeclaration instanceof KtBlockExpression) && (parent instanceof KtDoWhileExpression) && (condition = ((KtDoWhileExpression) parent).getCondition()) != null) {
                return new LocalSearchScope(new PsiElement[]{enclosingElementForLocalDeclaration, condition});
            }
            return new LocalSearchScope(enclosingElementForLocalDeclaration);
        }
        PsiElement parent2 = getLightParent();
        PsiElement parent3 = parent2 != null ? parent2.getLightParent() : null;
        if ((parent2 instanceof KtBlockExpression) && (parent3 instanceof KtScript)) {
            return new LocalSearchScope(getContainingFile());
        }
        if (hasModifier(KtTokens.PRIVATE_KEYWORD)) {
            KtElement ktElement2 = (KtElement) PsiTreeUtil.getParentOfType(this, KtClassOrObject.class);
            if ((ktElement2 instanceof KtObjectDeclaration) && ((KtObjectDeclaration) ktElement2).isCompanion() && (ktElement = (KtElement) PsiTreeUtil.getParentOfType(ktElement2, KtClassOrObject.class)) != null) {
                ktElement2 = ktElement;
            }
            if (ktElement2 != null) {
                return new LocalSearchScope(ktElement2);
            }
            KtFile containingKtFile = getContainingKtFile();
            if (this instanceof KtClassOrObject) {
                Project project = getProject();
                GlobalSearchScope scopeRestrictedByFileTypes = GlobalSearchScope.getScopeRestrictedByFileTypes(GlobalSearchScope.allScope(project), KotlinFileType.INSTANCE);
                GlobalSearchScope globalSearchScopeFileScope = GlobalSearchScope.fileScope(containingKtFile);
                PsiPackage psiPackageFindPackage = JavaPsiFacade.getInstance(project).findPackage(containingKtFile.getPackageFqName().asString());
                SearchScope useScope = super.getUseScope();
                if (psiPackageFindPackage == null) {
                    if (useScope == null) {
                        $$$reportNull$$$0(5);
                    }
                    return useScope;
                }
                SearchScope searchScopeUnion = globalSearchScopeFileScope.union(GlobalSearchScope.notScope(scopeRestrictedByFileTypes).intersectWith(PackageScope.packageScope(psiPackageFindPackage, false).intersectWith(useScope)));
                if (searchScopeUnion == null) {
                    $$$reportNull$$$0(6);
                }
                return searchScopeUnion;
            }
            return new LocalSearchScope(containingKtFile);
        }
        SearchScope useScope2 = super.getUseScope();
        KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(this);
        if (containingClassOrObject != null) {
            useScope2 = useScope2.intersectWith(containingClassOrObject.getUseScope());
        }
        if (useScope2 == null) {
            $$$reportNull$$$0(7);
        }
        return useScope2;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName */
    public FqName mo7391getFqName() {
        T stub = getStub();
        if (stub != null) {
            return stub.getFqName();
        }
        return KtNamedDeclarationUtil.getFQName(this);
    }
}
