package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOError;
import java.io.IOException;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.Curses;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;

/* JADX INFO: loaded from: classes4.dex */
public class CursorSupport {
    public static Cursor getCursorPosition(Terminal terminal, IntConsumer intConsumer) {
        try {
            String stringCapability = terminal.getStringCapability(InfoCmp.Capability.user6);
            String stringCapability2 = terminal.getStringCapability(InfoCmp.Capability.user7);
            if (stringCapability != null && stringCapability2 != null) {
                StringBuilder sb = new StringBuilder();
                int i = 0;
                boolean z = false;
                while (i < stringCapability.length()) {
                    int i2 = i + 1;
                    char cCharAt = stringCapability.charAt(i);
                    if (cCharAt == '%') {
                        i += 2;
                        char cCharAt2 = stringCapability.charAt(i2);
                        if (cCharAt2 == '%') {
                            sb.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        } else if (cCharAt2 == 'd') {
                            sb.append("([0-9]+)");
                        } else {
                            if (cCharAt2 != 'i') {
                                throw new IllegalArgumentException();
                            }
                            z = true;
                        }
                    } else if (cCharAt == '\\') {
                        i += 2;
                        char cCharAt3 = stringCapability.charAt(i2);
                        if (cCharAt3 != 'E' && cCharAt3 != 'e') {
                            throw new IllegalArgumentException();
                        }
                        sb.append("\\x1b");
                    } else {
                        if (cCharAt == '[') {
                            sb.append('\\');
                        }
                        sb.append(cCharAt);
                        i = i2;
                    }
                }
                Pattern patternCompile = Pattern.compile(sb.toString());
                Curses.tputs(terminal.writer(), stringCapability2, new Object[0]);
                terminal.flush();
                StringBuilder sb2 = new StringBuilder();
                int i3 = 0;
                while (true) {
                    int i4 = terminal.reader().read();
                    if (i4 < 0) {
                        return null;
                    }
                    sb2.append((char) i4);
                    Matcher matcher = patternCompile.matcher(sb2.substring(i3));
                    if (matcher.matches()) {
                        int i5 = Integer.parseInt(matcher.group(1));
                        int i6 = Integer.parseInt(matcher.group(2));
                        if (z) {
                            i6--;
                            i5--;
                        }
                        if (intConsumer != null) {
                            for (int i7 = 0; i7 < i3; i7++) {
                                intConsumer.accept(sb2.charAt(i7));
                            }
                        }
                        return new Cursor(i6, i5);
                    }
                    if (!matcher.hitEnd()) {
                        i3++;
                    }
                }
            }
            return null;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
