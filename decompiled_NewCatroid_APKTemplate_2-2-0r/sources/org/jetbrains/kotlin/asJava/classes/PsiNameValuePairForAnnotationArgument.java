package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;

/* JADX INFO: compiled from: ultraLightAnnotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B#\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010\u0014\u001a\u0004\u0018\u00010\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0004H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\n\u0010\u0018\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/PsiNameValuePairForAnnotationArgument;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair;", "_name", "", "constantValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;Lcom/intellij/psi/PsiElement;)V", "_value", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotationMemberValue;", "get_value", "()Lcom/intellij/psi/PsiAnnotationMemberValue;", "_value$delegate", "Lkotlin/Lazy;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getLiteralValue", "getName", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightIdentifier;", "getValue", "setValue", "", "newValue", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class PsiNameValuePairForAnnotationArgument extends KtLightElementBase implements PsiNameValuePair {
    private final String _name;

    /* JADX INFO: renamed from: _value$delegate, reason: from kotlin metadata */
    private final Lazy _value;
    private final ConstantValue<?> constantValue;

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtElement getKotlinOrigin() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    /* JADX INFO: renamed from: setValue, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ PsiAnnotationMemberValue mo6100setValue(PsiAnnotationMemberValue psiAnnotationMemberValue) {
        return (PsiAnnotationMemberValue) setValue(psiAnnotationMemberValue);
    }

    public /* synthetic */ PsiNameValuePairForAnnotationArgument(String str, ConstantValue constantValue, PsiElement psiElement, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str, constantValue, psiElement);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiNameValuePairForAnnotationArgument(String _name, ConstantValue<?> constantValue, PsiElement parent) {
        super(parent);
        Intrinsics.checkNotNullParameter(_name, "_name");
        Intrinsics.checkNotNullParameter(constantValue, "constantValue");
        Intrinsics.checkNotNullParameter(parent, "parent");
        this._name = _name;
        this.constantValue = constantValue;
        this._value = ImplUtilsKt.lazyPub(new Function0<PsiAnnotationMemberValue>() { // from class: org.jetbrains.kotlin.asJava.classes.PsiNameValuePairForAnnotationArgument$_value$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiAnnotationMemberValue invoke() {
                return UltraLightAnnotationsKt.toAnnotationMemberValue(this.this$0.constantValue, this.this$0);
            }
        });
    }

    private final PsiAnnotationMemberValue get_value() {
        return (PsiAnnotationMemberValue) this._value.getValue();
    }

    public Void setValue(PsiAnnotationMemberValue newValue) {
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        ImplUtilsKt.cannotModify(this);
        throw null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair
    public LightIdentifier getNameIdentifier() {
        return new LightIdentifier(getThis$0().getManager(), this._name);
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName, reason: from getter */
    public String get_name() {
        return this._name;
    }
}
