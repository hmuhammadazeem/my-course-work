package utils;

import java.util.ArrayList;

public class TokensParser {
    public String[] parser(String line){
        String[] tokens = line.split("\\\\s+");
        return tokens;
    }
}
