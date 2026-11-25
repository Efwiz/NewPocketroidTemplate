package org.jetbrains.kotlin.resolve.lazy;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfoFactory;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: loaded from: classes6.dex */
public class DeclarationScopeProviderImpl implements DeclarationScopeProvider {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final FileScopeProvider fileScopeProvider;
    private final LazyDeclarationResolver lazyDeclarationResolver;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                str = "@NotNull method %s.%s must not return null";
                break;
            case 9:
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                i2 = 2;
                break;
            case 9:
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "fileScopeProvider";
                break;
            case 2:
            case 9:
                objArr[0] = "elementOfDeclaration";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/resolve/lazy/DeclarationScopeProviderImpl";
                break;
            default:
                objArr[0] = "lazyDeclarationResolver";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[1] = "getResolutionScopeForDeclaration";
                break;
            case 9:
            default:
                objArr[1] = "org/jetbrains/kotlin/resolve/lazy/DeclarationScopeProviderImpl";
                break;
            case 10:
                objArr[1] = "getOuterDataFlowInfoForDeclaration";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "getResolutionScopeForDeclaration";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                break;
            case 9:
                objArr[2] = "getOuterDataFlowInfoForDeclaration";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
                throw new IllegalStateException(str2);
            case 9:
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public DeclarationScopeProviderImpl(LazyDeclarationResolver lazyDeclarationResolver, FileScopeProvider fileScopeProvider) {
        if (lazyDeclarationResolver == null) {
            $$$reportNull$$$0(0);
        }
        if (fileScopeProvider == null) {
            $$$reportNull$$$0(1);
        }
        this.lazyDeclarationResolver = lazyDeclarationResolver;
        this.fileScopeProvider = fileScopeProvider;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider
    public LexicalScope getResolutionScopeForDeclaration(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        KtDeclaration ktDeclaration = (KtDeclaration) KtStubbedPsiUtil.getPsiOrStubParent(psiElement, KtDeclaration.class, false);
        KtDeclaration containingDeclaration = KtStubbedPsiUtil.getContainingDeclaration(ktDeclaration);
        if (ktDeclaration instanceof KtPropertyAccessor) {
            containingDeclaration = KtStubbedPsiUtil.getContainingDeclaration(containingDeclaration, KtDeclaration.class);
        }
        if (containingDeclaration == null) {
            LexicalScope fileResolutionScope = this.fileScopeProvider.getFileResolutionScope((KtFile) psiElement.getContainingFile());
            if (fileResolutionScope == null) {
                $$$reportNull$$$0(3);
            }
            return fileResolutionScope;
        }
        if (containingDeclaration instanceof KtClassOrObject) {
            LazyClassDescriptor lazyClassDescriptor = (LazyClassDescriptor) this.lazyDeclarationResolver.getClassDescriptor((KtClassOrObject) containingDeclaration, NoLookupLocation.WHEN_GET_DECLARATION_SCOPE);
            if ((ktDeclaration instanceof KtAnonymousInitializer) || (ktDeclaration instanceof KtProperty)) {
                LexicalScope scopeForInitializerResolution = lazyClassDescriptor.getScopeForInitializerResolution();
                if (scopeForInitializerResolution == null) {
                    $$$reportNull$$$0(4);
                }
                return scopeForInitializerResolution;
            }
            boolean z = ktDeclaration instanceof KtObjectDeclaration;
            if (z && ((KtObjectDeclaration) ktDeclaration).isCompanion()) {
                LexicalScope scopeForCompanionObjectHeaderResolution = lazyClassDescriptor.getScopeForCompanionObjectHeaderResolution();
                if (scopeForCompanionObjectHeaderResolution == null) {
                    $$$reportNull$$$0(5);
                }
                return scopeForCompanionObjectHeaderResolution;
            }
            if (z || ((ktDeclaration instanceof KtClass) && !((KtClass) ktDeclaration).isInner())) {
                LexicalScope scopeForStaticMemberDeclarationResolution = lazyClassDescriptor.getScopeForStaticMemberDeclarationResolution();
                if (scopeForStaticMemberDeclarationResolution == null) {
                    $$$reportNull$$$0(6);
                }
                return scopeForStaticMemberDeclarationResolution;
            }
            LexicalScope scopeForMemberDeclarationResolution = lazyClassDescriptor.getScopeForMemberDeclarationResolution();
            if (scopeForMemberDeclarationResolution == null) {
                $$$reportNull$$$0(7);
            }
            return scopeForMemberDeclarationResolution;
        }
        if (containingDeclaration instanceof KtScript) {
            LexicalScope scopeForInitializerResolution2 = ((ClassDescriptorWithResolutionScopes) this.lazyDeclarationResolver.resolveToDescriptor(containingDeclaration)).getScopeForInitializerResolution();
            if (scopeForInitializerResolution2 == null) {
                $$$reportNull$$$0(8);
            }
            return scopeForInitializerResolution2;
        }
        throw new IllegalStateException("Don't call this method for local declarations: " + ktDeclaration + "\n" + PsiUtilsKt.getElementTextWithContext(ktDeclaration));
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider
    public DataFlowInfo getOuterDataFlowInfoForDeclaration(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        DataFlowInfo dataFlowInfo = DataFlowInfoFactory.EMPTY;
        if (dataFlowInfo == null) {
            $$$reportNull$$$0(10);
        }
        return dataFlowInfo;
    }
}
