package msgai.eml.poc.msgaiemlpoc;

import org.apache.commons.mail.util.MimeMessageParser;

import javax.mail.internet.MimeMessage;

public class MimeMessageParsorExtended extends MimeMessageParser {
    private String fileName = "";

    public MimeMessageParsorExtended(MimeMessage message) {
        super(message);
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String file) {
        this.fileName = file;
    }
}
