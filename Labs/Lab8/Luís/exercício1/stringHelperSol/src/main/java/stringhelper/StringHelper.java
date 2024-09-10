package stringhelper;

public class StringHelper {

  public String reverseLastTwoChars(String str) {
    if (str == null || str.length() <= 1)
      return str;

    char[] content = str.toCharArray();
    char t = content[str.length() - 1];
    content[str.length() - 1] = content[str.length() - 2];
    content[str.length() - 2] = t;

    return new String(content);
  }
}
