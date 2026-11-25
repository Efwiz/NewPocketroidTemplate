package org.jetbrains.kotlin.psi.stubs;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinModifierListStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/psi/KtDeclarationModifierList;", "hasModifier", "", "modifierToken", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinModifierListStub extends StubElement<KtDeclarationModifierList> {
    boolean hasModifier(KtModifierKeywordToken modifierToken);
}
