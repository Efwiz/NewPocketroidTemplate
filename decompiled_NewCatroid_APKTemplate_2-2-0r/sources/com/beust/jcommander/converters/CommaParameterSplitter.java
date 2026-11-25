package com.beust.jcommander.converters;

import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes8.dex */
public class CommaParameterSplitter implements IParameterSplitter {
    @Override // com.beust.jcommander.converters.IParameterSplitter
    public List<String> split(String str) {
        return Arrays.asList(str.split(","));
    }
}
