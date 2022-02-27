package com.company;

public class Playlist {

    private PodCast allAudios;
    private String audioType;
    private String playlistName;


    public Playlist(PodCast allAudios, String audioType, String playlistName) {

        this.allAudios = allAudios;
        this.audioType= audioType;
        this.playlistName = playlistName;
    }


    public PodCast getAllAudios() {
        return allAudios;
    }

    public void setAllAudios(PodCast allAudios) {
        this.allAudios = allAudios;
    }

    public String getAudioType() {
        return audioType;
    }

    public void setAudioType(String audioType) {
        this.audioType = audioType;
    }
    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

}
