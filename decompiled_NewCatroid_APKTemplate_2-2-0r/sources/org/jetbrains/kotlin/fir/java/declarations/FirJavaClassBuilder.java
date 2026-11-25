package org.jetbrains.kotlin.fir.java.declarations;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJavaClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010Q\u001a\u00020RH\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR+\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R+\u0010\u0014\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R+\u0010\u0017\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0019\u0010\u0013\u001a\u0004\b\u0017\u0010\u000f\"\u0004\b\u0018\u0010\u0011R \u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\b\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001c\u0010+\u001a\u0004\u0018\u00010,X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R*\u00103\u001a\u0002022\u0006\u00101\u001a\u0002028V@VX\u0097\u000e¢\u0006\u0012\u0012\u0004\b4\u0010\u0003\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001a\u00109\u001a\u00020:X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001c\u0010?\u001a\u0004\u0018\u00010@X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001a\u0010E\u001a\b\u0012\u0004\u0012\u00020F0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bG\u0010\bR\u001a\u0010H\u001a\b\u0012\u0004\u0012\u00020I0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\bR\u001a\u0010K\u001a\u00020LX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010P¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClassBuilder;", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "declarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclarations", "()Ljava/util/List;", "existingNestedClassifierNames", "Lorg/jetbrains/kotlin/name/Name;", "getExistingNestedClassifierNames", "<set-?>", "", "isFromSource", "()Z", "setFromSource", "(Z)V", "isFromSource$delegate", "Lkotlin/properties/ReadWriteProperty;", "isStatic", "setStatic", "isStatic$delegate", "isTopLevel", "setTopLevel", "isTopLevel$delegate", "javaAnnotations", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "getJavaAnnotations", "setJavaAnnotations", "(Ljava/util/List;)V", "javaPackage", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "getJavaPackage", "()Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "setJavaPackage", "(Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;)V", "javaTypeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "getJavaTypeParameterStack", "()Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "setJavaTypeParameterStack", "(Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;)V", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "value", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "origin", "getOrigin$annotations", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "superTypeRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getSuperTypeRefs", "typeParameters", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "getTypeParameters", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaClassBuilder extends FirRegularClassBuilder implements FirAnnotationContainerBuilder {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaClassBuilder.class), "isFromSource", "isFromSource()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaClassBuilder.class), "isTopLevel", "isTopLevel()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(FirJavaClassBuilder.class), "isStatic", "isStatic()Z"))};
    private JavaPackage javaPackage;
    public JavaTypeParameterStack javaTypeParameterStack;
    private Modality modality;
    private KtSourceElement source;
    public Visibility visibility;

    /* JADX INFO: renamed from: isFromSource$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isFromSource = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isTopLevel$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isTopLevel = Delegates.INSTANCE.notNull();

    /* JADX INFO: renamed from: isStatic$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty isStatic = Delegates.INSTANCE.notNull();
    private final List<Name> existingNestedClassifierNames = new ArrayList();
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private List<JavaAnnotation> javaAnnotations = new ArrayList();
    private final List<FirTypeParameterRef> typeParameters = new ArrayList();
    private final List<FirDeclaration> declarations = new ArrayList();
    private final List<FirTypeRef> superTypeRefs = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'origin' has no impact for FirJavaClassBuilder")
    public static /* synthetic */ void getOrigin$annotations() {
    }

    public final Visibility getVisibility() {
        Visibility visibility = this.visibility;
        if (visibility != null) {
            return visibility;
        }
        Intrinsics.throwUninitializedPropertyAccessException("visibility");
        return null;
    }

    public final void setVisibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "<set-?>");
        this.visibility = visibility;
    }

    public final Modality getModality() {
        return this.modality;
    }

    public final void setModality(Modality modality) {
        this.modality = modality;
    }

    public final boolean isFromSource() {
        return ((Boolean) this.isFromSource.getValue(this, $$delegatedProperties[0])).booleanValue();
    }

    public final void setFromSource(boolean z) {
        this.isFromSource.setValue(this, $$delegatedProperties[0], Boolean.valueOf(z));
    }

    public final boolean isTopLevel() {
        return ((Boolean) this.isTopLevel.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    public final void setTopLevel(boolean z) {
        this.isTopLevel.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    public final boolean isStatic() {
        return ((Boolean) this.isStatic.getValue(this, $$delegatedProperties[2])).booleanValue();
    }

    public final void setStatic(boolean z) {
        this.isStatic.setValue(this, $$delegatedProperties[2], Boolean.valueOf(z));
    }

    public final JavaPackage getJavaPackage() {
        return this.javaPackage;
    }

    public final void setJavaPackage(JavaPackage javaPackage) {
        this.javaPackage = javaPackage;
    }

    public final JavaTypeParameterStack getJavaTypeParameterStack() {
        JavaTypeParameterStack javaTypeParameterStack = this.javaTypeParameterStack;
        if (javaTypeParameterStack != null) {
            return javaTypeParameterStack;
        }
        Intrinsics.throwUninitializedPropertyAccessException("javaTypeParameterStack");
        return null;
    }

    public final void setJavaTypeParameterStack(JavaTypeParameterStack javaTypeParameterStack) {
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "<set-?>");
        this.javaTypeParameterStack = javaTypeParameterStack;
    }

    public final List<Name> getExistingNestedClassifierNames() {
        return this.existingNestedClassifierNames;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    public final List<JavaAnnotation> getJavaAnnotations() {
        return this.javaAnnotations;
    }

    public final void setJavaAnnotations(List<JavaAnnotation> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.javaAnnotations = list;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public List<FirTypeParameterRef> getTypeParameters() {
        return this.typeParameters;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public List<FirDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder
    public List<FirTypeRef> getSuperTypeRefs() {
        return this.superTypeRefs;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterRefsOwnerBuilder
    /* JADX INFO: renamed from: build */
    public FirJavaClass mo6810build() {
        return new FirJavaClass(getSource(), getModuleData(), FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES(), getName(), UtilsKt.javaOrigin(isFromSource()), FirBuilderDslKt.toMutableOrEmpty(this.javaAnnotations), getStatus(), getClassKind(), getDeclarations(), getScopeProvider(), getSymbol(), getSuperTypeRefs(), getTypeParameters(), this.javaPackage, getJavaTypeParameterStack(), this.existingNestedClassifierNames, null);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ FirDeclarationOrigin getOrigin() {
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder, org.jetbrains.kotlin.fir.declarations.builder.FirDeclarationBuilder
    public /* synthetic */ void setOrigin(FirDeclarationOrigin value) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException();
    }
}
