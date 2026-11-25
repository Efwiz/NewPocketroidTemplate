package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: GradleOption.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/DefaultValue;", "", "(Ljava/lang/String;I)V", "BOOLEAN_FALSE_DEFAULT", "BOOLEAN_TRUE_DEFAULT", "STRING_NULL_DEFAULT", "EMPTY_STRING_LIST_DEFAULT", "EMPTY_STRING_ARRAY_DEFAULT", "LANGUAGE_VERSIONS", "API_VERSIONS", "JVM_TARGET_VERSIONS", "JS_ECMA_VERSIONS", "JS_MODULE_KINDS", "JS_SOURCE_MAP_CONTENT_MODES", "JS_MAIN", "JS_SOURCE_MAP_NAMES_POLICY", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum DefaultValue {
    BOOLEAN_FALSE_DEFAULT,
    BOOLEAN_TRUE_DEFAULT,
    STRING_NULL_DEFAULT,
    EMPTY_STRING_LIST_DEFAULT,
    EMPTY_STRING_ARRAY_DEFAULT,
    LANGUAGE_VERSIONS,
    API_VERSIONS,
    JVM_TARGET_VERSIONS,
    JS_ECMA_VERSIONS,
    JS_MODULE_KINDS,
    JS_SOURCE_MAP_CONTENT_MODES,
    JS_MAIN,
    JS_SOURCE_MAP_NAMES_POLICY;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    public static EnumEntries<DefaultValue> getEntries() {
        return $ENTRIES;
    }
}
