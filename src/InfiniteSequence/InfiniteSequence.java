/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InfiniteSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 *
 * @author Alexey
 */
public class InfiniteSequence {

    public static final int SEQUENCE_LENGTH = 50;

    public static int[] readIntArray(BufferedReader reader, int length) throws IOException {
        String s;
        int[] array = null;
        try {
            s = reader.readLine();
            array = s.chars()
                    .map(x -> x - '0')
                    .toArray();
            if (array.length > length) {
                throw new IOException("Too long sequence: " + array.length);
            }
        } catch (IOException e) {
            throw e;
        }
        return array;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int[] src = InfiniteSequence.readIntArray(reader, SEQUENCE_LENGTH);

            StringSequence sequence = new StringSequence(src);

         //   sequence.findPosition();

            BigInteger position = sequence.findPosition();
            
            System.out.println(position);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
