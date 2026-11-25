package org.jetbrains.kotlin.com.intellij.ide.plugins;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.util.XmlInterner;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: DescriptorListLoadingContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002BE\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\t¢\u0006\u0002\u0010\rJ\u0016\u0010&\u001a\u00020\t2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020)J\b\u0010*\u001a\u00020+H\u0016J\u000e\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\u0005R$\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f8F@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0017R\u0010\u0010\n\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000RR\u0010\u001a\u001aF\u0012\u001c\u0012\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001d \u001e*\f\u0012\u0006\u0012\u0004\u0018\u00010\u001d\u0018\u00010\u001c0\u001c \u001e*\"\u0012\u001c\u0012\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001d \u001e*\f\u0012\u0006\u0012\u0004\u0018\u00010\u001d\u0018\u00010\u001c0\u001c\u0018\u00010\u001b0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u001d0\u001c0 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010!\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000f0#8F¢\u0006\u0006\u001a\u0004\b$\u0010%¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/DescriptorListLoadingContext;", "Ljava/lang/AutoCloseable;", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ReadModuleContext;", "disabledPlugins", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "result", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginLoadingResult;", "isMissingIncludeIgnored", "", "isMissingSubDescriptorIgnored", "checkOptionalConfigFileUniqueness", "transient", "(Ljava/util/Set;Lcom/intellij/ide/plugins/PluginLoadingResult;ZZZZ)V", "<set-?>", "", "defaultVersion", "getDefaultVersion", "()Ljava/lang/String;", "interner", "Lorg/jetbrains/kotlin/com/intellij/util/XmlInterner;", "getInterner", "()Lcom/intellij/util/XmlInterner;", "()Z", "optionalConfigNames", "", "threadLocalXmlFactory", "Ljava/lang/ThreadLocal;", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/MyXmlInterner;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "toDispose", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "usePluginClassLoader", "visitedFiles", "", "getVisitedFiles", "()Ljava/util/List;", "checkOptionalConfigShortName", "configFile", "descriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptor;", "close", "", "isPluginDisabled", "id", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class DescriptorListLoadingContext implements ReadModuleContext, AutoCloseable {
    private volatile String defaultVersion;
    public final Set<PluginId> disabledPlugins;
    private final boolean isMissingIncludeIgnored;
    public final boolean isMissingSubDescriptorIgnored;
    private final Map<String, PluginId> optionalConfigNames;
    public final PluginLoadingResult result;
    private final ThreadLocal<MyXmlInterner[]> threadLocalXmlFactory;
    private final ConcurrentLinkedQueue<MyXmlInterner[]> toDispose;
    public final boolean transient;
    public boolean usePluginClassLoader;

    public DescriptorListLoadingContext(Set<PluginId> disabledPlugins, PluginLoadingResult result, boolean z, boolean z2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(disabledPlugins, "disabledPlugins");
        Intrinsics.checkNotNullParameter(result, "result");
        this.disabledPlugins = disabledPlugins;
        this.result = result;
        this.isMissingIncludeIgnored = z;
        this.isMissingSubDescriptorIgnored = z2;
        this.transient = z4;
        this.toDispose = new ConcurrentLinkedQueue<>();
        this.threadLocalXmlFactory = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.DescriptorListLoadingContext$threadLocalXmlFactory$1
            @Override // java.util.function.Supplier
            public final MyXmlInterner[] get() {
                MyXmlInterner[] myXmlInternerArr = {new MyXmlInterner()};
                this.this$0.toDispose.add(myXmlInternerArr);
                return myXmlInternerArr;
            }
        });
        this.usePluginClassLoader = !PluginManagerCore.isUnitTestMode || DescriptorListLoadingContextKt.unitTestWithBundledPlugins;
        this.optionalConfigNames = z3 ? new ConcurrentHashMap() : null;
    }

    public /* synthetic */ DescriptorListLoadingContext(Set set, PluginLoadingResult pluginLoadingResult, boolean z, boolean z2, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, (i & 2) != 0 ? PluginDescriptorLoader.createPluginLoadingResult(null) : pluginLoadingResult, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4);
    }

    @Override // org.jetbrains.kotlin.com.intellij.ide.plugins.ReadModuleContext
    /* JADX INFO: renamed from: isMissingIncludeIgnored, reason: from getter */
    public boolean getIsMissingIncludeIgnored() {
        return this.isMissingIncludeIgnored;
    }

    public final String getDefaultVersion() {
        String str = this.defaultVersion;
        if (str != null) {
            return str;
        }
        String strAsStringWithoutProductCode = this.result.productBuildNumber.get().asStringWithoutProductCode();
        this.defaultVersion = strAsStringWithoutProductCode;
        return strAsStringWithoutProductCode;
    }

    public final boolean isPluginDisabled(PluginId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return !Intrinsics.areEqual(PluginManagerCore.CORE_ID, id2) && this.disabledPlugins.contains(id2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.ide.plugins.ReadModuleContext
    public XmlInterner getInterner() {
        MyXmlInterner myXmlInterner = this.threadLocalXmlFactory.get()[0];
        Intrinsics.checkNotNull(myXmlInterner);
        return myXmlInterner;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        Iterator<MyXmlInterner[]> it = this.toDispose.iterator();
        while (it.hasNext()) {
            it.next()[0] = null;
        }
    }

    public final List<String> getVisitedFiles() {
        MyXmlInterner myXmlInterner = this.threadLocalXmlFactory.get()[0];
        Intrinsics.checkNotNull(myXmlInterner);
        return myXmlInterner.visitedFiles;
    }

    public final boolean checkOptionalConfigShortName(String configFile, IdeaPluginDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(configFile, "configFile");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Map<String, PluginId> map = this.optionalConfigNames;
        if (map != null && !StringsKt.startsWith$default(configFile, "intellij.", false, 2, (Object) null)) {
            PluginId pluginId = descriptor.getId();
            Intrinsics.checkNotNullExpressionValue(pluginId, "descriptor.pluginId");
            PluginId pluginIdPut = map.put(configFile, pluginId);
            if (pluginIdPut != null && !Intrinsics.areEqual(pluginIdPut, pluginId)) {
                DescriptorListLoadingContextKt.getLOG().error("Optional config file with name " + configFile + " already registered by " + pluginIdPut + ". Please rename to ensure that lookup in the classloader by short name returns correct optional config. Current plugin: " + descriptor + '.');
                return true;
            }
        }
        return false;
    }
}
