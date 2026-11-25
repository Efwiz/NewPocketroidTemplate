package org.jetbrains.kotlin.org.jline.reader;

import java.util.Objects;

/* JADX INFO: loaded from: classes4.dex */
public class Candidate implements Comparable<Candidate> {
    private final boolean complete;
    private final String descr;
    private final String displ;
    private final String group;
    private final String key;
    private final String suffix;
    private final String value;

    public Candidate(String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        Objects.requireNonNull(str);
        this.value = str;
        this.displ = str2;
        this.group = str3;
        this.descr = str4;
        this.suffix = str5;
        this.key = str6;
        this.complete = z;
    }

    public String value() {
        return this.value;
    }

    public String displ() {
        return this.displ;
    }

    public String group() {
        return this.group;
    }

    public String descr() {
        return this.descr;
    }

    public String suffix() {
        return this.suffix;
    }

    public String key() {
        return this.key;
    }

    public boolean complete() {
        return this.complete;
    }

    @Override // java.lang.Comparable
    public int compareTo(Candidate candidate) {
        return this.value.compareTo(candidate.value);
    }
}
