package com.android.tools.r8.diagnostic.internal;

import com.android.tools.r8.diagnostic.MissingDefinitionInfo;
import com.android.tools.r8.diagnostic.MissingDefinitionsDiagnostic;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class m implements MissingDefinitionsDiagnostic {
    static final /* synthetic */ boolean b = true;
    private final Collection a;

    private m(AbstractC1639am abstractC1639am) {
        if (!b && abstractC1639am.isEmpty()) {
            throw new AssertionError();
        }
        this.a = abstractC1639am;
    }

    public static l a() {
        return new l();
    }

    @Override // com.android.tools.r8.Diagnostic
    public final String getDiagnosticMessage() {
        final StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList(this.a);
        arrayList.sort(k.a());
        Iterator it = arrayList.iterator();
        if (!b && !it.hasNext()) {
            throw new AssertionError();
        }
        k.a(sb, (MissingDefinitionInfo) it.next());
        it.forEachRemaining(new Consumer() { // from class: com.android.tools.r8.diagnostic.internal.m$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                m.a(sb, (MissingDefinitionInfo) obj);
            }
        });
        return sb.toString();
    }

    @Override // com.android.tools.r8.diagnostic.MissingDefinitionsDiagnostic
    public final Collection getMissingDefinitions() {
        ArrayList arrayList = new ArrayList(this.a);
        arrayList.sort(k.a());
        return arrayList;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Position getPosition() {
        return Position.UNKNOWN;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, MissingDefinitionInfo missingDefinitionInfo) {
        k.a(sb.append(System.lineSeparator()), missingDefinitionInfo);
    }
}
