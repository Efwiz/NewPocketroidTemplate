package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import javax.swing.Icon;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: KtDescriptorBasedFakeLightClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002:\u0001\u0019B\u0017\b\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0011\u001a\f0\u0012¢\u0006\u0002\b\u0013¢\u0006\u0002\b\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0003H\u0016J\r\u0010\u0016\u001a\u00070\u0017¢\u0006\u0002\b\u0018H\u0016R\u0014\u0010\t\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightMethod;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightMethod;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "ktDeclaration", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "kotlinOrigin", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "getKtDeclaration", "getIcon", "Ljavax/swing/Icon;", OverlayablePolicy.NAME_flags, "", "getName", "", "Lorg/jetbrains/annotations/Nullable;", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "getNavigationElement", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "Lorg/jetbrains/annotations/NotNull;", "Companion", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtFakeLightMethod extends LightMethod implements KtLightElement<KtNamedDeclaration, PsiMethod> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KtNamedDeclaration ktDeclaration;

    public /* synthetic */ KtFakeLightMethod(KtNamedDeclaration ktNamedDeclaration, KtClassOrObject ktClassOrObject, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktNamedDeclaration, ktClassOrObject);
    }

    public final KtNamedDeclaration getKtDeclaration() {
        return this.ktDeclaration;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private KtFakeLightMethod(KtNamedDeclaration ktNamedDeclaration, KtClassOrObject ktClassOrObject) {
        PsiManager manager = ktNamedDeclaration.getManager();
        DummyJavaPsiFactory dummyJavaPsiFactory = DummyJavaPsiFactory.INSTANCE;
        Project project = ktNamedDeclaration.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        super(manager, dummyJavaPsiFactory.createDummyVoidMethod(project), LightClassUtilsKt.toFakeLightClass(ktClassOrObject), KotlinLanguage.INSTANCE);
        this.ktDeclaration = ktNamedDeclaration;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    /* JADX INFO: renamed from: getKotlinOrigin, reason: from getter */
    public KtNamedDeclaration getKtDeclaration() {
        return this.ktDeclaration;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = this.ktDeclaration.getName();
        return name2 == null ? "" : name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtNamedDeclaration getNavigationElement() {
        return this.ktDeclaration;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
    public Icon getIcon(int flags) {
        return this.ktDeclaration.getIcon(flags);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightMethod, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = this.ktDeclaration.getUseScope();
        Intrinsics.checkNotNullExpressionValue(useScope, "getUseScope(...)");
        return useScope;
    }

    /* JADX INFO: compiled from: KtDescriptorBasedFakeLightClass.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightMethod$Companion;", "", "()V", "get", "Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightMethod;", "ktDeclaration", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KtFakeLightMethod get(KtNamedDeclaration ktDeclaration) {
            Intrinsics.checkNotNullParameter(ktDeclaration, "ktDeclaration");
            KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(ktDeclaration);
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (containingClassOrObject == null) {
                return null;
            }
            return new KtFakeLightMethod(ktDeclaration, containingClassOrObject, defaultConstructorMarker);
        }
    }
}
