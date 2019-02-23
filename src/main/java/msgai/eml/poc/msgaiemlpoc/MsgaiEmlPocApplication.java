package msgai.eml.poc.msgaiemlpoc;

import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SpringBootApplication
public class MsgaiEmlPocApplication {

    public static void main(String[] args) {
        // SpringApplication.run(MsgaiEmlPocApplication.class, args);
        String inputFolderPath = args[0];
        String outputFolderPath = args[1];

        System.out.println("Input folder provided: " + inputFolderPath);
        System.out.println("Input folder provided: " + outputFolderPath);
        System.out.println("Export Level will be DAILY and this Application works better with Outlook mail input");

        try {
//            new EMLPOC().parseEMLFile(inputFolderPath, outputFolderPath);
            new EmlMessageMasker().parseEMLFile(inputFolderPath, outputFolderPath);
        } catch (Exception e) {
            System.out.println("Exception occured while running the application");
            e.printStackTrace();
        }
    }


  /* public static void main(String[] args) {
       String string1 =  new MsgaiEmlPocApplication().test(ApplicationConstants.str);
       System.out.println(string1);
    }*/

   /* public String test(String str){
        final String regex = "(?<email>[A-Za-z0-9._%+-]+(\\n)?@[A-Za-z0-9.-|\\n]+(\\n)?\\.[A-Za-z|\\n]{2,4})";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group("email"))) {
                str = str.replace(matcher.group("email"),
                        "<" + "EMAIL" + "- " + Integer.toString(matcher.group("email").hashCode()) + ">");
            }
        }
        return str;
    }*/

}
