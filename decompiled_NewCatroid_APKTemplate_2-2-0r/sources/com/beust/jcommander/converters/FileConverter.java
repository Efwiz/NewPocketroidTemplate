package com.beust.jcommander.converters;

import com.beust.jcommander.IStringConverter;
import java.io.File;

/* JADX INFO: loaded from: classes8.dex */
public class FileConverter implements IStringConverter<File> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.beust.jcommander.IStringConverter
    public File convert(String str) {
        return new File(str);
    }
}
