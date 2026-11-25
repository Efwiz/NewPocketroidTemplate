package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder;

/* JADX INFO: compiled from: MetadataFinderFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/MetadataFinderFactory;", "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MetadataFinderFactory {
    KotlinMetadataFinder create(Project project, ModuleDescriptor module);

    KotlinMetadataFinder create(GlobalSearchScope scope);
}
