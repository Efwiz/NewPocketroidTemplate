package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: compiled from: ModuleDependenciesDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u0000 \n2\u00020\u0001:\u0003\n\u000b\fB!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016R\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor;", "", "modules", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor$ModuleReference;", "plugins", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor$PluginReference;", "(Ljava/util/List;Ljava/util/List;)V", "toString", "", "Companion", "ModuleReference", "PluginReference", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class ModuleDependenciesDescriptor {
    public static final ModuleDependenciesDescriptor EMPTY;
    public final List<ModuleReference> modules;
    public final List<PluginReference> plugins;

    public ModuleDependenciesDescriptor(List<ModuleReference> modules, List<PluginReference> plugins) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(plugins, "plugins");
        this.modules = modules;
        this.plugins = plugins;
    }

    static {
        List listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "Collections.emptyList()");
        List listEmptyList2 = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList2, "Collections.emptyList()");
        EMPTY = new ModuleDependenciesDescriptor(listEmptyList, listEmptyList2);
    }

    /* JADX INFO: compiled from: ModuleDependenciesDescriptor.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor$ModuleReference;", "", "name", "", "(Ljava/lang/String;)V", "toString", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
    public static final class ModuleReference {
        public final String name;

        public ModuleReference(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public String toString() {
            return "ModuleItem(name=" + this.name + ')';
        }
    }

    /* JADX INFO: compiled from: ModuleDependenciesDescriptor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor$PluginReference;", "", "id", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "(Lcom/intellij/openapi/extensions/PluginId;)V", "toString", "", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
    public static final class PluginReference {
        public final PluginId id;

        public PluginReference(PluginId id2) {
            Intrinsics.checkNotNullParameter(id2, "id");
            this.id = id2;
        }

        public String toString() {
            return "PluginReference(id=" + this.id + ')';
        }
    }

    public String toString() {
        return "ModuleDependenciesDescriptor(modules=" + this.modules + ", plugins=" + this.plugins + ')';
    }
}
