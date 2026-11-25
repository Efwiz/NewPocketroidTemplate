package org.xmlunit.diff;

import io.netty.util.internal.StringUtil;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public enum ComparisonType {
    XML_VERSION,
    XML_STANDALONE,
    XML_ENCODING,
    HAS_DOCTYPE_DECLARATION(true),
    DOCTYPE_NAME(true),
    DOCTYPE_PUBLIC_ID(true),
    DOCTYPE_SYSTEM_ID(true),
    SCHEMA_LOCATION,
    NO_NAMESPACE_SCHEMA_LOCATION,
    NODE_TYPE,
    NAMESPACE_PREFIX,
    NAMESPACE_URI,
    TEXT_VALUE,
    PROCESSING_INSTRUCTION_TARGET,
    PROCESSING_INSTRUCTION_DATA,
    ELEMENT_TAG_NAME,
    ATTR_VALUE_EXPLICITLY_SPECIFIED("attribute value explicitly specified"),
    ELEMENT_NUM_ATTRIBUTES("number of attributes"),
    ATTR_VALUE("attribute value"),
    CHILD_NODELIST_LENGTH,
    CHILD_NODELIST_SEQUENCE,
    CHILD_LOOKUP("child"),
    ATTR_NAME_LOOKUP("attribute name");

    private final String description;
    private final boolean doctypeComparison;

    ComparisonType() {
        this(false);
    }

    ComparisonType(boolean z) {
        this(null, z);
    }

    ComparisonType(String str) {
        this(str, false);
    }

    ComparisonType(String str, boolean z) {
        this.description = str;
        this.doctypeComparison = z;
    }

    public String getDescription() {
        String str = this.description;
        return str == null ? name().toLowerCase(Locale.ENGLISH).replace('_', StringUtil.SPACE) : str;
    }

    boolean isDoctypeComparison() {
        return this.doctypeComparison;
    }
}
