package by.epam.service;

import java.util.Base64;

public class Security {

    private final static String SALT = "*@#(D@@(G!+";
    private final static Base64.Encoder ENCODER = Base64.getEncoder();
    private final static Base64.Decoder DECODER = Base64.getDecoder();

    public String encryptData(String dataToEncrypt) {
        String finalPass = dataToEncrypt + SALT;
        return ENCODER.encodeToString(finalPass.getBytes());
    }

    public String decryptData(String dataToDecrypt) {
        byte[] decodedBytes = DECODER.decode(dataToDecrypt);
        String decrypted = new String(decodedBytes);
        return decrypted.substring(0, decrypted.length()-11);
    }
}
