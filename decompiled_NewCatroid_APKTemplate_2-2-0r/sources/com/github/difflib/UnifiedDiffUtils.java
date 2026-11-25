package com.github.difflib;

import com.beust.jcommander.Parameters;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.Patch;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public final class UnifiedDiffUtils {
    private static final String NULL_FILE_INDICATOR = "/dev/null";
    private static final Pattern UNIFIED_DIFF_CHUNK_REGEXP = Pattern.compile("^@@\\s+-(?:(\\d+)(?:,(\\d+))?)\\s+\\+(?:(\\d+)(?:,(\\d+))?)\\s+@@$");

    public static Patch<String> parseUnifiedDiff(List<String> list) {
        ArrayList arrayList = new ArrayList();
        Patch<String> patch = new Patch<>();
        boolean z = true;
        int i = 0;
        int i2 = 0;
        for (String str : list) {
            if (z) {
                if (str.startsWith("+++")) {
                    z = false;
                }
            } else {
                Matcher matcher = UNIFIED_DIFF_CHUNK_REGEXP.matcher(str);
                if (matcher.find()) {
                    processLinesInPrevChunk(arrayList, patch, i, i2);
                    i = matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1));
                    i2 = matcher.group(3) == null ? 1 : Integer.parseInt(matcher.group(3));
                    if (i == 0) {
                        i = 1;
                    }
                    if (i2 == 0) {
                        i2 = 1;
                    }
                } else if (str.length() > 0) {
                    String strSubstring = str.substring(0, 1);
                    String strSubstring2 = str.substring(1);
                    if (" ".equals(strSubstring) || Marker.ANY_NON_NULL_MARKER.equals(strSubstring) || Parameters.DEFAULT_OPTION_PREFIXES.equals(strSubstring)) {
                        arrayList.add(new String[]{strSubstring, strSubstring2});
                    }
                } else {
                    arrayList.add(new String[]{" ", ""});
                }
            }
        }
        processLinesInPrevChunk(arrayList, patch, i, i2);
        return patch;
    }

    private static void processLinesInPrevChunk(List<String[]> list, Patch<String> patch, int i, int i2) {
        if (list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        for (String[] strArr : list) {
            String str = strArr[0];
            String str2 = strArr[1];
            if (" ".equals(str) || Parameters.DEFAULT_OPTION_PREFIXES.equals(str)) {
                i3++;
                arrayList.add(str2);
                if (Parameters.DEFAULT_OPTION_PREFIXES.equals(str)) {
                    arrayList3.add(Integer.valueOf((i - 1) + i3));
                }
            }
            if (" ".equals(str) || Marker.ANY_NON_NULL_MARKER.equals(str)) {
                i4++;
                arrayList2.add(str2);
                if (Marker.ANY_NON_NULL_MARKER.equals(str)) {
                    arrayList4.add(Integer.valueOf((i2 - 1) + i4));
                }
            }
        }
        patch.addDelta(new ChangeDelta(new Chunk(i - 1, arrayList, arrayList3), new Chunk(i2 - 1, arrayList2, arrayList4)));
        list.clear();
    }

    public static List<String> generateUnifiedDiff(String str, String str2, List<String> list, Patch<String> patch, int i) {
        if (!patch.getDeltas().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("--- " + ((String) Optional.ofNullable(str).orElse("/dev/null")));
            arrayList.add("+++ " + ((String) Optional.ofNullable(str2).orElse("/dev/null")));
            ArrayList arrayList2 = new ArrayList(patch.getDeltas());
            ArrayList arrayList3 = new ArrayList();
            boolean z = false;
            AbstractDelta abstractDelta = (AbstractDelta) arrayList2.get(0);
            arrayList3.add(abstractDelta);
            if (arrayList2.size() > 1) {
                int i2 = 1;
                while (i2 < arrayList2.size()) {
                    int position = abstractDelta.getSource().getPosition();
                    AbstractDelta abstractDelta2 = (AbstractDelta) arrayList2.get(i2);
                    if (position + abstractDelta.getSource().size() + i >= abstractDelta2.getSource().getPosition() - i) {
                        arrayList3.add(abstractDelta2);
                    } else {
                        arrayList.addAll(processDeltas(list, arrayList3, i, false));
                        arrayList3.clear();
                        arrayList3.add(abstractDelta2);
                    }
                    i2++;
                    abstractDelta = abstractDelta2;
                }
            }
            if (arrayList2.size() == 1 && str == null) {
                z = true;
            }
            arrayList.addAll(processDeltas(list, arrayList3, i, z));
            return arrayList;
        }
        return new ArrayList();
    }

    private static List<String> processDeltas(List<String> list, List<AbstractDelta<String>> list2, int i, boolean z) {
        int position;
        ArrayList arrayList = new ArrayList();
        AbstractDelta<String> abstractDelta = list2.get(0);
        int i2 = 1;
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
        int i3 = 0;
        int i4 = 0;
        while (position3 < abstractDelta.getSource().getPosition()) {
            arrayList.add(" " + list.get(position3));
            i3++;
            i4++;
            position3++;
        }
        arrayList.addAll(getDeltaText(abstractDelta));
        int size = i3 + abstractDelta.getSource().getLines().size();
        int size2 = i4 + abstractDelta.getTarget().getLines().size();
        while (i2 < list2.size()) {
            AbstractDelta<String> abstractDelta2 = list2.get(i2);
            for (int position4 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size(); position4 < abstractDelta2.getSource().getPosition(); position4++) {
                arrayList.add(" " + list.get(position4));
                size++;
                size2++;
            }
            arrayList.addAll(getDeltaText(abstractDelta2));
            size += abstractDelta2.getSource().getLines().size();
            size2 += abstractDelta2.getTarget().getLines().size();
            i2++;
            abstractDelta = abstractDelta2;
        }
        int position5 = abstractDelta.getSource().getPosition() + abstractDelta.getSource().getLines().size();
        for (int i5 = position5; i5 < position5 + i && i5 < list.size(); i5++) {
            arrayList.add(" " + list.get(i5));
            size++;
            size2++;
        }
        arrayList.add(0, "@@ -" + position + "," + size + " +" + position2 + "," + size2 + " @@");
        return arrayList;
    }

    private static List<String> getDeltaText(AbstractDelta<String> abstractDelta) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = abstractDelta.getSource().getLines().iterator();
        while (it.hasNext()) {
            arrayList.add(Parameters.DEFAULT_OPTION_PREFIXES + it.next());
        }
        Iterator<String> it2 = abstractDelta.getTarget().getLines().iterator();
        while (it2.hasNext()) {
            arrayList.add(Marker.ANY_NON_NULL_MARKER + it2.next());
        }
        return arrayList;
    }

    private UnifiedDiffUtils() {
    }
}
