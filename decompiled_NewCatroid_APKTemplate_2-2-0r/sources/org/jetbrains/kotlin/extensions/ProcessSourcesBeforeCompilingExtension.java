package org.jetbrains.kotlin.extensions;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: ProcessSourcesBeforeCompilingExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/ProcessSourcesBeforeCompilingExtension;", "", "processSources", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "sources", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ProcessSourcesBeforeCompilingExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    Collection<KtFile> processSources(Collection<? extends KtFile> sources, CompilerConfiguration configuration);

    /* JADX INFO: compiled from: ProcessSourcesBeforeCompilingExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/extensions/ProcessSourcesBeforeCompilingExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/extensions/ProcessSourcesBeforeCompilingExtension;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<ProcessSourcesBeforeCompilingExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.processSourcesBeforeCompilingExtension", ProcessSourcesBeforeCompilingExtension.class);
        }
    }
}
