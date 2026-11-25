package org.jetbrains.kotlin.analysis.decompiled.light.classes.origin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.asJava.builder.LightMemberOrigin;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;

/* JADX INFO: compiled from: LightMemberOriginForCompiledElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0016R\u0012\u0010\u0004\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/origin/LightMemberOriginForCompiledElement;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMember;", "Lorg/jetbrains/kotlin/asJava/builder/LightMemberOrigin;", "member", "getMember", "()Lcom/intellij/psi/PsiMember;", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "isEquivalentTo", "", "other", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isValid", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LightMemberOriginForCompiledElement<T extends PsiMember> extends LightMemberOrigin {
    T getMember();

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin, org.jetbrains.kotlin.asJava.builder.LightElementOrigin
    default JvmDeclarationOriginKind getOriginKind() {
        return JvmDeclarationOriginKind.OTHER;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    default boolean isEquivalentTo(PsiElement other) {
        if (!(other instanceof KtDeclaration)) {
            if (other instanceof PsiMember) {
                return getMember().isEquivalentTo(other);
            }
            return false;
        }
        KtDeclaration originalElement = getOriginalElement();
        if (originalElement != null) {
            return originalElement.isEquivalentTo(other);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.asJava.builder.LightMemberOrigin
    default boolean isValid() {
        return getMember().isValid();
    }
}
