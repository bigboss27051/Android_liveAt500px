package bigboss27051.liveat500px.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a2g01 on 14/05/2559.
 */
public class PhotoItemDao implements Parcelable{
    @SerializedName("id")          private int id;
    @SerializedName("like")        private String Like;
    @SerializedName("image_url")   private String ImgUrl;
    @SerializedName("caption")     private String caption;
    @SerializedName("user_id")     private String userId;
    @SerializedName("username")    private String username;
    @SerializedName("tags")        private List<String> tags = new ArrayList<String>();
    @SerializedName("created_time")   private String createdTime;
    @SerializedName("camera")      private String camera;
    @SerializedName("lens")        private String lens;
    @SerializedName("focal_length")   private String focalLength;
    @SerializedName("iso")         private String iso;
    @SerializedName("shutter_speed")   private String shutterSpeed;
    @SerializedName("aperture")    private String aperture;

    public PhotoItemDao(){

    }

    protected PhotoItemDao(Parcel in) {
        id = in.readInt();
        Like = in.readString();
        ImgUrl = in.readString();
        caption = in.readString();
        userId = in.readString();
        username = in.readString();
        tags = in.createStringArrayList();
        createdTime = in.readString();
        camera = in.readString();
        lens = in.readString();
        focalLength = in.readString();
        iso = in.readString();
        shutterSpeed = in.readString();
        aperture = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Like);
        dest.writeString(ImgUrl);
        dest.writeString(caption);
        dest.writeString(userId);
        dest.writeString(username);
        dest.writeStringList(tags);
        dest.writeString(createdTime);
        dest.writeString(camera);
        dest.writeString(lens);
        dest.writeString(focalLength);
        dest.writeString(iso);
        dest.writeString(shutterSpeed);
        dest.writeString(aperture);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhotoItemDao> CREATOR = new Creator<PhotoItemDao>() {
        @Override
        public PhotoItemDao createFromParcel(Parcel in) {
            return new PhotoItemDao(in);
        }

        @Override
        public PhotoItemDao[] newArray(int size) {
            return new PhotoItemDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLike() {
        return Like;
    }

    public void setLike(String like) {
        Like = like;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }
}
