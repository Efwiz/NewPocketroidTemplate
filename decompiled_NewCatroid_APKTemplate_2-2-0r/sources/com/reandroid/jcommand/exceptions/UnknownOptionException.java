package com.reandroid.jcommand.exceptions;

import com.reandroid.jcommand.CommandStrings;

/* JADX INFO: loaded from: classes11.dex */
public class UnknownOptionException extends CommandException {
    public UnknownOptionException(String str) {
        super(CommandStrings.unknown_option_exception, str);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return format("Unknown option: '%s'");
    }
}
