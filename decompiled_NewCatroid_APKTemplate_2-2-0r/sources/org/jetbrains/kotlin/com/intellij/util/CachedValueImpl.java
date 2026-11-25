package org.jetbrains.kotlin.com.intellij.util;

import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;

/* JADX INFO: loaded from: classes3.dex */
public class CachedValueImpl<T> extends CachedValueBase<T> implements CachedValue<T> {
    private final CachedValueProvider<T> myProvider;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/CachedValueImpl";
        } else if (i != 3) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else {
            objArr[0] = "project";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/CachedValueImpl";
        } else {
            objArr[1] = "getValueProvider";
        }
        if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "isFromMyProject";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    public boolean isFromMyProject(Project project) {
        if (project != null) {
            return true;
        }
        $$$reportNull$$$0(3);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValue
    public /* bridge */ /* synthetic */ Getter getUpToDateOrNull() {
        return super.getUpToDateOrNull();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CachedValueImpl(CachedValueProvider<T> cachedValueProvider) {
        this(cachedValueProvider, false);
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CachedValueImpl(CachedValueProvider<T> cachedValueProvider, boolean z) {
        super(z);
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(1);
        }
        this.myProvider = cachedValueProvider;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    /* JADX INFO: renamed from: doCompute */
    public <P> CachedValueProvider.Result<T> m6701x9c9e63ff(P p) {
        return this.myProvider.compute();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CachedValueBase
    public CachedValueProvider<T> getValueProvider() {
        CachedValueProvider<T> cachedValueProvider = this.myProvider;
        if (cachedValueProvider == null) {
            $$$reportNull$$$0(2);
        }
        return cachedValueProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValue
    public T getValue() {
        return getValueWithLock(null);
    }
}
