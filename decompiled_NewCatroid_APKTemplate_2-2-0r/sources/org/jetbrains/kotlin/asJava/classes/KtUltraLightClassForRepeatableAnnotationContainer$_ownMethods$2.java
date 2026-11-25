package org.jetbrains.kotlin.asJava.classes;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethodBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightModifierList;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightParameterListBuilder;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: KtUltraLightClassForRepeatableAnnotationContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethodForSourceDeclaration;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class KtUltraLightClassForRepeatableAnnotationContainer$_ownMethods$2 extends Lambda implements Function0<List<? extends KtUltraLightMethodForSourceDeclaration>> {
    final /* synthetic */ KtUltraLightSupport $support;
    final /* synthetic */ KtUltraLightClassForRepeatableAnnotationContainer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    KtUltraLightClassForRepeatableAnnotationContainer$_ownMethods$2(KtUltraLightClassForRepeatableAnnotationContainer ktUltraLightClassForRepeatableAnnotationContainer, KtUltraLightSupport ktUltraLightSupport) {
        super(0);
        this.this$0 = ktUltraLightClassForRepeatableAnnotationContainer;
        this.$support = ktUltraLightSupport;
    }

    @Override // kotlin.jvm.functions.Function0
    public final List<? extends KtUltraLightMethodForSourceDeclaration> invoke() {
        LightMethodBuilder lightMethodBuilder = new LightMethodBuilder(this.this$0.getManager(), this.this$0.getLanguage(), "value", new LightParameterListBuilder(this.this$0.getManager(), this.this$0.getLanguage()), new LightModifierList(this.this$0.getManager(), this.this$0.getLanguage(), "public", "abstract"));
        final KtUltraLightClassForRepeatableAnnotationContainer ktUltraLightClassForRepeatableAnnotationContainer = this.this$0;
        lightMethodBuilder.setMethodReturnType(new Computable() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForRepeatableAnnotationContainer$_ownMethods$2$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return KtUltraLightClassForRepeatableAnnotationContainer$_ownMethods$2.invoke$lambda$0(ktUltraLightClassForRepeatableAnnotationContainer);
            }
        });
        return CollectionsKt.listOf(new KtUltraLightMethodForSourceDeclaration(lightMethodBuilder, null, this.$support, this.this$0, false, 6));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PsiType invoke$lambda$0(KtUltraLightClassForRepeatableAnnotationContainer this$0) {
        String qualifiedName;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        KtLightClass ktLightClassMo6092getContainingClass = this$0.getContainingClass();
        if (ktLightClassMo6092getContainingClass == null || (qualifiedName = ktLightClassMo6092getContainingClass.getQualifiedName()) == null) {
            return null;
        }
        return JavaPsiFacade.getElementFactory(this$0.getProject()).createTypeByFQClassName(qualifiedName, this$0.getResolveScope()).createArrayType();
    }
}
