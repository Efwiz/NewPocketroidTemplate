package org.catrobat.catroid.content.actions;

import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSoundManager;
import org.catrobat.catroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ErrorLog;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SoundFilesAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SoundFilesAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0010J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00122\b\u0010\u0019\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/content/actions/SoundFilesAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "name", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "createTemporaryFile", "Ljava/io/File;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "generateRandomString", "length", "", "getInputStreamFromFile", "Ljava/io/InputStream;", "file", "inputName", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SoundFilesAction extends TemporalAction {
    private Formula name;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getName() {
        return this.name;
    }

    public final void setName(Formula formula) {
        this.name = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws IOException, InterpretationException {
        Project project;
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity != null) {
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.SoundFilesAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SoundFilesAction.update$lambda$0(stageActivity);
                }
            });
        }
        Scope scope = this.scope;
        if (scope == null || (project = scope.getProject()) == null) {
            return;
        }
        Sprite sprite = scope.getSprite();
        String name2 = getName(this.name);
        if (name2 == null) {
            name2 = "my_sound.mp3";
        }
        File file = new File(project.getFilesDir(), name2);
        if (!file.exists()) {
            Log.e("SoundFile", "File " + file.getAbsolutePath() + " has not exists");
        }
        File file2 = new File(CatroidApplication.getAppContext().getFilesDir(), name2);
        StorageOperations.copyFile(file, file2);
        if (file.exists()) {
            String name3 = file2.getName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            boolean z = true;
            if (!StringsKt.endsWith(name3, ".mid", true)) {
                String name4 = file2.getName();
                Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                if (!StringsKt.endsWith(name4, ProjectToMidiConverter.MIDI_FILE_EXTENSION, true)) {
                    z = false;
                }
            }
            SoundInfo soundInfo = new SoundInfo("playingFileSound_" + generateRandomString(7), file2, z);
            if (soundInfo.isMidiFile()) {
                MidiSoundManager.getInstance().playSoundFile(soundInfo.getFile().getAbsolutePath(), sprite);
            } else {
                SoundManager.getInstance().playSoundFile(soundInfo.getFile().getAbsolutePath(), sprite);
            }
        } else {
            Log.e("SoundFile", "File has not exists");
        }
        file2.delete();
        file2.delete();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(StageActivity stageActivity) {
        if (ContextCompat.checkSelfPermission(CatroidApplication.getAppContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(stageActivity, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 2);
        }
    }

    public final String generateRandomString(int length) {
        List listPlus = CollectionsKt.plus((Iterable) new CharRange('a', 'z'), (Iterable) new CharRange('A', 'Z'));
        IntRange intRange = new IntRange(1, length);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it = intRange.iterator();
        while (it.hasNext()) {
            ((IntIterator) it).nextInt();
            arrayList.add(Character.valueOf(((Character) CollectionsKt.random(listPlus, Random.INSTANCE)).charValue()));
        }
        return CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null);
    }

    public final InputStream getInputStreamFromFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            ErrorLog errorLog = ErrorLog.INSTANCE;
            String message = e.getMessage();
            if (message == null) {
                message = "**message not provided :(**";
            }
            errorLog.log(message);
            e.printStackTrace();
            return null;
        }
    }

    public final File createTemporaryFile(String fileName) throws IOException {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        File fileCreateTempFile = File.createTempFile("temp_", "." + StringsKt.substringAfterLast(fileName, '.', ""));
        Intrinsics.checkNotNull(fileCreateTempFile);
        return fileCreateTempFile;
    }

    public final String getName(Formula inputName) throws InterpretationException {
        if (inputName == null) {
            return null;
        }
        String strInterpretString = inputName.interpretString(this.scope);
        Intrinsics.checkNotNull(strInterpretString);
        int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) strInterpretString, '.', 0, false, 6, (Object) null);
        return (iLastIndexOf$default > 0 || iLastIndexOf$default < strInterpretString.length() + (-1)) ? strInterpretString : strInterpretString + Constants.DEFAULT_IMAGE_EXTENSION;
    }
}
