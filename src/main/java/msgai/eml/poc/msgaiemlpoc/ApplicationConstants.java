package msgai.eml.poc.msgaiemlpoc;

public interface ApplicationConstants {
    String EMAIL_REGEX = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    String EMAL_REGEX_UPDATED = "[A-Za-z0-9._%+-]+(\\n)?@[A-Za-z0-9.-|\\n]+(\\n)?\\.[A-Za-z|\\n]{2,4}";
    String EMAIL_GROUP_REGEX = "(?<email>[A-Za-z0-9._%+-]+(\\n)?@[A-Za-z0-9.-|\\n]+(\\n)?\\.[A-Za-z|\\n]{2,4})";

    String NUMBER_REGEX_UPDATED = "((?![a-zA-Z])([\\+][0-9]{1,3}([ \\-])?)?([\\+|\\(]{1,2}[0-9]{2,3}[\\)])?([0-9 \\-]{1,15})(?![a-zA-Z])){9,15}";
    String NUMBER_REGEX_THIRD = "((?![0-9a-zA-Z])|[\\s|\\(|\\)|-|\\+])(?<number>(\\+65|\\+91|)(\\)|\\s|\\-)*(([0-9]\\s*-*){8,10})(?![0-9a-zA-Z:])(?!(a-zA-Z)))";
    String NUMBER_REGEX_FOURTH = "((?![0-9a-zA-Z])|[\\s|\\(|\\)|-|\\+])(?<number>(\\+([0-9]{2,3})|)(\\+65|\\+91|)(\\)|\\s|\\-)*(([0-9]\\s*-*){8,10})(?![0-9a-zA-Z:])(?!(a-zA-Z)))";
    String NUMBER_REGEX = "(\\()?(\\+)?(\\d){2,4}(\\))?(\\s|-)?(\\d|\\s|\\-|\\d){2,15}";

    String USERNAME_REGEX = "\\b[u,U][s,S][e,E][r,R][n,N][a,A][m,M][e,E][\\S*((?:\\s\\S+))]{2,20}\\b(?![p,P][a,A][s,S][s,S][w,W][o,O][r,R][d,D]\\b)\\w+";
    String PASSWORD_REGEX = "\\b[p,P][a,A][s,S][s,S][w,W][o,O][r,R][d,D][\\S*((?:\\s\\S+))]{2,20}\\b";
    String USERNAME_UPDATED_REGEX = "(?=\\S*([u,U][s,S][e,E][r,R][n,N][a,A][m,M][e,E]((\\s*|\\w+)(?!([p,P][a,A][s,S][s,S][w,W][o,O][r,R][d,D]))){1,7}(is|(\\-|\\:))(\\s*)([\\S]+)))";
    String PASSWORD_UPDATED_REGEX = "(?=\\S*([p,P][a,A][s,S][s,S][w,W][o,O][r,R][d,D]((\\s*|\\w+)(?!([u,U][s,S][e,E][r,R][n,N][a,A][m,M][e,E]))){1,7}(is|(\\-|\\:))(\\s*)([\\S]+)))";

    String IPv4_IPv6_REGEX = "(^|\\s|(\\[))(::)?([a-f\\d]{1,4}::?){0,7}(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}(?=(?(2)\\]|($|\\s|(?(3)($|\\s)|(?(4)($|\\s)|:\\d)))))|((?(3)[a-f\\d]{1,4})|(?(4)[a-f\\d]{1,4}))(?=(?(2)\\]|($|\\s))))(?(2)\\])(:\\d{1,5})?";
    String IPV4_PATTERN = "(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}";
    String IPV6_STD_PATTERN = "(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}";
    String IPV6_HEX_COMPRESSED_PATTERN = "((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)";
    String EMAIL_REPLACE_STRING = "**EMAIL**";
    String NUMBER_REPLACE_STRING = "**NUMBER**";
    String USERNAME_REPLACE_STRING = "**USERNAME**";
    String PASSWORD_REPLACE_STRING = "**PASSWORD**";
    String IP_REPLACE_STRING = "**IP**";
    String EML_EXTENSION = ".eml";
    String FILE_EXTENSION = ".xlsx";
    String FILE_EXTENSION_TXT = ".txt";
    String FILE_EXTENSION_EML = ".eml";

