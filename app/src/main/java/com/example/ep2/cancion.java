package com.example.ep2;

public class cancion {

    public String Tittle;
    public int Image;
    public int ResourceId;

    public cancion(int Image, String Tittle, int ResourceId ) {
        this.Image = Image;
        this.Tittle = Tittle;
        this.ResourceId = ResourceId;

    }

    public String getTittle() {
        return Tittle;
    }

    public void setTittle(String Tittle) {
        this.Tittle = Tittle;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int Image) {
        this.Image = Image;
    }

    public int getResourceId() {
        return ResourceId;
    }

    public void setResourceId(int ResourceId) {
        this.ResourceId = ResourceId;
    }
}
