package org.jetbrains.kotlin.ir.backend.js.utils.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.export.TypeScriptFragment;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrIcClassModel;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrProgramFragment;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
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
import org.jetbrains.kotlin.js.backend.ast.JsExport;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFor;
import org.jetbrains.kotlin.js.backend.ast.JsForIn;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
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
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: JsIrAstDeserializer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\"\u001a\u0004\u0018\u0001H#\"\u0004\b\u0000\u0010#2\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H#0%H\u0082\b¢\u0006\u0002\u0010&J*\u0010'\u001a\b\u0012\u0004\u0012\u0002H#0\f\"\u0006\b\u0000\u0010#\u0018\u00012\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H#0%H\u0082\b¢\u0006\u0002\u0010)J\b\u0010*\u001a\u00020+H\u0002J\b\u0010,\u001a\u00020-H\u0002J\b\u0010.\u001a\u00020/H\u0002J\b\u00100\u001a\u000201H\u0002J\b\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u000205H\u0002J\b\u00106\u001a\u000207H\u0002J\u0006\u00108\u001a\u000209J\f\u0010:\u001a\b\u0012\u0004\u0012\u0002090;J\b\u0010<\u001a\u00020=H\u0002J\b\u0010>\u001a\u00020?H\u0002J\b\u0010@\u001a\u00020AH\u0002J\b\u0010B\u001a\u00020CH\u0002J#\u0010D\u001a\b\u0012\u0004\u0012\u0002H#0;\"\u0004\b\u0000\u0010#2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H#0%H\u0082\bJ\b\u0010E\u001a\u00020FH\u0002J\b\u0010G\u001a\u00020\u0016H\u0002J\b\u0010H\u001a\u00020IH\u0002J\u0017\u0010J\u001a\u00020K2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020K0%H\u0082\bJ\b\u0010L\u001a\u00020MH\u0002J\b\u0010N\u001a\u00020\nH\u0002J\b\u0010O\u001a\u00020PH\u0002J&\u0010Q\u001a\u0002H#\"\b\b\u0000\u0010#*\u00020R2\f\u0010S\u001a\b\u0012\u0004\u0012\u0002H#0%H\u0082\b¢\u0006\u0002\u0010TJ&\u0010U\u001a\u0002H#\"\b\b\u0000\u0010#*\u00020R2\f\u0010S\u001a\b\u0012\u0004\u0012\u0002H#0%H\u0082\b¢\u0006\u0002\u0010TR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0011R\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001fR\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010!¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/serialization/JsIrAstDeserializer;", "", FirebaseAnalytics.Param.SOURCE, "", "([B)V", "buffer", "Ljava/nio/ByteBuffer;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "fileStack", "Ljava/util/Deque;", "", "jsBinaryOperatorValues", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "[Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "jsFunctionModifiersValues", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction$Modifier;", "[Lorg/jetbrains/kotlin/js/backend/ast/JsFunction$Modifier;", "jsUnaryOperatorValues", "Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperator;", "[Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperator;", "nameTable", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "[Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "scope", "Lorg/jetbrains/kotlin/js/backend/ast/JsScope;", "sideEffectKindValues", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;", "[Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;", "specialFunctionValues", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "[Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "stringTable", "[Ljava/lang/String;", "ifTrue", ExifInterface.GPS_DIRECTION_TRUE, "then", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "readArray", "readElement", "(Lkotlin/jvm/functions/Function0;)[Ljava/lang/Object;", "readBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "readBoolean", "", "readByte", "", "readComment", "Lorg/jetbrains/kotlin/js/backend/ast/JsComment;", "readCompositeBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "readDouble", "", "readExpression", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "readFragment", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrProgramFragment;", "readFragments", "", "readFunction", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "readInt", "", "readIrIcClassModel", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrIcClassModel;", "readJsImportedModule", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "readList", "readLocalAlias", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "readName", "readParameter", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "readRepeated", "", "readStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "readString", "readVars", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "withComments", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "action", "(Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "withLocation", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JsIrAstDeserializer {
    private final ByteBuffer buffer;
    private final Deque<String> fileStack;
    private final JsBinaryOperator[] jsBinaryOperatorValues;
    private final JsFunction.Modifier[] jsFunctionModifiersValues;
    private final JsUnaryOperator[] jsUnaryOperatorValues;
    private final JsName[] nameTable;
    private final JsScope scope;
    private final SideEffectKind[] sideEffectKindValues;
    private final byte[] source;
    private final SpecialFunction[] specialFunctionValues;
    private final String[] stringTable;

    public JsIrAstDeserializer(byte[] source) {
        Intrinsics.checkNotNullParameter(source, "source");
        this.source = source;
        this.buffer = ByteBuffer.wrap(source);
        this.scope = JsGenerationContextKt.getEmptyScope();
        this.fileStack = new ArrayDeque();
        int i = readInt();
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < i; i2++) {
            strArr[i2] = readString();
        }
        this.stringTable = strArr;
        int i3 = readInt();
        JsName[] jsNameArr = new JsName[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            jsNameArr[i4] = readName();
        }
        this.nameTable = jsNameArr;
        this.sideEffectKindValues = SideEffectKind.values();
        this.jsBinaryOperatorValues = JsBinaryOperator.values();
        this.jsUnaryOperatorValues = JsUnaryOperator.values();
        this.jsFunctionModifiersValues = JsFunction.Modifier.values();
        this.specialFunctionValues = SpecialFunction.values();
    }

    private final byte readByte() {
        return this.buffer.get();
    }

    private final boolean readBoolean() {
        return readByte() != 0;
    }

    private final int readInt() {
        return this.buffer.getInt();
    }

    private final double readDouble() {
        return this.buffer.getDouble();
    }

    private final String readString() {
        int i = readInt();
        int iPosition = this.buffer.position();
        String str = new String(this.source, iPosition, i, ConstantsKt.getSerializationCharset());
        this.buffer.position(iPosition + i);
        return str;
    }

    public final JsIrProgramFragment readFragment() {
        JsIrProgramFragment jsIrProgramFragment = new JsIrProgramFragment(readString(), readString());
        int i = readInt();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            jsIrProgramFragment.getImportedModules().add(new JsImportedModule(this.stringTable[readInt()], this.nameTable[readInt()], readBoolean() ? readExpression() : null, null, 8, null));
            i = i2;
        }
        int i3 = readInt();
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            jsIrProgramFragment.getImports().put(this.stringTable[readInt()], readStatement());
            i3 = i4;
        }
        int i5 = readInt();
        while (true) {
            int i6 = i5 - 1;
            if (i5 <= 0) {
                break;
            }
            List<JsStatement> statements = jsIrProgramFragment.getDeclarations().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(readStatement());
            i5 = i6;
        }
        int i7 = readInt();
        while (true) {
            int i8 = i7 - 1;
            if (i7 <= 0) {
                break;
            }
            List<JsStatement> statements2 = jsIrProgramFragment.getInitializers().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(readStatement());
            i7 = i8;
        }
        int i9 = readInt();
        while (true) {
            int i10 = i9 - 1;
            if (i9 <= 0) {
                break;
            }
            List<JsStatement> statements3 = jsIrProgramFragment.getExports().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
            statements3.add(readStatement());
            i9 = i10;
        }
        int i11 = readInt();
        while (true) {
            int i12 = i11 - 1;
            if (i11 <= 0) {
                break;
            }
            List<JsStatement> statements4 = jsIrProgramFragment.getPolyfills().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
            statements4.add(readStatement());
            i11 = i12;
        }
        int i13 = readInt();
        while (true) {
            int i14 = i13 - 1;
            if (i13 <= 0) {
                break;
            }
            jsIrProgramFragment.getNameBindings().put(this.stringTable[readInt()], this.nameTable[readInt()]);
            i13 = i14;
        }
        int i15 = readInt();
        while (true) {
            int i16 = i15 - 1;
            if (i15 <= 0) {
                break;
            }
            jsIrProgramFragment.getOptionalCrossModuleImports().add(this.stringTable[readInt()]);
            i15 = i16;
        }
        int i17 = readInt();
        while (true) {
            int i18 = i17 - 1;
            if (i17 <= 0) {
                break;
            }
            jsIrProgramFragment.getClasses().put(this.nameTable[readInt()], readIrIcClassModel());
            i17 = i18;
        }
        if (readBoolean()) {
            jsIrProgramFragment.setTestFunInvocation(readStatement());
        }
        if (readBoolean()) {
            jsIrProgramFragment.setMainFunction(readStatement());
        }
        if (readBoolean()) {
            jsIrProgramFragment.m7200setDtsY7E3pzw(TypeScriptFragment.m7090constructorimpl(readString()));
        }
        if (readBoolean()) {
            jsIrProgramFragment.setSuiteFn(this.nameTable[readInt()]);
        }
        int i19 = readInt();
        while (true) {
            int i20 = i19 - 1;
            if (i19 <= 0) {
                return jsIrProgramFragment;
            }
            jsIrProgramFragment.getDefinitions().add(this.stringTable[readInt()]);
            i19 = i20;
        }
    }

    private final JsFunction readFunction() {
        JsFunction jsFunction = new JsFunction(this.scope, readBlock(), "");
        int i = readInt();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            List<JsParameter> parameters = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            parameters.add(readParameter());
            i = i2;
        }
        int i3 = readInt();
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            Set<JsFunction.Modifier> modifiers = jsFunction.getModifiers();
            Intrinsics.checkNotNullExpressionValue(modifiers, "getModifiers(...)");
            modifiers.add(this.jsFunctionModifiersValues[readInt()]);
            i3 = i4;
        }
        if (readBoolean()) {
            jsFunction.setName(this.nameTable[readInt()]);
        }
        MetadataProperties.setLocal(jsFunction, readBoolean());
        return jsFunction;
    }

    private final JsImportedModule readJsImportedModule() {
        return new JsImportedModule(this.stringTable[readInt()], this.nameTable[readInt()], readBoolean() ? readExpression() : null, null, 8, null);
    }

    private final JsParameter readParameter() {
        JsParameter jsParameter = new JsParameter(this.nameTable[readInt()]);
        MetadataProperties.setHasDefaultValue(jsParameter, readBoolean());
        return jsParameter;
    }

    private final JsCompositeBlock readCompositeBlock() {
        JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        int i = readInt();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return jsCompositeBlock;
            }
            List<JsStatement> statements = jsCompositeBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(readStatement());
            i = i2;
        }
    }

    private final JsVars readVars() {
        JsVars.JsVar jsVar;
        JsLocation jsLocation;
        JsVars jsVars = new JsVars(readBoolean());
        int i = readInt();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            List<JsVars.JsVar> vars = jsVars.getVars();
            Intrinsics.checkNotNullExpressionValue(vars, "getVars(...)");
            List<JsVars.JsVar> list = vars;
            if (readBoolean()) {
                String str = readBoolean() ? this.stringTable[readInt()] : null;
                String strPeek = str == null ? this.fileStack.peek() : str;
                int i3 = readInt();
                int i4 = readInt();
                if (strPeek != null) {
                    Intrinsics.checkNotNull(strPeek);
                    jsLocation = new JsLocation(strPeek, i3, i4, null, 8, null);
                } else {
                    jsLocation = null;
                }
                boolean z = (str == null || Intrinsics.areEqual(str, this.fileStack.peek())) ? false : true;
                if (z) {
                    this.fileStack.push(str);
                }
                jsVar = new JsVars.JsVar(this.nameTable[readInt()], readBoolean() ? readExpression() : null);
                if (jsLocation != null) {
                    jsVar.setSource(jsLocation);
                }
                if (z) {
                    this.fileStack.pop();
                }
            } else {
                jsVar = null;
            }
            if (jsVar == null) {
                jsVar = new JsVars.JsVar(this.nameTable[readInt()], readBoolean() ? readExpression() : null);
            }
            list.add(jsVar);
            i = i2;
        }
        if (readBoolean()) {
            MetadataProperties.setExportedPackage(jsVars, this.stringTable[readInt()]);
        }
        return jsVars;
    }

    private final JsName readName() {
        String str = this.stringTable[readInt()];
        JsName jsNameDeclareTemporaryName = readBoolean() ? JsScope.declareTemporaryName(str) : null;
        if (jsNameDeclareTemporaryName == null) {
            jsNameDeclareTemporaryName = JsDynamicScope.INSTANCE.declareName(str);
        }
        Intrinsics.checkNotNull(jsNameDeclareTemporaryName);
        if (readBoolean()) {
            MetadataProperties.setLocalAlias(jsNameDeclareTemporaryName, readLocalAlias());
        }
        if (readBoolean()) {
            MetadataProperties.setImported(jsNameDeclareTemporaryName, true);
        }
        if (readBoolean()) {
            MetadataProperties.setSpecialFunction(jsNameDeclareTemporaryName, this.specialFunctionValues[readInt()]);
        }
        return jsNameDeclareTemporaryName;
    }

    private final LocalAlias readLocalAlias() {
        return new LocalAlias(this.nameTable[readInt()], readBoolean() ? this.stringTable[readInt()] : null);
    }

    private final JsComment readComment() {
        String string = readString();
        JsComment jsMultiLineComment = readBoolean() ? new JsMultiLineComment(string) : null;
        if (jsMultiLineComment == null) {
            jsMultiLineComment = new JsSingleLineComment(string);
        }
        return jsMultiLineComment;
    }

    public final List<JsIrProgramFragment> readFragments() {
        int i = readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(readFragment());
        }
        return arrayList;
    }

    private final JsIrIcClassModel readIrIcClassModel() {
        int i = readInt();
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(this.nameTable[readInt()]);
        }
        JsIrIcClassModel jsIrIcClassModel = new JsIrIcClassModel(arrayList);
        int i3 = readInt();
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            List<JsStatement> statements = jsIrIcClassModel.getPreDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(readStatement());
            i3 = i4;
        }
        int i5 = readInt();
        while (true) {
            int i6 = i5 - 1;
            if (i5 <= 0) {
                return jsIrIcClassModel;
            }
            List<JsStatement> statements2 = jsIrIcClassModel.getPostDeclarationBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
            statements2.add(readStatement());
            i5 = i6;
        }
    }

    private final JsStatement readStatement() {
        JsReturn jsReturn;
        JsReturn jsReturn2;
        JsIf jsIf;
        JsSwitch jsSwitch;
        JsNode jsDefault;
        JsCase jsCase;
        JsLocation jsLocation;
        JsCase jsCase2;
        JsFor jsForIn;
        JsImport.Target.All all;
        JsExport.Subject.All elements;
        JsLocation jsLocation2;
        JsNode jsDefault2;
        JsCase jsCase3;
        JsLocation jsLocation3;
        JsCase jsCase4;
        JsImport.Target.All all2;
        JsExport.Subject.All elements2;
        boolean z = true;
        if (readBoolean()) {
            String str = readBoolean() ? this.stringTable[readInt()] : null;
            String strPeek = str == null ? this.fileStack.peek() : str;
            int i = readInt();
            int i2 = readInt();
            if (strPeek != null) {
                Intrinsics.checkNotNull(strPeek);
                jsLocation2 = new JsLocation(strPeek, i, i2, null, 8, null);
            } else {
                jsLocation2 = null;
            }
            boolean z2 = (str == null || Intrinsics.areEqual(str, this.fileStack.peek())) ? false : true;
            if (z2) {
                this.fileStack.push(str);
            }
            StatementIds statementIds = StatementIds.INSTANCE;
            byte b = readByte();
            switch (b) {
                case 0:
                    jsReturn = new JsReturn(readBoolean() ? readExpression() : null);
                    break;
                case 1:
                    jsReturn = new JsThrow(readExpression());
                    break;
                case 2:
                    jsReturn = new JsBreak(readBoolean() ? new JsNameRef(this.nameTable[readInt()]) : null);
                    break;
                case 3:
                    jsReturn = new JsContinue(readBoolean() ? new JsNameRef(this.nameTable[readInt()]) : null);
                    break;
                case 4:
                    jsReturn = new JsDebugger();
                    break;
                case 5:
                    JsExpressionStatement jsExpressionStatement = new JsExpressionStatement(readExpression());
                    if (readBoolean()) {
                        MetadataProperties.setExportedTag(jsExpressionStatement, this.stringTable[readInt()]);
                        Unit unit = Unit.INSTANCE;
                    }
                    jsReturn = jsExpressionStatement;
                    break;
                case 6:
                    jsReturn = readVars();
                    break;
                case 7:
                    JsBlock jsBlock = new JsBlock();
                    int i3 = readInt();
                    while (true) {
                        int i4 = i3 - 1;
                        if (i3 <= 0) {
                            jsReturn = jsBlock;
                        } else {
                            List<JsStatement> statements = jsBlock.getStatements();
                            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                            statements.add(readStatement());
                            i3 = i4;
                        }
                        break;
                    }
                    break;
                case 8:
                    jsReturn = readCompositeBlock();
                    break;
                case 9:
                    jsReturn = new JsLabel(this.nameTable[readInt()], readStatement());
                    break;
                case 10:
                    jsReturn = new JsIf(readExpression(), readStatement(), readBoolean() ? readStatement() : null);
                    break;
                case 11:
                    JsExpression expression = readExpression();
                    int i5 = readInt();
                    ArrayList arrayList = new ArrayList(i5);
                    int i6 = 0;
                    while (i6 < i5) {
                        if (readBoolean()) {
                            String str2 = readBoolean() ? this.stringTable[readInt()] : null;
                            String strPeek2 = str2 == null ? this.fileStack.peek() : str2;
                            int i7 = readInt();
                            int i8 = readInt();
                            if (strPeek2 != null) {
                                Intrinsics.checkNotNull(strPeek2);
                                jsLocation3 = new JsLocation(strPeek2, i7, i8, null, 8, null);
                            } else {
                                jsLocation3 = null;
                            }
                            boolean z3 = (str2 == null || Intrinsics.areEqual(str2, this.fileStack.peek())) ? false : z;
                            if (z3) {
                                this.fileStack.push(str2);
                            }
                            if (readBoolean()) {
                                jsCase4 = new JsCase();
                                jsCase4.setCaseExpression(readExpression());
                            } else {
                                jsCase4 = null;
                            }
                            jsDefault2 = jsCase4 != null ? jsCase4 : new JsDefault();
                            if (jsLocation3 != null) {
                                jsDefault2.setSource(jsLocation3);
                            }
                            if (z3) {
                                this.fileStack.pop();
                            }
                        } else {
                            jsDefault2 = null;
                        }
                        if (jsDefault2 == null) {
                            if (readBoolean()) {
                                jsCase3 = new JsCase();
                                jsCase3.setCaseExpression(readExpression());
                            } else {
                                jsCase3 = null;
                            }
                            jsDefault2 = jsCase3 != null ? jsCase3 : new JsDefault();
                        }
                        JsSwitchMember jsSwitchMember = (JsSwitchMember) jsDefault2;
                        int i9 = readInt();
                        while (true) {
                            int i10 = i9 - 1;
                            if (i9 > 0) {
                                List<JsStatement> statements2 = jsSwitchMember.getStatements();
                                Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                                statements2.add(readStatement());
                                i9 = i10;
                            }
                        }
                        arrayList.add(jsSwitchMember);
                        i6++;
                        z = true;
                    }
                    jsReturn = new JsSwitch(expression, arrayList);
                    break;
                case 12:
                    jsReturn = new JsWhile(readExpression(), readStatement());
                    break;
                case 13:
                    jsReturn = new JsDoWhile(readExpression(), readStatement());
                    break;
                case 14:
                    JsExpression expression2 = readBoolean() ? readExpression() : null;
                    JsExpression expression3 = readBoolean() ? readExpression() : null;
                    JsStatement statement = readBoolean() ? readStatement() : null;
                    JsFor jsFor = readBoolean() ? new JsFor(readVars(), expression2, expression3, statement) : null;
                    if (jsFor == null) {
                        jsFor = new JsFor(readBoolean() ? readExpression() : null, expression2, expression3, statement);
                    }
                    jsReturn = jsFor;
                    break;
                case 15:
                    jsReturn = new JsForIn(readBoolean() ? this.nameTable[readInt()] : null, readBoolean() ? readExpression() : null, readExpression(), readStatement());
                    break;
                case 16:
                    JsBlock block = readBlock();
                    int i11 = readInt();
                    ArrayList arrayList2 = new ArrayList(i11);
                    for (int i12 = 0; i12 < i11; i12++) {
                        JsCatch jsCatch = new JsCatch(this.nameTable[readInt()]);
                        jsCatch.setBody(readBlock());
                        arrayList2.add(jsCatch);
                    }
                    jsReturn = new JsTry(block, arrayList2, readBoolean() ? readBlock() : null);
                    break;
                case 17:
                    jsReturn = JsEmpty.INSTANCE;
                    break;
                case 18:
                    jsReturn = new JsSingleLineComment(readString());
                    break;
                case 19:
                    jsReturn = new JsMultiLineComment(readString());
                    break;
                case 20:
                    String string = readString();
                    byte b2 = readByte();
                    if (b2 == 0) {
                        JsNameRef jsNameRefMakeRef = this.nameTable[readInt()].makeRef();
                        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
                        all2 = new JsImport.Target.All(jsNameRefMakeRef);
                    } else if (b2 == 1) {
                        int i13 = readInt();
                        ArrayList arrayList3 = new ArrayList(i13);
                        for (int i14 = 0; i14 < i13; i14++) {
                            arrayList3.add(new JsImport.Element(this.nameTable[readInt()], readBoolean() ? this.nameTable[readInt()].makeRef() : null));
                        }
                        all2 = new JsImport.Target.Elements(CollectionsKt.toMutableList((Collection) arrayList3));
                    } else {
                        if (b2 != 2) {
                            throw new IllegalStateException(("Unknown JsImport type " + ((int) b2)).toString());
                        }
                        JsNameRef jsNameRefMakeRef2 = this.nameTable[readInt()].makeRef();
                        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
                        all2 = new JsImport.Target.Default(jsNameRefMakeRef2);
                    }
                    jsReturn = new JsImport(string, all2);
                    break;
                case 21:
                    byte b3 = readByte();
                    if (b3 == 0) {
                        elements2 = JsExport.Subject.All.INSTANCE;
                    } else {
                        if (b3 != 1) {
                            throw new IllegalStateException(("Unknown JsExport type " + ((int) b3)).toString());
                        }
                        int i15 = readInt();
                        ArrayList arrayList4 = new ArrayList(i15);
                        for (int i16 = 0; i16 < i15; i16++) {
                            JsNameRef jsNameRefMakeRef3 = this.nameTable[readInt()].makeRef();
                            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef3, "makeRef(...)");
                            arrayList4.add(new JsExport.Element(jsNameRefMakeRef3, readBoolean() ? this.nameTable[readInt()] : null));
                        }
                        elements2 = new JsExport.Subject.Elements(arrayList4);
                    }
                    jsReturn = new JsExport(elements2, readBoolean() ? readString() : null);
                    break;
                default:
                    throw new IllegalStateException(("Unknown statement id: " + ((int) b)).toString());
            }
            if (jsLocation2 != null) {
                jsReturn.setSource(jsLocation2);
            }
            if (z2) {
                this.fileStack.pop();
            }
        } else {
            jsReturn = null;
        }
        if (jsReturn == null) {
            StatementIds statementIds2 = StatementIds.INSTANCE;
            byte b4 = readByte();
            switch (b4) {
                case 0:
                    jsReturn2 = new JsReturn(readBoolean() ? readExpression() : null);
                    jsReturn = jsReturn2;
                    break;
                case 1:
                    jsReturn2 = new JsThrow(readExpression());
                    jsReturn = jsReturn2;
                    break;
                case 2:
                    jsReturn2 = new JsBreak(readBoolean() ? new JsNameRef(this.nameTable[readInt()]) : null);
                    jsReturn = jsReturn2;
                    break;
                case 3:
                    jsReturn2 = new JsContinue(readBoolean() ? new JsNameRef(this.nameTable[readInt()]) : null);
                    jsReturn = jsReturn2;
                    break;
                case 4:
                    jsReturn2 = new JsDebugger();
                    jsReturn = jsReturn2;
                    break;
                case 5:
                    JsExpressionStatement jsExpressionStatement2 = new JsExpressionStatement(readExpression());
                    if (readBoolean()) {
                        MetadataProperties.setExportedTag(jsExpressionStatement2, this.stringTable[readInt()]);
                        Unit unit2 = Unit.INSTANCE;
                    }
                    jsReturn2 = jsExpressionStatement2;
                    jsReturn = jsReturn2;
                    break;
                case 6:
                    jsReturn2 = readVars();
                    jsReturn = jsReturn2;
                    break;
                case 7:
                    JsBlock jsBlock2 = new JsBlock();
                    int i17 = readInt();
                    while (true) {
                        int i18 = i17 - 1;
                        if (i17 <= 0) {
                            jsReturn2 = jsBlock2;
                            jsReturn = jsReturn2;
                        } else {
                            List<JsStatement> statements3 = jsBlock2.getStatements();
                            Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                            statements3.add(readStatement());
                            i17 = i18;
                        }
                        break;
                    }
                    break;
                case 8:
                    jsReturn2 = readCompositeBlock();
                    jsReturn = jsReturn2;
                    break;
                case 9:
                    jsReturn2 = new JsLabel(this.nameTable[readInt()], readStatement());
                    jsReturn = jsReturn2;
                    break;
                case 10:
                    jsIf = new JsIf(readExpression(), readStatement(), readBoolean() ? readStatement() : null);
                    jsReturn = jsIf;
                    break;
                case 11:
                    JsExpression expression4 = readExpression();
                    int i19 = readInt();
                    ArrayList arrayList5 = new ArrayList(i19);
                    for (int i20 = 0; i20 < i19; i20++) {
                        if (readBoolean()) {
                            String str3 = readBoolean() ? this.stringTable[readInt()] : null;
                            String strPeek3 = str3 == null ? this.fileStack.peek() : str3;
                            int i21 = readInt();
                            int i22 = readInt();
                            if (strPeek3 != null) {
                                Intrinsics.checkNotNull(strPeek3);
                                jsLocation = new JsLocation(strPeek3, i21, i22, null, 8, null);
                            } else {
                                jsLocation = null;
                            }
                            boolean z4 = (str3 == null || Intrinsics.areEqual(str3, this.fileStack.peek())) ? false : true;
                            if (z4) {
                                this.fileStack.push(str3);
                            }
                            if (readBoolean()) {
                                jsCase2 = new JsCase();
                                jsCase2.setCaseExpression(readExpression());
                            } else {
                                jsCase2 = null;
                            }
                            jsDefault = jsCase2 != null ? jsCase2 : new JsDefault();
                            if (jsLocation != null) {
                                jsDefault.setSource(jsLocation);
                            }
                            if (z4) {
                                this.fileStack.pop();
                            }
                        } else {
                            jsDefault = null;
                        }
                        if (jsDefault == null) {
                            if (readBoolean()) {
                                jsCase = new JsCase();
                                jsCase.setCaseExpression(readExpression());
                            } else {
                                jsCase = null;
                            }
                            jsDefault = jsCase != null ? jsCase : new JsDefault();
                        }
                        JsSwitchMember jsSwitchMember2 = (JsSwitchMember) jsDefault;
                        int i23 = readInt();
                        while (true) {
                            int i24 = i23 - 1;
                            if (i23 > 0) {
                                List<JsStatement> statements4 = jsSwitchMember2.getStatements();
                                Intrinsics.checkNotNullExpressionValue(statements4, "getStatements(...)");
                                statements4.add(readStatement());
                                i23 = i24;
                            }
                        }
                        arrayList5.add(jsSwitchMember2);
                    }
                    jsSwitch = new JsSwitch(expression4, arrayList5);
                    jsReturn = jsSwitch;
                    break;
                case 12:
                    jsReturn2 = new JsWhile(readExpression(), readStatement());
                    jsReturn = jsReturn2;
                    break;
                case 13:
                    jsReturn2 = new JsDoWhile(readExpression(), readStatement());
                    jsReturn = jsReturn2;
                    break;
                case 14:
                    JsExpression expression5 = readBoolean() ? readExpression() : null;
                    JsExpression expression6 = readBoolean() ? readExpression() : null;
                    JsStatement statement2 = readBoolean() ? readStatement() : null;
                    JsFor jsFor2 = readBoolean() ? new JsFor(readVars(), expression5, expression6, statement2) : null;
                    if (jsFor2 == null) {
                        jsFor2 = new JsFor(readBoolean() ? readExpression() : null, expression5, expression6, statement2);
                    }
                    jsForIn = jsFor2;
                    jsReturn = jsForIn;
                    break;
                case 15:
                    jsForIn = new JsForIn(readBoolean() ? this.nameTable[readInt()] : null, readBoolean() ? readExpression() : null, readExpression(), readStatement());
                    jsReturn = jsForIn;
                    break;
                case 16:
                    JsBlock block2 = readBlock();
                    int i25 = readInt();
                    ArrayList arrayList6 = new ArrayList(i25);
                    for (int i26 = 0; i26 < i25; i26++) {
                        JsCatch jsCatch2 = new JsCatch(this.nameTable[readInt()]);
                        jsCatch2.setBody(readBlock());
                        arrayList6.add(jsCatch2);
                    }
                    jsSwitch = new JsTry(block2, arrayList6, readBoolean() ? readBlock() : null);
                    jsReturn = jsSwitch;
                    break;
                case 17:
                    jsReturn2 = JsEmpty.INSTANCE;
                    jsReturn = jsReturn2;
                    break;
                case 18:
                    jsReturn2 = new JsSingleLineComment(readString());
                    jsReturn = jsReturn2;
                    break;
                case 19:
                    jsReturn2 = new JsMultiLineComment(readString());
                    jsReturn = jsReturn2;
                    break;
                case 20:
                    String string2 = readString();
                    byte b5 = readByte();
                    if (b5 == 0) {
                        JsNameRef jsNameRefMakeRef4 = this.nameTable[readInt()].makeRef();
                        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef4, "makeRef(...)");
                        all = new JsImport.Target.All(jsNameRefMakeRef4);
                    } else if (b5 == 1) {
                        int i27 = readInt();
                        ArrayList arrayList7 = new ArrayList(i27);
                        for (int i28 = 0; i28 < i27; i28++) {
                            arrayList7.add(new JsImport.Element(this.nameTable[readInt()], readBoolean() ? this.nameTable[readInt()].makeRef() : null));
                        }
                        all = new JsImport.Target.Elements(CollectionsKt.toMutableList((Collection) arrayList7));
                    } else {
                        if (b5 != 2) {
                            throw new IllegalStateException(("Unknown JsImport type " + ((int) b5)).toString());
                        }
                        JsNameRef jsNameRefMakeRef5 = this.nameTable[readInt()].makeRef();
                        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef5, "makeRef(...)");
                        all = new JsImport.Target.Default(jsNameRefMakeRef5);
                    }
                    jsIf = new JsImport(string2, all);
                    jsReturn = jsIf;
                    break;
                case 21:
                    byte b6 = readByte();
                    if (b6 == 0) {
                        elements = JsExport.Subject.All.INSTANCE;
                    } else {
                        if (b6 != 1) {
                            throw new IllegalStateException(("Unknown JsExport type " + ((int) b6)).toString());
                        }
                        int i29 = readInt();
                        ArrayList arrayList8 = new ArrayList(i29);
                        for (int i30 = 0; i30 < i29; i30++) {
                            JsNameRef jsNameRefMakeRef6 = this.nameTable[readInt()].makeRef();
                            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef6, "makeRef(...)");
                            arrayList8.add(new JsExport.Element(jsNameRefMakeRef6, readBoolean() ? this.nameTable[readInt()] : null));
                        }
                        elements = new JsExport.Subject.Elements(arrayList8);
                    }
                    jsSwitch = new JsExport(elements, readBoolean() ? readString() : null);
                    jsReturn = jsSwitch;
                    break;
                default:
                    throw new IllegalStateException(("Unknown statement id: " + ((int) b4)).toString());
            }
        }
        if (readBoolean()) {
            int i31 = readInt();
            JsComment[] jsCommentArr = new JsComment[i31];
            for (int i32 = 0; i32 < i31; i32++) {
                jsCommentArr[i32] = readComment();
            }
            jsReturn.setCommentsBeforeNode(ArraysKt.toList(jsCommentArr));
            Unit unit3 = Unit.INSTANCE;
        }
        if (readBoolean()) {
            int i33 = readInt();
            JsComment[] jsCommentArr2 = new JsComment[i33];
            for (int i34 = 0; i34 < i33; i34++) {
                jsCommentArr2[i34] = readComment();
            }
            jsReturn.setCommentsAfterNode(ArraysKt.toList(jsCommentArr2));
            Unit unit4 = Unit.INSTANCE;
        }
        JsStatement jsStatement = (JsStatement) jsReturn;
        MetadataProperties.setSynthetic((HasMetadata) jsStatement, readBoolean());
        return jsStatement;
    }

    private final JsExpression readExpression() {
        JsNode jsNode;
        JsThisRef jsThisRef;
        JsLocation jsLocation;
        JsThisRef jsThisRef2;
        if (readBoolean()) {
            String str = readBoolean() ? this.stringTable[readInt()] : null;
            String strPeek = str == null ? this.fileStack.peek() : str;
            int i = readInt();
            int i2 = readInt();
            if (strPeek != null) {
                Intrinsics.checkNotNull(strPeek);
                jsLocation = new JsLocation(strPeek, i, i2, null, 8, null);
            } else {
                jsLocation = null;
            }
            boolean z = (str == null || Intrinsics.areEqual(str, this.fileStack.peek())) ? false : true;
            if (z) {
                this.fileStack.push(str);
            }
            ExpressionIds expressionIds = ExpressionIds.INSTANCE;
            byte b = readByte();
            switch (b) {
                case 0:
                    jsThisRef2 = new JsThisRef();
                    break;
                case 1:
                    jsThisRef2 = new JsNullLiteral();
                    break;
                case 2:
                    jsThisRef2 = new JsBooleanLiteral(true);
                    break;
                case 3:
                    jsThisRef2 = new JsBooleanLiteral(false);
                    break;
                case 4:
                    jsThisRef2 = new JsStringLiteral(this.stringTable[readInt()]);
                    break;
                case 5:
                    JsRegExp jsRegExp = new JsRegExp();
                    jsRegExp.setPattern(this.stringTable[readInt()]);
                    if (readBoolean()) {
                        jsRegExp.setFlags(this.stringTable[readInt()]);
                        Unit unit = Unit.INSTANCE;
                    }
                    jsThisRef2 = jsRegExp;
                    break;
                case 6:
                    jsThisRef2 = new JsIntLiteral(readInt());
                    break;
                case 7:
                    jsThisRef2 = new JsDoubleLiteral(readDouble());
                    break;
                case 8:
                    int i3 = readInt();
                    ArrayList arrayList = new ArrayList(i3);
                    for (int i4 = 0; i4 < i3; i4++) {
                        arrayList.add(readExpression());
                    }
                    jsThisRef2 = new JsArrayLiteral(arrayList);
                    break;
                case 9:
                    int i5 = readInt();
                    ArrayList arrayList2 = new ArrayList(i5);
                    for (int i6 = 0; i6 < i5; i6++) {
                        arrayList2.add(new JsPropertyInitializer(readExpression(), readExpression()));
                    }
                    jsThisRef2 = new JsObjectLiteral(arrayList2, readBoolean());
                    break;
                case 10:
                    jsThisRef2 = readFunction();
                    break;
                case 11:
                    HashMap map = new HashMap();
                    int i7 = readInt();
                    while (true) {
                        int i8 = i7 - 1;
                        if (i7 <= 0) {
                            jsThisRef2 = new JsDocComment(map);
                        } else {
                            HashMap map2 = map;
                            String str2 = this.stringTable[readInt()];
                            Object expression = readBoolean() ? readExpression() : null;
                            if (expression == null) {
                                expression = this.stringTable[readInt()];
                            }
                            map2.put(str2, expression);
                            i7 = i8;
                        }
                        break;
                    }
                    break;
                case 12:
                    jsThisRef2 = new JsBinaryOperation(this.jsBinaryOperatorValues[readByte()], readExpression(), readExpression());
                    break;
                case 13:
                    jsThisRef2 = new JsPrefixOperation(this.jsUnaryOperatorValues[readByte()], readExpression());
                    break;
                case 14:
                    jsThisRef2 = new JsPostfixOperation(this.jsUnaryOperatorValues[readByte()], readExpression());
                    break;
                case 15:
                    jsThisRef2 = new JsConditional(readExpression(), readExpression(), readExpression());
                    break;
                case 16:
                    jsThisRef2 = new JsArrayAccess(readExpression(), readExpression());
                    break;
                case 17:
                    JsNameRef jsNameRef = new JsNameRef(this.nameTable[readInt()]);
                    if (readBoolean()) {
                        jsNameRef.setQualifier(readExpression());
                        Unit unit2 = Unit.INSTANCE;
                    }
                    if (readBoolean()) {
                        MetadataProperties.setInline(jsNameRef, Boolean.valueOf(readBoolean()));
                        Unit unit3 = Unit.INSTANCE;
                    }
                    jsThisRef2 = jsNameRef;
                    break;
                case 18:
                    jsThisRef2 = new JsNameRef(this.nameTable[readInt()]);
                    break;
                case 19:
                    JsNameRef jsNameRef2 = new JsNameRef(this.stringTable[readInt()]);
                    if (readBoolean()) {
                        jsNameRef2.setQualifier(readExpression());
                        Unit unit4 = Unit.INSTANCE;
                    }
                    if (readBoolean()) {
                        MetadataProperties.setInline(jsNameRef2, Boolean.valueOf(readBoolean()));
                        Unit unit5 = Unit.INSTANCE;
                    }
                    jsThisRef2 = jsNameRef2;
                    break;
                case 20:
                    JsExpression expression2 = readExpression();
                    int i9 = readInt();
                    ArrayList arrayList3 = new ArrayList(i9);
                    for (int i10 = 0; i10 < i9; i10++) {
                        arrayList3.add(readExpression());
                    }
                    JsInvocation jsInvocation = new JsInvocation(expression2, arrayList3);
                    if (readBoolean()) {
                        MetadataProperties.setInline(jsInvocation, Boolean.valueOf(readBoolean()));
                        Unit unit6 = Unit.INSTANCE;
                    }
                    jsThisRef2 = jsInvocation;
                    break;
                case 21:
                    JsExpression expression3 = readExpression();
                    int i11 = readInt();
                    ArrayList arrayList4 = new ArrayList(i11);
                    for (int i12 = 0; i12 < i11; i12++) {
                        arrayList4.add(readExpression());
                    }
                    jsThisRef2 = new JsNew(expression3, arrayList4);
                    break;
                case 22:
                    JsClass jsClass = new JsClass(readBoolean() ? this.nameTable[readInt()] : null, readBoolean() ? readExpression() : null, readBoolean() ? readFunction() : null, null, 8, null);
                    int i13 = readInt();
                    while (true) {
                        int i14 = i13 - 1;
                        if (i13 <= 0) {
                            jsThisRef2 = jsClass;
                        } else {
                            jsClass.getMembers().add(readFunction());
                            i13 = i14;
                        }
                        break;
                    }
                    break;
                case 23:
                    jsThisRef2 = new JsSuperRef();
                    break;
                default:
                    throw new IllegalStateException(("Unknown expression id: " + ((int) b)).toString());
            }
            jsNode = jsThisRef2;
            if (jsLocation != null) {
                jsNode.setSource(jsLocation);
            }
            if (z) {
                this.fileStack.pop();
            }
        } else {
            jsNode = null;
        }
        if (jsNode == null) {
            ExpressionIds expressionIds2 = ExpressionIds.INSTANCE;
            byte b2 = readByte();
            switch (b2) {
                case 0:
                    jsThisRef = new JsThisRef();
                    break;
                case 1:
                    jsThisRef = new JsNullLiteral();
                    break;
                case 2:
                    jsThisRef = new JsBooleanLiteral(true);
                    break;
                case 3:
                    jsThisRef = new JsBooleanLiteral(false);
                    break;
                case 4:
                    jsThisRef = new JsStringLiteral(this.stringTable[readInt()]);
                    break;
                case 5:
                    JsRegExp jsRegExp2 = new JsRegExp();
                    jsRegExp2.setPattern(this.stringTable[readInt()]);
                    if (readBoolean()) {
                        jsRegExp2.setFlags(this.stringTable[readInt()]);
                        Unit unit7 = Unit.INSTANCE;
                    }
                    jsThisRef = jsRegExp2;
                    break;
                case 6:
                    jsThisRef = new JsIntLiteral(readInt());
                    break;
                case 7:
                    jsThisRef = new JsDoubleLiteral(readDouble());
                    break;
                case 8:
                    int i15 = readInt();
                    ArrayList arrayList5 = new ArrayList(i15);
                    for (int i16 = 0; i16 < i15; i16++) {
                        arrayList5.add(readExpression());
                    }
                    jsThisRef = new JsArrayLiteral(arrayList5);
                    break;
                case 9:
                    int i17 = readInt();
                    ArrayList arrayList6 = new ArrayList(i17);
                    for (int i18 = 0; i18 < i17; i18++) {
                        arrayList6.add(new JsPropertyInitializer(readExpression(), readExpression()));
                    }
                    jsThisRef = new JsObjectLiteral(arrayList6, readBoolean());
                    break;
                case 10:
                    jsThisRef = readFunction();
                    break;
                case 11:
                    HashMap map3 = new HashMap();
                    int i19 = readInt();
                    while (true) {
                        int i20 = i19 - 1;
                        if (i19 <= 0) {
                            jsThisRef = new JsDocComment(map3);
                        } else {
                            HashMap map4 = map3;
                            String str3 = this.stringTable[readInt()];
                            Object expression4 = readBoolean() ? readExpression() : null;
                            if (expression4 == null) {
                                expression4 = this.stringTable[readInt()];
                            }
                            map4.put(str3, expression4);
                            i19 = i20;
                        }
                        break;
                    }
                    break;
                case 12:
                    jsThisRef = new JsBinaryOperation(this.jsBinaryOperatorValues[readByte()], readExpression(), readExpression());
                    break;
                case 13:
                    jsThisRef = new JsPrefixOperation(this.jsUnaryOperatorValues[readByte()], readExpression());
                    break;
                case 14:
                    jsThisRef = new JsPostfixOperation(this.jsUnaryOperatorValues[readByte()], readExpression());
                    break;
                case 15:
                    jsThisRef = new JsConditional(readExpression(), readExpression(), readExpression());
                    break;
                case 16:
                    jsThisRef = new JsArrayAccess(readExpression(), readExpression());
                    break;
                case 17:
                    JsNameRef jsNameRef3 = new JsNameRef(this.nameTable[readInt()]);
                    if (readBoolean()) {
                        jsNameRef3.setQualifier(readExpression());
                        Unit unit8 = Unit.INSTANCE;
                    }
                    if (readBoolean()) {
                        MetadataProperties.setInline(jsNameRef3, Boolean.valueOf(readBoolean()));
                        Unit unit9 = Unit.INSTANCE;
                    }
                    jsThisRef = jsNameRef3;
                    break;
                case 18:
                    jsThisRef = new JsNameRef(this.nameTable[readInt()]);
                    break;
                case 19:
                    JsNameRef jsNameRef4 = new JsNameRef(this.stringTable[readInt()]);
                    if (readBoolean()) {
                        jsNameRef4.setQualifier(readExpression());
                        Unit unit10 = Unit.INSTANCE;
                    }
                    if (readBoolean()) {
                        MetadataProperties.setInline(jsNameRef4, Boolean.valueOf(readBoolean()));
                        Unit unit11 = Unit.INSTANCE;
                    }
                    jsThisRef = jsNameRef4;
                    break;
                case 20:
                    JsExpression expression5 = readExpression();
                    int i21 = readInt();
                    ArrayList arrayList7 = new ArrayList(i21);
                    for (int i22 = 0; i22 < i21; i22++) {
                        arrayList7.add(readExpression());
                    }
                    JsInvocation jsInvocation2 = new JsInvocation(expression5, arrayList7);
                    if (readBoolean()) {
                        MetadataProperties.setInline(jsInvocation2, Boolean.valueOf(readBoolean()));
                        Unit unit12 = Unit.INSTANCE;
                    }
                    jsThisRef = jsInvocation2;
                    break;
                case 21:
                    JsExpression expression6 = readExpression();
                    int i23 = readInt();
                    ArrayList arrayList8 = new ArrayList(i23);
                    for (int i24 = 0; i24 < i23; i24++) {
                        arrayList8.add(readExpression());
                    }
                    jsThisRef = new JsNew(expression6, arrayList8);
                    break;
                case 22:
                    JsClass jsClass2 = new JsClass(readBoolean() ? this.nameTable[readInt()] : null, readBoolean() ? readExpression() : null, readBoolean() ? readFunction() : null, null, 8, null);
                    int i25 = readInt();
                    while (true) {
                        int i26 = i25 - 1;
                        if (i25 <= 0) {
                            jsThisRef = jsClass2;
                        } else {
                            jsClass2.getMembers().add(readFunction());
                            i25 = i26;
                        }
                        break;
                    }
                    break;
                case 23:
                    jsThisRef = new JsSuperRef();
                    break;
                default:
                    throw new IllegalStateException(("Unknown expression id: " + ((int) b2)).toString());
            }
            jsNode = jsThisRef;
        }
        JsExpression jsExpression = (JsExpression) jsNode;
        if (readBoolean()) {
            int i27 = readInt();
            JsComment[] jsCommentArr = new JsComment[i27];
            for (int i28 = 0; i28 < i27; i28++) {
                jsCommentArr[i28] = readComment();
            }
            jsExpression.setCommentsBeforeNode(ArraysKt.toList(jsCommentArr));
            Unit unit13 = Unit.INSTANCE;
        }
        if (readBoolean()) {
            int i29 = readInt();
            JsComment[] jsCommentArr2 = new JsComment[i29];
            for (int i30 = 0; i30 < i29; i30++) {
                jsCommentArr2[i30] = readComment();
            }
            jsExpression.setCommentsAfterNode(ArraysKt.toList(jsCommentArr2));
            Unit unit14 = Unit.INSTANCE;
        }
        JsExpression jsExpression2 = jsExpression;
        JsExpression jsExpression3 = jsExpression2;
        MetadataProperties.setSynthetic(jsExpression3, readBoolean());
        MetadataProperties.setSideEffects(jsExpression3, this.sideEffectKindValues[readByte()]);
        if (readBoolean()) {
            MetadataProperties.setLocalAlias(jsExpression2, readJsImportedModule());
            Unit unit15 = Unit.INSTANCE;
        }
        return jsExpression2;
    }

    private final JsBlock readBlock() {
        JsCompositeBlock compositeBlock = readBoolean() ? readCompositeBlock() : null;
        if (compositeBlock != null) {
            return compositeBlock;
        }
        JsBlock jsBlock = new JsBlock();
        int i = readInt();
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return jsBlock;
            }
            List<JsStatement> statements = jsBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add(readStatement());
            i = i2;
        }
    }
}
