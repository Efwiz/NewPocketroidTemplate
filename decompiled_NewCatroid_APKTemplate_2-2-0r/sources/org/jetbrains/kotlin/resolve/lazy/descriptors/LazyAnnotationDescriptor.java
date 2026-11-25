package org.jetbrains.kotlin.resolve.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.apache.xerces.impl.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValidateableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.FilteredByPredicateAnnotations;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.AnnotationResolver;
import org.jetbrains.kotlin.resolve.AnnotationResolverImpl;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.OverloadResolutionResults;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.LazyEntity;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: LazyAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003:\u0001)B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010$\u001a\u00020%H\u0016J\u000e\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u000bJ\b\u0010(\u001a\u00020%H\u0016R+\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\n8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001b\u0010\u001b\u001a\u00020\u001c8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0010\u001a\u0004\b\u001d\u0010\u001eR7\u0010 \u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\u00180!0\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0010\u001a\u0004\b\"\u0010\u000e¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/resolve/lazy/LazyEntity;", "Lorg/jetbrains/kotlin/descriptors/ValidateableDescriptor;", "c", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;", "annotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "(Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;)V", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllValueArguments", "()Ljava/util/Map;", "allValueArguments$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "getAnnotationEntry", "()Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getC", "()Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationsContext;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSource", "()Lorg/jetbrains/kotlin/descriptors/SourceElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "type$delegate", "valueArgumentsWithSourceInfo", "Lkotlin/Pair;", "getValueArgumentsWithSourceInfo", "valueArgumentsWithSourceInfo$delegate", "forceResolveAllContents", "", "getSourceForArgument", "name", Constants.DOM_VALIDATE, "FileDescriptorForVisibilityChecks", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyAnnotationDescriptor implements ValidateableDescriptor, AnnotationDescriptor, LazyEntity {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyAnnotationDescriptor.class), ModuleXmlParser.TYPE, "getType()Lorg/jetbrains/kotlin/types/KotlinType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyAnnotationDescriptor.class), "valueArgumentsWithSourceInfo", "getValueArgumentsWithSourceInfo()Ljava/util/Map;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};

    /* JADX INFO: renamed from: allValueArguments$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue allValueArguments;
    private final KtAnnotationEntry annotationEntry;
    private final LazyAnnotationsContext c;
    private final LexicalScope scope;
    private final SourceElement source;

    /* JADX INFO: renamed from: type$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue type;

    /* JADX INFO: renamed from: valueArgumentsWithSourceInfo$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue valueArgumentsWithSourceInfo;

    public LazyAnnotationDescriptor(LazyAnnotationsContext c, KtAnnotationEntry annotationEntry) {
        LexicalScope.Base scope;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
        this.c = c;
        this.annotationEntry = annotationEntry;
        this.type = c.getStorageManager().createLazyValue(new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor$type$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinType invoke() {
                KotlinType kotlinTypeResolveAnnotationType = this.this$0.getC().getAnnotationResolver().resolveAnnotationType(this.this$0.scope, this.this$0.getAnnotationEntry(), this.this$0.getC().getTrace());
                if (!(kotlinTypeResolveAnnotationType instanceof AbbreviatedType)) {
                    return kotlinTypeResolveAnnotationType;
                }
                Annotations annotations = kotlinTypeResolveAnnotationType.getAnnotations();
                LazyAnnotationDescriptor lazyAnnotationDescriptor = this.this$0;
                if ((annotations instanceof Collection) && ((Collection) annotations).isEmpty()) {
                    return kotlinTypeResolveAnnotationType;
                }
                Iterator<AnnotationDescriptor> it = annotations.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(it.next(), lazyAnnotationDescriptor)) {
                        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = ((AbbreviatedType) kotlinTypeResolveAnnotationType).getAbbreviation().getConstructor().mo7434getDeclarationDescriptor();
                        if (classifierDescriptorMo7434getDeclarationDescriptor != null) {
                            LazyAnnotationDescriptor lazyAnnotationDescriptor2 = this.this$0;
                            lazyAnnotationDescriptor2.getC().getTrace().report(Errors.RECURSIVE_TYPEALIAS_EXPANSION.on(lazyAnnotationDescriptor2.getAnnotationEntry(), classifierDescriptorMo7434getDeclarationDescriptor));
                        }
                        Annotations annotations2 = kotlinTypeResolveAnnotationType.getAnnotations();
                        final LazyAnnotationDescriptor lazyAnnotationDescriptor3 = this.this$0;
                        return TypeUtilsKt.replaceAnnotations(kotlinTypeResolveAnnotationType, new FilteredByPredicateAnnotations(annotations2, new Function1<AnnotationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor$type$2.3
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Boolean invoke(AnnotationDescriptor it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                return Boolean.valueOf(!Intrinsics.areEqual(it2, lazyAnnotationDescriptor3));
                            }
                        }));
                    }
                }
                return kotlinTypeResolveAnnotationType;
            }
        }, new Function1<Boolean, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor$type$3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ KotlinType invoke(Boolean bool) {
                return invoke(bool.booleanValue());
            }

            public final KotlinType invoke(boolean z) {
                return ErrorUtils.createErrorType(ErrorTypeKind.RECURSIVE_ANNOTATION_TYPE, new String[0]);
            }
        });
        this.source = KotlinSourceElementKt.toSourceElement(annotationEntry);
        DeclarationDescriptor ownerDescriptor = c.getScope().getOwnerDescriptor();
        PackageFragmentDescriptor packageFragmentDescriptor = ownerDescriptor instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) ownerDescriptor : null;
        if (packageFragmentDescriptor != null) {
            scope = new LexicalScope.Base(c.getScope(), new FileDescriptorForVisibilityChecks(getSource(), packageFragmentDescriptor));
        } else {
            scope = c.getScope();
        }
        this.scope = scope;
        this.valueArgumentsWithSourceInfo = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends Pair<? extends ConstantValue<?>, ? extends SourceElement>>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor$valueArgumentsWithSourceInfo$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends Pair<? extends ConstantValue<?>, ? extends SourceElement>> invoke() {
                OverloadResolutionResults<FunctionDescriptor> overloadResolutionResultsResolveAnnotationCall = this.this$0.getC().getAnnotationResolver().resolveAnnotationCall(this.this$0.getAnnotationEntry(), this.this$0.scope, this.this$0.getC().getTrace());
                AnnotationResolverImpl.checkAnnotationType(this.this$0.getAnnotationEntry(), this.this$0.getC().getTrace(), overloadResolutionResultsResolveAnnotationCall);
                if (!overloadResolutionResultsResolveAnnotationCall.isSingleResult()) {
                    return MapsKt.emptyMap();
                }
                Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = overloadResolutionResultsResolveAnnotationCall.mo7436getResultingCall().getValueArguments();
                Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
                LazyAnnotationDescriptor lazyAnnotationDescriptor = this.this$0;
                ArrayList arrayList = new ArrayList();
                for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
                    ValueParameterDescriptor key = entry.getKey();
                    ResolvedValueArgument value = entry.getValue();
                    Object obj = null;
                    if (value != null) {
                        AnnotationResolver annotationResolver = lazyAnnotationDescriptor.getC().getAnnotationResolver();
                        BindingTrace trace = lazyAnnotationDescriptor.getC().getTrace();
                        Intrinsics.checkNotNull(key);
                        ConstantValue<?> annotationArgumentValue = annotationResolver.getAnnotationArgumentValue(trace, key, value);
                        if (annotationArgumentValue != null) {
                            Name name2 = key.getName();
                            List<ValueArgument> arguments = value.getArguments();
                            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                            ValueArgument valueArgument = (ValueArgument) CollectionsKt.firstOrNull((List) arguments);
                            obj = TuplesKt.to(name2, TuplesKt.to(annotationArgumentValue, KotlinSourceElementKt.toSourceElement((KtPureElement) (valueArgument != null ? valueArgument.getArgumentExpression() : null))));
                        }
                    }
                    if (obj != null) {
                        arrayList.add(obj);
                    }
                }
                return MapsKt.toMap(arrayList);
            }
        });
        this.allValueArguments = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends ConstantValue<?>>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyAnnotationDescriptor$allValueArguments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends ConstantValue<?>> invoke() {
                Map valueArgumentsWithSourceInfo = this.this$0.getValueArgumentsWithSourceInfo();
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(valueArgumentsWithSourceInfo.size()));
                for (Map.Entry entry : valueArgumentsWithSourceInfo.entrySet()) {
                    linkedHashMap.put(entry.getKey(), (ConstantValue) ((Pair) entry.getValue()).getFirst());
                }
                return linkedHashMap;
            }
        });
        c.getTrace().record(BindingContext.ANNOTATION, annotationEntry, this);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public FqName getFqName() {
        return AnnotationDescriptor.DefaultImpls.getFqName(this);
    }

    public final LazyAnnotationsContext getC() {
        return this.c;
    }

    public final KtAnnotationEntry getAnnotationEntry() {
        return this.annotationEntry;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public KotlinType getType() {
        return (KotlinType) StorageKt.getValue(this.type, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public SourceElement getSource() {
        return this.source;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<Name, Pair<ConstantValue<?>, SourceElement>> getValueArgumentsWithSourceInfo() {
        return (Map) StorageKt.getValue(this.valueArgumentsWithSourceInfo, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return (Map) StorageKt.getValue(this.allValueArguments, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    public final SourceElement getSourceForArgument(Name name2) {
        SourceElement second;
        Intrinsics.checkNotNullParameter(name2, "name");
        Pair<ConstantValue<?>, SourceElement> pair = getValueArgumentsWithSourceInfo().get(name2);
        if (pair != null && (second = pair.getSecond()) != null) {
            return second;
        }
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return NO_SOURCE;
    }

    @Override // org.jetbrains.kotlin.descriptors.ValidateableDescriptor
    public void validate() {
        if (this.scope == null) {
            throw new IllegalStateException(("scope == null for " + this).toString());
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.LazyEntity
    public void forceResolveAllContents() {
        ForceResolveUtil.forceResolveAllContents(getType());
        getAllValueArguments();
    }

    /* JADX INFO: compiled from: LazyAnnotations.kt */
    @Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0002\u0010\u0006J7\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u00112\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u0011\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u0002H\u0011H\u0016¢\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\u00020\u00172\u0014\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0013H\u0016J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\t\u0010\u001a\u001a\u00020\u001bH\u0096\u0001J\t\u0010\u001c\u001a\u00020\u001dH\u0096\u0001J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0000H\u0016J\b\u0010!\u001a\u00020\u0004H\u0016J\b\u0010\"\u001a\u00020#H\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0005\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\fX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyAnnotationDescriptor$FileDescriptorForVisibilityChecks;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithSource;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "containingDeclaration", "(Lorg/jetbrains/kotlin/descriptors/SourceElement;Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;)V", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptVoid", "", "Ljava/lang/Void;", "error", "getContainingDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getOriginal", "getSource", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FileDescriptorForVisibilityChecks implements DeclarationDescriptorWithSource, PackageFragmentDescriptor {
        private final PackageFragmentDescriptor containingDeclaration;
        private final SourceElement source;

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        /* JADX INFO: renamed from: getContainingDeclaration */
        public ModuleDescriptor getModuleDescriptor() {
            return this.containingDeclaration.getModuleDescriptor();
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
        public FqName getFqName() {
            return this.containingDeclaration.getFqName();
        }

        @Override // org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor
        /* JADX INFO: renamed from: getMemberScope */
        public MemberScope getScope() {
            return this.containingDeclaration.getScope();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public FileDescriptorForVisibilityChecks getOriginal() {
            return this;
        }

        public FileDescriptorForVisibilityChecks(SourceElement source, PackageFragmentDescriptor containingDeclaration) {
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
            this.source = source;
            this.containingDeclaration = containingDeclaration;
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        /* JADX INFO: renamed from: acceptVoid, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ void mo7454acceptVoid(DeclarationDescriptorVisitor declarationDescriptorVisitor) {
            acceptVoid((DeclarationDescriptorVisitor<Void, Void>) declarationDescriptorVisitor);
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
        public Annotations getAnnotations() {
            return Annotations.INSTANCE.getEMPTY();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
        public SourceElement getSource() {
            return this.source;
        }

        @Override // org.jetbrains.kotlin.descriptors.Named
        public Name getName() {
            Name nameSpecial = Name.special("< file descriptor for annotation resolution >");
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            return nameSpecial;
        }

        private final Void error() {
            throw new IllegalStateException("This method should not be called".toString());
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
            error();
            throw null;
        }

        public Void acceptVoid(DeclarationDescriptorVisitor<Void, Void> visitor) {
            error();
            throw null;
        }

        public String toString() {
            return getName().asString() + " declared in LazyAnnotations.kt";
        }
    }
}
