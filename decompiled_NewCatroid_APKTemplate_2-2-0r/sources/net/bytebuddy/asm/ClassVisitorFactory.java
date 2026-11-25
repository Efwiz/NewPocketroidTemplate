package net.bytebuddy.asm;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.FieldManifestation;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.MultipleParentClassLoader;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.ExceptionMethod;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.TypeCreation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.AnnotationVisitor;
import net.bytebuddy.jar.asm.Attribute;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.ConstantDynamic;
import net.bytebuddy.jar.asm.FieldVisitor;
import net.bytebuddy.jar.asm.Handle;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.ModuleVisitor;
import net.bytebuddy.jar.asm.RecordComponentVisitor;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.jar.asm.TypePath;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.OpenedClassReader;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public abstract class ClassVisitorFactory<T> {
    private static final boolean ACCESS_CONTROLLER;
    private static final String DELEGATE = "delegate";
    private static final String LABELS = "labels";
    private static final String WRAP = "wrap";
    private final Class<?> type;

    static {
        boolean z = false;
        try {
            Class.forName("java.security.AccessController", false, null);
            ACCESS_CONTROLLER = Boolean.parseBoolean(System.getProperty("net.bytebuddy.securitymanager", "true"));
        } catch (ClassNotFoundException unused) {
            ACCESS_CONTROLLER = z;
        } catch (SecurityException unused2) {
            z = true;
            ACCESS_CONTROLLER = z;
        }
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.type.equals(((ClassVisitorFactory) obj).type);
    }

    public int hashCode() {
        return (getClass().hashCode() * 31) + this.type.hashCode();
    }

    public abstract ClassVisitor unwrap(T t);

    public abstract T wrap(ClassVisitor classVisitor);

    protected ClassVisitorFactory(Class<?> cls) {
        this.type = cls;
    }

    public Class<?> getType() {
        return this.type;
    }

    public static <S> ClassVisitorFactory<S> of(Class<S> cls) {
        return of(cls, new ByteBuddy().with(TypeValidation.DISABLED));
    }

    public static <S> ClassVisitorFactory<S> of(Class<S> cls, ByteBuddy byteBuddy) {
        return (ClassVisitorFactory) doPrivileged(new CreateClassVisitorFactory(cls, byteBuddy));
    }

    @AccessControllerPlugin.Enhance
    private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DynamicType.Builder<?> toVisitorBuilder(ByteBuddy byteBuddy, Class<?> cls, Class<?> cls2, @MaybeNull Class<?> cls3, @MaybeNull Class<?> cls4, Implementation implementation) throws Exception {
        DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<T> receiverTypeDefinitionIntercept = byteBuddy.subclass((Class) cls, (ConstructorStrategy) ConstructorStrategy.Default.NO_CONSTRUCTORS).defineField("delegate", cls2, Visibility.PRIVATE, FieldManifestation.FINAL).defineConstructor(Visibility.PUBLIC).withParameters(cls2).intercept(MethodCall.invoke(cls.getDeclaredConstructor(Integer.TYPE)).with(Integer.valueOf(OpenedClassReader.ASM_API)).andThen(FieldAccessor.ofField("delegate").setsArgumentAt(0)).andThen(implementation)).defineMethod(WRAP, cls, Visibility.PUBLIC, Ownership.STATIC).withParameters(cls2).intercept(new Implementation.Simple(new NullCheckedConstruction(cls2)));
        return (cls3 == null || cls4 == null) ? receiverTypeDefinitionIntercept : receiverTypeDefinitionIntercept.defineMethod("typePath", cls4, Visibility.PRIVATE, Ownership.STATIC).withParameters(cls3).intercept(new Implementation.Simple(new TypePathTranslator(cls3, cls4)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DynamicType.Builder<?> toMethodVisitorBuilder(ByteBuddy byteBuddy, Class<?> cls, Class<?> cls2, @MaybeNull Class<?> cls3, @MaybeNull Class<?> cls4, @MaybeNull Class<?> cls5, @MaybeNull Class<?> cls6, @MaybeNull Class<?> cls7, @MaybeNull Class<?> cls8, @MaybeNull Class<?> cls9, @MaybeNull Class<?> cls10, @MaybeNull Class<?> cls11, @MaybeNull Class<?> cls12) throws Exception {
        DynamicType.Builder visitorBuilder = toVisitorBuilder(byteBuddy, cls, cls2, cls3, cls4, FieldAccessor.ofField(LABELS).setsValue(new StackManipulation.Compound(TypeCreation.of(TypeDescription.ForLoadedType.of(HashMap.class)), Duplication.SINGLE, MethodInvocation.invoke((MethodDescription.InDefinedShape) TypeDescription.ForLoadedType.of(HashMap.class).getDeclaredMethods().filter(ElementMatchers.isConstructor().and(ElementMatchers.takesArguments(0))).getOnly())), Map.class));
        if (cls5 != null && cls6 != null) {
            visitorBuilder = visitorBuilder.defineField(LABELS, Map.class, Visibility.PRIVATE, FieldManifestation.FINAL).defineMethod(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, cls6, Visibility.PRIVATE).withParameters(cls5).intercept(new Implementation.Simple(new LabelTranslator(cls6))).defineMethod(LABELS, TypeDescription.ArrayProjection.of(TypeDescription.ForLoadedType.of(cls6)), Visibility.PRIVATE).withParameters(TypeDescription.ArrayProjection.of(TypeDescription.ForLoadedType.of(cls5))).intercept(new Implementation.Simple(new LabelArrayTranslator(cls5, cls6))).defineMethod("frames", Object[].class, Visibility.PRIVATE).withParameters(Object[].class).intercept(new Implementation.Simple(new FrameTranslator(cls5, cls6)));
        }
        if (cls9 != null && cls10 != null) {
            visitorBuilder = visitorBuilder.defineMethod("handle", cls10, Visibility.PRIVATE, Ownership.STATIC).withParameters(cls9).intercept(new Implementation.Simple(new HandleTranslator(cls9, cls10)));
        }
        if (cls11 != null && cls12 != null && cls9 != null && cls10 != null) {
            visitorBuilder = visitorBuilder.defineMethod("constantDyanmic", cls12, Visibility.PRIVATE, Ownership.STATIC).withParameters(cls11).intercept(new Implementation.Simple(new ConstantDynamicTranslator(cls11, cls12, cls9, cls10)));
        }
        return visitorBuilder.defineMethod("constant", Object.class, Visibility.PRIVATE, Ownership.STATIC).withParameters(Object.class).intercept(new Implementation.Simple(new ConstantTranslator(cls9, cls10, cls7, cls8, cls11, cls12))).defineMethod("constants", Object[].class, Visibility.PRIVATE, Ownership.STATIC).withParameters(Object[].class).intercept(new Implementation.Simple(new ConstantArrayTranslator()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MethodCall.ArgumentLoader.Factory toConvertedParameter(TypeDescription typeDescription, Class<?> cls, String str, int i, boolean z) {
        StackManipulation[] stackManipulationArr = new StackManipulation[3];
        stackManipulationArr[0] = z ? MethodVariableAccess.loadThis() : StackManipulation.Trivial.INSTANCE;
        stackManipulationArr[1] = MethodVariableAccess.REFERENCE.loadFrom(i);
        stackManipulationArr[2] = MethodInvocation.invoke((MethodDescription.InDefinedShape) typeDescription.getDeclaredMethods().filter(ElementMatchers.named(str)).getOnly());
        return new MethodCall.ArgumentLoader.ForStackManipulation(new StackManipulation.Compound(stackManipulationArr), cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static DynamicType toAttributeWrapper(DynamicType.Builder<?> builder, Class<?> cls, Class<?> cls2, TypeDescription typeDescription, TypeDescription typeDescription2) throws Exception {
        return builder.defineField("delegate", cls2, Visibility.PUBLIC, FieldManifestation.FINAL).defineConstructor(Visibility.PUBLIC).withParameters(cls2).intercept(MethodCall.invoke(cls.getDeclaredConstructor(String.class)).onSuper().with(new StackManipulation.Compound(MethodVariableAccess.REFERENCE.loadFrom(1), FieldAccess.forField((FieldDescription.InDefinedShape) new FieldDescription.ForLoadedField(cls2.getField(ModuleXmlParser.TYPE))).read()), String.class).andThen(FieldAccessor.ofField("delegate").setsArgumentAt(0))).defineMethod("attribute", cls, Visibility.PUBLIC, Ownership.STATIC).withParameters(cls2).intercept(new Implementation.Simple(new AttributeTranslator(cls, cls2, typeDescription, typeDescription2))).method(ElementMatchers.isProtected()).intercept(ExceptionMethod.throwing((Class<? extends Throwable>) UnsupportedOperationException.class)).method(ElementMatchers.named("isUnknown")).intercept(MethodCall.invoke(cls2.getMethod("isUnknown", new Class[0])).onField("delegate")).method(ElementMatchers.named("isCodeAttribute")).intercept(MethodCall.invoke(cls2.getMethod("isCodeAttribute", new Class[0])).onField("delegate")).make();
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class NullCheckedConstruction implements ByteCodeAppender {
        private final Class<?> type;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.type.equals(((NullCheckedConstruction) obj).type);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.type.hashCode();
        }

        protected NullCheckedConstruction(Class<?> cls) {
            this.type = cls;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            methodVisitor.visitVarInsn(25, 0);
            Label label = new Label();
            methodVisitor.visitJumpInsn(198, label);
            methodVisitor.visitTypeInsn(187, context.getInstrumentedType().getInternalName());
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(183, context.getInstrumentedType().getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(this.type)), false);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(3, 1);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class LabelTranslator implements ByteCodeAppender {
        protected static final String NAME = "label";
        private final Class<?> target;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.target.equals(((LabelTranslator) obj).target);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.target.hashCode();
        }

        protected LabelTranslator(Class<?> cls) {
            this.target = cls;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, CompoundList.of(context.getInstrumentedType(), methodDescription.getParameters().asTypeList()));
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, context.getInstrumentedType().getInternalName(), ClassVisitorFactory.LABELS, Type.getDescriptor(Map.class));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitMethodInsn(185, Type.getInternalName(Map.class), "get", Type.getMethodDescriptor(Type.getType((Class<?>) Object.class), Type.getType((Class<?>) Object.class)), true);
            methodVisitor.visitTypeInsn(192, Type.getInternalName(this.target));
            methodVisitor.visitVarInsn(58, 2);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitJumpInsn(199, label2);
            methodVisitor.visitTypeInsn(187, Type.getInternalName(this.target));
            methodVisitor.visitInsn(89);
            methodVisitor.visitMethodInsn(183, Type.getInternalName(this.target), "<init>", "()V", false);
            methodVisitor.visitVarInsn(58, 2);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitFieldInsn(180, context.getInstrumentedType().getInternalName(), ClassVisitorFactory.LABELS, Type.getDescriptor(Map.class));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitMethodInsn(185, Type.getInternalName(Map.class), "put", Type.getMethodDescriptor(Type.getType((Class<?>) Object.class), Type.getType((Class<?>) Object.class), Type.getType((Class<?>) Object.class)), true);
            methodVisitor.visitInsn(87);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().append(methodVisitor, Collections.singletonList(TypeDescription.ForLoadedType.of(this.target)), CompoundList.of(context.getInstrumentedType(), methodDescription.getParameters().asTypeList()));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(3, 3);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class LabelArrayTranslator implements ByteCodeAppender {
        protected static final String NAME = "labels";
        private final Class<?> sourceLabel;
        private final Class<?> targetLabel;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            LabelArrayTranslator labelArrayTranslator = (LabelArrayTranslator) obj;
            return this.sourceLabel.equals(labelArrayTranslator.sourceLabel) && this.targetLabel.equals(labelArrayTranslator.targetLabel);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.sourceLabel.hashCode()) * 31) + this.targetLabel.hashCode();
        }

        protected LabelArrayTranslator(Class<?> cls, Class<?> cls2) {
            this.sourceLabel = cls;
            this.targetLabel = cls2;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            Label label3 = new Label();
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, CompoundList.of(context.getInstrumentedType(), methodDescription.getParameters().asTypeList()));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(190);
            methodVisitor.visitTypeInsn(189, Type.getInternalName(this.targetLabel));
            methodVisitor.visitVarInsn(58, 2);
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, 3);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().append(methodVisitor, Arrays.asList(TypeDescription.ArrayProjection.of(TypeDescription.ForLoadedType.of(this.targetLabel)), TypeDescription.ForLoadedType.of(Integer.TYPE)), CompoundList.of(context.getInstrumentedType(), methodDescription.getParameters().asTypeList()));
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(190);
            methodVisitor.visitJumpInsn(162, label3);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitInsn(50);
            methodVisitor.visitMethodInsn(183, context.getInstrumentedType().getInternalName(), CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, Type.getMethodDescriptor(Type.getType(this.targetLabel), Type.getType(this.sourceLabel)), false);
            methodVisitor.visitInsn(83);
            methodVisitor.visitIincInsn(3, 1);
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label3);
            context.getFrameGeneration().chop(methodVisitor, 1, CompoundList.of(Collections.singletonList(context.getInstrumentedType()), methodDescription.getParameters().asTypeList(), Collections.singletonList(TypeDescription.ArrayProjection.of(TypeDescription.ForLoadedType.of(this.targetLabel)))));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(5, 4);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class HandleTranslator implements ByteCodeAppender {
        protected static final String NAME = "handle";
        private final Class<?> sourceHandle;
        private final Class<?> targetHandle;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HandleTranslator handleTranslator = (HandleTranslator) obj;
            return this.sourceHandle.equals(handleTranslator.sourceHandle) && this.targetHandle.equals(handleTranslator.targetHandle);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.sourceHandle.hashCode()) * 31) + this.targetHandle.hashCode();
        }

        protected HandleTranslator(Class<?> cls, Class<?> cls2) {
            this.sourceHandle = cls;
            this.targetHandle = cls2;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            methodVisitor.visitTypeInsn(187, Type.getInternalName(this.targetHandle));
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceHandle), "getTag", Type.getMethodDescriptor(Type.INT_TYPE, new Type[0]), false);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceHandle), "getOwner", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceHandle), "getName", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceHandle), "getDesc", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceHandle), "isInterface", Type.getMethodDescriptor(Type.BOOLEAN_TYPE, new Type[0]), false);
            methodVisitor.visitMethodInsn(183, Type.getInternalName(this.targetHandle), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE, Type.getType((Class<?>) String.class), Type.getType((Class<?>) String.class), Type.getType((Class<?>) String.class), Type.BOOLEAN_TYPE), false);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(7, 1);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ConstantDynamicTranslator implements ByteCodeAppender {
        protected static final String NAME = "constantDyanmic";
        private final Class<?> sourceConstantDynamic;
        private final Class<?> sourceHandle;
        private final Class<?> targetConstantDynamic;
        private final Class<?> targetHandle;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ConstantDynamicTranslator constantDynamicTranslator = (ConstantDynamicTranslator) obj;
            return this.sourceConstantDynamic.equals(constantDynamicTranslator.sourceConstantDynamic) && this.targetConstantDynamic.equals(constantDynamicTranslator.targetConstantDynamic) && this.sourceHandle.equals(constantDynamicTranslator.sourceHandle) && this.targetHandle.equals(constantDynamicTranslator.targetHandle);
        }

        public int hashCode() {
            return (((((((getClass().hashCode() * 31) + this.sourceConstantDynamic.hashCode()) * 31) + this.targetConstantDynamic.hashCode()) * 31) + this.sourceHandle.hashCode()) * 31) + this.targetHandle.hashCode();
        }

        protected ConstantDynamicTranslator(Class<?> cls, Class<?> cls2, Class<?> cls3, Class<?> cls4) {
            this.sourceConstantDynamic = cls;
            this.targetConstantDynamic = cls2;
            this.sourceHandle = cls3;
            this.targetHandle = cls4;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceConstantDynamic), "getBootstrapMethodArgumentCount", Type.getMethodDescriptor(Type.INT_TYPE, new Type[0]), false);
            methodVisitor.visitTypeInsn(189, Type.getInternalName(Object.class));
            methodVisitor.visitVarInsn(58, 1);
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, 2);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().append(methodVisitor, Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE)), methodDescription.getParameters().asTypeList());
            methodVisitor.visitVarInsn(21, 2);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(190);
            methodVisitor.visitJumpInsn(162, label2);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(21, 2);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(21, 2);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceConstantDynamic), "getBootstrapMethodArgument", Type.getMethodDescriptor(Type.getType((Class<?>) Object.class), Type.INT_TYPE), false);
            methodVisitor.visitMethodInsn(184, context.getInstrumentedType().getInternalName(), "ldc", Type.getMethodDescriptor(Type.getType((Class<?>) Object.class), Type.getType((Class<?>) Object.class)), false);
            methodVisitor.visitInsn(83);
            methodVisitor.visitIincInsn(2, 1);
            methodVisitor.visitJumpInsn(167, label);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().chop(methodVisitor, 1, CompoundList.of(methodDescription.getParameters().asTypeList(), TypeDescription.ForLoadedType.of(Object[].class)));
            methodVisitor.visitTypeInsn(187, Type.getInternalName(this.targetConstantDynamic));
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceConstantDynamic), "getName", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceConstantDynamic), "getDescriptor", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceConstantDynamic), "getBootstrapMethod", Type.getMethodDescriptor(Type.getType(this.sourceHandle), new Type[0]), false);
            methodVisitor.visitMethodInsn(184, context.getInstrumentedType().getInternalName(), "handle", Type.getMethodDescriptor(Type.getType(this.targetHandle), Type.getType(this.sourceHandle)), false);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitMethodInsn(183, Type.getInternalName(this.targetConstantDynamic), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType((Class<?>) String.class), Type.getType((Class<?>) String.class), Type.getType(this.targetHandle), Type.getType((Class<?>) Object[].class)), false);
            methodVisitor.visitInsn(176);
            methodVisitor.visitMaxs(6, 3);
            return new ByteCodeAppender.Size(6, 3);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ConstantTranslator implements ByteCodeAppender {
        protected static final String NAME = "constant";

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final Class<?> sourceConstantDynamic;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final Class<?> sourceHandle;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final Class<?> sourceType;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final Class<?> targetConstantDynamic;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final Class<?> targetHandle;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final Class<?> targetType;

        /* JADX WARN: Code duplicated, block: B:59:0x0081 A[RETURN] */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
        
            if (r2 != null) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0036, code lost:
        
            if (r2 != null) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0048, code lost:
        
            if (r2 != null) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x005a, code lost:
        
            if (r2 != null) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x006c, code lost:
        
            if (r2 != null) goto L50;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
            /*
                r4 = this;
                r0 = 1
                if (r4 != r5) goto L4
                return r0
            L4:
                r1 = 0
                if (r5 != 0) goto L8
                return r1
            L8:
                java.lang.Class r2 = r4.getClass()
                java.lang.Class r3 = r5.getClass()
                if (r2 == r3) goto L13
                return r1
            L13:
                java.lang.Class<?> r2 = r4.sourceHandle
                net.bytebuddy.asm.ClassVisitorFactory$ConstantTranslator r5 = (net.bytebuddy.asm.ClassVisitorFactory.ConstantTranslator) r5
                java.lang.Class<?> r3 = r5.sourceHandle
                if (r3 == 0) goto L24
                if (r2 == 0) goto L26
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L27
                return r1
            L24:
                if (r2 == 0) goto L27
            L26:
                return r1
            L27:
                java.lang.Class<?> r2 = r4.targetHandle
                java.lang.Class<?> r3 = r5.targetHandle
                if (r3 == 0) goto L36
                if (r2 == 0) goto L38
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L39
                return r1
            L36:
                if (r2 == 0) goto L39
            L38:
                return r1
            L39:
                java.lang.Class<?> r2 = r4.sourceType
                java.lang.Class<?> r3 = r5.sourceType
                if (r3 == 0) goto L48
                if (r2 == 0) goto L4a
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L4b
                return r1
            L48:
                if (r2 == 0) goto L4b
            L4a:
                return r1
            L4b:
                java.lang.Class<?> r2 = r4.targetType
                java.lang.Class<?> r3 = r5.targetType
                if (r3 == 0) goto L5a
                if (r2 == 0) goto L5c
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L5d
                return r1
            L5a:
                if (r2 == 0) goto L5d
            L5c:
                return r1
            L5d:
                java.lang.Class<?> r2 = r4.sourceConstantDynamic
                java.lang.Class<?> r3 = r5.sourceConstantDynamic
                if (r3 == 0) goto L6c
                if (r2 == 0) goto L6e
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L6f
                return r1
            L6c:
                if (r2 == 0) goto L6f
            L6e:
                return r1
            L6f:
                java.lang.Class<?> r2 = r4.targetConstantDynamic
                java.lang.Class<?> r5 = r5.targetConstantDynamic
                if (r5 == 0) goto L7e
                if (r2 == 0) goto L80
                boolean r5 = r2.equals(r5)
                if (r5 != 0) goto L81
                return r1
            L7e:
                if (r2 == 0) goto L81
            L80:
                return r1
            L81:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.ClassVisitorFactory.ConstantTranslator.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            int iHashCode = getClass().hashCode() * 31;
            Class<?> cls = this.sourceHandle;
            if (cls != null) {
                iHashCode += cls.hashCode();
            }
            int iHashCode2 = iHashCode * 31;
            Class<?> cls2 = this.targetHandle;
            if (cls2 != null) {
                iHashCode2 += cls2.hashCode();
            }
            int iHashCode3 = iHashCode2 * 31;
            Class<?> cls3 = this.sourceType;
            if (cls3 != null) {
                iHashCode3 += cls3.hashCode();
            }
            int iHashCode4 = iHashCode3 * 31;
            Class<?> cls4 = this.targetType;
            if (cls4 != null) {
                iHashCode4 += cls4.hashCode();
            }
            int iHashCode5 = iHashCode4 * 31;
            Class<?> cls5 = this.sourceConstantDynamic;
            if (cls5 != null) {
                iHashCode5 += cls5.hashCode();
            }
            int i = iHashCode5 * 31;
            Class<?> cls6 = this.targetConstantDynamic;
            return cls6 != null ? i + cls6.hashCode() : i;
        }

        protected ConstantTranslator(@MaybeNull Class<?> cls, @MaybeNull Class<?> cls2, @MaybeNull Class<?> cls3, @MaybeNull Class<?> cls4, @MaybeNull Class<?> cls5, @MaybeNull Class<?> cls6) {
            this.sourceHandle = cls;
            this.targetHandle = cls2;
            this.sourceType = cls3;
            this.targetType = cls4;
            this.sourceConstantDynamic = cls5;
            this.targetConstantDynamic = cls6;
        }

        /* JADX WARN: Failed to calculate best type for var: r19v0 ??
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r19v0 ??, new type: net.bytebuddy.jar.asm.MethodVisitor
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:103)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
        Caused by: java.lang.NullPointerException
         */
        /* JADX WARN: Failed to set immutable type for var: r19v0 ??
        jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r19v0 ??, new type: net.bytebuddy.jar.asm.MethodVisitor
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setImmutableType(TypeInferenceVisitor.java:111)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:102)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:102)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
        Caused by: java.lang.NullPointerException
         */
        /* JADX WARN: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r19v0 net.bytebuddy.jar.asm.MethodVisitor, new type: net.bytebuddy.jar.asm.MethodVisitor
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.applyResolvedVars(TypeSearch.java:100)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:76)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.runMultiVariableSearch(FixTypesVisitor.java:119)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
        Caused by: java.lang.NullPointerException
         */
        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            int i;
            Label label = new Label();
            Label label2 = new Label();
            Label label3 = new Label();
            if (this.sourceType == null || this.targetType == null) {
                i = 0;
            } else {
                methodVisitor.visitVarInsn(25, 0);
                methodVisitor.visitTypeInsn(193, Type.getInternalName(this.sourceType));
                methodVisitor.visitJumpInsn(153, label);
                methodVisitor.visitVarInsn(25, 0);
                methodVisitor.visitTypeInsn(192, Type.getInternalName(this.sourceType));
                i = 0;
                methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceType), "getDescriptor", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
                methodVisitor.visitMethodInsn(184, Type.getInternalName(this.targetType), "getType", Type.getMethodDescriptor(Type.getType(this.targetType), Type.getType((Class<?>) String.class)), false);
                methodVisitor.visitInsn(176);
                methodVisitor.visitLabel(label);
                context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            }
            if (this.sourceHandle != null && this.targetHandle != null) {
                methodVisitor.visitVarInsn(r6, i);
                methodVisitor.visitTypeInsn(193, Type.getInternalName(this.sourceHandle));
                methodVisitor.visitJumpInsn(153, label2);
                methodVisitor.visitVarInsn(25, i);
                methodVisitor.visitTypeInsn(192, Type.getInternalName(this.sourceHandle));
                String internalName = context.getInstrumentedType().getInternalName();
                Type type = Type.getType(this.targetHandle);
                Type[] typeArr = new Type[1];
                typeArr[i] = Type.getType(this.sourceHandle);
                methodVisitor.visitMethodInsn(184, internalName, "handle", Type.getMethodDescriptor(type, typeArr), false);
                methodVisitor.visitInsn(176);
                methodVisitor.visitLabel(label2);
                context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            }
            if (this.sourceConstantDynamic != null && this.targetConstantDynamic != null) {
                methodVisitor.visitVarInsn(r6, i);
                methodVisitor.visitTypeInsn(193, Type.getInternalName(this.sourceConstantDynamic));
                methodVisitor.visitJumpInsn(153, label3);
                methodVisitor.visitVarInsn(r6, i);
                methodVisitor.visitTypeInsn(192, Type.getInternalName(this.sourceConstantDynamic));
                String internalName2 = context.getInstrumentedType().getInternalName();
                Type type2 = Type.getType(this.targetConstantDynamic);
                Type[] typeArr2 = new Type[1];
                typeArr2[i] = Type.getType(this.sourceConstantDynamic);
                methodVisitor.visitMethodInsn(184, internalName2, "constantDyanmic", Type.getMethodDescriptor(type2, typeArr2), false);
                methodVisitor.visitInsn(176);
                methodVisitor.visitLabel(label3);
                context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            }
            methodVisitor.visitVarInsn(r6, i);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(1, 1);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ConstantArrayTranslator implements ByteCodeAppender {
        protected static final String NAME = "constants";

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass();
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        protected ConstantArrayTranslator() {
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            Label label3 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitInsn(190);
            methodVisitor.visitTypeInsn(189, Type.getInternalName(Object.class));
            methodVisitor.visitVarInsn(58, 1);
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, 2);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().append(methodVisitor, Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE)), methodDescription.getParameters().asTypeList());
            methodVisitor.visitVarInsn(21, 2);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(190);
            methodVisitor.visitJumpInsn(162, label3);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(21, 2);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(21, 2);
            methodVisitor.visitInsn(50);
            methodVisitor.visitMethodInsn(184, context.getInstrumentedType().getInternalName(), "constant", Type.getMethodDescriptor(Type.getType((Class<?>) Object.class), Type.getType((Class<?>) Object.class)), false);
            methodVisitor.visitInsn(83);
            methodVisitor.visitIincInsn(2, 1);
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label3);
            context.getFrameGeneration().chop(methodVisitor, 1, CompoundList.of(methodDescription.getParameters().asTypeList(), TypeDescription.ForLoadedType.of(Object[].class)));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(4, 3);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class FrameTranslator implements ByteCodeAppender {
        protected static final String NAME = "frames";
        private final Class<?> sourceLabel;
        private final Class<?> targetLabel;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            FrameTranslator frameTranslator = (FrameTranslator) obj;
            return this.sourceLabel.equals(frameTranslator.sourceLabel) && this.targetLabel.equals(frameTranslator.targetLabel);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.sourceLabel.hashCode()) * 31) + this.targetLabel.hashCode();
        }

        protected FrameTranslator(Class<?> cls, Class<?> cls2) {
            this.sourceLabel = cls;
            this.targetLabel = cls2;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            Label label3 = new Label();
            Label label4 = new Label();
            Label label5 = new Label();
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, CompoundList.of(context.getInstrumentedType(), methodDescription.getParameters().asTypeList()));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(190);
            methodVisitor.visitTypeInsn(189, Type.getInternalName(Object.class));
            methodVisitor.visitVarInsn(58, 2);
            methodVisitor.visitInsn(3);
            methodVisitor.visitVarInsn(54, 3);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().append(methodVisitor, Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE)), CompoundList.of(context.getInstrumentedType(), methodDescription.getParameters().asTypeList()));
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitInsn(190);
            methodVisitor.visitJumpInsn(162, label4);
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitInsn(50);
            methodVisitor.visitTypeInsn(193, Type.getInternalName(this.sourceLabel));
            methodVisitor.visitJumpInsn(153, label5);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitInsn(50);
            methodVisitor.visitTypeInsn(192, Type.getInternalName(this.sourceLabel));
            methodVisitor.visitMethodInsn(183, context.getInstrumentedType().getInternalName(), CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, Type.getMethodDescriptor(Type.getType(this.targetLabel), Type.getType(this.sourceLabel)), false);
            methodVisitor.visitJumpInsn(167, label3);
            methodVisitor.visitLabel(label5);
            context.getFrameGeneration().full(methodVisitor, Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE)), CompoundList.of(Collections.singletonList(context.getInstrumentedType()), methodDescription.getParameters().asTypeList(), Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE))));
            methodVisitor.visitVarInsn(25, 1);
            methodVisitor.visitVarInsn(21, 3);
            methodVisitor.visitInsn(50);
            methodVisitor.visitLabel(label3);
            context.getFrameGeneration().full(methodVisitor, Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE), TypeDescription.ForLoadedType.of(Object.class)), CompoundList.of(Collections.singletonList(context.getInstrumentedType()), methodDescription.getParameters().asTypeList(), Arrays.asList(TypeDescription.ForLoadedType.of(Object[].class), TypeDescription.ForLoadedType.of(Integer.TYPE))));
            methodVisitor.visitInsn(83);
            methodVisitor.visitIincInsn(3, 1);
            methodVisitor.visitJumpInsn(167, label2);
            methodVisitor.visitLabel(label4);
            context.getFrameGeneration().chop(methodVisitor, 1, CompoundList.of(Collections.singletonList(context.getInstrumentedType()), methodDescription.getParameters().asTypeList(), Collections.singletonList(TypeDescription.ForLoadedType.of(Object[].class))));
            methodVisitor.visitVarInsn(25, 2);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(5, 4);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class TypePathTranslator implements ByteCodeAppender {
        protected static final String NAME = "typePath";
        private final Class<?> sourceTypePath;
        private final Class<?> targetTypePath;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TypePathTranslator typePathTranslator = (TypePathTranslator) obj;
            return this.sourceTypePath.equals(typePathTranslator.sourceTypePath) && this.targetTypePath.equals(typePathTranslator.targetTypePath);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.sourceTypePath.hashCode()) * 31) + this.targetTypePath.hashCode();
        }

        protected TypePathTranslator(Class<?> cls, Class<?> cls2) {
            this.sourceTypePath = cls;
            this.targetTypePath = cls2;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitJumpInsn(167, label2);
            context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            methodVisitor.visitLabel(label);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(182, Type.getInternalName(this.sourceTypePath), "toString", Type.getMethodDescriptor(Type.getType((Class<?>) String.class), new Type[0]), false);
            methodVisitor.visitMethodInsn(184, Type.getInternalName(this.targetTypePath), "fromString", Type.getMethodDescriptor(Type.getType(this.targetTypePath), Type.getType((Class<?>) String.class)), false);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().same1(methodVisitor, TypeDescription.ForLoadedType.of(this.targetTypePath), methodDescription.getParameters().asTypeList());
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(1, 2);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class AttributeTranslator implements ByteCodeAppender {
        protected static final String NAME = "attribute";
        private final Class<?> sourceAttribute;
        private final TypeDescription sourceWrapper;
        private final Class<?> targetAttribute;
        private final TypeDescription targetWrapper;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AttributeTranslator attributeTranslator = (AttributeTranslator) obj;
            return this.sourceAttribute.equals(attributeTranslator.sourceAttribute) && this.targetAttribute.equals(attributeTranslator.targetAttribute) && this.sourceWrapper.equals(attributeTranslator.sourceWrapper) && this.targetWrapper.equals(attributeTranslator.targetWrapper);
        }

        public int hashCode() {
            return (((((((getClass().hashCode() * 31) + this.sourceAttribute.hashCode()) * 31) + this.targetAttribute.hashCode()) * 31) + this.sourceWrapper.hashCode()) * 31) + this.targetWrapper.hashCode();
        }

        protected AttributeTranslator(Class<?> cls, Class<?> cls2, TypeDescription typeDescription, TypeDescription typeDescription2) {
            this.sourceAttribute = cls;
            this.targetAttribute = cls2;
            this.sourceWrapper = typeDescription;
            this.targetWrapper = typeDescription2;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Label label = new Label();
            Label label2 = new Label();
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitJumpInsn(199, label);
            methodVisitor.visitInsn(1);
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label);
            context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitTypeInsn(193, this.targetWrapper.getInternalName());
            methodVisitor.visitJumpInsn(153, label2);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitTypeInsn(192, this.targetWrapper.getInternalName());
            methodVisitor.visitFieldInsn(180, this.targetWrapper.getInternalName(), "delegate", Type.getDescriptor(this.sourceAttribute));
            methodVisitor.visitInsn(176);
            methodVisitor.visitLabel(label2);
            context.getFrameGeneration().same(methodVisitor, methodDescription.getParameters().asTypeList());
            methodVisitor.visitTypeInsn(187, this.sourceWrapper.getInternalName());
            methodVisitor.visitInsn(89);
            methodVisitor.visitVarInsn(25, 0);
            methodVisitor.visitMethodInsn(183, this.sourceWrapper.getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(this.targetAttribute)), false);
            methodVisitor.visitInsn(176);
            return new ByteCodeAppender.Size(3, 1);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class CreateClassVisitorFactory<S> implements PrivilegedAction<ClassVisitorFactory<S>> {
        private final ByteBuddy byteBuddy;
        private final Class<S> classVisitor;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            CreateClassVisitorFactory createClassVisitorFactory = (CreateClassVisitorFactory) obj;
            return this.classVisitor.equals(createClassVisitorFactory.classVisitor) && this.byteBuddy.equals(createClassVisitorFactory.byteBuddy);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.classVisitor.hashCode()) * 31) + this.byteBuddy.hashCode();
        }

        protected CreateClassVisitorFactory(Class<S> cls, ByteBuddy byteBuddy) {
            this.classVisitor = cls;
            this.byteBuddy = byteBuddy;
        }

        /* JADX WARN: Code duplicated, block: B:101:0x0606 A[Catch: Exception -> 0x02d2, TryCatch #7 {Exception -> 0x02d2, blocks: (B:42:0x0283, B:46:0x02db, B:47:0x02e3, B:50:0x02eb, B:52:0x0325, B:124:0x0728, B:55:0x034c, B:56:0x036c, B:58:0x036f, B:60:0x0379, B:62:0x037f, B:98:0x0600, B:114:0x064a, B:116:0x0660, B:119:0x068d, B:121:0x06b9, B:123:0x070e, B:112:0x063a, B:101:0x0606, B:103:0x060c, B:64:0x03c9, B:66:0x03d3, B:68:0x03d9, B:69:0x0414, B:71:0x041a, B:72:0x0456, B:74:0x045e, B:76:0x0464, B:77:0x04b0, B:79:0x04bc, B:81:0x04c2, B:82:0x0504, B:84:0x0510, B:86:0x0516, B:88:0x0526, B:89:0x0564, B:90:0x05a1, B:92:0x05af, B:95:0x05bd, B:97:0x05ea, B:125:0x0745, B:126:0x079d), top: B:164:0x0283 }] */
        /* JADX WARN: Code duplicated, block: B:107:0x0615  */
        /* JADX WARN: Code duplicated, block: B:181:0x0604 A[SYNTHETIC] */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.security.PrivilegedAction
        public ClassVisitorFactory<S> run() {
            CreateClassVisitorFactory<S> createClassVisitorFactory;
            String str;
            DynamicType attributeWrapper;
            DynamicType attributeWrapper2;
            int i;
            DynamicType dynamicType;
            DynamicType dynamicType2;
            String str2;
            String str3;
            String str4;
            String str5;
            String str6;
            String str7;
            boolean z;
            Method method;
            String str8;
            HashMap map;
            DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<T> receiverTypeDefinitionIntercept;
            DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<T> receiverTypeDefinitionIntercept2;
            DynamicType.Builder builder;
            String str9;
            int i2;
            String str10;
            String str11;
            DynamicType.Builder visitorBuilder;
            DynamicType.Builder builder2;
            String str12;
            String str13 = "frames";
            String str14 = "constants";
            String str15 = "attribute";
            String str16 = "constant";
            String str17 = "handle";
            String str18 = ClassVisitorFactory.LABELS;
            String str19 = "delegate";
            if (!"ClassVisitor".equals(this.classVisitor.getSimpleName())) {
                throw new IllegalArgumentException("Expected a class named ClassVisitor: " + this.classVisitor);
            }
            try {
                String name2 = this.classVisitor.getPackage().getName();
                HashMap map2 = new HashMap();
                Iterator it = Arrays.asList(Attribute.class, Label.class, Type.class, TypePath.class, Handle.class, ConstantDynamic.class).iterator();
                while (true) {
                    str = ".";
                    if (!it.hasNext()) {
                        break;
                    }
                    Class cls = (Class) it.next();
                    Iterator it2 = it;
                    try {
                        str12 = str19;
                        try {
                            map2.put(cls, Class.forName(name2 + "." + cls.getSimpleName(), false, this.classVisitor.getClassLoader()));
                        } catch (ClassNotFoundException unused) {
                        }
                    } catch (ClassNotFoundException unused2) {
                        str12 = str19;
                    }
                    it = it2;
                    str19 = str12;
                    throw new IllegalArgumentException("Failed to generate factory for " + createClassVisitorFactory.classVisitor.getName(), e);
                }
                String str20 = str19;
                if (map2.containsKey(Label.class)) {
                    map2.put(Label[].class, Class.forName("[L" + ((Class) map2.get(Label.class)).getName() + ";", false, this.classVisitor.getClassLoader()));
                }
                HashMap map3 = new HashMap();
                HashMap map4 = new HashMap();
                Iterator it3 = Arrays.asList(ClassVisitor.class, AnnotationVisitor.class, ModuleVisitor.class, RecordComponentVisitor.class, FieldVisitor.class, MethodVisitor.class).iterator();
                while (it3.hasNext()) {
                    Class cls2 = (Class) it3.next();
                    Iterator it4 = it3;
                    try {
                        str10 = name2;
                        try {
                            str11 = str;
                            try {
                                Class<?> cls3 = Class.forName(name2 + str + cls2.getSimpleName(), false, this.classVisitor.getClassLoader());
                                if (cls2 == MethodVisitor.class) {
                                    DynamicType.Builder methodVisitorBuilder = ClassVisitorFactory.toMethodVisitorBuilder(this.byteBuddy, cls2, cls3, TypePath.class, (Class) map2.get(TypePath.class), Label.class, (Class) map2.get(Label.class), Type.class, (Class) map2.get(Type.class), Handle.class, (Class) map2.get(Handle.class), ConstantDynamic.class, (Class) map2.get(ConstantDynamic.class));
                                    visitorBuilder = ClassVisitorFactory.toMethodVisitorBuilder(this.byteBuddy, cls3, cls2, (Class) map2.get(TypePath.class), TypePath.class, (Class) map2.get(Label.class), Label.class, (Class) map2.get(Type.class), Type.class, (Class) map2.get(Handle.class), Handle.class, (Class) map2.get(ConstantDynamic.class), ConstantDynamic.class);
                                    builder2 = methodVisitorBuilder;
                                } else {
                                    DynamicType.Builder visitorBuilder2 = ClassVisitorFactory.toVisitorBuilder(this.byteBuddy, cls2, cls3, TypePath.class, (Class) map2.get(TypePath.class), new Implementation.Simple(MethodReturn.VOID));
                                    visitorBuilder = ClassVisitorFactory.toVisitorBuilder(this.byteBuddy, cls3, cls2, (Class) map2.get(TypePath.class), TypePath.class, new Implementation.Simple(MethodReturn.VOID));
                                    builder2 = visitorBuilder2;
                                }
                                map3.put(cls2, cls3);
                                map4.put(cls2, builder2);
                                map4.put(cls3, visitorBuilder);
                            } catch (ClassNotFoundException unused3) {
                                str13 = str13;
                                str14 = str14;
                            }
                        } catch (ClassNotFoundException unused4) {
                            str11 = str;
                        }
                    } catch (ClassNotFoundException unused5) {
                        str10 = name2;
                    }
                    it3 = it4;
                    name2 = str10;
                    str = str11;
                    str14 = str14;
                    str13 = str13;
                }
                String str21 = str13;
                String str22 = str14;
                ArrayList arrayList = new ArrayList();
                HashMap map5 = new HashMap();
                if (map2.containsKey(Attribute.class)) {
                    try {
                        DynamicType.Builder<T> builderSubclass = this.byteBuddy.subclass((Class) Attribute.class, (ConstructorStrategy) ConstructorStrategy.Default.NO_CONSTRUCTORS);
                        DynamicType.Builder<T> builderSubclass2 = this.byteBuddy.subclass((Class) map2.get(Attribute.class), (ConstructorStrategy) ConstructorStrategy.Default.NO_CONSTRUCTORS);
                        attributeWrapper = ClassVisitorFactory.toAttributeWrapper(builderSubclass, Attribute.class, (Class) map2.get(Attribute.class), builderSubclass.toTypeDescription(), builderSubclass2.toTypeDescription());
                        arrayList.add(attributeWrapper);
                        attributeWrapper2 = ClassVisitorFactory.toAttributeWrapper(builderSubclass2, (Class) map2.get(Attribute.class), Attribute.class, builderSubclass2.toTypeDescription(), builderSubclass.toTypeDescription());
                        arrayList.add(attributeWrapper2);
                    } catch (Exception e) {
                        e = e;
                        createClassVisitorFactory = this;
                    }
                } else {
                    attributeWrapper = null;
                    attributeWrapper2 = null;
                }
                Iterator it5 = map3.entrySet().iterator();
                while (it5.hasNext()) {
                    Map.Entry entry = (Map.Entry) it5.next();
                    DynamicType.Builder builder3 = (DynamicType.Builder) map4.get(entry.getKey());
                    Iterator it6 = it5;
                    DynamicType.Builder builder4 = (DynamicType.Builder) map4.get(entry.getValue());
                    Method[] methods = ((Class) entry.getKey()).getMethods();
                    int length = methods.length;
                    ArrayList arrayList2 = arrayList;
                    DynamicType.Builder builder5 = builder4;
                    HashMap map6 = map3;
                    int i3 = 0;
                    DynamicType.Builder builder6 = builder3;
                    while (i3 < length) {
                        Method method2 = methods[i3];
                        Method[] methodArr = methods;
                        int i4 = length;
                        if (method2.getDeclaringClass() == Object.class) {
                            dynamicType = attributeWrapper;
                            dynamicType2 = attributeWrapper2;
                            i = i3;
                            str2 = str17;
                            str3 = str18;
                            builder = builder6;
                            str8 = str20;
                            map = map6;
                            str4 = str15;
                            str5 = str22;
                            str6 = str21;
                            str7 = str16;
                        } else {
                            Class<?>[] parameterTypes = method2.getParameterTypes();
                            int length2 = parameterTypes.length;
                            i = i3;
                            Class<?>[] clsArr = new Class[length2];
                            DynamicType.Builder builder7 = builder5;
                            DynamicType.Builder builder8 = builder6;
                            ArrayList arrayList3 = new ArrayList(parameterTypes.length);
                            ArrayList arrayList4 = new ArrayList(length2);
                            dynamicType = attributeWrapper;
                            dynamicType2 = attributeWrapper2;
                            int i5 = 1;
                            int i6 = 0;
                            boolean z2 = false;
                            while (true) {
                                if (i6 >= parameterTypes.length) {
                                    str2 = str17;
                                    str3 = str18;
                                    str4 = str15;
                                    str5 = str22;
                                    str6 = str21;
                                    str7 = str16;
                                    z = false;
                                    break;
                                }
                                String str23 = str16;
                                if (entry.getKey() == MethodVisitor.class && parameterTypes[i6] == Label.class) {
                                    clsArr[i6] = (Class) map2.get(Label.class);
                                    str9 = str17;
                                    arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), clsArr[i6], CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, i5, true));
                                    arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), parameterTypes[i6], CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, i5, true));
                                } else {
                                    str9 = str17;
                                    if (entry.getKey() == MethodVisitor.class && parameterTypes[i6] == Label[].class) {
                                        clsArr[i6] = (Class) map2.get(Label[].class);
                                        arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), clsArr[i6], str18, i5, true));
                                        arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), parameterTypes[i6], str18, i5, true));
                                    } else {
                                        if (parameterTypes[i6] == TypePath.class) {
                                            clsArr[i6] = (Class) map2.get(TypePath.class);
                                            arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), clsArr[i6], "typePath", i5, false));
                                            arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), parameterTypes[i6], "typePath", i5, false));
                                        } else if (entry.getKey() == MethodVisitor.class && parameterTypes[i6] == Handle.class) {
                                            clsArr[i6] = (Class) map2.get(Handle.class);
                                            str3 = str18;
                                            arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), clsArr[i6], str9, i5, false));
                                            arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), parameterTypes[i6], str9, i5, false));
                                            str2 = str9;
                                            str4 = str15;
                                            str5 = str22;
                                            str6 = str21;
                                            str7 = str23;
                                        } else {
                                            str3 = str18;
                                            if (entry.getKey() == MethodVisitor.class && parameterTypes[i6] == Object.class) {
                                                clsArr[i6] = Object.class;
                                                str2 = str9;
                                                arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), Object.class, str23, i5, false));
                                                arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), Object.class, str23, i5, false));
                                                str4 = str15;
                                                str5 = str22;
                                                str6 = str21;
                                                str7 = str23;
                                            } else {
                                                str2 = str9;
                                                if (entry.getKey() == MethodVisitor.class && parameterTypes[i6] == Object[].class) {
                                                    clsArr[i6] = Object[].class;
                                                    if (method2.getName().equals("visitFrame")) {
                                                        String str24 = str21;
                                                        str7 = str23;
                                                        arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), Object[].class, str24, i5, true));
                                                        arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), Object[].class, str24, i5, true));
                                                        String str25 = str22;
                                                        str6 = str24;
                                                        str4 = str15;
                                                        str5 = str25;
                                                    } else {
                                                        String str26 = str21;
                                                        str7 = str23;
                                                        String str27 = str22;
                                                        str6 = str26;
                                                        arrayList3.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getKey())).toTypeDescription(), Object[].class, str27, i5, false));
                                                        arrayList4.add(ClassVisitorFactory.toConvertedParameter(((DynamicType.Builder) map4.get(entry.getValue())).toTypeDescription(), Object[].class, str27, i5, false));
                                                        str4 = str15;
                                                        str5 = str27;
                                                    }
                                                } else {
                                                    String str28 = str21;
                                                    str7 = str23;
                                                    String str29 = str22;
                                                    str6 = str28;
                                                    Class<?> cls4 = parameterTypes[i6];
                                                    if (cls4 == Attribute.class) {
                                                        clsArr[i6] = (Class) map2.get(Attribute.class);
                                                        if (dynamicType == null || dynamicType2 == null) {
                                                            str4 = str15;
                                                            str5 = str29;
                                                            z2 = true;
                                                        } else {
                                                            str4 = str15;
                                                            str5 = str29;
                                                            arrayList3.add(ClassVisitorFactory.toConvertedParameter(dynamicType2.getTypeDescription(), (Class) map2.get(Attribute.class), str4, i5, false));
                                                            arrayList4.add(ClassVisitorFactory.toConvertedParameter(dynamicType.getTypeDescription(), Attribute.class, str4, i5, false));
                                                        }
                                                    } else {
                                                        str4 = str15;
                                                        str5 = str29;
                                                        clsArr[i6] = cls4;
                                                        arrayList3.add(new MethodCall.ArgumentLoader.ForMethodParameter.Factory(i6));
                                                        arrayList4.add(new MethodCall.ArgumentLoader.ForMethodParameter.Factory(i6));
                                                    }
                                                }
                                            }
                                        }
                                        if (clsArr[i6] == null) {
                                            z = true;
                                            break;
                                        }
                                        if (parameterTypes[i6] != Long.TYPE || parameterTypes[i6] == Double.TYPE) {
                                            i2 = 2;
                                        } else {
                                            i2 = 1;
                                        }
                                        i5 += i2;
                                        i6++;
                                        str16 = str7;
                                        str18 = str3;
                                        str21 = str6;
                                        str22 = str5;
                                        str15 = str4;
                                        str17 = str2;
                                    }
                                }
                                str4 = str15;
                                str5 = str22;
                                str6 = str21;
                                str7 = str23;
                                str2 = str9;
                                str3 = str18;
                                if (clsArr[i6] == null) {
                                    z = true;
                                    break;
                                }
                                if (parameterTypes[i6] != Long.TYPE) {
                                    i2 = 2;
                                } else {
                                    i2 = 2;
                                }
                                i5 += i2;
                                i6++;
                                str16 = str7;
                                str18 = str3;
                                str21 = str6;
                                str22 = str5;
                                str15 = str4;
                                str17 = str2;
                            }
                            if (z) {
                                method = null;
                                z2 = true;
                            } else {
                                try {
                                    method = ((Class) entry.getValue()).getMethod(method2.getName(), clsArr);
                                } catch (NoSuchMethodException unused6) {
                                    method = null;
                                    z2 = true;
                                }
                            }
                            if (z2) {
                                receiverTypeDefinitionIntercept = builder8.method(ElementMatchers.is(method2)).intercept(ExceptionMethod.throwing((Class<? extends Throwable>) UnsupportedOperationException.class));
                                if (method != null) {
                                    receiverTypeDefinitionIntercept2 = builder7.method(ElementMatchers.is(method)).intercept(ExceptionMethod.throwing((Class<? extends Throwable>) UnsupportedOperationException.class));
                                    str8 = str20;
                                    map = map6;
                                    str4 = str4;
                                } else {
                                    builder = receiverTypeDefinitionIntercept;
                                    builder5 = builder7;
                                    str8 = str20;
                                    map = map6;
                                    str4 = str4;
                                }
                            } else {
                                str8 = str20;
                                MethodCall methodCallWith = MethodCall.invoke(method).onField(str8).with(arrayList3);
                                MethodCall methodCallWith2 = MethodCall.invoke(method2).onField(str8).with(arrayList4);
                                map = map6;
                                Class cls5 = (Class) map.get(method2.getReturnType());
                                if (cls5 != null) {
                                    methodCallWith = MethodCall.invoke((MethodDescription) ((DynamicType.Builder) map4.get(method2.getReturnType())).toTypeDescription().getDeclaredMethods().filter(ElementMatchers.named(ClassVisitorFactory.WRAP)).getOnly()).withMethodCall(methodCallWith);
                                    methodCallWith2 = MethodCall.invoke((MethodDescription) ((DynamicType.Builder) map4.get(cls5)).toTypeDescription().getDeclaredMethods().filter(ElementMatchers.named(ClassVisitorFactory.WRAP)).getOnly()).withMethodCall(methodCallWith2);
                                }
                                receiverTypeDefinitionIntercept = builder8.method(ElementMatchers.is(method2)).intercept(methodCallWith);
                                receiverTypeDefinitionIntercept2 = builder7.method(ElementMatchers.is(method)).intercept(methodCallWith2);
                            }
                            DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<T> receiverTypeDefinition = receiverTypeDefinitionIntercept;
                            builder5 = receiverTypeDefinitionIntercept2;
                            builder = receiverTypeDefinition;
                        }
                        i3 = i + 1;
                        map6 = map;
                        methods = methodArr;
                        length = i4;
                        str16 = str7;
                        attributeWrapper2 = dynamicType2;
                        str17 = str2;
                        builder6 = builder;
                        str21 = str6;
                        str22 = str5;
                        str15 = str4;
                        attributeWrapper = dynamicType;
                        str20 = str8;
                        str18 = str3;
                    }
                    DynamicType dynamicType3 = attributeWrapper;
                    String str30 = str18;
                    String str31 = str20;
                    String str32 = str15;
                    String str33 = str22;
                    String str34 = str21;
                    String str35 = str16;
                    DynamicType.Unloaded<T> unloadedMake = builder6.make();
                    DynamicType.Unloaded<T> unloadedMake2 = builder5.make();
                    HashMap map7 = map5;
                    map7.put(entry.getKey(), unloadedMake.getTypeDescription());
                    map7.put(entry.getValue(), unloadedMake2.getTypeDescription());
                    arrayList2.add(unloadedMake);
                    arrayList2.add(unloadedMake2);
                    arrayList = arrayList2;
                    map5 = map7;
                    map3 = map6;
                    it5 = it6;
                    str16 = str35;
                    attributeWrapper2 = attributeWrapper2;
                    attributeWrapper = dynamicType3;
                    str17 = str17;
                    str21 = str34;
                    str22 = str33;
                    str15 = str32;
                    str20 = str31;
                    str18 = str30;
                }
                ArrayList arrayList5 = arrayList;
                HashMap map8 = map5;
                MultipleParentClassLoader.Builder builder9 = new MultipleParentClassLoader.Builder(false);
                Class[] clsArr2 = new Class[2];
                clsArr2[0] = ClassVisitor.class;
                createClassVisitorFactory = this;
                try {
                    clsArr2[1] = createClassVisitorFactory.classVisitor;
                    ClassLoader classLoaderBuild = builder9.appendMostSpecific((Class<?>[]) clsArr2).build();
                    ClassVisitorFactory<S> classVisitorFactory = (ClassVisitorFactory) createClassVisitorFactory.byteBuddy.subclass((Class) ClassVisitorFactory.class, (ConstructorStrategy) ConstructorStrategy.Default.IMITATE_SUPER_CLASS_OPENING).method(ElementMatchers.named(ClassVisitorFactory.WRAP)).intercept(MethodCall.construct((MethodDescription) ((TypeDescription) map8.get(createClassVisitorFactory.classVisitor)).getDeclaredMethods().filter(ElementMatchers.isConstructor()).getOnly()).withArgument(0)).method(ElementMatchers.named("unwrap")).intercept(MethodCall.construct((MethodDescription) ((TypeDescription) map8.get(ClassVisitor.class)).getDeclaredMethods().filter(ElementMatchers.isConstructor()).getOnly()).withArgument(0).withAssigner(Assigner.DEFAULT, Assigner.Typing.DYNAMIC)).make().include(arrayList5).load(classLoaderBuild).getLoaded().getConstructor(Class.class).newInstance(createClassVisitorFactory.classVisitor);
                    if ((classLoaderBuild instanceof MultipleParentClassLoader) && classLoaderBuild != ClassVisitor.class.getClassLoader() && classLoaderBuild != createClassVisitorFactory.classVisitor.getClassLoader() && !((MultipleParentClassLoader) classLoaderBuild).seal()) {
                        throw new IllegalStateException("Failed to seal multiple parent class loader: " + classLoaderBuild);
                    }
                    return classVisitorFactory;
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                createClassVisitorFactory = this;
            }
        }
    }
}
