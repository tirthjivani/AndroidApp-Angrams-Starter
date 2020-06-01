/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private HashSet<String> wordset = new HashSet<>();
    private HashMap<String, ArrayList<String>> keyset = new HashMap();
    private ArrayList<String> wordlist = new ArrayList<>();
    private Random random = new Random();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            ArrayList<String> valid = new ArrayList<>();

            String word = line.trim();
            String sortedword = sortString(word);
            wordset.add(word);
            wordlist.add(word);

            if(keyset.containsKey(sortedword)){
                valid = keyset.get(sortedword);
                valid.add(word);
            }
            else{
                valid = new ArrayList<>();
                valid.add(word);
                keyset.put(sortedword, valid);
            }

        }
    }

    public static String sortString(String inputString)
    {
        // convert input string to char array
        char tempArray[] = inputString.toCharArray();

        // sort tempArray
        Arrays.sort(tempArray);

        // return new sorted string
        return new String(tempArray);
    }

    public boolean isGoodWord(String word, String base) {
        if(word.contains(word) && !(word.contains(base)))
            return true;

        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String word = sortString(targetWord);
        result = keyset.get(word);
        result.remove(targetWord);
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0 ; i<25 ; i++){
            result.add(word);
        }
        return result;
    }

    public String pickGoodStarterWord() {

        int x = random.nextInt(wordlist.size());
        String s = wordlist.get(x);
        while(keyset.get(sortString(wordlist.get(x))).size()<MIN_NUM_ANAGRAMS) {
            x++;
            x%=wordlist.size();
        }
        return wordlist.get(x);
    }
}
