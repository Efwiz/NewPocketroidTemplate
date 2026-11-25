package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ModuleDependenciesDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \b2\u00020\u0001:\u0002\b\tB\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016R\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginContentDescriptor;", "", "modules", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginContentDescriptor$ModuleItem;", "(Ljava/util/List;)V", "toString", "", "Companion", "ModuleItem", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class PluginContentDescriptor {
    public static final PluginContentDescriptor EMPTY;
    public final List<ModuleItem> modules;

    public PluginContentDescriptor(List<ModuleItem> modules) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        this.modules = modules;
    }

    static {
        List listEmptyList = Collections.emptyList();
        Intrinsics.checkNotNullExpressionValue(listEmptyList, "Collections.emptyList()");
        EMPTY = new PluginContentDescriptor(listEmptyList);
    }

    /* JADX INFO: compiled from: ModuleDependenciesDescriptor.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u0006\u0010\b\u001a\u00020\u0007J\b\u0010\t\u001a\u00020\u0003H\u0016R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginContentDescriptor$ModuleItem;", "", "name", "", "configFile", "(Ljava/lang/String;Ljava/lang/String;)V", "descriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptorImpl;", "requireDescriptor", "toString", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
    public static final class ModuleItem {
        public final String configFile;
        public IdeaPluginDescriptorImpl descriptor;
        public final String name;

        public ModuleItem(String name2, String str) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.configFile = str;
        }

        public String toString() {
            return "ModuleItem(name=" + this.name + ", descriptor=" + this.descriptor + ", configFile=" + this.configFile + ')';
        }
    }

    public String toString() {
        return "PluginContentDescriptor(modules=" + this.modules + ')';
    }
}
