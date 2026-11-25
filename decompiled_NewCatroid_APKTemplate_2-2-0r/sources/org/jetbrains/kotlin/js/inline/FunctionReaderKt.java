package org.jetbrains.kotlin.js.inline;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import kotlin.text.Regex;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.inline.util.CollectionUtilsKt;
import org.jetbrains.kotlin.js.inline.util.InvocationUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: FunctionReader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00150\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017H\u0002\u001a\u0014\u0010\u0019\u001a\u00020\u0011*\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\f\u001a\u00020\r*\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000f¨\u0006\u001d"}, d2 = {"DEFINE_MODULE_FIND_PATTERN", "", "DEFINE_MODULE_PATTERN", "Ljava/util/regex/Pattern;", "JS_IDENTIFIER", "JS_IDENTIFIER_PART", "JS_IDENTIFIER_START", "SPECIAL_FUNCTION_PATTERN", "specialFunctions", "specialFunctionsByName", "", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "isWhitespaceOrComma", "", "", "(C)Z", "replaceExternalNames", "", "node", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "replacements", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "definedNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "markInlineArguments", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionReaderKt {
    private static final String DEFINE_MODULE_FIND_PATTERN;
    private static final Pattern DEFINE_MODULE_PATTERN;
    private static final String JS_IDENTIFIER;
    private static final String JS_IDENTIFIER_PART;
    private static final String JS_IDENTIFIER_START = "\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}\\$_";
    private static final Pattern SPECIAL_FUNCTION_PATTERN;
    private static final String specialFunctions;
    private static final Map<String, SpecialFunction> specialFunctionsByName;

    static {
        String str = JS_IDENTIFIER_START + "\\p{Pc}\\p{Mc}\\p{Mn}\\d";
        JS_IDENTIFIER_PART = str;
        String str2 = "[" + JS_IDENTIFIER_START + "][" + str + "]*";
        JS_IDENTIFIER = str2;
        DEFINE_MODULE_PATTERN = new Regex("(" + str2 + ")\\.defineModule\\(\\s*(['\"])([^'\"]+)\\2\\s*,\\s*(\\w+)\\s*\\)").getNativePattern();
        DEFINE_MODULE_FIND_PATTERN = ".defineModule(";
        specialFunctions = ArraysKt.joinToString$default(SpecialFunction.values(), "|", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1<SpecialFunction, CharSequence>() { // from class: org.jetbrains.kotlin.js.inline.FunctionReaderKt$specialFunctions$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(SpecialFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSuggestedName();
            }
        }, 30, (Object) null);
        SpecialFunction[] specialFunctionArrValues = SpecialFunction.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(specialFunctionArrValues.length), 16));
        for (SpecialFunction specialFunction : specialFunctionArrValues) {
            linkedHashMap.put(specialFunction.getSuggestedName(), specialFunction);
        }
        specialFunctionsByName = linkedHashMap;
        StringBuilder sb = new StringBuilder("var\\s+(");
        String str3 = JS_IDENTIFIER;
        SPECIAL_FUNCTION_PATTERN = new Regex(sb.append(str3).append(")\\s*=\\s*(").append(str3).append(")\\.(").append(specialFunctions).append(")\\s*;").toString()).getNativePattern();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isWhitespaceOrComma(char c) {
        return c == ',' || CharsKt.isWhitespace(c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void markInlineArguments(JsFunction jsFunction, CallableDescriptor callableDescriptor) {
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        final Set setIdentitySet = CollectionUtilsKt.IdentitySet();
        boolean zIsExtension = DescriptorUtilsKt.isExtension(callableDescriptor);
        Iterator<ValueParameterDescriptor> it = valueParameters.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            KotlinType type = it.next().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (FunctionTypesKt.isFunctionTypeOrSubtype(type)) {
                JsName name2 = parameters.get(i + (zIsExtension ? 1 : 0)).getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                setIdentitySet.add(name2);
            }
            i = i2;
        }
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.FunctionReaderKt$markInlineArguments$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsInvocation x, JsContext<?> ctx) {
                JsExpression qualifier;
                JsName name3;
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (InvocationUtilsKt.isCallInvocation(x)) {
                    JsExpression qualifier2 = x.getQualifier();
                    JsNameRef jsNameRef = qualifier2 instanceof JsNameRef ? (JsNameRef) qualifier2 : null;
                    qualifier = jsNameRef != null ? jsNameRef.getQualifier() : null;
                } else {
                    qualifier = x.getQualifier();
                }
                JsNameRef jsNameRef2 = qualifier instanceof JsNameRef ? (JsNameRef) qualifier : null;
                if (jsNameRef2 == null || (name3 = jsNameRef2.getName()) == null || !setIdentitySet.contains(name3)) {
                    return;
                }
                MetadataProperties.setInline(x, (Boolean) true);
            }
        }.accept(jsFunction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void replaceExternalNames(JsNode jsNode, final Map<String, ? extends JsExpression> map, final Set<? extends JsName> set) {
        new JsVisitorWithContextImpl() { // from class: org.jetbrains.kotlin.js.inline.FunctionReaderKt$replaceExternalNames$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
            public void endVisit(JsNameRef x, JsContext<JsNode> ctx) {
                JsExpression jsExpression;
                Intrinsics.checkNotNullParameter(x, "x");
                Intrinsics.checkNotNullParameter(ctx, "ctx");
                if (x.getQualifier() != null || CollectionsKt.contains(set, x.getName()) || (jsExpression = map.get(x.getIdent())) == null) {
                    return;
                }
                ctx.replaceMe(jsExpression);
            }
        }.accept(jsNode);
    }
}
