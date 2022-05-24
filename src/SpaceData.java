import java.util.Date;
public class SpaceData {
    private String imgUrl;
    private String title;
    private Date date;
    private String explanation;

    public SpaceData(String imgUrl, String title, Date date, String explanation) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.date = date;
        this.explanation = explanation;
    }

    public String getImgUrl() { return imgUrl; }
    public String getTitle() { return title; }
    public Date getData() { return date; }
    public String getExplanation() { return explanation; }
}
