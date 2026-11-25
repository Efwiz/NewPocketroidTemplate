package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;

/* JADX INFO: compiled from: ValueArgument.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0005H'J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\n\u0010\b\u001a\u0004\u0018\u00010\tH&J\b\u0010\n\u001a\u00020\u000bH&J\b\u0010\f\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/ValueArgument;", "", "asElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "getArgumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getArgumentName", "Lorg/jetbrains/kotlin/psi/ValueArgumentName;", "getSpreadElement", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LeafPsiElement;", "isExternal", "", "isNamed", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ValueArgument {
    KtElement asElement();

    @IfNotParsed
    KtExpression getArgumentExpression();

    ValueArgumentName getArgumentName();

    LeafPsiElement getSpreadElement();

    boolean isExternal();

    boolean isNamed();
}
