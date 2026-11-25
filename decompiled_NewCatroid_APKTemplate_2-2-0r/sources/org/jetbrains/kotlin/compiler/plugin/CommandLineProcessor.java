package org.jetbrains.kotlin.compiler.plugin;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;

/* JADX INFO: compiled from: CommandLineProcessor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0010!\n\u0000\bg\u0018\u00002\u00020\u0001J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0017J3\u0010\u0012\u001a\u00020\f\"\u0004\b\u0000\u0010\u0013*\u00020\u00102\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00150\u00142\u0006\u0010\u000e\u001a\u0002H\u0013H\u0016¢\u0006\u0002\u0010\u0016J4\u0010\u0012\u001a\u00020\f\"\u0004\b\u0000\u0010\u0013*\u00020\u00102\u0012\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u00150\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00130\u0015H\u0016J4\u0010\u0018\u001a\u00020\f*\u00020\u00102\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00150\u001a2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00130\u001c\"\u0004\b\u0000\u0010\u0013*\b\u0012\u0004\u0012\u0002H\u00130\u0015H\u0002R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/CommandLineProcessor;", "", "pluginId", "", "getPluginId", "()Ljava/lang/String;", "pluginOptions", "", "Lorg/jetbrains/kotlin/compiler/plugin/AbstractCliOption;", "getPluginOptions", "()Ljava/util/Collection;", "processOption", "", "option", "value", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "Lorg/jetbrains/kotlin/compiler/plugin/CliOption;", "appendList", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;Ljava/lang/Object;)V", CompoundEntry.NAME_values, "applyOptionsFrom", "map", "", "asMutableList", "", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalCompilerApi
public interface CommandLineProcessor {
    String getPluginId();

    Collection<AbstractCliOption> getPluginOptions();

    @Deprecated(message = "Implement processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) instead.")
    default void processOption(CliOption option, String value, CompilerConfiguration configuration) throws CliOptionProcessingException {
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
    }

    /* JADX INFO: compiled from: CommandLineProcessor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        @Deprecated
        public static void processOption(CommandLineProcessor commandLineProcessor, AbstractCliOption option, String value, CompilerConfiguration configuration) throws CliOptionProcessingException {
            Intrinsics.checkNotNullParameter(option, "option");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            CommandLineProcessor.super.processOption(option, value, configuration);
        }

        @Deprecated(message = "Implement processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) instead.")
        @Deprecated
        public static void processOption(CommandLineProcessor commandLineProcessor, CliOption option, String value, CompilerConfiguration configuration) throws CliOptionProcessingException {
            Intrinsics.checkNotNullParameter(option, "option");
            Intrinsics.checkNotNullParameter(value, "value");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            CommandLineProcessor.super.processOption(option, value, configuration);
        }

        @Deprecated
        public static <T> void appendList(CommandLineProcessor commandLineProcessor, CompilerConfiguration receiver, CompilerConfigurationKey<List<T>> option, T t) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(option, "option");
            CommandLineProcessor.super.appendList(receiver, option, t);
        }

        @Deprecated
        public static <T> void appendList(CommandLineProcessor commandLineProcessor, CompilerConfiguration receiver, CompilerConfigurationKey<List<T>> option, List<? extends T> values) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(option, "option");
            Intrinsics.checkNotNullParameter(values, "values");
            CommandLineProcessor.super.appendList(receiver, (CompilerConfigurationKey) option, (List) values);
        }

        @Deprecated
        public static void applyOptionsFrom(CommandLineProcessor commandLineProcessor, CompilerConfiguration receiver, Map<String, ? extends List<String>> map, Collection<? extends AbstractCliOption> pluginOptions) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(map, "map");
            Intrinsics.checkNotNullParameter(pluginOptions, "pluginOptions");
            CommandLineProcessor.super.applyOptionsFrom(receiver, map, pluginOptions);
        }
    }

    default void processOption(AbstractCliOption option, String value, CompilerConfiguration configuration) throws CliOptionProcessingException {
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        processOption((CliOption) option, value, configuration);
    }

    default <T> void appendList(CompilerConfiguration compilerConfiguration, CompilerConfigurationKey<List<T>> option, T t) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(option, "option");
        List<? extends T> list = compilerConfiguration.getList(option);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        List<T> listAsMutableList = asMutableList(list);
        listAsMutableList.add(t);
        compilerConfiguration.put(option, listAsMutableList);
    }

    default <T> void appendList(CompilerConfiguration compilerConfiguration, CompilerConfigurationKey<List<T>> option, List<? extends T> values) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(values, "values");
        List<? extends T> list = compilerConfiguration.getList(option);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        List<T> listAsMutableList = asMutableList(list);
        listAsMutableList.addAll(values);
        compilerConfiguration.put(option, listAsMutableList);
    }

    default void applyOptionsFrom(CompilerConfiguration compilerConfiguration, Map<String, ? extends List<String>> map, Collection<? extends AbstractCliOption> pluginOptions) {
        Object next;
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(pluginOptions, "pluginOptions");
        for (Map.Entry<String, ? extends List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            Iterator<T> it = pluginOptions.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((AbstractCliOption) next).getOptionName(), key));
            AbstractCliOption abstractCliOption = (AbstractCliOption) next;
            if (abstractCliOption != null) {
                Iterator<String> it2 = value.iterator();
                while (it2.hasNext()) {
                    processOption(abstractCliOption, it2.next(), compilerConfiguration);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private default <T> List<T> asMutableList(List<? extends T> list) {
        return list instanceof ArrayList ? list : CollectionsKt.toMutableList((Collection) list);
    }
}
