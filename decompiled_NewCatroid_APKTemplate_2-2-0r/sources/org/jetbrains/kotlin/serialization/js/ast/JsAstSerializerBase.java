package org.jetbrains.kotlin.serialization.js.ast;

import com.reandroid.apk.PathMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsArrayLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBooleanLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCase;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsClass;
import org.jetbrains.kotlin.js.backend.ast.JsComment;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDebugger;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsDocComment;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsMultiLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPropertyInitializer;
import org.jetbrains.kotlin.js.backend.ast.JsRegExp;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.LocalAlias;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: JsAstSerializerBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0094\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 H&J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0004J\u0010\u0010!\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0004J\u0010\u0010!\u001a\u00020(2\u0006\u0010#\u001a\u00020)H\u0004J\u0010\u0010!\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0004J\u0010\u0010!\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0004J\u0010\u00100\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u0005H\u0004J\u0010\u00100\u001a\u0002022\u0006\u00103\u001a\u000204H\u0004J\u0010\u00100\u001a\u0002052\u0006\u00106\u001a\u000207H\u0004J\u0010\u00100\u001a\u0002082\u0006\u00109\u001a\u00020:H\u0004J\u0010\u00100\u001a\u00020\u000f2\u0006\u0010;\u001a\u00020\nH\u0004J\u0010\u00100\u001a\u00020<2\u0006\u0010=\u001a\u00020>H\u0004J\u0010\u00100\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0004J\u0010\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0004J\u0010\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020IH\u0004J\u0010\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020MH\u0004J\u0018\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020SH\u0004J\u0010\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020WH\u0004JG\u0010X\u001a\u00020Y2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010Z\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020Y0[2\u0012\u0010\\\u001a\u000e\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u00020Y0[2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020Y0^H\u0082\bJG\u0010_\u001a\u00020Y2\u0006\u0010\u001f\u001a\u00020 2\u0012\u0010`\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020Y0[2\u0012\u0010a\u001a\u000e\u0012\u0004\u0012\u00020b\u0012\u0004\u0012\u00020Y0[2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020Y0^H\u0082\bR\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR \u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000f0\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\n \u0014*\u0004\u0018\u00010\u00130\u0013X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R \u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u001c\u0010\u0019\u001a\n \u0014*\u0004\u0018\u00010\u001a0\u001aX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001c¨\u0006c"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/ast/JsAstSerializerBase;", "", "()V", "fileStack", "Ljava/util/Deque;", "", "getFileStack", "()Ljava/util/Deque;", "importedNames", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getImportedNames", "()Ljava/util/Set;", "nameMap", "", "", "getNameMap", "()Ljava/util/Map;", "nameTableBuilder", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$NameTable$Builder;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getNameTableBuilder", "()Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$NameTable$Builder;", "stringMap", "getStringMap", "stringTableBuilder", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$StringTable$Builder;", "getStringTableBuilder", "()Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$StringTable$Builder;", "extractLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "node", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "map", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$BinaryOperation$Type;", "op", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Function$Modifier;", "modifier", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction$Modifier;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$UnaryOperation$Type;", "Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperator;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$SideEffects;", "sideEffects", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$SpecialFunction;", "specialFunction", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "serialize", "string", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Comment;", "comment", "Lorg/jetbrains/kotlin/js/backend/ast/JsComment;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Expression;", "expression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$JsImportedModule;", "module", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "name", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Statement;", "statement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$LocalAlias;", PathMap.NAME_alias, "Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "serializeBlock", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$CompositeBlock;", "block", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "serializeFunction", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Function;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "serializeParameter", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Parameter;", "parameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "serializeUnary", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$UnaryOperation;", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperation;", "postfix", "", "serializeVars", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Vars;", "vars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "withComments", "", "beforeCommentsConsumer", "Lkotlin/Function1;", "afterCommentsConsumer", "inner", "Lkotlin/Function0;", "withLocation", "fileConsumer", "locationConsumer", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Location;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JsAstSerializerBase {
    private final JsAstProtoBuf.NameTable.Builder nameTableBuilder = JsAstProtoBuf.NameTable.newBuilder();
    private final JsAstProtoBuf.StringTable.Builder stringTableBuilder = JsAstProtoBuf.StringTable.newBuilder();
    private final Map<JsName, Integer> nameMap = new LinkedHashMap();
    private final Map<String, Integer> stringMap = new LinkedHashMap();
    private final Deque<String> fileStack = new ArrayDeque();
    private final Set<JsName> importedNames = new LinkedHashSet();

    /* JADX INFO: compiled from: JsAstSerializerBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;

        static {
            int[] iArr = new int[JsFunction.Modifier.values().length];
            try {
                iArr[JsFunction.Modifier.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsFunction.Modifier.SET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsFunction.Modifier.GET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[JsBinaryOperator.values().length];
            try {
                iArr2[JsBinaryOperator.MUL.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[JsBinaryOperator.DIV.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[JsBinaryOperator.MOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[JsBinaryOperator.ADD.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[JsBinaryOperator.SUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[JsBinaryOperator.SHL.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[JsBinaryOperator.SHR.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[JsBinaryOperator.SHRU.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[JsBinaryOperator.LT.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[JsBinaryOperator.LTE.ordinal()] = 10;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[JsBinaryOperator.GT.ordinal()] = 11;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[JsBinaryOperator.GTE.ordinal()] = 12;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[JsBinaryOperator.INSTANCEOF.ordinal()] = 13;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[JsBinaryOperator.INOP.ordinal()] = 14;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr2[JsBinaryOperator.EQ.ordinal()] = 15;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr2[JsBinaryOperator.NEQ.ordinal()] = 16;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr2[JsBinaryOperator.REF_EQ.ordinal()] = 17;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr2[JsBinaryOperator.REF_NEQ.ordinal()] = 18;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr2[JsBinaryOperator.BIT_AND.ordinal()] = 19;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[JsBinaryOperator.BIT_XOR.ordinal()] = 20;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[JsBinaryOperator.BIT_OR.ordinal()] = 21;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[JsBinaryOperator.AND.ordinal()] = 22;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[JsBinaryOperator.OR.ordinal()] = 23;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[JsBinaryOperator.ASG.ordinal()] = 24;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_ADD.ordinal()] = 25;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SUB.ordinal()] = 26;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_MUL.ordinal()] = 27;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_DIV.ordinal()] = 28;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_MOD.ordinal()] = 29;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SHL.ordinal()] = 30;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SHR.ordinal()] = 31;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_SHRU.ordinal()] = 32;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_BIT_AND.ordinal()] = 33;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_BIT_OR.ordinal()] = 34;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr2[JsBinaryOperator.ASG_BIT_XOR.ordinal()] = 35;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr2[JsBinaryOperator.COMMA.ordinal()] = 36;
            } catch (NoSuchFieldError unused39) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[JsUnaryOperator.values().length];
            try {
                iArr3[JsUnaryOperator.BIT_NOT.ordinal()] = 1;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr3[JsUnaryOperator.DEC.ordinal()] = 2;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr3[JsUnaryOperator.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr3[JsUnaryOperator.INC.ordinal()] = 4;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr3[JsUnaryOperator.NEG.ordinal()] = 5;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr3[JsUnaryOperator.POS.ordinal()] = 6;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr3[JsUnaryOperator.NOT.ordinal()] = 7;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr3[JsUnaryOperator.TYPEOF.ordinal()] = 8;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr3[JsUnaryOperator.VOID.ordinal()] = 9;
            } catch (NoSuchFieldError unused48) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[SideEffectKind.values().length];
            try {
                iArr4[SideEffectKind.AFFECTS_STATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr4[SideEffectKind.DEPENDS_ON_STATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr4[SideEffectKind.PURE.ordinal()] = 3;
            } catch (NoSuchFieldError unused51) {
            }
            $EnumSwitchMapping$3 = iArr4;
            int[] iArr5 = new int[SpecialFunction.values().length];
            try {
                iArr5[SpecialFunction.DEFINE_INLINE_FUNCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                iArr5[SpecialFunction.WRAP_FUNCTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                iArr5[SpecialFunction.TO_BOXED_CHAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                iArr5[SpecialFunction.UNBOX_CHAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                iArr5[SpecialFunction.SUSPEND_CALL.ordinal()] = 5;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                iArr5[SpecialFunction.COROUTINE_RESULT.ordinal()] = 6;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                iArr5[SpecialFunction.COROUTINE_CONTROLLER.ordinal()] = 7;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                iArr5[SpecialFunction.COROUTINE_RECEIVER.ordinal()] = 8;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                iArr5[SpecialFunction.SET_COROUTINE_RESULT.ordinal()] = 9;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                iArr5[SpecialFunction.GET_KCLASS.ordinal()] = 10;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                iArr5[SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE.ordinal()] = 11;
            } catch (NoSuchFieldError unused62) {
            }
            $EnumSwitchMapping$4 = iArr5;
        }
    }

    public abstract JsLocation extractLocation(JsNode node);

    protected final JsAstProtoBuf.NameTable.Builder getNameTableBuilder() {
        return this.nameTableBuilder;
    }

    protected final JsAstProtoBuf.StringTable.Builder getStringTableBuilder() {
        return this.stringTableBuilder;
    }

    protected final Map<JsName, Integer> getNameMap() {
        return this.nameMap;
    }

    protected final Map<String, Integer> getStringMap() {
        return this.stringMap;
    }

    protected final Deque<String> getFileStack() {
        return this.fileStack;
    }

    protected final Set<JsName> getImportedNames() {
        return this.importedNames;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [org.jetbrains.kotlin.serialization.js.ast.JsAstSerializerBase$serialize$visitor$1] */
    protected final JsAstProtoBuf.Statement serialize(JsStatement statement) {
        boolean z;
        Intrinsics.checkNotNullParameter(statement, "statement");
        ?? r0 = new JsVisitor() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstSerializerBase$serialize$visitor$1
            private final JsAstProtoBuf.Statement.Builder builder = JsAstProtoBuf.Statement.newBuilder();

            public final JsAstProtoBuf.Statement.Builder getBuilder() {
                return this.builder;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitReturn(JsReturn x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Return.Builder builderNewBuilder = JsAstProtoBuf.Return.newBuilder();
                JsExpression expression = x.getExpression();
                if (expression != null) {
                    builderNewBuilder.setValue(this.this$0.serialize(expression));
                }
                this.builder.setReturnStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitThrow(JsThrow x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Throw.Builder builderNewBuilder = JsAstProtoBuf.Throw.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                builderNewBuilder.setException(jsAstSerializerBase.serialize(expression));
                this.builder.setThrowStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBreak(JsBreak x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Break.Builder builderNewBuilder = JsAstProtoBuf.Break.newBuilder();
                JsNameRef label = x.getLabel();
                if (label != null) {
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    JsName name2 = label.getName();
                    Intrinsics.checkNotNull(name2);
                    builderNewBuilder.setLabelId(jsAstSerializerBase.serialize(name2));
                }
                this.builder.setBreakStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitContinue(JsContinue x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Continue.Builder builderNewBuilder = JsAstProtoBuf.Continue.newBuilder();
                JsNameRef label = x.getLabel();
                if (label != null) {
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    JsName name2 = label.getName();
                    Intrinsics.checkNotNull(name2);
                    builderNewBuilder.setLabelId(jsAstSerializerBase.serialize(name2));
                }
                this.builder.setContinueStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDebugger(JsDebugger x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setDebugger(JsAstProtoBuf.Debugger.newBuilder().build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitExpressionStatement(JsExpressionStatement x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.ExpressionStatement.Builder builderNewBuilder = JsAstProtoBuf.ExpressionStatement.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                builderNewBuilder.setExpression(jsAstSerializerBase.serialize(expression));
                String exportedTag = MetadataProperties.getExportedTag(x);
                if (exportedTag != null) {
                    builderNewBuilder.setExportedTagId(this.this$0.serialize(exportedTag));
                }
                this.builder.setExpression(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitVars(JsVars x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setVars(this.this$0.serializeVars(x));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBlock(JsBlock x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (x instanceof JsCompositeBlock) {
                    this.builder.setCompositeBlock(this.this$0.serializeBlock((JsCompositeBlock) x));
                    return;
                }
                JsAstProtoBuf.Block.Builder builderNewBuilder = JsAstProtoBuf.Block.newBuilder();
                for (JsStatement jsStatement : x.getStatements()) {
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    Intrinsics.checkNotNull(jsStatement);
                    builderNewBuilder.addStatement(jsAstSerializerBase.serialize(jsStatement));
                }
                this.builder.setBlock(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitLabel(JsLabel x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Label.Builder builderNewBuilder = JsAstProtoBuf.Label.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsName name2 = x.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                builderNewBuilder.setNameId(jsAstSerializerBase.serialize(name2));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsStatement statement2 = x.getStatement();
                Intrinsics.checkNotNullExpressionValue(statement2, "getStatement(...)");
                builderNewBuilder.setInnerStatement(jsAstSerializerBase2.serialize(statement2));
                this.builder.setLabel(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitIf(JsIf x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.If.Builder builderNewBuilder = JsAstProtoBuf.If.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression ifExpression = x.getIfExpression();
                Intrinsics.checkNotNullExpressionValue(ifExpression, "getIfExpression(...)");
                builderNewBuilder.setCondition(jsAstSerializerBase.serialize(ifExpression));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsStatement thenStatement = x.getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                builderNewBuilder.setThenStatement(jsAstSerializerBase2.serialize(thenStatement));
                JsStatement elseStatement = x.getElseStatement();
                if (elseStatement != null) {
                    builderNewBuilder.setElseStatement(this.this$0.serialize(elseStatement));
                }
                this.builder.setIfStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visit(JsSwitch x) {
                boolean z2;
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Switch.Builder builderNewBuilder = JsAstProtoBuf.Switch.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression expression = x.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                builderNewBuilder.setExpression(jsAstSerializerBase.serialize(expression));
                for (JsSwitchMember jsSwitchMember : x.getCases()) {
                    JsAstProtoBuf.SwitchEntry.Builder builderNewBuilder2 = JsAstProtoBuf.SwitchEntry.newBuilder();
                    JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                    Intrinsics.checkNotNull(jsSwitchMember);
                    JsLocation jsLocationExtractLocation = jsAstSerializerBase2.extractLocation(jsSwitchMember);
                    if (jsLocationExtractLocation != null) {
                        String strPeek = jsAstSerializerBase2.getFileStack().peek();
                        String file = jsLocationExtractLocation.getFile();
                        boolean zAreEqual = Intrinsics.areEqual(strPeek, file);
                        z2 = !zAreEqual;
                        if (!zAreEqual) {
                            builderNewBuilder2.setFileId(jsAstSerializerBase2.serialize(file));
                            jsAstSerializerBase2.getFileStack().push(jsLocationExtractLocation.getFile());
                        }
                        JsAstProtoBuf.Location.Builder builderNewBuilder3 = JsAstProtoBuf.Location.newBuilder();
                        builderNewBuilder3.setStartLine(jsLocationExtractLocation.getStartLine());
                        builderNewBuilder3.setStartChar(jsLocationExtractLocation.getStartChar());
                        JsAstProtoBuf.Location locationBuild = builderNewBuilder3.build();
                        Intrinsics.checkNotNullExpressionValue(locationBuild, "build(...)");
                        builderNewBuilder2.setLocation(locationBuild);
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        jsAstSerializerBase2.getFileStack().pop();
                    }
                    if (jsSwitchMember instanceof JsCase) {
                        JsAstSerializerBase jsAstSerializerBase3 = this.this$0;
                        JsExpression caseExpression = ((JsCase) jsSwitchMember).getCaseExpression();
                        Intrinsics.checkNotNullExpressionValue(caseExpression, "getCaseExpression(...)");
                        builderNewBuilder2.setLabel(jsAstSerializerBase3.serialize(caseExpression));
                    }
                    for (JsStatement jsStatement : jsSwitchMember.getStatements()) {
                        JsAstSerializerBase jsAstSerializerBase4 = this.this$0;
                        Intrinsics.checkNotNull(jsStatement);
                        builderNewBuilder2.addStatement(jsAstSerializerBase4.serialize(jsStatement));
                    }
                    builderNewBuilder.addEntry(builderNewBuilder2);
                }
                this.builder.setSwitchStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitWhile(JsWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.While.Builder builderNewBuilder = JsAstProtoBuf.While.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression condition = x.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
                builderNewBuilder.setCondition(jsAstSerializerBase.serialize(condition));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                builderNewBuilder.setBody(jsAstSerializerBase2.serialize(body));
                this.builder.setWhileStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDoWhile(JsDoWhile x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.DoWhile.Builder builderNewBuilder = JsAstProtoBuf.DoWhile.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression condition = x.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
                builderNewBuilder.setCondition(jsAstSerializerBase.serialize(condition));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                builderNewBuilder.setBody(jsAstSerializerBase2.serialize(body));
                this.builder.setDoWhileStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFor(JsFor x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.For.Builder builderNewBuilder = JsAstProtoBuf.For.newBuilder();
                if (x.getInitVars() != null) {
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    JsVars initVars = x.getInitVars();
                    Intrinsics.checkNotNullExpressionValue(initVars, "getInitVars(...)");
                    builderNewBuilder.setVariables(jsAstSerializerBase.serialize(initVars));
                } else if (x.getInitExpression() != null) {
                    JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                    JsExpression initExpression = x.getInitExpression();
                    Intrinsics.checkNotNullExpressionValue(initExpression, "getInitExpression(...)");
                    builderNewBuilder.setExpression(jsAstSerializerBase2.serialize(initExpression));
                } else {
                    builderNewBuilder.setEmpty(JsAstProtoBuf.EmptyInit.newBuilder().build());
                }
                JsExpression condition = x.getCondition();
                if (condition != null) {
                    builderNewBuilder.setCondition(this.this$0.serialize(condition));
                }
                JsExpression incrementExpression = x.getIncrementExpression();
                if (incrementExpression != null) {
                    builderNewBuilder.setIncrement(this.this$0.serialize(incrementExpression));
                }
                JsAstSerializerBase jsAstSerializerBase3 = this.this$0;
                JsEmpty body = x.getBody();
                if (body == null) {
                    body = JsEmpty.INSTANCE;
                }
                builderNewBuilder.setBody(jsAstSerializerBase3.serialize(body));
                this.builder.setForStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitForIn(JsForIn x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.ForIn.Builder builderNewBuilder = JsAstProtoBuf.ForIn.newBuilder();
                if (x.getIterVarName() != null) {
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    JsName iterVarName = x.getIterVarName();
                    Intrinsics.checkNotNullExpressionValue(iterVarName, "getIterVarName(...)");
                    builderNewBuilder.setNameId(jsAstSerializerBase.serialize(iterVarName));
                } else if (x.getIterExpression() != null) {
                    JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                    JsExpression iterExpression = x.getIterExpression();
                    Intrinsics.checkNotNullExpressionValue(iterExpression, "getIterExpression(...)");
                    builderNewBuilder.setExpression(jsAstSerializerBase2.serialize(iterExpression));
                }
                JsAstSerializerBase jsAstSerializerBase3 = this.this$0;
                JsExpression objectExpression = x.getObjectExpression();
                Intrinsics.checkNotNullExpressionValue(objectExpression, "getObjectExpression(...)");
                builderNewBuilder.setIterable(jsAstSerializerBase3.serialize(objectExpression));
                JsAstSerializerBase jsAstSerializerBase4 = this.this$0;
                JsStatement body = x.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                builderNewBuilder.setBody(jsAstSerializerBase4.serialize(body));
                this.builder.setForInStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitTry(JsTry x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Try.Builder builderNewBuilder = JsAstProtoBuf.Try.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsBlock tryBlock = x.getTryBlock();
                Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
                builderNewBuilder.setTryBlock(jsAstSerializerBase.serialize(tryBlock));
                List<JsCatch> catches = x.getCatches();
                Intrinsics.checkNotNullExpressionValue(catches, "getCatches(...)");
                JsCatch jsCatch = (JsCatch) CollectionsKt.firstOrNull((List) catches);
                if (jsCatch != null) {
                    JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                    JsAstProtoBuf.Catch.Builder builderNewBuilder2 = JsAstProtoBuf.Catch.newBuilder();
                    JsParameter parameter = jsCatch.getParameter();
                    Intrinsics.checkNotNullExpressionValue(parameter, "getParameter(...)");
                    builderNewBuilder2.setParameter(jsAstSerializerBase2.serializeParameter(parameter));
                    JsBlock body = jsCatch.getBody();
                    Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                    builderNewBuilder2.setBody(jsAstSerializerBase2.serialize(body));
                    builderNewBuilder.setCatchBlock(builderNewBuilder2.build());
                }
                JsBlock finallyBlock = x.getFinallyBlock();
                if (finallyBlock != null) {
                    builderNewBuilder.setFinallyBlock(this.this$0.serialize(finallyBlock));
                }
                this.builder.setTryStatement(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitEmpty(JsEmpty x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setEmpty(JsAstProtoBuf.Empty.newBuilder().build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitSingleLineComment(JsSingleLineComment comment) {
                Intrinsics.checkNotNullParameter(comment, "comment");
                this.builder.setSingleLineComment(JsAstProtoBuf.SingleLineComment.newBuilder().setMessage(comment.getText()).build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitMultiLineComment(JsMultiLineComment comment) {
                Intrinsics.checkNotNullParameter(comment, "comment");
                this.builder.setMultiLineComment(JsAstProtoBuf.MultiLineComment.newBuilder().setMessage(comment.getText()).build());
            }
        };
        JsStatement jsStatement = statement;
        List<JsComment> commentsBeforeNode = jsStatement.getCommentsBeforeNode();
        if (commentsBeforeNode != null) {
            Intrinsics.checkNotNull(commentsBeforeNode);
            for (JsComment jsComment : commentsBeforeNode) {
                Intrinsics.checkNotNull(jsComment);
                r0.getBuilder().addBeforeComments(serialize(jsComment));
            }
        }
        List<JsComment> commentsAfterNode = jsStatement.getCommentsAfterNode();
        if (commentsAfterNode != null) {
            Intrinsics.checkNotNull(commentsAfterNode);
            for (JsComment jsComment2 : commentsAfterNode) {
                Intrinsics.checkNotNull(jsComment2);
                r0.getBuilder().addAfterComments(serialize(jsComment2));
            }
        }
        JsLocation jsLocationExtractLocation = extractLocation(jsStatement);
        if (jsLocationExtractLocation != null) {
            String strPeek = getFileStack().peek();
            String file = jsLocationExtractLocation.getFile();
            boolean zAreEqual = Intrinsics.areEqual(strPeek, file);
            z = !zAreEqual;
            if (!zAreEqual) {
                r0.getBuilder().setFileId(serialize(file));
                getFileStack().push(jsLocationExtractLocation.getFile());
            }
            JsAstProtoBuf.Location.Builder builderNewBuilder = JsAstProtoBuf.Location.newBuilder();
            builderNewBuilder.setStartLine(jsLocationExtractLocation.getStartLine());
            builderNewBuilder.setStartChar(jsLocationExtractLocation.getStartChar());
            JsAstProtoBuf.Location locationBuild = builderNewBuilder.build();
            Intrinsics.checkNotNullExpressionValue(locationBuild, "build(...)");
            r0.getBuilder().setLocation(locationBuild);
        } else {
            z = false;
        }
        statement.accept((JsVisitor) r0);
        if (z) {
            getFileStack().pop();
        }
        if ((statement instanceof HasMetadata) && MetadataProperties.getSynthetic((HasMetadata) statement)) {
            r0.getBuilder().setSynthetic(true);
        }
        if (r0.getBuilder().getStatementCase() == JsAstProtoBuf.Statement.StatementCase.STATEMENT_NOT_SET) {
            throw new IllegalStateException(("Unknown statement type: " + Reflection.getOrCreateKotlinClass(statement.getClass()).getQualifiedName()).toString());
        }
        JsAstProtoBuf.Statement statementBuild = r0.getBuilder().build();
        Intrinsics.checkNotNullExpressionValue(statementBuild, "build(...)");
        return statementBuild;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [org.jetbrains.kotlin.serialization.js.ast.JsAstSerializerBase$serialize$visitor$2] */
    protected final JsAstProtoBuf.Expression serialize(JsExpression expression) {
        boolean z;
        Intrinsics.checkNotNullParameter(expression, "expression");
        ?? r0 = new JsVisitor() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstSerializerBase$serialize$visitor$2
            private final JsAstProtoBuf.Expression.Builder builder = JsAstProtoBuf.Expression.newBuilder();

            public final JsAstProtoBuf.Expression.Builder getBuilder() {
                return this.builder;
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitThis(JsThisRef x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setThisLiteral(JsAstProtoBuf.ThisLiteral.newBuilder().build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitSuper(JsSuperRef x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setSuperLiteral(JsAstProtoBuf.SuperLiteral.newBuilder().build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNull(JsNullLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setNullLiteral(JsAstProtoBuf.NullLiteral.newBuilder().build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBoolean(JsBooleanLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                if (x.getValue()) {
                    this.builder.setTrueLiteral(JsAstProtoBuf.TrueLiteral.newBuilder().build());
                } else {
                    this.builder.setFalseLiteral(JsAstProtoBuf.FalseLiteral.newBuilder().build());
                }
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitString(JsStringLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Expression.Builder builder = this.builder;
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                String value = x.getValue();
                Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                builder.setStringLiteral(jsAstSerializerBase.serialize(value));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitRegExp(JsRegExp x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.RegExpLiteral.Builder builderNewBuilder = JsAstProtoBuf.RegExpLiteral.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                String pattern = x.getPattern();
                Intrinsics.checkNotNullExpressionValue(pattern, "getPattern(...)");
                builderNewBuilder.setPatternStringId(jsAstSerializerBase.serialize(pattern));
                String flags = x.getFlags();
                if (flags != null) {
                    builderNewBuilder.setFlagsStringId(this.this$0.serialize(flags));
                }
                this.builder.setRegExpLiteral(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInt(JsIntLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setIntLiteral(x.value);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDouble(JsDoubleLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setDoubleLiteral(x.value);
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitArray(JsArrayLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.ArrayLiteral.Builder builderNewBuilder = JsAstProtoBuf.ArrayLiteral.newBuilder();
                List<JsExpression> expressions = x.getExpressions();
                Intrinsics.checkNotNullExpressionValue(expressions, "getExpressions(...)");
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                for (JsExpression jsExpression : expressions) {
                    Intrinsics.checkNotNull(jsExpression);
                    builderNewBuilder.addElement(jsAstSerializerBase.serialize(jsExpression));
                }
                this.builder.setArrayLiteral(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitObjectLiteral(JsObjectLiteral x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.ObjectLiteral.Builder builderNewBuilder = JsAstProtoBuf.ObjectLiteral.newBuilder();
                for (JsPropertyInitializer jsPropertyInitializer : x.getPropertyInitializers()) {
                    JsAstProtoBuf.ObjectLiteralEntry.Builder builderNewBuilder2 = JsAstProtoBuf.ObjectLiteralEntry.newBuilder();
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    JsExpression labelExpr = jsPropertyInitializer.getLabelExpr();
                    Intrinsics.checkNotNullExpressionValue(labelExpr, "getLabelExpr(...)");
                    builderNewBuilder2.setKey(jsAstSerializerBase.serialize(labelExpr));
                    JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                    JsExpression valueExpr = jsPropertyInitializer.getValueExpr();
                    Intrinsics.checkNotNullExpressionValue(valueExpr, "getValueExpr(...)");
                    builderNewBuilder2.setValue(jsAstSerializerBase2.serialize(valueExpr));
                    builderNewBuilder.addEntry(builderNewBuilder2);
                }
                builderNewBuilder.setMultiline(x.isMultiline());
                this.builder.setObjectLiteral(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitFunction(JsFunction x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setFunction(this.this$0.serializeFunction(x));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitClass(JsClass x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Class.Builder builderNewBuilder = JsAstProtoBuf.Class.newBuilder();
                JsName name2 = x.getName();
                if (name2 != null) {
                    builderNewBuilder.setNameId(this.this$0.serialize(name2));
                }
                JsExpression baseClass = x.getBaseClass();
                if (baseClass != null) {
                    builderNewBuilder.setSuperExpression(this.this$0.serialize(baseClass));
                }
                JsFunction constructor = x.getConstructor();
                if (constructor != null) {
                    builderNewBuilder.setConstructor(this.this$0.serializeFunction(constructor));
                }
                List<JsFunction> members = x.getMembers();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                Iterator<T> it = members.iterator();
                while (it.hasNext()) {
                    builderNewBuilder.addMember(jsAstSerializerBase.serializeFunction((JsFunction) it.next()));
                }
                this.builder.setClass_(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitDocComment(JsDocComment comment) {
                Intrinsics.checkNotNullParameter(comment, "comment");
                JsAstProtoBuf.DocComment.Builder builderNewBuilder = JsAstProtoBuf.DocComment.newBuilder();
                Map<String, Object> tags = comment.getTags();
                Intrinsics.checkNotNullExpressionValue(tags, "getTags(...)");
                for (Map.Entry<String, Object> entry : tags.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    JsAstProtoBuf.DocCommentTag.Builder builderNewBuilder2 = JsAstProtoBuf.DocCommentTag.newBuilder();
                    JsAstSerializerBase jsAstSerializerBase = this.this$0;
                    Intrinsics.checkNotNull(key);
                    builderNewBuilder2.setNameId(jsAstSerializerBase.serialize(key));
                    if (value instanceof JsNameRef) {
                        JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                        Intrinsics.checkNotNull(value);
                        builderNewBuilder2.setExpression(jsAstSerializerBase2.serialize((JsExpression) value));
                    } else if (value instanceof String) {
                        JsAstSerializerBase jsAstSerializerBase3 = this.this$0;
                        Intrinsics.checkNotNull(value);
                        builderNewBuilder2.setValueStringId(jsAstSerializerBase3.serialize((String) value));
                    }
                    builderNewBuilder.addTag(builderNewBuilder2);
                }
                this.builder.setDocComment(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitBinaryExpression(JsBinaryOperation x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.BinaryOperation.Builder builderNewBuilder = JsAstProtoBuf.BinaryOperation.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression arg1 = x.getArg1();
                Intrinsics.checkNotNullExpressionValue(arg1, "getArg1(...)");
                builderNewBuilder.setLeft(jsAstSerializerBase.serialize(arg1));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsExpression arg2 = x.getArg2();
                Intrinsics.checkNotNullExpressionValue(arg2, "getArg2(...)");
                builderNewBuilder.setRight(jsAstSerializerBase2.serialize(arg2));
                JsAstSerializerBase jsAstSerializerBase3 = this.this$0;
                JsBinaryOperator operator = x.getOperator();
                Intrinsics.checkNotNullExpressionValue(operator, "getOperator(...)");
                builderNewBuilder.setType(jsAstSerializerBase3.map(operator));
                this.builder.setBinary(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitPrefixOperation(JsPrefixOperation x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setUnary(this.this$0.serializeUnary(x, false));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitPostfixOperation(JsPostfixOperation x) {
                Intrinsics.checkNotNullParameter(x, "x");
                this.builder.setUnary(this.this$0.serializeUnary(x, true));
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitConditional(JsConditional x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Conditional.Builder builderNewBuilder = JsAstProtoBuf.Conditional.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression testExpression = x.getTestExpression();
                Intrinsics.checkNotNullExpressionValue(testExpression, "getTestExpression(...)");
                builderNewBuilder.setTestExpression(jsAstSerializerBase.serialize(testExpression));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsExpression thenExpression = x.getThenExpression();
                Intrinsics.checkNotNullExpressionValue(thenExpression, "getThenExpression(...)");
                builderNewBuilder.setThenExpression(jsAstSerializerBase2.serialize(thenExpression));
                JsAstSerializerBase jsAstSerializerBase3 = this.this$0;
                JsExpression elseExpression = x.getElseExpression();
                Intrinsics.checkNotNullExpressionValue(elseExpression, "getElseExpression(...)");
                builderNewBuilder.setElseExpression(jsAstSerializerBase3.serialize(elseExpression));
                this.builder.setConditional(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitArrayAccess(JsArrayAccess x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.ArrayAccess.Builder builderNewBuilder = JsAstProtoBuf.ArrayAccess.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression arrayExpression = x.getArrayExpression();
                Intrinsics.checkNotNullExpressionValue(arrayExpression, "getArrayExpression(...)");
                builderNewBuilder.setArray(jsAstSerializerBase.serialize(arrayExpression));
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                JsExpression indexExpression = x.getIndexExpression();
                Intrinsics.checkNotNullExpressionValue(indexExpression, "getIndexExpression(...)");
                builderNewBuilder.setIndex(jsAstSerializerBase2.serialize(indexExpression));
                this.builder.setArrayAccess(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNameRef(JsNameRef nameRef) {
                Intrinsics.checkNotNullParameter(nameRef, "nameRef");
                JsName name2 = nameRef.getName();
                JsExpression qualifier = nameRef.getQualifier();
                if (name2 != null) {
                    if (qualifier != null || Intrinsics.areEqual((Object) MetadataProperties.isInline(nameRef), (Object) true)) {
                        JsAstProtoBuf.NameReference.Builder builderNewBuilder = JsAstProtoBuf.NameReference.newBuilder();
                        builderNewBuilder.setNameId(this.this$0.serialize(name2));
                        if (qualifier != null) {
                            builderNewBuilder.setQualifier(this.this$0.serialize(qualifier));
                        }
                        Boolean boolIsInline = MetadataProperties.isInline(nameRef);
                        if (boolIsInline != null) {
                            builderNewBuilder.setInlineStrategy(boolIsInline.booleanValue() ? JsAstProtoBuf.InlineStrategy.IN_PLACE : JsAstProtoBuf.InlineStrategy.NOT_INLINE);
                        }
                        this.builder.setNameReference(builderNewBuilder.build());
                        return;
                    }
                    this.builder.setSimpleNameReference(this.this$0.serialize(name2));
                    return;
                }
                JsAstProtoBuf.PropertyReference.Builder builderNewBuilder2 = JsAstProtoBuf.PropertyReference.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                String ident = nameRef.getIdent();
                Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
                builderNewBuilder2.setStringId(jsAstSerializerBase.serialize(ident));
                if (qualifier != null) {
                    builderNewBuilder2.setQualifier(this.this$0.serialize(qualifier));
                }
                Boolean boolIsInline2 = MetadataProperties.isInline(nameRef);
                if (boolIsInline2 != null) {
                    builderNewBuilder2.setInlineStrategy(boolIsInline2.booleanValue() ? JsAstProtoBuf.InlineStrategy.IN_PLACE : JsAstProtoBuf.InlineStrategy.NOT_INLINE);
                }
                this.builder.setPropertyReference(builderNewBuilder2.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) {
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                JsAstProtoBuf.Invocation.Builder builderNewBuilder = JsAstProtoBuf.Invocation.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression qualifier = invocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
                builderNewBuilder.setQualifier(jsAstSerializerBase.serialize(qualifier));
                List<JsExpression> arguments = invocation.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                for (JsExpression jsExpression : arguments) {
                    Intrinsics.checkNotNull(jsExpression);
                    builderNewBuilder.addArgument(jsAstSerializerBase2.serialize(jsExpression));
                }
                if (Intrinsics.areEqual((Object) MetadataProperties.isInline(invocation), (Object) true)) {
                    builderNewBuilder.setInlineStrategy(JsAstProtoBuf.InlineStrategy.IN_PLACE);
                }
                this.builder.setInvocation(builderNewBuilder.build());
            }

            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitNew(JsNew x) {
                Intrinsics.checkNotNullParameter(x, "x");
                JsAstProtoBuf.Instantiation.Builder builderNewBuilder = JsAstProtoBuf.Instantiation.newBuilder();
                JsAstSerializerBase jsAstSerializerBase = this.this$0;
                JsExpression constructorExpression = x.getConstructorExpression();
                Intrinsics.checkNotNullExpressionValue(constructorExpression, "getConstructorExpression(...)");
                builderNewBuilder.setQualifier(jsAstSerializerBase.serialize(constructorExpression));
                List<JsExpression> arguments = x.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                JsAstSerializerBase jsAstSerializerBase2 = this.this$0;
                for (JsExpression jsExpression : arguments) {
                    Intrinsics.checkNotNull(jsExpression);
                    builderNewBuilder.addArgument(jsAstSerializerBase2.serialize(jsExpression));
                }
                this.builder.setInstantiation(builderNewBuilder.build());
            }
        };
        JsExpression jsExpression = expression;
        List<JsComment> commentsBeforeNode = jsExpression.getCommentsBeforeNode();
        if (commentsBeforeNode != null) {
            Intrinsics.checkNotNull(commentsBeforeNode);
            for (JsComment jsComment : commentsBeforeNode) {
                Intrinsics.checkNotNull(jsComment);
                r0.getBuilder().addBeforeComments(serialize(jsComment));
            }
        }
        List<JsComment> commentsAfterNode = jsExpression.getCommentsAfterNode();
        if (commentsAfterNode != null) {
            Intrinsics.checkNotNull(commentsAfterNode);
            for (JsComment jsComment2 : commentsAfterNode) {
                Intrinsics.checkNotNull(jsComment2);
                r0.getBuilder().addAfterComments(serialize(jsComment2));
            }
        }
        JsLocation jsLocationExtractLocation = extractLocation(jsExpression);
        if (jsLocationExtractLocation != null) {
            String strPeek = getFileStack().peek();
            String file = jsLocationExtractLocation.getFile();
            boolean zAreEqual = Intrinsics.areEqual(strPeek, file);
            z = !zAreEqual;
            if (!zAreEqual) {
                r0.getBuilder().setFileId(serialize(file));
                getFileStack().push(jsLocationExtractLocation.getFile());
            }
            JsAstProtoBuf.Location.Builder builderNewBuilder = JsAstProtoBuf.Location.newBuilder();
            builderNewBuilder.setStartLine(jsLocationExtractLocation.getStartLine());
            builderNewBuilder.setStartChar(jsLocationExtractLocation.getStartChar());
            JsAstProtoBuf.Location locationBuild = builderNewBuilder.build();
            Intrinsics.checkNotNullExpressionValue(locationBuild, "build(...)");
            r0.getBuilder().setLocation(locationBuild);
        } else {
            z = false;
        }
        expression.accept((JsVisitor) r0);
        if (z) {
            getFileStack().pop();
        }
        JsAstProtoBuf.Expression.Builder builder = r0.getBuilder();
        JsExpression jsExpression2 = expression;
        builder.setSynthetic(MetadataProperties.getSynthetic(jsExpression2));
        builder.setSideEffects(map(MetadataProperties.getSideEffects(jsExpression2)));
        JsImportedModule localAlias = MetadataProperties.getLocalAlias(expression);
        if (localAlias != null) {
            builder.setLocalAlias(serialize(localAlias));
        }
        JsAstProtoBuf.Expression expressionBuild = r0.getBuilder().build();
        Intrinsics.checkNotNullExpressionValue(expressionBuild, "build(...)");
        return expressionBuild;
    }

    protected final JsAstProtoBuf.JsImportedModule serialize(JsImportedModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        JsAstProtoBuf.JsImportedModule.Builder builderNewBuilder = JsAstProtoBuf.JsImportedModule.newBuilder();
        builderNewBuilder.setExternalName(serialize(module.getExternalName()));
        builderNewBuilder.setInternalName(serialize(module.getInternalName()));
        JsExpression plainReference = module.getPlainReference();
        if (plainReference != null) {
            builderNewBuilder.setPlainReference(serialize(plainReference));
        }
        JsAstProtoBuf.JsImportedModule jsImportedModuleBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(jsImportedModuleBuild, "build(...)");
        return jsImportedModuleBuild;
    }

    protected final JsAstProtoBuf.Parameter serializeParameter(JsParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        JsAstProtoBuf.Parameter.Builder builderNewBuilder = JsAstProtoBuf.Parameter.newBuilder();
        JsName name2 = parameter.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        builderNewBuilder.setNameId(serialize(name2));
        if (MetadataProperties.getHasDefaultValue(parameter)) {
            builderNewBuilder.setHasDefaultValue(true);
        }
        JsAstProtoBuf.Parameter parameterBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(parameterBuild, "build(...)");
        return parameterBuild;
    }

    protected final JsAstProtoBuf.CompositeBlock serializeBlock(JsCompositeBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        JsAstProtoBuf.CompositeBlock.Builder builderNewBuilder = JsAstProtoBuf.CompositeBlock.newBuilder();
        for (JsStatement jsStatement : block.getStatements()) {
            Intrinsics.checkNotNull(jsStatement);
            builderNewBuilder.addStatement(serialize(jsStatement));
        }
        JsAstProtoBuf.CompositeBlock compositeBlockBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(compositeBlockBuild, "build(...)");
        return compositeBlockBuild;
    }

    protected final JsAstProtoBuf.Function serializeFunction(JsFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        JsAstProtoBuf.Function.Builder builderNewBuilder = JsAstProtoBuf.Function.newBuilder();
        List<JsParameter> parameters = function.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        for (JsParameter jsParameter : parameters) {
            Intrinsics.checkNotNull(jsParameter);
            builderNewBuilder.addParameter(serializeParameter(jsParameter));
        }
        Set<JsFunction.Modifier> modifiers = function.getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        for (JsFunction.Modifier modifier : modifiers) {
            Intrinsics.checkNotNull(modifier);
            builderNewBuilder.addModifier(map(modifier));
        }
        JsName name2 = function.getName();
        if (name2 != null) {
            builderNewBuilder.setNameId(serialize(name2));
        }
        JsBlock body = function.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        builderNewBuilder.setBody(serialize(body));
        if (MetadataProperties.isLocal(function)) {
            builderNewBuilder.setLocal(true);
        }
        JsAstProtoBuf.Function functionBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(functionBuild, "build(...)");
        return functionBuild;
    }

    protected final JsAstProtoBuf.Vars serializeVars(JsVars vars) {
        boolean z;
        Intrinsics.checkNotNullParameter(vars, "vars");
        JsAstProtoBuf.Vars.Builder builderNewBuilder = JsAstProtoBuf.Vars.newBuilder();
        for (JsVars.JsVar jsVar : vars.getVars()) {
            JsAstProtoBuf.VarDeclaration.Builder builderNewBuilder2 = JsAstProtoBuf.VarDeclaration.newBuilder();
            Intrinsics.checkNotNull(jsVar);
            JsLocation jsLocationExtractLocation = extractLocation(jsVar);
            if (jsLocationExtractLocation != null) {
                String strPeek = getFileStack().peek();
                String file = jsLocationExtractLocation.getFile();
                boolean zAreEqual = Intrinsics.areEqual(strPeek, file);
                z = !zAreEqual;
                if (!zAreEqual) {
                    builderNewBuilder2.setFileId(serialize(file));
                    getFileStack().push(jsLocationExtractLocation.getFile());
                }
                JsAstProtoBuf.Location.Builder builderNewBuilder3 = JsAstProtoBuf.Location.newBuilder();
                builderNewBuilder3.setStartLine(jsLocationExtractLocation.getStartLine());
                builderNewBuilder3.setStartChar(jsLocationExtractLocation.getStartChar());
                JsAstProtoBuf.Location locationBuild = builderNewBuilder3.build();
                Intrinsics.checkNotNullExpressionValue(locationBuild, "build(...)");
                builderNewBuilder2.setLocation(locationBuild);
            } else {
                z = false;
            }
            JsName name2 = jsVar.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            builderNewBuilder2.setNameId(serialize(name2));
            JsExpression initExpression = jsVar.getInitExpression();
            if (initExpression != null) {
                Intrinsics.checkNotNull(initExpression);
                builderNewBuilder2.setInitialValue(serialize(initExpression));
            }
            if (z) {
                getFileStack().pop();
            }
            builderNewBuilder.addDeclaration(builderNewBuilder2);
        }
        if (vars.isMultiline()) {
            builderNewBuilder.setMultiline(true);
        }
        String exportedPackage = MetadataProperties.getExportedPackage(vars);
        if (exportedPackage != null) {
            builderNewBuilder.setExportedPackageId(serialize(exportedPackage));
        }
        JsAstProtoBuf.Vars varsBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(varsBuild, "build(...)");
        return varsBuild;
    }

    protected final JsAstProtoBuf.UnaryOperation serializeUnary(JsUnaryOperation x, boolean postfix) {
        Intrinsics.checkNotNullParameter(x, "x");
        JsAstProtoBuf.UnaryOperation.Builder builderNewBuilder = JsAstProtoBuf.UnaryOperation.newBuilder();
        JsExpression arg = x.getArg();
        Intrinsics.checkNotNullExpressionValue(arg, "getArg(...)");
        builderNewBuilder.setOperand(serialize(arg));
        JsUnaryOperator operator = x.getOperator();
        Intrinsics.checkNotNullExpressionValue(operator, "getOperator(...)");
        builderNewBuilder.setType(map(operator));
        builderNewBuilder.setPostfix(postfix);
        JsAstProtoBuf.UnaryOperation unaryOperationBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(unaryOperationBuild, "build(...)");
        return unaryOperationBuild;
    }

    protected final JsAstProtoBuf.Function.Modifier map(JsFunction.Modifier modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        int i = WhenMappings.$EnumSwitchMapping$0[modifier.ordinal()];
        if (i == 1) {
            return JsAstProtoBuf.Function.Modifier.STATIC;
        }
        if (i == 2) {
            return JsAstProtoBuf.Function.Modifier.SET;
        }
        if (i == 3) {
            return JsAstProtoBuf.Function.Modifier.GET;
        }
        throw new NoWhenBranchMatchedException();
    }

    protected final JsAstProtoBuf.BinaryOperation.Type map(JsBinaryOperator op) {
        Intrinsics.checkNotNullParameter(op, "op");
        switch (WhenMappings.$EnumSwitchMapping$1[op.ordinal()]) {
            case 1:
                return JsAstProtoBuf.BinaryOperation.Type.MUL;
            case 2:
                return JsAstProtoBuf.BinaryOperation.Type.DIV;
            case 3:
                return JsAstProtoBuf.BinaryOperation.Type.MOD;
            case 4:
                return JsAstProtoBuf.BinaryOperation.Type.ADD;
            case 5:
                return JsAstProtoBuf.BinaryOperation.Type.SUB;
            case 6:
                return JsAstProtoBuf.BinaryOperation.Type.SHL;
            case 7:
                return JsAstProtoBuf.BinaryOperation.Type.SHR;
            case 8:
                return JsAstProtoBuf.BinaryOperation.Type.SHRU;
            case 9:
                return JsAstProtoBuf.BinaryOperation.Type.LT;
            case 10:
                return JsAstProtoBuf.BinaryOperation.Type.LTE;
            case 11:
                return JsAstProtoBuf.BinaryOperation.Type.GT;
            case 12:
                return JsAstProtoBuf.BinaryOperation.Type.GTE;
            case 13:
                return JsAstProtoBuf.BinaryOperation.Type.INSTANCEOF;
            case 14:
                return JsAstProtoBuf.BinaryOperation.Type.IN;
            case 15:
                return JsAstProtoBuf.BinaryOperation.Type.EQ;
            case 16:
                return JsAstProtoBuf.BinaryOperation.Type.NEQ;
            case 17:
                return JsAstProtoBuf.BinaryOperation.Type.REF_EQ;
            case 18:
                return JsAstProtoBuf.BinaryOperation.Type.REF_NEQ;
            case 19:
                return JsAstProtoBuf.BinaryOperation.Type.BIT_AND;
            case 20:
                return JsAstProtoBuf.BinaryOperation.Type.BIT_XOR;
            case 21:
                return JsAstProtoBuf.BinaryOperation.Type.BIT_OR;
            case 22:
                return JsAstProtoBuf.BinaryOperation.Type.AND;
            case 23:
                return JsAstProtoBuf.BinaryOperation.Type.OR;
            case 24:
                return JsAstProtoBuf.BinaryOperation.Type.ASG;
            case 25:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_ADD;
            case 26:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_SUB;
            case 27:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_MUL;
            case 28:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_DIV;
            case 29:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_MOD;
            case 30:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_SHL;
            case 31:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_SHR;
            case 32:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_SHRU;
            case 33:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_BIT_AND;
            case 34:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_BIT_OR;
            case 35:
                return JsAstProtoBuf.BinaryOperation.Type.ASG_BIT_XOR;
            case 36:
                return JsAstProtoBuf.BinaryOperation.Type.COMMA;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    protected final JsAstProtoBuf.UnaryOperation.Type map(JsUnaryOperator op) {
        Intrinsics.checkNotNullParameter(op, "op");
        switch (WhenMappings.$EnumSwitchMapping$2[op.ordinal()]) {
            case 1:
                return JsAstProtoBuf.UnaryOperation.Type.BIT_NOT;
            case 2:
                return JsAstProtoBuf.UnaryOperation.Type.DEC;
            case 3:
                return JsAstProtoBuf.UnaryOperation.Type.DELETE;
            case 4:
                return JsAstProtoBuf.UnaryOperation.Type.INC;
            case 5:
                return JsAstProtoBuf.UnaryOperation.Type.NEG;
            case 6:
                return JsAstProtoBuf.UnaryOperation.Type.POS;
            case 7:
                return JsAstProtoBuf.UnaryOperation.Type.NOT;
            case 8:
                return JsAstProtoBuf.UnaryOperation.Type.TYPEOF;
            case 9:
                return JsAstProtoBuf.UnaryOperation.Type.VOID;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    protected final JsAstProtoBuf.SideEffects map(SideEffectKind sideEffects) {
        Intrinsics.checkNotNullParameter(sideEffects, "sideEffects");
        int i = WhenMappings.$EnumSwitchMapping$3[sideEffects.ordinal()];
        if (i == 1) {
            return JsAstProtoBuf.SideEffects.AFFECTS_STATE;
        }
        if (i == 2) {
            return JsAstProtoBuf.SideEffects.DEPENDS_ON_STATE;
        }
        if (i == 3) {
            return JsAstProtoBuf.SideEffects.PURE;
        }
        throw new NoWhenBranchMatchedException();
    }

    protected final JsAstProtoBuf.SpecialFunction map(SpecialFunction specialFunction) {
        Intrinsics.checkNotNullParameter(specialFunction, "specialFunction");
        switch (WhenMappings.$EnumSwitchMapping$4[specialFunction.ordinal()]) {
            case 1:
                return JsAstProtoBuf.SpecialFunction.DEFINE_INLINE_FUNCTION;
            case 2:
                return JsAstProtoBuf.SpecialFunction.WRAP_FUNCTION;
            case 3:
                return JsAstProtoBuf.SpecialFunction.TO_BOXED_CHAR;
            case 4:
                return JsAstProtoBuf.SpecialFunction.UNBOX_CHAR;
            case 5:
                return JsAstProtoBuf.SpecialFunction.SUSPEND_CALL;
            case 6:
                return JsAstProtoBuf.SpecialFunction.COROUTINE_RESULT;
            case 7:
                return JsAstProtoBuf.SpecialFunction.COROUTINE_CONTROLLER;
            case 8:
                return JsAstProtoBuf.SpecialFunction.COROUTINE_RECEIVER;
            case 9:
                return JsAstProtoBuf.SpecialFunction.SET_COROUTINE_RESULT;
            case 10:
                return JsAstProtoBuf.SpecialFunction.GET_KCLASS;
            case 11:
                return JsAstProtoBuf.SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    protected final int serialize(JsName name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Map<JsName, Integer> map = this.nameMap;
        Integer numValueOf = map.get(name2);
        if (numValueOf == null) {
            JsAstProtoBuf.Name.Builder builderNewBuilder = JsAstProtoBuf.Name.newBuilder();
            String ident = name2.getIdent();
            Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
            builderNewBuilder.setIdentifier(serialize(ident));
            builderNewBuilder.setTemporary(name2.isTemporary());
            LocalAlias localAlias = MetadataProperties.getLocalAlias(name2);
            if (localAlias != null) {
                builderNewBuilder.setLocalNameId(serialize(localAlias));
            }
            if (MetadataProperties.getImported(name2) && !this.importedNames.contains(name2)) {
                builderNewBuilder.setImported(true);
            }
            SpecialFunction specialFunction = MetadataProperties.getSpecialFunction(name2);
            if (specialFunction != null) {
                builderNewBuilder.setSpecialFunction(map(specialFunction));
            }
            int entryCount = this.nameTableBuilder.getEntryCount();
            this.nameTableBuilder.addEntry(builderNewBuilder);
            numValueOf = Integer.valueOf(entryCount);
            map.put(name2, numValueOf);
        }
        return numValueOf.intValue();
    }

    protected final JsAstProtoBuf.LocalAlias serialize(LocalAlias alias) {
        Intrinsics.checkNotNullParameter(alias, "alias");
        JsAstProtoBuf.LocalAlias.Builder builderNewBuilder = JsAstProtoBuf.LocalAlias.newBuilder();
        builderNewBuilder.setLocalNameId(serialize(alias.getName()));
        String tag = alias.getTag();
        if (tag != null) {
            builderNewBuilder.setTag(serialize(tag));
        }
        JsAstProtoBuf.LocalAlias localAliasBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(localAliasBuild, "build(...)");
        return localAliasBuild;
    }

    protected final int serialize(String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        Map<String, Integer> map = this.stringMap;
        Integer numValueOf = map.get(string);
        if (numValueOf == null) {
            int entryCount = this.stringTableBuilder.getEntryCount();
            this.stringTableBuilder.addEntry(string);
            numValueOf = Integer.valueOf(entryCount);
            map.put(string, numValueOf);
        }
        return numValueOf.intValue();
    }

    protected final JsAstProtoBuf.Comment serialize(JsComment comment) {
        boolean z;
        Intrinsics.checkNotNullParameter(comment, "comment");
        JsAstProtoBuf.Comment.Builder builderNewBuilder = JsAstProtoBuf.Comment.newBuilder();
        builderNewBuilder.setText(comment.getText());
        if (comment instanceof JsSingleLineComment) {
            z = false;
        } else {
            if (!(comment instanceof JsMultiLineComment)) {
                throw new IllegalStateException(("Unknown type of comment " + comment.getClass().getName()).toString());
            }
            z = true;
        }
        builderNewBuilder.setMultiline(z);
        JsAstProtoBuf.Comment commentBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(commentBuild, "build(...)");
        return commentBuild;
    }
}
