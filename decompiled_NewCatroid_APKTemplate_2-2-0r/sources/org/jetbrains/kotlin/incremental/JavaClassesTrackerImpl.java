package org.jetbrains.kotlin.incremental;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.load.java.JavaClassesTracker;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: JavaClassesTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\fH\u0016J\f\u0010\u001b\u001a\u00020\u001c*\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/incremental/JavaClassesTrackerImpl;", "Lorg/jetbrains/kotlin/load/java/JavaClassesTracker;", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;", "untrackedJavaClasses", "", "Lorg/jetbrains/kotlin/name/ClassId;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/util/Set;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "classDescriptors", "", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "classToSourceSerialized", "", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClassWithSource;", "javaClassesUpdates", "", "getJavaClassesUpdates", "()Ljava/util/Collection;", LineReader.CLEAR, "", "onCompletedAnalysis", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "reportClass", "classDescriptor", "wasContentRequested", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassesTrackerImpl implements JavaClassesTracker {
    private final IncrementalJvmCache cache;
    private final List<JavaClassDescriptor> classDescriptors;
    private final Map<ClassId, SerializedJavaClassWithSource> classToSourceSerialized;
    private final LanguageVersionSettings languageVersionSettings;
    private final Set<ClassId> untrackedJavaClasses;

    public JavaClassesTrackerImpl(IncrementalJvmCache cache, Set<ClassId> untrackedJavaClasses, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(cache, "cache");
        Intrinsics.checkNotNullParameter(untrackedJavaClasses, "untrackedJavaClasses");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.cache = cache;
        this.untrackedJavaClasses = untrackedJavaClasses;
        this.languageVersionSettings = languageVersionSettings;
        this.classToSourceSerialized = new HashMap();
        this.classDescriptors = new ArrayList();
    }

    public final Collection<SerializedJavaClassWithSource> getJavaClassesUpdates() {
        return this.classToSourceSerialized.values();
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassesTracker
    public void reportClass(JavaClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor);
        Intrinsics.checkNotNull(classId);
        if (!this.cache.isJavaClassToTrack(classId) || JavaClassesTrackerImplKt.getJavaSourceFile(classDescriptor) == null) {
            return;
        }
        this.classDescriptors.add(classDescriptor);
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassesTracker
    public void onCompletedAnalysis(ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Iterator it = CollectionsKt.plus((Collection) this.cache.getObsoleteJavaClasses(), (Iterable) this.untrackedJavaClasses).iterator();
        while (it.hasNext()) {
            FindClassInModuleKt.findClassAcrossModuleDependencies(module, (ClassId) it.next());
        }
        for (final JavaClassDescriptor javaClassDescriptor : CollectionsKt.toList(this.classDescriptors)) {
            ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) javaClassDescriptor);
            Intrinsics.checkNotNull(classId);
            if (this.cache.isJavaClassAlreadyInCache(classId) || this.untrackedJavaClasses.contains(classId) || wasContentRequested(javaClassDescriptor)) {
                this.classToSourceSerialized.containsKey(classId);
                this.classToSourceSerialized.put(classId, (SerializedJavaClassWithSource) JavaClassesTrackerImplKt.getCONVERTING_JAVA_CLASSES_TO_PROTO().time(new Function0<SerializedJavaClassWithSource>() { // from class: org.jetbrains.kotlin.incremental.JavaClassesTrackerImpl.onCompletedAnalysis.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final SerializedJavaClassWithSource invoke() {
                        return JavaClassesTrackerImplKt.convertToProto(javaClassDescriptor, this.languageVersionSettings);
                    }
                }));
            }
        }
    }

    @Override // org.jetbrains.kotlin.load.java.JavaClassesTracker
    public void clear() {
        this.classToSourceSerialized.clear();
        this.classDescriptors.clear();
    }

    private final boolean wasContentRequested(JavaClassDescriptor javaClassDescriptor) {
        if (!(javaClassDescriptor instanceof LazyJavaClassDescriptor)) {
            javaClassDescriptor = null;
        }
        LazyJavaClassDescriptor lazyJavaClassDescriptor = (LazyJavaClassDescriptor) javaClassDescriptor;
        boolean z = false;
        if (lazyJavaClassDescriptor != null && !lazyJavaClassDescriptor.wasScopeContentRequested()) {
            z = true;
        }
        return !z;
    }
}
