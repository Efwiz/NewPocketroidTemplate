package org.eclipse.jdt.internal.compiler.parser;

import java.util.ArrayList;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.SingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.ast.Wildcard;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;

/* JADX INFO: loaded from: classes14.dex */
public abstract class TypeConverter {
    protected boolean has1_5Compliance;
    private char memberTypeSeparator;
    int namePos;
    protected ProblemReporter problemReporter;

    protected TypeConverter(ProblemReporter problemReporter, char c) {
        this.problemReporter = problemReporter;
        this.has1_5Compliance = problemReporter.options.originalComplianceLevel >= ClassFileConstants.JDK1_5;
        this.memberTypeSeparator = c;
    }

    private void addIdentifiers(String str, int i, int i2, int i3, ArrayList arrayList) {
        if (i3 == 1) {
            char[] cArr = new char[i2 - i];
            str.getChars(i, i2, cArr, 0);
            arrayList.add(cArr);
            return;
        }
        arrayList.add(extractIdentifiers(str, i, i2 - 1, i3));
    }

    protected ImportReference createImportReference(String[] strArr, int i, int i2, boolean z, int i3) {
        int length = strArr.length;
        long[] jArr = new long[length];
        long j = (((long) i) << 32) + ((long) i2);
        char[][] cArr = new char[length][];
        for (int i4 = 0; i4 < length; i4++) {
            cArr[i4] = strArr[i4].toCharArray();
            jArr[i4] = j;
        }
        return new ImportReference(cArr, jArr, z, i3);
    }

    protected TypeParameter createTypeParameter(char[] cArr, char[][] cArr2, int i, int i2) {
        int length;
        TypeParameter typeParameter = new TypeParameter();
        typeParameter.f617name = cArr;
        typeParameter.sourceStart = i;
        typeParameter.sourceEnd = i2;
        if (cArr2 != null && (length = cArr2.length) > 0) {
            typeParameter.type = createTypeReference(cArr2[0], i, i2);
            if (length > 1) {
                typeParameter.bounds = new TypeReference[length - 1];
                for (int i3 = 1; i3 < length; i3++) {
                    TypeReference typeReferenceCreateTypeReference = createTypeReference(cArr2[i3], i, i2);
                    typeReferenceCreateTypeReference.bits |= 16;
                    typeParameter.bounds[i3 - 1] = typeReferenceCreateTypeReference;
                }
            }
        }
        return typeParameter;
    }

    protected TypeReference createTypeReference(char[] cArr, int i, int i2, boolean z) {
        int length = cArr.length;
        this.namePos = 0;
        return decodeType2(cArr, length, i, i2, true);
    }

    protected TypeReference createTypeReference(char[] cArr, int i, int i2) {
        int length = cArr.length;
        this.namePos = 0;
        return decodeType2(cArr, length, i, i2, false);
    }

