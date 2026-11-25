package org.jetbrains.kotlin.idea.references;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocName;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: referenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0013\u001a\u0004\u0018\u00010\u000b*\u00020\u0014\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0007\"\u0015\u0010\u0000\u001a\u00020\u0005*\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\t\"\u0015\u0010\u0000\u001a\u00020\n*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\f\"\u001b\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"mainReference", "Lorg/jetbrains/kotlin/idea/references/KDocReference;", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocName;", "getMainReference", "(Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocName;)Lorg/jetbrains/kotlin/idea/references/KDocReference;", "Lorg/jetbrains/kotlin/idea/references/KtReference;", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/psi/KtElement;)Lorg/jetbrains/kotlin/idea/references/KtReference;", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "(Lorg/jetbrains/kotlin/psi/KtReferenceExpression;)Lorg/jetbrains/kotlin/idea/references/KtReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;)Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference;", "unwrappedTargets", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "getUnwrappedTargets", "(Lcom/intellij/psi/PsiReference;)Ljava/util/Set;", "getCalleeByLambdaArgument", "Lorg/jetbrains/kotlin/psi/KtFunction;", "kt-references"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReferenceUtilsKt {
    public static final KtSimpleNameReference getMainReference(KtSimpleNameExpression ktSimpleNameExpression) {
        Intrinsics.checkNotNullParameter(ktSimpleNameExpression, "<this>");
        PsiReference[] references = ktSimpleNameExpression.getReferences();
        Intrinsics.checkNotNullExpressionValue(references, "getReferences(...)");
        for (PsiReference psiReference : references) {
            if (psiReference instanceof KtSimpleNameReference) {
                return (KtSimpleNameReference) psiReference;
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    public static final KtReference getMainReference(KtReferenceExpression ktReferenceExpression) {
        Intrinsics.checkNotNullParameter(ktReferenceExpression, "<this>");
        if (ktReferenceExpression instanceof KtSimpleNameExpression) {
            return getMainReference((KtSimpleNameExpression) ktReferenceExpression);
        }
        PsiReference[] references = ktReferenceExpression.getReferences();
        Intrinsics.checkNotNullExpressionValue(references, "getReferences(...)");
        for (PsiReference psiReference : references) {
            if (psiReference instanceof KtReference) {
                return (KtReference) psiReference;
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    public static final KDocReference getMainReference(KDocName kDocName) {
        Intrinsics.checkNotNullParameter(kDocName, "<this>");
        PsiReference[] references = kDocName.getReferences();
        Intrinsics.checkNotNullExpressionValue(references, "getReferences(...)");
        for (PsiReference psiReference : references) {
            if (psiReference instanceof KDocReference) {
                return (KDocReference) psiReference;
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }

    public static final KtReference getMainReference(KtElement ktElement) {
        PsiReference psiReference;
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtReferenceExpression) {
            return getMainReference((KtReferenceExpression) ktElement);
        }
        if (ktElement instanceof KDocName) {
            return getMainReference((KDocName) ktElement);
        }
        PsiReference[] references = ktElement.getReferences();
        Intrinsics.checkNotNullExpressionValue(references, "getReferences(...)");
        PsiReference[] psiReferenceArr = references;
        int length = psiReferenceArr.length;
        for (int i = 0; i < length; i++) {
            psiReference = psiReferenceArr[i];
            if (psiReference instanceof KtReference) {
                return (KtReference) psiReference;
            }
        }
        psiReference = null;
        return (KtReference) psiReference;
    }

    private static final PsiElement _get_unwrappedTargets_$adjust(PsiElement psiElement) {
        PsiElement unwrapped = LightClassUtilsKt.getUnwrapped(psiElement);
        PsiElement originalElement = unwrapped != null ? unwrapped.getFunDelegate() : null;
        return originalElement instanceof KtPropertyAccessor ? PsiTreeUtil.getParentOfType(originalElement, KtProperty.class, false) : originalElement;
    }

    public static final Set<PsiElement> getUnwrappedTargets(PsiReference psiReference) {
        PsiElement psiElement_get_unwrappedTargets_$adjust;
        Intrinsics.checkNotNullParameter(psiReference, "<this>");
        if (!(psiReference instanceof PsiPolyVariantReference)) {
            PsiElement psiElementResolve = psiReference.resolve();
            return CollectionsKt.toSet(CollectionsKt.listOfNotNull(psiElementResolve != null ? _get_unwrappedTargets_$adjust(psiElementResolve) : null));
        }
        ResolveResult[] resolveResultArrMultiResolve = ((PsiPolyVariantReference) psiReference).multiResolve(false);
        Intrinsics.checkNotNullExpressionValue(resolveResultArrMultiResolve, "multiResolve(...)");
        HashSet hashSet = new HashSet();
        for (ResolveResult resolveResult : resolveResultArrMultiResolve) {
            PsiElement element = resolveResult.getElement();
            if (element != null) {
                Intrinsics.checkNotNull(element);
                psiElement_get_unwrappedTargets_$adjust = _get_unwrappedTargets_$adjust(element);
            } else {
                psiElement_get_unwrappedTargets_$adjust = null;
            }
            if (psiElement_get_unwrappedTargets_$adjust != null) {
                hashSet.add(psiElement_get_unwrappedTargets_$adjust);
            }
        }
        return hashSet;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0031  */
    public static final KtSimpleNameExpression getCalleeByLambdaArgument(KtFunction ktFunction) {
        KtCallExpression ktCallExpression;
        Intrinsics.checkNotNullParameter(ktFunction, "<this>");
        KtFunction ktFunction2 = ktFunction;
        ReferenceUtilsKt$getCalleeByLambdaArgument$argument$1 referenceUtilsKt$getCalleeByLambdaArgument$argument$1 = new Function1<KtValueArgument, PsiElement>() { // from class: org.jetbrains.kotlin.idea.references.ReferenceUtilsKt$getCalleeByLambdaArgument$argument$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiElement invoke(KtValueArgument getParentOfTypeAndBranch) {
                Intrinsics.checkNotNullParameter(getParentOfTypeAndBranch, "$this$getParentOfTypeAndBranch");
                return getParentOfTypeAndBranch.getArgumentExpression();
            }
        };
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(ktFunction2, KtValueArgument.class, false);
        KtValueArgument ktValueArgument = (KtValueArgument) (parentOfType != null ? PsiUtilsKt.getIfChildIsInBranch(parentOfType, ktFunction2, referenceUtilsKt$getCalleeByLambdaArgument$argument$1) : null);
        if (ktValueArgument == null) {
            return null;
        }
        if (ktValueArgument instanceof KtLambdaArgument) {
            PsiElement parent = ((KtLambdaArgument) ktValueArgument).getLightParent();
            if (parent instanceof KtCallExpression) {
                ktCallExpression = (KtCallExpression) parent;
            } else {
                ktCallExpression = null;
            }
        } else {
            PsiElement parent2 = ktValueArgument.getLightParent();
            KtValueArgumentList ktValueArgumentList = parent2 instanceof KtValueArgumentList ? (KtValueArgumentList) parent2 : null;
            PsiElement parent3 = ktValueArgumentList != null ? ktValueArgumentList.getLightParent() : null;
            if (parent3 instanceof KtCallExpression) {
                ktCallExpression = (KtCallExpression) parent3;
            } else {
                ktCallExpression = null;
            }
        }
        if (ktCallExpression == null) {
            return null;
        }
        KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
        if (calleeExpression instanceof KtSimpleNameExpression) {
            return (KtSimpleNameExpression) calleeExpression;
        }
        return null;
    }
}
