package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.util.cls.ClsFormatException;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Methods.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\b\b&\u0018\u0000 (2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001(B9\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R)\u0010\u0017\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\u0004\u0012\u00020\u00140\u00188VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\"R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b'\u0010&¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", "access", "", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "valueParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaValueParameter;", "typeParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(ILorg/jetbrains/kotlin/load/java/structure/JavaClass;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/name/Name;)V", "getAccess", "()I", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "Lorg/jetbrains/kotlin/name/FqName;", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "getContainingClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "isFromSource", "", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getTypeParameters", "()Ljava/util/List;", "getValueParameters", "Companion", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BinaryJavaMethodBase implements JavaMember, MutableJavaAnnotationOwner, BinaryJavaModifierListOwner {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int access;
    private final Collection<JavaAnnotation> annotations;

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final JavaClass containingClass;
    private final Name name;
    private final List<JavaTypeParameter> typeParameters;
    private final List<BinaryJavaValueParameter> valueParameters;

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BinaryJavaMethodBase(int i, JavaClass containingClass, List<BinaryJavaValueParameter> valueParameters, List<? extends JavaTypeParameter> typeParameters, Name name2) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.access = i;
        this.containingClass = containingClass;
        this.valueParameters = valueParameters;
        this.typeParameters = typeParameters;
        this.name = name2;
        this.annotations = new SmartList();
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaModifierListOwner
    public int getAccess() {
        return this.access;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMember
    public JavaClass getContainingClass() {
        return this.containingClass;
    }

    public final List<BinaryJavaValueParameter> getValueParameters() {
        return this.valueParameters;
    }

    public final List<JavaTypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }

    /* JADX INFO: compiled from: Methods.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JL\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J \u0010\u0016\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0010H\u0002¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "name", "", "access", "", "desc", "signature", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "parentContext", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "parseMethodDescription", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase$Companion$MethodInfo;", "context", "parseMethodSignature", "MethodInfo", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: Methods.kt */
        @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaMethodBase$Companion$MethodInfo;", "", "returnType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "valueParameterTypes", "(Lorg/jetbrains/kotlin/load/java/structure/JavaType;Ljava/util/List;Ljava/util/List;)V", "getReturnType", "()Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "getTypeParameters", "()Ljava/util/List;", "getValueParameterTypes", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class MethodInfo {
            private final JavaType returnType;
            private final List<JavaTypeParameter> typeParameters;
            private final List<JavaType> valueParameterTypes;

            /* JADX WARN: Multi-variable type inference failed */
            public MethodInfo(JavaType returnType, List<? extends JavaTypeParameter> typeParameters, List<? extends JavaType> valueParameterTypes) {
                Intrinsics.checkNotNullParameter(returnType, "returnType");
                Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
                Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
                this.returnType = returnType;
                this.typeParameters = typeParameters;
                this.valueParameterTypes = valueParameterTypes;
            }

            public final JavaType getReturnType() {
                return this.returnType;
            }

            public final List<JavaTypeParameter> getTypeParameters() {
                return this.typeParameters;
            }

            public final List<JavaType> getValueParameterTypes() {
                return this.valueParameterTypes;
            }
        }

        private Companion() {
        }

        /* JADX WARN: Code duplicated, block: B:23:0x008f  */
        /* JADX WARN: Code duplicated, block: B:26:0x00b7  */
        /* JADX WARN: Code duplicated, block: B:30:0x00da  */
        /* JADX WARN: Code duplicated, block: B:32:0x00e2  */
        /* JADX WARN: Code duplicated, block: B:38:0x00f2  */
        /* JADX WARN: Code duplicated, block: B:42:0x0107  */
        /* JADX WARN: Code duplicated, block: B:43:0x0113  */
        /* JADX WARN: Code duplicated, block: B:46:0x0135  */
        /* JADX WARN: Code duplicated, block: B:47:0x0137 A[DONT_INVERT] */
        /* JADX WARN: Code duplicated, block: B:48:0x0139  */
        /* JADX WARN: Code duplicated, block: B:49:0x013c  */
        public final Pair<JavaMember, MethodVisitor> create(String name2, int access, String desc, String signature, JavaClass containingClass, ClassifierResolutionContext parentContext, BinaryClassSignatureParser signatureParser) {
            MethodInfo methodInfo;
            ArrayList arrayList;
            int i;
            ArrayList arrayList2;
            BinaryJavaMethod binaryJavaMethod;
            int i2;
            boolean z;
            MethodInfo methodSignature;
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            Intrinsics.checkNotNullParameter(containingClass, "containingClass");
            Intrinsics.checkNotNullParameter(parentContext, "parentContext");
            Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
            boolean zAreEqual = Intrinsics.areEqual("<init>", name2);
            boolean zIsSet = CommonMixinsKt.isSet(access, 128);
            boolean z2 = (!zAreEqual || containingClass.getOuterClass() == null || containingClass.mo7267isStatic()) ? false : true;
            boolean z3 = containingClass.mo7263isEnum() && zAreEqual;
            MethodInfo methodDescription = parseMethodDescription(desc, parentContext, signatureParser);
            if (z3) {
                methodInfo = new MethodInfo(methodDescription.getReturnType(), methodDescription.getTypeParameters(), CollectionsKt.drop(methodDescription.getValueParameterTypes(), 2));
            } else {
                if (z2) {
                    methodInfo = new MethodInfo(methodDescription.getReturnType(), methodDescription.getTypeParameters(), CollectionsKt.drop(methodDescription.getValueParameterTypes(), 1));
                }
                if (signature != null) {
                    ClassifierResolutionContext classifierResolutionContextCopyForMember$resolution_common_jvm = parentContext.copyForMember$resolution_common_jvm();
                    methodSignature = parseMethodSignature(signature, signatureParser, classifierResolutionContextCopyForMember$resolution_common_jvm);
                    classifierResolutionContextCopyForMember$resolution_common_jvm.addTypeParameters$resolution_common_jvm(methodSignature.getTypeParameters());
                    if (methodSignature.getValueParameterTypes().size() >= methodDescription.getValueParameterTypes().size()) {
                        methodDescription = methodSignature;
                    }
                }
                List<JavaType> valueParameterTypes = methodDescription.getValueParameterTypes();
                int size = valueParameterTypes.size();
                List<JavaType> list = valueParameterTypes;
                arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                i = 0;
                for (Object obj : list) {
                    int i3 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    JavaType javaType = (JavaType) obj;
                    if (zIsSet || i != size - 1) {
                        z = false;
                    } else {
                        z = true;
                    }
                    arrayList.add(new BinaryJavaValueParameter(javaType, z));
                    i = i3;
                }
                arrayList2 = arrayList;
                if (zAreEqual) {
                    binaryJavaMethod = new BinaryJavaConstructor(access, containingClass, arrayList2, methodDescription.getTypeParameters());
                } else {
                    List<JavaTypeParameter> typeParameters = methodDescription.getTypeParameters();
                    Name nameIdentifier = Name.identifier(name2);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    binaryJavaMethod = new BinaryJavaMethod(access, containingClass, arrayList2, typeParameters, nameIdentifier, methodDescription.getReturnType());
                }
                BinaryJavaMethodBase binaryJavaMethodBase = binaryJavaMethod;
                if (z3) {
                    i2 = 2;
                } else if (z2) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                return TuplesKt.to(binaryJavaMethodBase, new AnnotationsAndParameterCollectorMethodVisitor(binaryJavaMethodBase, parentContext, signatureParser, i2, Type.getArgumentTypes(desc).length));
            }
            methodDescription = methodInfo;
            if (signature != null) {
                ClassifierResolutionContext classifierResolutionContextCopyForMember$resolution_common_jvm2 = parentContext.copyForMember$resolution_common_jvm();
                methodSignature = parseMethodSignature(signature, signatureParser, classifierResolutionContextCopyForMember$resolution_common_jvm2);
                classifierResolutionContextCopyForMember$resolution_common_jvm2.addTypeParameters$resolution_common_jvm(methodSignature.getTypeParameters());
                if (methodSignature.getValueParameterTypes().size() >= methodDescription.getValueParameterTypes().size()) {
                    methodDescription = methodSignature;
                }
            }
            List<JavaType> valueParameterTypes2 = methodDescription.getValueParameterTypes();
            int size2 = valueParameterTypes2.size();
            List<JavaType> list2 = valueParameterTypes2;
            arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            i = 0;
            while (r3.hasNext()) {
                int i4 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                JavaType javaType2 = (JavaType) obj;
                if (zIsSet) {
                    z = false;
                } else {
                    z = false;
                }
                arrayList.add(new BinaryJavaValueParameter(javaType2, z));
                i = i4;
            }
            arrayList2 = arrayList;
            if (zAreEqual) {
                binaryJavaMethod = new BinaryJavaConstructor(access, containingClass, arrayList2, methodDescription.getTypeParameters());
            } else {
                List<JavaTypeParameter> typeParameters2 = methodDescription.getTypeParameters();
                Name nameIdentifier2 = Name.identifier(name2);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                binaryJavaMethod = new BinaryJavaMethod(access, containingClass, arrayList2, typeParameters2, nameIdentifier2, methodDescription.getReturnType());
            }
            BinaryJavaMethodBase binaryJavaMethodBase2 = binaryJavaMethod;
            if (z3) {
                i2 = 2;
            } else if (z2) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            return TuplesKt.to(binaryJavaMethodBase2, new AnnotationsAndParameterCollectorMethodVisitor(binaryJavaMethodBase2, parentContext, signatureParser, i2, Type.getArgumentTypes(desc).length));
        }

        private final MethodInfo parseMethodDescription(String desc, ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser) {
            Type returnType = Type.getReturnType(desc);
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            JavaType javaTypeMapAsmType = signatureParser.mapAsmType(returnType, context);
            Type[] argumentTypes = Type.getArgumentTypes(desc);
            Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
            Type[] typeArr = argumentTypes;
            ArrayList arrayList = new ArrayList(typeArr.length);
            for (Type type : typeArr) {
                Intrinsics.checkNotNull(type);
                arrayList.add(signatureParser.mapAsmType(type, context));
            }
            return new MethodInfo(javaTypeMapAsmType, CollectionsKt.emptyList(), arrayList);
        }

        private final MethodInfo parseMethodSignature(String signature, BinaryClassSignatureParser signatureParser, ClassifierResolutionContext context) throws ClsFormatException {
            List listCompact;
            StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(signature);
            StringCharacterIterator stringCharacterIterator2 = stringCharacterIterator;
            List<JavaTypeParameter> typeParametersDeclaration = signatureParser.parseTypeParametersDeclaration(stringCharacterIterator2, context);
            if (stringCharacterIterator.current() != '(') {
                throw new ClsFormatException();
            }
            stringCharacterIterator.next();
            if (stringCharacterIterator.current() == ')') {
                listCompact = CollectionsKt.emptyList();
            } else {
                ArrayList arrayList = new ArrayList();
                while (stringCharacterIterator.current() != ')' && stringCharacterIterator.current() != 65535) {
                    arrayList.add(signatureParser.parseTypeString(stringCharacterIterator2, context));
                }
                if (stringCharacterIterator.current() != ')') {
                    throw new ClsFormatException();
                }
                listCompact = org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList);
            }
            stringCharacterIterator.next();
            return new MethodInfo(signatureParser.parseTypeString(stringCharacterIterator2, context), typeParametersDeclaration, listCompact);
        }
    }
}
