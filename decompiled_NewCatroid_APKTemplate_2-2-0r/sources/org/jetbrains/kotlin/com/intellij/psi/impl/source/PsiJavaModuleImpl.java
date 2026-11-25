package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackageAccessibilityStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiProvidesStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaModuleStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.ProjectScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;

/* JADX INFO: loaded from: classes3.dex */
public class PsiJavaModuleImpl extends JavaStubPsiElement<PsiJavaModuleStub> implements PsiJavaModule {
    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 18) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 18) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "node";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleImpl";
                break;
            case 15:
            case 16:
                objArr[0] = "name";
                break;
            case 17:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i != 18) {
            switch (i) {
                case 2:
                case 3:
                    objArr[1] = "getRequires";
                    break;
                case 4:
                case 5:
                    objArr[1] = "getExports";
                    break;
                case 6:
                case 7:
                    objArr[1] = "getOpens";
                    break;
                case 8:
                case 9:
                    objArr[1] = "getUses";
                    break;
                case 10:
                case 11:
                    objArr[1] = "getProvides";
                    break;
                case 12:
                    objArr[1] = "getNameIdentifier";
                    break;
                case 13:
                case 14:
                    objArr[1] = "getName";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiJavaModuleImpl";
                    break;
            }
        } else {
            objArr[1] = "getUseScope";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
                break;
            case 15:
                objArr[2] = "setName";
                break;
            case 16:
                objArr[2] = "hasModifierProperty";
                break;
            case 17:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 18) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiJavaModuleImpl(PsiJavaModuleStub psiJavaModuleStub) {
        super(psiJavaModuleStub, JavaStubElementTypes.MODULE);
        if (psiJavaModuleStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiJavaModuleImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiRequiresStatement> getRequires() {
        PsiJavaModuleStub psiJavaModuleStub = (PsiJavaModuleStub) getGreenStub();
        if (psiJavaModuleStub != null) {
            JBIterable jBIterableOf = JBIterable.of(psiJavaModuleStub.getChildrenByType(JavaElementType.REQUIRES_STATEMENT, PsiRequiresStatement.EMPTY_ARRAY));
            if (jBIterableOf == null) {
                $$$reportNull$$$0(2);
            }
            return jBIterableOf;
        }
        Iterable iterableFilter = SyntaxTraverser.psiTraverser().children(this).filter(PsiRequiresStatement.class);
        if (iterableFilter == null) {
            $$$reportNull$$$0(3);
        }
        return iterableFilter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiPackageAccessibilityStatement> getExports() {
        PsiJavaModuleStub psiJavaModuleStub = (PsiJavaModuleStub) getGreenStub();
        if (psiJavaModuleStub != null) {
            JBIterable jBIterableOf = JBIterable.of(psiJavaModuleStub.getChildrenByType(JavaElementType.EXPORTS_STATEMENT, PsiPackageAccessibilityStatement.EMPTY_ARRAY));
            if (jBIterableOf == null) {
                $$$reportNull$$$0(4);
            }
            return jBIterableOf;
        }
        JBIterable jBIterableFilter = SyntaxTraverser.psiTraverser().children(this).filter(PsiPackageAccessibilityStatement.class).filter((Condition<? super T>) new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaModuleImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiJavaModuleImpl.lambda$getExports$0((PsiPackageAccessibilityStatement) obj);
            }
        });
        if (jBIterableFilter == null) {
            $$$reportNull$$$0(5);
        }
        return jBIterableFilter;
    }

    static /* synthetic */ boolean lambda$getExports$0(PsiPackageAccessibilityStatement psiPackageAccessibilityStatement) {
        return psiPackageAccessibilityStatement.getRole() == PsiPackageAccessibilityStatement.Role.EXPORTS;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiPackageAccessibilityStatement> getOpens() {
        PsiJavaModuleStub psiJavaModuleStub = (PsiJavaModuleStub) getGreenStub();
        if (psiJavaModuleStub != null) {
            JBIterable jBIterableOf = JBIterable.of(psiJavaModuleStub.getChildrenByType(JavaElementType.OPENS_STATEMENT, PsiPackageAccessibilityStatement.EMPTY_ARRAY));
            if (jBIterableOf == null) {
                $$$reportNull$$$0(6);
            }
            return jBIterableOf;
        }
        JBIterable jBIterableFilter = SyntaxTraverser.psiTraverser().children(this).filter(PsiPackageAccessibilityStatement.class).filter((Condition<? super T>) new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaModuleImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiJavaModuleImpl.lambda$getOpens$1((PsiPackageAccessibilityStatement) obj);
            }
        });
        if (jBIterableFilter == null) {
            $$$reportNull$$$0(7);
        }
        return jBIterableFilter;
    }

    static /* synthetic */ boolean lambda$getOpens$1(PsiPackageAccessibilityStatement psiPackageAccessibilityStatement) {
        return psiPackageAccessibilityStatement.getRole() == PsiPackageAccessibilityStatement.Role.OPENS;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiUsesStatement> getUses() {
        PsiJavaModuleStub psiJavaModuleStub = (PsiJavaModuleStub) getGreenStub();
        if (psiJavaModuleStub != null) {
            JBIterable jBIterableOf = JBIterable.of(psiJavaModuleStub.getChildrenByType(JavaElementType.USES_STATEMENT, PsiUsesStatement.EMPTY_ARRAY));
            if (jBIterableOf == null) {
                $$$reportNull$$$0(8);
            }
            return jBIterableOf;
        }
        Iterable iterableFilter = SyntaxTraverser.psiTraverser().children(this).filter(PsiUsesStatement.class);
        if (iterableFilter == null) {
            $$$reportNull$$$0(9);
        }
        return iterableFilter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule
    public Iterable<PsiProvidesStatement> getProvides() {
        PsiJavaModuleStub psiJavaModuleStub = (PsiJavaModuleStub) getGreenStub();
        if (psiJavaModuleStub != null) {
            JBIterable jBIterableOf = JBIterable.of(psiJavaModuleStub.getChildrenByType(JavaElementType.PROVIDES_STATEMENT, PsiProvidesStatement.EMPTY_ARRAY));
            if (jBIterableOf == null) {
                $$$reportNull$$$0(10);
            }
            return jBIterableOf;
        }
        Iterable iterableFilter = SyntaxTraverser.psiTraverser().children(this).filter(PsiProvidesStatement.class);
        if (iterableFilter == null) {
            $$$reportNull$$$0(11);
        }
        return iterableFilter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiJavaModuleReferenceElement mo7392getNameIdentifier() {
        PsiJavaModuleReferenceElement psiJavaModuleReferenceElement = (PsiJavaModuleReferenceElement) PsiTreeUtil.getRequiredChildOfType(this, PsiJavaModuleReferenceElement.class);
        if (psiJavaModuleReferenceElement == null) {
            $$$reportNull$$$0(12);
        }
        return psiJavaModuleReferenceElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String get$name() {
        PsiJavaModuleStub psiJavaModuleStub = (PsiJavaModuleStub) getGreenStub();
        if (psiJavaModuleStub != null) {
            String name2 = psiJavaModuleStub.getName();
            if (name2 == null) {
                $$$reportNull$$$0(13);
            }
            return name2;
        }
        String referenceText = mo7392getNameIdentifier().getReferenceText();
        if (referenceText == null) {
            $$$reportNull$$$0(14);
        }
        return referenceText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        mo7392getNameIdentifier().replace(PsiElementFactory.getInstance(getProject()).createModuleReferenceFromText(str, null));
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public PsiModifierList getModifierList() {
        return (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        PsiModifierList psiModifierListMo6095getModifierList = getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return (PsiDocComment) PsiTreeUtil.getChildOfType(this, PsiDocComment.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return mo7392getNameIdentifier().getTextOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getOriginalElement */
    public PsiElement getFunDelegate() {
        return (PsiElement) CachedValuesManager.getCachedValue((PsiElement) this, new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaModuleImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result compute() {
                return this.f$0.m6654xaa1a0546();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getOriginalElement$2$org-jetbrains-kotlin-com-intellij-psi-impl-source-PsiJavaModuleImpl, reason: not valid java name */
    /* synthetic */ CachedValueProvider.Result m6654xaa1a0546() {
        JavaPsiImplementationHelper javaPsiImplementationHelper = JavaPsiImplementationHelper.getInstance(getProject());
        return CachedValueProvider.Result.create(javaPsiImplementationHelper != null ? javaPsiImplementationHelper.getOriginalModule(this) : this, PsiModificationTracker.MODIFICATION_COUNT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitModule(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        GlobalSearchScope projectScope = ProjectScope.getProjectScope(getProject());
        if (projectScope == null) {
            $$$reportNull$$$0(18);
        }
        return projectScope;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiJavaModule:" + get$name();
    }
}
