package org.jetbrains.kotlin.org.jline.keymap;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.jetbrains.kotlin.org.jline.reader.EndOfFileException;
import org.jetbrains.kotlin.org.jline.utils.ClosedException;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;

/* JADX INFO: loaded from: classes4.dex */
public class BindingReader {
    protected String lastBinding;
    protected final StringBuilder opBuffer = new StringBuilder();
    protected final Deque<Integer> pushBackChar = new ArrayDeque();
    protected final NonBlockingReader reader;

    public BindingReader(NonBlockingReader nonBlockingReader) {
        this.reader = nonBlockingReader;
    }

    public <T> T readBinding(KeyMap<T> keyMap) {
        return (T) readBinding(keyMap, null, true);
    }

    public <T> T readBinding(KeyMap<T> keyMap, KeyMap<T> keyMap2) {
        return (T) readBinding(keyMap, keyMap2, true);
    }

    public <T> T readBinding(KeyMap<T> keyMap, KeyMap<T> keyMap2, boolean z) {
        int character;
        this.lastBinding = null;
        int[] iArr = new int[1];
        T unicode = null;
        boolean z2 = false;
        while (true) {
            if (keyMap2 != null) {
                unicode = keyMap2.getBound(this.opBuffer, iArr);
            }
            if (unicode == null && (keyMap2 == null || iArr[0] >= 0)) {
                unicode = keyMap.getBound(this.opBuffer, iArr);
            }
            if (unicode != null) {
                if (iArr[0] >= 0) {
                    StringBuilder sb = this.opBuffer;
                    runMacro(sb.substring(sb.length() - iArr[0]));
                    StringBuilder sb2 = this.opBuffer;
                    sb2.setLength(sb2.length() - iArr[0]);
                } else {
                    long ambiguousTimeout = keyMap.getAmbiguousTimeout();
                    if (ambiguousTimeout > 0 && peekCharacter(ambiguousTimeout) != -2) {
                        unicode = null;
                    }
                }
                if (unicode != null) {
                    this.lastBinding = this.opBuffer.toString();
                    this.opBuffer.setLength(0);
                    return unicode;
                }
            } else if (iArr[0] > 0) {
                int iCodePointAt = this.opBuffer.codePointAt(0);
                String strSubstring = this.opBuffer.substring(Character.charCount(iCodePointAt));
                this.lastBinding = this.opBuffer.substring(0, Character.charCount(iCodePointAt));
                unicode = iCodePointAt >= 128 ? keyMap.getUnicode() : keyMap.getNomatch();
                this.opBuffer.setLength(0);
                this.opBuffer.append(strSubstring);
                if (unicode != null) {
                    return unicode;
                }
            }
            if ((!z && z2) || (character = readCharacter()) == -1) {
                return null;
            }
            this.opBuffer.appendCodePoint(character);
            z2 = true;
        }
    }

    public int readCharacter() {
        int i;
        if (!this.pushBackChar.isEmpty()) {
            return this.pushBackChar.pop().intValue();
        }
        int i2 = 0;
        loop0: while (true) {
            i = -2;
            while (true) {
                if (i != -2) {
                    break loop0;
                }
                try {
                    i = this.reader.read(100L);
                    if (i < 0 || !Character.isHighSurrogate((char) i)) {
                    }
                } catch (ClosedException e) {
                    throw new EndOfFileException(e);
                } catch (IOException e2) {
                    throw new IOError(e2);
                }
            }
            i2 = i;
        }
        return i2 != 0 ? Character.toCodePoint((char) i2, (char) i) : i;
    }

    public int peekCharacter(long j) {
        if (!this.pushBackChar.isEmpty()) {
            return this.pushBackChar.peek().intValue();
        }
        try {
            return this.reader.peek(j);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public void runMacro(String str) {
        IntStream intStreamCodePoints = str.codePoints();
        final Deque<Integer> deque = this.pushBackChar;
        deque.getClass();
        intStreamCodePoints.forEachOrdered(new IntConsumer() { // from class: org.jetbrains.kotlin.org.jline.keymap.BindingReader$$ExternalSyntheticLambda0
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                deque.addLast(Integer.valueOf(i));
            }
        });
    }

    public String getLastBinding() {
        return this.lastBinding;
    }
}
