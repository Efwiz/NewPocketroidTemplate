package org.jetbrains.kotlin.com.intellij.util.text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.asm.Advice;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;

/* JADX INFO: loaded from: classes3.dex */
public final class KeyboardLayoutUtil {
    private static final Map<Character, Character> ourLLtoASCII = new ConcurrentHashMap();

    public static Character getAsciiForChar(char c) {
        Map<Character, Character> map = ourLLtoASCII;
        Character ch = map.get(Character.valueOf(c));
        if (ch != null) {
            return ch;
        }
        if (!map.isEmpty() && !SystemInfoRt.isLinux) {
            return null;
        }
        char lowerCase = Character.toLowerCase(c);
        Character ch2 = (Character) HardCoded.LL.get(Character.valueOf(lowerCase));
        if (ch2 == null) {
            return null;
        }
        return Character.valueOf(lowerCase == c ? ch2.charValue() : Character.toUpperCase(ch2.charValue()));
    }

    private static class HardCoded {
        private static final Map<Character, Character> LL = new HashMap(33);

        static {
            char[] cArr = {1081, 'q', 1094, 'w', 1091, 'e', 1082, Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName.SYMBOL, 1077, Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL, 1085, 'y', 1075, 'u', 1096, 'i', 1097, 'o', 1079, Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL, 1093, '[', 1098, ']', 1092, 'a', 1099, Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL, 1074, Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, 1072, 'f', 1087, 'g', 1088, 'h', 1086, 'j', 1083, 'k', 1076, 'l', 1078, ';', 1101, '\'', 1103, 'z', 1095, 'x', 1089, 'c', 1084, 'v', 1080, 'b', 1090, 'n', 1100, Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL, 1073, ',', 1102, '.', '.', '/'};
            int i = 0;
            while (i < 66) {
                Map<Character, Character> map = LL;
                int i2 = i + 1;
                Character chValueOf = Character.valueOf(cArr[i]);
                i += 2;
                map.put(chValueOf, Character.valueOf(cArr[i2]));
            }
        }
    }
}
