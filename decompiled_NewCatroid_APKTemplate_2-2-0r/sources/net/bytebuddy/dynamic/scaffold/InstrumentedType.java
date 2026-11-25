package net.bytebuddy.dynamic.scaffold;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.type.PackageDescription;
import net.bytebuddy.description.type.RecordComponentDescription;
import net.bytebuddy.description.type.RecordComponentList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.description.type.TypeVariableToken;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.dynamic.Transformer;
import net.bytebuddy.implementation.LoadedTypeInitializer;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes12.dex */
public interface InstrumentedType extends TypeDescription {

    public interface Prepareable {

        public enum NoOp implements Prepareable {
            INSTANCE;

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }
        }

        InstrumentedType prepare(InstrumentedType instrumentedType);
    }

    public interface WithFlexibleName extends InstrumentedType {
        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withAnnotations(List<? extends AnnotationDescription> list);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withAnonymousClass(boolean z);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withAuxiliaryField(FieldDescription.Token token, Object obj);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withDeclaredTypes(TypeList typeList);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withDeclaringType(@MaybeNull TypeDescription typeDescription);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withEnclosingMethod(MethodDescription.InDefinedShape inDefinedShape);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withEnclosingType(@MaybeNull TypeDescription typeDescription);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withField(FieldDescription.Token token);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withInitializer(LoadedTypeInitializer loadedTypeInitializer);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withInitializer(ByteCodeAppender byteCodeAppender);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withInterfaces(TypeList.Generic generic);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withLocalClass(boolean z);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withMethod(MethodDescription.Token token);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withModifiers(int i);

        WithFlexibleName withName(String str);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withNestHost(TypeDescription typeDescription);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withNestMembers(TypeList typeList);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withPermittedSubclasses(@MaybeNull TypeList typeList);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withRecord(boolean z);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withRecordComponent(RecordComponentDescription.Token token);

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        WithFlexibleName withTypeVariable(TypeVariableToken typeVariableToken);

        WithFlexibleName withTypeVariables(ElementMatcher<? super TypeDescription.Generic> elementMatcher, Transformer<TypeVariableToken> transformer);
    }

    LoadedTypeInitializer getLoadedTypeInitializer();

    TypeInitializer getTypeInitializer();

    TypeDescription validated();

    InstrumentedType withAnnotations(List<? extends AnnotationDescription> list);

    InstrumentedType withAnonymousClass(boolean z);

    InstrumentedType withAuxiliaryField(FieldDescription.Token token, Object obj);

    InstrumentedType withDeclaredTypes(TypeList typeList);

    InstrumentedType withDeclaringType(@MaybeNull TypeDescription typeDescription);

    InstrumentedType withEnclosingMethod(MethodDescription.InDefinedShape inDefinedShape);

    InstrumentedType withEnclosingType(TypeDescription typeDescription);

    InstrumentedType withField(FieldDescription.Token token);

    InstrumentedType withInitializer(LoadedTypeInitializer loadedTypeInitializer);

    InstrumentedType withInitializer(ByteCodeAppender byteCodeAppender);

    InstrumentedType withInterfaces(TypeList.Generic generic);

    InstrumentedType withLocalClass(boolean z);

    InstrumentedType withMethod(MethodDescription.Token token);

    InstrumentedType withModifiers(int i);

    InstrumentedType withNestHost(TypeDescription typeDescription);

    InstrumentedType withNestMembers(TypeList typeList);

    InstrumentedType withPermittedSubclasses(@MaybeNull TypeList typeList);

    InstrumentedType withRecord(boolean z);

    InstrumentedType withRecordComponent(RecordComponentDescription.Token token);

    InstrumentedType withTypeVariable(TypeVariableToken typeVariableToken);

    public interface Factory {
        WithFlexibleName represent(TypeDescription typeDescription);

        WithFlexibleName subclass(String str, int i, TypeDescription.Generic generic);

        public enum Default implements Factory {
            MODIFIABLE { // from class: net.bytebuddy.dynamic.scaffold.InstrumentedType.Factory.Default.1
                @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Factory
                public WithFlexibleName represent(TypeDescription typeDescription) {
                    List listEmptyList;
                    String name2 = typeDescription.getName();
                    int modifiers = typeDescription.getModifiers();
                    TypeDescription.Generic superClass = typeDescription.getSuperClass();
                    ByteCodeElement.Token.TokenList<TypeVariableToken> tokenListAsTokenList = typeDescription.getTypeVariables().asTokenList(ElementMatchers.is(typeDescription));
                    TypeList.Generic genericAccept = typeDescription.getInterfaces().accept(TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(typeDescription));
                    ByteCodeElement.Token.TokenList<FieldDescription.Token> tokenListAsTokenList2 = typeDescription.getDeclaredFields().asTokenList(ElementMatchers.is(typeDescription));
                    Map mapEmptyMap = Collections.emptyMap();
                    ByteCodeElement.Token.TokenList<MethodDescription.Token> tokenListAsTokenList3 = typeDescription.getDeclaredMethods().asTokenList(ElementMatchers.is(typeDescription));
                    ByteCodeElement.Token.TokenList<RecordComponentDescription.Token> tokenListAsTokenList4 = typeDescription.getRecordComponents().asTokenList(ElementMatchers.is(typeDescription));
                    AnnotationList declaredAnnotations = typeDescription.getDeclaredAnnotations();
                    TypeInitializer.None none = TypeInitializer.None.INSTANCE;
                    LoadedTypeInitializer.NoOp noOp = LoadedTypeInitializer.NoOp.INSTANCE;
                    TypeDescription declaringType = typeDescription.getDeclaringType();
                    MethodDescription.InDefinedShape enclosingMethod = typeDescription.getEnclosingMethod();
                    TypeDescription enclosingType = typeDescription.getEnclosingType();
                    TypeList declaredTypes = typeDescription.getDeclaredTypes();
                    TypeList permittedSubtypes = typeDescription.isSealed() ? typeDescription.getPermittedSubtypes() : TypeList.UNDEFINED;
                    boolean zIsAnonymousType = typeDescription.isAnonymousType();
                    boolean zIsLocalType = typeDescription.isLocalType();
                    boolean zIsRecord = typeDescription.isRecord();
                    TypeDescription nestHost = typeDescription.isNestHost() ? TargetType.DESCRIPTION : typeDescription.getNestHost();
                    if (typeDescription.isNestHost()) {
                        listEmptyList = typeDescription.getNestMembers().filter(ElementMatchers.not(ElementMatchers.is(typeDescription)));
                    } else {
                        listEmptyList = Collections.emptyList();
                    }
                    return new Default(name2, modifiers, superClass, tokenListAsTokenList, genericAccept, tokenListAsTokenList2, mapEmptyMap, tokenListAsTokenList3, tokenListAsTokenList4, declaredAnnotations, none, noOp, declaringType, enclosingMethod, enclosingType, declaredTypes, permittedSubtypes, zIsAnonymousType, zIsLocalType, zIsRecord, nestHost, listEmptyList);
                }
            },
            FROZEN { // from class: net.bytebuddy.dynamic.scaffold.InstrumentedType.Factory.Default.2
                @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Factory
                public WithFlexibleName represent(TypeDescription typeDescription) {
                    return new Frozen(typeDescription, LoadedTypeInitializer.NoOp.INSTANCE);
                }
            };

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Factory
            public WithFlexibleName subclass(String str, int i, TypeDescription.Generic generic) {
                return new Default(str, i, generic, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyMap(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), TypeInitializer.None.INSTANCE, LoadedTypeInitializer.NoOp.INSTANCE, TypeDescription.UNDEFINED, MethodDescription.UNDEFINED, TypeDescription.UNDEFINED, Collections.emptyList(), TypeList.UNDEFINED, false, false, false, TargetType.DESCRIPTION, Collections.emptyList());
            }
        }
    }

    public static class Default extends TypeDescription.AbstractBase.OfSimpleType implements WithFlexibleName {
        private static final Set<String> KEYWORDS = new HashSet(Arrays.asList("abstract", PsiKeyword.CONTINUE, PsiKeyword.FOR, PsiKeyword.NEW, PsiKeyword.SWITCH, PsiKeyword.ASSERT, "default", PsiKeyword.GOTO, "package", "synchronized", "boolean", PsiKeyword.DO, PsiKeyword.IF, "private", "this", PsiKeyword.BREAK, "double", PsiKeyword.IMPLEMENTS, "protected", PsiKeyword.THROW, "byte", PsiKeyword.ELSE, PsiKeyword.IMPORT, "public", PsiKeyword.THROWS, PsiKeyword.CASE, PsiKeyword.ENUM, PsiKeyword.INSTANCEOF, "return", "transient", PsiKeyword.CATCH, PsiKeyword.EXTENDS, "int", "short", PsiKeyword.TRY, PsiKeyword.CHAR, "final", PsiKeyword.INTERFACE, "static", PsiKeyword.VOID, "class", PsiKeyword.FINALLY, "long", "strictfp", "volatile", PsiKeyword.CONST, "float", "native", PsiKeyword.SUPER, PsiKeyword.WHILE));
        private final List<? extends AnnotationDescription> annotationDescriptions;
        private final boolean anonymousClass;
        private final Map<String, Object> auxiliaryFields;
        private final List<? extends TypeDescription> declaredTypes;

        @MaybeNull
        private final TypeDescription declaringType;

        @MaybeNull
        private final MethodDescription.InDefinedShape enclosingMethod;

        @MaybeNull
        private final TypeDescription enclosingType;
        private final List<? extends FieldDescription.Token> fieldTokens;
        private final List<? extends TypeDescription.Generic> interfaceTypes;
        private final LoadedTypeInitializer loadedTypeInitializer;
        private final boolean localClass;
        private final List<? extends MethodDescription.Token> methodTokens;
        private final int modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f360name;
        private final TypeDescription nestHost;
        private final List<? extends TypeDescription> nestMembers;

        @MaybeNull
        private final List<? extends TypeDescription> permittedSubclasses;
        private final boolean record;
        private final List<? extends RecordComponentDescription.Token> recordComponentTokens;

        @MaybeNull
        private final TypeDescription.Generic superClass;
        private final TypeInitializer typeInitializer;
        private final List<? extends TypeVariableToken> typeVariables;

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public /* bridge */ /* synthetic */ InstrumentedType withAnnotations(List list) {
            return withAnnotations((List<? extends AnnotationDescription>) list);
        }

        protected Default(String str, int i, @MaybeNull TypeDescription.Generic generic, List<? extends TypeVariableToken> list, List<? extends TypeDescription.Generic> list2, List<? extends FieldDescription.Token> list3, Map<String, Object> map, List<? extends MethodDescription.Token> list4, List<? extends RecordComponentDescription.Token> list5, List<? extends AnnotationDescription> list6, TypeInitializer typeInitializer, LoadedTypeInitializer loadedTypeInitializer, @MaybeNull TypeDescription typeDescription, @MaybeNull MethodDescription.InDefinedShape inDefinedShape, @MaybeNull TypeDescription typeDescription2, List<? extends TypeDescription> list7, @MaybeNull List<? extends TypeDescription> list8, boolean z, boolean z2, boolean z3, TypeDescription typeDescription3, List<? extends TypeDescription> list9) {
            this.f360name = str;
            this.modifiers = i;
            this.typeVariables = list;
            this.superClass = generic;
            this.interfaceTypes = list2;
            this.fieldTokens = list3;
            this.auxiliaryFields = map;
            this.methodTokens = list4;
            this.recordComponentTokens = list5;
            this.annotationDescriptions = list6;
            this.typeInitializer = typeInitializer;
            this.loadedTypeInitializer = loadedTypeInitializer;
            this.declaringType = typeDescription;
            this.enclosingMethod = inDefinedShape;
            this.enclosingType = typeDescription2;
            this.declaredTypes = list7;
            this.permittedSubclasses = list8;
            this.anonymousClass = z;
            this.localClass = z2;
            this.record = z3;
            this.nestHost = typeDescription3;
            this.nestMembers = list9;
        }

        public static InstrumentedType of(String str, TypeDescription.Generic generic, ModifierContributor.ForType... forTypeArr) {
            return of(str, generic, ModifierContributor.Resolver.of(forTypeArr).resolve());
        }

        public static InstrumentedType of(String str, TypeDescription.Generic generic, int i) {
            return Factory.Default.MODIFIABLE.subclass(str, i, generic);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withModifiers(int i) {
            return new Default(this.f360name, i, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withField(FieldDescription.Token token) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, CompoundList.of(this.fieldTokens, token.accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(this))), this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withAuxiliaryField(FieldDescription.Token token, Object obj) {
            HashMap map = new HashMap(this.auxiliaryFields);
            Object objPut = map.put(token.getName(), obj);
            if (objPut == null) {
                return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, CompoundList.of(this.fieldTokens, token.accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(this))), map, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, new LoadedTypeInitializer.Compound(this.loadedTypeInitializer, new LoadedTypeInitializer.ForStaticField(token.getName(), obj)), this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
            }
            if (objPut == obj) {
                return this;
            }
            throw new IllegalStateException("Field " + token.getName() + " for " + this + " already mapped to " + objPut + " and not " + obj);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withMethod(MethodDescription.Token token) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, CompoundList.of(this.methodTokens, token.accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(this))), this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withRecordComponent(RecordComponentDescription.Token token) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, CompoundList.of(this.recordComponentTokens, token.accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(this))), this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, true, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withInterfaces(TypeList.Generic generic) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, CompoundList.of((List) this.interfaceTypes, (List) generic.accept(TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(this))), this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.WithFlexibleName, net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withAnnotations(List<? extends AnnotationDescription> list) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, CompoundList.of((List) this.annotationDescriptions, (List) list), this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withNestHost(TypeDescription typeDescription) {
            String str = this.f360name;
            int i = this.modifiers;
            TypeDescription.Generic generic = this.superClass;
            List<? extends TypeVariableToken> list = this.typeVariables;
            List<? extends TypeDescription.Generic> list2 = this.interfaceTypes;
            List<? extends FieldDescription.Token> list3 = this.fieldTokens;
            Map<String, Object> map = this.auxiliaryFields;
            List<? extends MethodDescription.Token> list4 = this.methodTokens;
            List<? extends RecordComponentDescription.Token> list5 = this.recordComponentTokens;
            List<? extends AnnotationDescription> list6 = this.annotationDescriptions;
            TypeInitializer typeInitializer = this.typeInitializer;
            LoadedTypeInitializer loadedTypeInitializer = this.loadedTypeInitializer;
            TypeDescription typeDescription2 = this.declaringType;
            MethodDescription.InDefinedShape inDefinedShape = this.enclosingMethod;
            TypeDescription typeDescription3 = this.enclosingType;
            List<? extends TypeDescription> list7 = this.declaredTypes;
            List<? extends TypeDescription> list8 = this.permittedSubclasses;
            boolean z = this.anonymousClass;
            boolean z2 = this.localClass;
            boolean z3 = this.record;
            TypeDescription typeDescription4 = typeDescription;
            if (typeDescription4.equals(this)) {
                typeDescription4 = TargetType.DESCRIPTION;
            }
            return new Default(str, i, generic, list, list2, list3, map, list4, list5, list6, typeInitializer, loadedTypeInitializer, typeDescription2, inDefinedShape, typeDescription3, list7, list8, z, z2, z3, typeDescription4, Collections.emptyList());
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withNestMembers(TypeList typeList) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, TargetType.DESCRIPTION, CompoundList.of((List) this.nestMembers, (List) typeList));
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withEnclosingType(@MaybeNull TypeDescription typeDescription) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, MethodDescription.UNDEFINED, typeDescription, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withEnclosingMethod(MethodDescription.InDefinedShape inDefinedShape) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, inDefinedShape, inDefinedShape.getDeclaringType(), this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withDeclaringType(@MaybeNull TypeDescription typeDescription) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, typeDescription, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withDeclaredTypes(TypeList typeList) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, CompoundList.of((List) this.declaredTypes, (List) typeList), this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withPermittedSubclasses(@MaybeNull TypeList typeList) {
            List<? extends TypeDescription> list;
            List listOf = typeList;
            String str = this.f360name;
            int i = this.modifiers;
            TypeDescription.Generic generic = this.superClass;
            List<? extends TypeVariableToken> list2 = this.typeVariables;
            List<? extends TypeDescription.Generic> list3 = this.interfaceTypes;
            List<? extends FieldDescription.Token> list4 = this.fieldTokens;
            Map<String, Object> map = this.auxiliaryFields;
            List<? extends MethodDescription.Token> list5 = this.methodTokens;
            List<? extends RecordComponentDescription.Token> list6 = this.recordComponentTokens;
            List<? extends AnnotationDescription> list7 = this.annotationDescriptions;
            TypeInitializer typeInitializer = this.typeInitializer;
            LoadedTypeInitializer loadedTypeInitializer = this.loadedTypeInitializer;
            TypeDescription typeDescription = this.declaringType;
            MethodDescription.InDefinedShape inDefinedShape = this.enclosingMethod;
            TypeDescription typeDescription2 = this.enclosingType;
            List<? extends TypeDescription> list8 = this.declaredTypes;
            if (listOf != null && (list = this.permittedSubclasses) != null) {
                listOf = CompoundList.of((List) list, listOf);
            }
            return new Default(str, i, generic, list2, list3, list4, map, list5, list6, list7, typeInitializer, loadedTypeInitializer, typeDescription, inDefinedShape, typeDescription2, list8, listOf, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withTypeVariable(TypeVariableToken typeVariableToken) {
            return new Default(this.f360name, this.modifiers, this.superClass, CompoundList.of(this.typeVariables, typeVariableToken.accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) TypeDescription.Generic.Visitor.Substitutor.ForDetachment.of(this))), this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.WithFlexibleName
        public WithFlexibleName withName(String str) {
            return new Default(str, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.WithFlexibleName
        public WithFlexibleName withTypeVariables(ElementMatcher<? super TypeDescription.Generic> elementMatcher, Transformer<TypeVariableToken> transformer) {
            ArrayList arrayList = new ArrayList(this.typeVariables.size());
            int i = 0;
            for (TypeVariableToken typeVariableTokenTransform : this.typeVariables) {
                int i2 = i + 1;
                if (elementMatcher.matches(getTypeVariables().get(i))) {
                    typeVariableTokenTransform = transformer.transform(this, typeVariableTokenTransform);
                }
                arrayList.add(typeVariableTokenTransform);
                i = i2;
            }
            return new Default(this.f360name, this.modifiers, this.superClass, arrayList, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withLocalClass(boolean z) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, false, z, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withAnonymousClass(boolean z) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, z, false, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withRecord(boolean z) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, z ? this.recordComponentTokens : Collections.emptyList(), this.annotationDescriptions, this.typeInitializer, this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, z, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withInitializer(LoadedTypeInitializer loadedTypeInitializer) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer, new LoadedTypeInitializer.Compound(this.loadedTypeInitializer, loadedTypeInitializer), this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withInitializer(ByteCodeAppender byteCodeAppender) {
            return new Default(this.f360name, this.modifiers, this.superClass, this.typeVariables, this.interfaceTypes, this.fieldTokens, this.auxiliaryFields, this.methodTokens, this.recordComponentTokens, this.annotationDescriptions, this.typeInitializer.expandWith(byteCodeAppender), this.loadedTypeInitializer, this.declaringType, this.enclosingMethod, this.enclosingType, this.declaredTypes, this.permittedSubclasses, this.anonymousClass, this.localClass, this.record, this.nestHost, this.nestMembers);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public LoadedTypeInitializer getLoadedTypeInitializer() {
            return this.loadedTypeInitializer;
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public TypeInitializer getTypeInitializer() {
            return this.typeInitializer;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            return this.enclosingMethod;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public TypeDescription getEnclosingType() {
            return this.enclosingType;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            return new TypeList.Explicit(this.declaredTypes);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            return this.anonymousClass;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            return this.localClass;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public PackageDescription getPackage() {
            int iLastIndexOf = this.f360name.lastIndexOf(46);
            if (iLastIndexOf == -1) {
                return PackageDescription.DEFAULT;
            }
            return new PackageDescription.Simple(this.f360name.substring(0, iLastIndexOf));
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Explicit(this.annotationDescriptions);
        }

        @Override // net.bytebuddy.description.DeclaredByType
        @MaybeNull
        public TypeDescription getDeclaringType() {
            return this.declaringType;
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public TypeDescription.Generic getSuperClass() {
            return this.superClass == null ? TypeDescription.Generic.UNDEFINED : new TypeDescription.Generic.LazyProjection.WithResolvedErasure(this.superClass, TypeDescription.Generic.Visitor.Substitutor.ForAttachment.of(this));
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            return new TypeList.Generic.ForDetachedTypes.WithResolvedErasure(this.interfaceTypes, TypeDescription.Generic.Visitor.Substitutor.ForAttachment.of(this));
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            return new FieldList.ForTokens(this, this.fieldTokens);
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            return new MethodList.ForTokens(this, this.methodTokens);
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return TypeList.Generic.ForDetachedTypes.attachVariables(this, this.typeVariables);
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.modifiers;
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.f360name;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            return this.nestHost.represents(TargetType.class) ? this : this.nestHost;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            if (this.nestHost.represents(TargetType.class)) {
                return new TypeList.Explicit((List<? extends TypeDescription>) CompoundList.of(this, (List<? extends Default>) this.nestMembers));
            }
            return this.nestHost.getNestMembers();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            return new RecordComponentList.ForTokens(this, this.recordComponentTokens);
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            return this.record && this.superClass != null && getSuperClass().asErasure().equals(JavaType.RECORD.getTypeStub());
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isSealed() {
            return this.permittedSubclasses != null;
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            return this.permittedSubclasses == null ? new TypeList.Empty() : new TypeList.Explicit(this.permittedSubclasses);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public TypeDescription validated() {
            AnnotationDescription annotationDescription;
            if (!isValidIdentifier(getName().split("\\."))) {
                throw new IllegalStateException("Illegal type name: " + getName() + " for " + this);
            }
            if ((getModifiers() & (-161312)) != 0) {
                throw new IllegalStateException("Illegal modifiers " + getModifiers() + " for " + this);
            }
            if (isPackageType() && getModifiers() != 5632) {
                throw new IllegalStateException("Illegal modifiers " + getModifiers() + " for package " + this);
            }
            TypeDescription.Generic superClass = getSuperClass();
            if (superClass != null) {
                if (!((Boolean) superClass.accept(TypeDescription.Generic.Visitor.Validator.SUPER_CLASS)).booleanValue()) {
                    throw new IllegalStateException("Illegal super class " + superClass + " for " + this);
                }
                if (!((Boolean) superClass.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                    throw new IllegalStateException("Illegal type annotations on super class " + superClass + " for " + this);
                }
                if (!superClass.asErasure().isVisibleTo(this)) {
                    throw new IllegalStateException("Invisible super type " + superClass + " for " + this);
                }
            }
            HashSet hashSet = new HashSet();
            for (TypeDescription.Generic generic : getInterfaces()) {
                if (!((Boolean) generic.accept(TypeDescription.Generic.Visitor.Validator.INTERFACE)).booleanValue()) {
                    throw new IllegalStateException("Illegal interface " + generic + " for " + this);
                }
                if (!((Boolean) generic.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                    throw new IllegalStateException("Illegal type annotations on interface " + generic + " for " + this);
                }
                if (!hashSet.add(generic.asErasure())) {
                    throw new IllegalStateException("Already implemented interface " + generic + " for " + this);
                }
                if (!generic.asErasure().isVisibleTo(this)) {
                    throw new IllegalStateException("Invisible interface type " + generic + " for " + this);
                }
            }
            TypeList.Generic typeVariables = getTypeVariables();
            if (!typeVariables.isEmpty() && isAssignableTo(Throwable.class)) {
                throw new IllegalStateException("Cannot define throwable " + this + " to be generic");
            }
            HashSet hashSet2 = new HashSet();
            Iterator it = typeVariables.iterator();
            while (true) {
                String str = "Illegal interface bound ";
                String str2 = "Illegal type variable name '";
                String str3 = "Duplicate type variable symbol '";
                String str4 = "' for ";
                if (it.hasNext()) {
                    TypeDescription.Generic generic2 = (TypeDescription.Generic) it.next();
                    Iterator it2 = it;
                    String symbol = generic2.getSymbol();
                    if (!hashSet2.add(symbol)) {
                        throw new IllegalStateException("Duplicate type variable symbol '" + generic2 + "' for " + this);
                    }
                    if (!isValidIdentifier(symbol)) {
                        throw new IllegalStateException("Illegal type variable name '" + generic2 + "' for " + this);
                    }
                    if (!TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.ofFormalTypeVariable(generic2)) {
                        throw new IllegalStateException("Illegal type annotation on '" + generic2 + "' for " + this);
                    }
                    HashSet hashSet3 = new HashSet();
                    boolean z = false;
                    for (TypeDescription.Generic generic3 : generic2.getUpperBounds()) {
                        if (!((Boolean) generic3.accept(TypeDescription.Generic.Visitor.Validator.TYPE_VARIABLE)).booleanValue()) {
                            throw new IllegalStateException("Illegal type variable bound " + generic3 + " of " + generic2 + " for " + this);
                        }
                        if (!((Boolean) generic3.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                            throw new IllegalStateException("Illegal type annotations on type variable " + generic3 + " for " + this);
                        }
                        if (!hashSet3.add(generic3)) {
                            throw new IllegalStateException("Duplicate bound " + generic3 + " of " + generic2 + " for " + this);
                        }
                        if (z && (generic3.getSort().isTypeVariable() || !generic3.isInterface())) {
                            throw new IllegalStateException("Illegal interface bound " + generic3 + " of " + generic2 + " for " + this);
                        }
                        z = true;
                    }
                    if (!z) {
                        throw new IllegalStateException("Type variable " + generic2 + " for " + this + " does not define at least one bound");
                    }
                    it = it2;
                } else {
                    TypeDescription enclosingType = getEnclosingType();
                    if (enclosingType != null && (enclosingType.isArray() || enclosingType.isPrimitive())) {
                        throw new IllegalStateException("Cannot define array type or primitive type " + enclosingType + " + as enclosing type for " + this);
                    }
                    MethodDescription.InDefinedShape enclosingMethod = getEnclosingMethod();
                    if (enclosingMethod != null && enclosingMethod.isTypeInitializer()) {
                        throw new IllegalStateException("Cannot enclose type declaration in class initializer " + enclosingMethod);
                    }
                    TypeDescription declaringType = getDeclaringType();
                    if (declaringType != null) {
                        if (declaringType.isPrimitive() || declaringType.isArray()) {
                            throw new IllegalStateException("Cannot define array type or primitive type " + declaringType + " as declaring type for " + this);
                        }
                    } else if (enclosingType == null && enclosingMethod == null && (isLocalType() || isAnonymousType())) {
                        throw new IllegalStateException("Cannot define an anonymous or local class without a declaring type for " + this);
                    }
                    HashSet hashSet4 = new HashSet();
                    Iterator it3 = getDeclaredTypes().iterator();
                    while (it3.hasNext()) {
                        Iterator it4 = it3;
                        TypeDescription typeDescription = (TypeDescription) it3.next();
                        if (typeDescription.isArray() || typeDescription.isPrimitive()) {
                            throw new IllegalStateException("Cannot define array type or primitive type " + typeDescription + " + as declared type for " + this);
                        }
                        if (!hashSet4.add(typeDescription)) {
                            throw new IllegalStateException("Duplicate definition of declared type " + typeDescription);
                        }
                        it3 = it4;
                    }
                    TypeDescription nestHost = getNestHost();
                    if (nestHost.equals(this)) {
                        HashSet hashSet5 = new HashSet();
                        Iterator it5 = getNestMembers().iterator();
                        while (it5.hasNext()) {
                            Iterator it6 = it5;
                            TypeDescription typeDescription2 = (TypeDescription) it5.next();
                            if (typeDescription2.isArray() || typeDescription2.isPrimitive()) {
                                throw new IllegalStateException("Cannot define array type or primitive type " + typeDescription2 + " + as nest member of " + this);
                            }
                            if (!typeDescription2.isSamePackage(this)) {
                                throw new IllegalStateException("Cannot define nest member " + typeDescription2 + " + within different package then " + this);
                            }
                            if (!hashSet5.add(typeDescription2)) {
                                throw new IllegalStateException("Duplicate definition of nest member " + typeDescription2);
                            }
                            it5 = it6;
                        }
                    } else {
                        if (nestHost.isArray() || nestHost.isPrimitive()) {
                            throw new IllegalStateException("Cannot define array type or primitive type " + nestHost + " + as nest host for " + this);
                        }
                        if (!nestHost.isSamePackage(this)) {
                            throw new IllegalStateException("Cannot define nest host " + nestHost + " + within different package then " + this);
                        }
                    }
                    for (TypeDescription typeDescription3 : getPermittedSubtypes()) {
                        if (!typeDescription3.isAssignableTo(this) || typeDescription3.equals(this)) {
                            throw new IllegalStateException("Cannot assign permitted subclass " + typeDescription3 + " to " + this);
                        }
                    }
                    HashSet hashSet6 = new HashSet();
                    Iterator it7 = getDeclaredAnnotations().iterator();
                    while (true) {
                        String str5 = " on ";
                        String str6 = str3;
                        String str7 = str2;
                        if (it7.hasNext()) {
                            annotationDescription = (AnnotationDescription) it7.next();
                            Iterator it8 = it7;
                            if (!annotationDescription.isSupportedOn(ElementType.TYPE) && ((!isAnnotation() || !annotationDescription.isSupportedOn(ElementType.ANNOTATION_TYPE)) && (!isPackageType() || !annotationDescription.isSupportedOn(ElementType.PACKAGE)))) {
                                break;
                            }
                            if (!hashSet6.add(annotationDescription.getAnnotationType())) {
                                throw new IllegalStateException("Duplicate annotation " + annotationDescription + " for " + this);
                            }
                            str3 = str6;
                            str2 = str7;
                            it7 = it8;
                        } else {
                            HashSet hashSet7 = new HashSet();
                            Iterator it9 = getDeclaredFields().iterator();
                            while (true) {
                                String str8 = str4;
                                String str9 = "Illegal type annotations on ";
                                if (it9.hasNext()) {
                                    FieldDescription.InDefinedShape inDefinedShape = (FieldDescription.InDefinedShape) it9.next();
                                    String name2 = inDefinedShape.getName();
                                    Iterator it10 = it9;
                                    if (!hashSet7.add(inDefinedShape.asSignatureToken())) {
                                        throw new IllegalStateException("Duplicate field definition for " + inDefinedShape);
                                    }
                                    if (!isValidIdentifier(name2)) {
                                        throw new IllegalStateException("Illegal field name for " + inDefinedShape);
                                    }
                                    if ((inDefinedShape.getModifiers() & (-151776)) != 0) {
                                        throw new IllegalStateException("Illegal field modifiers " + inDefinedShape.getModifiers() + " for " + inDefinedShape);
                                    }
                                    TypeDescription.Generic type = inDefinedShape.getType();
                                    HashSet hashSet8 = hashSet7;
                                    if (!((Boolean) type.accept(TypeDescription.Generic.Visitor.Validator.FIELD)).booleanValue()) {
                                        throw new IllegalStateException("Illegal field type " + type + " for " + inDefinedShape);
                                    }
                                    if (!((Boolean) type.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                                        throw new IllegalStateException("Illegal type annotations on " + type + " for " + this);
                                    }
                                    if (!inDefinedShape.isSynthetic() && !type.asErasure().isVisibleTo(this)) {
                                        throw new IllegalStateException("Invisible field type " + inDefinedShape.getType() + " for " + inDefinedShape);
                                    }
                                    HashSet hashSet9 = new HashSet();
                                    Iterator it11 = inDefinedShape.getDeclaredAnnotations().iterator();
                                    while (it11.hasNext()) {
                                        AnnotationDescription annotationDescription2 = (AnnotationDescription) it11.next();
                                        Iterator it12 = it11;
                                        if (!annotationDescription2.isSupportedOn(ElementType.FIELD)) {
                                            throw new IllegalStateException("Cannot add " + annotationDescription2 + " on " + inDefinedShape);
                                        }
                                        if (!hashSet9.add(annotationDescription2.getAnnotationType())) {
                                            throw new IllegalStateException("Duplicate annotation " + annotationDescription2 + " for " + inDefinedShape);
                                        }
                                        it11 = it12;
                                    }
                                    str4 = str8;
                                    hashSet7 = hashSet8;
                                    it9 = it10;
                                } else {
                                    HashSet hashSet10 = new HashSet();
                                    Iterator it13 = getDeclaredMethods().iterator();
                                    while (it13.hasNext()) {
                                        MethodDescription.InDefinedShape inDefinedShape2 = (MethodDescription.InDefinedShape) it13.next();
                                        Iterator it14 = it13;
                                        if (!hashSet10.add(inDefinedShape2.asSignatureToken())) {
                                            throw new IllegalStateException("Duplicate method signature for " + inDefinedShape2);
                                        }
                                        if ((inDefinedShape2.getModifiers() & (-7680)) != 0) {
                                            throw new IllegalStateException("Illegal modifiers " + inDefinedShape2.getModifiers() + " for " + inDefinedShape2);
                                        }
                                        if (isInterface() && !inDefinedShape2.isPublic() && !inDefinedShape2.isPrivate()) {
                                            throw new IllegalStateException("Methods declared by an interface must be public or private " + inDefinedShape2);
                                        }
                                        HashSet hashSet11 = new HashSet();
                                        for (TypeDescription.Generic generic4 : inDefinedShape2.getTypeVariables()) {
                                            HashSet hashSet12 = hashSet10;
                                            String str10 = str9;
                                            String symbol2 = generic4.getSymbol();
                                            if (!hashSet11.add(symbol2)) {
                                                throw new IllegalStateException(str6 + generic4 + str8 + inDefinedShape2);
                                            }
                                            if (!isValidIdentifier(symbol2)) {
                                                throw new IllegalStateException(str7 + generic4 + str8 + inDefinedShape2);
                                            }
                                            if (!TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.ofFormalTypeVariable(generic4)) {
                                                throw new IllegalStateException("Illegal type annotation on '" + generic4 + str8 + inDefinedShape2);
                                            }
                                            HashSet hashSet13 = new HashSet();
                                            boolean z2 = false;
                                            for (TypeDescription.Generic generic5 : generic4.getUpperBounds()) {
                                                HashSet hashSet14 = hashSet11;
                                                String str11 = str5;
                                                if (!((Boolean) generic5.accept(TypeDescription.Generic.Visitor.Validator.TYPE_VARIABLE)).booleanValue()) {
                                                    throw new IllegalStateException("Illegal type variable bound " + generic5 + " of " + generic4 + " for " + inDefinedShape2);
                                                }
                                                if (!((Boolean) generic5.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                                                    throw new IllegalStateException("Illegal type annotations on bound " + generic5 + " of " + generic4 + " for " + this);
                                                }
                                                if (!hashSet13.add(generic5)) {
                                                    throw new IllegalStateException("Duplicate bound " + generic5 + " of " + generic4 + " for " + inDefinedShape2);
                                                }
                                                if (z2 && (generic5.getSort().isTypeVariable() || !generic5.isInterface())) {
                                                    throw new IllegalStateException(str + generic5 + " of " + generic4 + " for " + inDefinedShape2);
                                                }
                                                str5 = str11;
                                                hashSet11 = hashSet14;
                                                z2 = true;
                                            }
                                            HashSet hashSet15 = hashSet11;
                                            String str12 = str5;
                                            if (!z2) {
                                                throw new IllegalStateException("Type variable " + generic4 + " for " + inDefinedShape2 + " does not define at least one bound");
                                            }
                                            str9 = str10;
                                            hashSet10 = hashSet12;
                                            str5 = str12;
                                            hashSet11 = hashSet15;
                                        }
                                        HashSet hashSet16 = hashSet10;
                                        String str13 = str5;
                                        String str14 = str9;
                                        String str15 = str6;
                                        String str16 = str7;
                                        String str17 = str8;
                                        TypeDescription.Generic returnType = inDefinedShape2.getReturnType();
                                        if (inDefinedShape2.isTypeInitializer()) {
                                            throw new IllegalStateException("Illegal explicit declaration of a type initializer by " + this);
                                        }
                                        if (inDefinedShape2.isConstructor()) {
                                            str6 = str15;
                                            if (!returnType.represents(Void.TYPE)) {
                                                throw new IllegalStateException("A constructor must return void " + inDefinedShape2);
                                            }
                                            if (!returnType.getDeclaredAnnotations().isEmpty()) {
                                                throw new IllegalStateException("The void non-type must not be annotated for " + inDefinedShape2);
                                            }
                                        } else {
                                            str6 = str15;
                                            if (!isValidIdentifier(inDefinedShape2.getInternalName())) {
                                                throw new IllegalStateException("Illegal method name " + returnType + " for " + inDefinedShape2);
                                            }
                                            if (!((Boolean) returnType.accept(TypeDescription.Generic.Visitor.Validator.METHOD_RETURN)).booleanValue()) {
                                                throw new IllegalStateException("Illegal return type " + returnType + " for " + inDefinedShape2);
                                            }
                                            if (!((Boolean) returnType.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                                                throw new IllegalStateException("Illegal type annotations on return type " + returnType + " for " + inDefinedShape2);
                                            }
                                            if (!inDefinedShape2.isSynthetic() && !inDefinedShape2.getReturnType().asErasure().isVisibleTo(this)) {
                                                throw new IllegalStateException("Invisible return type " + inDefinedShape2.getReturnType() + " for " + inDefinedShape2);
                                            }
                                        }
                                        HashSet hashSet17 = new HashSet();
                                        Iterator it15 = inDefinedShape2.getParameters().iterator();
                                        while (it15.hasNext()) {
                                            String str18 = str17;
                                            ParameterDescription.InDefinedShape inDefinedShape3 = (ParameterDescription.InDefinedShape) it15.next();
                                            Iterator it16 = it15;
                                            TypeDescription.Generic type2 = inDefinedShape3.getType();
                                            String str19 = str;
                                            if (!((Boolean) type2.accept(TypeDescription.Generic.Visitor.Validator.METHOD_PARAMETER)).booleanValue()) {
                                                throw new IllegalStateException("Illegal parameter type of " + inDefinedShape3 + " for " + inDefinedShape2);
                                            }
                                            if (!((Boolean) type2.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                                                throw new IllegalStateException("Illegal type annotations on parameter " + inDefinedShape3 + " for " + inDefinedShape2);
                                            }
                                            if (!inDefinedShape2.isSynthetic() && !type2.asErasure().isVisibleTo(this)) {
                                                throw new IllegalStateException("Invisible parameter type of " + inDefinedShape3 + " for " + inDefinedShape2);
                                            }
                                            if (inDefinedShape3.isNamed()) {
                                                String name3 = inDefinedShape3.getName();
                                                if (!hashSet17.add(name3)) {
                                                    throw new IllegalStateException("Duplicate parameter name of " + inDefinedShape3 + " for " + inDefinedShape2);
                                                }
                                                if (!isValidIdentifier(name3)) {
                                                    throw new IllegalStateException("Illegal parameter name of " + inDefinedShape3 + " for " + inDefinedShape2);
                                                }
                                            }
                                            if (inDefinedShape3.hasModifiers() && (inDefinedShape3.getModifiers() & (-36881)) != 0) {
                                                throw new IllegalStateException("Illegal modifiers of " + inDefinedShape3 + " for " + inDefinedShape2);
                                            }
                                            HashSet hashSet18 = new HashSet();
                                            Iterator it17 = inDefinedShape3.getDeclaredAnnotations().iterator();
                                            while (it17.hasNext()) {
                                                HashSet hashSet19 = hashSet17;
                                                AnnotationDescription annotationDescription3 = (AnnotationDescription) it17.next();
                                                Iterator it18 = it17;
                                                if (!annotationDescription3.isSupportedOn(ElementType.PARAMETER)) {
                                                    throw new IllegalStateException("Cannot add " + annotationDescription3 + str13 + inDefinedShape3);
                                                }
                                                if (!hashSet18.add(annotationDescription3.getAnnotationType())) {
                                                    throw new IllegalStateException("Duplicate annotation " + annotationDescription3 + " of " + inDefinedShape3 + " for " + inDefinedShape2);
                                                }
                                                it17 = it18;
                                                hashSet17 = hashSet19;
                                            }
                                            it15 = it16;
                                            str17 = str18;
                                            str = str19;
                                        }
                                        str8 = str17;
                                        String str20 = str;
                                        for (TypeDescription.Generic generic6 : inDefinedShape2.getExceptionTypes()) {
                                            if (!((Boolean) generic6.accept(TypeDescription.Generic.Visitor.Validator.EXCEPTION)).booleanValue()) {
                                                throw new IllegalStateException("Illegal exception type " + generic6 + " for " + inDefinedShape2);
                                            }
                                            if (!((Boolean) generic6.accept(TypeDescription.Generic.Visitor.Validator.ForTypeAnnotations.INSTANCE)).booleanValue()) {
                                                throw new IllegalStateException(str14 + generic6 + " for " + inDefinedShape2);
                                            }
                                            if (!inDefinedShape2.isSynthetic() && !generic6.asErasure().isVisibleTo(this)) {
                                                throw new IllegalStateException("Invisible exception type " + generic6 + " for " + inDefinedShape2);
                                            }
                                        }
                                        String str21 = str14;
                                        HashSet hashSet20 = new HashSet();
                                        Iterator it19 = inDefinedShape2.getDeclaredAnnotations().iterator();
                                        while (it19.hasNext()) {
                                            Iterator it20 = it19;
                                            AnnotationDescription annotationDescription4 = (AnnotationDescription) it19.next();
                                            String str22 = str21;
                                            if (!annotationDescription4.isSupportedOn(inDefinedShape2.isMethod() ? ElementType.METHOD : ElementType.CONSTRUCTOR)) {
                                                throw new IllegalStateException("Cannot add " + annotationDescription4 + str13 + inDefinedShape2);
                                            }
                                            if (!hashSet20.add(annotationDescription4.getAnnotationType())) {
                                                throw new IllegalStateException("Duplicate annotation " + annotationDescription4 + " for " + inDefinedShape2);
                                            }
                                            it19 = it20;
                                            str21 = str22;
                                        }
                                        String str23 = str21;
                                        AnnotationValue<?, ?> defaultValue = inDefinedShape2.getDefaultValue();
                                        if (defaultValue != null && !inDefinedShape2.isDefaultValue(defaultValue)) {
                                            throw new IllegalStateException("Illegal default value " + defaultValue + "for " + inDefinedShape2);
                                        }
                                        TypeDescription.Generic receiverType = inDefinedShape2.getReceiverType();
                                        if (receiverType != null && !((Boolean) receiverType.accept(TypeDescription.Generic.Visitor.Validator.RECEIVER)).booleanValue()) {
                                            throw new IllegalStateException("Illegal receiver type " + receiverType + " for " + inDefinedShape2);
                                        }
                                        if (inDefinedShape2.isStatic()) {
                                            if (receiverType != null) {
                                                throw new IllegalStateException("Static method " + inDefinedShape2 + " defines a non-null receiver " + receiverType);
                                            }
                                        } else {
                                            if (inDefinedShape2.isConstructor()) {
                                                if (receiverType != null) {
                                                    if (receiverType.asErasure().equals(enclosingType == null ? this : enclosingType)) {
                                                    }
                                                }
                                                throw new IllegalStateException("Constructor " + inDefinedShape2 + " defines an illegal receiver " + receiverType);
                                            }
                                            if (receiverType == null || !equals(receiverType.asErasure())) {
                                                throw new IllegalStateException("Method " + inDefinedShape2 + " defines an illegal receiver " + receiverType);
                                            }
                                        }
                                        str7 = str16;
                                        it13 = it14;
                                        str = str20;
                                        hashSet10 = hashSet16;
                                        str9 = str23;
                                        str5 = str13;
                                    }
                                    return this;
                                }
                            }
                        }
                    }
                    throw new IllegalStateException("Cannot add " + annotationDescription + " on " + this);
                }
            }
        }

        private static boolean isValidIdentifier(String[] strArr) {
            if (strArr.length == 0) {
                return false;
            }
            for (String str : strArr) {
                if (!isValidIdentifier(str)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isValidIdentifier(String str) {
            if (KEYWORDS.contains(str) || str.length() == 0 || !Character.isJavaIdentifierStart(str.charAt(0))) {
                return false;
            }
            if (str.equals("package-info")) {
                return true;
            }
            for (int i = 1; i < str.length(); i++) {
                if (!Character.isJavaIdentifierPart(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class Frozen extends TypeDescription.AbstractBase.OfSimpleType implements WithFlexibleName {
        private final LoadedTypeInitializer loadedTypeInitializer;
        private final TypeDescription typeDescription;

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public /* bridge */ /* synthetic */ InstrumentedType withAnnotations(List list) {
            return withAnnotations((List<? extends AnnotationDescription>) list);
        }

        protected Frozen(TypeDescription typeDescription, LoadedTypeInitializer loadedTypeInitializer) {
            this.typeDescription = typeDescription;
            this.loadedTypeInitializer = loadedTypeInitializer;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return this.typeDescription.getDeclaredAnnotations();
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.typeDescription.getModifiers();
        }

        @Override // net.bytebuddy.description.TypeVariableSource
        public TypeList.Generic getTypeVariables() {
            return this.typeDescription.getTypeVariables();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.typeDescription.getName();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        @MaybeNull
        public TypeDescription.Generic getSuperClass() {
            return this.typeDescription.getSuperClass();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public TypeList.Generic getInterfaces() {
            return this.typeDescription.getInterfaces();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
            return this.typeDescription.getDeclaredFields();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
            return this.typeDescription.getDeclaredMethods();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isAnonymousType() {
            return this.typeDescription.isAnonymousType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public boolean isLocalType() {
            return this.typeDescription.isLocalType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public PackageDescription getPackage() {
            return this.typeDescription.getPackage();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public TypeDescription getEnclosingType() {
            return this.typeDescription.getEnclosingType();
        }

        @Override // net.bytebuddy.description.DeclaredByType
        @MaybeNull
        public TypeDescription getDeclaringType() {
            return this.typeDescription.getDeclaringType();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getDeclaredTypes() {
            return this.typeDescription.getDeclaredTypes();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public MethodDescription.InDefinedShape getEnclosingMethod() {
            return this.typeDescription.getEnclosingMethod();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
        @MaybeNull
        public String getGenericSignature() {
            return this.typeDescription.getGenericSignature();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public int getActualModifiers(boolean z) {
            return this.typeDescription.getActualModifiers(z);
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeDescription getNestHost() {
            return this.typeDescription.getNestHost();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getNestMembers() {
            return this.typeDescription.getNestMembers();
        }

        @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
        public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
            return this.typeDescription.getRecordComponents();
        }

        @Override // net.bytebuddy.description.type.TypeDefinition
        public boolean isRecord() {
            return this.typeDescription.isRecord();
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        public boolean isSealed() {
            return this.typeDescription.isSealed();
        }

        @Override // net.bytebuddy.description.type.TypeDescription
        public TypeList getPermittedSubtypes() {
            return this.typeDescription.getPermittedSubtypes();
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withField(FieldDescription.Token token) {
            throw new IllegalStateException("Cannot define field for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withAuxiliaryField(FieldDescription.Token token, Object obj) {
            throw new IllegalStateException("Cannot define auxiliary field for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withMethod(MethodDescription.Token token) {
            throw new IllegalStateException("Cannot define method for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withRecordComponent(RecordComponentDescription.Token token) {
            throw new IllegalStateException("Cannot define record component for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withModifiers(int i) {
            throw new IllegalStateException("Cannot change modifiers for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withInterfaces(TypeList.Generic generic) {
            throw new IllegalStateException("Cannot add interfaces for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withTypeVariable(TypeVariableToken typeVariableToken) {
            throw new IllegalStateException("Cannot define type variable for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.WithFlexibleName, net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withAnnotations(List<? extends AnnotationDescription> list) {
            throw new IllegalStateException("Cannot add annotation to frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withNestHost(TypeDescription typeDescription) {
            throw new IllegalStateException("Cannot set nest host of frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withNestMembers(TypeList typeList) {
            throw new IllegalStateException("Cannot add nest members to frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withEnclosingType(@MaybeNull TypeDescription typeDescription) {
            throw new IllegalStateException("Cannot set enclosing type of frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withEnclosingMethod(MethodDescription.InDefinedShape inDefinedShape) {
            throw new IllegalStateException("Cannot set enclosing method of frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withDeclaringType(@MaybeNull TypeDescription typeDescription) {
            throw new IllegalStateException("Cannot add declaring type to frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withDeclaredTypes(TypeList typeList) {
            throw new IllegalStateException("Cannot add declared types to frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withPermittedSubclasses(@MaybeNull TypeList typeList) {
            throw new IllegalStateException("Cannot add permitted subclasses to frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withLocalClass(boolean z) {
            throw new IllegalStateException("Cannot define local class state for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withAnonymousClass(boolean z) {
            throw new IllegalStateException("Cannot define anonymous class state for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withRecord(boolean z) {
            throw new IllegalStateException("Cannot define record state for frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withInitializer(LoadedTypeInitializer loadedTypeInitializer) {
            return new Frozen(this.typeDescription, new LoadedTypeInitializer.Compound(this.loadedTypeInitializer, loadedTypeInitializer));
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public WithFlexibleName withInitializer(ByteCodeAppender byteCodeAppender) {
            throw new IllegalStateException("Cannot add initializer to frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.WithFlexibleName
        public WithFlexibleName withName(String str) {
            throw new IllegalStateException("Cannot change name of frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.WithFlexibleName
        public WithFlexibleName withTypeVariables(ElementMatcher<? super TypeDescription.Generic> elementMatcher, Transformer<TypeVariableToken> transformer) {
            throw new IllegalStateException("Cannot add type variables of frozen type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public LoadedTypeInitializer getLoadedTypeInitializer() {
            return this.loadedTypeInitializer;
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public TypeInitializer getTypeInitializer() {
            return TypeInitializer.None.INSTANCE;
        }

        @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
        @MaybeNull
        public ClassFileVersion getClassFileVersion() {
            return this.typeDescription.getClassFileVersion();
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType
        public TypeDescription validated() {
            return this.typeDescription;
        }
    }
}
