package net.bytebuddy.description.type;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.ModifierReviewable;
import net.bytebuddy.description.TypeVariableSource;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationSource;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.jar.asm.signature.SignatureVisitor;
import net.bytebuddy.jar.asm.signature.SignatureWriter;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.FieldComparator;
import net.bytebuddy.utility.GraalImageCode;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes12.dex */
public interface TypeDescription extends TypeDefinition, ByteCodeElement, TypeVariableSource {

    @Deprecated
    public static final TypeDescription OBJECT = LazyProxy.of(Object.class);

    @Deprecated
    public static final TypeDescription STRING = LazyProxy.of(String.class);

    @Deprecated
    public static final TypeDescription CLASS = LazyProxy.of(Class.class);

    @Deprecated
    public static final TypeDescription THROWABLE = LazyProxy.of(Throwable.class);

    @Deprecated
    public static final TypeDescription VOID = LazyProxy.of(Void.TYPE);
    public static final TypeList.Generic ARRAY_INTERFACES = new TypeList.Generic.ForLoadedTypes(Cloneable.class, Serializable.class);

    @AlwaysNull
    public static final TypeDescription UNDEFINED = null;

    TypeDescription asBoxed();

    TypeDescription asUnboxed();

    int getActualModifiers(boolean z);

    @MaybeNull
    String getCanonicalName();

    @MaybeNull
    ClassFileVersion getClassFileVersion();

    @Override // net.bytebuddy.description.type.TypeDefinition
    @MaybeNull
    TypeDescription getComponentType();

    @Override // net.bytebuddy.description.type.TypeDefinition
    FieldList<FieldDescription.InDefinedShape> getDeclaredFields();

    @Override // net.bytebuddy.description.type.TypeDefinition
    MethodList<MethodDescription.InDefinedShape> getDeclaredMethods();

    TypeList getDeclaredTypes();

    @Override // net.bytebuddy.description.DeclaredByType
    @MaybeNull
    TypeDescription getDeclaringType();

    @MaybeNull
    Object getDefaultValue();

    @MaybeNull
    MethodDescription.InDefinedShape getEnclosingMethod();

    @MaybeNull
    TypeDescription getEnclosingType();

    AnnotationList getInheritedAnnotations();

    int getInnerClassCount();

    String getLongSimpleName();

    TypeDescription getNestHost();

    TypeList getNestMembers();

    @MaybeNull
    PackageDescription getPackage();

    TypeList getPermittedSubtypes();

    @Override // net.bytebuddy.description.type.TypeDefinition
    RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents();

    String getSimpleName();

    boolean isAnnotationReturnType();

    boolean isAnnotationValue();

    boolean isAnnotationValue(Object obj);

    boolean isAnonymousType();

    boolean isAssignableFrom(Class<?> cls);

    boolean isAssignableFrom(TypeDescription typeDescription);

    boolean isAssignableTo(Class<?> cls);

    boolean isAssignableTo(TypeDescription typeDescription);

    boolean isCompileTimeConstant();

    boolean isInHierarchyWith(Class<?> cls);

    boolean isInHierarchyWith(TypeDescription typeDescription);

    boolean isInnerClass();

    boolean isInstance(Object obj);

    boolean isLocalType();

    boolean isMemberType();

    boolean isNestHost();

    boolean isNestMateOf(Class<?> cls);

    boolean isNestMateOf(TypeDescription typeDescription);

    boolean isNestedClass();

    boolean isPackageType();

    boolean isPrimitiveWrapper();

    boolean isSamePackage(TypeDescription typeDescription);

    boolean isSealed();

    public interface Generic extends TypeDefinition, AnnotationSource {

        @Deprecated
        public static final Generic OBJECT = LazyProxy.of(Object.class);

        @Deprecated
        public static final Generic CLASS = LazyProxy.of(Class.class);

        @Deprecated
        public static final Generic VOID = LazyProxy.of(Void.TYPE);

        @Deprecated
        public static final Generic ANNOTATION = LazyProxy.of(Annotation.class);

        @AlwaysNull
        public static final Generic UNDEFINED = null;

        <T> T accept(Visitor<T> visitor);

        Generic asRawType();

        @MaybeNull
        Generic findBindingOf(Generic generic);

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        Generic getComponentType();

        @Override // net.bytebuddy.description.type.TypeDefinition
        FieldList<FieldDescription.InGenericShape> getDeclaredFields();

        @Override // net.bytebuddy.description.type.TypeDefinition
        MethodList<MethodDescription.InGenericShape> getDeclaredMethods();

        TypeList.Generic getLowerBounds();

        @MaybeNull
        Generic getOwnerType();

        @Override // net.bytebuddy.description.type.TypeDefinition
        RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents();

        String getSymbol();

        TypeList.Generic getTypeArguments();

        TypeVariableSource getTypeVariableSource();

        TypeList.Generic getUpperBounds();

        public interface Visitor<T> {

