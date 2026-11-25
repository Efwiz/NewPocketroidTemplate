package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.reandroid.arsc.value.CompoundEntry;
import gnu.trove.THashMap;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaElementsKt;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMember;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner;
import org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.RecordComponentVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: compiled from: BinaryJavaClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008c\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004BE\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013J\u0012\u0010d\u001a\u0004\u0018\u00010\u00102\u0006\u0010K\u001a\u000207H\u0016J\u001a\u0010d\u001a\u0004\u0018\u00010\u00102\u0006\u0010K\u001a\u0002072\b\u0010e\u001a\u0004\u0018\u00010\u0012J\b\u0010f\u001a\u00020;H\u0016J\u0010\u0010g\u001a\u00020;2\u0006\u0010h\u001a\u00020iH\u0016J\u0010\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020JH\u0002J\u001c\u0010m\u001a\u0004\u0018\u00010n2\b\u0010o\u001a\u0004\u0018\u00010n2\u0006\u0010p\u001a\u00020qH\u0002JK\u0010r\u001a\u00020k2\u0006\u0010s\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010K\u001a\u00020J2\b\u0010l\u001a\u0004\u0018\u00010J2\b\u0010t\u001a\u0004\u0018\u00010J2\u0010\u0010u\u001a\f\u0012\u0006\b\u0001\u0012\u00020J\u0018\u00010vH\u0016¢\u0006\u0002\u0010wJ\u0018\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020J2\u0006\u0010{\u001a\u00020;H\u0016J\b\u0010|\u001a\u00020kH\u0016J6\u0010}\u001a\u0004\u0018\u00010~2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010K\u001a\u00020J2\u0006\u0010z\u001a\u00020J2\b\u0010l\u001a\u0004\u0018\u00010J2\b\u0010o\u001a\u0004\u0018\u00010nH\u0016J.\u0010\u007f\u001a\u00020k2\u0006\u0010K\u001a\u00020J2\t\u0010\u0080\u0001\u001a\u0004\u0018\u00010J2\t\u0010\u0081\u0001\u001a\u0004\u0018\u00010J2\u0006\u0010\r\u001a\u00020\u000eH\u0016JG\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0083\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010K\u001a\u00020J2\u0006\u0010z\u001a\u00020J2\b\u0010l\u001a\u0004\u0018\u00010J2\u0011\u0010\u0084\u0001\u001a\f\u0012\u0006\b\u0001\u0012\u00020J\u0018\u00010vH\u0016¢\u0006\u0003\u0010\u0085\u0001J\u0014\u0010\u0086\u0001\u001a\u00020k2\t\u0010\u0087\u0001\u001a\u0004\u0018\u00010JH\u0016J'\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u00012\u0006\u0010K\u001a\u00020J2\u0007\u0010\u008a\u0001\u001a\u00020J2\b\u0010l\u001a\u0004\u0018\u00010JH\u0016J3\u0010\u008b\u0001\u001a\u0004\u0018\u00010y2\u0007\u0010\u008c\u0001\u001a\u00020\u000e2\n\u0010\u008d\u0001\u001a\u0005\u0018\u00010\u008e\u00012\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010J2\u0006\u0010{\u001a\u00020;H\u0016J\r\u0010\u008f\u0001\u001a\u000202*\u00020JH\u0002R\u001a\u0010\r\u001a\u00020\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR)\u0010\u001d\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u001a0\u001e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 R$\u0010#\u001a\u0012\u0012\u0004\u0012\u00020%0$j\b\u0012\u0004\u0012\u00020%`&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R$\u0010+\u001a\u0012\u0012\u0004\u0012\u00020,0$j\b\u0012\u0004\u0012\u00020,`&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010(R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u001a\u00100\u001a\b\u0012\u0004\u0012\u000202018BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b3\u00104R\u001a\u00105\u001a\b\u0012\u0004\u0012\u000207068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010<R\u0014\u0010=\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010<R\u0014\u0010>\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010<R\u0014\u0010?\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b?\u0010<R\u0014\u0010@\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010<R\u0014\u0010A\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010<R\u0016\u0010B\u001a\u0004\u0018\u00010C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER$\u0010F\u001a\u0012\u0012\u0004\u0012\u00020G0$j\b\u0012\u0004\u0012\u00020G`&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bH\u0010(R\u000e\u0010I\u001a\u00020JX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010K\u001a\u0002078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bL\u0010MR\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bN\u0010OR\u001a\u0010P\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020\u000e0QX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010R\u001a\u0012\u0012\u0004\u0012\u0002020$j\b\u0012\u0004\u0012\u000202`&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bS\u0010(R$\u0010T\u001a\u0012\u0012\u0004\u0012\u00020U0$j\b\u0012\u0004\u0012\u00020U`&X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bV\u0010(R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010W\u001a\u0004\u0018\u0001028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bX\u0010YR \u0010Z\u001a\b\u0012\u0004\u0012\u00020201X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b[\u00104\"\u0004\b\\\u0010]R \u0010^\u001a\b\u0012\u0004\u0012\u00020_01X\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b`\u00104\"\u0004\ba\u0010]R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010c¨\u0006\u0090\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaClass;", "Lorg/jetbrains/org/objectweb/asm/ClassVisitor;", "Lorg/jetbrains/kotlin/load/java/structure/impl/VirtualFileBoundJavaClass;", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryJavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/MutableJavaAnnotationOwner;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "signatureParser", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "access", "", "outerClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "classContent", "", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;ILorg/jetbrains/kotlin/load/java/structure/JavaClass;[B)V", "getAccess", "()I", "setAccess", "(I)V", "annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getAnnotations", "()Ljava/util/Collection;", "annotationsByFqName", "", "getAnnotationsByFqName", "()Ljava/util/Map;", "annotationsByFqName$delegate", "Lkotlin/Lazy;", "constructors", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "Lkotlin/collections/ArrayList;", "getConstructors", "()Ljava/util/ArrayList;", "getContext$resolution_common_jvm", "()Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "implementedInterfaces", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getImplementedInterfaces", "()Ljava/util/List;", "innerClassNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "()Ljava/util/Set;", "isAnnotationType", "", "()Z", "isEnum", "isFromSource", "isInterface", "isRecord", "isSealed", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "myInternalName", "", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "ownInnerClassNameToAccess", "", "permittedTypes", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "superclass", "getSuperclass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "supertypes", "getSupertypes", "setSupertypes", "(Ljava/util/List;)V", "typeParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "setTypeParameters", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "findInnerClass", "classFileContent", "hasDefaultConstructor", "isFromSourceCodeInScope", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "parseClassSignature", "", "signature", "processValue", "", "value", "fieldType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "visit", "version", "superName", Namer.METADATA_SUPERTYPES, "", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "visitAnnotation", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "desc", "visible", "visitEnd", "visitField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "visitInnerClass", "outerName", "innerName", "visitMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "exceptions", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "visitPermittedSubclass", "permittedSubclass", "visitRecordComponent", "Lorg/jetbrains/org/objectweb/asm/RecordComponentVisitor;", "descriptor", "visitTypeAnnotation", "typeRef", "typePath", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "convertInternalNameToClassifierType", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryJavaClass extends ClassVisitor implements MutableJavaAnnotationOwner, VirtualFileBoundJavaClass, BinaryJavaModifierListOwner {
    private int access;
    private final Collection<JavaAnnotation> annotations;

    /* JADX INFO: renamed from: annotationsByFqName$delegate, reason: from kotlin metadata */
    private final Lazy annotationsByFqName;
    private final ArrayList<JavaConstructor> constructors;
    private final ClassifierResolutionContext context;
    private final ArrayList<JavaField> fields;
    private final FqName fqName;
    private final ArrayList<JavaMethod> methods;
    private String myInternalName;
    private final JavaClass outerClass;
    private final Map<Name, Integer> ownInnerClassNameToAccess;
    private final ArrayList<JavaClassifierType> permittedTypes;
    private final ArrayList<JavaRecordComponent> recordComponents;
    private final BinaryClassSignatureParser signatureParser;
    public List<? extends JavaClassifierType> supertypes;
    public List<? extends JavaTypeParameter> typeParameters;
    private final VirtualFile virtualFile;

    /* JADX INFO: compiled from: BinaryJavaClass.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PrimitiveType.values().length];
            try {
                iArr[PrimitiveType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PrimitiveType.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor */
    public boolean mo7259hasDefaultConstructor() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public boolean isFromSourceCodeInScope(SearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return false;
    }

    public /* synthetic */ BinaryJavaClass(VirtualFile virtualFile, FqName fqName, ClassifierResolutionContext classifierResolutionContext, BinaryClassSignatureParser binaryClassSignatureParser, int i, JavaClass javaClass, byte[] bArr, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(virtualFile, fqName, classifierResolutionContext, binaryClassSignatureParser, (i2 & 16) != 0 ? 0 : i, javaClass, (i2 & 64) != 0 ? null : bArr);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public VirtualFile getVirtualFile() {
        return this.virtualFile;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        return this.fqName;
    }

    /* JADX INFO: renamed from: getContext$resolution_common_jvm, reason: from getter */
    public final ClassifierResolutionContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaModifierListOwner
    public int getAccess() {
        return this.access;
    }

    public void setAccess(int i) {
        this.access = i;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass getOuterClass() {
        return this.outerClass;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BinaryJavaClass(VirtualFile virtualFile, FqName fqName, ClassifierResolutionContext context, BinaryClassSignatureParser signatureParser, int i, JavaClass javaClass, byte[] bArr) {
        super(589824);
        Intrinsics.checkNotNullParameter(virtualFile, "virtualFile");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(signatureParser, "signatureParser");
        this.virtualFile = virtualFile;
        this.fqName = fqName;
        this.context = context;
        this.signatureParser = signatureParser;
        this.access = i;
        this.outerClass = javaClass;
        this.annotations = new SmartList();
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.constructors = new ArrayList<>();
        this.recordComponents = new ArrayList<>();
        this.annotationsByFqName = JavaElementsKt.buildLazyValueForMap(this);
        this.ownInnerClassNameToAccess = new THashMap();
        this.permittedTypes = new ArrayList<>();
        try {
            if (bArr == null) {
                bArr = getVirtualFile().contentsToByteArray();
                Intrinsics.checkNotNullExpressionValue(bArr, "contentsToByteArray(...)");
            }
            new ClassReader(bArr).accept(this, 5);
        } catch (Throwable th) {
            throw new IllegalStateException("Could not read class: " + getVirtualFile(), th);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MutableJavaAnnotationOwner, org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        List list = this.typeParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeParameters");
        return null;
    }

    public void setTypeParameters(List<? extends JavaTypeParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.typeParameters = list;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public List<JavaClassifierType> getSupertypes() {
        List list = this.supertypes;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("supertypes");
        return null;
    }

    public void setSupertypes(List<? extends JavaClassifierType> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.supertypes = list;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods */
    public ArrayList<JavaMethod> mo7255getMethods() {
        return this.methods;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields */
    public ArrayList<JavaField> mo7254getFields() {
        return this.fields;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors */
    public ArrayList<JavaConstructor> mo7253getConstructors() {
        return this.constructors;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents */
    public ArrayList<JavaRecordComponent> mo7257getRecordComponents() {
        return this.recordComponents;
    }

    private final JavaClassifierType getSuperclass() {
        return (JavaClassifierType) CollectionsKt.firstOrNull((List) getSupertypes());
    }

    private final List<JavaClassifierType> getImplementedInterfaces() {
        return CollectionsKt.drop(getSupertypes(), 1);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.MapBasedJavaAnnotationOwner
    public Map<FqName, JavaAnnotation> getAnnotationsByFqName() {
        return (Map) this.annotationsByFqName.getValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Set<Name> getInnerClassNames() {
        return this.ownInnerClassNameToAccess.keySet();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameShortName = getFqName().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return nameShortName;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface */
    public boolean mo7265isInterface() {
        return isSet(512);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType */
    public boolean mo7261isAnnotationType() {
        return isSet(8192);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum */
    public boolean mo7263isEnum() {
        return isSet(16384);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isRecord */
    public boolean mo7266isRecord() {
        return isSet(65536);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public boolean isSealed() {
        return !getPermittedTypes().isEmpty();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public ArrayList<JavaClassifierType> getPermittedTypes() {
        return this.permittedTypes;
    }

    private static final JavaType visitTypeAnnotation$getTargetType(TypePath typePath, JavaType javaType) {
        return typePath != null ? BinaryJavaAnnotation.INSTANCE.computeTargetType$resolution_common_jvm(javaType, typePath) : javaType;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        JavaAnnotationOwner javaAnnotationOwnerVisitTypeAnnotation$getTargetType;
        JavaClassifierType superclass;
        if (descriptor == null) {
            return null;
        }
        TypeReference typeReference = new TypeReference(typeRef);
        int sort = typeReference.getSort();
        if (sort == 0) {
            javaAnnotationOwnerVisitTypeAnnotation$getTargetType = mo7258getTypeParameters().get(typeReference.getTypeParameterIndex());
        } else if (sort == 16) {
            if (typeReference.getSuperTypeIndex() == -1) {
                superclass = getSuperclass();
                Intrinsics.checkNotNull(superclass);
            } else {
                superclass = getImplementedInterfaces().get(typeReference.getSuperTypeIndex());
            }
            javaAnnotationOwnerVisitTypeAnnotation$getTargetType = visitTypeAnnotation$getTargetType(typePath, superclass);
        } else {
            if (sort != 17) {
                return null;
            }
            javaAnnotationOwnerVisitTypeAnnotation$getTargetType = visitTypeAnnotation$getTargetType(typePath, BinaryJavaAnnotation.INSTANCE.computeTypeParameterBound$resolution_common_jvm(mo7258getTypeParameters(), typeReference));
        }
        if (javaAnnotationOwnerVisitTypeAnnotation$getTargetType instanceof MutableJavaAnnotationOwner) {
            return BinaryJavaAnnotation.INSTANCE.addAnnotation((MutableJavaAnnotationOwner) javaAnnotationOwnerVisitTypeAnnotation$getTargetType, descriptor, this.context, this.signatureParser, true);
        }
        return null;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitEnd() {
        mo7255getMethods().trimToSize();
        mo7254getFields().trimToSize();
        mo7253getConstructors().trimToSize();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if (CommonMixinsKt.isSet(access, 4096) || CommonMixinsKt.isSet(access, 64) || Intrinsics.areEqual(name2, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
            return null;
        }
        if (mo7263isEnum()) {
            if (Intrinsics.areEqual(name2, CompoundEntry.NAME_values) && StringsKt.startsWith$default(desc, "()", false, 2, (Object) null)) {
                return null;
            }
            if (Intrinsics.areEqual(name2, "valueOf") && StringsKt.startsWith$default(desc, "(Ljava/lang/String;)", false, 2, (Object) null)) {
                return null;
            }
        }
        Pair<JavaMember, MethodVisitor> pairCreate = BinaryJavaMethodBase.INSTANCE.create(name2, access, desc, signature, this, this.context.copyForMember$resolution_common_jvm(), this.signatureParser);
        JavaMember javaMemberComponent1 = pairCreate.component1();
        MethodVisitor methodVisitorComponent2 = pairCreate.component2();
        if (javaMemberComponent1 instanceof JavaMethod) {
            mo7255getMethods().add(javaMemberComponent1);
        } else {
            if (!(javaMemberComponent1 instanceof JavaConstructor)) {
                throw new IllegalStateException(("Unexpected member: " + javaMemberComponent1.getClass()).toString());
            }
            mo7253getConstructors().add(javaMemberComponent1);
        }
        return methodVisitorComponent2;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitInnerClass(String name2, String outerName, String innerName, int access) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (CommonMixinsKt.isSet(access, 4096) || innerName == null || outerName == null || !Intrinsics.areEqual(name2, outerName + '$' + innerName)) {
            return;
        }
        this.context.addInnerClass$resolution_common_jvm(name2, outerName, innerName);
        String str = this.myInternalName;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("myInternalName");
            str = null;
        }
        if (Intrinsics.areEqual(str, outerName)) {
            Map<Name, Integer> map = this.ownInnerClassNameToAccess;
            Name shortClassName = this.context.mapInternalNameToClassId$resolution_common_jvm(name2).getShortClassName();
            Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
            map.put(shortClassName, Integer.valueOf(access));
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
        Intrinsics.checkNotNullParameter(name2, "name");
        setAccess(getAccess() | access);
        this.myInternalName = name2;
        if (signature != null) {
            parseClassSignature(signature);
            return;
        }
        setTypeParameters(CollectionsKt.emptyList());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = arrayList;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, superName != null ? convertInternalNameToClassifierType(superName) : null);
        if (interfaces != null) {
            for (String str : interfaces) {
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList2, convertInternalNameToClassifierType(str));
            }
        }
        setSupertypes(arrayList);
    }

    private final void parseClassSignature(String signature) {
        StringCharacterIterator stringCharacterIterator = new StringCharacterIterator(signature);
        StringCharacterIterator stringCharacterIterator2 = stringCharacterIterator;
        List<JavaTypeParameter> typeParametersDeclaration = this.signatureParser.parseTypeParametersDeclaration(stringCharacterIterator2, this.context);
        this.context.addTypeParameters$resolution_common_jvm(typeParametersDeclaration);
        setTypeParameters(typeParametersDeclaration);
        SmartList smartList = new SmartList();
        SmartList smartList2 = smartList;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(smartList2, this.signatureParser.parseClassifierRefSignature(stringCharacterIterator2, this.context));
        while (stringCharacterIterator.current() != 65535) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(smartList2, this.signatureParser.parseClassifierRefSignature(stringCharacterIterator2, this.context));
        }
        setSupertypes(smartList);
    }

    private final JavaClassifierType convertInternalNameToClassifierType(final String str) {
        return new PlainJavaClassifierType(new Function0<ClassifierResolutionContext.Result>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaClass.convertInternalNameToClassifierType.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassifierResolutionContext.Result invoke() {
                return BinaryJavaClass.this.getContext().resolveByInternalName$resolution_common_jvm(str);
            }
        }, CollectionsKt.emptyList());
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public FieldVisitor visitField(int access, String name2, String desc, String signature, Object value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if (CommonMixinsKt.isSet(access, 4096)) {
            return null;
        }
        BinaryClassSignatureParser binaryClassSignatureParser = this.signatureParser;
        if (signature != null) {
            desc = signature;
        }
        JavaType typeString = binaryClassSignatureParser.parseTypeString(new StringCharacterIterator(desc), this.context);
        Object objProcessValue = processValue(value, typeString);
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        BinaryJavaField binaryJavaField = new BinaryJavaField(nameIdentifier, access, this, CommonMixinsKt.isSet(access, 16384), typeString, objProcessValue);
        mo7254getFields().add(binaryJavaField);
        return new AnnotationsCollectorFieldVisitor(binaryJavaField, this.context, this.signatureParser);
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public RecordComponentVisitor visitRecordComponent(String name2, String descriptor, String signature) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        BinaryClassSignatureParser binaryClassSignatureParser = this.signatureParser;
        if (signature != null) {
            descriptor = signature;
        }
        JavaType typeString = binaryClassSignatureParser.parseTypeString(new StringCharacterIterator(descriptor), this.context);
        ArrayList<JavaRecordComponent> arrayListMo7257getRecordComponents = mo7257getRecordComponents();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        arrayListMo7257getRecordComponents.add(new BinaryJavaRecordComponent(nameIdentifier, this, typeString, false));
        return null;
    }

    private final Object processValue(Object value, JavaType fieldType) {
        if (!(fieldType instanceof JavaPrimitiveType)) {
            return value;
        }
        JavaPrimitiveType javaPrimitiveType = (JavaPrimitiveType) fieldType;
        if (javaPrimitiveType.getType() == null || !(value instanceof Integer)) {
            return value;
        }
        PrimitiveType type = javaPrimitiveType.getType();
        int i = type == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
        if (i != 1) {
            return i != 2 ? value : Character.valueOf((char) ((Number) value).intValue());
        }
        int iIntValue = ((Number) value).intValue();
        if (iIntValue == 0) {
            return false;
        }
        if (iIntValue != 1) {
            return value;
        }
        return true;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        return BinaryJavaAnnotation.Companion.addAnnotation$default(BinaryJavaAnnotation.INSTANCE, this, desc, this.context, this.signatureParser, false, 16, null);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass findInnerClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return findInnerClass(name2, null);
    }

    public final JavaClass findInnerClass(Name name2, byte[] classFileContent) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Integer num = this.ownInnerClassNameToAccess.get(name2);
        BinaryJavaClass binaryJavaClass = null;
        if (num == null) {
            return null;
        }
        int iIntValue = num.intValue();
        VirtualFile virtualFileFindChild = getVirtualFile().getParent().findChild(getVirtualFile().getNameWithoutExtension() + '$' + name2 + '.' + getVirtualFile().getExtension());
        if (virtualFileFindChild != null) {
            FqName fqNameChild = getFqName().child(name2);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            binaryJavaClass = new BinaryJavaClass(virtualFileFindChild, fqNameChild, this.context.copyForMember$resolution_common_jvm(), this.signatureParser, iIntValue, this, classFileContent);
        }
        return binaryJavaClass;
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visitPermittedSubclass(String permittedSubclass) {
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(getPermittedTypes(), permittedSubclass != null ? convertInternalNameToClassifierType(permittedSubclass) : null);
    }
}
