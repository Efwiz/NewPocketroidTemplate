package org.jetbrains.kotlin.resolve.jvm.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.resolve.jvm.CompositeSyntheticJavaPartsProvider;
import org.jetbrains.kotlin.resolve.jvm.SyntheticJavaPartsProvider;

/* JADX INFO: compiled from: SyntheticJavaResolveExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0005À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/extensions/SyntheticJavaResolveExtension;", "", "buildProvider", "Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticJavaResolveExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    SyntheticJavaPartsProvider buildProvider();

    /* JADX INFO: compiled from: SyntheticJavaResolveExtension.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/extensions/SyntheticJavaResolveExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/resolve/jvm/extensions/SyntheticJavaResolveExtension;", "()V", "getProvider", "Lorg/jetbrains/kotlin/resolve/jvm/SyntheticJavaPartsProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<SyntheticJavaResolveExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.syntheticJavaResolveExtension", SyntheticJavaResolveExtension.class);
        }

        public final SyntheticJavaPartsProvider getProvider(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            List<SyntheticJavaResolveExtension> instances = getInstances(project);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(instances, 10));
            Iterator<T> it = instances.iterator();
            while (it.hasNext()) {
                arrayList.add(((SyntheticJavaResolveExtension) it.next()).buildProvider());
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return SyntheticJavaPartsProvider.INSTANCE.getEMPTY();
            }
            return new CompositeSyntheticJavaPartsProvider(arrayList2);
        }
    }
}
