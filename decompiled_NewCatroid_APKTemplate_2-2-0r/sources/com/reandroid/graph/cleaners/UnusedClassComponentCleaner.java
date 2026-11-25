package com.reandroid.graph.cleaners;

import com.reandroid.apk.ApkModule;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.model.Dex;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.model.DexDeclaration;
import com.reandroid.graph.ApkBuildOption;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public abstract class UnusedClassComponentCleaner<T extends Dex> extends UnusedCleaner<T> {
    protected abstract List<T> listUnusedInClass(DexClass dexClass);

    public UnusedClassComponentCleaner(ApkBuildOption apkBuildOption, ApkModule apkModule, DexClassRepository dexClassRepository) {
        super(apkBuildOption, apkModule, dexClassRepository);
    }

    @Override // com.reandroid.graph.GraphTask
    public void apply() {
        if (!isEnabled()) {
            debug("Skip");
            return;
        }
        verbose("Searching for unused ...");
        cleanUnusedInCleanableClasses();
        verbose("Cleaned: " + getCount());
    }

    protected void cleanUnusedInCleanableClasses() {
        Iterator<DexClass> cleanableClasses = getCleanableClasses();
        while (cleanableClasses.hasNext()) {
            cleanUnusedInClass(cleanableClasses.next());
        }
    }

    protected void cleanUnusedInClass(DexClass dexClass) {
        List<T> listListUnusedInClass = listUnusedInClass(dexClass);
        if (listListUnusedInClass != null) {
            boolean zIsDebugEnabled = isDebugEnabled();
            for (T t : listListUnusedInClass) {
                if (zIsDebugEnabled) {
                    debug(getDebugString(t));
                }
                t.removeSelf();
                addCount();
            }
        }
    }

    protected Iterator<DexClass> getCleanableClasses() {
        return getDexClasses(new Predicate() { // from class: com.reandroid.graph.cleaners.UnusedClassComponentCleaner$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.isCleanableClass((DexClass) obj);
            }
        });
    }

    protected boolean isCleanableClass(DexClass dexClass) {
        if (dexClass.usesNative() || dexClass.isEnum()) {
            return false;
        }
        Predicate<? super TypeKey> keepClasses = getBuildOption().getKeepClasses();
        return keepClasses == null || !keepClasses.test(dexClass.getKey());
    }

    protected String getDebugString(T t) {
        if (t instanceof DexDeclaration) {
            return ((DexDeclaration) t).getKey().toString();
        }
        return t.toSmaliString();
    }
}
