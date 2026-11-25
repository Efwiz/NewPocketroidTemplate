package org.jetbrains.kotlin.resolve.typeBinding;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypeBinding.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003R \u0010\u0004\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0012\u0010\f\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000bR\u0012\u0010\r\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/typeBinding/TypeBinding;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "arguments", "", "Lorg/jetbrains/kotlin/resolve/typeBinding/TypeArgumentBinding;", "getArguments", "()Ljava/util/List;", "isArgumentFromQualifier", "", "()Z", "isInAbbreviation", "psiElement", "getPsiElement", "()Lcom/intellij/psi/PsiElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeBinding<P extends PsiElement> {
    List<TypeArgumentBinding<P>> getArguments();

    P getPsiElement();

    KotlinType getType();

    default boolean isArgumentFromQualifier() {
        return false;
    }

    boolean isInAbbreviation();
}
