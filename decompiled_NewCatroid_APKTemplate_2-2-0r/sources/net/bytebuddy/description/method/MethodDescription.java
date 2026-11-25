package net.bytebuddy.description.method;

import io.netty.util.internal.StringUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.DeclaredByType;
import net.bytebuddy.description.ModifierReviewable;
import net.bytebuddy.description.TypeVariableSource;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.description.type.TypeVariableToken;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.jar.asm.signature.SignatureVisitor;
import net.bytebuddy.jar.asm.signature.SignatureWriter;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface MethodDescription extends TypeVariableSource, ModifierReviewable.ForMethodDescription, DeclaredByType.WithMandatoryDeclaration, ByteCodeElement.Member, ByteCodeElement.TypeDependant<InDefinedShape, Token> {
    public static final String CONSTRUCTOR_INTERNAL_NAME = "<init>";
    public static final String TYPE_INITIALIZER_INTERNAL_NAME = "<clinit>";
    public static final int TYPE_INITIALIZER_MODIFIER = 8;

    @AlwaysNull
    public static final InDefinedShape UNDEFINED = null;

    public interface InGenericShape extends MethodDescription {
        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        TypeDescription.Generic getDeclaringType();

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
        ParameterList<ParameterDescription.InGenericShape> getParameters();
    }

    SignatureToken asSignatureToken();

    TypeToken asTypeToken();

    int getActualModifiers();

    int getActualModifiers(boolean z);

    int getActualModifiers(boolean z, Visibility visibility);

    @Override // net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
    @Nonnull
    TypeDefinition getDeclaringType();

    @MaybeNull
    <T> T getDefaultValue(Class<T> cls);

    @MaybeNull
    AnnotationValue<?, ?> getDefaultValue();

    TypeList.Generic getExceptionTypes();

    ParameterList<?> getParameters();

    @MaybeNull
    TypeDescription.Generic getReceiverType();

    TypeDescription.Generic getReturnType();

    int getStackSize();

    boolean isBridgeCompatible(TypeToken typeToken);

    boolean isConstantBootstrap();

    boolean isConstantBootstrap(List<? extends TypeDefinition> list);

    boolean isConstructor();

    boolean isDefaultMethod();

    boolean isDefaultValue();

    boolean isDefaultValue(AnnotationValue<?, ?> annotationValue);

    boolean isInvokableOn(TypeDescription typeDescription);

    boolean isInvokeBootstrap();

    boolean isInvokeBootstrap(List<? extends TypeDefinition> list);

    boolean isMethod();

    boolean isSpecializableFor(TypeDescription typeDescription);

    boolean isTypeInitializer();

    boolean isVirtual();

    boolean represents(Constructor<?> constructor);

    boolean represents(Method method);

    public interface InDefinedShape extends MethodDescription {
        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        TypeDescription getDeclaringType();

        ParameterList<ParameterDescription.InDefinedShape> getParameters();

        public static abstract class AbstractBase extends AbstractBase implements InDefinedShape {

            @JavaDispatcher.Proxied("java.lang.reflect.Executable")
            protected interface Executable {
                @JavaDispatcher.Defaults
                @MaybeNull
                @JavaDispatcher.Proxied("getAnnotatedReceiverType")
                AnnotatedElement getAnnotatedReceiverType(Object obj);
            }

            @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
            public InDefinedShape asDefined() {
                return this;
            }

            @MaybeNull
            public TypeDescription.Generic getReceiverType() {
                if (isStatic()) {
                    return TypeDescription.Generic.UNDEFINED;
                }
                if (isConstructor()) {
                    TypeDescription declaringType = getDeclaringType();
                    TypeDescription enclosingType = getDeclaringType().getEnclosingType();
                    if (enclosingType == null) {
                        return TypeDescription.Generic.OfParameterizedType.ForGenerifiedErasure.of(declaringType);
                    }
                    if (declaringType.isStatic()) {
                        return enclosingType.asGenericType();
                    }
                    return TypeDescription.Generic.OfParameterizedType.ForGenerifiedErasure.of(enclosingType);
                }
                return TypeDescription.Generic.OfParameterizedType.ForGenerifiedErasure.of(getDeclaringType());
            }

            protected static abstract class ForLoadedExecutable<T extends AnnotatedElement> extends AbstractBase {
                private static final boolean ACCESS_CONTROLLER;
                protected static final Executable EXECUTABLE;
                protected final T executable;

                @Override // net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase, net.bytebuddy.description.ByteCodeElement.TypeDependant
                public /* bridge */ /* synthetic */ ByteCodeElement.TypeDependant asDefined() {
                    return super.asDefined();
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
                    EXECUTABLE = (Executable) doPrivileged(JavaDispatcher.of(Executable.class));
                }

                protected ForLoadedExecutable(T t) {
                    this.executable = t;
                }

                @AccessControllerPlugin.Enhance
                private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
                    return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
                }

                @Override // net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase, net.bytebuddy.description.method.MethodDescription
                public TypeDescription.Generic getReceiverType() {
                    AnnotatedElement annotatedReceiverType = EXECUTABLE.getAnnotatedReceiverType(this.executable);
                    if (annotatedReceiverType == null) {
                        return super.getReceiverType();
                    }
                    return TypeDefinition.Sort.describeAnnotated(annotatedReceiverType);
                }
            }
        }
    }

    public static abstract class AbstractBase extends TypeVariableSource.AbstractBase implements MethodDescription {
        private static final int SOURCE_MODIFIERS = 1343;
        private transient /* synthetic */ int hashCode;

        @Override // net.bytebuddy.description.TypeVariableSource
        public boolean isInferrable() {
            return true;
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public /* bridge */ /* synthetic */ ByteCodeElement.Token asToken(ElementMatcher elementMatcher) {
            return asToken((ElementMatcher<? super TypeDescription>) elementMatcher);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public int getStackSize() {
            return getParameters().asTypeList().getStackSize() + (!isStatic() ? 1 : 0);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isMethod() {
            return (isConstructor() || isTypeInitializer()) ? false : true;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isConstructor() {
            return "<init>".equals(getInternalName());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isTypeInitializer() {
            return MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME.equals(getInternalName());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean represents(Method method) {
            return equals(new ForLoadedMethod(method));
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean represents(Constructor<?> constructor) {
            return equals(new ForLoadedConstructor(constructor));
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            if (isMethod()) {
                return getInternalName();
            }
            return getDeclaringType().asErasure().getName();
        }

        @Override // net.bytebuddy.description.NamedElement
        public String getActualName() {
            return isMethod() ? getName() : "";
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            StringBuilder sb = new StringBuilder("(");
            Iterator it = getParameters().asTypeList().asErasures().iterator();
            while (it.hasNext()) {
                sb.append(((TypeDescription) it.next()).getDescriptor());
            }
            return sb.append(')').append(getReturnType().asErasure().getDescriptor()).toString();
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        @MaybeNull
        public String getGenericSignature() {
            SignatureVisitor signatureVisitorVisitInterfaceBound;
            try {
                SignatureWriter signatureWriter = new SignatureWriter();
                boolean z = false;
                for (TypeDescription.Generic generic : getTypeVariables()) {
                    signatureWriter.visitFormalTypeParameter(generic.getSymbol());
                    boolean z2 = true;
                    for (TypeDescription.Generic generic2 : generic.getUpperBounds()) {
                        if (z2) {
                            signatureVisitorVisitInterfaceBound = signatureWriter.visitClassBound();
                        } else {
                            signatureVisitorVisitInterfaceBound = signatureWriter.visitInterfaceBound();
                        }
                        generic2.accept(new TypeDescription.Generic.Visitor.ForSignatureVisitor(signatureVisitorVisitInterfaceBound));
                        z2 = false;
                    }
                    z = true;
                }
                for (TypeDescription.Generic generic3 : getParameters().asTypeList()) {
                    generic3.accept(new TypeDescription.Generic.Visitor.ForSignatureVisitor(signatureWriter.visitParameterType()));
                    z = z || !generic3.getSort().isNonGeneric();
                }
                TypeDescription.Generic returnType = getReturnType();
                returnType.accept(new TypeDescription.Generic.Visitor.ForSignatureVisitor(signatureWriter.visitReturnType()));
                boolean z3 = z || !returnType.getSort().isNonGeneric();
                TypeList.Generic<TypeDescription.Generic> exceptionTypes = getExceptionTypes();
                if (!exceptionTypes.filter(ElementMatchers.not(ElementMatchers.ofSort(TypeDefinition.Sort.NON_GENERIC))).isEmpty()) {
                    for (TypeDescription.Generic generic4 : exceptionTypes) {
                        generic4.accept(new TypeDescription.Generic.Visitor.ForSignatureVisitor(signatureWriter.visitExceptionType()));
                        z3 = z3 || !generic4.getSort().isNonGeneric();
                    }
                }
                return z3 ? signatureWriter.toString() : NON_GENERIC_SIGNATURE;
            } catch (GenericSignatureFormatError unused) {
                return NON_GENERIC_SIGNATURE;
            }
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public int getActualModifiers() {
            return getModifiers() | (getDeclaredAnnotations().isAnnotationPresent(Deprecated.class) ? 131072 : 0);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public int getActualModifiers(boolean z) {
            if (z) {
                return getActualModifiers() & (-1281);
            }
            return (getActualModifiers() & (-257)) | 1024;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public int getActualModifiers(boolean z, Visibility visibility) {
            return ModifierContributor.Resolver.of(Collections.singleton(getVisibility().expandTo(visibility))).resolve(getActualModifiers(z));
        }

        @Override // net.bytebuddy.description.ByteCodeElement
        public boolean isVisibleTo(TypeDescription typeDescription) {
            return (isVirtual() || getDeclaringType().asErasure().isVisibleTo(typeDescription)) && (isPublic() || typeDescription.equals(getDeclaringType().asErasure()) || ((isProtected() && getDeclaringType().asErasure().isAssignableFrom(typeDescription)) || ((!isPrivate() && typeDescription.isSamePackage(getDeclaringType().asErasure())) || (isPrivate() && typeDescription.isNestMateOf(getDeclaringType().asErasure())))));
        }

        @Override // net.bytebuddy.description.ByteCodeElement
        public boolean isAccessibleTo(TypeDescription typeDescription) {
            return ((isVirtual() || getDeclaringType().asErasure().isVisibleTo(typeDescription)) && (isPublic() || typeDescription.equals(getDeclaringType().asErasure()) || (!isPrivate() && typeDescription.isSamePackage(getDeclaringType().asErasure())))) || (isPrivate() && typeDescription.isNestMateOf(getDeclaringType().asErasure()));
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isVirtual() {
            return (isConstructor() || isPrivate() || isStatic() || isTypeInitializer()) ? false : true;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isDefaultMethod() {
            return (isAbstract() || isBridge() || !getDeclaringType().isInterface()) ? false : true;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isSpecializableFor(TypeDescription typeDescription) {
            if (isStatic()) {
                return false;
            }
            if (isPrivate() || isConstructor()) {
                return getDeclaringType().equals(typeDescription);
            }
            return !isAbstract() && getDeclaringType().asErasure().isAssignableFrom(typeDescription);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        @MaybeNull
        public <T> T getDefaultValue(Class<T> cls) {
            return cls.cast(getDefaultValue());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isInvokableOn(TypeDescription typeDescription) {
            return !isStatic() && !isTypeInitializer() && isVisibleTo(typeDescription) && (!isVirtual() ? !getDeclaringType().asErasure().equals(typeDescription) : !getDeclaringType().asErasure().isAssignableFrom(typeDescription));
        }

        private boolean isBootstrap(TypeDescription typeDescription) {
            TypeList typeListAsErasures = getParameters().asTypeList().asErasures();
            int size = typeListAsErasures.size();
            if (size == 0) {
                return false;
            }
            if (size == 1) {
                return typeListAsErasures.getOnly().represents(Object[].class);
            }
            if (size == 2) {
                return JavaType.METHOD_HANDLES_LOOKUP.getTypeStub().isAssignableTo((TypeDescription) typeListAsErasures.get(0)) && ((TypeDescription) typeListAsErasures.get(1)).represents(Object[].class);
            }
            if (size == 3) {
                if (!JavaType.METHOD_HANDLES_LOOKUP.getTypeStub().isAssignableTo((TypeDescription) typeListAsErasures.get(0))) {
                    return false;
                }
                if (((TypeDescription) typeListAsErasures.get(1)).represents(Object.class) || ((TypeDescription) typeListAsErasures.get(1)).represents(String.class)) {
                    return (((TypeDescription) typeListAsErasures.get(2)).isArray() && ((TypeDescription) typeListAsErasures.get(2)).getComponentType().isAssignableFrom(typeDescription)) || ((TypeDescription) typeListAsErasures.get(2)).isAssignableFrom(typeDescription);
                }
                return false;
            }
            if (JavaType.METHOD_HANDLES_LOOKUP.getTypeStub().isAssignableTo((TypeDescription) typeListAsErasures.get(0))) {
                return (((TypeDescription) typeListAsErasures.get(1)).represents(Object.class) || ((TypeDescription) typeListAsErasures.get(1)).represents(String.class)) && ((TypeDescription) typeListAsErasures.get(2)).isAssignableFrom(typeDescription);
            }
            return false;
        }

        private boolean isBootstrapping(List<? extends TypeDefinition> list) {
            TypeList typeListAsErasures = getParameters().asTypeList().asErasures();
            if (typeListAsErasures.size() < 4) {
                if (list.isEmpty()) {
                    return true;
                }
                if (!((TypeDescription) typeListAsErasures.get(typeListAsErasures.size() - 1)).isArray()) {
                    return false;
                }
                Iterator<? extends TypeDefinition> it = list.iterator();
                while (it.hasNext()) {
                    if (!it.next().asErasure().isAssignableTo(((TypeDescription) typeListAsErasures.get(typeListAsErasures.size() - 1)).getComponentType())) {
                        return false;
                    }
                }
                return true;
            }
            Iterator it2 = typeListAsErasures.subList(3, typeListAsErasures.size()).iterator();
            for (TypeDefinition typeDefinition : list) {
                if (!it2.hasNext()) {
                    return false;
                }
                TypeDescription typeDescription = (TypeDescription) it2.next();
                if (!it2.hasNext() && typeDescription.isArray()) {
                    return true;
                }
                if (!typeDefinition.asErasure().isAssignableTo(typeDescription)) {
                    return false;
                }
            }
            if (it2.hasNext()) {
                return ((TypeDescription) it2.next()).isArray() && !it2.hasNext();
            }
            return true;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isInvokeBootstrap() {
            TypeDescription typeDescriptionAsErasure = getReturnType().asErasure();
            if (isMethod()) {
                if (!isStatic()) {
                    return false;
                }
                if (!JavaType.CALL_SITE.getTypeStub().isAssignableFrom(typeDescriptionAsErasure) && !JavaType.CALL_SITE.getTypeStub().isAssignableTo(typeDescriptionAsErasure)) {
                    return false;
                }
            }
            if (!isConstructor() || JavaType.CALL_SITE.getTypeStub().isAssignableFrom(getDeclaringType().asErasure())) {
                return isBootstrap(JavaType.METHOD_TYPE.getTypeStub());
            }
            return false;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isInvokeBootstrap(List<? extends TypeDefinition> list) {
            return isInvokeBootstrap() && isBootstrapping(list);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isConstantBootstrap() {
            return isBootstrap(TypeDescription.ForLoadedType.of(Class.class));
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isConstantBootstrap(List<? extends TypeDefinition> list) {
            return isConstantBootstrap() && isBootstrapping(list);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isDefaultValue() {
            return !isConstructor() && !isStatic() && getReturnType().asErasure().isAnnotationReturnType() && getParameters().isEmpty();
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isDefaultValue(AnnotationValue<?, ?> annotationValue) {
            if (!isDefaultValue()) {
                return false;
            }
            TypeDescription typeDescriptionAsErasure = getReturnType().asErasure();
            Object objResolve = annotationValue.resolve();
            return (typeDescriptionAsErasure.represents(Boolean.TYPE) && (objResolve instanceof Boolean)) || (typeDescriptionAsErasure.represents(Byte.TYPE) && (objResolve instanceof Byte)) || ((typeDescriptionAsErasure.represents(Character.TYPE) && (objResolve instanceof Character)) || ((typeDescriptionAsErasure.represents(Short.TYPE) && (objResolve instanceof Short)) || ((typeDescriptionAsErasure.represents(Integer.TYPE) && (objResolve instanceof Integer)) || ((typeDescriptionAsErasure.represents(Long.TYPE) && (objResolve instanceof Long)) || ((typeDescriptionAsErasure.represents(Float.TYPE) && (objResolve instanceof Float)) || ((typeDescriptionAsErasure.represents(Double.TYPE) && (objResolve instanceof Double)) || ((typeDescriptionAsErasure.represents(String.class) && (objResolve instanceof String)) || ((typeDescriptionAsErasure.isAssignableTo(Enum.class) && (objResolve instanceof EnumerationDescription) && isEnumerationType(typeDescriptionAsErasure, (EnumerationDescription) objResolve)) || ((typeDescriptionAsErasure.isAssignableTo(Annotation.class) && (objResolve instanceof AnnotationDescription) && isAnnotationType(typeDescriptionAsErasure, (AnnotationDescription) objResolve)) || ((typeDescriptionAsErasure.represents(Class.class) && (objResolve instanceof TypeDescription)) || ((typeDescriptionAsErasure.represents(boolean[].class) && (objResolve instanceof boolean[])) || ((typeDescriptionAsErasure.represents(byte[].class) && (objResolve instanceof byte[])) || ((typeDescriptionAsErasure.represents(char[].class) && (objResolve instanceof char[])) || ((typeDescriptionAsErasure.represents(short[].class) && (objResolve instanceof short[])) || ((typeDescriptionAsErasure.represents(int[].class) && (objResolve instanceof int[])) || ((typeDescriptionAsErasure.represents(long[].class) && (objResolve instanceof long[])) || ((typeDescriptionAsErasure.represents(float[].class) && (objResolve instanceof float[])) || ((typeDescriptionAsErasure.represents(double[].class) && (objResolve instanceof double[])) || ((typeDescriptionAsErasure.represents(String[].class) && (objResolve instanceof String[])) || ((typeDescriptionAsErasure.isAssignableTo(Enum[].class) && (objResolve instanceof EnumerationDescription[]) && isEnumerationType(typeDescriptionAsErasure.getComponentType(), (EnumerationDescription[]) objResolve)) || ((typeDescriptionAsErasure.isAssignableTo(Annotation[].class) && (objResolve instanceof AnnotationDescription[]) && isAnnotationType(typeDescriptionAsErasure.getComponentType(), (AnnotationDescription[]) objResolve)) || (typeDescriptionAsErasure.represents(Class[].class) && (objResolve instanceof TypeDescription[])))))))))))))))))))))));
        }

        private static boolean isEnumerationType(TypeDescription typeDescription, EnumerationDescription... enumerationDescriptionArr) {
            for (EnumerationDescription enumerationDescription : enumerationDescriptionArr) {
                if (!enumerationDescription.getEnumerationType().equals(typeDescription)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isAnnotationType(TypeDescription typeDescription, AnnotationDescription... annotationDescriptionArr) {
            for (AnnotationDescription annotationDescription : annotationDescriptionArr) {
                if (!annotationDescription.getAnnotationType().equals(typeDescription)) {
                    return false;
                }
            }
            return true;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        @MaybeNull
        public TypeVariableSource getEnclosingSource() {
            return isStatic() ? TypeVariableSource.UNDEFINED : getDeclaringType().asErasure();
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public <T> T accept(TypeVariableSource.Visitor<T> visitor) {
            return visitor.onMethod(asDefined());
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public boolean isGenerified() {
            return !getTypeVariables().isEmpty();
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public Token asToken(ElementMatcher<? super TypeDescription> elementMatcher) {
            TypeDescription.Generic receiverType = getReceiverType();
            return new Token(getInternalName(), getModifiers(), getTypeVariables().asTokenList(elementMatcher), (TypeDescription.Generic) getReturnType().accept(new TypeDescription.Generic.Visitor.Substitutor.ForDetachment(elementMatcher)), getParameters().asTokenList(elementMatcher), getExceptionTypes().accept(new TypeDescription.Generic.Visitor.Substitutor.ForDetachment(elementMatcher)), getDeclaredAnnotations(), getDefaultValue(), receiverType == null ? TypeDescription.Generic.UNDEFINED : (TypeDescription.Generic) receiverType.accept(new TypeDescription.Generic.Visitor.Substitutor.ForDetachment(elementMatcher)));
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public SignatureToken asSignatureToken() {
            return new SignatureToken(getInternalName(), getReturnType().asErasure(), getParameters().asTypeList().asErasures());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeToken asTypeToken() {
            return new TypeToken(getReturnType().asErasure(), getParameters().asTypeList().asErasures());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public boolean isBridgeCompatible(TypeToken typeToken) {
            TypeList typeListAsErasures = getParameters().asTypeList().asErasures();
            List<TypeDescription> parameterTypes = typeToken.getParameterTypes();
            if (typeListAsErasures.size() != parameterTypes.size()) {
                return false;
            }
            for (int i = 0; i < typeListAsErasures.size(); i++) {
                if (!typeListAsErasures.get(i).equals(parameterTypes.get(i)) && (typeListAsErasures.get(i).isPrimitive() || parameterTypes.get(i).isPrimitive())) {
                    return false;
                }
            }
            TypeDescription typeDescriptionAsErasure = getReturnType().asErasure();
            TypeDescription returnType = typeToken.getReturnType();
            return typeDescriptionAsErasure.equals(returnType) || !(typeDescriptionAsErasure.isPrimitive() || returnType.isPrimitive());
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : ((((((getDeclaringType().hashCode() + 17) * 31) + getInternalName().hashCode()) * 31) + getReturnType().asErasure().hashCode()) * 31) + getParameters().asTypeList().asErasures().hashCode();
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
            if (!(obj instanceof MethodDescription)) {
                return false;
            }
            MethodDescription methodDescription = (MethodDescription) obj;
            return getInternalName().equals(methodDescription.getInternalName()) && getDeclaringType().equals(methodDescription.getDeclaringType()) && getReturnType().asErasure().equals(methodDescription.getReturnType().asErasure()) && getParameters().asTypeList().asErasures().equals(methodDescription.getParameters().asTypeList().asErasures());
        }

        @Override // net.bytebuddy.description.NamedElement.WithGenericName
        public String toGenericString() {
            StringBuilder sb = new StringBuilder();
            int modifiers = getModifiers() & SOURCE_MODIFIERS;
            if (modifiers != 0) {
                sb.append(Modifier.toString(modifiers)).append(StringUtil.SPACE);
            }
            if (isMethod()) {
                sb.append(getReturnType().getActualName()).append(StringUtil.SPACE);
                sb.append(getDeclaringType().asErasure().getActualName()).append('.');
            }
            sb.append(getName()).append('(');
            boolean z = true;
            boolean z2 = true;
            for (TypeDescription.Generic generic : getParameters().asTypeList()) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(',');
                }
                sb.append(generic.getActualName());
            }
            sb.append(')');
            TypeList.Generic<TypeDescription.Generic> exceptionTypes = getExceptionTypes();
            if (!exceptionTypes.isEmpty()) {
                sb.append(" throws ");
                for (TypeDescription.Generic generic2 : exceptionTypes) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(',');
                    }
                    sb.append(generic2.getActualName());
                }
            }
            return sb.toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int modifiers = getModifiers() & SOURCE_MODIFIERS;
            if (modifiers != 0) {
                sb.append(Modifier.toString(modifiers)).append(StringUtil.SPACE);
            }
            if (isMethod()) {
                sb.append(getReturnType().asErasure().getActualName()).append(StringUtil.SPACE);
                sb.append(getDeclaringType().asErasure().getActualName()).append('.');
            }
            sb.append(getName()).append('(');
            boolean z = true;
            boolean z2 = true;
            for (TypeDescription typeDescription : getParameters().asTypeList().asErasures()) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(',');
                }
                sb.append(typeDescription.getActualName());
            }
            sb.append(')');
            TypeList<TypeDescription> typeListAsErasures = getExceptionTypes().asErasures();
            if (!typeListAsErasures.isEmpty()) {
                sb.append(" throws ");
                for (TypeDescription typeDescription2 : typeListAsErasures) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(',');
                    }
                    sb.append(typeDescription2.getActualName());
                }
            }
            return sb.toString();
        }

        @Override // net.bytebuddy.description.TypeVariableSource.AbstractBase
        protected String toSafeString() {
            StringBuilder sb = new StringBuilder();
            int modifiers = getModifiers() & SOURCE_MODIFIERS;
            if (modifiers != 0) {
                sb.append(Modifier.toString(modifiers)).append(StringUtil.SPACE);
            }
            if (isMethod()) {
                sb.append(getReturnType().asErasure().getActualName()).append(StringUtil.SPACE);
                sb.append(getDeclaringType().asErasure().getActualName()).append('.');
            }
            return sb.append(getName()).append("(?)").toString();
        }
    }

    public static class ForLoadedConstructor extends InDefinedShape.AbstractBase.ForLoadedExecutable<Constructor<?>> implements ParameterDescription.ForLoadedParameter.ParameterAnnotationSource {
        private transient /* synthetic */ AnnotationList declaredAnnotations;
        private transient /* synthetic */ Annotation[][] parameterAnnotations;
        private transient /* synthetic */ ParameterList parameters;

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isConstructor() {
            return true;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isTypeInitializer() {
            return false;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean represents(Method method) {
            return false;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase.ForLoadedExecutable, net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public /* bridge */ /* synthetic */ TypeDescription.Generic getReceiverType() {
            return super.getReceiverType();
        }

        public ForLoadedConstructor(Constructor<?> constructor) {
            super(constructor);
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription getDeclaringType() {
            return TypeDescription.ForLoadedType.of(((Constructor) this.executable).getDeclaringClass());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeDescription.Generic getReturnType() {
            return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Void.TYPE);
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
        @CachedReturnPlugin.Enhance("parameters")
        public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
            ParameterList<ParameterDescription.InDefinedShape> parameterListOf = this.parameters != null ? null : ParameterList.ForLoadedExecutable.of((Constructor<?>) this.executable, (ParameterDescription.ForLoadedParameter.ParameterAnnotationSource) this);
            if (parameterListOf == null) {
                return this.parameters;
            }
            this.parameters = parameterListOf;
            return parameterListOf;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeList.Generic getExceptionTypes() {
            return new TypeList.Generic.OfConstructorExceptionTypes((Constructor) this.executable);
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean represents(Constructor<?> constructor) {
            return ((Constructor) this.executable).equals(constructor) || equals(new ForLoadedConstructor(constructor));
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return ((Constructor) this.executable).getName();
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return ((Constructor) this.executable).getModifiers();
        }

        @Override // net.bytebuddy.description.ModifierReviewable.AbstractBase, net.bytebuddy.description.ModifierReviewable
        public boolean isSynthetic() {
            return ((Constructor) this.executable).isSynthetic();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return "<init>";
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            return Type.getConstructorDescriptor((Constructor) this.executable);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        @AlwaysNull
        public AnnotationValue<?, ?> getDefaultValue() {
            return AnnotationValue.UNDEFINED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        @CachedReturnPlugin.Enhance("declaredAnnotations")
        public AnnotationList getDeclaredAnnotations() {
            AnnotationList.ForLoadedAnnotations forLoadedAnnotations = this.declaredAnnotations != null ? null : new AnnotationList.ForLoadedAnnotations(((Constructor) this.executable).getDeclaredAnnotations());
            if (forLoadedAnnotations == null) {
                return this.declaredAnnotations;
            }
            this.declaredAnnotations = forLoadedAnnotations;
            return forLoadedAnnotations;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return TypeList.Generic.ForLoadedTypes.OfTypeVariables.of((GenericDeclaration) this.executable);
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.ForLoadedParameter.ParameterAnnotationSource
        @CachedReturnPlugin.Enhance("parameterAnnotations")
        public Annotation[][] getParameterAnnotations() {
            Annotation[][] parameterAnnotations = this.parameterAnnotations != null ? null : ((Constructor) this.executable).getParameterAnnotations();
            if (parameterAnnotations == null) {
                return this.parameterAnnotations;
            }
            this.parameterAnnotations = parameterAnnotations;
            return parameterAnnotations;
        }
    }

    public static class ForLoadedMethod extends InDefinedShape.AbstractBase.ForLoadedExecutable<Method> implements ParameterDescription.ForLoadedParameter.ParameterAnnotationSource {
        private transient /* synthetic */ AnnotationList declaredAnnotations;
        private transient /* synthetic */ Annotation[][] parameterAnnotations;
        private transient /* synthetic */ ParameterList parameters;

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isConstructor() {
            return false;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isTypeInitializer() {
            return false;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean represents(Constructor<?> constructor) {
            return false;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase.ForLoadedExecutable, net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public /* bridge */ /* synthetic */ TypeDescription.Generic getReceiverType() {
            return super.getReceiverType();
        }

        public ForLoadedMethod(Method method) {
            super(method);
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription getDeclaringType() {
            return TypeDescription.ForLoadedType.of(((Method) this.executable).getDeclaringClass());
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeDescription.Generic getReturnType() {
            if (TypeDescription.AbstractBase.RAW_TYPES) {
                return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(((Method) this.executable).getReturnType());
            }
            return new TypeDescription.Generic.LazyProjection.ForLoadedReturnType((Method) this.executable);
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
        @CachedReturnPlugin.Enhance("parameters")
        public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
            ParameterList<ParameterDescription.InDefinedShape> parameterListOf = this.parameters != null ? null : ParameterList.ForLoadedExecutable.of((Method) this.executable, (ParameterDescription.ForLoadedParameter.ParameterAnnotationSource) this);
            if (parameterListOf == null) {
                return this.parameters;
            }
            this.parameters = parameterListOf;
            return parameterListOf;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeList.Generic getExceptionTypes() {
            if (TypeDescription.AbstractBase.RAW_TYPES) {
                return new TypeList.Generic.ForLoadedTypes(((Method) this.executable).getExceptionTypes());
            }
            return new TypeList.Generic.OfMethodExceptionTypes((Method) this.executable);
        }

        @Override // net.bytebuddy.description.ModifierReviewable.AbstractBase, net.bytebuddy.description.ModifierReviewable.ForMethodDescription
        public boolean isBridge() {
            return ((Method) this.executable).isBridge();
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean represents(Method method) {
            return ((Method) this.executable).equals(method) || equals(new ForLoadedMethod(method));
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return ((Method) this.executable).getName();
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return ((Method) this.executable).getModifiers();
        }

        @Override // net.bytebuddy.description.ModifierReviewable.AbstractBase, net.bytebuddy.description.ModifierReviewable
        public boolean isSynthetic() {
            return ((Method) this.executable).isSynthetic();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return ((Method) this.executable).getName();
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            return Type.getMethodDescriptor((Method) this.executable);
        }

        public Method getLoadedMethod() {
            return (Method) this.executable;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        @CachedReturnPlugin.Enhance("declaredAnnotations")
        public AnnotationList getDeclaredAnnotations() {
            AnnotationList.ForLoadedAnnotations forLoadedAnnotations = this.declaredAnnotations != null ? null : new AnnotationList.ForLoadedAnnotations(((Method) this.executable).getDeclaredAnnotations());
            if (forLoadedAnnotations == null) {
                return this.declaredAnnotations;
            }
            this.declaredAnnotations = forLoadedAnnotations;
            return forLoadedAnnotations;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        @MaybeNull
        public AnnotationValue<?, ?> getDefaultValue() {
            Object defaultValue = ((Method) this.executable).getDefaultValue();
            if (defaultValue == null) {
                return AnnotationValue.UNDEFINED;
            }
            return AnnotationDescription.ForLoadedAnnotation.asValue(defaultValue, ((Method) this.executable).getReturnType());
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            if (TypeDescription.AbstractBase.RAW_TYPES) {
                return new TypeList.Generic.Empty();
            }
            return TypeList.Generic.ForLoadedTypes.OfTypeVariables.of((GenericDeclaration) this.executable);
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.ForLoadedParameter.ParameterAnnotationSource
        @CachedReturnPlugin.Enhance("parameterAnnotations")
        public Annotation[][] getParameterAnnotations() {
            Annotation[][] parameterAnnotations = this.parameterAnnotations != null ? null : ((Method) this.executable).getParameterAnnotations();
            if (parameterAnnotations == null) {
                return this.parameterAnnotations;
            }
            this.parameterAnnotations = parameterAnnotations;
            return parameterAnnotations;
        }
    }

    public static class Latent extends InDefinedShape.AbstractBase {
        private final List<? extends AnnotationDescription> declaredAnnotations;
        private final TypeDescription declaringType;

        @MaybeNull
        private final AnnotationValue<?, ?> defaultValue;
        private final List<? extends TypeDescription.Generic> exceptionTypes;
        private final String internalName;
        private final int modifiers;
        private final List<? extends ParameterDescription.Token> parameterTokens;

        @MaybeNull
        private final TypeDescription.Generic receiverType;
        private final TypeDescription.Generic returnType;
        private final List<? extends TypeVariableToken> typeVariables;

        public Latent(TypeDescription typeDescription, Token token) {
            this(typeDescription, token.getName(), token.getModifiers(), token.getTypeVariableTokens(), token.getReturnType(), token.getParameterTokens(), token.getExceptionTypes(), token.getAnnotations(), token.getDefaultValue(), token.getReceiverType());
        }

        public Latent(TypeDescription typeDescription, String str, int i, List<? extends TypeVariableToken> list, TypeDescription.Generic generic, List<? extends ParameterDescription.Token> list2, List<? extends TypeDescription.Generic> list3, List<? extends AnnotationDescription> list4, @MaybeNull AnnotationValue<?, ?> annotationValue, @MaybeNull TypeDescription.Generic generic2) {
            this.declaringType = typeDescription;
            this.internalName = str;
            this.modifiers = i;
            this.typeVariables = list;
            this.returnType = generic;
            this.parameterTokens = list2;
            this.exceptionTypes = list3;
            this.declaredAnnotations = list4;
            this.defaultValue = annotationValue;
            this.receiverType = generic2;
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return TypeList.Generic.ForDetachedTypes.attachVariables(this, this.typeVariables);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeDescription.Generic getReturnType() {
            return (TypeDescription.Generic) this.returnType.accept(TypeDescription.Generic.Visitor.Substitutor.ForAttachment.of(this));
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
        public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
            return new ParameterList.ForTokens(this, this.parameterTokens);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeList.Generic getExceptionTypes() {
            return TypeList.Generic.ForDetachedTypes.attach(this, this.exceptionTypes);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Explicit(this.declaredAnnotations);
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return this.internalName;
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription getDeclaringType() {
            return this.declaringType;
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.modifiers;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        @MaybeNull
        public AnnotationValue<?, ?> getDefaultValue() {
            return this.defaultValue;
        }

        @Override // net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase, net.bytebuddy.description.method.MethodDescription
        @MaybeNull
        public TypeDescription.Generic getReceiverType() {
            TypeDescription.Generic generic = this.receiverType;
            if (generic == null) {
                return super.getReceiverType();
            }
            return (TypeDescription.Generic) generic.accept(TypeDescription.Generic.Visitor.Substitutor.ForAttachment.of(this));
        }

        public static class TypeInitializer extends InDefinedShape.AbstractBase {
            private final TypeDescription typeDescription;

            @Override // net.bytebuddy.description.ModifierReviewable
            public int getModifiers() {
                return 8;
            }

            public TypeInitializer(TypeDescription typeDescription) {
                this.typeDescription = typeDescription;
            }

            @Override // net.bytebuddy.description.method.MethodDescription
            public TypeDescription.Generic getReturnType() {
                return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Void.TYPE);
            }

            @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
            public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
                return new ParameterList.Empty();
            }

            @Override // net.bytebuddy.description.method.MethodDescription
            public TypeList.Generic getExceptionTypes() {
                return new TypeList.Generic.Empty();
            }

            @Override // net.bytebuddy.description.method.MethodDescription
            @AlwaysNull
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

            @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
            @Nonnull
            public TypeDescription getDeclaringType() {
                return this.typeDescription;
            }

            @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
            public String getInternalName() {
                return MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME;
            }
        }
    }

    public static class TypeSubstituting extends AbstractBase implements InGenericShape {
        private final TypeDescription.Generic declaringType;
        private final MethodDescription methodDescription;
        private final TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor;

        public TypeSubstituting(TypeDescription.Generic generic, MethodDescription methodDescription, TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            this.declaringType = generic;
            this.methodDescription = methodDescription;
            this.visitor = visitor;
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeDescription.Generic getReturnType() {
            return (TypeDescription.Generic) this.methodDescription.getReturnType().accept(this.visitor);
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return this.methodDescription.getTypeVariables().accept(this.visitor).filter(ElementMatchers.ofSort(TypeDefinition.Sort.VARIABLE));
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
        public ParameterList<ParameterDescription.InGenericShape> getParameters() {
            return new ParameterList.TypeSubstituting(this, this.methodDescription.getParameters(), this.visitor);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeList.Generic getExceptionTypes() {
            return new TypeList.Generic.ForDetachedTypes(this.methodDescription.getExceptionTypes(), this.visitor);
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        @MaybeNull
        public AnnotationValue<?, ?> getDefaultValue() {
            return this.methodDescription.getDefaultValue();
        }

        @Override // net.bytebuddy.description.method.MethodDescription
        public TypeDescription.Generic getReceiverType() {
            TypeDescription.Generic receiverType = this.methodDescription.getReceiverType();
            if (receiverType == null) {
                return TypeDescription.Generic.UNDEFINED;
            }
            return (TypeDescription.Generic) receiverType.accept(this.visitor);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return this.methodDescription.getDeclaredAnnotations();
        }

        @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription.Generic getDeclaringType() {
            return this.declaringType;
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.methodDescription.getModifiers();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return this.methodDescription.getInternalName();
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public InDefinedShape asDefined() {
            return this.methodDescription.asDefined();
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isConstructor() {
            return this.methodDescription.isConstructor();
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isMethod() {
            return this.methodDescription.isMethod();
        }

        @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.method.MethodDescription
        public boolean isTypeInitializer() {
            return this.methodDescription.isTypeInitializer();
        }
    }

    public static class Token implements ByteCodeElement.Token<Token> {
        private final List<? extends AnnotationDescription> annotations;

        @MaybeNull
        private final AnnotationValue<?, ?> defaultValue;
        private final List<? extends TypeDescription.Generic> exceptionTypes;
        private transient /* synthetic */ int hashCode;
        private final int modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f351name;
        private final List<? extends ParameterDescription.Token> parameterTokens;

        @MaybeNull
        private final TypeDescription.Generic receiverType;
        private final TypeDescription.Generic returnType;
        private final List<? extends TypeVariableToken> typeVariableTokens;

        @Override // net.bytebuddy.description.ByteCodeElement.Token
        public /* bridge */ /* synthetic */ ByteCodeElement.Token accept(TypeDescription.Generic.Visitor visitor) {
            return accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) visitor);
        }

        public Token(int i) {
            this("<init>", i, TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Void.TYPE));
        }

        public Token(String str, int i, TypeDescription.Generic generic) {
            this(str, i, generic, Collections.emptyList());
        }

        public Token(String str, int i, TypeDescription.Generic generic, List<? extends TypeDescription.Generic> list) {
            this(str, i, Collections.emptyList(), generic, new ParameterDescription.Token.TypeList(list), Collections.emptyList(), Collections.emptyList(), AnnotationValue.UNDEFINED, TypeDescription.Generic.UNDEFINED);
        }

        public Token(String str, int i, List<? extends TypeVariableToken> list, TypeDescription.Generic generic, List<? extends ParameterDescription.Token> list2, List<? extends TypeDescription.Generic> list3, List<? extends AnnotationDescription> list4, @MaybeNull AnnotationValue<?, ?> annotationValue, @MaybeNull TypeDescription.Generic generic2) {
            this.f351name = str;
            this.modifiers = i;
            this.typeVariableTokens = list;
            this.returnType = generic;
            this.parameterTokens = list2;
            this.exceptionTypes = list3;
            this.annotations = list4;
            this.defaultValue = annotationValue;
            this.receiverType = generic2;
        }

        public String getName() {
            return this.f351name;
        }

        public int getModifiers() {
            return this.modifiers;
        }

        public ByteCodeElement.Token.TokenList<TypeVariableToken> getTypeVariableTokens() {
            return new ByteCodeElement.Token.TokenList<>(this.typeVariableTokens);
        }

        public TypeDescription.Generic getReturnType() {
            return this.returnType;
        }

        public ByteCodeElement.Token.TokenList<ParameterDescription.Token> getParameterTokens() {
            return new ByteCodeElement.Token.TokenList<>(this.parameterTokens);
        }

        public TypeList.Generic getExceptionTypes() {
            return new TypeList.Generic.Explicit(this.exceptionTypes);
        }

        public AnnotationList getAnnotations() {
            return new AnnotationList.Explicit(this.annotations);
        }

        @MaybeNull
        public AnnotationValue<?, ?> getDefaultValue() {
            return this.defaultValue;
        }

        @MaybeNull
        public TypeDescription.Generic getReceiverType() {
            return this.receiverType;
        }

        @Override // net.bytebuddy.description.ByteCodeElement.Token
        public Token accept(TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            String str = this.f351name;
            int i = this.modifiers;
            List listAccept = getTypeVariableTokens().accept(visitor);
            TypeDescription.Generic generic = (TypeDescription.Generic) this.returnType.accept(visitor);
            List listAccept2 = getParameterTokens().accept(visitor);
            TypeList.Generic genericAccept = getExceptionTypes().accept(visitor);
            List<? extends AnnotationDescription> list = this.annotations;
            AnnotationValue<?, ?> annotationValue = this.defaultValue;
            TypeDescription.Generic generic2 = this.receiverType;
            return new Token(str, i, listAccept, generic, listAccept2, genericAccept, list, annotationValue, generic2 == null ? TypeDescription.Generic.UNDEFINED : (TypeDescription.Generic) generic2.accept(visitor));
        }

        public SignatureToken asSignatureToken(TypeDescription typeDescription) {
            TypeDescription.Generic.Visitor.Reducing reducing = new TypeDescription.Generic.Visitor.Reducing(typeDescription, this.typeVariableTokens);
            ArrayList arrayList = new ArrayList(this.parameterTokens.size());
            Iterator<? extends ParameterDescription.Token> it = this.parameterTokens.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getType().accept(reducing));
            }
            return new SignatureToken(this.f351name, (TypeDescription) this.returnType.accept(reducing), arrayList);
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = 0;
            if (this.hashCode == 0) {
                int iHashCode2 = ((((((((((((this.f351name.hashCode() * 31) + this.modifiers) * 31) + this.typeVariableTokens.hashCode()) * 31) + this.returnType.hashCode()) * 31) + this.parameterTokens.hashCode()) * 31) + this.exceptionTypes.hashCode()) * 31) + this.annotations.hashCode()) * 31;
                AnnotationValue<?, ?> annotationValue = this.defaultValue;
                int iHashCode3 = (iHashCode2 + (annotationValue != null ? annotationValue.hashCode() : 0)) * 31;
                TypeDescription.Generic generic = this.receiverType;
                iHashCode = (generic != null ? generic.hashCode() : 0) + iHashCode3;
            }
            if (iHashCode == 0) {
                return this.hashCode;
            }
            this.hashCode = iHashCode;
            return iHashCode;
        }

        public boolean equals(@MaybeNull Object obj) {
            AnnotationValue<?, ?> annotationValue;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Token token = (Token) obj;
            if (this.modifiers == token.modifiers && this.f351name.equals(token.f351name) && this.typeVariableTokens.equals(token.typeVariableTokens) && this.returnType.equals(token.returnType) && this.parameterTokens.equals(token.parameterTokens) && this.exceptionTypes.equals(token.exceptionTypes) && this.annotations.equals(token.annotations) && ((annotationValue = this.defaultValue) == null ? token.defaultValue == null : annotationValue.equals(token.defaultValue))) {
                TypeDescription.Generic generic = this.receiverType;
                if (generic != null) {
                    if (generic.equals(token.receiverType)) {
                        return true;
                    }
                } else if (token.receiverType == null) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "MethodDescription.Token{name='" + this.f351name + "', modifiers=" + this.modifiers + ", typeVariableTokens=" + this.typeVariableTokens + ", returnType=" + this.returnType + ", parameterTokens=" + this.parameterTokens + ", exceptionTypes=" + this.exceptionTypes + ", annotations=" + this.annotations + ", defaultValue=" + this.defaultValue + ", receiverType=" + this.receiverType + '}';
        }
    }

    public static class SignatureToken {
        private transient /* synthetic */ int hashCode;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f350name;
        private final List<? extends TypeDescription> parameterTypes;
        private final TypeDescription returnType;

        public SignatureToken(String str, TypeDescription typeDescription, TypeDescription... typeDescriptionArr) {
            this(str, typeDescription, (List<? extends TypeDescription>) Arrays.asList(typeDescriptionArr));
        }

        public SignatureToken(String str, TypeDescription typeDescription, List<? extends TypeDescription> list) {
            this.f350name = str;
            this.returnType = typeDescription;
            this.parameterTypes = list;
        }

        public String getName() {
            return this.f350name;
        }

        public TypeDescription getReturnType() {
            return this.returnType;
        }

        public List<TypeDescription> getParameterTypes() {
            return this.parameterTypes;
        }

        public TypeToken asTypeToken() {
            return new TypeToken(this.returnType, this.parameterTypes);
        }

        public String getDescriptor() {
            StringBuilder sb = new StringBuilder("(");
            Iterator<? extends TypeDescription> it = this.parameterTypes.iterator();
            while (it.hasNext()) {
                sb.append(it.next().getDescriptor());
            }
            return sb.append(')').append(this.returnType.getDescriptor()).toString();
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : (((this.f350name.hashCode() * 31) + this.returnType.hashCode()) * 31) + this.parameterTypes.hashCode();
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
            if (!(obj instanceof SignatureToken)) {
                return false;
            }
            SignatureToken signatureToken = (SignatureToken) obj;
            return this.f350name.equals(signatureToken.f350name) && this.returnType.equals(signatureToken.returnType) && this.parameterTypes.equals(signatureToken.parameterTypes);
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder().append(this.returnType).append(StringUtil.SPACE).append(this.f350name).append('(');
            boolean z = true;
            for (TypeDescription typeDescription : this.parameterTypes) {
                if (z) {
                    z = false;
                } else {
                    sbAppend.append(',');
                }
                sbAppend.append(typeDescription);
            }
            return sbAppend.append(')').toString();
        }
    }

    public static class TypeToken {
        private transient /* synthetic */ int hashCode;
        private final List<? extends TypeDescription> parameterTypes;
        private final TypeDescription returnType;

        public TypeToken(TypeDescription typeDescription, List<? extends TypeDescription> list) {
            this.returnType = typeDescription;
            this.parameterTypes = list;
        }

        public TypeDescription getReturnType() {
            return this.returnType;
        }

        public List<TypeDescription> getParameterTypes() {
            return this.parameterTypes;
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : (this.returnType.hashCode() * 31) + this.parameterTypes.hashCode();
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
            if (!(obj instanceof TypeToken)) {
                return false;
            }
            TypeToken typeToken = (TypeToken) obj;
            return this.returnType.equals(typeToken.returnType) && this.parameterTypes.equals(typeToken.parameterTypes);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("(");
            Iterator<? extends TypeDescription> it = this.parameterTypes.iterator();
            while (it.hasNext()) {
                sb.append(it.next().getDescriptor());
            }
            return sb.append(')').append(this.returnType.getDescriptor()).toString();
        }
    }
}
