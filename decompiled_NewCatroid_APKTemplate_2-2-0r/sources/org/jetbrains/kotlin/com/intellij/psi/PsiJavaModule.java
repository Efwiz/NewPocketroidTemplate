package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Set;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJavaModule extends NavigatablePsiElement, PsiJavaDocumentedElement, PsiModifierListOwner, PsiNameIdentifierOwner {
    public static final String AUTO_MODULE_NAME = "Automatic-Module-Name";
    public static final String JAVA_BASE = "java.base";
    public static final String MODULE_INFO_CLASS = "module-info";
    public static final String MODULE_INFO_CLS_FILE = "module-info.class";
    public static final String MODULE_INFO_FILE = "module-info.java";
    public static final Set<String> UPGRADEABLE = ContainerUtil.immutableSet("java.activation", SystemProperties.JAVA_COMPILER, "java.corba", "java.transaction", "java.xml.bind", "java.xml.ws", "java.xml.ws.annotation", "jdk.internal.vm.compiler", "jdk.xml.bind", "jdk.xml.ws");

    Iterable<PsiPackageAccessibilityStatement> getExports();

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    PsiJavaModuleReferenceElement mo7392getNameIdentifier();

    Iterable<PsiPackageAccessibilityStatement> getOpens();

    Iterable<PsiProvidesStatement> getProvides();

    Iterable<PsiRequiresStatement> getRequires();

    Iterable<PsiUsesStatement> getUses();
}
