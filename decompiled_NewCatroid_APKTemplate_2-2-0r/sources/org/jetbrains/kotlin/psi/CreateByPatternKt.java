package org.jetbrains.kotlin.psi;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u001aR\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2 \u0010\r\u001a\u001c\u0012\u0004\u0012\u00020\u000f\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0010\u0012\u0004\u0012\u0002H\f0\u000e2\u001d\u0010\u0011\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\f0\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010\u0016\u001aO\u0010\u0017\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u000f2\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0010\"\u00020\n2\b\b\u0002\u0010\u001b\u001a\u00020\u00012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u0002H\f0\u0012¢\u0006\u0002\u0010\u001c\u001a\u001e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\bH\u0002\u001a)\u0010\u001f\u001a\u00020 *\u00020!2\u001d\u0010\u0011\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015\u001a)\u0010\"\u001a\u00020#*\u00020!2\u001d\u0010\u0011\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015\u001a3\u0010$\u001a\u00020%*\u00020!2\b\b\u0002\u0010\u001b\u001a\u00020\u00012\u001d\u0010\u0011\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015\u001a)\u0010&\u001a\u00020'*\u00020!2\u001d\u0010\u0011\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0\u0013\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015\u001aC\u0010(\u001a\u0002H)\"\b\b\u0000\u0010)*\u00020 *\u00020!2\b\b\u0001\u0010\u0019\u001a\u00020\u000f2\u0014\b\u0001\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0010\"\u00020\n2\b\b\u0002\u0010\u001b\u001a\u00020\u0001¢\u0006\u0002\u0010*\u001a9\u0010+\u001a\u00020#*\u00020!2\b\b\u0001\u0010\u0019\u001a\u00020\u000f2\u0014\b\u0001\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0010\"\u00020\n2\b\b\u0002\u0010\u001b\u001a\u00020\u0001¢\u0006\u0002\u0010,\u001a9\u0010-\u001a\u00020%*\u00020!2\b\b\u0001\u0010\u0019\u001a\u00020\u000f2\u0014\b\u0001\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0010\"\u00020\n2\b\b\u0002\u0010\u001b\u001a\u00020\u0001¢\u0006\u0002\u0010.\u001a9\u0010/\u001a\u00020'*\u00020!2\b\b\u0001\u0010\u0019\u001a\u00020\u000f2\u0014\b\u0001\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0010\"\u00020\n2\b\b\u0002\u0010\u001b\u001a\u00020\u0001¢\u0006\u0002\u00100\"&\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006\"\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\n0\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"<set-?>", "", "CREATE_BY_PATTERN_MAY_NOT_REFORMAT", "getCREATE_BY_PATTERN_MAY_NOT_REFORMAT", "()Z", "setCREATE_BY_PATTERN_MAY_NOT_REFORMAT", "(Z)V", "SUPPORTED_ARGUMENT_TYPES", "", "Lorg/jetbrains/kotlin/psi/ArgumentType;", "", "buildByPattern", "TElement", "factory", "Lkotlin/Function2;", "", "", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/BuilderByPattern;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "createByPattern", "Lorg/jetbrains/kotlin/psi/KtElement;", "pattern", "args", "reformat", "(Ljava/lang/String;[Ljava/lang/Object;ZLkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/psi/KtElement;", "processPattern", "Lorg/jetbrains/kotlin/psi/PatternData;", "buildDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/psi/KtPsiFactory;", "buildDestructuringDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "buildExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "buildValueArgumentList", "Lorg/jetbrains/kotlin/psi/KtValueArgumentList;", "createDeclarationByPattern", "TDeclaration", "(Lorg/jetbrains/kotlin/psi/KtPsiFactory;Ljava/lang/String;[Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/psi/KtDeclaration;", "createDestructuringDeclarationByPattern", "(Lorg/jetbrains/kotlin/psi/KtPsiFactory;Ljava/lang/String;[Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "createExpressionByPattern", "(Lorg/jetbrains/kotlin/psi/KtPsiFactory;Ljava/lang/String;[Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/psi/KtExpression;", "createValueArgumentListByPattern", "(Lorg/jetbrains/kotlin/psi/KtPsiFactory;Ljava/lang/String;[Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/psi/KtValueArgumentList;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CreateByPatternKt {
    private static boolean CREATE_BY_PATTERN_MAY_NOT_REFORMAT;
    private static final List<ArgumentType<? extends Object>> SUPPORTED_ARGUMENT_TYPES = CollectionsKt.listOf((Object[]) new ArgumentType[]{new PsiElementArgumentType(KtExpression.class), new PsiElementArgumentType(KtTypeReference.class), new PlainTextArgumentType(String.class, new Function1<String, String>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt$SUPPORTED_ARGUMENT_TYPES$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return it;
        }
    }), new PlainTextArgumentType(Name.class, CreateByPatternKt$SUPPORTED_ARGUMENT_TYPES$2.INSTANCE), PsiChildRangeArgumentType.INSTANCE});

    public static /* synthetic */ KtExpression createExpressionByPattern$default(KtPsiFactory ktPsiFactory, String str, Object[] objArr, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return createExpressionByPattern(ktPsiFactory, str, objArr, z);
    }

    public static final KtExpression createExpressionByPattern(final KtPsiFactory ktPsiFactory, String pattern, Object[] args, boolean z) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(args, "args");
        return (KtExpression) createByPattern(pattern, Arrays.copyOf(args, args.length), z, new Function1<String, KtExpression>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.createExpressionByPattern.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KtExpression invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ktPsiFactory.createExpression(it);
            }
        });
    }

    public static /* synthetic */ KtValueArgumentList createValueArgumentListByPattern$default(KtPsiFactory ktPsiFactory, String str, Object[] objArr, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return createValueArgumentListByPattern(ktPsiFactory, str, objArr, z);
    }

    public static final KtValueArgumentList createValueArgumentListByPattern(final KtPsiFactory ktPsiFactory, String pattern, Object[] args, boolean z) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(args, "args");
        return (KtValueArgumentList) createByPattern(pattern, Arrays.copyOf(args, args.length), z, new Function1<String, KtValueArgumentList>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.createValueArgumentListByPattern.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KtValueArgumentList invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ktPsiFactory.createCallArguments(it);
            }
        });
    }

    public static /* synthetic */ KtDeclaration createDeclarationByPattern$default(KtPsiFactory ktPsiFactory, String str, Object[] objArr, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return createDeclarationByPattern(ktPsiFactory, str, objArr, z);
    }

    public static final <TDeclaration extends KtDeclaration> TDeclaration createDeclarationByPattern(final KtPsiFactory ktPsiFactory, String pattern, Object[] args, boolean z) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(args, "args");
        return (TDeclaration) createByPattern(pattern, Arrays.copyOf(args, args.length), z, new Function1<String, TDeclaration>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.createDeclarationByPattern.1
            {
                super(1);
            }

            /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/String;)TTDeclaration; */
            @Override // kotlin.jvm.functions.Function1
            public final KtDeclaration invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ktPsiFactory.createDeclaration(it);
            }
        });
    }

    public static /* synthetic */ KtDestructuringDeclaration createDestructuringDeclarationByPattern$default(KtPsiFactory ktPsiFactory, String str, Object[] objArr, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return createDestructuringDeclarationByPattern(ktPsiFactory, str, objArr, z);
    }

    public static final KtDestructuringDeclaration createDestructuringDeclarationByPattern(final KtPsiFactory ktPsiFactory, String pattern, Object[] args, boolean z) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(args, "args");
        return (KtDestructuringDeclaration) createByPattern(pattern, Arrays.copyOf(args, args.length), z, new Function1<String, KtDestructuringDeclaration>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.createDestructuringDeclarationByPattern.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KtDestructuringDeclaration invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ktPsiFactory.createDestructuringDeclaration(it);
            }
        });
    }

    public static final boolean getCREATE_BY_PATTERN_MAY_NOT_REFORMAT() {
        return CREATE_BY_PATTERN_MAY_NOT_REFORMAT;
    }

    public static final void setCREATE_BY_PATTERN_MAY_NOT_REFORMAT(boolean z) {
        CREATE_BY_PATTERN_MAY_NOT_REFORMAT = z;
    }

    public static /* synthetic */ KtElement createByPattern$default(String str, Object[] objArr, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return createByPattern(str, objArr, z, function1);
    }

    private static final PatternData processPattern(String str, List<? extends Object> list) {
        Integer next;
        String str2;
        Character chProcessPattern$charOrNull;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '$') {
                i2++;
                Character chProcessPattern$charOrNull2 = processPattern$charOrNull(str, i2);
                if (chProcessPattern$charOrNull2 != null && chProcessPattern$charOrNull2.charValue() == '$') {
                    sb.append(chProcessPattern$charOrNull2.charValue());
                } else {
                    processPattern$check(str, chProcessPattern$charOrNull2 != null ? Character.isDigit(chProcessPattern$charOrNull2.charValue()) : false, "unclosed '$'");
                    Iterator<Integer> it = RangesKt.until(i2, str.length()).iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (Character.isDigit(str.charAt(next.intValue())));
                    Integer num = next;
                    int iIntValue = num != null ? num.intValue() : str.length();
                    String strSubstring = str.substring(i2, iIntValue);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    int i3 = Integer.parseInt(strSubstring);
                    processPattern$check(str, i3 >= 0, "invalid placeholder number: " + i3);
                    Object obj = i3 < list.size() ? list.get(i3) : null;
                    Character chProcessPattern$charOrNull3 = processPattern$charOrNull(str, iIntValue);
                    if (chProcessPattern$charOrNull3 == null || chProcessPattern$charOrNull3.charValue() != ':' || (chProcessPattern$charOrNull = processPattern$charOrNull(str, iIntValue + 1)) == null || chProcessPattern$charOrNull.charValue() != '\'') {
                        String str3 = obj instanceof String ? (String) obj : null;
                        str2 = str3 == null ? "xyz" : str3;
                    } else {
                        processPattern$check(str, !(obj instanceof String), "do not specify placeholder text for $" + i3 + " - plain text argument passed");
                        int i4 = iIntValue + 2;
                        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, '\'', i4, false, 4, (Object) null);
                        processPattern$check(str, iIndexOf$default >= 0, "unclosed placeholder text");
                        processPattern$check(str, iIndexOf$default > i4, "empty placeholder text");
                        String strSubstring2 = str.substring(i4, iIndexOf$default);
                        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                        str2 = strSubstring2;
                        iIntValue = iIndexOf$default + 1;
                    }
                    sb.append(str2);
                    TextRange textRange = new TextRange(sb.length() - str2.length(), sb.length());
                    LinkedHashMap linkedHashMap2 = linkedHashMap;
                    Integer numValueOf = Integer.valueOf(i3);
                    Object obj2 = linkedHashMap2.get(numValueOf);
                    if (obj2 == null) {
                        obj2 = (List) new ArrayList();
                        linkedHashMap2.put(numValueOf, obj2);
                    }
                    ((List) obj2).add(new Placeholder(textRange, str2));
                    i2 = iIntValue;
                }
            } else {
                sb.append(cCharAt);
            }
            i2++;
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        if (!linkedHashMap.isEmpty()) {
            Set setKeySet = linkedHashMap.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            Object objMaxOrNull = CollectionsKt.maxOrNull((Iterable<? extends Object>) setKeySet);
            Intrinsics.checkNotNull(objMaxOrNull);
            int iIntValue2 = ((Number) objMaxOrNull).intValue();
            if (iIntValue2 >= 0) {
                while (true) {
                    processPattern$check(str, linkedHashMap.containsKey(Integer.valueOf(i)), "no '$" + i + "' placeholder");
                    if (i == iIntValue2) {
                        break;
                    }
                    i++;
                }
            }
        }
        if (list.size() != linkedHashMap.size()) {
            throw new IllegalArgumentException("Wrong number of arguments, expected: " + linkedHashMap.size() + ", passed: " + list.size());
        }
        return new PatternData(string, linkedHashMap);
    }

    private static final Character processPattern$charOrNull(String str, int i) {
        if (i < 0 || i >= str.length()) {
            return null;
        }
        return Character.valueOf(str.charAt(i));
    }

    private static final void processPattern$check(String str, boolean z, String str2) {
        if (!z) {
            throw new IllegalArgumentException("Invalid pattern '" + str + "' - " + str2);
        }
    }

    public static /* synthetic */ KtExpression buildExpression$default(KtPsiFactory ktPsiFactory, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return buildExpression(ktPsiFactory, z, function1);
    }

    public static final KtExpression buildExpression(final KtPsiFactory ktPsiFactory, final boolean z, Function1<? super BuilderByPattern<KtExpression>, Unit> build) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(build, "build");
        return (KtExpression) buildByPattern(new Function2<String, Object[], KtExpression>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.buildExpression.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final KtExpression invoke(String pattern, Object[] args) {
                Intrinsics.checkNotNullParameter(pattern, "pattern");
                Intrinsics.checkNotNullParameter(args, "args");
                return CreateByPatternKt.createExpressionByPattern(ktPsiFactory, pattern, Arrays.copyOf(args, args.length), z);
            }
        }, build);
    }

    public static final KtValueArgumentList buildValueArgumentList(final KtPsiFactory ktPsiFactory, Function1<? super BuilderByPattern<KtValueArgumentList>, Unit> build) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(build, "build");
        return (KtValueArgumentList) buildByPattern(new Function2<String, Object[], KtValueArgumentList>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.buildValueArgumentList.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final KtValueArgumentList invoke(String pattern, Object[] args) {
                Intrinsics.checkNotNullParameter(pattern, "pattern");
                Intrinsics.checkNotNullParameter(args, "args");
                return CreateByPatternKt.createValueArgumentListByPattern$default(ktPsiFactory, pattern, Arrays.copyOf(args, args.length), false, 4, null);
            }
        }, build);
    }

    public static final KtDeclaration buildDeclaration(final KtPsiFactory ktPsiFactory, Function1<? super BuilderByPattern<KtDeclaration>, Unit> build) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(build, "build");
        return (KtDeclaration) buildByPattern(new Function2<String, Object[], KtDeclaration>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.buildDeclaration.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final KtDeclaration invoke(String pattern, Object[] args) {
                Intrinsics.checkNotNullParameter(pattern, "pattern");
                Intrinsics.checkNotNullParameter(args, "args");
                return CreateByPatternKt.createDeclarationByPattern$default(ktPsiFactory, pattern, Arrays.copyOf(args, args.length), false, 4, null);
            }
        }, build);
    }

    public static final KtDestructuringDeclaration buildDestructuringDeclaration(final KtPsiFactory ktPsiFactory, Function1<? super BuilderByPattern<KtDestructuringDeclaration>, Unit> build) {
        Intrinsics.checkNotNullParameter(ktPsiFactory, "<this>");
        Intrinsics.checkNotNullParameter(build, "build");
        return (KtDestructuringDeclaration) buildByPattern(new Function2<String, Object[], KtDestructuringDeclaration>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt.buildDestructuringDeclaration.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final KtDestructuringDeclaration invoke(String pattern, Object[] args) {
                Intrinsics.checkNotNullParameter(pattern, "pattern");
                Intrinsics.checkNotNullParameter(args, "args");
                return CreateByPatternKt.createDestructuringDeclarationByPattern$default(ktPsiFactory, pattern, Arrays.copyOf(args, args.length), false, 4, null);
            }
        }, build);
    }

    public static final <TElement> TElement buildByPattern(Function2<? super String, ? super Object[], ? extends TElement> factory, Function1<? super BuilderByPattern<TElement>, Unit> build) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(build, "build");
        BuilderByPattern builderByPattern = new BuilderByPattern();
        build.invoke(builderByPattern);
        return (TElement) builderByPattern.create(factory);
    }

    public static final <TElement extends KtElement> TElement createByPattern(String pattern, Object[] args, boolean z, Function1<? super String, ? extends TElement> factory) {
        TElement telement;
        Object next;
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ArrayList arrayList = new ArrayList(args.length);
        for (Object obj : args) {
            Iterator<T> it = SUPPORTED_ARGUMENT_TYPES.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!((ArgumentType) next).getKlass().isInstance(obj));
            ArgumentType argumentType = (ArgumentType) next;
            if (argumentType == null) {
                throw new IllegalArgumentException("Unsupported argument type: " + obj.getClass() + ", should be one of: " + CollectionsKt.joinToString$default(SUPPORTED_ARGUMENT_TYPES, null, null, null, 0, null, new Function1<ArgumentType<? extends Object>, CharSequence>() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt$createByPattern$argumentTypes$1$2
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(ArgumentType<? extends Object> it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        String simpleName = it2.getKlass().getSimpleName();
                        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
                        return simpleName;
                    }
                }, 31, null));
            }
            arrayList.add(argumentType);
        }
        ArrayList arrayList2 = arrayList;
        List<Pair> listZip = ArraysKt.zip(args, arrayList2);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            Object objComponent1 = pair.component1();
            ArgumentType argumentType2 = (ArgumentType) pair.component2();
            if (argumentType2 instanceof PlainTextArgumentType) {
                Function1 toPlainText = ((PlainTextArgumentType) argumentType2).getToPlainText();
                Intrinsics.checkNotNull(toPlainText, "null cannot be cast to non-null type kotlin.Function1<kotlin.Any, kotlin.String>");
                objComponent1 = ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(toPlainText, 1)).invoke(objComponent1);
            }
            arrayList3.add(objComponent1);
        }
        ArrayList arrayList4 = arrayList3;
        PatternData patternDataProcessPattern = processPattern(pattern, arrayList4);
        String processedText = patternDataProcessPattern.getProcessedText();
        Map<Integer, List<Placeholder>> mapComponent2 = patternDataProcessPattern.component2();
        TElement telementInvoke = factory.invoke(StringsKt.trim((CharSequence) processedText).toString());
        Project project = telementInvoke.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        TElement telement2 = telementInvoke;
        int startOffset = PsiUtilsKt.getStartOffset(telement2);
        SmartPointerManager smartPointerManager = SmartPointerManager.getInstance(project);
        HashMap map = new HashMap();
        Iterator<Map.Entry<Integer, List<Placeholder>>> it2 = mapComponent2.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<Integer, List<Placeholder>> next2 = it2.next();
            int iIntValue = next2.getKey().intValue();
            List<Placeholder> value = next2.getValue();
            if (!(arrayList4.get(iIntValue) instanceof String)) {
                Object obj2 = arrayList2.get(iIntValue);
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.PsiElementPlaceholderArgumentType<*, *>");
                Class placeholderClass = ((PsiElementPlaceholderArgumentType) obj2).getPlaceholderClass();
                Iterator<Placeholder> it3 = value.iterator();
                while (it3.hasNext()) {
                    it3 = it3;
                    TextRange range = it3.next().getRange();
                    it2 = it2;
                    PsiElement psiElementFindElementAt = telementInvoke.findElementAt(range.getStartOffset());
                    Intrinsics.checkNotNull(psiElementFindElementAt);
                    Iterator<PsiElement> it4 = PsiUtilsKt.getParentsWithSelf(psiElementFindElementAt).iterator();
                    while (it4.hasNext()) {
                        TElement telement3 = telementInvoke;
                        PsiElement next3 = it4.next();
                        Iterator<PsiElement> it5 = it4;
                        ArrayList arrayList5 = arrayList2;
                        TextRange textRangeShiftRight = next3.getTextRange().shiftRight(-startOffset);
                        Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
                        if (Intrinsics.areEqual(textRangeShiftRight, range) && placeholderClass.isInstance(next3)) {
                            SmartPsiElementPointer smartPsiElementPointerCreateSmartPsiElementPointer = smartPointerManager.createSmartPsiElementPointer(next3);
                            Intrinsics.checkNotNullExpressionValue(smartPsiElementPointerCreateSmartPsiElementPointer, "createSmartPsiElementPointer(...)");
                            map.put(smartPsiElementPointerCreateSmartPsiElementPointer, Integer.valueOf(iIntValue));
                            telementInvoke = telement3;
                            arrayList2 = arrayList5;
                            break;
                        }
                        if (!range.contains(textRangeShiftRight)) {
                            throw new IllegalArgumentException("Invalid pattern '" + pattern + "' - no " + placeholderClass.getSimpleName() + " found for $" + iIntValue + ", text = '" + processedText + '\'');
                        }
                        it4 = it5;
                        telementInvoke = telement3;
                        arrayList2 = arrayList5;
                    }
                }
            }
        }
        KtElement ktElement = telementInvoke;
        ArrayList arrayList6 = arrayList2;
        CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
        if (!z) {
            telement = ktElement;
        } else {
            if (CREATE_BY_PATTERN_MAY_NOT_REFORMAT) {
                throw new IllegalArgumentException("Reformatting is not allowed in the current context; please change the invocation to use reformat=false");
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<Integer, List<Placeholder>> entry : mapComponent2.entrySet()) {
                if (arrayList4.get(entry.getKey().intValue()) instanceof String) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            ArrayList arrayList7 = new ArrayList();
            Iterator it6 = linkedHashMap.entrySet().iterator();
            while (it6.hasNext()) {
                CollectionsKt.addAll(arrayList7, (List) ((Map.Entry) it6.next()).getValue());
            }
            ArrayList arrayList8 = arrayList7;
            ArrayList arrayList9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList8, 10));
            Iterator it7 = arrayList8.iterator();
            while (it7.hasNext()) {
                arrayList9.add(((Placeholder) it7.next()).getRange());
            }
            ArrayList arrayList10 = new ArrayList();
            for (Object obj3 : arrayList9) {
                if (!((TextRange) obj3).isEmpty()) {
                    arrayList10.add(obj3);
                }
            }
            List<TextRange> listSortedWith = CollectionsKt.sortedWith(arrayList10, new Comparator() { // from class: org.jetbrains.kotlin.psi.CreateByPatternKt$createByPattern$$inlined$sortedByDescending$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Integer.valueOf(((TextRange) t2).getStartOffset()), Integer.valueOf(((TextRange) t).getStartOffset()));
                }
            });
            if (CollectionsKt.none(listSortedWith)) {
                PsiElement psiElementReformat = codeStyleManager.reformat(telement2, true);
                Intrinsics.checkNotNull(psiElementReformat, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
                telement = (TElement) psiElementReformat;
            } else {
                int endOffset = PsiUtilsKt.getEndOffset(telement2) - 1;
                for (TextRange textRange : listSortedWith) {
                    PsiElement psiElementReformatRange = codeStyleManager.reformatRange(ktElement, textRange.getEndOffset() + startOffset, endOffset + 1, true);
                    Intrinsics.checkNotNull(psiElementReformatRange, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
                    ktElement = (KtElement) psiElementReformatRange;
                    endOffset = textRange.getStartOffset() + startOffset;
                }
                PsiElement psiElementReformatRange2 = codeStyleManager.reformatRange(ktElement, startOffset, endOffset + 1, true);
                Intrinsics.checkNotNull(psiElementReformatRange2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
                telement = (TElement) psiElementReformatRange2;
            }
            CodeEditUtil.setNodeGeneratedRecursively(telement.getNode(), false);
        }
        for (Map.Entry entry2 : map.entrySet()) {
            SmartPsiElementPointer smartPsiElementPointer = (SmartPsiElementPointer) entry2.getKey();
            int iIntValue2 = ((Number) entry2.getValue()).intValue();
            KtLambdaExpression element = smartPsiElementPointer.getElement();
            Intrinsics.checkNotNull(element);
            if (element instanceof KtFunctionLiteral) {
                PsiElement parent = ((KtFunctionLiteral) element).getLightParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtLambdaExpression");
                element = (KtLambdaExpression) parent;
            }
            ArrayList arrayList11 = arrayList6;
            Object obj4 = arrayList11.get(iIntValue2);
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.PsiElementPlaceholderArgumentType<in kotlin.Any, in com.intellij.psi.PsiElement>");
            PsiChildRange psiChildRangeReplacePlaceholderElement = ((PsiElementPlaceholderArgumentType) obj4).replacePlaceholderElement(element, arrayList4.get(iIntValue2), z);
            if (Intrinsics.areEqual(element, telement)) {
                Intrinsics.areEqual(psiChildRangeReplacePlaceholderElement.getFirst(), psiChildRangeReplacePlaceholderElement.getLast());
                PsiElement first = psiChildRangeReplacePlaceholderElement.getFirst();
                Intrinsics.checkNotNull(first, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtElement");
                telement = (TElement) first;
            }
            arrayList6 = arrayList11;
        }
        if (z) {
            codeStyleManager.adjustLineIndent(telement.getContainingFile(), telement.getTextRange());
        }
        Intrinsics.checkNotNull(telement, "null cannot be cast to non-null type TElement of org.jetbrains.kotlin.psi.CreateByPatternKt.createByPattern");
        return (TElement) telement;
    }
}
