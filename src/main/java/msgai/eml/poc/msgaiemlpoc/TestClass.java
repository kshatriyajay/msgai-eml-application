package msgai.eml.poc.msgaiemlpoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestClass {
    public static void main(String[] args) {
        String line = "some user has entered the following information about the site \n" +
                "his username is abcdes and password is qwe212@12\n" +
                "for the different user the username : opwoe and password for that user is 208@11is\n" +
                "password - 02esa!1 username - asdbieb\n" +
                "username for usffffdsr is qsqdqd \n" +
                "\n" +
                "password is 123@123 and username for user is 12djfuh and password is msinu@123";

        String username = "(?=\\S*([u,U][s,S][e,E][r,R][n,N][a,A][m,M][e,E]((\\s|\\w+)(?!([p,P][a,A][s,S][s,S][w,W][o,O][r,R][d,D]))){1,7}(is|(\\-|\\:))(\\s*)([\\S]+)))";
        String password = "(?=\\S*([p,P][a,A][s,S][s,S][w,W][o,O][r,R][d,D]((\\s|\\w+)(?!([u,U][s,S][e,E][r,R][n,N][a,A][m,M][e,E]))){1,7}(is|(\\-|\\:))(\\s*)([\\S]+)))";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(username);
        Pattern pattern1 = Pattern.compile(password);
        Matcher matcher = pattern.matcher(line);
        Matcher matcher1 = pattern1.matcher(line);

        while (matcher.find()) {
            System.out.println("group 1: " + matcher.group(1));
            System.out.println("group 8: " + matcher.group(8));
        }

        while (matcher1.find()) {
            System.out.println("group 1: " + matcher1.group(1));
            System.out.println("group 8: " + matcher1.group(8));
        }
    }
}
