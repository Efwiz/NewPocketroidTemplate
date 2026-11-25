package com.beust.jcommander.internal;

import com.beust.jcommander.ParameterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes8.dex */
public class DefaultConsole implements Console {
    @Override // com.beust.jcommander.internal.Console
    public void print(String str) {
        System.out.print(str);
    }

    @Override // com.beust.jcommander.internal.Console
    public void println(String str) {
        System.out.println(str);
    }

    @Override // com.beust.jcommander.internal.Console
    public char[] readPassword(boolean z) {
        try {
            return new BufferedReader(new InputStreamReader(System.in)).readLine().toCharArray();
        } catch (IOException e) {
            throw new ParameterException(e);
        }
    }
}
