package org.jetbrains.kotlin.org.jdom;

/* JADX INFO: loaded from: classes4.dex */
public class IllegalNameException extends IllegalArgumentException {
    private static final long serialVersionUID = 200;

    IllegalNameException(String str, String str2, String str3) {
        super("The name \"" + str + "\" is not legal for JDOM/XML " + str2 + "s: " + str3 + ".");
    }
}
