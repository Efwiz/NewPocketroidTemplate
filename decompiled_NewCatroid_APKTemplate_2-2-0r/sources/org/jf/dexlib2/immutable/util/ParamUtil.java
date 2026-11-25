package org.jf.dexlib2.immutable.util;

import java.util.Iterator;
import org.jf.dexlib2.immutable.ImmutableMethodParameter;

/* JADX INFO: loaded from: classes6.dex */
public class ParamUtil {
    /* JADX INFO: Access modifiers changed from: private */
    public static int findTypeEnd(String str, int i) {
        char cCharAt = str.charAt(i);
        if (cCharAt != 'F') {
            if (cCharAt == 'L') {
                while (true) {
                    int i2 = i + 1;
                    if (str.charAt(i) == ';') {
                        return i2;
                    }
                    i = i2;
                }
            } else if (cCharAt != 'S' && cCharAt != 'I' && cCharAt != 'J' && cCharAt != 'Z') {
                if (cCharAt != '[') {
                    switch (cCharAt) {
                        case 'B':
                        case 'C':
                        case 'D':
                            break;
                        default:
                            throw new IllegalArgumentException(String.format("Param string \"%s\" contains invalid type prefix: %s", str, Character.toString(cCharAt)));
                    }
                } else {
                    while (true) {
                        int i3 = i + 1;
                        if (str.charAt(i) == '[') {
                            return findTypeEnd(str, i3);
                        }
                        i = i3;
                    }
                }
            }
        }
        return i + 1;
    }

    public static Iterable<ImmutableMethodParameter> parseParamString(final String str) {
        return new Iterable<ImmutableMethodParameter>() { // from class: org.jf.dexlib2.immutable.util.ParamUtil.1
            @Override // java.lang.Iterable
            public Iterator<ImmutableMethodParameter> iterator() {
                return new Iterator<ImmutableMethodParameter>() { // from class: org.jf.dexlib2.immutable.util.ParamUtil.1.1
                    private int index = 0;

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return this.index < str.length();
                    }

                    @Override // java.util.Iterator
                    public ImmutableMethodParameter next() {
                        int iFindTypeEnd = ParamUtil.findTypeEnd(str, this.index);
                        String strSubstring = str.substring(this.index, iFindTypeEnd);
                        this.index = iFindTypeEnd;
                        return new ImmutableMethodParameter(strSubstring, null, null);
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
