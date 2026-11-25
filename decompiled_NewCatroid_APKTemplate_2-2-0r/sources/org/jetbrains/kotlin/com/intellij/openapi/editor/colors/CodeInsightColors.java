package org.jetbrains.kotlin.com.intellij.openapi.editor.colors;

/* JADX INFO: loaded from: classes3.dex */
public interface CodeInsightColors {
    public static final TextAttributesKey WRONG_REFERENCES_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("WRONG_REFERENCES_ATTRIBUTES");
    public static final TextAttributesKey ERRORS_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("ERRORS_ATTRIBUTES");
    public static final TextAttributesKey WARNINGS_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("WARNING_ATTRIBUTES");
    public static final TextAttributesKey GENERIC_SERVER_ERROR_OR_WARNING = TextAttributesKey.createTextAttributesKey("GENERIC_SERVER_ERROR_OR_WARNING");
    public static final TextAttributesKey DUPLICATE_FROM_SERVER = TextAttributesKey.createTextAttributesKey("DUPLICATE_FROM_SERVER");
    public static final TextAttributesKey RUNTIME_ERROR = TextAttributesKey.createTextAttributesKey("RUNTIME_ERROR");

    @Deprecated
    public static final TextAttributesKey INFO_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("INFO_ATTRIBUTES");
    public static final TextAttributesKey WEAK_WARNING_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("INFO_ATTRIBUTES");
    public static final TextAttributesKey INFORMATION_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("INFORMATION_ATTRIBUTES");
    public static final TextAttributesKey NOT_USED_ELEMENT_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("NOT_USED_ELEMENT_ATTRIBUTES");
    public static final TextAttributesKey DEPRECATED_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("DEPRECATED_ATTRIBUTES");
    public static final TextAttributesKey MARKED_FOR_REMOVAL_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("MARKED_FOR_REMOVAL_ATTRIBUTES");

    @Deprecated
    public static final TextAttributesKey INSTANCE_FIELD_ATTRIBUTES = TextAttributesKey.DUMMY_DEPRECATED_ATTRIBUTES;

    @Deprecated
    public static final TextAttributesKey STATIC_FIELD_ATTRIBUTES = TextAttributesKey.DUMMY_DEPRECATED_ATTRIBUTES;

    @Deprecated
    public static final TextAttributesKey STATIC_FINAL_FIELD_ATTRIBUTES = TextAttributesKey.DUMMY_DEPRECATED_ATTRIBUTES;

    @Deprecated
    public static final TextAttributesKey METHOD_CALL_ATTRIBUTES = TextAttributesKey.DUMMY_DEPRECATED_ATTRIBUTES;

    @Deprecated
    public static final TextAttributesKey ANNOTATION_NAME_ATTRIBUTES = TextAttributesKey.DUMMY_DEPRECATED_ATTRIBUTES;
    public static final TextAttributesKey MATCHED_BRACE_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("MATCHED_BRACE_ATTRIBUTES");
    public static final TextAttributesKey UNMATCHED_BRACE_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("UNMATCHED_BRACE_ATTRIBUTES");
    public static final TextAttributesKey JOIN_POINT = TextAttributesKey.createTextAttributesKey("JOIN_POINT");
    public static final TextAttributesKey BLINKING_HIGHLIGHTS_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("BLINKING_HIGHLIGHTS_ATTRIBUTES");
    public static final TextAttributesKey HYPERLINK_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("HYPERLINK_ATTRIBUTES");
    public static final TextAttributesKey FOLLOWED_HYPERLINK_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("FOLLOWED_HYPERLINK_ATTRIBUTES");
    public static final TextAttributesKey INACTIVE_HYPERLINK_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("INACTIVE_HYPERLINK_ATTRIBUTES");
    public static final TextAttributesKey TODO_DEFAULT_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("TODO_DEFAULT_ATTRIBUTES");
    public static final TextAttributesKey BOOKMARKS_ATTRIBUTES = TextAttributesKey.createTextAttributesKey("BOOKMARKS_ATTRIBUTES");
    public static final ColorKey METHOD_SEPARATORS_COLOR = ColorKey.createColorKey("METHOD_SEPARATORS_COLOR");
    public static final TextAttributesKey LINE_FULL_COVERAGE = TextAttributesKey.createTextAttributesKey("LINE_FULL_COVERAGE");
    public static final TextAttributesKey LINE_PARTIAL_COVERAGE = TextAttributesKey.createTextAttributesKey("LINE_PARTIAL_COVERAGE");
    public static final TextAttributesKey LINE_NONE_COVERAGE = TextAttributesKey.createTextAttributesKey("LINE_NONE_COVERAGE");
}
