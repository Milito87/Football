package org.izv.ad.jmunoz.footballdb.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName="player",
        foreignKeys = {@ForeignKey(entity = Club.class, parentColumns = "id", childColumns = "idclub", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"name"}, unique = true)})
public class Player implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    @ColumnInfo(name="url")
    public String url;

    @NonNull
    @ColumnInfo(name="name")
    public String name;

    @NonNull
    @ColumnInfo(name="idclub")
    public long club;

    @NonNull
    @ColumnInfo(name="position")
    public String position;

    @NonNull
    @ColumnInfo(name="star")
    public int star;

    @NonNull
    @ColumnInfo(name="date")
    public String date;

    public Player(Parcel in) {
        id = in.readLong();
        url = in.readString();
        name = in.readString();
        club = in.readLong();
        position = in.readString();
        star = in.readInt();
        date = in.readString();
    }

    public Player(){

    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
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

    public long getClub() {
        return club;
    }

    public void setClub(long club) {
        this.club = club;
    }

    @NonNull
    public String getPosition() {
        return position;
    }

    public void setPosition(@NonNull String position) {
        this.position = position;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", club=" + club +
                ", position='" + position + '\'' +
                ", star=" + star +
                ", date='" + date + '\'' +
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
        dest.writeLong(club);
        dest.writeString(position);
        dest.writeInt(star);
        dest.writeString(date);
    }
}
