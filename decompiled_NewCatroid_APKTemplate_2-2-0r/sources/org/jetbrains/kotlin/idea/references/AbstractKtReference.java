package org.jetbrains.kotlin.idea.references;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.apk.PathMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReferenceBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtContainerNode;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtImportAlias;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtLabelReferenceExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: KtReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bH\u0014J\b\u0010\u000f\u001a\u00020\rH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0004J\u0013\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016¢\u0006\u0002\u0010\u0017J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u0011H\u0016J\u0010\u0010\u001a\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0010\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001dH\u0014J\b\u0010\u001e\u001a\u00020\rH\u0016J\u001b\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00152\u0006\u0010!\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020\u0011H\u0016J\f\u0010$\u001a\u00020\r*\u00020%H\u0002J\u0014\u0010&\u001a\u00020\r*\u00020\u000b2\u0006\u0010'\u001a\u00020\u000bH\u0002R\u0011\u0010\u0007\u001a\u00028\u00008F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/idea/references/AbstractKtReference;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPolyVariantReferenceBase;", "Lorg/jetbrains/kotlin/idea/references/KtReference;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/psi/KtElement;)V", "expression", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtElement;", "bindToElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "canBeReferenceTo", "", "candidateTarget", "canRename", "getCanonicalText", "", "getKtReferenceMutateService", "Lorg/jetbrains/kotlin/idea/references/KtReferenceMutateService;", "getVariants", "", "", "()[Ljava/lang/Object;", "handleElementRename", "newElementName", "isReferenceTo", "isReferenceToImportAlias", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/psi/KtImportAlias;", "isSoft", "multiResolve", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveResult;", "incompleteCode", "(Z)[Lcom/intellij/psi/ResolveResult;", "toString", "canBePsiMethodReference", "Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference;", "isConstructorOf", "unwrappedCandidate", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKtReference<T extends KtElement> extends PsiPolyVariantReferenceBase<T> implements KtReference {
    protected boolean canBeReferenceTo(PsiElement candidateTarget) {
        Intrinsics.checkNotNullParameter(candidateTarget, "candidateTarget");
        return true;
    }

    public boolean canRename() {
        return false;
    }

    protected boolean isReferenceToImportAlias(KtImportAlias alias) {
        Intrinsics.checkNotNullParameter(alias, "alias");
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractKtReference(T element) {
        super(element);
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public /* bridge */ /* synthetic */ KtElement getElement() {
        return (KtElement) getElement();
    }

    public final T getExpression() {
        PsiElement element = getElement();
        Intrinsics.checkNotNullExpressionValue(element, "getElement(...)");
        return (T) element;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    public ResolveResult[] multiResolve(boolean z) {
        ResolveResult[] resolveResultArrResolveWithCaching = ResolveCache.getInstance(getExpression().getProject()).resolveWithCaching(this, (ResolveCache.PolyVariantResolver<AbstractKtReference<T>>) getResolver(), false, z);
        Intrinsics.checkNotNullExpressionValue(resolveResultArrResolveWithCaching, "resolveWithCaching(...)");
        return resolveResultArrResolveWithCaching;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        String text = getExpression().getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String newElementName) {
        Intrinsics.checkNotNullParameter(newElementName, "newElementName");
        if (canRename()) {
            return getKtReferenceMutateService().handleElementRename(this, newElementName);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return getKtReferenceMutateService().bindToElement(this, element);
    }

    protected final KtReferenceMutateService getKtReferenceMutateService() {
        KtReferenceMutateService ktReferenceMutateService = (KtReferenceMutateService) ApplicationManager.getApplication().getService(KtReferenceMutateService.class);
        if (ktReferenceMutateService != null) {
            return ktReferenceMutateService;
        }
        throw new IllegalStateException("Cannot handle element rename because KtReferenceMutateService is missing");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        return PsiReference.EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase
    public String toString() {
        return getClass().getSimpleName() + ": " + getExpression().getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement candidateTarget) {
        KtLabeledExpression labeledParent;
        KtSimpleNameReference mainReference;
        Intrinsics.checkNotNullParameter(candidateTarget, "candidateTarget");
        if (!canBeReferenceTo(candidateTarget)) {
            return false;
        }
        PsiElement unwrapped = LightClassUtilsKt.getUnwrapped(candidateTarget);
        PsiElement originalElement = unwrapped != null ? unwrapped.getOriginalElement() : null;
        if (originalElement == null) {
            return false;
        }
        if (this instanceof KtInvokeFunctionReference) {
            if ((!(candidateTarget instanceof KtNamedFunction) && !(candidateTarget instanceof PsiMethod)) || !Intrinsics.areEqual(((PsiNamedElement) candidateTarget).getName(), OperatorNameConventions.INVOKE.asString())) {
                return false;
            }
        } else if (this instanceof KtDestructuringDeclarationReference) {
            if (!(candidateTarget instanceof KtNamedFunction) && !(candidateTarget instanceof KtParameter) && !(candidateTarget instanceof PsiMethod)) {
                return false;
            }
        } else if ((this instanceof KtSimpleNameReference) && (originalElement instanceof PsiMethod) && !canBePsiMethodReference((KtSimpleNameReference) this)) {
            return false;
        }
        PsiElement element = getElement();
        Intrinsics.checkNotNullExpressionValue(element, "getElement(...)");
        KtElement ktElement = (KtElement) element;
        if ((candidateTarget instanceof KtImportAlias) && (((ktElement instanceof KtSimpleNameExpression) && Intrinsics.areEqual(((KtSimpleNameExpression) ktElement).getReferencedName(), ((KtImportAlias) candidateTarget).getName())) || ((this instanceof KDocReference) && Intrinsics.areEqual(((KDocReference) this).getCanonicalText(), ((KtImportAlias) candidateTarget).getName())))) {
            return isReferenceToImportAlias((KtImportAlias) candidateTarget);
        }
        if (ktElement instanceof KtLabelReferenceExpression) {
            KtLabelReferenceExpression ktLabelReferenceExpression = (KtLabelReferenceExpression) ktElement;
            PsiElement parent = ktLabelReferenceExpression.getLightParent();
            KtContainerNode ktContainerNode = parent instanceof KtContainerNode ? (KtContainerNode) parent : null;
            PsiElement parent2 = ktContainerNode != null ? ktContainerNode.getLightParent() : null;
            if (!(parent2 instanceof KtReturnExpression)) {
                if (parent2 instanceof KtBreakExpression ? true : parent2 instanceof KtContinueExpression) {
                    for (PsiElement psiElement : ReferenceUtilsKt.getUnwrappedTargets(this)) {
                        KtExpression ktExpression = psiElement instanceof KtExpression ? (KtExpression) psiElement : null;
                        if (ktExpression != null && (labeledParent = KtPsiUtilKt.getLabeledParent(ktExpression, ktLabelReferenceExpression.getReferencedName())) != null && Intrinsics.areEqual(candidateTarget, labeledParent)) {
                            return true;
                        }
                    }
                }
            } else {
                for (PsiElement psiElement2 : ReferenceUtilsKt.getUnwrappedTargets(this)) {
                    if (!(psiElement2 instanceof KtFunctionLiteral)) {
                        if (psiElement2 instanceof KtNamedFunction) {
                            String name2 = ((KtNamedFunction) psiElement2).getName();
                            if (!(name2 == null || name2.length() == 0)) {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                    KtFunction ktFunction = (KtFunction) psiElement2;
                    KtLabeledExpression labeledParent2 = KtPsiUtilKt.getLabeledParent((KtExpression) psiElement2, ktLabelReferenceExpression.getReferencedName());
                    if (labeledParent2 != null) {
                        if (Intrinsics.areEqual(candidateTarget, labeledParent2)) {
                            return true;
                        }
                    } else {
                        KtSimpleNameExpression calleeByLambdaArgument = ReferenceUtilsKt.getCalleeByLambdaArgument(ktFunction);
                        if (calleeByLambdaArgument != null && (mainReference = ReferenceUtilsKt.getMainReference(calleeByLambdaArgument)) != null && mainReference.isReferenceTo(candidateTarget)) {
                            return true;
                        }
                    }
                }
            }
        }
        Set<PsiElement> unwrappedTargets = ReferenceUtilsKt.getUnwrappedTargets(this);
        PsiManager manager = candidateTarget.getManager();
        Set<PsiElement> set = unwrappedTargets;
        boolean z = set instanceof Collection;
        if (!z || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                if (manager.areElementsEquivalent(originalElement, (PsiElement) it.next())) {
                    return true;
                }
            }
        }
        if (z && set.isEmpty()) {
            return false;
        }
        for (PsiElement psiElement3 : set) {
            if (isConstructorOf(psiElement3, originalElement) || ((psiElement3 instanceof KtObjectDeclaration) && ((KtObjectDeclaration) psiElement3).isCompanion() && Intrinsics.areEqual(PsiTreeUtil.getParentOfType(psiElement3, KtClass.class, false), originalElement))) {
                return true;
            }
        }
        return false;
    }

    private final boolean canBePsiMethodReference(KtSimpleNameReference ktSimpleNameReference) {
        PsiElement element = ktSimpleNameReference.getElement();
        PsiElement parent = ((KtSimpleNameExpression) ktSimpleNameReference.getElement()).getLightParent();
        KtCallExpression ktCallExpression = parent instanceof KtCallExpression ? (KtCallExpression) parent : null;
        if (Intrinsics.areEqual(element, ktCallExpression != null ? ktCallExpression.getCalleeExpression() : null)) {
            return true;
        }
        PsiElement element2 = ktSimpleNameReference.getElement();
        Intrinsics.checkNotNullExpressionValue(element2, "getElement(...)");
        AbstractKtReference$canBePsiMethodReference$callableReference$1 abstractKtReference$canBePsiMethodReference$callableReference$1 = new Function1<KtCallableReferenceExpression, PsiElement>() { // from class: org.jetbrains.kotlin.idea.references.AbstractKtReference$canBePsiMethodReference$callableReference$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(KtCallableReferenceExpression getParentOfTypeAndBranch) {
                Intrinsics.checkNotNullParameter(getParentOfTypeAndBranch, "$this$getParentOfTypeAndBranch");
                return getParentOfTypeAndBranch.getCallableReference();
            }
        };
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(element2, KtCallableReferenceExpression.class, false);
        if (((KtCallableReferenceExpression) (parentOfType != null ? PsiUtilsKt.getIfChildIsInBranch(parentOfType, element2, abstractKtReference$canBePsiMethodReference$callableReference$1) : null)) != null) {
            return true;
        }
        PsiElement element3 = ktSimpleNameReference.getElement();
        Intrinsics.checkNotNullExpressionValue(element3, "getElement(...)");
        AbstractKtReference$canBePsiMethodReference$binaryOperator$1 abstractKtReference$canBePsiMethodReference$binaryOperator$1 = new Function1<KtBinaryExpression, PsiElement>() { // from class: org.jetbrains.kotlin.idea.references.AbstractKtReference$canBePsiMethodReference$binaryOperator$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(KtBinaryExpression getParentOfTypeAndBranch) {
                Intrinsics.checkNotNullParameter(getParentOfTypeAndBranch, "$this$getParentOfTypeAndBranch");
                return getParentOfTypeAndBranch.getOperationReference();
            }
        };
        PsiElement parentOfType2 = PsiTreeUtil.getParentOfType(element3, KtBinaryExpression.class, false);
        if (((KtBinaryExpression) (parentOfType2 != null ? PsiUtilsKt.getIfChildIsInBranch(parentOfType2, element3, abstractKtReference$canBePsiMethodReference$binaryOperator$1) : null)) != null) {
            return true;
        }
        PsiElement element4 = ktSimpleNameReference.getElement();
        Intrinsics.checkNotNullExpressionValue(element4, "getElement(...)");
        AbstractKtReference$canBePsiMethodReference$unaryOperator$1 abstractKtReference$canBePsiMethodReference$unaryOperator$1 = new Function1<KtUnaryExpression, PsiElement>() { // from class: org.jetbrains.kotlin.idea.references.AbstractKtReference$canBePsiMethodReference$unaryOperator$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(KtUnaryExpression getParentOfTypeAndBranch) {
                Intrinsics.checkNotNullParameter(getParentOfTypeAndBranch, "$this$getParentOfTypeAndBranch");
                return getParentOfTypeAndBranch.getOperationReference();
            }
        };
        PsiElement parentOfType3 = PsiTreeUtil.getParentOfType(element4, KtUnaryExpression.class, false);
        if (((KtUnaryExpression) (parentOfType3 != null ? PsiUtilsKt.getIfChildIsInBranch(parentOfType3, element4, abstractKtReference$canBePsiMethodReference$unaryOperator$1) : null)) != null) {
            return true;
        }
        PsiElement element5 = ktSimpleNameReference.getElement();
        Intrinsics.checkNotNullExpressionValue(element5, "getElement(...)");
        return PsiTreeUtil.getParentOfType(element5, KtImportDirective.class, false) != null;
    }

    private final boolean isConstructorOf(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) psiElement;
            if (psiMethod.isConstructor() && Intrinsics.areEqual(psiMethod.mo6092getContainingClass(), psiElement2)) {
                return true;
            }
        }
        return (psiElement instanceof KtConstructor) && ((KtConstructor) psiElement).getContainingClassOrObject().isEquivalentTo(psiElement2);
    }
}
