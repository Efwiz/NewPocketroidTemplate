package org.jetbrains.kotlin.analysis.project.structure;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;

/* JADX INFO: compiled from: KtCompilerPluginsProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0000\u0010\u0005*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\tH&J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtCompilerPluginsProvider;", "", "()V", "getRegisteredExtensions", "", ExifInterface.GPS_DIRECTION_TRUE, "module", "Lorg/jetbrains/kotlin/analysis/project/structure/KtSourceModule;", "extensionType", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "isPluginOfTypeRegistered", "", "pluginType", "Lorg/jetbrains/kotlin/analysis/project/structure/KtCompilerPluginsProvider$CompilerPluginType;", "CompilerPluginType", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtCompilerPluginsProvider {

    /* JADX INFO: compiled from: KtCompilerPluginsProvider.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0003\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtCompilerPluginsProvider$CompilerPluginType;", "", "(Ljava/lang/String;I)V", "ASSIGNMENT", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum CompilerPluginType {
        ASSIGNMENT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<CompilerPluginType> getEntries() {
            return $ENTRIES;
        }
    }

    public abstract <T> List<T> getRegisteredExtensions(KtSourceModule module, ProjectExtensionDescriptor<T> extensionType);

    public abstract boolean isPluginOfTypeRegistered(KtSourceModule module, CompilerPluginType pluginType);
}
