package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ModuleAnnotationsResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "", "getAnnotationsOnContainingModule", "", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleAnnotationsResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    List<ClassId> getAnnotationsOnContainingModule(DeclarationDescriptor descriptor);

    /* JADX INFO: compiled from: ModuleAnnotationsResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ModuleAnnotationsResolver getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            Object service = project.getService(ModuleAnnotationsResolver.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (ModuleAnnotationsResolver) service;
        }
    }
}
