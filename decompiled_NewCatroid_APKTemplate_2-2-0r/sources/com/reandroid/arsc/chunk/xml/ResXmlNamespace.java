package com.reandroid.arsc.chunk.xml;

import com.reandroid.common.Namespace;

/* JADX INFO: loaded from: classes10.dex */
public interface ResXmlNamespace extends Namespace {
    int getLineNumber();

    int getUriReference();

    void setLineNumber(int i);

    void setPrefix(String str);

    void setUri(String str);
}
