package org.jetbrains.kotlin.idea.references;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.resolve.references.ReferenceAccess;

/* JADX INFO: compiled from: SyntheticPropertyAccessorReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0005H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0004R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/SyntheticPropertyAccessorReference;", "Lorg/jetbrains/kotlin/idea/references/KtSimpleReference;", "Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;", "expression", "getter", "", "(Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;Z)V", "getGetter", "()Z", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "canBeReferenceTo", "candidateTarget", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "canRename", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "isAccessorName", "name", "", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SyntheticPropertyAccessorReference extends KtSimpleReference<KtNameReferenceExpression> {
    private final boolean getter;

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    public boolean canRename() {
        return true;
    }

    public final boolean getGetter() {
        return this.getter;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyntheticPropertyAccessorReference(KtNameReferenceExpression expression, boolean z) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.getter = z;
    }

    protected final boolean isAccessorName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.getter) {
            return StringsKt.startsWith$default(name2, "get", false, 2, (Object) null) || StringsKt.startsWith$default(name2, LanguageCodeUtil.IS, false, 2, (Object) null);
        }
        return StringsKt.startsWith$default(name2, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, false, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.idea.references.AbstractKtReference
    protected boolean canBeReferenceTo(PsiElement candidateTarget) {
        Intrinsics.checkNotNullParameter(candidateTarget, "candidateTarget");
        if (candidateTarget instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) candidateTarget;
            String myName = psiMethod.getMyName();
            Intrinsics.checkNotNullExpressionValue(myName, "getName(...)");
            if (isAccessorName(myName)) {
                if ((!this.getter || LightClassUtilsKt.getCanHaveSyntheticGetter(psiMethod)) && (this.getter || LightClassUtilsKt.getCanHaveSyntheticSetter(psiMethod))) {
                    return this.getter || ReadWriteAccessCheckerKt.readWriteAccess((KtExpression) getExpression(), true) != ReferenceAccess.READ;
                }
                return false;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReferenceBase, org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        return new TextRange(0, ((KtNameReferenceExpression) getExpression()).getTextLength());
    }

    @Override // org.jetbrains.kotlin.idea.references.KtReference
    public Collection<Name> getResolvesByNames() {
        return CollectionsKt.listOf(((KtNameReferenceExpression) getPsiElement()).getReferencedNameAsName());
    }
}
