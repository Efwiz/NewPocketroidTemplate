package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.CustomJavadocTagProvider;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocManager;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocTagInfo;

/* JADX INFO: loaded from: classes3.dex */
public class JavadocManagerImpl implements JavadocManager {
    private final List<JavadocTagInfo> myInfos;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/JavadocManagerImpl", "getTagInfos"));
    }

    public JavadocManagerImpl(Project project) {
        ArrayList arrayList = new ArrayList();
        this.myInfos = arrayList;
        arrayList.add(new AuthorDocTagInfo());
        arrayList.add(new SimpleDocTagInfo("deprecated", LanguageLevel.JDK_1_3, false, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("serialData", LanguageLevel.JDK_1_3, false, PsiMethod.class));
        arrayList.add(new SimpleDocTagInfo("serialField", LanguageLevel.JDK_1_3, false, PsiField.class));
        arrayList.add(new SimpleDocTagInfo("since", LanguageLevel.JDK_1_3, false, PsiElement.class, PsiPackage.class));
        arrayList.add(new SimpleDocTagInfo("version", LanguageLevel.JDK_1_3, false, PsiClass.class, PsiPackage.class));
        arrayList.add(new SimpleDocTagInfo("apiNote", LanguageLevel.JDK_1_8, false, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("implNote", LanguageLevel.JDK_1_8, false, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("implSpec", LanguageLevel.JDK_1_8, false, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("hidden", LanguageLevel.JDK_1_9, false, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("docRoot", LanguageLevel.JDK_1_3, true, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("inheritDoc", LanguageLevel.JDK_1_4, true, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("literal", LanguageLevel.JDK_1_5, true, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, LanguageLevel.JDK_1_5, true, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("index", LanguageLevel.JDK_1_9, true, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("systemProperty", LanguageLevel.JDK_12, true, PsiElement.class));
        arrayList.add(new SimpleDocTagInfo("noinspection", LanguageLevel.JDK_1_3, false, PsiElement.class));
        arrayList.add(new ParamDocTagInfo());
        arrayList.add(new ReturnDocTagInfo());
        arrayList.add(new SerialDocTagInfo());
        arrayList.add(new SeeDocTagInfo("see", false));
        arrayList.add(new SeeDocTagInfo("link", true));
        arrayList.add(new SeeDocTagInfo("linkplain", true));
        arrayList.add(new ExceptionTagInfo(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION));
        arrayList.add(new ExceptionTagInfo(PsiKeyword.THROWS));
        arrayList.add(new ServiceReferenceTagInfo(PsiKeyword.PROVIDES));
        arrayList.add(new ServiceReferenceTagInfo(PsiKeyword.USES));
        arrayList.add(new ValueDocTagInfo());
        Collections.addAll(arrayList, JavadocTagInfo.EP_NAME.getExtensions(project));
        Iterator<CustomJavadocTagProvider> it = CustomJavadocTagProvider.EP_NAME.getExtensionList().iterator();
        while (it.hasNext()) {
            this.myInfos.addAll(it.next().getSupportedTags());
        }
        JavadocTagInfo.EP_NAME.getPoint(project).addExtensionPointListener(new ExtensionPointListener<JavadocTagInfo>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.JavadocManagerImpl.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1 || i == 3) {
                    objArr[0] = "pluginDescriptor";
                } else {
                    objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/JavadocManagerImpl$1";
                if (i == 2 || i == 3) {
                    objArr[2] = "extensionRemoved";
                } else {
                    objArr[2] = "extensionAdded";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
            public void extensionAdded(JavadocTagInfo javadocTagInfo, PluginDescriptor pluginDescriptor) {
                if (javadocTagInfo == null) {
                    $$$reportNull$$$0(0);
                }
                if (pluginDescriptor == null) {
                    $$$reportNull$$$0(1);
                }
                JavadocManagerImpl.this.myInfos.add(javadocTagInfo);
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
            public void extensionRemoved(JavadocTagInfo javadocTagInfo, PluginDescriptor pluginDescriptor) {
                if (javadocTagInfo == null) {
                    $$$reportNull$$$0(2);
                }
                if (pluginDescriptor == null) {
                    $$$reportNull$$$0(3);
                }
                JavadocManagerImpl.this.myInfos.remove(javadocTagInfo);
            }
        }, false, project);
        CustomJavadocTagProvider.EP_NAME.addExtensionPointListener(new ExtensionPointListener<CustomJavadocTagProvider>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc.JavadocManagerImpl.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1 || i == 3) {
                    objArr[0] = "pluginDescriptor";
                } else {
                    objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                }
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/JavadocManagerImpl$2";
                if (i == 2 || i == 3) {
                    objArr[2] = "extensionRemoved";
                } else {
                    objArr[2] = "extensionAdded";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
            public void extensionAdded(CustomJavadocTagProvider customJavadocTagProvider, PluginDescriptor pluginDescriptor) {
                if (customJavadocTagProvider == null) {
                    $$$reportNull$$$0(0);
                }
                if (pluginDescriptor == null) {
                    $$$reportNull$$$0(1);
                }
                JavadocManagerImpl.this.myInfos.addAll(customJavadocTagProvider.getSupportedTags());
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointListener
            public void extensionRemoved(CustomJavadocTagProvider customJavadocTagProvider, PluginDescriptor pluginDescriptor) {
                if (customJavadocTagProvider == null) {
                    $$$reportNull$$$0(2);
                }
                if (pluginDescriptor == null) {
                    $$$reportNull$$$0(3);
                }
                JavadocManagerImpl.this.myInfos.removeAll(customJavadocTagProvider.getSupportedTags());
            }
        }, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocManager
    public JavadocTagInfo[] getTagInfos(PsiElement psiElement) {
        ArrayList arrayList = new ArrayList();
        for (JavadocTagInfo javadocTagInfo : this.myInfos) {
            if (javadocTagInfo.isValidInContext(psiElement)) {
                arrayList.add(javadocTagInfo);
            }
        }
        JavadocTagInfo[] javadocTagInfoArr = (JavadocTagInfo[]) arrayList.toArray(new JavadocTagInfo[0]);
        if (javadocTagInfoArr == null) {
            $$$reportNull$$$0(0);
        }
        return javadocTagInfoArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.JavadocManager
    public JavadocTagInfo getTagInfo(String str) {
        for (JavadocTagInfo javadocTagInfo : this.myInfos) {
            if (javadocTagInfo.getName().equals(str)) {
                return javadocTagInfo;
            }
        }
        return null;
    }
}
