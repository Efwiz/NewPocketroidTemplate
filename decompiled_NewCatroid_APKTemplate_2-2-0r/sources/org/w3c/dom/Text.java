package org.w3c.dom;

/* JADX INFO: loaded from: classes7.dex */
public interface Text extends CharacterData {
    String getWholeText();

    boolean isElementContentWhitespace();

    Text replaceWholeText(String str) throws DOMException;

    Text splitText(int i) throws DOMException;
}
