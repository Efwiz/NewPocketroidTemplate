package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: VirtualFileFinderFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinderFactory;", "Lorg/jetbrains/kotlin/load/kotlin/MetadataFinderFactory;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "SERVICE", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface VirtualFileFinderFactory extends MetadataFinderFactory {

    /* JADX INFO: renamed from: SERVICE, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    @Override // org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory
    VirtualFileFinder create(Project project, ModuleDescriptor module);

    @Override // org.jetbrains.kotlin.load.kotlin.MetadataFinderFactory
    VirtualFileFinder create(GlobalSearchScope scope);

    /* JADX INFO: renamed from: org.jetbrains.kotlin.load.kotlin.VirtualFileFinderFactory$SERVICE, reason: from kotlin metadata */
    /* JADX INFO: compiled from: VirtualFileFinderFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinderFactory$SERVICE;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinderFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final VirtualFileFinderFactory getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(VirtualFileFinderFactory.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (VirtualFileFinderFactory) service;
        }
    }
}
