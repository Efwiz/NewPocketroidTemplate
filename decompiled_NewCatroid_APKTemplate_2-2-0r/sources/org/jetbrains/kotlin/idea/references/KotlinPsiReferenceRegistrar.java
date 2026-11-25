package org.jetbrains.kotlin.idea.references;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KotlinPsiReferenceProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J4\u0010\n\u001a\u00020\u000b\"\n\b\u0000\u0010\f\u0018\u0001*\u00020\r2\u001a\b\u0004\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u0002H\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fH\u0086\bø\u0001\u0000J\u001e\u0010\n\u001a\u00020\u000b2\u000e\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00052\u0006\u0010\u0013\u001a\u00020\u0007J0\u0010\u0014\u001a\u00020\u000b\"\n\b\u0000\u0010\f\u0018\u0001*\u00020\r2\u0016\b\u0004\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u0002H\f\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000fH\u0086\bø\u0001\u0000R%\u0010\u0003\u001a\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KotlinPsiReferenceRegistrar;", "", "()V", "providers", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/idea/references/KotlinPsiReferenceProvider;", "getProviders", "()Lcom/intellij/util/containers/MultiMap;", "registerMultiProvider", "", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/psi/KtElement;", "factory", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "klass", Constants.EXTRA_PROVIDER, "registerProvider", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinPsiReferenceRegistrar {
    private final MultiMap<Class<? extends PsiElement>, KotlinPsiReferenceProvider> providers = new MultiMap<>(new LinkedHashMap());

    public final MultiMap<Class<? extends PsiElement>, KotlinPsiReferenceProvider> getProviders() {
        return this.providers;
    }

    public final /* synthetic */ <E extends KtElement> void registerMultiProvider(final Function1<? super E, PsiReference[]> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.needClassReification();
        KotlinPsiReferenceProvider kotlinPsiReferenceProvider = new KotlinPsiReferenceProvider() { // from class: org.jetbrains.kotlin.idea.references.KotlinPsiReferenceRegistrar$registerMultiProvider$provider$1
            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // org.jetbrains.kotlin.idea.references.KotlinPsiReferenceProvider
            public PsiReference[] getReferencesByElement(PsiElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                Function1<E, PsiReference[]> function1 = factory;
                Intrinsics.reifiedOperationMarker(1, ExifInterface.LONGITUDE_EAST);
                return function1.invoke((E) ((KtElement) element));
            }
        };
        Intrinsics.reifiedOperationMarker(4, ExifInterface.LONGITUDE_EAST);
        registerMultiProvider(KtElement.class, kotlinPsiReferenceProvider);
    }

    public final void registerMultiProvider(Class<? extends PsiElement> klass, KotlinPsiReferenceProvider provider) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(provider, "provider");
        this.providers.putValue(klass, provider);
    }

    public final /* synthetic */ <E extends KtElement> void registerProvider(final Function1<? super E, ? extends PsiReference> factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.needClassReification();
        KotlinPsiReferenceProvider kotlinPsiReferenceProvider = new KotlinPsiReferenceProvider() { // from class: org.jetbrains.kotlin.idea.references.KotlinPsiReferenceRegistrar$registerProvider$$inlined$registerMultiProvider$1
            @Override // org.jetbrains.kotlin.idea.references.KotlinPsiReferenceProvider
            public PsiReference[] getReferencesByElement(PsiElement element) {
                PsiReference[] psiReferenceArr;
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.reifiedOperationMarker(1, ExifInterface.LONGITUDE_EAST);
                PsiReference psiReference = (PsiReference) factory.invoke((KtElement) element);
                if (psiReference != null) {
                    psiReferenceArr = new PsiReference[]{psiReference};
                } else {
                    PsiReference[] EMPTY_ARRAY = PsiReference.EMPTY_ARRAY;
                    Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
                    psiReferenceArr = EMPTY_ARRAY;
                }
                return psiReferenceArr;
            }
        };
        Intrinsics.reifiedOperationMarker(4, ExifInterface.LONGITUDE_EAST);
        registerMultiProvider(KtElement.class, kotlinPsiReferenceProvider);
    }
}
