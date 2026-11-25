package org.eclipse.jdt.internal.compiler.classfmt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.codegen.AttributeNamesConstants;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair;
import org.eclipse.jdt.internal.compiler.env.IBinaryField;
import org.eclipse.jdt.internal.compiler.env.IBinaryMethod;
import org.eclipse.jdt.internal.compiler.env.IBinaryNestedType;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BinaryTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class ClassFileReader extends ClassFileStruct implements IBinaryType {
    private int accessFlags;
    private ExternalAnnotationProvider annotationProvider;
    private AnnotationInfo[] annotations;
    private char[] classFileName;
    private char[] className;
    private int classNameIndex;
    private int constantPoolCount;
    private char[] enclosingMethod;
    private int enclosingNameAndTypeIndex;
    private char[] enclosingTypeName;
    private BinaryTypeBinding.ExternalAnnotationStatus externalAnnotationStatus;
    private FieldInfo[] fields;
    private int fieldsCount;
    private InnerClassInfo innerInfo;
    private int innerInfoIndex;
    private InnerClassInfo[] innerInfos;
    private char[][] interfaceNames;
    private int interfacesCount;
    private MethodInfo[] methods;
    private int methodsCount;
    private char[][][] missingTypeNames;
    private char[] signature;
    private char[] sourceFileName;
    private char[] sourceName;
    private char[] superclassName;
    private long tagBits;
    private TypeAnnotationInfo[] typeAnnotations;
    private long version;

    public interface ZipFileProducer {
        ZipFile produce() throws IOException;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IGenericType
    public boolean isBinaryType() {
        return true;
    }

    private static String printTypeModifiers(int i) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        if ((i & 1) != 0) {
            printWriter.print("public ");
        }
        if ((i & 2) != 0) {
            printWriter.print("private ");
        }
        if ((i & 16) != 0) {
            printWriter.print("final ");
        }
        if ((i & 32) != 0) {
            printWriter.print(ExternalAnnotationProvider.SUPER_PREFIX);
        }
        if ((i & 512) != 0) {
            printWriter.print("interface ");
        }
        if ((i & 1024) != 0) {
            printWriter.print("abstract ");
        }
        printWriter.flush();
        return byteArrayOutputStream.toString();
    }

    public static ClassFileReader read(File file) throws ClassFormatException, IOException {
        return read(file, false);
    }

    public static ClassFileReader read(File file, boolean z) throws ClassFormatException, IOException {
        ClassFileReader classFileReader = new ClassFileReader(Util.getFileByteContent(file), file.getAbsolutePath().toCharArray());
        if (z) {
            classFileReader.initialize();
        }
        return classFileReader;
    }

    public static ClassFileReader read(InputStream inputStream, String str) throws ClassFormatException, IOException {
        return read(inputStream, str, false);
    }

    public static ClassFileReader read(InputStream inputStream, String str, boolean z) throws ClassFormatException, IOException {
        ClassFileReader classFileReader = new ClassFileReader(Util.getInputStreamAsByteArray(inputStream, -1), str.toCharArray());
        if (z) {
            classFileReader.initialize();
        }
        return classFileReader;
    }

    public static ClassFileReader read(ZipFile zipFile, String str) throws ClassFormatException, IOException {
        return read(zipFile, str, false);
    }

    public static ClassFileReader read(ZipFile zipFile, String str, boolean z) throws ClassFormatException, IOException {
        ZipEntry entry = zipFile.getEntry(str);
        if (entry == null) {
            return null;
        }
        ClassFileReader classFileReader = new ClassFileReader(Util.getZipEntryByteContent(entry, zipFile), str.toCharArray());
        if (z) {
            classFileReader.initialize();
        }
        return classFileReader;
    }

    public static ClassFileReader read(String str) throws ClassFormatException, IOException {
        return read(str, false);
    }

    public static ClassFileReader read(String str, boolean z) throws ClassFormatException, IOException {
        return read(new File(str), z);
    }

    public ClassFileReader(byte[] bArr, char[] cArr) throws ClassFormatException {
        this(bArr, cArr, false);
    }

    public ClassFileReader(byte[] bArr, char[] cArr, boolean z) throws ClassFormatException {
        long j;
        long jU4At;
        char[] enclosingTypeName;
        int iU2At;
        MethodInfo methodInfoCreateMethod;
        super(bArr, null, 0);
        this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
        this.classFileName = cArr;
        int iU2At2 = 10;
        try {
            try {
                this.version = (((long) u2At(6)) << 16) + ((long) u2At(4));
                int iU2At3 = u2At(8);
                this.constantPoolCount = iU2At3;
                this.constantPoolOffsets = new int[iU2At3];
                int i = 1;
                while (i < this.constantPoolCount) {
                    switch (u1At(iU2At2)) {
                        case 1:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += u2At(iU2At2 + 1);
                            iU2At2 += 3;
                            break;
                        case 3:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                        case 4:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                        case 5:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 9;
                            i++;
                            break;
                        case 6:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 9;
                            i++;
                            break;
                        case 7:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 3;
                            break;
                        case 8:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 3;
                            break;
                        case 9:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                        case 10:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                        case 11:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                        case 12:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                        case 15:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 4;
                            break;
                        case 16:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 3;
                            break;
                        case 18:
                            this.constantPoolOffsets[i] = iU2At2;
                            iU2At2 += 5;
                            break;
                    }
                    i++;
                }
                this.accessFlags = u2At(iU2At2);
                int i2 = iU2At2 + 2;
                try {
                    int iU2At4 = u2At(i2);
                    this.classNameIndex = iU2At4;
                    this.className = getConstantClassNameAt(iU2At4);
                    int iU2At5 = u2At(iU2At2 + 4);
                    int i3 = iU2At2 + 6;
                    if (iU2At5 != 0) {
                        try {
                            this.superclassName = getConstantClassNameAt(iU2At5);
                        } catch (Exception unused) {
                            iU2At2 = i3;
                            throw new ClassFormatException(21, iU2At2);
                        }
                    }
                    int iU2At6 = u2At(i3);
                    this.interfacesCount = iU2At6;
                    int i4 = iU2At2 + 8;
                    if (iU2At6 != 0) {
                        this.interfaceNames = new char[iU2At6][];
                        for (int i5 = 0; i5 < this.interfacesCount; i5++) {
                            this.interfaceNames[i5] = getConstantClassNameAt(u2At(i4));
                            i4 += 2;
                        }
                    }
                    int iU2At7 = u2At(i4);
                    this.fieldsCount = iU2At7;
                    int iSizeInBytes = i4 + 2;
                    if (iU2At7 != 0) {
                        this.fields = new FieldInfo[iU2At7];
                        for (int i6 = 0; i6 < this.fieldsCount; i6++) {
                            FieldInfo fieldInfoCreateField = FieldInfo.createField(this.reference, this.constantPoolOffsets, iSizeInBytes);
                            this.fields[i6] = fieldInfoCreateField;
                            iSizeInBytes += fieldInfoCreateField.sizeInBytes();
                        }
                    }
                    int iU2At8 = u2At(iSizeInBytes);
                    this.methodsCount = iU2At8;
                    int iSizeInBytes2 = iSizeInBytes + 2;
                    if (iU2At8 != 0) {
                        this.methods = new MethodInfo[iU2At8];
                        boolean z2 = (this.accessFlags & 8192) != 0;
                        for (int i7 = 0; i7 < this.methodsCount; i7++) {
                            MethodInfo[] methodInfoArr = this.methods;
                            if (z2) {
                                methodInfoCreateMethod = AnnotationMethodInfo.createAnnotationMethod(this.reference, this.constantPoolOffsets, iSizeInBytes2);
                            } else {
                                methodInfoCreateMethod = MethodInfo.createMethod(this.reference, this.constantPoolOffsets, iSizeInBytes2);
                            }
                            methodInfoArr[i7] = methodInfoCreateMethod;
                            iSizeInBytes2 += this.methods[i7].sizeInBytes();
                        }
                    }
                    int iU2At9 = u2At(iSizeInBytes2);
                    iU2At2 = iSizeInBytes2 + 2;
                    for (int i8 = 0; i8 < iU2At9; i8++) {
                        int i9 = this.constantPoolOffsets[u2At(iU2At2)];
                        char[] cArrUtf8At = utf8At(i9 + 3, u2At(i9 + 1));
                        if (cArrUtf8At.length == 0) {
                            j = iU2At2;
                            jU4At = u4At(iU2At2 + 2);
                        } else {
                            char c = cArrUtf8At[0];
                            if (c != 'D') {
                                if (c != 'E') {
                                    if (c != 'I') {
                                        if (c != 'M') {
                                            if (c != 'R') {
                                                if (c == 'S' && cArrUtf8At.length > 2) {
                                                    char c2 = cArrUtf8At[1];
                                                    if (c2 != 'i') {
                                                        if (c2 == 'o') {
                                                            if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.SourceName)) {
                                                                int i10 = this.constantPoolOffsets[u2At(iU2At2 + 6)];
                                                                this.sourceFileName = utf8At(i10 + 3, u2At(i10 + 1));
                                                            }
                                                        } else if (c2 == 'y' && CharOperation.equals(cArrUtf8At, AttributeNamesConstants.SyntheticName)) {
                                                            this.accessFlags |= 4096;
                                                        }
                                                    } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.SignatureName)) {
                                                        int i11 = this.constantPoolOffsets[u2At(iU2At2 + 6)];
                                                        this.signature = utf8At(i11 + 3, u2At(i11 + 1));
                                                    }
                                                }
                                            } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleAnnotationsName)) {
                                                decodeAnnotations(iU2At2, true);
                                            } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleAnnotationsName)) {
                                                decodeAnnotations(iU2At2, false);
                                            } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName)) {
                                                decodeTypeAnnotations(iU2At2, true);
                                            } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName)) {
                                                decodeTypeAnnotations(iU2At2, false);
                                            }
                                        } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.MissingTypesName) && (iU2At = u2At(iU2At2 + 6)) != 0) {
                                            this.missingTypeNames = new char[iU2At][][];
                                            int i12 = iU2At2 + 8;
                                            for (int i13 = 0; i13 < iU2At; i13++) {
                                                int i14 = this.constantPoolOffsets[u2At(this.constantPoolOffsets[u2At(i12)] + 1)];
                                                this.missingTypeNames[i13] = CharOperation.splitOn('/', utf8At(i14 + 3, u2At(i14 + 1)));
                                                i12 += 2;
                                            }
                                        }
                                    } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.InnerClassName)) {
                                        int iU2At10 = u2At(iU2At2 + 6);
                                        if (iU2At10 != 0) {
                                            int i15 = iU2At2 + 8;
                                            this.innerInfos = new InnerClassInfo[iU2At10];
                                            for (int i16 = 0; i16 < iU2At10; i16++) {
                                                this.innerInfos[i16] = new InnerClassInfo(this.reference, this.constantPoolOffsets, i15);
                                                if (this.classNameIndex == this.innerInfos[i16].innerClassNameIndex) {
                                                    this.innerInfo = this.innerInfos[i16];
                                                    this.innerInfoIndex = i16;
                                                }
                                                i15 += 8;
                                            }
                                            InnerClassInfo innerClassInfo = this.innerInfo;
                                            if (innerClassInfo != null && (enclosingTypeName = innerClassInfo.getEnclosingTypeName()) != null) {
                                                this.enclosingTypeName = enclosingTypeName;
                                            }
                                        }
                                    } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.InconsistentHierarchy)) {
                                        this.tagBits |= 131072;
                                    }
                                } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.EnclosingMethodName)) {
                                    int i17 = this.constantPoolOffsets[u2At(this.constantPoolOffsets[u2At(iU2At2 + 6)] + 1)];
                                    this.enclosingTypeName = utf8At(i17 + 3, u2At(i17 + 1));
                                    this.enclosingNameAndTypeIndex = u2At(iU2At2 + 8);
                                }
                            } else if (CharOperation.equals(cArrUtf8At, AttributeNamesConstants.DeprecatedName)) {
                                this.accessFlags |= 1048576;
                            }
                            j = iU2At2;
                            jU4At = u4At(iU2At2 + 2);
                        }
                        iU2At2 = (int) (j + jU4At + 6);
                    }
                    if (z) {
                        initialize();
                    }
                } catch (Exception unused2) {
                    iU2At2 = i2;
                }
            } catch (ClassFormatException e) {
                throw e;
            }
        } catch (Exception unused3) {
        }
    }

    public ZipFile setExternalAnnotationProvider(String str, String str2, ZipFile zipFile, ZipFileProducer zipFileProducer) throws IOException {
        this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.NO_EEA_FILE;
        String str3 = String.valueOf(str2) + ExternalAnnotationProvider.ANNOTATION_FILE_SUFFIX;
        if (zipFile == null) {
            File file = new File(str);
            if (file.isDirectory()) {
                try {
                    this.annotationProvider = new ExternalAnnotationProvider(new FileInputStream(String.valueOf(file.getAbsolutePath()) + '/' + str3), String.valueOf(getName()));
                    this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                } catch (FileNotFoundException unused) {
                }
                return null;
            }
            if (!file.exists()) {
                return null;
            }
            zipFile = zipFileProducer != null ? zipFileProducer.produce() : new ZipFile(file);
        }
        ZipEntry entry = zipFile.getEntry(str3);
        if (entry != null) {
            this.annotationProvider = new ExternalAnnotationProvider(zipFile.getInputStream(entry), String.valueOf(getName()));
            this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
        }
        return zipFile;
    }

    public boolean hasAnnotationProvider() {
        return this.annotationProvider != null;
    }

    public void markAsFromSource() {
        this.externalAnnotationStatus = BinaryTypeBinding.ExternalAnnotationStatus.FROM_SOURCE;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public BinaryTypeBinding.ExternalAnnotationStatus getExternalAnnotationStatus() {
        return this.externalAnnotationStatus;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public ITypeAnnotationWalker enrichWithExternalAnnotationsFor(ITypeAnnotationWalker iTypeAnnotationWalker, Object obj, LookupEnvironment lookupEnvironment) {
        ExternalAnnotationProvider externalAnnotationProvider;
        if (iTypeAnnotationWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER || (externalAnnotationProvider = this.annotationProvider) == null) {
            return iTypeAnnotationWalker;
        }
        if (obj == null) {
            return externalAnnotationProvider.forTypeHeader(lookupEnvironment);
        }
        if (obj instanceof IBinaryField) {
            IBinaryField iBinaryField = (IBinaryField) obj;
            char[] genericSignature = iBinaryField.getGenericSignature();
            if (genericSignature == null) {
                genericSignature = iBinaryField.getTypeName();
            }
            return this.annotationProvider.forField(iBinaryField.getName(), genericSignature, lookupEnvironment);
        }
        if (!(obj instanceof IBinaryMethod)) {
            return iTypeAnnotationWalker;
        }
        IBinaryMethod iBinaryMethod = (IBinaryMethod) obj;
        char[] genericSignature2 = iBinaryMethod.getGenericSignature();
        if (genericSignature2 == null) {
            genericSignature2 = iBinaryMethod.getMethodDescriptor();
        }
        return this.annotationProvider.forMethod(iBinaryMethod.isConstructor() ? TypeConstants.INIT : iBinaryMethod.getSelector(), genericSignature2, lookupEnvironment);
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    private void decodeAnnotations(int i, boolean z) {
        int iU2At = u2At(i + 6);
        if (iU2At > 0) {
            int i2 = i + 8;
            AnnotationInfo[] annotationInfoArr = null;
            int i3 = 0;
            for (int i4 = 0; i4 < iU2At; i4++) {
                AnnotationInfo annotationInfo = new AnnotationInfo(this.reference, this.constantPoolOffsets, i2, z, false);
                i2 += annotationInfo.readOffset;
                long j = annotationInfo.standardAnnotationTagBits;
                if (j != 0) {
                    this.tagBits = j | this.tagBits;
                } else {
                    if (annotationInfoArr == null) {
                        annotationInfoArr = new AnnotationInfo[iU2At - i4];
                    }
                    annotationInfoArr[i3] = annotationInfo;
                    i3++;
                }
            }
            if (annotationInfoArr == null) {
                return;
            }
            AnnotationInfo[] annotationInfoArr2 = this.annotations;
            if (annotationInfoArr2 == null) {
                if (i3 != annotationInfoArr.length) {
                    AnnotationInfo[] annotationInfoArr3 = new AnnotationInfo[i3];
                    System.arraycopy(annotationInfoArr, 0, annotationInfoArr3, 0, i3);
                    annotationInfoArr = annotationInfoArr3;
                }
                this.annotations = annotationInfoArr;
                return;
            }
            int length = annotationInfoArr2.length;
            AnnotationInfo[] annotationInfoArr4 = new AnnotationInfo[length + i3];
            System.arraycopy(annotationInfoArr2, 0, annotationInfoArr4, 0, length);
            System.arraycopy(annotationInfoArr, 0, annotationInfoArr4, length, i3);
            this.annotations = annotationInfoArr4;
        }
    }

    private void decodeTypeAnnotations(int i, boolean z) {
        int iU2At = u2At(i + 6);
        if (iU2At > 0) {
            int i2 = i + 8;
            TypeAnnotationInfo[] typeAnnotationInfoArr = new TypeAnnotationInfo[iU2At];
            for (int i3 = 0; i3 < iU2At; i3++) {
                TypeAnnotationInfo typeAnnotationInfo = new TypeAnnotationInfo(this.reference, this.constantPoolOffsets, i2, z, false);
                i2 += typeAnnotationInfo.readOffset;
                typeAnnotationInfoArr[i3] = typeAnnotationInfo;
            }
            TypeAnnotationInfo[] typeAnnotationInfoArr2 = this.typeAnnotations;
            if (typeAnnotationInfoArr2 == null) {
                this.typeAnnotations = typeAnnotationInfoArr;
                return;
            }
            int length = typeAnnotationInfoArr2.length;
            TypeAnnotationInfo[] typeAnnotationInfoArr3 = new TypeAnnotationInfo[length + iU2At];
            System.arraycopy(typeAnnotationInfoArr2, 0, typeAnnotationInfoArr3, 0, length);
            System.arraycopy(typeAnnotationInfoArr, 0, typeAnnotationInfoArr3, length, iU2At);
            this.typeAnnotations = typeAnnotationInfoArr3;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public IBinaryAnnotation[] getAnnotations() {
        return this.annotations;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public IBinaryTypeAnnotation[] getTypeAnnotations() {
        return this.typeAnnotations;
    }

    private char[] getConstantClassNameAt(int i) {
        int i2 = this.constantPoolOffsets[u2At(this.constantPoolOffsets[i] + 1)];
        return utf8At(i2 + 3, u2At(i2 + 1));
    }

    public int[] getConstantPoolOffsets() {
        return this.constantPoolOffsets;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] getEnclosingMethod() {
        if (this.enclosingNameAndTypeIndex <= 0) {
            return null;
        }
        if (this.enclosingMethod == null) {
            StringBuffer stringBuffer = new StringBuffer();
            int i = this.constantPoolOffsets[this.enclosingNameAndTypeIndex];
            int i2 = this.constantPoolOffsets[u2At(i + 1)];
            stringBuffer.append(utf8At(i2 + 3, u2At(i2 + 1)));
            int i3 = this.constantPoolOffsets[u2At(i + 3)];
            stringBuffer.append(utf8At(i3 + 3, u2At(i3 + 1)));
            this.enclosingMethod = String.valueOf(stringBuffer).toCharArray();
        }
        return this.enclosingMethod;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] getEnclosingTypeName() {
        return this.enclosingTypeName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public IBinaryField[] getFields() {
        return this.fields;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IDependent
    public char[] getFileName() {
        return this.classFileName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] getGenericSignature() {
        return this.signature;
    }

    public char[] getInnerSourceName() {
        InnerClassInfo innerClassInfo = this.innerInfo;
        if (innerClassInfo != null) {
            return innerClassInfo.getSourceName();
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[][] getInterfaceNames() {
        return this.interfaceNames;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public IBinaryNestedType[] getMemberTypes() {
        InnerClassInfo[] innerClassInfoArr = this.innerInfos;
        if (innerClassInfoArr == null) {
            return null;
        }
        int length = innerClassInfoArr.length;
        int i = this.innerInfo != null ? this.innerInfoIndex + 1 : 0;
        if (length == i) {
            return null;
        }
        int i2 = length - this.innerInfoIndex;
        IBinaryNestedType[] iBinaryNestedTypeArr = new IBinaryNestedType[i2];
        int i3 = 0;
        while (i < length) {
            InnerClassInfo innerClassInfo = this.innerInfos[i];
            int i4 = innerClassInfo.outerClassNameIndex;
            int i5 = innerClassInfo.innerNameIndex;
            if (i4 != 0 && i5 != 0 && i4 == this.classNameIndex && innerClassInfo.getSourceName().length != 0) {
                iBinaryNestedTypeArr[i3] = innerClassInfo;
                i3++;
            }
            i++;
        }
        if (i3 == 0) {
            return null;
        }
        if (i3 == i2) {
            return iBinaryNestedTypeArr;
        }
        IBinaryNestedType[] iBinaryNestedTypeArr2 = new IBinaryNestedType[i3];
        System.arraycopy(iBinaryNestedTypeArr, 0, iBinaryNestedTypeArr2, 0, i3);
        return iBinaryNestedTypeArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public IBinaryMethod[] getMethods() {
        return this.methods;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[][][] getMissingTypeNames() {
        return this.missingTypeNames;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IGenericType
    public int getModifiers() {
        InnerClassInfo innerClassInfo = this.innerInfo;
        if (innerClassInfo != null) {
            int modifiers = innerClassInfo.getModifiers();
            int i = this.accessFlags;
            return modifiers | (1048576 & i) | (i & 4096);
        }
        return this.accessFlags;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] getName() {
        return this.className;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] getSourceName() {
        int iLastIndexOf;
        char[] cArr = this.sourceName;
        if (cArr != null) {
            return cArr;
        }
        char[] innerSourceName = getInnerSourceName();
        if (innerSourceName == null) {
            innerSourceName = getName();
            if (isAnonymous()) {
                iLastIndexOf = CharOperation.indexOf('$', innerSourceName, CharOperation.lastIndexOf('/', innerSourceName) + 1);
            } else {
                iLastIndexOf = CharOperation.lastIndexOf('/', innerSourceName);
            }
            int i = iLastIndexOf + 1;
            if (i > 0) {
                int length = innerSourceName.length - i;
                char[] cArr2 = new char[length];
                System.arraycopy(innerSourceName, i, cArr2, 0, length);
                innerSourceName = cArr2;
            }
        }
        this.sourceName = innerSourceName;
        return innerSourceName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] getSuperclassName() {
        return this.superclassName;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public long getTagBits() {
        return this.tagBits;
    }

    public long getVersion() {
        return this.version;
    }

    private boolean hasNonSyntheticFieldChanges(FieldInfo[] fieldInfoArr, FieldInfo[] fieldInfoArr2) {
        int length = fieldInfoArr == null ? 0 : fieldInfoArr.length;
        int length2 = fieldInfoArr2 == null ? 0 : fieldInfoArr2.length;
        int i = 0;
        int i2 = 0;
        loop0: while (i < length && i2 < length2) {
            while (fieldInfoArr[i].isSynthetic()) {
                i++;
                if (i >= length) {
                    break loop0;
                }
            }
            while (fieldInfoArr2[i2].isSynthetic()) {
                i2++;
                if (i2 >= length2) {
                    break loop0;
                }
            }
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (hasStructuralFieldChanges(fieldInfoArr[i], fieldInfoArr2[i2])) {
                return true;
            }
            i = i3;
            i2 = i4;
        }
        while (i < length) {
            int i5 = i + 1;
            if (!fieldInfoArr[i].isSynthetic()) {
                return true;
            }
            i = i5;
        }
        while (i2 < length2) {
            int i6 = i2 + 1;
            if (!fieldInfoArr2[i2].isSynthetic()) {
                return true;
            }
            i2 = i6;
        }
        return false;
    }

    private boolean hasNonSyntheticMethodChanges(MethodInfo[] methodInfoArr, MethodInfo[] methodInfoArr2) {
        int length = methodInfoArr == null ? 0 : methodInfoArr.length;
        int length2 = methodInfoArr2 == null ? 0 : methodInfoArr2.length;
        int i = 0;
        int i2 = 0;
        loop0: while (i < length && i2 < length2) {
            while (true) {
                MethodInfo methodInfo = methodInfoArr[i];
                if (methodInfo.isSynthetic() || methodInfo.isClinit()) {
                    i++;
                    if (i >= length) {
                        break loop0;
                    }
                }
            }
            while (true) {
                MethodInfo methodInfo2 = methodInfoArr2[i2];
                if (methodInfo2.isSynthetic() || methodInfo2.isClinit()) {
                    i2++;
                    if (i2 >= length2) {
                        break loop0;
                    }
                }
            }
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (hasStructuralMethodChanges(methodInfoArr[i], methodInfoArr2[i2])) {
                return true;
            }
            i = i3;
            i2 = i4;
        }
        while (i < length) {
            int i5 = i + 1;
            MethodInfo methodInfo3 = methodInfoArr[i];
            if (!methodInfo3.isSynthetic() && !methodInfo3.isClinit()) {
                return true;
            }
            i = i5;
        }
        while (i2 < length2) {
            int i6 = i2 + 1;
            MethodInfo methodInfo4 = methodInfoArr2[i2];
            if (!methodInfo4.isSynthetic() && !methodInfo4.isClinit()) {
                return true;
            }
            i2 = i6;
        }
        return false;
    }

    public boolean hasStructuralChanges(byte[] bArr) {
        return hasStructuralChanges(bArr, true, true);
    }

    public boolean hasStructuralChanges(byte[] bArr, boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        int length;
        int i;
        try {
            ClassFileReader classFileReader = new ClassFileReader(bArr, this.classFileName);
            if (getModifiers() != classFileReader.getModifiers() || (getTagBits() & 27162300892971008L) != (27162300892971008L & classFileReader.getTagBits()) || hasStructuralAnnotationChanges(getAnnotations(), classFileReader.getAnnotations())) {
                return true;
            }
            if ((this.version >= ClassFileConstants.JDK1_8 && hasStructuralTypeAnnotationChanges(getTypeAnnotations(), classFileReader.getTypeAnnotations())) || !CharOperation.equals(getGenericSignature(), classFileReader.getGenericSignature()) || !CharOperation.equals(getSuperclassName(), classFileReader.getSuperclassName())) {
                return true;
            }
            char[][] interfaceNames = classFileReader.getInterfaceNames();
            if (this.interfaceNames != interfaceNames) {
                int length2 = interfaceNames == null ? 0 : interfaceNames.length;
                int i2 = this.interfacesCount;
                if (length2 != i2) {
                    return true;
                }
                for (int i3 = 0; i3 < i2; i3++) {
                    if (!CharOperation.equals(this.interfaceNames[i3], interfaceNames[i3])) {
                        return true;
                    }
                }
            }
            IBinaryNestedType[] memberTypes = getMemberTypes();
            IBinaryNestedType[] memberTypes2 = classFileReader.getMemberTypes();
            if (memberTypes != memberTypes2) {
                int length3 = memberTypes == null ? 0 : memberTypes.length;
                if (length3 != (memberTypes2 == null ? 0 : memberTypes2.length)) {
                    return true;
                }
                for (0; i < length3; i + 1) {
                    i = (CharOperation.equals(memberTypes[i].getName(), memberTypes2[i].getName()) && memberTypes[i].getModifiers() == memberTypes2[i].getModifiers()) ? i + 1 : 0;
                    return true;
                }
            }
            FieldInfo[] fieldInfoArr = (FieldInfo[]) classFileReader.getFields();
            int length4 = fieldInfoArr == null ? 0 : fieldInfoArr.length;
            if (this.fieldsCount == length4) {
                int i4 = 0;
                while (i4 < this.fieldsCount && !hasStructuralFieldChanges(this.fields[i4], fieldInfoArr[i4])) {
                    i4++;
                }
                z3 = i4 != this.fieldsCount;
                if (z3 && !z && !z2) {
                    return true;
                }
            } else {
                z3 = true;
            }
            if (z3) {
                int i5 = this.fieldsCount;
                if (i5 != length4 && !z2) {
                    return true;
                }
                if (z) {
                    if (i5 != 0) {
                        Arrays.sort(this.fields);
                    }
                    if (length4 != 0) {
                        Arrays.sort(fieldInfoArr);
                    }
                }
                if (z2) {
                    if (hasNonSyntheticFieldChanges(this.fields, fieldInfoArr)) {
                        return true;
                    }
                } else {
                    for (int i6 = 0; i6 < this.fieldsCount; i6++) {
                        if (hasStructuralFieldChanges(this.fields[i6], fieldInfoArr[i6])) {
                            return true;
                        }
                    }
                }
            }
            MethodInfo[] methodInfoArr = (MethodInfo[]) classFileReader.getMethods();
            int length5 = methodInfoArr == null ? 0 : methodInfoArr.length;
            if (this.methodsCount == length5) {
                int i7 = 0;
                while (i7 < this.methodsCount && !hasStructuralMethodChanges(this.methods[i7], methodInfoArr[i7])) {
                    i7++;
                }
                z4 = i7 != this.methodsCount;
                if (z4 && !z && !z2) {
                    return true;
                }
            } else {
                z4 = true;
            }
            if (z4) {
                int i8 = this.methodsCount;
                if (i8 != length5 && !z2) {
                    return true;
                }
                if (z) {
                    if (i8 != 0) {
                        Arrays.sort(this.methods);
                    }
                    if (length5 != 0) {
                        Arrays.sort(methodInfoArr);
                    }
                }
                if (z2) {
                    if (hasNonSyntheticMethodChanges(this.methods, methodInfoArr)) {
                        return true;
                    }
                } else {
                    for (int i9 = 0; i9 < this.methodsCount; i9++) {
                        if (hasStructuralMethodChanges(this.methods[i9], methodInfoArr[i9])) {
                            return true;
                        }
                    }
                }
            }
            char[][][] missingTypeNames = getMissingTypeNames();
            char[][][] missingTypeNames2 = classFileReader.getMissingTypeNames();
            if (missingTypeNames != null) {
                if (missingTypeNames2 == null || (length = missingTypeNames.length) != missingTypeNames2.length) {
                    return true;
                }
                for (int i10 = 0; i10 < length; i10++) {
                    if (!CharOperation.equals(missingTypeNames[i10], missingTypeNames2[i10])) {
                        return true;
                    }
                }
            } else if (missingTypeNames2 != null) {
                return true;
            }
            return false;
        } catch (ClassFormatException unused) {
            return true;
        }
    }

    private boolean hasStructuralAnnotationChanges(IBinaryAnnotation[] iBinaryAnnotationArr, IBinaryAnnotation[] iBinaryAnnotationArr2) {
        if (iBinaryAnnotationArr == iBinaryAnnotationArr2) {
            return false;
        }
        int length = iBinaryAnnotationArr == null ? 0 : iBinaryAnnotationArr.length;
        if (length != (iBinaryAnnotationArr2 == null ? 0 : iBinaryAnnotationArr2.length)) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            Boolean boolMatchAnnotations = matchAnnotations(iBinaryAnnotationArr[i], iBinaryAnnotationArr2[i]);
            if (boolMatchAnnotations != null) {
                return boolMatchAnnotations.booleanValue();
            }
        }
        return false;
    }

    private Boolean matchAnnotations(IBinaryAnnotation iBinaryAnnotation, IBinaryAnnotation iBinaryAnnotation2) {
        if (!CharOperation.equals(iBinaryAnnotation.getTypeName(), iBinaryAnnotation2.getTypeName())) {
            return true;
        }
        IBinaryElementValuePair[] elementValuePairs = iBinaryAnnotation.getElementValuePairs();
        IBinaryElementValuePair[] elementValuePairs2 = iBinaryAnnotation2.getElementValuePairs();
        int length = elementValuePairs == null ? 0 : elementValuePairs.length;
        if (length != (elementValuePairs2 == null ? 0 : elementValuePairs2.length)) {
            return Boolean.TRUE;
        }
        for (int i = 0; i < length; i++) {
            if (!CharOperation.equals(elementValuePairs[i].getName(), elementValuePairs2[i].getName())) {
                return Boolean.TRUE;
            }
            Object value = elementValuePairs[i].getValue();
            Object value2 = elementValuePairs2[i].getValue();
            if (value instanceof Object[]) {
                Object[] objArr = (Object[]) value;
                if (value2 instanceof Object[]) {
                    Object[] objArr2 = (Object[]) value2;
                    int length2 = objArr.length;
                    if (length2 != objArr2.length) {
                        return Boolean.TRUE;
                    }
                    for (int i2 = 0; i2 < length2; i2++) {
                        if (!objArr[i2].equals(objArr2[i2])) {
                            return Boolean.TRUE;
                        }
                    }
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
            if (!value.equals(value2)) {
                return Boolean.TRUE;
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean hasStructuralFieldChanges(FieldInfo fieldInfo, FieldInfo fieldInfo2) {
        if (!CharOperation.equals(fieldInfo.getGenericSignature(), fieldInfo2.getGenericSignature()) || fieldInfo.getModifiers() != fieldInfo2.getModifiers() || (fieldInfo.getTagBits() & 70368744177664L) != (70368744177664L & fieldInfo2.getTagBits()) || hasStructuralAnnotationChanges(fieldInfo.getAnnotations(), fieldInfo2.getAnnotations())) {
            return true;
        }
        if ((this.version >= ClassFileConstants.JDK1_8 && hasStructuralTypeAnnotationChanges(fieldInfo.getTypeAnnotations(), fieldInfo2.getTypeAnnotations())) || !CharOperation.equals(fieldInfo.getName(), fieldInfo2.getName()) || !CharOperation.equals(fieldInfo.getTypeName(), fieldInfo2.getTypeName()) || fieldInfo.hasConstant() != fieldInfo2.hasConstant()) {
            return true;
        }
        if (fieldInfo.hasConstant()) {
            Constant constant = fieldInfo.getConstant();
            Constant constant2 = fieldInfo2.getConstant();
            if (constant.typeID() != constant2.typeID() || !constant.getClass().equals(constant2.getClass())) {
                return true;
            }
            switch (constant.typeID()) {
                case 2:
                    if (constant.charValue() != constant2.charValue()) {
                        return true;
                    }
                    break;
                case 3:
                    return constant.byteValue() != constant2.byteValue();
                case 4:
                    return constant.shortValue() != constant2.shortValue();
                case 5:
                    return constant.booleanValue() ^ constant2.booleanValue();
                case 7:
                    return constant.longValue() != constant2.longValue();
                case 8:
                    return constant.doubleValue() != constant2.doubleValue();
                case 9:
                    return constant.floatValue() != constant2.floatValue();
                case 10:
                    return constant.intValue() != constant2.intValue();
                case 11:
                    return !constant.stringValue().equals(constant2.stringValue());
            }
        }
        return false;
    }

    private boolean hasStructuralMethodChanges(MethodInfo methodInfo, MethodInfo methodInfo2) {
        int annotatedParametersCount;
        if (!CharOperation.equals(methodInfo.getGenericSignature(), methodInfo2.getGenericSignature()) || methodInfo.getModifiers() != methodInfo2.getModifiers() || (methodInfo.getTagBits() & 70368744177664L) != (70368744177664L & methodInfo2.getTagBits()) || hasStructuralAnnotationChanges(methodInfo.getAnnotations(), methodInfo2.getAnnotations()) || (annotatedParametersCount = methodInfo.getAnnotatedParametersCount()) != methodInfo2.getAnnotatedParametersCount()) {
            return true;
        }
        for (int i = 0; i < annotatedParametersCount; i++) {
            if (hasStructuralAnnotationChanges(methodInfo.getParameterAnnotations(i, this.classFileName), methodInfo2.getParameterAnnotations(i, this.classFileName))) {
                return true;
            }
        }
        if ((this.version >= ClassFileConstants.JDK1_8 && hasStructuralTypeAnnotationChanges(methodInfo.getTypeAnnotations(), methodInfo2.getTypeAnnotations())) || !CharOperation.equals(methodInfo.getSelector(), methodInfo2.getSelector()) || !CharOperation.equals(methodInfo.getMethodDescriptor(), methodInfo2.getMethodDescriptor()) || !CharOperation.equals(methodInfo.getGenericSignature(), methodInfo2.getGenericSignature())) {
            return true;
        }
        char[][] exceptionTypeNames = methodInfo.getExceptionTypeNames();
        char[][] exceptionTypeNames2 = methodInfo2.getExceptionTypeNames();
        if (exceptionTypeNames != exceptionTypeNames2) {
            int length = exceptionTypeNames == null ? 0 : exceptionTypeNames.length;
            if (length != (exceptionTypeNames2 == null ? 0 : exceptionTypeNames2.length)) {
                return true;
            }
            for (int i2 = 0; i2 < length; i2++) {
                if (!CharOperation.equals(exceptionTypeNames[i2], exceptionTypeNames2[i2])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasStructuralTypeAnnotationChanges(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr, IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr2) {
        if (iBinaryTypeAnnotationArr2 != null) {
            int length = iBinaryTypeAnnotationArr2.length;
            IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr3 = new IBinaryTypeAnnotation[length];
            System.arraycopy(iBinaryTypeAnnotationArr2, 0, iBinaryTypeAnnotationArr3, 0, length);
            iBinaryTypeAnnotationArr2 = iBinaryTypeAnnotationArr3;
        }
        if (iBinaryTypeAnnotationArr != null) {
            for (IBinaryTypeAnnotation iBinaryTypeAnnotation : iBinaryTypeAnnotationArr) {
                if (affectsSignature(iBinaryTypeAnnotation)) {
                    if (iBinaryTypeAnnotationArr2 == null) {
                        return true;
                    }
                    for (int i = 0; i < iBinaryTypeAnnotationArr2.length; i++) {
                        IBinaryTypeAnnotation iBinaryTypeAnnotation2 = iBinaryTypeAnnotationArr2[i];
                        if (iBinaryTypeAnnotation2 != null && matchAnnotations(iBinaryTypeAnnotation.getAnnotation(), iBinaryTypeAnnotation2.getAnnotation()) == Boolean.TRUE) {
                            iBinaryTypeAnnotationArr2[i] = null;
                        }
                    }
                    return true;
                }
            }
        }
        if (iBinaryTypeAnnotationArr2 != null) {
            for (IBinaryTypeAnnotation iBinaryTypeAnnotation3 : iBinaryTypeAnnotationArr2) {
                if (affectsSignature(iBinaryTypeAnnotation3)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean affectsSignature(IBinaryTypeAnnotation iBinaryTypeAnnotation) {
        if (iBinaryTypeAnnotation == null) {
            return false;
        }
        int targetType = iBinaryTypeAnnotation.getTargetType();
        return targetType < 64 || targetType > 75;
    }

    private void initialize() throws ClassFormatException {
        try {
            int i = this.fieldsCount;
            for (int i2 = 0; i2 < i; i2++) {
                this.fields[i2].initialize();
            }
            int i3 = this.methodsCount;
            for (int i4 = 0; i4 < i3; i4++) {
                this.methods[i4].initialize();
            }
            InnerClassInfo[] innerClassInfoArr = this.innerInfos;
            if (innerClassInfoArr != null) {
                int length = innerClassInfoArr.length;
                for (int i5 = 0; i5 < length; i5++) {
                    this.innerInfos[i5].initialize();
                }
            }
            AnnotationInfo[] annotationInfoArr = this.annotations;
            if (annotationInfoArr != null) {
                int length2 = annotationInfoArr.length;
                for (int i6 = 0; i6 < length2; i6++) {
                    this.annotations[i6].initialize();
                }
            }
            getEnclosingMethod();
            reset();
        } catch (RuntimeException e) {
            throw new ClassFormatException(e, this.classFileName);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public boolean isAnonymous() {
        InnerClassInfo innerClassInfo = this.innerInfo;
        if (innerClassInfo == null) {
            return false;
        }
        char[] sourceName = innerClassInfo.getSourceName();
        return sourceName == null || sourceName.length == 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public boolean isLocal() {
        char[] sourceName;
        InnerClassInfo innerClassInfo = this.innerInfo;
        return innerClassInfo != null && innerClassInfo.getEnclosingTypeName() == null && (sourceName = this.innerInfo.getSourceName()) != null && sourceName.length > 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public boolean isMember() {
        char[] sourceName;
        InnerClassInfo innerClassInfo = this.innerInfo;
        return (innerClassInfo == null || innerClassInfo.getEnclosingTypeName() == null || (sourceName = this.innerInfo.getSourceName()) == null || sourceName.length <= 0) ? false : true;
    }

    public boolean isNestedType() {
        return this.innerInfo != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.env.IBinaryType
    public char[] sourceFileName() {
        return this.sourceFileName;
    }

    public String toString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
        printWriter.println(String.valueOf(getClass().getName()) + "{");
        printWriter.println(" this.className: ".concat(new String(getName())));
        printWriter.println(" this.superclassName: ".concat(getSuperclassName() == null ? "null" : new String(getSuperclassName())));
        printWriter.println(" access_flags: " + printTypeModifiers(accessFlags()) + "(" + accessFlags() + com.huawei.hms.ml.language.common.utils.Constant.AFTER_QUTO);
        printWriter.flush();
        return byteArrayOutputStream.toString();
    }
}
