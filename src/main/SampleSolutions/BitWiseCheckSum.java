public class BitWiseCheckSum {
    public static String[] possStrings = {
              "wehceasadkufbfqqarfsgfdireabevvbzdtvegrpegzbtrpnfdzoxmepdoqlptrscp", "tbgwnycyludvmzururfjmumztuesbacodbncystyhjxtlcroxlzmtlglyqvtmeuliggtwfrsdhrnhgrr", 
              "tzuqiavysbyrnselxapxogvwguhtqurarnrdgihugfnidibplyqpdcwjohziqigahlr", 
              "wgisecofqqwbschwxyyivgisbdwydrwfkdxwejdtswweczbpxcymznrzylpdcpmgootpfejzooadehhia", 
              "azwxwdodoqmqdbwwrgztmwudlhqthycxjzwwwbsdrbyzexgsqksgoyonccbvwbgrkvpiqzkeikitxons", 
              "rpmcsyecywaunnpaiprbdobjipdtodotrfrdnabhkklzzbjffoybnqwfbgoeyfkvowxvodqulm"
          };
          public static byte checkSum = -88;


    public static void main(String[] args) {
        System.out.println(answer());
    }

    public static String answer() {
        for(int i = 0; i < possStrings.length; i++) {
          byte[] input = possStrings[i].getBytes();
              byte checksum = 0;
              for (byte cur_byte : input) {
                checksum = (byte) (((checksum & 255) >>> 1) + ((checksum & 1) << 7));
                checksum = (byte) ((checksum + cur_byte) & 255);
              }
          if (checksum == checkSum) {
            return possStrings[i];
          }
        }
        return null;
    }
}