package org.izv.ad.jmunoz.footballdb.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "club", indices ={@Index(value = "name" , unique = true)})
public class Club implements Parcelable {

    @PrimaryKey
    public long id;

    @NonNull
    @ColumnInfo(name="url")
    public String url;

    @NonNull
    @ColumnInfo(name="name")
    public String name;

    @NonNull
    @ColumnInfo(name="star")
    public int star;

    @NonNull
    @ColumnInfo(name="description")
    public String description;

    public Club(Parcel in) {
        id = in.readLong();
        url = in.readString();
        name = in.readString();
        star = in.readInt();
        description = in.readString();
    }

    public Club(){

    }

    public static final Creator<Club> CREATOR = new Creator<Club>() {
        @Override
        public Club createFromParcel(Parcel in) {
            return new Club(in);
        }

        @Override
        public Club[] newArray(int size) {
            return new Club[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Club{" +
                "name='" + name + '\'' +
                ", star=" + star +
                ", description=" + description +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeInt(star);
        dest.writeString(description);
    }
}
