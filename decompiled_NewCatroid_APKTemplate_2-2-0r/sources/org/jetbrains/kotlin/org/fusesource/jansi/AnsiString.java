package org.jetbrains.kotlin.org.fusesource.jansi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class AnsiString implements CharSequence {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final CharSequence encoded;
    private final CharSequence plain;

    public AnsiString(CharSequence charSequence) {
        this.encoded = charSequence;
        this.plain = chew(charSequence);
    }

    private CharSequence chew(CharSequence charSequence) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AnsiOutputStream ansiOutputStream = new AnsiOutputStream(byteArrayOutputStream);
        try {
            ansiOutputStream.write(charSequence.toString().getBytes());
            ansiOutputStream.flush();
            ansiOutputStream.close();
            return new String(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CharSequence getEncoded() {
        return this.encoded;
    }

    public CharSequence getPlain() {
        return this.plain;
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return getEncoded().charAt(i);
    }

    @Override // java.lang.CharSequence
    public CharSequence subSequence(int i, int i2) {
        return getEncoded().subSequence(i, i2);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return getPlain().length();
    }

    public boolean equals(Object obj) {
        return getEncoded().equals(obj);
    }

    public int hashCode() {
        return getEncoded().hashCode();
    }

    @Override // java.lang.CharSequence
    public String toString() {
        return getEncoded().toString();
    }
}
