package com.beust.jcommander.converters;

import com.beust.jcommander.ParameterException;

/* JADX INFO: loaded from: classes8.dex */
public class BooleanConverter extends BaseConverter<Boolean> {
    public BooleanConverter(String str) {
        super(str);
    }

    @Override // com.beust.jcommander.IStringConverter
    public Boolean convert(String str) {
        if ("false".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str)) {
            return Boolean.valueOf(Boolean.parseBoolean(str));
        }
        throw new ParameterException(getErrorString(str, "a boolean"));
    }
}
