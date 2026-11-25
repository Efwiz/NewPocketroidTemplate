package org.eclipse.jdt.internal.compiler.util;

import io.netty.util.internal.StringUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.batch.FileSystem;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;

/* JADX INFO: loaded from: classes14.dex */
public class Util implements SuffixConstants {
    public static final char C_ARRAY = '[';
    public static final char C_BOOLEAN = 'Z';
    public static final char C_BYTE = 'B';
    public static final char C_CAPTURE = '!';
    public static final char C_CHAR = 'C';
    public static final char C_COLON = ':';
    public static final char C_DOLLAR = '$';
    public static final char C_DOT = '.';
    public static final char C_DOUBLE = 'D';
    public static final char C_EXCEPTION_START = '^';
    public static final char C_EXTENDS = '+';
    public static final char C_FLOAT = 'F';
    public static final char C_GENERIC_END = '>';
    public static final char C_GENERIC_START = '<';
    public static final char C_INT = 'I';
    public static final char C_LONG = 'J';
    public static final char C_NAME_END = ';';
    public static final char C_PARAM_END = ')';
    public static final char C_PARAM_START = '(';
    public static final char C_RESOLVED = 'L';
    public static final char C_SEMICOLON = ';';
    public static final char C_SHORT = 'S';
    public static final char C_STAR = '*';
    public static final char C_SUPER = '-';
    public static final char C_TYPE_VARIABLE = 'T';
    public static final char C_UNRESOLVED = 'Q';
    public static final char C_VOID = 'V';
    private static final int DEFAULT_READING_SIZE = 8192;
    private static final int DEFAULT_WRITING_SIZE = 1024;
    public static final String UTF_8 = "UTF-8";
    public static final String LINE_SEPARATOR = System.getProperty(SystemProperties.LINE_SEPARATOR);
    public static final String EMPTY_STRING = new String(CharOperation.NO_CHAR);
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    public interface Displayable {
        String displayString(Object obj);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:54:0x0153 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:56:0x017f A[SYNTHETIC] */
    public static String buildAllDirectoriesInto(String str, String str2) throws IOException {
        String strSubstring;
        String str3;
        char c = File.separatorChar;
        String str4 = File.separator;
        String strReplace = str.replace('/', c);
        String strReplace2 = str2.replace('/', c);
        int iLastIndexOf = strReplace2.lastIndexOf(c);
        if (iLastIndexOf == -1) {
            if (strReplace.endsWith(str4)) {
                strSubstring = strReplace.substring(0, strReplace.length() - 1);
                str3 = String.valueOf(strReplace) + strReplace2;
            } else {
                str3 = String.valueOf(strReplace) + str4 + strReplace2;
                strSubstring = strReplace;
            }
        } else if (strReplace.endsWith(str4)) {
            strSubstring = String.valueOf(strReplace) + strReplace2.substring(0, iLastIndexOf);
            str3 = String.valueOf(strReplace) + strReplace2;
        } else {
            strSubstring = String.valueOf(strReplace) + str4 + strReplace2.substring(0, iLastIndexOf);
            str3 = String.valueOf(strReplace) + str4 + strReplace2;
        }
        File file = new File(strSubstring);
        file.mkdirs();
        if (file.isDirectory()) {
            return str3;
        }
        if (strReplace.endsWith(str4)) {
            strReplace = strReplace.substring(0, strReplace.length() - 1);
        }
        File file2 = new File(strReplace);
        if (!file2.exists()) {
            if (!file2.mkdirs()) {
                if (!file2.exists()) {
                    throw new IOException(Messages.bind(Messages.output_notValidAll, file2.getAbsolutePath()));
                }
                if (!file2.isDirectory()) {
                    throw new IOException(Messages.bind(Messages.output_isFile, file2.getAbsolutePath()));
                }
            }
        } else if (!file2.isDirectory()) {
            throw new IOException(Messages.bind(Messages.output_isFile, file2.getAbsolutePath()));
        }
        StringBuffer stringBuffer = new StringBuffer(strReplace);
        stringBuffer.append(str4);
        StringTokenizer stringTokenizer = new StringTokenizer(strReplace2, str4);
        String strNextToken = stringTokenizer.nextToken();
        while (stringTokenizer.hasMoreTokens()) {
            File file3 = new File(stringBuffer.append(strNextToken).append(str4).toString());
            if (!file3.exists()) {
                if (file3.mkdir()) {
                    continue;
                } else {
                    if (!file3.exists()) {
                        throw new IOException(Messages.bind(Messages.output_notValid, stringBuffer.substring(strReplace.length() + 1, stringBuffer.length() - 1), strReplace));
                    }
                    if (file3.isDirectory()) {
                        throw new IOException(Messages.bind(Messages.output_isFile, file3.getAbsolutePath()));
                    }
                }
            } else if (file3.isDirectory()) {
                throw new IOException(Messages.bind(Messages.output_isFile, file3.getAbsolutePath()));
            }
            strNextToken = stringTokenizer.nextToken();
        }
        return stringBuffer.append(strNextToken).toString();
    }

    public static char[] bytesToChar(byte[] bArr, String str) throws IOException {
        return getInputStreamAsCharArray(new ByteArrayInputStream(bArr), bArr.length, str);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0013 A[PHI: r1
      0x0013: PHI (r1v1 int) = (r1v0 int), (r1v2 int) binds: [B:6:0x000a, B:11:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
    public static int computeOuterMostVisibility(TypeDeclaration typeDeclaration, int i) {
        while (typeDeclaration != null) {
            int i2 = typeDeclaration.modifiers & 7;
            int i3 = 2;
            if (i2 != 0) {
                if (i2 != 2) {
                    i3 = 4;
                    if (i2 == 4 && i == 1) {
                        i = i3;
                    }
                } else {
                    i = i3;
                }
            } else if (i != 2) {
                i = 0;
            }
            typeDeclaration = typeDeclaration.enclosingType;
        }
        return i;
    }

    public static byte[] getFileByteContent(File file) throws Throwable {
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            try {
                byte[] inputStreamAsByteArray = getInputStreamAsByteArray(bufferedInputStream2, (int) file.length());
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused) {
                }
                return inputStreamAsByteArray;
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static char[] getFileCharContent(File file, String str) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                char[] inputStreamAsCharArray = getInputStreamAsCharArray(fileInputStream2, (int) file.length(), str);
                try {
                    fileInputStream2.close();
                } catch (IOException unused) {
                }
                return inputStreamAsCharArray;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static FileOutputStream getFileOutputStream(boolean z, String str, String str2) throws IOException {
        String str3;
        if (z) {
            return new FileOutputStream(new File(buildAllDirectoriesInto(str, str2)));
        }
        char c = File.separatorChar;
        String str4 = File.separator;
        String strReplace = str.replace('/', c);
        int iLastIndexOf = str2.lastIndexOf(c);
        if (iLastIndexOf == -1) {
            if (strReplace.endsWith(str4)) {
                str3 = String.valueOf(strReplace) + str2;
            } else {
                str3 = String.valueOf(strReplace) + str4 + str2;
            }
        } else {
            int length = str2.length();
            if (strReplace.endsWith(str4)) {
                str3 = String.valueOf(strReplace) + str2.substring(iLastIndexOf + 1, length);
            } else {
                str3 = String.valueOf(strReplace) + str4 + str2.substring(iLastIndexOf + 1, length);
            }
        }
        return new FileOutputStream(new File(str3));
    }

    public static byte[] getInputStreamAsByteArray(InputStream inputStream, int i) throws IOException {
        int i2;
        int i3 = 0;
        if (i == -1) {
            byte[] bArr = new byte[0];
            int i4 = 0;
            do {
                int iMax = Math.max(inputStream.available(), 8192);
                int i5 = i4 + iMax;
                if (i5 > bArr.length) {
                    byte[] bArr2 = new byte[i5];
                    System.arraycopy(bArr, 0, bArr2, 0, i4);
                    bArr = bArr2;
                }
                i2 = inputStream.read(bArr, i4, iMax);
                if (i2 > 0) {
                    i4 += i2;
                }
            } while (i2 != -1);
            if (i4 >= bArr.length) {
                return bArr;
            }
            byte[] bArr3 = new byte[i4];
            System.arraycopy(bArr, 0, bArr3, 0, i4);
            return bArr3;
        }
        byte[] bArr4 = new byte[i];
        int i6 = 0;
        while (i3 != -1 && i6 != i) {
            i6 += i3;
            i3 = inputStream.read(bArr4, i6, i - i6);
        }
        return bArr4;
    }

    public static char[] getInputStreamAsCharArray(InputStream inputStream, int i, String str) throws IOException {
        BufferedReader bufferedReader;
        char[] cArr;
        int i2;
        int i3;
        int i4;
        try {
            if (str == null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, str));
            }
            while (true) {
                if (i2 >= i) {
                    int i5 = bufferedReader.read();
                    if (i5 < 0) {
                        break;
                    }
                    int iMax = Math.max(inputStream.available(), 8192);
                    int i6 = i2 + 1;
                    int i7 = i6 + iMax;
                    if (i7 > cArr.length) {
                        char[] cArr2 = new char[i7];
                        System.arraycopy(cArr, 0, cArr2, 0, i2);
                        cArr = cArr2;
                    }
                    cArr[i2] = (char) i5;
                    i3 = iMax;
                    i2 = i6;
                } else {
                    i3 = i - i2;
                }
                int i8 = bufferedReader.read(cArr, i2, i3);
                if (i8 < 0) {
                    break;
                }
                i2 += i8;
            }
        } catch (UnsupportedEncodingException unused) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }
        if (i == -1) {
            cArr = CharOperation.NO_CHAR;
        } else {
            cArr = new char[i];
        }
        i2 = 0;
        if (i2 > 0 && "UTF-8".equals(str) && cArr[0] == 65279) {
            i2--;
            i4 = 1;
        } else {
            i4 = 0;
        }
        if (i2 >= cArr.length) {
            return cArr;
        }
        char[] cArr3 = new char[i2];
        System.arraycopy(cArr, i4, cArr3, 0, i2);
        return cArr3;
    }

    public static String getExceptionSummary(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        StringBuffer buffer = stringWriter.getBuffer();
        StringBuffer stringBuffer = new StringBuffer(50);
        stringBuffer.append(th.toString());
        int length = buffer.length();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = buffer.charAt(i3);
            if (cCharAt != '\t') {
                if (cCharAt == '\n' || cCharAt == '\r') {
                    if (i > 0) {
                        stringBuffer.append(StringUtil.SPACE).append(buffer.substring(i, i3));
                        break;
                    }
                    i2++;
                } else if (cCharAt != ' ' && i2 > 0) {
                    i2 = 0;
                    i = i3;
                }
            }
        }
        return stringBuffer.toString();
    }

    public static int getLineNumber(int i, int[] iArr, int i2, int i3) {
        if (iArr == null || i3 == -1) {
            return 1;
        }
        int i4 = i3;
        int i5 = i2;
        while (i2 <= i4) {
            i5 = ((i4 - i2) / 2) + i2;
            int i6 = iArr[i5];
            if (i < i6) {
                i4 = i5 - 1;
            } else {
                if (i <= i6) {
                    return i5 + 1;
                }
                i2 = i5 + 1;
            }
        }
        return i < iArr[i5] ? i5 + 1 : i5 + 2;
    }

    public static byte[] getZipEntryByteContent(ZipEntry zipEntry, ZipFile zipFile) throws Throwable {
        BufferedInputStream bufferedInputStream = null;
        try {
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            if (inputStream == null) {
                throw new IOException("Invalid zip entry name : " + zipEntry.getName());
            }
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream);
            try {
                byte[] inputStreamAsByteArray = getInputStreamAsByteArray(bufferedInputStream2, (int) zipEntry.getSize());
                try {
                    bufferedInputStream2.close();
                } catch (IOException unused) {
                }
                return inputStreamAsByteArray;
            } catch (Throwable th) {
                th = th;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static int hashCode(Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        int iHashCode = 1;
        for (int i = 0; i < objArr.length; i++) {
            int i2 = 31 * iHashCode;
            Object obj = objArr[i];
            iHashCode = (obj == null ? 0 : obj.hashCode()) + i2;
        }
        return iHashCode;
    }

    public static final boolean isPotentialZipArchive(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf == -1 || str.lastIndexOf(File.separatorChar) > iLastIndexOf) {
            return false;
        }
        int length = str.length();
        int i = length - iLastIndexOf;
        int i2 = i - 1;
        if (i2 == SuffixConstants.EXTENSION_java.length()) {
            for (int i3 = i - 2; i3 >= 0 && Character.toLowerCase(str.charAt((length - i2) + i3)) == SuffixConstants.EXTENSION_java.charAt(i3); i3--) {
                if (i3 == 0) {
                    return false;
                }
            }
        }
        if (i2 != "class".length()) {
            return true;
        }
        for (int i4 = i - 2; i4 >= 0; i4--) {
            if (Character.toLowerCase(str.charAt((length - i2) + i4)) != "class".charAt(i4)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isClassFileName(char[] cArr) {
        int length = cArr == null ? 0 : cArr.length;
        int length2 = SUFFIX_CLASS.length;
        if (length < length2) {
            return false;
        }
        int i = length - length2;
        for (int i2 = 0; i2 < length2; i2++) {
            char c = cArr[i + i2];
            if (c != SUFFIX_class[i2] && c != SUFFIX_CLASS[i2]) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isClassFileName(String str) {
        int length = str == null ? 0 : str.length();
        int length2 = SUFFIX_CLASS.length;
        if (length < length2) {
            return false;
        }
        for (int i = 0; i < length2; i++) {
            char cCharAt = str.charAt((length - i) - 1);
            int i2 = (length2 - i) - 1;
            if (cCharAt != SUFFIX_class[i2] && cCharAt != SUFFIX_CLASS[i2]) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isExcluded(char[] cArr, char[][] cArr2, char[][] cArr3, boolean z) {
        int iLastIndexOf;
        int iIndexOf;
        if (cArr2 == null && cArr3 == null) {
            return false;
        }
        if (cArr2 != null) {
            int length = cArr2.length;
            for (int i = 0; i < length; i++) {
                char[] cArrSubarray = cArr2[i];
                if (z && (iLastIndexOf = CharOperation.lastIndexOf('/', cArrSubarray)) != -1 && iLastIndexOf != cArrSubarray.length - 1 && ((iIndexOf = CharOperation.indexOf('*', cArrSubarray, iLastIndexOf)) == -1 || iIndexOf >= cArrSubarray.length - 1 || cArrSubarray[iIndexOf + 1] != '*')) {
                    cArrSubarray = CharOperation.subarray(cArrSubarray, 0, iLastIndexOf);
                }
                if (!CharOperation.pathMatch(cArrSubarray, cArr, true, '/')) {
                }
            }
            return true;
        }
        if (z) {
            cArr = CharOperation.concat(cArr, new char[]{'*'}, '/');
        }
        if (cArr3 != null) {
            for (char[] cArr4 : cArr3) {
                if (CharOperation.pathMatch(cArr4, cArr, true, '/')) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isJavaFileName(char[] cArr) {
        int length = cArr == null ? 0 : cArr.length;
        int length2 = SUFFIX_JAVA.length;
        if (length < length2) {
            return false;
        }
        int i = length - length2;
        for (int i2 = 0; i2 < length2; i2++) {
            char c = cArr[i + i2];
            if (c != SUFFIX_java[i2] && c != SUFFIX_JAVA[i2]) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isJavaFileName(String str) {
        int length = str == null ? 0 : str.length();
        int length2 = SUFFIX_JAVA.length;
        if (length < length2) {
            return false;
        }
        for (int i = 0; i < length2; i++) {
            char cCharAt = str.charAt((length - i) - 1);
            int i2 = (length2 - i) - 1;
            if (cCharAt != SUFFIX_java[i2] && cCharAt != SUFFIX_JAVA[i2]) {
                return false;
            }
        }
        return true;
    }

    public static void reverseQuickSort(char[][] cArr, int i, int i2) {
        char[] cArr2 = cArr[((i2 - i) / 2) + i];
        int i3 = i;
        int i4 = i2;
        while (true) {
            if (CharOperation.compareTo(cArr[i3], cArr2) <= 0) {
                while (CharOperation.compareTo(cArr2, cArr[i4]) > 0) {
                    i4--;
                }
                if (i3 <= i4) {
                    char[] cArr3 = cArr[i3];
                    cArr[i3] = cArr[i4];
                    cArr[i4] = cArr3;
                    i3++;
                    i4--;
                }
                if (i3 > i4) {
                    break;
                }
            } else {
                i3++;
            }
        }
        if (i < i4) {
            reverseQuickSort(cArr, i, i4);
        }
        if (i3 < i2) {
            reverseQuickSort(cArr, i3, i2);
        }
    }

    public static void reverseQuickSort(char[][] cArr, int i, int i2, int[] iArr) {
        char[] cArr2 = cArr[((i2 - i) / 2) + i];
        int i3 = i;
        int i4 = i2;
        while (true) {
            if (CharOperation.compareTo(cArr[i3], cArr2) <= 0) {
                while (CharOperation.compareTo(cArr2, cArr[i4]) > 0) {
                    i4--;
                }
                if (i3 <= i4) {
                    char[] cArr3 = cArr[i3];
                    cArr[i3] = cArr[i4];
                    cArr[i4] = cArr3;
                    int i5 = iArr[i3];
                    iArr[i3] = iArr[i4];
                    iArr[i4] = i5;
                    i3++;
                    i4--;
                }
                if (i3 > i4) {
                    break;
                }
            } else {
                i3++;
            }
        }
        if (i < i4) {
            reverseQuickSort(cArr, i, i4, iArr);
        }
        if (i3 < i2) {
            reverseQuickSort(cArr, i3, i2, iArr);
        }
    }

    public static final int searchColumnNumber(int[] iArr, int i, int i2) {
        int i3;
        if (i == 1) {
            return i2 + 1;
        }
        if (i == 2) {
            i3 = iArr[0];
        } else {
            int i4 = i - 2;
            int length = iArr.length;
            if (i4 >= length) {
                i3 = iArr[length - 1];
            } else {
                i3 = iArr[i4];
            }
        }
        return i2 - i3;
    }

    public static Boolean toBoolean(boolean z) {
        if (z) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static String toString(Object[] objArr) {
        return toString(objArr, new Displayable() { // from class: org.eclipse.jdt.internal.compiler.util.Util.1
            @Override // org.eclipse.jdt.internal.compiler.util.Util.Displayable
            public String displayString(Object obj) {
                if (obj == null) {
                    return "null";
                }
                return obj.toString();
            }
        });
    }

    public static String toString(Object[] objArr, Displayable displayable) {
        if (objArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(displayable.displayString(objArr[i]));
        }
        return stringBuffer.toString();
    }

    public static void writeToDisk(boolean z, String str, String str2, ClassFile classFile) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(getFileOutputStream(z, str, str2), 1024);
        try {
            try {
                bufferedOutputStream.write(classFile.header, 0, classFile.headerOffset);
                bufferedOutputStream.write(classFile.contents, 0, classFile.contentsOffset);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (IOException e) {
                throw e;
            }
        } catch (Throwable th) {
            bufferedOutputStream.close();
            throw th;
        }
    }

    public static void recordNestedType(ClassFile classFile, TypeBinding typeBinding) {
        if (classFile.visitedTypes == null) {
            classFile.visitedTypes = new HashSet(3);
        } else if (classFile.visitedTypes.contains(typeBinding)) {
            return;
        }
        classFile.visitedTypes.add(typeBinding);
        if (typeBinding.isParameterizedType() && (typeBinding.tagBits & 2048) != 0) {
            ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding;
            ReferenceBinding referenceBindingGenericType = parameterizedTypeBinding.genericType();
            if ((referenceBindingGenericType.tagBits & 2048) != 0) {
                recordNestedType(classFile, referenceBindingGenericType);
            }
            TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
            if (typeBindingArr != null) {
                for (TypeBinding typeBinding2 : typeBindingArr) {
                    if (typeBinding2.isWildcard()) {
                        WildcardBinding wildcardBinding = (WildcardBinding) typeBinding2;
                        TypeBinding typeBinding3 = wildcardBinding.bound;
                        if (typeBinding3 != null && (typeBinding3.tagBits & 2048) != 0) {
                            recordNestedType(classFile, typeBinding3);
                        }
                        ReferenceBinding referenceBindingSuperclass = wildcardBinding.superclass();
                        if (referenceBindingSuperclass != null && (referenceBindingSuperclass.tagBits & 2048) != 0) {
                            recordNestedType(classFile, referenceBindingSuperclass);
                        }
                        ReferenceBinding[] referenceBindingArrSuperInterfaces = wildcardBinding.superInterfaces();
                        if (referenceBindingArrSuperInterfaces != null) {
                            for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
                                if ((referenceBinding.tagBits & 2048) != 0) {
                                    recordNestedType(classFile, referenceBinding);
                                }
                            }
                        }
                    } else if ((typeBinding2.tagBits & 2048) != 0) {
                        recordNestedType(classFile, typeBinding2);
                    }
                }
                return;
            }
            return;
        }
        if (typeBinding.isTypeVariable() && (typeBinding.tagBits & 2048) != 0) {
            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) typeBinding;
            TypeBinding typeBindingUpperBound = typeVariableBinding.upperBound();
            if (typeBindingUpperBound != null && (typeBindingUpperBound.tagBits & 2048) != 0) {
                recordNestedType(classFile, typeBindingUpperBound);
            }
            TypeBinding[] typeBindingArrOtherUpperBounds = typeVariableBinding.otherUpperBounds();
            if (typeBindingArrOtherUpperBounds != null) {
                for (TypeBinding typeBinding4 : typeBindingArrOtherUpperBounds) {
                    if ((typeBinding4.tagBits & 2048) != 0) {
                        recordNestedType(classFile, typeBinding4);
                    }
                }
                return;
            }
            return;
        }
        if (typeBinding.isNestedType()) {
            TypeBinding typeBindingEnclosingType = typeBinding;
            while (typeBindingEnclosingType.canBeSeenBy(classFile.referenceBinding.scope) && (typeBindingEnclosingType = typeBindingEnclosingType.enclosingType()) != null) {
            }
            classFile.recordInnerClasses(typeBinding, typeBindingEnclosingType != null);
        }
    }

    public static File getJavaHome() {
        String property = System.getProperty("java.home");
        if (property == null) {
            return null;
        }
        File file = new File(property);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public static void collectRunningVMBootclasspath(List list) {
        Iterator<String> it = collectFilesNames().iterator();
        while (it.hasNext()) {
            FileSystem.Classpath classpath = FileSystem.getClasspath(it.next(), null, null, null);
            if (classpath != null) {
                list.add(classpath);
            }
        }
    }

    public static List<String> collectFilesNames() {
        File[] fileArr;
        String property = System.getProperty("java.version");
        if (property != null && property.equalsIgnoreCase("1.1.8")) {
            throw new IllegalStateException();
        }
        String property2 = System.getProperty("sun.boot.class.path");
        if ((property2 == null || property2.length() == 0) && ((property2 = System.getProperty("vm.boot.class.path")) == null || property2.length() == 0)) {
            property2 = System.getProperty("org.apache.harmony.boot.class.path");
        }
        ArrayList arrayList = new ArrayList();
        if (property2 != null && property2.length() != 0) {
            StringTokenizer stringTokenizer = new StringTokenizer(property2, File.pathSeparator);
            while (stringTokenizer.hasMoreTokens()) {
                arrayList.add(stringTokenizer.nextToken());
            }
        } else {
            File javaHome = getJavaHome();
            if (javaHome != null) {
                if (System.getProperty(SystemProperties.OS_NAME).startsWith("Mac")) {
                    fileArr = new File[]{new File(javaHome, "../Classes")};
                } else {
                    fileArr = new File[]{new File(javaHome, "lib")};
                }
                File[][] librariesFiles = Main.getLibrariesFiles(fileArr);
                if (librariesFiles != null) {
                    for (File[] fileArr2 : librariesFiles) {
                        if (fileArr2 != null) {
                            for (File file : fileArr2) {
                                arrayList.add(file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    public static int getParameterCount(char[] cArr) {
        try {
            int iIndexOf = CharOperation.indexOf('(', cArr);
            if (iIndexOf < 0) {
                throw new IllegalArgumentException();
            }
            int i = iIndexOf + 1;
            int i2 = 0;
            while (cArr[i] != ')') {
                int iScanTypeSignature = scanTypeSignature(cArr, i);
                if (iScanTypeSignature < 0) {
                    throw new IllegalArgumentException();
                }
                i = iScanTypeSignature + 1;
                i2++;
            }
            return i2;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new IllegalArgumentException();
        }
    }

    public static int scanTypeSignature(char[] cArr, int i) {
        if (i >= cArr.length) {
            throw new IllegalArgumentException();
        }
        char c = cArr[i];
        if (c == '!') {
            return scanCaptureTypeSignature(cArr, i);
        }
        if (c != '-') {
            if (c != 'F') {
                if (c == 'L' || c == 'Q') {
                    return scanClassTypeSignature(cArr, i);
                }
                if (c != 'V') {
                    if (c != '*' && c != '+') {
                        if (c != 'I' && c != 'J' && c != 'S') {
                            if (c == 'T') {
                                return scanTypeVariableSignature(cArr, i);
                            }
                            if (c != 'Z') {
                                if (c == '[') {
                                    return scanArrayTypeSignature(cArr, i);
                                }
                                switch (c) {
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                        break;
                                    default:
                                        throw new IllegalArgumentException();
                                }
                            }
                        }
                    }
                }
            }
            return scanBaseTypeSignature(cArr, i);
        }
        return scanTypeBoundSignature(cArr, i);
    }

    public static int scanBaseTypeSignature(char[] cArr, int i) {
        if (i >= cArr.length) {
            throw new IllegalArgumentException();
        }
        if ("BCDFIJSVZ".indexOf(cArr[i]) >= 0) {
            return i;
        }
        throw new IllegalArgumentException();
    }

    public static int scanArrayTypeSignature(char[] cArr, int i) {
        int length = cArr.length - 1;
        if (i >= length) {
            throw new IllegalArgumentException();
        }
        if (cArr[i] != '[') {
            throw new IllegalArgumentException();
        }
        int i2 = i + 1;
        char c = cArr[i2];
        while (c == '[') {
            if (i2 >= length) {
                throw new IllegalArgumentException();
            }
            i2++;
            c = cArr[i2];
        }
        return scanTypeSignature(cArr, i2);
    }

    public static int scanCaptureTypeSignature(char[] cArr, int i) {
        if (i >= cArr.length - 1) {
            throw new IllegalArgumentException();
        }
        if (cArr[i] != '!') {
            throw new IllegalArgumentException();
        }
        return scanTypeBoundSignature(cArr, i + 1);
    }

    public static int scanTypeVariableSignature(char[] cArr, int i) {
        if (i >= cArr.length - 2) {
            throw new IllegalArgumentException();
        }
        if (cArr[i] != 'T') {
            throw new IllegalArgumentException();
        }
        int iScanIdentifier = scanIdentifier(cArr, i + 1) + 1;
        if (cArr[iScanIdentifier] == ';') {
            return iScanIdentifier;
        }
        throw new IllegalArgumentException();
    }

    public static int scanIdentifier(char[] cArr, int i) {
        if (i >= cArr.length) {
            throw new IllegalArgumentException();
        }
        while (true) {
            char c = cArr[i];
            if (c == '<' || c == '>' || c == ':' || c == ';' || c == '.' || c == '/') {
                break;
            }
            int i2 = i + 1;
            if (i2 == cArr.length) {
                return i;
            }
            i = i2;
        }
        return i - 1;
    }

    public static int scanClassTypeSignature(char[] cArr, int i) {
        if (i >= cArr.length - 2) {
            throw new IllegalArgumentException();
        }
        char c = cArr[i];
        if (c != 'L' && c != 'Q') {
            return -1;
        }
        while (true) {
            i++;
            if (i >= cArr.length) {
                throw new IllegalArgumentException();
            }
            char c2 = cArr[i];
            if (c2 == ';') {
                return i;
            }
            if (c2 == '<') {
                i = scanTypeArgumentSignatures(cArr, i);
            } else if (c2 == '.' || c2 == '/') {
                i = scanIdentifier(cArr, i + 1);
            }
        }
    }

    public static int scanTypeBoundSignature(char[] cArr, int i) {
        if (i >= cArr.length) {
            throw new IllegalArgumentException();
        }
        char c = cArr[i];
        if (c == '*') {
            return i;
        }
        if (c == '+' || c == '-') {
            if (i >= cArr.length - 2) {
                throw new IllegalArgumentException();
            }
            int i2 = i + 1;
            char c2 = cArr[i2];
            if (c2 == '!') {
                return scanCaptureTypeSignature(cArr, i2);
            }
            if (c2 != '-') {
                if (c2 == 'L' || c2 == 'Q') {
                    return scanClassTypeSignature(cArr, i2);
                }
                if (c2 == 'T') {
                    return scanTypeVariableSignature(cArr, i2);
                }
                if (c2 == '[') {
                    return scanArrayTypeSignature(cArr, i2);
                }
                if (c2 == '*') {
                    return i2;
                }
                if (c2 != '+') {
                    throw new IllegalArgumentException();
                }
            }
            return scanTypeBoundSignature(cArr, i2);
        }
        throw new IllegalArgumentException();
    }

    public static int scanTypeArgumentSignatures(char[] cArr, int i) {
        if (i >= cArr.length - 1) {
            throw new IllegalArgumentException();
        }
        if (cArr[i] != '<') {
            throw new IllegalArgumentException();
        }
        while (true) {
            int i2 = i + 1;
            if (i2 >= cArr.length) {
                throw new IllegalArgumentException();
            }
            if (cArr[i2] == '>') {
                return i2;
            }
            i = scanTypeArgumentSignature(cArr, i2);
        }
    }

    public static int scanTypeArgumentSignature(char[] cArr, int i) {
        if (i >= cArr.length) {
            throw new IllegalArgumentException();
        }
        char c = cArr[i];
        if (c == '*') {
            return i;
        }
        if (c == '+' || c == '-') {
            return scanTypeBoundSignature(cArr, i);
        }
        return scanTypeSignature(cArr, i);
    }

    public static boolean effectivelyEqual(Object[] objArr, Object[] objArr2) {
        if (objArr == objArr2) {
            return true;
        }
        int length = objArr == null ? 0 : objArr.length;
        if (length != (objArr2 == null ? 0 : objArr2.length)) {
            return false;
        }
        if (length == 0) {
            return true;
        }
        for (int i = 0; i < objArr.length; i++) {
            if (objArr[i] != objArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void appendEscapedChar(StringBuffer stringBuffer, char c, boolean z) {
        if (c == '\f') {
            stringBuffer.append("\\f");
            return;
        }
        if (c == '\r') {
            stringBuffer.append("\\r");
            return;
        }
        if (c == '\"') {
            if (z) {
                stringBuffer.append("\\\"");
                return;
            } else {
                stringBuffer.append(c);
                return;
            }
        }
        if (c == '\'') {
            if (z) {
                stringBuffer.append(c);
                return;
            } else {
                stringBuffer.append("\\'");
                return;
            }
        }
        if (c != '\\') {
            switch (c) {
                case '\b':
                    stringBuffer.append("\\b");
                    break;
                case '\t':
                    stringBuffer.append("\\t");
                    break;
                case '\n':
                    stringBuffer.append("\\n");
                    break;
                default:
                    if (c >= ' ') {
                        stringBuffer.append(c);
                    } else if (c >= 16) {
                        stringBuffer.append("\\u00").append(Integer.toHexString(c));
                    } else if (c >= 0) {
                        stringBuffer.append("\\u000").append(Integer.toHexString(c));
                    } else {
                        stringBuffer.append(c);
                    }
                    break;
            }
            return;
        }
        stringBuffer.append("\\\\");
    }
}
