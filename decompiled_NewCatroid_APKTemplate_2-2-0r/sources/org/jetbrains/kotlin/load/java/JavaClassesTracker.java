package org.jetbrains.kotlin.load.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: JavaClassesTracker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\nJ\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", "", LineReader.CLEAR, "", "onCompletedAnalysis", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "reportClass", "classDescriptor", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "Default", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaClassesTracker {
    void clear();

    void onCompletedAnalysis(ModuleDescriptor module);

    void reportClass(JavaClassDescriptor classDescriptor);

    /* JADX INFO: compiled from: JavaClassesTracker.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/load/java/JavaClassesTracker$Default;", "Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", "()V", LineReader.CLEAR, "", "onCompletedAnalysis", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "reportClass", "classDescriptor", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements JavaClassesTracker {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.load.java.JavaClassesTracker
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.load.java.JavaClassesTracker
        public void onCompletedAnalysis(ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
        }

        @Override // org.jetbrains.kotlin.load.java.JavaClassesTracker
        public void reportClass(JavaClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        }

        private Default() {
        }
    }
}
