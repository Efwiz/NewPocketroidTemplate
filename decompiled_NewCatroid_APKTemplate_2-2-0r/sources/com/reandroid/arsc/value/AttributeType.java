package com.reandroid.arsc.value;

import com.reandroid.utils.StringsUtil;
import org.eclipse.jdt.core.compiler.IProblem;

/* JADX INFO: loaded from: classes10.dex */
public enum AttributeType {
    FORMATS(16777216),
    MIN(IProblem.ObjectHasNoSuperclass),
    MAX(IProblem.UndefinedType),
    L10N(IProblem.NotVisibleType),
    OTHER(IProblem.AmbiguousType),
    ZERO(IProblem.UsingDeprecatedType),
    ONE(IProblem.InternalTypeNameProvided),
    TWO(16777223),
    FEW(16777224),
    MANY(16777225);

    private static final AttributeType[] VALUES = values();

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f231id;

    AttributeType(int i) {
        this.f231id = i;
    }

    public int getId() {
        return this.f231id;
    }

    public boolean isPlural() {
        int i = this.f231id & 65535;
        return i >= 4 && i <= 9;
    }

    public String getName() {
        return StringsUtil.toLowercase(name());
    }

    @Override // java.lang.Enum
    public String toString() {
        return getName();
    }

    public static AttributeType valueOf(int i) {
        if (i == 0) {
            return null;
        }
        for (AttributeType attributeType : VALUES) {
            if (attributeType.getId() == i) {
                return attributeType;
            }
        }
        return null;
    }

    public static AttributeType fromName(String str) {
        if (str == null) {
            return null;
        }
        String upperCase = str.toUpperCase();
        if ("FORMAT".equals(upperCase)) {
            return FORMATS;
        }
        for (AttributeType attributeType : VALUES) {
            if (upperCase.equals(attributeType.name())) {
                return attributeType;
            }
        }
        return null;
    }
}
