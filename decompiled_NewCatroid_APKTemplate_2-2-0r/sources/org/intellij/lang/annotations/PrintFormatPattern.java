package org.intellij.lang.annotations;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/annotations-13.0.jar:org/intellij/lang/annotations/PrintFormatPattern.class
  classes14.dex
 */
/* JADX INFO: compiled from: PrintFormat.java */
/* JADX INFO: loaded from: classes14.dex */
class PrintFormatPattern {
    private static final String ARG_INDEX = "(?:\\d+\\$)?";
    private static final String CONVERSION = "(?:[tT])?(?:[a-zA-Z%])";
    private static final String FLAGS = "(?:[-#+ 0,(<]*)?";
    private static final String PRECISION = "(?:\\.\\d+)?";
    static final String PRINT_FORMAT = "(?:[^%]|%%|(?:%(?:\\d+\\$)?(?:[-#+ 0,(<]*)?(?:\\d+)?(?:\\.\\d+)?(?:[tT])?(?:[a-zA-Z%])))*";
    private static final String TEXT = "[^%]|%%";
    private static final String WIDTH = "(?:\\d+)?";

    PrintFormatPattern() {
    }
}
