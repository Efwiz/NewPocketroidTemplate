package org.jetbrains.kotlin.load.kotlin;

import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.functions.Function4;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.header.ReadKotlinClassHeaderAnnotationVisitor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes5.dex */
public abstract class FileBasedKotlinClass implements KotlinJvmBinaryClass {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KotlinClassHeader classHeader;
    private final ClassId classId;
    private final int classVersion;
    private final InnerClassesInfo innerClasses;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 6 || i == 7 || i == 21) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 6 || i == 7 || i == 21) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "classHeader";
                break;
            case 2:
            case 11:
            case 13:
            case 16:
            case 18:
            case 20:
                objArr[0] = "innerClasses";
                break;
            case 3:
                objArr[0] = "fileContents";
                break;
            case 4:
                objArr[0] = "jvmMetadataVersionFromLanguageVersion";
                break;
            case 5:
                objArr[0] = "factory";
                break;
            case 6:
            case 7:
            case 21:
                objArr[0] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass";
                break;
            case 8:
                objArr[0] = "annotationVisitor";
                break;
            case 9:
                objArr[0] = "visitor";
                break;
            case 10:
            case 15:
                objArr[0] = "desc";
                break;
            case 12:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 14:
                objArr[0] = "memberVisitor";
                break;
            case 17:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 19:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "classId";
                break;
        }
        if (i == 6) {
            objArr[1] = "getClassId";
        } else if (i == 7) {
            objArr[1] = "getClassHeader";
        } else if (i != 21) {
            objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass";
        } else {
            objArr[1] = "resolveNameByInternalName";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 6:
            case 7:
            case 21:
                break;
            case 8:
                objArr[2] = "loadClassAnnotations";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "convertAnnotationVisitor";
                break;
            case 14:
                objArr[2] = "visitMembers";
                break;
            case 15:
            case 16:
                objArr[2] = "resolveNameByDesc";
                break;
            case 17:
            case 18:
                objArr[2] = "resolveKotlinNameByType";
                break;
            case 19:
            case 20:
                objArr[2] = "resolveNameByInternalName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 6 && i != 7 && i != 21) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract boolean equals(Object obj);

    protected abstract byte[] getFileContents();

    public abstract int hashCode();

    public abstract String toString();

    protected FileBasedKotlinClass(ClassId classId, int i, KotlinClassHeader kotlinClassHeader, InnerClassesInfo innerClassesInfo) {
        if (classId == null) {
            $$$reportNull$$$0(0);
        }
        if (kotlinClassHeader == null) {
            $$$reportNull$$$0(1);
        }
        if (innerClassesInfo == null) {
            $$$reportNull$$$0(2);
        }
        this.classId = classId;
        this.classVersion = i;
        this.classHeader = kotlinClassHeader;
        this.innerClasses = innerClassesInfo;
    }

    public static class OuterAndInnerName {
        public final String innerSimpleName;
        public final String outerInternalName;

        private OuterAndInnerName(String str, String str2) {
            this.outerInternalName = str;
            this.innerSimpleName = str2;
        }
    }

    public static class InnerClassesInfo {
        private Map<String, OuterAndInnerName> map = null;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = "name";
            objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$InnerClassesInfo";
            if (i != 1) {
                objArr[2] = ProductAction.ACTION_ADD;
            } else {
                objArr[2] = "get";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public void add(String str, String str2, String str3) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            if (this.map == null) {
                this.map = new HashMap();
            }
            this.map.put(str, new OuterAndInnerName(str2, str3));
        }

        public OuterAndInnerName get(String str) {
            if (str == null) {
                $$$reportNull$$$0(1);
            }
            Map<String, OuterAndInnerName> map = this.map;
            if (map == null) {
                return null;
            }
            return map.get(str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T create(byte[] bArr, JvmMetadataVersion jvmMetadataVersion, Function4<ClassId, Integer, KotlinClassHeader, InnerClassesInfo, T> function4) {
        KotlinClassHeader kotlinClassHeaderCreateHeader;
        if (bArr == null) {
            $$$reportNull$$$0(3);
        }
        if (jvmMetadataVersion == null) {
            $$$reportNull$$$0(4);
        }
        if (function4 == 0) {
            $$$reportNull$$$0(5);
        }
        final ReadKotlinClassHeaderAnnotationVisitor readKotlinClassHeaderAnnotationVisitor = new ReadKotlinClassHeaderAnnotationVisitor();
        final Ref refCreate = Ref.create();
        final Ref refCreate2 = Ref.create();
        final InnerClassesInfo innerClassesInfo = new InnerClassesInfo();
        new ClassReader(bArr).accept(new ClassVisitor(589824) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 2) {
                    objArr[0] = "name";
                } else {
                    objArr[0] = "desc";
                }
                objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$1";
                if (i == 1) {
                    objArr[2] = "visitInnerClass";
                } else if (i != 2) {
                    objArr[2] = "visit";
                } else {
                    objArr[2] = "visitAnnotation";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
                if (str == null) {
                    $$$reportNull$$$0(0);
                }
                refCreate.set(str);
                refCreate2.set(Integer.valueOf(i));
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitInnerClass(String str, String str2, String str3, int i) {
                if (str == null) {
                    $$$reportNull$$$0(1);
                }
                innerClassesInfo.add(str, str2, str3);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public AnnotationVisitor visitAnnotation(String str, boolean z) {
                if (str == null) {
                    $$$reportNull$$$0(2);
                }
                return FileBasedKotlinClass.convertAnnotationVisitor(readKotlinClassHeaderAnnotationVisitor, str, innerClassesInfo);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitEnd() {
                readKotlinClassHeaderAnnotationVisitor.visitEnd();
            }
        }, 7);
        String str = (String) refCreate.get();
        if (str == null || (kotlinClassHeaderCreateHeader = readKotlinClassHeaderAnnotationVisitor.createHeader(jvmMetadataVersion)) == null) {
            return null;
        }
        return (T) function4.invoke(resolveNameByInternalName(str, innerClassesInfo), refCreate2.get(), kotlinClassHeaderCreateHeader, innerClassesInfo);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public ClassId getClassId() {
        ClassId classId = this.classId;
        if (classId == null) {
            $$$reportNull$$$0(6);
        }
        return classId;
    }

    public int getClassVersion() {
        return this.classVersion;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public KotlinClassHeader getClassHeader() {
        KotlinClassHeader kotlinClassHeader = this.classHeader;
        if (kotlinClassHeader == null) {
            $$$reportNull$$$0(7);
        }
        return kotlinClassHeader;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public void loadClassAnnotations(final KotlinJvmBinaryClass.AnnotationVisitor annotationVisitor, byte[] bArr) {
        if (annotationVisitor == null) {
            $$$reportNull$$$0(8);
        }
        if (bArr == null) {
            bArr = getFileContents();
        }
        new ClassReader(bArr).accept(new ClassVisitor(589824) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "desc", "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$2", "visitAnnotation"));
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public AnnotationVisitor visitAnnotation(String str, boolean z) {
                if (str == null) {
                    $$$reportNull$$$0(0);
                }
                return FileBasedKotlinClass.convertAnnotationVisitor(annotationVisitor, str, FileBasedKotlinClass.this.innerClasses);
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitEnd() {
                annotationVisitor.visitEnd();
            }
        }, 7);
    }

    public static AnnotationVisitor convertAnnotationVisitor(KotlinJvmBinaryClass.AnnotationVisitor annotationVisitor, String str, InnerClassesInfo innerClassesInfo) {
        if (annotationVisitor == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        if (innerClassesInfo == null) {
            $$$reportNull$$$0(11);
        }
        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationVisitor.visitAnnotation(resolveNameByDesc(str, innerClassesInfo), SourceElement.NO_SOURCE);
        if (annotationArgumentVisitorVisitAnnotation == null) {
            return null;
        }
        return convertAnnotationVisitor(annotationArgumentVisitorVisitAnnotation, innerClassesInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AnnotationVisitor convertAnnotationVisitor(final KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitor, final InnerClassesInfo innerClassesInfo) {
        if (annotationArgumentVisitor == null) {
            $$$reportNull$$$0(12);
        }
        if (innerClassesInfo == null) {
            $$$reportNull$$$0(13);
        }
        return new AnnotationVisitor(589824) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1 || i == 2) {
                    objArr[0] = "desc";
                } else {
                    objArr[0] = "value";
                }
                objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$3";
                if (i == 1) {
                    objArr[2] = "visitAnnotation";
                } else if (i == 2 || i == 3) {
                    objArr[2] = "visitEnum";
                } else {
                    objArr[2] = "visit";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
            public void visit(String str, Object obj) {
                if (obj == null) {
                    $$$reportNull$$$0(0);
                }
                Name nameIdentifier = str == null ? null : Name.identifier(str);
                if (obj instanceof Type) {
                    annotationArgumentVisitor.visitClassLiteral(nameIdentifier, FileBasedKotlinClass.resolveKotlinNameByType((Type) obj, innerClassesInfo));
                } else {
                    annotationArgumentVisitor.visit(nameIdentifier, obj);
                }
            }

            @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
            public AnnotationVisitor visitArray(String str) {
                final KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor annotationArrayArgumentVisitorVisitArray = annotationArgumentVisitor.visitArray(str == null ? null : Name.identifier(str));
                if (annotationArrayArgumentVisitorVisitArray == null) {
                    return null;
                }
                return new AnnotationVisitor(589824) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.3.1
                    private static /* synthetic */ void $$$reportNull$$$0(int i) {
                        Object[] objArr = new Object[3];
                        if (i == 1 || i == 3) {
                            objArr[0] = "desc";
                        } else {
                            objArr[0] = "value";
                        }
                        objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$3$1";
                        if (i == 1 || i == 2) {
                            objArr[2] = "visitEnum";
                        } else if (i != 3) {
                            objArr[2] = "visit";
                        } else {
                            objArr[2] = "visitAnnotation";
                        }
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                    }

                    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
                    public void visit(String str2, Object obj) {
                        if (obj == null) {
                            $$$reportNull$$$0(0);
                        }
                        if (obj instanceof Type) {
                            annotationArrayArgumentVisitorVisitArray.visitClassLiteral(FileBasedKotlinClass.resolveKotlinNameByType((Type) obj, innerClassesInfo));
                        } else {
                            annotationArrayArgumentVisitorVisitArray.visit(obj);
                        }
                    }

                    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
                    public void visitEnum(String str2, String str3, String str4) {
                        if (str3 == null) {
                            $$$reportNull$$$0(1);
                        }
                        if (str4 == null) {
                            $$$reportNull$$$0(2);
                        }
                        annotationArrayArgumentVisitorVisitArray.visitEnum(FileBasedKotlinClass.resolveNameByDesc(str3, innerClassesInfo), Name.identifier(str4));
                    }

                    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
                    public AnnotationVisitor visitAnnotation(String str2, String str3) {
                        if (str3 == null) {
                            $$$reportNull$$$0(3);
                        }
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationArrayArgumentVisitorVisitArray.visitAnnotation(FileBasedKotlinClass.resolveNameByDesc(str3, innerClassesInfo));
                        if (annotationArgumentVisitorVisitAnnotation == null) {
                            return null;
                        }
                        return FileBasedKotlinClass.convertAnnotationVisitor(annotationArgumentVisitorVisitAnnotation, innerClassesInfo);
                    }

                    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
                    public void visitEnd() {
                        annotationArrayArgumentVisitorVisitArray.visitEnd();
                    }
                };
            }

            @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
            public AnnotationVisitor visitAnnotation(String str, String str2) {
                if (str2 == null) {
                    $$$reportNull$$$0(1);
                }
                KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = annotationArgumentVisitor.visitAnnotation(str == null ? null : Name.identifier(str), FileBasedKotlinClass.resolveNameByDesc(str2, innerClassesInfo));
                if (annotationArgumentVisitorVisitAnnotation == null) {
                    return null;
                }
                return FileBasedKotlinClass.convertAnnotationVisitor(annotationArgumentVisitorVisitAnnotation, innerClassesInfo);
            }

            @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
            public void visitEnum(String str, String str2, String str3) {
                if (str2 == null) {
                    $$$reportNull$$$0(2);
                }
                if (str3 == null) {
                    $$$reportNull$$$0(3);
                }
                annotationArgumentVisitor.visitEnum(str == null ? null : Name.identifier(str), FileBasedKotlinClass.resolveNameByDesc(str2, innerClassesInfo), Name.identifier(str3));
            }

            @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
            public void visitEnd() {
                annotationArgumentVisitor.visitEnd();
            }
        };
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public void visitMembers(final KotlinJvmBinaryClass.MemberVisitor memberVisitor, byte[] bArr) {
        if (memberVisitor == null) {
            $$$reportNull$$$0(14);
        }
        if (bArr == null) {
            bArr = getFileContents();
        }
        new ClassReader(bArr).accept(new ClassVisitor(589824) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1 || i == 3) {
                    objArr[0] = "desc";
                } else {
                    objArr[0] = "name";
                }
                objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$4";
                if (i == 2 || i == 3) {
                    objArr[2] = "visitMethod";
                } else {
                    objArr[2] = "visitField";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
                if (str == null) {
                    $$$reportNull$$$0(0);
                }
                if (str2 == null) {
                    $$$reportNull$$$0(1);
                }
                final KotlinJvmBinaryClass.AnnotationVisitor annotationVisitorVisitField = memberVisitor.visitField(Name.identifier(str), str2, obj);
                if (annotationVisitorVisitField == null) {
                    return null;
                }
                return new FieldVisitor(589824) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.4.1
                    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "desc", "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$4$1", "visitAnnotation"));
                    }

                    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
                    public AnnotationVisitor visitAnnotation(String str4, boolean z) {
                        if (str4 == null) {
                            $$$reportNull$$$0(0);
                        }
                        return FileBasedKotlinClass.convertAnnotationVisitor(annotationVisitorVisitField, str4, FileBasedKotlinClass.this.innerClasses);
                    }

                    @Override // org.jetbrains.org.objectweb.asm.FieldVisitor
                    public void visitEnd() {
                        annotationVisitorVisitField.visitEnd();
                    }
                };
            }

            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
                if (str == null) {
                    $$$reportNull$$$0(2);
                }
                if (str2 == null) {
                    $$$reportNull$$$0(3);
                }
                KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(Name.identifier(str), str2);
                if (methodAnnotationVisitorVisitMethod == null) {
                    return null;
                }
                return new MethodVisitor(589824, Type.getArgumentTypes(str2).length, methodAnnotationVisitorVisitMethod) { // from class: org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass.4.2
                    private int invisibleAnnotableParameterCount;
                    final /* synthetic */ int val$methodParamCount;
                    final /* synthetic */ KotlinJvmBinaryClass.MethodAnnotationVisitor val$v;
                    private int visibleAnnotableParameterCount;

                    private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                        Object[] objArr = new Object[3];
                        objArr[0] = "desc";
                        objArr[1] = "org/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$4$2";
                        if (i2 != 1) {
                            objArr[2] = "visitAnnotation";
                        } else {
                            objArr[2] = "visitParameterAnnotation";
                        }
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                    }

                    {
                        this.val$methodParamCount = i;
                        this.val$v = methodAnnotationVisitorVisitMethod;
                        this.visibleAnnotableParameterCount = i;
                        this.invisibleAnnotableParameterCount = i;
                    }

                    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
                    public AnnotationVisitor visitAnnotation(String str4, boolean z) {
                        if (str4 == null) {
                            $$$reportNull$$$0(0);
                        }
                        return FileBasedKotlinClass.convertAnnotationVisitor(this.val$v, str4, FileBasedKotlinClass.this.innerClasses);
                    }

                    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
                    public AnnotationVisitor visitAnnotationDefault() {
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotationMemberDefaultValue = this.val$v.visitAnnotationMemberDefaultValue();
                        if (annotationArgumentVisitorVisitAnnotationMemberDefaultValue == null) {
                            return null;
                        }
                        return FileBasedKotlinClass.convertAnnotationVisitor(annotationArgumentVisitorVisitAnnotationMemberDefaultValue, FileBasedKotlinClass.this.innerClasses);
                    }

                    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
                    public AnnotationVisitor visitParameterAnnotation(int i2, String str4, boolean z) {
                        if (str4 == null) {
                            $$$reportNull$$$0(1);
                        }
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = this.val$v.visitParameterAnnotation((i2 + this.val$methodParamCount) - (z ? this.visibleAnnotableParameterCount : this.invisibleAnnotableParameterCount), FileBasedKotlinClass.resolveNameByDesc(str4, FileBasedKotlinClass.this.innerClasses), SourceElement.NO_SOURCE);
                        if (annotationArgumentVisitorVisitParameterAnnotation == null) {
                            return null;
                        }
                        return FileBasedKotlinClass.convertAnnotationVisitor(annotationArgumentVisitorVisitParameterAnnotation, FileBasedKotlinClass.this.innerClasses);
                    }

                    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
                    public void visitAnnotableParameterCount(int i2, boolean z) {
                        if (z) {
                            this.visibleAnnotableParameterCount = i2;
                        } else {
                            this.invisibleAnnotableParameterCount = i2;
                        }
                    }

                    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
                    public void visitEnd() {
                        this.val$v.visitEnd();
                    }
                };
            }
        }, 7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ClassId resolveNameByDesc(String str, InnerClassesInfo innerClassesInfo) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        if (innerClassesInfo == null) {
            $$$reportNull$$$0(16);
        }
        return resolveNameByInternalName(str.substring(1, str.length() - 1), innerClassesInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ClassLiteralValue resolveKotlinNameByType(Type type, InnerClassesInfo innerClassesInfo) {
        if (type == null) {
            $$$reportNull$$$0(17);
        }
        if (innerClassesInfo == null) {
            $$$reportNull$$$0(18);
        }
        String descriptor = type.getDescriptor();
        int dimensions = descriptor.charAt(0) == '[' ? type.getDimensions() : 0;
        if (dimensions != 0) {
            descriptor = type.getElementType().getDescriptor();
        }
        JvmPrimitiveType byDesc = JvmPrimitiveType.getByDesc(descriptor);
        if (byDesc != null) {
            if (dimensions > 0) {
                return new ClassLiteralValue(ClassId.topLevel(byDesc.getPrimitiveType().getArrayTypeFqName()), dimensions - 1);
            }
            return new ClassLiteralValue(ClassId.topLevel(byDesc.getPrimitiveType().getTypeFqName()), dimensions);
        }
        ClassId classIdResolveNameByDesc = resolveNameByDesc(descriptor, innerClassesInfo);
        ClassId classIdMapJavaToKotlin = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(classIdResolveNameByDesc.asSingleFqName());
        if (classIdMapJavaToKotlin != null) {
            classIdResolveNameByDesc = classIdMapJavaToKotlin;
        }
        return new ClassLiteralValue(classIdResolveNameByDesc, dimensions);
    }

    public static ClassId resolveNameByInternalName(String str, InnerClassesInfo innerClassesInfo) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (innerClassesInfo == null) {
            $$$reportNull$$$0(20);
        }
        if (!str.contains("$")) {
            ClassId classId = ClassId.topLevel(new FqName(str.replace('/', '.')));
            if (classId == null) {
                $$$reportNull$$$0(21);
            }
            return classId;
        }
        boolean z = true;
        ArrayList arrayList = new ArrayList(1);
        while (true) {
            OuterAndInnerName outerAndInnerName = innerClassesInfo.get(str);
            if (outerAndInnerName == null) {
                z = false;
                break;
            }
            if (outerAndInnerName.outerInternalName == null) {
                break;
            }
            arrayList.add(outerAndInnerName.innerSimpleName);
            str = outerAndInnerName.outerInternalName;
        }
        FqName fqName = new FqName(str.replace('/', '.'));
        arrayList.add(fqName.shortName().asString());
        Collections.reverse(arrayList);
        return new ClassId(fqName.parent(), FqName.fromSegments(arrayList), z);
    }
}
