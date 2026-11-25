package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: StdlibClassFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/StdlibClassFinder;", "", "findEnumEntriesClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface StdlibClassFinder {
    ClassDescriptor findEnumEntriesClass(ModuleDescriptor moduleDescriptor);
}
