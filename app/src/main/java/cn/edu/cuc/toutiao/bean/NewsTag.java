package cn.edu.cuc.toutiao.bean;

/**
 * Created by zhengj on 2017/6/27.
 */

public class NewsTag {
    private String type;
    private String title;

    public NewsTag(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;

    }
}
