package com.reandroid.jcommand.exceptions;

import com.reandroid.jcommand.CommandStrings;

/* JADX INFO: loaded from: classes11.dex */
public class DuplicateOptionException extends CommandException {
    public DuplicateOptionException(String str) {
        super(CommandStrings.duplicate_option_exception, str);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return format("Duplicate option: '%s'");
    }
}
