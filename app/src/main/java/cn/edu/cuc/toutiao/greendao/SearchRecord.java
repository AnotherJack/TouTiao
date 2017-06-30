package cn.edu.cuc.toutiao.greendao;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhengj on 2017/6/30.
 */

@Entity
public class SearchRecord implements SearchSuggestion{
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String text;
    public SearchRecord(String text) {
        this.text = text;
    }
    public SearchRecord(Parcel source){
        this.text = source.readString();
        this.id = source.readLong();
    }
    @Generated(hash = 226505579)
    public SearchRecord(Long id, String text) {
        this.id = id;
        this.text = text;
    }
    @Generated(hash = 839789598)
    public SearchRecord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getBody() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeLong(id);
    }

    public static final Creator<SearchRecord> CREATOR = new Creator<SearchRecord>() {
        @Override
        public SearchRecord createFromParcel(Parcel in) {
            return new SearchRecord(in);
        }

        @Override
        public SearchRecord[] newArray(int size) {
            return new SearchRecord[size];
        }
    };
}
