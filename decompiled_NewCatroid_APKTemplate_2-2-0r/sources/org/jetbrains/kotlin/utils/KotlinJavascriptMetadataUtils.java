package org.jetbrains.kotlin.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinJavascriptMetadataUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0019\u001a\u00020\u0004H\u0007J\u001e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eH\u0007J\u000e\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/utils/KotlinJavascriptMetadataUtils;", "", "()V", "JS_EXT", "", "JS_MAP_EXT", "KOTLIN_JAVASCRIPT_METHOD_NAME", "KOTLIN_JAVASCRIPT_METHOD_NAME_PATTERN", "Ljava/util/regex/Pattern;", "METADATA_PATTERN", "META_JS_SUFFIX", "formatMetadataAsString", "moduleName", FirebaseAnalytics.Param.CONTENT, "", "metadataVersion", "Lorg/jetbrains/kotlin/utils/JsMetadataVersion;", "hasMetadata", "", "text", "loadMetadata", "", "Lorg/jetbrains/kotlin/utils/KotlinJavascriptMetadata;", "file", "Ljava/io/File;", "path", "parseMetadata", "", "", "metadataList", "", "replaceSuffix", "filePath", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJavascriptMetadataUtils {
    public static final String JS_EXT = ".js";
    public static final String JS_MAP_EXT = ".js.map";
    private static final Pattern KOTLIN_JAVASCRIPT_METHOD_NAME_PATTERN;
    private static final Pattern METADATA_PATTERN;
    public static final String META_JS_SUFFIX = ".meta.js";
    public static final KotlinJavascriptMetadataUtils INSTANCE = new KotlinJavascriptMetadataUtils();
    private static final String KOTLIN_JAVASCRIPT_METHOD_NAME = "kotlin_module_metadata";

    private KotlinJavascriptMetadataUtils() {
    }

    static {
        Pattern patternCompile = Pattern.compile("\\.kotlin_module_metadata\\(", 0);
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        KOTLIN_JAVASCRIPT_METHOD_NAME_PATTERN = patternCompile;
        Pattern patternCompile2 = Pattern.compile("(?m)\\w+\\.kotlin_module_metadata\\((\\d+),\\s*(['\"])([^'\"]*)\\2,\\s*(['\"])([^'\"]*)\\4\\)", 0);
        Intrinsics.checkNotNullExpressionValue(patternCompile2, "compile(...)");
        METADATA_PATTERN = patternCompile2;
    }

    public final String replaceSuffix(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        return kotlin.text.StringsKt.substringBeforeLast$default(filePath, ".js", (String) null, 2, (Object) null) + META_JS_SUFFIX;
    }

    @JvmStatic
    public static final boolean hasMetadata(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        String str = text;
        return KOTLIN_JAVASCRIPT_METHOD_NAME_PATTERN.matcher(str).find() && METADATA_PATTERN.matcher(str).find();
    }

    public final String formatMetadataAsString(String moduleName, byte[] content, JsMetadataVersion metadataVersion) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        return "// Kotlin." + KOTLIN_JAVASCRIPT_METHOD_NAME + '(' + metadataVersion.toInteger() + ", \"" + moduleName + "\", \"" + Base64.getEncoder().encodeToString(content) + "\");\n";
    }

    @JvmStatic
    public static final List<KotlinJavascriptMetadata> loadMetadata(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        file.exists();
        final ArrayList arrayList = new ArrayList();
        JsLibraryUtils.traverseJsLibrary(file, new Function1<JsLibrary, Unit>() { // from class: org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils.loadMetadata.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(JsLibrary jsLibrary) {
                invoke2(jsLibrary);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JsLibrary library) {
                Intrinsics.checkNotNullParameter(library, "library");
                KotlinJavascriptMetadataUtils.parseMetadata(library.getContent(), arrayList);
            }
        });
        return arrayList;
    }

    @JvmStatic
    public static final List<KotlinJavascriptMetadata> loadMetadata(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return loadMetadata(new File(path));
    }

    @JvmStatic
    public static final void parseMetadata(CharSequence text, List<KotlinJavascriptMetadata> metadataList) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(metadataList, "metadataList");
        if (KOTLIN_JAVASCRIPT_METHOD_NAME_PATTERN.matcher(text).find()) {
            Matcher matcher = METADATA_PATTERN.matcher(text);
            while (matcher.find()) {
                JsMetadataVersion.Companion companion = JsMetadataVersion.INSTANCE;
                String strGroup = matcher.group(1);
                Intrinsics.checkNotNullExpressionValue(strGroup, "group(...)");
                JsMetadataVersion jsMetadataVersionFromInteger = companion.fromInteger(Integer.parseInt(strGroup));
                String strGroup2 = matcher.group(3);
                String strGroup3 = matcher.group(5);
                Intrinsics.checkNotNull(strGroup2);
                byte[] bArrDecode = Base64.getDecoder().decode(strGroup3);
                Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
                metadataList.add(new KotlinJavascriptMetadata(jsMetadataVersionFromInteger, strGroup2, bArrDecode));
            }
        }
    }
}
