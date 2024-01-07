
package com.example.fashion.Domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationDomain {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time_created")
    @Expose
    private String timeCreated;
    @SerializedName("is_readed")
    @Expose
    private Boolean isReaded;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimeCreated() {
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(timeCreated, formatter);
            // Extract date and time as strings
            String dateString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        return timeCreated;
    }

    public String getDateCreated() {
        DateTimeFormatter formatter = null;
        LocalDateTime dateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(timeCreated, formatter);
            // Extract date and time as strings
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//             dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Boolean getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(Boolean isReaded) {
        this.isReaded = isReaded;
    }

}
