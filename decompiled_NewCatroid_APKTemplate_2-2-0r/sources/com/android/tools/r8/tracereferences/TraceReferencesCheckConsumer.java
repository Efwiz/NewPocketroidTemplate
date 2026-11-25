package com.android.tools.r8.tracereferences;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.diagnostic.DefinitionClassContext;
import com.android.tools.r8.diagnostic.DefinitionFieldContext;
import com.android.tools.r8.diagnostic.DefinitionMethodContext;
import com.android.tools.r8.diagnostic.internal.l;
import com.android.tools.r8.diagnostic.internal.m;
import com.android.tools.r8.diagnostic.internal.n;
import com.android.tools.r8.diagnostic.internal.o;
import com.android.tools.r8.diagnostic.internal.p;
import com.android.tools.r8.diagnostic.internal.q;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.PackageReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class TraceReferencesCheckConsumer extends TraceReferencesConsumer.ForwardingConsumer {
    private final ConcurrentHashMap c;
    private final ConcurrentHashMap d;
    private final ConcurrentHashMap e;

    public TraceReferencesCheckConsumer(TraceReferencesConsumer traceReferencesConsumer) {
        super(traceReferencesConsumer);
        this.c = new ConcurrentHashMap();
        this.d = new ConcurrentHashMap();
        this.e = new ConcurrentHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(ClassReference classReference) {
        return new ConcurrentHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Map map, DefinitionClassContext definitionClassContext) {
        map.put(definitionClassContext.getClassReference(), definitionClassContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Map map, DefinitionClassContext definitionClassContext) {
        map.put(definitionClassContext.getClassReference(), definitionClassContext);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ForwardingConsumer, com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public void acceptField(TraceReferencesConsumer.TracedField tracedField, DiagnosticsHandler diagnosticsHandler) {
        super.acceptField(tracedField, diagnosticsHandler);
        if (tracedField.isMissingDefinition()) {
            final Map map = (Map) this.d.computeIfAbsent(tracedField.getReference(), new Function() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return TraceReferencesCheckConsumer.a((FieldReference) obj);
                }
            });
            com.android.tools.r8.diagnostic.internal.d.a(tracedField.getReferencedFromContext(), new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.a(map, (DefinitionClassContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.a(map, (DefinitionFieldContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.a(map, (DefinitionMethodContext) obj);
                }
            });
        }
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ForwardingConsumer, com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public void acceptMethod(TraceReferencesConsumer.TracedMethod tracedMethod, DiagnosticsHandler diagnosticsHandler) {
        super.acceptMethod(tracedMethod, diagnosticsHandler);
        if (tracedMethod.isMissingDefinition()) {
            final Map map = (Map) this.e.computeIfAbsent(tracedMethod.getReference(), new Function() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return TraceReferencesCheckConsumer.a((MethodReference) obj);
                }
            });
            com.android.tools.r8.diagnostic.internal.d.a(tracedMethod.getReferencedFromContext(), new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.b(map, (DefinitionClassContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.b(map, (DefinitionFieldContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.b(map, (DefinitionMethodContext) obj);
                }
            });
        }
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ForwardingConsumer, com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public void acceptPackage(PackageReference packageReference, DiagnosticsHandler diagnosticsHandler) {
        super.acceptPackage(packageReference, diagnosticsHandler);
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ForwardingConsumer, com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public void acceptType(TraceReferencesConsumer.TracedClass tracedClass, DiagnosticsHandler diagnosticsHandler) {
        super.acceptType(tracedClass, diagnosticsHandler);
        if (tracedClass.isMissingDefinition()) {
            final Map map = (Map) this.c.computeIfAbsent(tracedClass.getReference(), new Function() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return TraceReferencesCheckConsumer.a((ClassReference) obj);
                }
            });
            com.android.tools.r8.diagnostic.internal.d.a(tracedClass.getReferencedFromContext(), new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.c(map, (DefinitionClassContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.c(map, (DefinitionFieldContext) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TraceReferencesCheckConsumer.c(map, (DefinitionMethodContext) obj);
                }
            });
        }
    }

    @Override // com.android.tools.r8.tracereferences.TraceReferencesConsumer.ForwardingConsumer, com.android.tools.r8.tracereferences.TraceReferencesConsumer
    public void finished(DiagnosticsHandler diagnosticsHandler) {
        super.finished(diagnosticsHandler);
        if (this.c.isEmpty() && this.d.isEmpty() && this.e.isEmpty()) {
            return;
        }
        diagnosticsHandler.error(a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(FieldReference fieldReference) {
        return new ConcurrentHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Map map, DefinitionFieldContext definitionFieldContext) {
        map.put(definitionFieldContext.getFieldReference(), definitionFieldContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Map map, DefinitionFieldContext definitionFieldContext) {
        map.put(definitionFieldContext.getFieldReference(), definitionFieldContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, DefinitionClassContext definitionClassContext) {
        map.put(definitionClassContext.getClassReference(), definitionClassContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Map map, DefinitionMethodContext definitionMethodContext) {
        map.put(definitionMethodContext.getMethodReference(), definitionMethodContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Map map, DefinitionMethodContext definitionMethodContext) {
        map.put(definitionMethodContext.getMethodReference(), definitionMethodContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, DefinitionFieldContext definitionFieldContext) {
        map.put(definitionFieldContext.getFieldReference(), definitionFieldContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, DefinitionMethodContext definitionMethodContext) {
        map.put(definitionMethodContext.getMethodReference(), definitionMethodContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(MethodReference methodReference) {
        return new ConcurrentHashMap();
    }

    private m a() {
        final l lVarA = m.a();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                TraceReferencesCheckConsumer.a(lVarA, (ClassReference) obj, (Map) obj2);
            }
        });
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                TraceReferencesCheckConsumer.a(lVarA, (FieldReference) obj, (Map) obj2);
            }
        });
        this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCheckConsumer$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                TraceReferencesCheckConsumer.a(lVarA, (MethodReference) obj, (Map) obj2);
            }
        });
        return lVarA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(l lVar, ClassReference classReference, Map map) {
        lVar.a((com.android.tools.r8.diagnostic.internal.j) ((com.android.tools.r8.diagnostic.internal.g) com.android.tools.r8.diagnostic.internal.h.a().a(classReference).a(map.values())).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(l lVar, FieldReference fieldReference, Map map) {
        lVar.a((com.android.tools.r8.diagnostic.internal.j) ((n) o.a().a(fieldReference).a(map.values())).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(l lVar, MethodReference methodReference, Map map) {
        lVar.a((com.android.tools.r8.diagnostic.internal.j) ((p) q.a().a(methodReference).a(map.values())).b());
    }
}
