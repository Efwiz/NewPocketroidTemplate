package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.ClassWriter;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.tree.ClassNode;
import org.jetbrains.org.objectweb.asm.tree.FieldNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: OriginCollectingClassBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0003H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/OriginCollectingClassBuilderFactory;", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "builderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;)V", "compiledClasses", "", "Lorg/jetbrains/org/objectweb/asm/tree/ClassNode;", "getCompiledClasses", "()Ljava/util/List;", "origins", "", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "getOrigins", "()Ljava/util/Map;", "asBytes", "", "builder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "asText", "", "close", "", "getClassBuilderMode", "newClassBuilder", "Lorg/jetbrains/kotlin/codegen/AbstractClassBuilder$Concrete;", "origin", "OriginCollectingClassBuilder", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OriginCollectingClassBuilderFactory implements ClassBuilderFactory {
    private final ClassBuilderMode builderMode;
    private final List<ClassNode> compiledClasses;
    private final Map<Object, JvmDeclarationOrigin> origins;

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public void close() {
    }

    public OriginCollectingClassBuilderFactory(ClassBuilderMode builderMode) {
        Intrinsics.checkNotNullParameter(builderMode, "builderMode");
        this.builderMode = builderMode;
        this.compiledClasses = new ArrayList();
        this.origins = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public /* bridge */ /* synthetic */ String asText(ClassBuilder classBuilder) {
        return (String) m6422asText(classBuilder);
    }

    public final List<ClassNode> getCompiledClasses() {
        return this.compiledClasses;
    }

    public final Map<Object, JvmDeclarationOrigin> getOrigins() {
        return this.origins;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    /* JADX INFO: renamed from: getClassBuilderMode, reason: from getter */
    public ClassBuilderMode getBuilderMode() {
        return this.builderMode;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public AbstractClassBuilder.Concrete newClassBuilder(JvmDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        ClassNode classNode = new ClassNode();
        this.compiledClasses.add(classNode);
        this.origins.put(classNode, origin);
        return new OriginCollectingClassBuilder(this, classNode);
    }

    /* JADX INFO: compiled from: OriginCollectingClassBuilderFactory.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J<\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016JI\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e2\u0010\u0010\u0015\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000e\u0018\u00010\u0016H\u0016¢\u0006\u0002\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/OriginCollectingClassBuilderFactory$OriginCollectingClassBuilder;", "Lorg/jetbrains/kotlin/codegen/AbstractClassBuilder$Concrete;", "classNode", "Lorg/jetbrains/org/objectweb/asm/tree/ClassNode;", "(Lorg/jetbrains/kotlin/codegen/OriginCollectingClassBuilderFactory;Lorg/jetbrains/org/objectweb/asm/tree/ClassNode;)V", "getClassNode", "()Lorg/jetbrains/org/objectweb/asm/tree/ClassNode;", "newField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "access", "", "name", "", "desc", "signature", "value", "", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "exceptions", "", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class OriginCollectingClassBuilder extends AbstractClassBuilder.Concrete {
        private final ClassNode classNode;
        final /* synthetic */ OriginCollectingClassBuilderFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OriginCollectingClassBuilder(OriginCollectingClassBuilderFactory originCollectingClassBuilderFactory, ClassNode classNode) {
            super(classNode);
            Intrinsics.checkNotNullParameter(classNode, "classNode");
            this.this$0 = originCollectingClassBuilderFactory;
            this.classNode = classNode;
        }

        public final ClassNode getClassNode() {
            return this.classNode;
        }

        @Override // org.jetbrains.kotlin.codegen.AbstractClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public FieldVisitor newField(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, Object value) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            FieldVisitor fieldVisitorNewField = super.newField(origin, access, name2, desc, signature, value);
            Intrinsics.checkNotNull(fieldVisitorNewField, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldNode");
            FieldNode fieldNode = (FieldNode) fieldVisitorNewField;
            this.this$0.getOrigins().put(fieldNode, origin);
            return fieldNode;
        }

        @Override // org.jetbrains.kotlin.codegen.AbstractClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public MethodVisitor newMethod(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, String[] exceptions) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            MethodVisitor methodVisitorNewMethod = super.newMethod(origin, access, name2, desc, signature, exceptions);
            Intrinsics.checkNotNull(methodVisitorNewMethod, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodNode");
            MethodNode methodNode = (MethodNode) methodVisitorNewMethod;
            this.this$0.getOrigins().put(methodNode, origin);
            if ((access & 1024) != 0 && methodNode.localVariables == null) {
                methodNode.localVariables = new ArrayList();
            }
            return methodNode;
        }
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    /* JADX INFO: renamed from: asBytes */
    public byte[] mo6372asBytes(ClassBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        ClassWriter classWriter = new ClassWriter(3);
        ((OriginCollectingClassBuilder) builder).getClassNode().accept(classWriter);
        byte[] byteArray = classWriter.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    /* JADX INFO: renamed from: asText, reason: collision with other method in class */
    public Void m6422asText(ClassBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        throw new UnsupportedOperationException();
    }
}
