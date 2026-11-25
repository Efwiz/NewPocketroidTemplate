package org.jf.smali;

/* JADX INFO: loaded from: classes6.dex */
public class SmaliOptions {
    public String markersListFile;
    public int apiLevel = 15;
    public String outputDexFile = "out.dex";
    public int jobs = Runtime.getRuntime().availableProcessors();
    public boolean allowOdexOpcodes = false;
    public boolean verboseErrors = false;
    public boolean printTokens = false;
}
