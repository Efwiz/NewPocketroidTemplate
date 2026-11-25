package org.jetbrains.kotlin.compiler.plugin;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: CliOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u0001\u001a\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0001\u001a\u0010\u0010\f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u0001¨\u0006\r"}, d2 = {"cliPluginUsageString", "", "pluginId", "options", "", "Lorg/jetbrains/kotlin/compiler/plugin/AbstractCliOption;", "getPluginOptionString", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "parseLegacyPluginOption", "Lorg/jetbrains/kotlin/compiler/plugin/CliOptionValue;", "argumentValue", "parseModernPluginOption", "plugin-api"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CliOptionsKt {
    public static final String cliPluginUsageString(String pluginId, Collection<? extends AbstractCliOption> options) {
        String strRepeat;
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Intrinsics.checkNotNullParameter(options, "options");
        Collection<? extends AbstractCliOption> collection = options;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (AbstractCliOption abstractCliOption : collection) {
            String str = abstractCliOption.getOptionName() + StringUtil.SPACE + abstractCliOption.getValueDescription();
            if (str.length() > 26) {
                strRepeat = "\n" + StringsKt.repeat(" ", 29);
            } else {
                strRepeat = StringsKt.repeat(" ", 27 - str.length());
            }
            String[] strArr = new String[2];
            strArr[0] = abstractCliOption.getRequired() ? "required" : null;
            strArr[1] = abstractCliOption.getAllowMultipleOccurrences() ? "multiple" : null;
            List listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) strArr);
            arrayList.add(StringsKt.repeat(" ", 2) + str + strRepeat + abstractCliOption.getDescription() + (listListOfNotNull.isEmpty() ? "" : " (" + CollectionsKt.joinToString$default(listListOfNotNull, null, null, null, 0, null, null, 63, null) + ')'));
        }
        return "Plugin \"" + pluginId + "\" usage:\n" + CollectionsKt.joinToString$default(arrayList, "\n", null, "\n", 0, null, null, 58, null);
    }

    public static final CliOptionValue parseLegacyPluginOption(String argumentValue) {
        Intrinsics.checkNotNullParameter(argumentValue, "argumentValue");
        Matcher matcher = Pattern.compile("^plugin:([^:]*):([^=]*)=(.*)$").matcher(argumentValue);
        if (!matcher.matches()) {
            return null;
        }
        String strGroup = matcher.group(1);
        Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
        String strGroup2 = matcher.group(2);
        Intrinsics.checkNotNullExpressionValue(strGroup2, "group(...)");
        String strGroup3 = matcher.group(3);
        Intrinsics.checkNotNullExpressionValue(strGroup3, "group(...)");
        return new CliOptionValue(strGroup, strGroup2, strGroup3);
    }

    public static final CliOptionValue parseModernPluginOption(String argumentValue) {
        Intrinsics.checkNotNullParameter(argumentValue, "argumentValue");
        Matcher matcher = Pattern.compile("^([^=]*)=(.*)$").matcher(argumentValue);
        if (!matcher.matches()) {
            return null;
        }
        String strGroup = matcher.group(1);
        Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
        String strGroup2 = matcher.group(2);
        Intrinsics.checkNotNullExpressionValue(strGroup2, "group(...)");
        return new CliOptionValue("<NO_ID>", strGroup, strGroup2);
    }

    public static final String getPluginOptionString(String pluginId, String key, String value) {
        Intrinsics.checkNotNullParameter(pluginId, "pluginId");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        return "plugin:" + pluginId + ':' + key + '=' + value;
    }
}
