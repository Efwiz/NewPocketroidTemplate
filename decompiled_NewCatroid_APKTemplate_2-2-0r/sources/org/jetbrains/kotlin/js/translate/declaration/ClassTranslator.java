package org.jetbrains.kotlin.js.translate.declaration;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.DeferredCallSite;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.StaticContext;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.context.UsageTracker;
import org.jetbrains.kotlin.js.translate.context.UsageTrackerKt;
import org.jetbrains.kotlin.js.translate.expression.FunctionTranslatorKt;
import org.jetbrains.kotlin.js.translate.extensions.JsSyntheticTranslateExtension;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.initializer.ClassInitializerTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.FunctionBodyTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.js.translate.utils.jsAstUtils.AstUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.CommonSupertypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: ClassTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 K2\u00020\u0001:\u0002KLB+\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u0005H\u0002J\b\u0010 \u001a\u00020\u001dH\u0002J\b\u0010!\u001a\u00020\u001dH\u0002J\u0016\u0010\"\u001a\u00020\u001d2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u001bH\u0002J\b\u0010%\u001a\u00020\u001dH\u0002J\b\u0010&\u001a\u00020\u001dH\u0002J\b\u0010'\u001a\u00020(H\u0002J\b\u0010)\u001a\u00020*H\u0002J\u001e\u0010+\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00052\f\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-H\u0002J\u0018\u0010/\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u00100\u001a\u000201H\u0002J\u0016\u00102\u001a\u00020\u001d2\f\u00103\u001a\b\u0012\u0004\u0012\u00020\u00120-H\u0002J\u0018\u00104\u001a\u00020\u001d2\u0006\u00105\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u000206H\u0002J\u000e\u00107\u001a\b\u0012\u0004\u0012\u0002080-H\u0002J\b\u00109\u001a\u00020\u0014H\u0002J\b\u0010:\u001a\u00020\u0014H\u0002J\u0010\u0010;\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0018\u0010<\u001a\u00020\u001d2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010=\u001a\u00020(H\u0002J\b\u0010>\u001a\u00020\u001dH\u0002J \u0010?\u001a\u00020\u001d2\u0006\u0010=\u001a\u00020(2\u0006\u00105\u001a\u00020\u00052\u0006\u0010@\u001a\u00020AH\u0002J\u0010\u0010B\u001a\u00020\u001d2\u0006\u0010C\u001a\u00020\u0005H\u0002J\u001b\u0010D\u001a\u0002HE\"\b\b\u0000\u0010E*\u00020F*\u0002HEH\u0002¢\u0006\u0002\u0010GJ\u0014\u0010H\u001a\u00020\u0005*\u00020\u00052\u0006\u0010I\u001a\u00020JH\u0002R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0018R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/ClassTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "classDeclaration", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "enumInitializerName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "ordinal", "", "(Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Ljava/lang/Integer;)V", "allConstructors", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/js/translate/declaration/ClassTranslator$ConstructorInfo;", "getAllConstructors", "()Lkotlin/sequences/Sequence;", "cachedInstanceName", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isLocalClass", "", "()Z", "metadataLiteral", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "Ljava/lang/Integer;", "primaryConstructor", "secondaryConstructors", "", "addClosureParameters", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "nonConstructorContext", "addMetadataObject", "addMetadataType", "addObjectCache", "statements", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "addObjectMethods", "addSuperclassReferences", "createEnumInitFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "createMetadataRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "emitConstructors", "callSites", "", "Lorg/jetbrains/kotlin/js/translate/context/DeferredCallSite;", "generateClassSyntheticParts", "declarationVisitor", "Lorg/jetbrains/kotlin/js/translate/declaration/DeclarationBodyVisitor;", "generateEnumStandardMethods", TypeBlock.NAME_entries, "generateSecondaryConstructor", "classContext", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "getSupertypesNameReferences", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "isObjectLike", "isTrait", "mayBeAddThrowableProperties", "maybeConvertInterfaceToSamAdapter", "constructorFunction", "translate", "translatePrimaryConstructor", "delegationTranslator", "Lorg/jetbrains/kotlin/js/translate/declaration/DelegationTranslator;", "translatePropertiesAsConstructorParameters", "classDeclarationContext", "withDefaultLocation", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "withUsageTrackerIfNecessary", "innerDescriptor", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "Companion", "ConstructorInfo", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassTranslator extends AbstractTranslator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private JsName cachedInstanceName;
    private final KtPureClassOrObject classDeclaration;
    private final ClassDescriptor descriptor;
    private final JsName enumInitializerName;
    private final JsObjectLiteral metadataLiteral;
    private final Integer ordinal;
    private ConstructorInfo primaryConstructor;
    private final List<ConstructorInfo> secondaryConstructors;

    /* JADX INFO: compiled from: ClassTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ENUM_ENTRY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ ClassTranslator(KtPureClassOrObject ktPureClassOrObject, TranslationContext translationContext, JsName jsName, Integer num, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktPureClassOrObject, translationContext, jsName, num);
    }

    @JvmStatic
    public static final void translate(KtClassOrObject ktClassOrObject, TranslationContext translationContext, JsName jsName) {
        INSTANCE.translate(ktClassOrObject, translationContext, jsName);
    }

    @JvmStatic
    public static final void translate(KtEnumEntry ktEnumEntry, TranslationContext translationContext, JsName jsName, int i) {
        INSTANCE.translate(ktEnumEntry, translationContext, jsName, i);
    }

    @JvmStatic
    public static final void translate(KtPureClassOrObject ktPureClassOrObject, TranslationContext translationContext) {
        INSTANCE.translate(ktPureClassOrObject, translationContext);
    }

    private ClassTranslator(KtPureClassOrObject ktPureClassOrObject, TranslationContext translationContext, JsName jsName, Integer num) {
        super(translationContext);
        this.classDeclaration = ktPureClassOrObject;
        this.enumInitializerName = jsName;
        this.ordinal = num;
        ClassDescriptor classDescriptor = BindingUtils.getClassDescriptor(translationContext.bindingContext(), ktPureClassOrObject);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "getClassDescriptor(...)");
        this.descriptor = classDescriptor;
        this.secondaryConstructors = new ArrayList();
        this.metadataLiteral = new JsObjectLiteral(true);
    }

    private final boolean isTrait() {
        return this.descriptor.getClassKind() == ClassKind.INTERFACE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void translate() {
        JsFunction jsFunctionCreateRootScopedFunction;
        TranslationContext translationContextNewDeclaration = context().newDeclaration(this.descriptor);
        Intrinsics.checkNotNullExpressionValue(translationContextNewDeclaration, "newDeclaration(...)");
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = this.descriptor.mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null || (jsFunctionCreateRootScopedFunction = translationContextNewDeclaration.getFunctionObject(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor)) == null) {
            jsFunctionCreateRootScopedFunction = translationContextNewDeclaration.createRootScopedFunction(this.descriptor);
        }
        Intrinsics.checkNotNull(jsFunctionCreateRootScopedFunction);
        jsFunctionCreateRootScopedFunction.setName(translationContextNewDeclaration.getInnerNameForDescriptor(this.descriptor));
        translationContextNewDeclaration.addDeclarationStatement(jsFunctionCreateRootScopedFunction.makeStmt());
        JsFunction jsFunctionCreateEnumInitFunction = this.descriptor.getClassKind() == ClassKind.ENUM_CLASS ? createEnumInitFunction() : null;
        TranslationContext translationContextWithUsageTrackerIfNecessary = withUsageTrackerIfNecessary(translationContextNewDeclaration, this.descriptor);
        translationContextWithUsageTrackerIfNecessary.startDeclaration();
        DelegationTranslator delegationTranslator = new DelegationTranslator(this.classDeclaration, translationContextWithUsageTrackerIfNecessary);
        translatePropertiesAsConstructorParameters(translationContextWithUsageTrackerIfNecessary);
        DeclarationBodyVisitor declarationBodyVisitor = new DeclarationBodyVisitor(this.descriptor, translationContextWithUsageTrackerIfNecessary, jsFunctionCreateEnumInitFunction);
        declarationBodyVisitor.traverseContainer(this.classDeclaration, translationContextWithUsageTrackerIfNecessary);
        final ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = this.descriptor.mo6346getCompanionObjectDescriptor();
        if (classDescriptorMo6346getCompanionObjectDescriptor instanceof SyntheticClassOrObjectDescriptor) {
            declarationBodyVisitor.generateClassOrObject(((SyntheticClassOrObjectDescriptor) classDescriptorMo6346getCompanionObjectDescriptor).getSyntheticDeclaration(), translationContextWithUsageTrackerIfNecessary, true);
        }
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(this.descriptor.getUnsubstitutedMemberScope().getContributedDescriptors(DescriptorKindFilter.CLASSIFIERS, MemberScope.INSTANCE.getALL_NAME_FILTER())), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$translate$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof SyntheticClassOrObjectDescriptor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        Iterator it = SequencesKt.filter(sequenceFilter, new Function1<SyntheticClassOrObjectDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.translate.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(SyntheticClassOrObjectDescriptor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it2, classDescriptorMo6346getCompanionObjectDescriptor));
            }
        }).iterator();
        while (it.hasNext()) {
            declarationBodyVisitor.generateClassOrObject(((SyntheticClassOrObjectDescriptor) it.next()).getSyntheticDeclaration(), translationContextWithUsageTrackerIfNecessary, false);
        }
        generateClassSyntheticParts(translationContextWithUsageTrackerIfNecessary, declarationBodyVisitor);
        mayBeAddThrowableProperties(translationContextNewDeclaration);
        List<JsStatement> statements = jsFunctionCreateRootScopedFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        CollectionsKt.addAll(statements, declarationBodyVisitor.getInitializerStatements());
        delegationTranslator.generateDelegated();
        if (jsFunctionCreateEnumInitFunction != null && classDescriptorMo6346getCompanionObjectDescriptor != null) {
            JsInvocation jsInvocation = new JsInvocation(JsAstUtils.pureFqn(context().getNameForObjectInstance(classDescriptorMo6346getCompanionObjectDescriptor), (JsExpression) null), new JsExpression[0]);
            List<JsStatement> statements2 = jsFunctionCreateEnumInitFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            SourceElement source = classDescriptorMo6346getCompanionObjectDescriptor.getSourceElement();
            Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
            statements2.add(JsAstUtils.asSyntheticStatement(jsInvocation.source((Object) PsiSourceElementKt.getPsi(source))));
        }
        maybeConvertInterfaceToSamAdapter(translationContextNewDeclaration, jsFunctionCreateRootScopedFunction);
        translatePrimaryConstructor(jsFunctionCreateRootScopedFunction, translationContextNewDeclaration, delegationTranslator);
        addMetadataObject();
        addMetadataType();
        translationContextNewDeclaration.addClass(this.descriptor);
        addSuperclassReferences();
        List<KtSecondaryConstructor> secondaryConstructors = this.classDeclaration.getSecondaryConstructors();
        Intrinsics.checkNotNullExpressionValue(secondaryConstructors, "getSecondaryConstructors(...)");
        for (KtSecondaryConstructor ktSecondaryConstructor : secondaryConstructors) {
            Intrinsics.checkNotNull(ktSecondaryConstructor);
            generateSecondaryConstructor(translationContextNewDeclaration, ktSecondaryConstructor);
        }
        if (this.classDeclaration instanceof KtClassOrObject) {
            if (this.descriptor.isData()) {
                new JsDataClassGenerator((KtClassOrObject) this.classDeclaration, translationContextNewDeclaration).generate();
            } else if (this.descriptor.isInline() || this.descriptor.isValue()) {
                new JsInlineClassGenerator((KtClassOrObject) this.classDeclaration, translationContextNewDeclaration).generate();
            }
        }
        List<DeferredCallSite> listEndDeclaration = translationContextWithUsageTrackerIfNecessary.endDeclaration();
        Intrinsics.checkNotNullExpressionValue(listEndDeclaration, "endDeclaration(...)");
        emitConstructors(translationContextWithUsageTrackerIfNecessary, listEndDeclaration);
        Iterator<ConstructorInfo> it2 = getAllConstructors().iterator();
        while (it2.hasNext()) {
            addClosureParameters(it2.next(), translationContextWithUsageTrackerIfNecessary);
        }
        if (isObjectLike()) {
            addObjectMethods();
        }
        if (this.descriptor.getClassKind() == ClassKind.ENUM_CLASS) {
            generateEnumStandardMethods(declarationBodyVisitor.getEnumEntries());
        }
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(this.descriptor);
        if (superClassNotAny != null) {
            ReferenceTranslator.translateAsTypeReference(superClassNotAny, translationContextNewDeclaration);
        }
    }

    private final void generateClassSyntheticParts(TranslationContext context, DeclarationBodyVisitor declarationVisitor) {
        JsSyntheticTranslateExtension.Companion companion = JsSyntheticTranslateExtension.INSTANCE;
        Project project = context.getConfig().getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        Iterator<T> it = companion.getInstances(project).iterator();
        while (it.hasNext()) {
            ((JsSyntheticTranslateExtension) it.next()).generateClassSyntheticParts(this.classDeclaration, this.descriptor, declarationVisitor, context);
        }
    }

    private final TranslationContext withUsageTrackerIfNecessary(TranslationContext translationContext, MemberDescriptor memberDescriptor) {
        if (isLocalClass()) {
            TranslationContext translationContextInnerWithUsageTracker = translationContext.innerWithUsageTracker(memberDescriptor);
            Intrinsics.checkNotNull(translationContextInnerWithUsageTracker);
            return translationContextInnerWithUsageTracker;
        }
        TranslationContext translationContextInner = translationContext.inner(memberDescriptor);
        Intrinsics.checkNotNull(translationContextInner);
        return translationContextInner;
    }

    private final boolean isLocalClass() {
        return !(this.descriptor.getContainingModule() instanceof ClassOrPackageFragmentDescriptor);
    }

    private final void translatePrimaryConstructor(JsFunction constructorFunction, TranslationContext classContext, DelegationTranslator delegationTranslator) {
        if (isTrait()) {
            return;
        }
        TranslationContext translationContextInnerWithUsageTracker = classContext.innerWithUsageTracker(this.descriptor);
        Intrinsics.checkNotNullExpressionValue(translationContextInnerWithUsageTracker, "innerWithUsageTracker(...)");
        if (isObjectLike()) {
            List<JsStatement> statements = constructorFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            addObjectCache(statements);
        }
        ClassInitializerTranslator classInitializerTranslator = new ClassInitializerTranslator(this.classDeclaration, translationContextInnerWithUsageTracker, constructorFunction);
        Integer num = this.ordinal;
        if (num != null) {
            classInitializerTranslator.setOrdinal(num.intValue());
        }
        classInitializerTranslator.generateInitializeMethod(delegationTranslator);
        this.primaryConstructor = new ConstructorInfo(constructorFunction, translationContextInnerWithUsageTracker, this.descriptor, null, 8, null);
    }

    private final JsFunction createEnumInitFunction() {
        JsNode jsNodeWithDefaultLocation = withDefaultLocation(context().createRootScopedFunction(this.descriptor));
        Intrinsics.checkNotNullExpressionValue(jsNodeWithDefaultLocation, "withDefaultLocation(...)");
        JsFunction jsFunction = (JsFunction) jsNodeWithDefaultLocation;
        jsFunction.setName(JsScope.declareTemporaryName(StaticContext.getSuggestedName(this.descriptor) + "_initFields"));
        JsNode jsNodeWithDefaultLocation2 = withDefaultLocation(context().createRootScopedFunction(this.descriptor));
        Intrinsics.checkNotNullExpressionValue(jsNodeWithDefaultLocation2, "withDefaultLocation(...)");
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(((JsBinaryOperation) withDefaultLocation(JsAstUtils.assignment(JsAstUtils.pureFqn(jsFunction.getName(), (JsExpression) null), (JsFunction) jsNodeWithDefaultLocation2))).makeStmt());
        context().addDeclarationStatement(jsFunction.makeStmt());
        return jsFunction;
    }

    private final boolean isObjectLike() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.descriptor.getClassKind().ordinal()];
        return i == 1 || i == 2;
    }

    private final void addMetadataObject() {
        context().addDeclarationStatement(JsAstUtils.assignment(createMetadataRef(), this.metadataLiteral).makeStmt());
    }

    private final JsNameRef createMetadataRef() {
        return new JsNameRef(Namer.METADATA, context().getInnerReference(this.descriptor));
    }

    private final void addMetadataType() {
        String str;
        StringBuilder sb = new StringBuilder("Kind.");
        if (DescriptorUtils.isInterface(this.descriptor)) {
            str = Namer.CLASS_KIND_INTERFACE;
        } else {
            str = DescriptorUtils.isObject(this.descriptor) ? Namer.CLASS_KIND_OBJECT : "CLASS";
        }
        sb.append(str);
        JsExpression referenceToIntrinsic = context().getReferenceToIntrinsic(sb.toString());
        Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
        List<JsPropertyInitializer> propertyInitializers = this.metadataLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        propertyInitializers.add(new JsPropertyInitializer(new JsNameRef(Namer.METADATA_CLASS_KIND), referenceToIntrinsic));
        Name name2 = this.descriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (name2.isSpecial()) {
            return;
        }
        JsPropertyInitializer jsPropertyInitializer = new JsPropertyInitializer(new JsNameRef(Namer.METADATA_SIMPLE_NAME), new JsStringLiteral(name2.getIdentifier()));
        List<JsPropertyInitializer> propertyInitializers2 = this.metadataLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
        propertyInitializers2.add(jsPropertyInitializer);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x022f  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [T, java.lang.Object, org.jetbrains.kotlin.js.translate.context.TranslationContext] */
    /* JADX WARN: Type inference failed for: r5v12, types: [T, org.jetbrains.kotlin.js.translate.context.TranslationContext] */
    /* JADX WARN: Type inference failed for: r5v31, types: [T, java.lang.Object, org.jetbrains.kotlin.js.translate.context.TranslationContext] */
    private final void generateSecondaryConstructor(TranslationContext classContext, KtSecondaryConstructor constructor) {
        ArrayList arrayList;
        DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(classContext.bindingContext(), constructor);
        Intrinsics.checkNotNull(descriptorForElement, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        final ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) descriptorForElement;
        final ClassDescriptor containingDeclaration = classConstructorDescriptor.getContainingModule();
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName("$this");
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        final JsNameRef jsNameRefMakeRef = jsNameDeclareTemporaryName.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        ReceiverParameterDescriptor thisAsReceiverParameter = containingDeclaration.getThisAsReceiverParameter();
        Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter, "getThisAsReceiverParameter(...)");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ClassConstructorDescriptor classConstructorDescriptor2 = classConstructorDescriptor;
        ?? InnerContextWithAliased = classContext.innerWithUsageTracker(classConstructorDescriptor2).innerContextWithAliased(thisAsReceiverParameter, jsNameRefMakeRef);
        Intrinsics.checkNotNullExpressionValue(InnerContextWithAliased, "innerContextWithAliased(...)");
        objectRef.element = InnerContextWithAliased;
        JsName outerClassReference = ((TranslationContext) objectRef.element).getOuterClassReference(containingDeclaration);
        ClassDescriptor classDescriptor = containingDeclaration;
        ClassDescriptor containingClass = DescriptorUtils.getContainingClass(classDescriptor);
        if (outerClassReference != null) {
            Intrinsics.checkNotNull(containingClass);
            ReceiverParameterDescriptor thisAsReceiverParameter2 = containingClass.getThisAsReceiverParameter();
            Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter2, "getThisAsReceiverParameter(...)");
            ?? InnerContextWithAliased2 = ((TranslationContext) objectRef.element).innerContextWithAliased(thisAsReceiverParameter2, outerClassReference.makeRef());
            Intrinsics.checkNotNullExpressionValue(InnerContextWithAliased2, "innerContextWithAliased(...)");
            objectRef.element = InnerContextWithAliased2;
        }
        final JsFunction functionObject = ((TranslationContext) objectRef.element).getFunctionObject(classConstructorDescriptor);
        Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
        functionObject.setName(((TranslationContext) objectRef.element).getInnerNameForDescriptor(classConstructorDescriptor));
        ((TranslationContext) objectRef.element).addDeclarationStatement(functionObject.makeStmt());
        TranslationContext translationContextContextWithScope = ((TranslationContext) objectRef.element).contextWithScope(functionObject);
        Intrinsics.checkNotNullExpressionValue(translationContextContextWithScope, "contextWithScope(...)");
        List<JsParameter> parameters = functionObject.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        objectRef.element = FunctionTranslatorKt.translateAndAliasParameters(translationContextContextWithScope, classConstructorDescriptor, parameters);
        FunctionTranslatorKt.translateFunction((TranslationContext) objectRef.element, constructor, functionObject);
        final ArrayList arrayList2 = new ArrayList();
        final JsExpression innerReference = ((TranslationContext) objectRef.element).getInnerReference(classDescriptor);
        Intrinsics.checkNotNullExpressionValue(innerReference, "getInnerReference(...)");
        ArrayList arrayList3 = arrayList2;
        arrayList3.add(new Function1<List<JsStatement>, Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.generateSecondaryConstructor.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<JsStatement> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<JsStatement> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                List<JsStatement> defaultValueForArguments = FunctionBodyTranslator.setDefaultValueForArguments(classConstructorDescriptor, objectRef.element);
                Intrinsics.checkNotNullExpressionValue(defaultValueForArguments, "setDefaultValueForArguments(...)");
                CollectionsKt.addAll(it, defaultValueForArguments);
            }
        });
        JsInvocation jsInvocationCreateObjectWithPrototypeFrom = Namer.createObjectWithPrototypeFrom(innerReference);
        Intrinsics.checkNotNullExpressionValue(jsInvocationCreateObjectWithPrototypeFrom, "createObjectWithPrototypeFrom(...)");
        final JsStatement jsStatementMakeStmt = JsAstUtils.assignment(jsNameRefMakeRef.deepCopy(), JsAstUtils.or(jsNameRefMakeRef.deepCopy(), jsInvocationCreateObjectWithPrototypeFrom)).source((Object) constructor).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        arrayList3.add(new Function1<List<JsStatement>, Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.generateSecondaryConstructor.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(List<JsStatement> list) {
                invoke2(list);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(List<JsStatement> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                it.add(jsStatementMakeStmt);
            }
        });
        final ArrayList arrayList4 = new ArrayList();
        if (this.descriptor.getClassKind() == ClassKind.ENUM_CLASS) {
            JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName("name");
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
            JsName jsNameDeclareTemporaryName3 = JsScope.declareTemporaryName("ordinal");
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName3, "declareTemporaryName(...)");
            functionObject.getParameters().addAll(0, CollectionsKt.listOf((Object[]) new JsParameter[]{new JsParameter(jsNameDeclareTemporaryName2), new JsParameter(jsNameDeclareTemporaryName3)}));
            CollectionsKt.addAll(arrayList4, CollectionsKt.listOf((Object[]) new JsNameRef[]{withDefaultLocation(jsNameDeclareTemporaryName2.makeRef()), withDefaultLocation(jsNameDeclareTemporaryName3.makeRef())}));
        }
        final List mutableList = CollectionsKt.toMutableList((Collection) arrayList4);
        if (outerClassReference != null) {
            functionObject.getParameters().add(0, new JsParameter(outerClassReference));
            mutableList.add(outerClassReference.makeRef());
        }
        List<JsParameter> parameters2 = functionObject.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
        parameters2.add(new JsParameter(jsNameDeclareTemporaryName));
        MetadataProperties.setForcedReturnVariable(functionObject, jsNameDeclareTemporaryName);
        final ResolvedCall<ConstructorDescriptor> delegationConstructorCall = BindingContextUtils.getDelegationConstructorCall(((TranslationContext) objectRef.element).bindingContext(), classConstructorDescriptor);
        ConstructorDescriptor constructorDescriptor = delegationConstructorCall != null ? (ConstructorDescriptor) delegationConstructorCall.getResultingDescriptor() : null;
        ClassConstructorDescriptor classConstructorDescriptor3 = constructorDescriptor instanceof ClassConstructorDescriptor ? (ClassConstructorDescriptor) constructorDescriptor : null;
        ClassDescriptor constructedClass = classConstructorDescriptor3 != null ? classConstructorDescriptor3.getConstructedClass() : null;
        if (delegationConstructorCall != null) {
            Intrinsics.checkNotNull(constructedClass);
            if (KotlinBuiltIns.isAny(constructedClass)) {
                arrayList = arrayList3;
            } else {
                boolean zAreEqual = Intrinsics.areEqual(constructedClass, containingDeclaration);
                if (JsDescriptorUtils.isImmediateSubtypeOfError(containingDeclaration) && !zAreEqual) {
                    arrayList = arrayList3;
                    arrayList.add(new Function1<List<JsStatement>, Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.generateSecondaryConstructor.3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(List<JsStatement> list) {
                            invoke2(list);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(List<JsStatement> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            TranslationContext translationContextInnerBlock = ClassTranslator.this.context().innerBlock();
                            Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
                            ClassInitializerTranslator.emulateSuperCallToNativeError(translationContextInnerBlock, containingDeclaration, delegationConstructorCall, jsNameRefMakeRef.deepCopy());
                            List<JsStatement> statements = translationContextInnerBlock.getCurrentBlock().getStatements();
                            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                            CollectionsKt.addAll(it, statements);
                        }
                    });
                } else {
                    arrayList = arrayList3;
                    arrayList.add(new Function1<List<JsStatement>, Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.generateSecondaryConstructor.4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(List<JsStatement> list) {
                            invoke2(list);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(List<JsStatement> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            CallableDescriptor resultingDescriptor = delegationConstructorCall.getResultingDescriptor();
                            Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
                            ConstructorDescriptor constructorDescriptor2 = (ConstructorDescriptor) resultingDescriptor;
                            TranslationContext translationContextInnerBlock = objectRef.element.innerBlock();
                            Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
                            List mutableList2 = CollectionsKt.toMutableList((Collection) arrayList4);
                            ClassDescriptor constructedClass2 = ((ConstructorDescriptor) delegationConstructorCall.getResultingDescriptor()).getConstructedClass();
                            Intrinsics.checkNotNullExpressionValue(constructedClass2, "getConstructedClass(...)");
                            if (constructedClass2.getIsInner()) {
                                DeclarationDescriptor containingDeclaration2 = constructedClass2.getContainingModule();
                                Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                                ReceiverParameterDescriptor thisAsReceiverParameter3 = ((ClassDescriptor) containingDeclaration2).getThisAsReceiverParameter();
                                Intrinsics.checkNotNullExpressionValue(thisAsReceiverParameter3, "getThisAsReceiverParameter(...)");
                                mutableList2.add(objectRef.element.getDispatchReceiver(thisAsReceiverParameter3));
                            }
                            JsExpression jsExpressionTranslate$default = CallTranslator.translate$default(translationContextInnerBlock, delegationConstructorCall, null, 4, null);
                            int size = constructorDescriptor2.getValueParameters().size();
                            JsNameRef jsNameRefDeepCopy = jsNameRefMakeRef.deepCopy();
                            Intrinsics.checkNotNullExpressionValue(jsNameRefDeepCopy, "deepCopy(...)");
                            JsStatement jsStatementMakeStmt2 = AstUtilsKt.toInvocationWith(jsExpressionTranslate$default, mutableList2, size, jsNameRefDeepCopy).source((Object) delegationConstructorCall.get$fakeCall().getCallElement()).makeStmt();
                            Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt2, "makeStmt(...)");
                            List<JsStatement> list = it;
                            List<JsStatement> statements = translationContextInnerBlock.getCurrentBlock().getStatements();
                            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                            CollectionsKt.addAll(list, statements);
                            list.add(jsStatementMakeStmt2);
                        }
                    });
                }
            }
        } else {
            arrayList = arrayList3;
        }
        if (!Intrinsics.areEqual(constructedClass, containingDeclaration) && !org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt.hasPrimaryConstructor(containingDeclaration)) {
            arrayList.add(new Function1<List<JsStatement>, Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.generateSecondaryConstructor.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(List<JsStatement> list) {
                    invoke2(list);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(List<JsStatement> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    UsageTracker usageTracker = objectRef.element.usageTracker();
                    Intrinsics.checkNotNull(usageTracker);
                    List<DeclarationDescriptor> classOrConstructorClosure = objectRef.element.getClassOrConstructorClosure(containingDeclaration);
                    if (classOrConstructorClosure == null) {
                        classOrConstructorClosure = CollectionsKt.emptyList();
                    }
                    List<DeclarationDescriptor> list = classOrConstructorClosure;
                    ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (DeclarationDescriptor declarationDescriptor : list) {
                        Intrinsics.checkNotNull(declarationDescriptor);
                        JsName nameForCapturedDescriptor = UsageTrackerKt.getNameForCapturedDescriptor(usageTracker, declarationDescriptor);
                        Intrinsics.checkNotNull(nameForCapturedDescriptor);
                        arrayList5.add(nameForCapturedDescriptor.makeRef());
                    }
                    it.add(((JsInvocation) this.withDefaultLocation(new JsInvocation(Namer.getFunctionCallRef(innerReference), (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.listOf(jsNameRefMakeRef.deepCopy()), (Iterable) arrayList5), (Iterable) mutableList)))).makeStmt());
                }
            });
        }
        List<JsStatement> statements = functionObject.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        JsReturn jsReturn = new JsReturn(jsNameRefMakeRef.deepCopy());
        jsReturn.setSource(constructor);
        statements.add(jsReturn);
        this.secondaryConstructors.add(new ConstructorInfo(functionObject, (TranslationContext) objectRef.element, classConstructorDescriptor2, new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$generateSecondaryConstructor$compositeSuperCallGenerator$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                ArrayList arrayList5 = new ArrayList();
                Iterator<Function1<List<JsStatement>, Unit>> it = arrayList2.iterator();
                while (it.hasNext()) {
                    it.next().invoke(arrayList5);
                }
                functionObject.getBody().getStatements().addAll(0, arrayList5);
            }
        }));
        ((TranslationContext) objectRef.element).export(classConstructorDescriptor2);
    }

    private final Sequence<ConstructorInfo> getAllConstructors() {
        ConstructorInfo constructorInfo = this.primaryConstructor;
        return constructorInfo != null ? SequencesKt.plus(SequencesKt.sequenceOf(constructorInfo), (Iterable) this.secondaryConstructors) : CollectionsKt.asSequence(this.secondaryConstructors);
    }

    private final void emitConstructors(TranslationContext nonConstructorContext, List<DeferredCallSite> callSites) {
        Set<DeclarationDescriptor> setEmptySet;
        ResolvedCall<ConstructorDescriptor> delegationConstructorCall;
        ConstructorInfo constructorInfo;
        ConstructorDescriptor constructorDescriptor;
        Map map = MapsKt.toMap(SequencesKt.map(getAllConstructors(), new Function1<ConstructorInfo, Pair<? extends MemberDescriptor, ? extends ConstructorInfo>>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$emitConstructors$constructorMap$1
            @Override // kotlin.jvm.functions.Function1
            public final Pair<MemberDescriptor, ClassTranslator.ConstructorInfo> invoke(ClassTranslator.ConstructorInfo it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return TuplesKt.to(it.getDescriptor(), it);
            }
        }));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : callSites) {
            ConstructorDescriptor constructor = ((DeferredCallSite) obj).getConstructor();
            if (constructor.isPrimary()) {
                constructorDescriptor = constructor;
                ClassifierDescriptorWithTypeParameters containingDeclaration = constructor.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                constructorDescriptor = containingDeclaration;
            }
            constructorDescriptor = constructor;
            ConstructorDescriptor constructorDescriptor2 = constructorDescriptor;
            Object obj2 = linkedHashMap.get(constructorDescriptor2);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(constructorDescriptor2, obj2);
            }
            ((List) obj2).add(obj);
        }
        List<ConstructorInfo> list = this.secondaryConstructors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (ConstructorInfo constructorInfo2 : list) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            MemberDescriptor descriptor = constructorInfo2.getDescriptor();
            if ((descriptor instanceof ConstructorDescriptor) && (delegationConstructorCall = BindingContextUtils.getDelegationConstructorCall(context().bindingContext(), (ConstructorDescriptor) descriptor)) != null && (constructorInfo = (ConstructorInfo) map.get(delegationConstructorCall.getResultingDescriptor())) != null) {
                linkedHashSet.add(constructorInfo);
            }
            arrayList.add(new Pair(constructorInfo2, linkedHashSet));
        }
        final Map map2 = MapsKt.toMap(arrayList);
        List list2 = DFS.topologicalOrder(SequencesKt.asIterable(getAllConstructors()), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj3) {
                return ClassTranslator.emitConstructors$lambda$9(map2, (ClassTranslator.ConstructorInfo) obj3);
            }
        });
        Intrinsics.checkNotNullExpressionValue(list2, "topologicalOrder(...)");
        for (ConstructorInfo constructorInfo3 : CollectionsKt.reversed(list2)) {
            constructorInfo3.getSuperCallGenerator().invoke();
            UsageTracker usageTracker = nonConstructorContext.usageTracker();
            UsageTracker usageTracker2 = constructorInfo3.getContext().usageTracker();
            Intrinsics.checkNotNull(usageTracker2);
            if (isLocalClass()) {
                Intrinsics.checkNotNull(usageTracker);
                setEmptySet = usageTracker.getCapturedDescriptors();
            } else {
                setEmptySet = SetsKt.emptySet();
            }
            List<DeclarationDescriptor> listDistinct = CollectionsKt.distinct(SetsKt.plus((Set) setEmptySet, (Iterable) usageTracker2.getCapturedDescriptors()));
            MemberDescriptor descriptor2 = constructorInfo3.getDescriptor();
            DeclarationDescriptor parentOfType = DescriptorUtils.getParentOfType(descriptor2, ClassDescriptor.class, false);
            Intrinsics.checkNotNull(parentOfType);
            ClassDescriptor classDescriptor = (ClassDescriptor) parentOfType;
            nonConstructorContext.putClassOrConstructorClosure(descriptor2, listDistinct);
            List<DeferredCallSite> listEmptyList = (List) linkedHashMap.get(constructorInfo3.getDescriptor());
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            for (DeferredCallSite deferredCallSite : listEmptyList) {
                JsExpression argumentForClosureConstructor = deferredCallSite.getContext().getArgumentForClosureConstructor(classDescriptor.getThisAsReceiverParameter());
                Intrinsics.checkNotNullExpressionValue(argumentForClosureConstructor, "getArgumentForClosureConstructor(...)");
                List<DeclarationDescriptor> list3 = listDistinct;
                for (DeclarationDescriptor declarationDescriptor : list3) {
                    Intrinsics.checkNotNull(usageTracker);
                    usageTracker.used(declarationDescriptor);
                }
                ArrayList arrayList2 = new ArrayList();
                for (DeclarationDescriptor declarationDescriptor2 : list3) {
                    ArrayList arrayList3 = new ArrayList();
                    Intrinsics.checkNotNull(usageTracker);
                    JsName nameForCapturedDescriptor = UsageTrackerKt.getNameForCapturedDescriptor(usageTracker, declarationDescriptor2);
                    Intrinsics.checkNotNull(nameForCapturedDescriptor);
                    ArrayList arrayList4 = arrayList3;
                    arrayList4.add(JsAstUtils.pureFqn(nameForCapturedDescriptor, argumentForClosureConstructor));
                    if (declarationDescriptor2 instanceof TypeParameterDescriptor) {
                        JsName jsName = usageTracker.getCapturedTypes().get(declarationDescriptor2);
                        Intrinsics.checkNotNull(jsName);
                        arrayList4.add(JsAstUtils.pureFqn(jsName, argumentForClosureConstructor));
                    }
                    CollectionsKt.addAll(arrayList2, arrayList3);
                }
                deferredCallSite.getInvocationArgs().addAll(0, arrayList2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable emitConstructors$lambda$9(Map thisCalls, ConstructorInfo constructorInfo) {
        Intrinsics.checkNotNullParameter(thisCalls, "$thisCalls");
        Set setEmptySet = (Set) thisCalls.get(constructorInfo);
        if (setEmptySet == null) {
            setEmptySet = SetsKt.emptySet();
        }
        return setEmptySet;
    }

    private final void addClosureParameters(ConstructorInfo constructor, TranslationContext nonConstructorContext) {
        Map<TypeParameterDescriptor, JsName> capturedTypes;
        Map<DeclarationDescriptor, JsName> capturedDescriptorToJsName;
        UsageTracker usageTracker = constructor.getContext().usageTracker();
        Intrinsics.checkNotNull(usageTracker);
        List<DeclarationDescriptor> classOrConstructorClosure = context().getClassOrConstructorClosure(constructor.getDescriptor());
        if (classOrConstructorClosure == null) {
            return;
        }
        UsageTracker usageTracker2 = nonConstructorContext.usageTracker();
        JsFunction function = constructor.getFunction();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (DeclarationDescriptor declarationDescriptor : classOrConstructorClosure) {
            JsName jsName = null;
            JsName jsName2 = (usageTracker2 == null || (capturedDescriptorToJsName = usageTracker2.getCapturedDescriptorToJsName()) == null) ? null : capturedDescriptorToJsName.get(declarationDescriptor);
            JsName jsName3 = usageTracker.getCapturedDescriptorToJsName().get(declarationDescriptor);
            if (jsName3 == null) {
                Intrinsics.checkNotNull(jsName2);
                jsName3 = jsName2;
            }
            ArrayList arrayList3 = arrayList2;
            arrayList3.add(new JsParameter(jsName3));
            MemberDescriptor descriptor = constructor.getDescriptor();
            MemberDescriptor memberDescriptor = descriptor instanceof DeclarationDescriptorWithSource ? descriptor : null;
            SourceElement sourceElement = memberDescriptor != null ? memberDescriptor.getSourceElement() : null;
            if (jsName2 != null && Intrinsics.areEqual(constructor, this.primaryConstructor)) {
                arrayList.add(JsAstUtils.defineSimpleProperty(jsName2, jsName3.makeRef(), sourceElement));
            }
            if (declarationDescriptor instanceof TypeParameterDescriptor) {
                if (usageTracker2 != null && (capturedTypes = usageTracker2.getCapturedTypes()) != null) {
                    jsName = capturedTypes.get(declarationDescriptor);
                }
                JsName jsName4 = usageTracker.getCapturedTypes().get(declarationDescriptor);
                if (jsName4 == null) {
                    Intrinsics.checkNotNull(jsName);
                    jsName4 = jsName;
                }
                arrayList3.add(new JsParameter(jsName4));
                if (jsName != null && Intrinsics.areEqual(constructor, this.primaryConstructor)) {
                    arrayList.add(JsAstUtils.defineSimpleProperty(jsName, jsName4.makeRef(), sourceElement));
                }
            }
        }
        function.getParameters().addAll(0, arrayList2);
        function.getBody().getStatements().addAll(0, arrayList);
    }

    private final void addSuperclassReferences() {
        JsArrayLiteral jsArrayLiteral = new JsArrayLiteral(getSupertypesNameReferences());
        List<JsPropertyInitializer> propertyInitializers = this.metadataLiteral.getPropertyInitializers();
        Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
        propertyInitializers.add(new JsPropertyInitializer(new JsNameRef(Namer.METADATA_SUPERTYPES), jsArrayLiteral));
    }

    private final List<JsExpression> getSupertypesNameReferences() {
        List<KotlinType> supertypesWithoutFakes = JsDescriptorUtils.getSupertypesWithoutFakes(this.descriptor);
        Intrinsics.checkNotNullExpressionValue(supertypesWithoutFakes, "getSupertypesWithoutFakes(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : supertypesWithoutFakes) {
            if (!(((KotlinType) obj).getConstructor().getThis$0() instanceof FunctionClassDescriptor)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        if (arrayList2.size() == 1) {
            ClassDescriptor classDescriptorForType = DescriptorUtils.getClassDescriptorForType((KotlinType) arrayList2.get(0));
            Intrinsics.checkNotNullExpressionValue(classDescriptorForType, "getClassDescriptorForType(...)");
            if (!AnnotationsUtils.isNativeObject(classDescriptorForType)) {
                return CollectionsKt.listOf(ReferenceTranslator.translateAsTypeReference(classDescriptorForType, context()));
            }
            return CollectionsKt.emptyList();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((KotlinType) it.next()).getConstructor());
        }
        List<TypeConstructor> list = CommonSupertypes.topologicallySortSuperclassesAndRecordAllInstances(this.descriptor.getDefaultType(), new LinkedHashMap(), new LinkedHashSet());
        Intrinsics.checkNotNullExpressionValue(list, "topologicallySortSupercl…ndRecordAllInstances(...)");
        ArrayList arrayList3 = new ArrayList();
        for (TypeConstructor typeConstructor : list) {
            if (linkedHashSet.contains(typeConstructor)) {
                ClassDescriptor classDescriptorForTypeConstructor = DescriptorUtils.getClassDescriptorForTypeConstructor(typeConstructor);
                Intrinsics.checkNotNullExpressionValue(classDescriptorForTypeConstructor, "getClassDescriptorForTypeConstructor(...)");
                if (!AnnotationsUtils.isNativeObject(classDescriptorForTypeConstructor)) {
                    arrayList3.add(ReferenceTranslator.translateAsTypeReference(classDescriptorForTypeConstructor, context()));
                }
            }
        }
        return arrayList3;
    }

    private final void translatePropertiesAsConstructorParameters(TranslationContext classDeclarationContext) {
        Iterator<KtParameter> it = PsiUtils.getPrimaryConstructorParameters(this.classDeclaration).iterator();
        while (it.hasNext()) {
            PropertyDescriptor propertyDescriptorForConstructorParameter = BindingUtils.getPropertyDescriptorForConstructorParameter(bindingContext(), it.next());
            if (propertyDescriptorForConstructorParameter != null) {
                JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
                List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
                Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
                PropertyTranslatorKt.translateAccessors(propertyDescriptorForConstructorParameter, propertyInitializers, classDeclarationContext);
                List<JsPropertyInitializer> propertyInitializers2 = jsObjectLiteral.getPropertyInitializers();
                Intrinsics.checkNotNullExpressionValue(propertyInitializers2, "getPropertyInitializers(...)");
                if (!propertyInitializers2.isEmpty()) {
                    UtilsKt.addAccessorsToPrototype(classDeclarationContext, this.descriptor, propertyDescriptorForConstructorParameter, jsObjectLiteral);
                }
            }
        }
    }

    private final void addObjectCache(List<JsStatement> statements) {
        JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(StaticContext.getSuggestedName(this.descriptor) + Namer.OBJECT_INSTANCE_VAR_SUFFIX);
        Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
        this.cachedInstanceName = jsNameDeclareTemporaryName;
        List<JsStatement> list = statements;
        if (jsNameDeclareTemporaryName == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cachedInstanceName");
            jsNameDeclareTemporaryName = null;
        }
        list.add(((JsBinaryOperation) withDefaultLocation(JsAstUtils.assignment(jsNameDeclareTemporaryName.makeRef(), new JsThisRef()))).makeStmt());
    }

    private final void addObjectMethods() {
        TranslationContext translationContextContext = context();
        JsName jsName = this.cachedInstanceName;
        JsName jsName2 = null;
        if (jsName == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cachedInstanceName");
            jsName = null;
        }
        translationContextContext.addDeclarationStatement(JsAstUtils.newVar(jsName, new JsNullLiteral()));
        JsNode jsNodeWithDefaultLocation = withDefaultLocation(context().createRootScopedFunction("Instance function: " + this.descriptor));
        Intrinsics.checkNotNullExpressionValue(jsNodeWithDefaultLocation, "withDefaultLocation(...)");
        JsFunction jsFunction = (JsFunction) jsNodeWithDefaultLocation;
        jsFunction.setName(context().getNameForObjectInstance(this.descriptor));
        if (this.enumInitializerName != null) {
            List<JsStatement> statements = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(((JsInvocation) withDefaultLocation(new JsInvocation(JsAstUtils.pureFqn(this.enumInitializerName, (JsExpression) null), new JsExpression[0]))).makeStmt());
        }
        if (this.descriptor.getClassKind() != ClassKind.ENUM_ENTRY) {
            JsName jsName3 = this.cachedInstanceName;
            if (jsName3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cachedInstanceName");
                jsName3 = null;
            }
            JsNode jsNodeWithDefaultLocation2 = withDefaultLocation(JsAstUtils.equality(jsName3.makeRef(), new JsNullLiteral()));
            Intrinsics.checkNotNullExpressionValue(jsNodeWithDefaultLocation2, "withDefaultLocation(...)");
            JsBlock jsBlock = new JsBlock();
            JsIf jsIf = (JsIf) withDefaultLocation(new JsIf((JsBinaryOperation) jsNodeWithDefaultLocation2, jsBlock));
            List<JsStatement> statements2 = jsFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(jsIf);
            JsExpression innerReference = context().getInnerReference(this.descriptor);
            Intrinsics.checkNotNullExpressionValue(innerReference, "getInnerReference(...)");
            List<JsStatement> statements3 = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            statements3.add(((JsNew) withDefaultLocation(new JsNew(innerReference))).makeStmt());
        }
        Collection statements4 = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
        Collection collection = statements4;
        JsName jsName4 = this.cachedInstanceName;
        if (jsName4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cachedInstanceName");
        } else {
            jsName2 = jsName4;
        }
        collection.add(withDefaultLocation(new JsReturn((JsExpression) withDefaultLocation(jsName2.makeRef()))));
        context().addDeclarationStatement(jsFunction.makeStmt());
    }

    private final void generateEnumStandardMethods(List<? extends ClassDescriptor> entries) {
        if (this.classDeclaration instanceof PsiElement) {
            TranslationContext translationContextContext = context();
            Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
            new EnumTranslator(translationContextContext, this.descriptor, entries, (PsiElement) this.classDeclaration).generateStandardMethods();
        }
    }

    private final void mayBeAddThrowableProperties(TranslationContext context) {
        if (JsDescriptorUtils.isImmediateSubtypeOfError(this.descriptor)) {
            List listListOf = CollectionsKt.listOf((Object[]) new String[]{CrashHianalyticsData.MESSAGE, "cause"});
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
            Iterator it = listListOf.iterator();
            while (it.hasNext()) {
                arrayList.add(Name.identifier((String) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                arrayList3.add(DescriptorUtils.getPropertyByName(this.descriptor.getUnsubstitutedMemberScope(), (Name) it2.next()));
            }
            ArrayList<PropertyDescriptor> arrayList4 = new ArrayList();
            for (Object obj : arrayList3) {
                if (!((PropertyDescriptor) obj).getKind().isReal()) {
                    arrayList4.add(obj);
                }
            }
            for (PropertyDescriptor propertyDescriptor : arrayList4) {
                Intrinsics.checkNotNull(propertyDescriptor);
                DefaultPropertyTranslator defaultPropertyTranslator = new DefaultPropertyTranslator(propertyDescriptor, context, new JsNullLiteral());
                JsObjectLiteral jsObjectLiteral = new JsObjectLiteral(true);
                PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                Intrinsics.checkNotNull(getter);
                JsFunction functionObject = context.getFunctionObject(getter);
                Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
                PropertyGetterDescriptor getter2 = propertyDescriptor.getGetter();
                Intrinsics.checkNotNull(getter2);
                defaultPropertyTranslator.generateDefaultGetterFunction(getter2, functionObject);
                List<JsPropertyInitializer> propertyInitializers = jsObjectLiteral.getPropertyInitializers();
                Intrinsics.checkNotNullExpressionValue(propertyInitializers, "getPropertyInitializers(...)");
                propertyInitializers.add(new JsPropertyInitializer(new JsStringLiteral("get"), functionObject));
                UtilsKt.addAccessorsToPrototype(context, this.descriptor, propertyDescriptor, jsObjectLiteral);
            }
        }
    }

    private final void maybeConvertInterfaceToSamAdapter(TranslationContext context, JsFunction constructorFunction) {
        if (this.descriptor.isFun()) {
            JsName jsNameDeclareFreshName = context.scope().declareFreshName("f");
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareFreshName, "declareFreshName(...)");
            List<JsParameter> parameters = constructorFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            parameters.add(new JsParameter(jsNameDeclareFreshName));
            List<JsStatement> statements = constructorFunction.getBody().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(new JsBinaryOperation(JsBinaryOperator.ASG, JsAstUtils.pureFqn(Namer.SAM_FIELD_NAME, new JsThisRef()), new JsNameRef(jsNameDeclareFreshName)).makeStmt());
            MemberScope unsubstitutedMemberScope = this.descriptor.getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            Object obj = null;
            Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, DescriptorKindFilter.FUNCTIONS, null, 2, null);
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : contributedDescriptors$default) {
                if (obj2 instanceof FunctionDescriptor) {
                    arrayList.add(obj2);
                }
            }
            boolean z = false;
            for (Object obj3 : arrayList) {
                if (((FunctionDescriptor) obj3).getModality() == Modality.ABSTRACT) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z = true;
                    obj = obj3;
                }
            }
            if (z) {
                FunctionDescriptor functionDescriptor = (FunctionDescriptor) obj;
                JsFunction functionObject = context.getFunctionObject(functionDescriptor);
                Intrinsics.checkNotNullExpressionValue(functionObject, "getFunctionObject(...)");
                TranslationContext translationContextNewDeclaration = context.newDeclaration(functionDescriptor);
                Intrinsics.checkNotNullExpressionValue(translationContextNewDeclaration, "newDeclaration(...)");
                List<JsParameter> parameters2 = functionObject.getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                TranslationContext translationContextTranslateAndAliasParameters = FunctionTranslatorKt.translateAndAliasParameters(translationContextNewDeclaration, functionDescriptor, parameters2);
                if (functionDescriptor.isSuspend()) {
                    UtilsKt.fillCoroutineMetadata(functionObject, translationContextTranslateAndAliasParameters, functionDescriptor, false);
                }
                List listListOfNotNull = CollectionsKt.listOfNotNull(functionDescriptor.getExtensionReceiverParameter());
                List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                List listPlus = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) valueParameters), (Iterable) CollectionsKt.listOfNotNull(translationContextTranslateAndAliasParameters.getContinuationParameterDescriptor()));
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
                Iterator it = listPlus.iterator();
                while (it.hasNext()) {
                    arrayList2.add(TranslationUtils.coerce(translationContextTranslateAndAliasParameters, ReferenceTranslator.translateAsValueReference((ParameterDescriptor) it.next(), translationContextTranslateAndAliasParameters), context.getCurrentModule().getBuiltIns().getAnyType()));
                }
                functionObject.setBody(new JsBlock(new JsReturn(new JsInvocation(JsAstUtils.pureFqn(Namer.SAM_FIELD_NAME, new JsThisRef()), arrayList2))));
                context.addDeclarationStatement(UtilsKt.addFunctionToPrototype(context, this.descriptor, functionDescriptor, functionObject));
                return;
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <T extends JsNode> T withDefaultLocation(T t) {
        t.setSource(this.classDeclaration);
        return t;
    }

    /* JADX INFO: compiled from: ClassTranslator.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/ClassTranslator$Companion;", "", "()V", "translate", "", "classDeclaration", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "enumInitializerName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "ordinal", "", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void translate(KtPureClassOrObject classDeclaration, TranslationContext context) {
            Intrinsics.checkNotNullParameter(classDeclaration, "classDeclaration");
            Intrinsics.checkNotNullParameter(context, "context");
            new ClassTranslator(classDeclaration, context, null, null, null).translate();
        }

        @JvmStatic
        public final void translate(KtClassOrObject classDeclaration, TranslationContext context, JsName enumInitializerName) {
            Intrinsics.checkNotNullParameter(classDeclaration, "classDeclaration");
            Intrinsics.checkNotNullParameter(context, "context");
            new ClassTranslator(classDeclaration, context, enumInitializerName, null, null).translate();
        }

        @JvmStatic
        public final void translate(KtEnumEntry classDeclaration, TranslationContext context, JsName enumInitializerName, int ordinal) {
            Intrinsics.checkNotNullParameter(classDeclaration, "classDeclaration");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(enumInitializerName, "enumInitializerName");
            new ClassTranslator(classDeclaration, context, enumInitializerName, Integer.valueOf(ordinal), null).translate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: ClassTranslator.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/declaration/ClassTranslator$ConstructorInfo;", "", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "superCallGenerator", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;Lkotlin/jvm/functions/Function0;)V", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "getFunction", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getSuperCallGenerator", "()Lkotlin/jvm/functions/Function0;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class ConstructorInfo {
        private final TranslationContext context;
        private final MemberDescriptor descriptor;
        private final JsFunction function;
        private final Function0<Unit> superCallGenerator;

        public ConstructorInfo(JsFunction function, TranslationContext context, MemberDescriptor descriptor, Function0<Unit> superCallGenerator) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(superCallGenerator, "superCallGenerator");
            this.function = function;
            this.context = context;
            this.descriptor = descriptor;
            this.superCallGenerator = superCallGenerator;
        }

        public final JsFunction getFunction() {
            return this.function;
        }

        public final TranslationContext getContext() {
            return this.context;
        }

        public final MemberDescriptor getDescriptor() {
            return this.descriptor;
        }

        public /* synthetic */ ConstructorInfo(JsFunction jsFunction, TranslationContext translationContext, MemberDescriptor memberDescriptor, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(jsFunction, translationContext, memberDescriptor, (i & 8) != 0 ? new Function0<Unit>() { // from class: org.jetbrains.kotlin.js.translate.declaration.ClassTranslator.ConstructorInfo.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }
            } : anonymousClass1);
        }

        public final Function0<Unit> getSuperCallGenerator() {
            return this.superCallGenerator;
        }
    }
}
