package net.bytebuddy.dynamic.scaffold;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.method.ParameterList;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.RecordComponentDescription;
import net.bytebuddy.description.type.RecordComponentList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.TypeResolutionStrategy;
import net.bytebuddy.dynamic.scaffold.inline.MethodRebaseResolver;
import net.bytebuddy.dynamic.scaffold.inline.RebaseImplementationTarget;
import net.bytebuddy.dynamic.scaffold.subclass.SubclassImplementationTarget;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.LoadedTypeInitializer;
import net.bytebuddy.implementation.attribute.AnnotationAppender;
import net.bytebuddy.implementation.attribute.AnnotationRetention;
import net.bytebuddy.implementation.attribute.AnnotationValueFilter;
import net.bytebuddy.implementation.attribute.FieldAttributeAppender;
import net.bytebuddy.implementation.attribute.MethodAttributeAppender;
import net.bytebuddy.implementation.attribute.RecordComponentAttributeAppender;
import net.bytebuddy.implementation.attribute.TypeAttributeAppender;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.AnnotationVisitor;
import net.bytebuddy.jar.asm.ClassReader;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.ConstantDynamic;
import net.bytebuddy.jar.asm.FieldVisitor;
import net.bytebuddy.jar.asm.Handle;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import net.bytebuddy.jar.asm.RecordComponentVisitor;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.jar.asm.TypePath;
import net.bytebuddy.jar.asm.commons.ClassRemapper;
import net.bytebuddy.jar.asm.commons.Remapper;
import net.bytebuddy.jar.asm.commons.SimpleRemapper;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.OpenedClassReader;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.nullability.UnknownNull;
import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import net.bytebuddy.utility.visitor.ContextClassVisitor;
import net.bytebuddy.utility.visitor.MetadataAwareClassVisitor;

/* JADX INFO: loaded from: classes12.dex */
public interface TypeWriter<T> {
    public static final String DUMP_PROPERTY = "net.bytebuddy.dump";

    DynamicType.Unloaded<T> make(TypeResolutionStrategy.Resolved resolved);

    ContextClassVisitor wrap(ClassVisitor classVisitor, int i, int i2);

    public interface FieldPool {
        Record target(FieldDescription fieldDescription);

        public interface Record {
            void apply(ClassVisitor classVisitor, AnnotationValueFilter.Factory factory);

            void apply(FieldVisitor fieldVisitor, AnnotationValueFilter.Factory factory);

            FieldDescription getField();

            FieldAttributeAppender getFieldAppender();

            boolean isImplicit();

