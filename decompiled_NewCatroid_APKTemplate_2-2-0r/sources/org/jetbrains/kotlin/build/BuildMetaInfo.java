package org.jetbrains.kotlin.build;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersion;

/* JADX INFO: compiled from: BuildMetaInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u00002\u00020\u0001:\u0001\u001dB\u0005¢\u0006\u0002\u0010\u0002J'\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\u000fJ \u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u0005H\u0002J\u001c\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001a\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u0016\u001a\u00020\u0005J0\u0010\u0017\u001a\u0004\u0018\u00010\u00052\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00122\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0012J\u000e\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0014J\u001c\u0010\u001b\u001a\u00020\u00052\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0012H\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/build/BuildMetaInfo;", "", "()V", "argumentsListForSpecialCheck", "", "", "getArgumentsListForSpecialCheck", "()Ljava/util/List;", "excludedProperties", "getExcludedProperties", "checkIfPlatformSpecificCompilerArgumentWasChanged", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "currentValue", "previousValue", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Boolean;", "compareIsChanged", "createPropertiesMapFromCompilerArguments", "", "args", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "deserializeMapFromString", "inputString", "obtainReasonForRebuild", "currentCompilerArgumentsMap", "previousCompilerArgsMap", "serializeArgsToString", "serializeMapToString", "myList", "CustomKeys", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BuildMetaInfo {
    private final List<String> excludedProperties = CollectionsKt.listOf((Object[]) new String[]{"languageVersion", "apiVersion", "pluginClasspaths", "metadataVersion", "dumpDirectory", "dumpOnlyFqName", "dumpPerf", "errors", "extraHelp", "freeArgs", "help", "intellijPluginRoot", "kotlinHome", "listPhases", "phasesToDump", "phasesToDumpAfter", "phasesToDumpBefore", "profilePhases", "renderInternalDiagnosticNames", "reportOutputFiles", "reportPerf", "script", "verbose", "verbosePhases", "version"});
    private final List<String> argumentsListForSpecialCheck = CollectionsKt.listOf((Object[]) new String[]{"allowAnyScriptsInSourceRoots", "allowKotlinPackage", "allowResultReturnType", "noCheckActual", "skipMetadataVersionCheck", "skipPrereleaseCheck", "suppressVersionWarnings", "suppressWarnings", "IS_EAP"});

    /* JADX INFO: compiled from: BuildMetaInfo.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/build/BuildMetaInfo$CustomKeys;", "", "(Ljava/lang/String;I)V", "LANGUAGE_VERSION_STRING", "IS_EAP", "METADATA_VERSION_STRING", "PLUGIN_CLASSPATHS", "API_VERSION_STRING", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum CustomKeys {
        LANGUAGE_VERSION_STRING,
        IS_EAP,
        METADATA_VERSION_STRING,
        PLUGIN_CLASSPATHS,
        API_VERSION_STRING;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<CustomKeys> getEntries() {
            return $ENTRIES;
        }
    }

    public Boolean checkIfPlatformSpecificCompilerArgumentWasChanged(String key, String currentValue, String previousValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(currentValue, "currentValue");
        Intrinsics.checkNotNullParameter(previousValue, "previousValue");
        return null;
    }

    public final String obtainReasonForRebuild(Map<String, String> currentCompilerArgumentsMap, Map<String, String> previousCompilerArgsMap) {
        String str;
        Intrinsics.checkNotNullParameter(currentCompilerArgumentsMap, "currentCompilerArgumentsMap");
        Intrinsics.checkNotNullParameter(previousCompilerArgsMap, "previousCompilerArgsMap");
        if (!Intrinsics.areEqual(currentCompilerArgumentsMap.keySet(), previousCompilerArgsMap.keySet())) {
            return "Compiler arguments version was changed";
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, String>> it = currentCompilerArgumentsMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<String, String> next = it.next();
            String key = next.getKey();
            String str2 = previousCompilerArgsMap.get(next.getKey());
            String str3 = (str2 == null || compareIsChanged(key, next.getValue(), str2)) ? key : null;
            if (str3 != null) {
                arrayList.add(str3);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (arrayList2.size() == 1) {
            str = "One of compiler arguments was changed: ";
        } else {
            str = "Some compiler arguments were changed: ";
        }
        return sb.append(str).append(SerializationUtilsKt.joinToReadableString(arrayList2)).toString();
    }

    private final boolean compareIsChanged(String key, String currentValue, String previousValue) {
        Boolean boolCheckIfPlatformSpecificCompilerArgumentWasChanged = checkIfPlatformSpecificCompilerArgumentWasChanged(key, currentValue, previousValue);
        if (boolCheckIfPlatformSpecificCompilerArgumentWasChanged != null) {
            return boolCheckIfPlatformSpecificCompilerArgumentWasChanged.booleanValue();
        }
        int iHashCode = key.hashCode();
        if (iHashCode != -192453027) {
            if (iHashCode != 621066559) {
                if (iHashCode == 1335857922 && key.equals("PLUGIN_CLASSPATHS")) {
                    return !new PluginClasspathComparator(previousValue, currentValue).equals();
                }
            } else if (key.equals("LANGUAGE_VERSION_STRING")) {
                return LanguageVersion.INSTANCE.fromVersionString(currentValue) != LanguageVersion.INSTANCE.fromVersionString(previousValue);
            }
        } else if (key.equals("API_VERSION_STRING")) {
            return !Intrinsics.areEqual(ApiVersion.INSTANCE.parse(currentValue), ApiVersion.INSTANCE.parse(previousValue));
        }
        if (getArgumentsListForSpecialCheck().contains(key)) {
            return Intrinsics.areEqual(previousValue, "true") && !Intrinsics.areEqual(currentValue, "true");
        }
        return !Intrinsics.areEqual(previousValue, currentValue);
    }

    public Map<String, String> createPropertiesMapFromCompilerArguments(CommonCompilerArguments args) {
        LanguageVersion languageVersionFromVersionString;
        Intrinsics.checkNotNullParameter(args, "args");
        Map<String, String> mutableMap = MapsKt.toMutableMap(SerializationUtilsKt.transformClassToPropertiesMap(args, getExcludedProperties()));
        String languageVersion = args.getLanguageVersion();
        if (languageVersion == null || (languageVersionFromVersionString = LanguageVersion.INSTANCE.fromVersionString(languageVersion)) == null) {
            languageVersionFromVersionString = LanguageVersion.LATEST_STABLE;
        }
        String versionString = languageVersionFromVersionString.getVersionString();
        mutableMap.put("LANGUAGE_VERSION_STRING", versionString);
        mutableMap.put("IS_EAP", String.valueOf(!languageVersionFromVersionString.isStable()));
        String apiVersion = args.getApiVersion();
        if (apiVersion != null) {
            versionString = apiVersion;
        }
        mutableMap.put("API_VERSION_STRING", versionString);
        mutableMap.put("PLUGIN_CLASSPATHS", new PluginClasspath(args.getPluginClasspaths()).serialize());
        return mutableMap;
    }

    public final Map<String, String> deserializeMapFromString(String inputString) {
        Intrinsics.checkNotNullParameter(inputString, "inputString");
        List listSplit$default = StringsKt.split$default((CharSequence) inputString, new String[]{"\n"}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            if (!StringsKt.isBlank((String) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList<String> arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (String str : arrayList2) {
            Pair pair = TuplesKt.to(StringsKt.substringBefore$default(str, ContainerUtils.KEY_VALUE_DELIMITER, (String) null, 2, (Object) null), StringsKt.substringAfter$default(str, ContainerUtils.KEY_VALUE_DELIMITER, (String) null, 2, (Object) null));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    public final String serializeArgsToString(CommonCompilerArguments args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return serializeMapToString(createPropertiesMapFromCompilerArguments(args));
    }

    public List<String> getExcludedProperties() {
        return this.excludedProperties;
    }

    public List<String> getArgumentsListForSpecialCheck() {
        return this.argumentsListForSpecialCheck;
    }

    private final String serializeMapToString(Map<String, String> myList) {
        ArrayList arrayList = new ArrayList(myList.size());
        for (Map.Entry<String, String> entry : myList.entrySet()) {
            arrayList.add(entry.getKey() + '=' + entry.getValue());
        }
        return CollectionsKt.joinToString$default(arrayList, "\n", null, null, 0, null, null, 62, null);
    }
}
