package com.github.difflib.unifieddiff;

import com.beust.jcommander.Parameters;
import com.github.difflib.patch.AbstractDelta;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.diff.DiffEntry;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class UnifiedDiffWriter {
    private static final Logger LOG = Logger.getLogger(UnifiedDiffWriter.class.getName());

    public static void write(UnifiedDiff unifiedDiff, Function<String, List<String>> function, final Writer writer, int i) throws IOException {
        Objects.requireNonNull(function, "original lines provider needs to be specified");
        write(unifiedDiff, function, (Consumer<String>) new Consumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffWriter$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UnifiedDiffWriter.lambda$write$0(writer, (String) obj);
            }
        }, i);
    }

    static /* synthetic */ void lambda$write$0(Writer writer, String str) {
        try {
            writer.append((CharSequence) str).append("\n");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, (String) null, (Throwable) e);
        }
    }

    public static void write(UnifiedDiff unifiedDiff, Function<String, List<String>> function, Consumer<String> consumer, int i) throws IOException {
        if (unifiedDiff.getHeader() != null) {
            consumer.accept(unifiedDiff.getHeader());
        }
        for (UnifiedDiffFile unifiedDiffFile : unifiedDiff.getFiles()) {
            ArrayList arrayList = new ArrayList(unifiedDiffFile.getPatch().getDeltas());
            if (!arrayList.isEmpty()) {
                writeOrNothing(consumer, unifiedDiffFile.getDiffCommand());
                if (unifiedDiffFile.getIndex() != null) {
                    consumer.accept("index " + unifiedDiffFile.getIndex());
                }
                consumer.accept("--- " + (unifiedDiffFile.getFromFile() == null ? DiffEntry.DEV_NULL : unifiedDiffFile.getFromFile()));
                if (unifiedDiffFile.getToFile() != null) {
                    consumer.accept("+++ " + unifiedDiffFile.getToFile());
                }
                List<String> listApply = function.apply(unifiedDiffFile.getFromFile());
                ArrayList arrayList2 = new ArrayList();
                boolean z = false;
                AbstractDelta abstractDelta = (AbstractDelta) arrayList.get(0);
                arrayList2.add(abstractDelta);
                if (arrayList.size() > 1) {
                    int i2 = 1;
                    while (i2 < arrayList.size()) {
                        int position = abstractDelta.getSource().getPosition();
                        AbstractDelta abstractDelta2 = (AbstractDelta) arrayList.get(i2);
                        if (position + abstractDelta.getSource().size() + i >= abstractDelta2.getSource().getPosition() - i) {
                            arrayList2.add(abstractDelta2);
                        } else {
                            processDeltas(consumer, listApply, arrayList2, i, false);
                            arrayList2.clear();
                            arrayList2.add(abstractDelta2);
                        }
                        i2++;
                        abstractDelta = abstractDelta2;
                    }
                }
                if (arrayList.size() == 1 && unifiedDiffFile.getFromFile() == null) {
                    z = true;
                }
                processDeltas(consumer, listApply, arrayList2, i, z);
            }
        }
        if (unifiedDiff.getTail() != null) {
            consumer.accept("--");
            consumer.accept(unifiedDiff.getTail());
        }
    }

    private static void processDeltas(final Consumer<String> consumer, List<String> list, List<AbstractDelta<String>> list2, int i, boolean z) {
        int position;
        final ArrayList arrayList = new ArrayList();
        int i2 = 0;
        AbstractDelta<String> abstractDelta = list2.get(0);
        int i3 = 1;
        if (z) {
            position = 0;
        } else {
            position = (abstractDelta.getSource().getPosition() + 1) - i;
            if (position < 1) {
                position = 1;
            }
        }
        int position2 = (abstractDelta.getTarget().getPosition() + 1) - i;
        if (position2 < 1) {
            position2 = 1;
        }
        int position3 = abstractDelta.getSource().getPosition() - i;
        if (position3 < 0) {
            position3 = 0;
        }
        int i4 = 0;
        for (int i5 = position3; i5 < abstractDelta.getSource().getPosition() && i5 < list.size(); i5++) {
            arrayList.add(" " + list.get(i5));
            i2++;
            i4++;
        }
        getDeltaText(new Consumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffWriter$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((String) obj);
            }
        }, abstractDelta);
        int size = i2 + abstractDelta.getSource().getLines().size();
        int size2 = i4 + abstractDelta.getTarget().getLines().size();
        while (i3 < list2.size()) {
            AbstractDelta<String> abstractDelta2 = list2.get(i3);
            for (int position4 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size(); position4 < abstractDelta2.getSource().getPosition() && position4 < list.size(); position4++) {
                arrayList.add(" " + list.get(position4));
                size++;
                size2++;
            }
            getDeltaText(new Consumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffWriter$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((String) obj);
                }
            }, abstractDelta2);
            size += abstractDelta2.getSource().getLines().size();
            size2 += abstractDelta2.getTarget().getLines().size();
            i3++;
            abstractDelta = abstractDelta2;
        }
        int position5 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size();
        for (int i6 = position5; i6 < position5 + i && i6 < list.size(); i6++) {
            arrayList.add(" " + list.get(i6));
            size++;
            size2++;
        }
        consumer.accept("@@ -" + position + "," + size + " +" + position2 + "," + size2 + " @@");
        arrayList.forEach(new Consumer() { // from class: com.github.difflib.unifieddiff.UnifiedDiffWriter$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                consumer.accept((String) obj);
            }
        });
    }

    private static void getDeltaText(Consumer<String> consumer, AbstractDelta<String> abstractDelta) {
        Iterator<String> it = abstractDelta.getSource().getLines().iterator();
        while (it.hasNext()) {
            consumer.accept(Parameters.DEFAULT_OPTION_PREFIXES + it.next());
        }
        Iterator<String> it2 = abstractDelta.getTarget().getLines().iterator();
        while (it2.hasNext()) {
            consumer.accept(Marker.ANY_NON_NULL_MARKER + it2.next());
        }
    }

    private static void writeOrNothing(Consumer<String> consumer, String str) throws IOException {
        if (str != null) {
            consumer.accept(str);
        }
    }
}
