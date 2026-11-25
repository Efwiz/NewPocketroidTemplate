package org.jetbrains.kotlin.js.translate.reference;

import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.ranges.RangesKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.functions.FunctionInvokeDescriptor;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TemporaryConstVariable;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CallArgumentTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 )2\u00020\u0001:\u0002()B%\b\u0002\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ4\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0016\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u0010H\u0002J \u0010\u001a\u001a\u00020\u00052\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012H\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J(\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00050\u001f2\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0002J6\u0010 \u001a\u0004\u0018\u00010\u00052\u0006\u0010!\u001a\u00020\"2\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00050\u001f2\u0006\u0010$\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\f\u0010%\u001a\u00020\n*\u00020\u0013H\u0002J\u0014\u0010&\u001a\u00020\u0005*\u00020\u00052\u0006\u0010'\u001a\u00020\u0016H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "isNativeFunctionCall", "", "typeToUTypeArray", "", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "prepareConcatArguments", "", "arguments", "", "Lorg/jetbrains/kotlin/psi/ValueArgument;", SchemaSymbols.ATTVAL_LIST, "varargElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "removeLastUndefinedArguments", "", "result", "toArray", PathTree.NAME_elements, "translate", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "translateUnresolvedArguments", "", "translateVarargArgument", "resolvedArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "translatedArgs", "shouldWrapVarargInArray", "hasSpreadElementOrNamedArgument", "wrapInUArray", "elementType", "ArgumentsInfo", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallArgumentTranslator extends AbstractTranslator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final TranslationContext context;
    private final boolean isNativeFunctionCall;
    private final JsExpression receiver;
    private final ResolvedCall<?> resolvedCall;
    private final Map<PrimitiveType, ClassDescriptor> typeToUTypeArray;

    public /* synthetic */ CallArgumentTranslator(ResolvedCall resolvedCall, JsExpression jsExpression, TranslationContext translationContext, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolvedCall, jsExpression, translationContext);
    }

    @JvmStatic
    public static final ArgumentsInfo translate(ResolvedCall<?> resolvedCall, JsExpression jsExpression, TranslationContext translationContext) {
        return INSTANCE.translate(resolvedCall, jsExpression, translationContext);
    }

    @JvmStatic
    public static final ArgumentsInfo translate(ResolvedCall<?> resolvedCall, JsExpression jsExpression, TranslationContext translationContext, JsBlock jsBlock) {
        return INSTANCE.translate(resolvedCall, jsExpression, translationContext, jsBlock);
    }

    private CallArgumentTranslator(ResolvedCall<?> resolvedCall, JsExpression jsExpression, TranslationContext translationContext) {
        super(translationContext);
        this.resolvedCall = resolvedCall;
        this.receiver = jsExpression;
        this.context = translationContext;
        this.isNativeFunctionCall = AnnotationsUtils.isNativeObject(resolvedCall.get$suspendPropertyDescriptor());
        this.typeToUTypeArray = new LinkedHashMap();
    }

    /* JADX INFO: compiled from: CallArgumentTranslator.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\nJ\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\bHÆ\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J?\u0010\u0018\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u00062\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "", "valueArguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "hasSpreadOperator", "", "cachedReceiver", "Lorg/jetbrains/kotlin/js/translate/context/TemporaryConstVariable;", "reifiedArguments", "(Ljava/util/List;ZLorg/jetbrains/kotlin/js/translate/context/TemporaryConstVariable;Ljava/util/List;)V", "getCachedReceiver", "()Lorg/jetbrains/kotlin/js/translate/context/TemporaryConstVariable;", "getHasSpreadOperator", "()Z", "getReifiedArguments", "()Ljava/util/List;", "translateArguments", "getTranslateArguments", "getValueArguments", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ArgumentsInfo {
        private final TemporaryConstVariable cachedReceiver;
        private final boolean hasSpreadOperator;
        private final List<JsExpression> reifiedArguments;
        private final List<JsExpression> valueArguments;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ArgumentsInfo copy$default(ArgumentsInfo argumentsInfo, List list, boolean z, TemporaryConstVariable temporaryConstVariable, List list2, int i, Object obj) {
            if ((i & 1) != 0) {
                list = argumentsInfo.valueArguments;
            }
            if ((i & 2) != 0) {
                z = argumentsInfo.hasSpreadOperator;
            }
            if ((i & 4) != 0) {
                temporaryConstVariable = argumentsInfo.cachedReceiver;
            }
            if ((i & 8) != 0) {
                list2 = argumentsInfo.reifiedArguments;
            }
            return argumentsInfo.copy(list, z, temporaryConstVariable, list2);
        }

        public final List<JsExpression> component1() {
            return this.valueArguments;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasSpreadOperator() {
            return this.hasSpreadOperator;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final TemporaryConstVariable getCachedReceiver() {
            return this.cachedReceiver;
        }

        public final List<JsExpression> component4() {
            return this.reifiedArguments;
        }

        public final ArgumentsInfo copy(List<? extends JsExpression> valueArguments, boolean hasSpreadOperator, TemporaryConstVariable cachedReceiver, List<? extends JsExpression> reifiedArguments) {
            Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
            Intrinsics.checkNotNullParameter(reifiedArguments, "reifiedArguments");
            return new ArgumentsInfo(valueArguments, hasSpreadOperator, cachedReceiver, reifiedArguments);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ArgumentsInfo)) {
                return false;
            }
            ArgumentsInfo argumentsInfo = (ArgumentsInfo) other;
            return Intrinsics.areEqual(this.valueArguments, argumentsInfo.valueArguments) && this.hasSpreadOperator == argumentsInfo.hasSpreadOperator && Intrinsics.areEqual(this.cachedReceiver, argumentsInfo.cachedReceiver) && Intrinsics.areEqual(this.reifiedArguments, argumentsInfo.reifiedArguments);
        }

        public int hashCode() {
            int iHashCode = ((this.valueArguments.hashCode() * 31) + Boolean.hashCode(this.hasSpreadOperator)) * 31;
            TemporaryConstVariable temporaryConstVariable = this.cachedReceiver;
            return ((iHashCode + (temporaryConstVariable == null ? 0 : temporaryConstVariable.hashCode())) * 31) + this.reifiedArguments.hashCode();
        }

        public String toString() {
            return "ArgumentsInfo(valueArguments=" + this.valueArguments + ", hasSpreadOperator=" + this.hasSpreadOperator + ", cachedReceiver=" + this.cachedReceiver + ", reifiedArguments=" + this.reifiedArguments + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ArgumentsInfo(List<? extends JsExpression> valueArguments, boolean z, TemporaryConstVariable temporaryConstVariable, List<? extends JsExpression> reifiedArguments) {
            Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
            Intrinsics.checkNotNullParameter(reifiedArguments, "reifiedArguments");
            this.valueArguments = valueArguments;
            this.hasSpreadOperator = z;
            this.cachedReceiver = temporaryConstVariable;
            this.reifiedArguments = reifiedArguments;
        }

        public final List<JsExpression> getValueArguments() {
            return this.valueArguments;
        }

        public final boolean getHasSpreadOperator() {
            return this.hasSpreadOperator;
        }

        public final TemporaryConstVariable getCachedReceiver() {
            return this.cachedReceiver;
        }

        public /* synthetic */ ArgumentsInfo(List list, boolean z, TemporaryConstVariable temporaryConstVariable, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, z, temporaryConstVariable, (i & 8) != 0 ? CollectionsKt.emptyList() : list2);
        }

        public final List<JsExpression> getReifiedArguments() {
            return this.reifiedArguments;
        }

        public final List<JsExpression> getTranslateArguments() {
            return CollectionsKt.plus((Collection) this.reifiedArguments, (Iterable) this.valueArguments);
        }
    }

    private final void removeLastUndefinedArguments(List<JsExpression> result) {
        int lastIndex = CollectionsKt.getLastIndex(result);
        while (lastIndex >= 0 && JsAstUtils.isUndefinedExpression(result.get(lastIndex))) {
            lastIndex--;
        }
        result.subList(lastIndex + 1, result.size()).clear();
    }

    private final boolean hasSpreadElementOrNamedArgument(ValueArgument valueArgument) {
        return valueArgument.getSpreadElement() != null || (this.context.getConfig().getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowAssigningArrayElementsToVarargsInNamedFormForFunctions) && valueArgument.isNamed());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ArgumentsInfo translate() {
        List<ValueParameterDescriptor> valueParameters = this.resolvedCall.getResultingDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<JsExpression> arrayList = new ArrayList<>(valueParameters.size());
        List<ResolvedValueArgument> valueArgumentsByIndex = this.resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null) {
            throw new IllegalStateException("Failed to arrange value arguments by index: " + this.resolvedCall.getResultingDescriptor());
        }
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        Map<ValueArgument, JsExpression> mapTranslateUnresolvedArguments = translateUnresolvedArguments(translationContextContext, this.resolvedCall);
        Iterator<ValueParameterDescriptor> it = valueParameters.iterator();
        TemporaryConstVariable orDeclareTemporaryConstVariable = null;
        boolean z = false;
        List<JsExpression> list = null;
        List<JsExpression> listPrepareConcatArguments = null;
        KotlinType kotlinType = null;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ValueParameterDescriptor next = it.next();
            ResolvedValueArgument resolvedValueArgument = valueArgumentsByIndex.get(next.getIndex());
            if (resolvedValueArgument instanceof VarargValueArgument) {
                List<ValueArgument> arguments = resolvedValueArgument.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                if (!z) {
                    List<ValueArgument> list2 = arguments;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        Iterator<T> it2 = list2.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = false;
                                break;
                            }
                            ValueArgument valueArgument = (ValueArgument) it2.next();
                            Intrinsics.checkNotNull(valueArgument);
                            if (hasSpreadElementOrNamedArgument(valueArgument)) {
                                z = true;
                                break;
                            }
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                KotlinType varargElementType = next.getOriginal().getVarargElementType();
                Intrinsics.checkNotNull(varargElementType);
                if (z) {
                    if (this.isNativeFunctionCall) {
                        ArrayList arrayList2 = new ArrayList();
                        listPrepareConcatArguments = prepareConcatArguments(arguments, INSTANCE.translateResolvedArgument(resolvedValueArgument, mapTranslateUnresolvedArguments), null);
                        kotlinType = varargElementType;
                        list = arrayList;
                        arrayList = arrayList2;
                    } else {
                        JsExpression jsExpressionTranslateVarargArgument = translateVarargArgument(resolvedValueArgument, mapTranslateUnresolvedArguments, ((VarargValueArgument) resolvedValueArgument).getArguments().size() > 1, varargElementType);
                        if (jsExpressionTranslateVarargArgument != null) {
                            arrayList.add(jsExpressionTranslateVarargArgument);
                        }
                    }
                } else if (!this.isNativeFunctionCall) {
                    JsExpression jsExpressionTranslateVarargArgument2 = translateVarargArgument(resolvedValueArgument, mapTranslateUnresolvedArguments, true, varargElementType);
                    if (jsExpressionTranslateVarargArgument2 != null) {
                        arrayList.add(jsExpressionTranslateVarargArgument2);
                    }
                } else {
                    arrayList.addAll(INSTANCE.translateResolvedArgument(resolvedValueArgument, mapTranslateUnresolvedArguments));
                }
                kotlinType = varargElementType;
            } else {
                Companion companion = INSTANCE;
                Intrinsics.checkNotNull(resolvedValueArgument);
                arrayList.addAll(companion.translateResolvedArgument(resolvedValueArgument, mapTranslateUnresolvedArguments));
            }
        }
        if (this.isNativeFunctionCall && z) {
            if (!arrayList.isEmpty()) {
                Intrinsics.checkNotNull(listPrepareConcatArguments);
                listPrepareConcatArguments.add(toArray(null, arrayList));
            }
            Intrinsics.checkNotNull(list);
            if (!list.isEmpty()) {
                Intrinsics.checkNotNull(listPrepareConcatArguments);
                listPrepareConcatArguments.add(0, toArray(null, list));
            }
            Companion companion2 = INSTANCE;
            Intrinsics.checkNotNull(listPrepareConcatArguments);
            arrayList = CollectionsKt.mutableListOf(companion2.concatArgumentsIfNeeded(listPrepareConcatArguments, kotlinType, true));
            if (this.receiver != null) {
                orDeclareTemporaryConstVariable = context().getOrDeclareTemporaryConstVariable(this.receiver);
                JsNameRef jsNameRefReference = orDeclareTemporaryConstVariable.reference();
                Intrinsics.checkNotNullExpressionValue(jsNameRefReference, "reference(...)");
                arrayList.add(0, jsNameRefReference);
            } else if (DescriptorUtils.isObject(this.resolvedCall.getResultingDescriptor().getPackageFragment())) {
                orDeclareTemporaryConstVariable = context().getOrDeclareTemporaryConstVariable(ReferenceTranslator.translateAsValueReference(this.resolvedCall.getResultingDescriptor().getPackageFragment(), context()));
                JsNameRef jsNameRefReference2 = orDeclareTemporaryConstVariable.reference();
                Intrinsics.checkNotNullExpressionValue(jsNameRefReference2, "reference(...)");
                arrayList.add(0, jsNameRefReference2);
            } else {
                arrayList.add(0, new JsNullLiteral());
            }
        }
        List<JsExpression> list3 = arrayList;
        TemporaryConstVariable temporaryConstVariable = orDeclareTemporaryConstVariable;
        CallableDescriptor resultingDescriptor = this.resolvedCall.getResultingDescriptor();
        if ((resultingDescriptor instanceof FunctionDescriptor) && ((FunctionDescriptor) resultingDescriptor).isSuspend()) {
            JsExpression jsExpressionTranslateContinuationArgument = TranslationUtils.translateContinuationArgument(context());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateContinuationArgument, "translateContinuationArgument(...)");
            list3.add(jsExpressionTranslateContinuationArgument);
        }
        removeLastUndefinedArguments(list3);
        return new ArgumentsInfo(list3, z, temporaryConstVariable, null, 8, null);
    }

    /* JADX WARN: Code duplicated, block: B:76:0x02b0 A[LOOP:7: B:74:0x02aa->B:76:0x02b0, LOOP_END] */
    private final Map<ValueArgument, JsExpression> translateUnresolvedArguments(TranslationContext context, ResolvedCall<?> resolvedCall) {
        ArrayList arrayList;
        SimpleType varargElementType;
        Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments = resolvedCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : valueArguments.entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            List<ValueArgument> arguments = entry.getValue().getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            List<ValueArgument> list = arguments;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList3.add(TuplesKt.to(key, (ValueArgument) it.next()));
            }
            CollectionsKt.addAll(arrayList2, arrayList3);
        }
        ArrayList<Pair> arrayList4 = arrayList2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList4, 10)), 16));
        for (Pair pair : arrayList4) {
            Pair pair2 = TuplesKt.to((ValueArgument) pair.component2(), (ValueParameterDescriptor) pair.component1());
            linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
        }
        List<? extends ValueArgument> valueArguments2 = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments2, "getValueArguments(...)");
        List<? extends ValueArgument> list2 = valueArguments2;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            Pair pair3 = TuplesKt.to((ValueArgument) it2.next(), context.innerBlock());
            linkedHashMap2.put(pair3.getFirst(), pair3.getSecond());
        }
        List<? extends ValueArgument> valueArguments3 = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments3, "getValueArguments(...)");
        List<? extends ValueArgument> list3 = valueArguments3;
        Map linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list3, 10)), 16));
        for (ValueArgument valueArgument : list3) {
            Object obj = linkedHashMap2.get(valueArgument);
            Intrinsics.checkNotNull(obj);
            TranslationContext translationContext = (TranslationContext) obj;
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            Object obj2 = linkedHashMap.get(valueArgument);
            Intrinsics.checkNotNull(obj2);
            ValueParameterDescriptor original = ((ValueParameterDescriptor) obj2).getOriginal();
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            Intrinsics.checkNotNull(resultingDescriptor);
            if (FunctionTypesKt.getFunctionTypeKind(resultingDescriptor) != null || (resultingDescriptor instanceof FunctionInvokeDescriptor)) {
                SimpleType anyType = context.getCurrentModule().getBuiltIns().getAnyType();
                Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
                varargElementType = anyType;
            } else {
                varargElementType = original.getVarargElementType();
                if (varargElementType == null) {
                    varargElementType = original.getType();
                    Intrinsics.checkNotNullExpressionValue(varargElementType, "getType(...)");
                }
            }
            Intrinsics.checkNotNull(argumentExpression);
            JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(argumentExpression, translationContext);
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "translateAsExpression(...)");
            if (!ArgumentsUtilsKt.isVararg(original) || valueArgument.getSpreadElement() == null) {
                jsExpressionTranslateAsExpression = TranslationUtils.coerce(context, jsExpressionTranslateAsExpression, varargElementType);
                Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsExpression, "coerce(...)");
            }
            Pair pair4 = TuplesKt.to(valueArgument, jsExpressionTranslateAsExpression);
            linkedHashMap3.put(pair4.getFirst(), pair4.getSecond());
        }
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null) {
            valueArgumentsByIndex = CollectionsKt.emptyList();
        }
        ArrayList arrayList5 = new ArrayList();
        Iterator<T> it3 = valueArgumentsByIndex.iterator();
        while (it3.hasNext()) {
            List<ValueArgument> arguments2 = ((ResolvedValueArgument) it3.next()).getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments2, "getArguments(...)");
            CollectionsKt.addAll(arrayList5, arguments2);
        }
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(arrayList5);
        LinkedHashMap linkedHashMap4 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            Pair pair5 = TuplesKt.to((ValueArgument) indexedValue.component2(), Integer.valueOf(indexedValue.getIndex()));
            linkedHashMap4.put(pair5.getFirst(), pair5.getSecond());
        }
        List<? extends ValueArgument> valueArguments4 = resolvedCall.getCall().getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments4, "getValueArguments(...)");
        Iterable<IndexedValue> iterableWithIndex2 = CollectionsKt.withIndex(valueArguments4);
        boolean z = true;
        if (!(iterableWithIndex2 instanceof Collection) || !((Collection) iterableWithIndex2).isEmpty()) {
            for (IndexedValue indexedValue2 : iterableWithIndex2) {
                int index = indexedValue2.getIndex();
                Integer num = (Integer) linkedHashMap4.get((ValueArgument) indexedValue2.component2());
                if (num == null || num.intValue() != index) {
                    z = false;
                    break;
                }
            }
        }
        Collection collectionValues = linkedHashMap2.values();
        if (!(collectionValues instanceof Collection) || !collectionValues.isEmpty()) {
            Iterator it4 = collectionValues.iterator();
            while (true) {
                if (it4.hasNext()) {
                    if (!((TranslationContext) it4.next()).currentBlockIsEmpty()) {
                    }
                } else if (!z) {
                }
                arrayList = new ArrayList(linkedHashMap3.size());
                for (Map.Entry entry2 : linkedHashMap3.entrySet()) {
                    ValueArgument valueArgument2 = (ValueArgument) entry2.getKey();
                    JsExpression jsExpression = (JsExpression) entry2.getValue();
                    Object obj3 = linkedHashMap2.get(valueArgument2);
                    Intrinsics.checkNotNull(obj3);
                    arrayList.add(TuplesKt.to(valueArgument2, ((TranslationContext) obj3).cacheExpressionIfNeeded(jsExpression)));
                }
                linkedHashMap3 = MapsKt.toMap(arrayList);
            }
        } else if (!z) {
            arrayList = new ArrayList(linkedHashMap3.size());
            while (r2.hasNext()) {
                ValueArgument valueArgument3 = (ValueArgument) entry2.getKey();
                JsExpression jsExpression2 = (JsExpression) entry2.getValue();
                Object obj4 = linkedHashMap2.get(valueArgument3);
                Intrinsics.checkNotNull(obj4);
                arrayList.add(TuplesKt.to(valueArgument3, ((TranslationContext) obj4).cacheExpressionIfNeeded(jsExpression2)));
            }
            linkedHashMap3 = MapsKt.toMap(arrayList);
        }
        for (TranslationContext translationContext2 : linkedHashMap2.values()) {
            context.moveVarsFrom(translationContext2);
            context.addStatementsToCurrentBlockFrom(translationContext2);
        }
        return linkedHashMap3;
    }

    private final JsExpression wrapInUArray(JsExpression jsExpression, KotlinType kotlinType) {
        final PrimitiveType primitiveTypeUnsignedPrimitiveToSigned = ArrayFIF.INSTANCE.unsignedPrimitiveToSigned(kotlinType);
        if (primitiveTypeUnsignedPrimitiveToSigned == null) {
            return jsExpression;
        }
        ModuleDescriptor currentModule = this.context.getCurrentModule();
        FqName safe = new FqNameUnsafe(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME).toSafe();
        Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
        final MemberScope memberScope = currentModule.getPackage(safe).getMemberScope();
        Map<PrimitiveType, ClassDescriptor> map = this.typeToUTypeArray;
        final Function1<PrimitiveType, ClassDescriptor> function1 = new Function1<PrimitiveType, ClassDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator$wrapInUArray$1$classDescriptor$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ClassDescriptor invoke(PrimitiveType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Name nameIdentifier = Name.identifier("U" + primitiveTypeUnsignedPrimitiveToSigned.getTypeName() + "Array");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                ClassifierDescriptor contributedClassifier = memberScope.mo7465getContributedClassifier(nameIdentifier, NoLookupLocation.FROM_BACKEND);
                Intrinsics.checkNotNull(contributedClassifier, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                return (ClassDescriptor) contributedClassifier;
            }
        };
        ClassDescriptor classDescriptorComputeIfAbsent = map.computeIfAbsent(primitiveTypeUnsignedPrimitiveToSigned, new Function() { // from class: org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CallArgumentTranslator.wrapInUArray$lambda$18$lambda$17(function1, obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(classDescriptorComputeIfAbsent, "computeIfAbsent(...)");
        return new JsNew(ReferenceTranslator.translateAsTypeReference(classDescriptorComputeIfAbsent, this.context), CollectionsKt.listOf(jsExpression));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassDescriptor wrapInUArray$lambda$18$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (ClassDescriptor) tmp0.invoke(obj);
    }

    private final JsExpression translateVarargArgument(ResolvedValueArgument resolvedArgument, Map<ValueArgument, ? extends JsExpression> translatedArgs, boolean shouldWrapVarargInArray, KotlinType varargElementType) {
        JsExpression jsInvocation;
        JsInvocation jsInvocationConcatArgumentsIfNeeded;
        List<ValueArgument> arguments = resolvedArgument.getArguments();
        Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
        if (arguments.isEmpty()) {
            if (shouldWrapVarargInArray) {
                return wrapInUArray(toArray(varargElementType, new ArrayList()), varargElementType);
            }
            return null;
        }
        Companion companion = INSTANCE;
        List<? extends JsExpression> listTranslateResolvedArgument = companion.translateResolvedArgument(resolvedArgument, translatedArgs);
        if (!shouldWrapVarargInArray) {
            if (ArrayFIF.INSTANCE.unsignedPrimitiveToSigned(varargElementType) != null) {
                jsInvocation = new JsInvocation(new JsNameRef("unbox", listTranslateResolvedArgument.get(0)), new JsExpression[0]);
            } else {
                jsInvocation = listTranslateResolvedArgument.get(0);
            }
            JsInvocation jsInvocationInvokeMethod = JsAstUtils.invokeMethod(jsInvocation, "slice", new JsExpression[0]);
            Intrinsics.checkNotNull(jsInvocationInvokeMethod);
            jsInvocationConcatArgumentsIfNeeded = jsInvocationInvokeMethod;
        } else {
            jsInvocationConcatArgumentsIfNeeded = companion.concatArgumentsIfNeeded(prepareConcatArguments(arguments, listTranslateResolvedArgument, varargElementType), varargElementType, false);
        }
        return wrapInUArray(jsInvocationConcatArgumentsIfNeeded, varargElementType);
    }

    private final JsExpression toArray(KotlinType varargElementType, List<? extends JsExpression> elements) {
        JsArrayLiteral jsArrayLiteral = new JsArrayLiteral(elements);
        MetadataProperties.setSideEffects(jsArrayLiteral, SideEffectKind.PURE);
        if (varargElementType == null) {
            return jsArrayLiteral;
        }
        ArrayFIF arrayFIF = ArrayFIF.INSTANCE;
        TranslationContext translationContextContext = context();
        Intrinsics.checkNotNullExpressionValue(translationContextContext, "context(...)");
        return arrayFIF.castOrCreatePrimitiveArray(translationContextContext, varargElementType, jsArrayLiteral);
    }

    private final List<JsExpression> prepareConcatArguments(List<? extends ValueArgument> arguments, List<? extends JsExpression> list, KotlinType varargElementType) {
        arguments.isEmpty();
        arguments.size();
        list.size();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = arguments.size();
        for (int i = 0; i < size; i++) {
            ValueArgument valueArgument = arguments.get(i);
            JsInvocation jsInvocation = list.get(i);
            if (hasSpreadElementOrNamedArgument(valueArgument)) {
                if (arrayList2.size() > 0) {
                    arrayList.add(toArray(varargElementType, arrayList2));
                    arrayList2 = new ArrayList();
                }
                if (varargElementType != null && ArrayFIF.INSTANCE.unsignedPrimitiveToSigned(varargElementType) != null) {
                    jsInvocation = new JsInvocation(new JsNameRef("unbox", jsInvocation), new JsExpression[0]);
                }
                arrayList.add(jsInvocation);
            } else {
                arrayList2.add(jsInvocation);
            }
        }
        if (arrayList2.size() > 0) {
            arrayList.add(toArray(varargElementType, arrayList2));
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: CallArgumentTranslator.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0002J&\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J.\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J*\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0015\u001a\u00020\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00040\u0018H\u0002¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$Companion;", "", "()V", "concatArgumentsIfNeeded", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "concatArguments", "", "varargElementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isMixed", "", "translate", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiver", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "block", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "translateResolvedArgument", "resolvedArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "translatedArgs", "", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ArgumentsInfo translate(ResolvedCall<?> resolvedCall, JsExpression receiver, TranslationContext context) {
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Intrinsics.checkNotNullParameter(context, "context");
            JsBlock jsBlock = context.dynamicContext().jsBlock();
            Intrinsics.checkNotNullExpressionValue(jsBlock, "jsBlock(...)");
            return translate(resolvedCall, receiver, context, jsBlock);
        }

        @JvmStatic
        public final ArgumentsInfo translate(ResolvedCall<?> resolvedCall, JsExpression receiver, TranslationContext context, JsBlock block) {
            Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(block, "block");
            TranslationContext translationContextInnerBlock = context.innerBlock(block);
            Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
            ArgumentsInfo argumentsInfoTranslate = new CallArgumentTranslator(resolvedCall, receiver, translationContextInnerBlock, null).translate();
            context.moveVarsFrom(translationContextInnerBlock);
            if (!CallExpressionTranslator.shouldBeInlined(resolvedCall.get$suspendPropertyDescriptor())) {
                return argumentsInfoTranslate;
            }
            Map<TypeParameterDescriptor, KotlinType> typeArguments = resolvedCall.getTypeArguments();
            Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
            return ArgumentsInfo.copy$default(argumentsInfoTranslate, null, false, null, CallArgumentTranslatorKt.buildReifiedTypeArgs(typeArguments, context), 7, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<JsExpression> translateResolvedArgument(ResolvedValueArgument resolvedArgument, Map<ValueArgument, ? extends JsExpression> translatedArgs) {
            if (resolvedArgument instanceof DefaultValueArgument) {
                JsExpression undefinedExpression = Namer.getUndefinedExpression();
                Intrinsics.checkNotNullExpressionValue(undefinedExpression, "getUndefinedExpression(...)");
                return CollectionsKt.listOf(undefinedExpression);
            }
            List<ValueArgument> arguments = resolvedArgument.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            List<ValueArgument> list = arguments;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                JsExpression jsExpression = translatedArgs.get((ValueArgument) it.next());
                Intrinsics.checkNotNull(jsExpression);
                arrayList.add(jsExpression);
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final JsExpression concatArgumentsIfNeeded(List<? extends JsExpression> concatArguments, KotlinType varargElementType, boolean isMixed) {
            JsInvocation jsInvocation;
            concatArguments.isEmpty();
            if (concatArguments.size() > 1) {
                if (varargElementType != null && (TypeUtilsKt.isPrimitiveNumberType(varargElementType) || ArrayFIF.INSTANCE.unsignedPrimitiveToSigned(varargElementType) != null)) {
                    String str = isMixed ? "arrayConcat" : "primitiveArrayConcat";
                    SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                    spreadBuilder.add(concatArguments.get(0));
                    spreadBuilder.addSpread(concatArguments.subList(1, concatArguments.size()).toArray(new JsExpression[0]));
                    jsInvocation = JsAstUtils.invokeKotlinFunction(str, (JsExpression[]) spreadBuilder.toArray(new JsExpression[spreadBuilder.size()]));
                } else {
                    jsInvocation = new JsInvocation(new JsNameRef("concat", concatArguments.get(0)), concatArguments.subList(1, concatArguments.size()));
                }
                Intrinsics.checkNotNull(jsInvocation);
                return jsInvocation;
            }
            return concatArguments.get(0);
        }
    }
}
