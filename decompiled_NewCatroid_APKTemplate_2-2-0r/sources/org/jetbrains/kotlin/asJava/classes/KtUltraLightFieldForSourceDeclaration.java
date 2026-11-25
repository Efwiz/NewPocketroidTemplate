package org.jetbrains.kotlin.asJava.classes;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightFieldForSourceDeclarationSupport;
import org.jetbrains.kotlin.asJava.elements.KtLightIdentifier;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;

/* JADX INFO: compiled from: ultraLightField.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\f¢\u0006\u0002\u0010\rJ\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0012H\u0016J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0014H\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\u000e\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldForSourceDeclaration;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightFieldImpl;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightFieldForSourceDeclarationSupport;", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "name", "", "containingClass", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "support", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "modifiers", "", "(Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;Ljava/lang/String;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;Ljava/util/Set;)V", "kotlinOrigin", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "findElementAt", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", TypedValues.CycleType.S_WAVE_OFFSET, "", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getNavigationElement", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "getStartOffsetInParent", "isWritable", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightFieldForSourceDeclaration extends KtUltraLightFieldImpl implements KtLightFieldForSourceDeclarationSupport {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightFieldForSourceDeclaration(KtNamedDeclaration declaration, String name2, KtLightClass containingClass, KtUltraLightSupport support, Set<String> modifiers) {
        super(declaration, name2, containingClass, support, modifiers);
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(support, "support");
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return new KtLightIdentifier(this, getDeclaration(), null, 4, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return getKotlinOrigin().getStartOffsetInParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return getKotlinOrigin().isWritable();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement navigationElement = getKotlinOrigin().getNavigationElement();
        return navigationElement == null ? this : navigationElement;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = getLightOwner().getContainingFile();
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

    @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightFieldImpl, org.jetbrains.kotlin.asJava.elements.KtLightElement
    public KtNamedDeclaration getKotlinOrigin() {
        return super.getKotlinOrigin();
    }
}
