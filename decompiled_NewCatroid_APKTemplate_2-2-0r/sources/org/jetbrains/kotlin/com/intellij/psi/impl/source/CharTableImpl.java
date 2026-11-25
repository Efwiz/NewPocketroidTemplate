package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.beust.jcommander.Parameters;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.lang.reflect.Field;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.CharTable;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes3.dex */
public final class CharTableImpl implements CharTable {
    private static final StringHashToCharSequencesMap STATIC_ENTRIES = newStaticSet();
    private final StringHashToCharSequencesMap entries = new StringHashToCharSequencesMap(10, 0.9f);

    /* JADX WARN: Code duplicated, block: B:36:0x005a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 7 || i == 14 || i == 3 || i == 4 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 7 || i == 14 || i == 3 || i == 4 || i == 10 || i == 11) ? 2 : 3];
        if (i == 1 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/CharTableImpl";
        } else if (i == 6) {
            objArr[0] = "baseText";
        } else if (i != 7 && i != 10 && i != 11) {
            switch (i) {
                case 14:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/CharTableImpl";
                    break;
                case 15:
                    objArr[0] = "aClass";
                    break;
                case 16:
                    objArr[0] = "sequence";
                    break;
                default:
                    objArr[0] = "text";
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/CharTableImpl";
        }
        if (i == 1 || i == 7) {
            objArr[1] = "intern";
        } else if (i == 14) {
            objArr[1] = "newStaticSet";
        } else if (i == 3 || i == 4) {
            objArr[1] = "doIntern";
        } else if (i == 10 || i == 11) {
            objArr[1] = "createSequence";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/CharTableImpl";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 7:
            case 10:
            case 11:
            case 14:
                break;
            case 2:
            case 5:
                objArr[2] = "doIntern";
                break;
            case 6:
            default:
                objArr[2] = "intern";
                break;
            case 8:
            case 9:
                objArr[2] = "createSequence";
                break;
            case 12:
                objArr[2] = "getStaticInterned";
                break;
            case 13:
                objArr[2] = "staticIntern";
                break;
            case 15:
                objArr[2] = "addStringsFromClassToStatics";
                break;
            case 16:
                objArr[2] = "subSequenceHashCode";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 7 && i != 14 && i != 3 && i != 4 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        addStringsFromClassToStatics(CommonClassNames.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CharTable
    public CharSequence intern(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        CharSequence charSequenceCreateSequence = charSequence.length() > 40 ? createSequence(charSequence) : doIntern(charSequence);
        if (charSequenceCreateSequence == null) {
            $$$reportNull$$$0(1);
        }
        return charSequenceCreateSequence;
    }

    private CharSequence doIntern(CharSequence charSequence, int i, int i2) {
        CharSequence subSequenceWithHashCode;
        CharSequence orAddSubSequenceWithHashCode;
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        int iSubSequenceHashCode = subSequenceHashCode(charSequence, i, i2);
        StringHashToCharSequencesMap stringHashToCharSequencesMap = STATIC_ENTRIES;
        synchronized (stringHashToCharSequencesMap) {
            subSequenceWithHashCode = stringHashToCharSequencesMap.getSubSequenceWithHashCode(iSubSequenceHashCode, charSequence, i, i2);
        }
        if (subSequenceWithHashCode != null) {
            if (subSequenceWithHashCode == null) {
                $$$reportNull$$$0(3);
            }
            return subSequenceWithHashCode;
        }
        synchronized (this.entries) {
            orAddSubSequenceWithHashCode = this.entries.getOrAddSubSequenceWithHashCode(iSubSequenceHashCode, charSequence, i, i2);
        }
        if (orAddSubSequenceWithHashCode == null) {
            $$$reportNull$$$0(4);
        }
        return orAddSubSequenceWithHashCode;
    }

    public CharSequence doIntern(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        return doIntern(charSequence, 0, charSequence.length());
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.CharTable
    public CharSequence intern(CharSequence charSequence, int i, int i2) {
        CharSequence charSequenceDoIntern;
        if (charSequence == null) {
            $$$reportNull$$$0(6);
        }
        int i3 = i2 - i;
        if (i3 == charSequence.length()) {
            charSequenceDoIntern = intern(charSequence);
        } else if (i3 > 40) {
            charSequenceDoIntern = createSequence(charSequence, i, i2);
        } else {
            charSequenceDoIntern = doIntern(charSequence, i, i2);
        }
        if (charSequenceDoIntern == null) {
            $$$reportNull$$$0(7);
        }
        return charSequenceDoIntern;
    }

    private static String createSequence(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
        return createSequence(charSequence, 0, charSequence.length());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String createSequence(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(9);
        }
        if (charSequence instanceof String) {
            String strSubstring = ((String) charSequence).substring(i, i2);
            if (strSubstring == null) {
                $$$reportNull$$$0(10);
            }
            return strSubstring;
        }
        String string = charSequence.subSequence(i, i2).toString();
        if (string == null) {
            $$$reportNull$$$0(11);
        }
        return string;
    }

    public static CharSequence getStaticInterned(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(12);
        }
        return STATIC_ENTRIES.get(charSequence);
    }

    public static void staticIntern(String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        StringHashToCharSequencesMap stringHashToCharSequencesMap = STATIC_ENTRIES;
        synchronized (stringHashToCharSequencesMap) {
            stringHashToCharSequencesMap.add(str);
        }
    }

    private static StringHashToCharSequencesMap newStaticSet() {
        StringHashToCharSequencesMap stringHashToCharSequencesMap = new StringHashToCharSequencesMap(10, 0.9f);
        stringHashToCharSequencesMap.add("==");
        stringHashToCharSequencesMap.add("!=");
        stringHashToCharSequencesMap.add("||");
        stringHashToCharSequencesMap.add("++");
        stringHashToCharSequencesMap.add("--");
        stringHashToCharSequencesMap.add("<");
        stringHashToCharSequencesMap.add("<=");
        stringHashToCharSequencesMap.add("<<=");
        stringHashToCharSequencesMap.add("<<");
        stringHashToCharSequencesMap.add(">");
        stringHashToCharSequencesMap.add(ContainerUtils.FIELD_DELIMITER);
        stringHashToCharSequencesMap.add("&&");
        stringHashToCharSequencesMap.add("+=");
        stringHashToCharSequencesMap.add("-=");
        stringHashToCharSequencesMap.add("*=");
        stringHashToCharSequencesMap.add("/=");
        stringHashToCharSequencesMap.add("&=");
        stringHashToCharSequencesMap.add("|=");
        stringHashToCharSequencesMap.add("^=");
        stringHashToCharSequencesMap.add("%=");
        stringHashToCharSequencesMap.add("(");
        stringHashToCharSequencesMap.add(Constant.AFTER_QUTO);
        stringHashToCharSequencesMap.add("{");
        stringHashToCharSequencesMap.add("}");
        stringHashToCharSequencesMap.add("[");
        stringHashToCharSequencesMap.add("]");
        stringHashToCharSequencesMap.add(";");
        stringHashToCharSequencesMap.add(",");
        stringHashToCharSequencesMap.add("...");
        stringHashToCharSequencesMap.add(".");
        stringHashToCharSequencesMap.add(ContainerUtils.KEY_VALUE_DELIMITER);
        stringHashToCharSequencesMap.add("!");
        stringHashToCharSequencesMap.add("~");
        stringHashToCharSequencesMap.add(TypeDescription.Generic.OfWildcardType.SYMBOL);
        stringHashToCharSequencesMap.add(":");
        stringHashToCharSequencesMap.add(Marker.ANY_NON_NULL_MARKER);
        stringHashToCharSequencesMap.add(Parameters.DEFAULT_OPTION_PREFIXES);
        stringHashToCharSequencesMap.add("*");
        stringHashToCharSequencesMap.add("/");
        stringHashToCharSequencesMap.add("|");
        stringHashToCharSequencesMap.add("^");
        stringHashToCharSequencesMap.add("%");
        stringHashToCharSequencesMap.add(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        stringHashToCharSequencesMap.add(" ");
        stringHashToCharSequencesMap.add("  ");
        stringHashToCharSequencesMap.add("   ");
        stringHashToCharSequencesMap.add("    ");
        stringHashToCharSequencesMap.add("     ");
        stringHashToCharSequencesMap.add("      ");
        stringHashToCharSequencesMap.add("       ");
        stringHashToCharSequencesMap.add("        ");
        stringHashToCharSequencesMap.add("         ");
        stringHashToCharSequencesMap.add("          ");
        stringHashToCharSequencesMap.add("           ");
        stringHashToCharSequencesMap.add("            ");
        stringHashToCharSequencesMap.add("             ");
        stringHashToCharSequencesMap.add("              ");
        stringHashToCharSequencesMap.add("               ");
        stringHashToCharSequencesMap.add("\n");
        stringHashToCharSequencesMap.add("\n  ");
        stringHashToCharSequencesMap.add("\n    ");
        stringHashToCharSequencesMap.add("\n      ");
        stringHashToCharSequencesMap.add("\n        ");
        stringHashToCharSequencesMap.add("\n          ");
        stringHashToCharSequencesMap.add("\n            ");
        stringHashToCharSequencesMap.add("\n              ");
        stringHashToCharSequencesMap.add("\n                ");
        stringHashToCharSequencesMap.add("<");
        stringHashToCharSequencesMap.add(">");
        stringHashToCharSequencesMap.add("</");
        stringHashToCharSequencesMap.add("/>");
        stringHashToCharSequencesMap.add("\"");
        stringHashToCharSequencesMap.add(FormatHelper.QUOTE);
        stringHashToCharSequencesMap.add("<![CDATA[");
        stringHashToCharSequencesMap.add("]]>");
        stringHashToCharSequencesMap.add("<!--");
        stringHashToCharSequencesMap.add("-->");
        stringHashToCharSequencesMap.add("<!DOCTYPE");
        stringHashToCharSequencesMap.add("SYSTEM");
        stringHashToCharSequencesMap.add("PUBLIC");
        stringHashToCharSequencesMap.add("<?");
        stringHashToCharSequencesMap.add("?>");
        stringHashToCharSequencesMap.add("<%");
        stringHashToCharSequencesMap.add("%>");
        stringHashToCharSequencesMap.add("<%=");
        stringHashToCharSequencesMap.add("<%@");
        stringHashToCharSequencesMap.add("${");
        stringHashToCharSequencesMap.add("");
        return stringHashToCharSequencesMap;
    }

    public static void addStringsFromClassToStatics(Class<?> cls) {
        String str;
        if (cls == null) {
            $$$reportNull$$$0(15);
        }
        for (Field field : cls.getDeclaredFields()) {
            if ((field.getModifiers() & 8) != 0 && (field.getModifiers() & 1) != 0 && String.class.equals(field.getType()) && (str = (String) ReflectionUtil.getStaticFieldValue(cls, String.class, field.getName())) != null) {
                staticIntern(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class StringHashToCharSequencesMap extends Int2ObjectOpenHashMap<Object> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            if (i == 2) {
                objArr[0] = LanguageCodeUtil.CS;
            } else if (i != 3) {
                switch (i) {
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/CharTableImpl$StringHashToCharSequencesMap";
                        break;
                    default:
                        objArr[0] = "sequence";
                        break;
                }
            } else {
                objArr[0] = "baseSequence";
            }
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[1] = "getOrAddSubSequenceWithHashCode";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/CharTableImpl$StringHashToCharSequencesMap";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[2] = "getSubSequenceWithHashCode";
                    break;
                case 2:
                case 3:
                    objArr[2] = "charSequenceSubSequenceEquals";
                    break;
                case 4:
                default:
                    objArr[2] = "get";
                    break;
                case 5:
                    objArr[2] = ProductAction.ACTION_ADD;
                    break;
                case 6:
                    objArr[2] = "getOrAddSubSequenceWithHashCode";
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    throw new IllegalStateException(str2);
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        private StringHashToCharSequencesMap(int i, float f) {
            super(i, f);
        }

        private CharSequence get(CharSequence charSequence, int i, int i2) {
            if (charSequence == null) {
                $$$reportNull$$$0(0);
            }
            return getSubSequenceWithHashCode(CharTableImpl.subSequenceHashCode(charSequence, i, i2), charSequence, i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence getSubSequenceWithHashCode(int i, CharSequence charSequence, int i2, int i3) {
            if (charSequence == null) {
                $$$reportNull$$$0(1);
            }
            Object obj = get(i);
            if (obj == null) {
                return null;
            }
            if (obj instanceof CharSequence) {
                CharSequence charSequence2 = (CharSequence) obj;
                if (charSequenceSubSequenceEquals(charSequence2, charSequence, i2, i3)) {
                    return charSequence2;
                }
                return null;
            }
            if (obj instanceof CharSequence[]) {
                for (CharSequence charSequence3 : (CharSequence[]) obj) {
                    if (charSequenceSubSequenceEquals(charSequence3, charSequence, i2, i3)) {
                        return charSequence3;
                    }
                }
            }
            return null;
        }

        private static boolean charSequenceSubSequenceEquals(CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
            if (charSequence == null) {
                $$$reportNull$$$0(2);
            }
            if (charSequence2 == null) {
                $$$reportNull$$$0(3);
            }
            if (charSequence.length() != i2 - i) {
                return false;
            }
            if (charSequence == charSequence2 && i == 0) {
                return true;
            }
            int length = charSequence.length();
            for (int i3 = 0; i3 < length; i3++) {
                if (charSequence.charAt(i3) != charSequence2.charAt(i + i3)) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence get(CharSequence charSequence) {
            if (charSequence == null) {
                $$$reportNull$$$0(4);
            }
            return get(charSequence, 0, charSequence.length());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void add(CharSequence charSequence) {
            if (charSequence == null) {
                $$$reportNull$$$0(5);
            }
            int length = charSequence.length();
            getOrAddSubSequenceWithHashCode(CharTableImpl.subSequenceHashCode(charSequence, 0, length), charSequence, 0, length);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CharSequence getOrAddSubSequenceWithHashCode(int i, CharSequence charSequence, int i2, int i3) {
            String strCreateSequence;
            if (charSequence == null) {
                $$$reportNull$$$0(6);
            }
            Object obj = get(i);
            if (obj == null) {
                strCreateSequence = CharTableImpl.createSequence(charSequence, i2, i3);
                put(i, strCreateSequence);
            } else {
                if (obj instanceof CharSequence) {
                    CharSequence charSequence2 = (CharSequence) obj;
                    if (charSequenceSubSequenceEquals(charSequence2, charSequence, i2, i3)) {
                        if (charSequence2 == null) {
                            $$$reportNull$$$0(7);
                        }
                        return charSequence2;
                    }
                    strCreateSequence = CharTableImpl.createSequence(charSequence, i2, i3);
                    put(i, new CharSequence[]{charSequence2, strCreateSequence});
                } else if (obj instanceof CharSequence[]) {
                    CharSequence[] charSequenceArr = (CharSequence[]) obj;
                    for (CharSequence charSequence3 : charSequenceArr) {
                        if (charSequenceSubSequenceEquals(charSequence3, charSequence, i2, i3)) {
                            if (charSequence3 == null) {
                                $$$reportNull$$$0(8);
                            }
                            return charSequence3;
                        }
                    }
                    strCreateSequence = CharTableImpl.createSequence(charSequence, i2, i3);
                    put(i, (CharSequence[]) ArrayUtil.append((String[]) charSequenceArr, strCreateSequence, (ArrayFactory<? extends String>) new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.CharTableImpl$StringHashToCharSequencesMap$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
                        public final Object[] create(int i4) {
                            return CharTableImpl.StringHashToCharSequencesMap.lambda$getOrAddSubSequenceWithHashCode$0(i4);
                        }
                    }));
                } else {
                    $$$reportNull$$$0(9);
                    return null;
                }
            }
            if (strCreateSequence == null) {
                $$$reportNull$$$0(10);
            }
            return strCreateSequence;
        }

        static /* synthetic */ CharSequence[] lambda$getOrAddSubSequenceWithHashCode$0(int i) {
            return new CharSequence[i];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int subSequenceHashCode(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(16);
        }
        if (i == 0 && i2 == charSequence.length()) {
            return StringUtil.stringHashCode(charSequence);
        }
        return StringUtil.stringHashCode(charSequence, i, i2);
    }
}
