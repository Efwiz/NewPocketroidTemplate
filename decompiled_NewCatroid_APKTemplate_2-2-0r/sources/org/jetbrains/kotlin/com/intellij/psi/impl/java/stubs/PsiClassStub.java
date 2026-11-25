package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiClassStub<T extends PsiClass> extends PsiMemberStub<T> {
    String getBaseClassReferenceText();

    @Deprecated
    default LanguageLevel getLanguageLevel() {
        return null;
    }

    String getQualifiedName();

    String getSourceFileName();

    boolean isAnnotationType();

    boolean isAnonymous();

    boolean isAnonymousInQualifiedNew();

    boolean isEnum();

    boolean isEnumConstantInitializer();

    boolean isInterface();

    default boolean isRecord() {
        return false;
    }
}