    protected TypeReference createTypeReference(String str, int i, int i2) {
        int length = str.length();
        this.namePos = 0;
        return decodeType(str, length, i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:110:0x0205 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:111:0x0207  */
    /* JADX WARN: Code duplicated, block: B:172:0x0326  */
    /* JADX WARN: Code duplicated, block: B:174:0x032d  */
    /* JADX WARN: Code duplicated, block: B:176:0x0331  */
    /* JADX WARN: Code duplicated, block: B:217:? A[SYNTHETIC] */
    private TypeReference decodeType(String str, int i, int i2, int i3) {
        int i4;
        int i5 = 0;
        int i6 = -1;
        int i7 = this.namePos;
        ArrayList arrayList = null;
        int i8 = 1;
        boolean z = false;
        int i9 = 0;
        while (true) {
            int i10 = this.namePos;
            if (i10 < i) {
                char cCharAt = str.charAt(i10);
                if (cCharAt != '$') {
                    if (cCharAt != '>') {
                        if (cCharAt == 'F') {
                            if (!z) {
                                this.namePos++;
                                if (i9 == 0) {
                                    return new SingleTypeReference(TypeBinding.FLOAT.simpleName, (((long) i2) << 32) + ((long) i3));
                                }
                                return new ArrayTypeReference(TypeBinding.FLOAT.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                            }
                            i4 = i9;
                        } else if (cCharAt == 'L' || cCharAt == 'Q') {
                            if (z) {
                                i4 = i9;
                            } else {
                                i7 = this.namePos + 1;
                                z = true;
                            }
                        } else if (cCharAt != 'V') {
                            if (cCharAt == '*') {
                                this.namePos++;
                                Wildcard wildcard = new Wildcard(0);
                                wildcard.sourceStart = i2;
                                wildcard.sourceEnd = i3;
                                return wildcard;
                            }
                            if (cCharAt == '+') {
                                this.namePos++;
                                Wildcard wildcard2 = new Wildcard(1);
                                wildcard2.bound = decodeType(str, i, i2, i3);
                                wildcard2.sourceStart = i2;
                                wildcard2.sourceEnd = i3;
                                return wildcard2;
                            }
                            if (cCharAt == '-') {
                                this.namePos++;
                                Wildcard wildcard3 = new Wildcard(2);
                                wildcard3.bound = decodeType(str, i, i2, i3);
                                wildcard3.sourceStart = i2;
                                wildcard3.sourceEnd = i3;
                                return wildcard3;
                            }
                            if (cCharAt == '.') {
                                i4 = i9;
                                if (!z) {
                                    i7 = this.namePos + 1;
                                    i9 = i4;
                                    z = true;
                                } else if (this.namePos > i7) {
                                    i8++;
                                }
                            } else if (cCharAt != ';') {
                                if (cCharAt == '<') {
                                    int i11 = this.namePos;
                                    int i12 = i11 - 1;
                                    if (this.has1_5Compliance) {
                                        if (arrayList == null) {
                                            arrayList = new ArrayList(2);
                                        }
                                        addIdentifiers(str, i7, i11, i8, arrayList);
                                        this.namePos++;
                                        arrayList.add(decodeTypeArguments(str, i, i2, i3));
                                        i8 = 1;
                                        z = false;
                                        i6 = i12;
                                    } else {
                                        i6 = i12;
                                    }
                                } else if (cCharAt == 'I') {
                                    if (!z) {
                                        this.namePos++;
                                        if (i9 == 0) {
                                            return new SingleTypeReference(TypeBinding.INT.simpleName, (((long) i2) << 32) + ((long) i3));
                                        }
                                        return new ArrayTypeReference(TypeBinding.INT.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                    }
                                    i4 = i9;
                                } else if (cCharAt == 'J') {
                                    if (!z) {
                                        this.namePos++;
                                        if (i9 == 0) {
                                            return new SingleTypeReference(TypeBinding.LONG.simpleName, (((long) i2) << 32) + ((long) i3));
                                        }
                                        return new ArrayTypeReference(TypeBinding.LONG.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                    }
                                    i4 = i9;
                                } else if (cCharAt == 'S') {
                                    if (!z) {
                                        this.namePos++;
                                        if (i9 == 0) {
                                            return new SingleTypeReference(TypeBinding.SHORT.simpleName, (((long) i2) << 32) + ((long) i3));
                                        }
                                        return new ArrayTypeReference(TypeBinding.SHORT.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                    }
                                    i4 = i9;
                                } else if (cCharAt != 'T') {
                                    if (cCharAt == 'Z') {
                                        if (!z) {
                                            this.namePos++;
                                            if (i9 == 0) {
                                                return new SingleTypeReference(TypeBinding.BOOLEAN.simpleName, (((long) i2) << 32) + ((long) i3));
                                            }
                                            return new ArrayTypeReference(TypeBinding.BOOLEAN.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                        }
                                        i4 = i9;
                                    } else if (cCharAt != '[') {
                                        switch (cCharAt) {
                                            case 'B':
                                                if (!z) {
                                                    this.namePos++;
                                                    if (i9 == 0) {
                                                        return new SingleTypeReference(TypeBinding.BYTE.simpleName, (((long) i2) << 32) + ((long) i3));
                                                    }
                                                    return new ArrayTypeReference(TypeBinding.BYTE.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                                }
                                                i4 = i9;
                                                break;
                                                break;
                                            case 'C':
                                                if (!z) {
                                                    this.namePos++;
                                                    if (i9 == 0) {
                                                        return new SingleTypeReference(TypeBinding.CHAR.simpleName, (((long) i2) << 32) + ((long) i3));
                                                    }
                                                    return new ArrayTypeReference(TypeBinding.CHAR.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                                }
                                                i4 = i9;
                                                break;
                                                break;
                                            case 'D':
                                                if (!z) {
                                                    this.namePos++;
                                                    if (i9 == 0) {
                                                        return new SingleTypeReference(TypeBinding.DOUBLE.simpleName, (((long) i2) << 32) + ((long) i3));
                                                    }
                                                    return new ArrayTypeReference(TypeBinding.DOUBLE.simpleName, i9, (((long) i2) << 32) + ((long) i3));
                                                }
                                                i4 = i9;
                                                break;
                                                break;
                                            default:
                                                i4 = i9;
                                                break;
                                        }
                                    } else {
                                        i9++;
                                    }
                                } else if (z) {
                                    i7 = this.namePos + 1;
                                    z = true;
                                } else {
                                    i4 = i9;
                                }
                            }
                        } else {
                            if (!z) {
                                this.namePos++;
                                return new SingleTypeReference(TypeBinding.VOID.simpleName, (((long) i2) << 32) + ((long) i3));
                            }
                            i4 = i9;
                        }
                        i9 = i4;
                    }
                    int i13 = this.namePos;
                    this.namePos = i13 + 1;
                    i6 = i13 - 1;
                } else {
                    i4 = i9;
                    if (this.memberTypeSeparator == '$') {
                        if (!z) {
                            i7 = this.namePos + 1;
                            i9 = i4;
                            z = true;
                        } else if (this.namePos > i7) {
                            i8++;
                        }
                    }
                    i9 = i4;
                }
                this.namePos++;
            }
        }
        if (arrayList == null) {
            if (i8 == 1) {
                if (i9 == 0) {
                    char[] cArr = new char[(i6 - i7) + 1];
                    str.getChars(i7, i6 + 1, cArr, 0);
                    return new SingleTypeReference(cArr, (((long) i2) << 32) + ((long) i3));
                }
                char[] cArr2 = new char[(i6 - i7) + 1];
                str.getChars(i7, i6 + 1, cArr2, 0);
                return new ArrayTypeReference(cArr2, i9, (((long) i2) << 32) + ((long) i3));
            }
            long[] jArr = new long[i8];
            long j = (((long) i2) << 32) + ((long) i3);
            while (i5 < i8) {
                jArr[i5] = j;
                i5++;
            }
            char[][] cArrExtractIdentifiers = extractIdentifiers(str, i7, i6, i8);
            if (i9 == 0) {
                return new QualifiedTypeReference(cArrExtractIdentifiers, jArr);
            }
            return new ArrayQualifiedTypeReference(cArrExtractIdentifiers, i9, jArr);
        }
        if (z) {
            addIdentifiers(str, i7, i6 + 1, i8, arrayList);
        }
        int size = arrayList.size();
        if (size == 2) {
            Object obj = arrayList.get(0);
            if (obj instanceof char[]) {
                return new ParameterizedSingleTypeReference((char[]) obj, (TypeReference[]) arrayList.get(1), i9, (((long) i2) << 32) + ((long) i3));
            }
        }
        int i14 = i9;
        int length = 0;
        for (int i15 = 0; i15 < size; i15++) {
            Object obj2 = arrayList.get(i15);
            if (obj2 instanceof char[][]) {
                length += ((char[][]) obj2).length;
            } else if (obj2 instanceof char[]) {
                length++;
            }
        }
        char[][] cArr3 = new char[length][];
        TypeReference[][] typeReferenceArr = new TypeReference[length][];
        int i16 = 0;
        for (int i17 = 0; i17 < size; i17++) {
            Object obj3 = arrayList.get(i17);
            if (obj3 instanceof char[][]) {
                char[][] cArr4 = (char[][]) obj3;
                int length2 = cArr4.length;
                System.arraycopy(cArr4, 0, cArr3, i16, length2);
                i16 += length2;
            } else if (obj3 instanceof char[]) {
                cArr3[i16] = (char[]) obj3;
                i16++;
            } else {
                typeReferenceArr[i16 - 1] = (TypeReference[]) obj3;
            }
        }
        long[] jArr2 = new long[length];
        long j2 = (((long) i2) << 32) + ((long) i3);
        while (i5 < length) {
            jArr2[i5] = j2;
            i5++;
        }
        return new ParameterizedQualifiedTypeReference(cArr3, typeReferenceArr, i14, jArr2);
    }

    private TypeReference decodeType2(char[] cArr, int i, int i2, int i3, boolean z) {
        char c;
        char c2;
        int i4 = 1;
        int i5 = this.namePos;
        int i6 = 0;
        ArrayList arrayList = null;
        int i7 = -1;
        int i8 = 1;
        while (true) {
            int i9 = this.namePos;
            if (i9 >= i || (c = cArr[i9]) == ',') {
                break;
            }
            if (c == '.') {
                if (i5 < 0) {
                    i5 = i9 + 1;
                }
                i8++;
            } else if (c == '<') {
                if ((this.has1_5Compliance || z) && arrayList == null) {
                    arrayList = new ArrayList(2);
                }
                int i10 = this.namePos;
                i7 = i10 - 1;
                if (this.has1_5Compliance || z) {
                    arrayList.add(CharOperation.splitOn('.', cArr, i5, i10));
                }
                this.namePos++;
                TypeReference[] typeReferenceArrDecodeTypeArguments = decodeTypeArguments(cArr, i, i2, i3, z);
                if (this.has1_5Compliance || z) {
                    arrayList.add(typeReferenceArrDecodeTypeArguments);
                    i8 = 0;
                    i5 = -1;
                    i7 = -1;
                }
            } else if (c == '[') {
                if (i6 == 0 && i7 < 0) {
                    i7 = i9 - 1;
                }
                i6++;
            } else {
                if (c == '>') {
                    break;
                }
                if (c == '?') {
                    this.namePos = i9 + 1;
                    while (true) {
                        int i11 = this.namePos;
                        c2 = cArr[i11];
                        if (c2 != ' ') {
                            break;
                        }
                        this.namePos = i11 + 1;
                    }
                    if (c2 == 'e') {
                        int length = TypeConstants.WILDCARD_EXTENDS.length - 1;
                        int i12 = 1;
                        while (i12 < length) {
                            char c3 = cArr[this.namePos + i12];
                            i12++;
                            if (c3 != TypeConstants.WILDCARD_EXTENDS[i12]) {
                            }
                        }
                        this.namePos += length;
                        Wildcard wildcard = new Wildcard(1);
                        wildcard.bound = decodeType2(cArr, i, i2, i3, z);
                        wildcard.sourceStart = i2;
                        wildcard.sourceEnd = i3;
                        return wildcard;
                    }
                    if (c2 == 's') {
                        int length2 = TypeConstants.WILDCARD_SUPER.length - 1;
                        while (i4 < length2) {
                            char c4 = cArr[this.namePos + i4];
                            i4++;
                            if (c4 != TypeConstants.WILDCARD_SUPER[i4]) {
                            }
                        }
                        this.namePos += length2;
                        Wildcard wildcard2 = new Wildcard(2);
                        wildcard2.bound = decodeType2(cArr, i, i2, i3, z);
                        wildcard2.sourceStart = i2;
                        wildcard2.sourceEnd = i3;
                        return wildcard2;
                    }
                    Wildcard wildcard3 = new Wildcard(0);
                    wildcard3.sourceStart = i2;
                    wildcard3.sourceEnd = i3;
                    return wildcard3;
                }
            }
            this.namePos++;
        }
        return decodeType3(cArr, i, i2, i3, i8, i6, i5, i7, arrayList);
    }

    private TypeReference decodeType3(char[] cArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, ArrayList arrayList) {
        char[] cArr2 = cArr;
        int i8 = i7 < 0 ? this.namePos - 1 : i7;
        int i9 = 0;
        if (arrayList == null) {
            if (i4 != 1) {
                long[] jArr = new long[i4];
                long j = (((long) i2) << 32) + ((long) i3);
                while (i9 < i4) {
                    jArr[i9] = j;
                    i9++;
                }
                char[][] cArrSplitOn = CharOperation.splitOn('.', cArr2, i6, i8 + 1);
                if (i5 == 0) {
                    return new QualifiedTypeReference(cArrSplitOn, jArr);
                }
                return new ArrayQualifiedTypeReference(cArrSplitOn, i5, jArr);
            }
            if (i5 == 0) {
                if (i6 != 0 || i8 >= 0) {
                    int i10 = (i8 - i6) + 1;
                    char[] cArr3 = new char[i10];
                    System.arraycopy(cArr2, i6, cArr3, 0, i10);
                    cArr2 = cArr3;
                }
                return new SingleTypeReference(cArr2, (((long) i2) << 32) + ((long) i3));
            }
            int i11 = (i8 - i6) + 1;
            char[] cArr4 = new char[i11];
            System.arraycopy(cArr2, i6, cArr4, 0, i11);
            return new ArrayTypeReference(cArr4, i5, (((long) i2) << 32) + ((long) i3));
        }
        if (i6 > 0 && i6 < i) {
            arrayList.add(CharOperation.splitOn('.', cArr2, i6, i8 + 1));
        }
        int size = arrayList.size();
        if (size == 2) {
            char[][] cArr5 = (char[][]) arrayList.get(0);
            if (cArr5.length == 1) {
                return new ParameterizedSingleTypeReference(cArr5[0], (TypeReference[]) arrayList.get(1), i5, ((long) i3) + (((long) i2) << 32));
            }
        }
        int length = 0;
        for (int i12 = 0; i12 < size; i12++) {
            Object obj = arrayList.get(i12);
            if (obj instanceof char[][]) {
                length += ((char[][]) obj).length;
            }
        }
        char[][] cArr6 = new char[length][];
        TypeReference[][] typeReferenceArr = new TypeReference[length][];
        int i13 = 0;
        for (int i14 = 0; i14 < size; i14++) {
            Object obj2 = arrayList.get(i14);
            if (obj2 instanceof char[][]) {
                char[][] cArr7 = (char[][]) obj2;
                int length2 = cArr7.length;
                System.arraycopy(cArr7, 0, cArr6, i13, length2);
                i13 += length2;
            } else {
                typeReferenceArr[i13 - 1] = (TypeReference[]) obj2;
            }
        }
        long[] jArr2 = new long[length];
        long j2 = (((long) i2) << 32) + ((long) i3);
        while (i9 < length) {
            jArr2[i9] = j2;
            i9++;
        }
        return new ParameterizedQualifiedTypeReference(cArr6, typeReferenceArr, i5, jArr2);
    }

    private TypeReference[] decodeTypeArguments(char[] cArr, int i, int i2, int i3, boolean z) {
        ArrayList arrayList = new ArrayList(1);
        int i4 = 0;
        while (this.namePos < i) {
            i4++;
            arrayList.add(decodeType2(cArr, i, i2, i3, z));
            int i5 = this.namePos;
            if (i5 >= i || cArr[i5] == '>') {
                break;
            }
            this.namePos = i5 + 1;
        }
        TypeReference[] typeReferenceArr = new TypeReference[i4];
        arrayList.toArray(typeReferenceArr);
        return typeReferenceArr;
    }

    private TypeReference[] decodeTypeArguments(String str, int i, int i2, int i3) {
        ArrayList arrayList = new ArrayList(1);
        int i4 = 0;
        while (this.namePos < i) {
            i4++;
            arrayList.add(decodeType(str, i, i2, i3));
            int i5 = this.namePos;
            if (i5 >= i || str.charAt(i5) == '>') {
                break;
            }
        }
        TypeReference[] typeReferenceArr = new TypeReference[i4];
        arrayList.toArray(typeReferenceArr);
        return typeReferenceArr;
    }

    private char[][] extractIdentifiers(String str, int i, int i2, int i3) {
        char[][] cArr = new char[i3][];
        int i4 = i;
        int i5 = 0;
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt == this.memberTypeSeparator || cCharAt == '.') {
                char[] cArr2 = new char[i - i4];
                cArr[i5] = cArr2;
                str.getChars(i4, i, cArr2, 0);
                i4 = i + 1;
                i = i4;
                i5++;
            } else {
                i++;
            }
        }
        int i6 = i + 1;
        char[] cArr3 = new char[(i - i4) + 1];
        cArr[i5] = cArr3;
        str.getChars(i4, i6, cArr3, 0);
        return cArr;
    }
}
