package org.catrobat.catroid.embroidery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTPatternManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTPatternManager implements EmbroideryPatternManager {
    private TreeMap<Integer, EmbroideryWorkSpace> layerWorkspaceMap = new TreeMap<>();
    private TreeMap<Integer, EmbroideryStream> layerStreamMap = new TreeMap<>();
    private HashMap<Sprite, StitchCommand> lastCommandOfSpriteMap = new HashMap<>();

    @Override // org.catrobat.catroid.embroidery.EmbroideryPatternManager
    public void addStitchCommand(StitchCommand stitchCommand) {
        if (!this.layerWorkspaceMap.containsKey(Integer.valueOf(stitchCommand.getLayer()))) {
            this.layerWorkspaceMap.put(Integer.valueOf(stitchCommand.getLayer()), new DSTWorkSpace());
            this.layerStreamMap.put(Integer.valueOf(stitchCommand.getLayer()), new DSTStream(new DSTHeader()));
        }
        stitchCommand.act(this.layerWorkspaceMap.get(Integer.valueOf(stitchCommand.getLayer())), this.layerStreamMap.get(Integer.valueOf(stitchCommand.getLayer())), this.lastCommandOfSpriteMap.get(stitchCommand.getSprite()));
        this.lastCommandOfSpriteMap.put(stitchCommand.getSprite(), stitchCommand);
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryPatternManager
    public ArrayList<StitchPoint> getEmbroideryPatternList() {
        return getEmbroideryStream().getPointList();
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryPatternManager
    public EmbroideryStream getEmbroideryStream() {
        DSTStream dSTStream = new DSTStream(new DSTHeader());
        if (this.layerStreamMap.isEmpty()) {
            return dSTStream;
        }
        Iterator<Map.Entry<Integer, EmbroideryStream>> it = this.layerStreamMap.entrySet().iterator();
        while (it.hasNext()) {
            ArrayList<StitchPoint> pointList = it.next().getValue().getPointList();
            dSTStream.addAllStitchPoints(pointList);
            if (it.hasNext() && !pointList.isEmpty()) {
                StitchPoint stitchPoint = pointList.get(pointList.size() - 1);
                dSTStream.addColorChange();
                dSTStream.addStitchPoint(stitchPoint.getX(), stitchPoint.getY(), stitchPoint.getColor());
            }
        }
        return dSTStream;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryPatternManager
    public boolean validPatternExists() {
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<Integer, EmbroideryStream>> it = this.layerStreamMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getValue().getPointList());
        }
        return arrayList.size() > 1;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryPatternManager
    public void clear() {
        this.layerWorkspaceMap.clear();
        this.layerStreamMap.clear();
        this.lastCommandOfSpriteMap.clear();
    }
}