    String SHEET_NAME = "EmailData";
    String FAILED_SHEET_NAME = "FailedEmailData";
    String MAIL_HOST = "smtp-mail.outlook.com";
    String MAIL_TRANSPORT_PROTOCOL = "smtp";
    String MAIL_HOST_PROPERTY = "mail.host";
    String MAIL_TRANSPORT_PROTOCOL_PROPERTY = "mail.transport.protocol";

    String OS_NAME_PROPERTY = "os.name";
    String WINDOWS_OS = "window";
    String WINDOWS_OS_FILE_PATH = "\\Result_";
    String MAC_LINUX_OS_FILE_PATH = "/Result_";
    String OUTPUT_FOLDER_NAME = "ResultFiles";

    String THREAD_TOPIC = "Thread-Topic";
    String MESSAGE_ID = "Message-ID";
    String DATE = "Date";
    String IN_REPLY_TO = "In-Reply-To";
    String REFERENCES = "References";
    String[] COLUMNS = {"FileName", "Thread Id", THREAD_TOPIC, MESSAGE_ID, "Timestamp", "Subject", "From", "To", "CC", "BCC", "Body", "Has Attachments", "Attachments", IN_REPLY_TO, REFERENCES};
    String[] FAILED_COLUMNS = {"FileName", "Timestamp", "Masked Content"};
    String UNCLASSIFIED_DATE = "unclassified_date";

    String HTML_DIV_CLASS = "div";
    String TEXT_SPLITTER_STRING = "From:";

    String EMPTY_STRING = "";
    int BATCH_SIZE = 3000;
    String INPUT_PATH_DOESNT_EXISTS = "Input folder path doesnt exists";
    String RUNNING_BATCH = "Running batch: ";
    String PROCESSING_FILE = "Processing mails for file: ";
    String ERROR = "ERROR";
    String NUMBER_MATCH_GROUP = "number";
    String EMAIL_MATCH_GROUP = "email";
    String READ_WRITE_MODE = "rw";

    String UNWANTED_CONTENT1 = "%2E";
    String UNWANTED_CONTENT2 = "=2E";
    String UNWANTED_CONTENT3 = "=";

