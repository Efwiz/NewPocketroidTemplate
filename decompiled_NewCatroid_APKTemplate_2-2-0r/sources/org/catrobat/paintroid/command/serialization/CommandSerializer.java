package org.catrobat.paintroid.command.serialization;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.colorpicker.ColorHistory;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.implementation.AddEmptyLayerCommand;
import org.catrobat.paintroid.command.implementation.ClipboardCommand;
import org.catrobat.paintroid.command.implementation.ClippingCommand;
import org.catrobat.paintroid.command.implementation.CompositeCommand;
import org.catrobat.paintroid.command.implementation.CropCommand;
import org.catrobat.paintroid.command.implementation.CutCommand;
import org.catrobat.paintroid.command.implementation.FillCommand;
import org.catrobat.paintroid.command.implementation.FlipCommand;
import org.catrobat.paintroid.command.implementation.GeometricFillCommand;
import org.catrobat.paintroid.command.implementation.LayerOpacityCommand;
import org.catrobat.paintroid.command.implementation.LoadCommand;
import org.catrobat.paintroid.command.implementation.LoadLayerListCommand;
import org.catrobat.paintroid.command.implementation.MergeLayersCommand;
import org.catrobat.paintroid.command.implementation.PathCommand;
import org.catrobat.paintroid.command.implementation.PointCommand;
import org.catrobat.paintroid.command.implementation.RemoveLayerCommand;
import org.catrobat.paintroid.command.implementation.ReorderLayersCommand;
import org.catrobat.paintroid.command.implementation.ResetCommand;
import org.catrobat.paintroid.command.implementation.ResizeCommand;
import org.catrobat.paintroid.command.implementation.RotateCommand;
import org.catrobat.paintroid.command.implementation.SelectLayerCommand;
import org.catrobat.paintroid.command.implementation.SetDimensionCommand;
import org.catrobat.paintroid.command.implementation.SmudgePathCommand;
import org.catrobat.paintroid.command.implementation.SprayCommand;
import org.catrobat.paintroid.command.implementation.TextToolCommand;
import org.catrobat.paintroid.common.Constants;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.iotasks.OpenRasterFileFormatConversion;
import org.catrobat.paintroid.iotasks.WorkspaceReturnValue;
import org.catrobat.paintroid.model.CommandManagerModel;
import org.catrobat.paintroid.tools.drawable.HeartDrawable;
import org.catrobat.paintroid.tools.drawable.OvalDrawable;
import org.catrobat.paintroid.tools.drawable.RectangleDrawable;
import org.catrobat.paintroid.tools.drawable.ShapeDrawable;
import org.catrobat.paintroid.tools.drawable.StarDrawable;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX INFO: compiled from: CommandSerializer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 )2\u00020\u0001:\u0002)*B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u0013J\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\b\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010%\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020&J\u0010\u0010'\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020(H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\r\u001a2\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00100\u000ej\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u0010`\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "", "activityContext", "Landroid/content/Context;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "model", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;", "(Landroid/content/Context;Lorg/catrobat/paintroid/command/CommandManager;Lorg/catrobat/paintroid/contract/MainActivityContracts$Model;)V", "kryo", "Lcom/esotericsoftware/kryo/Kryo;", "getKryo", "()Lcom/esotericsoftware/kryo/Kryo;", "registerMap", "Ljava/util/LinkedHashMap;", "Ljava/lang/Class;", "Lorg/catrobat/paintroid/command/serialization/VersionSerializer;", "Lkotlin/collections/LinkedHashMap;", "overWriteFile", "Landroid/net/Uri;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "uri", "resolver", "Landroid/content/ContentResolver;", "readFromFile", "Lorg/catrobat/paintroid/command/serialization/CatrobatFileContent;", "readFromInternalMemory", "Lorg/catrobat/paintroid/iotasks/WorkspaceReturnValue;", "stream", "Ljava/io/FileInputStream;", "registerClasses", "", "setRegisterMapVersion", "version", "", "writeToFile", "writeToInternalMemory", "Ljava/io/FileOutputStream;", "writeToStream", "Ljava/io/OutputStream;", "Companion", "NotCatrobatImageException", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class CommandSerializer {
    public static final int CURRENT_IMAGE_VERSION = 2;
    public static final String MAGIC_VALUE = "CATROBAT";
    private final Context activityContext;
    private final CommandManager commandManager;
    private final Kryo kryo;
    private final MainActivityContracts.Model model;
    private final LinkedHashMap<Class<?>, VersionSerializer<?>> registerMap;

    public CommandSerializer(Context activityContext, CommandManager commandManager, MainActivityContracts.Model model) {
        Intrinsics.checkNotNullParameter(activityContext, "activityContext");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        Intrinsics.checkNotNullParameter(model, "model");
        this.activityContext = activityContext;
        this.commandManager = commandManager;
        this.model = model;
        this.kryo = new Kryo();
        this.registerMap = new LinkedHashMap<>();
        setRegisterMapVersion(2);
        registerClasses();
    }

    public final Kryo getKryo() {
        return this.kryo;
    }

    private final void setRegisterMapVersion(int version) {
        LinkedHashMap<Class<?>, VersionSerializer<?>> linkedHashMap = this.registerMap;
        linkedHashMap.put(Command.class, null);
        linkedHashMap.put(CompositeCommand.class, new CompositeCommandSerializer(version));
        linkedHashMap.put(float[].class, new DataStructuresSerializer.FloatArraySerializer(version));
        linkedHashMap.put(PointF.class, new DataStructuresSerializer.PointFSerializer(version));
        linkedHashMap.put(Point.class, new DataStructuresSerializer.PointSerializer(version));
        linkedHashMap.put(CommandManagerModel.class, new CommandManagerModelSerializer(version));
        linkedHashMap.put(SetDimensionCommand.class, new SetDimensionCommandSerializer(version));
        linkedHashMap.put(SprayCommand.class, new SprayCommandSerializer(version));
        linkedHashMap.put(Paint.class, new PaintSerializer(version, this.activityContext));
        linkedHashMap.put(AddEmptyLayerCommand.class, new AddLayerCommandSerializer(version));
        linkedHashMap.put(SelectLayerCommand.class, new SelectLayerCommandSerializer(version));
        linkedHashMap.put(LoadCommand.class, new LoadCommandSerializer(version));
        linkedHashMap.put(TextToolCommand.class, new TextToolCommandSerializer(version, this.activityContext));
        linkedHashMap.put(String[].class, new DataStructuresSerializer.StringArraySerializer(version));
        linkedHashMap.put(FillCommand.class, new FillCommandSerializer(version));
        linkedHashMap.put(FlipCommand.class, new FlipCommandSerializer(version));
        linkedHashMap.put(CropCommand.class, new CropCommandSerializer(version));
        linkedHashMap.put(CutCommand.class, new CutCommandSerializer(version));
        linkedHashMap.put(ResizeCommand.class, new ResizeCommandSerializer(version));
        linkedHashMap.put(RotateCommand.class, new RotateCommandSerializer(version));
        linkedHashMap.put(ResetCommand.class, new ResetCommandSerializer(version));
        linkedHashMap.put(ReorderLayersCommand.class, new ReorderLayersCommandSerializer(version));
        linkedHashMap.put(RemoveLayerCommand.class, new RemoveLayerCommandSerializer(version));
        linkedHashMap.put(MergeLayersCommand.class, new MergeLayersCommandSerializer(version));
        linkedHashMap.put(PathCommand.class, new PathCommandSerializer(version));
        linkedHashMap.put(SerializablePath.class, new SerializablePath.PathSerializer(version));
        linkedHashMap.put(SerializablePath.Move.class, new SerializablePath.PathActionMoveSerializer(version));
        linkedHashMap.put(SerializablePath.Line.class, new SerializablePath.PathActionLineSerializer(version));
        linkedHashMap.put(SerializablePath.Quad.class, new SerializablePath.PathActionQuadSerializer(version));
        linkedHashMap.put(SerializablePath.Rewind.class, new SerializablePath.PathActionRewindSerializer(version));
        linkedHashMap.put(LoadLayerListCommand.class, new LoadLayerListCommandSerializer(version));
        linkedHashMap.put(GeometricFillCommand.class, new GeometricFillCommandSerializer(version));
        linkedHashMap.put(HeartDrawable.class, new GeometricFillCommandSerializer.HeartDrawableSerializer(version));
        linkedHashMap.put(OvalDrawable.class, new GeometricFillCommandSerializer.OvalDrawableSerializer(version));
        linkedHashMap.put(RectangleDrawable.class, new GeometricFillCommandSerializer.RectangleDrawableSerializer(version));
        linkedHashMap.put(StarDrawable.class, new GeometricFillCommandSerializer.StarDrawableSerializer(version));
        linkedHashMap.put(ShapeDrawable.class, null);
        linkedHashMap.put(RectF.class, new DataStructuresSerializer.RectFSerializer(version));
        linkedHashMap.put(ClipboardCommand.class, new ClipboardCommandSerializer(version));
        linkedHashMap.put(SerializableTypeface.class, new SerializableTypeface.TypefaceSerializer(version));
        linkedHashMap.put(PointCommand.class, new PointCommandSerializer(version));
        linkedHashMap.put(SerializablePath.Cube.class, new SerializablePath.PathActionCubeSerializer(version));
        linkedHashMap.put(Bitmap.class, new BitmapSerializer(version));
        linkedHashMap.put(SmudgePathCommand.class, new SmudgePathCommandSerializer(version));
        linkedHashMap.put(ColorHistory.class, new ColorHistorySerializer(version));
        linkedHashMap.put(ClippingCommand.class, new ClippingCommandSerializer(version));
        linkedHashMap.put(LayerOpacityCommand.class, new LayerOpacityCommandSerializer(version));
    }

    private final void registerClasses() {
        for (Map.Entry<Class<?>, VersionSerializer<?>> entry : this.registerMap.entrySet()) {
            Class<?> key = entry.getKey();
            VersionSerializer<?> value = entry.getValue();
            Registration registrationRegister = getKryo().register(key);
            if (value != null) {
                registrationRegister.setSerializer(value);
            }
        }
    }

    public final Uri writeToFile(String fileName) {
        Context baseContext;
        SharedPreferences.Editor editorEdit;
        SharedPreferences.Editor editorPutLong;
        OutputStream outputStreamOpenOutputStream;
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        ContentResolver contentResolver = this.activityContext.getContentResolver();
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", fileName);
            contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
            Uri uriInsert = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
            if (uriInsert == null || (outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert)) == null) {
                return null;
            }
            OutputStream outputStream = outputStreamOpenOutputStream;
            try {
                writeToStream(outputStream);
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(outputStream, null);
                return uriInsert;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(outputStream, th);
                    throw th2;
                }
            }
        }
        if (!Constants.DOWNLOADS_DIRECTORY.exists() && !Constants.DOWNLOADS_DIRECTORY.mkdirs()) {
            return null;
        }
        File file = new File(Constants.DOWNLOADS_DIRECTORY, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            writeToStream(fileOutputStream);
            Uri uriFromFile = Uri.fromFile(file);
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
            MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            Object systemService = (mainActivity == null || (baseContext = mainActivity.getBaseContext()) == null) ? null : baseContext.getSystemService("download");
            if (systemService == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.app.DownloadManager");
            }
            long jAddCompletedDownload = ((DownloadManager) systemService).addCompletedDownload(fileName, fileName, true, "application/zip", file.getAbsolutePath(), file.length(), true);
            MainActivity mainActivity2 = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            SharedPreferences sharedPreferences = mainActivity2 != null ? mainActivity2.getSharedPreferences(ConstantsKt.SPECIFIC_FILETYPE_SHARED_PREFERENCES_NAME, 0) : null;
            if (sharedPreferences != null && (editorEdit = sharedPreferences.edit()) != null && (editorPutLong = editorEdit.putLong(file.getAbsolutePath(), jAddCompletedDownload)) != null) {
                editorPutLong.apply();
            }
            return uriFromFile;
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(fileOutputStream, th3);
                throw th4;
            }
        }
    }

    public final Uri overWriteFile(String fileName, Uri uri, ContentResolver resolver) {
        Context baseContext;
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Object systemService = null;
        if (Build.VERSION.SDK_INT >= 29) {
            Cursor cursorQuery = resolver.query(uri, new String[]{"_id"}, null, null, null);
            if (cursorQuery != null) {
                if (cursorQuery.moveToFirst()) {
                    Uri uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_id")));
                    Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(MediaStor…EXTERNAL_CONTENT_URI, id)");
                    resolver.delete(uriWithAppendedId, null, null);
                    cursorQuery.close();
                } else {
                    throw new AssertionError("No file to delete was found!");
                }
            }
        } else {
            boolean zDelete = new File(String.valueOf(uri.getPath())).delete();
            MainActivity mainActivity = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
            SharedPreferences sharedPreferences = mainActivity == null ? null : mainActivity.getSharedPreferences(ConstantsKt.SPECIFIC_FILETYPE_SHARED_PREFERENCES_NAME, 0);
            Long lValueOf = sharedPreferences == null ? null : Long.valueOf(sharedPreferences.getLong(uri.getPath(), -1L));
            if (lValueOf != null && lValueOf.longValue() > -1) {
                MainActivity mainActivity2 = OpenRasterFileFormatConversion.INSTANCE.getMainActivity();
                if (mainActivity2 != null && (baseContext = mainActivity2.getBaseContext()) != null) {
                    systemService = baseContext.getSystemService("download");
                }
                if (systemService == null) {
                    throw new NullPointerException("null cannot be cast to non-null type android.app.DownloadManager");
                }
                ((DownloadManager) systemService).remove(lValueOf.longValue());
            }
            if (!zDelete) {
                throw new AssertionError("No file to delete was found!");
            }
        }
        return writeToFile(fileName);
    }

    public final void writeToInternalMemory(FileOutputStream stream) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        FileOutputStream fileOutputStream = stream;
        try {
            writeToStream(fileOutputStream);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileOutputStream, th);
                throw th2;
            }
        }
    }

    public final WorkspaceReturnValue readFromInternalMemory(FileInputStream stream) {
        List<Command> commands;
        Intrinsics.checkNotNullParameter(stream, "stream");
        Input input = new Input(stream);
        try {
            Input input2 = input;
            if (!input2.readString().equals(MAGIC_VALUE)) {
                throw new NotCatrobatImageException("Magic Value doesn't exist.");
            }
            int i = input2.readInt();
            if (2 != i) {
                setRegisterMapVersion(i);
                registerClasses();
            }
            Object object = getKryo().readObject(input2, CommandManagerModel.class);
            Object object2 = input2.available() != 0 ? getKryo().readObject(input2, ColorHistory.class) : null;
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(input, null);
            CommandManagerModel commandManagerModel = (CommandManagerModel) object;
            if (commandManagerModel != null && (commands = commandManagerModel.getCommands()) != null) {
                CollectionsKt.reverse(commands);
            }
            return new WorkspaceReturnValue(commandManagerModel, (ColorHistory) object2);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(input, th);
                throw th2;
            }
        }
    }

    private final void writeToStream(OutputStream stream) {
        Output output = new Output(stream);
        try {
            Output output2 = output;
            output2.writeString(MAGIC_VALUE);
            output2.writeInt(2);
            getKryo().writeObject(output2, this.commandManager.getCommandManagerModelForCatrobatImage());
            if (!this.model.getColorHistory().getColors().isEmpty()) {
                getKryo().writeObject(output2, this.model.getColorHistory());
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(output, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(output, th);
                throw th2;
            }
        }
    }

    public final CatrobatFileContent readFromFile(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        InputStream inputStreamOpenInputStream = this.activityContext.getContentResolver().openInputStream(uri);
        try {
            Input input = new Input(inputStreamOpenInputStream);
            try {
                Input input2 = input;
                if (!input2.readString().equals(MAGIC_VALUE)) {
                    throw new NotCatrobatImageException("Magic Value doesn't exist.");
                }
                int i = input2.readInt();
                if (2 != i) {
                    setRegisterMapVersion(i);
                    registerClasses();
                }
                Object object = getKryo().readObject(input2, CommandManagerModel.class);
                Intrinsics.checkNotNullExpressionValue(object, "kryo.readObject(input, C…ManagerModel::class.java)");
                Object object2 = input2.canReadInt() ? getKryo().readObject(input2, ColorHistory.class) : null;
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(input, null);
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(inputStreamOpenInputStream, null);
                CommandManagerModel commandManagerModel = (CommandManagerModel) object;
                CollectionsKt.reverse(commandManagerModel.getCommands());
                return new CatrobatFileContent(commandManagerModel, (ColorHistory) object2);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(input, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(inputStreamOpenInputStream, th3);
                throw th4;
            }
        }
    }

    /* JADX INFO: compiled from: CommandSerializer.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/command/serialization/CommandSerializer$NotCatrobatImageException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class NotCatrobatImageException extends Exception {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotCatrobatImageException(String message) {
            super(message);
            Intrinsics.checkNotNullParameter(message, "message");
        }
    }
}
