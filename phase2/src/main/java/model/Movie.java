package model;

public class Movie {
    private int id;
    private String name;
    private int directorId;

    public Movie(){
    }

    public Movie(int id, String name, int directorId){
        this.id = id;
        this.name = name;
        this.directorId = directorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }
}
