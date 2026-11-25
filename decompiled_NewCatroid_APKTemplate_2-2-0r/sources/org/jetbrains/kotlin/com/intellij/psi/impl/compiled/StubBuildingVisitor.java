package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import com.reandroid.arsc.value.CompoundEntry;
import java.lang.reflect.Array;
import java.text.StringCharacterIterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.description.method.MethodDescription;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.cache.TypeInfo;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaClassReferenceListElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassReferenceListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiFieldStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiModifierListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordComponentStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRecordHeaderStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiAnnotationStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassReferenceListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiFieldStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiMethodStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiModifierListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiParameterListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiParameterStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiRecordComponentStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiRecordHeaderStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiTypeParameterListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: loaded from: classes3.dex */
public class StubBuildingVisitor<T> extends ClassVisitor {
    private final int myAccess;
    private Map<TypeInfo, ClsTypeAnnotationCollector> myAnnoBuilders;
    private final boolean myAnonymousInner;
    private ClassInfo myClassInfo;
    private final FirstPassData myFirstPassData;
    private PsiRecordHeaderStub myHeaderStub;
    private final InnerClassSourceStrategy<T> myInnersStrategy;
    private String myInternalName;
    private final boolean myLocalClassInner;
    private PsiModifierListStub myModList;
    private final StubElement<?> myParent;
    private PsiClassStub<?> myResult;
    private final String myShortName;
    private final T mySource;
    private static final Logger LOG = Logger.getInstance((Class<?>) StubBuildingVisitor.class);
    private static final String[] parameterNames = {"p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9"};
    public static final Function<String, String> GUESSING_MAPPER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$$ExternalSyntheticLambda4
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return StubBuildingVisitor.lambda$static$4((String) obj);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i != 2) {
            objArr[0] = "internalName";
        } else {
            objArr[0] = "mapping";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/StubBuildingVisitor";
        if (i == 1) {
            objArr[2] = "newReferenceList";
        } else if (i != 2) {
            objArr[2] = "getFqn";
        } else {
            objArr[2] = "toJavaType";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public StubBuildingVisitor(T t, InnerClassSourceStrategy<T> innerClassSourceStrategy, StubElement<?> stubElement, int i, String str) {
        this(t, innerClassSourceStrategy, stubElement, i, str, false, false);
    }

    public StubBuildingVisitor(T t, InnerClassSourceStrategy<T> innerClassSourceStrategy, StubElement<?> stubElement, int i, String str, boolean z, boolean z2) {
        super(589824);
        this.mySource = t;
        this.myInnersStrategy = innerClassSourceStrategy;
        this.myParent = stubElement;
        this.myAccess = i;
        this.myShortName = str;
        this.myFirstPassData = FirstPassData.create(t);
        this.myAnonymousInner = z;
        this.myLocalClassInner = z2;
    }

    public PsiClassStub<?> getResult() {
        return this.myResult;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        String packageName;
        this.myInternalName = str;
        StubElement<?> stubElement = this.myParent;
        String fqn = null;
        if (stubElement instanceof PsiClassStub) {
            packageName = ((PsiClassStub) stubElement).getQualifiedName();
        } else {
            packageName = stubElement instanceof PsiJavaFileStub ? ((PsiJavaFileStub) stubElement).getPackageName() : null;
        }
        if (!this.myAnonymousInner && !this.myLocalClassInner) {
            fqn = getFqn(str, this.myShortName, packageName);
        }
        String str4 = fqn;
        String str5 = this.myShortName;
        String shortClassName = ((str5 == null || !str.endsWith(str5)) && str4 != null) ? PsiNameHelper.getShortClassName(str4) : this.myShortName;
        int i3 = this.myAccess | i2;
        boolean zIsSet = BitUtil.isSet(i3, 131072);
        boolean zIsSet2 = BitUtil.isSet(i3, 512);
        boolean zIsSet3 = BitUtil.isSet(i3, 16384);
        boolean zIsSet4 = BitUtil.isSet(i3, 8192);
        boolean zIsSet5 = BitUtil.isSet(i3, 65536);
        this.myResult = new PsiClassStubImpl(JavaStubElementTypes.CLASS, this.myParent, str4, shortClassName, null, PsiClassStubImpl.packFlags(zIsSet, zIsSet2, zIsSet3, false, false, zIsSet4, false, false, this.myAnonymousInner, this.myLocalClassInner, false, zIsSet5));
        this.myModList = new PsiModifierListStubImpl(this.myResult, packClassFlags(i3));
        if (zIsSet5) {
            this.myHeaderStub = new PsiRecordHeaderStubImpl(this.myResult);
        }
        if (str2 != null) {
            try {
                this.myClassInfo = parseClassSignature(str2);
            } catch (ClsFormatException e) {
                Logger logger = LOG;
                if (logger.isDebugEnabled()) {
                    logger.debug("source=" + this.mySource + " signature=" + str2, e);
                }
            }
        }
        if (this.myClassInfo == null) {
            this.myClassInfo = parseClassDescription(str3, strArr);
        }
        new PsiTypeParameterListStubImpl(this.myResult);
        if (this.myResult.isInterface()) {
            if (this.myClassInfo.interfaces != null && this.myResult.isAnnotationType()) {
                this.myClassInfo.interfaces.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((TypeInfo) obj).text.equals(CommonClassNames.JAVA_LANG_ANNOTATION_ANNOTATION);
                    }
                });
            }
            newReferenceList(JavaStubElementTypes.EXTENDS_LIST, this.myResult, this.myClassInfo.interfaces);
            newReferenceList(JavaStubElementTypes.IMPLEMENTS_LIST, this.myResult, Collections.emptyList());
            return;
        }
        if (this.myClassInfo.superType == null || ((this.myResult.isEnum() && "java/lang/Enum".equals(str3)) || (this.myResult.isRecord() && "java/lang/Record".equals(str3)))) {
            newReferenceList(JavaStubElementTypes.EXTENDS_LIST, this.myResult, Collections.emptyList());
        } else {
            newReferenceList(JavaStubElementTypes.EXTENDS_LIST, this.myResult, Collections.singletonList(this.myClassInfo.superType));
        }
        newReferenceList(JavaStubElementTypes.IMPLEMENTS_LIST, this.myResult, this.myClassInfo.interfaces);
    }

    private String getFqn(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null || !str.endsWith(str2)) {
            return this.myFirstPassData.mapJvmClassNameToJava(str);
        }
        if (str.length() == str2.length()) {
            return str2;
        }
        if (str3 == null) {
            str3 = this.myFirstPassData.mapJvmClassNameToJava(str.substring(0, (str.length() - str2.length()) - 1));
        }
        return str3 + '.' + str2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ClassInfo parseClassSignature(String str) throws ClsFormatException {
        ClassInfo classInfo = new ClassInfo();
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(str);
        classInfo.typeParameters = SignatureParsing.parseTypeParametersDeclaration(stringCharacterIterator, this.myFirstPassData);
        String topLevelClassRefSignature = SignatureParsing.parseTopLevelClassRefSignature(stringCharacterIterator, this.myFirstPassData);
        classInfo.superType = topLevelClassRefSignature != null ? new TypeInfo(topLevelClassRefSignature) : null;
        while (stringCharacterIterator.current() != 65535) {
            String topLevelClassRefSignature2 = SignatureParsing.parseTopLevelClassRefSignature(stringCharacterIterator, this.myFirstPassData);
            if (topLevelClassRefSignature2 == null) {
                throw new ClsFormatException();
            }
            if (classInfo.interfaces == null) {
                classInfo.interfaces = new SmartList();
            }
            classInfo.interfaces.add(new TypeInfo(topLevelClassRefSignature2));
        }
        return classInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ClassInfo parseClassDescription(String str, String[] strArr) {
        ClassInfo classInfo = new ClassInfo();
        classInfo.typeParameters = SignatureParsing.TypeParametersDeclaration.EMPTY;
        classInfo.superType = str != null ? new TypeInfo(this.myFirstPassData.mapJvmClassNameToJava(str, false)) : null;
        classInfo.interfaces = this.myFirstPassData.createTypes(strArr);
        return classInfo;
    }

    private static void newReferenceList(JavaClassReferenceListElementType javaClassReferenceListElementType, StubElement<?> stubElement, List<TypeInfo> list) {
        if (javaClassReferenceListElementType == null) {
            $$$reportNull$$$0(1);
        }
        new PsiClassReferenceListStubImpl(javaClassReferenceListElementType, stubElement, list == null ? TypeInfo.EMPTY_ARRAY : (TypeInfo[]) list.toArray(TypeInfo.EMPTY_ARRAY));
    }

    private static int packCommonFlags(int i) {
        int i2 = 2;
        if (!BitUtil.isSet(i, 2)) {
            i2 = 4;
            if (!BitUtil.isSet(i, 4)) {
                i2 = 1;
                if (!BitUtil.isSet(i, 1)) {
                    i2 = 4096;
                }
            }
        }
        if (BitUtil.isSet(i, 8)) {
            i2 |= 8;
        }
        return BitUtil.isSet(i, 16) ? i2 | 16 : i2;
    }

    private static int packClassFlags(int i) {
        int iPackCommonFlags = packCommonFlags(i);
        return BitUtil.isSet(i, 1024) ? iPackCommonFlags | 1024 : iPackCommonFlags;
    }

    private static int packFieldFlags(int i) {
        int iPackCommonFlags = packCommonFlags(i);
        if (BitUtil.isSet(i, 64)) {
            iPackCommonFlags |= 64;
        }
        return BitUtil.isSet(i, 128) ? iPackCommonFlags | 128 : iPackCommonFlags;
    }

    private static int packMethodFlags(int i, boolean z) {
        int iPackCommonFlags = packCommonFlags(i);
        if (BitUtil.isSet(i, 32)) {
            iPackCommonFlags |= 32;
        }
        if (BitUtil.isSet(i, 256)) {
            iPackCommonFlags |= 256;
        }
        if (BitUtil.isSet(i, 2048)) {
            iPackCommonFlags |= 2048;
        }
        if (BitUtil.isSet(i, 1024)) {
            return iPackCommonFlags | 1024;
        }
        return (!z || BitUtil.isSet(i, 8)) ? iPackCommonFlags : iPackCommonFlags | 512;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitSource(String str, String str2) {
        ((PsiClassStubImpl) this.myResult).setSourceFileName(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitOuterClass(String str, String str2, String str3) {
        if (this.myParent instanceof PsiFileStub) {
            throw new OutOfOrderInnerClassException();
        }
    }

    /* JADX INFO: renamed from: lambda$visitAnnotation$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor, reason: not valid java name */
    /* synthetic */ void m6606x5766247e(String str) {
        new PsiAnnotationStubImpl(this.myModList, str);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        return new AnnotationTextCollector(str, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
            public final void consume(Object obj) {
                this.f$0.m6606x5766247e((String) obj);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:17:0x005b  */
    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        TypeInfo parameterType;
        TypeReference typeReference = new TypeReference(i);
        if (typeReference.getSort() != 17) {
            if (typeReference.getSort() != 0) {
                if (typeReference.getSort() != 16) {
                    parameterType = null;
                } else {
                    int superTypeIndex = typeReference.getSuperTypeIndex();
                    if (superTypeIndex == -1) {
                        parameterType = this.myClassInfo.superType;
                    } else if (superTypeIndex < 0 || superTypeIndex >= this.myClassInfo.interfaces.size()) {
                        parameterType = null;
                    } else {
                        parameterType = (TypeInfo) this.myClassInfo.interfaces.get(superTypeIndex);
                    }
                }
            } else {
                parameterType = this.myClassInfo.typeParameters.getParameterType(typeReference);
            }
        } else {
            parameterType = this.myClassInfo.typeParameters.getBoundType(typeReference);
        }
        if (parameterType == null) {
            return null;
        }
        if (this.myAnnoBuilders == null) {
            this.myAnnoBuilders = new HashMap();
        }
        return this.myAnnoBuilders.computeIfAbsent(parameterType, new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6607x3407c6d9((TypeInfo) obj);
            }
        }).collect(typePath, str);
    }

    /* JADX INFO: renamed from: lambda$visitTypeAnnotation$2$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor, reason: not valid java name */
    /* synthetic */ ClsTypeAnnotationCollector m6607x3407c6d9(TypeInfo typeInfo) {
        return new ClsTypeAnnotationCollector(typeInfo, this.myFirstPassData);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitEnd() {
        Map<TypeInfo, ClsTypeAnnotationCollector> map = this.myAnnoBuilders;
        if (map != null) {
            map.values().forEach(new StubBuildingVisitor$$ExternalSyntheticLambda2());
        }
        this.myClassInfo.typeParameters.fillInTypeParameterList(this.myResult);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitInnerClass(String str, String str2, String str3, int i) {
        int iLastIndexOf;
        String str4;
        T tFindInnerClass;
        if (BitUtil.isSet(i, 4096)) {
            return;
        }
        boolean z = str3 == null;
        boolean z2 = !z && str2 == null;
        if (str3 != null && str2 != null) {
            str4 = str3;
        } else {
            if (this.myInternalName.equals(str) || (iLastIndexOf = str.lastIndexOf(36)) == -1) {
                return;
            }
            if (z) {
                str3 = str.substring(iLastIndexOf + 1);
                str2 = str.substring(0, iLastIndexOf);
                str4 = str3;
            } else {
                String strSubstring = str.substring(0, iLastIndexOf);
                str4 = str3;
                str3 = str.substring(iLastIndexOf + 1);
                str2 = strSubstring;
            }
        }
        if ((this.myParent instanceof PsiFileStub) && this.myInternalName.equals(str)) {
            throw new OutOfOrderInnerClassException();
        }
        if (!this.myInternalName.equals(str2) || (tFindInnerClass = this.myInnersStrategy.findInnerClass(str3, this.mySource)) == null) {
            return;
        }
        this.myInnersStrategy.accept(tFindInnerClass, new StubBuildingVisitor<>(tFindInnerClass, this.myInnersStrategy, this.myResult, i, str4, z, z2));
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public RecordComponentVisitor visitRecordComponent(String str, String str2, String str3) {
        if (this.myHeaderStub == null) {
            return null;
        }
        boolean zIsVarArgComponent = this.myFirstPassData.isVarArgComponent(str);
        byte bPackFlags = PsiRecordComponentStubImpl.packFlags(zIsVarArgComponent, false);
        TypeInfo typeInfoFieldType = fieldType(str2, str3);
        if (zIsVarArgComponent) {
            typeInfoFieldType = new TypeInfo(typeInfoFieldType.text, typeInfoFieldType.arrayCount, true);
        }
        PsiRecordComponentStubImpl psiRecordComponentStubImpl = new PsiRecordComponentStubImpl(this.myHeaderStub, str, typeInfoFieldType, bPackFlags);
        return new RecordComponentAnnotationCollectingVisitor(psiRecordComponentStubImpl, new PsiModifierListStubImpl(psiRecordComponentStubImpl, 0), this.myFirstPassData);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
        if (BitUtil.isSet(i, 4096) || str == null) {
            return null;
        }
        if (this.myResult.isRecord() && i == 18) {
            return null;
        }
        byte bPackFlags = PsiFieldStubImpl.packFlags(BitUtil.isSet(i, 16384), BitUtil.isSet(i, 131072), false, false);
        TypeInfo typeInfoFieldType = fieldType(str2, str3);
        PsiFieldStubImpl psiFieldStubImpl = new PsiFieldStubImpl(this.myResult, str, typeInfoFieldType, constToString(obj, typeInfoFieldType.text, false, this.myFirstPassData), bPackFlags);
        return new FieldAnnotationCollectingVisitor(psiFieldStubImpl, new PsiModifierListStubImpl(psiFieldStubImpl, packFieldFlags(i)), this.myFirstPassData);
    }

    private TypeInfo fieldType(String str, String str2) {
        String typeString;
        if (str2 != null) {
            try {
                typeString = SignatureParsing.parseTypeString(new StringCharacterIterator(str2), this.myFirstPassData);
            } catch (ClsFormatException e) {
                Logger logger = LOG;
                if (logger.isDebugEnabled()) {
                    logger.debug("source=" + this.mySource + " signature=" + str2, e);
                }
                typeString = null;
            }
        } else {
            typeString = null;
        }
        if (typeString == null) {
            typeString = toJavaType(Type.getType(str), this.myFirstPassData);
        }
        return TypeInfo.fromString(typeString, false);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        boolean z;
        int i2;
        int i3;
        String name2 = str;
        MethodInfo methodSignature = null;
        if (BitUtil.isSet(i, 4096) || name2 == null || MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME.equals(name2)) {
            return null;
        }
        boolean zEquals = "<init>".equals(name2);
        if (zEquals && this.myAnonymousInner) {
            return null;
        }
        boolean zIsEnum = this.myResult.isEnum();
        if (zIsEnum) {
            if (CompoundEntry.NAME_values.equals(name2) && str2.startsWith("()")) {
                return null;
            }
            if ("valueOf".equals(name2) && str2.startsWith("(Ljava/lang/String;)")) {
                return null;
            }
        }
        if (this.myFirstPassData.isSyntheticRecordMethod(name2, str2)) {
            return null;
        }
        boolean zIsSet = BitUtil.isSet(i, 131072);
        boolean zIsSet2 = BitUtil.isSet(i, 128);
        boolean zIsSet3 = BitUtil.isSet(i, 8);
        byte bPackFlags = PsiMethodStubImpl.packFlags(zEquals, this.myResult.isAnnotationType(), zIsSet2, zIsSet, false, false);
        if (zEquals) {
            name2 = this.myResult.getName();
        }
        String str4 = name2;
        int i4 = 0;
        if (str3 != null) {
            try {
                methodSignature = parseMethodSignature(str3, strArr);
                z = true;
            } catch (ClsFormatException e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("source=" + this.mySource + " signature=" + str3, e);
                }
                z = false;
            }
        } else {
            z = false;
        }
        if (methodSignature == null) {
            methodSignature = parseMethodDescription(str2, strArr);
        }
        MethodInfo methodInfo = methodSignature;
        PsiMethodStubImpl psiMethodStubImpl = new PsiMethodStubImpl(this.myResult, str4, TypeInfo.fromString(methodInfo.returnType, false), bPackFlags, null);
        PsiModifierListStubImpl psiModifierListStubImpl = new PsiModifierListStubImpl(psiMethodStubImpl, packMethodFlags(i, this.myResult.isInterface()));
        new PsiTypeParameterListStubImpl(psiMethodStubImpl);
        boolean z2 = zEquals && zIsEnum;
        boolean z3 = zEquals && !zIsEnum && isInner() && !isGroovyClosure(str4);
        List listSubList = methodInfo.argTypes;
        if (!z) {
            if (z2 && listSubList.size() >= 2 && CommonClassNames.JAVA_LANG_STRING.equals(listSubList.get(0)) && "int".equals(listSubList.get(1))) {
                listSubList = listSubList.subList(2, listSubList.size());
            } else if (z3 && listSubList.size() >= 1) {
                listSubList = listSubList.subList(1, listSubList.size());
            }
        }
        PsiParameterListStubImpl psiParameterListStubImpl = new PsiParameterListStubImpl(psiMethodStubImpl);
        int size = listSubList.size();
        PsiParameterStubImpl[] psiParameterStubImplArr = new PsiParameterStubImpl[size];
        int i5 = 0;
        while (i5 < size) {
            String str5 = (String) listSubList.get(i5);
            boolean z4 = zIsSet2 && i5 == size + (-1);
            TypeInfo typeInfoFromString = TypeInfo.fromString(str5, z4);
            String[] strArr2 = parameterNames;
            PsiParameterStubImpl psiParameterStubImpl = new PsiParameterStubImpl(psiParameterListStubImpl, i5 < strArr2.length ? strArr2[i5] : "p" + (i5 + 1), typeInfoFromString, z4, true);
            psiParameterStubImplArr[i5] = psiParameterStubImpl;
            new PsiModifierListStubImpl(psiParameterStubImpl, 0);
            i5++;
        }
        newReferenceList(JavaStubElementTypes.THROWS_LIST, psiMethodStubImpl, methodInfo.throwTypes);
        if (z2) {
            i2 = 2;
        } else {
            i2 = z3 ? 1 : 0;
        }
        if (!z2) {
            if (z3) {
                i3 = 2;
            } else if (!zIsSet3) {
                i3 = 1;
            }
            return new MethodAnnotationCollectingVisitor(psiMethodStubImpl, methodInfo, psiModifierListStubImpl, psiParameterStubImplArr, i2, i3, this.myFirstPassData);
        }
        i4 = 3;
        i3 = i4;
        return new MethodAnnotationCollectingVisitor(psiMethodStubImpl, methodInfo, psiModifierListStubImpl, psiParameterStubImplArr, i2, i3, this.myFirstPassData);
    }

    private boolean isInner() {
        return (this.myParent instanceof PsiClassStub) && !BitUtil.isSet(this.myModList.getModifiersMask(), 8);
    }

    private boolean isGroovyClosure(String str) {
        PsiClassReferenceListStub psiClassReferenceListStub;
        if (str == null || !str.startsWith("_closure") || (psiClassReferenceListStub = (PsiClassReferenceListStub) this.myResult.findChildStubByType(JavaStubElementTypes.EXTENDS_LIST)) == null) {
            return false;
        }
        String[] referencedNames = psiClassReferenceListStub.getReferencedNames();
        return referencedNames.length == 1 && "groovy.lang.Closure".equals(referencedNames[0]);
    }

    private MethodInfo parseMethodSignature(String str, String[] strArr) throws ClsFormatException {
        MethodInfo methodInfo = new MethodInfo();
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(str);
        methodInfo.typeParameters = SignatureParsing.parseTypeParametersDeclaration(stringCharacterIterator, this.myFirstPassData);
        if (stringCharacterIterator.current() != '(') {
            throw new ClsFormatException();
        }
        stringCharacterIterator.next();
        if (stringCharacterIterator.current() == ')') {
            methodInfo.argTypes = ContainerUtil.emptyList();
        } else {
            methodInfo.argTypes = new SmartList();
            while (stringCharacterIterator.current() != ')' && stringCharacterIterator.current() != 65535) {
                methodInfo.argTypes.add(SignatureParsing.parseTypeString(stringCharacterIterator, this.myFirstPassData));
            }
            if (stringCharacterIterator.current() != ')') {
                throw new ClsFormatException();
            }
        }
        stringCharacterIterator.next();
        methodInfo.returnType = SignatureParsing.parseTypeString(stringCharacterIterator, this.myFirstPassData);
        methodInfo.throwTypes = null;
        while (stringCharacterIterator.current() == '^') {
            stringCharacterIterator.next();
            if (methodInfo.throwTypes == null) {
                methodInfo.throwTypes = new SmartList();
            }
            methodInfo.throwTypes.add(new TypeInfo(SignatureParsing.parseTypeString(stringCharacterIterator, this.myFirstPassData)));
        }
        if (strArr != null && (methodInfo.throwTypes == null || strArr.length > methodInfo.throwTypes.size())) {
            methodInfo.throwTypes = this.myFirstPassData.createTypes(strArr);
        }
        return methodInfo;
    }

    private MethodInfo parseMethodDescription(String str, String[] strArr) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.typeParameters = SignatureParsing.TypeParametersDeclaration.EMPTY;
        methodInfo.returnType = toJavaType(Type.getReturnType(str), this.myFirstPassData);
        methodInfo.argTypes = ContainerUtil.map(Type.getArgumentTypes(str), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return this.f$0.m6605xa8b263e8((Type) obj);
            }
        });
        methodInfo.throwTypes = this.myFirstPassData.createTypes(strArr);
        return methodInfo;
    }

    /* JADX INFO: renamed from: lambda$parseMethodDescription$3$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor, reason: not valid java name */
    /* synthetic */ String m6605xa8b263e8(Type type) {
        return toJavaType(type, this.myFirstPassData);
    }

    private static class ClassInfo {
        private List<TypeInfo> interfaces;
        private TypeInfo superType;
        private SignatureParsing.TypeParametersDeclaration typeParameters;

        private ClassInfo() {
        }
    }

    private static class MethodInfo {
        private List<String> argTypes;
        private String returnType;
        private List<TypeInfo> throwTypes;
        private SignatureParsing.TypeParametersDeclaration typeParameters;

        private MethodInfo() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class FieldAnnotationCollectingVisitor extends FieldVisitor {
        private final ClsTypeAnnotationCollector myAnnoBuilder;
        private final FirstPassData myFirstPassData;
        private final PsiModifierListStub myModList;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "modList";
            } else if (i != 2) {
                objArr[0] = "stub";
            } else {
                objArr[0] = "firstPassData";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/StubBuildingVisitor$FieldAnnotationCollectingVisitor";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private FieldAnnotationCollectingVisitor(PsiFieldStub psiFieldStub, PsiModifierListStub psiModifierListStub, FirstPassData firstPassData) {
            super(589824);
            if (psiFieldStub == null) {
                $$$reportNull$$$0(0);
            }
            if (psiModifierListStub == null) {
                $$$reportNull$$$0(1);
            }
            if (firstPassData == null) {
                $$$reportNull$$$0(2);
            }
            this.myModList = psiModifierListStub;
            this.myFirstPassData = firstPassData;
            this.myAnnoBuilder = new ClsTypeAnnotationCollector(psiFieldStub.getType(), firstPassData);
        }

        @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
        public AnnotationVisitor visitAnnotation(String str, boolean z) {
            return new AnnotationTextCollector(str, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$FieldAnnotationCollectingVisitor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                public final void consume(Object obj) {
                    this.f$0.m6608x4971b474((String) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$visitAnnotation$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor$FieldAnnotationCollectingVisitor, reason: not valid java name */
        /* synthetic */ void m6608x4971b474(String str) {
            new PsiAnnotationStubImpl(this.myModList, str);
        }

        @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
        public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
            return this.myAnnoBuilder.collect(typePath, str);
        }

        @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
        public void visitEnd() {
            this.myAnnoBuilder.install();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RecordComponentAnnotationCollectingVisitor extends RecordComponentVisitor {
        private final ClsTypeAnnotationCollector myAnnoBuilder;
        private final FirstPassData myFirstPassData;
        private final PsiModifierListStub myModList;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "modList";
            } else if (i != 2) {
                objArr[0] = "stub";
            } else {
                objArr[0] = "firstPassData";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/StubBuildingVisitor$RecordComponentAnnotationCollectingVisitor";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private RecordComponentAnnotationCollectingVisitor(PsiRecordComponentStub psiRecordComponentStub, PsiModifierListStub psiModifierListStub, FirstPassData firstPassData) {
            super(589824);
            if (psiRecordComponentStub == null) {
                $$$reportNull$$$0(0);
            }
            if (psiModifierListStub == null) {
                $$$reportNull$$$0(1);
            }
            if (firstPassData == null) {
                $$$reportNull$$$0(2);
            }
            this.myModList = psiModifierListStub;
            this.myFirstPassData = firstPassData;
            this.myAnnoBuilder = new ClsTypeAnnotationCollector(psiRecordComponentStub.getType(), firstPassData);
        }

        @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
        public AnnotationVisitor visitAnnotation(String str, boolean z) {
            return new AnnotationTextCollector(str, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$RecordComponentAnnotationCollectingVisitor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                public final void consume(Object obj) {
                    this.f$0.m6613x6c7585c2((String) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$visitAnnotation$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor$RecordComponentAnnotationCollectingVisitor, reason: not valid java name */
        /* synthetic */ void m6613x6c7585c2(String str) {
            new PsiAnnotationStubImpl(this.myModList, str);
        }

        @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
        public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
            return this.myAnnoBuilder.collect(typePath, str);
        }

        @Override // org.jetbrains.org.objectweb.asm.RecordComponentVisitor
        public void visitEnd() {
            this.myAnnoBuilder.install();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MethodAnnotationCollectingVisitor extends MethodVisitor {
        private Map<TypeInfo, ClsTypeAnnotationCollector> myAnnoBuilders;
        private final FirstPassData myFirstPassData;
        private final int myLocalVarIgnoreCount;
        private final MethodInfo myMethodInfo;
        private final PsiModifierListStub myModList;
        private final PsiMethodStub myOwner;
        private final int myParamCount;
        private int myParamIgnoreCount;
        private int myParamNameIndex;
        private final PsiParameterStubImpl[] myParamStubs;
        private int myUsedParamCount;
        private int myUsedParamSize;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "modList";
            } else if (i == 2) {
                objArr[0] = "firstPassData";
            } else if (i != 3) {
                objArr[0] = "methodInfo";
            } else {
                objArr[0] = "name";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/StubBuildingVisitor$MethodAnnotationCollectingVisitor";
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "setParameterName";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private MethodAnnotationCollectingVisitor(PsiMethodStub psiMethodStub, MethodInfo methodInfo, PsiModifierListStub psiModifierListStub, PsiParameterStubImpl[] psiParameterStubImplArr, int i, int i2, FirstPassData firstPassData) {
            super(589824);
            if (methodInfo == null) {
                $$$reportNull$$$0(0);
            }
            if (psiModifierListStub == null) {
                $$$reportNull$$$0(1);
            }
            if (firstPassData == null) {
                $$$reportNull$$$0(2);
            }
            this.myOwner = psiMethodStub;
            this.myMethodInfo = methodInfo;
            this.myModList = psiModifierListStub;
            this.myParamStubs = psiParameterStubImplArr;
            this.myParamCount = psiParameterStubImplArr.length;
            this.myLocalVarIgnoreCount = i2;
            this.myParamIgnoreCount = i;
            this.myFirstPassData = firstPassData;
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public void visitAnnotableParameterCount(int i, boolean z) {
            if (this.myParamIgnoreCount <= 0 || i != this.myParamCount) {
                return;
            }
            this.myParamIgnoreCount = 0;
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public AnnotationVisitor visitAnnotation(String str, boolean z) {
            return new AnnotationTextCollector(str, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$MethodAnnotationCollectingVisitor$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                public final void consume(Object obj) {
                    this.f$0.m6609xf580e43f((String) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$visitAnnotation$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor$MethodAnnotationCollectingVisitor, reason: not valid java name */
        /* synthetic */ void m6609xf580e43f(String str) {
            new PsiAnnotationStubImpl(this.myModList, str);
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public AnnotationVisitor visitParameterAnnotation(final int i, String str, boolean z) {
            if (i < this.myParamIgnoreCount) {
                return null;
            }
            return new AnnotationTextCollector(str, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$MethodAnnotationCollectingVisitor$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                public final void consume(Object obj) {
                    this.f$0.m6611x1bf96a2d(i, (String) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$visitParameterAnnotation$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor$MethodAnnotationCollectingVisitor, reason: not valid java name */
        /* synthetic */ void m6611x1bf96a2d(int i, String str) {
            new PsiAnnotationStubImpl(this.myOwner.findParameter(i - this.myParamIgnoreCount).getModList(), str);
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0077  */
        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
            int exceptionIndex;
            TypeInfo boundType;
            TypeReference typeReference = new TypeReference(i);
            if (typeReference.getSort() == 20) {
                boundType = this.myOwner.getReturnTypeText();
            } else if (typeReference.getSort() == 22) {
                int formalParameterIndex = typeReference.getFormalParameterIndex();
                PsiParameterStubImpl[] psiParameterStubImplArr = this.myParamStubs;
                if (formalParameterIndex < psiParameterStubImplArr.length) {
                    boundType = psiParameterStubImplArr[formalParameterIndex].getType();
                } else {
                    boundType = null;
                }
            } else if (typeReference.getSort() == 1) {
                boundType = this.myMethodInfo.typeParameters.getParameterType(typeReference);
            } else if (typeReference.getSort() == 18) {
                boundType = this.myMethodInfo.typeParameters.getBoundType(typeReference);
            } else if (typeReference.getSort() != 23 || (exceptionIndex = typeReference.getExceptionIndex()) >= this.myMethodInfo.throwTypes.size()) {
                boundType = null;
            } else {
                boundType = (TypeInfo) this.myMethodInfo.throwTypes.get(exceptionIndex);
            }
            if (boundType == null) {
                return null;
            }
            if (this.myAnnoBuilders == null) {
                this.myAnnoBuilders = new HashMap();
            }
            return this.myAnnoBuilders.computeIfAbsent(boundType, new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$MethodAnnotationCollectingVisitor$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m6612x6271169b((TypeInfo) obj);
                }
            }).collect(typePath, str);
        }

        /* JADX INFO: renamed from: lambda$visitTypeAnnotation$2$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor$MethodAnnotationCollectingVisitor, reason: not valid java name */
        /* synthetic */ ClsTypeAnnotationCollector m6612x6271169b(TypeInfo typeInfo) {
            return new ClsTypeAnnotationCollector(typeInfo, this.myFirstPassData);
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public void visitEnd() {
            Map<TypeInfo, ClsTypeAnnotationCollector> map = this.myAnnoBuilders;
            if (map != null) {
                map.values().forEach(new StubBuildingVisitor$$ExternalSyntheticLambda2());
            }
            this.myMethodInfo.typeParameters.fillInTypeParameterList(this.myOwner);
        }

        /* JADX INFO: renamed from: lambda$visitAnnotationDefault$3$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-StubBuildingVisitor$MethodAnnotationCollectingVisitor, reason: not valid java name */
        /* synthetic */ void m6610x854c189(String str) {
            ((PsiMethodStubImpl) this.myOwner).setDefaultValueText(str);
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public AnnotationVisitor visitAnnotationDefault() {
            return new AnnotationTextCollector(null, this.myFirstPassData, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.StubBuildingVisitor$MethodAnnotationCollectingVisitor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
                public final void consume(Object obj) {
                    this.f$0.m6610x854c189((String) obj);
                }
            });
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public void visitParameter(String str, int i) {
            int i2 = this.myParamNameIndex;
            this.myParamNameIndex = i2 + 1;
            int i3 = i2 - this.myParamIgnoreCount;
            if (str == null || BitUtil.isSet(i, 4096) || i3 < 0 || i3 >= this.myParamCount) {
                return;
            }
            setParameterName(str, i3);
        }

        @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
        public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
            int i2 = this.myLocalVarIgnoreCount;
            if (i >= i2) {
                int i3 = i - i2 == this.myUsedParamSize ? this.myUsedParamCount : i - i2;
                if (i3 < this.myParamCount) {
                    setParameterName(str, i3);
                    this.myUsedParamCount = i3 + 1;
                    this.myUsedParamSize += ("D".equals(str2) || "J".equals(str2)) ? 2 : 1;
                }
            }
        }

        private void setParameterName(String str, int i) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            if (ClsParsingUtil.isJavaIdentifier(str, LanguageLevel.HIGHEST)) {
                this.myParamStubs[i].setName(str);
            }
        }
    }

    static String constToString(Object obj, String str, boolean z, Function<? super String, String> function) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return "\"" + StringUtil.escapeStringCharacters((String) obj) + "\"";
        }
        if ((obj instanceof Boolean) || (obj instanceof Short) || (obj instanceof Byte)) {
            return obj.toString();
        }
        if (obj instanceof Character) {
            return FormatHelper.QUOTE + StringUtil.escapeCharCharacters(obj.toString()) + FormatHelper.QUOTE;
        }
        if (obj instanceof Long) {
            return obj.toString() + 'L';
        }
        if (obj instanceof Integer) {
            if ("boolean".equals(str)) {
                if (obj.equals(0)) {
                    return "false";
                }
                if (obj.equals(1)) {
                    return "true";
                }
            }
            if (PsiKeyword.CHAR.equals(str)) {
                return FormatHelper.QUOTE + StringUtil.escapeCharCharacters(String.valueOf((char) ((Integer) obj).intValue())) + FormatHelper.QUOTE;
            }
            return obj.toString();
        }
        if (obj instanceof Double) {
            double dDoubleValue = ((Double) obj).doubleValue();
            if (Double.isInfinite(dDoubleValue)) {
                return dDoubleValue > 0.0d ? "1.0 / 0.0" : "-1.0 / 0.0";
            }
            if (Double.isNaN(dDoubleValue)) {
                return "0.0d / 0.0";
            }
            return Double.toString(dDoubleValue);
        }
        if (obj instanceof Float) {
            float fFloatValue = ((Float) obj).floatValue();
            if (Float.isInfinite(fFloatValue)) {
                return fFloatValue > 0.0f ? "1.0f / 0.0" : "-1.0f / 0.0";
            }
            if (Float.isNaN(fFloatValue)) {
                return "0.0f / 0.0";
            }
            return Float.toString(fFloatValue) + 'f';
        }
        if (obj.getClass().isArray()) {
            StringBuilder sb = new StringBuilder("{");
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(constToString(Array.get(obj, i), str, z, function));
            }
            sb.append('}');
            return sb.toString();
        }
        if (z && (obj instanceof Type)) {
            return toJavaType((Type) obj, function) + ".class";
        }
        return null;
    }

    static String toJavaType(Type type, Function<? super String, String> function) {
        int dimensions;
        if (function == null) {
            $$$reportNull$$$0(2);
        }
        if (type.getSort() == 9) {
            dimensions = type.getDimensions();
            type = type.getElementType();
        } else {
            dimensions = 0;
        }
        String strFun = type.getSort() == 10 ? function.fun(type.getInternalName()) : type.getClassName();
        return dimensions > 0 ? strFun + StringUtil.repeat(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, dimensions) : strFun;
    }

    static /* synthetic */ String lambda$static$4(String str) {
        if (str.indexOf(36) >= 0) {
            StringBuilder sb = new StringBuilder(str);
            boolean z = false;
            for (int iLastIndexOf = str.lastIndexOf(47) + 2; iLastIndexOf < sb.length(); iLastIndexOf++) {
                if (sb.charAt(iLastIndexOf) == '$' && iLastIndexOf < sb.length() - 1 && sb.charAt(iLastIndexOf + 1) != '$') {
                    sb.setCharAt(iLastIndexOf, '.');
                    z = true;
                }
            }
            if (z) {
                str = sb.toString();
            }
        }
        return str.replace('/', '.');
    }

    public static AnnotationVisitor getAnnotationTextCollector(String str, Consumer<? super String> consumer) {
        return new AnnotationTextCollector(str, GUESSING_MAPPER, consumer);
    }
}
