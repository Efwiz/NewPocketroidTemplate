package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class NativeStats {
    private final HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot;

    public interface StatsInterface {
        int functionCount();

        int functionCounter(int i);

        String functionName(int i);

        String getNativeClass();
    }

    public static class NativeFunction implements Comparable<NativeFunction> {
        private int counter;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f928name;
        private final int ordinal;

        public NativeFunction(int i, String str, int i2) {
            this.ordinal = i;
            this.f928name = str;
            this.counter = i2;
        }

        void subtract(NativeFunction nativeFunction) {
            this.counter -= nativeFunction.counter;
        }

        public int getCounter() {
            return this.counter;
        }

        public void setCounter(int i) {
            this.counter = i;
        }

        public String getName() {
            return this.f928name;
        }

        public int getOrdinal() {
            return this.ordinal;
        }

        @Override // java.lang.Comparable
        public int compareTo(NativeFunction nativeFunction) {
            return nativeFunction.counter - this.counter;
        }

        public void reset() {
            this.counter = 0;
        }

        public NativeFunction copy() {
            return new NativeFunction(this.ordinal, this.f928name, this.counter);
        }
    }

    public NativeStats(StatsInterface... statsInterfaceArr) {
        this(Arrays.asList(statsInterfaceArr));
    }

    public NativeStats(Collection<StatsInterface> collection) {
        this(snapshot(collection));
    }

    private NativeStats(HashMap<StatsInterface, ArrayList<NativeFunction>> map) {
        this.snapshot = map;
    }

    public void reset() {
        Iterator<ArrayList<NativeFunction>> it = this.snapshot.values().iterator();
        while (it.hasNext()) {
            Iterator<NativeFunction> it2 = it.next().iterator();
            while (it2.hasNext()) {
                it2.next().reset();
            }
        }
    }

    public void update() {
        for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            StatsInterface key = entry.getKey();
            for (NativeFunction nativeFunction : entry.getValue()) {
                nativeFunction.setCounter(key.functionCounter(nativeFunction.getOrdinal()));
            }
        }
    }

    public NativeStats snapshot() {
        NativeStats nativeStatsCopy = copy();
        nativeStatsCopy.update();
        return nativeStatsCopy;
    }

    public NativeStats copy() {
        HashMap map = new HashMap(this.snapshot.size() * 2);
        for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            ArrayList arrayList = new ArrayList(entry.getValue().size());
            Iterator<NativeFunction> it = entry.getValue().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().copy());
            }
            map.put(entry.getKey(), arrayList);
        }
        return new NativeStats((HashMap<StatsInterface, ArrayList<NativeFunction>>) map);
    }

    public NativeStats diff() {
        HashMap map = new HashMap(this.snapshot.size() * 2);
        for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            StatsInterface key = entry.getKey();
            ArrayList arrayList = new ArrayList(entry.getValue().size());
            for (NativeFunction nativeFunction : entry.getValue()) {
                NativeFunction nativeFunctionCopy = nativeFunction.copy();
                nativeFunctionCopy.setCounter(key.functionCounter(nativeFunctionCopy.getOrdinal()));
                nativeFunctionCopy.subtract(nativeFunction);
                arrayList.add(nativeFunctionCopy);
            }
            map.put(key, arrayList);
        }
        return new NativeStats((HashMap<StatsInterface, ArrayList<NativeFunction>>) map);
    }

    public void dump(PrintStream printStream) {
        boolean z = true;
        for (Map.Entry<StatsInterface, ArrayList<NativeFunction>> entry : this.snapshot.entrySet()) {
            StatsInterface key = entry.getKey();
            ArrayList<NativeFunction> value = entry.getValue();
            Iterator<NativeFunction> it = value.iterator();
            int counter = 0;
            while (it.hasNext()) {
                counter += it.next().getCounter();
            }
            if (!z) {
                printStream.print(", ");
            }
            printStream.print("[");
            if (counter > 0) {
                printStream.println("{ ");
                printStream.println("  \"class\": \"" + key.getNativeClass() + "\",");
                printStream.println("  \"total\": " + counter + ", ");
                printStream.print("  \"functions\": {");
                boolean z2 = true;
                for (NativeFunction nativeFunction : value) {
                    if (nativeFunction.getCounter() > 0) {
                        if (!z2) {
                            printStream.print(",");
                        }
                        printStream.println();
                        printStream.print("    \"" + nativeFunction.getName() + "\": " + nativeFunction.getCounter());
                        z2 = false;
                    }
                }
                printStream.println();
                printStream.println("  }");
                printStream.print("}");
            }
            printStream.print("]");
            z = false;
        }
    }

    private static HashMap<StatsInterface, ArrayList<NativeFunction>> snapshot(Collection<StatsInterface> collection) {
        HashMap<StatsInterface, ArrayList<NativeFunction>> map = new HashMap<>();
        for (StatsInterface statsInterface : collection) {
            int iFunctionCount = statsInterface.functionCount();
            ArrayList<NativeFunction> arrayList = new ArrayList<>(iFunctionCount);
            for (int i = 0; i < iFunctionCount; i++) {
                arrayList.add(new NativeFunction(i, statsInterface.functionName(i), 0));
            }
            Collections.sort(arrayList);
            map.put(statsInterface, arrayList);
        }
        return map;
    }
}
