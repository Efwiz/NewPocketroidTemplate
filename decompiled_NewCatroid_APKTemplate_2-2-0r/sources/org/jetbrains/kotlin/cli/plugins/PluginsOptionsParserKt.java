package org.jetbrains.kotlin.cli.plugins;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption;
import org.jetbrains.kotlin.compiler.plugin.CliOptionProcessingException;
import org.jetbrains.kotlin.compiler.plugin.CliOptionValue;
import org.jetbrains.kotlin.compiler.plugin.CliOptionsKt;
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor;
import org.jetbrains.kotlin.compiler.plugin.PluginCliOptionProcessingException;
import org.jetbrains.kotlin.config.CompilerConfiguration;

/* JADX INFO: compiled from: PluginsOptionsParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001\u001a\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b\u001a$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00062\u0006\u0010\u000f\u001a\u00020\u0010\u001a,\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"classpathOptionsDelimiter", "", "regularDelimiter", "extractPluginClasspathAndOptions", "Lorg/jetbrains/kotlin/cli/plugins/PluginClasspathAndOptions;", "pluginConfiguration", "", "pluginConfigurations", "", "processCompilerPluginOptions", "", "processor", "Lorg/jetbrains/kotlin/compiler/plugin/CommandLineProcessor;", "pluginOptions", "Lorg/jetbrains/kotlin/compiler/plugin/CliOptionValue;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "processCompilerPluginsOptions", "commandLineProcessors", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PluginsOptionsParserKt {
    public static final PluginClasspathAndOptions extractPluginClasspathAndOptions(String pluginConfiguration) {
        ArrayList arrayListEmptyList;
        List listSplit$default;
        Intrinsics.checkNotNullParameter(pluginConfiguration, "pluginConfiguration");
        String strSubstringBefore$default = StringsKt.substringBefore$default(pluginConfiguration, ContainerUtils.KEY_VALUE_DELIMITER, (String) null, 2, (Object) null);
        String strSubstringAfter = StringsKt.substringAfter(pluginConfiguration, ContainerUtils.KEY_VALUE_DELIMITER, "");
        List listSplit$default2 = StringsKt.split$default((CharSequence) strSubstringBefore$default, new String[]{","}, false, 0, 6, (Object) null);
        String str = StringsKt.isBlank(strSubstringAfter) ? null : strSubstringAfter;
        if (str == null || (listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null)) == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList();
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                CliOptionValue modernPluginOption = CliOptionsKt.parseModernPluginOption((String) it.next());
                if (modernPluginOption != null) {
                    arrayList.add(modernPluginOption);
                }
            }
            arrayListEmptyList = arrayList;
        }
        return new PluginClasspathAndOptions(pluginConfiguration, listSplit$default2, arrayListEmptyList);
    }

    public static final void processCompilerPluginOptions(CommandLineProcessor processor, List<CliOptionValue> pluginOptions, CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(pluginOptions, "pluginOptions");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Collection<AbstractCliOption> pluginOptions2 = processor.getPluginOptions();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(pluginOptions2, 10)), 16));
        for (Object obj : pluginOptions2) {
            linkedHashMap.put(((AbstractCliOption) obj).getOptionName(), obj);
        }
        MultiMap multiMap = new MultiMap();
        for (CliOptionValue cliOptionValue : pluginOptions) {
            AbstractCliOption abstractCliOption = (AbstractCliOption) linkedHashMap.get(cliOptionValue.getOptionName());
            if (abstractCliOption == null) {
                throw new CliOptionProcessingException("Unsupported plugin option: " + cliOptionValue, null, 2, null);
            }
            multiMap.putValue(abstractCliOption, cliOptionValue);
        }
        for (AbstractCliOption abstractCliOption2 : processor.getPluginOptions()) {
            Collection collection = multiMap.get(abstractCliOption2);
            Intrinsics.checkNotNullExpressionValue(collection, "get(...)");
            if (abstractCliOption2.getRequired() && collection.isEmpty()) {
                throw new PluginCliOptionProcessingException(processor.getPluginId(), processor.getPluginOptions(), "Required plugin option not present: " + processor.getPluginId() + ':' + abstractCliOption2.getOptionName(), null, 8, null);
            }
            if (!abstractCliOption2.getAllowMultipleOccurrences() && collection.size() > 1) {
                throw new PluginCliOptionProcessingException(processor.getPluginId(), processor.getPluginOptions(), "Multiple values are not allowed for plugin option " + processor.getPluginId() + ':' + abstractCliOption2.getOptionName(), null, 8, null);
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                processor.processOption(abstractCliOption2, ((CliOptionValue) it.next()).getValue(), configuration);
            }
        }
    }

    public static final List<PluginClasspathAndOptions> extractPluginClasspathAndOptions(Iterable<String> pluginConfigurations) {
        Intrinsics.checkNotNullParameter(pluginConfigurations, "pluginConfigurations");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(pluginConfigurations, 10));
        Iterator<String> it = pluginConfigurations.iterator();
        while (it.hasNext()) {
            arrayList.add(extractPluginClasspathAndOptions(it.next()));
        }
        return arrayList;
    }

    public static final void processCompilerPluginsOptions(CompilerConfiguration configuration, Iterable<String> iterable, List<? extends CommandLineProcessor> commandLineProcessors) {
        LinkedHashMap linkedHashMapEmptyMap;
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(commandLineProcessors, "commandLineProcessors");
        if (iterable == null) {
            linkedHashMapEmptyMap = MapsKt.emptyMap();
        } else {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
            Iterator<String> it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(CliOptionsKt.parseLegacyPluginOption(it.next()));
            }
            linkedHashMapEmptyMap = new LinkedHashMap();
            for (Object obj : arrayList) {
                CliOptionValue cliOptionValue = (CliOptionValue) obj;
                if (cliOptionValue == null) {
                    throw new CliOptionProcessingException("Wrong plugin option format: " + cliOptionValue + ", should be plugin:<pluginId>:<optionName>=<value>", null, 2, null);
                }
                String pluginId = cliOptionValue.getPluginId();
                Object obj2 = linkedHashMapEmptyMap.get(pluginId);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMapEmptyMap.put(pluginId, obj2);
                }
                ((List) obj2).add(obj);
            }
        }
        for (CommandLineProcessor commandLineProcessor : commandLineProcessors) {
            List listEmptyList = (List) linkedHashMapEmptyMap.get(commandLineProcessor.getPluginId());
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Intrinsics.checkNotNull(listEmptyList, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.compiler.plugin.CliOptionValue>");
            processCompilerPluginOptions(commandLineProcessor, listEmptyList, configuration);
        }
    }
}
