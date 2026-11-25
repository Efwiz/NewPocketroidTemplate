package org.catrobat.catroid.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/JsonRegexExtractor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class JsonRegexExtractor {
    private JsonRegexExtractor() {
        throw new IllegalStateException("Utility class");
    }

    public static String getJsonParserRegex(String keyword) {
        return "\"" + keyword + "\"\\s*:\\s*\"?((?<=\")(\\\\\"|[^\"])*(?=\")|(?<!\")([+-]?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?\\d+)?|(true|false|null)|\\{[^{]*?\\}|\\[[^\\[]*?\\])(?!\"))\"?(?=\\s*[,\\]}])";
    }
}
