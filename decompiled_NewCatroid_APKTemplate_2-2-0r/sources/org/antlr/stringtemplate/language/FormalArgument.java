package org.antlr.stringtemplate.language;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.LinkedHashMap;
import net.bytebuddy.description.type.TypeDescription;
import org.antlr.stringtemplate.StringTemplate;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes12.dex */
public class FormalArgument {
    public static final int ONE_OR_MORE = 8;
    public static final int OPTIONAL = 1;
    public static final int REQUIRED = 2;
    public static final int ZERO_OR_MORE = 4;
    public StringTemplate defaultValueST;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f430name;
    public static final String[] suffixes = {null, TypeDescription.Generic.OfWildcardType.SYMBOL, "", null, "*", null, null, null, Marker.ANY_NON_NULL_MARKER};
    public static final LinkedHashMap UNKNOWN = new LinkedHashMap();

    public FormalArgument(String str) {
        this.f430name = str;
    }

    public FormalArgument(String str, StringTemplate stringTemplate) {
        this.f430name = str;
        this.defaultValueST = stringTemplate;
    }

    public static String getCardinalityName(int i) {
        if (i == 1) {
            return SchemaSymbols.ATTVAL_OPTIONAL;
        }
        if (i == 2) {
            return "exactly one";
        }
        if (i == 4) {
            return "zero-or-more";
        }
        if (i == 8) {
            return "one-or-more";
        }
        return "unknown";
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof FormalArgument)) {
            return false;
        }
        FormalArgument formalArgument = (FormalArgument) obj;
        if (!this.f430name.equals(formalArgument.f430name)) {
            return false;
        }
        StringTemplate stringTemplate = this.defaultValueST;
        return (stringTemplate == null || formalArgument.defaultValueST != null) && (stringTemplate != null || formalArgument.defaultValueST == null);
    }

    public String toString() {
        if (this.defaultValueST != null) {
            return new StringBuffer().append(this.f430name).append(ContainerUtils.KEY_VALUE_DELIMITER).append(this.defaultValueST).toString();
        }
        return this.f430name;
    }
}
