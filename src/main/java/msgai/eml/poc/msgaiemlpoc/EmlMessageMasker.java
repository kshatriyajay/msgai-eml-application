package msgai.eml.poc.msgaiemlpoc;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmlMessageMasker implements ApplicationConstants {

    private static long count = 0L;

    public static String replaceWithPattern(String regex, String str, String replace) {
        Pattern ptn = Pattern.compile(regex);
        Matcher mtch = ptn.matcher(str);
        return mtch.replaceAll(replace);
    }

    public static boolean isTextContainsPattern(String text, String regex) {
        Matcher mat = Pattern.compile(regex).matcher(text);
        return mat.find();
    }

    public void parseEMLFile(String inputFolderPath, String outputFolderPath) throws Exception {
        Path inputPath = Paths.get(inputFolderPath);
        if (!Files.exists(inputPath) || StringUtils.isEmpty(inputFolderPath)) {
            System.out.println(INPUT_PATH_DOESNT_EXISTS);
            return;
        }
        File folder = new File(inputFolderPath);

        String[] fileNames = folder.list();
        /*if (fileNames.length <= 2) {
            String temp = fileNames[1];
            fileNames = new String[1000];
            for (int i = 0; i < 1000; i++) {
                fileNames[i] = new String(temp);
            }
        }*/
        int filesize = fileNames.length;
//        int batches = (filesize / BATCH_SIZE) + 1;
        String tempFilePath = EMPTY_STRING;

        if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
            tempFilePath = inputFolderPath + "\\";
        } else {
            tempFilePath = inputFolderPath + "/";
        }

       /* for (int i = 1; i <= batches; i++) {
            System.out.println(RUNNING_BATCH + i);
            count = i;
            for (int k = BATCH_SIZE * (i - 1); k < k + BATCH_SIZE && k < filesize; k++) {
                File emlFile = new File(tempFilePath + fileNames[k]);
                if (emlFile != null && emlFile.exists() && emlFile.getName().endsWith(EML_EXTENSION)) {
                    System.out.println(PROCESSING_FILE + emlFile.getAbsolutePath());
                    try {
                        String processedText = processFile(emlFile);
                        writeDataToFile(processedText, emlFile.getName(), outputFolderPath);
                    } catch (Exception e) {
                        writeFileToErrorLocation(emlFile.getAbsolutePath(), ERROR, outputFolderPath);
                        continue;
                    }
                }
            }
        }*/

        for (int k = 0; k < filesize; k++) {
            File emlFile = new File(tempFilePath + fileNames[k]);
            if (emlFile != null && emlFile.exists() && emlFile.getName().endsWith(EML_EXTENSION)) {
                System.out.println(PROCESSING_FILE + emlFile.getAbsolutePath());
                try {
                    String processedText = processFile(emlFile);
                    writeDataToFile(processedText, emlFile.getName(), outputFolderPath);
                } catch (Exception e) {
                    writeFileToErrorLocation(emlFile.getAbsolutePath(), ERROR, outputFolderPath);
                    continue;
                }
            }
        }

    }

    public void writeFileToErrorLocation(String fileName, String type, String outputFolderPath) {
        Path outputPath = Paths.get(outputFolderPath);
        File file;
        File typefile;
        if (!Files.exists(outputPath)) {
            file = new File(outputFolderPath);
            file.mkdir();
            outputFolderPath = file.getAbsolutePath();
        }

        if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
            typefile = new File(outputFolderPath + "\\" + type + "Folder" + "\\");
            typefile.mkdir();
        } else {
            typefile = new File(outputFolderPath + "/" + type + "Folder" + "/");
            typefile.mkdir();
        }

        try {
            File emlfile = new File(fileName);
            Path inputPath = Paths.get(fileName);
            String outputCopyPath = EMPTY_STRING;
            if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
                outputCopyPath = typefile.getAbsolutePath() + "\\" + emlfile.getName();
            } else {
                outputCopyPath = typefile.getAbsolutePath() + "/" + emlfile.getName();
            }

            Path outputPathWrite = Paths.get(outputCopyPath);
            if (!Files.exists(outputPathWrite)) {
                Files.copy(inputPath, outputPathWrite);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

    }

    private String readFile(String path) throws IOException {
       /* Charset encoding = Charset.defaultCharset();
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);*/
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private String processFile(File emlFile) throws IOException {
        String emlTextFile = emlFile.getAbsolutePath();
        String fileText = readFile(emlTextFile);
        if (StringUtils.isEmpty(fileText)) {
            return EMPTY_STRING;
        }
//        String maskedText = maskData(fileText);
        fileText = maskUnwantedContent(fileText);
        String maskedText = maskDataWithHashcode(fileText);
        return maskedText;
    }

    public String maskUnwantedContent(String originalMessage) {
        boolean msgContainsEmailAdd1 = isTextContainsPattern(originalMessage, UNWANTED_CONTENT1);
        if (msgContainsEmailAdd1) {
            originalMessage = replaceWithPattern(UNWANTED_CONTENT1, originalMessage, ".");
        }

        boolean msgContainsEmailAdd2 = isTextContainsPattern(originalMessage, UNWANTED_CONTENT2);
        if (msgContainsEmailAdd2) {
            originalMessage = replaceWithPattern(UNWANTED_CONTENT2, originalMessage, ".");
        }

        boolean msgContainsEmailAdd3 = isTextContainsPattern(originalMessage, UNWANTED_CONTENT3);
        if (msgContainsEmailAdd3) {
            originalMessage = replaceWithPattern(UNWANTED_CONTENT3, originalMessage, "");
        }

        /*boolean msgContainsEmailAdd4 = isTextContainsPattern(originalMessage, "\n");
        if (msgContainsEmailAdd4) {
            originalMessage = replaceWithPattern("\n", originalMessage, "");
        }

        boolean msgContainsEmailAdd5 = isTextContainsPattern(originalMessage, "\r");
        if (msgContainsEmailAdd5) {
            originalMessage = replaceWithPattern("\r", originalMessage, "");
        }

        boolean msgContainsEmailAdd6 = isTextContainsPattern(originalMessage, "\t");
        if (msgContainsEmailAdd6) {
            originalMessage = replaceWithPattern("\t", originalMessage, "");
        }*/
        return originalMessage;
    }

    public List<String> phoneNumberPatternCheck(String msg, String regex) {
        List<String> strList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group(NUMBER_MATCH_GROUP))) {
                strList.add(matcher.group(NUMBER_MATCH_GROUP));
            }
        }
        return strList;
    }

    public String phoneNumberPatternCheck1(String msg, String regex, String type) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group(NUMBER_MATCH_GROUP))) {
                msg = msg.replace(matcher.group(NUMBER_MATCH_GROUP),
                        "<" + type + "- " + Integer.toString(matcher.group(NUMBER_MATCH_GROUP).hashCode()) + ">");
            }
        }
        return msg;
    }

    public List<String> breakEmailPatternCheck(String msg, String regex) {
        List<String> strList = new ArrayList<>();
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(msg);

        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group(EMAIL_MATCH_GROUP))) {
                strList.add(matcher.group(EMAIL_MATCH_GROUP));
            }
        }
        return strList;
    }

    public String breakEmailPatternCheck1(String msg, String regex, String type) {
        String regex1 = "(?<email>[A-Za-z0-9._%+-]+(\\n)?@[A-Za-z0-9.-|\\n]+(\\n)?\\.[A-Za-z|\\n]{2,4})";
        final Pattern pattern = Pattern.compile(regex1, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(msg);

        /*while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group(EMAIL_MATCH_GROUP))) {
                msg = msg.replace(matcher.group(EMAIL_MATCH_GROUP),
                        "<" + type + "- " + Integer.toString(matcher.group(EMAIL_MATCH_GROUP).hashCode()) + ">");
            }
        }*/
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group())) {
                msg = msg.replace(matcher.group(),
                        "<" + type + "- " + Integer.toString(matcher.group().hashCode()) + ">");
            }
        }
        return msg;
    }

    public String replacePatternCheck(String msg, String regex, String type) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group())) {
                msg = msg.replace(matcher.group(), "<" + type + "- " + Integer.toString(matcher.group().hashCode()) + ">");
            }
        }
        return msg;
    }

    public String replaceNumberMatchedList(String msg, List<String> strList, String type) {
        try {
            for (String str : strList) {
                msg = msg.replace(str, "<" + type + "- " + Integer.toString(str.hashCode()) + ">");
            }
        } catch (Exception e) {
            return msg;
        }
        return msg;
    }

    public String maskData(String originalMessage) {
        if (StringUtils.isEmpty(originalMessage))
            return EMPTY_STRING;
        //Email check
        boolean msgContainsEmailAdd = isTextContainsPattern(originalMessage, EMAIL_REGEX);
        if (msgContainsEmailAdd) {
            originalMessage = replaceWithPattern(EMAIL_REGEX, originalMessage, EMAIL_REPLACE_STRING);
        }

        List<String> msgContainsPhoneNumberList = phoneNumberPatternCheck(originalMessage, NUMBER_REGEX_FOURTH);
        if (!CollectionUtils.isEmpty(msgContainsPhoneNumberList)) {
            originalMessage = replaceNumberMatchedList(originalMessage, msgContainsPhoneNumberList, NUMBER_REPLACE_STRING);
        }
        /*//Number check
        boolean msgContainsDigits = isTextContainsPattern(originalMessage, NUMBER_REGEX_UPDATED);
        if (msgContainsDigits) {
            originalMessage = replaceWithPattern(NUMBER_REGEX_UPDATED, originalMessage, NUMBER_REPLACE_STRING);
        }*/

        //IPv4 check
        boolean ipv4Contains = isTextContainsPattern(originalMessage, IPV4_PATTERN);
        if (ipv4Contains) {
            originalMessage = replaceWithPattern(IPV4_PATTERN, originalMessage, IP_REPLACE_STRING);
        }

        //IPv6 check
        boolean ipv6Contains = isTextContainsPattern(originalMessage, IPV6_STD_PATTERN);
        if (ipv6Contains) {
            originalMessage = replaceWithPattern(IPV6_STD_PATTERN, originalMessage, IP_REPLACE_STRING);
        }

        //IPv6Hex check
        boolean ipv6HexContains = isTextContainsPattern(originalMessage, IPV6_HEX_COMPRESSED_PATTERN);
        if (ipv6HexContains) {
            originalMessage = replaceWithPattern(IPV6_HEX_COMPRESSED_PATTERN, originalMessage, IP_REPLACE_STRING);
        }

        //Username check
        List<String> msgContainsUsernameList = usernamePwdPatternCheck(originalMessage, USERNAME_UPDATED_REGEX);
        if (!CollectionUtils.isEmpty(msgContainsUsernameList)) {
            originalMessage = replaceMatchedList(originalMessage, msgContainsUsernameList, USERNAME_REPLACE_STRING);
        }

        //Password check
        List<String> msgContainsPwdList = usernamePwdPatternCheck(originalMessage, PASSWORD_UPDATED_REGEX);
        if (!CollectionUtils.isEmpty(msgContainsPwdList)) {
            originalMessage = replaceMatchedList(originalMessage, msgContainsPwdList, PASSWORD_REPLACE_STRING);
        }

        return originalMessage;
    }

    public String maskDataWithHashcode(String originalMessage) {
        if (StringUtils.isEmpty(originalMessage))
            return EMPTY_STRING;
        //Number check
        originalMessage = phoneNumberPatternCheck1(originalMessage, NUMBER_REGEX_FOURTH, "NUMBER");


        //Break email check
        originalMessage = breakEmailPatternCheck1(originalMessage, EMAIL_GROUP_REGEX, "EMAIL");

        //Email check
        originalMessage = replacePatternCheck(originalMessage, EMAIL_REGEX, "EMAIL");


        //IPv4 check
        originalMessage = replacePatternCheck(originalMessage, IPV4_PATTERN, "IP");


        //IPv6 check
        originalMessage = replacePatternCheck(originalMessage, IPV6_STD_PATTERN, "IP");


        //IPv6Hex check
        originalMessage = replacePatternCheck(originalMessage, IPV6_HEX_COMPRESSED_PATTERN, "IP");


        //Username check
        originalMessage = usernamePwdPatternCheck1(originalMessage, USERNAME_UPDATED_REGEX, "USERNAME");


        //Password check
        originalMessage = usernamePwdPatternCheck1(originalMessage, PASSWORD_UPDATED_REGEX, "PASSWORD");


        return originalMessage;
    }

    /*public String maskDataWithHashcode(String originalMessage) {
        if (StringUtils.isEmpty(originalMessage))
            return EMPTY_STRING;
        //Number check
        List<String> msgContainsPhoneNumberList = phoneNumberPatternCheck(originalMessage, NUMBER_REGEX_FOURTH);
        if (!CollectionUtils.isEmpty(msgContainsPhoneNumberList)) {
            originalMessage = replaceNumberMatchedList(originalMessage, msgContainsPhoneNumberList, "NUMBER");
        }

        //Break email check
        List<String> msgContainsBreakEmailList = breakEmailPatternCheck(originalMessage, EMAIL_GROUP_REGEX);
        if (!CollectionUtils.isEmpty(msgContainsBreakEmailList)) {
            originalMessage = replaceNumberMatchedList(originalMessage, msgContainsBreakEmailList, "EMAIL");
        }

        //Email check
        boolean msgContainsEmailAdd = isTextContainsPattern(originalMessage, EMAIL_REGEX);
        if (msgContainsEmailAdd) {
            originalMessage = replacePatternCheck(originalMessage, EMAIL_REGEX, "EMAIL");
        }

        //IPv4 check
        boolean ipv4Contains = isTextContainsPattern(originalMessage, IPV4_PATTERN);
        if (ipv4Contains) {
            originalMessage = replacePatternCheck(originalMessage, IPV4_PATTERN, "IP");
        }

        //IPv6 check
        boolean ipv6Contains = isTextContainsPattern(originalMessage, IPV6_STD_PATTERN);
        if (ipv6Contains) {
            originalMessage = replacePatternCheck(originalMessage, IPV6_STD_PATTERN, "IP");
        }

        //IPv6Hex check
        boolean ipv6HexContains = isTextContainsPattern(originalMessage, IPV6_HEX_COMPRESSED_PATTERN);
        if (ipv6HexContains) {
            originalMessage = replacePatternCheck(originalMessage, IPV6_HEX_COMPRESSED_PATTERN, "IP");
        }

        //Username check
        List<String> msgContainsUsernameList = usernamePwdPatternCheck(originalMessage, USERNAME_UPDATED_REGEX);
        if (!CollectionUtils.isEmpty(msgContainsUsernameList)) {
            originalMessage = replaceMatchedList(originalMessage, msgContainsUsernameList, "USERNAME");
        }

        //Password check
        List<String> msgContainsPwdList = usernamePwdPatternCheck(originalMessage, PASSWORD_UPDATED_REGEX);
        if (!CollectionUtils.isEmpty(msgContainsPwdList)) {
            originalMessage = replaceMatchedList(originalMessage, msgContainsPwdList, "PASSWORD");
        }

        return originalMessage;
    }*/

    public List<String> usernamePwdPatternCheck(String msg, String regex) {
        List<String> strList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group(8))) {
                strList.add(matcher.group(8));
            }
        }
        return strList;
    }

    public String usernamePwdPatternCheck1(String msg, String regex, String type) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group(8))) {
                msg = msg.replace(matcher.group(8), "<" + type + "- " + Integer.toString(matcher.group(8).hashCode()) + ">");
            }
        }
        return msg;
    }

    public String replaceMatchedList(String msg, List<String> strList, String type) {
        try {
            for (String str : strList) {
                msg = msg.replaceAll(str, "<" + type + "- " + Integer.toString(str.hashCode()) + ">");
            }
        } catch (Exception e) {
            return msg;
        }
        return msg;
    }

    private void writeDataToFile(String processedText, String fileName, String outputFolderPath) throws Exception {
        Path outputPath = Paths.get(outputFolderPath);
        File file;
        if (!Files.exists(outputPath)) {
            file = new File(outputFolderPath);
            file.mkdir();
            outputFolderPath = file.getAbsolutePath();
        }

        String finalPath = EMPTY_STRING;
        if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
            finalPath = outputFolderPath + WINDOWS_OS_FILE_PATH + fileName + FILE_EXTENSION_TXT;
        } else {
            finalPath = outputFolderPath + MAC_LINUX_OS_FILE_PATH + fileName + FILE_EXTENSION_TXT;
        }

        System.out.println("Creating file: " + finalPath);
        writeData(finalPath, processedText);
    }

    private void writeData(String path, String processedText) throws IOException {
        RandomAccessFile stream = new RandomAccessFile(path, READ_WRITE_MODE);
        FileChannel channel = stream.getChannel();
        byte[] strBytes = processedText.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
        buffer.put(strBytes);
        buffer.flip();
        channel.write(buffer);
        stream.close();
        channel.close();

    }

}
