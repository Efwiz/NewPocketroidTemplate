package org.jetbrains.kotlin.com.intellij.psi;

import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPatternBean;
import org.jetbrains.kotlin.com.intellij.patterns.StandardPatterns;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Property;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Tag;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.XCollection;

/* JADX INFO: loaded from: classes3.dex */
public class PsiReferenceProviderBean implements PluginAware, KeyedLazyInstance<PsiReferenceProviderBean> {
    public static final ExtensionPointName<PsiReferenceProviderBean> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.psi.referenceProvider");

    @Attribute("providerClass")
    public String className;

    @Tag(MediaTrack.ROLE_DESCRIPTION)
    public String description;

    @Attribute("language")
    public String language = Language.ANY.getID();

    @XCollection
    @Property(surroundWithTag = false)
    public ElementPatternBean[] patterns;
    private PluginDescriptor pluginDescriptor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceProviderBean";
        } else {
            objArr[0] = "pluginDescriptor";
        }
        if (i == 1) {
            objArr[1] = "getKey";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceProviderBean";
        } else {
            objArr[1] = "getInstance";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "setPluginDescriptor";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance
    public PsiReferenceProviderBean getInstance() {
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public PsiReferenceProvider instantiate() {
        try {
            return (PsiReferenceProvider) ApplicationManager.getApplication().instantiateClass(this.className, this.pluginDescriptor);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Exception e2) {
            Logger.getInstance((Class<?>) PsiReferenceProviderBean.class).error((Throwable) e2);
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginAware
    @Transient
    public void setPluginDescriptor(PluginDescriptor pluginDescriptor) {
        if (pluginDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        this.pluginDescriptor = pluginDescriptor;
    }

    public ElementPattern<PsiElement> createElementPattern() {
        ElementPatternBean[] elementPatternBeanArr = this.patterns;
        if (elementPatternBeanArr.length > 1) {
            List arrayList = new ArrayList(this.patterns.length);
            for (ElementPatternBean elementPatternBean : this.patterns) {
                ElementPattern<PsiElement> elementPatternCompilePattern = elementPatternBean.compilePattern();
                if (elementPatternCompilePattern != null) {
                    arrayList.add(elementPatternCompilePattern);
                }
            }
            if (arrayList.isEmpty()) {
                arrayList = Collections.emptyList();
            }
            return StandardPatterns.or((ElementPattern[]) arrayList.toArray(new ElementPattern[0]));
        }
        if (elementPatternBeanArr.length == 1) {
            return elementPatternBeanArr[0].compilePattern();
        }
        Logger.getInstance((Class<?>) PsiReferenceProviderBean.class).error("At least one pattern should be specified");
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance
    public String getKey() {
        String str = this.language;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }
}
