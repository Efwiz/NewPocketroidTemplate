package org.jetbrains.kotlin.fir.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;

/* JADX INFO: compiled from: FirAnalysisHandlerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirAnalysisHandlerExtension;", "", "()V", "doAnalysis", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "isApplicable", "Companion", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAnalysisHandlerExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract boolean doAnalysis(CompilerConfiguration configuration);

    public abstract boolean isApplicable(CompilerConfiguration configuration);

    /* JADX INFO: compiled from: FirAnalysisHandlerExtension.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u001d\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirAnalysisHandlerExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/fir/extensions/FirAnalysisHandlerExtension;", "()V", "analyze", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Ljava/lang/Boolean;", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<FirAnalysisHandlerExtension> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
            super("org.jetbrains.kotlin.fir.firAnalyzeCompleteHandlerExtension", FirAnalysisHandlerExtension.class);
        }

        public final Boolean analyze(Project project, CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            List<FirAnalysisHandlerExtension> instances = FirAnalysisHandlerExtension.INSTANCE.getInstances(project);
            ArrayList arrayList = new ArrayList();
            for (Object obj : instances) {
                if (((FirAnalysisHandlerExtension) obj).isApplicable(configuration)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return null;
            }
            ArrayList arrayList3 = arrayList2;
            boolean z = true;
            if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    if (!((FirAnalysisHandlerExtension) it.next()).doAnalysis(configuration)) {
                        z = false;
                        break;
                    }
                }
            }
            return Boolean.valueOf(z);
        }
    }
}
