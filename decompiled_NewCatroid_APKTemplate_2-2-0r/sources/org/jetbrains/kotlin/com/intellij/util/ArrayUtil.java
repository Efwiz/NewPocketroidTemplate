package org.jetbrains.kotlin.com.intellij.util;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes3.dex */
public final class ArrayUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final char[] EMPTY_CHAR_ARRAY = ArrayUtilRt.EMPTY_CHAR_ARRAY;
    public static final byte[] EMPTY_BYTE_ARRAY = ArrayUtilRt.EMPTY_BYTE_ARRAY;
    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
    public static final int[] EMPTY_INT_ARRAY = ArrayUtilRt.EMPTY_INT_ARRAY;
    public static final Object[] EMPTY_OBJECT_ARRAY = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
    public static final String[] EMPTY_STRING_ARRAY = ArrayUtilRt.EMPTY_STRING_ARRAY;
    public static final Class[] EMPTY_CLASS_ARRAY = ArrayUtilRt.EMPTY_CLASS_ARRAY;
    public static final long[] EMPTY_LONG_ARRAY = ArrayUtilRt.EMPTY_LONG_ARRAY;
    public static final File[] EMPTY_FILE_ARRAY = ArrayUtilRt.EMPTY_FILE_ARRAY;
    public static final Runnable[] EMPTY_RUNNABLE_ARRAY = new Runnable[0];

    @Deprecated
    public static final CharSequence EMPTY_CHAR_SEQUENCE = new String();
    public static final ArrayFactory<String> STRING_ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.util.ArrayUtil$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return ArrayUtil.newStringArray(i);
        }
    };
    public static final ArrayFactory<Object> OBJECT_ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.util.ArrayUtil$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return ArrayUtil.newObjectArray(i);
        }
    };

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:100:0x016c A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:106:0x0180  */
    /* JADX WARN: Code duplicated, block: B:21:0x0041 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 13 && i != 14 && i != 16 && i != 17 && i != 40 && i != 41) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 64:
                                case 65:
                                case 66:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 54:
                        case 55:
                        case 56:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 20:
                case 21:
                case 22:
                case 24:
                case 26:
                case 28:
                case 30:
                case 32:
                case 34:
                case 36:
                case 38:
                case 44:
                case 47:
                case 49:
                case 51:
                case 60:
                case 71:
                case 72:
                case 73:
                case 76:
                case 77:
                case 78:
                case 82:
                case 83:
                case 84:
                case 89:
                case 92:
                case 94:
                case 97:
                case 100:
                case 102:
                case 104:
                case 107:
                case 111:
                case 113:
                case 115:
                case 128:
                case 130:
                case 162:
                case 163:
                case 167:
                case 177:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 13 && i != 14 && i != 16 && i != 17 && i != 40 && i != 41) {
            switch (i) {
                case 20:
                case 21:
                case 22:
                case 24:
                case 26:
                case 28:
                case 30:
                case 32:
                case 34:
                case 36:
                case 38:
                case 44:
                case 47:
                case 49:
                case 51:
                case 60:
                case 71:
                case 72:
                case 73:
                case 76:
                case 77:
                case 78:
                case 82:
                case 83:
                case 84:
                case 89:
                case 92:
                case 94:
                case 97:
                case 100:
                case 102:
                case 104:
                case 107:
                case 111:
                case 113:
                case 115:
                case 128:
                case 130:
                case 162:
                case 163:
                case 167:
                case 177:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 54:
                        case 55:
                        case 56:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 64:
                                case 65:
                                case 66:
                                    i2 = 2;
                                    break;
                                default:
                                    i2 = 3;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 16:
            case 17:
            case 20:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 38:
            case 40:
            case 41:
            case 44:
            case 47:
            case 49:
            case 51:
            case 54:
            case 55:
            case 56:
            case 60:
            case 64:
            case 65:
            case 66:
            case 71:
            case 72:
            case 73:
            case 76:
            case 77:
            case 78:
            case 82:
            case 83:
            case 84:
            case 89:
            case 92:
            case 94:
            case 97:
            case 100:
            case 102:
            case 104:
            case 107:
            case 111:
            case 113:
            case 115:
            case 128:
            case 130:
            case 162:
            case 163:
            case 167:
            case 177:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/ArrayUtil";
                break;
            case 3:
            case 6:
            case 9:
            case 12:
            case 15:
            case 19:
            case 23:
            case 25:
            case 27:
            case 29:
            case 31:
            case 33:
            case 35:
            case 37:
            case 39:
            case 81:
            case 86:
            case 88:
            case 93:
            case 118:
            case 120:
            case 122:
            case 127:
            case 129:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 150:
            case 166:
            case 168:
            default:
                objArr[0] = "array";
                break;
            case 18:
            case 59:
            case 61:
            case 80:
            case 90:
            case 95:
            case 105:
            case 109:
                objArr[0] = "factory";
                break;
            case 42:
            case 48:
            case 79:
            case 176:
                objArr[0] = "collection";
                break;
            case 43:
            case 45:
                objArr[0] = "aClass";
                break;
            case 46:
                objArr[0] = FirebaseAnalytics.Param.SOURCE;
                break;
            case 50:
            case 178:
                objArr[0] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 52:
            case 62:
            case 67:
            case 69:
            case 74:
            case 125:
            case 174:
                objArr[0] = "a1";
                break;
            case 53:
            case 63:
            case 68:
            case 70:
            case 75:
            case 126:
            case 175:
                objArr[0] = "a2";
                break;
            case 57:
                objArr[0] = "c1";
                break;
            case 58:
                objArr[0] = "c2";
                break;
            case 85:
            case 91:
            case 96:
            case 99:
            case 101:
            case 106:
            case 108:
            case 110:
            case 112:
            case 114:
            case 116:
            case 117:
            case 152:
            case 153:
            case 154:
            case 156:
            case 157:
            case 164:
                objArr[0] = "src";
                break;
            case 87:
            case 103:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 98:
                objArr[0] = "componentType";
                break;
            case 119:
                objArr[0] = "prefix";
                break;
            case 121:
            case 123:
                objArr[0] = "subArray";
                break;
            case 124:
            case 146:
            case 158:
                objArr[0] = "comparator";
                break;
            case 131:
            case 133:
            case 135:
                objArr[0] = "obj1";
                break;
            case 132:
            case 134:
            case 136:
                objArr[0] = "obj2";
                break;
            case 143:
            case 144:
            case 147:
            case 159:
                objArr[0] = Constants.OBJECTS;
                break;
            case 145:
            case 155:
                objArr[0] = "predicate";
                break;
            case 148:
            case 149:
                objArr[0] = "ints";
                break;
            case 151:
                objArr[0] = "pattern";
                break;
            case 160:
                objArr[0] = "strings";
                break;
            case 161:
                objArr[0] = "sample";
                break;
            case 165:
                objArr[0] = "dst";
                break;
            case 169:
            case 170:
                objArr[0] = "time";
                break;
            case 171:
            case 172:
            case 173:
                objArr[0] = CompoundEntry.NAME_values;
                break;
        }
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 13 && i != 14 && i != 16 && i != 17 && i != 40 && i != 41) {
            switch (i) {
                case 20:
                case 21:
                case 22:
                    objArr[1] = "realloc";
                    break;
                case 24:
                case 26:
                case 36:
                case 38:
                case 97:
                case 100:
                    objArr[1] = "append";
                    break;
                case 28:
                case 30:
                case 32:
                case 34:
                    objArr[1] = "insert";
                    break;
                case 44:
                case 47:
                case 49:
                    objArr[1] = "toObjectArray";
                    break;
                case 51:
                    objArr[1] = "toIntArray";
                    break;
                case 60:
                    objArr[1] = "mergeCollections";
                    break;
                case 71:
                case 72:
                case 73:
                case 76:
                case 77:
                case 78:
                    objArr[1] = "mergeArrays";
                    break;
                case 82:
                case 83:
                case 84:
                    objArr[1] = "mergeArrayAndCollection";
                    break;
                case 89:
                case 92:
                case 94:
                    objArr[1] = "prepend";
                    break;
                case 102:
                case 107:
                case 111:
                case 113:
                case 115:
                    objArr[1] = ProductAction.ACTION_REMOVE;
                    break;
                case 104:
                    objArr[1] = "newArray";
                    break;
                case 128:
                case 130:
                    objArr[1] = "reverseArray";
                    break;
                case 162:
                    objArr[1] = "ensureExactSize";
                    break;
                case 163:
                    objArr[1] = "toStringArray";
                    break;
                case 167:
                    objArr[1] = "stripTrailingNulls";
                    break;
                case 177:
                    objArr[1] = "getComponentType";
                    break;
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 64:
                                case 65:
                                case 66:
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/com/intellij/util/ArrayUtil";
                                    break;
                            }
                        case 54:
                        case 55:
                        case 56:
                            objArr[1] = "mergeArrays";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "realloc";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
            case 10:
            case 11:
            case 13:
            case 14:
            case 16:
            case 17:
            case 20:
            case 21:
            case 22:
            case 24:
            case 26:
            case 28:
            case 30:
            case 32:
            case 34:
            case 36:
            case 38:
            case 40:
            case 41:
            case 44:
            case 47:
            case 49:
            case 51:
            case 54:
            case 55:
            case 56:
            case 60:
            case 64:
            case 65:
            case 66:
            case 71:
            case 72:
            case 73:
            case 76:
            case 77:
            case 78:
            case 82:
            case 83:
            case 84:
            case 89:
            case 92:
            case 94:
            case 97:
            case 100:
            case 102:
            case 104:
            case 107:
            case 111:
            case 113:
            case 115:
            case 128:
            case 130:
            case 162:
            case 163:
            case 167:
            case 177:
                break;
            case 3:
            case 6:
            case 9:
            case 12:
            case 15:
            case 18:
            case 19:
            case 39:
            default:
                objArr[2] = "realloc";
                break;
            case 23:
            case 25:
            case 35:
            case 37:
            case 85:
            case 95:
            case 96:
            case 98:
            case 99:
                objArr[2] = "append";
                break;
            case 27:
            case 29:
            case 31:
            case 33:
                objArr[2] = "insert";
                break;
            case 42:
            case 43:
            case 45:
            case 46:
            case 48:
                objArr[2] = "toObjectArray";
                break;
            case 50:
                objArr[2] = "toIntArray";
                break;
            case 52:
            case 53:
            case 61:
            case 62:
            case 63:
            case 67:
            case 68:
            case 69:
            case 70:
            case 74:
            case 75:
                objArr[2] = "mergeArrays";
                break;
            case 57:
            case 58:
            case 59:
                objArr[2] = "mergeCollections";
                break;
            case 79:
            case 80:
            case 81:
                objArr[2] = "mergeArrayAndCollection";
                break;
            case 86:
            case 87:
            case 88:
            case 90:
            case 91:
            case 93:
                objArr[2] = "prepend";
                break;
            case 101:
            case 105:
            case 106:
            case 108:
            case 109:
            case 110:
            case 112:
            case 114:
                objArr[2] = ProductAction.ACTION_REMOVE;
                break;
            case 103:
                objArr[2] = "newArray";
                break;
            case 116:
            case 117:
                objArr[2] = "find";
                break;
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
                objArr[2] = "startsWith";
                break;
            case 124:
            case 125:
            case 126:
                objArr[2] = Namer.EQUALS_METHOD_NAME;
                break;
            case 127:
            case 129:
                objArr[2] = "reverseArray";
                break;
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
                objArr[2] = "lexicographicCompare";
                break;
            case 137:
            case 138:
            case 139:
            case 140:
                objArr[2] = "swap";
                break;
            case 141:
                objArr[2] = "rotateLeft";
                break;
            case 142:
                objArr[2] = "rotateRight";
                break;
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
                objArr[2] = "indexOf";
                break;
            case 152:
            case 153:
            case 155:
            case 156:
            case 157:
            case 158:
                objArr[2] = "lastIndexOf";
                break;
            case 154:
                objArr[2] = "lastIndexOfNot";
                break;
            case 159:
            case 160:
                objArr[2] = "contains";
                break;
            case 161:
                objArr[2] = "ensureExactSize";
                break;
            case 164:
            case 165:
                objArr[2] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 166:
                objArr[2] = "stripTrailingNulls";
                break;
            case 168:
                objArr[2] = "trailingNullsIndex";
                break;
            case 169:
            case 170:
                objArr[2] = "averageAmongMedians";
                break;
            case 171:
                objArr[2] = "min";
                break;
            case 172:
            case 173:
                objArr[2] = "max";
                break;
            case 174:
            case 175:
                objArr[2] = "mergeSortedArrays";
                break;
            case 176:
                objArr[2] = "getComponentType";
                break;
            case 178:
                objArr[2] = "indexOfIdentity";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4 && i != 5 && i != 7 && i != 8 && i != 10 && i != 11 && i != 13 && i != 14 && i != 16 && i != 17 && i != 40 && i != 41) {
            switch (i) {
                case 20:
                case 21:
                case 22:
                case 24:
                case 26:
                case 28:
                case 30:
                case 32:
                case 34:
                case 36:
                case 38:
                case 44:
                case 47:
                case 49:
                case 51:
                case 60:
                case 71:
                case 72:
                case 73:
                case 76:
                case 77:
                case 78:
                case 82:
                case 83:
                case 84:
                case 89:
                case 92:
                case 94:
                case 97:
                case 100:
                case 102:
                case 104:
                case 107:
                case 111:
                case 113:
                case 115:
                case 128:
                case 130:
                case 162:
                case 163:
                case 167:
                case 177:
                    break;
                default:
                    switch (i) {
                        case 54:
                        case 55:
                        case 56:
                            break;
                        default:
                            switch (i) {
                                case 64:
                                case 65:
                                case 66:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private ArrayUtil() {
    }

    public static boolean[] realloc(boolean[] zArr, int i) {
        if (zArr == null) {
            $$$reportNull$$$0(6);
        }
        if (i == 0) {
            boolean[] zArr2 = ArrayUtilRt.EMPTY_BOOLEAN_ARRAY;
            if (zArr2 == null) {
                $$$reportNull$$$0(7);
            }
            return zArr2;
        }
        if (zArr.length != i) {
            zArr = Arrays.copyOf(zArr, i);
        }
        if (zArr == null) {
            $$$reportNull$$$0(8);
        }
        return zArr;
    }

    public static short[] realloc(short[] sArr, int i) {
        if (sArr == null) {
            $$$reportNull$$$0(9);
        }
        if (i == 0) {
            short[] sArr2 = ArrayUtilRt.EMPTY_SHORT_ARRAY;
            if (sArr2 == null) {
                $$$reportNull$$$0(10);
            }
            return sArr2;
        }
        if (sArr.length != i) {
            sArr = Arrays.copyOf(sArr, i);
        }
        if (sArr == null) {
            $$$reportNull$$$0(11);
        }
        return sArr;
    }

    public static int[] realloc(int[] iArr, int i) {
        if (iArr == null) {
            $$$reportNull$$$0(15);
        }
        if (i == 0) {
            int[] iArr2 = EMPTY_INT_ARRAY;
            if (iArr2 == null) {
                $$$reportNull$$$0(16);
            }
            return iArr2;
        }
        if (iArr.length != i) {
            iArr = Arrays.copyOf(iArr, i);
        }
        if (iArr == null) {
            $$$reportNull$$$0(17);
        }
        return iArr;
    }

    public static <T> T[] realloc(T[] tArr, int i, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(18);
        }
        if (tArr == null) {
            $$$reportNull$$$0(19);
        }
        int length = tArr.length;
        if (length == i) {
            if (tArr == null) {
                $$$reportNull$$$0(20);
            }
            return tArr;
        }
        Object[] objArrCreate = arrayFactory.create(i);
        if (i == 0) {
            if (objArrCreate == null) {
                $$$reportNull$$$0(21);
            }
            return objArrCreate;
        }
        System.arraycopy(tArr, 0, objArrCreate, 0, Math.min(length, i));
        if (objArrCreate == null) {
            $$$reportNull$$$0(22);
        }
        return objArrCreate;
    }

    public static int[] append(int[] iArr, int i) {
        if (iArr == null) {
            $$$reportNull$$$0(25);
        }
        int[] iArrRealloc = realloc(iArr, iArr.length + 1);
        iArrRealloc[iArrRealloc.length - 1] = i;
        if (iArrRealloc == null) {
            $$$reportNull$$$0(26);
        }
        return iArrRealloc;
    }

    public static <T> T[] insert(T[] tArr, int i, T t) {
        if (tArr == null) {
            $$$reportNull$$$0(27);
        }
        T[] tArr2 = (T[]) newArray(getComponentType(tArr), tArr.length + 1);
        System.arraycopy(tArr, 0, tArr2, 0, i);
        tArr2[i] = t;
        System.arraycopy(tArr, i, tArr2, i + 1, tArr.length - i);
        if (tArr2 == null) {
            $$$reportNull$$$0(28);
        }
        return tArr2;
    }

    public static int[] insert(int[] iArr, int i, int i2) {
        if (iArr == null) {
            $$$reportNull$$$0(29);
        }
        int[] iArr2 = new int[iArr.length + 1];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        iArr2[i] = i2;
        System.arraycopy(iArr, i, iArr2, i + 1, iArr.length - i);
        return iArr2;
    }

    public static char[] insert(char[] cArr, int i, char c) {
        if (cArr == null) {
            $$$reportNull$$$0(33);
        }
        char[] cArr2 = new char[cArr.length + 1];
        System.arraycopy(cArr, 0, cArr2, 0, i);
        cArr2[i] = c;
        System.arraycopy(cArr, i, cArr2, i + 1, cArr.length - i);
        return cArr2;
    }

    public static char[] realloc(char[] cArr, int i) {
        if (cArr == null) {
            $$$reportNull$$$0(39);
        }
        if (i == 0) {
            char[] cArr2 = ArrayUtilRt.EMPTY_CHAR_ARRAY;
            if (cArr2 == null) {
                $$$reportNull$$$0(40);
            }
            return cArr2;
        }
        if (cArr.length != i) {
            cArr = Arrays.copyOf(cArr, i);
        }
        if (cArr == null) {
            $$$reportNull$$$0(41);
        }
        return cArr;
    }

    public static <T> T[] toObjectArray(Collection<? extends T> collection, Class<T> cls) {
        if (collection == null) {
            $$$reportNull$$$0(42);
        }
        if (cls == null) {
            $$$reportNull$$$0(43);
        }
        T[] tArr = (T[]) collection.toArray(newArray(cls, collection.size()));
        if (tArr == null) {
            $$$reportNull$$$0(44);
        }
        return tArr;
    }

    public static Object[] toObjectArray(Collection<?> collection) {
        if (collection == null) {
            $$$reportNull$$$0(48);
        }
        Object[] array = collection.toArray(ArrayUtilRt.EMPTY_OBJECT_ARRAY);
        if (array == null) {
            $$$reportNull$$$0(49);
        }
        return array;
    }

    public static <T> T[] mergeArrays(T[] tArr, T[] tArr2) {
        if (tArr == null) {
            $$$reportNull$$$0(52);
        }
        if (tArr2 == null) {
            $$$reportNull$$$0(53);
        }
        if (tArr.length == 0) {
            if (tArr2 == null) {
                $$$reportNull$$$0(54);
            }
            return tArr2;
        }
        if (tArr2.length == 0) {
            if (tArr == null) {
                $$$reportNull$$$0(55);
            }
            return tArr;
        }
        Class<?> componentType = getComponentType(tArr);
        Class<?> componentType2 = getComponentType(tArr2);
        if (!componentType.isAssignableFrom(componentType2)) {
            componentType = componentType2;
        }
        T[] tArr3 = (T[]) newArray(componentType, tArr.length + tArr2.length);
        System.arraycopy(tArr, 0, tArr3, 0, tArr.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        if (tArr3 == null) {
            $$$reportNull$$$0(56);
        }
        return tArr3;
    }

    public static <T> T[] mergeCollections(Collection<? extends T> collection, Collection<? extends T> collection2, ArrayFactory<? extends T> arrayFactory) {
        if (collection == null) {
            $$$reportNull$$$0(57);
        }
        if (collection2 == null) {
            $$$reportNull$$$0(58);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(59);
        }
        Object[] objArrCreate = arrayFactory.create(collection.size() + collection2.size());
        Iterator<? extends T> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            objArrCreate[i] = it.next();
            i++;
        }
        Iterator<? extends T> it2 = collection2.iterator();
        while (it2.hasNext()) {
            objArrCreate[i] = it2.next();
            i++;
        }
        if (objArrCreate == null) {
            $$$reportNull$$$0(60);
        }
        return objArrCreate;
    }

    public static <T> T[] mergeArrays(T[] tArr, T[] tArr2, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(61);
        }
        if (tArr == null) {
            $$$reportNull$$$0(62);
        }
        if (tArr2 == null) {
            $$$reportNull$$$0(63);
        }
        if (tArr.length == 0) {
            if (tArr2 == null) {
                $$$reportNull$$$0(64);
            }
            return tArr2;
        }
        if (tArr2.length == 0) {
            if (tArr == null) {
                $$$reportNull$$$0(65);
            }
            return tArr;
        }
        Object[] objArrCreate = arrayFactory.create(tArr.length + tArr2.length);
        System.arraycopy(tArr, 0, objArrCreate, 0, tArr.length);
        System.arraycopy(tArr2, 0, objArrCreate, tArr.length, tArr2.length);
        if (objArrCreate == null) {
            $$$reportNull$$$0(66);
        }
        return objArrCreate;
    }

    public static String[] mergeArrays(String[] strArr, String... strArr2) {
        if (strArr == null) {
            $$$reportNull$$$0(67);
        }
        if (strArr2 == null) {
            $$$reportNull$$$0(68);
        }
        return (String[]) mergeArrays(strArr, strArr2, STRING_ARRAY_FACTORY);
    }

    public static <T> T[] mergeArrayAndCollection(T[] tArr, Collection<? extends T> collection, ArrayFactory<? extends T> arrayFactory) {
        if (collection == null) {
            $$$reportNull$$$0(79);
        }
        if (arrayFactory == null) {
            $$$reportNull$$$0(80);
        }
        if (tArr == null) {
            $$$reportNull$$$0(81);
        }
        if (collection.isEmpty()) {
            if (tArr == null) {
                $$$reportNull$$$0(82);
            }
            return tArr;
        }
        try {
            T[] tArr2 = (T[]) collection.toArray(arrayFactory.create(collection.size()));
            if (tArr.length == 0) {
                if (tArr2 == null) {
                    $$$reportNull$$$0(83);
                }
                return tArr2;
            }
            Object[] objArrCreate = arrayFactory.create(tArr.length + collection.size());
            System.arraycopy(tArr, 0, objArrCreate, 0, tArr.length);
            System.arraycopy(tArr2, 0, objArrCreate, tArr.length, tArr2.length);
            if (objArrCreate == null) {
                $$$reportNull$$$0(84);
            }
            return objArrCreate;
        } catch (ArrayStoreException e) {
            throw new RuntimeException("Bad elements in collection: " + collection, e);
        }
    }

    public static <T> T[] append(T[] tArr, T t) {
        if (tArr == null) {
            $$$reportNull$$$0(85);
        }
        return (T[]) append(tArr, t, getComponentType(tArr));
    }

    public static <T> T[] prepend(T t, T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(86);
        }
        return (T[]) prepend(t, tArr, getComponentType(tArr));
    }

    public static <T> T[] prepend(T t, T[] tArr, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(87);
        }
        if (tArr == null) {
            $$$reportNull$$$0(88);
        }
        int length = tArr.length;
        T[] tArr2 = (T[]) newArray(cls, length + 1);
        System.arraycopy(tArr, 0, tArr2, 1, length);
        tArr2[0] = t;
        if (tArr2 == null) {
            $$$reportNull$$$0(89);
        }
        return tArr2;
    }

    public static <T> T[] prepend(T t, T[] tArr, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(90);
        }
        if (tArr == null) {
            $$$reportNull$$$0(91);
        }
        int length = tArr.length;
        Object[] objArrCreate = arrayFactory.create(length + 1);
        System.arraycopy(tArr, 0, objArrCreate, 1, length);
        objArrCreate[0] = t;
        if (objArrCreate == null) {
            $$$reportNull$$$0(92);
        }
        return objArrCreate;
    }

    public static <T> T[] append(T[] tArr, T t, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(95);
        }
        if (tArr == null) {
            $$$reportNull$$$0(96);
        }
        int length = tArr.length;
        Object[] objArrCreate = arrayFactory.create(length + 1);
        System.arraycopy(tArr, 0, objArrCreate, 0, length);
        objArrCreate[length] = t;
        if (objArrCreate == null) {
            $$$reportNull$$$0(97);
        }
        return objArrCreate;
    }

    public static <T> T[] append(T[] tArr, T t, Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(98);
        }
        if (tArr == null) {
            $$$reportNull$$$0(99);
        }
        int length = tArr.length;
        T[] tArr2 = (T[]) newArray(cls, length + 1);
        System.arraycopy(tArr, 0, tArr2, 0, length);
        tArr2[length] = t;
        if (tArr2 == null) {
            $$$reportNull$$$0(100);
        }
        return tArr2;
    }

    public static <T> T[] remove(T[] tArr, int i) {
        if (tArr == null) {
            $$$reportNull$$$0(101);
        }
        int length = tArr.length;
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException("invalid index: " + i);
        }
        T[] tArr2 = (T[]) newArray(getComponentType(tArr), length - 1);
        System.arraycopy(tArr, 0, tArr2, 0, i);
        System.arraycopy(tArr, i + 1, tArr2, i, (length - i) - 1);
        if (tArr2 == null) {
            $$$reportNull$$$0(102);
        }
        return tArr2;
    }

    public static <T> T[] newArray(Class<T> cls, int i) {
        if (cls == null) {
            $$$reportNull$$$0(103);
        }
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        if (tArr == null) {
            $$$reportNull$$$0(104);
        }
        return tArr;
    }

    public static <T> T[] remove(T[] tArr, int i, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(105);
        }
        if (tArr == null) {
            $$$reportNull$$$0(106);
        }
        int length = tArr.length;
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException("invalid index: " + i);
        }
        Object[] objArrCreate = arrayFactory.create(length - 1);
        System.arraycopy(tArr, 0, objArrCreate, 0, i);
        System.arraycopy(tArr, i + 1, objArrCreate, i, (length - i) - 1);
        if (objArrCreate == null) {
            $$$reportNull$$$0(107);
        }
        return objArrCreate;
    }

    public static <T> T[] remove(T[] tArr, T t) {
        if (tArr == null) {
            $$$reportNull$$$0(108);
        }
        int iFind = ArrayUtilRt.find(tArr, t);
        return iFind == -1 ? tArr : (T[]) remove(tArr, iFind);
    }

    public static <T> T[] remove(T[] tArr, T t, ArrayFactory<? extends T> arrayFactory) {
        if (arrayFactory == null) {
            $$$reportNull$$$0(109);
        }
        if (tArr == null) {
            $$$reportNull$$$0(110);
        }
        int iFind = find(tArr, t);
        if (iFind != -1) {
            return (T[]) remove(tArr, iFind, arrayFactory);
        }
        if (tArr == null) {
            $$$reportNull$$$0(111);
        }
        return tArr;
    }

    public static int[] remove(int[] iArr, int i) {
        if (iArr == null) {
            $$$reportNull$$$0(112);
        }
        int length = iArr.length;
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException("invalid index: " + i);
        }
        int[] iArrNewIntArray = newIntArray(iArr.length - 1);
        System.arraycopy(iArr, 0, iArrNewIntArray, 0, i);
        System.arraycopy(iArr, i + 1, iArrNewIntArray, i, (length - i) - 1);
        if (iArrNewIntArray == null) {
            $$$reportNull$$$0(113);
        }
        return iArrNewIntArray;
    }

    public static int find(int[] iArr, int i) {
        if (iArr == null) {
            $$$reportNull$$$0(116);
        }
        return indexOf(iArr, i);
    }

    public static <T> int find(T[] tArr, T t) {
        if (tArr == null) {
            $$$reportNull$$$0(117);
        }
        return ArrayUtilRt.find(tArr, t);
    }

    public static boolean startsWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            $$$reportNull$$$0(118);
        }
        if (bArr2 == null) {
            $$$reportNull$$$0(119);
        }
        if (bArr == bArr2) {
            return true;
        }
        int length = bArr2.length;
        if (bArr.length < length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static <T> void swap(T[] tArr, int i, int i2) {
        if (tArr == null) {
            $$$reportNull$$$0(137);
        }
        T t = tArr[i];
        tArr[i] = tArr[i2];
        tArr[i2] = t;
    }

    public static int indexOf(Object[] objArr, Object obj) {
        if (objArr == null) {
            $$$reportNull$$$0(143);
        }
        return ArrayUtilRt.indexOf(objArr, obj, 0, objArr.length);
    }

    public static int indexOf(int[] iArr, int i) {
        if (iArr == null) {
            $$$reportNull$$$0(149);
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public static int lastIndexOfNot(int[] iArr, int i) {
        if (iArr == null) {
            $$$reportNull$$$0(154);
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (iArr[length] != i) {
                return length;
            }
        }
        return -1;
    }

    @SafeVarargs
    public static <T> boolean contains(T t, T... tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(159);
        }
        return indexOf(tArr, t) >= 0;
    }

    public static boolean contains(String str, String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(160);
        }
        return indexOf(strArr, str) >= 0;
    }

    public static int[] newIntArray(int i) {
        return i == 0 ? ArrayUtilRt.EMPTY_INT_ARRAY : new int[i];
    }

    public static byte[] newByteArray(int i) {
        return i == 0 ? ArrayUtilRt.EMPTY_BYTE_ARRAY : new byte[i];
    }

    public static String[] newStringArray(int i) {
        return i == 0 ? ArrayUtilRt.EMPTY_STRING_ARRAY : new String[i];
    }

    public static Object[] newObjectArray(int i) {
        return i == 0 ? ArrayUtilRt.EMPTY_OBJECT_ARRAY : new Object[i];
    }

    public static <E> E[] ensureExactSize(int i, E[] eArr) {
        if (eArr == null) {
            $$$reportNull$$$0(161);
        }
        if (i != eArr.length) {
            return (E[]) newArray(getComponentType(eArr), i);
        }
        if (eArr == null) {
            $$$reportNull$$$0(162);
        }
        return eArr;
    }

    public static <T> T getLastElement(T[] tArr) {
        if (tArr == null || tArr.length <= 0) {
            return null;
        }
        return tArr[tArr.length - 1];
    }

    public static String[] toStringArray(Collection<String> collection) {
        String[] stringArray = ArrayUtilRt.toStringArray(collection);
        if (stringArray == null) {
            $$$reportNull$$$0(163);
        }
        return stringArray;
    }

    public static <T> T[] stripTrailingNulls(T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(166);
        }
        if (tArr.length != 0 && tArr[tArr.length - 1] == null) {
            tArr = (T[]) Arrays.copyOf(tArr, trailingNullsIndex(tArr));
        }
        if (tArr == null) {
            $$$reportNull$$$0(167);
        }
        return tArr;
    }

    private static <T> int trailingNullsIndex(T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(168);
        }
        for (int length = tArr.length - 1; length >= 0; length--) {
            if (tArr[length] != null) {
                return length + 1;
            }
        }
        return 0;
    }

    public static int max(int[] iArr) {
        if (iArr == null) {
            $$$reportNull$$$0(172);
        }
        int i = Integer.MIN_VALUE;
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    public static double max(double[] dArr) {
        if (dArr == null) {
            $$$reportNull$$$0(173);
        }
        double d = Double.NEGATIVE_INFINITY;
        for (double d2 : dArr) {
            if (d2 > d) {
                d = d2;
            }
        }
        return d;
    }

    public static <T> Class<T> getComponentType(T[] tArr) {
        if (tArr == null) {
            $$$reportNull$$$0(176);
        }
        Class<T> cls = (Class<T>) tArr.getClass().getComponentType();
        if (cls == null) {
            $$$reportNull$$$0(177);
        }
        return cls;
    }

    public static <T> int indexOfIdentity(T[] tArr, T t) {
        if (tArr == null) {
            $$$reportNull$$$0(178);
        }
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i] == t) {
                return i;
            }
        }
        return -1;
    }
}
