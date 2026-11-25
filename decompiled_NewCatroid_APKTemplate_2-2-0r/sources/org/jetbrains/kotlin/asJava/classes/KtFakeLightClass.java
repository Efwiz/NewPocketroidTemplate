package org.jetbrains.kotlin.asJava.classes;

import com.reandroid.arsc.chunk.OverlayablePolicy;
import javax.swing.Icon;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: KtFakeLightClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0012\u001a\u00020\u0000H&J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0000H&J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0007H\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\n\u0010\u001f\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010 \u001a\u00020!H\u0016J\u0012\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u001eH\u0016J\u0018\u0010%\u001a\u00020#2\u0006\u0010&\u001a\u00020\u00072\u0006\u0010'\u001a\u00020#H&R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtFakeLightClass;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/AbstractLightClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "_delegate", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "get_delegate", "()Lcom/intellij/psi/PsiClass;", "_delegate$delegate", "Lkotlin/Lazy;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "originKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "getContainingClass", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getDelegate", "getIcon", "Ljavax/swing/Icon;", OverlayablePolicy.NAME_flags, "", "getName", "", "getNavigationElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getQualifiedName", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "isEquivalentTo", "", "another", "isInheritor", "baseClass", "checkDeep", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtFakeLightClass extends AbstractLightClass implements KtLightClass {

    /* JADX INFO: renamed from: _delegate$delegate, reason: from kotlin metadata */
    private final Lazy _delegate;
    private final KtClassOrObject kotlinOrigin;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract KtFakeLightClass copy();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public abstract KtFakeLightClass mo6092getContainingClass();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public abstract boolean isInheritor(PsiClass baseClass, boolean checkDeep);

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtClassOrObject getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFakeLightClass(KtClassOrObject kotlinOrigin) {
        super(kotlinOrigin.getManager(), KotlinLanguage.INSTANCE);
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        this.kotlinOrigin = kotlinOrigin;
        this._delegate = LazyKt.lazy(new Function0<PsiClass>() { // from class: org.jetbrains.kotlin.asJava.classes.KtFakeLightClass$_delegate$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PsiClass invoke() {
                DummyJavaPsiFactory dummyJavaPsiFactory = DummyJavaPsiFactory.INSTANCE;
                Project project = this.this$0.getKotlinOrigin().getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return dummyJavaPsiFactory.createDummyClass(project);
            }
        });
    }

    private final PsiClass get_delegate() {
        return (PsiClass) this._delegate.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.KtLightClassMarker
    public LightClassOriginKind getOriginKind() {
        return LightClassOriginKind.SOURCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        return getKotlinOrigin().getMyName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass
    /* JADX INFO: renamed from: getDelegate */
    public PsiClass mo6090getDelegate() {
        return get_delegate();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        FqName fqName = getKotlinOrigin().mo7391getFqName();
        if (fqName != null) {
            return fqName.asString();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = getKotlinOrigin().getNavigationElement();
        Intrinsics.checkNotNullExpressionValue(navigationElement, "getNavigationElement(...)");
        return navigationElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
    public Icon getIcon(int flags) {
        return getKotlinOrigin().getIcon(flags);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = getKotlinOrigin().getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = getKotlinOrigin().getUseScope();
        Intrinsics.checkNotNullExpressionValue(useScope, "getUseScope(...)");
        return useScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return PsiClassImplUtil.isClassEquivalentTo(this, another);
    }
}
