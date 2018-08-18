package by.epam.utils.security;

import java.util.Random;

public class PasswordGenerator {

    public String generate() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?").toCharArray();
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int length = random.nextInt(3) + 8;
        for (int x = 0; x < length; x++) {
            stringBuilder.append(possibleCharacters[random.nextInt(possibleCharacters.length)]);
        }
        return stringBuilder.toString();
    }
}
