package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacadeFactory;

/* JADX INFO: compiled from: DummyJavaFileCodeStyleFacadeFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/DummyJavaFileCodeStyleFacadeFactory;", "Lorg/jetbrains/kotlin/com/intellij/psi/codeStyle/JavaFileCodeStyleFacadeFactory;", "()V", "createFacade", "Lorg/jetbrains/kotlin/com/intellij/psi/codeStyle/JavaFileCodeStyleFacade;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "DummyJavaFileCodeStyleFacade", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DummyJavaFileCodeStyleFacadeFactory implements JavaFileCodeStyleFacadeFactory {

    /* JADX INFO: compiled from: DummyJavaFileCodeStyleFacadeFactory.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\bH\u0016J\b\u0010\u000b\u001a\u00020\bH\u0016J\b\u0010\f\u001a\u00020\bH\u0016J\b\u0010\r\u001a\u00020\bH\u0016J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\bH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/DummyJavaFileCodeStyleFacadeFactory$DummyJavaFileCodeStyleFacade;", "Lorg/jetbrains/kotlin/com/intellij/psi/codeStyle/JavaFileCodeStyleFacade;", "()V", "getIndentSize", "", "getNamesCountToUseImportOnDemand", "getTabSize", "isGenerateFinalLocals", "", "isGenerateFinalParameters", "isJavaDocLeadingAsterisksEnabled", "isSpaceAfterComma", "isSpaceAroundAssignmentOperators", "isSpaceBeforeComma", "isToImportOnDemand", "qualifiedName", "", "useFQClassNames", "withLanguage", "Lorg/jetbrains/kotlin/com/intellij/psi/codeStyle/CodeStyleSettingsFacade;", "language", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DummyJavaFileCodeStyleFacade implements JavaFileCodeStyleFacade {
        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade
        public int getIndentSize() {
            return 4;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade
        public int getNamesCountToUseImportOnDemand() {
            return 0;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade
        public int getTabSize() {
            return 4;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade
        public boolean isGenerateFinalLocals() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade
        public boolean isGenerateFinalParameters() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade
        public boolean isJavaDocLeadingAsterisksEnabled() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade
        public boolean isSpaceAfterComma() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade
        public boolean isSpaceAroundAssignmentOperators() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade
        public boolean isSpaceBeforeComma() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade
        public boolean isToImportOnDemand(String qualifiedName) {
            Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacade
        public boolean useFQClassNames() {
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleSettingsFacade
        public CodeStyleSettingsFacade withLanguage(Language language) {
            Intrinsics.checkNotNullParameter(language, "language");
            return new DummyJavaFileCodeStyleFacade();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacadeFactory
    public JavaFileCodeStyleFacade createFacade(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        return new DummyJavaFileCodeStyleFacade();
    }
}
