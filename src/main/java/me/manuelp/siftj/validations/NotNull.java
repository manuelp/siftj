package me.manuelp.siftj.validations;

public class NotNull {
  public static void check(Object... objs) {
    for (Object obj : objs) {
      if (obj == null)
        throw new RuntimeException("Invalid data!");
    }
  }
}
