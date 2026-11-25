package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.util.MethodSignatureUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;

/* JADX INFO: compiled from: KtDefaultAnnotationArgumentReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001aB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\r\u0010\u0011\u001a\u00070\u0012¢\u0006\u0002\b\u0013H\u0016J\u0010\u0010\u0014\u001a\n \u0016*\u0004\u0018\u00010\u00150\u0015H\u0016J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0019H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtDefaultAnnotationArgumentReference;", "Lorg/jetbrains/kotlin/idea/references/AbstractKtReference;", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/psi/KtValueArgument;)V", "resolver", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache$PolyVariantResolver;", "Lorg/jetbrains/kotlin/idea/references/KtReference;", "getResolver", "()Lcom/intellij/psi/impl/source/resolve/ResolveCache$PolyVariantResolver;", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "canRename", "", "getCanonicalText", "", "Lorg/jetbrains/annotations/NonNls;", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isReferenceTo", "candidateTarget", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Resolver", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDefaultAnnotationArgumentReference extends AbstractKtReference<KtValueArgument> {
    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    public boolean canRename() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDefaultAnnotationArgumentReference(KtValueArgument element) {
        super(element);
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public ResolveCache.PolyVariantResolver<KtReference> getResolver() {
        return Resolver.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        Name nameIdentifier = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return CollectionsKt.listOf(nameIdentifier);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return TextRange.EMPTY_RANGE;
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        return "value";
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference, org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement candidateTarget) {
        Intrinsics.checkNotNullParameter(candidateTarget, "candidateTarget");
        PsiElement unwrapped = LightClassUtilsKt.getUnwrapped(candidateTarget);
        return ((unwrapped instanceof PsiMethod) || (unwrapped instanceof KtParameter)) && Intrinsics.areEqual(unwrapped, resolve());
    }

    /* JADX INFO: compiled from: KtDefaultAnnotationArgumentReference.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J#\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0016¢\u0006\u0002\u0010\nJ\u000e\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtDefaultAnnotationArgumentReference$Resolver;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache$PolyVariantResolver;", "Lorg/jetbrains/kotlin/idea/references/KtReference;", "()V", "resolve", "", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveResult;", "t", "incompleteCode", "", "(Lorg/jetbrains/kotlin/idea/references/KtReference;Z)[Lcom/intellij/psi/ResolveResult;", "resolveAnnotationCallee", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/idea/references/KtDefaultAnnotationArgumentReference;", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Resolver implements ResolveCache.PolyVariantResolver<KtReference> {
        public static final Resolver INSTANCE = new Resolver();

        private Resolver() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache.PolyVariantResolver
        public ResolveResult[] resolve(KtReference t, boolean z) {
            Intrinsics.checkNotNullParameter(t, "t");
            if (!(t instanceof KtDefaultAnnotationArgumentReference)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            PsiElement psiElementResolveAnnotationCallee = resolveAnnotationCallee((KtDefaultAnnotationArgumentReference) t);
            if (psiElementResolveAnnotationCallee != null) {
                if (psiElementResolveAnnotationCallee instanceof PsiClass) {
                    MethodSignature methodSignatureCreateMethodSignature = MethodSignatureUtil.createMethodSignature("value", PsiType.EMPTY_ARRAY, PsiTypeParameter.EMPTY_ARRAY, PsiSubstitutor.EMPTY);
                    Intrinsics.checkNotNullExpressionValue(methodSignatureCreateMethodSignature, "createMethodSignature(...)");
                    PsiMethod psiMethodFindMethodBySignature = MethodSignatureUtil.findMethodBySignature((PsiClass) psiElementResolveAnnotationCallee, methodSignatureCreateMethodSignature, false);
                    if (psiMethodFindMethodBySignature != null) {
                        return new ResolveResult[]{new PsiElementResolveResult(psiMethodFindMethodBySignature)};
                    }
                    return new ResolveResult[0];
                }
                if (psiElementResolveAnnotationCallee instanceof KtPrimaryConstructor) {
                    KtParameter ktParameter = (KtParameter) CollectionsKt.firstOrNull((List) ((KtPrimaryConstructor) psiElementResolveAnnotationCallee).getValueParameters());
                    if (ktParameter != null) {
                        return new ResolveResult[]{new PsiElementResolveResult(ktParameter)};
                    }
                    return new ResolveResult[0];
                }
                return new ResolveResult[0];
            }
            return new ResolveResult[0];
        }

        private final PsiElement resolveAnnotationCallee(KtDefaultAnnotationArgumentReference ktDefaultAnnotationArgumentReference) {
            KtConstructorCalleeExpression calleeExpression;
            KtSimpleNameExpression constructorReferenceExpression;
            KtSimpleNameReference mainReference;
            PsiElement element = ktDefaultAnnotationArgumentReference.getPsiElement();
            Intrinsics.checkNotNullExpressionValue(element, "getElement(...)");
            KtAnnotationEntry ktAnnotationEntry = (KtAnnotationEntry) PsiTreeUtil.getParentOfType(element, KtAnnotationEntry.class, true);
            if (ktAnnotationEntry == null || (calleeExpression = ktAnnotationEntry.getCalleeExpression()) == null || (constructorReferenceExpression = calleeExpression.getConstructorReferenceExpression()) == null || (mainReference = ReferenceUtilsKt.getMainReference(constructorReferenceExpression)) == null) {
                return null;
            }
            return mainReference.resolve();
        }
    }
}
