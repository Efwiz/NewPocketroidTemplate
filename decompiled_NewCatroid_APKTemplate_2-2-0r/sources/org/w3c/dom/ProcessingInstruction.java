package org.w3c.dom;

/* JADX INFO: loaded from: classes7.dex */
public interface ProcessingInstruction extends Node {
    String getData();

    String getTarget();

    void setData(String str) throws DOMException;
}
