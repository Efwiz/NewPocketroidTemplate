package org.jetbrains.kotlin.analyzer;

import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: ModuleInfoUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\u001a\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0000\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0002*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0017\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0010"}, d2 = {"JDK_CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "", "getJDK_CAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "hasJdkCapability", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getHasJdkCapability", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Z", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getModuleInfo", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "collectAllExpectedByModules", "", "entryModule", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleInfoUtilsKt {
    private static final ModuleCapability<Boolean> JDK_CAPABILITY = new ModuleCapability<>("IsJdk");

    public static final ModuleInfo getModuleInfo(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return (ModuleInfo) moduleDescriptor.getCapability(ModuleInfo.INSTANCE.getCapability());
    }

    public static final Set<ModuleInfo> collectAllExpectedByModules(ModuleInfo entryModule) {
        Intrinsics.checkNotNullParameter(entryModule, "entryModule");
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.addAll(entryModule.getExpectedBy());
        HashSet hashSet = new HashSet();
        while (!arrayDeque.isEmpty()) {
            ModuleInfo moduleInfo = (ModuleInfo) arrayDeque.removeFirst();
            if (hashSet.add(moduleInfo)) {
                arrayDeque.addAll(moduleInfo.getExpectedBy());
            }
        }
        return hashSet;
    }

    public static final ModuleCapability<Boolean> getJDK_CAPABILITY() {
        return JDK_CAPABILITY;
    }

    public static final boolean getHasJdkCapability(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return Intrinsics.areEqual(moduleDescriptor.getCapability(JDK_CAPABILITY), (Object) true);
    }
}
