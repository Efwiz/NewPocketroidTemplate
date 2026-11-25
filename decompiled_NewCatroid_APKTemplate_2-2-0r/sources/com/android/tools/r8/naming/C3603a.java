package com.android.tools.r8.naming;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.StringDiagnostic;

/* JADX INFO: renamed from: com.android.tools.r8.naming.a, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3603a extends StringDiagnostic {
    private C3603a(String str, Position position) {
        super(str, Origin.unknown(), position);
    }

    static C3603a a(String str, String str2, Position position) {
        return new C3603a(String.format("'%s' cannot be mapped to '%s' because it is in conflict with an existing class with the same name. This usually happens when compiling a test application against a source application and there are used classes in the test that was not given a -keep rule when compiling the app. Try either renaming '%s' such that it will not collide or add a keep rule to keep '%s'.", str, str2, str2, str), position);
    }

    static void b(String str, String str2, Position position) {
        new C3603a(String.format("'%s' cannot be mapped to '%s' because it is in conflict with an existing member with the same signature. This usually happens when compiling a test application against a source application and there are used classes in the test that was not given a -keep rule when compiling the app. Try either renaming '%s' such that it will not collide or add a keep rule to keep '%s'.", str, str2, str2, str), position);
    }
}
