package me.khuirulhuda.glaciervotes.utils;

import me.khuirulhuda.glaciervotes.Main;

import java.io.IOException;

import org.bukkit.Bukkit;


public class CheckAssistant {
  
  private Player player;
  private int timestamp;
  private String comment;
  
  public void logPlayer(Player player) {
    this.player = player;
  }
  
  public void addComment(String comment) {
    this.comment = comment;
  }
  
  public String getComment() {
    //TO DO
  }
  
}