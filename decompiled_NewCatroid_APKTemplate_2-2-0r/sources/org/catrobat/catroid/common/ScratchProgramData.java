package org.catrobat.catroid.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ScratchProgramData.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public class ScratchProgramData implements Serializable, Parcelable {
    public static final Parcelable.Creator<ScratchProgramData> CREATOR = new Parcelable.Creator<ScratchProgramData>() { // from class: org.catrobat.catroid.common.ScratchProgramData.1
        @Override // android.os.Parcelable.Creator
        public ScratchProgramData createFromParcel(Parcel source) {
            return new ScratchProgramData(source);
        }

        @Override // android.os.Parcelable.Creator
        public ScratchProgramData[] newArray(int size) {
            return new ScratchProgramData[size];
        }
    };
    private static final long serialVersionUID = 1;
    private Date createdDate;
    private int favorites;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private long f516id;
    private WebImage image;
    private String instructions;
    private int loves;
    private Date modifiedDate;
    private String notesAndCredits;
    private String owner;
    private List<ScratchProgramData> remixes;
    private Date sharedDate;
    private List<String> tags;
    private String title;
    private int views;
    private ScratchVisibilityState visibilityState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ScratchProgramData(long id2, String title, String owner, WebImage image) {
        this.remixes = new ArrayList();
        this.f516id = id2;
        this.title = title;
        this.owner = owner;
        this.image = image;
        this.instructions = null;
        this.notesAndCredits = null;
        this.views = 0;
        this.favorites = 0;
        this.loves = 0;
        this.createdDate = null;
        this.modifiedDate = null;
        this.sharedDate = null;
        this.tags = new ArrayList();
        this.visibilityState = null;
        this.remixes = new ArrayList();
    }

    private ScratchProgramData(Parcel in) {
        this.remixes = new ArrayList();
        this.f516id = in.readLong();
        this.title = in.readString();
        this.owner = in.readString();
        this.image = (WebImage) in.readParcelable(WebImage.class.getClassLoader());
        this.instructions = in.readString();
        this.notesAndCredits = in.readString();
        this.views = in.readInt();
        this.favorites = in.readInt();
        this.loves = in.readInt();
        long j = in.readLong();
        this.createdDate = j == 0 ? null : new Date(j);
        long j2 = in.readLong();
        this.modifiedDate = j2 == 0 ? null : new Date(j2);
        long j3 = in.readLong();
        this.sharedDate = j3 != 0 ? new Date(j3) : null;
        ArrayList arrayList = new ArrayList();
        this.tags = arrayList;
        in.readStringList(arrayList);
        this.visibilityState = (ScratchVisibilityState) in.readParcelable(ScratchVisibilityState.class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        this.remixes = arrayList2;
        in.readTypedList(arrayList2, CREATOR);
    }

    public long getId() {
        return this.f516id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOwner() {
        return this.owner;
    }

    public WebImage getImage() {
        return this.image;
    }

    public void setImage(WebImage image) {
        this.image = image;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getNotesAndCredits() {
        return this.notesAndCredits;
    }

    public void setNotesAndCredits(String notesAndCredits) {
        this.notesAndCredits = notesAndCredits;
    }

    public void addRemixProgram(ScratchProgramData remixProgramData) {
        this.remixes.add(remixProgramData);
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getFavorites() {
        return this.favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getLoves() {
        return this.loves;
    }

    public void setLoves(int loves) {
        this.loves = loves;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getSharedDate() {
        return this.sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void addTag(String tagName) {
        this.tags.add(tagName);
    }

    public ScratchVisibilityState getVisibilityState() {
        return this.visibilityState;
    }

    public void setVisibilityState(ScratchVisibilityState visibilityState) {
        this.visibilityState = visibilityState;
    }

    public List<ScratchProgramData> getRemixes() {
        return this.remixes;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.f516id);
        dest.writeString(this.title);
        dest.writeString(this.owner);
        dest.writeParcelable(this.image, flags);
        dest.writeString(this.instructions);
        dest.writeString(this.notesAndCredits);
        dest.writeInt(this.views);
        dest.writeInt(this.favorites);
        dest.writeInt(this.loves);
        Date date = this.createdDate;
        dest.writeLong(date != null ? date.getTime() : 0L);
        Date date2 = this.modifiedDate;
        dest.writeLong(date2 != null ? date2.getTime() : 0L);
        Date date3 = this.sharedDate;
        dest.writeLong(date3 != null ? date3.getTime() : 0L);
        dest.writeStringList(this.tags);
        dest.writeParcelable(this.visibilityState, flags);
        dest.writeTypedList(this.remixes);
    }
}