    String str = "From: \"Jiang Xu  (NCS)\" <<EMAIL- 577662265>>\n" +
            "To: \"Lee, Sing Ai\" <<EMAIL- -1309192852>>, \"Kaung Pyie Win  NCS\"\n" +
            "\t<<EMAIL- -120004080>>\n" +
            "CC: \"Jianfeng Yang  (Singtel)\" <<EMAIL- 976343818>>\n" +
            "Subject: RE: Agile tools for software teams - for msg.ai testing\n" +
            "Thread-Topic: Agile tools for software teams - for msg.ai testing\n" +
            "Thread-Index: AdSy/59qkcYIT0nVQAyXmVlT+HkW1AAAUMuwAABkRmA\n" +
            "Date: Wed, 23 Jan 2019 10:00:26 +0000\n" +
            "Message-ID:\n" +
            " <<EMAIL- -2090273279>>\n" +
            "References:\n" +
            " <<EMAIL- -383170649>>\n" +
            " <<EMAIL- 759553496>>\n" +
            "In-Reply-To:\n" +
            " <<EMAIL- 759553496>>\n" +
            "Content-Language: en-US\n" +
            "X-MS-Has-Attach: yes\n" +
            "X-MS-TNEF-Correlator:\n" +
            "X-MS-Exchange-Organization-RecordReviewCfmType: 0\n" +
            "Content-Type: multipart/related;\n" +
            "\tboundary\"_004_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_\";\n" +
            "\ttype\"multipart/alternative\"\n" +
            "MIME-Version: 1.0\n" +
            "\n" +
            "--_004_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_\n" +
            "Content-Type: multipart/alternative;\n" +
            "\tboundary\"_000_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_\"\n" +
            "\n" +
            "--_000_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_\n" +
            "Content-Type: text/plain; charset\"us-ascii\"\n" +
            "Content-Transfer-Encoding: quoted-printable\n" +
            "\n" +
            "Thanks. Guys\n" +
            "\n" +
            "\n" +
            "\n" +
            "From: Lee, Sing Ai [mailto:<EMAIL- -1309192852>]\n" +
            "Sent: Wednesday, January 23, 2019 5:49 PM\n" +
            "To: Jiang Xu (NCS) <<EMAIL- 577662265>>; Kaung Pyie Win NCS <pyiewin.kaung@\n" +
            "ncs.com.sg>\n" +
            "Cc: Jianfeng Yang (Singtel) <<EMAIL- 976343818>>\n" +
            "Subject: RE: Agile tools for software teams - for msg.ai testing\n" +
            "\n" +
            "\n" +
            "\n" +
            "Blah blah blah back\n" +
            "\n" +
            "\n" +
            "\n" +
            "From: Jiang Xu (NCS) <<EMAIL- 577662265><mailto:<EMAIL- 577662265>>>\n" +
            "Sent: Wednesday, January 23, 2019 5:48 PM\n" +
            "To: Kaung Pyie Win NCS <<EMAIL- -120004080><mailto:pyiewin.kaung@ncs.c\n" +
            "om.sg>>\n" +
            "Cc: Jianfeng Yang (Singtel) <<EMAIL- 976343818><mailto:jianfeng.yang\n" +
            "@singtel.com>>; Lee, Sing Ai <<EMAIL- -1309192852><mailto:SingAi.Lee@at\n" +
            "kearney.com>>\n" +
            "Subject: Agile tools for software teams - for msg.ai testing\n" +
            "\n" +
            "\n" +
            "\n" +
            "Hi, Guys,\n" +
            "\n" +
            "This emails is just for the msg.ai testing purpose.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Jira Software supports any agile project management methodology for softwar\n" +
            "e development.\n" +
            "\n" +
            "Jira Software is an agile project management tool that supports any agile m\n" +
            "ethodology, be it scrum, kanban, or your own unique flavor. From agile boar\n" +
            "ds to reports, you can plan, track, and manage all your agile software deve\n" +
            "lopment projects from a single tool. Pick a framework to see how Jira Softw\n" +
            "are can help your team release higher quality software, faster.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Agile tools for scrum\n" +
            "\n" +
            "Scrum is an agile methodology where products are built in a series of fixed\n" +
            "-length iterations. There are four pillars that bring structure to this fra\n" +
            "mework: sprint planning, stand ups (also called daily scrums), sprints, and\n" +
            " retrospectives. Out-of-the-box, Jira Software comes with a comprehensive s\n" +
            "et of agile tools that help your scrum team perform these events with ease.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Tools for sprint planning\n" +
            "\n" +
            "Sprint planning meetings determine what the team should complete in the com\n" +
            "ing sprint from the backlog, or list of work to be done. Jira Software make\n" +
            "s your backlog the center of your sprint planning meeting, so you can estim\n" +
            "ate stories, adjust sprint scope, check velocity, and re-prioritize issues \n" +
            "in real-time. There are several tools within Jira Software that can help yo\n" +
            "ur sprint planning run smoothly.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Version management\n" +
            "\n" +
            "Track versions, features, and progress at a glance. Click into a version to\n" +
            " see the complete status, including the issues, development data, and poten\n" +
            "tial problems.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Easy backlog grooming\n" +
            "\n" +
            "Easily re-prioritize your user stories and bugs. Select one or more issues,\n" +
            " then drag and drop to reorder them in your backlog. Create quick filters t\n" +
            "o surface issues with important attributes.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Make your backlog the center of your sprint planning meeting. Estimate stor\n" +
            "ies, adjust sprint scope, check velocity, and re-prioritize issues in real-\n" +
            "time with the rest of the team.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Story points\n" +
            "\n" +
            "Estimate, track and report on story points to help your team become more ac\n" +
            "curate in future sprints. Use story points, ideal hours, or your own method\n" +
            " of estimating.\n" +
            "\n" +
            "\n" +
            "\n" +
            "Scrum board\n" +
            "\n" +
            "Scrum boards are used to visualize all the work in a given sprint. Jira Sof\n" +
            "tware's scrum boards can be customized to fit your team's unique workflow. \n" +
            "You can also easily add things like swimlanes to separate epics, assignees,\n" +
            " projects, and more. At the end of the sprint, get a quick snapshot of all \n" +
            "the issues that were completed and any unfinished issues will automatically\n" +
            " move into the backlog to be addressed in the next sprint planning meeting.\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "Regards\n" +
            "\n" +
            "Jiang Xu  (NCS)\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "This message and all attachments are confidential. Any unauthorized review,\n" +
            " use, disclosure, or distribution is prohibited. If you believe this messag\n" +
            "e has been sent to you by mistake, please notify the sender by replying to \n" +
            "this transmission, and delete the message and its attachments without discl\n" +
            "osing them.\n" +
            "\n" +
            "\n" +
            "--_000_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_\n" +
            "Content-Type: text/html; charset\"us-ascii\"\n" +
            "Content-Transfer-Encoding: quoted-printable\n" +
            "\n" +
            "<html xmlns:v3D\"urn:schemas-microsoft-com:vml\" xmlns:o3D\"urn:schemas-micr\n" +
            "osoft-com:office:office\" xmlns:w3D\"urn:schemas-microsoft-com:office:word\" \n" +
            "xmlns:m3D\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns3D\"http:\n" +
            "//www.w3.org/TR/REC-html40\">\n" +
            "<head>\n" +
            "<meta http-equiv3D\"Content-Type\" content3D\"text/html; charset3Dus-ascii\"\n" +
            ">\n" +
            "<meta name3D\"Generator\" content3D\"Microsoft Word 15 (filtered medium)\">\n" +
            "<!--[if !mso]><style>v\\:* {behavior:url(#default#VML);}\n" +
            "o\\:* {behavior:url(#default#VML);}\n" +
            "w\\:* {behavior:url(#default#VML);}\n" +
            ".shape {behavior:url(#default#VML);}\n" +
            "</style><![endif]--><style><!--\n" +
            "/* Font Definitions */\n" +
            "@font-face\n" +
            "\t{font-family:\"Cambria Math\";\n" +
            "\tpanose-1:2<NUMBER- 1342906036>;}\n" +
            "@font-face\n" +
            "\t{font-family:DengXian;\n" +
            "\tpanose-1:2<NUMBER- -47329825>;}\n" +
            "@font-face\n" +
            "\t{font-family:Calibri;\n" +
            "\tpanose-1:2<NUMBER- 606782876>;}\n" +
            "@font-face\n" +
            "\t{font-family:\"\\@DengXian\";\n" +
            "\tpanose-1:2<NUMBER- -47329825>;}\n" +
            "/* Style Definitions */\n" +
            "p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
            "\t{margin:0in;\n" +
            "\tmargin-bottom:.0001pt;\n" +
            "\tfont-size:11.0pt;\n" +
            "\tfont-family:\"Calibri\",sans-serif;}\n" +
            "a:link, span.MsoHyperlink\n" +
            "\t{mso-style-priority:99;\n" +
            "\tcolor:#0563C1;\n" +
            "\ttext-decoration:underline;}\n" +
            "a:visited, span.MsoHyperlinkFollowed\n" +
            "\t{mso-style-priority:99;\n" +
            "\tcolor:#954F72;\n" +
            "\ttext-decoration:underline;}\n" +
            "p.msonormal0, li.msonormal0, div.msonormal0\n" +
            "\t{mso-style-name:msonormal;\n" +
            "\tmso-margin-top-alt:auto;\n" +
            "\tmargin-right:0in;\n" +
            "\tmso-margin-bottom-alt:auto;\n" +
            "\tmargin-left:0in;\n" +
            "\tfont-size:11.0pt;\n" +
            "\tfont-family:\"Calibri\",sans-serif;}\n" +
            "span.EmailStyle18\n" +
            "\t{mso-style-type:personal;\n" +
            "\tfont-family:\"Calibri\",sans-serif;\n" +
            "\tcolor:windowtext;}\n" +
            "span.EmailStyle19\n" +
            "\t{mso-style-type:personal;\n" +
            "\tfont-family:\"Calibri\",sans-serif;\n" +
            "\tcolor:windowtext;}\n" +
            "span.EmailStyle21\n" +
            "\t{mso-style-type:personal-reply;\n" +
            "\tfont-family:\"Calibri\",sans-serif;\n" +
            "\tcolor:windowtext;}\n" +
            ".MsoChpDefault\n" +
            "\t{mso-style-type:export-only;\n" +
            "\tfont-size:10.0pt;}\n" +
            "@page WordSection1\n" +
            "\t{size:8.5in 11.0in;\n" +
            "\tmargin:1.0in 1.0in 1.0in 1.0in;}\n" +
            "div.WordSection1\n" +
            "\t{page:WordSection1;}\n" +
            "--></style><!--[if gte mso 9]><xml>\n" +
            "<o:shapedefaults v:ext3D\"edit\" spidmax3D\"1026\" />\n" +
            "</xml><![endif]--><!--[if gte mso 9]><xml>\n" +
            "<o:shapelayout v:ext3D\"edit\">\n" +
            "<o:idmap v:ext3D\"edit\" data3D\"1\" />\n" +
            "</o:shapelayout></xml><![endif]-->\n" +
            "</head>\n" +
            "<body lang3D\"EN-US\" link3D\"#0563C1\" vlink3D\"#954F72\">\n" +
            "<div class3D\"WordSection1\">\n" +
            "<p class3D\"MsoNormal\">Thanks. Guys<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<div>\n" +
            "<div style3D\"border:none;border-top:solid #E1E1E1 1.0pt;padding:3.0pt 0in \n" +
            "0in 0in\">\n" +
            "<p class3D\"MsoNormal\"><b>From:</b> Lee, Sing Ai [mailto:SingAi.Lee@atkearn\n" +
            "ey.com] <br>\n" +
            "<b>Sent:</b> Wednesday, January 23, 2019 5:49 PM<br>\n" +
            "<b>To:</b> Jiang Xu (NCS) &lt;<EMAIL- 577662265>&gt;; Kaung Pyie Win NCS &l\n" +
            "t;<EMAIL- -120004080>&gt;<br>\n" +
            "<b>Cc:</b> Jianfeng Yang (Singtel) &lt;<EMAIL- 976343818>&gt;<br>\n" +
            "<b>Subject:</b> RE: Agile tools for software teams - for msg.ai testing<o:p\n" +
            "></o:p></p>\n" +
            "</div>\n" +
            "</div>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Blah blah blah back<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<div>\n" +
            "<div style3D\"border:none;border-top:solid #E1E1E1 1.0pt;padding:3.0pt 0in \n" +
            "0in 0in\">\n" +
            "<p class3D\"MsoNormal\"><b>From:</b> Jiang Xu (NCS) &lt;<a href3D\"mailto:ji\n" +
            "<EMAIL- -1470448134>\"><EMAIL- 577662265></a>&gt;\n" +
            "<br>\n" +
            "<b>Sent:</b> Wednesday, January 23, 2019 5:48 PM<br>\n" +
            "<b>To:</b> Kaung Pyie Win NCS &lt;<a href3D\"mailto:<EMAIL- -972430414>.s\n" +
            "g\"><EMAIL- -120004080></a>&gt;<br>\n" +
            "<b>Cc:</b> Jianfeng Yang (Singtel) &lt;<a href3D\"mailto:jianfeng.yang@sing\n" +
            "tel.com\"><EMAIL- 976343818></a>&gt;; Lee, Sing Ai &lt;<a href3D\"mai\n" +
            "lto:<EMAIL- -1309192852>\"><EMAIL- -1309192852></a>&gt;<br>\n" +
            "<b>Subject:</b> Agile tools for software teams - for msg.ai testing<o:p></o\n" +
            ":p></p>\n" +
            "</div>\n" +
            "</div>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Hi, Guys,<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">This emails is just for the msg.ai testing purpose.<\n" +
            "o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Jira Software supports any agile project management \n" +
            "methodology for software development.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Jira Software is an agile project management tool th\n" +
            "at supports any agile methodology, be it scrum, kanban, or your own unique \n" +
            "flavor. From agile boards to reports, you can plan, track, and manage all y\n" +
            "our agile software development projects\n" +
            " from a single tool. Pick a framework to see how Jira Software can help you\n" +
            "r team release higher quality software, faster.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Agile tools for scrum<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Scrum is an agile methodology where&nbsp;products ar\n" +
            "e built in a series of&nbsp;fixed-length iterations. There are&nbsp;four pi\n" +
            "llars that bring structure to this framework: sprint planning, stand ups (a\n" +
            "lso called daily scrums), sprints, and retrospectives.\n" +
            " Out-of-the-box, Jira Software comes with a comprehensive set of agile tool\n" +
            "s that help your scrum team perform these events with ease.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Tools for sprint planning<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Sprint planning meetings determine what the team sho\n" +
            "uld complete in the coming sprint from the backlog, or list of work to be d\n" +
            "one. Jira Software makes your backlog the center of your sprint planning me\n" +
            "eting, so you can estimate stories,\n" +
            " adjust sprint scope, check velocity, and re-prioritize issues in real-time\n" +
            ". There are several tools within Jira Software that can help your sprint pl\n" +
            "anning run smoothly.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Version management<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Track versions, features, and progress at a glance. \n" +
            "Click into a version to see the complete status, including the issues, deve\n" +
            "lopment data, and potential problems.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Easy backlog grooming<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Easily re-prioritize your user stories and bugs. Sel\n" +
            "ect one or more issues, then drag and drop to reorder them in your backlog.\n" +
            " Create quick filters to surface issues with important attributes.<o:p></o:\n" +
            "p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Make your backlog the center of your sprint planning\n" +
            " meeting. Estimate stories, adjust sprint scope, check velocity, and re-pri\n" +
            "oritize issues in real-time with the rest of the team.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Story points<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Estimate, track and report on story points to help y\n" +
            "our team become more accurate in future sprints. Use story points, ideal ho\n" +
            "urs, or your own method of estimating.<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Scrum board<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Scrum boards are used to visualize all the work in a\n" +
            " given sprint. Jira Software's scrum boards can be customized to fit your t\n" +
            "eam's unique workflow. You can also easily add things like swimlanes to sep\n" +
            "arate epics, assignees, projects,\n" +
            " and more. At the end of the sprint, get a quick snapshot of all the issues\n" +
            " that were completed and any unfinished issues will automatically move into\n" +
            " the backlog to be addressed in the next sprint planning meeting.&nbsp;<o:p\n" +
            "></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\"><img border3D\"0\" width3D\"730\" height3D\"408\" style\n" +
            "3D\"width:7.6041in;height:4.25in\" id3D\"Picture_x0020_1\" src3D\"cid:image00\n" +
            "1.jpg@01D4B345.84CC64D0\"><o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">Regards<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\">Jiang Xu&nbsp; (NCS)<o:p></o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\"><o:p>&nbsp;</o:p></p>\n" +
            "<p class3D\"MsoNormal\">This message and all attachments are confidential. A\n" +
            "ny unauthorized review, use, disclosure, or distribution is prohibited. If \n" +
            "you believe this message has been sent to you by mistake, please notify the\n" +
            " sender by replying to this transmission,\n" +
            " and delete the message and its attachments without disclosing them. <o:p><\n" +
            "/o:p></p>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>\n" +
            "\n" +
            "--_000_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_--\n" +
            "\n" +
            "--_004_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_\n" +
            "Content-Type: image/jpeg; name\"image001.jpg\"\n" +
            "Content-Description: image001.jpg\n" +
            "Content-Disposition: inline; filename\"image001.jpg\"; size38371;\n" +
            "\tcreation-date\"Wed, 23 Jan 2019 10:08:41 GMT\";\n" +
            "\tmodification-date\"Wed, 23 Jan 2019 10:08:41 GMT\"\n" +
            "Content-ID: <image001.jpg@01D4B345.84CC64D0>\n" +
            "Content-Transfer-Encoding: base64\n" +
            "\n" +
            "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAoHBwgHBgoICAgLCgoLDhgQDg0NDh0VFhEYIx8lJCIf\n" +
            "IiEmKzcvJik0KSEiMEExNDk7Pj4+JS5ESUM8SDc9Pjv/2wBDAQoLCw4NDhwQEBw7KCIoOzs7Ozs7\n" +
            "\n" +
            "--_004_HK0PR02MB325040E8DBF28E5FAF87974E97990HK0PR02MB3250apcp_--\n";
}