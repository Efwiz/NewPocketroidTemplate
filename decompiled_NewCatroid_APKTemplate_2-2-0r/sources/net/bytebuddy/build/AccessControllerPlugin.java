package net.bytebuddy.build;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.FieldManifestation;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.OpenedClassReader;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class AccessControllerPlugin extends Plugin.ForElementMatcher implements Plugin.Factory {
    private static final String ACCESS_CONTROLLER = "java.security.AccessController";
    private static final Object[] EMPTY = new Object[0];
    private static final String NAME = "ACCESS_CONTROLLER";
    private static final Map<MethodDescription.SignatureToken, MethodDescription.SignatureToken> SIGNATURES;

    @MaybeNull
    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
    private final String property;

    @Target({ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Enhance {
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    /* JADX WARN: Code duplicated, block: B:21:0x002e A[RETURN] */
    @Override // net.bytebuddy.build.Plugin.ForElementMatcher
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        String str = this.property;
        String str2 = ((AccessControllerPlugin) obj).property;
        if (str2 == null) {
            if (str != null) {
                return false;
            }
            return true;
        }
        if (str == null || !str.equals(str2)) {
            return false;
        }
        return true;
    }

    @Override // net.bytebuddy.build.Plugin.ForElementMatcher
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        String str = this.property;
        return str != null ? iHashCode + str.hashCode() : iHashCode;
    }

    @Override // net.bytebuddy.build.Plugin.Factory
    public Plugin make() {
        return this;
    }

    static {
        HashMap map = new HashMap();
        SIGNATURES = map;
        map.put(new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class)), new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class)));
        map.put(new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class)), new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class)));
        map.put(new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class), TypeDescription.ForLoadedType.of(Object.class)), new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class), JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub()));
        map.put(new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class), TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(Permission[].class)), new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class), JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub(), TypeDescription.ForLoadedType.of(Permission[].class)));
        map.put(new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class), TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(Permission[].class)), new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedAction.class), JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub(), TypeDescription.ForLoadedType.of(Permission[].class)));
        map.put(new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class)), new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class)));
        map.put(new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class)), new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class)));
        map.put(new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class), TypeDescription.ForLoadedType.of(Object.class)), new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class), JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub()));
        map.put(new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class), TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(Permission[].class)), new MethodDescription.SignatureToken("doPrivileged", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class), JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub(), TypeDescription.ForLoadedType.of(Permission[].class)));
        map.put(new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class), TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(Permission[].class)), new MethodDescription.SignatureToken("doPrivilegedWithCombiner", TypeDescription.ForLoadedType.of(Object.class), TypeDescription.ForLoadedType.of(PrivilegedExceptionAction.class), JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub(), TypeDescription.ForLoadedType.of(Permission[].class)));
        map.put(new MethodDescription.SignatureToken("getContext", TypeDescription.ForLoadedType.of(Object.class), new TypeDescription[0]), new MethodDescription.SignatureToken("getContext", JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub(), new TypeDescription[0]));
        map.put(new MethodDescription.SignatureToken("checkPermission", TypeDescription.ForLoadedType.of(Void.TYPE), TypeDescription.ForLoadedType.of(Permission.class)), new MethodDescription.SignatureToken("checkPermission", TypeDescription.ForLoadedType.of(Void.TYPE), TypeDescription.ForLoadedType.of(Permission.class)));
    }

    public AccessControllerPlugin() {
        this(null);
    }

    @Plugin.Factory.UsingReflection.Priority(Integer.MAX_VALUE)
    public AccessControllerPlugin(@MaybeNull String str) {
        super(ElementMatchers.declaresMethod(ElementMatchers.isAnnotatedWith((Class<? extends Annotation>) Enhance.class)));
        this.property = str;
    }

    @Override // net.bytebuddy.build.Plugin
    public DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
        String str = NAME;
        while (!typeDescription.getDeclaredFields().filter(ElementMatchers.named(str)).isEmpty()) {
            str = str + "$";
        }
        return builder.defineField(str, Boolean.TYPE, Visibility.PRIVATE, Ownership.STATIC, FieldManifestation.FINAL).visit(new AsmVisitorWrapper.ForDeclaredMethods().method(ElementMatchers.isAnnotatedWith((Class<? extends Annotation>) Enhance.class), new AccessControlWrapper(str))).initializer(this.property == null ? new Initializer.WithoutProperty(typeDescription, str) : new Initializer.WithProperty(typeDescription, str, this.property));
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static abstract class Initializer implements ByteCodeAppender {
        private final TypeDescription instrumentedType;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f344name;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Initializer initializer = (Initializer) obj;
            return this.f344name.equals(initializer.f344name) && this.instrumentedType.equals(initializer.instrumentedType);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + this.f344name.hashCode();
        }

        protected abstract int onAccessController(MethodVisitor methodVisitor);

        protected Initializer(TypeDescription typeDescription, String str) {
            this.instrumentedType = typeDescription;
            this.f344name = str;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            Label label3 = new Label();
            Label label4 = new Label();
            Label label5 = new Label();
            methodVisitor.visitTryCatchBlock(label, label2, label3, Type.getInternalName(ClassNotFoundException.class));
            methodVisitor.visitTryCatchBlock(label, label2, label4, Type.getInternalName(SecurityException.class));
            methodVisitor.visitLabel(label);
            methodVisitor.visitLdcInsn(AccessControllerPlugin.ACCESS_CONTROLLER);
            methodVisitor.visitInsn(3);
            methodVisitor.visitInsn(1);
            methodVisitor.visitMethodInsn(184, Type.getInternalName(Class.class), "forName", Type.getMethodDescriptor(Type.getType((Class<?>) Class.class), Type.getType((Class<?>) String.class), Type.getType((Class<?>) Boolean.TYPE), Type.getType((Class<?>) ClassLoader.class)), false);
            methodVisitor.visitInsn(87);
            int iOnAccessController = onAccessController(methodVisitor);
            methodVisitor.visitFieldInsn(179, this.instrumentedType.getInternalName(), this.f344name, Type.getDescriptor(Boolean.TYPE));
            methodVisitor.visitLabel(label2);
            methodVisitor.visitJumpInsn(167, label5);
            methodVisitor.visitLabel(label3);
            context.getFrameGeneration().same1(methodVisitor, TypeDescription.ForLoadedType.of(ClassNotFoundException.class), Collections.emptyList());
            methodVisitor.visitInsn(87);
            methodVisitor.visitInsn(3);
            methodVisitor.visitFieldInsn(179, this.instrumentedType.getInternalName(), this.f344name, Type.getDescriptor(Boolean.TYPE));
            methodVisitor.visitJumpInsn(167, label5);
            methodVisitor.visitLabel(label4);
            context.getFrameGeneration().same1(methodVisitor, TypeDescription.ForLoadedType.of(SecurityException.class), Collections.emptyList());
            methodVisitor.visitInsn(87);
            methodVisitor.visitInsn(4);
            methodVisitor.visitFieldInsn(179, this.instrumentedType.getInternalName(), this.f344name, Type.getDescriptor(Boolean.TYPE));
            methodVisitor.visitLabel(label5);
            context.getFrameGeneration().same(methodVisitor, Collections.emptyList());
            return new ByteCodeAppender.Size(Math.max(3, iOnAccessController), 0);
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class WithProperty extends Initializer {
            private final String property;

            @Override // net.bytebuddy.build.AccessControllerPlugin.Initializer
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.property.equals(((WithProperty) obj).property);
            }

            @Override // net.bytebuddy.build.AccessControllerPlugin.Initializer
            public int hashCode() {
                return (super.hashCode() * 31) + this.property.hashCode();
            }

            protected WithProperty(TypeDescription typeDescription, String str, String str2) {
                super(typeDescription, str);
                this.property = str2;
            }

            @Override // net.bytebuddy.build.AccessControllerPlugin.Initializer
            protected int onAccessController(MethodVisitor methodVisitor) {
                methodVisitor.visitLdcInsn(this.property);
                methodVisitor.visitLdcInsn("true");
                methodVisitor.visitMethodInsn(184, Type.getInternalName(System.class), "getProperty", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), Type.getType((Class<?>) String.class), Type.getType((Class<?>) String.class)), false);
                methodVisitor.visitMethodInsn(184, Type.getInternalName(Boolean.class), "parseBoolean", Type.getMethodDescriptor(Type.getType((Class<?>) Boolean.TYPE), Type.getType((Class<?>) String.class)), false);
                return 2;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class WithoutProperty extends Initializer {
            @Override // net.bytebuddy.build.AccessControllerPlugin.Initializer
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass();
            }

            @Override // net.bytebuddy.build.AccessControllerPlugin.Initializer
            public int hashCode() {
                return super.hashCode();
            }

            protected WithoutProperty(TypeDescription typeDescription, String str) {
                super(typeDescription, str);
            }

            @Override // net.bytebuddy.build.AccessControllerPlugin.Initializer
            protected int onAccessController(MethodVisitor methodVisitor) {
                methodVisitor.visitInsn(4);
                return 1;
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class AccessControlWrapper implements AsmVisitorWrapper.ForDeclaredMethods.MethodVisitorWrapper {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f342name;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.f342name.equals(((AccessControlWrapper) obj).f342name);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.f342name.hashCode();
        }

        protected AccessControlWrapper(String str) {
            this.f342name = str;
        }

        @Override // net.bytebuddy.asm.AsmVisitorWrapper.ForDeclaredMethods.MethodVisitorWrapper
        public MethodVisitor wrap(TypeDescription typeDescription, MethodDescription methodDescription, MethodVisitor methodVisitor, Implementation.Context context, TypePool typePool, int i, int i2) {
            MethodDescription.SignatureToken signatureToken = (MethodDescription.SignatureToken) AccessControllerPlugin.SIGNATURES.get(methodDescription.asDefined().asSignatureToken());
            if (signatureToken == null) {
                throw new IllegalStateException(methodDescription + " does not have a method with a matching signature in java.security.AccessController");
            }
            if (methodDescription.isPublic() || methodDescription.isProtected()) {
                throw new IllegalStateException(methodDescription + " is either public or protected what is not permitted to avoid context leaks");
            }
            return new PrefixingMethodVisitor(methodVisitor, typeDescription, signatureToken, this.f342name, !methodDescription.isStatic() ? 1 : 0, context.getFrameGeneration());
        }

        protected static class PrefixingMethodVisitor extends MethodVisitor {
            private final Implementation.Context.FrameGeneration frameGeneration;
            private final TypeDescription instrumentedType;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f343name;
            private final int offset;
            private final MethodDescription.SignatureToken token;

            protected PrefixingMethodVisitor(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodDescription.SignatureToken signatureToken, String str, int i, Implementation.Context.FrameGeneration frameGeneration) {
                super(OpenedClassReader.ASM_API, methodVisitor);
                this.instrumentedType = typeDescription;
                this.token = signatureToken;
                this.f343name = str;
                this.offset = i;
                this.frameGeneration = frameGeneration;
            }

            @Override // net.bytebuddy.jar.asm.MethodVisitor
            public void visitCode() {
                this.mv.visitCode();
                this.mv.visitFieldInsn(178, this.instrumentedType.getInternalName(), this.f343name, Type.getDescriptor(Boolean.TYPE));
                Label label = new Label();
                this.mv.visitJumpInsn(153, label);
                int size = this.offset;
                for (TypeDescription typeDescription : this.token.getParameterTypes()) {
                    this.mv.visitVarInsn(Type.getType(typeDescription.getDescriptor()).getOpcode(21), size);
                    if (typeDescription.equals(JavaType.ACCESS_CONTROL_CONTEXT.getTypeStub())) {
                        this.mv.visitTypeInsn(192, typeDescription.getInternalName());
                    }
                    size += typeDescription.getStackSize().getSize();
                }
                this.mv.visitMethodInsn(184, AccessControllerPlugin.ACCESS_CONTROLLER.replace('.', '/'), this.token.getName(), this.token.getDescriptor(), false);
                this.mv.visitInsn(Type.getType(this.token.getReturnType().getDescriptor()).getOpcode(172));
                this.mv.visitLabel(label);
                this.frameGeneration.same(this.mv, this.token.getParameterTypes());
            }

            @Override // net.bytebuddy.jar.asm.MethodVisitor
            public void visitMaxs(int i, int i2) {
                this.mv.visitMaxs(Math.max(Math.max(StackSize.of(this.token.getParameterTypes()), this.token.getReturnType().getStackSize().getSize()), i), i2);
            }
        }
    }
}
