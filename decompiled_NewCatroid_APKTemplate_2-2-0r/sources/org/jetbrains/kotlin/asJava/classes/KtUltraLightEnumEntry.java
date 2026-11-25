package org.jetbrains.kotlin.asJava.classes;

import java.util.Set;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;

/* JADX INFO: compiled from: ultraLightEnumEntry.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f¢\u0006\u0002\u0010\rJ\u0018\u0010\u0017\u001a\u00020\u00002\u000e\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019H\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u001eH\u0016J\b\u0010 \u001a\u00020!H\u0016J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\n\u0010$\u001a\u0004\u0018\u00010#H\u0016J\b\u0010%\u001a\u00020&H\u0016R\u001d\u0010\u000e\u001a\u0004\u0018\u00010\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightEnumEntry;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstant;", "declaration", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "name", "", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "modifiers", "", "(Lorg/jetbrains/kotlin/psi/KtEnumEntry;Ljava/lang/String;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Ljava/util/Set;)V", "_initializingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForEnumEntry;", "get_initializingClass", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForEnumEntry;", "_initializingClass$delegate", "Lkotlin/Lazy;", "enumEntry", "getEnumEntry", "()Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "computeConstantValue", "visitedVars", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiVariable;", "getArgumentList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiExpressionList;", "getInitializingClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiEnumConstantInitializer;", "getOrCreateInitializingClass", "hasInitializer", "", "resolveConstructor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "resolveMethod", "resolveMethodGenerics", "Lorg/jetbrains/kotlin/com/intellij/psi/JavaResolveResult;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightEnumEntry extends KtUltraLightFieldImpl implements PsiEnumConstant {

    /* JADX INFO: renamed from: _initializingClass$delegate, reason: from kotlin metadata */
    private final Lazy _initializingClass;

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx
    public KtUltraLightEnumEntry computeConstantValue(Set<PsiVariable> visitedVars) {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant, org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiExpressionList getArgumentList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getOrCreateInitializingClass() {
        KtUltraLightClassForEnumEntry ktUltraLightClassForEnumEntry = get_initializingClass();
        if (ktUltraLightClassForEnumEntry != null) {
            return ktUltraLightClassForEnumEntry;
        }
        throw new IllegalStateException("cannot create initializing class in light enum constant".toString());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightFieldBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstructorCall
    public PsiMethod resolveConstructor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public PsiMethod resolveMethod() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiVariableEx
    public /* bridge */ /* synthetic */ Object computeConstantValue(Set set) {
        return computeConstantValue((Set<PsiVariable>) set);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightEnumEntry(KtEnumEntry declaration, String name2, KtLightClass containingClass, final KtUltraLightSupport support, Set<String> modifiers) {
        super(declaration, name2, containingClass, support, modifiers);
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        this._initializingClass = ImplUtilsKt.lazyPub(new Function0<KtUltraLightClassForEnumEntry>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightEnumEntry$_initializingClass$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightClassForEnumEntry invoke() {
                if (this.this$0.getEnumEntry().getBody() == null) {
                    return null;
                }
                KtUltraLightEnumEntry ktUltraLightEnumEntry = this.this$0;
                return new KtUltraLightClassForEnumEntry(ktUltraLightEnumEntry.getEnumEntry(), support, ktUltraLightEnumEntry);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KtEnumEntry getEnumEntry() {
        KtNamedDeclaration declaration = getDeclaration();
        Intrinsics.checkNotNull(declaration, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtEnumEntry");
        return (KtEnumEntry) declaration;
    }

    private final KtUltraLightClassForEnumEntry get_initializingClass() {
        return (KtUltraLightClassForEnumEntry) this._initializingClass.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant
    public PsiEnumConstantInitializer getInitializingClass() {
        return get_initializingClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    public JavaResolveResult resolveMethodGenerics() {
        JavaResolveResult EMPTY = JavaResolveResult.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
        return EMPTY;
    }
}
