package com.tom.breathingspace.model;

public class Level {
  private static final int MAX_LEVEL = 100;

  public static int getMaxLevel() {
    return MAX_LEVEL;
  }

  public static long getXpInLevel(final int level) {
    return (level < MAX_LEVEL ? level * (100 + level * level) : 0);
  }

  public static String getLevelTagline(final int level) {
    String levelTagline = "";
    if (level <= 10) {
      levelTagline = "Beginner";
    } else if (level > 10 && level <= 20) {
      levelTagline = "Novice";
    } else if (level > 20 && level <= 30) {
      levelTagline = "Intermediate";
    } else if (level > 30 && level <= 40) {
      levelTagline = "Advanced";
    } else if (level > 40 && level <= 50) {
      levelTagline = "Expert";
    } else if (level > 50 && level <= 60) {
      levelTagline = "General";
    } else if (level > 60 && level <= 70) {
      levelTagline = "Captain";
    } else if (level > 70 && level <= 80) {
      levelTagline = "Major";
    } else if (level > 80 && level <= 90) {
      levelTagline = "Veteran";
    } else if (level > 90 && level < 100) {
      levelTagline = "Hero";
    } else if (level == 100) {
      levelTagline = "God";
    }
    return levelTagline;
  }
}
