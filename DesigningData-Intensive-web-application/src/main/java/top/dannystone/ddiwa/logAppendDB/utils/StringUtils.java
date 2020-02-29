package top.dannystone.ddiwa.logAppendDB.utils;

import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 句号
     */
    private static final String COMMA = ".";

    private static final int MAX_UA_LENGTH = 500;
    /**
     * 用户名验证的正则表达式
     */
    private final static String UserNicknamePattern = "^[\\w\\u4E00-\\u9FA5\\uF900-\\uFA2D]*$";
    /**
     * 手机号验证的正则表达式
     */
    private final static String MobileNoPattern = "^1\\d{10}$";

    private final static String PhoneNoPattern = "^[\\d()+]{8,14}$";

    /**
     * 小灵通验证的正则表达式
     */
    private final static String LTMobileNoPattern = "^\\d{11}$";

    /**
     * 姓名字数验证的正则表达式
     */
    private final static String NameLengthPattern = "^[\u4e00-\u9fa5]{2,4}$";

    /**
     * 姓名结束符验证的正则表达式 先生/小姐/女士 结尾
     */
    private final static String NameEndPattern = "^.*?(\u5148\u751F|\u5C0F\u59D0|\u5973\u58EB)$";

    /**
     * 中文验证的正则表达式
     */
    private final static String ChinesePattern = "[\u4e00-\u9fa5]";

    /**
     * 邮编验证的正则表达式
     */
    private final static String ZipPattern = "^[1-9]\\d{5}(?!\\d)$";

    // / IPv4验证的正则表达式
    private final static String IPAddresLayer4 = "(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))";

    private final static String ImageExtension = ".bmp.jpg.tiff.gif.pcx.tga.exif.fpx.svg.psd.cdr.pcd.dxf.ufo.eps.ai.raw.";

    public static boolean isImageResource(String string) {
        String extension = substringAfterLast(string, COMMA);
        if (isBlank(extension)) {
            return false;
        }
        return ImageExtension.contains(COMMA + extension + COMMA);
    }

    public static boolean isImageType(String fileType) {
        if (isBlank(fileType)) {
            return false;
        }
        return ImageExtension.contains(COMMA + fileType + COMMA);
    }

    public static boolean isValidMobileNo(String mobileNo) {
        if (isBlank(mobileNo)){
            return false;
        }
        return Pattern.matches(MobileNoPattern, mobileNo);
    }

    public static boolean isValidPhone(String phoneNo) {
        if (isBlank(phoneNo)){
            return false;
        }
        return Pattern.matches(PhoneNoPattern, phoneNo);
    }

    public static boolean isValidLTMobileNo(String mobielNo) {
        return Pattern.matches(LTMobileNoPattern, mobielNo);
    }

    public static boolean isValidNikename(String nickName) {
        return Pattern.matches(UserNicknamePattern, nickName);
    }

    public static boolean isValidIpAddress(String ip) {
        return Pattern.matches(IPAddresLayer4, ip);
    }

    public static boolean isValidUserName(String userName) {
        return Pattern.matches(NameLengthPattern, userName) && !Pattern.matches(NameEndPattern, userName);
    }

    public static boolean isNumeric(String str) {
        if(str == null || str.length() ==0) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isValidUserAddress(String userAddress) {
        if (userAddress.length() <= 4 || userAddress.length() > 80) {
            return false;
        }
        // 判断是否含有中文
        String regEx = ChinesePattern;
        Pattern pat = Pattern.compile(regEx);
        return pat.matcher(userAddress).find();
    }

    public static boolean isValidUserZip(String userZip) {
        return Pattern.matches(ZipPattern, userZip);
    }

    /**
     * 将字符串转换为囧编码的字符串
     *
     * @param str
     * @return
     */

    public static String orz(String str) {
        String result = str;
        if (str != null) {
            result = result.replace("<", "〈");
            result = result.replace(">", "〉");
        }

        return result;
    }

    private static char[] splitChar = { '.', '。', '@', '~', '～', '-', '—', '=', '！', '!', ' ', '\r', '\n', '／', '/',
            '·', '，', '、', '：', '，', '。', '＝', '·', '＋', '＊', '？', '＄', '＃', '＠', '：', '?', '+', '_', '*', '%', '#',
            '$' };

    /**
     * 获取去除过特殊符号的内容长度，主要用于点评内容长度修订
     *
     * @param str
     * @return
     */

    public static int fixedLength(String str) {
        int count = 0;
        char[] chars = str.toCharArray();
        String splitStr = String.valueOf(splitChar);
        for (char c : chars) {
            if (!splitStr.contains(String.valueOf(c))) {
                count++;
            }
        }

        return count;
    }

    /**
     * 获取字符串中包含的中文字符个数
     *
     * @param str
     * @return
     */
    public static int countOfChineseLetter(String str) {
        return (str.getBytes().length - str.length()) / 2;
    }

    /**
     * 判断字符串中是否包含字符串数组中包含的字符串
     *
     * @param text
     * @param words
     * @return
     */
    public static boolean hasWords(String text, String[] words) {
        if (isBlank(text)) {
            return false;
        }
        for (String word : words) {
            if (text.contains(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 产生指定长度的随机字符串
     *
     * @param length
     * @param type
     * @return
     */
    public static String randomString(int length, RandomStringType type) {
        if (type == null) {
            throw new IllegalArgumentException("Requested random type[" + type + "] is not supported.");
        }
        switch (type) {
            case Lowercase:
                return RandomStringUtils.randomAlphabetic(length).toLowerCase();
            case Uppercase:
                return RandomStringUtils.randomAlphabetic(length).toUpperCase();
            case Alphabetic:
                return RandomStringUtils.randomAlphabetic(length);
            case Numberic:
                return RandomStringUtils.randomNumeric(length);
            default:
                return RandomStringUtils.randomAlphanumeric(length);
        }
    }

    /**
     * 返回偶数位字符串，位数从1开始
     *
     * @param text
     * @return
     */
    public static String evenSubstring(String text) {
        return evenOrOdd(text, true);
    }

    /**
     * 返回奇数位字符串，位数从1开始
     *
     * @param text
     * @return
     */
    public static String oddSubstring(String text) {
        return evenOrOdd(text, false);
    }

    private static String evenOrOdd(String text, boolean even) {
        if (isEmpty(text)) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i = i + 2) {
            int start = i + (even ? 1 : 0);
            sb.append(substring(text, start, start + 1));
        }
        return sb.toString();
    }

    /**
     * 随机字符串的类型
     */
    public static enum RandomStringType {
        // 字符，小写字符，大写字符，数字，所有可能
        Alphabetic, Lowercase, Uppercase, Numberic, All;
    }

    /**
     * 判断string是否为空
     *
     * @param text
     * @return
     */
    public static boolean isNullOrEmpty(String text) {
        return org.apache.commons.lang3.StringUtils.isBlank(text);
    }

    /**
     * 判断string不为空
     *
     * @param text
     * @return
     */
    public static boolean isNotBlank(String text) {
        return org.apache.commons.lang3.StringUtils.isNotBlank(text);
    }

    public static String replaceAll(String text, String regex, String replacement, int start, int count) {
        if (text == null || regex == null || replacement == null || start < 0 || (start + count) < 0
                || start > text.length() || (start + count) > text.length()) {
            return text;
        }
        String prefix = text.substring(0, start);
        String middlefix = text.substring(start, start + count).replaceAll(regex, replacement);
        String postfix = text.substring(start + count, text.length());
        return new StringBuilder(500).append(prefix).append(middlefix).append(postfix).toString();
    }

    /**
     * 将一个list转化为逗号分隔的字符串(没有合适的地方放，先放在这里吧cong.qu)
     *
     * @param <T>
     * @param list
     * @return
     * @author cong.qu
     */
    public static <T> String listToQueryString(List<T> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder queueBuilder = new StringBuilder("");
        for (T text : list) {
            if (text == null) {
                continue;
            }
            queueBuilder.append(text.toString());
            queueBuilder.append(",");
        }
        if (queueBuilder.length() > 0) {
            queueBuilder.deleteCharAt(queueBuilder.length() - 1);
        }
        return queueBuilder.toString();
    }

    /**
     * 获取字符串中文本的差异度
     *
     * @param words
     *            目标字符串
     * @return 差异度0~100，越高差异越大
     */
    public static int wordDifference(String words) {
        if (isBlank(words)) {
            return 0;
        }
        int wordLength = words.length();
        Set<Character> wordArray = new HashSet<Character>();
        for (char singleChar : words.toCharArray()) {
            wordArray.add(singleChar);
        }
        int singleWordCount = wordArray.size();
        return (int) ((float) singleWordCount / (float) wordLength * 100);
    }

    /**
     * 转为unicode编码
     *
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            result.append("\\u" + getHexString(chr1));
        }
        return result.toString();
    }

    private static String getHexString(int decimalNumber) {
        String hex = Integer.toHexString(decimalNumber);
        hex = hex.toLowerCase();
        while (hex.length() < 4) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static int getByteLengthOfString(String str) {
        int byteLength = (str.getBytes().length + str.length()) / 2;
        return byteLength;
    }

    public static String limitByByteLength(String subject, int limit) {
        //先限制大一点, 全部按照4byte来限制。
        int lengthLimit = limit/4;

        if (lengthLimit < 0) {
            return "";
        }

        if (lengthLimit >= subject.length()) {
            return subject;
        }

        return subject.substring(0,lengthLimit);
    }


    public static String subStringOrLess(String subject , int size){
        if(subject.length()<size){
            return subject;
        }else {
            return subject.substring(size);
        }
    }

    /**
     * 向前截取多少size
     * @param subject
     * @param size
     * @return
     */
    public static String subStringFormer(String subject , int size){
        if(subject.length()<size){
            return subject;
        }else {
            return subject.substring(0, size);
        }
    }

    /**
     * 将过长的ua过滤掉,默认512字符
     * @param ua
     * @return
     */
    public static String trimUa(String ua) {
        return StringUtils.substring(ua, 0, MAX_UA_LENGTH);
    }

    public static int parseToIntOrDefault(String subject,int other){
        int result = other;
        try {
           result= Integer.parseInt(subject);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 去掉字符串2端的空格
     * @param subject
     * @return
     */
    public static String trimString(String subject){
        String textContent = subject.trim();
        textContent = textContent.trim();
        while (textContent.startsWith("　")) {
            textContent = textContent.substring(1, textContent.length()).trim();
        }
        while (textContent.endsWith("　")) {
            textContent = textContent.substring(0, textContent.length() - 1).trim();
        }
        return textContent;
    }

    public static void checkNotEmptyStr(String s, @Nullable Object errorMessage) throws NullPointerException{
        if (isEmpty(s)){
            throw new NullPointerException(String.valueOf(errorMessage));
        }
    }

    public static void main(String[] args) {
        System.out.println(isNotBlank(""));
        System.out.println(isValidMobileNo("\u202D13699974402\u202C"));
        System.out.println("\u202D13699974402\u202C");
        System.out.println("18515888988");
        System.out.println(isValidPhone("(853)63106993"));
        System.out.println(isValidPhone("(853)121363106993"));
        System.out.println(isValidPhone("13641843675"));
    }
}