            public enum NoOp implements Visitor<Generic> {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onGenericArray(Generic generic) {
                    return generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onNonGenericType(Generic generic) {
                    return generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onParameterizedType(Generic generic) {
                    return generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onTypeVariable(Generic generic) {
                    return generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onWildcard(Generic generic) {
                    return generic;
                }
            }

            T onGenericArray(Generic generic);

            T onNonGenericType(Generic generic);

            T onParameterizedType(Generic generic);

            T onTypeVariable(Generic generic);

            T onWildcard(Generic generic);

            public enum TypeErasing implements Visitor<Generic> {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onGenericArray(Generic generic) {
                    return generic.asRawType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onWildcard(Generic generic) {
                    throw new IllegalArgumentException("Cannot erase a wildcard type: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onParameterizedType(Generic generic) {
                    return generic.asRawType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onTypeVariable(Generic generic) {
                    return generic.asRawType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onNonGenericType(Generic generic) {
                    return generic.asRawType();
                }
            }

            public enum AnnotationStripper implements Visitor<Generic> {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onGenericArray(Generic generic) {
                    return new OfGenericArray.Latent((Generic) generic.getComponentType().accept(this), AnnotationSource.Empty.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onWildcard(Generic generic) {
                    return new OfWildcardType.Latent(generic.getUpperBounds().accept(this), generic.getLowerBounds().accept(this), AnnotationSource.Empty.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onParameterizedType(Generic generic) {
                    Generic ownerType = generic.getOwnerType();
                    return new OfParameterizedType.Latent(generic.asErasure(), ownerType == null ? Generic.UNDEFINED : (Generic) ownerType.accept(this), generic.getTypeArguments().accept(this), AnnotationSource.Empty.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onTypeVariable(Generic generic) {
                    return new NonAnnotatedTypeVariable(generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onNonGenericType(Generic generic) {
                    if (generic.isArray()) {
                        return new OfGenericArray.Latent(onNonGenericType(generic.getComponentType()), AnnotationSource.Empty.INSTANCE);
                    }
                    return new OfNonGenericType.Latent(generic.asErasure(), AnnotationSource.Empty.INSTANCE);
                }

                protected static class NonAnnotatedTypeVariable extends OfTypeVariable {
                    private final Generic typeVariable;

                    protected NonAnnotatedTypeVariable(Generic generic) {
                        this.typeVariable = generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic
                    public TypeList.Generic getUpperBounds() {
                        return this.typeVariable.getUpperBounds();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic
                    public TypeVariableSource getTypeVariableSource() {
                        return this.typeVariable.getTypeVariableSource();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic
                    public String getSymbol() {
                        return this.typeVariable.getSymbol();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return new AnnotationList.Empty();
                    }
                }
            }

            public enum Assigner implements Visitor<Dispatcher> {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Dispatcher onGenericArray(Generic generic) {
                    return new Dispatcher.ForGenericArray(generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Dispatcher onWildcard(Generic generic) {
                    throw new IllegalArgumentException("A wildcard is not a first level type: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Dispatcher onParameterizedType(Generic generic) {
                    return new Dispatcher.ForParameterizedType(generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Dispatcher onTypeVariable(Generic generic) {
                    return new Dispatcher.ForTypeVariable(generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Dispatcher onNonGenericType(Generic generic) {
                    return new Dispatcher.ForNonGenericType(generic.asErasure());
                }

                public interface Dispatcher {
                    boolean isAssignableFrom(Generic generic);

                    public static abstract class AbstractBase implements Dispatcher, Visitor<Boolean> {
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Assigner.Dispatcher
                        public boolean isAssignableFrom(Generic generic) {
                            return ((Boolean) generic.accept(this)).booleanValue();
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class ForNonGenericType extends AbstractBase {
                        private final TypeDescription typeDescription;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((ForNonGenericType) obj).typeDescription);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
                        }

                        protected ForNonGenericType(TypeDescription typeDescription) {
                            this.typeDescription = typeDescription;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onGenericArray(Generic generic) {
                            boolean zBooleanValue;
                            if (this.typeDescription.isArray()) {
                                zBooleanValue = ((Boolean) generic.getComponentType().accept(new ForNonGenericType(this.typeDescription.getComponentType()))).booleanValue();
                            } else {
                                zBooleanValue = this.typeDescription.represents(Object.class) || TypeDescription.ARRAY_INTERFACES.contains(this.typeDescription.asGenericType());
                            }
                            return Boolean.valueOf(zBooleanValue);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onWildcard(Generic generic) {
                            throw new IllegalArgumentException("A wildcard is not a first-level type: " + generic);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onParameterizedType(Generic generic) {
                            if (this.typeDescription.equals(generic.asErasure())) {
                                return true;
                            }
                            Generic superClass = generic.getSuperClass();
                            if (superClass != null && isAssignableFrom(superClass)) {
                                return true;
                            }
                            Iterator it = generic.getInterfaces().iterator();
                            while (it.hasNext()) {
                                if (isAssignableFrom((Generic) it.next())) {
                                    return true;
                                }
                            }
                            return Boolean.valueOf(this.typeDescription.represents(Object.class));
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onTypeVariable(Generic generic) {
                            Iterator it = generic.getUpperBounds().iterator();
                            while (it.hasNext()) {
                                if (isAssignableFrom((Generic) it.next())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onNonGenericType(Generic generic) {
                            return Boolean.valueOf(this.typeDescription.isAssignableFrom(generic.asErasure()));
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class ForTypeVariable extends AbstractBase {
                        private final Generic typeVariable;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.typeVariable.equals(((ForTypeVariable) obj).typeVariable);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + this.typeVariable.hashCode();
                        }

                        protected ForTypeVariable(Generic generic) {
                            this.typeVariable = generic;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onGenericArray(Generic generic) {
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onWildcard(Generic generic) {
                            throw new IllegalArgumentException("A wildcard is not a first-level type: " + generic);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onParameterizedType(Generic generic) {
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onTypeVariable(Generic generic) {
                            if (generic.equals(this.typeVariable)) {
                                return true;
                            }
                            Iterator it = generic.getUpperBounds().iterator();
                            while (it.hasNext()) {
                                if (isAssignableFrom((Generic) it.next())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onNonGenericType(Generic generic) {
                            return false;
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class ForParameterizedType extends AbstractBase {
                        private final Generic parameterizedType;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.parameterizedType.equals(((ForParameterizedType) obj).parameterizedType);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + this.parameterizedType.hashCode();
                        }

                        protected ForParameterizedType(Generic generic) {
                            this.parameterizedType = generic;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onGenericArray(Generic generic) {
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onWildcard(Generic generic) {
                            throw new IllegalArgumentException("A wildcard is not a first-level type: " + generic);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onParameterizedType(Generic generic) {
                            if (this.parameterizedType.asErasure().equals(generic.asErasure())) {
                                Generic ownerType = this.parameterizedType.getOwnerType();
                                Generic ownerType2 = generic.getOwnerType();
                                if (ownerType == null || ownerType2 == null || ((Dispatcher) ownerType.accept(Assigner.INSTANCE)).isAssignableFrom(ownerType2)) {
                                    TypeList.Generic typeArguments = this.parameterizedType.getTypeArguments();
                                    TypeList.Generic typeArguments2 = generic.getTypeArguments();
                                    if (typeArguments.size() == typeArguments2.size()) {
                                        for (int i = 0; i < typeArguments.size(); i++) {
                                            if (!((Dispatcher) ((Generic) typeArguments.get(i)).accept(ParameterAssigner.INSTANCE)).isAssignableFrom((Generic) typeArguments2.get(i))) {
                                                return false;
                                            }
                                        }
                                        return true;
                                    }
                                    throw new IllegalArgumentException("Incompatible generic types: " + generic + " and " + this.parameterizedType);
                                }
                                return false;
                            }
                            Generic superClass = generic.getSuperClass();
                            if (superClass != null && isAssignableFrom(superClass)) {
                                return true;
                            }
                            Iterator it = generic.getInterfaces().iterator();
                            while (it.hasNext()) {
                                if (isAssignableFrom((Generic) it.next())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onTypeVariable(Generic generic) {
                            Iterator it = generic.getUpperBounds().iterator();
                            while (it.hasNext()) {
                                if (isAssignableFrom((Generic) it.next())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onNonGenericType(Generic generic) {
                            if (this.parameterizedType.asErasure().equals(generic.asErasure())) {
                                return true;
                            }
                            Generic superClass = generic.getSuperClass();
                            if (superClass != null && isAssignableFrom(superClass)) {
                                return true;
                            }
                            Iterator it = generic.getInterfaces().iterator();
                            while (it.hasNext()) {
                                if (isAssignableFrom((Generic) it.next())) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        protected enum ParameterAssigner implements Visitor<Dispatcher> {
                            INSTANCE;

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                            public Dispatcher onGenericArray(Generic generic) {
                                return new InvariantBinding(generic);
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                            public Dispatcher onWildcard(Generic generic) {
                                TypeList.Generic lowerBounds = generic.getLowerBounds();
                                if (lowerBounds.isEmpty()) {
                                    return new CovariantBinding(generic.getUpperBounds().getOnly());
                                }
                                return new ContravariantBinding(lowerBounds.getOnly());
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                            public Dispatcher onParameterizedType(Generic generic) {
                                return new InvariantBinding(generic);
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                            public Dispatcher onTypeVariable(Generic generic) {
                                return new InvariantBinding(generic);
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                            public Dispatcher onNonGenericType(Generic generic) {
                                return new InvariantBinding(generic);
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class InvariantBinding implements Dispatcher {
                                private final Generic typeDescription;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((InvariantBinding) obj).typeDescription);
                                }

                                public int hashCode() {
                                    return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
                                }

                                protected InvariantBinding(Generic generic) {
                                    this.typeDescription = generic;
                                }

                                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Assigner.Dispatcher
                                public boolean isAssignableFrom(Generic generic) {
                                    return generic.equals(this.typeDescription);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class CovariantBinding implements Dispatcher {
                                private final Generic upperBound;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.upperBound.equals(((CovariantBinding) obj).upperBound);
                                }

                                public int hashCode() {
                                    return (getClass().hashCode() * 31) + this.upperBound.hashCode();
                                }

                                protected CovariantBinding(Generic generic) {
                                    this.upperBound = generic;
                                }

                                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Assigner.Dispatcher
                                public boolean isAssignableFrom(Generic generic) {
                                    if (generic.getSort().isWildcard()) {
                                        return generic.getLowerBounds().isEmpty() && ((Dispatcher) this.upperBound.accept(Assigner.INSTANCE)).isAssignableFrom(generic.getUpperBounds().getOnly());
                                    }
                                    return ((Dispatcher) this.upperBound.accept(Assigner.INSTANCE)).isAssignableFrom(generic);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class ContravariantBinding implements Dispatcher {
                                private final Generic lowerBound;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.lowerBound.equals(((ContravariantBinding) obj).lowerBound);
                                }

                                public int hashCode() {
                                    return (getClass().hashCode() * 31) + this.lowerBound.hashCode();
                                }

                                protected ContravariantBinding(Generic generic) {
                                    this.lowerBound = generic;
                                }

                                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Assigner.Dispatcher
                                public boolean isAssignableFrom(Generic generic) {
                                    if (!generic.getSort().isWildcard()) {
                                        return generic.getSort().isWildcard() || ((Dispatcher) generic.accept(Assigner.INSTANCE)).isAssignableFrom(this.lowerBound);
                                    }
                                    TypeList.Generic lowerBounds = generic.getLowerBounds();
                                    return !lowerBounds.isEmpty() && ((Dispatcher) lowerBounds.getOnly().accept(Assigner.INSTANCE)).isAssignableFrom(this.lowerBound);
                                }
                            }
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class ForGenericArray extends AbstractBase {
                        private final Generic genericArray;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.genericArray.equals(((ForGenericArray) obj).genericArray);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + this.genericArray.hashCode();
                        }

                        protected ForGenericArray(Generic generic) {
                            this.genericArray = generic;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onGenericArray(Generic generic) {
                            return Boolean.valueOf(((Dispatcher) this.genericArray.getComponentType().accept(Assigner.INSTANCE)).isAssignableFrom(generic.getComponentType()));
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onWildcard(Generic generic) {
                            throw new IllegalArgumentException("A wildcard is not a first-level type: " + generic);
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onParameterizedType(Generic generic) {
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onTypeVariable(Generic generic) {
                            return false;
                        }

                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                        public Boolean onNonGenericType(Generic generic) {
                            return Boolean.valueOf(generic.isArray() && ((Dispatcher) this.genericArray.getComponentType().accept(Assigner.INSTANCE)).isAssignableFrom(generic.getComponentType()));
                        }
                    }
                }
            }

            public enum Validator implements Visitor<Boolean> {
                SUPER_CLASS(false, false, false, false) { // from class: net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator.1
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onTypeVariable(Generic generic) {
                        return super.onTypeVariable(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onNonGenericType(Generic generic) {
                        return Boolean.valueOf(super.onNonGenericType(generic).booleanValue() && !generic.isInterface());
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onParameterizedType(Generic generic) {
                        return Boolean.valueOf(!generic.isInterface());
                    }
                },
                INTERFACE(false, false, false, false) { // from class: net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator.2
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onTypeVariable(Generic generic) {
                        return super.onTypeVariable(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onNonGenericType(Generic generic) {
                        return Boolean.valueOf(super.onNonGenericType(generic).booleanValue() && generic.isInterface());
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onParameterizedType(Generic generic) {
                        return Boolean.valueOf(generic.isInterface());
                    }
                },
                TYPE_VARIABLE(false, false, true, false),
                FIELD(true, true, true, false),
                METHOD_RETURN(true, true, true, true),
                METHOD_PARAMETER(true, true, true, false),
                EXCEPTION(false, false, true, false) { // from class: net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator.3
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Boolean onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onParameterizedType(Generic generic) {
                        return false;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onTypeVariable(Generic generic) {
                        Iterator it = generic.getUpperBounds().iterator();
                        while (it.hasNext()) {
                            if (((Boolean) ((Generic) it.next()).accept(this)).booleanValue()) {
                                return true;
                            }
                        }
                        return false;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Validator, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onNonGenericType(Generic generic) {
                        return Boolean.valueOf(generic.asErasure().isAssignableTo(Throwable.class));
                    }
                },
                RECEIVER(false, false, false, false);

                private final boolean acceptsArray;
                private final boolean acceptsPrimitive;
                private final boolean acceptsVariable;
                private final boolean acceptsVoid;

                Validator(boolean z, boolean z2, boolean z3, boolean z4) {
                    this.acceptsArray = z;
                    this.acceptsPrimitive = z2;
                    this.acceptsVariable = z3;
                    this.acceptsVoid = z4;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Boolean onGenericArray(Generic generic) {
                    return Boolean.valueOf(this.acceptsArray);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Boolean onWildcard(Generic generic) {
                    return false;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Boolean onParameterizedType(Generic generic) {
                    return true;
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Boolean onTypeVariable(Generic generic) {
                    return Boolean.valueOf(this.acceptsVariable);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Boolean onNonGenericType(Generic generic) {
                    return Boolean.valueOf((this.acceptsArray || !generic.isArray()) && (this.acceptsPrimitive || !generic.isPrimitive()) && (this.acceptsVoid || !generic.represents(Void.TYPE)));
                }

                public enum ForTypeAnnotations implements Visitor<Boolean> {
                    INSTANCE;

                    private static final String TYPE_PARAMETER = "TYPE_PARAMETER";
                    private static final String TYPE_USE = "TYPE_USE";

                    public static boolean ofFormalTypeVariable(Generic generic) {
                        HashSet hashSet = new HashSet();
                        for (AnnotationDescription annotationDescription : generic.getDeclaredAnnotations()) {
                            if (!annotationDescription.isSupportedOn(TYPE_PARAMETER) || !hashSet.add(annotationDescription.getAnnotationType())) {
                                return false;
                            }
                        }
                        return true;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onGenericArray(Generic generic) {
                        return Boolean.valueOf(isValid(generic) && ((Boolean) generic.getComponentType().accept(this)).booleanValue());
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onWildcard(Generic generic) {
                        if (!isValid(generic)) {
                            return false;
                        }
                        TypeList.Generic lowerBounds = generic.getLowerBounds();
                        if (lowerBounds.isEmpty()) {
                            lowerBounds = generic.getUpperBounds();
                        }
                        return (Boolean) lowerBounds.getOnly().accept(this);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onParameterizedType(Generic generic) {
                        if (!isValid(generic)) {
                            return false;
                        }
                        Generic ownerType = generic.getOwnerType();
                        if (ownerType != null && !((Boolean) ownerType.accept(this)).booleanValue()) {
                            return false;
                        }
                        Iterator it = generic.getTypeArguments().iterator();
                        while (it.hasNext()) {
                            if (!((Boolean) ((Generic) it.next()).accept(this)).booleanValue()) {
                                return false;
                            }
                        }
                        return true;
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onTypeVariable(Generic generic) {
                        return Boolean.valueOf(isValid(generic));
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Boolean onNonGenericType(Generic generic) {
                        return Boolean.valueOf(isValid(generic) && (!generic.isArray() || ((Boolean) generic.getComponentType().accept(this)).booleanValue()));
                    }

                    private boolean isValid(Generic generic) {
                        HashSet hashSet = new HashSet();
                        for (AnnotationDescription annotationDescription : generic.getDeclaredAnnotations()) {
                            if (!annotationDescription.isSupportedOn(TYPE_USE) || !hashSet.add(annotationDescription.getAnnotationType())) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }

            public enum Reifying implements Visitor<Generic> {
                INITIATING { // from class: net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying.1
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onParameterizedType(Generic generic) {
                        return generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onNonGenericType(Generic generic) {
                        return super.onNonGenericType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onTypeVariable(Generic generic) {
                        return super.onTypeVariable(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }
                },
                INHERITING { // from class: net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying.2
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onNonGenericType(Generic generic) {
                        return super.onNonGenericType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onTypeVariable(Generic generic) {
                        return super.onTypeVariable(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Reifying, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onParameterizedType(Generic generic) {
                        return new OfParameterizedType.ForReifiedType(generic);
                    }
                };

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onGenericArray(Generic generic) {
                    throw new IllegalArgumentException("Cannot reify a generic array: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onWildcard(Generic generic) {
                    throw new IllegalArgumentException("Cannot reify a wildcard: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onTypeVariable(Generic generic) {
                    throw new IllegalArgumentException("Cannot reify a type variable: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onNonGenericType(Generic generic) {
                    TypeDescription typeDescriptionAsErasure = generic.asErasure();
                    return typeDescriptionAsErasure.isGenerified() ? new OfNonGenericType.ForReifiedErasure(typeDescriptionAsErasure) : generic;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForSignatureVisitor implements Visitor<SignatureVisitor> {
                private static final int ONLY_CHARACTER = 0;
                protected final SignatureVisitor signatureVisitor;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.signatureVisitor.equals(((ForSignatureVisitor) obj).signatureVisitor);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.signatureVisitor.hashCode();
                }

                public ForSignatureVisitor(SignatureVisitor signatureVisitor) {
                    this.signatureVisitor = signatureVisitor;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public SignatureVisitor onGenericArray(Generic generic) {
                    generic.getComponentType().accept(new ForSignatureVisitor(this.signatureVisitor.visitArrayType()));
                    return this.signatureVisitor;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public SignatureVisitor onWildcard(Generic generic) {
                    throw new IllegalStateException("Unexpected wildcard: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public SignatureVisitor onParameterizedType(Generic generic) {
                    onOwnableType(generic);
                    this.signatureVisitor.visitEnd();
                    return this.signatureVisitor;
                }

                private void onOwnableType(Generic generic) {
                    Generic ownerType = generic.getOwnerType();
                    if (ownerType != null && ownerType.getSort().isParameterized()) {
                        onOwnableType(ownerType);
                        this.signatureVisitor.visitInnerClassType(generic.asErasure().getSimpleName());
                    } else {
                        this.signatureVisitor.visitClassType(generic.asErasure().getInternalName());
                    }
                    Iterator it = generic.getTypeArguments().iterator();
                    while (it.hasNext()) {
                        ((Generic) it.next()).accept(new OfTypeArgument(this.signatureVisitor));
                    }
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public SignatureVisitor onTypeVariable(Generic generic) {
                    this.signatureVisitor.visitTypeVariable(generic.getSymbol());
                    return this.signatureVisitor;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public SignatureVisitor onNonGenericType(Generic generic) {
                    if (generic.isArray()) {
                        generic.getComponentType().accept(new ForSignatureVisitor(this.signatureVisitor.visitArrayType()));
                    } else if (generic.isPrimitive()) {
                        this.signatureVisitor.visitBaseType(generic.asErasure().getDescriptor().charAt(0));
                    } else {
                        this.signatureVisitor.visitClassType(generic.asErasure().getInternalName());
                        this.signatureVisitor.visitEnd();
                    }
                    return this.signatureVisitor;
                }

                protected static class OfTypeArgument extends ForSignatureVisitor {
                    protected OfTypeArgument(SignatureVisitor signatureVisitor) {
                        super(signatureVisitor);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.ForSignatureVisitor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public SignatureVisitor onWildcard(Generic generic) {
                        TypeList.Generic upperBounds = generic.getUpperBounds();
                        TypeList.Generic lowerBounds = generic.getLowerBounds();
                        if (lowerBounds.isEmpty() && upperBounds.getOnly().represents(Object.class)) {
                            this.signatureVisitor.visitTypeArgument();
                        } else if (!lowerBounds.isEmpty()) {
                            lowerBounds.getOnly().accept(new ForSignatureVisitor(this.signatureVisitor.visitTypeArgument('-')));
                        } else {
                            upperBounds.getOnly().accept(new ForSignatureVisitor(this.signatureVisitor.visitTypeArgument('+')));
                        }
                        return this.signatureVisitor;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.ForSignatureVisitor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public SignatureVisitor onGenericArray(Generic generic) {
                        generic.accept(new ForSignatureVisitor(this.signatureVisitor.visitTypeArgument('=')));
                        return this.signatureVisitor;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.ForSignatureVisitor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public SignatureVisitor onParameterizedType(Generic generic) {
                        generic.accept(new ForSignatureVisitor(this.signatureVisitor.visitTypeArgument('=')));
                        return this.signatureVisitor;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.ForSignatureVisitor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public SignatureVisitor onTypeVariable(Generic generic) {
                        generic.accept(new ForSignatureVisitor(this.signatureVisitor.visitTypeArgument('=')));
                        return this.signatureVisitor;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.ForSignatureVisitor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public SignatureVisitor onNonGenericType(Generic generic) {
                        generic.accept(new ForSignatureVisitor(this.signatureVisitor.visitTypeArgument('=')));
                        return this.signatureVisitor;
                    }
                }
            }

            public static abstract class Substitutor implements Visitor<Generic> {
                protected abstract Generic onSimpleType(Generic generic);

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onParameterizedType(Generic generic) {
                    Generic ownerType = generic.getOwnerType();
                    ArrayList arrayList = new ArrayList(generic.getTypeArguments().size());
                    Iterator it = generic.getTypeArguments().iterator();
                    while (it.hasNext()) {
                        arrayList.add(((Generic) it.next()).accept(this));
                    }
                    return new OfParameterizedType.Latent(((Generic) generic.asRawType().accept(this)).asErasure(), ownerType == null ? Generic.UNDEFINED : (Generic) ownerType.accept(this), arrayList, generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onGenericArray(Generic generic) {
                    return new OfGenericArray.Latent((Generic) generic.getComponentType().accept(this), generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onWildcard(Generic generic) {
                    return new OfWildcardType.Latent(generic.getUpperBounds().accept(this), generic.getLowerBounds().accept(this), generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onNonGenericType(Generic generic) {
                    if (generic.isArray()) {
                        return new OfGenericArray.Latent((Generic) generic.getComponentType().accept(this), generic);
                    }
                    return onSimpleType(generic);
                }

                public static abstract class WithoutTypeSubstitution extends Substitutor {
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onNonGenericType(Generic generic) {
                        return generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor
                    protected Generic onSimpleType(Generic generic) {
                        return generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onParameterizedType(Generic generic) {
                        return super.onParameterizedType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForAttachment extends Substitutor {
                    private final TypeDescription declaringType;
                    private final TypeVariableSource typeVariableSource;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForAttachment forAttachment = (ForAttachment) obj;
                        return this.declaringType.equals(forAttachment.declaringType) && this.typeVariableSource.equals(forAttachment.typeVariableSource);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.declaringType.hashCode()) * 31) + this.typeVariableSource.hashCode();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onNonGenericType(Generic generic) {
                        return super.onNonGenericType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onParameterizedType(Generic generic) {
                        return super.onParameterizedType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    protected ForAttachment(TypeDefinition typeDefinition, TypeVariableSource typeVariableSource) {
                        this(typeDefinition.asErasure(), typeVariableSource);
                    }

                    protected ForAttachment(TypeDescription typeDescription, TypeVariableSource typeVariableSource) {
                        this.declaringType = typeDescription;
                        this.typeVariableSource = typeVariableSource;
                    }

                    public static ForAttachment of(TypeDescription typeDescription) {
                        return new ForAttachment(typeDescription, (TypeVariableSource) typeDescription);
                    }

                    public static ForAttachment of(FieldDescription fieldDescription) {
                        return new ForAttachment(fieldDescription.getDeclaringType(), fieldDescription.getDeclaringType().asErasure());
                    }

                    public static ForAttachment of(MethodDescription methodDescription) {
                        return new ForAttachment(methodDescription.getDeclaringType(), methodDescription);
                    }

                    public static ForAttachment of(ParameterDescription parameterDescription) {
                        return new ForAttachment(parameterDescription.getDeclaringMethod().getDeclaringType(), parameterDescription.getDeclaringMethod());
                    }

                    public static ForAttachment of(RecordComponentDescription recordComponentDescription) {
                        return new ForAttachment(recordComponentDescription.getDeclaringType(), recordComponentDescription.getDeclaringType().asErasure());
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onTypeVariable(Generic generic) {
                        return new OfTypeVariable.WithAnnotationOverlay(this.typeVariableSource.findExpectedVariable(generic.getSymbol()), generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor
                    protected Generic onSimpleType(Generic generic) {
                        return generic.represents(TargetType.class) ? new OfNonGenericType.Latent(this.declaringType, generic) : generic;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForDetachment extends Substitutor {
                    private final ElementMatcher<? super TypeDescription> typeMatcher;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.typeMatcher.equals(((ForDetachment) obj).typeMatcher);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.typeMatcher.hashCode();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onNonGenericType(Generic generic) {
                        return super.onNonGenericType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onParameterizedType(Generic generic) {
                        return super.onParameterizedType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    public ForDetachment(ElementMatcher<? super TypeDescription> elementMatcher) {
                        this.typeMatcher = elementMatcher;
                    }

                    public static Visitor<Generic> of(TypeDefinition typeDefinition) {
                        return new ForDetachment(ElementMatchers.is(typeDefinition));
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onTypeVariable(Generic generic) {
                        return new OfTypeVariable.Symbolic(generic.getSymbol(), generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor
                    protected Generic onSimpleType(Generic generic) {
                        return this.typeMatcher.matches(generic.asErasure()) ? new OfNonGenericType.Latent(TargetType.DESCRIPTION, generic.getOwnerType(), generic) : generic;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForTypeVariableBinding extends WithoutTypeSubstitution {
                    private final Generic parameterizedType;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.parameterizedType.equals(((ForTypeVariableBinding) obj).parameterizedType);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.parameterizedType.hashCode();
                    }

                    protected ForTypeVariableBinding(Generic generic) {
                        this.parameterizedType = generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onTypeVariable(Generic generic) {
                        return (Generic) generic.getTypeVariableSource().accept(new TypeVariableSubstitutor(generic));
                    }

                    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                    protected class TypeVariableSubstitutor implements TypeVariableSource.Visitor<Generic> {
                        private final Generic typeVariable;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            TypeVariableSubstitutor typeVariableSubstitutor = (TypeVariableSubstitutor) obj;
                            return this.typeVariable.equals(typeVariableSubstitutor.typeVariable) && ForTypeVariableBinding.this.equals(ForTypeVariableBinding.this);
                        }

                        public int hashCode() {
                            return (((getClass().hashCode() * 31) + this.typeVariable.hashCode()) * 31) + ForTypeVariableBinding.this.hashCode();
                        }

                        protected TypeVariableSubstitutor(Generic generic) {
                            this.typeVariable = generic;
                        }

                        @Override // net.bytebuddy.description.TypeVariableSource.Visitor
                        public Generic onType(TypeDescription typeDescription) {
                            Generic genericFindBindingOf = ForTypeVariableBinding.this.parameterizedType.findBindingOf(this.typeVariable);
                            return genericFindBindingOf == null ? this.typeVariable.asRawType() : genericFindBindingOf;
                        }

                        @Override // net.bytebuddy.description.TypeVariableSource.Visitor
                        public Generic onMethod(MethodDescription.InDefinedShape inDefinedShape) {
                            return ForTypeVariableBinding.this.new RetainedMethodTypeVariable(this.typeVariable);
                        }
                    }

                    protected class RetainedMethodTypeVariable extends OfTypeVariable {
                        private final Generic typeVariable;

                        protected RetainedMethodTypeVariable(Generic generic) {
                            this.typeVariable = generic;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getUpperBounds() {
                            return this.typeVariable.getUpperBounds().accept(ForTypeVariableBinding.this);
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeVariableSource getTypeVariableSource() {
                            return this.typeVariable.getTypeVariableSource();
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public String getSymbol() {
                            return this.typeVariable.getSymbol();
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return this.typeVariable.getDeclaredAnnotations();
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForTokenNormalization extends Substitutor {
                    private final TypeDescription typeDescription;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((ForTokenNormalization) obj).typeDescription);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onNonGenericType(Generic generic) {
                        return super.onNonGenericType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onParameterizedType(Generic generic) {
                        return super.onParameterizedType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    public ForTokenNormalization(TypeDescription typeDescription) {
                        this.typeDescription = typeDescription;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor
                    protected Generic onSimpleType(Generic generic) {
                        return generic.represents(TargetType.class) ? new OfNonGenericType.Latent(this.typeDescription, generic) : generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onTypeVariable(Generic generic) {
                        return new OfTypeVariable.Symbolic(generic.getSymbol(), generic);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForReplacement extends Substitutor {
                    private final TypeDescription typeDescription;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((ForReplacement) obj).typeDescription);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public Generic onTypeVariable(Generic generic) {
                        return generic;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onGenericArray(Generic generic) {
                        return super.onGenericArray(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onNonGenericType(Generic generic) {
                        return super.onNonGenericType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onParameterizedType(Generic generic) {
                        return super.onParameterizedType(generic);
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor, net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                    public /* bridge */ /* synthetic */ Generic onWildcard(Generic generic) {
                        return super.onWildcard(generic);
                    }

                    public ForReplacement(TypeDescription typeDescription) {
                        this.typeDescription = typeDescription;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Substitutor
                    protected Generic onSimpleType(Generic generic) {
                        return generic.asErasure().equals(this.typeDescription) ? new OfNonGenericType.Latent(this.typeDescription, generic) : generic;
                    }
                }
            }

            public static class ForRawType implements Visitor<Generic> {
                private final TypeDescription declaringType;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onNonGenericType(Generic generic) {
                    return generic;
                }

                public ForRawType(TypeDescription typeDescription) {
                    this.declaringType = typeDescription;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onGenericArray(Generic generic) {
                    return this.declaringType.isGenerified() ? new OfNonGenericType.Latent(generic.asErasure(), generic) : generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onWildcard(Generic generic) {
                    throw new IllegalStateException("Did not expect wildcard on top-level: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onParameterizedType(Generic generic) {
                    return this.declaringType.isGenerified() ? new OfNonGenericType.Latent(generic.asErasure(), generic) : generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Generic onTypeVariable(Generic generic) {
                    return this.declaringType.isGenerified() ? new OfNonGenericType.Latent(generic.asErasure(), generic) : generic;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Reducing implements Visitor<TypeDescription> {
                private final TypeDescription declaringType;
                private final List<? extends TypeVariableToken> typeVariableTokens;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Reducing reducing = (Reducing) obj;
                    return this.declaringType.equals(reducing.declaringType) && this.typeVariableTokens.equals(reducing.typeVariableTokens);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.declaringType.hashCode()) * 31) + this.typeVariableTokens.hashCode();
                }

                public Reducing(TypeDescription typeDescription, TypeVariableToken... typeVariableTokenArr) {
                    this(typeDescription, (List<? extends TypeVariableToken>) Arrays.asList(typeVariableTokenArr));
                }

                public Reducing(TypeDescription typeDescription, List<? extends TypeVariableToken> list) {
                    this.declaringType = typeDescription;
                    this.typeVariableTokens = list;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public TypeDescription onGenericArray(Generic generic) {
                    Generic componentType = generic;
                    int i = 0;
                    do {
                        componentType = componentType.getComponentType();
                        i++;
                    } while (componentType.isArray());
                    if (componentType.getSort().isTypeVariable()) {
                        for (TypeVariableToken typeVariableToken : this.typeVariableTokens) {
                            if (componentType.getSymbol().equals(typeVariableToken.getSymbol())) {
                                return ArrayProjection.of((TypeDescription) ((Generic) typeVariableToken.getBounds().get(0)).accept(this), i);
                            }
                        }
                        return TargetType.resolve(ArrayProjection.of(this.declaringType.findExpectedVariable(componentType.getSymbol()).asErasure(), i), this.declaringType);
                    }
                    return TargetType.resolve(generic.asErasure(), this.declaringType);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public TypeDescription onWildcard(Generic generic) {
                    throw new IllegalStateException("A wildcard cannot be a top-level type: " + generic);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public TypeDescription onParameterizedType(Generic generic) {
                    return TargetType.resolve(generic.asErasure(), this.declaringType);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public TypeDescription onTypeVariable(Generic generic) {
                    for (TypeVariableToken typeVariableToken : this.typeVariableTokens) {
                        if (generic.getSymbol().equals(typeVariableToken.getSymbol())) {
                            return (TypeDescription) ((Generic) typeVariableToken.getBounds().get(0)).accept(this);
                        }
                    }
                    return TargetType.resolve(this.declaringType.findExpectedVariable(generic.getSymbol()).asErasure(), this.declaringType);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public TypeDescription onNonGenericType(Generic generic) {
                    return TargetType.resolve(generic.asErasure(), this.declaringType);
                }
            }
        }

        public interface AnnotationReader {
            AnnotationList asList();

            AnnotationReader ofComponentType();

            AnnotationReader ofOuterClass();

            AnnotationReader ofOwnerType();

            AnnotationReader ofTypeArgument(int i);

            AnnotationReader ofTypeVariableBoundType(int i);

            AnnotationReader ofWildcardLowerBoundType(int i);

            AnnotationReader ofWildcardUpperBoundType(int i);

            AnnotatedElement resolve();

            public enum NoOp implements AnnotationReader, AnnotatedElement {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofComponentType() {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofOuterClass() {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofOwnerType() {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofTypeArgument(int i) {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofTypeVariableBoundType(int i) {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofWildcardLowerBoundType(int i) {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofWildcardUpperBoundType(int i) {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotatedElement resolve() {
                    return this;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationList asList() {
                    return new AnnotationList.Empty();
                }

                @Override // java.lang.reflect.AnnotatedElement
                public boolean isAnnotationPresent(Class<? extends Annotation> cls) {
                    throw new IllegalStateException("Cannot resolve annotations for no-op reader: " + this);
                }

                @Override // java.lang.reflect.AnnotatedElement
                public <T extends Annotation> T getAnnotation(Class<T> cls) {
                    throw new IllegalStateException("Cannot resolve annotations for no-op reader: " + this);
                }

                @Override // java.lang.reflect.AnnotatedElement
                public Annotation[] getAnnotations() {
                    throw new IllegalStateException("Cannot resolve annotations for no-op reader: " + this);
                }

                @Override // java.lang.reflect.AnnotatedElement
                public Annotation[] getDeclaredAnnotations() {
                    return new Annotation[0];
                }
            }

            public static abstract class Delegator implements AnnotationReader {
                private static final boolean ACCESS_CONTROLLER;

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

                @AccessControllerPlugin.Enhance
                static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
                    return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofWildcardUpperBoundType(int i) {
                    return new ForWildcardUpperBoundType(this, i);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofWildcardLowerBoundType(int i) {
                    return new ForWildcardLowerBoundType(this, i);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofTypeVariableBoundType(int i) {
                    return new ForTypeVariableBoundType(this, i);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofTypeArgument(int i) {
                    return new ForTypeArgument(this, i);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofOwnerType() {
                    return new ForOwnerType(this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofOuterClass() {
                    return new ForOwnerType(this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationReader ofComponentType() {
                    return new ForComponentType(this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public AnnotationList asList() {
                    return new AnnotationList.ForLoadedAnnotations(resolve().getDeclaredAnnotations());
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple extends Delegator {
                    private final AnnotatedElement annotatedElement;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.annotatedElement.equals(((Simple) obj).annotatedElement);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.annotatedElement.hashCode();
                    }

                    public Simple(AnnotatedElement annotatedElement) {
                        this.annotatedElement = annotatedElement;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        return this.annotatedElement;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                protected static abstract class Chained extends Delegator {
                    protected final AnnotationReader annotationReader;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.annotationReader.equals(((Chained) obj).annotationReader);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.annotationReader.hashCode();
                    }

                    protected abstract AnnotatedElement resolve(AnnotatedElement annotatedElement);

                    protected Chained(AnnotationReader annotationReader) {
                        this.annotationReader = annotationReader;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        return resolve(this.annotationReader.resolve());
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForLoadedTypeVariable extends Delegator {
                    private final TypeVariable<?> typeVariable;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.typeVariable.equals(((ForLoadedTypeVariable) obj).typeVariable);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.typeVariable.hashCode();
                    }

                    public ForLoadedTypeVariable(TypeVariable<?> typeVariable) {
                        this.typeVariable = typeVariable;
                    }

                    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.reflect.AnnotatedElement, java.lang.reflect.TypeVariable<?>] */
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        ?? r0 = this.typeVariable;
                        return r0 instanceof AnnotatedElement ? r0 : NoOp.INSTANCE;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotationReader ofTypeVariableBoundType(int i) {
                        return new ForTypeVariableBoundType.OfFormalTypeVariable(this.typeVariable, i);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                public static class ForLoadedSuperClass extends Delegator {
                    private final Class<?> type;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.type.equals(((ForLoadedSuperClass) obj).type);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.type.hashCode();
                    }

                    public ForLoadedSuperClass(Class<?> cls) {
                        this.type = cls;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        AnnotatedElement annotatedSuperclass = ForLoadedType.DISPATCHER.getAnnotatedSuperclass(this.type);
                        return annotatedSuperclass == null ? NoOp.INSTANCE : annotatedSuperclass;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                public static class ForLoadedInterface extends Delegator {
                    private final int index;
                    private final Class<?> type;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForLoadedInterface forLoadedInterface = (ForLoadedInterface) obj;
                        return this.index == forLoadedInterface.index && this.type.equals(forLoadedInterface.type);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.type.hashCode()) * 31) + this.index;
                    }

                    public ForLoadedInterface(Class<?> cls, int i) {
                        this.type = cls;
                        this.index = i;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        AnnotatedElement[] annotatedInterfaces = ForLoadedType.DISPATCHER.getAnnotatedInterfaces(this.type);
                        return annotatedInterfaces.length == 0 ? NoOp.INSTANCE : annotatedInterfaces[this.index];
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                public static class ForLoadedField extends Delegator {
                    protected static final Dispatcher DISPATCHER = (Dispatcher) doPrivileged(JavaDispatcher.of(Dispatcher.class));
                    private final Field field;

                    @JavaDispatcher.Proxied("java.lang.reflect.Field")
                    protected interface Dispatcher {
                        @JavaDispatcher.Defaults
                        @MaybeNull
                        @JavaDispatcher.Proxied("getAnnotatedType")
                        AnnotatedElement getAnnotatedType(Field field);
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.field.equals(((ForLoadedField) obj).field);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.field.hashCode();
                    }

                    public ForLoadedField(Field field) {
                        this.field = field;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        AnnotatedElement annotatedType = DISPATCHER.getAnnotatedType(this.field);
                        return annotatedType == null ? NoOp.INSTANCE : annotatedType;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                public static class ForLoadedMethodReturnType extends Delegator {
                    protected static final Dispatcher DISPATCHER = (Dispatcher) doPrivileged(JavaDispatcher.of(Dispatcher.class));
                    private final Method method;

                    @JavaDispatcher.Proxied("java.lang.reflect.Method")
                    protected interface Dispatcher {
                        @JavaDispatcher.Defaults
                        @MaybeNull
                        @JavaDispatcher.Proxied("getAnnotatedReturnType")
                        AnnotatedElement getAnnotatedReturnType(Method method);
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.method.equals(((ForLoadedMethodReturnType) obj).method);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.method.hashCode();
                    }

                    public ForLoadedMethodReturnType(Method method) {
                        this.method = method;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        AnnotatedElement annotatedReturnType = DISPATCHER.getAnnotatedReturnType(this.method);
                        return annotatedReturnType == null ? NoOp.INSTANCE : annotatedReturnType;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                public static class ForLoadedExecutableParameterType extends Delegator {
                    protected static final Dispatcher DISPATCHER = (Dispatcher) doPrivileged(JavaDispatcher.of(Dispatcher.class));
                    private final AccessibleObject executable;
                    private final int index;

                    @JavaDispatcher.Proxied("java.lang.reflect.Executable")
                    protected interface Dispatcher {
                        @JavaDispatcher.Defaults
                        @JavaDispatcher.Proxied("getAnnotatedParameterTypes")
                        AnnotatedElement[] getAnnotatedParameterTypes(Object obj);
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForLoadedExecutableParameterType forLoadedExecutableParameterType = (ForLoadedExecutableParameterType) obj;
                        return this.index == forLoadedExecutableParameterType.index && this.executable.equals(forLoadedExecutableParameterType.executable);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.executable.hashCode()) * 31) + this.index;
                    }

                    public ForLoadedExecutableParameterType(AccessibleObject accessibleObject, int i) {
                        this.executable = accessibleObject;
                        this.index = i;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        AnnotatedElement[] annotatedParameterTypes = DISPATCHER.getAnnotatedParameterTypes(this.executable);
                        return annotatedParameterTypes.length == 0 ? NoOp.INSTANCE : annotatedParameterTypes[this.index];
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                public static class ForLoadedExecutableExceptionType extends Delegator {
                    protected static final Dispatcher DISPATCHER = (Dispatcher) doPrivileged(JavaDispatcher.of(Dispatcher.class));
                    private final AccessibleObject executable;
                    private final int index;

                    @JavaDispatcher.Proxied("java.lang.reflect.Executable")
                    protected interface Dispatcher {
                        @JavaDispatcher.Defaults
                        @JavaDispatcher.Proxied("getAnnotatedExceptionTypes")
                        AnnotatedElement[] getAnnotatedExceptionTypes(Object obj);
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForLoadedExecutableExceptionType forLoadedExecutableExceptionType = (ForLoadedExecutableExceptionType) obj;
                        return this.index == forLoadedExecutableExceptionType.index && this.executable.equals(forLoadedExecutableExceptionType.executable);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.executable.hashCode()) * 31) + this.index;
                    }

                    public ForLoadedExecutableExceptionType(AccessibleObject accessibleObject, int i) {
                        this.executable = accessibleObject;
                        this.index = i;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        AnnotatedElement[] annotatedExceptionTypes = DISPATCHER.getAnnotatedExceptionTypes(this.executable);
                        return annotatedExceptionTypes.length == 0 ? NoOp.INSTANCE : annotatedExceptionTypes[this.index];
                    }
                }

                public static class ForLoadedRecordComponent extends Delegator {
                    private final Object recordComponent;

                    public ForLoadedRecordComponent(Object obj) {
                        this.recordComponent = obj;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        return RecordComponentDescription.ForLoadedRecordComponent.RECORD_COMPONENT.getAnnotatedType(this.recordComponent);
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForWildcardUpperBoundType extends Delegator.Chained {
                private static final AnnotatedWildcardType ANNOTATED_WILDCARD_TYPE = (AnnotatedWildcardType) doPrivileged(JavaDispatcher.of(AnnotatedWildcardType.class));
                private final int index;

                @JavaDispatcher.Proxied("java.lang.reflect.AnnotatedWildcardType")
                protected interface AnnotatedWildcardType {
                    @JavaDispatcher.Proxied("getAnnotatedUpperBounds")
                    AnnotatedElement[] getAnnotatedUpperBounds(AnnotatedElement annotatedElement);

                    @JavaDispatcher.Instance
                    @JavaDispatcher.Proxied("isInstance")
                    boolean isInstance(AnnotatedElement annotatedElement);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.index == ((ForWildcardUpperBoundType) obj).index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public int hashCode() {
                    return (super.hashCode() * 31) + this.index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public /* bridge */ /* synthetic */ AnnotatedElement resolve() {
                    return super.resolve();
                }

                protected ForWildcardUpperBoundType(AnnotationReader annotationReader, int i) {
                    super(annotationReader);
                    this.index = i;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                protected AnnotatedElement resolve(AnnotatedElement annotatedElement) {
                    AnnotatedWildcardType annotatedWildcardType = ANNOTATED_WILDCARD_TYPE;
                    if (!annotatedWildcardType.isInstance(annotatedElement)) {
                        return NoOp.INSTANCE;
                    }
                    try {
                        AnnotatedElement[] annotatedUpperBounds = annotatedWildcardType.getAnnotatedUpperBounds(annotatedElement);
                        return annotatedUpperBounds.length == 0 ? NoOp.INSTANCE : annotatedUpperBounds[this.index];
                    } catch (ClassCastException unused) {
                        return NoOp.INSTANCE;
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForWildcardLowerBoundType extends Delegator.Chained {
                private static final AnnotatedWildcardType ANNOTATED_WILDCARD_TYPE = (AnnotatedWildcardType) doPrivileged(JavaDispatcher.of(AnnotatedWildcardType.class));
                private final int index;

                @JavaDispatcher.Proxied("java.lang.reflect.AnnotatedWildcardType")
                protected interface AnnotatedWildcardType {
                    @JavaDispatcher.Proxied("getAnnotatedLowerBounds")
                    AnnotatedElement[] getAnnotatedLowerBounds(AnnotatedElement annotatedElement);

                    @JavaDispatcher.Instance
                    @JavaDispatcher.Proxied("isInstance")
                    boolean isInstance(AnnotatedElement annotatedElement);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.index == ((ForWildcardLowerBoundType) obj).index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public int hashCode() {
                    return (super.hashCode() * 31) + this.index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public /* bridge */ /* synthetic */ AnnotatedElement resolve() {
                    return super.resolve();
                }

                protected ForWildcardLowerBoundType(AnnotationReader annotationReader, int i) {
                    super(annotationReader);
                    this.index = i;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                protected AnnotatedElement resolve(AnnotatedElement annotatedElement) {
                    AnnotatedWildcardType annotatedWildcardType = ANNOTATED_WILDCARD_TYPE;
                    if (!annotatedWildcardType.isInstance(annotatedElement)) {
                        return NoOp.INSTANCE;
                    }
                    try {
                        return annotatedWildcardType.getAnnotatedLowerBounds(annotatedElement)[this.index];
                    } catch (ClassCastException unused) {
                        return NoOp.INSTANCE;
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForTypeVariableBoundType extends Delegator.Chained {
                private static final AnnotatedTypeVariable ANNOTATED_TYPE_VARIABLE = (AnnotatedTypeVariable) doPrivileged(JavaDispatcher.of(AnnotatedTypeVariable.class));
                private final int index;

                @JavaDispatcher.Proxied("java.lang.reflect.AnnotatedTypeVariable")
                protected interface AnnotatedTypeVariable {
                    @JavaDispatcher.Proxied("getAnnotatedBounds")
                    AnnotatedElement[] getAnnotatedBounds(AnnotatedElement annotatedElement);

                    @JavaDispatcher.Instance
                    @JavaDispatcher.Proxied("isInstance")
                    boolean isInstance(AnnotatedElement annotatedElement);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.index == ((ForTypeVariableBoundType) obj).index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public int hashCode() {
                    return (super.hashCode() * 31) + this.index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public /* bridge */ /* synthetic */ AnnotatedElement resolve() {
                    return super.resolve();
                }

                protected ForTypeVariableBoundType(AnnotationReader annotationReader, int i) {
                    super(annotationReader);
                    this.index = i;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                protected AnnotatedElement resolve(AnnotatedElement annotatedElement) {
                    AnnotatedTypeVariable annotatedTypeVariable = ANNOTATED_TYPE_VARIABLE;
                    if (!annotatedTypeVariable.isInstance(annotatedElement)) {
                        return NoOp.INSTANCE;
                    }
                    try {
                        return annotatedTypeVariable.getAnnotatedBounds(annotatedElement)[this.index];
                    } catch (ClassCastException unused) {
                        return NoOp.INSTANCE;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                protected static class OfFormalTypeVariable extends Delegator {
                    private static final FormalTypeVariable TYPE_VARIABLE = (FormalTypeVariable) doPrivileged(JavaDispatcher.of(FormalTypeVariable.class));
                    private final int index;
                    private final TypeVariable<?> typeVariable;

                    @JavaDispatcher.Proxied("java.lang.reflect.TypeVariable")
                    protected interface FormalTypeVariable {
                        @JavaDispatcher.Defaults
                        @JavaDispatcher.Proxied("getAnnotatedBounds")
                        AnnotatedElement[] getAnnotatedBounds(Object obj);
                    }

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        OfFormalTypeVariable ofFormalTypeVariable = (OfFormalTypeVariable) obj;
                        return this.index == ofFormalTypeVariable.index && this.typeVariable.equals(ofFormalTypeVariable.typeVariable);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.typeVariable.hashCode()) * 31) + this.index;
                    }

                    protected OfFormalTypeVariable(TypeVariable<?> typeVariable, int i) {
                        this.typeVariable = typeVariable;
                        this.index = i;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                    public AnnotatedElement resolve() {
                        try {
                            AnnotatedElement[] annotatedBounds = TYPE_VARIABLE.getAnnotatedBounds(this.typeVariable);
                            return annotatedBounds.length == 0 ? NoOp.INSTANCE : annotatedBounds[this.index];
                        } catch (ClassCastException unused) {
                            return NoOp.INSTANCE;
                        }
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForTypeArgument extends Delegator.Chained {
                private static final AnnotatedParameterizedType ANNOTATED_PARAMETERIZED_TYPE = (AnnotatedParameterizedType) doPrivileged(JavaDispatcher.of(AnnotatedParameterizedType.class));
                private final int index;

                @JavaDispatcher.Proxied("java.lang.reflect.AnnotatedParameterizedType")
                protected interface AnnotatedParameterizedType {
                    @JavaDispatcher.Proxied("getAnnotatedActualTypeArguments")
                    AnnotatedElement[] getAnnotatedActualTypeArguments(AnnotatedElement annotatedElement);

                    @JavaDispatcher.Instance
                    @JavaDispatcher.Proxied("isInstance")
                    boolean isInstance(AnnotatedElement annotatedElement);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.index == ((ForTypeArgument) obj).index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                public int hashCode() {
                    return (super.hashCode() * 31) + this.index;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public /* bridge */ /* synthetic */ AnnotatedElement resolve() {
                    return super.resolve();
                }

                protected ForTypeArgument(AnnotationReader annotationReader, int i) {
                    super(annotationReader);
                    this.index = i;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                protected AnnotatedElement resolve(AnnotatedElement annotatedElement) {
                    AnnotatedParameterizedType annotatedParameterizedType = ANNOTATED_PARAMETERIZED_TYPE;
                    if (!annotatedParameterizedType.isInstance(annotatedElement)) {
                        return NoOp.INSTANCE;
                    }
                    try {
                        return annotatedParameterizedType.getAnnotatedActualTypeArguments(annotatedElement)[this.index];
                    } catch (ClassCastException unused) {
                        return NoOp.INSTANCE;
                    }
                }
            }

            public static class ForComponentType extends Delegator.Chained {
                private static final AnnotatedParameterizedType ANNOTATED_PARAMETERIZED_TYPE = (AnnotatedParameterizedType) doPrivileged(JavaDispatcher.of(AnnotatedParameterizedType.class));

                @JavaDispatcher.Proxied("java.lang.reflect.AnnotatedArrayType")
                protected interface AnnotatedParameterizedType {
                    @JavaDispatcher.Proxied("getAnnotatedGenericComponentType")
                    AnnotatedElement getAnnotatedGenericComponentType(AnnotatedElement annotatedElement);

                    @JavaDispatcher.Instance
                    @JavaDispatcher.Proxied("isInstance")
                    boolean isInstance(AnnotatedElement annotatedElement);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public /* bridge */ /* synthetic */ AnnotatedElement resolve() {
                    return super.resolve();
                }

                protected ForComponentType(AnnotationReader annotationReader) {
                    super(annotationReader);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                protected AnnotatedElement resolve(AnnotatedElement annotatedElement) {
                    AnnotatedParameterizedType annotatedParameterizedType = ANNOTATED_PARAMETERIZED_TYPE;
                    if (!annotatedParameterizedType.isInstance(annotatedElement)) {
                        return NoOp.INSTANCE;
                    }
                    try {
                        return annotatedParameterizedType.getAnnotatedGenericComponentType(annotatedElement);
                    } catch (ClassCastException unused) {
                        return NoOp.INSTANCE;
                    }
                }
            }

            public static class ForOwnerType extends Delegator.Chained {
                private static final AnnotatedType ANNOTATED_TYPE = (AnnotatedType) doPrivileged(JavaDispatcher.of(AnnotatedType.class));

                @JavaDispatcher.Proxied("java.lang.reflect.AnnotatedType")
                protected interface AnnotatedType {
                    @JavaDispatcher.Defaults
                    @MaybeNull
                    @JavaDispatcher.Proxied("getAnnotatedOwnerType")
                    AnnotatedElement getAnnotatedOwnerType(AnnotatedElement annotatedElement);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained, net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader
                public /* bridge */ /* synthetic */ AnnotatedElement resolve() {
                    return super.resolve();
                }

                protected ForOwnerType(AnnotationReader annotationReader) {
                    super(annotationReader);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AnnotationReader.Delegator.Chained
                protected AnnotatedElement resolve(AnnotatedElement annotatedElement) {
                    try {
                        AnnotatedElement annotatedOwnerType = ANNOTATED_TYPE.getAnnotatedOwnerType(annotatedElement);
                        return annotatedOwnerType == null ? NoOp.INSTANCE : annotatedOwnerType;
                    } catch (ClassCastException unused) {
                        return NoOp.INSTANCE;
                    }
                }
            }
        }

        public static abstract class AbstractBase extends ModifierReviewable.AbstractBase implements Generic {
            @Override // net.bytebuddy.description.type.TypeDefinition
            public Generic asGenericType() {
                return this;
            }

            @Override // net.bytebuddy.description.ModifierReviewable
            public int getModifiers() {
                return asErasure().getModifiers();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic asRawType() {
                return asErasure().asGenericType();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean represents(Type type) {
                return equals(TypeDefinition.Sort.describe(type));
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class LazyProxy implements InvocationHandler {
            private final Class<?> type;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.type.equals(((LazyProxy) obj).type);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.type.hashCode();
            }

            protected LazyProxy(Class<?> cls) {
                this.type = cls;
            }

            protected static Generic of(Class<?> cls) {
                return (Generic) Proxy.newProxyInstance(Generic.class.getClassLoader(), new Class[]{Generic.class}, new LazyProxy(cls));
            }

            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, Method method, @MaybeNull Object[] objArr) throws Throwable {
                try {
                    return method.invoke(OfNonGenericType.ForLoadedType.of(this.type), objArr);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }

        public static abstract class OfNonGenericType extends AbstractBase {
            private transient /* synthetic */ int hashCode;

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDefinition.Sort getSort() {
                return TypeDefinition.Sort.NON_GENERIC;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public Generic getSuperClass() {
                TypeDescription typeDescriptionAsErasure = asErasure();
                Generic superClass = typeDescriptionAsErasure.getSuperClass();
                if (AbstractBase.RAW_TYPES) {
                    return superClass;
                }
                return superClass == null ? Generic.UNDEFINED : new LazyProjection.WithResolvedErasure(superClass, new Visitor.ForRawType(typeDescriptionAsErasure), AnnotationSource.Empty.INSTANCE);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeList.Generic getInterfaces() {
                TypeDescription typeDescriptionAsErasure = asErasure();
                if (AbstractBase.RAW_TYPES) {
                    return typeDescriptionAsErasure.getInterfaces();
                }
                return new TypeList.Generic.ForDetachedTypes.WithResolvedErasure(typeDescriptionAsErasure.getInterfaces(), new Visitor.ForRawType(typeDescriptionAsErasure));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                TypeDescription typeDescriptionAsErasure = asErasure();
                return new FieldList.TypeSubstituting(this, typeDescriptionAsErasure.getDeclaredFields(), AbstractBase.RAW_TYPES ? Visitor.NoOp.INSTANCE : new Visitor.ForRawType(typeDescriptionAsErasure));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                TypeDescription typeDescriptionAsErasure = asErasure();
                return new MethodList.TypeSubstituting(this, typeDescriptionAsErasure.getDeclaredMethods(), AbstractBase.RAW_TYPES ? Visitor.NoOp.INSTANCE : new Visitor.ForRawType(typeDescriptionAsErasure));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                TypeDescription typeDescriptionAsErasure = asErasure();
                return new RecordComponentList.TypeSubstituting(this, typeDescriptionAsErasure.getRecordComponents(), AbstractBase.RAW_TYPES ? Visitor.NoOp.INSTANCE : new Visitor.ForRawType(typeDescriptionAsErasure));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getTypeArguments() {
                throw new IllegalStateException("A non-generic type does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic findBindingOf(Generic generic) {
                throw new IllegalStateException("A non-generic type does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public <T> T accept(Visitor<T> visitor) {
                return visitor.onNonGenericType(this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public String getTypeName() {
                return asErasure().getTypeName();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getUpperBounds() {
                throw new IllegalStateException("A non-generic type does not imply upper type bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getLowerBounds() {
                throw new IllegalStateException("A non-generic type does not imply lower type bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeVariableSource getTypeVariableSource() {
                throw new IllegalStateException("A non-generic type does not imply a type variable source: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public String getSymbol() {
                throw new IllegalStateException("A non-generic type does not imply a symbol: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                return asErasure().getStackSize();
            }

            @Override // net.bytebuddy.description.NamedElement
            public String getActualName() {
                return asErasure().getActualName();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return asErasure().isArray();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return asErasure().isPrimitive();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return asErasure().isRecord();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
            public boolean represents(Type type) {
                return asErasure().represents(type);
            }

            @Override // java.lang.Iterable
            public Iterator<TypeDefinition> iterator() {
                return new TypeDefinition.SuperClassIterator(this);
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int iHashCode = this.hashCode != 0 ? 0 : asErasure().hashCode();
                if (iHashCode == 0) {
                    return this.hashCode;
                }
                this.hashCode = iHashCode;
                return iHashCode;
            }

            public boolean equals(@MaybeNull Object obj) {
                return this == obj || asErasure().equals(obj);
            }

            public String toString() {
                return asErasure().toString();
            }

            public static class ForLoadedType extends OfNonGenericType {
                private static final Map<Class<?>, Generic> TYPE_CACHE;
                private final AnnotationReader annotationReader;
                private final Class<?> type;

                static {
                    HashMap map = new HashMap();
                    TYPE_CACHE = map;
                    map.put(TargetType.class, new ForLoadedType(TargetType.class));
                    map.put(Class.class, new ForLoadedType(Class.class));
                    map.put(Throwable.class, new ForLoadedType(Throwable.class));
                    map.put(Annotation.class, new ForLoadedType(Annotation.class));
                    map.put(Object.class, new ForLoadedType(Object.class));
                    map.put(String.class, new ForLoadedType(String.class));
                    map.put(Boolean.class, new ForLoadedType(Boolean.class));
                    map.put(Byte.class, new ForLoadedType(Byte.class));
                    map.put(Short.class, new ForLoadedType(Short.class));
                    map.put(Character.class, new ForLoadedType(Character.class));
                    map.put(Integer.class, new ForLoadedType(Integer.class));
                    map.put(Long.class, new ForLoadedType(Long.class));
                    map.put(Float.class, new ForLoadedType(Float.class));
                    map.put(Double.class, new ForLoadedType(Double.class));
                    map.put(Void.TYPE, new ForLoadedType(Void.TYPE));
                    map.put(Boolean.TYPE, new ForLoadedType(Boolean.TYPE));
                    map.put(Byte.TYPE, new ForLoadedType(Byte.TYPE));
                    map.put(Short.TYPE, new ForLoadedType(Short.TYPE));
                    map.put(Character.TYPE, new ForLoadedType(Character.TYPE));
                    map.put(Integer.TYPE, new ForLoadedType(Integer.TYPE));
                    map.put(Long.TYPE, new ForLoadedType(Long.TYPE));
                    map.put(Float.TYPE, new ForLoadedType(Float.TYPE));
                    map.put(Double.TYPE, new ForLoadedType(Double.TYPE));
                }

                public ForLoadedType(Class<?> cls) {
                    this(cls, AnnotationReader.NoOp.INSTANCE);
                }

                protected ForLoadedType(Class<?> cls, AnnotationReader annotationReader) {
                    this.type = cls;
                    this.annotationReader = annotationReader;
                }

                public static Generic of(Class<?> cls) {
                    Generic generic = TYPE_CACHE.get(cls);
                    return generic == null ? new ForLoadedType(cls) : generic;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(this.type);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    Class<?> declaringClass = this.type.getDeclaringClass();
                    if (declaringClass == null) {
                        return Generic.UNDEFINED;
                    }
                    return new ForLoadedType(declaringClass, this.annotationReader.ofOuterClass());
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getComponentType() {
                    Class<?> componentType = this.type.getComponentType();
                    if (componentType == null) {
                        return Generic.UNDEFINED;
                    }
                    return new ForLoadedType(componentType, this.annotationReader.ofComponentType());
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationReader.asList();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfNonGenericType, net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
                public boolean represents(Type type) {
                    return this.type == type || super.represents(type);
                }
            }

            public static class ForErasure extends OfNonGenericType {
                private final TypeDescription typeDescription;

                public ForErasure(TypeDescription typeDescription) {
                    this.typeDescription = typeDescription;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.typeDescription;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    TypeDescription declaringType = this.typeDescription.getDeclaringType();
                    if (declaringType == null) {
                        return Generic.UNDEFINED;
                    }
                    return declaringType.asGenericType();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getComponentType() {
                    TypeDescription componentType = this.typeDescription.getComponentType();
                    if (componentType == null) {
                        return Generic.UNDEFINED;
                    }
                    return componentType.asGenericType();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return new AnnotationList.Empty();
                }
            }

            public static class Latent extends OfNonGenericType {
                private final AnnotationSource annotationSource;

                @MaybeNull
                private final Generic declaringType;
                private final TypeDescription typeDescription;

                public Latent(TypeDescription typeDescription, AnnotationSource annotationSource) {
                    this(typeDescription, typeDescription.getDeclaringType(), annotationSource);
                }

                /* JADX WARN: Illegal instructions before constructor call */
                private Latent(TypeDescription typeDescription, @MaybeNull TypeDescription typeDescription2, AnnotationSource annotationSource) {
                    Generic genericAsGenericType;
                    if (typeDescription2 == null) {
                        genericAsGenericType = Generic.UNDEFINED;
                    } else {
                        genericAsGenericType = typeDescription2.asGenericType();
                    }
                    this(typeDescription, genericAsGenericType, annotationSource);
                }

                protected Latent(TypeDescription typeDescription, @MaybeNull Generic generic, AnnotationSource annotationSource) {
                    this.typeDescription = typeDescription;
                    this.declaringType = generic;
                    this.annotationSource = annotationSource;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    return this.declaringType;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getComponentType() {
                    TypeDescription componentType = this.typeDescription.getComponentType();
                    if (componentType == null) {
                        return Generic.UNDEFINED;
                    }
                    return componentType.asGenericType();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.typeDescription;
                }
            }

            public static class ForReifiedErasure extends OfNonGenericType {
                private final TypeDescription typeDescription;

                protected ForReifiedErasure(TypeDescription typeDescription) {
                    this.typeDescription = typeDescription;
                }

                protected static Generic of(TypeDescription typeDescription) {
                    return typeDescription.isGenerified() ? new ForReifiedErasure(typeDescription) : new ForErasure(typeDescription);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfNonGenericType, net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getSuperClass() {
                    Generic superClass = this.typeDescription.getSuperClass();
                    return superClass == null ? Generic.UNDEFINED : new LazyProjection.WithResolvedErasure(superClass, Visitor.Reifying.INHERITING);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfNonGenericType, net.bytebuddy.description.type.TypeDefinition
                public TypeList.Generic getInterfaces() {
                    return new TypeList.Generic.ForDetachedTypes.WithResolvedErasure(this.typeDescription.getInterfaces(), Visitor.Reifying.INHERITING);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfNonGenericType, net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                    return new FieldList.TypeSubstituting(this, this.typeDescription.getDeclaredFields(), Visitor.TypeErasing.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfNonGenericType, net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                    return new MethodList.TypeSubstituting(this, this.typeDescription.getDeclaredMethods(), Visitor.TypeErasing.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.typeDescription;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    TypeDescription declaringType = this.typeDescription.getDeclaringType();
                    if (declaringType == null) {
                        return Generic.UNDEFINED;
                    }
                    return of(declaringType);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getComponentType() {
                    TypeDescription componentType = this.typeDescription.getComponentType();
                    if (componentType == null) {
                        return Generic.UNDEFINED;
                    }
                    return of(componentType);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return new AnnotationList.Empty();
                }
            }
        }

        public static abstract class OfGenericArray extends AbstractBase {
            private transient /* synthetic */ int hashCode;

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return true;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDefinition.Sort getSort() {
                return getComponentType().getSort().isNonGeneric() ? TypeDefinition.Sort.NON_GENERIC : TypeDefinition.Sort.GENERIC_ARRAY;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDescription asErasure() {
                return ArrayProjection.of(getComponentType().asErasure(), 1);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public Generic getSuperClass() {
                return OfNonGenericType.ForLoadedType.of(Object.class);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeList.Generic getInterfaces() {
                return TypeDescription.ARRAY_INTERFACES;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                return new FieldList.Empty();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                return new MethodList.Empty();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                return new RecordComponentList.Empty();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getUpperBounds() {
                throw new IllegalStateException("A generic array type does not imply upper type bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getLowerBounds() {
                throw new IllegalStateException("A generic array type does not imply lower type bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeVariableSource getTypeVariableSource() {
                throw new IllegalStateException("A generic array type does not imply a type variable source: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getTypeArguments() {
                throw new IllegalStateException("A generic array type does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic findBindingOf(Generic generic) {
                throw new IllegalStateException("A generic array type does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            @MaybeNull
            public Generic getOwnerType() {
                return Generic.UNDEFINED;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public String getSymbol() {
                throw new IllegalStateException("A generic array type does not imply a symbol: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public String getTypeName() {
                if (getSort().isNonGeneric()) {
                    return asErasure().getTypeName();
                }
                return toString();
            }

            @Override // net.bytebuddy.description.NamedElement
            public String getActualName() {
                if (getSort().isNonGeneric()) {
                    return asErasure().getActualName();
                }
                return toString();
            }

            @Override // java.lang.Iterable
            public Iterator<TypeDefinition> iterator() {
                return new TypeDefinition.SuperClassIterator(this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public <T> T accept(Visitor<T> visitor) {
                if (getSort().isNonGeneric()) {
                    return visitor.onNonGenericType(this);
                }
                return visitor.onGenericArray(this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                return StackSize.SINGLE;
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int iHashCode;
                if (this.hashCode != 0) {
                    iHashCode = 0;
                } else if (getSort().isNonGeneric()) {
                    iHashCode = asErasure().hashCode();
                } else {
                    iHashCode = getComponentType().hashCode();
                }
                if (iHashCode == 0) {
                    return this.hashCode;
                }
                this.hashCode = iHashCode;
                return iHashCode;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (getSort().isNonGeneric()) {
                    return asErasure().equals(obj);
                }
                if (!(obj instanceof Generic)) {
                    return false;
                }
                Generic generic = (Generic) obj;
                return generic.getSort().isGenericArray() && getComponentType().equals(generic.getComponentType());
            }

            public String toString() {
                if (getSort().isNonGeneric()) {
                    return asErasure().toString();
                }
                return getComponentType().getTypeName() + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
            }

            public static class ForLoadedType extends OfGenericArray {
                private final AnnotationReader annotationReader;
                private final GenericArrayType genericArrayType;

                public ForLoadedType(GenericArrayType genericArrayType) {
                    this(genericArrayType, AnnotationReader.NoOp.INSTANCE);
                }

                protected ForLoadedType(GenericArrayType genericArrayType, AnnotationReader annotationReader) {
                    this.genericArrayType = genericArrayType;
                    this.annotationReader = annotationReader;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getComponentType() {
                    return TypeDefinition.Sort.describe(this.genericArrayType.getGenericComponentType(), this.annotationReader.ofComponentType());
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationReader.asList();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
                public boolean represents(Type type) {
                    return this.genericArrayType == type || super.represents(type);
                }
            }

            public static class Latent extends OfGenericArray {
                private final AnnotationSource annotationSource;
                private final Generic componentType;

                public Latent(Generic generic, AnnotationSource annotationSource) {
                    this.componentType = generic;
                    this.annotationSource = annotationSource;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public Generic getComponentType() {
                    return this.componentType;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }
            }
        }

        public static abstract class OfWildcardType extends AbstractBase {
            public static final String SYMBOL = "?";
            private transient /* synthetic */ int hashCode;

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDefinition.Sort getSort() {
                return TypeDefinition.Sort.WILDCARD;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDescription asErasure() {
                throw new IllegalStateException("A wildcard does not represent an erasable type: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public Generic getSuperClass() {
                throw new IllegalStateException("A wildcard does not imply a super type definition: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeList.Generic getInterfaces() {
                throw new IllegalStateException("A wildcard does not imply an interface type definition: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                throw new IllegalStateException("A wildcard does not imply field definitions: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                throw new IllegalStateException("A wildcard does not imply method definitions: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                throw new IllegalStateException("A wildcard does not imply record component definitions: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public Generic getComponentType() {
                throw new IllegalStateException("A wildcard does not imply a component type: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeVariableSource getTypeVariableSource() {
                throw new IllegalStateException("A wildcard does not imply a type variable source: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getTypeArguments() {
                throw new IllegalStateException("A wildcard does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic findBindingOf(Generic generic) {
                throw new IllegalStateException("A wildcard does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic getOwnerType() {
                throw new IllegalStateException("A wildcard does not imply an owner type: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public String getSymbol() {
                throw new IllegalStateException("A wildcard does not imply a symbol: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public String getTypeName() {
                return toString();
            }

            @Override // net.bytebuddy.description.NamedElement
            public String getActualName() {
                return toString();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
            public boolean represents(Type type) {
                return equals(TypeDefinition.Sort.describe(type));
            }

            @Override // java.lang.Iterable
            public Iterator<TypeDefinition> iterator() {
                throw new IllegalStateException("A wildcard does not imply a super type definition: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public <T> T accept(Visitor<T> visitor) {
                return visitor.onWildcard(this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                throw new IllegalStateException("A wildcard does not imply an operand stack size: " + this);
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int i;
                if (this.hashCode != 0) {
                    i = 0;
                } else {
                    Iterator it = getLowerBounds().iterator();
                    int iHashCode = 1;
                    int iHashCode2 = 1;
                    while (it.hasNext()) {
                        iHashCode2 = (iHashCode2 * 31) + ((Generic) it.next()).hashCode();
                    }
                    Iterator it2 = getUpperBounds().iterator();
                    while (it2.hasNext()) {
                        iHashCode = (iHashCode * 31) + ((Generic) it2.next()).hashCode();
                    }
                    i = iHashCode2 ^ iHashCode;
                }
                if (i == 0) {
                    return this.hashCode;
                }
                this.hashCode = i;
                return i;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Generic)) {
                    return false;
                }
                Generic generic = (Generic) obj;
                return generic.getSort().isWildcard() && getUpperBounds().equals(generic.getUpperBounds()) && getLowerBounds().equals(generic.getLowerBounds());
            }

            public String toString() {
                StringBuilder sb = new StringBuilder(SYMBOL);
                TypeList.Generic lowerBounds = getLowerBounds();
                if (!lowerBounds.isEmpty()) {
                    sb.append(" super ");
                } else {
                    lowerBounds = getUpperBounds();
                    if (lowerBounds.getOnly().equals(OfNonGenericType.ForLoadedType.of(Object.class))) {
                        return SYMBOL;
                    }
                    sb.append(" extends ");
                }
                return sb.append(lowerBounds.getOnly().getTypeName()).toString();
            }

            public static class ForLoadedType extends OfWildcardType {
                private final AnnotationReader annotationReader;
                private final WildcardType wildcardType;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfWildcardType, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                public ForLoadedType(WildcardType wildcardType) {
                    this(wildcardType, AnnotationReader.NoOp.INSTANCE);
                }

                protected ForLoadedType(WildcardType wildcardType, AnnotationReader annotationReader) {
                    this.wildcardType = wildcardType;
                    this.annotationReader = annotationReader;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getUpperBounds() {
                    return new WildcardUpperBoundTypeList(this.wildcardType.getUpperBounds(), this.annotationReader);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getLowerBounds() {
                    return new WildcardLowerBoundTypeList(this.wildcardType.getLowerBounds(), this.annotationReader);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationReader.asList();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfWildcardType, net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
                public boolean represents(Type type) {
                    return this.wildcardType == type || super.represents(type);
                }

                protected static class WildcardUpperBoundTypeList extends TypeList.Generic.AbstractBase {
                    private final AnnotationReader annotationReader;
                    private final Type[] upperBound;

                    protected WildcardUpperBoundTypeList(Type[] typeArr, AnnotationReader annotationReader) {
                        this.upperBound = typeArr;
                        this.annotationReader = annotationReader;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public Generic get(int i) {
                        return TypeDefinition.Sort.describe(this.upperBound[i], this.annotationReader.ofWildcardUpperBoundType(i));
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.upperBound.length;
                    }
                }

                protected static class WildcardLowerBoundTypeList extends TypeList.Generic.AbstractBase {
                    private final AnnotationReader annotationReader;
                    private final Type[] lowerBound;

                    protected WildcardLowerBoundTypeList(Type[] typeArr, AnnotationReader annotationReader) {
                        this.lowerBound = typeArr;
                        this.annotationReader = annotationReader;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public Generic get(int i) {
                        return TypeDefinition.Sort.describe(this.lowerBound[i], this.annotationReader.ofWildcardLowerBoundType(i));
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.lowerBound.length;
                    }
                }
            }

            public static class Latent extends OfWildcardType {
                private final AnnotationSource annotationSource;
                private final List<? extends Generic> lowerBounds;
                private final List<? extends Generic> upperBounds;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfWildcardType, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                protected Latent(List<? extends Generic> list, List<? extends Generic> list2, AnnotationSource annotationSource) {
                    this.upperBounds = list;
                    this.lowerBounds = list2;
                    this.annotationSource = annotationSource;
                }

                public static Generic unbounded(AnnotationSource annotationSource) {
                    return new Latent(Collections.singletonList(OfNonGenericType.ForLoadedType.of(Object.class)), Collections.emptyList(), annotationSource);
                }

                public static Generic boundedAbove(Generic generic, AnnotationSource annotationSource) {
                    return new Latent(Collections.singletonList(generic), Collections.emptyList(), annotationSource);
                }

                public static Generic boundedBelow(Generic generic, AnnotationSource annotationSource) {
                    return new Latent(Collections.singletonList(OfNonGenericType.ForLoadedType.of(Object.class)), Collections.singletonList(generic), annotationSource);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getUpperBounds() {
                    return new TypeList.Generic.Explicit(this.upperBounds);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getLowerBounds() {
                    return new TypeList.Generic.Explicit(this.lowerBounds);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }
            }
        }

        public static abstract class OfParameterizedType extends AbstractBase {
            private transient /* synthetic */ int hashCode;

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDefinition.Sort getSort() {
                return TypeDefinition.Sort.PARAMETERIZED;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public Generic getSuperClass() {
                Generic superClass = asErasure().getSuperClass();
                return superClass == null ? Generic.UNDEFINED : new LazyProjection.WithResolvedErasure(superClass, new Visitor.Substitutor.ForTypeVariableBinding(this));
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeList.Generic getInterfaces() {
                return new TypeList.Generic.ForDetachedTypes.WithResolvedErasure(asErasure().getInterfaces(), new Visitor.Substitutor.ForTypeVariableBinding(this));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                return new FieldList.TypeSubstituting(this, asErasure().getDeclaredFields(), new Visitor.Substitutor.ForTypeVariableBinding(this));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                return new MethodList.TypeSubstituting(this, asErasure().getDeclaredMethods(), new Visitor.Substitutor.ForTypeVariableBinding(this));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                return new RecordComponentList.TypeSubstituting(this, asErasure().getRecordComponents(), new Visitor.Substitutor.ForTypeVariableBinding(this));
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            @MaybeNull
            public Generic findBindingOf(Generic generic) {
                Generic ownerType = this;
                do {
                    TypeList.Generic typeArguments = ownerType.getTypeArguments();
                    TypeList.Generic typeVariables = ownerType.asErasure().getTypeVariables();
                    for (int i = 0; i < Math.min(typeArguments.size(), typeVariables.size()); i++) {
                        if (generic.equals(typeVariables.get(i))) {
                            return (Generic) typeArguments.get(i);
                        }
                    }
                    ownerType = ownerType.getOwnerType();
                    if (ownerType == null) {
                        break;
                    }
                } while (ownerType.getSort().isParameterized());
                return Generic.UNDEFINED;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getUpperBounds() {
                throw new IllegalStateException("A parameterized type does not imply upper bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getLowerBounds() {
                throw new IllegalStateException("A parameterized type does not imply lower bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public Generic getComponentType() {
                throw new IllegalStateException("A parameterized type does not imply a component type: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeVariableSource getTypeVariableSource() {
                throw new IllegalStateException("A parameterized type does not imply a type variable source: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public String getSymbol() {
                throw new IllegalStateException("A parameterized type does not imply a symbol: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public String getTypeName() {
                return toString();
            }

            @Override // net.bytebuddy.description.NamedElement
            public String getActualName() {
                return toString();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return asErasure().isRecord();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
            public boolean represents(Type type) {
                return equals(TypeDefinition.Sort.describe(type));
            }

            @Override // java.lang.Iterable
            public Iterator<TypeDefinition> iterator() {
                return new TypeDefinition.SuperClassIterator(this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public <T> T accept(Visitor<T> visitor) {
                return visitor.onParameterizedType(this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                return StackSize.SINGLE;
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int iHashCode;
                int i;
                if (this.hashCode != 0) {
                    i = 0;
                } else {
                    Iterator it = getTypeArguments().iterator();
                    int iHashCode2 = 1;
                    while (it.hasNext()) {
                        iHashCode2 = (iHashCode2 * 31) + ((Generic) it.next()).hashCode();
                    }
                    Generic ownerType = getOwnerType();
                    if (ownerType == null) {
                        iHashCode = asErasure().hashCode();
                    } else {
                        iHashCode = ownerType.hashCode();
                    }
                    i = iHashCode ^ iHashCode2;
                }
                if (i == 0) {
                    return this.hashCode;
                }
                this.hashCode = i;
                return i;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Generic)) {
                    return false;
                }
                Generic generic = (Generic) obj;
                if (!generic.getSort().isParameterized()) {
                    return false;
                }
                Generic ownerType = getOwnerType();
                Generic ownerType2 = generic.getOwnerType();
                return asErasure().equals(generic.asErasure()) && (ownerType != null || ownerType2 == null) && ((ownerType == null || ownerType.equals(ownerType2)) && getTypeArguments().equals(generic.getTypeArguments()));
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                RenderingDelegate.CURRENT.apply(sb, asErasure(), getOwnerType());
                TypeList.Generic<Generic> typeArguments = getTypeArguments();
                if (!typeArguments.isEmpty()) {
                    sb.append('<');
                    boolean z = false;
                    for (Generic generic : typeArguments) {
                        if (z) {
                            sb.append(", ");
                        }
                        sb.append(generic.getTypeName());
                        z = true;
                    }
                    sb.append('>');
                }
                return sb.toString();
            }

            protected enum RenderingDelegate {
                FOR_LEGACY_VM { // from class: net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType.RenderingDelegate.1
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType.RenderingDelegate
                    protected void apply(StringBuilder sb, TypeDescription typeDescription, @MaybeNull Generic generic) {
                        String name2;
                        if (generic != null) {
                            StringBuilder sbAppend = sb.append(generic.getTypeName()).append('.');
                            if (generic.getSort().isParameterized()) {
                                name2 = typeDescription.getSimpleName();
                            } else {
                                name2 = typeDescription.getName();
                            }
                            sbAppend.append(name2);
                            return;
                        }
                        sb.append(typeDescription.getName());
                    }
                },
                FOR_JAVA_8_CAPABLE_VM { // from class: net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType.RenderingDelegate.2
                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType.RenderingDelegate
                    protected void apply(StringBuilder sb, TypeDescription typeDescription, @MaybeNull Generic generic) {
                        if (generic != null) {
                            sb.append(generic.getTypeName()).append('$');
                            if (generic.getSort().isParameterized()) {
                                sb.append(typeDescription.getName().replace(generic.asErasure().getName() + "$", ""));
                                return;
                            } else {
                                sb.append(typeDescription.getSimpleName());
                                return;
                            }
                        }
                        sb.append(typeDescription.getName());
                    }
                };

                protected static final RenderingDelegate CURRENT;

                protected abstract void apply(StringBuilder sb, TypeDescription typeDescription, @MaybeNull Generic generic);

                static {
                    RenderingDelegate renderingDelegate = FOR_LEGACY_VM;
                    CURRENT = ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).isAtLeast(ClassFileVersion.JAVA_V8) ? FOR_JAVA_8_CAPABLE_VM : renderingDelegate;
                }
            }

            public static class ForLoadedType extends OfParameterizedType {
                private final AnnotationReader annotationReader;
                private final ParameterizedType parameterizedType;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                public ForLoadedType(ParameterizedType parameterizedType) {
                    this(parameterizedType, AnnotationReader.NoOp.INSTANCE);
                }

                protected ForLoadedType(ParameterizedType parameterizedType, AnnotationReader annotationReader) {
                    this.parameterizedType = parameterizedType;
                    this.annotationReader = annotationReader;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getTypeArguments() {
                    return new ParameterArgumentTypeList(this.parameterizedType.getActualTypeArguments(), this.annotationReader);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    Type ownerType = this.parameterizedType.getOwnerType();
                    if (ownerType == null) {
                        return Generic.UNDEFINED;
                    }
                    return TypeDefinition.Sort.describe(ownerType, this.annotationReader.ofOwnerType());
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of((Class) this.parameterizedType.getRawType());
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationReader.asList();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
                public boolean represents(Type type) {
                    return this.parameterizedType == type || super.represents(type);
                }

                protected static class ParameterArgumentTypeList extends TypeList.Generic.AbstractBase {
                    private final AnnotationReader annotationReader;
                    private final Type[] argumentType;

                    protected ParameterArgumentTypeList(Type[] typeArr, AnnotationReader annotationReader) {
                        this.argumentType = typeArr;
                        this.annotationReader = annotationReader;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public Generic get(int i) {
                        return TypeDefinition.Sort.describe(this.argumentType[i], this.annotationReader.ofTypeArgument(i));
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.argumentType.length;
                    }
                }
            }

            public static class Latent extends OfParameterizedType {
                private final AnnotationSource annotationSource;

                @MaybeNull
                private final Generic ownerType;
                private final List<? extends Generic> parameters;
                private final TypeDescription rawType;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                public Latent(TypeDescription typeDescription, @MaybeNull Generic generic, List<? extends Generic> list, AnnotationSource annotationSource) {
                    this.rawType = typeDescription;
                    this.ownerType = generic;
                    this.parameters = list;
                    this.annotationSource = annotationSource;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.rawType;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    return this.ownerType;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getTypeArguments() {
                    return new TypeList.Generic.Explicit(this.parameters);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }
            }

            public static class ForReifiedType extends OfParameterizedType {
                private final Generic parameterizedType;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                protected ForReifiedType(Generic generic) {
                    this.parameterizedType = generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getSuperClass() {
                    Generic superClass = super.getSuperClass();
                    return superClass == null ? Generic.UNDEFINED : new LazyProjection.WithResolvedErasure(superClass, Visitor.Reifying.INHERITING);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDefinition
                public TypeList.Generic getInterfaces() {
                    return new TypeList.Generic.ForDetachedTypes.WithResolvedErasure(super.getInterfaces(), Visitor.Reifying.INHERITING);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                    return new FieldList.TypeSubstituting(this, super.getDeclaredFields(), Visitor.TypeErasing.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                    return new MethodList.TypeSubstituting(this, super.getDeclaredMethods(), Visitor.TypeErasing.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getTypeArguments() {
                    return new TypeList.Generic.ForDetachedTypes(this.parameterizedType.getTypeArguments(), Visitor.TypeErasing.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    Generic ownerType = this.parameterizedType.getOwnerType();
                    if (ownerType == null) {
                        return Generic.UNDEFINED;
                    }
                    return (Generic) ownerType.accept(Visitor.Reifying.INHERITING);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.parameterizedType.asErasure();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return new AnnotationList.Empty();
                }
            }

            public static class ForGenerifiedErasure extends OfParameterizedType {
                private final TypeDescription typeDescription;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfParameterizedType, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                protected ForGenerifiedErasure(TypeDescription typeDescription) {
                    this.typeDescription = typeDescription;
                }

                public static Generic of(TypeDescription typeDescription) {
                    return typeDescription.isGenerified() ? new ForGenerifiedErasure(typeDescription) : new OfNonGenericType.ForErasure(typeDescription);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.typeDescription;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getTypeArguments() {
                    return new TypeList.Generic.ForDetachedTypes(this.typeDescription.getTypeVariables(), Visitor.AnnotationStripper.INSTANCE);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                @MaybeNull
                public Generic getOwnerType() {
                    TypeDescription declaringType = this.typeDescription.getDeclaringType();
                    if (declaringType == null) {
                        return Generic.UNDEFINED;
                    }
                    return of(declaringType);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return new AnnotationList.Empty();
                }
            }
        }

        public static abstract class OfTypeVariable extends AbstractBase {
            private transient /* synthetic */ int hashCode;

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDefinition.Sort getSort() {
                return TypeDefinition.Sort.VARIABLE;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDescription asErasure() {
                TypeList.Generic upperBounds = getUpperBounds();
                if (upperBounds.isEmpty()) {
                    return ForLoadedType.of(Object.class);
                }
                return ((Generic) upperBounds.get(0)).asErasure();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public Generic getSuperClass() {
                throw new IllegalStateException("A type variable does not imply a super type definition: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeList.Generic getInterfaces() {
                throw new IllegalStateException("A type variable does not imply an interface type definition: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                throw new IllegalStateException("A type variable does not imply field definitions: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                throw new IllegalStateException("A type variable does not imply method definitions: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                throw new IllegalStateException("A type variable does not imply record component definitions: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public Generic getComponentType() {
                throw new IllegalStateException("A type variable does not imply a component type: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getTypeArguments() {
                throw new IllegalStateException("A type variable does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic findBindingOf(Generic generic) {
                throw new IllegalStateException("A type variable does not imply type arguments: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getLowerBounds() {
                throw new IllegalStateException("A type variable does not imply lower bounds: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public Generic getOwnerType() {
                throw new IllegalStateException("A type variable does not imply an owner type: " + this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public String getTypeName() {
                return toString();
            }

            @Override // net.bytebuddy.description.NamedElement
            public String getActualName() {
                return getSymbol();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public <T> T accept(Visitor<T> visitor) {
                return visitor.onTypeVariable(this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                return StackSize.SINGLE;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
            public boolean represents(Type type) {
                return equals(TypeDefinition.Sort.describe(type));
            }

            @Override // java.lang.Iterable
            public Iterator<TypeDefinition> iterator() {
                throw new IllegalStateException("A type variable does not imply a super type definition: " + this);
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int iHashCode = this.hashCode != 0 ? 0 : getTypeVariableSource().hashCode() ^ getSymbol().hashCode();
                if (iHashCode == 0) {
                    return this.hashCode;
                }
                this.hashCode = iHashCode;
                return iHashCode;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Generic)) {
                    return false;
                }
                Generic generic = (Generic) obj;
                return generic.getSort().isTypeVariable() && getSymbol().equals(generic.getSymbol()) && getTypeVariableSource().equals(generic.getTypeVariableSource());
            }

            public String toString() {
                return getSymbol();
            }

            public static class Symbolic extends AbstractBase {
                private final AnnotationSource annotationSource;
                private final String symbol;

                @Override // net.bytebuddy.description.type.TypeDefinition
                public boolean isArray() {
                    return false;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public boolean isPrimitive() {
                    return false;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public boolean isRecord() {
                    return false;
                }

                public Symbolic(String str, AnnotationSource annotationSource) {
                    this.symbol = str;
                    this.annotationSource = annotationSource;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDefinition.Sort getSort() {
                    return TypeDefinition.Sort.VARIABLE_SYMBOLIC;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public String getSymbol() {
                    return this.symbol;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    throw new IllegalStateException("A symbolic type variable does not imply an erasure: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getUpperBounds() {
                    throw new IllegalStateException("A symbolic type variable does not imply an upper type bound: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeVariableSource getTypeVariableSource() {
                    throw new IllegalStateException("A symbolic type variable does not imply a variable source: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getSuperClass() {
                    throw new IllegalStateException("A symbolic type variable does not imply a super type definition: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeList.Generic getInterfaces() {
                    throw new IllegalStateException("A symbolic type variable does not imply an interface type definition: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                    throw new IllegalStateException("A symbolic type variable does not imply field definitions: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                    throw new IllegalStateException("A symbolic type variable does not imply method definitions: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
                public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                    throw new IllegalStateException("A symbolic type variable does not imply record component definitions: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public Generic getComponentType() {
                    throw new IllegalStateException("A symbolic type variable does not imply a component type: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getTypeArguments() {
                    throw new IllegalStateException("A symbolic type variable does not imply type arguments: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public Generic findBindingOf(Generic generic) {
                    throw new IllegalStateException("A symbolic type variable does not imply type arguments: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getLowerBounds() {
                    throw new IllegalStateException("A symbolic type variable does not imply lower bounds: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public Generic getOwnerType() {
                    throw new IllegalStateException("A symbolic type variable does not imply an owner type: " + this);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public String getTypeName() {
                    return toString();
                }

                @Override // net.bytebuddy.description.NamedElement
                public String getActualName() {
                    return getSymbol();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public <T> T accept(Visitor<T> visitor) {
                    return visitor.onTypeVariable(this);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public StackSize getStackSize() {
                    return StackSize.SINGLE;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
                public boolean represents(Type type) {
                    type.getClass();
                    return false;
                }

                @Override // java.lang.Iterable
                public Iterator<TypeDefinition> iterator() {
                    throw new IllegalStateException("A symbolic type variable does not imply a super type definition: " + this);
                }

                public int hashCode() {
                    return this.symbol.hashCode();
                }

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (!(obj instanceof Generic)) {
                        return false;
                    }
                    Generic generic = (Generic) obj;
                    return generic.getSort().isTypeVariable() && getSymbol().equals(generic.getSymbol());
                }

                public String toString() {
                    return getSymbol();
                }
            }

            public static class ForLoadedType extends OfTypeVariable {
                private final AnnotationReader annotationReader;
                private final TypeVariable<?> typeVariable;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfTypeVariable, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                public ForLoadedType(TypeVariable<?> typeVariable) {
                    this(typeVariable, AnnotationReader.NoOp.INSTANCE);
                }

                protected ForLoadedType(TypeVariable<?> typeVariable, AnnotationReader annotationReader) {
                    this.typeVariable = typeVariable;
                    this.annotationReader = annotationReader;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeVariableSource getTypeVariableSource() {
                    GenericDeclaration genericDeclaration = this.typeVariable.getGenericDeclaration();
                    if (genericDeclaration instanceof Class) {
                        return ForLoadedType.of((Class) genericDeclaration);
                    }
                    if (genericDeclaration instanceof Method) {
                        return new MethodDescription.ForLoadedMethod((Method) genericDeclaration);
                    }
                    if (genericDeclaration instanceof Constructor) {
                        return new MethodDescription.ForLoadedConstructor((Constructor) genericDeclaration);
                    }
                    throw new IllegalStateException("Unknown declaration: " + genericDeclaration);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getUpperBounds() {
                    return new TypeVariableBoundList(this.typeVariable.getBounds(), this.annotationReader);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public String getSymbol() {
                    return this.typeVariable.getName();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationReader.asList();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfTypeVariable, net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
                public boolean represents(Type type) {
                    return this.typeVariable == type || super.represents(type);
                }

                protected static class TypeVariableBoundList extends TypeList.Generic.AbstractBase {
                    private final AnnotationReader annotationReader;
                    private final Type[] bound;

                    protected TypeVariableBoundList(Type[] typeArr, AnnotationReader annotationReader) {
                        this.bound = typeArr;
                        this.annotationReader = annotationReader;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public Generic get(int i) {
                        return TypeDefinition.Sort.describe(this.bound[i], this.annotationReader.ofTypeVariableBoundType(i));
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.bound.length;
                    }
                }
            }

            public static class WithAnnotationOverlay extends OfTypeVariable {
                private final AnnotationSource annotationSource;
                private final Generic typeVariable;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.OfTypeVariable, net.bytebuddy.description.type.TypeDefinition
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                public WithAnnotationOverlay(Generic generic, AnnotationSource annotationSource) {
                    this.typeVariable = generic;
                    this.annotationSource = annotationSource;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeList.Generic getUpperBounds() {
                    return this.typeVariable.getUpperBounds();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public TypeVariableSource getTypeVariableSource() {
                    return this.typeVariable.getTypeVariableSource();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic
                public String getSymbol() {
                    return this.typeVariable.getSymbol();
                }
            }
        }

        public static abstract class LazyProjection extends AbstractBase {
            private transient /* synthetic */ int hashCode;

            protected abstract Generic resolve();

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeDefinition.Sort getSort() {
                return resolve().getSort();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InGenericShape> getDeclaredFields() {
                return resolve().getDeclaredFields();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InGenericShape> getDeclaredMethods() {
                return resolve().getDeclaredMethods();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InGenericShape> getRecordComponents() {
                return resolve().getRecordComponents();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getUpperBounds() {
                return resolve().getUpperBounds();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getLowerBounds() {
                return resolve().getLowerBounds();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public Generic getComponentType() {
                return resolve().getComponentType();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeList.Generic getTypeArguments() {
                return resolve().getTypeArguments();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            @MaybeNull
            public Generic findBindingOf(Generic generic) {
                return resolve().findBindingOf(generic);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public TypeVariableSource getTypeVariableSource() {
                return resolve().getTypeVariableSource();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            @MaybeNull
            public Generic getOwnerType() {
                return resolve().getOwnerType();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public String getTypeName() {
                return resolve().getTypeName();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public String getSymbol() {
                return resolve().getSymbol();
            }

            @Override // net.bytebuddy.description.NamedElement
            public String getActualName() {
                return resolve().getActualName();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic
            public <T> T accept(Visitor<T> visitor) {
                return (T) resolve().accept(visitor);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                return asErasure().getStackSize();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return asErasure().isArray();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return asErasure().isPrimitive();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return asErasure().isRecord();
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic.AbstractBase, net.bytebuddy.description.type.TypeDefinition
            public boolean represents(Type type) {
                return resolve().represents(type);
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int iHashCode = this.hashCode != 0 ? 0 : resolve().hashCode();
                if (iHashCode == 0) {
                    return this.hashCode;
                }
                this.hashCode = iHashCode;
                return iHashCode;
            }

            public boolean equals(@MaybeNull Object obj) {
                return this == obj || ((obj instanceof TypeDefinition) && resolve().equals(obj));
            }

            public String toString() {
                return resolve().toString();
            }

            public static abstract class WithLazyNavigation extends LazyProjection {
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection, net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getSuperClass() {
                    return LazySuperClass.of(this);
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeList.Generic getInterfaces() {
                    return LazyInterfaceList.of((LazyProjection) this);
                }

                @Override // java.lang.Iterable
                public Iterator<TypeDefinition> iterator() {
                    return new TypeDefinition.SuperClassIterator(this);
                }

                protected static class LazySuperClass extends WithLazyNavigation {
                    private final LazyProjection delegate;
                    private transient /* synthetic */ Generic resolved;

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithLazyNavigation, net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection, net.bytebuddy.description.type.TypeDefinition
                    @MaybeNull
                    public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                        return super.getComponentType();
                    }

                    protected LazySuperClass(LazyProjection lazyProjection) {
                        this.delegate = lazyProjection;
                    }

                    @MaybeNull
                    protected static Generic of(LazyProjection lazyProjection) {
                        return lazyProjection.asErasure().getSuperClass() == null ? Generic.UNDEFINED : new LazySuperClass(lazyProjection);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return resolve().getDeclaredAnnotations();
                    }

                    @Override // net.bytebuddy.description.type.TypeDefinition
                    public TypeDescription asErasure() {
                        return this.delegate.asErasure().getSuperClass().asErasure();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                    @CachedReturnPlugin.Enhance("resolved")
                    protected Generic resolve() {
                        Generic superClass = this.resolved != null ? null : this.delegate.resolve().getSuperClass();
                        if (superClass == null) {
                            return this.resolved;
                        }
                        this.resolved = superClass;
                        return superClass;
                    }
                }

                protected static class LazyInterfaceType extends WithLazyNavigation {
                    private final LazyProjection delegate;
                    private final int index;
                    private final Generic rawInterface;
                    private transient /* synthetic */ Generic resolved;

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithLazyNavigation, net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection, net.bytebuddy.description.type.TypeDefinition
                    @MaybeNull
                    public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                        return super.getComponentType();
                    }

                    protected LazyInterfaceType(LazyProjection lazyProjection, int i, Generic generic) {
                        this.delegate = lazyProjection;
                        this.index = i;
                        this.rawInterface = generic;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return resolve().getDeclaredAnnotations();
                    }

                    @Override // net.bytebuddy.description.type.TypeDefinition
                    public TypeDescription asErasure() {
                        return this.rawInterface.asErasure();
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                    @CachedReturnPlugin.Enhance("resolved")
                    protected Generic resolve() {
                        Generic generic = this.resolved != null ? null : (Generic) this.delegate.resolve().getInterfaces().get(this.index);
                        if (generic == null) {
                            return this.resolved;
                        }
                        this.resolved = generic;
                        return generic;
                    }
                }

                protected static class LazyInterfaceList extends TypeList.Generic.AbstractBase {
                    private final LazyProjection delegate;
                    private final TypeList.Generic rawInterfaces;

                    protected LazyInterfaceList(LazyProjection lazyProjection, TypeList.Generic generic) {
                        this.delegate = lazyProjection;
                        this.rawInterfaces = generic;
                    }

                    protected static TypeList.Generic of(LazyProjection lazyProjection) {
                        return new LazyInterfaceList(lazyProjection, lazyProjection.asErasure().getInterfaces());
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public Generic get(int i) {
                        return new LazyInterfaceType(this.delegate, i, (Generic) this.rawInterfaces.get(i));
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.rawInterfaces.size();
                    }
                }

                protected static abstract class OfAnnotatedElement extends WithLazyNavigation {
                    protected abstract AnnotationReader getAnnotationReader();

                    protected OfAnnotatedElement() {
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithLazyNavigation, net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection, net.bytebuddy.description.type.TypeDefinition
                    @MaybeNull
                    public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                        return super.getComponentType();
                    }

                    public AnnotationList getDeclaredAnnotations() {
                        return getAnnotationReader().asList();
                    }
                }
            }

            public static abstract class WithEagerNavigation extends LazyProjection {
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection, net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public Generic getSuperClass() {
                    return resolve().getSuperClass();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeList.Generic getInterfaces() {
                    return resolve().getInterfaces();
                }

                @Override // java.lang.Iterable
                public Iterator<TypeDefinition> iterator() {
                    return resolve().iterator();
                }

                protected static abstract class OfAnnotatedElement extends WithEagerNavigation {
                    protected abstract AnnotationReader getAnnotationReader();

                    protected OfAnnotatedElement() {
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation, net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection, net.bytebuddy.description.type.TypeDefinition
                    @MaybeNull
                    public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                        return super.getComponentType();
                    }

                    public AnnotationList getDeclaredAnnotations() {
                        return getAnnotationReader().asList();
                    }
                }
            }

            public static class ForLoadedSuperClass extends WithLazyNavigation.OfAnnotatedElement {
                private transient /* synthetic */ Generic resolved;
                private final Class<?> type;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithLazyNavigation.OfAnnotatedElement, net.bytebuddy.description.annotation.AnnotationSource
                public /* bridge */ /* synthetic */ AnnotationList getDeclaredAnnotations() {
                    return super.getDeclaredAnnotations();
                }

                protected ForLoadedSuperClass(Class<?> cls) {
                    this.type = cls;
                }

                @MaybeNull
                public static Generic of(Class<?> cls) {
                    return cls.getSuperclass() == null ? Generic.UNDEFINED : new ForLoadedSuperClass(cls);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected Generic resolve() {
                    Generic genericDescribe = this.resolved != null ? null : TypeDefinition.Sort.describe(this.type.getGenericSuperclass(), getAnnotationReader());
                    if (genericDescribe == null) {
                        return this.resolved;
                    }
                    this.resolved = genericDescribe;
                    return genericDescribe;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(this.type.getSuperclass());
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithLazyNavigation.OfAnnotatedElement
                protected AnnotationReader getAnnotationReader() {
                    return new AnnotationReader.Delegator.ForLoadedSuperClass(this.type);
                }
            }

            public static class ForLoadedFieldType extends WithEagerNavigation.OfAnnotatedElement {
                private final Field field;
                private transient /* synthetic */ Generic resolved;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement, net.bytebuddy.description.annotation.AnnotationSource
                public /* bridge */ /* synthetic */ AnnotationList getDeclaredAnnotations() {
                    return super.getDeclaredAnnotations();
                }

                public ForLoadedFieldType(Field field) {
                    this.field = field;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected Generic resolve() {
                    Generic genericDescribe = this.resolved != null ? null : TypeDefinition.Sort.describe(this.field.getGenericType(), getAnnotationReader());
                    if (genericDescribe == null) {
                        return this.resolved;
                    }
                    this.resolved = genericDescribe;
                    return genericDescribe;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(this.field.getType());
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement
                protected AnnotationReader getAnnotationReader() {
                    return new AnnotationReader.Delegator.ForLoadedField(this.field);
                }
            }

            public static class ForLoadedReturnType extends WithEagerNavigation.OfAnnotatedElement {
                private final Method method;
                private transient /* synthetic */ Generic resolved;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement, net.bytebuddy.description.annotation.AnnotationSource
                public /* bridge */ /* synthetic */ AnnotationList getDeclaredAnnotations() {
                    return super.getDeclaredAnnotations();
                }

                public ForLoadedReturnType(Method method) {
                    this.method = method;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected Generic resolve() {
                    Generic genericDescribe = this.resolved != null ? null : TypeDefinition.Sort.describe(this.method.getGenericReturnType(), getAnnotationReader());
                    if (genericDescribe == null) {
                        return this.resolved;
                    }
                    this.resolved = genericDescribe;
                    return genericDescribe;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(this.method.getReturnType());
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement
                protected AnnotationReader getAnnotationReader() {
                    return new AnnotationReader.Delegator.ForLoadedMethodReturnType(this.method);
                }
            }

            public static class OfConstructorParameter extends WithEagerNavigation.OfAnnotatedElement {
                private final Constructor<?> constructor;
                private transient /* synthetic */ Generic delegate;
                private final Class<?>[] erasure;
                private final int index;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement, net.bytebuddy.description.annotation.AnnotationSource
                public /* bridge */ /* synthetic */ AnnotationList getDeclaredAnnotations() {
                    return super.getDeclaredAnnotations();
                }

                public OfConstructorParameter(Constructor<?> constructor, int i, Class<?>[] clsArr) {
                    this.constructor = constructor;
                    this.index = i;
                    this.erasure = clsArr;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance(MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX)
                protected Generic resolve() {
                    Generic genericOf;
                    if (this.delegate != null) {
                        genericOf = null;
                    } else {
                        Type[] genericParameterTypes = this.constructor.getGenericParameterTypes();
                        Class<?>[] clsArr = this.erasure;
                        if (clsArr.length == genericParameterTypes.length) {
                            genericOf = TypeDefinition.Sort.describe(genericParameterTypes[this.index], getAnnotationReader());
                        } else {
                            genericOf = OfNonGenericType.ForLoadedType.of(clsArr[this.index]);
                        }
                    }
                    if (genericOf == null) {
                        return this.delegate;
                    }
                    this.delegate = genericOf;
                    return genericOf;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(this.erasure[this.index]);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement
                protected AnnotationReader getAnnotationReader() {
                    return new AnnotationReader.Delegator.ForLoadedExecutableParameterType(this.constructor, this.index);
                }
            }

            public static class OfMethodParameter extends WithEagerNavigation.OfAnnotatedElement {
                private final Class<?>[] erasure;
                private final int index;
                private final Method method;
                private transient /* synthetic */ Generic resolved;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement, net.bytebuddy.description.annotation.AnnotationSource
                public /* bridge */ /* synthetic */ AnnotationList getDeclaredAnnotations() {
                    return super.getDeclaredAnnotations();
                }

                public OfMethodParameter(Method method, int i, Class<?>[] clsArr) {
                    this.method = method;
                    this.index = i;
                    this.erasure = clsArr;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected Generic resolve() {
                    Generic genericOf;
                    if (this.resolved != null) {
                        genericOf = null;
                    } else {
                        Type[] genericParameterTypes = this.method.getGenericParameterTypes();
                        Class<?>[] clsArr = this.erasure;
                        if (clsArr.length == genericParameterTypes.length) {
                            genericOf = TypeDefinition.Sort.describe(genericParameterTypes[this.index], getAnnotationReader());
                        } else {
                            genericOf = OfNonGenericType.ForLoadedType.of(clsArr[this.index]);
                        }
                    }
                    if (genericOf == null) {
                        return this.resolved;
                    }
                    this.resolved = genericOf;
                    return genericOf;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(this.erasure[this.index]);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement
                protected AnnotationReader getAnnotationReader() {
                    return new AnnotationReader.Delegator.ForLoadedExecutableParameterType(this.method, this.index);
                }
            }

            public static class OfRecordComponent extends WithEagerNavigation.OfAnnotatedElement {
                private final Object recordComponent;
                private transient /* synthetic */ Generic resolved;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement, net.bytebuddy.description.annotation.AnnotationSource
                public /* bridge */ /* synthetic */ AnnotationList getDeclaredAnnotations() {
                    return super.getDeclaredAnnotations();
                }

                protected OfRecordComponent(Object obj) {
                    this.recordComponent = obj;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected Generic resolve() {
                    Generic genericDescribe = this.resolved != null ? null : TypeDefinition.Sort.describe(RecordComponentDescription.ForLoadedRecordComponent.RECORD_COMPONENT.getGenericType(this.recordComponent), getAnnotationReader());
                    if (genericDescribe == null) {
                        return this.resolved;
                    }
                    this.resolved = genericDescribe;
                    return genericDescribe;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return ForLoadedType.of(RecordComponentDescription.ForLoadedRecordComponent.RECORD_COMPONENT.getType(this.recordComponent));
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection.WithEagerNavigation.OfAnnotatedElement
                protected AnnotationReader getAnnotationReader() {
                    return new AnnotationReader.Delegator.ForLoadedRecordComponent(this.recordComponent);
                }
            }

            public static class WithResolvedErasure extends WithEagerNavigation {
                private final AnnotationSource annotationSource;
                private final Generic delegate;
                private transient /* synthetic */ Generic resolved;
                private final Visitor<? extends Generic> visitor;

                public WithResolvedErasure(Generic generic, Visitor<? extends Generic> visitor) {
                    this(generic, visitor, generic);
                }

                public WithResolvedErasure(Generic generic, Visitor<? extends Generic> visitor, AnnotationSource annotationSource) {
                    this.delegate = generic;
                    this.visitor = visitor;
                    this.annotationSource = annotationSource;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return this.annotationSource.getDeclaredAnnotations();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeDescription asErasure() {
                    return this.delegate.asErasure();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected Generic resolve() {
                    Generic generic = this.resolved != null ? null : (Generic) this.delegate.accept(this.visitor);
                    if (generic == null) {
                        return this.resolved;
                    }
                    this.resolved = generic;
                    return generic;
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static abstract class Builder {

            @AlwaysNull
            private static final Type UNDEFINED = null;
            protected final List<? extends AnnotationDescription> annotations;

            protected abstract Builder doAnnotate(List<? extends AnnotationDescription> list);

            protected abstract Generic doBuild();

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.annotations.equals(((Builder) obj).annotations);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.annotations.hashCode();
            }

            protected Builder(List<? extends AnnotationDescription> list) {
                this.annotations = list;
            }

            public static Builder of(Type type) {
                return of(TypeDefinition.Sort.describe(type));
            }

            public static Builder of(Generic generic) {
                return (Builder) generic.accept(Visitor.INSTANCE);
            }

            public static Builder rawType(Class<?> cls) {
                return rawType(ForLoadedType.of(cls));
            }

            public static Builder rawType(TypeDescription typeDescription) {
                return new OfNonGenericType(typeDescription);
            }

            public static Builder rawType(Class<?> cls, @MaybeNull Generic generic) {
                return rawType(ForLoadedType.of(cls), generic);
            }

            public static Builder rawType(TypeDescription typeDescription, @MaybeNull Generic generic) {
                TypeDescription declaringType = typeDescription.getDeclaringType();
                if (declaringType == null && generic != null) {
                    throw new IllegalArgumentException(typeDescription + " does not have a declaring type: " + generic);
                }
                if (declaringType != null && (generic == null || !declaringType.equals(generic.asErasure()))) {
                    throw new IllegalArgumentException(generic + " is not the declaring type of " + typeDescription);
                }
                return new OfNonGenericType(typeDescription, generic);
            }

            public static Generic unboundWildcard() {
                return unboundWildcard(Collections.emptySet());
            }

            public static Generic unboundWildcard(Annotation... annotationArr) {
                return unboundWildcard((List<? extends Annotation>) Arrays.asList(annotationArr));
            }

            public static Generic unboundWildcard(List<? extends Annotation> list) {
                return unboundWildcard((Collection<? extends AnnotationDescription>) new AnnotationList.ForLoadedAnnotations(list));
            }

            public static Generic unboundWildcard(AnnotationDescription... annotationDescriptionArr) {
                return unboundWildcard((Collection<? extends AnnotationDescription>) Arrays.asList(annotationDescriptionArr));
            }

            public static Generic unboundWildcard(Collection<? extends AnnotationDescription> collection) {
                return OfWildcardType.Latent.unbounded(new AnnotationSource.Explicit(new ArrayList(collection)));
            }

            public static Builder typeVariable(String str) {
                return new OfTypeVariable(str);
            }

            public static Builder parameterizedType(Class<?> cls, Type... typeArr) {
                return parameterizedType(cls, (List<? extends Type>) Arrays.asList(typeArr));
            }

            public static Builder parameterizedType(Class<?> cls, List<? extends Type> list) {
                return parameterizedType(cls, UNDEFINED, list);
            }

            public static Builder parameterizedType(Class<?> cls, @MaybeNull Type type, List<? extends Type> list) {
                return parameterizedType(ForLoadedType.of(cls), type == null ? null : TypeDefinition.Sort.describe(type), new TypeList.Generic.ForLoadedTypes(list));
            }

            public static Builder parameterizedType(TypeDescription typeDescription, TypeDefinition... typeDefinitionArr) {
                return parameterizedType(typeDescription, Arrays.asList(typeDefinitionArr));
            }

            public static Builder parameterizedType(TypeDescription typeDescription, Collection<? extends TypeDefinition> collection) {
                return parameterizedType(typeDescription, Generic.UNDEFINED, collection);
            }

            public static Builder parameterizedType(TypeDescription typeDescription, @MaybeNull Generic generic, Collection<? extends TypeDefinition> collection) {
                TypeDescription declaringType = typeDescription.getDeclaringType();
                if (generic == null && declaringType != null && typeDescription.isStatic()) {
                    generic = declaringType.asGenericType();
                }
                if (!typeDescription.represents(TargetType.class)) {
                    if (!typeDescription.isGenerified()) {
                        throw new IllegalArgumentException(typeDescription + " is not a parameterized type");
                    }
                    if (generic == null && declaringType != null && !typeDescription.isStatic()) {
                        throw new IllegalArgumentException(typeDescription + " requires an owner type");
                    }
                    if (generic != null && !generic.asErasure().equals(declaringType)) {
                        throw new IllegalArgumentException(generic + " does not represent required owner for " + typeDescription);
                    }
                    if (generic != null && (typeDescription.isStatic() ^ generic.getSort().isNonGeneric())) {
                        throw new IllegalArgumentException(generic + " does not define the correct parameters for owning " + typeDescription);
                    }
                    if (typeDescription.getTypeVariables().size() != collection.size()) {
                        throw new IllegalArgumentException(collection + " does not contain number of required parameters for " + typeDescription);
                    }
                }
                return new OfParameterizedType(typeDescription, generic, new TypeList.Generic.Explicit(new ArrayList(collection)));
            }

            public Generic asWildcardUpperBound() {
                return asWildcardUpperBound(Collections.emptySet());
            }

            public Generic asWildcardUpperBound(Annotation... annotationArr) {
                return asWildcardUpperBound(Arrays.asList(annotationArr));
            }

            public Generic asWildcardUpperBound(List<? extends Annotation> list) {
                return asWildcardUpperBound((Collection<? extends AnnotationDescription>) new AnnotationList.ForLoadedAnnotations(list));
            }

            public Generic asWildcardUpperBound(AnnotationDescription... annotationDescriptionArr) {
                return asWildcardUpperBound((Collection<? extends AnnotationDescription>) Arrays.asList(annotationDescriptionArr));
            }

            public Generic asWildcardUpperBound(Collection<? extends AnnotationDescription> collection) {
                return OfWildcardType.Latent.boundedAbove(build(), new AnnotationSource.Explicit(new ArrayList(collection)));
            }

            public Generic asWildcardLowerBound() {
                return asWildcardLowerBound(Collections.emptySet());
            }

            public Generic asWildcardLowerBound(Annotation... annotationArr) {
                return asWildcardLowerBound(Arrays.asList(annotationArr));
            }

            public Generic asWildcardLowerBound(List<? extends Annotation> list) {
                return asWildcardLowerBound((Collection<? extends AnnotationDescription>) new AnnotationList.ForLoadedAnnotations(list));
            }

            public Generic asWildcardLowerBound(AnnotationDescription... annotationDescriptionArr) {
                return asWildcardLowerBound((Collection<? extends AnnotationDescription>) Arrays.asList(annotationDescriptionArr));
            }

            public Generic asWildcardLowerBound(Collection<? extends AnnotationDescription> collection) {
                return OfWildcardType.Latent.boundedBelow(build(), new AnnotationSource.Explicit(new ArrayList(collection)));
            }

            public Builder asArray() {
                return asArray(1);
            }

            public Builder asArray(int i) {
                if (i < 1) {
                    throw new IllegalArgumentException("Cannot define an array of a non-positive arity: " + i);
                }
                Generic genericBuild = build();
                while (true) {
                    i--;
                    if (i > 0) {
                        genericBuild = new OfGenericArray.Latent(genericBuild, AnnotationSource.Empty.INSTANCE);
                    } else {
                        return new OfGenericArrayType(genericBuild);
                    }
                }
            }

            public Builder annotate(Annotation... annotationArr) {
                return annotate(Arrays.asList(annotationArr));
            }

            public Builder annotate(List<? extends Annotation> list) {
                return annotate((Collection<? extends AnnotationDescription>) new AnnotationList.ForLoadedAnnotations(list));
            }

            public Builder annotate(AnnotationDescription... annotationDescriptionArr) {
                return annotate((Collection<? extends AnnotationDescription>) Arrays.asList(annotationDescriptionArr));
            }

            public Builder annotate(Collection<? extends AnnotationDescription> collection) {
                return doAnnotate(new ArrayList(collection));
            }

            public Generic build() {
                return doBuild();
            }

            public Generic build(Annotation... annotationArr) {
                return build(Arrays.asList(annotationArr));
            }

            public Generic build(List<? extends Annotation> list) {
                return build((Collection<? extends AnnotationDescription>) new AnnotationList.ForLoadedAnnotations(list));
            }

            public Generic build(AnnotationDescription... annotationDescriptionArr) {
                return build((Collection<? extends AnnotationDescription>) Arrays.asList(annotationDescriptionArr));
            }

            public Generic build(Collection<? extends AnnotationDescription> collection) {
                return doAnnotate(new ArrayList(collection)).doBuild();
            }

            protected enum Visitor implements Visitor<Builder> {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Builder onGenericArray(Generic generic) {
                    return new OfGenericArrayType(generic.getComponentType(), generic.getDeclaredAnnotations());
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Builder onWildcard(Generic generic) {
                    throw new IllegalArgumentException("Cannot resolve wildcard type " + generic + " to builder");
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Builder onParameterizedType(Generic generic) {
                    return new OfParameterizedType(generic.asErasure(), generic.getOwnerType(), generic.getTypeArguments(), generic.getDeclaredAnnotations());
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Builder onTypeVariable(Generic generic) {
                    return new OfTypeVariable(generic.getSymbol(), generic.getDeclaredAnnotations());
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
                public Builder onNonGenericType(Generic generic) {
                    if (generic.isArray()) {
                        return ((Builder) generic.getComponentType().accept(this)).asArray().annotate((Collection<? extends AnnotationDescription>) generic.getDeclaredAnnotations());
                    }
                    return new OfNonGenericType(generic.asErasure(), generic.getOwnerType(), generic.getDeclaredAnnotations());
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class OfNonGenericType extends Builder {

                @MaybeNull
                @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                private final Generic ownerType;
                private final TypeDescription typeDescription;

                /* JADX WARN: Code duplicated, block: B:24:0x0039 A[RETURN] */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
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
                    OfNonGenericType ofNonGenericType = (OfNonGenericType) obj;
                    if (!this.typeDescription.equals(ofNonGenericType.typeDescription)) {
                        return false;
                    }
                    Generic generic = this.ownerType;
                    Generic generic2 = ofNonGenericType.ownerType;
                    if (generic2 == null) {
                        if (generic != null) {
                            return false;
                        }
                        return true;
                    }
                    if (generic == null || !generic.equals(generic2)) {
                        return false;
                    }
                    return true;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                public int hashCode() {
                    int iHashCode = ((super.hashCode() * 31) + this.typeDescription.hashCode()) * 31;
                    Generic generic = this.ownerType;
                    return generic != null ? iHashCode + generic.hashCode() : iHashCode;
                }

                protected OfNonGenericType(TypeDescription typeDescription) {
                    this(typeDescription, typeDescription.getDeclaringType());
                }

                /* JADX WARN: Illegal instructions before constructor call */
                protected OfNonGenericType(TypeDescription typeDescription, @MaybeNull TypeDescription typeDescription2) {
                    Generic genericAsGenericType;
                    if (typeDescription2 == null) {
                        genericAsGenericType = Generic.UNDEFINED;
                    } else {
                        genericAsGenericType = typeDescription2.asGenericType();
                    }
                    this(typeDescription, genericAsGenericType);
                }

                protected OfNonGenericType(TypeDescription typeDescription, @MaybeNull Generic generic) {
                    this(typeDescription, generic, Collections.emptyList());
                }

                protected OfNonGenericType(TypeDescription typeDescription, @MaybeNull Generic generic, List<? extends AnnotationDescription> list) {
                    super(list);
                    this.ownerType = generic;
                    this.typeDescription = typeDescription;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Builder doAnnotate(List<? extends AnnotationDescription> list) {
                    return new OfNonGenericType(this.typeDescription, this.ownerType, CompoundList.of((List) this.annotations, (List) list));
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Generic doBuild() {
                    if (this.typeDescription.represents(Void.TYPE) && !this.annotations.isEmpty()) {
                        throw new IllegalArgumentException("The void non-type cannot be annotated");
                    }
                    return new OfNonGenericType.Latent(this.typeDescription, this.ownerType, new AnnotationSource.Explicit(this.annotations));
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class OfParameterizedType extends Builder {

                @MaybeNull
                @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                private final Generic ownerType;
                private final List<? extends Generic> parameterTypes;
                private final TypeDescription rawType;

                /* JADX WARN: Code restructure failed: missing block: B:22:0x0036, code lost:
                
                    if (r2 != null) goto L23;
                 */
                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                    /*
                        r4 = this;
                        boolean r0 = super.equals(r5)
                        r1 = 0
                        if (r0 != 0) goto L8
                        return r1
                    L8:
                        r0 = 1
                        if (r4 != r5) goto Lc
                        return r0
                    Lc:
                        if (r5 != 0) goto Lf
                        return r1
                    Lf:
                        java.lang.Class r2 = r4.getClass()
                        java.lang.Class r3 = r5.getClass()
                        if (r2 == r3) goto L1a
                        return r1
                    L1a:
                        net.bytebuddy.description.type.TypeDescription r2 = r4.rawType
                        net.bytebuddy.description.type.TypeDescription$Generic$Builder$OfParameterizedType r5 = (net.bytebuddy.description.type.TypeDescription.Generic.Builder.OfParameterizedType) r5
                        net.bytebuddy.description.type.TypeDescription r3 = r5.rawType
                        boolean r2 = r2.equals(r3)
                        if (r2 != 0) goto L27
                        return r1
                    L27:
                        net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.ownerType
                        net.bytebuddy.description.type.TypeDescription$Generic r3 = r5.ownerType
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
                        java.util.List<? extends net.bytebuddy.description.type.TypeDescription$Generic> r2 = r4.parameterTypes
                        java.util.List<? extends net.bytebuddy.description.type.TypeDescription$Generic> r5 = r5.parameterTypes
                        boolean r5 = r2.equals(r5)
                        if (r5 != 0) goto L44
                        return r1
                    L44:
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.description.type.TypeDescription.Generic.Builder.OfParameterizedType.equals(java.lang.Object):boolean");
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                public int hashCode() {
                    int iHashCode = ((super.hashCode() * 31) + this.rawType.hashCode()) * 31;
                    Generic generic = this.ownerType;
                    if (generic != null) {
                        iHashCode += generic.hashCode();
                    }
                    return (iHashCode * 31) + this.parameterTypes.hashCode();
                }

                protected OfParameterizedType(TypeDescription typeDescription, @MaybeNull Generic generic, List<? extends Generic> list) {
                    this(typeDescription, generic, list, Collections.emptyList());
                }

                protected OfParameterizedType(TypeDescription typeDescription, @MaybeNull Generic generic, List<? extends Generic> list, List<? extends AnnotationDescription> list2) {
                    super(list2);
                    this.rawType = typeDescription;
                    this.ownerType = generic;
                    this.parameterTypes = list;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Builder doAnnotate(List<? extends AnnotationDescription> list) {
                    return new OfParameterizedType(this.rawType, this.ownerType, this.parameterTypes, CompoundList.of((List) this.annotations, (List) list));
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Generic doBuild() {
                    return new OfParameterizedType.Latent(this.rawType, this.ownerType, this.parameterTypes, new AnnotationSource.Explicit(this.annotations));
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class OfGenericArrayType extends Builder {
                private final Generic componentType;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.componentType.equals(((OfGenericArrayType) obj).componentType);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                public int hashCode() {
                    return (super.hashCode() * 31) + this.componentType.hashCode();
                }

                protected OfGenericArrayType(Generic generic) {
                    this(generic, Collections.emptyList());
                }

                protected OfGenericArrayType(Generic generic, List<? extends AnnotationDescription> list) {
                    super(list);
                    this.componentType = generic;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Builder doAnnotate(List<? extends AnnotationDescription> list) {
                    return new OfGenericArrayType(this.componentType, CompoundList.of((List) this.annotations, (List) list));
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Generic doBuild() {
                    return new OfGenericArray.Latent(this.componentType, new AnnotationSource.Explicit(this.annotations));
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class OfTypeVariable extends Builder {
                private final String symbol;

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.symbol.equals(((OfTypeVariable) obj).symbol);
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                public int hashCode() {
                    return (super.hashCode() * 31) + this.symbol.hashCode();
                }

                protected OfTypeVariable(String str) {
                    this(str, Collections.emptyList());
                }

                protected OfTypeVariable(String str, List<? extends AnnotationDescription> list) {
                    super(list);
                    this.symbol = str;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Builder doAnnotate(List<? extends AnnotationDescription> list) {
                    return new OfTypeVariable(this.symbol, CompoundList.of((List) this.annotations, (List) list));
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.Builder
                protected Generic doBuild() {
                    return new OfTypeVariable.Symbolic(this.symbol, new AnnotationSource.Explicit(this.annotations));
                }
            }
        }
    }

    public static abstract class AbstractBase extends TypeVariableSource.AbstractBase implements TypeDescription {
        private static final boolean ACCESS_CONTROLLER;
        public static final boolean RAW_TYPES;
        private transient /* synthetic */ int hashCode;

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeDescription asErasure() {
            return this;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public ClassFileVersion getClassFileVersion() {
            return null;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public boolean isInferrable() {
            return false;
        }

        static {
            boolean z = false;
            try {
                Class.forName("java.security.AccessController", false, null);
                ACCESS_CONTROLLER = Boolean.parseBoolean(System.getProperty("net.bytebuddy.securitymanager", "true"));
            } catch (ClassNotFoundException unused) {
                ACCESS_CONTROLLER = false;
            } catch (SecurityException unused2) {
                ACCESS_CONTROLLER = true;
            }
            try {
                z = Boolean.parseBoolean((String) doPrivileged(new GetSystemPropertyAction(TypeDefinition.RAW_TYPES_PROPERTY)));
            } catch (Exception unused3) {
            }
            RAW_TYPES = z;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        private static boolean isAssignable(TypeDescription typeDescription, TypeDescription typeDescription2) {
            if (typeDescription.equals(typeDescription2)) {
                return true;
            }
            if (typeDescription2.isArray()) {
                if (typeDescription.isArray()) {
                    return isAssignable(typeDescription.getComponentType(), typeDescription2.getComponentType());
                }
                return typeDescription.represents(Object.class) || ARRAY_INTERFACES.contains(typeDescription.asGenericType());
            }
            if (typeDescription.represents(Object.class)) {
                return !typeDescription2.isPrimitive();
            }
            Generic superClass = typeDescription2.getSuperClass();
            if (superClass != null && typeDescription.isAssignableFrom(superClass.asErasure())) {
                return true;
            }
            if (typeDescription.isInterface()) {
                Iterator it = typeDescription2.getInterfaces().asErasures().iterator();
                while (it.hasNext()) {
                    if (typeDescription.isAssignableFrom((TypeDescription) it.next())) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableFrom(Class<?> cls) {
            return isAssignableFrom(ForLoadedType.of(cls));
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableFrom(TypeDescription typeDescription) {
            return isAssignable(this, typeDescription);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableTo(Class<?> cls) {
            return isAssignableTo(ForLoadedType.of(cls));
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableTo(TypeDescription typeDescription) {
            return isAssignable(typeDescription, this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isInHierarchyWith(Class<?> cls) {
            return isAssignableTo(cls) || isAssignableFrom(cls);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isInHierarchyWith(TypeDescription typeDescription) {
            return isAssignableTo(typeDescription) || isAssignableFrom(typeDescription);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public Generic asGenericType() {
            return new Generic.OfNonGenericType.ForErasure(this);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeDefinition.Sort getSort() {
            return TypeDefinition.Sort.NON_GENERIC;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isInstance(Object obj) {
            return isAssignableFrom(obj.getClass());
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnnotationValue(Object obj) {
            if ((represents(Class.class) && (obj instanceof TypeDescription)) || (((obj instanceof AnnotationDescription) && ((AnnotationDescription) obj).getAnnotationType().equals(this)) || (((obj instanceof EnumerationDescription) && ((EnumerationDescription) obj).getEnumerationType().equals(this)) || ((represents(String.class) && (obj instanceof String)) || ((represents(Boolean.TYPE) && (obj instanceof Boolean)) || ((represents(Byte.TYPE) && (obj instanceof Byte)) || ((represents(Short.TYPE) && (obj instanceof Short)) || ((represents(Character.TYPE) && (obj instanceof Character)) || ((represents(Integer.TYPE) && (obj instanceof Integer)) || ((represents(Long.TYPE) && (obj instanceof Long)) || ((represents(Float.TYPE) && (obj instanceof Float)) || ((represents(Double.TYPE) && (obj instanceof Double)) || ((represents(String[].class) && (obj instanceof String[])) || ((represents(boolean[].class) && (obj instanceof boolean[])) || ((represents(byte[].class) && (obj instanceof byte[])) || ((represents(short[].class) && (obj instanceof short[])) || ((represents(char[].class) && (obj instanceof char[])) || ((represents(int[].class) && (obj instanceof int[])) || ((represents(long[].class) && (obj instanceof long[])) || ((represents(float[].class) && (obj instanceof float[])) || ((represents(double[].class) && (obj instanceof double[])) || (represents(Class[].class) && (obj instanceof TypeDescription[]))))))))))))))))))))))) {
                return true;
            }
            if (isAssignableTo(Annotation[].class) && (obj instanceof AnnotationDescription[])) {
                for (AnnotationDescription annotationDescription : (AnnotationDescription[]) obj) {
                    if (!annotationDescription.getAnnotationType().equals(getComponentType())) {
                        return false;
                    }
                }
                return true;
            }
            if (!isAssignableTo(Enum[].class) || !(obj instanceof EnumerationDescription[])) {
                return false;
            }
            for (EnumerationDescription enumerationDescription : (EnumerationDescription[]) obj) {
                if (!enumerationDescription.getEnumerationType().equals(getComponentType())) {
                    return false;
                }
            }
            return true;
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return getName().replace('.', '/');
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public int getActualModifiers(boolean z) {
            int modifiers = getModifiers() | (getDeclaredAnnotations().isAnnotationPresent(Deprecated.class) ? 131072 : 0) | (isRecord() ? 65536 : 0) | (z ? 32 : 0);
            if (isPrivate()) {
                return modifiers & (-11);
            }
            return isProtected() ? (modifiers & (-13)) | 1 : modifiers & (-9);
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        @MaybeNull
        public String getGenericSignature() {
            SignatureVisitor signatureVisitorVisitClassBound;
            try {
                SignatureWriter signatureWriter = new SignatureWriter();
                boolean z = false;
                for (Generic generic : getTypeVariables()) {
                    signatureWriter.visitFormalTypeParameter(generic.getSymbol());
                    for (Generic generic2 : generic.getUpperBounds()) {
                        if (generic2.asErasure().isInterface()) {
                            signatureVisitorVisitClassBound = signatureWriter.visitInterfaceBound();
                        } else {
                            signatureVisitorVisitClassBound = signatureWriter.visitClassBound();
                        }
                        generic2.accept(new Generic.Visitor.ForSignatureVisitor(signatureVisitorVisitClassBound));
                    }
                    z = true;
                }
                Generic superClass = getSuperClass();
                if (superClass == null) {
                    superClass = Generic.OfNonGenericType.ForLoadedType.of(Object.class);
                }
                superClass.accept(new Generic.Visitor.ForSignatureVisitor(signatureWriter.visitSuperclass()));
                boolean z2 = z || !superClass.getSort().isNonGeneric();
                for (Generic generic3 : getInterfaces()) {
                    generic3.accept(new Generic.Visitor.ForSignatureVisitor(signatureWriter.visitInterface()));
                    z2 = z2 || !generic3.getSort().isNonGeneric();
                }
                return z2 ? signatureWriter.toString() : NON_GENERIC_SIGNATURE;
            } catch (GenericSignatureFormatError unused) {
                return NON_GENERIC_SIGNATURE;
            }
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isSamePackage(TypeDescription typeDescription) {
            PackageDescription packageDescription = getPackage();
            PackageDescription packageDescription2 = typeDescription.getPackage();
            if (packageDescription == null || packageDescription2 == null) {
                return packageDescription == packageDescription2;
            }
            return packageDescription.equals(packageDescription2);
        }

        @Override // net.bytebuddy.description.ByteCodeElement
        public boolean isVisibleTo(TypeDescription typeDescription) {
            return isPrimitive() || (!isArray() ? !(isPublic() || isProtected() || isSamePackage(typeDescription)) : !getComponentType().isVisibleTo(typeDescription));
        }

        @Override // net.bytebuddy.description.ByteCodeElement
        public boolean isAccessibleTo(TypeDescription typeDescription) {
            return isPrimitive() || (!isArray() ? !(isPublic() || isSamePackage(typeDescription)) : !getComponentType().isVisibleTo(typeDescription));
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public AnnotationList getInheritedAnnotations() {
            Generic superClass = getSuperClass();
            AnnotationList declaredAnnotations = getDeclaredAnnotations();
            if (superClass == null) {
                return declaredAnnotations;
            }
            HashSet hashSet = new HashSet();
            Iterator it = declaredAnnotations.iterator();
            while (it.hasNext()) {
                hashSet.add(((AnnotationDescription) it.next()).getAnnotationType());
            }
            return new AnnotationList.Explicit((List<? extends AnnotationDescription>) CompoundList.of((List) declaredAnnotations, (List) superClass.asErasure().getInheritedAnnotations().inherited(hashSet)));
        }

        @Override // net.bytebuddy.description.NamedElement
        public String getActualName() {
            if (isArray()) {
                TypeDescription componentType = this;
                int i = 0;
                do {
                    i++;
                    componentType = componentType.getComponentType();
                } while (componentType.isArray());
                StringBuilder sb = new StringBuilder();
                sb.append(componentType.getActualName());
                for (int i2 = 0; i2 < i; i2++) {
                    sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                }
                return sb.toString();
            }
            return getName();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public String getLongSimpleName() {
            TypeDescription declaringType = getDeclaringType();
            if (declaringType == null) {
                return getSimpleName();
            }
            return declaringType.getLongSimpleName() + "." + getSimpleName();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isPrimitiveWrapper() {
            return represents(Boolean.class) || represents(Byte.class) || represents(Short.class) || represents(Character.class) || represents(Integer.class) || represents(Long.class) || represents(Float.class) || represents(Double.class);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnnotationReturnType() {
            return isPrimitive() || represents(String.class) || (isAssignableTo(Enum.class) && !represents(Enum.class)) || ((isAssignableTo(Annotation.class) && !represents(Annotation.class)) || represents(Class.class) || (isArray() && !getComponentType().isArray() && getComponentType().isAnnotationReturnType()));
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnnotationValue() {
            return isPrimitive() || represents(String.class) || isAssignableTo(TypeDescription.class) || isAssignableTo(AnnotationDescription.class) || isAssignableTo(EnumerationDescription.class) || (isArray() && !getComponentType().isArray() && getComponentType().isAnnotationValue());
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean represents(Type type) {
            return equals(TypeDefinition.Sort.describe(type));
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public String getTypeName() {
            return getName();
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        @MaybeNull
        public TypeVariableSource getEnclosingSource() {
            MethodDescription.InDefinedShape enclosingMethod = getEnclosingMethod();
            if (enclosingMethod == null) {
                return isStatic() ? TypeVariableSource.UNDEFINED : getEnclosingType();
            }
            return enclosingMethod;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public <T> T accept(TypeVariableSource.Visitor<T> visitor) {
            return visitor.onType(this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isPackageType() {
            return getSimpleName().equals("package-info");
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public boolean isGenerified() {
            TypeDescription declaringType;
            if (!getTypeVariables().isEmpty()) {
                return true;
            }
            if (!isStatic() && (declaringType = getDeclaringType()) != null && declaringType.isGenerified()) {
                return true;
            }
            try {
                MethodDescription.InDefinedShape enclosingMethod = getEnclosingMethod();
                return enclosingMethod != null && enclosingMethod.isGenerified();
            } catch (Throwable unused) {
                return false;
            }
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public int getInnerClassCount() {
            TypeDescription declaringType;
            if (isStatic() || (declaringType = getDeclaringType()) == null) {
                return 0;
            }
            return declaringType.getInnerClassCount() + 1;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isInnerClass() {
            return !isStatic() && isNestedClass();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isNestedClass() {
            return getDeclaringType() != null;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription asBoxed() {
            if (represents(Boolean.TYPE)) {
                return ForLoadedType.of(Boolean.class);
            }
            if (represents(Byte.TYPE)) {
                return ForLoadedType.of(Byte.class);
            }
            if (represents(Short.TYPE)) {
                return ForLoadedType.of(Short.class);
            }
            if (represents(Character.TYPE)) {
                return ForLoadedType.of(Character.class);
            }
            if (represents(Integer.TYPE)) {
                return ForLoadedType.of(Integer.class);
            }
            if (represents(Long.TYPE)) {
                return ForLoadedType.of(Long.class);
            }
            if (represents(Float.TYPE)) {
                return ForLoadedType.of(Float.class);
            }
            return represents(Double.TYPE) ? ForLoadedType.of(Double.class) : this;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription asUnboxed() {
            if (represents(Boolean.class)) {
                return ForLoadedType.of(Boolean.TYPE);
            }
            if (represents(Byte.class)) {
                return ForLoadedType.of(Byte.TYPE);
            }
            if (represents(Short.class)) {
                return ForLoadedType.of(Short.TYPE);
            }
            if (represents(Character.class)) {
                return ForLoadedType.of(Character.TYPE);
            }
            if (represents(Integer.class)) {
                return ForLoadedType.of(Integer.TYPE);
            }
            if (represents(Long.class)) {
                return ForLoadedType.of(Long.TYPE);
            }
            if (represents(Float.class)) {
                return ForLoadedType.of(Float.TYPE);
            }
            return represents(Double.class) ? ForLoadedType.of(Double.TYPE) : this;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public Object getDefaultValue() {
            if (represents(Boolean.TYPE)) {
                return false;
            }
            if (represents(Byte.TYPE)) {
                return (byte) 0;
            }
            if (represents(Short.TYPE)) {
                return (short) 0;
            }
            if (represents(Character.TYPE)) {
                return (char) 0;
            }
            if (represents(Integer.TYPE)) {
                return 0;
            }
            if (represents(Long.TYPE)) {
                return 0L;
            }
            if (represents(Float.TYPE)) {
                return Float.valueOf(0.0f);
            }
            if (represents(Double.TYPE)) {
                return Double.valueOf(0.0d);
            }
            return null;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isNestHost() {
            return equals(getNestHost());
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isNestMateOf(Class<?> cls) {
            return isNestMateOf(ForLoadedType.of(cls));
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isNestMateOf(TypeDescription typeDescription) {
            return getNestHost().equals(typeDescription.getNestHost());
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isMemberType() {
            return (isLocalType() || isAnonymousType() || getDeclaringType() == null) ? false : true;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isCompileTimeConstant() {
            return represents(Integer.TYPE) || represents(Long.TYPE) || represents(Float.TYPE) || represents(Double.TYPE) || represents(String.class) || represents(Class.class) || equals(JavaType.METHOD_TYPE.getTypeStub()) || equals(JavaType.METHOD_HANDLE.getTypeStub());
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isSealed() {
            return (isPrimitive() || isArray() || getPermittedSubtypes().isEmpty()) ? false : true;
        }

        @Override // java.lang.Iterable
        public Iterator<TypeDefinition> iterator() {
            return new TypeDefinition.SuperClassIterator(this);
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : getName().hashCode();
            if (iHashCode == 0) {
                return this.hashCode;
            }
            this.hashCode = iHashCode;
            return iHashCode;
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TypeDefinition)) {
                return false;
            }
            TypeDefinition typeDefinition = (TypeDefinition) obj;
            return typeDefinition.getSort().isNonGeneric() && getName().equals(typeDefinition.asErasure().getName());
        }

        public String toString() {
            String strConcat;
            StringBuilder sb = new StringBuilder();
            if (isPrimitive()) {
                strConcat = "";
            } else {
                strConcat = (isInterface() ? PsiKeyword.INTERFACE : "class").concat(" ");
            }
            return sb.append(strConcat).append(getName()).toString();
        }

        @Override // net.bytebuddy.description.TypeVariableSource.AbstractBase
        protected String toSafeString() {
            return toString();
        }

        public static abstract class OfSimpleType extends AbstractBase {
            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isArray() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isPrimitive() {
                return false;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public TypeDescription getComponentType() {
                return TypeDescription.UNDEFINED;
            }

            @Override // net.bytebuddy.description.NamedElement.WithDescriptor
            public String getDescriptor() {
                return "L" + getInternalName() + ";";
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            @MaybeNull
            public String getCanonicalName() {
                if (isAnonymousType() || isLocalType()) {
                    return NO_NAME;
                }
                String internalName = getInternalName();
                TypeDescription enclosingType = getEnclosingType();
                if (enclosingType != null && internalName.startsWith(enclosingType.getInternalName() + "$")) {
                    return enclosingType.getCanonicalName() + "." + internalName.substring(enclosingType.getInternalName().length() + 1);
                }
                return getName();
            }

            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:14:0x004a -> B:7:0x002f). Please report as a decompilation issue!!! */
            @Override // net.bytebuddy.description.type.TypeDescription
            public String getSimpleName() {
                int iLastIndexOf;
                String internalName = getInternalName();
                TypeDescription enclosingType = getEnclosingType();
                if (enclosingType != null && internalName.startsWith(enclosingType.getInternalName() + "$")) {
                    iLastIndexOf = enclosingType.getInternalName().length();
                } else {
                    iLastIndexOf = internalName.lastIndexOf(47);
                    if (iLastIndexOf == -1) {
                        return internalName;
                    }
                    if (iLastIndexOf < internalName.length() || Character.isLetter(internalName.charAt(iLastIndexOf))) {
                        return internalName.substring(iLastIndexOf);
                    }
                }
                iLastIndexOf++;
                if (iLastIndexOf < internalName.length()) {
                }
                return internalName.substring(iLastIndexOf);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public StackSize getStackSize() {
                return StackSize.SINGLE;
            }

            public static abstract class WithDelegation extends OfSimpleType {
                protected abstract TypeDescription delegate();

                @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase.OfSimpleType, net.bytebuddy.description.type.TypeDefinition
                @MaybeNull
                public /* bridge */ /* synthetic */ TypeDefinition getComponentType() {
                    return super.getComponentType();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public Generic getSuperClass() {
                    return delegate().getSuperClass();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public TypeList.Generic getInterfaces() {
                    return delegate().getInterfaces();
                }

                @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
                public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
                    return delegate().getDeclaredFields();
                }

                @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
                public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
                    return delegate().getDeclaredMethods();
                }

                @Override // net.bytebuddy.description.DeclaredByType
                @MaybeNull
                public TypeDescription getDeclaringType() {
                    return delegate().getDeclaringType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                @MaybeNull
                public MethodDescription.InDefinedShape getEnclosingMethod() {
                    return delegate().getEnclosingMethod();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                @MaybeNull
                public TypeDescription getEnclosingType() {
                    return delegate().getEnclosingType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                public TypeList getDeclaredTypes() {
                    return delegate().getDeclaredTypes();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                public boolean isAnonymousType() {
                    return delegate().isAnonymousType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                public boolean isLocalType() {
                    return delegate().isLocalType();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                @MaybeNull
                public PackageDescription getPackage() {
                    return delegate().getPackage();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return delegate().getDeclaredAnnotations();
                }

                @Override // net.bytebuddy.description.TypeVariableSource
                public TypeList.Generic getTypeVariables() {
                    return delegate().getTypeVariables();
                }

                @Override // net.bytebuddy.description.ModifierReviewable
                public int getModifiers() {
                    return delegate().getModifiers();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
                @MaybeNull
                public String getGenericSignature() {
                    return delegate().getGenericSignature();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
                public int getActualModifiers(boolean z) {
                    return delegate().getActualModifiers(z);
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                public TypeDescription getNestHost() {
                    return delegate().getNestHost();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                public TypeList getNestMembers() {
                    return delegate().getNestMembers();
                }

                @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
                public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
                    return delegate().getRecordComponents();
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                public boolean isRecord() {
                    return delegate().isRecord();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
                public boolean isSealed() {
                    return delegate().isSealed();
                }

                @Override // net.bytebuddy.description.type.TypeDescription
                public TypeList getPermittedSubtypes() {
                    return delegate().getPermittedSubtypes();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
                @MaybeNull
                public ClassFileVersion getClassFileVersion() {
                    return delegate().getClassFileVersion();
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class LazyProxy implements InvocationHandler {
        private final Class<?> type;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.type.equals(((LazyProxy) obj).type);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.type.hashCode();
        }

        protected LazyProxy(Class<?> cls) {
            this.type = cls;
        }

        protected static TypeDescription of(Class<?> cls) {
            return (TypeDescription) Proxy.newProxyInstance(TypeDescription.class.getClassLoader(), new Class[]{TypeDescription.class}, new LazyProxy(cls));
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, @MaybeNull Object[] objArr) throws Throwable {
            try {
                return method.invoke(ForLoadedType.of(this.type), objArr);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }

    public static class ForLoadedType extends AbstractBase implements Serializable {
        private static final boolean ACCESS_CONTROLLER;
        private static final Dispatcher DISPATCHER;
        private static final Map<Class<?>, TypeDescription> TYPE_CACHE;
        private static final long serialVersionUID = 1;
        private transient /* synthetic */ ClassFileVersion classFileVersion;
        private transient /* synthetic */ AnnotationList declaredAnnotations;
        private transient /* synthetic */ FieldList declaredFields;
        private transient /* synthetic */ MethodList declaredMethods;
        private final Class<?> type;

        @JavaDispatcher.Defaults
        @JavaDispatcher.Proxied(CommonClassNames.JAVA_LANG_CLASS)
        protected interface Dispatcher {
            @JavaDispatcher.Proxied("getAnnotatedInterfaces")
            AnnotatedElement[] getAnnotatedInterfaces(Class<?> cls);

            @MaybeNull
            @JavaDispatcher.Proxied("getAnnotatedSuperclass")
            AnnotatedElement getAnnotatedSuperclass(Class<?> cls);

            @MaybeNull
            @JavaDispatcher.Proxied("getNestHost")
            Class<?> getNestHost(Class<?> cls);

            @JavaDispatcher.Proxied("getNestMembers")
            Class<?>[] getNestMembers(Class<?> cls);

            @MaybeNull
            @JavaDispatcher.Proxied("getPermittedSubclasses")
            Class<?>[] getPermittedSubclasses(Class<?> cls);

            @MaybeNull
            @JavaDispatcher.Proxied("getRecordComponents")
            Object[] getRecordComponents(Class<?> cls);

            @JavaDispatcher.Proxied("isNestmateOf")
            boolean isNestmateOf(Class<?> cls, Class<?> cls2);

            @JavaDispatcher.Proxied("isRecord")
            boolean isRecord(Class<?> cls);

            @JavaDispatcher.Proxied("isSealed")
            boolean isSealed(Class<?> cls);
        }

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
            DISPATCHER = (Dispatcher) doPrivileged(JavaDispatcher.of(Dispatcher.class));
            HashMap map = new HashMap();
            TYPE_CACHE = map;
            map.put(TargetType.class, new ForLoadedType(TargetType.class));
            map.put(Class.class, new ForLoadedType(Class.class));
            map.put(Throwable.class, new ForLoadedType(Throwable.class));
            map.put(Annotation.class, new ForLoadedType(Annotation.class));
            map.put(Object.class, new ForLoadedType(Object.class));
            map.put(String.class, new ForLoadedType(String.class));
            map.put(Boolean.class, new ForLoadedType(Boolean.class));
            map.put(Byte.class, new ForLoadedType(Byte.class));
            map.put(Short.class, new ForLoadedType(Short.class));
            map.put(Character.class, new ForLoadedType(Character.class));
            map.put(Integer.class, new ForLoadedType(Integer.class));
            map.put(Long.class, new ForLoadedType(Long.class));
            map.put(Float.class, new ForLoadedType(Float.class));
            map.put(Double.class, new ForLoadedType(Double.class));
            map.put(Void.TYPE, new ForLoadedType(Void.TYPE));
            map.put(Boolean.TYPE, new ForLoadedType(Boolean.TYPE));
            map.put(Byte.TYPE, new ForLoadedType(Byte.TYPE));
            map.put(Short.TYPE, new ForLoadedType(Short.TYPE));
            map.put(Character.TYPE, new ForLoadedType(Character.TYPE));
            map.put(Integer.TYPE, new ForLoadedType(Integer.TYPE));
            map.put(Long.TYPE, new ForLoadedType(Long.TYPE));
            map.put(Float.TYPE, new ForLoadedType(Float.TYPE));
            map.put(Double.TYPE, new ForLoadedType(Double.TYPE));
        }

        public ForLoadedType(Class<?> cls) {
            this.type = cls;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        public static String getName(Class<?> cls) {
            String name2 = cls.getName();
            int iIndexOf = name2.indexOf(47);
            return iIndexOf == -1 ? name2 : name2.substring(0, iIndexOf);
        }

        public static TypeDescription of(Class<?> cls) {
            TypeDescription typeDescription = TYPE_CACHE.get(cls);
            return typeDescription == null ? new ForLoadedType(cls) : typeDescription;
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableFrom(Class<?> cls) {
            return this.type.isAssignableFrom(cls) || super.isAssignableFrom(cls);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableFrom(TypeDescription typeDescription) {
            return ((typeDescription instanceof ForLoadedType) && this.type.isAssignableFrom(((ForLoadedType) typeDescription).type)) || super.isAssignableFrom(typeDescription);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableTo(Class<?> cls) {
            return cls.isAssignableFrom(this.type) || super.isAssignableTo(cls);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isAssignableTo(TypeDescription typeDescription) {
            return ((typeDescription instanceof ForLoadedType) && ((ForLoadedType) typeDescription).type.isAssignableFrom(this.type)) || super.isAssignableTo(typeDescription);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isInHierarchyWith(Class<?> cls) {
            return cls.isAssignableFrom(this.type) || this.type.isAssignableFrom(cls) || super.isInHierarchyWith(cls);
        }

        /* JADX WARN: Code duplicated, block: B:11:0x0023 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Code duplicated, block: B:8:0x001b  */
        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isInHierarchyWith(TypeDescription typeDescription) {
            if (typeDescription instanceof ForLoadedType) {
                ForLoadedType forLoadedType = (ForLoadedType) typeDescription;
                if (!forLoadedType.type.isAssignableFrom(this.type) && !this.type.isAssignableFrom(forLoadedType.type)) {
                    if (super.isInHierarchyWith(typeDescription)) {
                        return false;
                    }
                }
            } else if (super.isInHierarchyWith(typeDescription)) {
                return false;
            }
            return true;
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDefinition
        public boolean represents(Type type) {
            return type == this.type || super.represents(type);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public TypeDescription getComponentType() {
            Class<?> componentType = this.type.getComponentType();
            if (componentType == null) {
                return TypeDescription.UNDEFINED;
            }
            return of(componentType);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isArray() {
            return this.type.isArray();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isPrimitive() {
            return this.type.isPrimitive();
        }

        @Override // net.bytebuddy.description.ModifierReviewable.AbstractBase, net.bytebuddy.description.ModifierReviewable.ForTypeDefinition
        public boolean isAnnotation() {
            return this.type.isAnnotation();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public Generic getSuperClass() {
            if (RAW_TYPES) {
                return this.type.getSuperclass() == null ? Generic.UNDEFINED : Generic.OfNonGenericType.ForLoadedType.of(this.type.getSuperclass());
            }
            return Generic.LazyProjection.ForLoadedSuperClass.of(this.type);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            if (RAW_TYPES) {
                return isArray() ? ARRAY_INTERFACES : new TypeList.Generic.ForLoadedTypes(this.type.getInterfaces());
            }
            return isArray() ? ARRAY_INTERFACES : new TypeList.Generic.OfLoadedInterfaceTypes(this.type);
        }

        @Override // net.bytebuddy.description.DeclaredByType
        @MaybeNull
        public TypeDescription getDeclaringType() {
            Class<?> declaringClass = this.type.getDeclaringClass();
            if (declaringClass == null) {
                return TypeDescription.UNDEFINED;
            }
            return of(declaringClass);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            Method enclosingMethod = this.type.getEnclosingMethod();
            Constructor<?> enclosingConstructor = this.type.getEnclosingConstructor();
            if (enclosingMethod != null) {
                return new MethodDescription.ForLoadedMethod(enclosingMethod);
            }
            if (enclosingConstructor != null) {
                return new MethodDescription.ForLoadedConstructor(enclosingConstructor);
            }
            return MethodDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getEnclosingType() {
            Class<?> enclosingClass = this.type.getEnclosingClass();
            if (enclosingClass == null) {
                return TypeDescription.UNDEFINED;
            }
            return of(enclosingClass);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            return new TypeList.ForLoadedTypes(this.type.getDeclaredClasses());
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public String getSimpleName() {
            String simpleName = this.type.getSimpleName();
            int iIndexOf = simpleName.indexOf(47);
            if (iIndexOf == -1) {
                return simpleName;
            }
            StringBuilder sb = new StringBuilder(simpleName.substring(0, iIndexOf));
            for (Class<?> componentType = this.type; componentType.isArray(); componentType = componentType.getComponentType()) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            return sb.toString();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            return this.type.isAnonymousClass();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            return this.type.isLocalClass();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isMemberType() {
            return this.type.isMemberClass();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        @CachedReturnPlugin.Enhance("declaredFields")
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            FieldList.ForLoadedFields forLoadedFields = this.declaredFields != null ? null : new FieldList.ForLoadedFields((Field[]) GraalImageCode.getCurrent().sorted(this.type.getDeclaredFields(), FieldComparator.INSTANCE));
            if (forLoadedFields == null) {
                return this.declaredFields;
            }
            this.declaredFields = forLoadedFields;
            return forLoadedFields;
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        @CachedReturnPlugin.Enhance("declaredMethods")
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            MethodList.ForLoadedMethods forLoadedMethods = this.declaredMethods != null ? null : new MethodList.ForLoadedMethods(this.type);
            if (forLoadedMethods == null) {
                return this.declaredMethods;
            }
            this.declaredMethods = forLoadedMethods;
            return forLoadedMethods;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public PackageDescription getPackage() {
            if (this.type.isArray() || this.type.isPrimitive()) {
                return PackageDescription.UNDEFINED;
            }
            Package r0 = this.type.getPackage();
            if (r0 == null) {
                String name2 = this.type.getName();
                int iLastIndexOf = name2.lastIndexOf(46);
                if (iLastIndexOf == -1) {
                    return PackageDescription.DEFAULT;
                }
                return new PackageDescription.Simple(name2.substring(0, iLastIndexOf));
            }
            return new PackageDescription.ForLoadedPackage(r0);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public StackSize getStackSize() {
            return StackSize.of(this.type);
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return getName(this.type);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public String getCanonicalName() {
            String canonicalName = this.type.getCanonicalName();
            if (canonicalName == null) {
                return NO_NAME;
            }
            int iIndexOf = canonicalName.indexOf(47);
            if (iIndexOf == -1) {
                return canonicalName;
            }
            StringBuilder sb = new StringBuilder(canonicalName.substring(0, iIndexOf));
            for (Class<?> componentType = this.type; componentType.isArray(); componentType = componentType.getComponentType()) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            return sb.toString();
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            String name2 = this.type.getName();
            int iIndexOf = name2.indexOf(47);
            if (iIndexOf == -1) {
                return net.bytebuddy.jar.asm.Type.getDescriptor(this.type);
            }
            return "L" + name2.substring(0, iIndexOf).replace('.', '/') + ";";
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.type.getModifiers();
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            if (RAW_TYPES) {
                return new TypeList.Generic.Empty();
            }
            return TypeList.Generic.ForLoadedTypes.OfTypeVariables.of((GenericDeclaration) this.type);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        @CachedReturnPlugin.Enhance("declaredAnnotations")
        public AnnotationList getDeclaredAnnotations() {
            AnnotationList.ForLoadedAnnotations forLoadedAnnotations = this.declaredAnnotations != null ? null : new AnnotationList.ForLoadedAnnotations(this.type.getDeclaredAnnotations());
            if (forLoadedAnnotations == null) {
                return this.declaredAnnotations;
            }
            this.declaredAnnotations = forLoadedAnnotations;
            return forLoadedAnnotations;
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDefinition
        public Generic asGenericType() {
            return Generic.OfNonGenericType.ForLoadedType.of(this.type);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            Class<?> nestHost = DISPATCHER.getNestHost(this.type);
            return nestHost == null ? this : of(nestHost);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            Class<?>[] nestMembers = DISPATCHER.getNestMembers(this.type);
            if (nestMembers.length == 0) {
                nestMembers = new Class[]{this.type};
            }
            return new TypeList.ForLoadedTypes(nestMembers);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isNestHost() {
            Class<?> nestHost = DISPATCHER.getNestHost(this.type);
            return nestHost == null || nestHost == this.type;
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isNestMateOf(Class<?> cls) {
            return DISPATCHER.isNestmateOf(this.type, cls) || super.isNestMateOf(of(cls));
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isNestMateOf(TypeDescription typeDescription) {
            return ((typeDescription instanceof ForLoadedType) && DISPATCHER.isNestmateOf(this.type, ((ForLoadedType) typeDescription).type)) || super.isNestMateOf(typeDescription);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            Object[] recordComponents = DISPATCHER.getRecordComponents(this.type);
            return recordComponents == null ? new RecordComponentList.Empty<>() : new RecordComponentList.ForLoadedRecordComponents(recordComponents);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            return DISPATCHER.isRecord(this.type);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isSealed() {
            return DISPATCHER.isSealed(this.type);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            Class<?>[] permittedSubclasses = DISPATCHER.getPermittedSubclasses(this.type);
            return permittedSubclasses == null ? new TypeList.Empty() : new TypeList.ForLoadedTypes(permittedSubclasses);
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        @CachedReturnPlugin.Enhance("classFileVersion")
        @MaybeNull
        public ClassFileVersion getClassFileVersion() {
            ClassFileVersion classFileVersionOf = null;
            if (this.classFileVersion == null) {
                try {
                    classFileVersionOf = ClassFileVersion.of(this.type);
                } catch (Throwable unused) {
                }
            }
            if (classFileVersionOf == null) {
                return this.classFileVersion;
            }
            this.classFileVersion = classFileVersionOf;
            return classFileVersionOf;
        }
    }

    public static class ArrayProjection extends AbstractBase {
        private static final int ARRAY_EXCLUDED = 8712;
        private static final int ARRAY_IMPLIED = 1040;
        private final int arity;
        private final TypeDescription componentType;

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            return this;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isArray() {
            return true;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isMemberType() {
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isPrimitive() {
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            return false;
        }

        protected ArrayProjection(TypeDescription typeDescription, int i) {
            this.componentType = typeDescription;
            this.arity = i;
        }

        public static TypeDescription of(TypeDescription typeDescription) {
            return of(typeDescription, 1);
        }

        public static TypeDescription of(TypeDescription typeDescription, int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Arrays cannot have a negative arity");
            }
            while (typeDescription.isArray()) {
                typeDescription = typeDescription.getComponentType();
                i++;
            }
            return i == 0 ? typeDescription : new ArrayProjection(typeDescription, i);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public TypeDescription getComponentType() {
            int i = this.arity;
            return i == 1 ? this.componentType : new ArrayProjection(this.componentType, i - 1);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public Generic getSuperClass() {
            return Generic.OfNonGenericType.ForLoadedType.of(Object.class);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            return ARRAY_INTERFACES;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            return MethodDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public TypeDescription getEnclosingType() {
            return TypeDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            return new TypeList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public String getSimpleName() {
            StringBuilder sb = new StringBuilder(this.componentType.getSimpleName());
            for (int i = 0; i < this.arity; i++) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            return sb.toString();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public String getCanonicalName() {
            String canonicalName = this.componentType.getCanonicalName();
            if (canonicalName == null) {
                return NO_NAME;
            }
            StringBuilder sb = new StringBuilder(canonicalName);
            for (int i = 0; i < this.arity; i++) {
                sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            }
            return sb.toString();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            return new FieldList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            return new MethodList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public StackSize getStackSize() {
            return StackSize.SINGLE;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public AnnotationList getInheritedAnnotations() {
            return new AnnotationList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public PackageDescription getPackage() {
            return PackageDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            String descriptor = this.componentType.getDescriptor();
            StringBuilder sb = new StringBuilder(descriptor.length() + this.arity);
            for (int i = 0; i < this.arity; i++) {
                sb.append('[');
            }
            for (int i2 = 0; i2 < descriptor.length(); i2++) {
                char cCharAt = descriptor.charAt(i2);
                if (cCharAt == '/') {
                    cCharAt = '.';
                }
                sb.append(cCharAt);
            }
            return sb.toString();
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.arity; i++) {
                sb.append('[');
            }
            return sb.append(this.componentType.getDescriptor()).toString();
        }

        @Override // net.bytebuddy.description.DeclaredByType
        @AlwaysNull
        public TypeDescription getDeclaringType() {
            return TypeDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return (getComponentType().getModifiers() & (-8713)) | ARRAY_IMPLIED;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return new TypeList.Generic.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            return new TypeList.Explicit(this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            return new RecordComponentList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            return new TypeList.Empty();
        }
    }

    public static class Latent extends AbstractBase.OfSimpleType {
        private final List<? extends Generic> interfaces;
        private final int modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f357name;

        @MaybeNull
        private final Generic superClass;

        public Latent(String str, int i, @MaybeNull Generic generic, Generic... genericArr) {
            this(str, i, generic, (List<? extends Generic>) Arrays.asList(genericArr));
        }

        public Latent(String str, int i, @MaybeNull Generic generic, List<? extends Generic> list) {
            this.f357name = str;
            this.modifiers = i;
            this.superClass = generic;
            this.interfaces = list;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public Generic getSuperClass() {
            return this.superClass;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            return new TypeList.Generic.Explicit(this.interfaces);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            throw new IllegalStateException("Cannot resolve enclosing method of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getEnclosingType() {
            throw new IllegalStateException("Cannot resolve enclosing type of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            throw new IllegalStateException("Cannot resolve inner types of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            throw new IllegalStateException("Cannot resolve anonymous type property of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            throw new IllegalStateException("Cannot resolve local class property of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            throw new IllegalStateException("Cannot resolve declared fields of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            throw new IllegalStateException("Cannot resolve declared methods of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public PackageDescription getPackage() {
            String name2 = getName();
            int iLastIndexOf = name2.lastIndexOf(46);
            if (iLastIndexOf == -1) {
                return PackageDescription.DEFAULT;
            }
            return new PackageDescription.Simple(name2.substring(0, iLastIndexOf));
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            throw new IllegalStateException("Cannot resolve declared annotations of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.DeclaredByType
        public TypeDescription getDeclaringType() {
            throw new IllegalStateException("Cannot resolve declared type of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.modifiers;
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.f357name;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            throw new IllegalStateException("Cannot resolve type variables of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            throw new IllegalStateException("Cannot resolve nest host of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            throw new IllegalStateException("Cannot resolve nest mates of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            throw new IllegalStateException("Cannot resolve record components of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            throw new IllegalStateException("Cannot resolve record attribute of a latent type description: " + this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            throw new IllegalStateException("Cannot resolve permitted subclasses of a latent type description: " + this);
        }
    }

    public static class ForPackageDescription extends AbstractBase.OfSimpleType {
        private final PackageDescription packageDescription;

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return PackageDescription.PACKAGE_MODIFIERS;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            return this;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            return false;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            return false;
        }

        public ForPackageDescription(PackageDescription packageDescription) {
            this.packageDescription = packageDescription;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public Generic getSuperClass() {
            return Generic.OfNonGenericType.ForLoadedType.of(Object.class);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            return new TypeList.Generic.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            return MethodDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public TypeDescription getEnclosingType() {
            return TypeDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            return new TypeList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            return new FieldList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            return new MethodList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public PackageDescription getPackage() {
            return this.packageDescription;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return this.packageDescription.getDeclaredAnnotations();
        }

        @Override // net.bytebuddy.description.DeclaredByType
        @MaybeNull
        public TypeDescription getDeclaringType() {
            return TypeDescription.UNDEFINED;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return new TypeList.Generic.Empty();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.packageDescription.getName() + ".package-info";
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            return new TypeList.Explicit(this);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            return new RecordComponentList.Empty();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            return new TypeList.Empty();
        }
    }

    public static class SuperTypeLoading extends AbstractBase {

        @MaybeNull
        private final ClassLoader classLoader;
        private final ClassLoadingDelegate classLoadingDelegate;
        private final TypeDescription delegate;

        public SuperTypeLoading(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader) {
            this(typeDescription, classLoader, ClassLoadingDelegate.Simple.INSTANCE);
        }

        public SuperTypeLoading(TypeDescription typeDescription, @MaybeNull ClassLoader classLoader, ClassLoadingDelegate classLoadingDelegate) {
            this.delegate = typeDescription;
            this.classLoader = classLoader;
            this.classLoadingDelegate = classLoadingDelegate;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return this.delegate.getDeclaredAnnotations();
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.delegate.getModifiers();
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return this.delegate.getTypeVariables();
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            return this.delegate.getDescriptor();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.delegate.getName();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public Generic getSuperClass() {
            Generic superClass = this.delegate.getSuperClass();
            return superClass == null ? Generic.UNDEFINED : new ClassLoadingTypeProjection(superClass, this.classLoader, this.classLoadingDelegate);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            return new ClassLoadingTypeList(this.delegate.getInterfaces(), this.classLoader, this.classLoadingDelegate);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            return this.delegate.getDeclaredFields();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            return this.delegate.getDeclaredMethods();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public StackSize getStackSize() {
            return this.delegate.getStackSize();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isArray() {
            return this.delegate.isArray();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isPrimitive() {
            return this.delegate.isPrimitive();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public TypeDescription getComponentType() {
            return this.delegate.getComponentType();
        }

        @Override // net.bytebuddy.description.DeclaredByType
        @MaybeNull
        public TypeDescription getDeclaringType() {
            return this.delegate.getDeclaringType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            return this.delegate.getDeclaredTypes();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            return this.delegate.getEnclosingMethod();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public TypeDescription getEnclosingType() {
            return this.delegate.getEnclosingType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public String getSimpleName() {
            return this.delegate.getSimpleName();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public String getCanonicalName() {
            return this.delegate.getCanonicalName();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            return this.delegate.isAnonymousType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            return this.delegate.isLocalType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public PackageDescription getPackage() {
            return this.delegate.getPackage();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            return this.delegate.getNestHost();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            return this.delegate.getNestMembers();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            return this.delegate.getRecordComponents();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            return this.delegate.isRecord();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isSealed() {
            return this.delegate.isSealed();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            return this.delegate.getPermittedSubtypes();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public ClassFileVersion getClassFileVersion() {
            return this.delegate.getClassFileVersion();
        }

        public interface ClassLoadingDelegate {
            Class<?> load(String str, @MaybeNull ClassLoader classLoader) throws ClassNotFoundException;

            public enum Simple implements ClassLoadingDelegate {
                INSTANCE;

                @Override // net.bytebuddy.description.type.TypeDescription.SuperTypeLoading.ClassLoadingDelegate
                public Class<?> load(String str, @MaybeNull ClassLoader classLoader) throws ClassNotFoundException {
                    return Class.forName(str, false, classLoader);
                }
            }
        }

        protected static class ClassLoadingTypeProjection extends Generic.LazyProjection {

            @MaybeNull
            private final ClassLoader classLoader;
            private final ClassLoadingDelegate classLoadingDelegate;
            private final Generic delegate;
            private transient /* synthetic */ TypeDescription erasure;
            private transient /* synthetic */ TypeList.Generic interfaces;
            private transient /* synthetic */ Generic superClass;

            protected ClassLoadingTypeProjection(Generic generic, @MaybeNull ClassLoader classLoader, ClassLoadingDelegate classLoadingDelegate) {
                this.delegate = generic;
                this.classLoader = classLoader;
                this.classLoadingDelegate = classLoadingDelegate;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationSource
            public AnnotationList getDeclaredAnnotations() {
                return this.delegate.getDeclaredAnnotations();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @CachedReturnPlugin.Enhance("erasure")
            public TypeDescription asErasure() {
                TypeDescription typeDescriptionAsErasure;
                if (this.erasure != null) {
                    typeDescriptionAsErasure = null;
                } else {
                    try {
                        typeDescriptionAsErasure = ForLoadedType.of(this.classLoadingDelegate.load(this.delegate.asErasure().getName(), this.classLoader));
                    } catch (ClassNotFoundException unused) {
                        typeDescriptionAsErasure = this.delegate.asErasure();
                    }
                }
                if (typeDescriptionAsErasure == null) {
                    return this.erasure;
                }
                this.erasure = typeDescriptionAsErasure;
                return typeDescriptionAsErasure;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
            protected Generic resolve() {
                return this.delegate;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @CachedReturnPlugin.Enhance("superClass")
            @MaybeNull
            public Generic getSuperClass() {
                Generic superClass;
                if (this.superClass != null) {
                    superClass = null;
                } else {
                    superClass = this.delegate.getSuperClass();
                    if (superClass == null) {
                        superClass = Generic.UNDEFINED;
                    } else {
                        try {
                            superClass = new ClassLoadingTypeProjection(superClass, this.classLoadingDelegate.load(this.delegate.asErasure().getName(), this.classLoader).getClassLoader(), this.classLoadingDelegate);
                        } catch (ClassNotFoundException unused) {
                        }
                    }
                }
                if (superClass == null) {
                    return this.superClass;
                }
                this.superClass = superClass;
                return superClass;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @CachedReturnPlugin.Enhance(Namer.METADATA_SUPERTYPES)
            public TypeList.Generic getInterfaces() {
                TypeList.Generic interfaces;
                if (this.interfaces != null) {
                    interfaces = null;
                } else {
                    interfaces = this.delegate.getInterfaces();
                    try {
                        interfaces = new ClassLoadingTypeList(interfaces, this.classLoadingDelegate.load(this.delegate.asErasure().getName(), this.classLoader).getClassLoader(), this.classLoadingDelegate);
                    } catch (ClassNotFoundException unused) {
                    }
                }
                if (interfaces == null) {
                    return this.interfaces;
                }
                this.interfaces = interfaces;
                return interfaces;
            }

            @Override // java.lang.Iterable
            public Iterator<TypeDefinition> iterator() {
                return new TypeDefinition.SuperClassIterator(this);
            }
        }

        protected static class ClassLoadingTypeList extends TypeList.Generic.AbstractBase {

            @MaybeNull
            private final ClassLoader classLoader;
            private final ClassLoadingDelegate classLoadingDelegate;
            private final TypeList.Generic delegate;

            protected ClassLoadingTypeList(TypeList.Generic generic, @MaybeNull ClassLoader classLoader, ClassLoadingDelegate classLoadingDelegate) {
                this.delegate = generic;
                this.classLoader = classLoader;
                this.classLoadingDelegate = classLoadingDelegate;
            }

            @Override // java.util.AbstractList, java.util.List
            public Generic get(int i) {
                return new ClassLoadingTypeProjection((Generic) this.delegate.get(i), this.classLoader, this.classLoadingDelegate);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return this.delegate.size();
            }
        }
    }
}
