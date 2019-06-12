package com.ibiker.ibiker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class JWTSecretKeyProvider {
	
	private static final String SECRET_FILE_NAME = "jwt_secret_key";
	
	public static String getSecretKey() {
        final File file = new File(SECRET_FILE_NAME + ".txt");
        StringBuilder secretKey = new StringBuilder();

        try {
            final FileReader fileReader = new FileReader(file);
            final BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((bufferedReader.readLine()) != null) {
                secretKey.append(bufferedReader.readLine());
            }
            
            bufferedReader.close();  
        } catch (Exception e) {
            e.printStackTrace();
        }

        return secretKey.toString();
    }
}