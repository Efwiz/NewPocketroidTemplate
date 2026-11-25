package org.jf.util.jcommander;

import com.beust.jcommander.converters.IParameterSplitter;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class ColonParameterSplitter implements IParameterSplitter {
    @Override // com.beust.jcommander.converters.IParameterSplitter
    public List<String> split(String str) {
        return Arrays.asList(str.split(":"));
    }
}
