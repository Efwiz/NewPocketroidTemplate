package org.jetbrains.kotlin.com.intellij.util;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.ParameterizedCachedValueProvider;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultCachedValuesFactory implements CachedValuesFactory {
    private final Project myProject;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else {
            objArr[0] = "project";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/DefaultCachedValuesFactory";
        if (i == 1) {
            objArr[2] = "createCachedValue";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "createParameterizedCachedValue";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    DefaultCachedValuesFactory(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myProject = project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValuesFactory
    public <T> CachedValue<T> createCachedValue(CachedValueProvider<T> cachedValueProvider, boolean z) {
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(1);
        }
        return new CachedValueImpl<T>(cachedValueProvider, z) { // from class: org.jetbrains.kotlin.com.intellij.util.DefaultCachedValuesFactory.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/util/DefaultCachedValuesFactory$1", "isFromMyProject"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueImpl, org.jetbrains.kotlin.com.intellij.util.CachedValueBase
            public boolean isFromMyProject(Project project) {
                if (project == null) {
                    $$$reportNull$$$0(0);
                }
                return DefaultCachedValuesFactory.this.myProject == project;
            }
        };
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValuesFactory
    public <T, P> ParameterizedCachedValue<T, P> createParameterizedCachedValue(ParameterizedCachedValueProvider<T, P> parameterizedCachedValueProvider, boolean z) {
        if (parameterizedCachedValueProvider == null) {
            $$$reportNull$$$0(2);
        }
        return new ParameterizedCachedValueImpl(this.myProject, parameterizedCachedValueProvider, z);
    }
}
