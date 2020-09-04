package com.example.museepourtous;

public class Player {

    public String UUID;
    public String Title;
    public String Description;
    public String ImageURL;
      public String SoundURL;

public Player(){

}

    public Player (String UUID, String Title,String Description,String ImageURL, String SoundURL)
    {
        this.UUID = UUID;
        this.Title = Title;
        this.Description = Description;
        this.ImageURL = ImageURL;
        this.SoundURL = SoundURL;

    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getSoundURL() {
        return SoundURL;
    }

    public void setSoundURL(String soundURL) {
        SoundURL = soundURL;
    }
}
