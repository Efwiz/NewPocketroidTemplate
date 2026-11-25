package org.jetbrains.kotlin.serialization.js.ast;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
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
import org.jetbrains.kotlin.js.backend.ast.JsDefault;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsDocComment;
import org.jetbrains.kotlin.js.backend.ast.JsDoubleLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsDynamicScope;
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
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource;
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
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsSuperRef;
import org.jetbrains.kotlin.js.backend.ast.JsSwitch;
import org.jetbrains.kotlin.js.backend.ast.JsSwitchMember;
import org.jetbrains.kotlin.js.backend.ast.JsThisRef;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.backend.ast.metadata.LocalAlias;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;

/* JADX INFO: compiled from: JsAstDeserializerBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0082\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0004J\u0010\u0010\u0016\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u001bH\u0004J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0004J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u0018\u001a\u00020\"H\u0004J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0004J\u0010\u0010'\u001a\u00020(2\u0006\u0010\u0018\u001a\u00020)H\u0004J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0004J\u0010\u0010.\u001a\u00020\n2\u0006\u0010/\u001a\u000200H\u0004J\u0010\u00101\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0004J\u0010\u00101\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u001bH\u0004J\u0010\u00102\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0004J\u0010\u00102\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u001bH\u0004J\u0010\u00103\u001a\u0002042\u0006\u0010\u0018\u001a\u000205H\u0004J\u0010\u00106\u001a\u00020\u00052\u0006\u0010/\u001a\u000200H\u0004J\u0010\u00107\u001a\u0002082\u0006\u0010\u0018\u001a\u000209H\u0004J\u001a\u0010:\u001a\u0004\u0018\u00010;2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020\u0005H$J\u0010\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020BH\u0004J\u0010\u0010?\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0004J\u0010\u0010?\u001a\u00020F2\u0006\u0010G\u001a\u00020HH\u0004J\u0010\u0010?\u001a\u00020I2\u0006\u0010J\u001a\u00020KH\u0004J\u0010\u0010?\u001a\u00020L2\u0006\u0010M\u001a\u00020NH\u0004J\u0010\u0010?\u001a\u00020O2\u0006\u0010A\u001a\u00020PH\u0004J9\u0010Q\u001a\u0002HR\"\b\b\u0000\u0010R*\u00020S2\b\u0010T\u001a\u0004\u0018\u0001002\b\u0010U\u001a\u0004\u0018\u00010V2\f\u0010W\u001a\b\u0012\u0004\u0012\u0002HR0XH\u0004¢\u0006\u0002\u0010YR\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0012\u0010\u0010\u001a\u00020\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\f¨\u0006Z"}, d2 = {"Lorg/jetbrains/kotlin/serialization/js/ast/JsAstDeserializerBase;", "", "()V", "fileStack", "Ljava/util/Deque;", "", "getFileStack", "()Ljava/util/Deque;", "nameCache", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getNameCache", "()Ljava/util/List;", "nameTable", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Name;", "getNameTable", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "getScope", "()Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "stringTable", "getStringTable", "deserialize", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "proto", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Expression;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Statement;", "deserializeComment", "Lorg/jetbrains/kotlin/js/backend/ast/JsComment;", "comment", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Comment;", "deserializeCompositeBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$CompositeBlock;", "deserializeFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "functionProto", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Function;", "deserializeJsImportedModule", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$JsImportedModule;", "deserializeLocalAlias", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "localNameId", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$LocalAlias;", "deserializeName", "id", "", "deserializeNoMetadata", "deserializeNoMetadataHelper", "deserializeParameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Parameter;", "deserializeString", "deserializeVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Vars;", "embedSources", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocationWithSource;", "deserializedLocation", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "file", "map", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "op", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$BinaryOperation$Type;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction$Modifier;", "modifier", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Function$Modifier;", "", "inlineStrategy", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$InlineStrategy;", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;", "sideEffects", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$SideEffects;", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "specialFunction", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$SpecialFunction;", "Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperator;", "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$UnaryOperation$Type;", "withLocation", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "fileId", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Location;", "action", "Lkotlin/Function0;", "(Ljava/lang/Integer;Lorg/jetbrains/kotlin/serialization/js/ast/JsAstProtoBuf$Location;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "js.serializer"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JsAstDeserializerBase {
    private final List<String> stringTable = new ArrayList();
    private final List<JsAstProtoBuf.Name> nameTable = new ArrayList();
    private final List<JsName> nameCache = new ArrayList();
    private final Deque<String> fileStack = new ArrayDeque();

    /* JADX INFO: compiled from: JsAstDeserializerBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;
        public static final /* synthetic */ int[] $EnumSwitchMapping$4;
        public static final /* synthetic */ int[] $EnumSwitchMapping$5;
        public static final /* synthetic */ int[] $EnumSwitchMapping$6;

        static {
            int[] iArr = new int[JsAstProtoBuf.Statement.StatementCase.values().length];
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.RETURN_STATEMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.THROW_STATEMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.BREAK_STATEMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.CONTINUE_STATEMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.DEBUGGER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.EXPRESSION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.VARS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.BLOCK.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.COMPOSITE_BLOCK.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.LABEL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.IF_STATEMENT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.SWITCH_STATEMENT.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.WHILE_STATEMENT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.DO_WHILE_STATEMENT.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.FOR_STATEMENT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.FOR_IN_STATEMENT.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.TRY_STATEMENT.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.EMPTY.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.SINGLE_LINE_COMMENT.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.MULTI_LINE_COMMENT.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[JsAstProtoBuf.Statement.StatementCase.STATEMENT_NOT_SET.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[JsAstProtoBuf.Expression.ExpressionCase.values().length];
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.THIS_LITERAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.SUPER_LITERAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.NULL_LITERAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.TRUE_LITERAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.FALSE_LITERAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.STRING_LITERAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.INT_LITERAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.DOUBLE_LITERAL.ordinal()] = 8;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.SIMPLE_NAME_REFERENCE.ordinal()] = 9;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.REG_EXP_LITERAL.ordinal()] = 10;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.ARRAY_LITERAL.ordinal()] = 11;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.OBJECT_LITERAL.ordinal()] = 12;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.CLASS.ordinal()] = 13;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.FUNCTION.ordinal()] = 14;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.DOC_COMMENT.ordinal()] = 15;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.BINARY.ordinal()] = 16;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.UNARY.ordinal()] = 17;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.CONDITIONAL.ordinal()] = 18;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.ARRAY_ACCESS.ordinal()] = 19;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.NAME_REFERENCE.ordinal()] = 20;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.PROPERTY_REFERENCE.ordinal()] = 21;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.INVOCATION.ordinal()] = 22;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.INSTANTIATION.ordinal()] = 23;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr2[JsAstProtoBuf.Expression.ExpressionCase.EXPRESSION_NOT_SET.ordinal()] = 24;
            } catch (NoSuchFieldError unused45) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[JsAstProtoBuf.Function.Modifier.values().length];
            try {
                iArr3[JsAstProtoBuf.Function.Modifier.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr3[JsAstProtoBuf.Function.Modifier.SET.ordinal()] = 2;
            } catch (NoSuchFieldError unused47) {
            }
            try {
                iArr3[JsAstProtoBuf.Function.Modifier.GET.ordinal()] = 3;
            } catch (NoSuchFieldError unused48) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[JsAstProtoBuf.BinaryOperation.Type.values().length];
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.MUL.ordinal()] = 1;
            } catch (NoSuchFieldError unused49) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.DIV.ordinal()] = 2;
            } catch (NoSuchFieldError unused50) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.MOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused51) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ADD.ordinal()] = 4;
            } catch (NoSuchFieldError unused52) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.SUB.ordinal()] = 5;
            } catch (NoSuchFieldError unused53) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.SHL.ordinal()] = 6;
            } catch (NoSuchFieldError unused54) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.SHR.ordinal()] = 7;
            } catch (NoSuchFieldError unused55) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.SHRU.ordinal()] = 8;
            } catch (NoSuchFieldError unused56) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.LT.ordinal()] = 9;
            } catch (NoSuchFieldError unused57) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.LTE.ordinal()] = 10;
            } catch (NoSuchFieldError unused58) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.GT.ordinal()] = 11;
            } catch (NoSuchFieldError unused59) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.GTE.ordinal()] = 12;
            } catch (NoSuchFieldError unused60) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.INSTANCEOF.ordinal()] = 13;
            } catch (NoSuchFieldError unused61) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.IN.ordinal()] = 14;
            } catch (NoSuchFieldError unused62) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.EQ.ordinal()] = 15;
            } catch (NoSuchFieldError unused63) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.NEQ.ordinal()] = 16;
            } catch (NoSuchFieldError unused64) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.REF_EQ.ordinal()] = 17;
            } catch (NoSuchFieldError unused65) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.REF_NEQ.ordinal()] = 18;
            } catch (NoSuchFieldError unused66) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.BIT_AND.ordinal()] = 19;
            } catch (NoSuchFieldError unused67) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.BIT_XOR.ordinal()] = 20;
            } catch (NoSuchFieldError unused68) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.BIT_OR.ordinal()] = 21;
            } catch (NoSuchFieldError unused69) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.AND.ordinal()] = 22;
            } catch (NoSuchFieldError unused70) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.OR.ordinal()] = 23;
            } catch (NoSuchFieldError unused71) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG.ordinal()] = 24;
            } catch (NoSuchFieldError unused72) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_ADD.ordinal()] = 25;
            } catch (NoSuchFieldError unused73) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_SUB.ordinal()] = 26;
            } catch (NoSuchFieldError unused74) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_MUL.ordinal()] = 27;
            } catch (NoSuchFieldError unused75) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_DIV.ordinal()] = 28;
            } catch (NoSuchFieldError unused76) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_MOD.ordinal()] = 29;
            } catch (NoSuchFieldError unused77) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_SHL.ordinal()] = 30;
            } catch (NoSuchFieldError unused78) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_SHR.ordinal()] = 31;
            } catch (NoSuchFieldError unused79) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_SHRU.ordinal()] = 32;
            } catch (NoSuchFieldError unused80) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_BIT_AND.ordinal()] = 33;
            } catch (NoSuchFieldError unused81) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_BIT_OR.ordinal()] = 34;
            } catch (NoSuchFieldError unused82) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.ASG_BIT_XOR.ordinal()] = 35;
            } catch (NoSuchFieldError unused83) {
            }
            try {
                iArr4[JsAstProtoBuf.BinaryOperation.Type.COMMA.ordinal()] = 36;
            } catch (NoSuchFieldError unused84) {
            }
            $EnumSwitchMapping$3 = iArr4;
            int[] iArr5 = new int[JsAstProtoBuf.UnaryOperation.Type.values().length];
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.BIT_NOT.ordinal()] = 1;
            } catch (NoSuchFieldError unused85) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.DEC.ordinal()] = 2;
            } catch (NoSuchFieldError unused86) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused87) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.INC.ordinal()] = 4;
            } catch (NoSuchFieldError unused88) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.NEG.ordinal()] = 5;
            } catch (NoSuchFieldError unused89) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.POS.ordinal()] = 6;
            } catch (NoSuchFieldError unused90) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.NOT.ordinal()] = 7;
            } catch (NoSuchFieldError unused91) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.TYPEOF.ordinal()] = 8;
            } catch (NoSuchFieldError unused92) {
            }
            try {
                iArr5[JsAstProtoBuf.UnaryOperation.Type.VOID.ordinal()] = 9;
            } catch (NoSuchFieldError unused93) {
            }
            $EnumSwitchMapping$4 = iArr5;
            int[] iArr6 = new int[JsAstProtoBuf.SideEffects.values().length];
            try {
                iArr6[JsAstProtoBuf.SideEffects.AFFECTS_STATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused94) {
            }
            try {
                iArr6[JsAstProtoBuf.SideEffects.DEPENDS_ON_STATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused95) {
            }
            try {
                iArr6[JsAstProtoBuf.SideEffects.PURE.ordinal()] = 3;
            } catch (NoSuchFieldError unused96) {
            }
            $EnumSwitchMapping$5 = iArr6;
            int[] iArr7 = new int[JsAstProtoBuf.SpecialFunction.values().length];
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.DEFINE_INLINE_FUNCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused97) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.WRAP_FUNCTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused98) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.TO_BOXED_CHAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused99) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.UNBOX_CHAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused100) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.SUSPEND_CALL.ordinal()] = 5;
            } catch (NoSuchFieldError unused101) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.COROUTINE_RESULT.ordinal()] = 6;
            } catch (NoSuchFieldError unused102) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.COROUTINE_CONTROLLER.ordinal()] = 7;
            } catch (NoSuchFieldError unused103) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.COROUTINE_RECEIVER.ordinal()] = 8;
            } catch (NoSuchFieldError unused104) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.SET_COROUTINE_RESULT.ordinal()] = 9;
            } catch (NoSuchFieldError unused105) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.GET_KCLASS.ordinal()] = 10;
            } catch (NoSuchFieldError unused106) {
            }
            try {
                iArr7[JsAstProtoBuf.SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE.ordinal()] = 11;
            } catch (NoSuchFieldError unused107) {
            }
            $EnumSwitchMapping$6 = iArr7;
        }
    }

    protected abstract JsLocationWithSource embedSources(JsLocation deserializedLocation, String file);

    public abstract JsScope getScope();

    protected final List<String> getStringTable() {
        return this.stringTable;
    }

    protected final List<JsAstProtoBuf.Name> getNameTable() {
        return this.nameTable;
    }

    protected final List<JsName> getNameCache() {
        return this.nameCache;
    }

    protected final Deque<String> getFileStack() {
        return this.fileStack;
    }

    protected final JsStatement deserialize(final JsAstProtoBuf.Statement proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsStatement jsStatement = (JsStatement) withLocation(proto.hasFileId() ? Integer.valueOf(proto.getFileId()) : null, proto.hasLocation() ? proto.getLocation() : null, new Function0<JsStatement>() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializerBase$deserialize$statement$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsStatement invoke() {
                return this.this$0.deserializeNoMetadata(proto);
            }
        });
        if (jsStatement instanceof HasMetadata) {
            MetadataProperties.setSynthetic((HasMetadata) jsStatement, proto.getSynthetic());
        }
        return jsStatement;
    }

    protected final JsStatement deserializeNoMetadata(JsAstProtoBuf.Statement proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsStatement jsStatementDeserializeNoMetadataHelper = deserializeNoMetadataHelper(proto);
        if (proto.getBeforeCommentsCount() != 0) {
            List<JsAstProtoBuf.Comment> beforeCommentsList = proto.getBeforeCommentsList();
            Intrinsics.checkNotNullExpressionValue(beforeCommentsList, "getBeforeCommentsList(...)");
            List<JsAstProtoBuf.Comment> list = beforeCommentsList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(deserializeComment((JsAstProtoBuf.Comment) it.next()));
            }
            jsStatementDeserializeNoMetadataHelper.setCommentsBeforeNode(arrayList);
        }
        if (proto.getAfterCommentsCount() != 0) {
            List<JsAstProtoBuf.Comment> afterCommentsList = proto.getAfterCommentsList();
            Intrinsics.checkNotNullExpressionValue(afterCommentsList, "getAfterCommentsList(...)");
            List<JsAstProtoBuf.Comment> list2 = afterCommentsList;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList2.add(deserializeComment((JsAstProtoBuf.Comment) it2.next()));
            }
            jsStatementDeserializeNoMetadataHelper.setCommentsAfterNode(arrayList2);
        }
        return jsStatementDeserializeNoMetadataHelper;
    }

    protected final JsStatement deserializeNoMetadataHelper(JsAstProtoBuf.Statement proto) {
        JsVars jsVars;
        JsExpression jsExpressionDeserialize;
        JsExpression jsExpressionDeserialize2;
        JsFor jsFor;
        JsCatch jsCatch;
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsAstProtoBuf.Statement.StatementCase statementCase = proto.getStatementCase();
        JsExpression jsExpressionDeserialize3 = null;
        JsBlock jsBlock = null;
        JsExpression jsExpressionDeserialize4 = null;
        JsExpression jsExpressionDeserialize5 = null;
        JsStatement jsStatementDeserialize = null;
        switch (statementCase == null ? -1 : WhenMappings.$EnumSwitchMapping$0[statementCase.ordinal()]) {
            case -1:
            case 21:
                throw new IllegalStateException("Statement not set".toString());
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                JsAstProtoBuf.Return returnStatement = proto.getReturnStatement();
                if (returnStatement.hasValue()) {
                    JsAstProtoBuf.Expression value = returnStatement.getValue();
                    Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                    jsExpressionDeserialize3 = deserialize(value);
                }
                return new JsReturn(jsExpressionDeserialize3);
            case 2:
                JsAstProtoBuf.Expression exception = proto.getThrowStatement().getException();
                Intrinsics.checkNotNullExpressionValue(exception, "getException(...)");
                return new JsThrow(deserialize(exception));
            case 3:
                JsAstProtoBuf.Break breakStatement = proto.getBreakStatement();
                return new JsBreak(breakStatement.hasLabelId() ? new JsNameRef(deserializeName(breakStatement.getLabelId())) : null);
            case 4:
                JsAstProtoBuf.Continue continueStatement = proto.getContinueStatement();
                return new JsContinue(continueStatement.hasLabelId() ? new JsNameRef(deserializeName(continueStatement.getLabelId())) : null);
            case 5:
                return new JsDebugger();
            case 6:
                JsAstProtoBuf.ExpressionStatement expression = proto.getExpression();
                JsAstProtoBuf.Expression expression2 = expression.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression2, "getExpression(...)");
                JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(deserialize(expression2));
                if (expression.hasExportedTagId()) {
                    MetadataProperties.setExportedTag(jsExpressionStatement, deserializeString(expression.getExportedTagId()));
                }
                return jsExpressionStatement;
            case 7:
                JsAstProtoBuf.Vars vars = proto.getVars();
                Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
                return deserializeVars(vars);
            case 8:
                JsAstProtoBuf.Block block = proto.getBlock();
                JsBlock jsBlock2 = new JsBlock();
                List<JsStatement> statements = jsBlock2.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                List<JsStatement> list = statements;
                List<JsAstProtoBuf.Statement> statementList = block.getStatementList();
                Intrinsics.checkNotNullExpressionValue(statementList, "getStatementList(...)");
                List<JsAstProtoBuf.Statement> list2 = statementList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                for (JsAstProtoBuf.Statement statement : list2) {
                    Intrinsics.checkNotNull(statement);
                    arrayList.add(deserialize(statement));
                }
                CollectionsKt.addAll(list, arrayList);
                return jsBlock2;
            case 9:
                JsAstProtoBuf.CompositeBlock compositeBlock = proto.getCompositeBlock();
                Intrinsics.checkNotNullExpressionValue(compositeBlock, "getCompositeBlock(...)");
                return deserializeCompositeBlock(compositeBlock);
            case 10:
                JsAstProtoBuf.Label label = proto.getLabel();
                JsName jsNameDeserializeName = deserializeName(label.getNameId());
                JsAstProtoBuf.Statement innerStatement = label.getInnerStatement();
                Intrinsics.checkNotNullExpressionValue(innerStatement, "getInnerStatement(...)");
                return new JsLabel(jsNameDeserializeName, deserialize(innerStatement));
            case 11:
                JsAstProtoBuf.If ifStatement = proto.getIfStatement();
                JsAstProtoBuf.Expression condition = ifStatement.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition, "getCondition(...)");
                JsExpression jsExpressionDeserialize6 = deserialize(condition);
                JsAstProtoBuf.Statement thenStatement = ifStatement.getThenStatement();
                Intrinsics.checkNotNullExpressionValue(thenStatement, "getThenStatement(...)");
                JsStatement jsStatementDeserialize2 = deserialize(thenStatement);
                if (ifStatement.hasElseStatement()) {
                    JsAstProtoBuf.Statement elseStatement = ifStatement.getElseStatement();
                    Intrinsics.checkNotNullExpressionValue(elseStatement, "getElseStatement(...)");
                    jsStatementDeserialize = deserialize(elseStatement);
                }
                return new JsIf(jsExpressionDeserialize6, jsStatementDeserialize2, jsStatementDeserialize);
            case 12:
                JsAstProtoBuf.Switch switchStatement = proto.getSwitchStatement();
                JsAstProtoBuf.Expression expression3 = switchStatement.getExpression();
                Intrinsics.checkNotNullExpressionValue(expression3, "getExpression(...)");
                JsExpression jsExpressionDeserialize7 = deserialize(expression3);
                List<JsAstProtoBuf.SwitchEntry> entryList = switchStatement.getEntryList();
                Intrinsics.checkNotNullExpressionValue(entryList, "getEntryList(...)");
                List<JsAstProtoBuf.SwitchEntry> list3 = entryList;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                for (final JsAstProtoBuf.SwitchEntry switchEntry : list3) {
                    JsSwitchMember jsSwitchMember = (JsSwitchMember) withLocation(switchEntry.hasFileId() ? Integer.valueOf(switchEntry.getFileId()) : null, switchEntry.hasLocation() ? switchEntry.getLocation() : null, new Function0<JsSwitchMember>() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializerBase$deserializeNoMetadataHelper$3$member$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final JsSwitchMember invoke() {
                            if (switchEntry.hasLabel()) {
                                JsCase jsCase = new JsCase();
                                JsAstDeserializerBase jsAstDeserializerBase = this;
                                JsAstProtoBuf.Expression label2 = switchEntry.getLabel();
                                Intrinsics.checkNotNullExpressionValue(label2, "getLabel(...)");
                                jsCase.setCaseExpression(jsAstDeserializerBase.deserialize(label2));
                                return jsCase;
                            }
                            return new JsDefault();
                        }
                    });
                    List<JsStatement> statements2 = jsSwitchMember.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    List<JsStatement> list4 = statements2;
                    List<JsAstProtoBuf.Statement> statementList2 = switchEntry.getStatementList();
                    Intrinsics.checkNotNullExpressionValue(statementList2, "getStatementList(...)");
                    List<JsAstProtoBuf.Statement> list5 = statementList2;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                    for (JsAstProtoBuf.Statement statement2 : list5) {
                        Intrinsics.checkNotNull(statement2);
                        arrayList3.add(deserialize(statement2));
                    }
                    CollectionsKt.addAll(list4, arrayList3);
                    arrayList2.add(jsSwitchMember);
                }
                return new JsSwitch(jsExpressionDeserialize7, arrayList2);
            case 13:
                JsAstProtoBuf.While whileStatement = proto.getWhileStatement();
                JsAstProtoBuf.Expression condition2 = whileStatement.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition2, "getCondition(...)");
                JsExpression jsExpressionDeserialize8 = deserialize(condition2);
                JsAstProtoBuf.Statement body = whileStatement.getBody();
                Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
                return new JsWhile(jsExpressionDeserialize8, deserialize(body));
            case 14:
                JsAstProtoBuf.DoWhile doWhileStatement = proto.getDoWhileStatement();
                JsAstProtoBuf.Expression condition3 = doWhileStatement.getCondition();
                Intrinsics.checkNotNullExpressionValue(condition3, "getCondition(...)");
                JsExpression jsExpressionDeserialize9 = deserialize(condition3);
                JsAstProtoBuf.Statement body2 = doWhileStatement.getBody();
                Intrinsics.checkNotNullExpressionValue(body2, "getBody(...)");
                return new JsDoWhile(jsExpressionDeserialize9, deserialize(body2));
            case 15:
                JsAstProtoBuf.For forStatement = proto.getForStatement();
                if (forStatement.hasVariables()) {
                    JsAstProtoBuf.Statement variables = forStatement.getVariables();
                    Intrinsics.checkNotNullExpressionValue(variables, "getVariables(...)");
                    JsStatement jsStatementDeserialize3 = deserialize(variables);
                    Intrinsics.checkNotNull(jsStatementDeserialize3, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsVars");
                    jsVars = (JsVars) jsStatementDeserialize3;
                } else {
                    jsVars = null;
                }
                if (forStatement.hasExpression()) {
                    JsAstProtoBuf.Expression expression4 = forStatement.getExpression();
                    Intrinsics.checkNotNullExpressionValue(expression4, "getExpression(...)");
                    jsExpressionDeserialize = deserialize(expression4);
                } else {
                    jsExpressionDeserialize = null;
                }
                if (forStatement.hasCondition()) {
                    JsAstProtoBuf.Expression condition4 = forStatement.getCondition();
                    Intrinsics.checkNotNullExpressionValue(condition4, "getCondition(...)");
                    jsExpressionDeserialize2 = deserialize(condition4);
                } else {
                    jsExpressionDeserialize2 = null;
                }
                if (forStatement.hasIncrement()) {
                    JsAstProtoBuf.Expression increment = forStatement.getIncrement();
                    Intrinsics.checkNotNullExpressionValue(increment, "getIncrement(...)");
                    jsExpressionDeserialize5 = deserialize(increment);
                }
                JsAstProtoBuf.Statement body3 = forStatement.getBody();
                Intrinsics.checkNotNullExpressionValue(body3, "getBody(...)");
                JsStatement jsStatementDeserialize4 = deserialize(body3);
                if (jsVars != null) {
                    jsFor = new JsFor(jsVars, jsExpressionDeserialize2, jsExpressionDeserialize5, jsStatementDeserialize4);
                } else {
                    jsFor = new JsFor(jsExpressionDeserialize, jsExpressionDeserialize2, jsExpressionDeserialize5, jsStatementDeserialize4);
                }
                return jsFor;
            case 16:
                JsAstProtoBuf.ForIn forInStatement = proto.getForInStatement();
                JsName jsNameDeserializeName2 = forInStatement.hasNameId() ? deserializeName(forInStatement.getNameId()) : null;
                if (forInStatement.hasExpression()) {
                    JsAstProtoBuf.Expression expression5 = forInStatement.getExpression();
                    Intrinsics.checkNotNullExpressionValue(expression5, "getExpression(...)");
                    jsExpressionDeserialize4 = deserialize(expression5);
                }
                JsAstProtoBuf.Expression iterable = forInStatement.getIterable();
                Intrinsics.checkNotNullExpressionValue(iterable, "getIterable(...)");
                JsExpression jsExpressionDeserialize10 = deserialize(iterable);
                JsAstProtoBuf.Statement body4 = forInStatement.getBody();
                Intrinsics.checkNotNullExpressionValue(body4, "getBody(...)");
                return new JsForIn(jsNameDeserializeName2, jsExpressionDeserialize4, jsExpressionDeserialize10, deserialize(body4));
            case 17:
                JsAstProtoBuf.Try tryStatement = proto.getTryStatement();
                JsAstProtoBuf.Statement tryBlock = tryStatement.getTryBlock();
                Intrinsics.checkNotNullExpressionValue(tryBlock, "getTryBlock(...)");
                JsStatement jsStatementDeserialize5 = deserialize(tryBlock);
                Intrinsics.checkNotNull(jsStatementDeserialize5, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsBlock");
                JsBlock jsBlock3 = (JsBlock) jsStatementDeserialize5;
                if (tryStatement.hasCatchBlock()) {
                    JsAstProtoBuf.Catch catchBlock = tryStatement.getCatchBlock();
                    jsCatch = new JsCatch(deserializeName(catchBlock.getParameter().getNameId()));
                    JsAstProtoBuf.Statement body5 = catchBlock.getBody();
                    Intrinsics.checkNotNullExpressionValue(body5, "getBody(...)");
                    JsStatement jsStatementDeserialize6 = deserialize(body5);
                    Intrinsics.checkNotNull(jsStatementDeserialize6, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsBlock");
                    jsCatch.setBody((JsBlock) jsStatementDeserialize6);
                } else {
                    jsCatch = null;
                }
                if (tryStatement.hasFinallyBlock()) {
                    JsAstProtoBuf.Statement finallyBlock = tryStatement.getFinallyBlock();
                    Intrinsics.checkNotNullExpressionValue(finallyBlock, "getFinallyBlock(...)");
                    JsStatement jsStatementDeserialize7 = deserialize(finallyBlock);
                    Intrinsics.checkNotNull(jsStatementDeserialize7, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsBlock");
                    jsBlock = (JsBlock) jsStatementDeserialize7;
                }
                return new JsTry(jsBlock3, jsCatch, jsBlock);
            case 18:
                return JsEmpty.INSTANCE;
            case 19:
                String message = proto.getSingleLineComment().getMessage();
                Intrinsics.checkNotNullExpressionValue(message, "getMessage(...)");
                return new JsSingleLineComment(message);
            case 20:
                String message2 = proto.getMultiLineComment().getMessage();
                Intrinsics.checkNotNullExpressionValue(message2, "getMessage(...)");
                return new JsMultiLineComment(message2);
        }
    }

    protected final JsExpression deserialize(final JsAstProtoBuf.Expression proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsExpression jsExpression = (JsExpression) withLocation(proto.hasFileId() ? Integer.valueOf(proto.getFileId()) : null, proto.hasLocation() ? proto.getLocation() : null, new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializerBase$deserialize$expression$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return this.this$0.deserializeNoMetadata(proto);
            }
        });
        JsExpression jsExpression2 = jsExpression;
        MetadataProperties.setSynthetic(jsExpression2, proto.getSynthetic());
        JsAstProtoBuf.SideEffects sideEffects = proto.getSideEffects();
        Intrinsics.checkNotNullExpressionValue(sideEffects, "getSideEffects(...)");
        MetadataProperties.setSideEffects(jsExpression2, map(sideEffects));
        if (proto.hasLocalAlias()) {
            JsAstProtoBuf.JsImportedModule localAlias = proto.getLocalAlias();
            Intrinsics.checkNotNullExpressionValue(localAlias, "getLocalAlias(...)");
            MetadataProperties.setLocalAlias(jsExpression, deserializeJsImportedModule(localAlias));
        }
        return jsExpression;
    }

    protected final JsImportedModule deserializeJsImportedModule(JsAstProtoBuf.JsImportedModule proto) {
        JsExpression jsExpressionDeserialize;
        Intrinsics.checkNotNullParameter(proto, "proto");
        String strDeserializeString = deserializeString(proto.getExternalName());
        JsName jsNameDeserializeName = deserializeName(proto.getInternalName());
        if (proto.hasPlainReference()) {
            JsAstProtoBuf.Expression plainReference = proto.getPlainReference();
            Intrinsics.checkNotNull(plainReference);
            jsExpressionDeserialize = deserialize(plainReference);
        } else {
            jsExpressionDeserialize = null;
        }
        return new JsImportedModule(strDeserializeString, jsNameDeserializeName, jsExpressionDeserialize, null, 8, null);
    }

    protected final JsExpression deserializeNoMetadata(JsAstProtoBuf.Expression proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsExpression jsExpressionDeserializeNoMetadataHelper = deserializeNoMetadataHelper(proto);
        if (proto.getBeforeCommentsCount() != 0) {
            List<JsAstProtoBuf.Comment> beforeCommentsList = proto.getBeforeCommentsList();
            Intrinsics.checkNotNullExpressionValue(beforeCommentsList, "getBeforeCommentsList(...)");
            List<JsAstProtoBuf.Comment> list = beforeCommentsList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(deserializeComment((JsAstProtoBuf.Comment) it.next()));
            }
            jsExpressionDeserializeNoMetadataHelper.setCommentsBeforeNode(arrayList);
        }
        if (proto.getAfterCommentsCount() != 0) {
            List<JsAstProtoBuf.Comment> afterCommentsList = proto.getAfterCommentsList();
            Intrinsics.checkNotNullExpressionValue(afterCommentsList, "getAfterCommentsList(...)");
            List<JsAstProtoBuf.Comment> list2 = afterCommentsList;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList2.add(deserializeComment((JsAstProtoBuf.Comment) it2.next()));
            }
            jsExpressionDeserializeNoMetadataHelper.setCommentsAfterNode(arrayList2);
        }
        return jsExpressionDeserializeNoMetadataHelper;
    }

    protected final JsExpression deserializeNoMetadataHelper(JsAstProtoBuf.Expression proto) {
        JsNameRef jsNameRef;
        Object objDeserializeString;
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsAstProtoBuf.Expression.ExpressionCase expressionCase = proto.getExpressionCase();
        JsFunction jsFunctionDeserializeFunction = null;
        JsExpression jsExpressionDeserialize = null;
        JsExpression jsExpressionDeserialize2 = null;
        switch (expressionCase == null ? -1 : WhenMappings.$EnumSwitchMapping$1[expressionCase.ordinal()]) {
            case -1:
            case 24:
                throw new IllegalStateException("Unknown expression".toString());
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                return new JsThisRef();
            case 2:
                return new JsSuperRef();
            case 3:
                return new JsNullLiteral();
            case 4:
                return new JsBooleanLiteral(true);
            case 5:
                return new JsBooleanLiteral(false);
            case 6:
                return new JsStringLiteral(deserializeString(proto.getStringLiteral()));
            case 7:
                return new JsIntLiteral(proto.getIntLiteral());
            case 8:
                return new JsDoubleLiteral(proto.getDoubleLiteral());
            case 9:
                return new JsNameRef(deserializeName(proto.getSimpleNameReference()));
            case 10:
                JsAstProtoBuf.RegExpLiteral regExpLiteral = proto.getRegExpLiteral();
                JsRegExp jsRegExp = new JsRegExp();
                jsRegExp.setPattern(deserializeString(regExpLiteral.getPatternStringId()));
                if (regExpLiteral.hasFlagsStringId()) {
                    jsRegExp.setFlags(deserializeString(regExpLiteral.getFlagsStringId()));
                }
                return jsRegExp;
            case 11:
                List<JsAstProtoBuf.Expression> elementList = proto.getArrayLiteral().getElementList();
                Intrinsics.checkNotNullExpressionValue(elementList, "getElementList(...)");
                List<JsAstProtoBuf.Expression> list = elementList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (JsAstProtoBuf.Expression expression : list) {
                    Intrinsics.checkNotNull(expression);
                    arrayList.add(deserialize(expression));
                }
                return new JsArrayLiteral(arrayList);
            case 12:
                JsAstProtoBuf.ObjectLiteral objectLiteral = proto.getObjectLiteral();
                List<JsAstProtoBuf.ObjectLiteralEntry> entryList = objectLiteral.getEntryList();
                Intrinsics.checkNotNullExpressionValue(entryList, "getEntryList(...)");
                List<JsAstProtoBuf.ObjectLiteralEntry> list2 = entryList;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                for (JsAstProtoBuf.ObjectLiteralEntry objectLiteralEntry : list2) {
                    JsAstProtoBuf.Expression key = objectLiteralEntry.getKey();
                    Intrinsics.checkNotNullExpressionValue(key, "getKey(...)");
                    JsExpression jsExpressionDeserialize3 = deserialize(key);
                    JsAstProtoBuf.Expression value = objectLiteralEntry.getValue();
                    Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
                    arrayList2.add(new JsPropertyInitializer(jsExpressionDeserialize3, deserialize(value)));
                }
                return new JsObjectLiteral(arrayList2, objectLiteral.getMultiline());
            case 13:
                JsAstProtoBuf.Class class_ = proto.getClass_();
                JsName jsNameDeserializeName = class_.hasNameId() ? deserializeName(class_.getNameId()) : null;
                if (class_.hasSuperExpression()) {
                    JsAstProtoBuf.Expression superExpression = class_.getSuperExpression();
                    Intrinsics.checkNotNullExpressionValue(superExpression, "getSuperExpression(...)");
                    JsExpression jsExpressionDeserialize4 = deserialize(superExpression);
                    Intrinsics.checkNotNull(jsExpressionDeserialize4, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsNameRef");
                    jsNameRef = (JsNameRef) jsExpressionDeserialize4;
                } else {
                    jsNameRef = null;
                }
                JsNameRef jsNameRef2 = jsNameRef;
                if (class_.hasConstructor()) {
                    JsAstProtoBuf.Function constructor = class_.getConstructor();
                    Intrinsics.checkNotNullExpressionValue(constructor, "getConstructor(...)");
                    jsFunctionDeserializeFunction = deserializeFunction(constructor);
                }
                List<JsAstProtoBuf.Function> memberList = class_.getMemberList();
                Intrinsics.checkNotNullExpressionValue(memberList, "getMemberList(...)");
                List<JsAstProtoBuf.Function> list3 = memberList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it = list3.iterator();
                while (it.hasNext()) {
                    arrayList3.add(deserializeFunction((JsAstProtoBuf.Function) it.next()));
                }
                return new JsClass(jsNameDeserializeName, jsNameRef2, jsFunctionDeserializeFunction, CollectionsKt.toMutableList((Collection) arrayList3));
            case 14:
                JsAstProtoBuf.Function function = proto.getFunction();
                Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
                return deserializeFunction(function);
            case 15:
                List<JsAstProtoBuf.DocCommentTag> tagList = proto.getDocComment().getTagList();
                Intrinsics.checkNotNullExpressionValue(tagList, "getTagList(...)");
                List<JsAstProtoBuf.DocCommentTag> list4 = tagList;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list4, 10)), 16));
                for (JsAstProtoBuf.DocCommentTag docCommentTag : list4) {
                    String strDeserializeString = deserializeString(docCommentTag.getNameId());
                    if (docCommentTag.hasExpression()) {
                        JsAstProtoBuf.Expression expression2 = docCommentTag.getExpression();
                        Intrinsics.checkNotNullExpressionValue(expression2, "getExpression(...)");
                        objDeserializeString = deserialize(expression2);
                    } else {
                        objDeserializeString = deserializeString(docCommentTag.getValueStringId());
                    }
                    Pair pair = TuplesKt.to(strDeserializeString, objDeserializeString);
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return new JsDocComment(linkedHashMap);
            case 16:
                JsAstProtoBuf.BinaryOperation binary = proto.getBinary();
                JsAstProtoBuf.BinaryOperation.Type type = binary.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                JsBinaryOperator map = map(type);
                JsAstProtoBuf.Expression left = binary.getLeft();
                Intrinsics.checkNotNullExpressionValue(left, "getLeft(...)");
                JsExpression jsExpressionDeserialize5 = deserialize(left);
                JsAstProtoBuf.Expression right = binary.getRight();
                Intrinsics.checkNotNullExpressionValue(right, "getRight(...)");
                return new JsBinaryOperation(map, jsExpressionDeserialize5, deserialize(right));
            case 17:
                JsAstProtoBuf.UnaryOperation unary = proto.getUnary();
                JsAstProtoBuf.UnaryOperation.Type type2 = unary.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                JsUnaryOperator map2 = map(type2);
                JsAstProtoBuf.Expression operand = unary.getOperand();
                Intrinsics.checkNotNullExpressionValue(operand, "getOperand(...)");
                JsExpression jsExpressionDeserialize6 = deserialize(operand);
                return unary.getPostfix() ? new JsPostfixOperation(map2, jsExpressionDeserialize6) : new JsPrefixOperation(map2, jsExpressionDeserialize6);
            case 18:
                JsAstProtoBuf.Conditional conditional = proto.getConditional();
                JsAstProtoBuf.Expression testExpression = conditional.getTestExpression();
                Intrinsics.checkNotNullExpressionValue(testExpression, "getTestExpression(...)");
                JsExpression jsExpressionDeserialize7 = deserialize(testExpression);
                JsAstProtoBuf.Expression thenExpression = conditional.getThenExpression();
                Intrinsics.checkNotNullExpressionValue(thenExpression, "getThenExpression(...)");
                JsExpression jsExpressionDeserialize8 = deserialize(thenExpression);
                JsAstProtoBuf.Expression elseExpression = conditional.getElseExpression();
                Intrinsics.checkNotNullExpressionValue(elseExpression, "getElseExpression(...)");
                return new JsConditional(jsExpressionDeserialize7, jsExpressionDeserialize8, deserialize(elseExpression));
            case 19:
                JsAstProtoBuf.ArrayAccess arrayAccess = proto.getArrayAccess();
                JsAstProtoBuf.Expression array = arrayAccess.getArray();
                Intrinsics.checkNotNullExpressionValue(array, "getArray(...)");
                JsExpression jsExpressionDeserialize9 = deserialize(array);
                JsAstProtoBuf.Expression index = arrayAccess.getIndex();
                Intrinsics.checkNotNullExpressionValue(index, "getIndex(...)");
                return new JsArrayAccess(jsExpressionDeserialize9, deserialize(index));
            case 20:
                JsAstProtoBuf.NameReference nameReference = proto.getNameReference();
                if (nameReference.hasQualifier()) {
                    JsAstProtoBuf.Expression qualifier = nameReference.getQualifier();
                    Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
                    jsExpressionDeserialize2 = deserialize(qualifier);
                }
                JsNameRef jsNameRef3 = new JsNameRef(deserializeName(nameReference.getNameId()), jsExpressionDeserialize2);
                if (nameReference.hasInlineStrategy()) {
                    JsAstProtoBuf.InlineStrategy inlineStrategy = nameReference.getInlineStrategy();
                    Intrinsics.checkNotNullExpressionValue(inlineStrategy, "getInlineStrategy(...)");
                    MetadataProperties.setInline(jsNameRef3, Boolean.valueOf(map(inlineStrategy)));
                }
                return jsNameRef3;
            case 21:
                JsAstProtoBuf.PropertyReference propertyReference = proto.getPropertyReference();
                if (propertyReference.hasQualifier()) {
                    JsAstProtoBuf.Expression qualifier2 = propertyReference.getQualifier();
                    Intrinsics.checkNotNullExpressionValue(qualifier2, "getQualifier(...)");
                    jsExpressionDeserialize = deserialize(qualifier2);
                }
                JsNameRef jsNameRef4 = new JsNameRef(deserializeString(propertyReference.getStringId()), jsExpressionDeserialize);
                if (propertyReference.hasInlineStrategy()) {
                    JsAstProtoBuf.InlineStrategy inlineStrategy2 = propertyReference.getInlineStrategy();
                    Intrinsics.checkNotNullExpressionValue(inlineStrategy2, "getInlineStrategy(...)");
                    MetadataProperties.setInline(jsNameRef4, Boolean.valueOf(map(inlineStrategy2)));
                }
                return jsNameRef4;
            case 22:
                JsAstProtoBuf.Invocation invocation = proto.getInvocation();
                JsAstProtoBuf.Expression qualifier3 = invocation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier3, "getQualifier(...)");
                JsExpression jsExpressionDeserialize10 = deserialize(qualifier3);
                List<JsAstProtoBuf.Expression> argumentList = invocation.getArgumentList();
                Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
                List<JsAstProtoBuf.Expression> list5 = argumentList;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
                for (JsAstProtoBuf.Expression expression3 : list5) {
                    Intrinsics.checkNotNull(expression3);
                    arrayList4.add(deserialize(expression3));
                }
                JsInvocation jsInvocation = new JsInvocation(jsExpressionDeserialize10, arrayList4);
                if (invocation.hasInlineStrategy()) {
                    JsAstProtoBuf.InlineStrategy inlineStrategy3 = invocation.getInlineStrategy();
                    Intrinsics.checkNotNullExpressionValue(inlineStrategy3, "getInlineStrategy(...)");
                    MetadataProperties.setInline(jsInvocation, Boolean.valueOf(map(inlineStrategy3)));
                }
                return jsInvocation;
            case 23:
                JsAstProtoBuf.Instantiation instantiation = proto.getInstantiation();
                JsAstProtoBuf.Expression qualifier4 = instantiation.getQualifier();
                Intrinsics.checkNotNullExpressionValue(qualifier4, "getQualifier(...)");
                JsExpression jsExpressionDeserialize11 = deserialize(qualifier4);
                List<JsAstProtoBuf.Expression> argumentList2 = instantiation.getArgumentList();
                Intrinsics.checkNotNullExpressionValue(argumentList2, "getArgumentList(...)");
                List<JsAstProtoBuf.Expression> list6 = argumentList2;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list6, 10));
                for (JsAstProtoBuf.Expression expression4 : list6) {
                    Intrinsics.checkNotNull(expression4);
                    arrayList5.add(deserialize(expression4));
                }
                return new JsNew(jsExpressionDeserialize11, arrayList5);
        }
    }

    protected final JsFunction deserializeFunction(JsAstProtoBuf.Function functionProto) {
        Intrinsics.checkNotNullParameter(functionProto, "functionProto");
        JsScope scope = getScope();
        JsAstProtoBuf.Statement body = functionProto.getBody();
        Intrinsics.checkNotNullExpressionValue(body, "getBody(...)");
        JsStatement jsStatementDeserialize = deserialize(body);
        Intrinsics.checkNotNull(jsStatementDeserialize, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsBlock");
        JsFunction jsFunction = new JsFunction(scope, (JsBlock) jsStatementDeserialize, "");
        Set<JsFunction.Modifier> modifiers = jsFunction.getModifiers();
        Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
        Set<JsFunction.Modifier> set = modifiers;
        List<JsAstProtoBuf.Function.Modifier> modifierList = functionProto.getModifierList();
        Intrinsics.checkNotNullExpressionValue(modifierList, "getModifierList(...)");
        List<JsAstProtoBuf.Function.Modifier> list = modifierList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(map((JsAstProtoBuf.Function.Modifier) it.next()));
        }
        CollectionsKt.addAll(set, arrayList);
        List<JsParameter> parameters = jsFunction.getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<JsParameter> list2 = parameters;
        List<JsAstProtoBuf.Parameter> parameterList = functionProto.getParameterList();
        Intrinsics.checkNotNullExpressionValue(parameterList, "getParameterList(...)");
        List<JsAstProtoBuf.Parameter> list3 = parameterList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (JsAstProtoBuf.Parameter parameter : list3) {
            Intrinsics.checkNotNull(parameter);
            arrayList2.add(deserializeParameter(parameter));
        }
        CollectionsKt.addAll(list2, arrayList2);
        if (functionProto.hasNameId()) {
            jsFunction.setName(deserializeName(functionProto.getNameId()));
        }
        MetadataProperties.setLocal(jsFunction, functionProto.getLocal());
        return jsFunction;
    }

    protected final JsVars deserializeVars(JsAstProtoBuf.Vars proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsVars jsVars = new JsVars(proto.getMultiline());
        for (final JsAstProtoBuf.VarDeclaration varDeclaration : proto.getDeclarationList()) {
            Collection vars = jsVars.getVars();
            Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
            Collection collection = vars;
            JsAstProtoBuf.Location location = null;
            Integer numValueOf = varDeclaration.hasFileId() ? Integer.valueOf(varDeclaration.getFileId()) : null;
            if (varDeclaration.hasLocation()) {
                location = varDeclaration.getLocation();
            }
            collection.add(withLocation(numValueOf, location, new Function0<JsVars.JsVar>() { // from class: org.jetbrains.kotlin.serialization.js.ast.JsAstDeserializerBase.deserializeVars.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final JsVars.JsVar invoke() {
                    JsExpression jsExpressionDeserialize;
                    if (varDeclaration.hasInitialValue()) {
                        JsAstDeserializerBase jsAstDeserializerBase = this;
                        JsAstProtoBuf.Expression initialValue = varDeclaration.getInitialValue();
                        Intrinsics.checkNotNullExpressionValue(initialValue, "getInitialValue(...)");
                        jsExpressionDeserialize = jsAstDeserializerBase.deserialize(initialValue);
                    } else {
                        jsExpressionDeserialize = null;
                    }
                    return new JsVars.JsVar(this.deserializeName(varDeclaration.getNameId()), jsExpressionDeserialize);
                }
            }));
        }
        if (proto.hasExportedPackageId()) {
            MetadataProperties.setExportedPackage(jsVars, deserializeString(proto.getExportedPackageId()));
        }
        return jsVars;
    }

    protected final JsCompositeBlock deserializeCompositeBlock(JsAstProtoBuf.CompositeBlock proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        List<JsStatement> statements = jsCompositeBlock.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        List<JsStatement> list = statements;
        List<JsAstProtoBuf.Statement> statementList = proto.getStatementList();
        Intrinsics.checkNotNullExpressionValue(statementList, "getStatementList(...)");
        List<JsAstProtoBuf.Statement> list2 = statementList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (JsAstProtoBuf.Statement statement : list2) {
            Intrinsics.checkNotNull(statement);
            arrayList.add(deserialize(statement));
        }
        CollectionsKt.addAll(list, arrayList);
        return jsCompositeBlock;
    }

    protected final JsParameter deserializeParameter(JsAstProtoBuf.Parameter proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        JsParameter jsParameter = new JsParameter(deserializeName(proto.getNameId()));
        MetadataProperties.setHasDefaultValue(jsParameter, proto.getHasDefaultValue());
        return jsParameter;
    }

    protected final JsName deserializeName(int id2) {
        JsName jsNameDeclareName;
        JsName jsName = this.nameCache.get(id2);
        if (jsName != null) {
            return jsName;
        }
        JsAstProtoBuf.Name name2 = this.nameTable.get(id2);
        String strDeserializeString = deserializeString(name2.getIdentifier());
        if (name2.getTemporary()) {
            jsNameDeclareName = JsScope.declareTemporaryName(strDeserializeString);
        } else {
            jsNameDeclareName = JsDynamicScope.INSTANCE.declareName(strDeserializeString);
        }
        Intrinsics.checkNotNull(jsNameDeclareName);
        if (name2.hasLocalNameId()) {
            JsAstProtoBuf.LocalAlias localNameId = name2.getLocalNameId();
            Intrinsics.checkNotNullExpressionValue(localNameId, "getLocalNameId(...)");
            MetadataProperties.setLocalAlias(jsNameDeclareName, deserializeLocalAlias(localNameId));
        }
        if (name2.hasImported()) {
            MetadataProperties.setImported(jsNameDeclareName, name2.getImported());
        }
        if (name2.hasSpecialFunction()) {
            JsAstProtoBuf.SpecialFunction specialFunction = name2.getSpecialFunction();
            Intrinsics.checkNotNullExpressionValue(specialFunction, "getSpecialFunction(...)");
            MetadataProperties.setSpecialFunction(jsNameDeclareName, map(specialFunction));
        }
        this.nameCache.set(id2, jsNameDeclareName);
        return jsNameDeclareName;
    }

    protected final LocalAlias deserializeLocalAlias(JsAstProtoBuf.LocalAlias localNameId) {
        Intrinsics.checkNotNullParameter(localNameId, "localNameId");
        return new LocalAlias(deserializeName(localNameId.getLocalNameId()), localNameId.hasTag() ? deserializeString(localNameId.getTag()) : null);
    }

    protected final String deserializeString(int id2) {
        return this.stringTable.get(id2);
    }

    protected final JsFunction.Modifier map(JsAstProtoBuf.Function.Modifier modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        int i = WhenMappings.$EnumSwitchMapping$2[modifier.ordinal()];
        if (i == 1) {
            return JsFunction.Modifier.STATIC;
        }
        if (i == 2) {
            return JsFunction.Modifier.SET;
        }
        if (i == 3) {
            return JsFunction.Modifier.GET;
        }
        throw new NoWhenBranchMatchedException();
    }

    protected final JsBinaryOperator map(JsAstProtoBuf.BinaryOperation.Type op) {
        Intrinsics.checkNotNullParameter(op, "op");
        switch (WhenMappings.$EnumSwitchMapping$3[op.ordinal()]) {
            case 1:
                return JsBinaryOperator.MUL;
            case 2:
                return JsBinaryOperator.DIV;
            case 3:
                return JsBinaryOperator.MOD;
            case 4:
                return JsBinaryOperator.ADD;
            case 5:
                return JsBinaryOperator.SUB;
            case 6:
                return JsBinaryOperator.SHL;
            case 7:
                return JsBinaryOperator.SHR;
            case 8:
                return JsBinaryOperator.SHRU;
            case 9:
                return JsBinaryOperator.LT;
            case 10:
                return JsBinaryOperator.LTE;
            case 11:
                return JsBinaryOperator.GT;
            case 12:
                return JsBinaryOperator.GTE;
            case 13:
                return JsBinaryOperator.INSTANCEOF;
            case 14:
                return JsBinaryOperator.INOP;
            case 15:
                return JsBinaryOperator.EQ;
            case 16:
                return JsBinaryOperator.NEQ;
            case 17:
                return JsBinaryOperator.REF_EQ;
            case 18:
                return JsBinaryOperator.REF_NEQ;
            case 19:
                return JsBinaryOperator.BIT_AND;
            case 20:
                return JsBinaryOperator.BIT_XOR;
            case 21:
                return JsBinaryOperator.BIT_OR;
            case 22:
                return JsBinaryOperator.AND;
            case 23:
                return JsBinaryOperator.OR;
            case 24:
                return JsBinaryOperator.ASG;
            case 25:
                return JsBinaryOperator.ASG_ADD;
            case 26:
                return JsBinaryOperator.ASG_SUB;
            case 27:
                return JsBinaryOperator.ASG_MUL;
            case 28:
                return JsBinaryOperator.ASG_DIV;
            case 29:
                return JsBinaryOperator.ASG_MOD;
            case 30:
                return JsBinaryOperator.ASG_SHL;
            case 31:
                return JsBinaryOperator.ASG_SHR;
            case 32:
                return JsBinaryOperator.ASG_SHRU;
            case 33:
                return JsBinaryOperator.ASG_BIT_AND;
            case 34:
                return JsBinaryOperator.ASG_BIT_OR;
            case 35:
                return JsBinaryOperator.ASG_BIT_XOR;
            case 36:
                return JsBinaryOperator.COMMA;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    protected final JsUnaryOperator map(JsAstProtoBuf.UnaryOperation.Type op) {
        Intrinsics.checkNotNullParameter(op, "op");
        switch (WhenMappings.$EnumSwitchMapping$4[op.ordinal()]) {
            case 1:
                return JsUnaryOperator.BIT_NOT;
            case 2:
                return JsUnaryOperator.DEC;
            case 3:
                return JsUnaryOperator.DELETE;
            case 4:
                return JsUnaryOperator.INC;
            case 5:
                return JsUnaryOperator.NEG;
            case 6:
                return JsUnaryOperator.POS;
            case 7:
                return JsUnaryOperator.NOT;
            case 8:
                return JsUnaryOperator.TYPEOF;
            case 9:
                return JsUnaryOperator.VOID;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    protected final SideEffectKind map(JsAstProtoBuf.SideEffects sideEffects) {
        Intrinsics.checkNotNullParameter(sideEffects, "sideEffects");
        int i = WhenMappings.$EnumSwitchMapping$5[sideEffects.ordinal()];
        if (i == 1) {
            return SideEffectKind.AFFECTS_STATE;
        }
        if (i == 2) {
            return SideEffectKind.DEPENDS_ON_STATE;
        }
        if (i == 3) {
            return SideEffectKind.PURE;
        }
        throw new NoWhenBranchMatchedException();
    }

    protected final boolean map(JsAstProtoBuf.InlineStrategy inlineStrategy) {
        Intrinsics.checkNotNullParameter(inlineStrategy, "inlineStrategy");
        return inlineStrategy == JsAstProtoBuf.InlineStrategy.AS_FUNCTION || inlineStrategy == JsAstProtoBuf.InlineStrategy.IN_PLACE;
    }

    protected final SpecialFunction map(JsAstProtoBuf.SpecialFunction specialFunction) {
        Intrinsics.checkNotNullParameter(specialFunction, "specialFunction");
        switch (WhenMappings.$EnumSwitchMapping$6[specialFunction.ordinal()]) {
            case 1:
                return SpecialFunction.DEFINE_INLINE_FUNCTION;
            case 2:
                return SpecialFunction.WRAP_FUNCTION;
            case 3:
                return SpecialFunction.TO_BOXED_CHAR;
            case 4:
                return SpecialFunction.UNBOX_CHAR;
            case 5:
                return SpecialFunction.SUSPEND_CALL;
            case 6:
                return SpecialFunction.COROUTINE_RESULT;
            case 7:
                return SpecialFunction.COROUTINE_CONTROLLER;
            case 8:
                return SpecialFunction.COROUTINE_RECEIVER;
            case 9:
                return SpecialFunction.SET_COROUTINE_RESULT;
            case 10:
                return SpecialFunction.GET_KCLASS;
            case 11:
                return SpecialFunction.GET_REIFIED_TYPE_PARAMETER_KTYPE;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    protected final <T extends JsNode> T withLocation(Integer fileId, JsAstProtoBuf.Location location, Function0<? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        JsLocation jsLocation = null;
        String strDeserializeString = fileId != null ? deserializeString(fileId.intValue()) : null;
        String strPeek = strDeserializeString == null ? this.fileStack.peek() : strDeserializeString;
        if (strPeek != null && location != null) {
            jsLocation = new JsLocation(strPeek, location.getStartLine(), location.getStartChar(), null, 8, null);
        }
        boolean z = (location == null || strDeserializeString == null || Intrinsics.areEqual(strDeserializeString, this.fileStack.peek())) ? false : true;
        if (z) {
            this.fileStack.push(strDeserializeString);
        }
        T tInvoke = action.invoke();
        if (jsLocation != null) {
            Intrinsics.checkNotNull(strPeek);
            JsLocationWithSource jsLocationWithSourceEmbedSources = embedSources(jsLocation, strPeek);
            if (jsLocationWithSourceEmbedSources != null) {
                jsLocation = jsLocationWithSourceEmbedSources;
            }
            tInvoke.setSource(jsLocation);
        }
        if (z) {
            this.fileStack.pop();
        }
        return tInvoke;
    }

    protected final JsComment deserializeComment(JsAstProtoBuf.Comment comment) {
        Intrinsics.checkNotNullParameter(comment, "comment");
        if (comment.getMultiline()) {
            String text = comment.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            return new JsMultiLineComment(text);
        }
        String text2 = comment.getText();
        Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
        return new JsSingleLineComment(text2);
    }
}
