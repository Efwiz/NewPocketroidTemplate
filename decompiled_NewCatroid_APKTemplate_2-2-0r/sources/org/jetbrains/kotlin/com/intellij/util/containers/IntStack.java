package org.jetbrains.kotlin.com.intellij.util.containers;

import java.util.Arrays;
import java.util.EmptyStackException;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class IntStack {
    private int[] data;
    private int size;

    public IntStack(int i) {
        this.data = new int[i];
        this.size = 0;
    }

    public IntStack() {
        this(5);
    }

    public void push(int i) {
        int i2 = this.size;
        int[] iArr = this.data;
        if (i2 >= iArr.length) {
            this.data = ArrayUtil.realloc(iArr, (iArr.length * 3) / 2);
        }
        int[] iArr2 = this.data;
        int i3 = this.size;
        this.size = i3 + 1;
        iArr2[i3] = i;
    }

    public int pop() {
        int i = this.size;
        if (i == 0) {
            throw new EmptyStackException();
        }
        int[] iArr = this.data;
        int i2 = i - 1;
        this.size = i2;
        return iArr[i2];
    }

    public int size() {
        return this.size;
    }

    public boolean empty() {
        return this.size == 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntStack)) {
            return false;
        }
        IntStack intStack = (IntStack) obj;
        if (this.size != intStack.size) {
            return false;
        }
        for (int i = 0; i < intStack.size; i++) {
            if (this.data[i] != intStack.data[i]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return Arrays.toString(Arrays.copyOf(this.data, this.size));
    }
}
