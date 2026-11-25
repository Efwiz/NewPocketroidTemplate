package org.jetbrains.kotlin.resolve.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtImportInfo;

/* JADX INFO: compiled from: ExtraImportsProviderExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/ExtraImportsProviderExtension;", "", "getExtraImports", "", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ExtraImportsProviderExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Collection<KtImportInfo> getExtraImports(KtFile ktFile);

    /* JADX INFO: compiled from: ExtraImportsProviderExtension.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/ExtraImportsProviderExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/resolve/extensions/ExtraImportsProviderExtension;", "()V", "getInstance", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "CompoundExtraImportsProviderExtension", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<ExtraImportsProviderExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.extraImportsProviderExtension", ExtraImportsProviderExtension.class);
        }

        /* JADX INFO: compiled from: ExtraImportsProviderExtension.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/extensions/ExtraImportsProviderExtension$Companion$CompoundExtraImportsProviderExtension;", "Lorg/jetbrains/kotlin/resolve/extensions/ExtraImportsProviderExtension;", "instances", "", "(Ljava/util/List;)V", "getInstances", "()Ljava/util/List;", "getExtraImports", "", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class CompoundExtraImportsProviderExtension implements ExtraImportsProviderExtension {
            private final List<ExtraImportsProviderExtension> instances;

            /* JADX WARN: Multi-variable type inference failed */
            public CompoundExtraImportsProviderExtension(List<? extends ExtraImportsProviderExtension> instances) {
                Intrinsics.checkNotNullParameter(instances, "instances");
                this.instances = instances;
            }

            public final List<ExtraImportsProviderExtension> getInstances() {
                return this.instances;
            }

            @Override // org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension
            public Collection<KtImportInfo> getExtraImports(KtFile ktFile) {
                Intrinsics.checkNotNullParameter(ktFile, "ktFile");
                List<ExtraImportsProviderExtension> list = this.instances;
                ArrayList arrayList = new ArrayList();
                for (ExtraImportsProviderExtension extraImportsProviderExtension : list) {
                    try {
                        CollectionsKt.addAll(arrayList, extraImportsProviderExtension.getExtraImports(ktFile));
                    } catch (LinkageError e) {
                        Logger logger = Logger.getInstance(extraImportsProviderExtension.getClass());
                        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
                        logger.error(extraImportsProviderExtension.getClass().getName() + " caused LinkageError", e);
                        throw e;
                    }
                }
                return arrayList;
            }
        }

        public final ExtraImportsProviderExtension getInstance(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            List<ExtraImportsProviderExtension> instances = getInstances(project);
            ExtraImportsProviderExtension extraImportsProviderExtension = (ExtraImportsProviderExtension) CollectionsKt.singleOrNull((List) instances);
            return extraImportsProviderExtension == null ? new CompoundExtraImportsProviderExtension(instances) : extraImportsProviderExtension;
        }
    }
}
