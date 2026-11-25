package org.catrobat.catroid.utils.lunoscript;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.beust.jcommander.Parameters;
import com.google.android.material.badge.BadgeDrawable;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.UserVarsManager;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.AddEditBrick;
import org.catrobat.catroid.content.bricks.AddItemToUserListBrick;
import org.catrobat.catroid.content.bricks.AddRadioBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.AskGPTBrick;
import org.catrobat.catroid.content.bricks.AskGemini2Brick;
import org.catrobat.catroid.content.bricks.AskGeminiBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CloneBrick;
import org.catrobat.catroid.content.bricks.ShowToastBlock;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InternTokenType;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.ErrorLog;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.SpecialNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Marker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter.class
  classes14.dex
 */
/* JADX INFO: compiled from: Interpreter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000¸\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J,\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00172\u0006\u0010\u0018\u001a\u00020\u000fJ:\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u000f2\u0018\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001eH\u0002J_\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020%2@\u0010&\u001a<\u0012\u0013\u0012\u00110\u0000¢\u0006\f\b'\u0012\b\b#\u0012\u0004\b\b((\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u00120\u0017¢\u0006\f\b'\u0012\b\b#\u0012\u0004\b\b()\u0012\u0004\u0012\u00020\u00120\u001ej\u0002`*H\u0000¢\u0006\u0002\b+J\u0010\u0010,\u001a\u00020\u00122\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\u00122\u0006\u0010-\u001a\u000200H\u0002J(\u00101\u001a\u00020\u00122\u0006\u00102\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u0002032\u0006\u00104\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u000fH\u0002J\u001a\u00105\u001a\u00020\u00122\u0006\u0010-\u001a\u0002062\b\b\u0002\u00107\u001a\u00020 H\u0002J\u001a\u00108\u001a\u00020\u00122\u0006\u0010-\u001a\u0002092\b\b\u0002\u00107\u001a\u00020 H\u0002J\u0010\u0010:\u001a\u00020;2\u0006\u0010-\u001a\u00020<H\u0002J\u0010\u0010=\u001a\u00020\u00122\u0006\u0010-\u001a\u00020>H\u0002J\u0010\u0010?\u001a\u00020\u00122\u0006\u0010-\u001a\u00020@H\u0002J\u0010\u0010A\u001a\u00020\"2\u0006\u0010B\u001a\u00020CH\u0002J\u0010\u0010D\u001a\u00020\"2\u0006\u0010E\u001a\u00020FH\u0002J\u001c\u0010G\u001a\u00020\"2\f\u0010H\u001a\b\u0012\u0004\u0012\u00020C0\u00172\u0006\u0010I\u001a\u00020\bJ\u0010\u0010J\u001a\u00020\"2\u0006\u0010E\u001a\u00020KH\u0002J\u0010\u0010L\u001a\u00020\"2\u0006\u0010E\u001a\u00020MH\u0002J\u0010\u0010N\u001a\u00020\"2\u0006\u0010E\u001a\u00020OH\u0002J\u0010\u0010P\u001a\u00020\"2\u0006\u0010E\u001a\u00020QH\u0002J\u0010\u0010R\u001a\u00020\"2\u0006\u0010E\u001a\u00020SH\u0002J\u0010\u0010T\u001a\u00020\"2\u0006\u0010E\u001a\u00020UH\u0002J\u0010\u0010V\u001a\u00020\"2\u0006\u0010E\u001a\u00020WH\u0002J\u0010\u0010X\u001a\u00020\"2\u0006\u0010E\u001a\u00020YH\u0002J\u0010\u0010Z\u001a\u00020\"2\u0006\u0010E\u001a\u00020[H\u0002J\u0010\u0010\\\u001a\u00020\"2\u0006\u0010E\u001a\u00020]H\u0002J\u0010\u0010^\u001a\u00020\"2\u0006\u0010E\u001a\u00020_H\u0002J\u0010\u0010`\u001a\u00020\"2\u0006\u0010E\u001a\u00020aH\u0002J\u001e\u0010b\u001a\u00020\u000f2\u0006\u0010c\u001a\u00020\u00122\u0006\u0010d\u001a\u00020\u00152\u0006\u0010e\u001a\u00020\u000fJ\u0010\u0010f\u001a\u0004\u0018\u00010g2\u0006\u0010h\u001a\u00020\u0012J\u000e\u0010i\u001a\u00020\"2\u0006\u0010j\u001a\u00020kJ\u0018\u0010l\u001a\u00020 2\u0006\u0010m\u001a\u00020\u00122\u0006\u0010n\u001a\u00020\u0012H\u0002J\u0018\u0010o\u001a\u00020\u00122\u0006\u0010p\u001a\u0002032\u0006\u0010q\u001a\u00020\u000eH\u0002J\u001c\u0010r\u001a\u0004\u0018\u00010\u00012\u0006\u0010s\u001a\u00020\u00122\n\u0010t\u001a\u0006\u0012\u0002\b\u00030uJ\u001a\u0010v\u001a\u00020\u00152\b\u0010s\u001a\u0004\u0018\u00010\u00122\b\b\u0002\u0010w\u001a\u00020 J\u000e\u0010x\u001a\u00020y2\u0006\u0010z\u001a\u00020\u0015J:\u0010{\u001a\u00020|2\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u000f2\u0018\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f0\u001eH\u0002J(\u0010}\u001a\u00020\u00122\u0006\u0010~\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u0002032\u0006\u00104\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u000fH\u0002J\u0012\u0010\u007f\u001a\u0004\u0018\u00010\u00012\u0006\u0010s\u001a\u00020\u0012H\u0002R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0080\u0001"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "", "androidContext", "Landroid/content/Context;", "projectScope", "Lorg/catrobat/catroid/content/Scope;", "(Landroid/content/Context;Lorg/catrobat/catroid/content/Scope;)V", "currentScope", "Lorg/catrobat/catroid/utils/lunoscript/Scope;", "globals", "getGlobals", "()Lorg/catrobat/catroid/utils/lunoscript/Scope;", "locals", "", "Lorg/catrobat/catroid/utils/lunoscript/AstNode;", "", "loopDepth", "callNativeMethod", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "instanceOrClass", "methodName", "", "lunoArgs", "", Constant.LINE, "cmpOp", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Boolean;", "l", "r", "op", "Lkotlin/Function2;", "", "", "defineNative", "", "name", "arity", "Lkotlin/ranges/IntRange;", "function", "Lkotlin/ParameterName;", "interpreter", "arguments", "Lorg/catrobat/catroid/utils/lunoscript/RawNativeLunoFunction;", "defineNative$catroid_apktemplateTemplateRelease", "evaluate", "expr", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "evaluateCallExpr", "Lorg/catrobat/catroid/utils/lunoscript/CallExpr;", "evaluateCompoundAssignment", "current", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "right", "evaluateGetExpr", "Lorg/catrobat/catroid/utils/lunoscript/GetExpr;", "allowUndefined", "evaluateIndexAccessExpr", "Lorg/catrobat/catroid/utils/lunoscript/IndexAccessExpr;", "evaluateInterpolatedString", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$String;", "Lorg/catrobat/catroid/utils/lunoscript/InterpolatedStringExpr;", "evaluateLogicalExpr", "Lorg/catrobat/catroid/utils/lunoscript/LogicalExpr;", "evaluateUnaryExpr", "Lorg/catrobat/catroid/utils/lunoscript/UnaryExpr;", "execute", "statement", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "executeAssignment", "stmt", "Lorg/catrobat/catroid/utils/lunoscript/AssignmentStatement;", "executeBlock", "statements", "environment", "executeBreakStatement", "Lorg/catrobat/catroid/utils/lunoscript/BreakStatement;", "executeClassDeclaration", "Lorg/catrobat/catroid/utils/lunoscript/ClassDeclarationStatement;", "executeContinueStatement", "Lorg/catrobat/catroid/utils/lunoscript/ContinueStatement;", "executeForInStatement", "Lorg/catrobat/catroid/utils/lunoscript/ForInStatement;", "executeFunDeclaration", "Lorg/catrobat/catroid/utils/lunoscript/FunDeclarationStatement;", "executeIfStatement", "Lorg/catrobat/catroid/utils/lunoscript/IfStatement;", "executeImportStatement", "Lorg/catrobat/catroid/utils/lunoscript/ImportStatement;", "executeReturnStatement", "Lorg/catrobat/catroid/utils/lunoscript/ReturnStatement;", "executeSwitchStatement", "Lorg/catrobat/catroid/utils/lunoscript/SwitchStatement;", "executeTryCatchStatement", "Lorg/catrobat/catroid/utils/lunoscript/TryCatchStatement;", "executeVarDeclaration", "Lorg/catrobat/catroid/utils/lunoscript/VarDeclarationStatement;", "executeWhileStatement", "Lorg/catrobat/catroid/utils/lunoscript/WhileStatement;", "getKotlinIntFromLunoValue", "lunoVal", "functionNameForError", "argPositionForError", "getProjectFromLunoValue", "Lorg/catrobat/catroid/content/Project;", "lunoArg", "interpret", ConfigConstants.CONFIG_KEY_PROGRAM, "Lorg/catrobat/catroid/utils/lunoscript/ProgramNode;", "isEqual", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "lookUpVariable", "nameToken", "exprNode", "lunoValueToKotlin", "value", "targetClass", "Ljava/lang/Class;", "lunoValueToString", "humanReadable", "mapStringToInternTokenType", "Lorg/catrobat/catroid/formulaeditor/InternTokenType;", "typeName", "numOp", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue$Number;", "operateBinary", "left", "toKotlinType", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Interpreter {
    private final Context androidContext;
    private Scope currentScope;
    private final Scope globals;
    private final Map<AstNode, Integer> locals;
    private int loopDepth;
    private final org.catrobat.catroid.content.Scope projectScope;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: Interpreter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TokenType.values().length];
            try {
                iArr[TokenType.PLUS_ASSIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TokenType.MINUS_ASSIGN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TokenType.MULTIPLY_ASSIGN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[TokenType.DIVIDE_ASSIGN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[TokenType.MODULO_ASSIGN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[TokenType.PLUS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[TokenType.MINUS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[TokenType.MULTIPLY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[TokenType.DIVIDE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[TokenType.MODULO.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[TokenType.GT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[TokenType.GTE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[TokenType.LT.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[TokenType.LTE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[TokenType.BANG.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Interpreter() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Interpreter(Context context, org.catrobat.catroid.content.Scope scope) {
        this.androidContext = context;
        this.projectScope = scope;
        Scope scope2 = new Scope(null, 1, 0 == true ? 1 : 0);
        this.globals = scope2;
        this.currentScope = scope2;
        this.locals = new LinkedHashMap();
        defineNative$catroid_apktemplateTemplateRelease("GetAppContext", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.1
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                Context appContext = CatroidApplication.getAppContext();
                Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
                return new LunoValue.NativeObject(appContext);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetStageListener", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.2
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                StageListener stageListener;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                StageActivity stageActivity = StageActivity.activeStageActivity.get();
                if (stageActivity != null && (stageListener = stageActivity.stageListener) != null) {
                    new LunoValue.NativeObject(stageListener);
                }
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("StageActivity", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.3
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                WeakReference<StageActivity> activeStageActivity = StageActivity.activeStageActivity;
                Intrinsics.checkNotNullExpressionValue(activeStageActivity, "activeStageActivity");
                return new LunoValue.NativeObject(activeStageActivity);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("MakeToast", new IntRange(1, Integer.MAX_VALUE), new AnonymousClass4());
        defineNative$catroid_apktemplateTemplateRelease("delay", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.5
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("delay expects a number (milliseconds).", -1, null, 4, null);
                }
                try {
                    Thread.sleep((long) number.getValue());
                } catch (InterruptedException unused) {
                }
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("print", new IntRange(0, Integer.MAX_VALUE), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.6
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                final Interpreter interpreter2 = Interpreter.this;
                Log.d("LunoScript", CollectionsKt.joinToString$default(args, " ", null, null, 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.6.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(LunoValue it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return interpreter2.lunoValueToString(it, true);
                    }
                }, 30, null));
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("error", new IntRange(0, Integer.MAX_VALUE), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.7
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                ErrorLog errorLog = ErrorLog.INSTANCE;
                final Interpreter interpreter2 = Interpreter.this;
                errorLog.log(CollectionsKt.joinToString$default(args, " ", null, null, 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.7.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(LunoValue it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return interpreter2.lunoValueToString(it, true);
                    }
                }, 30, null));
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("len", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.8
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.String) {
                    return new LunoValue.Number(((LunoValue.String) lunoValue).getValue().length());
                }
                if (lunoValue instanceof LunoValue.List) {
                    return new LunoValue.Number(((LunoValue.List) lunoValue).getElements().size());
                }
                if (lunoValue instanceof LunoValue.LunoObject) {
                    return new LunoValue.Number(((LunoValue.LunoObject) lunoValue).getFields().size());
                }
                throw new LunoRuntimeError("Unsupported type for len(): " + Reflection.getOrCreateKotlinClass(lunoValue.getClass()).getSimpleName(), -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("CreateEmptyProjectContext", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.9
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                Project project = new Project(CatroidApplication.getAppContext(), "Temp");
                project.getSceneList().clear();
                project.getUserVariables().clear();
                project.getUserLists().clear();
                return new LunoValue.NativeObject(project);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SetGlobalProject", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.10
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.NativeObject nativeObject = lunoValue instanceof LunoValue.NativeObject ? (LunoValue.NativeObject) lunoValue : null;
                if (nativeObject == null) {
                    throw new LunoRuntimeError("SetGlobalProject: Argument must be NativeObject", -1, null, 4, null);
                }
                Object obj = nativeObject.getObj();
                Project project = obj instanceof Project ? (Project) obj : null;
                if (project == null) {
                    throw new LunoRuntimeError("SetGlobalProject: Object is not a Project", -1, null, 4, null);
                }
                ProjectManager.getInstance().setCurrentProject(project);
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("getBytes", new IntRange(1, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.11
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.String) {
                    Charset charset = Charsets.UTF_8;
                    if (args.size() > 1) {
                        LunoValue lunoValue2 = args.get(1);
                        if (lunoValue2 instanceof LunoValue.NativeObject) {
                            Object obj = ((LunoValue.NativeObject) lunoValue2).getObj();
                            if (obj instanceof Charset) {
                                charset = (Charset) obj;
                            } else {
                                throw new LunoRuntimeError("Argument 2 must be 'java. nio. charset'. Got: " + Reflection.getOrCreateKotlinClass(obj.getClass()), -1, null, 4, null);
                            }
                        } else {
                            throw new LunoRuntimeError("Argument 2 must be 'NativeOject'. Got: " + Reflection.getOrCreateKotlinClass(lunoValue2.getClass()).getSimpleName(), -1, null, 4, null);
                        }
                    }
                    byte[] bytes = ((LunoValue.String) lunoValue).getValue().getBytes(charset);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    return new LunoValue.NativeObject(bytes);
                }
                throw new LunoRuntimeError("Argument 1 for 'getBytes()' must be String. Got: " + Reflection.getOrCreateKotlinClass(lunoValue.getClass()).getSimpleName(), -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("bytesSize", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.12
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.NativeObject) {
                    Object obj = ((LunoValue.NativeObject) lunoValue).getObj();
                    if (obj instanceof byte[]) {
                        return new LunoValue.Number(((byte[]) obj).length);
                    }
                    throw new LunoRuntimeError("Argument 1 must be 'ByteArray'. Got: " + Reflection.getOrCreateKotlinClass(obj.getClass()), -1, null, 4, null);
                }
                throw new LunoRuntimeError("Argument 1 must be 'NativeOject'. Got: " + Reflection.getOrCreateKotlinClass(lunoValue.getClass()).getSimpleName(), -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("typeof", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.13
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.String) {
                    return new LunoValue.String(CommonClassNames.JAVA_LANG_STRING_SHORT);
                }
                if (lunoValue instanceof LunoValue.List) {
                    return new LunoValue.String("List");
                }
                if (lunoValue instanceof LunoValue.LunoObject) {
                    return new LunoValue.String(CommonClassNames.JAVA_LANG_OBJECT_SHORT);
                }
                if (lunoValue instanceof LunoValue.Number) {
                    return new LunoValue.String("Number");
                }
                if (lunoValue instanceof LunoValue.Boolean) {
                    return new LunoValue.String("Boolean");
                }
                if (lunoValue instanceof LunoValue.LunoClass) {
                    return new LunoValue.String("Class");
                }
                if (lunoValue instanceof LunoValue.Callable) {
                    return new LunoValue.String("Callable");
                }
                if (lunoValue instanceof LunoValue.LunoFunction) {
                    return new LunoValue.String("Function");
                }
                if (lunoValue instanceof LunoValue.Null) {
                    return new LunoValue.String("Null");
                }
                if (lunoValue instanceof LunoValue.NativeObject) {
                    return new LunoValue.String("NativeObject");
                }
                if (lunoValue instanceof LunoValue.NativeCallable) {
                    return new LunoValue.String("NativeCallable");
                }
                if (lunoValue instanceof LunoValue.Float) {
                    return new LunoValue.String("Float");
                }
                if (lunoValue instanceof LunoValue.NativeClass) {
                    return new LunoValue.String("NativeClass");
                }
                if (!(lunoValue instanceof LunoValue.BoundMethod) && !(lunoValue instanceof LunoValue.BoundMethod2)) {
                    return LunoValue.Null.INSTANCE;
                }
                return new LunoValue.String("BoundMethod");
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("parseInt", new IntRange(1, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.14
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.String)) {
                    throw new LunoRuntimeError("parseInt expects a string as the first argument.", -1, null, 4, null);
                }
                LunoValue number = args.size() > 1 ? args.get(1) : new LunoValue.Number(10.0d);
                if (!(number instanceof LunoValue.Number)) {
                    throw new LunoRuntimeError("parseInt radix (second argument) must be a number.", -1, null, 4, null);
                }
                int value = (int) ((LunoValue.Number) number).getValue();
                if (value < 2 || value > 36) {
                    throw new LunoRuntimeError("parseInt radix must be between 2 and 36.", -1, null, 4, null);
                }
                try {
                    return new LunoValue.Number(Integer.parseInt(((LunoValue.String) lunoValue).getValue(), CharsKt.checkRadix(value)));
                } catch (NumberFormatException unused) {
                    return new LunoValue.Number(Double.NaN);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("parseFloat", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.15
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                String strLunoValueToString;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.String) {
                    strLunoValueToString = ((LunoValue.String) lunoValue).getValue();
                } else {
                    if (lunoValue instanceof LunoValue.Number) {
                        return lunoValue;
                    }
                    strLunoValueToString = Interpreter.this.lunoValueToString(lunoValue, true);
                }
                String string = StringsKt.trimStart((CharSequence) strLunoValueToString).toString();
                if (string.length() == 0) {
                    return new LunoValue.Number(Double.NaN);
                }
                if (StringsKt.equals(string, "Infinity", true) || StringsKt.equals(string, "+Infinity", true)) {
                    return new LunoValue.Number(Double.POSITIVE_INFINITY);
                }
                if (StringsKt.equals(string, "-Infinity", true)) {
                    return new LunoValue.Number(Double.NEGATIVE_INFINITY);
                }
                if (StringsKt.equals(string, "NaN", true)) {
                    return new LunoValue.Number(Double.NaN);
                }
                StringBuilder sb = new StringBuilder();
                int length = string.length();
                boolean z = false;
                boolean z2 = false;
                boolean z3 = false;
                boolean z4 = false;
                for (int i = 0; i < length; i++) {
                    char cCharAt = string.charAt(i);
                    if (cCharAt == '+' || cCharAt == '-') {
                        if (i == 0 && !z && !z2) {
                            sb.append(cCharAt);
                            z2 = true;
                        } else {
                            Character chLastOrNull = StringsKt.lastOrNull(sb);
                            if (chLastOrNull == null || !CharsKt.equals(chLastOrNull.charValue(), 'e', true) || z) {
                                break;
                            }
                            sb.append(cCharAt);
                        }
                    } else {
                        if (Character.isDigit(cCharAt)) {
                            sb.append(cCharAt);
                            z = true;
                        } else if (cCharAt == '.') {
                            if (z3 || z4) {
                                break;
                            }
                            sb.append(cCharAt);
                            z3 = true;
                        } else {
                            if (!CharsKt.equals(cCharAt, 'e', true) || z4 || !z) {
                                break;
                            }
                            sb.append(cCharAt);
                            z4 = true;
                            z = false;
                            z2 = false;
                        }
                    }
                }
                String string2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                String str = string2;
                if (str.length() == 0 || Intrinsics.areEqual(string2, ".") || Intrinsics.areEqual(string2, Marker.ANY_NON_NULL_MARKER) || Intrinsics.areEqual(string2, Parameters.DEFAULT_OPTION_PREFIXES)) {
                    return new LunoValue.Number(Double.NaN);
                }
                if ((StringsKt.startsWith((CharSequence) str, 'e', true) || StringsKt.endsWith((CharSequence) str, 'e', true)) && string2.length() == 1) {
                    return new LunoValue.Number(Double.NaN);
                }
                if (StringsKt.endsWith$default((CharSequence) str, '+', false, 2, (Object) null) || StringsKt.endsWith$default((CharSequence) str, '-', false, 2, (Object) null)) {
                    return new LunoValue.Number(Double.NaN);
                }
                try {
                    return new LunoValue.Number(Double.parseDouble(string2));
                } catch (NumberFormatException unused) {
                    return new LunoValue.Number(Double.NaN);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("isNaN", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.16
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                return new LunoValue.Boolean(lunoValue instanceof LunoValue.Number ? Double.isNaN(((LunoValue.Number) lunoValue).getValue()) : false);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("isFinite", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.17
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                boolean z = false;
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.Number) {
                    double value = ((LunoValue.Number) lunoValue).getValue();
                    if (!Double.isInfinite(value) && !Double.isNaN(value)) {
                        z = true;
                    }
                }
                return new LunoValue.Boolean(z);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("currentTimeMillis", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.18
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                return new LunoValue.Number(System.currentTimeMillis());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("sqrt", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.19
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number != null) {
                    return number.getValue() < 0.0d ? new LunoValue.Number(Double.NaN) : new LunoValue.Number(Math.sqrt(number.getValue()));
                }
                throw new LunoRuntimeError("sqrt expects a number argument.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("abs", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.20
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("abs expects a number argument.", -1, null, 4, null);
                }
                return new LunoValue.Number(Math.abs(number.getValue()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("round", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.21
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("round expects a number argument.", -1, null, 4, null);
                }
                return new LunoValue.Number(Math.rint(number.getValue()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("floor", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.22
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("floor expects a number argument.", -1, null, 4, null);
                }
                return new LunoValue.Number(Math.floor(number.getValue()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ceil", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.23
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("ceil expects a number argument.", -1, null, 4, null);
                }
                return new LunoValue.Number(Math.ceil(number.getValue()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("random", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.24
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                return new LunoValue.Number(Random.INSTANCE.nextDouble());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease(CommonClassNames.JAVA_LANG_STRING_SHORT, new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.25
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                return new LunoValue.String(Interpreter.this.lunoValueToString(args.get(0), true));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("Number", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.26
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.Number) {
                    return lunoValue;
                }
                if (lunoValue instanceof LunoValue.String) {
                    Double doubleOrNull = StringsKt.toDoubleOrNull(((LunoValue.String) lunoValue).getValue());
                    return new LunoValue.Number(doubleOrNull != null ? doubleOrNull.doubleValue() : Double.NaN);
                }
                if (lunoValue instanceof LunoValue.Boolean) {
                    return new LunoValue.Number(((LunoValue.Boolean) lunoValue).getValue() ? 1.0d : 0.0d);
                }
                return lunoValue instanceof LunoValue.Null ? new LunoValue.Number(0.0d) : new LunoValue.Number(Double.NaN);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("Boolean", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.27
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                return new LunoValue.Boolean(args.get(0).isTruthy());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("Formula", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.28
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                return new LunoValue.NativeObject(new Formula(args.get(0).toString()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("toUpperCase", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.29
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("toUpperCase expects a string argument.", -1, null, 4, null);
                }
                String upperCase = string.getValue().toUpperCase();
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                return new LunoValue.String(upperCase);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("toLowerCase", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.30
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("toLowerCase expects a string argument.", -1, null, 4, null);
                }
                String lowerCase = string.getValue().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                return new LunoValue.String(lowerCase);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("trim", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.31
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("trim expects a string argument.", -1, null, 4, null);
                }
                return new LunoValue.String(StringsKt.trim((CharSequence) string.getValue()).toString());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("startsWith", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.32
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("startsWith expects a string as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.String string2 = lunoValue2 instanceof LunoValue.String ? (LunoValue.String) lunoValue2 : null;
                if (string2 == null) {
                    throw new LunoRuntimeError("startsWith expects a string as second argument (prefix).", -1, null, 4, null);
                }
                return new LunoValue.Boolean(StringsKt.startsWith$default(string.getValue(), string2.getValue(), false, 2, (Object) null));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("endsWith", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.33
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("endsWith expects a string as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.String string2 = lunoValue2 instanceof LunoValue.String ? (LunoValue.String) lunoValue2 : null;
                if (string2 == null) {
                    throw new LunoRuntimeError("endsWith expects a string as second argument (suffix).", -1, null, 4, null);
                }
                return new LunoValue.Boolean(StringsKt.endsWith$default(string.getValue(), string2.getValue(), false, 2, (Object) null));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("stringContains", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.34
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("stringContains expects a string as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.String string2 = lunoValue2 instanceof LunoValue.String ? (LunoValue.String) lunoValue2 : null;
                if (string2 == null) {
                    throw new LunoRuntimeError("stringContains expects a string as second argument (substring).", -1, null, 4, null);
                }
                return new LunoValue.Boolean(StringsKt.contains$default((CharSequence) string.getValue(), (CharSequence) string2.getValue(), false, 2, (Object) null));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease(SchemaSymbols.ATTVAL_REPLACE, new IntRange(3, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.35
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("replace expects a string as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.String string2 = lunoValue2 instanceof LunoValue.String ? (LunoValue.String) lunoValue2 : null;
                if (string2 == null) {
                    throw new LunoRuntimeError("replace expects a string as second argument (oldValue).", -1, null, 4, null);
                }
                LunoValue lunoValue3 = args.get(2);
                LunoValue.String string3 = lunoValue3 instanceof LunoValue.String ? (LunoValue.String) lunoValue3 : null;
                if (string3 == null) {
                    throw new LunoRuntimeError("replace expects a string as third argument (newValue).", -1, null, 4, null);
                }
                return new LunoValue.String(StringsKt.replace$default(string.getValue(), string2.getValue(), string3.getValue(), false, 4, (Object) null));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("split", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.36
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("split expects a string as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.String string2 = lunoValue2 instanceof LunoValue.String ? (LunoValue.String) lunoValue2 : null;
                if (string2 == null) {
                    throw new LunoRuntimeError("split expects a string as second argument (delimiter).", -1, null, 4, null);
                }
                List listSplit$default = StringsKt.split$default((CharSequence) string.getValue(), new String[]{string2.getValue()}, false, 0, 6, (Object) null);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                Iterator it = listSplit$default.iterator();
                while (it.hasNext()) {
                    arrayList.add(new LunoValue.String((String) it.next()));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("substring", new IntRange(2, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.37
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                int length;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null) {
                    throw new LunoRuntimeError("substring expects a string as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.Number number = lunoValue2 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue2 : null;
                if (number == null) {
                    throw new LunoRuntimeError("substring expects a number as second argument (startIndex).", -1, null, 4, null);
                }
                int value = (int) number.getValue();
                if (args.size() > 2) {
                    LunoValue lunoValue3 = args.get(2);
                    LunoValue.Number number2 = lunoValue3 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue3 : null;
                    if (number2 == null) {
                        throw new LunoRuntimeError("substring expects a number as third argument (endIndex) if provided.", -1, null, 4, null);
                    }
                    length = (int) number2.getValue();
                } else {
                    length = string.getValue().length();
                }
                try {
                    String strSubstring = string.getValue().substring(value, length);
                    Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                    return new LunoValue.String(strSubstring);
                } catch (IndexOutOfBoundsException unused) {
                    throw new LunoRuntimeError("substring: index out of bounds. Start: " + value + ", End: " + length + ", Length: " + string.getValue().length(), -1, null, 4, null);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listPush", new IntRange(2, Integer.MAX_VALUE), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.38
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list == null) {
                    throw new LunoRuntimeError("listPush expects a list as first argument.", -1, null, 4, null);
                }
                int size = args.size();
                for (int i = 1; i < size; i++) {
                    list.getElements().add(args.get(i));
                }
                return new LunoValue.Number(list.getElements().size());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listPop", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.39
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list != null) {
                    return list.getElements().isEmpty() ? LunoValue.Null.INSTANCE : list.getElements().remove(list.getElements().size() - 1);
                }
                throw new LunoRuntimeError("listPop expects a list as first argument.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listShift", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.40
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list != null) {
                    return list.getElements().isEmpty() ? LunoValue.Null.INSTANCE : list.getElements().remove(0);
                }
                throw new LunoRuntimeError("listShift expects a list as first argument.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listUnshift", new IntRange(2, Integer.MAX_VALUE), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.41
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list == null) {
                    throw new LunoRuntimeError("listUnshift expects a list as first argument.", -1, null, 4, null);
                }
                for (int size = args.size() - 1; size > 0; size--) {
                    list.getElements().add(0, args.get(size));
                }
                return new LunoValue.Number(list.getElements().size());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listJoin", new IntRange(1, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.42
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                String value;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list == null) {
                    throw new LunoRuntimeError("listJoin expects a list as first argument.", -1, null, 4, null);
                }
                String str = ",";
                LunoValue string = args.size() > 1 ? args.get(1) : new LunoValue.String(",");
                LunoValue.String string2 = string instanceof LunoValue.String ? (LunoValue.String) string : null;
                if (string2 != null && (value = string2.getValue()) != null) {
                    str = value;
                }
                final Interpreter interpreter2 = Interpreter.this;
                return new LunoValue.String(CollectionsKt.joinToString$default(list.getElements(), str, null, null, 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.42.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(LunoValue it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return interpreter2.lunoValueToString(it, true);
                    }
                }, 30, null));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listSlice", new IntRange(2, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.43
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                int size;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list == null) {
                    throw new LunoRuntimeError("listSlice expects a list as first argument.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.Number number = lunoValue2 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue2 : null;
                if (number == null) {
                    throw new LunoRuntimeError("listSlice startIndex must be a number.", -1, null, 4, null);
                }
                int value = (int) number.getValue();
                if (args.size() > 2) {
                    LunoValue lunoValue3 = args.get(2);
                    LunoValue.Number number2 = lunoValue3 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue3 : null;
                    size = number2 != null ? (int) number2.getValue() : list.getElements().size();
                } else {
                    size = list.getElements().size();
                }
                if (value < 0) {
                    value += list.getElements().size();
                }
                if (size < 0) {
                    size += list.getElements().size();
                }
                int iCoerceIn = RangesKt.coerceIn(value, 0, list.getElements().size());
                int iCoerceIn2 = RangesKt.coerceIn(size, 0, list.getElements().size());
                if (iCoerceIn >= iCoerceIn2) {
                    return new LunoValue.List(new ArrayList());
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) list.getElements().subList(iCoerceIn, iCoerceIn2)));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("listReverse", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.44
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.List list = lunoValue instanceof LunoValue.List ? (LunoValue.List) lunoValue : null;
                if (list == null) {
                    throw new LunoRuntimeError("listReverse expects a list argument.", -1, null, 4, null);
                }
                CollectionsKt.reverse(list.getElements());
                return list;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ObjectKeys", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.45
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.LunoObject lunoObject = lunoValue instanceof LunoValue.LunoObject ? (LunoValue.LunoObject) lunoValue : null;
                if (lunoObject == null) {
                    throw new LunoRuntimeError("ObjectKeys expects an object argument.", -1, null, 4, null);
                }
                Set<String> setKeySet = lunoObject.getFields().keySet();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setKeySet, 10));
                Iterator<T> it = setKeySet.iterator();
                while (it.hasNext()) {
                    arrayList.add(new LunoValue.String((String) it.next()));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ObjectValues", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.46
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.LunoObject lunoObject = lunoValue instanceof LunoValue.LunoObject ? (LunoValue.LunoObject) lunoValue : null;
                if (lunoObject == null) {
                    throw new LunoRuntimeError("ObjectValues expects an object argument.", -1, null, 4, null);
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) lunoObject.getFields().values()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ObjectHasProperty", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.47
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                boolean zContainsKey = false;
                LunoValue lunoValue = args.get(0);
                LunoValue lunoValue2 = args.get(1);
                LunoValue.String string = lunoValue2 instanceof LunoValue.String ? (LunoValue.String) lunoValue2 : null;
                if (string == null) {
                    throw new LunoRuntimeError("ObjectHasProperty expects a string as key argument.", -1, null, 4, null);
                }
                String value = string.getValue();
                if (lunoValue instanceof LunoValue.LunoObject) {
                    zContainsKey = ((LunoValue.LunoObject) lunoValue).getFields().containsKey(value);
                } else if (lunoValue instanceof LunoValue.NativeObject) {
                    LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                    if (nativeObject.getObj() instanceof Map) {
                        Object obj = nativeObject.getObj();
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
                        zContainsKey = ((Map) obj).containsKey(value);
                    }
                }
                return new LunoValue.Boolean(zContainsKey);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("JSON_parse", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.48
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                String value;
                Object jSONArray;
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null || (value = string.getValue()) == null) {
                    throw new LunoRuntimeError("JSON.parse expects a string argument.", -1, null, 4, null);
                }
                try {
                    String string2 = StringsKt.trim((CharSequence) value).toString();
                    if (StringsKt.startsWith$default(string2, "{", false, 2, (Object) null)) {
                        jSONArray = new JSONObject(value);
                    } else if (StringsKt.startsWith$default(string2, "[", false, 2, (Object) null)) {
                        jSONArray = new JSONArray(value);
                    } else {
                        throw new LunoRuntimeError("JSON string must start with { or [", -1, null, 4, null);
                    }
                    return invoke$fromJson(jSONArray);
                } catch (JSONException e) {
                    throw new LunoRuntimeError("Failed to parse JSON string: " + e.getMessage(), -1, e);
                }
            }

            private static final LunoValue invoke$fromJson(Object obj) throws JSONException {
                if (obj instanceof JSONObject) {
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    JSONObject jSONObject = (JSONObject) obj;
                    Iterator<String> itKeys = jSONObject.keys();
                    Intrinsics.checkNotNullExpressionValue(itKeys, "keys(...)");
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        Intrinsics.checkNotNull(next);
                        Object obj2 = jSONObject.get(next);
                        Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
                        linkedHashMap.put(next, invoke$fromJson(obj2));
                    }
                    return new LunoValue.LunoObject(null, linkedHashMap);
                }
                if (obj instanceof JSONArray) {
                    ArrayList arrayList = new ArrayList();
                    JSONArray jSONArray = (JSONArray) obj;
                    int length = jSONArray.length();
                    for (int i = 0; i < length; i++) {
                        Object obj3 = jSONArray.get(i);
                        Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
                        arrayList.add(invoke$fromJson(obj3));
                    }
                    return new LunoValue.List(arrayList);
                }
                if (obj instanceof String) {
                    return new LunoValue.String((String) obj);
                }
                if (obj instanceof Number) {
                    return new LunoValue.Number(((Number) obj).doubleValue());
                }
                if (obj instanceof Boolean) {
                    return new LunoValue.Boolean(((Boolean) obj).booleanValue());
                }
                return Intrinsics.areEqual(obj, JSONObject.NULL) ? LunoValue.Null.INSTANCE : LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("JSON_stringify", new IntRange(1, 1), AnonymousClass49.INSTANCE);
        defineNative$catroid_apktemplateTemplateRelease("min", new IntRange(1, Integer.MAX_VALUE), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.50
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.isEmpty()) {
                    throw new LunoRuntimeError("MathMin requires at least one argument.", -1, null, 4, null);
                }
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("MathMin arguments must be numbers.", -1, null, 4, null);
                }
                double value = number.getValue();
                int size = args.size();
                for (int i = 1; i < size; i++) {
                    LunoValue lunoValue2 = args.get(i);
                    LunoValue.Number number2 = lunoValue2 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue2 : null;
                    if (number2 == null) {
                        throw new LunoRuntimeError("MathMin arguments must be numbers.", -1, null, 4, null);
                    }
                    value = Math.min(value, number2.getValue());
                }
                return new LunoValue.Number(value);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("max", new IntRange(1, Integer.MAX_VALUE), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.51
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.isEmpty()) {
                    throw new LunoRuntimeError("MathMax requires at least one argument.", -1, null, 4, null);
                }
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("MathMax arguments must be numbers.", -1, null, 4, null);
                }
                double value = number.getValue();
                int size = args.size();
                for (int i = 1; i < size; i++) {
                    LunoValue lunoValue2 = args.get(i);
                    LunoValue.Number number2 = lunoValue2 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue2 : null;
                    if (number2 == null) {
                        throw new LunoRuntimeError("MathMax arguments must be numbers.", -1, null, 4, null);
                    }
                    value = Math.max(value, number2.getValue());
                }
                return new LunoValue.Number(value);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("pow", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.52
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.Number number = lunoValue instanceof LunoValue.Number ? (LunoValue.Number) lunoValue : null;
                if (number == null) {
                    throw new LunoRuntimeError("MathPow base must be a number.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.Number number2 = lunoValue2 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue2 : null;
                if (number2 == null) {
                    throw new LunoRuntimeError("MathPow exponent must be a number.", -1, null, 4, null);
                }
                return new LunoValue.Number(Math.pow(number.getValue(), number2.getValue()));
            }
        });
        scope2.define("PI", new LunoValue.Number(3.141592653589793d));
        scope2.define(ExifInterface.LONGITUDE_EAST, new LunoValue.Number(2.718281828459045d));
        defineNative$catroid_apktemplateTemplateRelease("SetLocalVar", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.53
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                UserVarsManager.INSTANCE.setVar(args.get(0).toString(), args.get(1).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetLocalVar", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.54
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                String var = UserVarsManager.INSTANCE.getVar(args.get(0).toString());
                if (var == null) {
                    var = "null";
                }
                return new LunoValue.String(var);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("DeleteLocalVar", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.55
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                UserVarsManager.INSTANCE.delVar(args.get(0).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("DeleteLocalVars", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.56
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                UserVarsManager.INSTANCE.clearVars();
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetLocalVarName", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.57
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                String varName = UserVarsManager.INSTANCE.getVarName(Interpreter.this.getKotlinIntFromLunoValue(args.get(0), "GetLocalVarName", 1));
                if (varName == null) {
                    varName = "null";
                }
                return new LunoValue.String(varName);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetLocalVarValue", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.58
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                String varValue = UserVarsManager.INSTANCE.getVarValue(Interpreter.this.getKotlinIntFromLunoValue(args.get(0), "GetLocalVarName", 1));
                if (varValue == null) {
                    varValue = "null";
                }
                return new LunoValue.String(varValue);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetScope", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.59
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Object obj = Interpreter.this.projectScope;
                if (obj == null) {
                    obj = "";
                }
                return new LunoValue.NativeObject(obj);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetSprite", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.60
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Class<?> cls;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.NativeObject) {
                    Object obj = ((LunoValue.NativeObject) lunoValue).getObj();
                    if (obj instanceof org.catrobat.catroid.content.Scope) {
                        Sprite sprite = ((org.catrobat.catroid.content.Scope) obj).getSprite();
                        if (sprite != null) {
                            return new LunoValue.NativeObject(sprite);
                        }
                        return LunoValue.Null.INSTANCE;
                    }
                    String name2 = (obj == null || (cls = obj.getClass()) == null) ? null : cls.getName();
                    if (name2 == null) {
                        name2 = "null";
                    }
                    throw new LunoRuntimeError("GetSprite: Argument 0's native object is not a Scope. Got type: " + name2 + ".", -1, null, 4, null);
                }
                throw new LunoRuntimeError("GetSprite: Argument 0 must be a NativeObject containing a Scope. Got type: " + Reflection.getOrCreateKotlinClass(lunoValue.getClass()).getSimpleName() + ".", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetProject", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.61
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Class<?> cls;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (lunoValue instanceof LunoValue.NativeObject) {
                    Object obj = ((LunoValue.NativeObject) lunoValue).getObj();
                    if (obj instanceof org.catrobat.catroid.content.Scope) {
                        Project project = ((org.catrobat.catroid.content.Scope) obj).getProject();
                        if (project != null) {
                            return new LunoValue.NativeObject(project);
                        }
                        return LunoValue.Null.INSTANCE;
                    }
                    String name2 = (obj == null || (cls = obj.getClass()) == null) ? null : cls.getName();
                    if (name2 == null) {
                        name2 = "null";
                    }
                    throw new LunoRuntimeError("GetSprite: Argument 0's native object is not a Scope. Got type: " + name2 + ".", -1, null, 4, null);
                }
                throw new LunoRuntimeError("GetSprite: Argument 0 must be a NativeObject containing a Scope. Got type: " + Reflection.getOrCreateKotlinClass(lunoValue.getClass()).getSimpleName() + ".", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetDirectory", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.62
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String string = projectFromLunoValue.getDirectory().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return new LunoValue.String(string);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetDirectory", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.63
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                if (!(args.get(1) instanceof LunoValue.String)) {
                    LunoValue.Null r1 = LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.setDirectory(new File(args.get(1).toString()));
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetSceneList", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.64
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<Scene> sceneList = projectFromLunoValue.getSceneList();
                Intrinsics.checkNotNullExpressionValue(sceneList, "getSceneList(...)");
                List<Scene> list = sceneList;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (Scene scene : list) {
                    Intrinsics.checkNotNull(scene);
                    arrayList.add(new LunoValue.NativeObject(scene));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetSceneNames", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.65
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<String> sceneNames = projectFromLunoValue.getSceneNames();
                Intrinsics.checkNotNullExpressionValue(sceneNames, "getSceneNames(...)");
                List<String> list = sceneNames;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (String str : list) {
                    Intrinsics.checkNotNull(str);
                    arrayList.add(new LunoValue.String(str));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectAddScene", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.66
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("AddProjectScene: Argument 1 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Scene) {
                    projectFromLunoValue.addScene((Scene) nativeObject.getObj());
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("AddProjectScene: Argument 1 was not a Scene. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectRemoveScene", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.67
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ProjectRemoveScene: Argument 1 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Scene) {
                    projectFromLunoValue.removeScene((Scene) nativeObject.getObj());
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("ProjectRemoveScene: Argument 1 was not a Scene. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectHasScene", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.68
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                return new LunoValue.Boolean(projectFromLunoValue.hasScene());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetDefaultScene", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.69
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                Scene defaultScene = projectFromLunoValue.getDefaultScene();
                Intrinsics.checkNotNullExpressionValue(defaultScene, "getDefaultScene(...)");
                return new LunoValue.NativeObject(defaultScene);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetUserVariables", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.70
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<UserVariable> userVariables = projectFromLunoValue.getUserVariables();
                Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
                List<UserVariable> list = userVariables;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (UserVariable userVariable : list) {
                    Intrinsics.checkNotNull(userVariable);
                    arrayList.add(new LunoValue.NativeObject(userVariable));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetUserVariablesCopy", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.71
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<UserVariable> userVariablesCopy = projectFromLunoValue.getUserVariablesCopy();
                Intrinsics.checkNotNullExpressionValue(userVariablesCopy, "getUserVariablesCopy(...)");
                List<UserVariable> list = userVariablesCopy;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (UserVariable userVariable : list) {
                    Intrinsics.checkNotNull(userVariable);
                    arrayList.add(new LunoValue.NativeObject(userVariable));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetUserVariable", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.72
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                UserVariable userVariable = projectFromLunoValue.getUserVariable(args.get(1).toString());
                Intrinsics.checkNotNullExpressionValue(userVariable, "getUserVariable(...)");
                return new LunoValue.NativeObject(userVariable);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectAddUserVariable", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.73
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ProjectAddUserVariable: Argument 1 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof UserVariable) {
                    projectFromLunoValue.addUserVariable((UserVariable) nativeObject.getObj());
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("ProjectAddUserVariable: Argument 1 in the list was not a UserVariable. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectRemoveUserVariable", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.74
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.removeUserVariable(args.get(1).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("UserVariable", new IntRange(0, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.75
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.size() == 0) {
                    return new LunoValue.NativeObject(new UserVariable());
                }
                if (args.size() == 1) {
                    return new LunoValue.NativeObject(new UserVariable(args.get(0).toString()));
                }
                return new LunoValue.NativeObject(new UserVariable(args.get(0).toString(), args.get(1).toString()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetUserLists", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.76
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<UserList> userLists = projectFromLunoValue.getUserLists();
                Intrinsics.checkNotNullExpressionValue(userLists, "getUserLists(...)");
                List<UserList> list = userLists;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (UserList userList : list) {
                    Intrinsics.checkNotNull(userList);
                    arrayList.add(new LunoValue.NativeObject(userList));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetUserListsCopy", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.77
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<UserList> userListsCopy = projectFromLunoValue.getUserListsCopy();
                Intrinsics.checkNotNullExpressionValue(userListsCopy, "getUserListsCopy(...)");
                List<UserList> list = userListsCopy;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (UserList userList : list) {
                    Intrinsics.checkNotNull(userList);
                    arrayList.add(new LunoValue.NativeObject(userList));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetUserList", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.78
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                UserList userList = projectFromLunoValue.getUserList(args.get(1).toString());
                Intrinsics.checkNotNullExpressionValue(userList, "getUserList(...)");
                return new LunoValue.NativeObject(userList);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectAddUserList", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.79
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ProjectAddUserList: Argument 1 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof UserList) {
                    projectFromLunoValue.addUserList((UserList) nativeObject.getObj());
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("ProjectAddUserList: Argument 1 in the list was not a UserVariable. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectRemoveUserList", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.80
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.removeUserList(args.get(1).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("UserList", new IntRange(0, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.81
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.size() == 0) {
                    return new LunoValue.NativeObject(new UserList());
                }
                if (args.size() == 1) {
                    return new LunoValue.NativeObject(new UserList(args.get(0).toString()));
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.List)) {
                    throw new LunoRuntimeError("UserList: Argument 1 must be a List.", -1, null, 4, null);
                }
                ArrayList arrayList = new ArrayList();
                for (LunoValue lunoValue2 : ((LunoValue.List) lunoValue).getElements()) {
                    if (lunoValue2 instanceof LunoValue.NativeObject) {
                        arrayList.add(((LunoValue.NativeObject) lunoValue2).getObj());
                    } else if (lunoValue2 instanceof LunoValue.String) {
                        arrayList.add(((LunoValue.String) lunoValue2).getValue());
                    } else if (lunoValue2 instanceof LunoValue.Number) {
                        arrayList.add(Double.valueOf(((LunoValue.Number) lunoValue2).getValue()));
                    } else if (lunoValue2 instanceof LunoValue.Null) {
                        arrayList.add(null);
                    } else {
                        arrayList.add(lunoValue2.toString());
                    }
                }
                return new LunoValue.NativeObject(new UserList(args.get(0).toString(), arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectResetUserData", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.82
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.resetUserData();
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetSpriteListWithClones", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.83
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<Sprite> spriteListWithClones = projectFromLunoValue.getSpriteListWithClones();
                Intrinsics.checkNotNullExpressionValue(spriteListWithClones, "getSpriteListWithClones(...)");
                List<Sprite> list = spriteListWithClones;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (Sprite sprite : list) {
                    Intrinsics.checkNotNull(sprite);
                    arrayList.add(new LunoValue.NativeObject(sprite));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetName", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.84
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String name2 = projectFromLunoValue.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                return new LunoValue.String(name2);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetName", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.85
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.setName(args.get(1).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetDescription", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.86
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String description = projectFromLunoValue.getDescription();
                Intrinsics.checkNotNullExpressionValue(description, "getDescription(...)");
                return new LunoValue.String(description);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetDescription", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.87
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.setDescription(args.get(1).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetNotesAndCredits", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.88
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String notesAndCredits = projectFromLunoValue.getNotesAndCredits();
                Intrinsics.checkNotNullExpressionValue(notesAndCredits, "getNotesAndCredits(...)");
                return new LunoValue.String(notesAndCredits);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetNotesAndCredits", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.89
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.setNotesAndCredits(args.get(1).toString());
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetCatrobatLanguageVersion", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.90
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                return new LunoValue.String(String.valueOf(projectFromLunoValue.getCatrobatLanguageVersion()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetXmlHeader", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.91
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                XmlHeader xmlHeader = projectFromLunoValue.getXmlHeader();
                Intrinsics.checkNotNullExpressionValue(xmlHeader, "getXmlHeader(...)");
                return new LunoValue.NativeObject(xmlHeader);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetFilesDir", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.92
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String string = projectFromLunoValue.getFilesDir().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return new LunoValue.String(string);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetLibsDir", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.93
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String string = projectFromLunoValue.getLibsDir().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return new LunoValue.String(string);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetFile", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.94
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String string = projectFromLunoValue.getFile(args.get(1).toString()).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return new LunoValue.String(string);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetLib", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.95
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String string = projectFromLunoValue.getLib(args.get(1).toString()).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return new LunoValue.String(string);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectCheckExtension", new IntRange(3, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.96
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                String strCheckExtension = projectFromLunoValue.checkExtension(args.get(1).toString(), args.get(2).toString());
                Intrinsics.checkNotNullExpressionValue(strCheckExtension, "checkExtension(...)");
                return new LunoValue.String(strCheckExtension);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectAddFile", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.97
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                projectFromLunoValue.addFile(new File(args.get(1).toString()));
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("render", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.98
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.LunoFunction)) {
                    throw new LunoRuntimeError("Render_AddTask expects a function as its only argument.", -1, null, 4, null);
                }
                LunoValue.LunoFunction lunoFunction = (LunoValue.LunoFunction) lunoValue;
                if (lunoFunction.arity().getFirst() != 1 || lunoFunction.arity().getLast() != 1) {
                    throw new LunoRuntimeError("The function passed to Render_AddTask must accept exactly one argument (the renderer).", -1, null, 4, null);
                }
                RenderManager.INSTANCE.addTask(new RenderTask(lunoFunction, interpreter));
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("renderWidth", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.99
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                return new LunoValue.Number(Resources.getSystem().getDisplayMetrics().widthPixels);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("renderHeight", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.100
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> list) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(list, "<anonymous parameter 1>");
                return new LunoValue.Number(Resources.getSystem().getDisplayMetrics().heightPixels);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("renderDelete", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.101
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.LunoFunction)) {
                    throw new LunoRuntimeError("Render_AddTask expects a function as its only argument.", -1, null, 4, null);
                }
                LunoValue.LunoFunction lunoFunction = (LunoValue.LunoFunction) lunoValue;
                if (lunoFunction.arity().getFirst() != 1 || lunoFunction.arity().getLast() != 1) {
                    throw new LunoRuntimeError("The function passed to Render_AddTask must accept exactly one argument (the renderer).", -1, null, 4, null);
                }
                RenderManager.INSTANCE.deleteTask(new RenderTask(lunoFunction, interpreter));
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectDeleteFile", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.102
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                if (StringsKt.contains$default((CharSequence) args.get(1).toString(), (CharSequence) "/", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) args.get(1).toString(), (CharSequence) "\\", false, 2, (Object) null)) {
                    projectFromLunoValue.deleteFile(new File(args.get(1).toString()));
                } else {
                    projectFromLunoValue.deleteFile(args.get(1).toString());
                }
                return LunoValue.Null.INSTANCE;
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("File", new IntRange(1, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.103
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.size() == 1) {
                    return new LunoValue.NativeObject(new File(args.get(0).toString()));
                }
                return new LunoValue.NativeObject(new File(args.get(0).toString(), args.get(1).toString()));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("FilePath", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.104
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("FilePath: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof File) {
                    String absolutePath = ((File) nativeObject.getObj()).getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                    return new LunoValue.String(absolutePath);
                }
                throw new LunoRuntimeError("FilePath: Argument 0 was not a File. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease(PsiKeyword.THROW, new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.105
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                throw new LunoRuntimeError(args.get(0).toString(), -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("Rectangle", new IntRange(4, 4), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.106
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue lunoValue2 = args.get(1);
                LunoValue lunoValue3 = args.get(2);
                LunoValue lunoValue4 = args.get(3);
                if ((lunoValue instanceof LunoValue.Number) && (lunoValue2 instanceof LunoValue.Number) && (lunoValue3 instanceof LunoValue.Number) && (lunoValue4 instanceof LunoValue.Number)) {
                    return new LunoValue.NativeObject(new Rectangle((float) ((LunoValue.Number) lunoValue).getValue(), (float) ((LunoValue.Number) lunoValue2).getValue(), (float) ((LunoValue.Number) lunoValue3).getValue(), (float) ((LunoValue.Number) lunoValue4).getValue()));
                }
                throw new LunoRuntimeError("Rectangle: Arguments (0, 1, 2, 3) must be a Number.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetScreenRectangle", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.107
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                Rectangle screenRectangle = projectFromLunoValue.getScreenRectangle();
                Intrinsics.checkNotNullExpressionValue(screenRectangle, "getScreenRectangle(...)");
                return new LunoValue.NativeObject(screenRectangle);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetCatrobatLanguageVersion", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.108
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                try {
                    projectFromLunoValue.setCatrobatLanguageVersion(Double.parseDouble(args.get(1).toString()));
                    return LunoValue.Null.INSTANCE;
                } catch (Exception e) {
                    throw new LunoRuntimeError("ProjectSetCatrobatLanguageVersion: Failed to convert String to Double. " + e.getMessage(), -1, null, 4, null);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetTags", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.109
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                List<String> tags = projectFromLunoValue.getTags();
                Intrinsics.checkNotNullExpressionValue(tags, "getTags(...)");
                List<String> list = tags;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (String str : list) {
                    Intrinsics.checkNotNull(str);
                    arrayList.add(new LunoValue.NativeObject(str));
                }
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetTags", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.110
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                LunoValue next;
                boolean z;
                String simpleName;
                Class<?> cls;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                int i = 0;
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.List)) {
                    throw new LunoRuntimeError("ProjectSetTags: Argument 1 must be a List.", -1, null, 4, null);
                }
                ArrayList arrayList = new ArrayList();
                Iterator<LunoValue> it = ((LunoValue.List) lunoValue).getElements().iterator();
                while (true) {
                    if (it.hasNext()) {
                        int i2 = i + 1;
                        next = it.next();
                        z = next instanceof LunoValue.NativeObject;
                        if (!z) {
                            break;
                        }
                        LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) next;
                        if (!(nativeObject.getObj() instanceof String)) {
                            break;
                        }
                        arrayList.add(nativeObject.getObj());
                        i = i2;
                    } else {
                        projectFromLunoValue.setTags(arrayList);
                        return LunoValue.Null.INSTANCE;
                    }
                }
                if (z) {
                    Object obj = ((LunoValue.NativeObject) next).getObj();
                    simpleName = (obj == null || (cls = obj.getClass()) == null) ? null : cls.getName();
                    if (simpleName == null) {
                        simpleName = "null native object";
                    }
                } else {
                    simpleName = Reflection.getOrCreateKotlinClass(next.getClass()).getSimpleName();
                }
                throw new LunoRuntimeError("ProjectSetTags: Element at index " + i + " in the list was not a String. Got '" + simpleName + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetSceneByName", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.111
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                Scene sceneByName = projectFromLunoValue.getSceneByName(args.get(1).toString());
                Intrinsics.checkNotNullExpressionValue(sceneByName, "getSceneByName(...)");
                return new LunoValue.NativeObject(sceneByName);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectGetSceneById", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.112
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                try {
                    Scene sceneById = projectFromLunoValue.getSceneById(Integer.valueOf(Integer.parseInt(args.get(1).toString())));
                    Intrinsics.checkNotNullExpressionValue(sceneById, "getSceneById(...)");
                    return new LunoValue.NativeObject(sceneById);
                } catch (Exception e) {
                    throw new LunoRuntimeError("ProjectGetSceneById: Failed to convert String to Int. " + e.getMessage(), -1, null, 4, null);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ProjectSetXmlHeader", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.113
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                Project projectFromLunoValue = Interpreter.this.getProjectFromLunoValue(args.get(0));
                if (projectFromLunoValue == null) {
                    return LunoValue.Null.INSTANCE;
                }
                LunoValue lunoValue = args.get(1);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ProjectSetXmlHeader: Argument 1 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof XmlHeader) {
                    projectFromLunoValue.setXmlHeader((XmlHeader) nativeObject.getObj());
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("ProjectSetXmlHeader: Argument 1 was not a Xmlheader. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetName", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.114
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetName: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    String name2 = ((Sprite) nativeObject.getObj()).getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    return new LunoValue.String(name2);
                }
                throw new LunoRuntimeError("SpriteGetName: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("Sprite", new IntRange(1, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.115
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                if (args.size() == 1) {
                    return new LunoValue.NativeObject(new Sprite(args.get(0).toString()));
                }
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("Sprite: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    LunoValue lunoValue2 = args.get(0);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("Sprite: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Scene) {
                        return new LunoValue.NativeObject(new Sprite((Sprite) nativeObject.getObj(), (Scene) nativeObject2.getObj()));
                    }
                    throw new LunoRuntimeError("Sprite: Argument 1 was not a Scene. Got '" + nativeObject2.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("Sprite: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("Look", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.116
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("Look: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    return new LunoValue.NativeObject(new Look((Sprite) nativeObject.getObj()));
                }
                throw new LunoRuntimeError("Look: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetLook", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.117
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetLook: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    Look look = ((Sprite) nativeObject.getObj()).look;
                    Intrinsics.checkNotNullExpressionValue(look, "look");
                    return new LunoValue.NativeObject(look);
                }
                throw new LunoRuntimeError("SpriteGetLook: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookRemove", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.118
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookRemove: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.NativeObject(Boolean.valueOf(((Look) nativeObject.getObj()).remove()));
                }
                throw new LunoRuntimeError("LookRemove: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetLookData", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.119
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetLookData: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    LookData lookData = ((Look) nativeObject.getObj()).getLookData();
                    Intrinsics.checkNotNullExpressionValue(lookData, "getLookData(...)");
                    return new LunoValue.NativeObject(lookData);
                }
                throw new LunoRuntimeError("LookGetLookData: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetLookData2", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.120
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetLookData2: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    LookData lookData2 = ((Look) nativeObject.getObj()).lookData2;
                    Intrinsics.checkNotNullExpressionValue(lookData2, "lookData2");
                    return new LunoValue.NativeObject(lookData2);
                }
                throw new LunoRuntimeError("LookGetLookData2: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookData", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.121
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                return new LunoValue.NativeObject(new LookData(args.get(0).toString(), new File(args.get(1).toString())));
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookDataGetName", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.122
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookDataGetName: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof LookData) {
                    String name2 = ((LookData) nativeObject.getObj()).f512name;
                    Intrinsics.checkNotNullExpressionValue(name2, "name");
                    return new LunoValue.NativeObject(name2);
                }
                throw new LunoRuntimeError("LookDataGetName: Argument 0 was not a LookData. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookDataSetName", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.123
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookDataSetName: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof LookData) {
                    ((LookData) nativeObject.getObj()).f512name = args.get(1).toString();
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("LookDataSetName: Argument 0 was not a LookData. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookDataGetFile", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.124
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookDataGetFile: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof LookData) {
                    File file = ((LookData) nativeObject.getObj()).getFile();
                    Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
                    return new LunoValue.NativeObject(file);
                }
                throw new LunoRuntimeError("LookDataGetFile: Argument 0 was not a LookData. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookDataSetFile", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.125
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookDataSetFile: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof LookData) {
                    ((LookData) nativeObject.getObj()).setFile(new File(args.get(1).toString()));
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("LookDataSetFile: Argument 0 was not a LookData. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetLookData", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.126
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetLookData: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("LookSetLookData: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof LookData) {
                        ((Look) nativeObject.getObj()).setLookData((LookData) nativeObject2.getObj());
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("LookSetLookData: Argument 1 was not a LookData. Got '" + nativeObject2.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("LookSetLookData: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetLookData2", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.127
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetLookData2: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("LookSetLookData2: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof LookData) {
                        ((Look) nativeObject.getObj()).lookData2 = (LookData) nativeObject2.getObj();
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("LookSetLookData2: Argument 1 was not a LookData. Got '" + nativeObject2.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("LookSetLookData2: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetX", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.128
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetX: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getX());
                }
                throw new LunoRuntimeError("LookGetX: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetY", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.129
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetY: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getY());
                }
                throw new LunoRuntimeError("LookGetY: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetWidth", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.130
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetWidth: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getWidth());
                }
                throw new LunoRuntimeError("LookGetWidth: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetHeight", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.131
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetHeight: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getHeight());
                }
                throw new LunoRuntimeError("LookGetHeight: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetRotation", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.132
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetRotation: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getRotation());
                }
                throw new LunoRuntimeError("LookGetRotation: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetAlpha", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.133
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetAlpha: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getAlpha());
                }
                throw new LunoRuntimeError("LookGetAlpha: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetBrightness", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.134
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetBrightness: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getBrightness());
                }
                throw new LunoRuntimeError("LookGetBrightness: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetHue", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.135
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetHue: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Number(((Look) nativeObject.getObj()).getColorInUserInterfaceDimensionUnit());
                }
                throw new LunoRuntimeError("LookGetHue: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetVisible", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.136
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetVisible: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Boolean(((Look) nativeObject.getObj()).isVisible());
                }
                throw new LunoRuntimeError("LookGetVisible: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookGetLookVisible", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.137
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookGetLookVisible: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    return new LunoValue.Boolean(((Look) nativeObject.getObj()).isLookVisible());
                }
                throw new LunoRuntimeError("LookGetLookVisible: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetX", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.138
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetX: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setX(Float.parseFloat(args.get(1).toString()));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetX: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetX: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetY", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.139
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetY: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setY(Float.parseFloat(args.get(1).toString()));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetY: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetY: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetWidth", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.140
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetWidth: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setWidth(Float.parseFloat(args.get(1).toString()));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetWidth: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetWidth: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetHeight", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.141
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetHeight: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setHeight(Float.parseFloat(args.get(1).toString()));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetHeight: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetHeight: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetRotation", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.142
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetRotation: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setRotation(Float.parseFloat(args.get(1).toString()));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetRotation: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetRotation: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetAlpha", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.143
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetAlpha: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setTransparencyInUserInterfaceDimensionUnit(100.0f - (Float.parseFloat(args.get(1).toString()) * 100.0f));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetAlpha: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetAlpha: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetBrightness", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.144
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetBrightness: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setBrightnessInUserInterfaceDimensionUnit(Float.parseFloat(args.get(1).toString()) * 100.0f);
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetBrightness: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetBrightness: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetHue", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.145
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetHue: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setColorInUserInterfaceDimensionUnit(Float.parseFloat(args.get(1).toString()));
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetHue: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetHue: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetVisible", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.146
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetVisible: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setVisible(args.get(1).isTruthy());
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetVisible: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetVisible: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetUserDefinedBrickField", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.147
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Class<?> cls;
                Class<?> cls2;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                String string = args.get(0).toString();
                Constructor<?> constructor = null;
                try {
                    try {
                        cls2 = Class.forName("org.catrobat.catroid.content.bricks.UserDefinedBrick$UserDefinedBrickField");
                    } catch (ClassNotFoundException unused) {
                        Class<?>[] declaredClasses = UserDefinedBrick.class.getDeclaredClasses();
                        Intrinsics.checkNotNullExpressionValue(declaredClasses, "getDeclaredClasses(...)");
                        Class<?>[] clsArr = declaredClasses;
                        int length = clsArr.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                cls = null;
                                break;
                            }
                            cls = clsArr[i];
                            if (Intrinsics.areEqual(cls.getSimpleName(), "UserDefinedBrickField")) {
                                break;
                            }
                            i++;
                        }
                        cls2 = cls;
                        if (cls2 == null) {
                            throw new LunoRuntimeError("Class UserDefinedBrickField not found via forName or declaredClasses", -1, null, 4, null);
                        }
                    }
                    Constructor<?>[] declaredConstructors = cls2.getDeclaredConstructors();
                    Intrinsics.checkNotNullExpressionValue(declaredConstructors, "getDeclaredConstructors(...)");
                    for (Constructor<?> constructor2 : declaredConstructors) {
                        Constructor<?> constructor3 = constructor2;
                        if (constructor3.getParameterTypes().length == 1 && Intrinsics.areEqual(constructor3.getParameterTypes()[0], String.class)) {
                            constructor = constructor2;
                            break;
                        }
                    }
                    Constructor<?> constructor4 = constructor;
                    if (constructor4 == null) {
                        Constructor<?>[] declaredConstructors2 = cls2.getDeclaredConstructors();
                        Intrinsics.checkNotNullExpressionValue(declaredConstructors2, "getDeclaredConstructors(...)");
                        Constructor<?>[] constructorArr = declaredConstructors2;
                        ArrayList arrayList = new ArrayList(constructorArr.length);
                        for (Constructor<?> constructor5 : constructorArr) {
                            String string2 = Arrays.toString(constructor5.getParameterTypes());
                            Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                            arrayList.add(string2);
                        }
                        throw new LunoRuntimeError("Constructor(String) for UserDefinedBrickField not found. Constructors: " + arrayList, -1, null, 4, null);
                    }
                    constructor4.setAccessible(true);
                    Object objNewInstance = constructor4.newInstance(string);
                    Intrinsics.checkNotNull(objNewInstance);
                    return new LunoValue.NativeObject(objNewInstance);
                } catch (Exception e) {
                    throw new LunoRuntimeError("Failed to create field '" + string + "': " + e, -1, e);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("LookSetLookVisible", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.148
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("LookSetLookVisible: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Look) {
                    try {
                        ((Look) ((LunoValue.NativeObject) lunoValue).getObj()).setLookVisible(args.get(1).isTruthy());
                        return LunoValue.Null.INSTANCE;
                    } catch (Exception e) {
                        throw new LunoRuntimeError("LookSetLookVisible: Failed to convert String to Float. " + e.getMessage(), -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("LookSetLookVisible: Argument 0 was not a Look. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetScriptList", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.149
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetScriptList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    List<Script> scriptList = ((Sprite) nativeObject.getObj()).getScriptList();
                    Intrinsics.checkNotNullExpressionValue(scriptList, "getScriptList(...)");
                    List<Script> list = scriptList;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (Script script : list) {
                        Intrinsics.checkNotNull(script);
                        arrayList.add(new LunoValue.NativeObject(script));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("SpriteGetScriptList: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetLookList", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.150
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetLookList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    List<LookData> lookList = ((Sprite) nativeObject.getObj()).getLookList();
                    Intrinsics.checkNotNullExpressionValue(lookList, "getLookList(...)");
                    List<LookData> list = lookList;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (LookData lookData : list) {
                        Intrinsics.checkNotNull(lookData);
                        arrayList.add(new LunoValue.NativeObject(lookData));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("SpriteGetLookList: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetSoundList", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.151
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetSoundList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    List<SoundInfo> soundList = ((Sprite) nativeObject.getObj()).getSoundList();
                    Intrinsics.checkNotNullExpressionValue(soundList, "getSoundList(...)");
                    List<SoundInfo> list = soundList;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (SoundInfo soundInfo : list) {
                        Intrinsics.checkNotNull(soundInfo);
                        arrayList.add(new LunoValue.NativeObject(soundInfo));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("SpriteGetSoundList: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetUserVariables", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.152
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetUserVariables: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    List<UserVariable> userVariables = ((Sprite) nativeObject.getObj()).getUserVariables();
                    Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
                    List<UserVariable> list = userVariables;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (UserVariable userVariable : list) {
                        Intrinsics.checkNotNull(userVariable);
                        arrayList.add(new LunoValue.NativeObject(userVariable));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("SpriteGetUserVariables: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetUserLists", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.153
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetUserLists: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    List<UserList> userLists = ((Sprite) nativeObject.getObj()).getUserLists();
                    Intrinsics.checkNotNullExpressionValue(userLists, "getUserLists(...)");
                    List<UserList> list = userLists;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (UserList userList : list) {
                        Intrinsics.checkNotNull(userList);
                        arrayList.add(new LunoValue.NativeObject(userList));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("SpriteGetUserLists: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ActionFactory", new IntRange(0, 0), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.154
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                return new LunoValue.NativeObject(new ActionFactory());
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetActionFactory", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.155
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetActionFactory: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    ActionFactory actionFactory = ((Sprite) nativeObject.getObj()).getActionFactory();
                    Intrinsics.checkNotNullExpressionValue(actionFactory, "getActionFactory(...)");
                    return new LunoValue.NativeObject(actionFactory);
                }
                throw new LunoRuntimeError("SpriteGetActionFactory: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteIsClone", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.156
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteIsClone: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    return new LunoValue.Boolean(((Sprite) nativeObject.getObj()).isClone);
                }
                throw new LunoRuntimeError("SpriteIsClone: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteOriginal", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.157
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteOriginal: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    Sprite myOriginal = ((Sprite) nativeObject.getObj()).myOriginal;
                    Intrinsics.checkNotNullExpressionValue(myOriginal, "myOriginal");
                    return new LunoValue.NativeObject(myOriginal);
                }
                throw new LunoRuntimeError("SpriteOriginal: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteIsGliding", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.158
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteIsClone: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    return new LunoValue.Boolean(((Sprite) nativeObject.getObj()).isGliding());
                }
                throw new LunoRuntimeError("SpriteIsClone: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetAllBricks", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.159
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetAllBricks: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    List<Brick> allBricks = ((Sprite) nativeObject.getObj()).getAllBricks();
                    Intrinsics.checkNotNullExpressionValue(allBricks, "getAllBricks(...)");
                    List<Brick> list = allBricks;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (Brick brick : list) {
                        Intrinsics.checkNotNull(brick);
                        arrayList.add(new LunoValue.NativeObject(brick));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("SpriteGetAllBricks: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetUserVariable", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.160
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetUserVariable: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    UserVariable userVariable = ((Sprite) nativeObject.getObj()).getUserVariable(args.get(1).toString());
                    Intrinsics.checkNotNullExpressionValue(userVariable, "getUserVariable(...)");
                    return new LunoValue.NativeObject(userVariable);
                }
                throw new LunoRuntimeError("SpriteGetUserVariable: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteAddUserVariable", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.161
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteAddUserVariable: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("SpriteAddUserVariable: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof UserVariable) {
                        ((Sprite) nativeObject.getObj()).addUserVariable((UserVariable) nativeObject2.getObj());
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("SpriteAddUserVariable: Argument 1 was not a UserVariable. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("SpriteIsClone: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteAddUserList", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.162
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteAddUserList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    LunoValue lunoValue2 = args.get(0);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("SpriteAddUserList: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof UserList) {
                        ((Sprite) nativeObject.getObj()).addUserList((UserList) nativeObject2.getObj());
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("SpriteAddUserList: Argument 1 was not a UserList. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("SpriteAddUserList: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetUserList", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.163
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetUserList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    UserList userList = ((Sprite) nativeObject.getObj()).getUserList(args.get(1).toString());
                    Intrinsics.checkNotNullExpressionValue(userList, "getUserList(...)");
                    return new LunoValue.NativeObject(userList);
                }
                throw new LunoRuntimeError("SpriteGetUserList: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteAddScript", new IntRange(2, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.164
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteAddScript: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("SpriteAddScript: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Script) {
                        if (args.size() == 2) {
                            ((Sprite) nativeObject.getObj()).addScript((Script) nativeObject2.getObj());
                        } else {
                            try {
                                ((Sprite) ((LunoValue.NativeObject) lunoValue).getObj()).addScript(Integer.parseInt(args.get(2).toString()), (Script) ((LunoValue.NativeObject) lunoValue2).getObj());
                            } catch (Exception unused) {
                                throw new LunoRuntimeError("SpriteAddScript: Failed to convert String to Int.", -1, null, 4, null);
                            }
                        }
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("SpriteAddScript: Argument 1 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("SpriteAddScript: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteGetScript", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.165
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteGetScript: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    try {
                        Script script = ((Sprite) ((LunoValue.NativeObject) lunoValue).getObj()).getScript(Integer.parseInt(args.get(1).toString()));
                        Intrinsics.checkNotNullExpressionValue(script, "getScript(...)");
                        return new LunoValue.NativeObject(script);
                    } catch (Exception unused) {
                        throw new LunoRuntimeError("SpriteGetScript: Failed to convert String to Int.", -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("SpriteGetScript: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteRemoveAllScripts", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.166
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteRemoveAllScripts: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    ((Sprite) nativeObject.getObj()).removeAllScripts();
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("SpriteRemoveAllScripts: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteRemoveScript", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.167
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteRemoveScript: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("SpriteRemoveScript: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Script) {
                        ((Sprite) nativeObject.getObj()).removeScript((Script) nativeObject2.getObj());
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("SpriteRemoveScript: Argument 1 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("SpriteRemoveScript: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("SpriteIsBackground", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.168
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("SpriteRemoveScript: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Sprite) {
                    return new LunoValue.Boolean(((Sprite) nativeObject.getObj()).isBackgroundSprite());
                }
                throw new LunoRuntimeError("SpriteRemoveScript: Argument 0 was not a Sprite. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ScriptGetBrickList", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.169
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ScriptGetBrickList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Script) {
                    List<Brick> brickList = ((Script) nativeObject.getObj()).getBrickList();
                    Intrinsics.checkNotNullExpressionValue(brickList, "getBrickList(...)");
                    List<Brick> list = brickList;
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (Brick brick : list) {
                        Intrinsics.checkNotNull(brick);
                        arrayList.add(new LunoValue.NativeObject(brick));
                    }
                    return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
                }
                throw new LunoRuntimeError("ScriptGetBrickList: Argument 0 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ScriptRun", new IntRange(3, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.170
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ScriptGetBrickList: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Script) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("ScriptGetBrickList: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Sprite) {
                        LunoValue lunoValue3 = args.get(2);
                        if (!(lunoValue3 instanceof LunoValue.NativeObject)) {
                            throw new LunoRuntimeError("ScriptGetBrickList: Argument 2 must be a NativeObject.", -1, null, 4, null);
                        }
                        LunoValue.NativeObject nativeObject3 = (LunoValue.NativeObject) lunoValue3;
                        if (nativeObject3.getObj() instanceof ScriptSequenceAction) {
                            ((Script) nativeObject.getObj()).run((Sprite) nativeObject2.getObj(), (ScriptSequenceAction) nativeObject3.getObj());
                            return LunoValue.Null.INSTANCE;
                        }
                        throw new LunoRuntimeError("ScriptGetBrickList: Argument 2 was not a ScriptSequenceAction. Got '" + nativeObject3.getObj().getClass().getName() + "'.", -1, null, 4, null);
                    }
                    throw new LunoRuntimeError("ScriptGetBrickList: Argument 1 was not a Sprite. Got '" + nativeObject2.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("ScriptGetBrickList: Argument 0 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("GetSequence", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.171
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("GetSequence: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof org.catrobat.catroid.content.Scope) {
                    if (((org.catrobat.catroid.content.Scope) nativeObject.getObj()).getSequence() != null) {
                        return new LunoValue.NativeObject(((org.catrobat.catroid.content.Scope) nativeObject.getObj()).getSequence());
                    }
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("GetSequence: Argument 0 was not a Scope. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ScriptSequenceAction", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.172
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ScriptSequenceAction: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Script) {
                    return new LunoValue.NativeObject(new ScriptSequenceAction((Script) nativeObject.getObj()));
                }
                throw new LunoRuntimeError("ScriptSequenceAction: Argument 0 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ScriptAddBrick", new IntRange(2, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.173
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ScriptAddBrick: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Script) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("ScriptAddBrick: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Brick) {
                        if (args.size() == 2) {
                            ((Script) nativeObject.getObj()).addBrick((Brick) nativeObject2.getObj());
                        } else {
                            try {
                                ((Script) ((LunoValue.NativeObject) lunoValue).getObj()).addBrick(Integer.parseInt(args.get(2).toString()), (Brick) ((LunoValue.NativeObject) lunoValue2).getObj());
                            } catch (Exception unused) {
                                throw new LunoRuntimeError("ScriptAddBrick: Failed to convert String to Int.", -1, null, 4, null);
                            }
                        }
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("ScriptAddBrick: Argument 1 was not a Brick. Got '" + nativeObject2.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("ScriptAddBrick: Argument 0 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ScriptRemoveBrick", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.174
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ScriptRemoveBrick: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Script) {
                    LunoValue lunoValue2 = args.get(1);
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("ScriptRemoveBrick: Argument 1 must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Brick) {
                        ((Script) nativeObject.getObj()).removeBrick((Brick) nativeObject2.getObj());
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("ScriptRemoveBrick: Argument 1 was not a Brick. Got '" + nativeObject2.getObj().getClass().getName() + "'.", -1, null, 4, null);
                }
                throw new LunoRuntimeError("ScriptRemoveBrick: Argument 0 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("ScriptGetBrick", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.175
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("ScriptGetBrick: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof Script) {
                    try {
                        Brick brick = ((Script) ((LunoValue.NativeObject) lunoValue).getObj()).getBrick(Integer.parseInt(args.get(1).toString()));
                        Intrinsics.checkNotNullExpressionValue(brick, "getBrick(...)");
                        return new LunoValue.NativeObject(brick);
                    } catch (Exception unused) {
                        throw new LunoRuntimeError("ScriptGetBrick: Failed to convert String to Int.", -1, null, 4, null);
                    }
                }
                throw new LunoRuntimeError("ScriptGetBrick: Argument 0 was not a Script. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("UserVariableGetValue", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.176
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("UserVariableGetValue: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof UserVariable) {
                    return new LunoValue.String(((UserVariable) nativeObject.getObj()).getValue().toString());
                }
                throw new LunoRuntimeError("UserVariableGetValue: Argument 0 was not a UserVariable. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("UserVariableSetValue", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.177
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("UserVariableSetValue: Argument 0 must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                if (nativeObject.getObj() instanceof UserVariable) {
                    ((UserVariable) nativeObject.getObj()).setValue(args.get(1).toString());
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("UserVariableSetValue: Argument 0 was not a UserVariable. Got '" + nativeObject.getObj().getClass().getName() + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("RunBrick", new IntRange(3, 3), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.178
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Class<?> cls;
                Class<?> cls2;
                Class<?> cls3;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue lunoValue2 = args.get(1);
                LunoValue lunoValue3 = args.get(2);
                if (!(lunoValue instanceof LunoValue.NativeObject)) {
                    throw new LunoRuntimeError("RunBrick: Argument 0 (brick) must be a NativeObject.", -1, null, 4, null);
                }
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValue;
                String name2 = null;
                if (nativeObject.getObj() instanceof Brick) {
                    Object obj = nativeObject.getObj();
                    if (!(lunoValue2 instanceof LunoValue.NativeObject)) {
                        throw new LunoRuntimeError("RunBrick: Argument 1 (sprite) must be a NativeObject.", -1, null, 4, null);
                    }
                    LunoValue.NativeObject nativeObject2 = (LunoValue.NativeObject) lunoValue2;
                    if (nativeObject2.getObj() instanceof Sprite) {
                        Object obj2 = nativeObject2.getObj();
                        if (!(lunoValue3 instanceof LunoValue.NativeObject)) {
                            throw new LunoRuntimeError("RunBrick: Argument 2 (sequence) must be a NativeObject.", -1, null, 4, null);
                        }
                        LunoValue.NativeObject nativeObject3 = (LunoValue.NativeObject) lunoValue3;
                        if (nativeObject3.getObj() instanceof ScriptSequenceAction) {
                            try {
                                ((Brick) obj).addActionToSequence((Sprite) obj2, (ScriptSequenceAction) nativeObject3.getObj());
                                return LunoValue.Null.INSTANCE;
                            } catch (Exception e) {
                                throw new LunoRuntimeError("RunBrick: Error during addActionToSequence for '" + Reflection.getOrCreateKotlinClass(obj.getClass()).getSimpleName() + "': " + e.getMessage(), -1, e);
                            }
                        }
                        Object obj3 = nativeObject3.getObj();
                        if (obj3 != null && (cls3 = obj3.getClass()) != null) {
                            name2 = cls3.getName();
                        }
                        throw new LunoRuntimeError("RunBrick: Argument 2 (sequence) was not a ScriptSequenceAction. Got '" + (name2 != null ? name2 : "null") + "'.", -1, null, 4, null);
                    }
                    Object obj4 = nativeObject2.getObj();
                    if (obj4 != null && (cls2 = obj4.getClass()) != null) {
                        name2 = cls2.getName();
                    }
                    throw new LunoRuntimeError("RunBrick: Argument 1 (sprite) was not a Sprite. Got '" + (name2 != null ? name2 : "null") + "'.", -1, null, 4, null);
                }
                Object obj5 = nativeObject.getObj();
                if (obj5 != null && (cls = obj5.getClass()) != null) {
                    name2 = cls.getName();
                }
                throw new LunoRuntimeError("RunBrick: Argument 0 (brick) was not a Brick. Got '" + (name2 != null ? name2 : "null") + "'.", -1, null, 4, null);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("createListener", new IntRange(2, 2), AnonymousClass179.INSTANCE);
        defineNative$catroid_apktemplateTemplateRelease("Brick", new IntRange(2, 2), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.180
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                AddRadioBrick addRadioBrick;
                Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue lunoValue2 = args.get(1);
                if (!(lunoValue instanceof LunoValue.String)) {
                    throw new LunoRuntimeError("Brick: First argument (brick name) must be a String.", -1, null, 4, null);
                }
                LunoValue.String string = (LunoValue.String) lunoValue;
                String lowerCase = string.getValue().toLowerCase();
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (!(lunoValue2 instanceof LunoValue.List)) {
                    throw new LunoRuntimeError("Brick: Second argument (parameters) must be a List.", -1, null, 4, null);
                }
                List<LunoValue> elements = ((LunoValue.List) lunoValue2).getElements();
                switch (lowerCase.hashCode()) {
                    case -1972732793:
                        if (lowerCase.equals("addradiobrick")) {
                            LunoValue lunoValue3 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue3, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            String value = ((LunoValue.String) lunoValue3).getValue();
                            LunoValue lunoValue4 = elements.get(1);
                            Intrinsics.checkNotNull(lunoValue4, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            addRadioBrick = new AddRadioBrick(value, ((LunoValue.String) lunoValue4).getValue());
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case -1917871427:
                        if (lowerCase.equals("askgemini2brick")) {
                            LunoValue lunoValue5 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue5, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            String value2 = ((LunoValue.String) lunoValue5).getValue();
                            LunoValue lunoValue6 = elements.get(1);
                            Intrinsics.checkNotNull(lunoValue6, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            String value3 = ((LunoValue.String) lunoValue6).getValue();
                            Object obj = elements.get(2);
                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserVariable");
                            addRadioBrick = new AskGemini2Brick(new Formula(value2), new Formula(value3), (UserVariable) obj);
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case -930955304:
                        if (lowerCase.equals("arduinosenddigitalvaluebrick")) {
                            LunoValue lunoValue7 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue7, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.Number");
                            int value4 = (int) ((LunoValue.Number) lunoValue7).getValue();
                            LunoValue lunoValue8 = elements.get(1);
                            Intrinsics.checkNotNull(lunoValue8, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.Number");
                            addRadioBrick = new ArduinoSendDigitalValueBrick(value4, (int) ((LunoValue.Number) lunoValue8).getValue());
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case -831655549:
                        if (lowerCase.equals("showtoastblock")) {
                            if (elements.isEmpty() || !(elements.get(0) instanceof LunoValue.String)) {
                                throw new LunoRuntimeError("ShowToastBlock expects a String message as its first parameter.", -1, null, 4, null);
                            }
                            LunoValue lunoValue9 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue9, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            addRadioBrick = new ShowToastBlock(((LunoValue.String) lunoValue9).getValue());
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case -605056664:
                        if (lowerCase.equals("askbrick")) {
                            LunoValue lunoValue10 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue10, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            String value5 = ((LunoValue.String) lunoValue10).getValue();
                            Object obj2 = elements.get(1);
                            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserVariable");
                            addRadioBrick = new AskBrick(new Formula(value5), (UserVariable) obj2);
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case -601916433:
                        if (lowerCase.equals("askgptbrick")) {
                            LunoValue lunoValue11 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue11, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            ((LunoValue.String) lunoValue11).getValue();
                            LunoValue lunoValue12 = elements.get(1);
                            Intrinsics.checkNotNull(lunoValue12, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            ((LunoValue.String) lunoValue12).getValue();
                            Object obj3 = elements.get(2);
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserVariable");
                            addRadioBrick = new AskGPTBrick();
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case 103291561:
                        if (lowerCase.equals("additemtouserlistbrick")) {
                            LunoValue lunoValue13 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue13, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.Number");
                            addRadioBrick = new AddItemToUserListBrick(((LunoValue.Number) lunoValue13).getValue());
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case 289791222:
                        if (lowerCase.equals("addeditbrick")) {
                            LunoValue lunoValue14 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue14, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            String value6 = ((LunoValue.String) lunoValue14).getValue();
                            LunoValue lunoValue15 = elements.get(1);
                            Intrinsics.checkNotNull(lunoValue15, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            addRadioBrick = new AddEditBrick(value6, ((LunoValue.String) lunoValue15).getValue());
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case 1319832454:
                        if (lowerCase.equals("arduinosendpwmvaluebrick")) {
                            LunoValue lunoValue16 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue16, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.Number");
                            int value7 = (int) ((LunoValue.Number) lunoValue16).getValue();
                            LunoValue lunoValue17 = elements.get(1);
                            Intrinsics.checkNotNull(lunoValue17, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.Number");
                            addRadioBrick = new ArduinoSendPWMValueBrick(value7, (int) ((LunoValue.Number) lunoValue17).getValue());
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case 1784045331:
                        if (lowerCase.equals("askgeminibrick")) {
                            LunoValue lunoValue18 = elements.get(0);
                            Intrinsics.checkNotNull(lunoValue18, "null cannot be cast to non-null type org.catrobat.catroid.utils.lunoscript.LunoValue.String");
                            String value8 = ((LunoValue.String) lunoValue18).getValue();
                            Object obj4 = elements.get(1);
                            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserVariable");
                            addRadioBrick = new AskGeminiBrick(new Formula(value8), (UserVariable) obj4);
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    case 2093571332:
                        if (lowerCase.equals("clonebrick")) {
                            addRadioBrick = new CloneBrick();
                            return new LunoValue.NativeObject(addRadioBrick);
                        }
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                    default:
                        throw new LunoRuntimeError("Brick: Unknown Brick ID: '" + string.getValue() + "'.", -1, null, 4, null);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("runInRenderThread", new IntRange(1, 1), new AnonymousClass181());
        defineNative$catroid_apktemplateTemplateRelease("runInUIThread", new IntRange(1, 1), new AnonymousClass182());
        defineNative$catroid_apktemplateTemplateRelease("createView", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.183
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                Context context2;
                Class<?> cls;
                String message;
                StageActivity stageActivity;
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
                if (weakReference == null || (stageActivity = weakReference.get()) == null) {
                    context2 = Interpreter.this.androidContext;
                    if (context2 == null) {
                        throw new LunoRuntimeError("createView: Could not get any valid context.", -1, null, 4, null);
                    }
                } else {
                    context2 = stageActivity;
                }
                if (lunoValue instanceof LunoValue.NativeClass) {
                    cls = ((LunoValue.NativeClass) lunoValue).getKlass();
                } else if (lunoValue instanceof LunoValue.String) {
                    String value = ((LunoValue.String) lunoValue).getValue();
                    Iterator it = CollectionsKt.listOf((Object[]) new String[]{"android.widget.", "android.view.", "android.webkit.", "com.google.android.material.button.", "com.google.android.material.textfield."}).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            cls = null;
                            break;
                        }
                        try {
                            cls = Class.forName(((String) it.next()) + value);
                            break;
                        } catch (ClassNotFoundException unused) {
                        }
                    }
                    if (cls == null) {
                        throw new LunoRuntimeError("View class '" + value + "' not found in standard packages.", -1, null, 4, null);
                    }
                } else {
                    throw new LunoRuntimeError("createView expects a class or a string class name.", -1, null, 4, null);
                }
                try {
                    return LunoValue.INSTANCE.fromKotlin(cls.getConstructor(Context.class).newInstance(context2));
                } catch (Exception e) {
                    String simpleName = cls.getSimpleName();
                    Throwable cause = e.getCause();
                    if (cause == null || (message = cause.getMessage()) == null) {
                        message = e.getMessage();
                    }
                    throw new LunoRuntimeError("Failed to create instance of View '" + simpleName + "': " + message, -1, e);
                }
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("addView", new IntRange(5, 5), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.184
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                StageActivity stageActivity;
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.NativeObject nativeObject = lunoValue instanceof LunoValue.NativeObject ? (LunoValue.NativeObject) lunoValue : null;
                Object obj = nativeObject != null ? nativeObject.getObj() : null;
                View view = obj instanceof View ? (View) obj : null;
                if (view == null) {
                    throw new LunoRuntimeError("addView: Argument 1 must be a View.", -1, null, 4, null);
                }
                LunoValue lunoValue2 = args.get(1);
                LunoValue.Number number = lunoValue2 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue2 : null;
                int value = number != null ? (int) number.getValue() : 0;
                LunoValue lunoValue3 = args.get(2);
                LunoValue.Number number2 = lunoValue3 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue3 : null;
                int value2 = number2 != null ? (int) number2.getValue() : 0;
                LunoValue lunoValue4 = args.get(3);
                LunoValue.Number number3 = lunoValue4 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue4 : null;
                int value3 = number3 != null ? (int) number3.getValue() : -2;
                LunoValue lunoValue5 = args.get(4);
                LunoValue.Number number4 = lunoValue5 instanceof LunoValue.Number ? (LunoValue.Number) lunoValue5 : null;
                int value4 = number4 != null ? (int) number4.getValue() : -2;
                WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
                if (weakReference == null || (stageActivity = weakReference.get()) == null) {
                    throw new LunoRuntimeError("addView: Could not get StageActivity.", -1, null, 4, null);
                }
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(value3, value4);
                layoutParams.gravity = BadgeDrawable.TOP_START;
                layoutParams.leftMargin = value;
                layoutParams.topMargin = value2;
                String str = "luno_view_" + view.hashCode();
                stageActivity.addViewToStage(str, view, layoutParams);
                return new LunoValue.String(str);
            }
        });
        defineNative$catroid_apktemplateTemplateRelease("removeView", new IntRange(1, 1), new Function2<Interpreter, List<? extends LunoValue>, LunoValue>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.185
            @Override // kotlin.jvm.functions.Function2
            public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
                String value;
                StageActivity stageActivity;
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                Intrinsics.checkNotNullParameter(args, "args");
                LunoValue lunoValue = args.get(0);
                LunoValue.String string = lunoValue instanceof LunoValue.String ? (LunoValue.String) lunoValue : null;
                if (string == null || (value = string.getValue()) == null) {
                    throw new LunoRuntimeError("removeView expects a string view ID.", -1, null, 4, null);
                }
                WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
                if (weakReference == null || (stageActivity = weakReference.get()) == null) {
                    throw new LunoRuntimeError("removeView: Could not get StageActivity.", -1, null, 4, null);
                }
                stageActivity.removeViewFromStage(value);
                return LunoValue.Null.INSTANCE;
            }
        });
    }

    public /* synthetic */ Interpreter(Context context, org.catrobat.catroid.content.Scope scope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : context, (i & 2) != 0 ? null : scope);
    }

    public final Scope getGlobals() {
        return this.globals;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter$4.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.lunoscript.Interpreter$4, reason: invalid class name */
    /* JADX INFO: compiled from: Interpreter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "<anonymous parameter 0>", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "args", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass4 extends Lambda implements Function2<Interpreter, List<? extends LunoValue>, LunoValue> {
        AnonymousClass4() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
            Intrinsics.checkNotNullParameter(interpreter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(args, "args");
            final Interpreter interpreter2 = Interpreter.this;
            final String strJoinToString$default = CollectionsKt.joinToString$default(args, " ", null, null, 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$4$actualMessageString$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(LunoValue it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return interpreter2.lunoValueToString(it, true);
                }
            }, 30, null);
            WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
            if ((weakReference != null ? weakReference.get() : null) == null || StageActivity.messageHandler == null) {
                final Context appContext = Interpreter.this.androidContext;
                if (appContext == null) {
                    appContext = CatroidApplication.getAppContext();
                }
                if (appContext != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$4$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Interpreter.AnonymousClass4.invoke$lambda$0(appContext, strJoinToString$default);
                        }
                    });
                }
            } else {
                StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(strJoinToString$default))).sendToTarget();
            }
            return LunoValue.Null.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(Context context, String actualMessageString) {
            Intrinsics.checkNotNullParameter(actualMessageString, "$actualMessageString");
            Toast.makeText(context, actualMessageString, 0).show();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter$49.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.lunoscript.Interpreter$49, reason: invalid class name */
    /* JADX INFO: compiled from: Interpreter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "args", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass49 extends Lambda implements Function2<Interpreter, List<? extends LunoValue>, LunoValue> {
        public static final AnonymousClass49 INSTANCE = new AnonymousClass49();

        AnonymousClass49() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(args, "args");
            return new LunoValue.String(invoke$stringifyValue(args.get(0)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final String invoke$stringifyValue(LunoValue lunoValue) {
            if (lunoValue instanceof LunoValue.Null) {
                return "null";
            }
            if (lunoValue instanceof LunoValue.Boolean) {
                return String.valueOf(((LunoValue.Boolean) lunoValue).getValue());
            }
            if (lunoValue instanceof LunoValue.Number) {
                LunoValue.Number number = (LunoValue.Number) lunoValue;
                return (Double.isNaN(number.getValue()) || Double.isInfinite(number.getValue())) ? "null" : lunoValue.toString();
            }
            if (lunoValue instanceof LunoValue.String) {
                return "\"" + StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(((LunoValue.String) lunoValue).getValue(), "\\", "\\\\", false, 4, (Object) null), "\"", "\\\"", false, 4, (Object) null), "\n", "\\n", false, 4, (Object) null), "\r", "\\r", false, 4, (Object) null), "\t", "\\t", false, 4, (Object) null) + "\"";
            }
            if (lunoValue instanceof LunoValue.List) {
                return CollectionsKt.joinToString$default(((LunoValue.List) lunoValue).getElements(), ",", "[", "]", 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$49$stringifyValue$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(LunoValue it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Interpreter.AnonymousClass49.invoke$stringifyValue(it);
                    }
                }, 24, null);
            }
            if (lunoValue instanceof LunoValue.LunoObject) {
                return CollectionsKt.joinToString$default(((LunoValue.LunoObject) lunoValue).getFields().entrySet(), ",", "{", "}", 0, null, new Function1<Map.Entry<String, LunoValue>, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$49$stringifyValue$2
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(Map.Entry<String, LunoValue> entry) {
                        Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                        return "\"" + entry.getKey() + "\":" + Interpreter.AnonymousClass49.invoke$stringifyValue(entry.getValue());
                    }
                }, 24, null);
            }
            if (lunoValue instanceof LunoValue.NativeObject) {
                return "\"<NativeObject: " + Reflection.getOrCreateKotlinClass(((LunoValue.NativeObject) lunoValue).getObj().getClass()).getSimpleName() + ">\"";
            }
            return lunoValue instanceof LunoValue.Callable ? "\"<Callable>\"" : "\"<Unsupported LunoValue for JSON>\"";
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter$179.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.lunoscript.Interpreter$179, reason: invalid class name */
    /* JADX INFO: compiled from: Interpreter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "args", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass179 extends Lambda implements Function2<Interpreter, List<? extends LunoValue>, LunoValue> {
        public static final AnonymousClass179 INSTANCE = new AnonymousClass179();

        AnonymousClass179() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LunoValue invoke(final Interpreter interpreter, List<? extends LunoValue> args) {
            Class<?> klass;
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(args, "args");
            LunoValue lunoValue = args.get(0);
            LunoValue.NativeClass nativeClass = lunoValue instanceof LunoValue.NativeClass ? (LunoValue.NativeClass) lunoValue : null;
            if (nativeClass == null || (klass = nativeClass.getKlass()) == null) {
                throw new LunoRuntimeError("createListener: Argument 1 must be a native interface class (e.g., OnClickListener.class).", -1, null, 4, null);
            }
            LunoValue lunoValue2 = args.get(1);
            final LunoValue.Callable callable = lunoValue2 instanceof LunoValue.Callable ? (LunoValue.Callable) lunoValue2 : null;
            if (callable == null) {
                throw new LunoRuntimeError("createListener: Argument 2 must be a function.", -1, null, 4, null);
            }
            if (!klass.isInterface()) {
                throw new LunoRuntimeError("createListener: '" + klass.getSimpleName() + "' is not an interface.", -1, null, 4, null);
            }
            Object objNewProxyInstance = Proxy.newProxyInstance(klass.getClassLoader(), new Class[]{klass}, new InvocationHandler() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$179$$ExternalSyntheticLambda0
                @Override // java.lang.reflect.InvocationHandler
                public final Object invoke(Object obj, Method method, Object[] objArr) {
                    return Interpreter.AnonymousClass179.invoke$lambda$1(callable, interpreter, obj, method, objArr);
                }
            });
            Intrinsics.checkNotNull(objNewProxyInstance);
            return new LunoValue.NativeObject(objNewProxyInstance);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Object invoke$lambda$1(LunoValue.Callable lunoLambda, Interpreter interpreter, Object obj, Method method, Object[] objArr) {
            Intrinsics.checkNotNullParameter(lunoLambda, "$lunoLambda");
            Intrinsics.checkNotNullParameter(interpreter, "$interpreter");
            if (objArr == null) {
                objArr = new Object[0];
            }
            ArrayList arrayList = new ArrayList(objArr.length);
            for (Object obj2 : objArr) {
                arrayList.add(LunoValue.INSTANCE.fromKotlin(obj2));
            }
            LunoValue lunoValueCall = lunoLambda.call(interpreter, arrayList, new Token(TokenType.EOF, "", null, -1, 0));
            Class<?> returnType = method.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            return interpreter.lunoValueToKotlin(lunoValueCall, returnType);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter$181.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.lunoscript.Interpreter$181, reason: invalid class name */
    /* JADX INFO: compiled from: Interpreter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "args", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass181 extends Lambda implements Function2<Interpreter, List<? extends LunoValue>, LunoValue> {
        AnonymousClass181() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(args, "args");
            final LunoValue lunoValue = args.get(0);
            final Scope scope = interpreter.currentScope;
            Application application = Gdx.f82app;
            final Interpreter interpreter2 = Interpreter.this;
            application.postRunnable(new Runnable() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$181$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Interpreter.AnonymousClass181.invoke$lambda$1(interpreter2, lunoValue, scope);
                }
            });
            return LunoValue.Null.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Interpreter this$0, LunoValue argument, Scope capturedScope) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(argument, "$argument");
            Intrinsics.checkNotNullParameter(capturedScope, "$capturedScope");
            try {
                Interpreter interpreter = new Interpreter(this$0.androidContext, this$0.projectScope);
                interpreter.currentScope = capturedScope;
                if (argument instanceof LunoValue.String) {
                    interpreter.interpret(new Parser(new Lexer(((LunoValue.String) argument).getValue()).scanTokens()).parse());
                } else if (argument instanceof LunoValue.Callable) {
                    ((LunoValue.Callable) argument).call(interpreter, CollectionsKt.emptyList(), new Token(TokenType.EOF, "", null, -1, 0));
                } else {
                    ErrorLog.INSTANCE.log("runInRenderThread expects a string or a function, but got " + Reflection.getOrCreateKotlinClass(argument.getClass()).getSimpleName() + ".");
                }
            } catch (Exception e) {
                Log.e("LunoRenderThread", "Error executing task in render thread: " + e.getMessage(), e);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/Interpreter$182.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.utils.lunoscript.Interpreter$182, reason: invalid class name */
    /* JADX INFO: compiled from: Interpreter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "args", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass182 extends Lambda implements Function2<Interpreter, List<? extends LunoValue>, LunoValue> {
        AnonymousClass182() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LunoValue invoke(Interpreter interpreter, List<? extends LunoValue> args) {
            Intrinsics.checkNotNullParameter(interpreter, "interpreter");
            Intrinsics.checkNotNullParameter(args, "args");
            final LunoValue lunoValue = args.get(0);
            final Scope scope = interpreter.currentScope;
            Handler handler = new Handler(Looper.getMainLooper());
            final Interpreter interpreter2 = Interpreter.this;
            handler.post(new Runnable() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$182$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Interpreter.AnonymousClass182.invoke$lambda$1(interpreter2, lunoValue, scope);
                }
            });
            return LunoValue.Null.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$1(Interpreter this$0, LunoValue argument, Scope capturedScope) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(argument, "$argument");
            Intrinsics.checkNotNullParameter(capturedScope, "$capturedScope");
            try {
                Interpreter interpreter = new Interpreter(this$0.androidContext, this$0.projectScope);
                interpreter.currentScope = capturedScope;
                if (argument instanceof LunoValue.String) {
                    interpreter.interpret(new Parser(new Lexer(((LunoValue.String) argument).getValue()).scanTokens()).parse());
                } else if (argument instanceof LunoValue.Callable) {
                    ((LunoValue.Callable) argument).call(interpreter, CollectionsKt.emptyList(), new Token(TokenType.EOF, "", null, -1, 0));
                } else {
                    ErrorLog.INSTANCE.log("runInUIThread expects a string or a function, but got " + Reflection.getOrCreateKotlinClass(argument.getClass()).getSimpleName() + ".");
                }
            } catch (Exception e) {
                Log.e("LunoUIThread", "Error executing task in UI thread: " + e.getMessage(), e);
            }
        }
    }

    private final Object toKotlinType(LunoValue value) {
        if (value instanceof LunoValue.Null) {
            return null;
        }
        if (value instanceof LunoValue.Number) {
            return Double.valueOf(((LunoValue.Number) value).getValue());
        }
        if (value instanceof LunoValue.String) {
            return ((LunoValue.String) value).getValue();
        }
        if (value instanceof LunoValue.Boolean) {
            return Boolean.valueOf(((LunoValue.Boolean) value).getValue());
        }
        if (!(value instanceof LunoValue.List)) {
            if (!(value instanceof LunoValue.LunoObject)) {
                if (value instanceof LunoValue.NativeObject) {
                    return ((LunoValue.NativeObject) value).getObj();
                }
                if (value instanceof LunoValue.Callable) {
                    return value.toString();
                }
                return value instanceof LunoValue.Float ? Float.valueOf(((LunoValue.Float) value).getValue()) : value;
            }
            Map<String, LunoValue> fields = ((LunoValue.LunoObject) value).getFields();
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(fields.size()));
            Iterator<T> it = fields.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap.put(entry.getKey(), toKotlinType((LunoValue) entry.getValue()));
            }
            return MapsKt.toMutableMap(linkedHashMap);
        }
        List<LunoValue> elements = ((LunoValue.List) value).getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        Iterator<T> it2 = elements.iterator();
        while (it2.hasNext()) {
            arrayList.add(toKotlinType((LunoValue) it2.next()));
        }
        return CollectionsKt.toMutableList((Collection) arrayList);
    }

    public final InternTokenType mapStringToInternTokenType(String typeName) {
        Intrinsics.checkNotNullParameter(typeName, "typeName");
        String upperCase = typeName.toUpperCase();
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        int iHashCode = upperCase.hashCode();
        if (iHashCode != -1981034679) {
            if (iHashCode != -1838656495) {
                if (iHashCode == 782694408 && upperCase.equals("BOOLEAN")) {
                    return InternTokenType.STRING;
                }
            } else if (upperCase.equals("STRING")) {
                return InternTokenType.STRING;
            }
        } else if (upperCase.equals("NUMBER")) {
            return InternTokenType.NUMBER;
        }
        System.out.println((Object) ("Warning: Unknown parameter type name '" + typeName + "', defaulting to UNKNOWN."));
        return InternTokenType.STRING;
    }

    public final int getKotlinIntFromLunoValue(LunoValue lunoVal, String functionNameForError, int argPositionForError) {
        Intrinsics.checkNotNullParameter(lunoVal, "lunoVal");
        Intrinsics.checkNotNullParameter(functionNameForError, "functionNameForError");
        if (lunoVal instanceof LunoValue.Number) {
            double value = ((LunoValue.Number) lunoVal).getValue();
            if (value < -2.147483648E9d || value > 2.147483647E9d) {
                throw new LunoRuntimeError(functionNameForError + ": Number value " + value + " for argument " + argPositionForError + " is out of Int range.", -1, null, 4, null);
            }
            return (int) value;
        }
        throw new LunoRuntimeError(functionNameForError + ": Argument " + argPositionForError + " must be a Number to be converted to Int. Got " + Reflection.getOrCreateKotlinClass(lunoVal.getClass()).getSimpleName() + ".", -1, null, 4, null);
    }

    public final Project getProjectFromLunoValue(LunoValue lunoArg) {
        Class<?> cls;
        Intrinsics.checkNotNullParameter(lunoArg, "lunoArg");
        if (lunoArg instanceof LunoValue.NativeObject) {
            Object obj = ((LunoValue.NativeObject) lunoArg).getObj();
            if (obj instanceof Project) {
                return (Project) obj;
            }
            String name2 = (obj == null || (cls = obj.getClass()) == null) ? null : cls.getName();
            if (name2 == null) {
                name2 = "null";
            }
            throw new LunoRuntimeError("GetSprite: Argument 0's native object is not a Scope. Got type: " + name2 + ".", -1, null, 4, null);
        }
        throw new LunoRuntimeError("GetSprite: Argument 0 must be a NativeObject containing a Scope. Got type: " + Reflection.getOrCreateKotlinClass(lunoArg.getClass()).getSimpleName() + ".", -1, null, 4, null);
    }

    public final void defineNative$catroid_apktemplateTemplateRelease(String name2, IntRange arity, Function2<? super Interpreter, ? super List<? extends LunoValue>, ? extends LunoValue> function) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(arity, "arity");
        Intrinsics.checkNotNullParameter(function, "function");
        this.globals.define(name2, new LunoValue.NativeCallable(new CallableNativeLunoFunction(name2, arity, function)));
    }

    public final void interpret(ProgramNode program) throws IllegalAccessException {
        Intrinsics.checkNotNullParameter(program, "program");
        Iterator<T> it = program.getStatements().iterator();
        while (it.hasNext()) {
            execute((Statement) it.next());
        }
    }

    private final void execute(Statement statement) throws IllegalAccessException {
        if (statement instanceof ExpressionStatement) {
            evaluate(((ExpressionStatement) statement).getExpression());
            return;
        }
        if (statement instanceof VarDeclarationStatement) {
            executeVarDeclaration((VarDeclarationStatement) statement);
            return;
        }
        if (statement instanceof AssignmentStatement) {
            executeAssignment((AssignmentStatement) statement);
            return;
        }
        if (statement instanceof BlockStatement) {
            executeBlock(((BlockStatement) statement).getStatements(), new Scope(this.currentScope));
            return;
        }
        if (statement instanceof IfStatement) {
            executeIfStatement((IfStatement) statement);
            return;
        }
        if (statement instanceof WhileStatement) {
            executeWhileStatement((WhileStatement) statement);
            return;
        }
        if (statement instanceof ForInStatement) {
            executeForInStatement((ForInStatement) statement);
            return;
        }
        if (statement instanceof SwitchStatement) {
            executeSwitchStatement((SwitchStatement) statement);
            return;
        }
        if (statement instanceof FunDeclarationStatement) {
            executeFunDeclaration((FunDeclarationStatement) statement);
            return;
        }
        if (statement instanceof ReturnStatement) {
            executeReturnStatement((ReturnStatement) statement);
            return;
        }
        if (statement instanceof BreakStatement) {
            executeBreakStatement((BreakStatement) statement);
            return;
        }
        if (statement instanceof ContinueStatement) {
            executeContinueStatement((ContinueStatement) statement);
            return;
        }
        if (statement instanceof ClassDeclarationStatement) {
            executeClassDeclaration((ClassDeclarationStatement) statement);
        } else if (statement instanceof ImportStatement) {
            executeImportStatement((ImportStatement) statement);
        } else if (statement instanceof TryCatchStatement) {
            executeTryCatchStatement((TryCatchStatement) statement);
        }
    }

    private final void executeTryCatchStatement(TryCatchStatement stmt) throws IllegalAccessException {
        try {
            try {
                execute(stmt.getTryBlock());
                if (stmt.getFinallyBlock() == null) {
                    return;
                }
            } catch (LunoRuntimeError e) {
                if (stmt.getCatchBlock() != null && stmt.getCatchVariable() != null) {
                    Scope scope = new Scope(this.currentScope);
                    Pair[] pairArr = new Pair[2];
                    String message = e.getMessage();
                    if (message == null) {
                        message = "Unknown error";
                    }
                    pairArr[0] = TuplesKt.to(CrashHianalyticsData.MESSAGE, new LunoValue.String(message));
                    pairArr[1] = TuplesKt.to(Constant.LINE, new LunoValue.Number(e.getLine()));
                    scope.define(stmt.getCatchVariable().getLexeme(), new LunoValue.LunoObject(null, MapsKt.mutableMapOf(pairArr)));
                    executeBlock(CollectionsKt.listOf(stmt.getCatchBlock()), scope);
                    if (stmt.getFinallyBlock() == null) {
                        return;
                    }
                } else {
                    throw e;
                }
            }
            execute(stmt.getFinallyBlock());
        } catch (Throwable th) {
            if (stmt.getFinallyBlock() != null) {
                execute(stmt.getFinallyBlock());
            }
            throw th;
        }
    }

    private final void executeImportStatement(ImportStatement stmt) {
        String strJoinToString$default = CollectionsKt.joinToString$default(stmt.getPath(), ".", null, null, 0, null, new Function1<Token, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$executeImportStatement$fullClassName$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Token it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getLexeme();
            }
        }, 30, null);
        try {
            Class<?> cls = Class.forName(strJoinToString$default);
            String simpleName = cls.getSimpleName();
            Intrinsics.checkNotNull(cls);
            LunoValue.NativeClass nativeClass = new LunoValue.NativeClass(cls);
            Scope scope = this.currentScope;
            Intrinsics.checkNotNull(simpleName);
            scope.define(simpleName, nativeClass);
        } catch (ClassNotFoundException e) {
            throw new LunoRuntimeError("Class '" + strJoinToString$default + "' not found.", stmt.getLine(), e);
        } catch (Exception e2) {
            throw new LunoRuntimeError("Error while importing '" + strJoinToString$default + "': " + e2.getMessage(), stmt.getLine(), e2);
        }
    }

    public final void executeBlock(List<? extends Statement> statements, Scope environment) {
        Intrinsics.checkNotNullParameter(statements, "statements");
        Intrinsics.checkNotNullParameter(environment, "environment");
        Scope scope = this.currentScope;
        try {
            this.currentScope = environment;
            Iterator<? extends Statement> it = statements.iterator();
            while (it.hasNext()) {
                execute(it.next());
            }
            this.currentScope = scope;
        } catch (Throwable th) {
            this.currentScope = scope;
            throw th;
        }
    }

    private final void executeVarDeclaration(VarDeclarationStatement stmt) {
        LunoValue.Null nullEvaluate;
        Expression initializer = stmt.getInitializer();
        if (initializer == null || (nullEvaluate = evaluate(initializer)) == null) {
            nullEvaluate = LunoValue.Null.INSTANCE;
        }
        this.currentScope.define(stmt.getName().getLexeme(), nullEvaluate, stmt.isConstant());
    }

    private final void executeAssignment(AssignmentStatement stmt) throws IllegalAccessException {
        LunoValue lunoValueEvaluate;
        Method method;
        LunoValue lunoValueEvaluateIndexAccessExpr;
        Expression target = stmt.getTarget();
        if (stmt.getOperatorToken().getType() != TokenType.ASSIGN) {
            if (target instanceof VariableExpr) {
                lunoValueEvaluateIndexAccessExpr = lookUpVariable(((VariableExpr) target).getName(), target);
            } else if (target instanceof GetExpr) {
                lunoValueEvaluateIndexAccessExpr = evaluateGetExpr((GetExpr) target, false);
            } else {
                if (!(target instanceof IndexAccessExpr)) {
                    throw new LunoRuntimeError("Invalid target for compound assignment.", stmt.getLine(), null, 4, null);
                }
                lunoValueEvaluateIndexAccessExpr = evaluateIndexAccessExpr((IndexAccessExpr) target, false);
            }
            lunoValueEvaluate = evaluateCompoundAssignment(lunoValueEvaluateIndexAccessExpr, stmt.getOperatorToken(), evaluate(stmt.getValue()), stmt.getLine());
        } else {
            lunoValueEvaluate = evaluate(stmt.getValue());
        }
        boolean z = target instanceof GetExpr;
        if (z) {
            GetExpr getExpr = (GetExpr) target;
            LunoValue lunoValueEvaluate2 = evaluate(getExpr.getObj());
            if (lunoValueEvaluate2 instanceof LunoValue.NativeObject) {
                Object obj = ((LunoValue.NativeObject) lunoValueEvaluate2).getObj();
                String lexeme = getExpr.getName().getLexeme();
                LunoValue lunoValueEvaluate3 = evaluate(stmt.getValue());
                String str = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX + StringsKt.capitalize(lexeme);
                Field declaredField = null;
                try {
                    Method[] methods = obj.getClass().getMethods();
                    Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
                    Method[] methodArr = methods;
                    int length = methodArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            method = null;
                            break;
                        }
                        method = methodArr[i];
                        Method method2 = method;
                        if (Intrinsics.areEqual(method2.getName(), str) && method2.getParameterCount() == 1) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    Method method3 = method;
                    if (method3 != null) {
                        Class<?> cls = method3.getParameterTypes()[0];
                        Intrinsics.checkNotNull(cls);
                        method3.invoke(obj, lunoValueToKotlin(lunoValueEvaluate3, cls));
                        return;
                    }
                } catch (Exception unused) {
                }
                try {
                    for (Class<?> superclass = obj.getClass(); superclass != null; superclass = superclass.getSuperclass()) {
                        try {
                            declaredField = superclass.getDeclaredField(lexeme);
                            break;
                        } catch (NoSuchFieldException unused2) {
                        }
                    }
                    if (declaredField != null) {
                        declaredField.setAccessible(true);
                        Class<?> type = declaredField.getType();
                        Intrinsics.checkNotNull(type);
                        declaredField.set(obj, lunoValueToKotlin(lunoValueEvaluate3, type));
                        return;
                    }
                } catch (NoSuchFieldException unused3) {
                }
                throw new LunoRuntimeError("Property '" + lexeme + "' not found or is not writable.", target.getLine(), null, 4, null);
            }
        }
        if (target instanceof VariableExpr) {
            this.currentScope.assign(((VariableExpr) target).getName(), lunoValueEvaluate);
            return;
        }
        if (z) {
            GetExpr getExpr2 = (GetExpr) target;
            LunoValue lunoValueEvaluate4 = evaluate(getExpr2.getObj());
            if (lunoValueEvaluate4 instanceof LunoValue.LunoObject) {
                ((LunoValue.LunoObject) lunoValueEvaluate4).getFields().put(getExpr2.getName().getLexeme(), lunoValueEvaluate);
                return;
            }
            if ((lunoValueEvaluate4 instanceof LunoValue.NativeObject) && TypeIntrinsics.isMutableMap(((LunoValue.NativeObject) lunoValueEvaluate4).getObj())) {
                try {
                    Object obj2 = ((LunoValue.NativeObject) lunoValueEvaluate4).getObj();
                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.String, kotlin.Any?>");
                    TypeIntrinsics.asMutableMap(obj2).put(((GetExpr) target).getName().getLexeme(), toKotlinType(lunoValueEvaluate));
                    return;
                } catch (Exception e) {
                    throw new LunoRuntimeError("Failed to set property on native map: " + e.getMessage(), target.getLine(), null, 4, null);
                }
            }
            throw new LunoRuntimeError("Can only assign properties to Luno objects or native mutable maps. Got " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate4.getClass()).getSimpleName() + ".", target.getLine(), null, 4, null);
        }
        if (target instanceof IndexAccessExpr) {
            IndexAccessExpr indexAccessExpr = (IndexAccessExpr) target;
            LunoValue lunoValueEvaluate5 = evaluate(indexAccessExpr.getCallee());
            LunoValue lunoValueEvaluate6 = evaluate(indexAccessExpr.getIndex());
            if (lunoValueEvaluate5 instanceof LunoValue.List) {
                if (lunoValueEvaluate6 instanceof LunoValue.Number) {
                    int value = (int) ((LunoValue.Number) lunoValueEvaluate6).getValue();
                    if (value >= 0) {
                        LunoValue.List list = (LunoValue.List) lunoValueEvaluate5;
                        if (value < list.getElements().size()) {
                            list.getElements().set(value, lunoValueEvaluate);
                            return;
                        }
                    }
                    LunoValue.List list2 = (LunoValue.List) lunoValueEvaluate5;
                    if (value == list2.getElements().size()) {
                        list2.getElements().add(lunoValueEvaluate);
                        return;
                    }
                    throw new LunoRuntimeError("Index " + value + " out of bounds for list of size " + list2.getElements().size() + ".", target.getLine(), null, 4, null);
                }
                throw new LunoRuntimeError("List index must be a number.", target.getLine(), null, 4, null);
            }
            if (lunoValueEvaluate5 instanceof LunoValue.LunoObject) {
                ((LunoValue.LunoObject) lunoValueEvaluate5).getFields().put(lunoValueToString(lunoValueEvaluate6, false), lunoValueEvaluate);
                return;
            }
            if (lunoValueEvaluate5 instanceof LunoValue.NativeObject) {
                if (TypeIntrinsics.isMutableMap(((LunoValue.NativeObject) lunoValueEvaluate5).getObj())) {
                    try {
                        Object obj3 = ((LunoValue.NativeObject) lunoValueEvaluate5).getObj();
                        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.Any?, kotlin.Any?>");
                        TypeIntrinsics.asMutableMap(obj3).put(toKotlinType(lunoValueEvaluate6), toKotlinType(lunoValueEvaluate));
                        return;
                    } catch (Exception e2) {
                        throw new LunoRuntimeError("Failed to set value in native map by index/key: " + e2.getMessage(), target.getLine(), null, 4, null);
                    }
                }
                throw new LunoRuntimeError("Native object does not support indexed assignment.", target.getLine(), null, 4, null);
            }
            throw new LunoRuntimeError("Target for indexed assignment must be a list, map or supported native collection.", target.getLine(), null, 4, null);
        }
        throw new LunoRuntimeError("Invalid assignment target.", stmt.getLine(), null, 4, null);
    }

    private final LunoValue evaluateCompoundAssignment(LunoValue current, Token op, LunoValue right, int line) {
        int i = WhenMappings.$EnumSwitchMapping$0[op.getType().ordinal()];
        if (i == 1) {
            return operateBinary(current, new Token(TokenType.PLUS, Marker.ANY_NON_NULL_MARKER, null, line, 0), right, line);
        }
        if (i == 2) {
            return operateBinary(current, new Token(TokenType.MINUS, Parameters.DEFAULT_OPTION_PREFIXES, null, line, 0), right, line);
        }
        if (i == 3) {
            return operateBinary(current, new Token(TokenType.MULTIPLY, "*", null, line, 0), right, line);
        }
        if (i == 4) {
            return operateBinary(current, new Token(TokenType.DIVIDE, "/", null, line, 0), right, line);
        }
        if (i == 5) {
            return operateBinary(current, new Token(TokenType.MODULO, "%", null, line, 0), right, line);
        }
        throw new LunoRuntimeError("Unsupported compound assignment operator: " + op.getLexeme(), line, null, 4, null);
    }

    private final void executeIfStatement(IfStatement stmt) throws IllegalAccessException {
        if (evaluate(stmt.getCondition()).isTruthy()) {
            execute(stmt.getThenBranch());
        } else if (stmt.getElseBranch() != null) {
            execute(stmt.getElseBranch());
        }
    }

    private final void executeWhileStatement(WhileStatement stmt) {
        this.loopDepth++;
        while (evaluate(stmt.getCondition()).isTruthy()) {
            try {
                try {
                    execute(stmt.getBody());
                } catch (ContinueSignal unused) {
                }
            } catch (BreakSignal unused2) {
            } finally {
                this.loopDepth--;
            }
        }
    }

    private final void executeForInStatement(ForInStatement stmt) {
        ArrayList elements;
        this.loopDepth++;
        LunoValue lunoValueEvaluate = evaluate(stmt.getIterable());
        if (lunoValueEvaluate instanceof LunoValue.List) {
            elements = ((LunoValue.List) lunoValueEvaluate).getElements();
        } else if (!(lunoValueEvaluate instanceof LunoValue.String)) {
            if (lunoValueEvaluate instanceof LunoValue.NativeObject) {
                LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValueEvaluate;
                if (nativeObject.getObj() instanceof Iterable) {
                    Iterable iterable = (Iterable) nativeObject.getObj();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                    Iterator it = iterable.iterator();
                    while (it.hasNext()) {
                        arrayList.add(LunoValue.INSTANCE.fromKotlin(it.next()));
                    }
                    elements = arrayList;
                } else {
                    throw new LunoRuntimeError("'for-in' expects an iterable.", stmt.getForToken().getLine(), null, 4, null);
                }
            } else {
                throw new LunoRuntimeError("'for-in' loop requires an iterable (list, string). Got " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate.getClass()).getSimpleName() + ".", stmt.getForToken().getLine(), null, 4, null);
            }
        } else {
            String value = ((LunoValue.String) lunoValueEvaluate).getValue();
            ArrayList arrayList2 = new ArrayList(value.length());
            for (int i = 0; i < value.length(); i++) {
                arrayList2.add(new LunoValue.String(String.valueOf(value.charAt(i))));
            }
            elements = arrayList2;
        }
        try {
            Iterator it2 = elements.iterator();
            while (it2.hasNext()) {
                this.currentScope.define(stmt.getVariable().getLexeme(), (LunoValue) it2.next());
                try {
                    execute(stmt.getBody());
                } catch (ContinueSignal unused) {
                }
            }
        } catch (BreakSignal unused2) {
        } catch (Throwable th) {
            this.loopDepth--;
            throw th;
        }
        this.loopDepth--;
    }

    private final void executeSwitchStatement(SwitchStatement stmt) throws IllegalAccessException {
        LunoValue lunoValueEvaluate = evaluate(stmt.getExpression());
        for (SwitchCase switchCase : stmt.getCases()) {
            if (!switchCase.isDefault()) {
                List<Expression> valueExpressions = switchCase.getValueExpressions();
                Intrinsics.checkNotNull(valueExpressions);
                Iterator<Expression> it = valueExpressions.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!isEqual(lunoValueEvaluate, evaluate(it.next()))) {
                        }
                    }
                }
            }
            try {
                execute(switchCase.getBody());
                return;
            } catch (BreakSignal unused) {
                return;
            }
        }
    }

    private final void executeFunDeclaration(FunDeclarationStatement stmt) {
        Log.d("LUNO_INTERPRETER", "Executing FunDeclaration for: " + stmt.getName().getLexeme());
        this.currentScope.define(stmt.getName().getLexeme(), new LunoValue.LunoFunction(stmt, this.currentScope));
    }

    private final void executeReturnStatement(ReturnStatement stmt) {
        LunoValue.Null nullEvaluate;
        Expression value = stmt.getValue();
        if (value == null || (nullEvaluate = evaluate(value)) == null) {
            nullEvaluate = LunoValue.Null.INSTANCE;
        }
        throw new ReturnSignal(nullEvaluate);
    }

    private final void executeBreakStatement(BreakStatement stmt) {
        if (this.loopDepth != 0) {
            throw BreakSignal.INSTANCE;
        }
        throw new LunoRuntimeError("'break' is not inside a loop.", stmt.getLine(), null, 4, null);
    }

    private final void executeContinueStatement(ContinueStatement stmt) {
        if (this.loopDepth != 0) {
            throw ContinueSignal.INSTANCE;
        }
        throw new LunoRuntimeError("'continue' is not inside a loop.", stmt.getLine(), null, 4, null);
    }

    private final void executeClassDeclaration(ClassDeclarationStatement stmt) {
        LunoValue.LunoClass lunoClass;
        Class<?> klass;
        stmt.getName().getLexeme();
        if (stmt.getSuperclass() != null) {
            LunoValue lunoValueLookUpVariable = lookUpVariable(stmt.getSuperclass().getName(), stmt.getSuperclass());
            if (lunoValueLookUpVariable instanceof LunoValue.LunoClass) {
                lunoClass = (LunoValue.LunoClass) lunoValueLookUpVariable;
                klass = null;
            } else {
                if (!(lunoValueLookUpVariable instanceof LunoValue.NativeClass)) {
                    throw new LunoRuntimeError("Superclass must be a class (Luno or native). Got " + Reflection.getOrCreateKotlinClass(lunoValueLookUpVariable.getClass()).getSimpleName() + ".", stmt.getSuperclass().getLine(), null, 4, null);
                }
                klass = ((LunoValue.NativeClass) lunoValueLookUpVariable).getKlass();
                lunoClass = null;
            }
        } else {
            lunoClass = null;
            klass = null;
        }
        List<FunDeclarationStatement> methods = stmt.getMethods();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(methods, 10)), 16));
        for (FunDeclarationStatement funDeclarationStatement : methods) {
            Pair pair = TuplesKt.to(funDeclarationStatement.getName().getLexeme(), new LunoValue.LunoFunction(funDeclarationStatement, this.currentScope));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.currentScope.define(stmt.getName().getLexeme(), new LunoValue.LunoClass(stmt.getName().getLexeme(), stmt, linkedHashMap, lunoClass, klass));
    }

    private final LunoValue evaluate(Expression expr) {
        String lexeme;
        if (expr instanceof LiteralExpr) {
            return ((LiteralExpr) expr).getValue();
        }
        if (expr instanceof VariableExpr) {
            return lookUpVariable(((VariableExpr) expr).getName(), expr);
        }
        if (expr instanceof ThisExpr) {
            return lookUpVariable(((ThisExpr) expr).getKeyword(), expr);
        }
        if (expr instanceof BinaryExpr) {
            BinaryExpr binaryExpr = (BinaryExpr) expr;
            return operateBinary(evaluate(binaryExpr.getLeft()), binaryExpr.getOperator(), evaluate(binaryExpr.getRight()), expr.getLine());
        }
        if (expr instanceof LogicalExpr) {
            return evaluateLogicalExpr((LogicalExpr) expr);
        }
        if (expr instanceof UnaryExpr) {
            return evaluateUnaryExpr((UnaryExpr) expr);
        }
        if (expr instanceof CallExpr) {
            return evaluateCallExpr((CallExpr) expr);
        }
        if (expr instanceof GetExpr) {
            return evaluateGetExpr$default(this, (GetExpr) expr, false, 2, null);
        }
        if (expr instanceof IndexAccessExpr) {
            return evaluateIndexAccessExpr$default(this, (IndexAccessExpr) expr, false, 2, null);
        }
        if (!(expr instanceof ListLiteralExpr)) {
            if (expr instanceof InterpolatedStringExpr) {
                return evaluateInterpolatedString((InterpolatedStringExpr) expr);
            }
            if (expr instanceof LambdaExpr) {
                LambdaExpr lambdaExpr = (LambdaExpr) expr;
                return new LunoValue.LunoFunction(new FunDeclarationStatement(new Token(TokenType.IDENTIFIER, "lambda", null, expr.getLine(), 0), lambdaExpr.getParams(), lambdaExpr.getBody(), expr.getLine()), this.currentScope);
            }
            if (expr instanceof MapLiteralExpr) {
                Map<Token, Expression> entries = ((MapLiteralExpr) expr).getEntries();
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(entries.size()));
                Iterator<T> it = entries.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    linkedHashMap.put(entry.getKey(), evaluate((Expression) entry.getValue()));
                }
                Map mutableMap = MapsKt.toMutableMap(linkedHashMap);
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(mutableMap.size()));
                for (Map.Entry entry2 : mutableMap.entrySet()) {
                    if (((Token) entry2.getKey()).getType() == TokenType.STRING_LITERAL) {
                        Object literal = ((Token) entry2.getKey()).getLiteral();
                        Intrinsics.checkNotNull(literal, "null cannot be cast to non-null type kotlin.String");
                        lexeme = (String) literal;
                    } else {
                        lexeme = ((Token) entry2.getKey()).getLexeme();
                    }
                    linkedHashMap2.put(lexeme, entry2.getValue());
                }
                return new LunoValue.LunoObject(null, MapsKt.toMutableMap(linkedHashMap2));
            }
            throw new LunoRuntimeError("Cannot evaluate AST node of type " + Reflection.getOrCreateKotlinClass(expr.getClass()).getSimpleName(), expr.getLine(), null, 4, null);
        }
        List<Expression> elements = ((ListLiteralExpr) expr).getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        Iterator<T> it2 = elements.iterator();
        while (it2.hasNext()) {
            arrayList.add(evaluate((Expression) it2.next()));
        }
        return new LunoValue.List(CollectionsKt.toMutableList((Collection) arrayList));
    }

    private final LunoValue.String evaluateInterpolatedString(InterpolatedStringExpr expr) {
        StringBuilder sb = new StringBuilder();
        Iterator<Expression> it = expr.getParts().iterator();
        while (it.hasNext()) {
            sb.append(lunoValueToString(evaluate(it.next()), true));
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return new LunoValue.String(string);
    }

    private final LunoValue lookUpVariable(Token nameToken, AstNode exprNode) {
        if (Intrinsics.areEqual(nameToken.getLexeme(), "this")) {
            for (Scope enclosing$catroid_apktemplateTemplateRelease = this.currentScope; enclosing$catroid_apktemplateTemplateRelease != null; enclosing$catroid_apktemplateTemplateRelease = enclosing$catroid_apktemplateTemplateRelease.getEnclosing()) {
                if (enclosing$catroid_apktemplateTemplateRelease.getValues().containsKey("this")) {
                    Scope.ScopeEntry scopeEntry = enclosing$catroid_apktemplateTemplateRelease.getValues().get("this");
                    Intrinsics.checkNotNull(scopeEntry);
                    return scopeEntry.getValue();
                }
            }
            throw new LunoRuntimeError("'this' can only be used inside a method.", nameToken.getLine(), null, 4, null);
        }
        return this.currentScope.get(nameToken);
    }

    /* JADX WARN: Code duplicated, block: B:141:0x0294  */
    /* JADX WARN: Code duplicated, block: B:142:0x0298  */
    /* JADX WARN: Code duplicated, block: B:144:0x029b  */
    /* JADX WARN: Code duplicated, block: B:146:0x02a4  */
    /* JADX WARN: Code duplicated, block: B:148:0x02a8  */
    /* JADX WARN: Code duplicated, block: B:149:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:151:0x02af  */
    /* JADX WARN: Code duplicated, block: B:153:0x02b7 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:169:0x0309  */
    /* JADX WARN: Code duplicated, block: B:33:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:34:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:37:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:39:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:41:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:42:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:45:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:50:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:53:0x00e8  */
    /* JADX WARN: Code duplicated, block: B:56:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:58:0x00ef A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:62:0x0105 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:65:0x0114 A[LOOP:0: B:64:0x0112->B:65:0x0114, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:84:0x014f  */
    /* JADX WARN: Code duplicated, block: B:85:0x0153  */
    /* JADX WARN: Code duplicated, block: B:87:0x0156  */
    /* JADX WARN: Code duplicated, block: B:89:0x015f  */
    /* JADX WARN: Code duplicated, block: B:91:0x0163  */
    /* JADX WARN: Code duplicated, block: B:92:0x0167  */
    /* JADX WARN: Code duplicated, block: B:94:0x016a  */
    /* JADX WARN: Code duplicated, block: B:96:0x0172 A[ADDED_TO_REGION] */
    private final LunoValue operateBinary(LunoValue left, Token op, LunoValue right, int line) {
        Double dValueOf;
        double value;
        LunoValue.Number number;
        LunoValue.Float r7;
        double value2;
        Double dValueOf2;
        double value3;
        LunoValue.Number number2;
        LunoValue.Float r5;
        double value4;
        double dDoubleValue;
        LunoValue.Number number3;
        Integer numValueOf;
        int iIntValue;
        LunoValue.String string;
        String value5;
        LunoValue.List list;
        List<LunoValue> elements;
        ArrayList arrayList;
        int iCoerceAtLeast;
        boolean z = false;
        z = false;
        z = false;
        z = false;
        Double dValueOf3 = null;
        if (!SetsKt.setOf((Object[]) new TokenType[]{TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE, TokenType.MODULO}).contains(op.getType())) {
            if (SetsKt.setOf((Object[]) new TokenType[]{TokenType.GT, TokenType.GTE, TokenType.LT, TokenType.LTE}).contains(op.getType())) {
                LunoValue.Number number4 = left instanceof LunoValue.Number ? (LunoValue.Number) left : null;
                if (number4 == null) {
                    LunoValue.Float r3 = left instanceof LunoValue.Float ? (LunoValue.Float) left : null;
                    if (r3 != null) {
                        value = r3.getValue();
                    } else {
                        dValueOf = null;
                    }
                    if (right instanceof LunoValue.Number) {
                        number = (LunoValue.Number) right;
                    } else {
                        number = null;
                    }
                    if (number != null) {
                        if (right instanceof LunoValue.Float) {
                            r7 = (LunoValue.Float) right;
                        } else {
                            r7 = null;
                        }
                        if (r7 != null) {
                            value2 = r7.getValue();
                        }
                        if (dValueOf != null || dValueOf3 == null) {
                            throw new LunoRuntimeError("Operands for comparison must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
                        }
                        switch (WhenMappings.$EnumSwitchMapping$0[op.getType().ordinal()]) {
                            case 11:
                                if (dValueOf.doubleValue() > dValueOf3.doubleValue()) {
                                    z = true;
                                }
                                return new LunoValue.Boolean(z);
                            case 12:
                                if (dValueOf.doubleValue() >= dValueOf3.doubleValue()) {
                                    z = true;
                                }
                                return new LunoValue.Boolean(z);
                            case 13:
                                if (dValueOf.doubleValue() < dValueOf3.doubleValue()) {
                                    z = true;
                                }
                                return new LunoValue.Boolean(z);
                            case 14:
                                if (dValueOf.doubleValue() <= dValueOf3.doubleValue()) {
                                    z = true;
                                }
                                return new LunoValue.Boolean(z);
                            default:
                                throw new LunoRuntimeError("Unreachable: Unknown comparison operator.", line, null, 4, null);
                        }
                    }
                    value2 = number.getValue();
                    dValueOf3 = Double.valueOf(value2);
                    if (dValueOf != null) {
                    }
                    throw new LunoRuntimeError("Operands for comparison must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
                }
                value = number4.getValue();
                dValueOf = Double.valueOf(value);
                if (right instanceof LunoValue.Number) {
                    number = (LunoValue.Number) right;
                } else {
                    number = null;
                }
                if (number != null) {
                    if (right instanceof LunoValue.Float) {
                        r7 = (LunoValue.Float) right;
                    } else {
                        r7 = null;
                    }
                    if (r7 != null) {
                        value2 = r7.getValue();
                    }
                    if (dValueOf != null) {
                    }
                    throw new LunoRuntimeError("Operands for comparison must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
                }
                value2 = number.getValue();
                dValueOf3 = Double.valueOf(value2);
                if (dValueOf != null) {
                }
                throw new LunoRuntimeError("Operands for comparison must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
            }
            if (op.getType() == TokenType.EQ) {
                return new LunoValue.Boolean(isEqual(left, right));
            }
            if (op.getType() == TokenType.NEQ) {
                return new LunoValue.Boolean(!isEqual(left, right));
            }
            throw new LunoRuntimeError("Unknown binary operator: " + op.getLexeme(), line, null, 4, null);
        }
        if (op.getType() == TokenType.PLUS) {
            if ((left instanceof LunoValue.String) || (right instanceof LunoValue.String)) {
                return new LunoValue.String(lunoValueToString(left, true) + lunoValueToString(right, true));
            }
            if ((left instanceof LunoValue.List) && (right instanceof LunoValue.List)) {
                return new LunoValue.List(CollectionsKt.toMutableList((Collection) CollectionsKt.plus((Collection) ((LunoValue.List) left).getElements(), (Iterable) ((LunoValue.List) right).getElements())));
            }
        }
        if (op.getType() == TokenType.MULTIPLY) {
            if (left instanceof LunoValue.Number) {
                number3 = (LunoValue.Number) left;
            } else {
                number3 = right instanceof LunoValue.Number ? (LunoValue.Number) right : null;
                if (number3 == null) {
                    numValueOf = null;
                }
                if (numValueOf != null) {
                    iIntValue = numValueOf.intValue();
                } else {
                    iIntValue = -1;
                }
                if (left instanceof LunoValue.String) {
                    string = (LunoValue.String) left;
                } else {
                    if (right instanceof LunoValue.String) {
                        string = (LunoValue.String) right;
                    } else {
                        string = null;
                    }
                    if (string != null) {
                        value5 = null;
                    }
                    if (left instanceof LunoValue.List) {
                        list = (LunoValue.List) left;
                    } else {
                        if (right instanceof LunoValue.List) {
                            list = (LunoValue.List) right;
                        } else {
                            list = null;
                        }
                        if (list != null) {
                            elements = null;
                        }
                        if (iIntValue < 0 && value5 != null) {
                            return new LunoValue.String(StringsKt.repeat(value5, RangesKt.coerceAtLeast(iIntValue, 0)));
                        }
                        if (iIntValue >= 0 && elements != null) {
                            arrayList = new ArrayList();
                            iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                            for (int i = 0; i < iCoerceAtLeast; i++) {
                                arrayList.addAll(elements);
                            }
                            return new LunoValue.List(arrayList);
                        }
                    }
                    elements = list.getElements();
                    if (iIntValue < 0) {
                    }
                    if (iIntValue >= 0) {
                        arrayList = new ArrayList();
                        iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                        while (i < iCoerceAtLeast) {
                            arrayList.addAll(elements);
                        }
                        return new LunoValue.List(arrayList);
                    }
                }
                value5 = string.getValue();
                if (left instanceof LunoValue.List) {
                    list = (LunoValue.List) left;
                } else {
                    if (right instanceof LunoValue.List) {
                        list = (LunoValue.List) right;
                    } else {
                        list = null;
                    }
                    if (list != null) {
                        elements = null;
                    }
                    if (iIntValue < 0) {
                    }
                    if (iIntValue >= 0) {
                        arrayList = new ArrayList();
                        iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                        while (i < iCoerceAtLeast) {
                            arrayList.addAll(elements);
                        }
                        return new LunoValue.List(arrayList);
                    }
                }
                elements = list.getElements();
                if (iIntValue < 0) {
                }
                if (iIntValue >= 0) {
                    arrayList = new ArrayList();
                    iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                    while (i < iCoerceAtLeast) {
                        arrayList.addAll(elements);
                    }
                    return new LunoValue.List(arrayList);
                }
            }
            numValueOf = Integer.valueOf((int) number3.getValue());
            if (numValueOf != null) {
                iIntValue = numValueOf.intValue();
            } else {
                iIntValue = -1;
            }
            if (left instanceof LunoValue.String) {
                string = (LunoValue.String) left;
            } else {
                if (right instanceof LunoValue.String) {
                    string = (LunoValue.String) right;
                } else {
                    string = null;
                }
                if (string != null) {
                    value5 = null;
                }
                if (left instanceof LunoValue.List) {
                    list = (LunoValue.List) left;
                } else {
                    if (right instanceof LunoValue.List) {
                        list = (LunoValue.List) right;
                    } else {
                        list = null;
                    }
                    if (list != null) {
                        elements = null;
                    }
                    if (iIntValue < 0) {
                    }
                    if (iIntValue >= 0) {
                        arrayList = new ArrayList();
                        iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                        while (i < iCoerceAtLeast) {
                            arrayList.addAll(elements);
                        }
                        return new LunoValue.List(arrayList);
                    }
                }
                elements = list.getElements();
                if (iIntValue < 0) {
                }
                if (iIntValue >= 0) {
                    arrayList = new ArrayList();
                    iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                    while (i < iCoerceAtLeast) {
                        arrayList.addAll(elements);
                    }
                    return new LunoValue.List(arrayList);
                }
            }
            value5 = string.getValue();
            if (left instanceof LunoValue.List) {
                list = (LunoValue.List) left;
            } else {
                if (right instanceof LunoValue.List) {
                    list = (LunoValue.List) right;
                } else {
                    list = null;
                }
                if (list != null) {
                    elements = null;
                }
                if (iIntValue < 0) {
                }
                if (iIntValue >= 0) {
                    arrayList = new ArrayList();
                    iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                    while (i < iCoerceAtLeast) {
                        arrayList.addAll(elements);
                    }
                    return new LunoValue.List(arrayList);
                }
            }
            elements = list.getElements();
            if (iIntValue < 0) {
            }
            if (iIntValue >= 0) {
                arrayList = new ArrayList();
                iCoerceAtLeast = RangesKt.coerceAtLeast(iIntValue, 0);
                while (i < iCoerceAtLeast) {
                    arrayList.addAll(elements);
                }
                return new LunoValue.List(arrayList);
            }
        }
        LunoValue.Number number5 = left instanceof LunoValue.Number ? (LunoValue.Number) left : null;
        if (number5 == null) {
            LunoValue.Float r4 = left instanceof LunoValue.Float ? (LunoValue.Float) left : null;
            if (r4 != null) {
                value3 = r4.getValue();
            } else {
                dValueOf2 = null;
            }
            if (right instanceof LunoValue.Number) {
                number2 = (LunoValue.Number) right;
            } else {
                number2 = null;
            }
            if (number2 != null) {
                if (right instanceof LunoValue.Float) {
                    r5 = (LunoValue.Float) right;
                } else {
                    r5 = null;
                }
                if (r5 != null) {
                    value4 = r5.getValue();
                }
                if (dValueOf2 != null || dValueOf3 == null) {
                    throw new LunoRuntimeError("Operands for '" + op.getLexeme() + "' must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
                }
                if ((op.getType() == TokenType.DIVIDE || op.getType() == TokenType.MODULO) && Intrinsics.areEqual(dValueOf3, 0.0d)) {
                    throw new LunoRuntimeError("Division by zero.", line, null, 4, null);
                }
                switch (WhenMappings.$EnumSwitchMapping$0[op.getType().ordinal()]) {
                    case 6:
                        dDoubleValue = dValueOf2.doubleValue() + dValueOf3.doubleValue();
                        break;
                    case 7:
                        dDoubleValue = dValueOf2.doubleValue() - dValueOf3.doubleValue();
                        break;
                    case 8:
                        dDoubleValue = dValueOf2.doubleValue() * dValueOf3.doubleValue();
                        break;
                    case 9:
                        dDoubleValue = dValueOf2.doubleValue() / dValueOf3.doubleValue();
                        break;
                    case 10:
                        dDoubleValue = dValueOf2.doubleValue() % dValueOf3.doubleValue();
                        break;
                    default:
                        throw new LunoRuntimeError("Unreachable: Unknown arithmetic operator.", line, null, 4, null);
                }
                if ((left instanceof LunoValue.Float) && (right instanceof LunoValue.Float)) {
                    return new LunoValue.Float((float) dDoubleValue);
                }
                return new LunoValue.Number(dDoubleValue);
            }
            value4 = number2.getValue();
            dValueOf3 = Double.valueOf(value4);
            if (dValueOf2 != null) {
            }
            throw new LunoRuntimeError("Operands for '" + op.getLexeme() + "' must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
        }
        value3 = number5.getValue();
        dValueOf2 = Double.valueOf(value3);
        if (right instanceof LunoValue.Number) {
            number2 = (LunoValue.Number) right;
        } else {
            number2 = null;
        }
        if (number2 != null) {
            if (right instanceof LunoValue.Float) {
                r5 = (LunoValue.Float) right;
            } else {
                r5 = null;
            }
            if (r5 != null) {
                value4 = r5.getValue();
            }
            if (dValueOf2 != null) {
            }
            throw new LunoRuntimeError("Operands for '" + op.getLexeme() + "' must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
        }
        value4 = number2.getValue();
        dValueOf3 = Double.valueOf(value4);
        if (dValueOf2 != null) {
        }
        throw new LunoRuntimeError("Operands for '" + op.getLexeme() + "' must be numbers. Got " + Reflection.getOrCreateKotlinClass(left.getClass()).getSimpleName() + " and " + Reflection.getOrCreateKotlinClass(right.getClass()).getSimpleName(), line, null, 4, null);
    }

    private final LunoValue evaluateLogicalExpr(LogicalExpr expr) {
        LunoValue lunoValueEvaluate = evaluate(expr.getLeft());
        if (expr.getOperator().getType() == TokenType.OR) {
            if (lunoValueEvaluate.isTruthy()) {
                return lunoValueEvaluate;
            }
        } else if (!lunoValueEvaluate.isTruthy()) {
            return lunoValueEvaluate;
        }
        return evaluate(expr.getRight());
    }

    private final LunoValue evaluateUnaryExpr(UnaryExpr expr) {
        LunoValue lunoValueEvaluate = evaluate(expr.getRight());
        int i = WhenMappings.$EnumSwitchMapping$0[expr.getOperator().getType().ordinal()];
        if (i != 7) {
            if (i == 15) {
                return new LunoValue.Boolean(!lunoValueEvaluate.isTruthy());
            }
            throw new LunoRuntimeError("Unknown unary operator: " + expr.getOperator().getLexeme(), expr.getLine(), null, 4, null);
        }
        if (lunoValueEvaluate instanceof LunoValue.Number) {
            return new LunoValue.Number(-((LunoValue.Number) lunoValueEvaluate).getValue());
        }
        if (lunoValueEvaluate instanceof LunoValue.Float) {
            return new LunoValue.Float(-((LunoValue.Float) lunoValueEvaluate).getValue());
        }
        throw new LunoRuntimeError("Operand for unary '-' must be a number. Got " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate.getClass()).getSimpleName(), expr.getLine(), null, 4, null);
    }

    private final LunoValue evaluateCallExpr(CallExpr expr) {
        LunoValue lunoValueEvaluate = evaluate(expr.getCallee());
        List<Expression> arguments = expr.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(evaluate((Expression) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        Log.d("LunoCallExpr", "Calling " + expr.getCallee() + ", evaluated callee type: " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate.getClass()).getSimpleName() + ", value: " + lunoValueEvaluate);
        if (lunoValueEvaluate instanceof LunoValue.LunoFunction) {
            return ((LunoValue.LunoFunction) lunoValueEvaluate).call(this, arrayList2, expr.getParenToken());
        }
        if (lunoValueEvaluate instanceof LunoValue.NativeCallable) {
            return ((LunoValue.NativeCallable) lunoValueEvaluate).call(this, arrayList2, expr.getParenToken());
        }
        if (lunoValueEvaluate instanceof LunoValue.BoundMethod) {
            return ((LunoValue.BoundMethod) lunoValueEvaluate).call(this, arrayList2, expr.getParenToken());
        }
        if (lunoValueEvaluate instanceof LunoValue.NativeClass) {
            return ((LunoValue.NativeClass) lunoValueEvaluate).call(this, arrayList2, expr.getParenToken());
        }
        if (lunoValueEvaluate instanceof LunoValue.LunoClass) {
            return ((LunoValue.LunoClass) lunoValueEvaluate).call(this, arrayList2, expr.getParenToken());
        }
        if (lunoValueEvaluate instanceof LunoValue.BoundMethod2) {
            return ((LunoValue.BoundMethod2) lunoValueEvaluate).call(this, arrayList2, expr.getParenToken());
        }
        throw new LunoRuntimeError("Can only call functions, methods, or classes. Got " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate.getClass()).getSimpleName() + ".", expr.getLine(), null, 4, null);
    }

    static /* synthetic */ LunoValue evaluateGetExpr$default(Interpreter interpreter, GetExpr getExpr, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return interpreter.evaluateGetExpr(getExpr, z);
    }

    /* JADX WARN: Code duplicated, block: B:84:0x0276  */
    /* JADX WARN: Code duplicated, block: B:92:0x029f  */
    /* JADX WARN: Code duplicated, block: B:94:0x02ac A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:95:0x02ae  */
    /* JADX WARN: Code duplicated, block: B:97:0x02b3  */
    private final LunoValue evaluateGetExpr(GetExpr expr, boolean allowUndefined) {
        ArrayList arrayList;
        Method method;
        LunoValue.LunoFunction lunoFunctionFindMethod;
        LunoValue lunoValueEvaluate = evaluate(expr.getObj());
        if (lunoValueEvaluate instanceof LunoValue.LunoObject) {
            LunoValue.LunoObject lunoObject = (LunoValue.LunoObject) lunoValueEvaluate;
            if (lunoObject.getFields().containsKey(expr.getName().getLexeme())) {
                LunoValue lunoValue = lunoObject.getFields().get(expr.getName().getLexeme());
                Intrinsics.checkNotNull(lunoValue);
                return lunoValue;
            }
            LunoValue.LunoClass klass = lunoObject.getKlass();
            if (klass != null && (lunoFunctionFindMethod = klass.findMethod(expr.getName().getLexeme())) != null) {
                return new LunoValue.BoundMethod2(lunoObject, lunoFunctionFindMethod);
            }
            if (allowUndefined) {
                return LunoValue.Null.INSTANCE;
            }
            throw new LunoRuntimeError("Undefined property or method '" + expr.getName().getLexeme() + "' on object.", expr.getLine(), null, 4, null);
        }
        if (lunoValueEvaluate instanceof LunoValue.String) {
            if (Intrinsics.areEqual(expr.getName().getLexeme(), "length")) {
                return new LunoValue.Number(((LunoValue.String) lunoValueEvaluate).getValue().length());
            }
            if (allowUndefined) {
                return LunoValue.Null.INSTANCE;
            }
            throw new LunoRuntimeError("Undefined property '" + expr.getName().getLexeme() + "' on String.", expr.getLine(), null, 4, null);
        }
        if (lunoValueEvaluate instanceof LunoValue.List) {
            if (Intrinsics.areEqual(expr.getName().getLexeme(), "length")) {
                return new LunoValue.Number(((LunoValue.List) lunoValueEvaluate).getElements().size());
            }
            if (allowUndefined) {
                return LunoValue.Null.INSTANCE;
            }
            throw new LunoRuntimeError("Undefined property '" + expr.getName().getLexeme() + "' on List.", expr.getLine(), null, 4, null);
        }
        Method method2 = null;
        if (lunoValueEvaluate instanceof LunoValue.NativeObject) {
            Object obj = ((LunoValue.NativeObject) lunoValueEvaluate).getObj();
            String lexeme = expr.getName().getLexeme();
            if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.containsKey(lexeme)) {
                    return LunoValue.INSTANCE.fromKotlin(map.get(lexeme));
                }
            }
            if ((obj instanceof List) && (Intrinsics.areEqual(lexeme, "size") || Intrinsics.areEqual(lexeme, "length"))) {
                return new LunoValue.Number(((List) obj).size());
            }
            try {
                String str = "get" + StringsKt.capitalize(lexeme);
                String str2 = LanguageCodeUtil.IS + StringsKt.capitalize(lexeme);
                Method[] methods = obj.getClass().getMethods();
                Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
                for (Method method3 : methods) {
                    Method method4 = method3;
                    if ((Intrinsics.areEqual(method4.getName(), str) || Intrinsics.areEqual(method4.getName(), str2)) && method4.getParameterCount() == 0) {
                        method2 = method3;
                        break;
                    }
                }
                Method method5 = method2;
                if (method5 != null) {
                    return LunoValue.INSTANCE.fromKotlin(method5.invoke(obj, new Object[0]));
                }
            } catch (Exception unused) {
            }
            try {
                return LunoValue.INSTANCE.fromKotlin(obj.getClass().getField(lexeme).get(obj));
            } catch (NoSuchFieldException unused2) {
                return new LunoValue.BoundMethod(obj, new LunoValue.String(lexeme));
            }
        }
        if (lunoValueEvaluate instanceof LunoValue.NativeClass) {
            Class<?> klass2 = ((LunoValue.NativeClass) lunoValueEvaluate).getKlass();
            String lexeme2 = expr.getName().getLexeme();
            try {
                try {
                    Class<?> cls = Class.forName(klass2.getName() + "$" + lexeme2);
                    Intrinsics.checkNotNull(cls);
                    return new LunoValue.NativeClass(cls);
                } catch (ClassNotFoundException unused3) {
                    Field field = klass2.getField(lexeme2);
                    if (Modifier.isStatic(field.getModifiers())) {
                        return LunoValue.INSTANCE.fromKotlin(field.get(null));
                    }
                    Method[] methods2 = klass2.getMethods();
                    Intrinsics.checkNotNullExpressionValue(methods2, "getMethods(...)");
                    arrayList = new ArrayList();
                    for (Method method6 : methods2) {
                        method = method6;
                        if (!Modifier.isStatic(method.getModifiers()) && Intrinsics.areEqual(method.getName(), lexeme2)) {
                            arrayList.add(method6);
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        return new LunoValue.BoundMethod(klass2, new LunoValue.String(lexeme2));
                    }
                    if (allowUndefined) {
                        return LunoValue.Null.INSTANCE;
                    }
                    throw new LunoRuntimeError("Static property, method, or nested class '" + lexeme2 + "' not found on class '" + klass2.getSimpleName() + "'.", expr.getLine(), null, 4, null);
                }
            } catch (NoSuchFieldException unused4) {
                Method[] methods3 = klass2.getMethods();
                Intrinsics.checkNotNullExpressionValue(methods3, "getMethods(...)");
                arrayList = new ArrayList();
                while (i < r4) {
                    method = method6;
                    if (!Modifier.isStatic(method.getModifiers())) {
                    }
                }
                if (!arrayList.isEmpty()) {
                    return new LunoValue.BoundMethod(klass2, new LunoValue.String(lexeme2));
                }
                if (allowUndefined) {
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("Static property, method, or nested class '" + lexeme2 + "' not found on class '" + klass2.getSimpleName() + "'.", expr.getLine(), null, 4, null);
            }
        }
        throw new LunoRuntimeError("Can only access properties or methods on objects or classes. Got " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate.getClass()).getSimpleName() + ".", expr.getLine(), null, 4, null);
    }

    public final LunoValue callNativeMethod(Object instanceOrClass, String methodName, List<? extends LunoValue> lunoArgs, int line) {
        Object instanceOrClass2 = instanceOrClass;
        Intrinsics.checkNotNullParameter(instanceOrClass2, "instanceOrClass");
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        Intrinsics.checkNotNullParameter(lunoArgs, "lunoArgs");
        boolean z = instanceOrClass2 instanceof Class;
        Class<?> cls = z ? (Class) instanceOrClass2 : instanceOrClass.getClass();
        ArrayList<Method> arrayList = new ArrayList();
        Class<?> superclass = cls;
        while (true) {
            if (superclass == null) {
                break;
            }
            Method[] declaredMethods = superclass.getDeclaredMethods();
            Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
            ArrayList arrayList2 = new ArrayList();
            for (Method method : declaredMethods) {
                Method method2 = method;
                if (z == Modifier.isStatic(method2.getModifiers()) && Intrinsics.areEqual(method2.getName(), methodName) && method2.getParameterCount() == lunoArgs.size()) {
                    arrayList2.add(method);
                }
            }
            arrayList.addAll(arrayList2);
            superclass = superclass.getSuperclass();
        }
        if (arrayList.isEmpty()) {
            throw new LunoRuntimeError("Method '" + methodName + "(" + lunoArgs.size() + " args)' not found on '" + cls.getSimpleName() + "' or any of its superclasses.", line, null, 4, null);
        }
        for (Method method3 : arrayList) {
            try {
                List<? extends LunoValue> list = lunoArgs;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                int i = 0;
                for (Object obj : list) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    LunoValue lunoValue = (LunoValue) obj;
                    Class<?> cls2 = method3.getParameterTypes()[i];
                    Intrinsics.checkNotNull(cls2);
                    try {
                        arrayList3.add(lunoValueToKotlin(lunoValue, cls2));
                        i = i2;
                    } catch (LunoRuntimeError | Exception unused) {
                    }
                }
                Object[] array = arrayList3.toArray(new Object[0]);
                method3.setAccessible(true);
                return LunoValue.INSTANCE.fromKotlin(method3.invoke(z ? null : instanceOrClass2, Arrays.copyOf(array, array.length)));
            } catch (LunoRuntimeError | Exception unused2) {
            }
        }
        throw new LunoRuntimeError("Could not find a suitable overload for method '" + methodName + "' with argument types: (" + CollectionsKt.joinToString$default(lunoArgs, ", ", null, null, 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter$callNativeMethod$argTypes$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(LunoValue it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String simpleName = Reflection.getOrCreateKotlinClass(it.getClass()).getSimpleName();
                if (simpleName == null) {
                    simpleName = "unknown";
                }
                return simpleName;
            }
        }, 30, null) + Constant.AFTER_QUTO, line, null, 4, null);
    }

    static /* synthetic */ LunoValue evaluateIndexAccessExpr$default(Interpreter interpreter, IndexAccessExpr indexAccessExpr, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return interpreter.evaluateIndexAccessExpr(indexAccessExpr, z);
    }

    private final LunoValue evaluateIndexAccessExpr(IndexAccessExpr expr, boolean allowUndefined) {
        LunoValue lunoValueEvaluate = evaluate(expr.getCallee());
        LunoValue lunoValueEvaluate2 = evaluate(expr.getIndex());
        if (lunoValueEvaluate instanceof LunoValue.List) {
            if (lunoValueEvaluate2 instanceof LunoValue.Number) {
                int value = (int) ((LunoValue.Number) lunoValueEvaluate2).getValue();
                LunoValue lunoValue = (LunoValue) CollectionsKt.getOrNull(((LunoValue.List) lunoValueEvaluate).getElements(), value);
                if (lunoValue != null) {
                    return lunoValue;
                }
                if (allowUndefined) {
                    return LunoValue.Null.INSTANCE;
                }
                throw new LunoRuntimeError("Index " + value + " out of bounds.", expr.getLine(), null, 4, null);
            }
            throw new LunoRuntimeError("List index must be a number.", expr.getLine(), null, 4, null);
        }
        if (lunoValueEvaluate instanceof LunoValue.String) {
            if (lunoValueEvaluate2 instanceof LunoValue.Number) {
                Character orNull = StringsKt.getOrNull(((LunoValue.String) lunoValueEvaluate).getValue(), (int) ((LunoValue.Number) lunoValueEvaluate2).getValue());
                return orNull != null ? new LunoValue.String(String.valueOf(orNull.charValue())) : LunoValue.Null.INSTANCE;
            }
            throw new LunoRuntimeError("String index must be a number.", expr.getLine(), null, 4, null);
        }
        if (lunoValueEvaluate instanceof LunoValue.LunoObject) {
            String strLunoValueToString = lunoValueToString(lunoValueEvaluate2, false);
            LunoValue lunoValue2 = ((LunoValue.LunoObject) lunoValueEvaluate).getFields().get(strLunoValueToString);
            if (lunoValue2 != null) {
                return lunoValue2;
            }
            if (allowUndefined) {
                return LunoValue.Null.INSTANCE;
            }
            throw new LunoRuntimeError("Key '" + strLunoValueToString + "' not found in object.", expr.getLine(), null, 4, null);
        }
        if (lunoValueEvaluate instanceof LunoValue.NativeObject) {
            LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) lunoValueEvaluate;
            Object obj = nativeObject.getObj();
            if (obj instanceof List) {
                if (lunoValueEvaluate2 instanceof LunoValue.Number) {
                    return LunoValue.INSTANCE.fromKotlin(CollectionsKt.getOrNull((List) nativeObject.getObj(), (int) ((LunoValue.Number) lunoValueEvaluate2).getValue()));
                }
                throw new LunoRuntimeError("Native list index must be a number.", expr.getLine(), null, 4, null);
            }
            if (obj instanceof Map) {
                Object obj2 = nativeObject.getObj();
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.Any?, kotlin.Any?>");
                return LunoValue.INSTANCE.fromKotlin(((Map) obj2).get(toKotlinType(lunoValueEvaluate2)));
            }
            if (allowUndefined) {
                return LunoValue.Null.INSTANCE;
            }
            throw new LunoRuntimeError("Native object does not support indexed access.", expr.getLine(), null, 4, null);
        }
        throw new LunoRuntimeError("Can only perform index access on lists, strings, or map-like objects. Got " + Reflection.getOrCreateKotlinClass(lunoValueEvaluate.getClass()).getSimpleName() + ".", expr.getLine(), null, 4, null);
    }

    public final Object lunoValueToKotlin(LunoValue value, Class<?> targetClass) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(targetClass, "targetClass");
        boolean z = value instanceof LunoValue.Null;
        if (z) {
            return null;
        }
        if (Intrinsics.areEqual(targetClass, Object.class)) {
            if (z) {
                return null;
            }
            if (value instanceof LunoValue.Number) {
                LunoValue.Number number = (LunoValue.Number) value;
                if (number.getValue() != ((long) number.getValue())) {
                    return Double.valueOf(number.getValue());
                }
                if (number.getValue() >= -2.147483648E9d && number.getValue() <= 2.147483647E9d) {
                    return Integer.valueOf((int) number.getValue());
                }
                return Long.valueOf((long) number.getValue());
            }
            if (value instanceof LunoValue.Float) {
                return Float.valueOf(((LunoValue.Float) value).getValue());
            }
            if (value instanceof LunoValue.String) {
                return ((LunoValue.String) value).getValue();
            }
            if (value instanceof LunoValue.Boolean) {
                return Boolean.valueOf(((LunoValue.Boolean) value).getValue());
            }
            if (value instanceof LunoValue.NativeObject) {
                return ((LunoValue.NativeObject) value).getObj();
            }
            if (!(value instanceof LunoValue.List)) {
                if (!(value instanceof LunoValue.LunoObject)) {
                    return value;
                }
                Map<String, LunoValue> fields = ((LunoValue.LunoObject) value).getFields();
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(fields.size()));
                Iterator<T> it = fields.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    linkedHashMap.put(entry.getKey(), lunoValueToKotlin((LunoValue) entry.getValue(), Object.class));
                }
                return linkedHashMap;
            }
            List<LunoValue> elements = ((LunoValue.List) value).getElements();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
            Iterator<T> it2 = elements.iterator();
            while (it2.hasNext()) {
                arrayList.add(lunoValueToKotlin((LunoValue) it2.next(), Object.class));
            }
            return arrayList;
        }
        if (value instanceof LunoValue.NativeObject) {
            LunoValue.NativeObject nativeObject = (LunoValue.NativeObject) value;
            if (targetClass.isAssignableFrom(nativeObject.getObj().getClass())) {
                return nativeObject.getObj();
            }
        }
        if ((value instanceof LunoValue.String) && targetClass.isAssignableFrom(String.class)) {
            return ((LunoValue.String) value).getValue();
        }
        boolean z2 = value instanceof LunoValue.Number;
        if (z2 || (value instanceof LunoValue.Float)) {
            double value2 = z2 ? ((LunoValue.Number) value).getValue() : ((LunoValue.Float) value).getValue();
            if (Intrinsics.areEqual(targetClass, Integer.TYPE) || Intrinsics.areEqual(targetClass, Integer.TYPE)) {
                return Integer.valueOf((int) value2);
            }
            if (Intrinsics.areEqual(targetClass, Long.TYPE) || Intrinsics.areEqual(targetClass, Long.TYPE)) {
                return Long.valueOf((long) value2);
            }
            if (Intrinsics.areEqual(targetClass, Float.TYPE) || Intrinsics.areEqual(targetClass, Float.TYPE)) {
                return Float.valueOf((float) value2);
            }
            if (Intrinsics.areEqual(targetClass, Double.TYPE) || Intrinsics.areEqual(targetClass, Double.TYPE)) {
                return Double.valueOf(value2);
            }
        }
        if ((value instanceof LunoValue.Boolean) && (Intrinsics.areEqual(targetClass, Boolean.TYPE) || Intrinsics.areEqual(targetClass, Boolean.class) || Intrinsics.areEqual(targetClass, Boolean.TYPE) || Intrinsics.areEqual(targetClass, Object.class))) {
            return Boolean.valueOf(((LunoValue.Boolean) value).getValue());
        }
        if ((value instanceof LunoValue.List) && targetClass.isAssignableFrom(List.class)) {
            List<LunoValue> elements2 = ((LunoValue.List) value).getElements();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements2, 10));
            Iterator<T> it3 = elements2.iterator();
            while (it3.hasNext()) {
                arrayList2.add(lunoValueToKotlin((LunoValue) it3.next(), Object.class));
            }
            return CollectionsKt.toMutableList((Collection) arrayList2);
        }
        Log.e("LunoConvert", "Failed to convert " + Reflection.getOrCreateKotlinClass(value.getClass()).getSimpleName() + " (" + value + ") to " + targetClass.getName());
        throw new LunoRuntimeError("Cannot convert LunoValue type `" + Reflection.getOrCreateKotlinClass(value.getClass()).getSimpleName() + "` to native type `" + targetClass.getSimpleName() + "`", 0, null, 6, null);
    }

    private final LunoValue.Number numOp(LunoValue l, LunoValue r, int line, Function2<? super Double, ? super Double, Double> op) {
        LunoValue.Number number = l instanceof LunoValue.Number ? (LunoValue.Number) l : null;
        if (number == null) {
            throw new LunoRuntimeError("Left operand must be a number. Got " + Reflection.getOrCreateKotlinClass(l.getClass()).getSimpleName(), line, null, 4, null);
        }
        LunoValue.Number number2 = r instanceof LunoValue.Number ? (LunoValue.Number) r : null;
        if (number2 == null) {
            throw new LunoRuntimeError("Right operand must be a number. Got " + Reflection.getOrCreateKotlinClass(r.getClass()).getSimpleName(), line, null, 4, null);
        }
        return new LunoValue.Number(op.invoke(Double.valueOf(number.getValue()), Double.valueOf(number2.getValue())).doubleValue());
    }

    private final LunoValue.Boolean cmpOp(LunoValue l, LunoValue r, int line, Function2<? super Double, ? super Double, Boolean> op) {
        LunoValue.Number number = l instanceof LunoValue.Number ? (LunoValue.Number) l : null;
        if (number == null) {
            throw new LunoRuntimeError("Left operand for comparison must be a number. Got " + Reflection.getOrCreateKotlinClass(l.getClass()).getSimpleName(), line, null, 4, null);
        }
        LunoValue.Number number2 = r instanceof LunoValue.Number ? (LunoValue.Number) r : null;
        if (number2 == null) {
            throw new LunoRuntimeError("Right operand for comparison must be a number. Got " + Reflection.getOrCreateKotlinClass(r.getClass()).getSimpleName(), line, null, 4, null);
        }
        return new LunoValue.Boolean(op.invoke(Double.valueOf(number.getValue()), Double.valueOf(number2.getValue())).booleanValue());
    }

    private final boolean isEqual(LunoValue a, LunoValue b) {
        boolean z;
        boolean z2 = a instanceof LunoValue.Number;
        if (z2 && (b instanceof LunoValue.Number)) {
            return ((LunoValue.Number) a).getValue() == ((LunoValue.Number) b).getValue();
        }
        boolean z3 = a instanceof LunoValue.Float;
        if (z3 && (b instanceof LunoValue.Float)) {
            return ((LunoValue.Float) a).getValue() == ((LunoValue.Float) b).getValue();
        }
        if (z2 && (b instanceof LunoValue.Float)) {
            return ((LunoValue.Number) a).getValue() == ((double) ((LunoValue.Float) b).getValue());
        }
        if (z3 && (b instanceof LunoValue.Number)) {
            return ((double) ((LunoValue.Float) a).getValue()) == ((LunoValue.Number) b).getValue();
        }
        boolean z4 = a instanceof LunoValue.Null;
        if (z4 && (b instanceof LunoValue.Null)) {
            return true;
        }
        if (z4 || ((z = b instanceof LunoValue.Null))) {
            return false;
        }
        if (z4 && z) {
            return true;
        }
        if (!z4 && !z) {
            if (z2 && (b instanceof LunoValue.Number)) {
                if (((LunoValue.Number) a).getValue() == ((LunoValue.Number) b).getValue()) {
                    return true;
                }
            } else {
                if ((a instanceof LunoValue.String) && (b instanceof LunoValue.String)) {
                    return Intrinsics.areEqual(((LunoValue.String) a).getValue(), ((LunoValue.String) b).getValue());
                }
                if ((a instanceof LunoValue.Boolean) && (b instanceof LunoValue.Boolean)) {
                    if (((LunoValue.Boolean) a).getValue() == ((LunoValue.Boolean) b).getValue()) {
                        return true;
                    }
                } else {
                    if ((a instanceof LunoValue.List) && (b instanceof LunoValue.List)) {
                        return Intrinsics.areEqual(((LunoValue.List) a).getElements(), ((LunoValue.List) b).getElements());
                    }
                    if ((a instanceof LunoValue.LunoObject) && (b instanceof LunoValue.LunoObject)) {
                        return Intrinsics.areEqual(((LunoValue.LunoObject) a).getFields(), ((LunoValue.LunoObject) b).getFields());
                    }
                    if ((a instanceof LunoValue.NativeObject) && (b instanceof LunoValue.NativeObject)) {
                        return Intrinsics.areEqual(((LunoValue.NativeObject) a).getObj(), ((LunoValue.NativeObject) b).getObj());
                    }
                    if ((a instanceof LunoValue.Callable) && (b instanceof LunoValue.Callable) && a == b) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static /* synthetic */ String lunoValueToString$default(Interpreter interpreter, LunoValue lunoValue, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return interpreter.lunoValueToString(lunoValue, z);
    }

    public final String lunoValueToString(LunoValue value, final boolean humanReadable) {
        String name2;
        String string;
        if (value == null || (value instanceof LunoValue.Null)) {
            return "null";
        }
        if (value instanceof LunoValue.Number) {
            return ((LunoValue.Number) value).toString();
        }
        if (value instanceof LunoValue.String) {
            LunoValue.String string2 = (LunoValue.String) value;
            return humanReadable ? string2.getValue() : string2.toLunoScriptString();
        }
        if (value instanceof LunoValue.Boolean) {
            return String.valueOf(((LunoValue.Boolean) value).getValue());
        }
        if (value instanceof LunoValue.List) {
            return CollectionsKt.joinToString$default(((LunoValue.List) value).getElements(), ", ", "[", "]", 0, null, new Function1<LunoValue, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.lunoValueToString.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(LunoValue it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Interpreter.this.lunoValueToString(it, humanReadable);
                }
            }, 24, null);
        }
        if (value instanceof LunoValue.LunoObject) {
            LunoValue.LunoObject lunoObject = (LunoValue.LunoObject) value;
            LunoValue.LunoClass klass = lunoObject.getKlass();
            return (klass == null || (name2 = klass.getName()) == null || (string = new StringBuilder("<").append(name2).append(" instance>").toString()) == null) ? CollectionsKt.joinToString$default(lunoObject.getFields().entrySet(), ", ", "{", "}", 0, null, new Function1<Map.Entry<String, LunoValue>, CharSequence>() { // from class: org.catrobat.catroid.utils.lunoscript.Interpreter.lunoValueToString.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(Map.Entry<String, LunoValue> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return ((Object) it.getKey()) + ": " + Interpreter.this.lunoValueToString(it.getValue(), humanReadable);
                }
            }, 24, null) : string;
        }
        if (value instanceof LunoValue.Callable) {
            return ((LunoValue.Callable) value).toString();
        }
        return value instanceof LunoValue.NativeObject ? "<NativeObject: " + Reflection.getOrCreateKotlinClass(((LunoValue.NativeObject) value).getObj().getClass()).getSimpleName() + ">" : value.toString();
    }
}
