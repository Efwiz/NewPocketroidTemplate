package com.reandroid.jcommand.exceptions;

import com.reandroid.jcommand.CommandStrings;

/* JADX INFO: loaded from: classes11.dex */
public class CommandFormatException extends CommandException {
    public CommandFormatException(Class<?> cls, String str) {
        super(CommandStrings.invalid_type_format, cls.getSimpleName(), str);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return format("Invalid <%s> string: '%s'");
    }
}
