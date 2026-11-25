package org.jetbrains.kotlin.asJava.classes;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightParameters.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\n\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0017H\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u001fH\u0016J\u0012\u0010!\u001a\u00020\u00152\b\b\u0001\u0010\u0002\u001a\u00020\u0003H\u0016J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0014R\u001c\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightParameterForSource;", "Lorg/jetbrains/kotlin/asJava/classes/KtAbstractUltraLightParameterForDeclaration;", "name", "", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtParameter;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "method", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;", "containingDeclaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/psi/KtParameter;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightMethod;Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;)V", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtParameter;", "findElementAt", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", TypedValues.CycleType.S_WAVE_OFFSET, "", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getNavigationElement", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "getStartOffsetInParent", "isVarArgs", "", "isWritable", "setName", "tryGetKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightParameterForSource extends KtAbstractUltraLightParameterForDeclaration {
    private final KtParameter kotlinOrigin;

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtParameter getKotlinOrigin() {
        return this.kotlinOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightParameterForSource(String name2, KtParameter kotlinOrigin, KtUltraLightSupport support, KtUltraLightMethod method, KtCallableDeclaration containingDeclaration) {
        super(name2, kotlinOrigin, support, method, containingDeclaration);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kotlinOrigin, "kotlinOrigin");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        this.kotlinOrigin = kotlinOrigin;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtAbstractUltraLightParameterForDeclaration
    protected KotlinType tryGetKotlinType() {
        return UltraLightUtilsKt.getKotlinType(getKotlinOrigin());
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.asJava.elements.LightParameter, org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    /* JADX INFO: renamed from: isVarArgs */
    public boolean getMyVarArgs() {
        if (getKotlinOrigin().isVarArg()) {
            PsiParameter[] parameters = getMethod().getParameterList().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            if (Intrinsics.areEqual(ArraysKt.last(parameters), this)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        getKotlinOrigin().setName(name2);
        return this;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public List<KtLightAbstractAnnotation> getGivenAnnotations() {
        if (getKotlinOrigin().hasValOrVar()) {
            List<KtAnnotationEntry> annotationEntries = getKotlinOrigin().getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : annotationEntries) {
                Name shortName = ((KtAnnotationEntry) obj).getShortName();
                if (!Intrinsics.areEqual(shortName != null ? shortName.getIdentifier() : null, "JvmField")) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            KtUltraLightParameterForSource ktUltraLightParameterForSource = this;
            return CollectionsKt.plus((Collection) UltraLightUtilsKt.toLightAnnotations(arrayList2, ktUltraLightParameterForSource, null), (Iterable) UltraLightUtilsKt.toLightAnnotations(arrayList2, ktUltraLightParameterForSource, AnnotationUseSiteTarget.CONSTRUCTOR_PARAMETER));
        }
        List<KtAnnotationEntry> annotationEntries2 = getKotlinOrigin().getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries2, "getAnnotationEntries(...)");
        return UltraLightUtilsKt.toLightAnnotations(annotationEntries2, this, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return getKotlinOrigin().getStartOffsetInParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return getKotlinOrigin().isWritable();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = getKotlinOrigin().getNavigationElement();
        Intrinsics.checkNotNullExpressionValue(navigationElement, "getNavigationElement(...)");
        return navigationElement;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightParameter, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = getLightParent().getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(getKotlinOrigin());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int offset) {
        return getKotlinOrigin().findElementAt(offset);
    }
}
