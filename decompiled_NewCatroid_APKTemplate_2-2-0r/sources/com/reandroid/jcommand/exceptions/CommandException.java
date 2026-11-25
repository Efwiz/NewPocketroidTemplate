package com.reandroid.jcommand.exceptions;

import com.reandroid.jcommand.CommandStringResource;

/* JADX INFO: loaded from: classes11.dex */
public class CommandException extends RuntimeException {
    private final String format;
    private final Object[] values;

    public CommandException(String str, Object... objArr) {
        super(str);
        this.format = str;
        this.values = objArr;
    }

    public String getMessage(CommandStringResource commandStringResource) {
        return format(commandStringResource.getString(getFormat()));
    }

    public String getFormat() {
        return this.format;
    }

    public Object[] getValues() {
        return this.values;
    }

    public String format(String str) {
        return String.format(str, getValues());
    }
}
