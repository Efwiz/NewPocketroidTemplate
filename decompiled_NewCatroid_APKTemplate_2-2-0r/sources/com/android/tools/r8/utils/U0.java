package com.android.tools.r8.utils;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.ExtractMarker;
import com.android.tools.r8.ExtractMarkerCommand;
import com.android.tools.r8.origin.Origin;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class U0 {
    public static Collection<com.android.tools.r8.dex.U> a(Path path) throws CompilationFailedException {
        ExtractMarkerCommand.Builder builderAddProgramFiles = ExtractMarkerCommand.builder().addProgramFiles(path);
        ArrayList arrayList = new ArrayList();
        ExtractMarker.run(builderAddProgramFiles.setMarkerInfoConsumer(new C3878c2(arrayList)).build());
        return arrayList;
    }

    public static Collection<com.android.tools.r8.dex.U> b(byte[] bArr) throws CompilationFailedException {
        ExtractMarkerCommand.Builder builderAddDexProgramData = ExtractMarkerCommand.builder().addDexProgramData(bArr, Origin.unknown());
        ArrayList arrayList = new ArrayList();
        ExtractMarker.run(builderAddDexProgramData.setMarkerInfoConsumer(new C3878c2(arrayList)).build());
        return arrayList;
    }

    public static Collection<com.android.tools.r8.dex.U> a(byte[] bArr) throws CompilationFailedException {
        ExtractMarkerCommand.Builder builderAddClassProgramData = ExtractMarkerCommand.builder().addClassProgramData(bArr, Origin.unknown());
        ArrayList arrayList = new ArrayList();
        ExtractMarker.run(builderAddClassProgramData.setMarkerInfoConsumer(new C3878c2(arrayList)).build());
        return arrayList;
    }
}
