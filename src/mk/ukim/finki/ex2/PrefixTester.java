package mk.ukim.finki.ex2;

public class PrefixTester {

    public static boolean isPrefix (String str1, String str2){
        if (str1.length()>str2.length()){
            return false;
        }

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i)!=str2.charAt(i)){
                return false;
            }
        }

        return true;
    }
    public static void main(String[] args) {
        String str1 = "Napr";
        String str2 = "Napredno programiranje";

        System.out.println(str2.startsWith(str1));
    }
}
