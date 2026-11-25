package org.jetbrains.kotlin.asJava.builder;

import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: LightElementOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0011\u001a\u00020\u0000H&J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H&J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0000H&J\b\u0010\u0016\u001a\u00020\u0013H&R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u001e\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0018\u00010\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "Lorg/jetbrains/kotlin/asJava/builder/LightElementOrigin;", "auxiliaryOriginalElement", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getAuxiliaryOriginalElement", "()Lorg/jetbrains/kotlin/psi/KtDeclaration;", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "originalElement", "getOriginalElement", "parametersForJvmOverloads", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getParametersForJvmOverloads", "()Ljava/util/List;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "isEquivalentTo", "", "other", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isValid", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LightMemberOrigin extends LightElementOrigin {
    LightMemberOrigin copy();

    default KtDeclaration getAuxiliaryOriginalElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    JvmDeclarationOriginKind getOriginKind();

    @Override // org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    KtDeclaration getOriginalElement();

    default List<KtParameter> getParametersForJvmOverloads() {
        return null;
    }

    boolean isEquivalentTo(LightMemberOrigin other);

    boolean isEquivalentTo(PsiElement other);

    boolean isValid();
}
