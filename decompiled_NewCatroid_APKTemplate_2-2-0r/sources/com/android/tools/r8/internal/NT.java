package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.idea.KotlinFileType;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class NT {
    private static final HashSet a;
    static final /* synthetic */ boolean b = true;

    public static String a(String str, String str2, boolean z) {
        if (!z || a.contains(str2)) {
            return str2;
        }
        String strA = AbstractC2406ki.a(str2);
        int iLastIndexOf = str.lastIndexOf(46);
        int iIndexOf = str.indexOf(36, iLastIndexOf);
        if (iLastIndexOf > iIndexOf || iIndexOf < 0) {
            iIndexOf = str.length();
        }
        String strSubstring = str.substring(iLastIndexOf + 1, iIndexOf);
        if (strSubstring.endsWith("Kt") && (strA.isEmpty() || strA.equals(KotlinFileType.EXTENSION))) {
            strSubstring = AbstractC0402a.a(strSubstring, 2, 0);
            strA = KotlinFileType.EXTENSION;
        } else if (!strA.equals(KotlinFileType.EXTENSION)) {
            strA = SuffixConstants.EXTENSION_java;
        }
        return strSubstring + "." + strA;
    }

    static {
        HashSet hashSetA = OU.a(1);
        Collections.addAll(hashSetA, "Native Method");
        a = hashSetA;
    }

    static MethodReference a(com.android.tools.r8.naming.Q.b bVar, ClassReference classReference) {
        if (bVar.e()) {
            classReference = Reference.classFromDescriptor(C3970u0.H(bVar.g()));
        }
        ArrayList arrayList = new ArrayList(bVar.d.length);
        for (String str : bVar.d) {
            arrayList.add(Reference.typeFromTypeName(str));
        }
        return Reference.method(classReference, bVar.e() ? bVar.h() : bVar.a, arrayList, Reference.returnTypeFromDescriptor(C3970u0.H(bVar.c)));
    }

    public static int a(String str) {
        InterfaceC3205vM interfaceC3205vMA = CM.a(new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.NT$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return Character.isWhitespace(((Character) obj).charValue());
            }
        });
        for (int i = 0; i < str.length(); i++) {
            if (interfaceC3205vMA.apply(Character.valueOf(str.charAt(i)))) {
                return i;
            }
        }
        return str.length();
    }
}
