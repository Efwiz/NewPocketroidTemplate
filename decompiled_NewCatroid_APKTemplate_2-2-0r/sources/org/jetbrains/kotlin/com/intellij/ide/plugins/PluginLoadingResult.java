package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.util.BuildNumber;
import org.jetbrains.kotlin.com.intellij.util.PlatformUtils;
import org.jetbrains.kotlin.com.intellij.util.text.VersionComparatorUtil;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: PluginLoadingResult.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0007\u0018\u00002\u00020\u0001B9\u0012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00050\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0016\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u000bJ\u001f\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u00102\b\u0010'\u001a\u0004\u0018\u00010\u001cH\u0000¢\u0006\u0002\b(J\u0018\u0010)\u001a\u00020%2\u0006\u0010\"\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u0004H\u0002J\u0006\u0010+\u001a\u00020,J\u0006\u0010-\u001a\u00020%J\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00100\u0012J\u0012\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\b0\u0012J\u0019\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001c0\u0003H\u0000¢\u0006\u0002\b1J\u000e\u00102\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u0010J\u000e\u00102\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u0004J\u0010\u00103\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u0010H\u0002J\u0018\u00104\u001a\u00020%2\u0006\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000108R\"\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\r\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f\u0018\u00010\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000RJ\u0010\u0013\u001a>\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0006 \u0014*\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b0\b \u0014*\u001e\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0006 \u0014*\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b0\b\u0018\u00010\u00120\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u000b8G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00100\u00198\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00100\u00198\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001c0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00100\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040 8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginLoadingResult;", "", "brokenPluginVersions", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "", "", "productBuildNumber", "Ljava/util/function/Supplier;", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/BuildNumber;", "checkModuleDependencies", "", "(Ljava/util/Map;Ljava/util/function/Supplier;Z)V", "duplicateModuleMap", "", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptorImpl;", "enabledPlugins", "", "globalErrors", JvmProtoBufUtil.PLATFORM_TYPE_ID, "hasPluginErrors", "getHasPluginErrors", "()Z", "idMap", "Ljava/util/concurrent/ConcurrentHashMap;", "incompletePlugins", "pluginErrors", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginLoadingError;", "plugins", "Ljava/util/HashMap;", "shadowedBundledIds", "Ljava/util/HashSet;", ProductAction.ACTION_ADD, "descriptor", "overrideUseIfCompatible", "addIncompletePlugin", "", "plugin", "error", "addIncompletePlugin$intellij_platform_core_impl", "checkAndAdd", "id", "enabledPluginCount", "", "finishLoading", "getEnabledPlugins", "getGlobalErrors", "getPluginErrors", "getPluginErrors$intellij_platform_core_impl", "isBroken", "isCompatible", "reportCannotLoad", "file", "Ljava/nio/file/Path;", "e", "", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class PluginLoadingResult {
    private final Map<PluginId, Set<String>> brokenPluginVersions;
    private final boolean checkModuleDependencies;
    private final List<Supplier<String>> globalErrors;
    public final ConcurrentHashMap<PluginId, IdeaPluginDescriptorImpl> idMap;
    public final ConcurrentHashMap<PluginId, IdeaPluginDescriptorImpl> incompletePlugins;
    private final ConcurrentHashMap<PluginId, PluginLoadingError> pluginErrors;
    private final HashMap<PluginId, IdeaPluginDescriptorImpl> plugins;
    public final Supplier<BuildNumber> productBuildNumber;
    public final HashSet<PluginId> shadowedBundledIds;

    /* JADX WARN: Multi-variable type inference failed */
    public PluginLoadingResult(Map<PluginId, ? extends Set<String>> brokenPluginVersions, Supplier<BuildNumber> productBuildNumber, boolean z) {
        Intrinsics.checkNotNullParameter(brokenPluginVersions, "brokenPluginVersions");
        Intrinsics.checkNotNullParameter(productBuildNumber, "productBuildNumber");
        this.brokenPluginVersions = brokenPluginVersions;
        this.productBuildNumber = productBuildNumber;
        this.checkModuleDependencies = z;
        this.incompletePlugins = new ConcurrentHashMap<>();
        this.plugins = new HashMap<>();
        this.idMap = new ConcurrentHashMap<>();
        this.pluginErrors = new ConcurrentHashMap<>();
        this.globalErrors = Collections.synchronizedList(new ArrayList());
        this.shadowedBundledIds = new HashSet<>();
    }

    public /* synthetic */ PluginLoadingResult(Map map, Supplier supplier, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, supplier, (i & 4) != 0 ? !PlatformUtils.isIntelliJ() : z);
    }

    public final boolean isBroken(PluginId id2) {
        IdeaPluginDescriptorImpl ideaPluginDescriptorImpl;
        Intrinsics.checkNotNullParameter(id2, "id");
        Set<String> set = this.brokenPluginVersions.get(id2);
        return (set == null || (ideaPluginDescriptorImpl = this.idMap.get(id2)) == null || !set.contains(ideaPluginDescriptorImpl.getVersion())) ? false : true;
    }

    public final boolean isBroken(IdeaPluginDescriptorImpl descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Set<String> set = this.brokenPluginVersions.get(descriptor.getId());
        if (set != null) {
            return set.contains(descriptor.getVersion());
        }
        return false;
    }

    public final void addIncompletePlugin$intellij_platform_core_impl(IdeaPluginDescriptorImpl plugin, PluginLoadingError error) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        if (this.idMap.containsKey(plugin.getId())) {
            return;
        }
        IdeaPluginDescriptorImpl ideaPluginDescriptorImplPutIfAbsent = this.incompletePlugins.putIfAbsent(plugin.getId(), plugin);
        if (ideaPluginDescriptorImplPutIfAbsent == null || VersionComparatorUtil.compare(plugin.getVersion(), ideaPluginDescriptorImplPutIfAbsent.getVersion()) <= 0) {
            if (error != null) {
                this.pluginErrors.putIfAbsent(plugin.getId(), error);
            }
        } else {
            this.incompletePlugins.put(plugin.getId(), plugin);
            if (error != null) {
                this.pluginErrors.put(plugin.getId(), error);
            }
        }
    }

    public final void reportCannotLoad(final Path file, Throwable e) {
        Intrinsics.checkNotNullParameter(file, "file");
        PluginManagerCore.getLogger().warn("Cannot load " + file, e);
        this.globalErrors.add(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.PluginLoadingResult.reportCannotLoad.1
            @Override // java.util.function.Supplier
            public final String get() {
                return CoreBundle.message("plugin.loading.error.text.file.contains.invalid.plugin.descriptor", IdeaPluginDescriptorImplKt.pluginPathToUserString(file));
            }
        });
    }
}
