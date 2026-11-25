package com.reandroid.jcommand.exceptions;

import com.reandroid.jcommand.CommandStrings;

/* JADX INFO: loaded from: classes11.dex */
public class MissingValueException extends CommandException {
    public MissingValueException(String str) {
        super(CommandStrings.missing_value_exception, str);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return format("Missing value for: '%s'");
    }
}
