package org.antlr.stringtemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes12.dex */
public class CommonGroupLoader extends PathGroupLoader {
    public CommonGroupLoader(StringTemplateErrorListener stringTemplateErrorListener) {
        super(stringTemplateErrorListener);
    }

    public CommonGroupLoader(String str, StringTemplateErrorListener stringTemplateErrorListener) {
        super(str, stringTemplateErrorListener);
    }

    @Override // org.antlr.stringtemplate.PathGroupLoader
    protected BufferedReader locate(String str) throws IOException {
        for (int i = 0; i < this.dirs.size(); i++) {
            String string = new StringBuffer().append((String) this.dirs.get(i)).append("/").append(str).toString();
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(string);
            if (resourceAsStream == null) {
                resourceAsStream = getClass().getClassLoader().getResourceAsStream(string);
            }
            if (resourceAsStream != null) {
                return new BufferedReader(getInputStreamReader(resourceAsStream));
            }
        }
        return null;
    }
}
