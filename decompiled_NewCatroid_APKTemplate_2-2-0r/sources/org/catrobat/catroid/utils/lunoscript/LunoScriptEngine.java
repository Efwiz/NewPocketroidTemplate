package org.catrobat.catroid.utils.lunoscript;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.Charsets;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoScriptEngine.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoScriptEngine.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0006\u0010\r\u001a\u00020\bJ\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u0003JX\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u00152@\u0010\u0016\u001a<\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0018\u0012\b\b\u0013\u0012\u0004\b\b(\u0007\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\u001a0\u0019¢\u0006\f\b\u0018\u0012\b\b\u0013\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001a0\u0017j\u0002`\u001cJ\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\fH\u0002R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoScriptEngine;", "", "androidContext", "Landroid/content/Context;", "scope", "Lorg/catrobat/catroid/content/Scope;", "(Landroid/content/Context;Lorg/catrobat/catroid/content/Scope;)V", "interpreter", "Lorg/catrobat/catroid/utils/lunoscript/Interpreter;", "execute", "", "script", "", "getInterpreter", "handleLunoError", "throwable", "", "toastMessagePrefix", "registerNativeFunction", "name", "arity", "Lkotlin/ranges/IntRange;", "func", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "", "Lorg/catrobat/catroid/utils/lunoscript/LunoValue;", "arguments", "Lorg/catrobat/catroid/utils/lunoscript/RawNativeLunoFunction;", "toast", NotificationCompat.CATEGORY_MESSAGE, "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LunoScriptEngine {
    private final Context androidContext;
    private final Interpreter interpreter;
    private final org.catrobat.catroid.content.Scope scope;

    public LunoScriptEngine(Context context, org.catrobat.catroid.content.Scope scope) {
        this.androidContext = context;
        this.scope = scope;
        this.interpreter = new Interpreter(context, scope);
    }

    public /* synthetic */ LunoScriptEngine(Context context, org.catrobat.catroid.content.Scope scope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : scope);
    }

    public final void registerNativeFunction(String name2, IntRange arity, Function2<? super Interpreter, ? super List<? extends LunoValue>, ? extends LunoValue> func) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(arity, "arity");
        Intrinsics.checkNotNullParameter(func, "func");
        this.interpreter.getGlobals().define(name2, new LunoValue.NativeCallable(new CallableNativeLunoFunction(name2, arity, func)));
    }

    public final void execute(String script) {
        Intrinsics.checkNotNullParameter(script, "script");
        try {
            this.interpreter.interpret(new Parser(new Lexer(script).scanTokens()).parse());
        } catch (LunoRuntimeError e) {
            System.err.println("LunoScript Runtime Error (Engine): " + e.getMessage() + " (Line: " + e.getLine() + Constant.AFTER_QUTO);
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace(System.err);
            }
            handleLunoError(e, "LunoRuntimeError: " + e.getMessage());
        } catch (LunoSyntaxError e2) {
            System.err.println("LunoScript Syntax Error (Engine): " + e2.getMessage() + " (Line: " + e2.getLine() + ", Pos: " + e2.getPosition() + Constant.AFTER_QUTO);
            handleLunoError(e2, "LunoSyntaxError: " + e2.getMessage());
        } catch (Throwable th) {
            System.err.println("Unexpected LunoScript Engine Error (Throwable): " + th.getClass().getSimpleName() + " - " + th.getLocalizedMessage());
            th.printStackTrace(System.err);
            handleLunoError(th, "LunoEngineError: " + th.getClass().getSimpleName() + " - " + th.getLocalizedMessage());
        }
    }

    private final void toast(String msg) {
        try {
            StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(msg))).sendToTarget();
        } catch (Exception unused) {
            Toast.makeText(this.androidContext, msg, 0).show();
        }
    }

    /* JADX WARN: Code duplicated, block: B:37:0x01c8  */
    private final void handleLunoError(Throwable throwable, String toastMessagePrefix) {
        Uri uriInsert;
        Log.d("LunoEngine", "handleLunoError CALLED for: " + toastMessagePrefix + ", Exception: " + throwable.getClass().getSimpleName());
        Context appContext = CatroidApplication.getAppContext();
        if (appContext == null) {
            Log.d("LunoScriptEngine", "LunoScript Error: CatroidApplication.getAppContext() returned null. Cannot save log or show toast.");
            return;
        }
        String str = ((Object) (new SimpleDateFormat(Constants.DATE_FORMAT_DEFAULT, Locale.getDefault()).format(new Date()) + " - " + toastMessagePrefix + "\n")) + "Details: " + throwable.getMessage() + "\n";
        if (throwable instanceof LunoSyntaxError) {
            LunoSyntaxError lunoSyntaxError = (LunoSyntaxError) throwable;
            str = ((Object) str) + "Location: Line " + lunoSyntaxError.getLine() + ", Position " + lunoSyntaxError.getPosition() + "\n";
        } else if (throwable instanceof LunoRuntimeError) {
            str = ((Object) str) + "Location: Line " + ((LunoRuntimeError) throwable).getLine() + "\n";
        }
        String str2 = ((Object) str) + "Stack Trace:\n" + ExceptionsKt.stackTraceToString(throwable) + "\n";
        Throwable cause = throwable.getCause();
        if (cause != null) {
            str2 = ((Object) str2) + "\nCaused by: " + cause.getClass().getSimpleName() + " - " + cause.getLocalizedMessage() + "\n" + ExceptionsKt.stackTraceToString(cause) + "\n";
        }
        String str3 = ((Object) str2) + "-------------------------------------------------\n";
        try {
            ContentResolver contentResolver = appContext.getContentResolver();
            if (Build.VERSION.SDK_INT >= 29) {
                Uri EXTERNAL_CONTENT_URI = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
                Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
                Cursor cursorQuery = contentResolver.query(EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_display_name = ? AND relative_path = ?", new String[]{"LunoLog.txt", Environment.DIRECTORY_DOWNLOADS + "/"}, null);
                if (cursorQuery != null) {
                    Cursor cursor = cursorQuery;
                    try {
                        Cursor cursor2 = cursor;
                        uriInsert = cursor2.moveToFirst() ? ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, cursor2.getLong(cursor2.getColumnIndexOrThrow("_id"))) : null;
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(cursor, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(cursor, th);
                            throw th2;
                        }
                    }
                } else {
                    uriInsert = null;
                }
            } else {
                uriInsert = null;
            }
            if (uriInsert == null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_display_name", "LunoLog.txt");
                contentValues.put("mime_type", "text/plain");
                if (Build.VERSION.SDK_INT >= 29) {
                    contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
                }
                uriInsert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            }
            if (uriInsert == null) {
                throw new Exception("Failed to find or create MediaStore record.");
            }
            OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
            if (outputStreamOpenOutputStream == null) {
                throw new Exception("Failed to open output stream for URI: " + uriInsert);
            }
            OutputStream outputStream = outputStreamOpenOutputStream;
            try {
                byte[] bytes = str3.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                outputStream.write(bytes);
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(outputStream, null);
                final String str4 = toastMessagePrefix + ". Log saved to Downloads/LunoLog.txt";
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.lunoscript.LunoScriptEngine$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LunoScriptEngine.handleLunoError$lambda$4(this.f$0, str4);
                    }
                });
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(outputStream, th3);
                    throw th4;
                }
            }
        } catch (Exception e) {
            System.err.println("LunoScript Error: Failed to write to log file: " + e.getMessage());
            e.printStackTrace(System.err);
            final String str5 = toastMessagePrefix + " (Failed to save log)";
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.lunoscript.LunoScriptEngine$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    LunoScriptEngine.handleLunoError$lambda$5(this.f$0, str5);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleLunoError$lambda$4(LunoScriptEngine this$0, String finalToastMsg) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(finalToastMsg, "$finalToastMsg");
        this$0.toast(finalToastMsg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleLunoError$lambda$5(LunoScriptEngine this$0, String finalToastMsg) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(finalToastMsg, "$finalToastMsg");
        this$0.toast(finalToastMsg);
    }

    public final Interpreter getInterpreter() {
        return this.interpreter;
    }
}
