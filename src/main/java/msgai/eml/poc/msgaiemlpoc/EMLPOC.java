package msgai.eml.poc.msgaiemlpoc;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EMLPOC implements ApplicationConstants {

    private static Map<String, List<MimeMessageParsorExtended>> dateMailMap = new HashMap<>();
    private static Map<String, List<String>> dateFileMap = new HashMap<>();
    private static String mapDateKey = "";
    private static long count = 0L;
    private static List<String> failedFiles = new ArrayList<>();
    Workbook failed_Workbook = new XSSFWorkbook();
    Sheet failed_Sheet = failed_Workbook.createSheet(FAILED_SHEET_NAME);


    public static boolean isTextContainsPattern(String text, String regex) {
        Matcher mat = Pattern.compile(regex).matcher(text);
        return mat.find();
    }

    public static boolean isTextContainsPatternSet(String text, String[] regex) {
        return isTextContainsPattern(text, String.join("|", regex));
    }

    public static String replaceWithPattern(String regex, String str, String replace) {
        Pattern ptn = Pattern.compile(regex);
        Matcher mtch = ptn.matcher(str);
        return mtch.replaceAll(replace);
    }

    public void createHeader(Workbook workbook, Sheet sheet) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(false);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < COLUMNS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(COLUMNS[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public void createFailedHeader(Workbook workbook, Sheet sheet) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(false);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < FAILED_COLUMNS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(FAILED_COLUMNS[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void writeDataToFile(String dateKey, List<MimeMessageParsorExtended> mimeMessageParsers, String inputFolderPath, String outputFolderPath, MimeMessage message) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(SHEET_NAME);

        createHeader(workbook, sheet);
        createFailedHeader(failed_Workbook, failed_Sheet);

        writeToBook(mimeMessageParsers, sheet, failed_Sheet, failed_Workbook, message, inputFolderPath, outputFolderPath);

        Path outputPath = Paths.get(outputFolderPath);
        File file;
        if (!Files.exists(outputPath)) {
            file = new File(outputFolderPath);
            file.mkdir();
            outputFolderPath = file.getAbsolutePath();
        }

        String finalPath = EMPTY_STRING;

        if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
            finalPath = outputFolderPath + WINDOWS_OS_FILE_PATH + dateKey + FILE_EXTENSION;
        } else {
            finalPath = outputFolderPath + MAC_LINUX_OS_FILE_PATH + dateKey + FILE_EXTENSION;
        }

        System.out.println("Creating file: " + finalPath);
        try (FileOutputStream outputStream = new FileOutputStream(new File(finalPath))) {
            workbook.write(outputStream);
        }

        writeFileToLocation(failedFiles, "ERROR", inputFolderPath, outputFolderPath, failed_Workbook);
    }

    public void processMainMail(MimeMessageParsorExtended mimeMessageParser, Sheet sheet, int threadId, int rowNum, String rootBody) throws Exception {
        int cellNumber = 0;

        Row row = sheet.createRow(rowNum);

        //fileName
        Cell cell = row.createCell(cellNumber++);
        cell.setCellValue(mimeMessageParser.getFileName());

        //Thread Id
        cell = row.createCell(cellNumber++);
        cell.setCellValue(threadId);

        //Thread Topic
        cell = row.createCell(cellNumber++);
        cell.setCellValue(mimeMessageParser.getMimeMessage().getHeader(THREAD_TOPIC) != null
                && mimeMessageParser.getMimeMessage().getHeader(THREAD_TOPIC).length > 0
                ? maskData(mimeMessageParser.getMimeMessage().getHeader(THREAD_TOPIC)[0]) : EMPTY_STRING);

        //MessageId
        cell = row.createCell(cellNumber++);
        cell.setCellValue(mimeMessageParser.getMimeMessage().getHeader(MESSAGE_ID)[0]);

        //TimeStamp
        cell = row.createCell(cellNumber++);
        cell.setCellValue(mimeMessageParser.getMimeMessage().getHeader(DATE)[0]);

        //Subject
        cell = row.createCell(cellNumber++);
        cell.setCellValue(maskData(mimeMessageParser.getSubject()));

        //From
        cell = row.createCell(cellNumber++);
        cell.setCellValue(maskData(mimeMessageParser.getFrom()));

        //To
        cell = row.createCell(cellNumber++);
        cell.setCellValue(maskData(mimeMessageParser.getTo().toString()));

        // CC
        cell = row.createCell(cellNumber++);
        cell.setCellValue(maskData(CollectionUtils.isEmpty(mimeMessageParser.getCc()) ? mimeMessageParser.getCc().toString() : EMPTY_STRING));

        // BCC
        cell = row.createCell(cellNumber++);
        cell.setCellValue(maskData(CollectionUtils.isEmpty(mimeMessageParser.getBcc()) ? mimeMessageParser.getBcc().toString() : EMPTY_STRING));

        //Body
        cell = row.createCell(cellNumber++);
        if (StringUtils.isEmpty(rootBody))
            cell.setCellValue(maskData(mimeMessageParser.getPlainContent()));
        else
            cell.setCellValue(rootBody);

        //Has Attachments
        cell = row.createCell(cellNumber++);
        cell.setCellValue(mimeMessageParser.hasAttachments());

        //Attachments
        cell = row.createCell(cellNumber++);
        StringBuilder attachmentString = new StringBuilder(" ");
        mimeMessageParser.getAttachmentList().stream().forEach(x -> attachmentString.append(x.getName() + "  "));
        cell.setCellValue(attachmentString.toString());

        // In reply to
        cell = row.createCell(cellNumber++);
        cell.setCellValue(mimeMessageParser.getMimeMessage().getHeader(IN_REPLY_TO) != null
                && mimeMessageParser.getMimeMessage().getHeader(IN_REPLY_TO).length > 0
                ? mimeMessageParser.getMimeMessage().getHeader(IN_REPLY_TO)[0] : EMPTY_STRING);

        //References
        cell = row.createCell(cellNumber++);
        cell.setCellValue((mimeMessageParser.getMimeMessage().getHeader(REFERENCES) != null
                && mimeMessageParser.getMimeMessage().getHeader(REFERENCES).length > 0)
                ? String.join(", ", mimeMessageParser.getMimeMessage().getHeader(REFERENCES)) : EMPTY_STRING);
    }

    public void writeToBook(List<MimeMessageParsorExtended> mimeMessageParsers, Sheet sheet, Sheet failedSheet, Workbook failed_Workbook, MimeMessage message, String inputFolderPath, String outputFolderPath) throws Exception {
        int threadId = 0;
        int rowNum = 1;
        List<String> successfullyProcessedFiles = new ArrayList<>();
//        List<String> failedFiles = new ArrayList<>();
        for (MimeMessageParsorExtended mimeMessageParser : mimeMessageParsers) {
            int referenceCount = 0;
            threadId++;
            String[] refSplitArray = null;

            try {
                Multipart part = (Multipart) message.getContent();
                List<String> emailThreadList = new ArrayList<>();
                String rootBody = EMPTY_STRING;

                if (part.getContentType().startsWith("multipart/") && (mimeMessageParser.getMimeMessage().getHeader(REFERENCES) != null
                        && mimeMessageParser.getMimeMessage().getHeader(REFERENCES).length > 0)) {
                    refSplitArray = String.join(", ", mimeMessageParser.getMimeMessage().getHeader(REFERENCES)).split("\r\n");
                    referenceCount = refSplitArray.length;

                    emailThreadList = htmlParser(mimeMessageParser.getHtmlContent(), referenceCount);
                    rootBody = emailThreadList.get(0);
                }
                rootBody = maskData(rootBody);

                processMainMail(mimeMessageParser, sheet, threadId, rowNum, rootBody);
                rowNum++;

                // Child threads processing
                for (int i = 1; i < emailThreadList.size(); i++) {
                    String thread = emailThreadList.get(i);
                    String from = EMPTY_STRING;
                    String sent = EMPTY_STRING;
                    String to = EMPTY_STRING;
                    String Cc = EMPTY_STRING;
                    String bcc = EMPTY_STRING;
                    String subject = EMPTY_STRING;
                    String threadBody = EMPTY_STRING;

                    if (thread.contains("Sent:")) {
                        from = thread.substring(0, thread.indexOf("Sent:"));
                    }
                    if (thread.contains("Sent:") && thread.contains("To:") && thread.indexOf("Sent:") < thread.indexOf("To:")) {
                        sent = thread.substring(thread.indexOf("Sent:"), thread.indexOf("To:"));
                    }

                    if (thread.contains("To:") && thread.contains("Cc:") && thread.indexOf("To:") < thread.indexOf("Cc:")) {
                        to = thread.substring(thread.indexOf("To:"), thread.indexOf("Cc:"));
                    } else if (thread.contains("To:") && thread.contains("Bcc:") && thread.indexOf("To:") < thread.indexOf("Bcc:")) {
                        to = thread.substring(thread.indexOf("To:"), thread.indexOf("Bcc:"));
                    } else if (thread.contains("To:") && thread.contains("Subject:") && thread.indexOf("To:") < thread.indexOf("Subject:")) {
                        to = thread.substring(thread.indexOf("To:"), thread.indexOf("Subject:"));
                    }

                    if (thread.contains("Bcc:") && thread.contains("Subject:") && thread.indexOf("Bcc:") < thread.indexOf("Subject:")) {
                        Cc = thread.substring(thread.indexOf("Bcc:"), thread.indexOf("Subject:"));
                    } else if (thread.contains("Cc:") && !thread.contains("Bcc:") && thread.contains("Subject:") && thread.indexOf("Cc:") < thread.indexOf("Subject:")) {
                        Cc = thread.substring(thread.indexOf("Cc:"), thread.indexOf("Subject"));
                    } else {
                        Cc = EMPTY_STRING;
                    }

                    if (thread.contains("Bcc:") && thread.contains("Subject:") && thread.indexOf("Bcc:") < thread.indexOf("Subject:")) {
                        bcc = thread.substring(thread.indexOf("Bcc:"), thread.indexOf("Subject:"));
                    }


                    if (thread.contains("Subject: RE:") && !ObjectUtils.isEmpty(mimeMessageParser.getSubject())) {
                        subject = thread.substring(thread.indexOf("Subject: RE:"), thread.indexOf("Subject: RE:") + "Subject: RE:".length() + mimeMessageParser.getSubject().length() - 1);
                    } else if (thread.contains("Subject:")) {
                        subject = thread.substring(thread.indexOf("Subject:"), thread.indexOf("Subject:") + "Subject:".length() + mimeMessageParser.getSubject().length() - 1);
                    }

                    if (!StringUtils.isEmpty(subject) && thread.indexOf(subject) > 0 && (thread.length() - 1) > 0) {
                        threadBody = thread.substring(thread.indexOf(subject) + subject.length(), thread.length() - 1);
                    }

                    Row newRow = sheet.createRow(rowNum++);
                    int cellNumber = 0;

                    //Filename
                    Cell newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(mimeMessageParser.getFileName());

                    //Thread Id
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(threadId);

                    //Thread Topic
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(mimeMessageParser.getMimeMessage().getHeader(THREAD_TOPIC) != null
                            && mimeMessageParser.getMimeMessage().getHeader(THREAD_TOPIC).length > 0
                            ? mimeMessageParser.getMimeMessage().getHeader(THREAD_TOPIC)[0] : EMPTY_STRING);

                    //MessageId
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(refSplitArray[i - 1]);

                    //TimeStamp
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(sent);

                    //Subject
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(maskData(subject));

                    //From
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(maskData(from));

                    //To
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(maskData(to));

                    // CC
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(maskData(Cc));

                    // BCC
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(maskData(bcc));

                    //Body
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue(maskData(threadBody));

                    //Has Attachments
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue("");

                    //Attachments
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue("");

                    // In reply to
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue("");

                    //References
                    newCell = newRow.createCell(cellNumber++);
                    newCell.setCellValue("");
                }
                successfullyProcessedFiles.add(mimeMessageParser.getFileName());
            } catch (Throwable e) {
                failedFiles.add(mimeMessageParser.getFileName());

                String body = String.valueOf(message.getContent());

                Row row = failedSheet.createRow(rowNum++);
                int cellNumber = 0;

                //Filename
                Cell newCell = row.createCell(cellNumber++);
                newCell.setCellValue(mimeMessageParser.getFileName());

                //TimeStamp
                newCell = row.createCell(cellNumber++);
                newCell.setCellValue(!CollectionUtils.isEmpty(Arrays.asList(mimeMessageParser.getMimeMessage().getHeader(DATE))) ? mimeMessageParser.getMimeMessage().getHeader(DATE)[0] : EMPTY_STRING);

                //content
                newCell = row.createCell(cellNumber++);
                newCell.setCellValue(maskData(mimeMessageParser.getPlainContent()));

//                writeFileToLocation(failedFiles, "ERROR", inputFolderPath, outputFolderPath, failed_Workbook);

                System.out.println("Exception : " + e.getMessage());
            }
        }
//        writeFileToLocation(successfullyProcessedFiles, "SUCCESS", inputFolderPath, outputFolderPath);
//        writeFileToLocation(failedFiles, "ERROR", inputFolderPath, outputFolderPath, failed_Workbook);
    }

    public void writeFileToLocation(List<String> filenames, String type, String inputFolderPath, String outputFolderPath, Workbook failed_Workbook) {
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

        for (String fileName : filenames) {
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

        System.out.println("Creating excel file: " + typefile);
        String opFilePath = EMPTY_STRING;
        if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
            opFilePath = typefile.getAbsolutePath() + "\\EmailData.xlsx";
        } else {
            opFilePath = typefile.getAbsolutePath() + "/EmailData.xlsx";
        }

        try (FileOutputStream outputStream = new FileOutputStream(new File(typefile.getAbsolutePath() + "/EmailData.xlsx"))) {
            failed_Workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalPath = EMPTY_STRING;

//        file = new File(finalPath);
       /* System.out.println("Creating file: " + finalPath);
        try (FileOutputStream outputStream = new FileOutputStream(new File(finalPath))) {
            workbook.write(outputStream);
        }*/
    }

    public String getMailSentDate(MimeMessage message) throws Exception {
        Date sentDate = message.getSentDate();

        if (ObjectUtils.isEmpty(sentDate))
            return UNCLASSIFIED_DATE;

        Calendar cal = Calendar.getInstance();
        cal.setTime(sentDate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return (day + "_" + month + "_" + year);
    }

    public void parseEMLFile(String inputFolderPath, String outputFolderPath) throws Exception {
        Path inputPath = Paths.get(inputFolderPath);
        if (!Files.exists(inputPath) || StringUtils.isEmpty(inputFolderPath)) {
            System.out.println("Input folder path doesnt exists");
            return;
        }
        File folder = new File(inputFolderPath);

        Properties props = System.getProperties();
        props.put(MAIL_HOST_PROPERTY, MAIL_HOST);
        props.put(MAIL_TRANSPORT_PROTOCOL_PROPERTY, MAIL_TRANSPORT_PROTOCOL);

        MimeMessage message = null;
        Session mailSession = Session.getDefaultInstance(props, null);
        String[] fileNames = folder.list();
        if (fileNames.length < 2) {
            String temp = fileNames[0];
            fileNames = new String[30000];
            for (int i = 0; i < 30000; i++) {
                fileNames[i] = new String(temp);
            }
        }
        int filesize = fileNames.length;
        int batches = (filesize / BATCH_SIZE) + 1;
        String tempFilePath = EMPTY_STRING;

        if (System.getProperty(OS_NAME_PROPERTY).toLowerCase().contains(WINDOWS_OS)) {
            tempFilePath = inputFolderPath + "\\";
        } else {
            tempFilePath = inputFolderPath + "/";
        }

        for (int i = 1; i <= batches; i++) {
            System.out.println("Running batch: " + i);
            count = i;
            dateMailMap = new HashMap<>();
            for (int k = BATCH_SIZE * (i - 1); k < k + BATCH_SIZE && k < filesize; k++) {
                File emlFile = new File(tempFilePath + fileNames[k]);
                //folder.list();

                mapDateKey = EMPTY_STRING;

                List<MimeMessageParsorExtended> mimeMessageParserList = new ArrayList<>();
                if (emlFile != null && emlFile.exists() && emlFile.getName().endsWith(EML_EXTENSION)) {

                    try (InputStream source = new FileInputStream(emlFile)) {
                        message = new MimeMessage(mailSession, source);
                    }

                    mapDateKey = getMailSentDate(message);
                    MimeMessageParsorExtended mimeMessageParser = new MimeMessageParsorExtended(message);
                    mimeMessageParser.parse();
                    mimeMessageParser.setFileName(emlFile.getAbsolutePath());

                    if (dateMailMap.containsKey(mapDateKey)) {
                        mimeMessageParserList = dateMailMap.get(mapDateKey);
                        mimeMessageParserList.add(mimeMessageParser);
                    } else {
                        mimeMessageParserList.add(mimeMessageParser);
                    }
                    dateMailMap.put(mapDateKey, mimeMessageParserList);
                }
            }
            if (!CollectionUtils.isEmpty(dateMailMap) && !CollectionUtils.isEmpty(dateMailMap)) {
                for (Map.Entry<String, List<MimeMessageParsorExtended>> entry : dateMailMap.entrySet()) {
                    System.out.println("Processing mails for date: " + entry.getKey());
                    writeDataToFile(entry.getKey(), entry.getValue(), inputFolderPath, outputFolderPath, message);
                }
            }
        }


      /*      for (File emlFile : folder.listFiles()) {
//                for (int i = 0; i < 10000; i++) {
//                    System.out.println("running loop" + i);

                    mapDateKey = "";
                    List<MimeMessageParser> mimeMessageParserList = new ArrayList<>();
                    if (emlFile != null && emlFile.exists() && emlFile.getName().endsWith(EML_EXTENSION)) {

                        try (InputStream source = new FileInputStream(emlFile)) {
                            message = new MimeMessage(mailSession, source);
                        }

                        mapDateKey = getMailSentDate(message);
                        MimeMessageParser mimeMessageParser = new MimeMessageParser(message);
                        mimeMessageParser.parse();

                        if (dateMailMap.containsKey(mapDateKey)) {
                            mimeMessageParserList = dateMailMap.get(mapDateKey);
                            mimeMessageParserList.add(mimeMessageParser);
                        } else {
                            mimeMessageParserList.add(mimeMessageParser);
                        }
                        dateMailMap.put(mapDateKey, mimeMessageParserList);
                    }
//                }
            }


        if (!CollectionUtils.isEmpty(dateMailMap)) {
            for (Map.Entry<String, List<MimeMessageParser>> entry : dateMailMap.entrySet()) {
                System.out.println("Processing mails for date: " + entry.getKey());
                writeDataToFile(entry.getKey(), entry.getValue(), inputFolderPath, outputFolderPath, message);
            }
        }*/
    }

    public List<String> htmlParser(String htmlContent, int refCount) {
        List<String> splitString = new ArrayList<>();
        List<String> emailThreadList = new ArrayList<>();
        Document doc = Jsoup.parse(htmlContent);
        Elements divExtract = doc.body().select(HTML_DIV_CLASS);
        String divExtractString = doc.body().select(HTML_DIV_CLASS).text();
        if (divExtractString.contains(TEXT_SPLITTER_STRING)) {
            splitString = Arrays.asList(divExtractString.split(TEXT_SPLITTER_STRING));
            if (!(refCount >= splitString.size() + 1))
                emailThreadList = splitString.subList(0, refCount + 1);
            else
                emailThreadList = splitString;
        } else {
            emailThreadList = Arrays.asList(divExtractString);
        }
        return emailThreadList;
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

    public List<String> phoneNumberPatternCheck(String msg, String regex) {
        List<String> strList = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            if (!ObjectUtils.isEmpty(matcher.group("number"))) {
                strList.add(matcher.group("number"));
            }
        }
        return strList;
    }

    public String replaceMatchedList(String msg, List<String> strList, String replaceString) {
        try {
            for (String str : strList) {
                msg = msg.replaceAll(str, replaceString);
            }
        } catch (Exception e) {
            return msg;
        }
        return msg;
    }

    public String replaceNumberMatchedList(String msg, List<String> strList, String replaceString) {
        try {
            for (String str : strList) {
                msg = msg.replace(str, replaceString);
            }
        } catch (Exception e) {
            return msg;
        }
        return msg;
    }
}