            @MaybeNull
            Object resolveDefault(@MaybeNull Object obj);

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForImplicitField implements Record {
                private final FieldDescription fieldDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((ForImplicitField) obj).fieldDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.fieldDescription.hashCode();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public boolean isImplicit() {
                    return true;
                }

                public ForImplicitField(FieldDescription fieldDescription) {
                    this.fieldDescription = fieldDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public FieldDescription getField() {
                    return this.fieldDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public FieldAttributeAppender getFieldAppender() {
                    throw new IllegalStateException("An implicit field record does not expose a field appender: " + this);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public Object resolveDefault(@MaybeNull Object obj) {
                    throw new IllegalStateException("An implicit field record does not expose a default value: " + this);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public void apply(ClassVisitor classVisitor, AnnotationValueFilter.Factory factory) {
                    FieldVisitor fieldVisitorVisitField = classVisitor.visitField(this.fieldDescription.getActualModifiers(), this.fieldDescription.getInternalName(), this.fieldDescription.getDescriptor(), this.fieldDescription.getGenericSignature(), FieldDescription.NO_DEFAULT_VALUE);
                    if (fieldVisitorVisitField != null) {
                        FieldAttributeAppender.ForInstrumentedField forInstrumentedField = FieldAttributeAppender.ForInstrumentedField.INSTANCE;
                        FieldDescription fieldDescription = this.fieldDescription;
                        forInstrumentedField.apply(fieldVisitorVisitField, fieldDescription, factory.on(fieldDescription));
                        fieldVisitorVisitField.visitEnd();
                    }
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public void apply(FieldVisitor fieldVisitor, AnnotationValueFilter.Factory factory) {
                    throw new IllegalStateException("An implicit field record is not intended for partial application: " + this);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForExplicitField implements Record {
                private final FieldAttributeAppender attributeAppender;

                @MaybeNull
                @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                private final Object defaultValue;
                private final FieldDescription fieldDescription;

                /* JADX WARN: Code restructure failed: missing block: B:20:0x002f, code lost:
                
                    if (r2 != null) goto L21;
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
                        net.bytebuddy.implementation.attribute.FieldAttributeAppender r2 = r4.attributeAppender
                        net.bytebuddy.dynamic.scaffold.TypeWriter$FieldPool$Record$ForExplicitField r5 = (net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record.ForExplicitField) r5
                        net.bytebuddy.implementation.attribute.FieldAttributeAppender r3 = r5.attributeAppender
                        boolean r2 = r2.equals(r3)
                        if (r2 != 0) goto L20
                        return r1
                    L20:
                        java.lang.Object r2 = r4.defaultValue
                        java.lang.Object r3 = r5.defaultValue
                        if (r3 == 0) goto L2f
                        if (r2 == 0) goto L31
                        boolean r2 = r2.equals(r3)
                        if (r2 != 0) goto L32
                        return r1
                    L2f:
                        if (r2 == 0) goto L32
                    L31:
                        return r1
                    L32:
                        net.bytebuddy.description.field.FieldDescription r2 = r4.fieldDescription
                        net.bytebuddy.description.field.FieldDescription r5 = r5.fieldDescription
                        boolean r5 = r2.equals(r5)
                        if (r5 != 0) goto L3d
                        return r1
                    L3d:
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record.ForExplicitField.equals(java.lang.Object):boolean");
                }

                public int hashCode() {
                    int iHashCode = ((getClass().hashCode() * 31) + this.attributeAppender.hashCode()) * 31;
                    Object obj = this.defaultValue;
                    if (obj != null) {
                        iHashCode += obj.hashCode();
                    }
                    return (iHashCode * 31) + this.fieldDescription.hashCode();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public boolean isImplicit() {
                    return false;
                }

                public ForExplicitField(FieldAttributeAppender fieldAttributeAppender, @MaybeNull Object obj, FieldDescription fieldDescription) {
                    this.attributeAppender = fieldAttributeAppender;
                    this.defaultValue = obj;
                    this.fieldDescription = fieldDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public FieldDescription getField() {
                    return this.fieldDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public FieldAttributeAppender getFieldAppender() {
                    return this.attributeAppender;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                @MaybeNull
                public Object resolveDefault(@MaybeNull Object obj) {
                    Object obj2 = this.defaultValue;
                    return obj2 == null ? obj : obj2;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public void apply(ClassVisitor classVisitor, AnnotationValueFilter.Factory factory) {
                    FieldVisitor fieldVisitorVisitField = classVisitor.visitField(this.fieldDescription.getActualModifiers(), this.fieldDescription.getInternalName(), this.fieldDescription.getDescriptor(), this.fieldDescription.getGenericSignature(), resolveDefault(FieldDescription.NO_DEFAULT_VALUE));
                    if (fieldVisitorVisitField != null) {
                        FieldAttributeAppender fieldAttributeAppender = this.attributeAppender;
                        FieldDescription fieldDescription = this.fieldDescription;
                        fieldAttributeAppender.apply(fieldVisitorVisitField, fieldDescription, factory.on(fieldDescription));
                        fieldVisitorVisitField.visitEnd();
                    }
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record
                public void apply(FieldVisitor fieldVisitor, AnnotationValueFilter.Factory factory) {
                    FieldAttributeAppender fieldAttributeAppender = this.attributeAppender;
                    FieldDescription fieldDescription = this.fieldDescription;
                    fieldAttributeAppender.apply(fieldVisitor, fieldDescription, factory.on(fieldDescription));
                }
            }
        }

        public enum Disabled implements FieldPool {
            INSTANCE;

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool
            public Record target(FieldDescription fieldDescription) {
                throw new IllegalStateException("Cannot look up field from disabled pool");
            }
        }
    }

    public interface MethodPool {
        Record target(MethodDescription methodDescription);

        public interface Record {
            void apply(ClassVisitor classVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory);

            void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory);

            void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory);

            ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context);

            void applyHead(MethodVisitor methodVisitor);

            MethodDescription getMethod();

            Sort getSort();

            Visibility getVisibility();

            Record prepend(ByteCodeAppender byteCodeAppender);

            public enum Sort {
                SKIPPED(false, false),
                DEFINED(true, false),
                IMPLEMENTED(true, true);

                private final boolean define;
                private final boolean implement;

                Sort(boolean z, boolean z2) {
                    this.define = z;
                    this.implement = z2;
                }

                public boolean isDefined() {
                    return this.define;
                }

                public boolean isImplemented() {
                    return this.implement;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForNonImplementedMethod implements Record {
                private final MethodDescription methodDescription;

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void apply(ClassVisitor classVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory) {
                }

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.methodDescription.equals(((ForNonImplementedMethod) obj).methodDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.methodDescription.hashCode();
                }

                public ForNonImplementedMethod(MethodDescription methodDescription) {
                    this.methodDescription = methodDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                    throw new IllegalStateException("Cannot apply body for non-implemented method on " + this.methodDescription);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context) {
                    throw new IllegalStateException("Cannot apply code for non-implemented method on " + this.methodDescription);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void applyHead(MethodVisitor methodVisitor) {
                    throw new IllegalStateException("Cannot apply head for non-implemented method on " + this.methodDescription);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public MethodDescription getMethod() {
                    return this.methodDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public Visibility getVisibility() {
                    return this.methodDescription.getVisibility();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public Sort getSort() {
                    return Sort.SKIPPED;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public Record prepend(ByteCodeAppender byteCodeAppender) {
                    return new ForDefinedMethod.WithBody(this.methodDescription, new ByteCodeAppender.Compound(byteCodeAppender, new ByteCodeAppender.Simple(DefaultValue.of(this.methodDescription.getReturnType()), MethodReturn.of(this.methodDescription.getReturnType()))));
                }
            }

            public static abstract class ForDefinedMethod implements Record {
                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void apply(ClassVisitor classVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                    MethodVisitor methodVisitorVisitMethod = classVisitor.visitMethod(getMethod().getActualModifiers(getSort().isImplemented(), getVisibility()), getMethod().getInternalName(), getMethod().getDescriptor(), getMethod().getGenericSignature(), getMethod().getExceptionTypes().asErasures().toInternalNames());
                    if (methodVisitorVisitMethod != null) {
                        ParameterList<?> parameters = getMethod().getParameters();
                        if (parameters.hasExplicitMetaData()) {
                            Iterator it = parameters.iterator();
                            while (it.hasNext()) {
                                ParameterDescription parameterDescription = (ParameterDescription) it.next();
                                methodVisitorVisitMethod.visitParameter(parameterDescription.getName(), parameterDescription.getModifiers());
                            }
                        }
                        applyHead(methodVisitorVisitMethod);
                        applyBody(methodVisitorVisitMethod, context, factory);
                        methodVisitorVisitMethod.visitEnd();
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class WithBody extends ForDefinedMethod {
                    private final ByteCodeAppender byteCodeAppender;
                    private final MethodAttributeAppender methodAttributeAppender;
                    private final MethodDescription methodDescription;
                    private final Visibility visibility;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyHead(MethodVisitor methodVisitor) {
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        WithBody withBody = (WithBody) obj;
                        return this.visibility.equals(withBody.visibility) && this.methodDescription.equals(withBody.methodDescription) && this.byteCodeAppender.equals(withBody.byteCodeAppender) && this.methodAttributeAppender.equals(withBody.methodAttributeAppender);
                    }

                    public int hashCode() {
                        return (((((((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.byteCodeAppender.hashCode()) * 31) + this.methodAttributeAppender.hashCode()) * 31) + this.visibility.hashCode();
                    }

                    public WithBody(MethodDescription methodDescription, ByteCodeAppender byteCodeAppender) {
                        this(methodDescription, byteCodeAppender, MethodAttributeAppender.NoOp.INSTANCE, methodDescription.getVisibility());
                    }

                    public WithBody(MethodDescription methodDescription, ByteCodeAppender byteCodeAppender, MethodAttributeAppender methodAttributeAppender, Visibility visibility) {
                        this.methodDescription = methodDescription;
                        this.byteCodeAppender = byteCodeAppender;
                        this.methodAttributeAppender = methodAttributeAppender;
                        this.visibility = visibility;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public MethodDescription getMethod() {
                        return this.methodDescription;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Sort getSort() {
                        return Sort.IMPLEMENTED;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Visibility getVisibility() {
                        return this.visibility;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                        applyAttributes(methodVisitor, factory);
                        methodVisitor.visitCode();
                        ByteCodeAppender.Size sizeApplyCode = applyCode(methodVisitor, context);
                        methodVisitor.visitMaxs(sizeApplyCode.getOperandStackSize(), sizeApplyCode.getLocalVariableSize());
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory) {
                        MethodAttributeAppender methodAttributeAppender = this.methodAttributeAppender;
                        MethodDescription methodDescription = this.methodDescription;
                        methodAttributeAppender.apply(methodVisitor, methodDescription, factory.on(methodDescription));
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context) {
                        return this.byteCodeAppender.apply(methodVisitor, context, this.methodDescription);
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Record prepend(ByteCodeAppender byteCodeAppender) {
                        return new WithBody(this.methodDescription, new ByteCodeAppender.Compound(byteCodeAppender, this.byteCodeAppender), this.methodAttributeAppender, this.visibility);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class WithoutBody extends ForDefinedMethod {
                    private final MethodAttributeAppender methodAttributeAppender;
                    private final MethodDescription methodDescription;
                    private final Visibility visibility;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyHead(MethodVisitor methodVisitor) {
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        WithoutBody withoutBody = (WithoutBody) obj;
                        return this.visibility.equals(withoutBody.visibility) && this.methodDescription.equals(withoutBody.methodDescription) && this.methodAttributeAppender.equals(withoutBody.methodAttributeAppender);
                    }

                    public int hashCode() {
                        return (((((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.methodAttributeAppender.hashCode()) * 31) + this.visibility.hashCode();
                    }

                    public WithoutBody(MethodDescription methodDescription, MethodAttributeAppender methodAttributeAppender, Visibility visibility) {
                        this.methodDescription = methodDescription;
                        this.methodAttributeAppender = methodAttributeAppender;
                        this.visibility = visibility;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public MethodDescription getMethod() {
                        return this.methodDescription;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Sort getSort() {
                        return Sort.DEFINED;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Visibility getVisibility() {
                        return this.visibility;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                        applyAttributes(methodVisitor, factory);
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory) {
                        MethodAttributeAppender methodAttributeAppender = this.methodAttributeAppender;
                        MethodDescription methodDescription = this.methodDescription;
                        methodAttributeAppender.apply(methodVisitor, methodDescription, factory.on(methodDescription));
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context) {
                        throw new IllegalStateException("Cannot apply code for abstract method on " + this.methodDescription);
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Record prepend(ByteCodeAppender byteCodeAppender) {
                        throw new IllegalStateException("Cannot prepend code for abstract method on " + this.methodDescription);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class WithAnnotationDefaultValue extends ForDefinedMethod {
                    private final AnnotationValue<?, ?> annotationValue;
                    private final MethodAttributeAppender methodAttributeAppender;
                    private final MethodDescription methodDescription;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        WithAnnotationDefaultValue withAnnotationDefaultValue = (WithAnnotationDefaultValue) obj;
                        return this.methodDescription.equals(withAnnotationDefaultValue.methodDescription) && this.annotationValue.equals(withAnnotationDefaultValue.annotationValue) && this.methodAttributeAppender.equals(withAnnotationDefaultValue.methodAttributeAppender);
                    }

                    public int hashCode() {
                        return (((((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.annotationValue.hashCode()) * 31) + this.methodAttributeAppender.hashCode();
                    }

                    public WithAnnotationDefaultValue(MethodDescription methodDescription, AnnotationValue<?, ?> annotationValue, MethodAttributeAppender methodAttributeAppender) {
                        this.methodDescription = methodDescription;
                        this.annotationValue = annotationValue;
                        this.methodAttributeAppender = methodAttributeAppender;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public MethodDescription getMethod() {
                        return this.methodDescription;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Sort getSort() {
                        return Sort.DEFINED;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Visibility getVisibility() {
                        return this.methodDescription.getVisibility();
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyHead(MethodVisitor methodVisitor) {
                        if (!this.methodDescription.isDefaultValue(this.annotationValue)) {
                            throw new IllegalStateException("Cannot set " + this.annotationValue + " as default for " + this.methodDescription);
                        }
                        AnnotationVisitor annotationVisitorVisitAnnotationDefault = methodVisitor.visitAnnotationDefault();
                        AnnotationAppender.Default.apply(annotationVisitorVisitAnnotationDefault, this.methodDescription.getReturnType().asErasure(), AnnotationAppender.NO_NAME, this.annotationValue.resolve());
                        annotationVisitorVisitAnnotationDefault.visitEnd();
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                        MethodAttributeAppender methodAttributeAppender = this.methodAttributeAppender;
                        MethodDescription methodDescription = this.methodDescription;
                        methodAttributeAppender.apply(methodVisitor, methodDescription, factory.on(methodDescription));
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory) {
                        throw new IllegalStateException("Cannot apply attributes for default value on " + this.methodDescription);
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context) {
                        throw new IllegalStateException("Cannot apply code for default value on " + this.methodDescription);
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Record prepend(ByteCodeAppender byteCodeAppender) {
                        throw new IllegalStateException("Cannot prepend code for default value on " + this.methodDescription);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class OfVisibilityBridge extends ForDefinedMethod implements ByteCodeAppender {
                    private final MethodAttributeAppender attributeAppender;
                    private final MethodDescription bridgeTarget;
                    private final TypeDescription bridgeType;
                    private final MethodDescription visibilityBridge;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyHead(MethodVisitor methodVisitor) {
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        OfVisibilityBridge ofVisibilityBridge = (OfVisibilityBridge) obj;
                        return this.visibilityBridge.equals(ofVisibilityBridge.visibilityBridge) && this.bridgeTarget.equals(ofVisibilityBridge.bridgeTarget) && this.bridgeType.equals(ofVisibilityBridge.bridgeType) && this.attributeAppender.equals(ofVisibilityBridge.attributeAppender);
                    }

                    public int hashCode() {
                        return (((((((getClass().hashCode() * 31) + this.visibilityBridge.hashCode()) * 31) + this.bridgeTarget.hashCode()) * 31) + this.bridgeType.hashCode()) * 31) + this.attributeAppender.hashCode();
                    }

                    protected OfVisibilityBridge(MethodDescription methodDescription, MethodDescription methodDescription2, TypeDescription typeDescription, MethodAttributeAppender methodAttributeAppender) {
                        this.visibilityBridge = methodDescription;
                        this.bridgeTarget = methodDescription2;
                        this.bridgeType = typeDescription;
                        this.attributeAppender = methodAttributeAppender;
                    }

                    public static Record of(TypeDescription typeDescription, MethodDescription methodDescription, MethodAttributeAppender methodAttributeAppender) {
                        TypeDefinition superClass = null;
                        if (methodDescription.isDefaultMethod()) {
                            TypeDescription typeDescriptionAsErasure = methodDescription.getDeclaringType().asErasure();
                            for (TypeDescription typeDescription2 : typeDescription.getInterfaces().asErasures().filter(ElementMatchers.isSubTypeOf(typeDescriptionAsErasure))) {
                                if (superClass == null || typeDescriptionAsErasure.isAssignableTo(superClass.asErasure())) {
                                    superClass = typeDescription2;
                                }
                            }
                        }
                        if (superClass == null && (superClass = typeDescription.getSuperClass()) == null) {
                            superClass = TypeDescription.ForLoadedType.of(Object.class);
                        }
                        return new OfVisibilityBridge(new VisibilityBridge(typeDescription, methodDescription), methodDescription, superClass.asErasure(), methodAttributeAppender);
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public MethodDescription getMethod() {
                        return this.visibilityBridge;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Sort getSort() {
                        return Sort.IMPLEMENTED;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Visibility getVisibility() {
                        return this.bridgeTarget.getVisibility();
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public Record prepend(ByteCodeAppender byteCodeAppender) {
                        return new WithBody(this.visibilityBridge, new ByteCodeAppender.Compound(this, byteCodeAppender), this.attributeAppender, this.bridgeTarget.getVisibility());
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                        applyAttributes(methodVisitor, factory);
                        methodVisitor.visitCode();
                        ByteCodeAppender.Size sizeApplyCode = applyCode(methodVisitor, context);
                        methodVisitor.visitMaxs(sizeApplyCode.getOperandStackSize(), sizeApplyCode.getLocalVariableSize());
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory) {
                        MethodAttributeAppender methodAttributeAppender = this.attributeAppender;
                        MethodDescription methodDescription = this.visibilityBridge;
                        methodAttributeAppender.apply(methodVisitor, methodDescription, factory.on(methodDescription));
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                    public ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context) {
                        return apply(methodVisitor, context, this.visibilityBridge);
                    }

                    @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
                    public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                        return new ByteCodeAppender.Simple(MethodVariableAccess.allArgumentsOf(methodDescription).prependThisReference(), MethodInvocation.invoke(this.bridgeTarget).special(this.bridgeType), MethodReturn.of(methodDescription.getReturnType())).apply(methodVisitor, context, methodDescription);
                    }

                    protected static class VisibilityBridge extends MethodDescription.InDefinedShape.AbstractBase {
                        private final MethodDescription bridgeTarget;
                        private final TypeDescription instrumentedType;

                        protected VisibilityBridge(TypeDescription typeDescription, MethodDescription methodDescription) {
                            this.instrumentedType = typeDescription;
                            this.bridgeTarget = methodDescription;
                        }

                        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
                        @Nonnull
                        public TypeDescription getDeclaringType() {
                            return this.instrumentedType;
                        }

                        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
                        public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
                            return new ParameterList.Explicit.ForTypes(this, this.bridgeTarget.getParameters().asTypeList().asRawTypes());
                        }

                        @Override // net.bytebuddy.description.method.MethodDescription
                        public TypeDescription.Generic getReturnType() {
                            return this.bridgeTarget.getReturnType().asRawType();
                        }

                        @Override // net.bytebuddy.description.method.MethodDescription
                        public TypeList.Generic getExceptionTypes() {
                            return this.bridgeTarget.getExceptionTypes().asRawTypes();
                        }

                        @Override // net.bytebuddy.description.method.MethodDescription
                        @MaybeNull
                        public AnnotationValue<?, ?> getDefaultValue() {
                            return AnnotationValue.UNDEFINED;
                        }

                        @Override // net.bytebuddy.description.TypeVariableSource
                        public TypeList.Generic getTypeVariables() {
                            return new TypeList.Generic.Empty();
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return this.bridgeTarget.getDeclaredAnnotations();
                        }

                        @Override // net.bytebuddy.description.ModifierReviewable
                        public int getModifiers() {
                            return (this.bridgeTarget.getModifiers() | 4160) & (-257);
                        }

                        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                        public String getInternalName() {
                            return this.bridgeTarget.getName();
                        }
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class AccessBridgeWrapper implements Record {
                private final MethodAttributeAppender attributeAppender;
                private final MethodDescription bridgeTarget;
                private final Set<MethodDescription.TypeToken> bridgeTypes;
                private final Record delegate;
                private final TypeDescription instrumentedType;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    AccessBridgeWrapper accessBridgeWrapper = (AccessBridgeWrapper) obj;
                    return this.delegate.equals(accessBridgeWrapper.delegate) && this.instrumentedType.equals(accessBridgeWrapper.instrumentedType) && this.bridgeTarget.equals(accessBridgeWrapper.bridgeTarget) && this.bridgeTypes.equals(accessBridgeWrapper.bridgeTypes) && this.attributeAppender.equals(accessBridgeWrapper.attributeAppender);
                }

                public int hashCode() {
                    return (((((((((getClass().hashCode() * 31) + this.delegate.hashCode()) * 31) + this.instrumentedType.hashCode()) * 31) + this.bridgeTarget.hashCode()) * 31) + this.bridgeTypes.hashCode()) * 31) + this.attributeAppender.hashCode();
                }

                protected AccessBridgeWrapper(Record record, TypeDescription typeDescription, MethodDescription methodDescription, Set<MethodDescription.TypeToken> set, MethodAttributeAppender methodAttributeAppender) {
                    this.delegate = record;
                    this.instrumentedType = typeDescription;
                    this.bridgeTarget = methodDescription;
                    this.bridgeTypes = set;
                    this.attributeAppender = methodAttributeAppender;
                }

                public static Record of(Record record, TypeDescription typeDescription, MethodDescription methodDescription, Set<MethodDescription.TypeToken> set, MethodAttributeAppender methodAttributeAppender) {
                    HashSet hashSet = new HashSet();
                    for (MethodDescription.TypeToken typeToken : set) {
                        if (methodDescription.isBridgeCompatible(typeToken)) {
                            hashSet.add(typeToken);
                        }
                    }
                    if (hashSet.isEmpty()) {
                        return record;
                    }
                    return (!typeDescription.isInterface() || record.getSort().isImplemented()) ? new AccessBridgeWrapper(record, typeDescription, methodDescription, hashSet, methodAttributeAppender) : record;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public Sort getSort() {
                    return this.delegate.getSort();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public MethodDescription getMethod() {
                    return this.bridgeTarget;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public Visibility getVisibility() {
                    return this.delegate.getVisibility();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public Record prepend(ByteCodeAppender byteCodeAppender) {
                    return new AccessBridgeWrapper(this.delegate.prepend(byteCodeAppender), this.instrumentedType, this.bridgeTarget, this.bridgeTypes, this.attributeAppender);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void apply(ClassVisitor classVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                    this.delegate.apply(classVisitor, context, factory);
                    Iterator<MethodDescription.TypeToken> it = this.bridgeTypes.iterator();
                    while (it.hasNext()) {
                        AccessorBridge accessorBridge = new AccessorBridge(this.bridgeTarget, it.next(), this.instrumentedType);
                        BridgeTarget bridgeTarget = new BridgeTarget(this.bridgeTarget, this.instrumentedType);
                        MethodVisitor methodVisitorVisitMethod = classVisitor.visitMethod(accessorBridge.getActualModifiers(true, getVisibility()), accessorBridge.getInternalName(), accessorBridge.getDescriptor(), MethodDescription.NON_GENERIC_SIGNATURE, accessorBridge.getExceptionTypes().asErasures().toInternalNames());
                        if (methodVisitorVisitMethod != null) {
                            this.attributeAppender.apply(methodVisitorVisitMethod, accessorBridge, factory.on(this.instrumentedType));
                            methodVisitorVisitMethod.visitCode();
                            StackManipulation[] stackManipulationArr = new StackManipulation[4];
                            stackManipulationArr[0] = MethodVariableAccess.allArgumentsOf(accessorBridge).asBridgeOf(bridgeTarget).prependThisReference();
                            stackManipulationArr[1] = MethodInvocation.invoke((MethodDescription.InDefinedShape) bridgeTarget).virtual(this.instrumentedType);
                            stackManipulationArr[2] = bridgeTarget.getReturnType().asErasure().isAssignableTo(accessorBridge.getReturnType().asErasure()) ? StackManipulation.Trivial.INSTANCE : TypeCasting.to(accessorBridge.getReturnType().asErasure());
                            stackManipulationArr[3] = MethodReturn.of(accessorBridge.getReturnType());
                            ByteCodeAppender.Size sizeApply = new ByteCodeAppender.Simple(stackManipulationArr).apply(methodVisitorVisitMethod, context, accessorBridge);
                            methodVisitorVisitMethod.visitMaxs(sizeApply.getOperandStackSize(), sizeApply.getLocalVariableSize());
                            methodVisitorVisitMethod.visitEnd();
                        }
                    }
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void applyHead(MethodVisitor methodVisitor) {
                    this.delegate.applyHead(methodVisitor);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void applyBody(MethodVisitor methodVisitor, Implementation.Context context, AnnotationValueFilter.Factory factory) {
                    this.delegate.applyBody(methodVisitor, context, factory);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public void applyAttributes(MethodVisitor methodVisitor, AnnotationValueFilter.Factory factory) {
                    this.delegate.applyAttributes(methodVisitor, factory);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record
                public ByteCodeAppender.Size applyCode(MethodVisitor methodVisitor, Implementation.Context context) {
                    return this.delegate.applyCode(methodVisitor, context);
                }

                protected static class AccessorBridge extends MethodDescription.InDefinedShape.AbstractBase {
                    private final MethodDescription bridgeTarget;
                    private final MethodDescription.TypeToken bridgeType;
                    private final TypeDescription instrumentedType;

                    protected AccessorBridge(MethodDescription methodDescription, MethodDescription.TypeToken typeToken, TypeDescription typeDescription) {
                        this.bridgeTarget = methodDescription;
                        this.bridgeType = typeToken;
                        this.instrumentedType = typeDescription;
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
                    @Nonnull
                    public TypeDescription getDeclaringType() {
                        return this.instrumentedType;
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
                    public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
                        return new ParameterList.Explicit.ForTypes(this, this.bridgeType.getParameterTypes());
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription
                    public TypeDescription.Generic getReturnType() {
                        return this.bridgeType.getReturnType().asGenericType();
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription
                    public TypeList.Generic getExceptionTypes() {
                        return this.bridgeTarget.getExceptionTypes().accept(TypeDescription.Generic.Visitor.TypeErasing.INSTANCE);
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription
                    @MaybeNull
                    public AnnotationValue<?, ?> getDefaultValue() {
                        return AnnotationValue.UNDEFINED;
                    }

                    @Override // net.bytebuddy.description.TypeVariableSource
                    public TypeList.Generic getTypeVariables() {
                        return new TypeList.Generic.Empty();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return new AnnotationList.Empty();
                    }

                    @Override // net.bytebuddy.description.ModifierReviewable
                    public int getModifiers() {
                        return (this.bridgeTarget.getModifiers() | 4160) & (-1281);
                    }

                    @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                    public String getInternalName() {
                        return this.bridgeTarget.getInternalName();
                    }
                }

                protected static class BridgeTarget extends MethodDescription.InDefinedShape.AbstractBase {
                    private final MethodDescription bridgeTarget;
                    private final TypeDescription instrumentedType;

                    protected BridgeTarget(MethodDescription methodDescription, TypeDescription typeDescription) {
                        this.bridgeTarget = methodDescription;
                        this.instrumentedType = typeDescription;
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
                    @Nonnull
                    public TypeDescription getDeclaringType() {
                        return this.instrumentedType;
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
                    public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
                        return new ParameterList.ForTokens(this, this.bridgeTarget.getParameters().asTokenList(ElementMatchers.is(this.instrumentedType)));
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription
                    public TypeDescription.Generic getReturnType() {
                        return this.bridgeTarget.getReturnType();
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription
                    public TypeList.Generic getExceptionTypes() {
                        return this.bridgeTarget.getExceptionTypes();
                    }

                    @Override // net.bytebuddy.description.method.MethodDescription
                    @MaybeNull
                    public AnnotationValue<?, ?> getDefaultValue() {
                        return this.bridgeTarget.getDefaultValue();
                    }

                    @Override // net.bytebuddy.description.TypeVariableSource
                    public TypeList.Generic getTypeVariables() {
                        return this.bridgeTarget.getTypeVariables();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return this.bridgeTarget.getDeclaredAnnotations();
                    }

                    @Override // net.bytebuddy.description.ModifierReviewable
                    public int getModifiers() {
                        return this.bridgeTarget.getModifiers();
                    }

                    @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                    public String getInternalName() {
                        return this.bridgeTarget.getInternalName();
                    }
                }
            }
        }
    }

    public interface RecordComponentPool {
        Record target(RecordComponentDescription recordComponentDescription);

        public interface Record {
            void apply(ClassVisitor classVisitor, AnnotationValueFilter.Factory factory);

            void apply(RecordComponentVisitor recordComponentVisitor, AnnotationValueFilter.Factory factory);

            RecordComponentDescription getRecordComponent();

            RecordComponentAttributeAppender getRecordComponentAppender();

            boolean isImplicit();

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForImplicitRecordComponent implements Record {
                private final RecordComponentDescription recordComponentDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.recordComponentDescription.equals(((ForImplicitRecordComponent) obj).recordComponentDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.recordComponentDescription.hashCode();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public boolean isImplicit() {
                    return true;
                }

                public ForImplicitRecordComponent(RecordComponentDescription recordComponentDescription) {
                    this.recordComponentDescription = recordComponentDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public RecordComponentDescription getRecordComponent() {
                    return this.recordComponentDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public RecordComponentAttributeAppender getRecordComponentAppender() {
                    throw new IllegalStateException("An implicit field record does not expose a field appender: " + this);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public void apply(ClassVisitor classVisitor, AnnotationValueFilter.Factory factory) {
                    RecordComponentVisitor recordComponentVisitorVisitRecordComponent = classVisitor.visitRecordComponent(this.recordComponentDescription.getActualName(), this.recordComponentDescription.getDescriptor(), this.recordComponentDescription.getGenericSignature());
                    if (recordComponentVisitorVisitRecordComponent != null) {
                        RecordComponentAttributeAppender.ForInstrumentedRecordComponent forInstrumentedRecordComponent = RecordComponentAttributeAppender.ForInstrumentedRecordComponent.INSTANCE;
                        RecordComponentDescription recordComponentDescription = this.recordComponentDescription;
                        forInstrumentedRecordComponent.apply(recordComponentVisitorVisitRecordComponent, recordComponentDescription, factory.on(recordComponentDescription));
                        recordComponentVisitorVisitRecordComponent.visitEnd();
                    }
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public void apply(RecordComponentVisitor recordComponentVisitor, AnnotationValueFilter.Factory factory) {
                    throw new IllegalStateException("An implicit field record is not intended for partial application: " + this);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForExplicitRecordComponent implements Record {
                private final RecordComponentAttributeAppender attributeAppender;
                private final RecordComponentDescription recordComponentDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    ForExplicitRecordComponent forExplicitRecordComponent = (ForExplicitRecordComponent) obj;
                    return this.attributeAppender.equals(forExplicitRecordComponent.attributeAppender) && this.recordComponentDescription.equals(forExplicitRecordComponent.recordComponentDescription);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.attributeAppender.hashCode()) * 31) + this.recordComponentDescription.hashCode();
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public boolean isImplicit() {
                    return false;
                }

                public ForExplicitRecordComponent(RecordComponentAttributeAppender recordComponentAttributeAppender, RecordComponentDescription recordComponentDescription) {
                    this.attributeAppender = recordComponentAttributeAppender;
                    this.recordComponentDescription = recordComponentDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public RecordComponentDescription getRecordComponent() {
                    return this.recordComponentDescription;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public RecordComponentAttributeAppender getRecordComponentAppender() {
                    return this.attributeAppender;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public void apply(ClassVisitor classVisitor, AnnotationValueFilter.Factory factory) {
                    RecordComponentVisitor recordComponentVisitorVisitRecordComponent = classVisitor.visitRecordComponent(this.recordComponentDescription.getActualName(), this.recordComponentDescription.getDescriptor(), this.recordComponentDescription.getGenericSignature());
                    if (recordComponentVisitorVisitRecordComponent != null) {
                        RecordComponentAttributeAppender recordComponentAttributeAppender = this.attributeAppender;
                        RecordComponentDescription recordComponentDescription = this.recordComponentDescription;
                        recordComponentAttributeAppender.apply(recordComponentVisitorVisitRecordComponent, recordComponentDescription, factory.on(recordComponentDescription));
                        recordComponentVisitorVisitRecordComponent.visitEnd();
                    }
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool.Record
                public void apply(RecordComponentVisitor recordComponentVisitor, AnnotationValueFilter.Factory factory) {
                    RecordComponentAttributeAppender recordComponentAttributeAppender = this.attributeAppender;
                    RecordComponentDescription recordComponentDescription = this.recordComponentDescription;
                    recordComponentAttributeAppender.apply(recordComponentVisitor, recordComponentDescription, factory.on(recordComponentDescription));
                }
            }
        }

        public enum Disabled implements RecordComponentPool {
            INSTANCE;

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.RecordComponentPool
            public Record target(RecordComponentDescription recordComponentDescription) {
                throw new IllegalStateException("Cannot look up record component from disabled pool");
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static abstract class Default<S> implements TypeWriter<S> {
        private static final boolean ACCESS_CONTROLLER;

        @MaybeNull
        protected static final String DUMP_FOLDER;

        @AlwaysNull
        private static final String NO_REFERENCE;
        protected final AnnotationRetention annotationRetention;
        protected final AnnotationValueFilter.Factory annotationValueFilterFactory;
        protected final AsmVisitorWrapper asmVisitorWrapper;
        protected final AuxiliaryType.NamingStrategy auxiliaryTypeNamingStrategy;
        protected final List<? extends DynamicType> auxiliaryTypes;
        protected final ClassFileVersion classFileVersion;
        protected final ClassWriterStrategy classWriterStrategy;
        protected final FieldPool fieldPool;
        protected final FieldList<FieldDescription.InDefinedShape> fields;
        protected final Implementation.Context.Factory implementationContextFactory;
        protected final MethodList<?> instrumentedMethods;
        protected final TypeDescription instrumentedType;
        protected final LoadedTypeInitializer loadedTypeInitializer;
        protected final MethodList<?> methods;
        protected final RecordComponentPool recordComponentPool;
        protected final RecordComponentList<RecordComponentDescription.InDefinedShape> recordComponents;
        protected final TypeAttributeAppender typeAttributeAppender;
        protected final TypeInitializer typeInitializer;
        protected final TypePool typePool;
        protected final TypeValidation typeValidation;

        protected abstract Default<S>.UnresolvedType create(TypeInitializer typeInitializer, ClassDumpAction.Dispatcher dispatcher);

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Default r5 = (Default) obj;
            return this.annotationRetention.equals(r5.annotationRetention) && this.typeValidation.equals(r5.typeValidation) && this.instrumentedType.equals(r5.instrumentedType) && this.classFileVersion.equals(r5.classFileVersion) && this.fieldPool.equals(r5.fieldPool) && this.recordComponentPool.equals(r5.recordComponentPool) && this.auxiliaryTypes.equals(r5.auxiliaryTypes) && this.fields.equals(r5.fields) && this.methods.equals(r5.methods) && this.instrumentedMethods.equals(r5.instrumentedMethods) && this.recordComponents.equals(r5.recordComponents) && this.loadedTypeInitializer.equals(r5.loadedTypeInitializer) && this.typeInitializer.equals(r5.typeInitializer) && this.typeAttributeAppender.equals(r5.typeAttributeAppender) && this.asmVisitorWrapper.equals(r5.asmVisitorWrapper) && this.annotationValueFilterFactory.equals(r5.annotationValueFilterFactory) && this.auxiliaryTypeNamingStrategy.equals(r5.auxiliaryTypeNamingStrategy) && this.implementationContextFactory.equals(r5.implementationContextFactory) && this.classWriterStrategy.equals(r5.classWriterStrategy) && this.typePool.equals(r5.typePool);
        }

        public int hashCode() {
            return (((((((((((((((((((((((((((((((((((((((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + this.classFileVersion.hashCode()) * 31) + this.fieldPool.hashCode()) * 31) + this.recordComponentPool.hashCode()) * 31) + this.auxiliaryTypes.hashCode()) * 31) + this.fields.hashCode()) * 31) + this.methods.hashCode()) * 31) + this.instrumentedMethods.hashCode()) * 31) + this.recordComponents.hashCode()) * 31) + this.loadedTypeInitializer.hashCode()) * 31) + this.typeInitializer.hashCode()) * 31) + this.typeAttributeAppender.hashCode()) * 31) + this.asmVisitorWrapper.hashCode()) * 31) + this.annotationValueFilterFactory.hashCode()) * 31) + this.annotationRetention.hashCode()) * 31) + this.auxiliaryTypeNamingStrategy.hashCode()) * 31) + this.implementationContextFactory.hashCode()) * 31) + this.typeValidation.hashCode()) * 31) + this.classWriterStrategy.hashCode()) * 31) + this.typePool.hashCode();
        }

        static {
            boolean z = false;
            String str = null;
            try {
                Class.forName("java.security.AccessController", false, null);
                ACCESS_CONTROLLER = Boolean.parseBoolean(System.getProperty("net.bytebuddy.securitymanager", "true"));
            } catch (ClassNotFoundException unused) {
                ACCESS_CONTROLLER = z;
            } catch (SecurityException unused2) {
                z = true;
                ACCESS_CONTROLLER = z;
            }
            NO_REFERENCE = null;
            try {
                str = (String) doPrivileged(new GetSystemPropertyAction(TypeWriter.DUMP_PROPERTY));
            } catch (RuntimeException unused3) {
            }
            DUMP_FOLDER = str;
        }

        protected Default(TypeDescription typeDescription, ClassFileVersion classFileVersion, FieldPool fieldPool, RecordComponentPool recordComponentPool, List<? extends DynamicType> list, FieldList<FieldDescription.InDefinedShape> fieldList, MethodList<?> methodList, MethodList<?> methodList2, RecordComponentList<RecordComponentDescription.InDefinedShape> recordComponentList, LoadedTypeInitializer loadedTypeInitializer, TypeInitializer typeInitializer, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool) {
            this.instrumentedType = typeDescription;
            this.classFileVersion = classFileVersion;
            this.fieldPool = fieldPool;
            this.recordComponentPool = recordComponentPool;
            this.auxiliaryTypes = list;
            this.fields = fieldList;
            this.methods = methodList;
            this.instrumentedMethods = methodList2;
            this.recordComponents = recordComponentList;
            this.loadedTypeInitializer = loadedTypeInitializer;
            this.typeInitializer = typeInitializer;
            this.typeAttributeAppender = typeAttributeAppender;
            this.asmVisitorWrapper = asmVisitorWrapper;
            this.auxiliaryTypeNamingStrategy = namingStrategy;
            this.annotationValueFilterFactory = factory;
            this.annotationRetention = annotationRetention;
            this.implementationContextFactory = factory2;
            this.typeValidation = typeValidation;
            this.classWriterStrategy = classWriterStrategy;
            this.typePool = typePool;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        /* JADX INFO: Access modifiers changed from: private */
        @AccessControllerPlugin.Enhance
        public static <T> T doPrivileged(PrivilegedExceptionAction<T> privilegedExceptionAction) throws Exception {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedExceptionAction) : privilegedExceptionAction.run();
        }

        public static <U> TypeWriter<U> forCreation(MethodRegistry.Compiled compiled, List<? extends DynamicType> list, FieldPool fieldPool, RecordComponentPool recordComponentPool, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, ClassFileVersion classFileVersion, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool) {
            return new ForCreation(compiled.getInstrumentedType(), classFileVersion, fieldPool, compiled, recordComponentPool, list, compiled.getInstrumentedType().getDeclaredFields(), compiled.getMethods(), compiled.getInstrumentedMethods(), compiled.getInstrumentedType().getRecordComponents(), compiled.getLoadedTypeInitializer(), compiled.getTypeInitializer(), typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool);
        }

        public static <U> TypeWriter<U> forRedefinition(MethodRegistry.Prepared prepared, List<? extends DynamicType> list, FieldPool fieldPool, RecordComponentPool recordComponentPool, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, ClassFileVersion classFileVersion, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool, TypeDescription typeDescription, ClassFileLocator classFileLocator) {
            return new ForInlining.WithFullProcessing(prepared.getInstrumentedType(), classFileVersion, fieldPool, recordComponentPool, list, prepared.getInstrumentedType().getDeclaredFields(), prepared.getMethods(), prepared.getInstrumentedMethods(), prepared.getInstrumentedType().getRecordComponents(), prepared.getLoadedTypeInitializer(), prepared.getTypeInitializer(), typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool, typeDescription, classFileLocator, prepared, SubclassImplementationTarget.Factory.LEVEL_TYPE, MethodRebaseResolver.Disabled.INSTANCE);
        }

        public static <U> TypeWriter<U> forRebasing(MethodRegistry.Prepared prepared, List<? extends DynamicType> list, FieldPool fieldPool, RecordComponentPool recordComponentPool, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, ClassFileVersion classFileVersion, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool, TypeDescription typeDescription, ClassFileLocator classFileLocator, MethodRebaseResolver methodRebaseResolver) {
            return new ForInlining.WithFullProcessing(prepared.getInstrumentedType(), classFileVersion, fieldPool, recordComponentPool, CompoundList.of((List) list, (List) methodRebaseResolver.getAuxiliaryTypes()), prepared.getInstrumentedType().getDeclaredFields(), prepared.getMethods(), prepared.getInstrumentedMethods(), prepared.getInstrumentedType().getRecordComponents(), prepared.getLoadedTypeInitializer(), prepared.getTypeInitializer(), typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool, typeDescription, classFileLocator, prepared, new RebaseImplementationTarget.Factory(methodRebaseResolver), methodRebaseResolver);
        }

        public static <U> TypeWriter<U> forDecoration(TypeDescription typeDescription, ClassFileVersion classFileVersion, List<? extends DynamicType> list, List<? extends MethodDescription> list2, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool, ClassFileLocator classFileLocator) {
            return new ForInlining.WithDecorationOnly(typeDescription, classFileVersion, list, new MethodList.Explicit(list2), typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool, classFileLocator);
        }

        @Override // net.bytebuddy.dynamic.scaffold.TypeWriter
        public DynamicType.Unloaded<S> make(TypeResolutionStrategy.Resolved resolved) {
            String str = DUMP_FOLDER;
            ClassDumpAction.Dispatcher enabled = str == null ? ClassDumpAction.Dispatcher.Disabled.INSTANCE : new ClassDumpAction.Dispatcher.Enabled(str, System.currentTimeMillis());
            Default<S>.UnresolvedType unresolvedTypeCreate = create(resolved.injectedInto(this.typeInitializer), enabled);
            enabled.dump(this.instrumentedType, false, unresolvedTypeCreate.getBinaryRepresentation());
            return unresolvedTypeCreate.toDynamicType(resolved);
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class UnresolvedType {
            private final List<? extends DynamicType> auxiliaryTypes;
            private final byte[] binaryRepresentation;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                UnresolvedType unresolvedType = (UnresolvedType) obj;
                return Arrays.equals(this.binaryRepresentation, unresolvedType.binaryRepresentation) && this.auxiliaryTypes.equals(unresolvedType.auxiliaryTypes) && Default.this.equals(Default.this);
            }

            public int hashCode() {
                return (((((getClass().hashCode() * 31) + Arrays.hashCode(this.binaryRepresentation)) * 31) + this.auxiliaryTypes.hashCode()) * 31) + Default.this.hashCode();
            }

            protected UnresolvedType(byte[] bArr, List<? extends DynamicType> list) {
                this.binaryRepresentation = bArr;
                this.auxiliaryTypes = list;
            }

            protected DynamicType.Unloaded<S> toDynamicType(TypeResolutionStrategy.Resolved resolved) {
                return new DynamicType.Default.Unloaded(Default.this.instrumentedType, this.binaryRepresentation, Default.this.loadedTypeInitializer, CompoundList.of((List) Default.this.auxiliaryTypes, (List) this.auxiliaryTypes), resolved);
            }

            protected byte[] getBinaryRepresentation() {
                return this.binaryRepresentation;
            }
        }

        protected static class SignatureKey {
            private final String descriptor;
            private final String internalName;

            public SignatureKey(String str, String str2) {
                this.internalName = str;
                this.descriptor = str2;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                SignatureKey signatureKey = (SignatureKey) obj;
                return this.internalName.equals(signatureKey.internalName) && this.descriptor.equals(signatureKey.descriptor);
            }

            public int hashCode() {
                return this.internalName.hashCode() + 17 + (this.descriptor.hashCode() * 31);
            }
        }

        protected static class ValidatingClassVisitor extends ClassVisitor {

            @AlwaysNull
            private static final FieldVisitor IGNORE_FIELD = null;

            @AlwaysNull
            private static final MethodVisitor IGNORE_METHOD = null;
            private static final String NO_PARAMETERS = "()";
            private static final String RETURNS_VOID = "V";
            private static final String STRING_DESCRIPTOR = "Ljava/lang/String;";

            @UnknownNull
            private Constraint constraint;

            protected ValidatingClassVisitor(ClassVisitor classVisitor) {
                super(OpenedClassReader.ASM_API, classVisitor);
            }

            protected static ClassVisitor of(ClassVisitor classVisitor, TypeValidation typeValidation) {
                return typeValidation.isEnabled() ? new ValidatingClassVisitor(classVisitor) : classVisitor;
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visit(int i, int i2, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String[] strArr) {
                boolean z;
                ClassFileVersion classFileVersionOfMinorMajor = ClassFileVersion.ofMinorMajor(i);
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Constraint.ForClassFileVersion(classFileVersionOfMinorMajor));
                if (str.endsWith("/package-info")) {
                    arrayList.add(Constraint.ForPackageType.INSTANCE);
                } else if ((i2 & 8192) != 0) {
                    if (!classFileVersionOfMinorMajor.isAtLeast(ClassFileVersion.JAVA_V5)) {
                        throw new IllegalStateException("Cannot define an annotation type for class file version " + classFileVersionOfMinorMajor);
                    }
                    arrayList.add(classFileVersionOfMinorMajor.isAtLeast(ClassFileVersion.JAVA_V8) ? Constraint.ForAnnotation.JAVA_8 : Constraint.ForAnnotation.CLASSIC);
                } else if ((i2 & 512) != 0) {
                    arrayList.add(classFileVersionOfMinorMajor.isAtLeast(ClassFileVersion.JAVA_V8) ? Constraint.ForInterface.JAVA_8 : Constraint.ForInterface.CLASSIC);
                } else if ((i2 & 1024) != 0) {
                    arrayList.add(Constraint.ForClass.ABSTRACT);
                } else {
                    arrayList.add(Constraint.ForClass.MANIFEST);
                }
                if ((65536 & i2) != 0) {
                    arrayList.add(Constraint.ForRecord.INSTANCE);
                    z = true;
                } else {
                    z = false;
                }
                Constraint.Compound compound = new Constraint.Compound(arrayList);
                this.constraint = compound;
                compound.assertType(i2, strArr != null, str2 != null);
                if (z) {
                    this.constraint.assertRecord();
                }
                super.visit(i, i2, str, str2, str3, strArr);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitPermittedSubclass(String str) {
                this.constraint.assertPermittedSubclass();
                super.visitPermittedSubclass(str);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            @MaybeNull
            public AnnotationVisitor visitAnnotation(String str, boolean z) {
                this.constraint.assertAnnotation();
                return super.visitAnnotation(str, z);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            @MaybeNull
            public AnnotationVisitor visitTypeAnnotation(int i, @MaybeNull TypePath typePath, String str, boolean z) {
                this.constraint.assertTypeAnnotation();
                return super.visitTypeAnnotation(i, typePath, str, z);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitNestHost(String str) {
                this.constraint.assertNestMate();
                super.visitNestHost(str);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitNestMember(String str) {
                this.constraint.assertNestMate();
                super.visitNestMember(str);
            }

            /* JADX WARN: Code duplicated, block: B:20:0x0045  */
            @Override // net.bytebuddy.jar.asm.ClassVisitor
            @MaybeNull
            public FieldVisitor visitField(int i, String str, String str2, @MaybeNull String str3, @MaybeNull Object obj) {
                Class cls;
                int i2;
                int i3;
                if (obj != null) {
                    char cCharAt = str2.charAt(0);
                    if (cCharAt == 'F') {
                        cls = Float.class;
                    } else if (cCharAt == 'S' || cCharAt == 'Z' || cCharAt == 'I') {
                        cls = Integer.class;
                    } else if (cCharAt != 'J') {
                        switch (cCharAt) {
                            case 'B':
                            case 'C':
                                cls = Integer.class;
                                break;
                            case 'D':
                                cls = Double.class;
                                break;
                            default:
                                if (!str2.equals(STRING_DESCRIPTOR)) {
                                    throw new IllegalStateException("Cannot define a default value for type of field " + str);
                                }
                                cls = String.class;
                                break;
                                break;
                        }
                    } else {
                        cls = Long.class;
                    }
                    if (!cls.isInstance(obj)) {
                        throw new IllegalStateException("Field " + str + " defines an incompatible default value " + obj);
                    }
                    if (cls == Integer.class) {
                        char cCharAt2 = str2.charAt(0);
                        if (cCharAt2 != 'B') {
                            if (cCharAt2 == 'C') {
                                i3 = 65535;
                            } else if (cCharAt2 == 'S') {
                                i2 = -32768;
                                i3 = 32767;
                            } else if (cCharAt2 != 'Z') {
                                i2 = Integer.MIN_VALUE;
                                i3 = Integer.MAX_VALUE;
                            } else {
                                i3 = 1;
                            }
                            i2 = 0;
                        } else {
                            i2 = -128;
                            i3 = 127;
                        }
                        Integer num = (Integer) obj;
                        if (num.intValue() < i2 || num.intValue() > i3) {
                            throw new IllegalStateException("Field " + str + " defines an incompatible default value " + obj);
                        }
                    }
                }
                this.constraint.assertField(str, (i & 1) != 0, (i & 8) != 0, (i & 16) != 0, str3 != null);
                FieldVisitor fieldVisitorVisitField = super.visitField(i, str, str2, str3, obj);
                return fieldVisitorVisitField == null ? IGNORE_FIELD : new ValidatingFieldVisitor(fieldVisitorVisitField);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            @MaybeNull
            public MethodVisitor visitMethod(int i, String str, String str2, @MaybeNull String str3, @MaybeNull String[] strArr) {
                this.constraint.assertMethod(str, (i & 1024) != 0, (i & 1) != 0, (i & 2) != 0, (i & 8) != 0, (str.equals("<init>") || str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME) || (i & 10) != 0) ? false : true, str.equals("<init>"), !str2.startsWith(NO_PARAMETERS) || str2.endsWith("V"), str3 != null);
                MethodVisitor methodVisitorVisitMethod = super.visitMethod(i, str, str2, str3, strArr);
                return methodVisitorVisitMethod == null ? IGNORE_METHOD : new ValidatingMethodVisitor(methodVisitorVisitMethod, str);
            }

            protected interface Constraint {
                void assertAnnotation();

                void assertDefaultMethodCall();

                void assertDefaultValue(String str);

                void assertDynamicValueInConstantPool();

                void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4);

                void assertHandleInConstantPool();

                void assertInvokeDynamic();

                void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8);

                void assertMethodTypeInConstantPool();

                void assertNestMate();

                void assertPermittedSubclass();

                void assertRecord();

                void assertSubRoutine();

                void assertType(int i, boolean z, boolean z2);

                void assertTypeAnnotation();

                void assertTypeInConstantPool();

                public enum ForClass implements Constraint {
                    MANIFEST(true),
                    ABSTRACT(false);

                    private final boolean manifestType;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                    }

                    ForClass(boolean z) {
                        this.manifestType = z;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                        if (z && this.manifestType) {
                            throw new IllegalStateException("Cannot define abstract method '" + str + "' for non-abstract class");
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                        throw new IllegalStateException("Cannot define default value for '" + str + "' for non-annotation type");
                    }
                }

                public enum ForPackageType implements Constraint {
                    INSTANCE;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                        throw new IllegalStateException("Cannot define a field for a package description type");
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                        throw new IllegalStateException("Cannot define a method for a package description type");
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                        if (i != 5632) {
                            throw new IllegalStateException("A package description type must define 5632 as modifier");
                        }
                        if (z) {
                            throw new IllegalStateException("Cannot implement interface for package type");
                        }
                    }
                }

                public enum ForInterface implements Constraint {
                    CLASSIC(true),
                    JAVA_8(false);

                    private final boolean classic;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                    }

                    ForInterface(boolean z) {
                        this.classic = z;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                        if (!z2 || !z || !z3) {
                            throw new IllegalStateException("Cannot only define public, static, final field '" + str + "' for interface type");
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                        if (str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                            return;
                        }
                        if (z6) {
                            throw new IllegalStateException("Cannot define constructor for interface type");
                        }
                        boolean z9 = this.classic;
                        if (z9 && !z2) {
                            throw new IllegalStateException("Cannot define non-public method '" + str + "' for interface type");
                        }
                        if (z9 && !z5) {
                            throw new IllegalStateException("Cannot define non-virtual method '" + str + "' for a pre-Java 8 interface type");
                        }
                        if (z9 && !z) {
                            throw new IllegalStateException("Cannot define default method '" + str + "' for pre-Java 8 interface type");
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                        throw new IllegalStateException("Cannot define default value for '" + str + "' for non-annotation type");
                    }
                }

                public enum ForRecord implements Constraint {
                    INSTANCE;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                        if ((i & 1024) != 0) {
                            throw new IllegalStateException("Cannot define a record class as abstract");
                        }
                    }
                }

                public enum ForAnnotation implements Constraint {
                    CLASSIC(true),
                    JAVA_8(false);

                    private final boolean classic;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                    }

                    ForAnnotation(boolean z) {
                        this.classic = z;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                        if (!z2 || !z || !z3) {
                            throw new IllegalStateException("Cannot only define public, static, final field '" + str + "' for interface type");
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                        if (str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                            return;
                        }
                        if (z6) {
                            throw new IllegalStateException("Cannot define constructor for interface type");
                        }
                        if (this.classic && !z5) {
                            throw new IllegalStateException("Cannot define non-virtual method '" + str + "' for a pre-Java 8 annotation type");
                        }
                        if (!z4 && z7) {
                            throw new IllegalStateException("Cannot define method '" + str + "' with the given signature as an annotation type method");
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                        if ((i & 512) == 0) {
                            throw new IllegalStateException("Cannot define annotation type without interface modifier");
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForClassFileVersion implements Constraint {
                    private final ClassFileVersion classFileVersion;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.classFileVersion.equals(((ForClassFileVersion) obj).classFileVersion);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.classFileVersion.hashCode();
                    }

                    protected ForClassFileVersion(ClassFileVersion classFileVersion) {
                        this.classFileVersion = classFileVersion;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                        if ((i & 8192) != 0 && !this.classFileVersion.isAtLeast(ClassFileVersion.JAVA_V5)) {
                            throw new IllegalStateException("Cannot define annotation type for class file version " + this.classFileVersion);
                        }
                        if (z2 && !this.classFileVersion.isAtLeast(ClassFileVersion.JAVA_V4)) {
                            throw new IllegalStateException("Cannot define a generic type for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                        if (z4 && !this.classFileVersion.isAtLeast(ClassFileVersion.JAVA_V4)) {
                            throw new IllegalStateException("Cannot define generic field '" + str + "' for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                        if (z8 && !this.classFileVersion.isAtLeast(ClassFileVersion.JAVA_V4)) {
                            throw new IllegalStateException("Cannot define generic method '" + str + "' for class file version " + this.classFileVersion);
                        }
                        if (!z5 && z) {
                            throw new IllegalStateException("Cannot define static or non-virtual method '" + str + "' to be abstract");
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V5)) {
                            throw new IllegalStateException("Cannot write annotations for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V5)) {
                            throw new IllegalStateException("Cannot write type annotations for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V8)) {
                            throw new IllegalStateException("Cannot invoke default method for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V5)) {
                            throw new IllegalStateException("Cannot write type to constant pool for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V7)) {
                            throw new IllegalStateException("Cannot write method type to constant pool for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V7)) {
                            throw new IllegalStateException("Cannot write method handle to constant pool for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V7)) {
                            throw new IllegalStateException("Cannot write invoke dynamic instruction for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                        if (this.classFileVersion.isGreaterThan(ClassFileVersion.JAVA_V5)) {
                            throw new IllegalStateException("Cannot write subroutine for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V11)) {
                            throw new IllegalStateException("Cannot write dynamic constant for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V11)) {
                            throw new IllegalStateException("Cannot define nest mate for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V14)) {
                            throw new IllegalStateException("Cannot define record for class file version " + this.classFileVersion);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                        if (this.classFileVersion.isLessThan(ClassFileVersion.JAVA_V17)) {
                            throw new IllegalStateException("Cannot define permitted subclasses for class file version " + this.classFileVersion);
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class Compound implements Constraint {
                    private final List<Constraint> constraints = new ArrayList();

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.constraints.equals(((Compound) obj).constraints);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.constraints.hashCode();
                    }

                    public Compound(List<? extends Constraint> list) {
                        for (Constraint constraint : list) {
                            if (constraint instanceof Compound) {
                                this.constraints.addAll(((Compound) constraint).constraints);
                            } else {
                                this.constraints.add(constraint);
                            }
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertType(int i, boolean z, boolean z2) {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertType(i, z, z2);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertField(String str, boolean z, boolean z2, boolean z3, boolean z4) {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertField(str, z, z2, z3, z4);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethod(String str, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertMethod(str, z, z2, z3, z4, z5, z6, z7, z8);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultValue(String str) {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertDefaultValue(str);
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDefaultMethodCall() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertDefaultMethodCall();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertAnnotation() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertAnnotation();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeAnnotation() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertTypeAnnotation();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertTypeInConstantPool() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertTypeInConstantPool();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertMethodTypeInConstantPool() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertMethodTypeInConstantPool();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertHandleInConstantPool() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertHandleInConstantPool();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertInvokeDynamic() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertInvokeDynamic();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertSubRoutine() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertSubRoutine();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertDynamicValueInConstantPool() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertDynamicValueInConstantPool();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertNestMate() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertNestMate();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertRecord() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertRecord();
                        }
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ValidatingClassVisitor.Constraint
                    public void assertPermittedSubclass() {
                        Iterator<Constraint> it = this.constraints.iterator();
                        while (it.hasNext()) {
                            it.next().assertPermittedSubclass();
                        }
                    }
                }
            }

            protected class ValidatingFieldVisitor extends FieldVisitor {
                protected ValidatingFieldVisitor(FieldVisitor fieldVisitor) {
                    super(OpenedClassReader.ASM_API, fieldVisitor);
                }

                @Override // net.bytebuddy.jar.asm.FieldVisitor
                public AnnotationVisitor visitAnnotation(String str, boolean z) {
                    ValidatingClassVisitor.this.constraint.assertAnnotation();
                    return super.visitAnnotation(str, z);
                }
            }

            protected class ValidatingMethodVisitor extends MethodVisitor {

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f361name;

                protected ValidatingMethodVisitor(MethodVisitor methodVisitor, String str) {
                    super(OpenedClassReader.ASM_API, methodVisitor);
                    this.f361name = str;
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                @MaybeNull
                public AnnotationVisitor visitAnnotation(String str, boolean z) {
                    ValidatingClassVisitor.this.constraint.assertAnnotation();
                    return super.visitAnnotation(str, z);
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                @MaybeNull
                public AnnotationVisitor visitAnnotationDefault() {
                    ValidatingClassVisitor.this.constraint.assertDefaultValue(this.f361name);
                    return super.visitAnnotationDefault();
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitLdcInsn(Object obj) {
                    if (obj instanceof Type) {
                        switch (((Type) obj).getSort()) {
                            case 9:
                            case 10:
                                ValidatingClassVisitor.this.constraint.assertTypeInConstantPool();
                                break;
                            case 11:
                                ValidatingClassVisitor.this.constraint.assertMethodTypeInConstantPool();
                                break;
                        }
                    } else if (obj instanceof Handle) {
                        ValidatingClassVisitor.this.constraint.assertHandleInConstantPool();
                    } else if (obj instanceof ConstantDynamic) {
                        ValidatingClassVisitor.this.constraint.assertDynamicValueInConstantPool();
                    }
                    super.visitLdcInsn(obj);
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
                    if (z && i == 183) {
                        ValidatingClassVisitor.this.constraint.assertDefaultMethodCall();
                    }
                    super.visitMethodInsn(i, str, str2, str3, z);
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
                    ValidatingClassVisitor.this.constraint.assertInvokeDynamic();
                    for (Object obj : objArr) {
                        if (obj instanceof ConstantDynamic) {
                            ValidatingClassVisitor.this.constraint.assertDynamicValueInConstantPool();
                        }
                    }
                    super.visitInvokeDynamicInsn(str, str2, handle, objArr);
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitJumpInsn(int i, Label label) {
                    if (i == 168) {
                        ValidatingClassVisitor.this.constraint.assertSubRoutine();
                    }
                    super.visitJumpInsn(i, label);
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static abstract class ForInlining<U> extends Default<U> {

            @AlwaysNull
            private static final AnnotationVisitor IGNORE_ANNOTATION = null;

            @AlwaysNull
            private static final FieldVisitor IGNORE_FIELD = null;

            @AlwaysNull
            private static final MethodVisitor IGNORE_METHOD = null;

            @AlwaysNull
            private static final RecordComponentVisitor IGNORE_RECORD_COMPONENT = null;
            protected final ClassFileLocator classFileLocator;
            protected final TypeDescription originalType;

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default
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
                ForInlining forInlining = (ForInlining) obj;
                return this.originalType.equals(forInlining.originalType) && this.classFileLocator.equals(forInlining.classFileLocator);
            }

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default
            public int hashCode() {
                return (((super.hashCode() * 31) + this.originalType.hashCode()) * 31) + this.classFileLocator.hashCode();
            }

            protected abstract ClassVisitor writeTo(ClassVisitor classVisitor, TypeInitializer typeInitializer, ContextRegistry contextRegistry, int i, int i2);

            protected ForInlining(TypeDescription typeDescription, ClassFileVersion classFileVersion, FieldPool fieldPool, RecordComponentPool recordComponentPool, List<? extends DynamicType> list, FieldList<FieldDescription.InDefinedShape> fieldList, MethodList<?> methodList, MethodList<?> methodList2, RecordComponentList<RecordComponentDescription.InDefinedShape> recordComponentList, LoadedTypeInitializer loadedTypeInitializer, TypeInitializer typeInitializer, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool, TypeDescription typeDescription2, ClassFileLocator classFileLocator) {
                super(typeDescription, classFileVersion, fieldPool, recordComponentPool, list, fieldList, methodList, methodList2, recordComponentList, loadedTypeInitializer, typeInitializer, typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool);
                this.originalType = typeDescription2;
                this.classFileLocator = classFileLocator;
            }

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter
            public ContextClassVisitor wrap(ClassVisitor classVisitor, int i, int i2) {
                ContextRegistry contextRegistry = new ContextRegistry();
                return new RegistryContextClassVisitor(writeTo(ValidatingClassVisitor.of(classVisitor, this.typeValidation), this.typeInitializer, contextRegistry, this.asmVisitorWrapper.mergeWriter(i), this.asmVisitorWrapper.mergeReader(i2)), contextRegistry);
            }

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default
            protected Default<U>.UnresolvedType create(TypeInitializer typeInitializer, ClassDumpAction.Dispatcher dispatcher) {
                try {
                    int iMergeWriter = this.asmVisitorWrapper.mergeWriter(0);
                    int iMergeReader = this.asmVisitorWrapper.mergeReader(0);
                    byte[] bArrResolve = this.classFileLocator.locate(this.originalType.getName()).resolve();
                    dispatcher.dump(this.instrumentedType, true, bArrResolve);
                    ClassReader classReaderOf = OpenedClassReader.of(bArrResolve);
                    ClassWriter classWriterResolve = this.classWriterStrategy.resolve(iMergeWriter, this.typePool, classReaderOf);
                    ContextRegistry contextRegistry = new ContextRegistry();
                    classReaderOf.accept(writeTo(ValidatingClassVisitor.of(classWriterResolve, this.typeValidation), typeInitializer, contextRegistry, iMergeWriter, iMergeReader), iMergeReader);
                    return new UnresolvedType(classWriterResolve.toByteArray(), contextRegistry.getAuxiliaryTypes());
                } catch (IOException e) {
                    throw new RuntimeException("The class file could not be written", e);
                }
            }

            protected class RegistryContextClassVisitor extends ContextClassVisitor {
                private final ContextRegistry contextRegistry;

                protected RegistryContextClassVisitor(ClassVisitor classVisitor, ContextRegistry contextRegistry) {
                    super(classVisitor);
                    this.contextRegistry = contextRegistry;
                }

                @Override // net.bytebuddy.utility.visitor.ContextClassVisitor
                public List<DynamicType> getAuxiliaryTypes() {
                    return CompoundList.of((List) ForInlining.this.auxiliaryTypes, (List) this.contextRegistry.getAuxiliaryTypes());
                }

                @Override // net.bytebuddy.utility.visitor.ContextClassVisitor
                public LoadedTypeInitializer getLoadedTypeInitializer() {
                    return ForInlining.this.loadedTypeInitializer;
                }
            }

            protected static class ContextRegistry {

                @UnknownNull
                private Implementation.Context.ExtractableView implementationContext;

                protected ContextRegistry() {
                }

                public void setImplementationContext(Implementation.Context.ExtractableView extractableView) {
                    this.implementationContext = extractableView;
                }

                public List<DynamicType> getAuxiliaryTypes() {
                    return this.implementationContext.getAuxiliaryTypes();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class WithFullProcessing<V> extends ForInlining<V> {
                private static final Object[] EMPTY = new Object[0];
                private final Implementation.Target.Factory implementationTargetFactory;
                private final MethodRebaseResolver methodRebaseResolver;
                private final MethodRegistry.Prepared methodRegistry;

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining, net.bytebuddy.dynamic.scaffold.TypeWriter.Default
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
                    WithFullProcessing withFullProcessing = (WithFullProcessing) obj;
                    return this.methodRegistry.equals(withFullProcessing.methodRegistry) && this.implementationTargetFactory.equals(withFullProcessing.implementationTargetFactory) && this.methodRebaseResolver.equals(withFullProcessing.methodRebaseResolver);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining, net.bytebuddy.dynamic.scaffold.TypeWriter.Default
                public int hashCode() {
                    return (((((super.hashCode() * 31) + this.methodRegistry.hashCode()) * 31) + this.implementationTargetFactory.hashCode()) * 31) + this.methodRebaseResolver.hashCode();
                }

                protected WithFullProcessing(TypeDescription typeDescription, ClassFileVersion classFileVersion, FieldPool fieldPool, RecordComponentPool recordComponentPool, List<? extends DynamicType> list, FieldList<FieldDescription.InDefinedShape> fieldList, MethodList<?> methodList, MethodList<?> methodList2, RecordComponentList<RecordComponentDescription.InDefinedShape> recordComponentList, LoadedTypeInitializer loadedTypeInitializer, TypeInitializer typeInitializer, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool, TypeDescription typeDescription2, ClassFileLocator classFileLocator, MethodRegistry.Prepared prepared, Implementation.Target.Factory factory3, MethodRebaseResolver methodRebaseResolver) {
                    super(typeDescription, classFileVersion, fieldPool, recordComponentPool, list, fieldList, methodList, methodList2, recordComponentList, loadedTypeInitializer, typeInitializer, typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool, typeDescription2, classFileLocator);
                    this.methodRegistry = prepared;
                    this.implementationTargetFactory = factory3;
                    this.methodRebaseResolver = methodRebaseResolver;
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining
                protected ClassVisitor writeTo(ClassVisitor classVisitor, TypeInitializer typeInitializer, ContextRegistry contextRegistry, int i, int i2) {
                    RedefinitionClassVisitor redefinitionClassVisitor = new RedefinitionClassVisitor(classVisitor, typeInitializer, contextRegistry, i, i2);
                    return this.originalType.getName().equals(this.instrumentedType.getName()) ? redefinitionClassVisitor : new OpenedClassRemapper(redefinitionClassVisitor, new SimpleRemapper(this.originalType.getInternalName(), this.instrumentedType.getInternalName()));
                }

                protected static class OpenedClassRemapper extends ClassRemapper {
                    protected OpenedClassRemapper(ClassVisitor classVisitor, Remapper remapper) {
                        super(OpenedClassReader.ASM_API, classVisitor, remapper);
                    }
                }

                protected interface InitializationHandler {
                    void complete(ClassVisitor classVisitor, Implementation.Context.ExtractableView extractableView);

                    public static class Creating extends TypeInitializer.Drain.Default implements InitializationHandler {
                        protected Creating(TypeDescription typeDescription, MethodPool methodPool, AnnotationValueFilter.Factory factory) {
                            super(typeDescription, methodPool, factory);
                        }

                        @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler
                        public void complete(ClassVisitor classVisitor, Implementation.Context.ExtractableView extractableView) {
                            extractableView.drain(this, classVisitor, this.annotationValueFilterFactory);
                        }
                    }

                    public static abstract class Appending extends MethodVisitor implements InitializationHandler, TypeInitializer.Drain {
                        protected final AnnotationValueFilter.Factory annotationValueFilterFactory;
                        protected final FrameWriter frameWriter;
                        protected final TypeDescription instrumentedType;
                        protected int localVariableLength;
                        protected final MethodPool.Record record;
                        protected int stackSize;

                        protected abstract void onComplete(Implementation.Context context);

                        protected abstract void onStart();

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public abstract void visitEnd();

                        protected Appending(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                            super(OpenedClassReader.ASM_API, methodVisitor);
                            this.instrumentedType = typeDescription;
                            this.record = record;
                            this.annotationValueFilterFactory = factory;
                            if (!z) {
                                this.frameWriter = FrameWriter.NoOp.INSTANCE;
                            } else if (z2) {
                                this.frameWriter = FrameWriter.Expanding.INSTANCE;
                            } else {
                                this.frameWriter = new FrameWriter.Active();
                            }
                        }

                        protected static InitializationHandler of(boolean z, MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool methodPool, AnnotationValueFilter.Factory factory, boolean z2, boolean z3) {
                            if (z) {
                                return withDrain(methodVisitor, typeDescription, methodPool, factory, z2, z3);
                            }
                            return withoutDrain(methodVisitor, typeDescription, methodPool, factory, z2, z3);
                        }

                        private static WithDrain withDrain(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool methodPool, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                            MethodPool.Record recordTarget = methodPool.target(new MethodDescription.Latent.TypeInitializer(typeDescription));
                            return recordTarget.getSort().isImplemented() ? new WithDrain.WithActiveRecord(methodVisitor, typeDescription, recordTarget, factory, z, z2) : new WithDrain.WithoutActiveRecord(methodVisitor, typeDescription, recordTarget, factory, z, z2);
                        }

                        private static WithoutDrain withoutDrain(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool methodPool, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                            MethodPool.Record recordTarget = methodPool.target(new MethodDescription.Latent.TypeInitializer(typeDescription));
                            return recordTarget.getSort().isImplemented() ? new WithoutDrain.WithActiveRecord(methodVisitor, typeDescription, recordTarget, factory, z, z2) : new WithoutDrain.WithoutActiveRecord(methodVisitor, typeDescription, recordTarget, factory);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitCode() {
                            this.record.applyAttributes(this.mv, this.annotationValueFilterFactory);
                            super.visitCode();
                            onStart();
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitFrame(int i, int i2, @MaybeNull Object[] objArr, int i3, @MaybeNull Object[] objArr2) {
                            super.visitFrame(i, i2, objArr, i3, objArr2);
                            this.frameWriter.onFrame(i, i2);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitMaxs(int i, int i2) {
                            this.stackSize = i;
                            this.localVariableLength = i2;
                        }

                        @Override // net.bytebuddy.dynamic.scaffold.TypeInitializer.Drain
                        public void apply(ClassVisitor classVisitor, TypeInitializer typeInitializer, Implementation.Context context) {
                            ByteCodeAppender.Size sizeApply = typeInitializer.apply(this.mv, context, new MethodDescription.Latent.TypeInitializer(this.instrumentedType));
                            this.stackSize = Math.max(this.stackSize, sizeApply.getOperandStackSize());
                            this.localVariableLength = Math.max(this.localVariableLength, sizeApply.getLocalVariableSize());
                            onComplete(context);
                        }

                        @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler
                        public void complete(ClassVisitor classVisitor, Implementation.Context.ExtractableView extractableView) {
                            extractableView.drain(this, classVisitor, this.annotationValueFilterFactory);
                            this.mv.visitMaxs(this.stackSize, this.localVariableLength);
                            this.mv.visitEnd();
                        }

                        protected interface FrameWriter {
                            public static final Object[] EMPTY = new Object[0];

                            public enum NoOp implements FrameWriter {
                                INSTANCE;

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.FrameWriter
                                public void emitFrame(MethodVisitor methodVisitor) {
                                }

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.FrameWriter
                                public void onFrame(int i, int i2) {
                                }
                            }

                            void emitFrame(MethodVisitor methodVisitor);

                            void onFrame(int i, int i2);

                            public enum Expanding implements FrameWriter {
                                INSTANCE;

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.FrameWriter
                                public void onFrame(int i, int i2) {
                                }

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.FrameWriter
                                public void emitFrame(MethodVisitor methodVisitor) {
                                    methodVisitor.visitFrame(-1, EMPTY.length, EMPTY, EMPTY.length, EMPTY);
                                    methodVisitor.visitInsn(0);
                                }
                            }

                            public static class Active implements FrameWriter {
                                private int currentLocalVariableLength;

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.FrameWriter
                                public void onFrame(int i, int i2) {
                                    if (i == -1 || i == 0) {
                                        this.currentLocalVariableLength = i2;
                                        return;
                                    }
                                    if (i == 1) {
                                        this.currentLocalVariableLength += i2;
                                    } else if (i == 2) {
                                        this.currentLocalVariableLength -= i2;
                                    } else if (i != 3 && i != 4) {
                                        throw new IllegalStateException("Unexpected frame type: " + i);
                                    }
                                }

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.FrameWriter
                                public void emitFrame(MethodVisitor methodVisitor) {
                                    int i = this.currentLocalVariableLength;
                                    if (i == 0) {
                                        methodVisitor.visitFrame(3, EMPTY.length, EMPTY, EMPTY.length, EMPTY);
                                    } else if (i > 3) {
                                        methodVisitor.visitFrame(0, EMPTY.length, EMPTY, EMPTY.length, EMPTY);
                                    } else {
                                        methodVisitor.visitFrame(2, i, EMPTY, EMPTY.length, EMPTY);
                                    }
                                    methodVisitor.visitInsn(0);
                                    this.currentLocalVariableLength = 0;
                                }
                            }
                        }

                        protected static abstract class WithoutDrain extends Appending {
                            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending
                            protected void onStart() {
                            }

                            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending, net.bytebuddy.jar.asm.MethodVisitor
                            public void visitEnd() {
                            }

                            protected WithoutDrain(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                                super(methodVisitor, typeDescription, record, factory, z, z2);
                            }

                            protected static class WithoutActiveRecord extends WithoutDrain {
                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending
                                protected void onComplete(Implementation.Context context) {
                                }

                                protected WithoutActiveRecord(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory) {
                                    super(methodVisitor, typeDescription, record, factory, false, false);
                                }
                            }

                            protected static class WithActiveRecord extends WithoutDrain {
                                private final Label label;

                                protected WithActiveRecord(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                                    super(methodVisitor, typeDescription, record, factory, z, z2);
                                    this.label = new Label();
                                }

                                @Override // net.bytebuddy.jar.asm.MethodVisitor
                                public void visitInsn(int i) {
                                    if (i == 177) {
                                        this.mv.visitJumpInsn(167, this.label);
                                    } else {
                                        super.visitInsn(i);
                                    }
                                }

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending
                                protected void onComplete(Implementation.Context context) {
                                    this.mv.visitLabel(this.label);
                                    this.frameWriter.emitFrame(this.mv);
                                    ByteCodeAppender.Size sizeApplyCode = this.record.applyCode(this.mv, context);
                                    this.stackSize = Math.max(this.stackSize, sizeApplyCode.getOperandStackSize());
                                    this.localVariableLength = Math.max(this.localVariableLength, sizeApplyCode.getLocalVariableSize());
                                }
                            }
                        }

                        protected static abstract class WithDrain extends Appending {
                            protected final Label appended;
                            protected final Label original;

                            protected abstract void onAfterComplete(Implementation.Context context);

                            protected WithDrain(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                                super(methodVisitor, typeDescription, record, factory, z, z2);
                                this.appended = new Label();
                                this.original = new Label();
                            }

                            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending
                            protected void onStart() {
                                this.mv.visitJumpInsn(167, this.appended);
                                this.mv.visitLabel(this.original);
                                this.frameWriter.emitFrame(this.mv);
                            }

                            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending, net.bytebuddy.jar.asm.MethodVisitor
                            public void visitEnd() {
                                this.mv.visitLabel(this.appended);
                                this.frameWriter.emitFrame(this.mv);
                            }

                            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending
                            protected void onComplete(Implementation.Context context) {
                                this.mv.visitJumpInsn(167, this.original);
                                onAfterComplete(context);
                            }

                            protected static class WithoutActiveRecord extends WithDrain {
                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.WithDrain
                                protected void onAfterComplete(Implementation.Context context) {
                                }

                                protected WithoutActiveRecord(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                                    super(methodVisitor, typeDescription, record, factory, z, z2);
                                }
                            }

                            protected static class WithActiveRecord extends WithDrain {
                                private final Label label;

                                protected WithActiveRecord(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodPool.Record record, AnnotationValueFilter.Factory factory, boolean z, boolean z2) {
                                    super(methodVisitor, typeDescription, record, factory, z, z2);
                                    this.label = new Label();
                                }

                                @Override // net.bytebuddy.jar.asm.MethodVisitor
                                public void visitInsn(int i) {
                                    if (i == 177) {
                                        this.mv.visitJumpInsn(167, this.label);
                                    } else {
                                        super.visitInsn(i);
                                    }
                                }

                                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining.WithFullProcessing.InitializationHandler.Appending.WithDrain
                                protected void onAfterComplete(Implementation.Context context) {
                                    this.mv.visitLabel(this.label);
                                    this.frameWriter.emitFrame(this.mv);
                                    ByteCodeAppender.Size sizeApplyCode = this.record.applyCode(this.mv, context);
                                    this.stackSize = Math.max(this.stackSize, sizeApplyCode.getOperandStackSize());
                                    this.localVariableLength = Math.max(this.localVariableLength, sizeApplyCode.getLocalVariableSize());
                                }
                            }
                        }
                    }
                }

                protected class RedefinitionClassVisitor extends MetadataAwareClassVisitor {
                    private final ContextRegistry contextRegistry;
                    private final LinkedHashMap<SignatureKey, FieldDescription> declarableFields;
                    private final LinkedHashMap<SignatureKey, MethodDescription> declarableMethods;
                    private final LinkedHashMap<String, RecordComponentDescription> declarableRecordComponents;
                    private final LinkedHashMap<String, TypeDescription> declaredTypes;

                    @UnknownNull
                    private Implementation.Context.ExtractableView implementationContext;

                    @UnknownNull
                    private InitializationHandler initializationHandler;

                    @UnknownNull
                    private MethodPool methodPool;
                    private final Set<String> nestMembers;

                    @MaybeNull
                    private final Set<String> permittedSubclasses;
                    private final int readerFlags;
                    private boolean retainDeprecationModifiers;
                    private final TypeInitializer typeInitializer;
                    private final int writerFlags;

                    protected RedefinitionClassVisitor(ClassVisitor classVisitor, TypeInitializer typeInitializer, ContextRegistry contextRegistry, int i, int i2) {
                        super(OpenedClassReader.ASM_API, classVisitor);
                        this.typeInitializer = typeInitializer;
                        this.contextRegistry = contextRegistry;
                        this.writerFlags = i;
                        this.readerFlags = i2;
                        this.declarableFields = new LinkedHashMap<>((int) Math.ceil(((double) WithFullProcessing.this.fields.size()) / 0.75d));
                        for (FieldDescription fieldDescription : WithFullProcessing.this.fields) {
                            this.declarableFields.put(new SignatureKey(fieldDescription.getInternalName(), fieldDescription.getDescriptor()), fieldDescription);
                        }
                        this.declarableMethods = new LinkedHashMap<>((int) Math.ceil(((double) WithFullProcessing.this.instrumentedMethods.size()) / 0.75d));
                        Iterator it = WithFullProcessing.this.instrumentedMethods.iterator();
                        while (it.hasNext()) {
                            MethodDescription methodDescription = (MethodDescription) it.next();
                            this.declarableMethods.put(new SignatureKey(methodDescription.getInternalName(), methodDescription.getDescriptor()), methodDescription);
                        }
                        this.declarableRecordComponents = new LinkedHashMap<>((int) Math.ceil(((double) WithFullProcessing.this.recordComponents.size()) / 0.75d));
                        for (RecordComponentDescription recordComponentDescription : WithFullProcessing.this.recordComponents) {
                            this.declarableRecordComponents.put(recordComponentDescription.getActualName(), recordComponentDescription);
                        }
                        if (WithFullProcessing.this.instrumentedType.isNestHost()) {
                            this.nestMembers = new LinkedHashSet((int) Math.ceil(((double) WithFullProcessing.this.instrumentedType.getNestMembers().size()) / 0.75d));
                            Iterator it2 = WithFullProcessing.this.instrumentedType.getNestMembers().filter(ElementMatchers.not(ElementMatchers.is(WithFullProcessing.this.instrumentedType))).iterator();
                            while (it2.hasNext()) {
                                this.nestMembers.add(((TypeDescription) it2.next()).getInternalName());
                            }
                        } else {
                            this.nestMembers = Collections.emptySet();
                        }
                        this.declaredTypes = new LinkedHashMap<>((int) Math.ceil(((double) WithFullProcessing.this.instrumentedType.getDeclaredTypes().size()) / 0.75d));
                        for (TypeDescription typeDescription : WithFullProcessing.this.instrumentedType.getDeclaredTypes()) {
                            this.declaredTypes.put(typeDescription.getInternalName(), typeDescription);
                        }
                        if (WithFullProcessing.this.instrumentedType.isSealed()) {
                            this.permittedSubclasses = new LinkedHashSet((int) Math.ceil(((double) WithFullProcessing.this.instrumentedType.getPermittedSubtypes().size()) / 0.75d));
                            Iterator it3 = WithFullProcessing.this.instrumentedType.getPermittedSubtypes().iterator();
                            while (it3.hasNext()) {
                                this.permittedSubclasses.add(((TypeDescription) it3.next()).getInternalName());
                            }
                            return;
                        }
                        this.permittedSubclasses = null;
                    }

                    @Override // net.bytebuddy.jar.asm.ClassVisitor
                    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
                        Implementation.Context.FrameGeneration frameGeneration;
                        String internalName;
                        ClassFileVersion classFileVersionOfMinorMajor = ClassFileVersion.ofMinorMajor(i);
                        this.methodPool = WithFullProcessing.this.methodRegistry.compile(WithFullProcessing.this.implementationTargetFactory, classFileVersionOfMinorMajor);
                        this.initializationHandler = new InitializationHandler.Creating(WithFullProcessing.this.instrumentedType, this.methodPool, WithFullProcessing.this.annotationValueFilterFactory);
                        Implementation.Context.Factory factory = WithFullProcessing.this.implementationContextFactory;
                        TypeDescription typeDescription = WithFullProcessing.this.instrumentedType;
                        AuxiliaryType.NamingStrategy namingStrategy = WithFullProcessing.this.auxiliaryTypeNamingStrategy;
                        TypeInitializer typeInitializer = this.typeInitializer;
                        ClassFileVersion classFileVersion = WithFullProcessing.this.classFileVersion;
                        if ((this.writerFlags & 2) == 0 && classFileVersionOfMinorMajor.isAtLeast(ClassFileVersion.JAVA_V6)) {
                            frameGeneration = (this.readerFlags & 8) == 0 ? Implementation.Context.FrameGeneration.GENERATE : Implementation.Context.FrameGeneration.EXPAND;
                        } else {
                            frameGeneration = Implementation.Context.FrameGeneration.DISABLED;
                        }
                        this.implementationContext = factory.make(typeDescription, namingStrategy, typeInitializer, classFileVersionOfMinorMajor, classFileVersion, frameGeneration);
                        this.retainDeprecationModifiers = classFileVersionOfMinorMajor.isLessThan(ClassFileVersion.JAVA_V5);
                        this.contextRegistry.setImplementationContext(this.implementationContext);
                        this.cv = WithFullProcessing.this.asmVisitorWrapper.wrap(WithFullProcessing.this.instrumentedType, this.cv, this.implementationContext, WithFullProcessing.this.typePool, WithFullProcessing.this.fields, WithFullProcessing.this.methods, this.writerFlags, this.readerFlags);
                        ClassVisitor classVisitor = this.cv;
                        int i3 = 0;
                        int actualModifiers = WithFullProcessing.this.instrumentedType.getActualModifiers(((i2 & 32) == 0 || WithFullProcessing.this.instrumentedType.isInterface()) ? false : true) | resolveDeprecationModifiers(i2);
                        if ((i2 & 16) != 0 && WithFullProcessing.this.instrumentedType.isAnonymousType()) {
                            i3 = 16;
                        }
                        int i4 = actualModifiers | i3;
                        String internalName2 = WithFullProcessing.this.instrumentedType.getInternalName();
                        if (!TypeDescription.AbstractBase.RAW_TYPES) {
                            str2 = WithFullProcessing.this.instrumentedType.getGenericSignature();
                        }
                        String str4 = str2;
                        if (WithFullProcessing.this.instrumentedType.getSuperClass() == null) {
                            internalName = WithFullProcessing.this.instrumentedType.isInterface() ? TypeDescription.ForLoadedType.of(Object.class).getInternalName() : Default.NO_REFERENCE;
                        } else {
                            internalName = WithFullProcessing.this.instrumentedType.getSuperClass().asErasure().getInternalName();
                        }
                        classVisitor.visit(i, i4, internalName2, str4, internalName, WithFullProcessing.this.instrumentedType.getInterfaces().asErasures().toInternalNames());
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitNestHost(String str) {
                        onNestHost();
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onNestHost() {
                        if (WithFullProcessing.this.instrumentedType.isNestHost()) {
                            return;
                        }
                        this.cv.visitNestHost(WithFullProcessing.this.instrumentedType.getNestHost().getInternalName());
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitPermittedSubclass(String str) {
                        Set<String> set = this.permittedSubclasses;
                        if (set == null || !set.remove(str)) {
                            return;
                        }
                        this.cv.visitPermittedSubclass(str);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitOuterClass(String str, @MaybeNull String str2, @MaybeNull String str3) {
                        try {
                            onOuterType();
                        } catch (Throwable unused) {
                            this.cv.visitOuterClass(str, str2, str3);
                        }
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onOuterType() {
                        MethodDescription.InDefinedShape enclosingMethod = WithFullProcessing.this.instrumentedType.getEnclosingMethod();
                        if (enclosingMethod != null) {
                            this.cv.visitOuterClass(enclosingMethod.getDeclaringType().getInternalName(), enclosingMethod.getInternalName(), enclosingMethod.getDescriptor());
                        } else if (WithFullProcessing.this.instrumentedType.isLocalType() || WithFullProcessing.this.instrumentedType.isAnonymousType()) {
                            this.cv.visitOuterClass(WithFullProcessing.this.instrumentedType.getEnclosingType().getInternalName(), Default.NO_REFERENCE, Default.NO_REFERENCE);
                        }
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onAfterAttributes() {
                        WithFullProcessing.this.typeAttributeAppender.apply(this.cv, WithFullProcessing.this.instrumentedType, WithFullProcessing.this.annotationValueFilterFactory.on(WithFullProcessing.this.instrumentedType));
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected AnnotationVisitor onVisitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
                        if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                            return ForInlining.IGNORE_ANNOTATION;
                        }
                        return this.cv.visitTypeAnnotation(i, typePath, str, z);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected AnnotationVisitor onVisitAnnotation(String str, boolean z) {
                        if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                            return ForInlining.IGNORE_ANNOTATION;
                        }
                        return this.cv.visitAnnotation(str, z);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected RecordComponentVisitor onVisitRecordComponent(String str, String str2, @MaybeNull String str3) {
                        RecordComponentDescription recordComponentDescriptionRemove = this.declarableRecordComponents.remove(str);
                        if (recordComponentDescriptionRemove != null) {
                            RecordComponentPool.Record recordTarget = WithFullProcessing.this.recordComponentPool.target(recordComponentDescriptionRemove);
                            if (!recordTarget.isImplicit()) {
                                return redefine(recordTarget, str3);
                            }
                        }
                        return this.cv.visitRecordComponent(str, str2, str3);
                    }

                    @MaybeNull
                    protected RecordComponentVisitor redefine(RecordComponentPool.Record record, @MaybeNull String str) {
                        RecordComponentDescription recordComponent = record.getRecordComponent();
                        ClassVisitor classVisitor = this.cv;
                        String actualName = recordComponent.getActualName();
                        String descriptor = recordComponent.getDescriptor();
                        if (!TypeDescription.AbstractBase.RAW_TYPES) {
                            str = recordComponent.getGenericSignature();
                        }
                        RecordComponentVisitor recordComponentVisitorVisitRecordComponent = classVisitor.visitRecordComponent(actualName, descriptor, str);
                        return recordComponentVisitorVisitRecordComponent == null ? ForInlining.IGNORE_RECORD_COMPONENT : new AttributeObtainingRecordComponentVisitor(recordComponentVisitorVisitRecordComponent, record);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected FieldVisitor onVisitField(int i, String str, String str2, @MaybeNull String str3, @MaybeNull Object obj) {
                        FieldDescription fieldDescriptionRemove = this.declarableFields.remove(new SignatureKey(str, str2));
                        if (fieldDescriptionRemove != null) {
                            FieldPool.Record recordTarget = WithFullProcessing.this.fieldPool.target(fieldDescriptionRemove);
                            if (!recordTarget.isImplicit()) {
                                return redefine(recordTarget, obj, i, str3);
                            }
                        }
                        return this.cv.visitField(i, str, str2, str3, obj);
                    }

                    @MaybeNull
                    protected FieldVisitor redefine(FieldPool.Record record, @MaybeNull Object obj, int i, @MaybeNull String str) {
                        FieldDescription field = record.getField();
                        ClassVisitor classVisitor = this.cv;
                        int actualModifiers = field.getActualModifiers() | resolveDeprecationModifiers(i);
                        String internalName = field.getInternalName();
                        String descriptor = field.getDescriptor();
                        if (!TypeDescription.AbstractBase.RAW_TYPES) {
                            str = field.getGenericSignature();
                        }
                        FieldVisitor fieldVisitorVisitField = classVisitor.visitField(actualModifiers, internalName, descriptor, str, record.resolveDefault(obj));
                        return fieldVisitorVisitField == null ? ForInlining.IGNORE_FIELD : new AttributeObtainingFieldVisitor(fieldVisitorVisitField, record);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected MethodVisitor onVisitMethod(int i, String str, String str2, @MaybeNull String str3, @MaybeNull String[] strArr) {
                        boolean z = true;
                        if (str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                            MethodVisitor methodVisitorVisitMethod = this.cv.visitMethod(i, str, str2, str3, strArr);
                            if (methodVisitorVisitMethod == null) {
                                return ForInlining.IGNORE_METHOD;
                            }
                            InitializationHandler initializationHandlerOf = InitializationHandler.Appending.of(this.implementationContext.isEnabled(), methodVisitorVisitMethod, WithFullProcessing.this.instrumentedType, this.methodPool, WithFullProcessing.this.annotationValueFilterFactory, (this.writerFlags & 2) == 0 && this.implementationContext.getClassFileVersion().isAtLeast(ClassFileVersion.JAVA_V6), (this.readerFlags & 8) != 0);
                            this.initializationHandler = initializationHandlerOf;
                            return (MethodVisitor) initializationHandlerOf;
                        }
                        MethodDescription methodDescriptionRemove = this.declarableMethods.remove(new SignatureKey(str, str2));
                        if (methodDescriptionRemove == null) {
                            return this.cv.visitMethod(i, str, str2, str3, strArr);
                        }
                        if ((i & 1024) == 0) {
                            z = false;
                        }
                        return redefine(methodDescriptionRemove, z, i, str3);
                    }

                    @MaybeNull
                    protected MethodVisitor redefine(MethodDescription methodDescription, boolean z, int i, @MaybeNull String str) {
                        MethodPool.Record recordTarget = this.methodPool.target(methodDescription);
                        if (!recordTarget.getSort().isDefined()) {
                            ClassVisitor classVisitor = this.cv;
                            int actualModifiers = methodDescription.getActualModifiers() | resolveDeprecationModifiers(i);
                            String internalName = methodDescription.getInternalName();
                            String descriptor = methodDescription.getDescriptor();
                            if (!TypeDescription.AbstractBase.RAW_TYPES) {
                                str = methodDescription.getGenericSignature();
                            }
                            return classVisitor.visitMethod(actualModifiers, internalName, descriptor, str, methodDescription.getExceptionTypes().asErasures().toInternalNames());
                        }
                        MethodDescription method = recordTarget.getMethod();
                        MethodVisitor methodVisitorVisitMethod = this.cv.visitMethod(ModifierContributor.Resolver.of(Collections.singleton(recordTarget.getVisibility())).resolve(method.getActualModifiers(recordTarget.getSort().isImplemented())) | resolveDeprecationModifiers(i), method.getInternalName(), method.getDescriptor(), TypeDescription.AbstractBase.RAW_TYPES ? str : method.getGenericSignature(), method.getExceptionTypes().asErasures().toInternalNames());
                        if (methodVisitorVisitMethod == null) {
                            return ForInlining.IGNORE_METHOD;
                        }
                        if (z) {
                            return new AttributeObtainingMethodVisitor(methodVisitorVisitMethod, recordTarget);
                        }
                        if (methodDescription.isNative()) {
                            MethodRebaseResolver.Resolution resolutionResolve = WithFullProcessing.this.methodRebaseResolver.resolve(method.asDefined());
                            if (resolutionResolve.isRebased()) {
                                int actualModifiers2 = resolutionResolve.getResolvedMethod().getActualModifiers() | resolveDeprecationModifiers(i);
                                String internalName2 = resolutionResolve.getResolvedMethod().getInternalName();
                                String descriptor2 = resolutionResolve.getResolvedMethod().getDescriptor();
                                if (!TypeDescription.AbstractBase.RAW_TYPES) {
                                    str = method.getGenericSignature();
                                }
                                MethodVisitor methodVisitorVisitMethod2 = super.visitMethod(actualModifiers2, internalName2, descriptor2, str, resolutionResolve.getResolvedMethod().getExceptionTypes().asErasures().toInternalNames());
                                if (methodVisitorVisitMethod2 != null) {
                                    methodVisitorVisitMethod2.visitEnd();
                                }
                            }
                            return new AttributeObtainingMethodVisitor(methodVisitorVisitMethod, recordTarget);
                        }
                        return new CodePreservingMethodVisitor(methodVisitorVisitMethod, recordTarget, WithFullProcessing.this.methodRebaseResolver.resolve(method.asDefined()));
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitInnerClass(String str, @MaybeNull String str2, @MaybeNull String str3, int i) {
                        String internalName;
                        String simpleName;
                        if (str.equals(WithFullProcessing.this.instrumentedType.getInternalName())) {
                            return;
                        }
                        TypeDescription typeDescriptionRemove = this.declaredTypes.remove(str);
                        if (typeDescriptionRemove == null) {
                            this.cv.visitInnerClass(str, str2, str3, i);
                            return;
                        }
                        ClassVisitor classVisitor = this.cv;
                        if (!typeDescriptionRemove.isMemberType() && (str2 == null || str3 != null || !typeDescriptionRemove.isAnonymousType())) {
                            internalName = Default.NO_REFERENCE;
                        } else {
                            internalName = WithFullProcessing.this.instrumentedType.getInternalName();
                        }
                        if (typeDescriptionRemove.isAnonymousType()) {
                            simpleName = Default.NO_REFERENCE;
                        } else {
                            simpleName = typeDescriptionRemove.getSimpleName();
                        }
                        classVisitor.visitInnerClass(str, internalName, simpleName, typeDescriptionRemove.getModifiers());
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitNestMember(String str) {
                        if (WithFullProcessing.this.instrumentedType.isNestHost() && this.nestMembers.remove(str)) {
                            this.cv.visitNestMember(str);
                        }
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitEnd() {
                        String internalName;
                        String simpleName;
                        Iterator<String> it = this.nestMembers.iterator();
                        while (it.hasNext()) {
                            this.cv.visitNestMember(it.next());
                        }
                        Set<String> set = this.permittedSubclasses;
                        if (set != null) {
                            Iterator<String> it2 = set.iterator();
                            while (it2.hasNext()) {
                                this.cv.visitPermittedSubclass(it2.next());
                            }
                        }
                        TypeDescription declaringType = WithFullProcessing.this.instrumentedType.getDeclaringType();
                        if (declaringType != null) {
                            this.cv.visitInnerClass(WithFullProcessing.this.instrumentedType.getInternalName(), declaringType.getInternalName(), WithFullProcessing.this.instrumentedType.getSimpleName(), WithFullProcessing.this.instrumentedType.getModifiers());
                        } else if (WithFullProcessing.this.instrumentedType.isLocalType()) {
                            this.cv.visitInnerClass(WithFullProcessing.this.instrumentedType.getInternalName(), Default.NO_REFERENCE, WithFullProcessing.this.instrumentedType.getSimpleName(), WithFullProcessing.this.instrumentedType.getModifiers());
                        } else if (WithFullProcessing.this.instrumentedType.isAnonymousType()) {
                            this.cv.visitInnerClass(WithFullProcessing.this.instrumentedType.getInternalName(), Default.NO_REFERENCE, Default.NO_REFERENCE, WithFullProcessing.this.instrumentedType.getModifiers());
                        }
                        for (TypeDescription typeDescription : this.declaredTypes.values()) {
                            ClassVisitor classVisitor = this.cv;
                            String internalName2 = typeDescription.getInternalName();
                            if (!typeDescription.isMemberType()) {
                                internalName = Default.NO_REFERENCE;
                            } else {
                                internalName = WithFullProcessing.this.instrumentedType.getInternalName();
                            }
                            if (typeDescription.isAnonymousType()) {
                                simpleName = Default.NO_REFERENCE;
                            } else {
                                simpleName = typeDescription.getSimpleName();
                            }
                            classVisitor.visitInnerClass(internalName2, internalName, simpleName, typeDescription.getModifiers());
                        }
                        Iterator<RecordComponentDescription> it3 = this.declarableRecordComponents.values().iterator();
                        while (it3.hasNext()) {
                            WithFullProcessing.this.recordComponentPool.target(it3.next()).apply(this.cv, WithFullProcessing.this.annotationValueFilterFactory);
                        }
                        Iterator<FieldDescription> it4 = this.declarableFields.values().iterator();
                        while (it4.hasNext()) {
                            WithFullProcessing.this.fieldPool.target(it4.next()).apply(this.cv, WithFullProcessing.this.annotationValueFilterFactory);
                        }
                        Iterator<MethodDescription> it5 = this.declarableMethods.values().iterator();
                        while (it5.hasNext()) {
                            this.methodPool.target(it5.next()).apply(this.cv, this.implementationContext, WithFullProcessing.this.annotationValueFilterFactory);
                        }
                        this.initializationHandler.complete(this.cv, this.implementationContext);
                        this.cv.visitEnd();
                    }

                    private int resolveDeprecationModifiers(int i) {
                        return (!this.retainDeprecationModifiers || (i & 131072) == 0) ? 0 : 131072;
                    }

                    protected class AttributeObtainingFieldVisitor extends FieldVisitor {
                        private final FieldPool.Record record;

                        protected AttributeObtainingFieldVisitor(FieldVisitor fieldVisitor, FieldPool.Record record) {
                            super(OpenedClassReader.ASM_API, fieldVisitor);
                            this.record = record;
                        }

                        @Override // net.bytebuddy.jar.asm.FieldVisitor
                        @MaybeNull
                        public AnnotationVisitor visitTypeAnnotation(int i, @MaybeNull TypePath typePath, String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitTypeAnnotation(i, typePath, str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.FieldVisitor
                        @MaybeNull
                        public AnnotationVisitor visitAnnotation(String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitAnnotation(str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.FieldVisitor
                        public void visitEnd() {
                            this.record.apply(this.fv, WithFullProcessing.this.annotationValueFilterFactory);
                            super.visitEnd();
                        }
                    }

                    protected class AttributeObtainingRecordComponentVisitor extends RecordComponentVisitor {
                        private final RecordComponentPool.Record record;

                        protected AttributeObtainingRecordComponentVisitor(RecordComponentVisitor recordComponentVisitor, RecordComponentPool.Record record) {
                            super(OpenedClassReader.ASM_API, recordComponentVisitor);
                            this.record = record;
                        }

                        @Override // net.bytebuddy.jar.asm.RecordComponentVisitor
                        public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitTypeAnnotation(i, typePath, str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.RecordComponentVisitor
                        public AnnotationVisitor visitAnnotation(String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitAnnotation(str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.RecordComponentVisitor
                        public void visitEnd() {
                            this.record.apply(getDelegate(), WithFullProcessing.this.annotationValueFilterFactory);
                            super.visitEnd();
                        }
                    }

                    protected class CodePreservingMethodVisitor extends MethodVisitor {
                        private final MethodVisitor actualMethodVisitor;
                        private final MethodPool.Record record;
                        private final MethodRebaseResolver.Resolution resolution;

                        protected CodePreservingMethodVisitor(MethodVisitor methodVisitor, MethodPool.Record record, MethodRebaseResolver.Resolution resolution) {
                            super(OpenedClassReader.ASM_API, methodVisitor);
                            this.actualMethodVisitor = methodVisitor;
                            this.record = record;
                            this.resolution = resolution;
                            record.applyHead(methodVisitor);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitAnnotationDefault() {
                            return ForInlining.IGNORE_ANNOTATION;
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitTypeAnnotation(int i, @MaybeNull TypePath typePath, String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitTypeAnnotation(i, typePath, str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitAnnotation(String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitAnnotation(str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitAnnotableParameterCount(int i, boolean z) {
                            if (WithFullProcessing.this.annotationRetention.isEnabled()) {
                                super.visitAnnotableParameterCount(i, z);
                            }
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitParameterAnnotation(i, str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitCode() {
                            this.record.applyBody(this.actualMethodVisitor, RedefinitionClassVisitor.this.implementationContext, WithFullProcessing.this.annotationValueFilterFactory);
                            this.actualMethodVisitor.visitEnd();
                            if (!this.resolution.isRebased()) {
                                this.mv = ForInlining.IGNORE_METHOD;
                                super.visitCode();
                                return;
                            }
                            this.mv = RedefinitionClassVisitor.this.cv.visitMethod(this.resolution.getResolvedMethod().getActualModifiers(), this.resolution.getResolvedMethod().getInternalName(), this.resolution.getResolvedMethod().getDescriptor(), this.resolution.getResolvedMethod().getGenericSignature(), this.resolution.getResolvedMethod().getExceptionTypes().asErasures().toInternalNames());
                            super.visitCode();
                            if (this.resolution.getAppendedParameters().isEmpty() || !RedefinitionClassVisitor.this.implementationContext.getFrameGeneration().isActive()) {
                                return;
                            }
                            if (RedefinitionClassVisitor.this.implementationContext.getFrameGeneration() == Implementation.Context.FrameGeneration.GENERATE && this.resolution.getAppendedParameters().size() < 4) {
                                super.visitFrame(2, this.resolution.getAppendedParameters().size(), WithFullProcessing.EMPTY, WithFullProcessing.EMPTY.length, WithFullProcessing.EMPTY);
                            } else {
                                int size = (this.resolution.getResolvedMethod().getParameters().size() - this.resolution.getAppendedParameters().size()) + 1;
                                Object[] objArr = new Object[size];
                                objArr[0] = Opcodes.UNINITIALIZED_THIS;
                                for (int i = 1; i < size; i++) {
                                    TypeDescription.Generic type = ((ParameterDescription.InDefinedShape) this.resolution.getResolvedMethod().getParameters().get(i - 1)).getType();
                                    if (type.represents(Boolean.TYPE) || type.represents(Byte.TYPE) || type.represents(Short.TYPE) || type.represents(Character.TYPE) || type.represents(Integer.TYPE)) {
                                        objArr[i] = Opcodes.INTEGER;
                                    } else if (type.represents(Long.TYPE)) {
                                        objArr[i] = Opcodes.LONG;
                                    } else if (type.represents(Float.TYPE)) {
                                        objArr[i] = Opcodes.FLOAT;
                                    } else if (type.represents(Double.TYPE)) {
                                        objArr[i] = Opcodes.DOUBLE;
                                    } else {
                                        objArr[i] = type.asErasure().getInternalName();
                                    }
                                }
                                super.visitFrame((RedefinitionClassVisitor.this.readerFlags & 8) == 0 ? 0 : -1, size, objArr, WithFullProcessing.EMPTY.length, WithFullProcessing.EMPTY);
                            }
                            super.visitInsn(0);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitMaxs(int i, int i2) {
                            super.visitMaxs(i, Math.max(i2, this.resolution.getResolvedMethod().getStackSize()));
                        }
                    }

                    protected class AttributeObtainingMethodVisitor extends MethodVisitor {
                        private final MethodVisitor actualMethodVisitor;
                        private final MethodPool.Record record;

                        protected AttributeObtainingMethodVisitor(MethodVisitor methodVisitor, MethodPool.Record record) {
                            super(OpenedClassReader.ASM_API, methodVisitor);
                            this.actualMethodVisitor = methodVisitor;
                            this.record = record;
                            record.applyHead(methodVisitor);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitAnnotationDefault() {
                            return ForInlining.IGNORE_ANNOTATION;
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitTypeAnnotation(int i, @MaybeNull TypePath typePath, String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitTypeAnnotation(i, typePath, str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitAnnotation(String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitAnnotation(str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitAnnotableParameterCount(int i, boolean z) {
                            if (WithFullProcessing.this.annotationRetention.isEnabled()) {
                                super.visitAnnotableParameterCount(i, z);
                            }
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        @MaybeNull
                        public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
                            if (!WithFullProcessing.this.annotationRetention.isEnabled()) {
                                return ForInlining.IGNORE_ANNOTATION;
                            }
                            return super.visitParameterAnnotation(i, str, z);
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitCode() {
                            this.mv = ForInlining.IGNORE_METHOD;
                        }

                        @Override // net.bytebuddy.jar.asm.MethodVisitor
                        public void visitEnd() {
                            this.record.applyBody(this.actualMethodVisitor, RedefinitionClassVisitor.this.implementationContext, WithFullProcessing.this.annotationValueFilterFactory);
                            this.actualMethodVisitor.visitEnd();
                        }
                    }
                }
            }

            protected static class WithDecorationOnly<V> extends ForInlining<V> {
                protected WithDecorationOnly(TypeDescription typeDescription, ClassFileVersion classFileVersion, List<? extends DynamicType> list, MethodList<?> methodList, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool, ClassFileLocator classFileLocator) {
                    super(typeDescription, classFileVersion, FieldPool.Disabled.INSTANCE, RecordComponentPool.Disabled.INSTANCE, list, new LazyFieldList(typeDescription), methodList, new MethodList.Empty(), new RecordComponentList.Empty(), LoadedTypeInitializer.NoOp.INSTANCE, TypeInitializer.None.INSTANCE, typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool, typeDescription, classFileLocator);
                }

                @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ForInlining
                protected ClassVisitor writeTo(ClassVisitor classVisitor, TypeInitializer typeInitializer, ContextRegistry contextRegistry, int i, int i2) {
                    if (typeInitializer.isDefined()) {
                        throw new UnsupportedOperationException("Cannot apply a type initializer for a decoration");
                    }
                    return new DecorationClassVisitor(classVisitor, contextRegistry, i, i2);
                }

                protected static class LazyFieldList extends FieldList.AbstractBase<FieldDescription.InDefinedShape> {
                    private final TypeDescription instrumentedType;

                    protected LazyFieldList(TypeDescription typeDescription) {
                        this.instrumentedType = typeDescription;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public FieldDescription.InDefinedShape get(int i) {
                        return (FieldDescription.InDefinedShape) this.instrumentedType.getDeclaredFields().get(i);
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.instrumentedType.getDeclaredFields().size();
                    }
                }

                protected class DecorationClassVisitor extends MetadataAwareClassVisitor implements TypeInitializer.Drain {
                    private final ContextRegistry contextRegistry;

                    @UnknownNull
                    private Implementation.Context.ExtractableView implementationContext;
                    private final int readerFlags;
                    private final int writerFlags;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeInitializer.Drain
                    public void apply(ClassVisitor classVisitor, TypeInitializer typeInitializer, Implementation.Context context) {
                    }

                    protected DecorationClassVisitor(ClassVisitor classVisitor, ContextRegistry contextRegistry, int i, int i2) {
                        super(OpenedClassReader.ASM_API, classVisitor);
                        this.contextRegistry = contextRegistry;
                        this.writerFlags = i;
                        this.readerFlags = i2;
                    }

                    @Override // net.bytebuddy.jar.asm.ClassVisitor
                    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
                        Implementation.Context.FrameGeneration frameGeneration;
                        ClassFileVersion classFileVersionOfMinorMajor = ClassFileVersion.ofMinorMajor(i);
                        Implementation.Context.Factory factory = WithDecorationOnly.this.implementationContextFactory;
                        TypeDescription typeDescription = WithDecorationOnly.this.instrumentedType;
                        AuxiliaryType.NamingStrategy namingStrategy = WithDecorationOnly.this.auxiliaryTypeNamingStrategy;
                        TypeInitializer typeInitializer = WithDecorationOnly.this.typeInitializer;
                        ClassFileVersion classFileVersion = WithDecorationOnly.this.classFileVersion;
                        if ((this.writerFlags & 2) == 0 && classFileVersionOfMinorMajor.isAtLeast(ClassFileVersion.JAVA_V6)) {
                            frameGeneration = (this.readerFlags & 8) == 0 ? Implementation.Context.FrameGeneration.GENERATE : Implementation.Context.FrameGeneration.EXPAND;
                        } else {
                            frameGeneration = Implementation.Context.FrameGeneration.DISABLED;
                        }
                        Implementation.Context.ExtractableView extractableViewMake = factory.make(typeDescription, namingStrategy, typeInitializer, classFileVersionOfMinorMajor, classFileVersion, frameGeneration);
                        this.implementationContext = extractableViewMake;
                        this.contextRegistry.setImplementationContext(extractableViewMake);
                        this.cv = WithDecorationOnly.this.asmVisitorWrapper.wrap(WithDecorationOnly.this.instrumentedType, this.cv, this.implementationContext, WithDecorationOnly.this.typePool, WithDecorationOnly.this.fields, WithDecorationOnly.this.methods, this.writerFlags, this.readerFlags);
                        this.cv.visit(i, i2, str, str2, str3, strArr);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected AnnotationVisitor onVisitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
                        if (!WithDecorationOnly.this.annotationRetention.isEnabled()) {
                            return ForInlining.IGNORE_ANNOTATION;
                        }
                        return this.cv.visitTypeAnnotation(i, typePath, str, z);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    @MaybeNull
                    protected AnnotationVisitor onVisitAnnotation(String str, boolean z) {
                        if (!WithDecorationOnly.this.annotationRetention.isEnabled()) {
                            return ForInlining.IGNORE_ANNOTATION;
                        }
                        return this.cv.visitAnnotation(str, z);
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onAfterAttributes() {
                        WithDecorationOnly.this.typeAttributeAppender.apply(this.cv, WithDecorationOnly.this.instrumentedType, WithDecorationOnly.this.annotationValueFilterFactory.on(WithDecorationOnly.this.instrumentedType));
                    }

                    @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                    protected void onVisitEnd() {
                        this.implementationContext.drain(this, this.cv, WithDecorationOnly.this.annotationValueFilterFactory);
                        this.cv.visitEnd();
                    }
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForCreation<U> extends Default<U> {
            private final MethodPool methodPool;

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.methodPool.equals(((ForCreation) obj).methodPool);
            }

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default
            public int hashCode() {
                return (super.hashCode() * 31) + this.methodPool.hashCode();
            }

            protected ForCreation(TypeDescription typeDescription, ClassFileVersion classFileVersion, FieldPool fieldPool, MethodPool methodPool, RecordComponentPool recordComponentPool, List<? extends DynamicType> list, FieldList<FieldDescription.InDefinedShape> fieldList, MethodList<?> methodList, MethodList<?> methodList2, RecordComponentList<RecordComponentDescription.InDefinedShape> recordComponentList, LoadedTypeInitializer loadedTypeInitializer, TypeInitializer typeInitializer, TypeAttributeAppender typeAttributeAppender, AsmVisitorWrapper asmVisitorWrapper, AnnotationValueFilter.Factory factory, AnnotationRetention annotationRetention, AuxiliaryType.NamingStrategy namingStrategy, Implementation.Context.Factory factory2, TypeValidation typeValidation, ClassWriterStrategy classWriterStrategy, TypePool typePool) {
                super(typeDescription, classFileVersion, fieldPool, recordComponentPool, list, fieldList, methodList, methodList2, recordComponentList, loadedTypeInitializer, typeInitializer, typeAttributeAppender, asmVisitorWrapper, factory, annotationRetention, namingStrategy, factory2, typeValidation, classWriterStrategy, typePool);
                this.methodPool = methodPool;
            }

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter
            public ContextClassVisitor wrap(ClassVisitor classVisitor, int i, int i2) {
                Implementation.Context.FrameGeneration frameGeneration;
                Implementation.Context.Factory factory = this.implementationContextFactory;
                TypeDescription typeDescription = this.instrumentedType;
                AuxiliaryType.NamingStrategy namingStrategy = this.auxiliaryTypeNamingStrategy;
                TypeInitializer typeInitializer = this.typeInitializer;
                ClassFileVersion classFileVersion = this.classFileVersion;
                ClassFileVersion classFileVersion2 = this.classFileVersion;
                if ((i & 2) == 0 && this.classFileVersion.isAtLeast(ClassFileVersion.JAVA_V6)) {
                    frameGeneration = (i2 & 8) == 0 ? Implementation.Context.FrameGeneration.GENERATE : Implementation.Context.FrameGeneration.EXPAND;
                } else {
                    frameGeneration = Implementation.Context.FrameGeneration.DISABLED;
                }
                Implementation.Context.ExtractableView extractableViewMake = factory.make(typeDescription, namingStrategy, typeInitializer, classFileVersion, classFileVersion2, frameGeneration);
                return new ImplementationContextClassVisitor(new CreationClassVisitor(this.asmVisitorWrapper.wrap(this.instrumentedType, ValidatingClassVisitor.of(classVisitor, this.typeValidation), extractableViewMake, this.typePool, this.fields, this.methods, this.asmVisitorWrapper.mergeWriter(i), this.asmVisitorWrapper.mergeReader(i2)), extractableViewMake), extractableViewMake);
            }

            @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default
            protected Default<U>.UnresolvedType create(TypeInitializer typeInitializer, ClassDumpAction.Dispatcher dispatcher) {
                TypeDescription typeDescriptionAsErasure;
                String internalName;
                String simpleName;
                int iMergeWriter = this.asmVisitorWrapper.mergeWriter(0);
                int iMergeReader = this.asmVisitorWrapper.mergeReader(0);
                ClassWriter classWriterResolve = this.classWriterStrategy.resolve(iMergeWriter, this.typePool);
                Implementation.Context.ExtractableView extractableViewMake = this.implementationContextFactory.make(this.instrumentedType, this.auxiliaryTypeNamingStrategy, typeInitializer, this.classFileVersion, this.classFileVersion, ((iMergeWriter & 2) == 0 && this.classFileVersion.isAtLeast(ClassFileVersion.JAVA_V6)) ? (iMergeReader & 8) == 0 ? Implementation.Context.FrameGeneration.GENERATE : Implementation.Context.FrameGeneration.EXPAND : Implementation.Context.FrameGeneration.DISABLED);
                ClassVisitor classVisitorWrap = this.asmVisitorWrapper.wrap(this.instrumentedType, ValidatingClassVisitor.of(classWriterResolve, this.typeValidation), extractableViewMake, this.typePool, this.fields, this.methods, iMergeWriter, iMergeReader);
                int minorMajorVersion = this.classFileVersion.getMinorMajorVersion();
                int actualModifiers = this.instrumentedType.getActualModifiers(!this.instrumentedType.isInterface());
                String internalName2 = this.instrumentedType.getInternalName();
                String genericSignature = this.instrumentedType.getGenericSignature();
                if (this.instrumentedType.getSuperClass() == null) {
                    typeDescriptionAsErasure = TypeDescription.ForLoadedType.of(Object.class);
                } else {
                    typeDescriptionAsErasure = this.instrumentedType.getSuperClass().asErasure();
                }
                classVisitorWrap.visit(minorMajorVersion, actualModifiers, internalName2, genericSignature, typeDescriptionAsErasure.getInternalName(), this.instrumentedType.getInterfaces().asErasures().toInternalNames());
                if (!this.instrumentedType.isNestHost()) {
                    classVisitorWrap.visitNestHost(this.instrumentedType.getNestHost().getInternalName());
                }
                MethodDescription.InDefinedShape enclosingMethod = this.instrumentedType.getEnclosingMethod();
                if (enclosingMethod != null) {
                    classVisitorWrap.visitOuterClass(enclosingMethod.getDeclaringType().getInternalName(), enclosingMethod.getInternalName(), enclosingMethod.getDescriptor());
                } else if (this.instrumentedType.isLocalType() || this.instrumentedType.isAnonymousType()) {
                    classVisitorWrap.visitOuterClass(this.instrumentedType.getEnclosingType().getInternalName(), Default.NO_REFERENCE, Default.NO_REFERENCE);
                }
                this.typeAttributeAppender.apply(classVisitorWrap, this.instrumentedType, this.annotationValueFilterFactory.on(this.instrumentedType));
                if (this.instrumentedType.isNestHost()) {
                    Iterator it = this.instrumentedType.getNestMembers().filter(ElementMatchers.not(ElementMatchers.is(this.instrumentedType))).iterator();
                    while (it.hasNext()) {
                        classVisitorWrap.visitNestMember(((TypeDescription) it.next()).getInternalName());
                    }
                }
                Iterator it2 = this.instrumentedType.getPermittedSubtypes().iterator();
                while (it2.hasNext()) {
                    classVisitorWrap.visitPermittedSubclass(((TypeDescription) it2.next()).getInternalName());
                }
                TypeDescription declaringType = this.instrumentedType.getDeclaringType();
                if (declaringType != null) {
                    classVisitorWrap.visitInnerClass(this.instrumentedType.getInternalName(), declaringType.getInternalName(), this.instrumentedType.getSimpleName(), this.instrumentedType.getModifiers());
                } else if (this.instrumentedType.isLocalType()) {
                    classVisitorWrap.visitInnerClass(this.instrumentedType.getInternalName(), Default.NO_REFERENCE, this.instrumentedType.getSimpleName(), this.instrumentedType.getModifiers());
                } else if (this.instrumentedType.isAnonymousType()) {
                    classVisitorWrap.visitInnerClass(this.instrumentedType.getInternalName(), Default.NO_REFERENCE, Default.NO_REFERENCE, this.instrumentedType.getModifiers());
                }
                for (TypeDescription typeDescription : this.instrumentedType.getDeclaredTypes()) {
                    String internalName3 = typeDescription.getInternalName();
                    if (!typeDescription.isMemberType()) {
                        internalName = Default.NO_REFERENCE;
                    } else {
                        internalName = this.instrumentedType.getInternalName();
                    }
                    if (typeDescription.isAnonymousType()) {
                        simpleName = Default.NO_REFERENCE;
                    } else {
                        simpleName = typeDescription.getSimpleName();
                    }
                    classVisitorWrap.visitInnerClass(internalName3, internalName, simpleName, typeDescription.getModifiers());
                }
                Iterator it3 = this.recordComponents.iterator();
                while (it3.hasNext()) {
                    this.recordComponentPool.target((RecordComponentDescription) it3.next()).apply(classVisitorWrap, this.annotationValueFilterFactory);
                }
                Iterator it4 = this.fields.iterator();
                while (it4.hasNext()) {
                    this.fieldPool.target((FieldDescription) it4.next()).apply(classVisitorWrap, this.annotationValueFilterFactory);
                }
                Iterator it5 = this.instrumentedMethods.iterator();
                while (it5.hasNext()) {
                    this.methodPool.target((MethodDescription) it5.next()).apply(classVisitorWrap, extractableViewMake, this.annotationValueFilterFactory);
                }
                extractableViewMake.drain(new TypeInitializer.Drain.Default(this.instrumentedType, this.methodPool, this.annotationValueFilterFactory), classVisitorWrap, this.annotationValueFilterFactory);
                classVisitorWrap.visitEnd();
                return new UnresolvedType(classWriterResolve.toByteArray(), extractableViewMake.getAuxiliaryTypes());
            }

            protected class CreationClassVisitor extends MetadataAwareClassVisitor {
                private final Set<String> declaredTypes;
                private final Implementation.Context.ExtractableView implementationContext;
                private final Set<SignatureKey> visitedFields;
                private final Set<SignatureKey> visitedMethods;

                protected CreationClassVisitor(ClassVisitor classVisitor, Implementation.Context.ExtractableView extractableView) {
                    super(OpenedClassReader.ASM_API, classVisitor);
                    this.declaredTypes = new HashSet();
                    this.visitedFields = new HashSet();
                    this.visitedMethods = new HashSet();
                    this.implementationContext = extractableView;
                }

                @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                protected void onAfterAttributes() {
                    ForCreation.this.typeAttributeAppender.apply(this.cv, ForCreation.this.instrumentedType, ForCreation.this.annotationValueFilterFactory.on(ForCreation.this.instrumentedType));
                }

                @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                protected void onVisitInnerClass(String str, @MaybeNull String str2, @MaybeNull String str3, int i) {
                    this.declaredTypes.add(str);
                    super.onVisitInnerClass(str, str2, str3, i);
                }

                @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                protected FieldVisitor onVisitField(int i, String str, String str2, @MaybeNull String str3, @MaybeNull Object obj) {
                    this.visitedFields.add(new SignatureKey(str, str2));
                    return super.onVisitField(i, str, str2, str3, obj);
                }

                @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                protected MethodVisitor onVisitMethod(int i, String str, String str2, @MaybeNull String str3, @MaybeNull String[] strArr) {
                    this.visitedMethods.add(new SignatureKey(str, str2));
                    return super.onVisitMethod(i, str, str2, str3, strArr);
                }

                @Override // net.bytebuddy.utility.visitor.MetadataAwareClassVisitor
                protected void onVisitEnd() {
                    String internalName;
                    String simpleName;
                    for (TypeDescription typeDescription : ForCreation.this.instrumentedType.getDeclaredTypes()) {
                        if (!this.declaredTypes.contains(typeDescription.getInternalName())) {
                            ClassVisitor classVisitor = this.cv;
                            String internalName2 = typeDescription.getInternalName();
                            if (!typeDescription.isMemberType()) {
                                internalName = Default.NO_REFERENCE;
                            } else {
                                internalName = ForCreation.this.instrumentedType.getInternalName();
                            }
                            if (typeDescription.isAnonymousType()) {
                                simpleName = Default.NO_REFERENCE;
                            } else {
                                simpleName = typeDescription.getSimpleName();
                            }
                            classVisitor.visitInnerClass(internalName2, internalName, simpleName, typeDescription.getModifiers());
                        }
                    }
                    for (FieldDescription fieldDescription : ForCreation.this.fields) {
                        if (!this.visitedFields.contains(new SignatureKey(fieldDescription.getName(), fieldDescription.getDescriptor()))) {
                            ForCreation.this.fieldPool.target(fieldDescription).apply(this.cv, ForCreation.this.annotationValueFilterFactory);
                        }
                    }
                    Iterator it = ForCreation.this.instrumentedMethods.iterator();
                    while (it.hasNext()) {
                        MethodDescription methodDescription = (MethodDescription) it.next();
                        if (!this.visitedMethods.contains(new SignatureKey(methodDescription.getInternalName(), methodDescription.getDescriptor()))) {
                            ForCreation.this.methodPool.target(methodDescription).apply(this.cv, this.implementationContext, ForCreation.this.annotationValueFilterFactory);
                        }
                    }
                    this.implementationContext.drain(new TypeInitializer.Drain.Default(ForCreation.this.instrumentedType, ForCreation.this.methodPool, ForCreation.this.annotationValueFilterFactory), this.cv, ForCreation.this.annotationValueFilterFactory);
                    super.onVisitEnd();
                }
            }

            protected class ImplementationContextClassVisitor extends ContextClassVisitor {
                private final Implementation.Context.ExtractableView implementationContext;

                protected ImplementationContextClassVisitor(ClassVisitor classVisitor, Implementation.Context.ExtractableView extractableView) {
                    super(classVisitor);
                    this.implementationContext = extractableView;
                }

                @Override // net.bytebuddy.utility.visitor.ContextClassVisitor
                public List<DynamicType> getAuxiliaryTypes() {
                    return CompoundList.of((List) ForCreation.this.auxiliaryTypes, (List) this.implementationContext.getAuxiliaryTypes());
                }

                @Override // net.bytebuddy.utility.visitor.ContextClassVisitor
                public LoadedTypeInitializer getLoadedTypeInitializer() {
                    return ForCreation.this.loadedTypeInitializer;
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class ClassDumpAction implements PrivilegedExceptionAction<Void> {

            @AlwaysNull
            private static final Void NOTHING = null;
            private final byte[] binaryRepresentation;
            private final TypeDescription instrumentedType;
            private final boolean original;
            private final long suffix;
            private final String target;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ClassDumpAction classDumpAction = (ClassDumpAction) obj;
                return this.original == classDumpAction.original && this.suffix == classDumpAction.suffix && this.target.equals(classDumpAction.target) && this.instrumentedType.equals(classDumpAction.instrumentedType) && Arrays.equals(this.binaryRepresentation, classDumpAction.binaryRepresentation);
            }

            public int hashCode() {
                int iHashCode = ((((((getClass().hashCode() * 31) + this.target.hashCode()) * 31) + this.instrumentedType.hashCode()) * 31) + (this.original ? 1 : 0)) * 31;
                long j = this.suffix;
                return ((iHashCode + ((int) (j ^ (j >>> 32)))) * 31) + Arrays.hashCode(this.binaryRepresentation);
            }

            protected ClassDumpAction(String str, TypeDescription typeDescription, boolean z, long j, byte[] bArr) {
                this.target = str;
                this.instrumentedType = typeDescription;
                this.original = z;
                this.suffix = j;
                this.binaryRepresentation = bArr;
            }

            @Override // java.security.PrivilegedExceptionAction
            public Void run() throws Exception {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(this.target, this.instrumentedType.getName() + (this.original ? "-original." : ".") + this.suffix + ".class"));
                try {
                    fileOutputStream.write(this.binaryRepresentation);
                    return NOTHING;
                } finally {
                    fileOutputStream.close();
                }
            }

            protected interface Dispatcher {

                public enum Disabled implements Dispatcher {
                    INSTANCE;

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ClassDumpAction.Dispatcher
                    public void dump(TypeDescription typeDescription, boolean z, byte[] bArr) {
                    }
                }

                void dump(TypeDescription typeDescription, boolean z, byte[] bArr);

                @HashCodeAndEqualsPlugin.Enhance
                public static class Enabled implements Dispatcher {
                    private final String folder;
                    private final long timestamp;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        Enabled enabled = (Enabled) obj;
                        return this.timestamp == enabled.timestamp && this.folder.equals(enabled.folder);
                    }

                    public int hashCode() {
                        int iHashCode = ((getClass().hashCode() * 31) + this.folder.hashCode()) * 31;
                        long j = this.timestamp;
                        return iHashCode + ((int) (j ^ (j >>> 32)));
                    }

                    protected Enabled(String str, long j) {
                        this.folder = str;
                        this.timestamp = j;
                    }

                    @Override // net.bytebuddy.dynamic.scaffold.TypeWriter.Default.ClassDumpAction.Dispatcher
                    public void dump(TypeDescription typeDescription, boolean z, byte[] bArr) {
                        try {
                            Default.doPrivileged(new ClassDumpAction(this.folder, typeDescription, z, this.timestamp, bArr));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
