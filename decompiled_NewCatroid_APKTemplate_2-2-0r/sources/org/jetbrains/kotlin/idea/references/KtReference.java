package org.jetbrains.kotlin.idea.references;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u000b\u001a\u00020\fH&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00000\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtReference;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPolyVariantReference;", "resolver", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveCache$PolyVariantResolver;", "getResolver", "()Lcom/intellij/psi/impl/source/resolve/ResolveCache$PolyVariantResolver;", "resolvesByNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getResolvesByNames", "()Ljava/util/Collection;", "getElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtReference extends PsiPolyVariantReference {
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    KtElement getElement();

    ResolveCache.PolyVariantResolver<KtReference> getResolver();

    Collection<Name> getResolvesByNames();
}
