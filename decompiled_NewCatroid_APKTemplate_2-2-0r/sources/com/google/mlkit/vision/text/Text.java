package com.google.mlkit.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.internal.mlkit_vision_text.zzbu;
import com.google.android.gms.internal.mlkit_vision_text.zzlk;
import com.google.android.gms.internal.mlkit_vision_text.zzlm;
import com.google.android.gms.internal.mlkit_vision_text.zzlo;
import com.google.android.gms.internal.mlkit_vision_text.zzlq;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public class Text {
    private final List<TextBlock> zza;
    private final String zzb;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
    public static class TextBase {
        private final String zza;
        private final Rect zzb;
        private final Point[] zzc;
        private final String zzd;

        TextBase(String str, Rect rect, List<Point> list, String str2) {
            this.zza = str;
            this.zzb = rect;
            this.zzc = (Point[]) list.toArray(new Point[0]);
            this.zzd = str2;
        }

        public Rect getBoundingBox() {
            return this.zzb;
        }

        public Point[] getCornerPoints() {
            return this.zzc;
        }

        public String getRecognizedLanguage() {
            return this.zzd;
        }

        protected final String zza() {
            String str = this.zza;
            return str == null ? "" : str;
        }
    }

    public Text(zzlq zzlqVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        this.zzb = zzlqVar.zza();
        arrayList.addAll(zzbu.zza(zzlqVar.zzb(), zza.zza));
    }

    public String getText() {
        return this.zzb;
    }

    public List<TextBlock> getTextBlocks() {
        return Collections.unmodifiableList(this.zza);
    }

    public Text(String str, List<TextBlock> list) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        arrayList.addAll(list);
        this.zzb = str;
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
    public static class Element extends TextBase {
        Element(zzlm zzlmVar) {
            super(zzlmVar.zza(), zzlmVar.zzb(), zzlmVar.zzc(), zzlmVar.zzd());
        }

        public String getText() {
            return zza();
        }

        public Element(String str, Rect rect, List<Point> list, String str2) {
            super(str, rect, list, str2);
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
    public static class Line extends TextBase {
        private final List<Element> zza;

        Line(zzlo zzloVar) {
            super(zzloVar.zza(), zzloVar.zzb(), zzloVar.zzc(), zzloVar.zzd());
            this.zza = zzbu.zza(zzloVar.zze(), zzb.zza);
        }

        public synchronized List<Element> getElements() {
            return this.zza;
        }

        public String getText() {
            return zza();
        }

        public Line(String str, Rect rect, List<Point> list, String str2, List<Element> list2) {
            super(str, rect, list, str2);
            this.zza = list2;
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
    public static class TextBlock extends TextBase {
        private final List<Line> zza;

        TextBlock(zzlk zzlkVar) {
            super(zzlkVar.zza(), zzlkVar.zzb(), zzlkVar.zzc(), zzlkVar.zzd());
            this.zza = zzbu.zza(zzlkVar.zze(), zzc.zza);
        }

        public synchronized List<Line> getLines() {
            return this.zza;
        }

        public String getText() {
            return zza();
        }

        public TextBlock(String str, Rect rect, List<Point> list, String str2, List<Line> list2) {
            super(str, rect, list, str2);
            this.zza = list2;
        }
    }
}
