package org.catrobat.catroid.apkbuild;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/ProjectScanner.class
  classes12.dex
 */
/* JADX INFO: compiled from: ProjectScanner.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/apkbuild/ProjectScanner;", "", "()V", "scanProject", "Lorg/catrobat/catroid/apkbuild/ProjectScanner$ScanResult;", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "ScanResult", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectScanner {
    public static final ProjectScanner INSTANCE = new ProjectScanner();

    private ProjectScanner() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/apkbuild/ProjectScanner$ScanResult.class
      classes12.dex
     */
    /* JADX INFO: compiled from: ProjectScanner.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b5\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0003¢\u0006\u0002\u0010\u0013J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0003HÆ\u0003J\t\u0010.\u001a\u00020\u0003HÆ\u0003J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\t\u00100\u001a\u00020\u0003HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0003HÆ\u0003J\t\u00103\u001a\u00020\u0003HÆ\u0003J\t\u00104\u001a\u00020\u0003HÆ\u0003J©\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0003HÆ\u0001J\u0013\u00106\u001a\u00020\u00032\b\u00107\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00108\u001a\u000209HÖ\u0001J\t\u0010:\u001a\u00020;HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u0012\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0015R\u0011\u0010\u0011\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015¨\u0006<"}, d2 = {"Lorg/catrobat/catroid/apkbuild/ProjectScanner$ScanResult;", "", "hasQemu", "", "hasPython", "hasCompiler", "hasMkit", "hasOnnx", "hasMnn", "needsInternet", "needsCamera", "needsLocation", "needsBluetooth", "needsNfc", "needsAudioRecord", "needsVibrate", "needsOverlay", "needsStorage", "needsBackground", "(ZZZZZZZZZZZZZZZZ)V", "getHasCompiler", "()Z", "getHasMkit", "getHasMnn", "getHasOnnx", "getHasPython", "getHasQemu", "getNeedsAudioRecord", "getNeedsBackground", "getNeedsBluetooth", "getNeedsCamera", "getNeedsInternet", "getNeedsLocation", "getNeedsNfc", "getNeedsOverlay", "getNeedsStorage", "getNeedsVibrate", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ScanResult {
        private final boolean hasCompiler;
        private final boolean hasMkit;
        private final boolean hasMnn;
        private final boolean hasOnnx;
        private final boolean hasPython;
        private final boolean hasQemu;
        private final boolean needsAudioRecord;
        private final boolean needsBackground;
        private final boolean needsBluetooth;
        private final boolean needsCamera;
        private final boolean needsInternet;
        private final boolean needsLocation;
        private final boolean needsNfc;
        private final boolean needsOverlay;
        private final boolean needsStorage;
        private final boolean needsVibrate;

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getHasQemu() {
            return this.hasQemu;
        }

        /* JADX INFO: renamed from: component10, reason: from getter */
        public final boolean getNeedsBluetooth() {
            return this.needsBluetooth;
        }

        /* JADX INFO: renamed from: component11, reason: from getter */
        public final boolean getNeedsNfc() {
            return this.needsNfc;
        }

        /* JADX INFO: renamed from: component12, reason: from getter */
        public final boolean getNeedsAudioRecord() {
            return this.needsAudioRecord;
        }

        /* JADX INFO: renamed from: component13, reason: from getter */
        public final boolean getNeedsVibrate() {
            return this.needsVibrate;
        }

        /* JADX INFO: renamed from: component14, reason: from getter */
        public final boolean getNeedsOverlay() {
            return this.needsOverlay;
        }

        /* JADX INFO: renamed from: component15, reason: from getter */
        public final boolean getNeedsStorage() {
            return this.needsStorage;
        }

        /* JADX INFO: renamed from: component16, reason: from getter */
        public final boolean getNeedsBackground() {
            return this.needsBackground;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasPython() {
            return this.hasPython;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getHasCompiler() {
            return this.hasCompiler;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getHasMkit() {
            return this.hasMkit;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getHasOnnx() {
            return this.hasOnnx;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final boolean getHasMnn() {
            return this.hasMnn;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final boolean getNeedsInternet() {
            return this.needsInternet;
        }

        /* JADX INFO: renamed from: component8, reason: from getter */
        public final boolean getNeedsCamera() {
            return this.needsCamera;
        }

        /* JADX INFO: renamed from: component9, reason: from getter */
        public final boolean getNeedsLocation() {
            return this.needsLocation;
        }

        public final ScanResult copy(boolean hasQemu, boolean hasPython, boolean hasCompiler, boolean hasMkit, boolean hasOnnx, boolean hasMnn, boolean needsInternet, boolean needsCamera, boolean needsLocation, boolean needsBluetooth, boolean needsNfc, boolean needsAudioRecord, boolean needsVibrate, boolean needsOverlay, boolean needsStorage, boolean needsBackground) {
            return new ScanResult(hasQemu, hasPython, hasCompiler, hasMkit, hasOnnx, hasMnn, needsInternet, needsCamera, needsLocation, needsBluetooth, needsNfc, needsAudioRecord, needsVibrate, needsOverlay, needsStorage, needsBackground);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ScanResult)) {
                return false;
            }
            ScanResult scanResult = (ScanResult) other;
            return this.hasQemu == scanResult.hasQemu && this.hasPython == scanResult.hasPython && this.hasCompiler == scanResult.hasCompiler && this.hasMkit == scanResult.hasMkit && this.hasOnnx == scanResult.hasOnnx && this.hasMnn == scanResult.hasMnn && this.needsInternet == scanResult.needsInternet && this.needsCamera == scanResult.needsCamera && this.needsLocation == scanResult.needsLocation && this.needsBluetooth == scanResult.needsBluetooth && this.needsNfc == scanResult.needsNfc && this.needsAudioRecord == scanResult.needsAudioRecord && this.needsVibrate == scanResult.needsVibrate && this.needsOverlay == scanResult.needsOverlay && this.needsStorage == scanResult.needsStorage && this.needsBackground == scanResult.needsBackground;
        }

        public int hashCode() {
            return (((((((((((((((((((((((((((((Boolean.hashCode(this.hasQemu) * 31) + Boolean.hashCode(this.hasPython)) * 31) + Boolean.hashCode(this.hasCompiler)) * 31) + Boolean.hashCode(this.hasMkit)) * 31) + Boolean.hashCode(this.hasOnnx)) * 31) + Boolean.hashCode(this.hasMnn)) * 31) + Boolean.hashCode(this.needsInternet)) * 31) + Boolean.hashCode(this.needsCamera)) * 31) + Boolean.hashCode(this.needsLocation)) * 31) + Boolean.hashCode(this.needsBluetooth)) * 31) + Boolean.hashCode(this.needsNfc)) * 31) + Boolean.hashCode(this.needsAudioRecord)) * 31) + Boolean.hashCode(this.needsVibrate)) * 31) + Boolean.hashCode(this.needsOverlay)) * 31) + Boolean.hashCode(this.needsStorage)) * 31) + Boolean.hashCode(this.needsBackground);
        }

        public String toString() {
            return "ScanResult(hasQemu=" + this.hasQemu + ", hasPython=" + this.hasPython + ", hasCompiler=" + this.hasCompiler + ", hasMkit=" + this.hasMkit + ", hasOnnx=" + this.hasOnnx + ", hasMnn=" + this.hasMnn + ", needsInternet=" + this.needsInternet + ", needsCamera=" + this.needsCamera + ", needsLocation=" + this.needsLocation + ", needsBluetooth=" + this.needsBluetooth + ", needsNfc=" + this.needsNfc + ", needsAudioRecord=" + this.needsAudioRecord + ", needsVibrate=" + this.needsVibrate + ", needsOverlay=" + this.needsOverlay + ", needsStorage=" + this.needsStorage + ", needsBackground=" + this.needsBackground + Constant.AFTER_QUTO;
        }

        public ScanResult(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16) {
            this.hasQemu = z;
            this.hasPython = z2;
            this.hasCompiler = z3;
            this.hasMkit = z4;
            this.hasOnnx = z5;
            this.hasMnn = z6;
            this.needsInternet = z7;
            this.needsCamera = z8;
            this.needsLocation = z9;
            this.needsBluetooth = z10;
            this.needsNfc = z11;
            this.needsAudioRecord = z12;
            this.needsVibrate = z13;
            this.needsOverlay = z14;
            this.needsStorage = z15;
            this.needsBackground = z16;
        }

        public final boolean getHasQemu() {
            return this.hasQemu;
        }

        public final boolean getHasPython() {
            return this.hasPython;
        }

        public final boolean getHasCompiler() {
            return this.hasCompiler;
        }

        public final boolean getHasMkit() {
            return this.hasMkit;
        }

        public final boolean getHasOnnx() {
            return this.hasOnnx;
        }

        public final boolean getHasMnn() {
            return this.hasMnn;
        }

        public final boolean getNeedsInternet() {
            return this.needsInternet;
        }

        public final boolean getNeedsCamera() {
            return this.needsCamera;
        }

        public final boolean getNeedsLocation() {
            return this.needsLocation;
        }

        public final boolean getNeedsBluetooth() {
            return this.needsBluetooth;
        }

        public final boolean getNeedsNfc() {
            return this.needsNfc;
        }

        public final boolean getNeedsAudioRecord() {
            return this.needsAudioRecord;
        }

        public final boolean getNeedsVibrate() {
            return this.needsVibrate;
        }

        public final boolean getNeedsOverlay() {
            return this.needsOverlay;
        }

        public final boolean getNeedsStorage() {
            return this.needsStorage;
        }

        public final boolean getNeedsBackground() {
            return this.needsBackground;
        }
    }

    public final ScanResult scanProject(File projectDir) {
        final Ref.BooleanRef booleanRef;
        final Ref.BooleanRef booleanRef2;
        Intrinsics.checkNotNullParameter(projectDir, "projectDir");
        File file = new File(projectDir, Constants.CODE_XML_FILE_NAME);
        if (!file.exists()) {
            return new ScanResult(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        }
        final Ref.BooleanRef booleanRef3 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef4 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef5 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef6 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef7 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef8 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef9 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef10 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef11 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef12 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef13 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef14 = new Ref.BooleanRef();
        Ref.BooleanRef booleanRef15 = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef16 = new Ref.BooleanRef();
        Ref.BooleanRef booleanRef17 = new Ref.BooleanRef();
        try {
            booleanRef2 = booleanRef17;
            try {
                booleanRef = booleanRef15;
                try {
                    Reader inputStreamReader = new InputStreamReader(new FileInputStream(file), Charsets.UTF_8);
                    BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
                    try {
                        TextStreamsKt.forEachLine(bufferedReader, new Function1<String, Unit>() { // from class: org.catrobat.catroid.apkbuild.ProjectScanner$scanProject$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                                invoke2(str);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(String line) {
                                Intrinsics.checkNotNullParameter(line, "line");
                                String str = line;
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "RunVMBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "RunVM2Brick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "StopVMBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "CreateDiskBrick", false, 2, (Object) null)) {
                                    booleanRef3.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "RunPythonScriptBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "LoadNativeModuleBrick", false, 2, (Object) null)) {
                                    booleanRef4.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "JavaCompileBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "JavaLoadAndRunBrick", false, 2, (Object) null)) {
                                    booleanRef5.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "LoadNNBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "PredictNNBrick", false, 2, (Object) null)) {
                                    booleanRef7.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "PtSetTrainingBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "PtCreateTensorBrick", false, 2, (Object) null)) {
                                    booleanRef8.element = true;
                                }
                                if (StringsKt.contains((CharSequence) str, (CharSequence) "Multiplayer", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Http", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Download", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Network", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Firebase", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Mqtt", true)) {
                                    booleanRef9.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "CameraBrick", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "ChooseCameraBrick", false, 2, (Object) null)) {
                                    booleanRef10.element = true;
                                    if (StringsKt.contains((CharSequence) str, (CharSequence) "FACE_DETECTED", true) || StringsKt.contains((CharSequence) str, (CharSequence) "FACE_SIZE", true) || StringsKt.contains((CharSequence) str, (CharSequence) "TEXT_BLOCK", true) || StringsKt.contains$default((CharSequence) str, (CharSequence) "OBJECT_ID", false, 2, (Object) null)) {
                                        booleanRef6.element = true;
                                    }
                                }
                                if (StringsKt.contains((CharSequence) str, (CharSequence) "Location", true) || StringsKt.contains((CharSequence) str, (CharSequence) "GPS", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Latitude", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Longitude", true)) {
                                    booleanRef11.element = true;
                                }
                                if (StringsKt.contains((CharSequence) str, (CharSequence) "Bluetooth", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Nxt", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Ev3", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Lego", true)) {
                                    booleanRef12.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "Nfc", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) Constants.NFC, false, 2, (Object) null)) {
                                    booleanRef13.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "StartRecordingBrick", false, 2, (Object) null) || StringsKt.contains((CharSequence) str, (CharSequence) "Audio", true) || StringsKt.contains((CharSequence) str, (CharSequence) "Speech", true)) {
                                    booleanRef14.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "VibrationBrick", false, 2, (Object) null)) {
                                    booleanRef.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "File", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "Save", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "Export", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "Write", false, 2, (Object) null)) {
                                    booleanRef16.element = true;
                                }
                                if (StringsKt.contains$default((CharSequence) str, (CharSequence) "Background", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "Service", false, 2, (Object) null) || StringsKt.contains$default((CharSequence) str, (CharSequence) "Foreground", false, 2, (Object) null)) {
                                    booleanRef2.element = true;
                                }
                            }
                        });
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(bufferedReader, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(bufferedReader, th);
                            throw th2;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e = e2;
                booleanRef = booleanRef15;
            }
        } catch (Exception e3) {
            e = e3;
            booleanRef = booleanRef15;
            booleanRef2 = booleanRef17;
        }
        return new ScanResult(booleanRef3.element, booleanRef4.element, booleanRef5.element, booleanRef6.element, booleanRef7.element, booleanRef8.element, booleanRef9.element, booleanRef10.element, booleanRef11.element, booleanRef12.element, booleanRef13.element, booleanRef14.element, booleanRef.element, false, booleanRef16.element, booleanRef2.element);
    }
}
