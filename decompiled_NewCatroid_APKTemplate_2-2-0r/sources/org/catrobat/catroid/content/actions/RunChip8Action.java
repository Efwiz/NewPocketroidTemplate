package org.catrobat.catroid.content.actions;

import androidx.core.app.NotificationCompat;
import com.android.dx.rop.code.RegisterSpec;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.io.FilesKt;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.cpio.CpioConstants;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.FileMode;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunChip8Action.class
  classes14.dex
 */
/* JADX INFO: compiled from: RunChip8Action.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020%H\u0016J\b\u0010)\u001a\u00020*H\u0002J\u0018\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0002J\u0018\u00100\u001a\u00020%2\u0006\u0010,\u001a\u00020-2\u0006\u00101\u001a\u00020%H\u0002J\b\u00102\u001a\u00020*H\u0002J\b\u00103\u001a\u00020*H\u0016J\u0010\u00104\u001a\u00020*2\u0006\u00105\u001a\u00020-H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunChip8Action;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "delayTimer", "", "drawFlag", "", "filenameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getFilenameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setFilenameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "fontset", "", "gfx", "", "iReg", "initialized", "memory", "", "pc", "pixmap", "Lcom/badlogic/gdx/graphics/Pixmap;", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "screenImage", "Lcom/badlogic/gdx/scenes/scene2d/ui/Image;", "sp", "stack", "texture", "Lcom/badlogic/gdx/graphics/Texture;", "timeAccumulator", "", RegisterSpec.PREFIX, "act", "delta", "emulateCycle", "", "ensureVariable", "name", "", "initialValue", "", "getVarValue", "defaultValue", "initEmulator", "reset", "setConsole", NotificationCompat.CATEGORY_MESSAGE, "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RunChip8Action extends Action {
    private int delayTimer;
    private boolean drawFlag;
    private Formula filenameFormula;
    private int iReg;
    private boolean initialized;
    private Pixmap pixmap;
    private Scope scope;
    private Image screenImage;
    private int sp;
    private Texture texture;
    private float timeAccumulator;
    private final byte[] memory = new byte[4096];
    private final int[] v = new int[16];
    private int pc = 512;
    private final int[] stack = new int[16];
    private final boolean[] gfx = new boolean[2048];
    private final int[] fontset = {240, 144, 144, 144, 240, 32, 96, 32, 32, 112, 240, 16, 240, 128, 240, 240, 16, 240, 16, 240, 144, 144, 240, 16, 16, 240, 128, 240, 16, 240, 240, 128, 240, 144, 240, 240, 16, 32, 64, 64, 240, 144, 240, 144, 240, 240, 144, 240, 16, 240, 240, 144, 240, 144, 144, 224, 144, 224, 144, 224, 240, 128, 128, 128, 240, 224, 144, 144, 144, 224, 240, 128, 240, 128, 240, 240, 128, 240, 128, 128};

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getFilenameFormula() {
        return this.filenameFormula;
    }

    public final void setFilenameFormula(Formula formula) {
        this.filenameFormula = formula;
    }

    private final float getVarValue(String name2, float defaultValue) {
        Object value;
        String string;
        Float floatOrNull;
        Project project;
        Scope scope = this.scope;
        UserVariable userVariable = (scope == null || (project = scope.getProject()) == null) ? null : project.getUserVariable(name2);
        return (userVariable == null || (value = userVariable.getValue()) == null || (string = value.toString()) == null || (floatOrNull = StringsKt.toFloatOrNull(string)) == null) ? defaultValue : floatOrNull.floatValue();
    }

    private final void ensureVariable(String name2, Object initialValue) {
        Scope scope;
        Project project;
        List<UserVariable> userVariables;
        Project project2;
        Scope scope2 = this.scope;
        if (((scope2 == null || (project2 = scope2.getProject()) == null) ? null : project2.getUserVariable(name2)) != null || (scope = this.scope) == null || (project = scope.getProject()) == null || (userVariables = project.getUserVariables()) == null) {
            return;
        }
        userVariables.add(new UserVariable(name2, initialValue));
    }

    private final void initEmulator() {
        Stage stage;
        Project project;
        Formula formula = this.filenameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            return;
        }
        Scope scope = this.scope;
        File file = (scope == null || (project = scope.getProject()) == null) ? null : project.getFile(strInterpretString);
        if (file == null || !file.exists()) {
            setConsole("Ошибка: Файл " + strInterpretString + " не найден!");
            return;
        }
        ensureVariable("chip8_key", -1);
        ensureVariable("chip8_console", "Запуск...");
        ensureVariable("chip8_speed", 10);
        ensureVariable("chip8_x", -320);
        ensureVariable("chip8_y", -160);
        ensureVariable("chip8_width", 640);
        ensureVariable("chip8_height", 320);
        int length = this.fontset.length;
        for (int i = 0; i < length; i++) {
            this.memory[i + 80] = (byte) this.fontset[i];
        }
        byte[] bytes = FilesKt.readBytes(file);
        System.arraycopy(bytes, 0, this.memory, 512, bytes.length);
        Pixmap pixmap = new Pixmap(64, 32, Pixmap.Format.RGBA8888);
        this.pixmap = pixmap;
        pixmap.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        Pixmap pixmap2 = this.pixmap;
        if (pixmap2 != null) {
            pixmap2.fill();
        }
        this.texture = new Texture(this.pixmap);
        this.screenImage = new Image(this.texture);
        StageActivity stageActivity = StageActivity.activeStageActivity.get();
        StageListener stageListener = stageActivity != null ? stageActivity.stageListener : null;
        if (stageListener != null && (stage = stageListener.getStage()) != null) {
            stage.addActor(this.screenImage);
        }
        setConsole("Игра загружена. Инициализация завершена!");
        this.initialized = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.initialized) {
            initEmulator();
        }
        if (!this.initialized) {
            return true;
        }
        int iCoerceIn = RangesKt.coerceIn((int) getVarValue("chip8_speed", 10.0f), 1, 100);
        float varValue = getVarValue("chip8_x", -320.0f);
        float varValue2 = getVarValue("chip8_y", -160.0f);
        float varValue3 = getVarValue("chip8_width", 640.0f);
        float varValue4 = getVarValue("chip8_height", 320.0f);
        Image image = this.screenImage;
        if (image != null) {
            image.setPosition(varValue, varValue2);
        }
        Image image2 = this.screenImage;
        if (image2 != null) {
            image2.setSize(varValue3, varValue4);
        }
        for (int i = 0; i < iCoerceIn; i++) {
            emulateCycle();
        }
        float f = this.timeAccumulator + delta;
        this.timeAccumulator = f;
        if (f >= 0.016666668f) {
            int i2 = this.delayTimer;
            if (i2 > 0) {
                this.delayTimer = i2 - 1;
            }
            this.timeAccumulator = f - 0.016666668f;
        }
        if (this.drawFlag) {
            Pixmap pixmap = this.pixmap;
            if (pixmap != null) {
                pixmap.setColor(0.0f, 0.0f, 0.0f, 1.0f);
                pixmap.fill();
                pixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                for (int i3 = 0; i3 < 32; i3++) {
                    for (int i4 = 0; i4 < 64; i4++) {
                        if (this.gfx[(i3 * 64) + i4]) {
                            pixmap.drawPixel(i4, i3);
                        }
                    }
                }
                Texture texture = this.texture;
                if (texture != null) {
                    texture.draw(pixmap, 0, 0);
                }
            }
            this.drawFlag = false;
        }
        return false;
    }

    private final void emulateCycle() {
        byte[] bArr = this.memory;
        int i = this.pc;
        int i2 = (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
        int i3 = (i2 & 3840) >> 8;
        int i4 = (i2 & 240) >> 4;
        int i5 = i2 & UnixStat.PERM_MASK;
        int i6 = i2 & 255;
        int i7 = i2 & 15;
        this.pc = i + 2;
        int varValue = (int) getVarValue("chip8_key", -1.0f);
        int i8 = 0;
        switch (61440 & i2) {
            case 0:
                if (i2 == 224) {
                    ArraysKt.fill$default(this.gfx, false, 0, 0, 6, (Object) null);
                    this.drawFlag = true;
                    break;
                } else if (i2 == 238) {
                    int[] iArr = this.stack;
                    int i9 = this.sp - 1;
                    this.sp = i9;
                    this.pc = iArr[i9];
                    break;
                }
                break;
            case 4096:
                this.pc = i5;
                break;
            case 8192:
                int[] iArr2 = this.stack;
                int i10 = this.sp;
                this.sp = i10 + 1;
                iArr2[i10] = this.pc;
                this.pc = i5;
                break;
            case 12288:
                if (this.v[i3] == i6) {
                    this.pc += 2;
                }
                break;
            case 16384:
                if (this.v[i3] != i6) {
                    this.pc += 2;
                }
                break;
            case ContactCallbackEvent.ON_STARTED_FILTERED_MANIFOLD /* 20480 */:
                int[] iArr3 = this.v;
                if (iArr3[i3] == iArr3[i4]) {
                    this.pc += 2;
                }
                break;
            case CpioConstants.C_ISBLK /* 24576 */:
                this.v[i3] = i6;
                break;
            case 28672:
                int[] iArr4 = this.v;
                iArr4[i3] = (iArr4[i3] + i6) & 255;
                break;
            case 32768:
                if (i7 != 14) {
                    switch (i7) {
                        case 0:
                            int[] iArr5 = this.v;
                            iArr5[i3] = iArr5[i4];
                            break;
                        case 1:
                            int[] iArr6 = this.v;
                            iArr6[i3] = iArr6[i3] | iArr6[i4];
                            break;
                        case 2:
                            int[] iArr7 = this.v;
                            iArr7[i3] = iArr7[i3] & iArr7[i4];
                            break;
                        case 3:
                            int[] iArr8 = this.v;
                            iArr8[i3] = iArr8[i3] ^ iArr8[i4];
                            break;
                        case 4:
                            int[] iArr9 = this.v;
                            int i11 = iArr9[i3] + iArr9[i4];
                            iArr9[15] = i11 > 255 ? 1 : 0;
                            iArr9[i3] = i11 & 255;
                            break;
                        case 5:
                            int[] iArr10 = this.v;
                            iArr10[15] = iArr10[i3] > iArr10[i4] ? 1 : 0;
                            iArr10[i3] = (iArr10[i3] - iArr10[i4]) & 255;
                            break;
                        case 6:
                            int[] iArr11 = this.v;
                            iArr11[15] = iArr11[i3] & 1;
                            iArr11[i3] = iArr11[i3] >> 1;
                            break;
                        case 7:
                            int[] iArr12 = this.v;
                            iArr12[15] = iArr12[i4] > iArr12[i3] ? 1 : 0;
                            iArr12[i3] = (iArr12[i4] - iArr12[i3]) & 255;
                            break;
                    }
                } else {
                    int[] iArr13 = this.v;
                    iArr13[15] = iArr13[i3] >> 7;
                    iArr13[i3] = (iArr13[i3] << 1) & 255;
                    break;
                }
                break;
            case 36864:
                int[] iArr14 = this.v;
                if (iArr14[i3] != iArr14[i4]) {
                    this.pc += 2;
                }
                break;
            case 40960:
                this.iReg = i5;
                break;
            case 45056:
                this.pc = i5 + this.v[0];
                break;
            case 49152:
                this.v[i3] = Random.INSTANCE.nextInt(256) & i6;
                break;
            case ContactCallbackEvent.ON_STARTED_FILTERED_VALUE /* 53248 */:
                int[] iArr15 = this.v;
                int i12 = iArr15[i3] % 64;
                int i13 = iArr15[i4] % 32;
                iArr15[15] = 0;
                for (int i14 = 0; i14 < i7; i14++) {
                    byte b = this.memory[this.iReg + i14];
                    for (int i15 = 0; i15 < 8; i15++) {
                        if (((128 >> i15) & b) != 0) {
                            int i16 = ((i12 + i15) % 64) + (((i13 + i14) % 32) * 64);
                            boolean[] zArr = this.gfx;
                            boolean z = zArr[i16];
                            if (z) {
                                this.v[15] = 1;
                            }
                            zArr[i16] = !z;
                        }
                    }
                }
                this.drawFlag = true;
                break;
            case FileMode.TYPE_GITLINK /* 57344 */:
                if (i6 == 158) {
                    if (varValue == this.v[i3]) {
                        this.pc += 2;
                    }
                } else if (i6 == 161 && varValue != this.v[i3]) {
                    this.pc += 2;
                }
                break;
            case 61440:
                if (i6 == 7) {
                    this.v[i3] = this.delayTimer;
                } else if (i6 == 10) {
                    if (varValue >= 0 && varValue < 16) {
                        this.v[i3] = varValue;
                    } else {
                        this.pc -= 2;
                    }
                } else if (i6 == 21) {
                    this.delayTimer = this.v[i3];
                } else if (i6 == 30) {
                    this.iReg += this.v[i3];
                } else if (i6 == 41) {
                    this.iReg = (this.v[i3] * 5) + 80;
                } else if (i6 == 51) {
                    byte[] bArr2 = this.memory;
                    int i17 = this.iReg;
                    int i18 = this.v[i3];
                    bArr2[i17] = (byte) (i18 / 100);
                    bArr2[i17 + 1] = (byte) ((i18 / 10) % 10);
                    bArr2[i17 + 2] = (byte) ((i18 % 100) % 10);
                } else if (i6 == 85) {
                    if (i3 >= 0) {
                        while (true) {
                            this.memory[this.iReg + i8] = (byte) this.v[i8];
                            if (i8 != i3) {
                                i8++;
                            }
                        }
                    }
                } else if (i6 == 101 && i3 >= 0) {
                    while (true) {
                        this.v[i8] = this.memory[this.iReg + i8] & 255;
                        if (i8 != i3) {
                            i8++;
                        }
                    }
                }
                break;
        }
    }

    private final void setConsole(String msg) {
        List<UserVariable> userVariables;
        Scope scope = this.scope;
        Project project = scope != null ? scope.getProject() : null;
        UserVariable userVariable = project != null ? project.getUserVariable("chip8_console") : null;
        if (userVariable != null) {
            userVariable.setValue(msg);
        } else {
            if (project == null || (userVariables = project.getUserVariables()) == null) {
                return;
            }
            userVariables.add(new UserVariable("chip8_console", msg));
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        Texture texture = this.texture;
        if (texture != null) {
            texture.dispose();
        }
        Pixmap pixmap = this.pixmap;
        if (pixmap != null) {
            pixmap.dispose();
        }
        Image image = this.screenImage;
        if (image != null) {
            image.remove();
        }
    }
}
