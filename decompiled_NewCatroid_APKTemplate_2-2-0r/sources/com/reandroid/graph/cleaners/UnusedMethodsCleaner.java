package com.reandroid.graph.cleaners;

import com.reandroid.apk.ApkModule;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.dex.model.DexInstruction;
import com.reandroid.dex.model.DexMethod;
import com.reandroid.graph.ApkBuildOption;
import com.reandroid.utils.collection.ArrayCollection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class UnusedMethodsCleaner extends UnusedClassComponentCleaner<DexMethod> {
    private Set<MethodKey> unusedInternalMethods;

    public UnusedMethodsCleaner(ApkBuildOption apkBuildOption, ApkModule apkModule, DexClassRepository dexClassRepository) {
        super(apkBuildOption, apkModule, dexClassRepository);
    }

    @Override // com.reandroid.graph.cleaners.UnusedCleaner
    protected boolean isEnabled() {
        return getBuildOption().isMinifyMethods();
    }

    @Override // com.reandroid.graph.cleaners.UnusedClassComponentCleaner
    protected List<DexMethod> listUnusedInClass(DexClass dexClass) {
        Iterator<DexMethod> declaredMethods = dexClass.getDeclaredMethods();
        ArrayCollection arrayCollection = null;
        while (declaredMethods.hasNext()) {
            DexMethod next = declaredMethods.next();
            if (isUnusedMethod(next)) {
                if (arrayCollection == null) {
                    arrayCollection = new ArrayCollection();
                }
                arrayCollection.add(next);
            }
        }
        return arrayCollection;
    }

    private boolean isUnusedMethod(DexMethod dexMethod) {
        if (dexMethod.isConstructor() && dexMethod.isStatic()) {
            return false;
        }
        return isUnusedPrivateMethod(dexMethod) || isUnusedVirtualMethod(dexMethod);
    }

    private boolean isUnusedPrivateMethod(DexMethod dexMethod) {
        if (!dexMethod.isPrivate() || dexMethod.isConstructor()) {
            return false;
        }
        MethodKey key = dexMethod.getKey();
        Iterator<DexInstruction> dexInstructions = dexMethod.getDexClass().getDexInstructions();
        while (dexInstructions.hasNext()) {
            if (key.equals(dexInstructions.next().getMethodKey())) {
                return false;
            }
        }
        return true;
    }

    private boolean isUnusedVirtualMethod(DexMethod dexMethod) {
        return getUnusedInternalMethods().contains(dexMethod.getKey());
    }

    public Set<MethodKey> getUnusedInternalMethods() {
        Set<MethodKey> set = this.unusedInternalMethods;
        if (set != null) {
            return set;
        }
        loadUnusedInternalMethods();
        return this.unusedInternalMethods;
    }

    private void loadUnusedInternalMethods() {
        HashSet hashSet = new HashSet();
        Iterator<DexClass> dexClasses = getClassRepository().getDexClasses();
        while (dexClasses.hasNext()) {
            DexClass next = dexClasses.next();
            if (!next.usesNative()) {
                Iterator<DexMethod> declaredMethods = next.getDeclaredMethods();
                while (declaredMethods.hasNext()) {
                    DexMethod next2 = declaredMethods.next();
                    if (isInternal(next2)) {
                        hashSet.add(next2.getKey());
                    }
                }
            }
        }
        subtractUnused(hashSet);
        this.unusedInternalMethods = hashSet;
        Iterator<MethodKey> it = hashSet.iterator();
        while (it.hasNext()) {
            debug(it.next().toString());
        }
        debug("Internal methods: " + hashSet.size());
    }

    private void subtractUnused(Set<MethodKey> set) {
        if (set.isEmpty()) {
            return;
        }
        DexClassRepository classRepository = getClassRepository();
        Iterator<DexClass> dexClasses = classRepository.getDexClasses();
        while (dexClasses.hasNext()) {
            Iterator<DexInstruction> dexInstructions = dexClasses.next().getDexInstructions();
            while (dexInstructions.hasNext()) {
                MethodKey methodKey = dexInstructions.next().getMethodKey();
                if (methodKey != null) {
                    set.remove(methodKey);
                    Iterator<MethodKey> itFindEquivalentMethods = classRepository.findEquivalentMethods(methodKey);
                    while (itFindEquivalentMethods.hasNext()) {
                        set.remove(itFindEquivalentMethods.next());
                    }
                }
            }
        }
    }

    private boolean isInternal(DexMethod dexMethod) {
        if (dexMethod.isConstructor()) {
            return false;
        }
        return ((!dexMethod.isInternal() && !dexMethod.isStatic()) || dexMethod.getSuperMethods().hasNext() || dexMethod.getExtending().hasNext()) ? false : true;
    }
}
