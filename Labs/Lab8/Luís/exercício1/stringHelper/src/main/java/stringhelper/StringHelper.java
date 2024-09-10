package stringhelper;

public class StringHelper {

  public String reverseLastTwoChars(String str) {

    if (str == null || str.length() < 2) {
      return str;
    }

    char[] cont = str.toCharArray();
    char t;
    t = cont[cont.length - 1];
    cont[cont.length - 1] = cont[cont.length - 2];
    cont[cont.length - 2] = t;


    return new String(cont);
    
  }
}
