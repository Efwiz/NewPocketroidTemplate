package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public interface ReferenceAdjuster {
    ASTNode process(ASTNode aSTNode, boolean z, boolean z2, Project project);

    ASTNode process(ASTNode aSTNode, boolean z, boolean z2, boolean z3, boolean z4);

    void processRange(ASTNode aSTNode, int i, int i2, Project project);

    void processRange(ASTNode aSTNode, int i, int i2, boolean z, boolean z2);

    public static class Extension extends LanguageExtension<ReferenceAdjuster> {
        public static final Extension INSTANCE = new Extension();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "language", "org/jetbrains/kotlin/com/intellij/psi/codeStyle/ReferenceAdjuster$Extension", "getReferenceAdjuster"));
        }

        public Extension() {
            super("org.jetbrains.kotlin.com.intellij.codeStyle.ReferenceAdjuster");
        }

        public static ReferenceAdjuster getReferenceAdjuster(Language language) {
            if (language == null) {
                $$$reportNull$$$0(0);
            }
            return INSTANCE.forLanguage(language);
        }
    }
}
