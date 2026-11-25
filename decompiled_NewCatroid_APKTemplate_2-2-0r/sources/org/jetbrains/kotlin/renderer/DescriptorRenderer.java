package org.jetbrains.kotlin.renderer;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 82\u00020\u0001:\u000289B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001c\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH&J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H&J\u000e\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0004H&J\u0018\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H&J\u0010\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020#H&J\u0016\u0010$\u001a\u00020\u00042\f\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&H&J\u0010\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020*H&J\u0010\u0010+\u001a\u00020\u00042\u0006\u0010,\u001a\u00020'H&J\u001e\u0010-\u001a\u00020\u00042\f\u0010.\u001a\b\u0012\u0004\u0012\u0002000/2\u0006\u00101\u001a\u00020 H&J\u001f\u00102\u001a\u00020\u00002\u0017\u00103\u001a\u0013\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u00020604¢\u0006\u0002\b7¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "", "()V", "render", "", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "renderAnnotation", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "target", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "renderClassifierName", "klass", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "renderFlexibleType", "lowerRendered", "upperRendered", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "renderFqName", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "renderFunctionParameters", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "renderMessage", CrashHianalyticsData.MESSAGE, "renderName", "name", "Lorg/jetbrains/kotlin/name/Name;", "rootRenderedElement", "", "renderType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "renderTypeArguments", "typeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "renderTypeConstructor", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "renderTypeProjection", "typeProjection", "renderValueParameters", "parameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "synthesizedParameterNames", "withOptions", "changeOptions", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "", "Lkotlin/ExtensionFunctionType;", "Companion", "ValueParametersHandler", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorRenderer {
    public static final DescriptorRenderer COMPACT;
    public static final DescriptorRenderer COMPACT_WITHOUT_SUPERTYPES;
    public static final DescriptorRenderer COMPACT_WITH_MODIFIERS;
    public static final DescriptorRenderer COMPACT_WITH_SHORT_TYPES;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    public static final DescriptorRenderer DEBUG_TEXT;
    public static final DescriptorRenderer FQ_NAMES_IN_TYPES;
    public static final DescriptorRenderer FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS;
    public static final DescriptorRenderer HTML;
    public static final DescriptorRenderer ONLY_NAMES_WITH_SHORT_TYPES;
    public static final DescriptorRenderer SHORT_NAMES_IN_TYPES;

    public abstract String render(DeclarationDescriptor declarationDescriptor);

    public abstract String renderAnnotation(AnnotationDescriptor annotation, AnnotationUseSiteTarget target);

    public abstract String renderClassifierName(ClassifierDescriptor klass);

    public abstract String renderFlexibleType(String lowerRendered, String upperRendered, KotlinBuiltIns builtIns);

    public abstract String renderFqName(FqNameUnsafe fqName);

    public abstract String renderMessage(String message);

    public abstract String renderName(Name name2, boolean rootRenderedElement);

    public abstract String renderType(KotlinType type);

    public abstract String renderTypeArguments(List<? extends TypeProjection> typeArguments);

    public abstract String renderTypeConstructor(TypeConstructor typeConstructor);

    public abstract String renderTypeProjection(TypeProjection typeProjection);

    public abstract String renderValueParameters(Collection<? extends ValueParameterDescriptor> parameters, boolean synthesizedParameterNames);

    public final DescriptorRenderer withOptions(Function1<? super DescriptorRendererOptions, Unit> changeOptions) {
        Intrinsics.checkNotNullParameter(changeOptions, "changeOptions");
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
        DescriptorRendererOptionsImpl descriptorRendererOptionsImplCopy = ((DescriptorRendererImpl) this).getOptions().copy();
        changeOptions.invoke(descriptorRendererOptionsImplCopy);
        descriptorRendererOptionsImplCopy.lock();
        return new DescriptorRendererImpl(descriptorRendererOptionsImplCopy);
    }

    public static /* synthetic */ String renderAnnotation$default(DescriptorRenderer descriptorRenderer, AnnotationDescriptor annotationDescriptor, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderAnnotation");
        }
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        return descriptorRenderer.renderAnnotation(annotationDescriptor, annotationUseSiteTarget);
    }

    public final String renderFunctionParameters(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return renderValueParameters(valueParameters, functionDescriptor.hasSynthesizedParameterNames());
    }

    /* JADX INFO: compiled from: DescriptorRenderer.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\u000fJ,\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u00060\nj\u0002`\u000bH&J\u001c\u0010\f\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u00060\nj\u0002`\u000bH&J,\u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u00060\nj\u0002`\u000bH&J\u001c\u0010\u000e\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u00072\n\u0010\t\u001a\u00060\nj\u0002`\u000bH&¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "", "appendAfterValueParameter", "", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "parameterIndex", "", "parameterCount", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "appendAfterValueParameters", "appendBeforeValueParameter", "appendBeforeValueParameters", "DEFAULT", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ValueParametersHandler {
        void appendAfterValueParameter(ValueParameterDescriptor parameter, int parameterIndex, int parameterCount, StringBuilder builder);

        void appendAfterValueParameters(int parameterCount, StringBuilder builder);

        void appendBeforeValueParameter(ValueParameterDescriptor parameter, int parameterIndex, int parameterCount, StringBuilder builder);

        void appendBeforeValueParameters(int parameterCount, StringBuilder builder);

        /* JADX INFO: compiled from: DescriptorRenderer.kt */
        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0016J\u001c\u0010\r\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0016J,\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0016J\u001c\u0010\u000f\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b2\n\u0010\n\u001a\u00060\u000bj\u0002`\fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler$DEFAULT;", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "()V", "appendAfterValueParameter", "", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "parameterIndex", "", "parameterCount", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "appendAfterValueParameters", "appendBeforeValueParameter", "appendBeforeValueParameters", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DEFAULT implements ValueParametersHandler {
            public static final DEFAULT INSTANCE = new DEFAULT();

            @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer.ValueParametersHandler
            public void appendBeforeValueParameter(ValueParameterDescriptor parameter, int parameterIndex, int parameterCount, StringBuilder builder) {
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                Intrinsics.checkNotNullParameter(builder, "builder");
            }

            private DEFAULT() {
            }

            @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer.ValueParametersHandler
            public void appendBeforeValueParameters(int parameterCount, StringBuilder builder) {
                Intrinsics.checkNotNullParameter(builder, "builder");
                builder.append("(");
            }

            @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer.ValueParametersHandler
            public void appendAfterValueParameters(int parameterCount, StringBuilder builder) {
                Intrinsics.checkNotNullParameter(builder, "builder");
                builder.append(Constant.AFTER_QUTO);
            }

            @Override // org.jetbrains.kotlin.renderer.DescriptorRenderer.ValueParametersHandler
            public void appendAfterValueParameter(ValueParameterDescriptor parameter, int parameterIndex, int parameterCount, StringBuilder builder) {
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                Intrinsics.checkNotNullParameter(builder, "builder");
                if (parameterIndex != parameterCount - 1) {
                    builder.append(", ");
                }
            }
        }
    }

    /* JADX INFO: compiled from: DescriptorRenderer.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u001f\u0010\u0012\u001a\u00020\u00042\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014¢\u0006\u0002\b\u0017R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$Companion;", "", "()V", "COMPACT", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "COMPACT_WITHOUT_SUPERTYPES", "COMPACT_WITH_MODIFIERS", "COMPACT_WITH_SHORT_TYPES", "DEBUG_TEXT", "FQ_NAMES_IN_TYPES", "FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS", "HTML", "ONLY_NAMES_WITH_SHORT_TYPES", "SHORT_NAMES_IN_TYPES", "getClassifierKindPrefix", "", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "withOptions", "changeOptions", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "", "Lkotlin/ExtensionFunctionType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: DescriptorRenderer.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ClassKind.values().length];
                try {
                    iArr[ClassKind.CLASS.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ClassKind.INTERFACE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ClassKind.ENUM_CLASS.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ClassKind.OBJECT.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[ClassKind.ENUM_ENTRY.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DescriptorRenderer withOptions(Function1<? super DescriptorRendererOptions, Unit> changeOptions) {
            Intrinsics.checkNotNullParameter(changeOptions, "changeOptions");
            DescriptorRendererOptionsImpl descriptorRendererOptionsImpl = new DescriptorRendererOptionsImpl();
            changeOptions.invoke(descriptorRendererOptionsImpl);
            descriptorRendererOptionsImpl.lock();
            return new DescriptorRendererImpl(descriptorRendererOptionsImpl);
        }

        public final String getClassifierKindPrefix(ClassifierDescriptorWithTypeParameters classifier) {
            Intrinsics.checkNotNullParameter(classifier, "classifier");
            if (classifier instanceof TypeAliasDescriptor) {
                return "typealias";
            }
            if (classifier instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) classifier;
                if (classDescriptor.isCompanionObject()) {
                    return "companion object";
                }
                switch (WhenMappings.$EnumSwitchMapping$0[classDescriptor.getKind().ordinal()]) {
                    case 1:
                        return "class";
                    case 2:
                        return PsiKeyword.INTERFACE;
                    case 3:
                        return "enum class";
                    case 4:
                        return "object";
                    case 5:
                        return "annotation class";
                    case 6:
                        return "enum entry";
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            throw new AssertionError("Unexpected classifier: " + classifier);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        COMPACT_WITH_MODIFIERS = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$COMPACT_WITH_MODIFIERS$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setWithDefinedIn(false);
            }
        });
        COMPACT = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$COMPACT$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setWithDefinedIn(false);
                withOptions.setModifiers(SetsKt.emptySet());
            }
        });
        COMPACT_WITHOUT_SUPERTYPES = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$COMPACT_WITHOUT_SUPERTYPES$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setWithDefinedIn(false);
                withOptions.setModifiers(SetsKt.emptySet());
                withOptions.setWithoutSuperTypes(true);
            }
        });
        COMPACT_WITH_SHORT_TYPES = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$COMPACT_WITH_SHORT_TYPES$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setModifiers(SetsKt.emptySet());
                withOptions.setClassifierNamePolicy(ClassifierNamePolicy.SHORT.INSTANCE);
                withOptions.setParameterNameRenderingPolicy(ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED);
            }
        });
        ONLY_NAMES_WITH_SHORT_TYPES = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$ONLY_NAMES_WITH_SHORT_TYPES$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setWithDefinedIn(false);
                withOptions.setModifiers(SetsKt.emptySet());
                withOptions.setClassifierNamePolicy(ClassifierNamePolicy.SHORT.INSTANCE);
                withOptions.setWithoutTypeParameters(true);
                withOptions.setParameterNameRenderingPolicy(ParameterNameRenderingPolicy.NONE);
                withOptions.setReceiverAfterName(true);
                withOptions.setRenderCompanionObjectName(true);
                withOptions.setWithoutSuperTypes(true);
                withOptions.setStartFromName(true);
            }
        });
        FQ_NAMES_IN_TYPES = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$FQ_NAMES_IN_TYPES$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setModifiers(DescriptorRendererModifier.ALL_EXCEPT_ANNOTATIONS);
            }
        });
        FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setModifiers(DescriptorRendererModifier.ALL);
            }
        });
        SHORT_NAMES_IN_TYPES = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$SHORT_NAMES_IN_TYPES$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setClassifierNamePolicy(ClassifierNamePolicy.SHORT.INSTANCE);
                withOptions.setParameterNameRenderingPolicy(ParameterNameRenderingPolicy.ONLY_NON_SYNTHESIZED);
            }
        });
        DEBUG_TEXT = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$DEBUG_TEXT$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setDebugMode(true);
                withOptions.setClassifierNamePolicy(ClassifierNamePolicy.FULLY_QUALIFIED.INSTANCE);
                withOptions.setModifiers(DescriptorRendererModifier.ALL);
            }
        });
        HTML = companion.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRenderer$Companion$HTML$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setTextFormat(RenderingFormat.HTML);
                withOptions.setModifiers(DescriptorRendererModifier.ALL);
            }
        });
    }
}
