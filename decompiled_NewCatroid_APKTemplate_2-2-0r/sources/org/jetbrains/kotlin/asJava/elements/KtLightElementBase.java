package org.jetbrains.kotlin.asJava.elements;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtLightElementBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0017\u0010\t\u001a\t\u0018\u00010\u0003¢\u0006\u0002\b\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000eH\u0016J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\b\u0010\u0011\u001a\u00020\u0003H\u0016J\u000f\u0010\u0012\u001a\t\u0018\u00010\u0013¢\u0006\u0002\b\nH\u0016J\b\u0010\u0014\u001a\u00020\fH\u0016J\r\u0010\u0015\u001a\u00070\u0016¢\u0006\u0002\b\u0017H\u0016J\b\u0010\u0018\u001a\u00020\fH\u0016J\u0010\u0010\u0019\u001a\n \u000f*\u0004\u0018\u00010\u001a0\u001aH\u0016J\r\u0010\u001b\u001a\u00070\u001c¢\u0006\u0002\b\u001dH\u0016J\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010!\u001a\u00020\u001fH\u0016J\b\u0010\"\u001a\u00020\u001fH\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0003H\u0007J\b\u0010&\u001a\u00020\u0016H\u0016R\u0014\u0010\u0005\u001a\u0004\u0018\u00010\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightElementBase;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightElement;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)V", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtElement;", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "findElementAt", "Lorg/jetbrains/annotations/Nullable;", TypedValues.CycleType.S_WAVE_OFFSET, "", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getNavigationElement", "getParent", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "getStartOffsetInParent", "getText", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "getTextOffset", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "Lorg/jetbrains/annotations/NotNull;", "isEquivalentTo", "", "another", "isValid", "isWritable", "setParent", "", "newParent", "toString", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtLightElementBase extends LightElement {
    private PsiElement parent;

    /* JADX INFO: renamed from: getKotlinOrigin */
    public abstract KtElement mo6102getKotlinOrigin();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightElementBase(PsiElement parent) {
        super(parent.getManager(), KotlinLanguage.INSTANCE);
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.parent = parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + " of " + this.parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getParent() {
        return this.parent;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Hack for ULC")
    public final void setParent(PsiElement newParent) {
        Intrinsics.checkNotNullParameter(newParent, "newParent");
        this.parent = newParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        String text = ktElementMo6102getKotlinOrigin != null ? ktElementMo6102getKotlinOrigin.getText() : null;
        return text == null ? "" : text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        TextRange textRange;
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        return (ktElementMo6102getKotlinOrigin == null || (textRange = ktElementMo6102getKotlinOrigin.getTextRange()) == null) ? TextRange.EMPTY_RANGE : textRange;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        if (ktElementMo6102getKotlinOrigin != null) {
            return ktElementMo6102getKotlinOrigin.getTextOffset();
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        if (ktElementMo6102getKotlinOrigin != null) {
            return ktElementMo6102getKotlinOrigin.getStartOffsetInParent();
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        if (ktElementMo6102getKotlinOrigin != null) {
            return ktElementMo6102getKotlinOrigin.isWritable();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        PsiElement navigationElement = ktElementMo6102getKotlinOrigin != null ? ktElementMo6102getKotlinOrigin.getNavigationElement() : null;
        return navigationElement == null ? this : navigationElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope;
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        if (ktElementMo6102getKotlinOrigin == null || (useScope = ktElementMo6102getKotlinOrigin.getUseScope()) == null) {
            useScope = super.getUseScope();
        }
        Intrinsics.checkNotNull(useScope);
        return useScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        return this.parent.getContainingFile();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        return ItemPresentationProviders.getItemPresentation(ktElementMo6102getKotlinOrigin != null ? ktElementMo6102getKotlinOrigin : this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        KtElement ktElementMo6102getKotlinOrigin;
        return this.parent.isValid() && ((ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin()) == null || ktElementMo6102getKotlinOrigin.isValid());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int offset) {
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        if (ktElementMo6102getKotlinOrigin != null) {
            return ktElementMo6102getKotlinOrigin.findElementAt(offset);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        if (super.isEquivalentTo(another)) {
            return true;
        }
        KtElement ktElementMo6102getKotlinOrigin = mo6102getKotlinOrigin();
        if (ktElementMo6102getKotlinOrigin == null) {
            return false;
        }
        if (ktElementMo6102getKotlinOrigin.isEquivalentTo(another)) {
            return true;
        }
        return (another instanceof KtLightElementBase) && ktElementMo6102getKotlinOrigin.isEquivalentTo(((KtLightElementBase) another).mo6102getKotlinOrigin());
    }
}
