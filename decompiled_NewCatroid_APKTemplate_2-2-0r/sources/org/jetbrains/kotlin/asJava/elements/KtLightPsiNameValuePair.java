package org.jetbrains.kotlin.asJava.elements;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtLightAnnotationsValues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n¢\u0006\u0002\u0010\fJ\n\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0006H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\n\u0010\u0018\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u000bH\u0016R\u001d\u0010\r\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightPsiNameValuePair;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "name", "", "lightParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "argument", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "(Lorg/jetbrains/kotlin/psi/KtElement;Ljava/lang/String;Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function1;)V", "_value", "get_value", "()Lcom/intellij/psi/PsiAnnotationMemberValue;", "_value$delegate", "Lkotlin/Lazy;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getLiteralValue", "getName", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getValue", "setValue", "newValue", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtLightPsiNameValuePair extends KtLightElementBase implements PsiNameValuePair {

    /* JADX INFO: renamed from: _value$delegate, reason: from kotlin metadata */
    private final Lazy _value;
    private final Function1<KtLightPsiNameValuePair, PsiAnnotationMemberValue> argument;
    private final KtElement kotlinOrigin;
    private final String name;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtElement getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtLightPsiNameValuePair(KtElement kotlinOrigin, String name2, PsiElement lightParent, Function1<? super KtLightPsiNameValuePair, ? extends PsiAnnotationMemberValue> argument) {
        super(lightParent);
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lightParent, "lightParent");
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.kotlinOrigin = kotlinOrigin;
        this.name = name2;
        this.argument = argument;
        this._value = ImplUtilsKt.lazyPub(new Function0<PsiAnnotationMemberValue>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightPsiNameValuePair$_value$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiAnnotationMemberValue invoke() {
                return (PsiAnnotationMemberValue) this.this$0.argument.invoke(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    /* JADX INFO: renamed from: setValue */
    public PsiAnnotationMemberValue mo6100setValue(PsiAnnotationMemberValue newValue) {
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        ImplUtilsKt.cannotModify(this);
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiIdentifier getNameIdentifier() {
        return new LightIdentifier(getKotlinOrigin().getManager(), this.name);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        return this.name;
    }

    private final PsiAnnotationMemberValue get_value() {
        return (PsiAnnotationMemberValue) this._value.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public PsiAnnotationMemberValue getValue() {
        return get_value();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public String getLiteralValue() {
        Object value;
        PsiAnnotationMemberValue value2 = getValue();
        PsiLiteralExpression psiLiteralExpression = value2 instanceof PsiLiteralExpression ? (PsiLiteralExpression) value2 : null;
        if (psiLiteralExpression == null || (value = psiLiteralExpression.getValue()) == null) {
            return null;
        }
        return value.toString();
    }
}
