package org.jetbrains.kotlin.backend.jvm.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: ClassBuilderExtensionAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassBuilderExtensionAdapter;", "", "()V", "getExtensions", "", "Lorg/jetbrains/kotlin/codegen/extensions/ClassBuilderInterceptorExtension;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassBuilderExtensionAdapter {
    public static final ClassBuilderExtensionAdapter INSTANCE = new ClassBuilderExtensionAdapter();

    private ClassBuilderExtensionAdapter() {
    }

    @JvmStatic
    public static final List<ClassBuilderInterceptorExtension> getExtensions(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        List<ClassGeneratorExtension> instances = ClassGeneratorExtension.INSTANCE.getInstances(project);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(instances, 10));
        Iterator<T> it = instances.iterator();
        while (it.hasNext()) {
            arrayList.add(new ExtensionAdapter((ClassGeneratorExtension) it.next()));
        }
        return arrayList;
    }
}
