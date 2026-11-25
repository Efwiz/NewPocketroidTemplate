package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameterBuilder;
import org.jetbrains.kotlin.psi.KtTypeParameter;

/* JADX INFO: compiled from: KotlinLightTypeParameterBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bR\u0014\u0010\n\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KotlinLightTypeParameterBuilder;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterBuilder;", "Lorg/jetbrains/kotlin/asJava/elements/PsiElementWithOrigin;", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "name", "", "owner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterListOwner;", "index", "", "origin", "(Ljava/lang/String;Lcom/intellij/psi/PsiTypeParameterListOwner;ILorg/jetbrains/kotlin/psi/KtTypeParameter;)V", "getOrigin", "()Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinLightTypeParameterBuilder extends LightTypeParameterBuilder implements PsiElementWithOrigin<KtTypeParameter> {
    private final KtTypeParameter origin;

    @Override // org.jetbrains.kotlin.asJava.elements.PsiElementWithOrigin
    public KtTypeParameter getOrigin() {
        return this.origin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinLightTypeParameterBuilder(String name2, PsiTypeParameterListOwner owner, int i, KtTypeParameter origin) {
        super(name2, owner, i);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(origin, "origin");
        this.origin = origin;
    }
}
