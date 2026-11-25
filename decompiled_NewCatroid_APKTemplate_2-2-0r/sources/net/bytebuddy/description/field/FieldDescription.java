package net.bytebuddy.description.field;

import io.netty.util.internal.StringUtil;
import java.lang.reflect.Field;
import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.DeclaredByType;
import net.bytebuddy.description.ModifierReviewable;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeVariableToken;
import net.bytebuddy.jar.asm.signature.SignatureVisitor;
import net.bytebuddy.jar.asm.signature.SignatureWriter;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface FieldDescription extends ModifierReviewable.ForFieldDescription, DeclaredByType.WithMandatoryDeclaration, ByteCodeElement.Member, ByteCodeElement.TypeDependant<InDefinedShape, Token> {

    @AlwaysNull
    public static final Object NO_DEFAULT_VALUE = null;

    public interface InDefinedShape extends FieldDescription {

        public static abstract class AbstractBase extends AbstractBase implements InDefinedShape {
            @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
            public InDefinedShape asDefined() {
                return this;
            }
        }

        @Override // net.bytebuddy.description.field.FieldDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        TypeDescription getDeclaringType();
    }

    public interface InGenericShape extends FieldDescription {
        @Override // net.bytebuddy.description.field.FieldDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        TypeDescription.Generic getDeclaringType();
    }

    SignatureToken asSignatureToken();

    int getActualModifiers();

    @Override // net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
    @Nonnull
    TypeDefinition getDeclaringType();

    TypeDescription.Generic getType();

    public static abstract class AbstractBase extends ModifierReviewable.AbstractBase implements FieldDescription {
        private transient /* synthetic */ int hashCode;

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public /* bridge */ /* synthetic */ ByteCodeElement.Token asToken(ElementMatcher elementMatcher) {
            return asToken((ElementMatcher<? super TypeDescription>) elementMatcher);
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return getName();
        }

        @Override // net.bytebuddy.description.NamedElement
        public String getActualName() {
            return getName();
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        public String getDescriptor() {
            return getType().asErasure().getDescriptor();
        }

        @Override // net.bytebuddy.description.NamedElement.WithDescriptor
        @MaybeNull
        public String getGenericSignature() {
            TypeDescription.Generic type = getType();
            try {
                return type.getSort().isNonGeneric() ? NON_GENERIC_SIGNATURE : ((SignatureVisitor) type.accept(new TypeDescription.Generic.Visitor.ForSignatureVisitor(new SignatureWriter()))).toString();
            } catch (GenericSignatureFormatError unused) {
                return NON_GENERIC_SIGNATURE;
            }
        }

        @Override // net.bytebuddy.description.ByteCodeElement
        public boolean isVisibleTo(TypeDescription typeDescription) {
            return getDeclaringType().asErasure().isVisibleTo(typeDescription) && (isPublic() || typeDescription.equals(getDeclaringType().asErasure()) || ((isProtected() && getDeclaringType().asErasure().isAssignableFrom(typeDescription)) || ((!isPrivate() && typeDescription.isSamePackage(getDeclaringType().asErasure())) || (isPrivate() && typeDescription.isNestMateOf(getDeclaringType().asErasure())))));
        }

        @Override // net.bytebuddy.description.ByteCodeElement
        public boolean isAccessibleTo(TypeDescription typeDescription) {
            return isPublic() || typeDescription.equals(getDeclaringType().asErasure()) || (!isPrivate() && typeDescription.isSamePackage(getDeclaringType().asErasure())) || (isPrivate() && typeDescription.isNestMateOf(getDeclaringType().asErasure()));
        }

        @Override // net.bytebuddy.description.field.FieldDescription
        public int getActualModifiers() {
            return getModifiers() | (getDeclaredAnnotations().isAnnotationPresent(Deprecated.class) ? 131072 : 0);
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public Token asToken(ElementMatcher<? super TypeDescription> elementMatcher) {
            return new Token(getName(), getModifiers(), (TypeDescription.Generic) getType().accept(new TypeDescription.Generic.Visitor.Substitutor.ForDetachment(elementMatcher)), getDeclaredAnnotations());
        }

        @Override // net.bytebuddy.description.field.FieldDescription
        public SignatureToken asSignatureToken() {
            return new SignatureToken(getInternalName(), getType().asErasure());
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : getDeclaringType().hashCode() + ((getName().hashCode() + 17) * 31);
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
            if (!(obj instanceof FieldDescription)) {
                return false;
            }
            FieldDescription fieldDescription = (FieldDescription) obj;
            return getName().equals(fieldDescription.getName()) && getDeclaringType().equals(fieldDescription.getDeclaringType());
        }

        @Override // net.bytebuddy.description.NamedElement.WithGenericName
        public String toGenericString() {
            StringBuilder sb = new StringBuilder();
            if (getModifiers() != 0) {
                sb.append(Modifier.toString(getModifiers())).append(StringUtil.SPACE);
            }
            sb.append(getType().getActualName()).append(StringUtil.SPACE);
            sb.append(getDeclaringType().asErasure().getActualName()).append('.');
            return sb.append(getName()).toString();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (getModifiers() != 0) {
                sb.append(Modifier.toString(getModifiers())).append(StringUtil.SPACE);
            }
            sb.append(getType().asErasure().getActualName()).append(StringUtil.SPACE);
            sb.append(getDeclaringType().asErasure().getActualName()).append('.');
            return sb.append(getName()).toString();
        }
    }

    public static class ForLoadedField extends InDefinedShape.AbstractBase {
        private transient /* synthetic */ AnnotationList declaredAnnotations;
        private final Field field;

        public ForLoadedField(Field field) {
            this.field = field;
        }

        @Override // net.bytebuddy.description.field.FieldDescription
        public TypeDescription.Generic getType() {
            if (TypeDescription.AbstractBase.RAW_TYPES) {
                return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(this.field.getType());
            }
            return new TypeDescription.Generic.LazyProjection.ForLoadedFieldType(this.field);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        @CachedReturnPlugin.Enhance("declaredAnnotations")
        public AnnotationList getDeclaredAnnotations() {
            AnnotationList.ForLoadedAnnotations forLoadedAnnotations = this.declaredAnnotations != null ? null : new AnnotationList.ForLoadedAnnotations(this.field.getDeclaredAnnotations());
            if (forLoadedAnnotations == null) {
                return this.declaredAnnotations;
            }
            this.declaredAnnotations = forLoadedAnnotations;
            return forLoadedAnnotations;
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.field.getName();
        }

        @Override // net.bytebuddy.description.field.FieldDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription getDeclaringType() {
            return TypeDescription.ForLoadedType.of(this.field.getDeclaringClass());
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.field.getModifiers();
        }

        @Override // net.bytebuddy.description.ModifierReviewable.AbstractBase, net.bytebuddy.description.ModifierReviewable
        public boolean isSynthetic() {
            return this.field.isSynthetic();
        }
    }

    public static class Latent extends InDefinedShape.AbstractBase {
        private final List<? extends AnnotationDescription> declaredAnnotations;
        private final TypeDescription declaringType;
        private final TypeDescription.Generic fieldType;
        private final int modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f347name;

        public Latent(TypeDescription typeDescription, Token token) {
            this(typeDescription, token.getName(), token.getModifiers(), token.getType(), token.getAnnotations());
        }

        public Latent(TypeDescription typeDescription, String str, int i, TypeDescription.Generic generic, List<? extends AnnotationDescription> list) {
            this.declaringType = typeDescription;
            this.f347name = str;
            this.modifiers = i;
            this.fieldType = generic;
            this.declaredAnnotations = list;
        }

        @Override // net.bytebuddy.description.field.FieldDescription
        public TypeDescription.Generic getType() {
            return (TypeDescription.Generic) this.fieldType.accept(TypeDescription.Generic.Visitor.Substitutor.ForAttachment.of(this));
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Explicit(this.declaredAnnotations);
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.f347name;
        }

        @Override // net.bytebuddy.description.field.FieldDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription getDeclaringType() {
            return this.declaringType;
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.modifiers;
        }
    }

    public static class TypeSubstituting extends AbstractBase implements InGenericShape {
        private final TypeDescription.Generic declaringType;
        private final FieldDescription fieldDescription;
        private final TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor;

        public TypeSubstituting(TypeDescription.Generic generic, FieldDescription fieldDescription, TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            this.declaringType = generic;
            this.fieldDescription = fieldDescription;
            this.visitor = visitor;
        }

        @Override // net.bytebuddy.description.field.FieldDescription
        public TypeDescription.Generic getType() {
            return (TypeDescription.Generic) this.fieldDescription.getType().accept(this.visitor);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return this.fieldDescription.getDeclaredAnnotations();
        }

        @Override // net.bytebuddy.description.field.FieldDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
        @Nonnull
        public TypeDescription.Generic getDeclaringType() {
            return this.declaringType;
        }

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.fieldDescription.getModifiers();
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.fieldDescription.getName();
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public InDefinedShape asDefined() {
            return this.fieldDescription.asDefined();
        }
    }

    public static class Token implements ByteCodeElement.Token<Token> {
        private final List<? extends AnnotationDescription> annotations;
        private transient /* synthetic */ int hashCode;
        private final int modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f349name;
        private final TypeDescription.Generic type;

        @Override // net.bytebuddy.description.ByteCodeElement.Token
        public /* bridge */ /* synthetic */ ByteCodeElement.Token accept(TypeDescription.Generic.Visitor visitor) {
            return accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) visitor);
        }

        public Token(String str, int i, TypeDescription.Generic generic) {
            this(str, i, generic, Collections.emptyList());
        }

        public Token(String str, int i, TypeDescription.Generic generic, List<? extends AnnotationDescription> list) {
            this.f349name = str;
            this.modifiers = i;
            this.type = generic;
            this.annotations = list;
        }

        public String getName() {
            return this.f349name;
        }

        public TypeDescription.Generic getType() {
            return this.type;
        }

        public int getModifiers() {
            return this.modifiers;
        }

        public AnnotationList getAnnotations() {
            return new AnnotationList.Explicit(this.annotations);
        }

        @Override // net.bytebuddy.description.ByteCodeElement.Token
        public Token accept(TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            return new Token(this.f349name, this.modifiers, (TypeDescription.Generic) this.type.accept(visitor), this.annotations);
        }

        public SignatureToken asSignatureToken(TypeDescription typeDescription) {
            return new SignatureToken(this.f349name, (TypeDescription) this.type.accept(new TypeDescription.Generic.Visitor.Reducing(typeDescription, new TypeVariableToken[0])));
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : (((((this.f349name.hashCode() * 31) + this.modifiers) * 31) + this.type.hashCode()) * 31) + this.annotations.hashCode();
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
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Token token = (Token) obj;
            return this.modifiers == token.modifiers && this.f349name.equals(token.f349name) && this.type.equals(token.type) && this.annotations.equals(token.annotations);
        }
    }

    public static class SignatureToken {
        private transient /* synthetic */ int hashCode;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f348name;
        private final TypeDescription type;

        public SignatureToken(String str, TypeDescription typeDescription) {
            this.f348name = str;
            this.type = typeDescription;
        }

        public String getName() {
            return this.f348name;
        }

        public TypeDescription getType() {
            return this.type;
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : (this.f348name.hashCode() * 31) + this.type.hashCode();
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
            return this.f348name.equals(signatureToken.f348name) && this.type.equals(signatureToken.type);
        }

        public String toString() {
            return this.type + " " + this.f348name;
        }
    }
}
