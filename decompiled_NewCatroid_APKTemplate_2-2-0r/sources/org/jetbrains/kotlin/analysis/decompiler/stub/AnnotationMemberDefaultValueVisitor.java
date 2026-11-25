package org.jetbrains.kotlin.analysis.decompiler.stub;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.impl.AnnotationData;
import org.jetbrains.kotlin.psi.stubs.impl.EnumData;
import org.jetbrains.kotlin.psi.stubs.impl.KClassData;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: CallableClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\t\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0002J\u001c\u0010\u000b\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u00012\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0016J\u001a\u0010\u0014\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\r\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\fH\u0016J\"\u0010\u0017\u001a\u00020\f2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0005H\u0016R$\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationMemberDefaultValueVisitor;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "()V", "args", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "getArgs", "()Ljava/util/Map;", "nameOrSpecial", "name", "visit", "", "value", "", "visitAnnotation", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitArray", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AnnotationMemberDefaultValueVisitor implements KotlinJvmBinaryClass.AnnotationArgumentVisitor {
    private final Map<Name, ConstantValue<?>> args = new LinkedHashMap();

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
    public void visitEnd() {
    }

    protected final Map<Name, ConstantValue<?>> getArgs() {
        return this.args;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Name nameOrSpecial(Name name2) {
        if (name2 != null) {
            return name2;
        }
        Name nameSpecial = Name.special("<no_name>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        return nameSpecial;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
    public void visit(Name name2, Object value) {
        this.args.put(nameOrSpecial(name2), KotlinConstantValueKt.createConstantValue(value));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
    public void visitClassLiteral(Name name2, ClassLiteralValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.args.put(nameOrSpecial(name2), KotlinConstantValueKt.createConstantValue(new KClassData(value.getClassId(), value.getArrayNestedness())));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
    public void visitEnum(Name name2, ClassId enumClassId, Name enumEntryName) {
        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
        this.args.put(nameOrSpecial(name2), KotlinConstantValueKt.createConstantValue(new EnumData(enumClassId, enumEntryName)));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
    public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(final Name name2, final ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return new KotlinJvmBinaryClass.AnnotationArgumentVisitor() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationMemberDefaultValueVisitor.visitAnnotation.1
            private final /* synthetic */ AnnotationMemberDefaultValueVisitor $$delegate_0;

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visit(Name name3, Object value) {
                this.$$delegate_0.visit(name3, value);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name name3, ClassId classId2) {
                Intrinsics.checkNotNullParameter(classId2, "classId");
                return this.$$delegate_0.visitAnnotation(name3, classId2);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name3) {
                return this.$$delegate_0.visitArray(name3);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitClassLiteral(Name name3, ClassLiteralValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                this.$$delegate_0.visitClassLiteral(name3, value);
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnum(Name name3, ClassId enumClassId, Name enumEntryName) {
                Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                this.$$delegate_0.visitEnum(name3, enumClassId, enumEntryName);
            }

            {
                this.$$delegate_0 = AnnotationMemberDefaultValueVisitor.this;
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnd() {
                this.getArgs().put(this.nameOrSpecial(name2), KotlinConstantValueKt.createConstantValue(new AnnotationData(classId, AnnotationMemberDefaultValueVisitor.this.getArgs())));
            }
        };
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationMemberDefaultValueVisitor$visitArray$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CallableClsStubBuilder.kt */
    @Metadata(d1 = {"\u00007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u0006H\u0016J\u0018\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"org/jetbrains/kotlin/analysis/decompiler/stub/AnnotationMemberDefaultValueVisitor$visitArray$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", PathTree.NAME_elements, "", "", "visit", "", "value", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "Lorg/jetbrains/kotlin/name/Name;", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class C42331 implements KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor {
        final /* synthetic */ Name $name;
        private final List<Object> elements = new ArrayList();

        C42331(Name name2) {
            this.$name = name2;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
        public void visit(Object value) {
            CollectionsKt.addIfNotNull(this.elements, value);
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
        public void visitEnum(ClassId enumClassId, Name enumEntryName) {
            Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
            Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
            this.elements.add(new EnumData(enumClassId, enumEntryName));
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
        public void visitClassLiteral(ClassLiteralValue value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.elements.add(new KClassData(value.getClassId(), value.getArrayNestedness()));
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
        public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(final ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            final AnnotationMemberDefaultValueVisitor annotationMemberDefaultValueVisitor = new AnnotationMemberDefaultValueVisitor();
            return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, classId) { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationMemberDefaultValueVisitor$visitArray$1$visitAnnotation$1
                private final /* synthetic */ AnnotationMemberDefaultValueVisitor $$delegate_0;
                final /* synthetic */ ClassId $classId;
                final /* synthetic */ AnnotationMemberDefaultValueVisitor.C42331 this$0;

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visit(Name name2, Object value) {
                    this.$$delegate_0.visit(name2, value);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name name2, ClassId classId2) {
                    Intrinsics.checkNotNullParameter(classId2, "classId");
                    return this.$$delegate_0.visitAnnotation(name2, classId2);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name2) {
                    return this.$$delegate_0.visitArray(name2);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitClassLiteral(Name name2, ClassLiteralValue value) {
                    Intrinsics.checkNotNullParameter(value, "value");
                    this.$$delegate_0.visitClassLiteral(name2, value);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitEnum(Name name2, ClassId enumClassId, Name enumEntryName) {
                    Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                    Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                    this.$$delegate_0.visitEnum(name2, enumClassId, enumEntryName);
                }

                {
                    this.this$0 = this;
                    this.$classId = classId;
                    this.$$delegate_0 = this.$visitor;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitEnd() {
                    CollectionsKt.addIfNotNull(this.this$0.elements, new AnnotationData(this.$classId, this.$visitor.getArgs()));
                }
            };
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor
        public void visitEnd() {
            AnnotationMemberDefaultValueVisitor.this.getArgs().put(AnnotationMemberDefaultValueVisitor.this.nameOrSpecial(this.$name), KotlinConstantValueKt.createConstantValue(this.elements.toArray(new Object[0])));
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
    public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name2) {
        return new C42331(name2);
    }
}
