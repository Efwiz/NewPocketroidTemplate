package com.reandroid.jcommand;

/* JADX INFO: loaded from: classes11.dex */
public interface CommandStringResource {
    static /* synthetic */ String lambda$none$0(String str) {
        return str;
    }

    String getString(String str);

    static CommandStringResource none() {
        return new CommandStringResource() { // from class: com.reandroid.jcommand.CommandStringResource$$ExternalSyntheticLambda0
            @Override // com.reandroid.jcommand.CommandStringResource
            public final String getString(String str) {
                return CommandStringResource.lambda$none$0(str);
            }
        };
    }
}
