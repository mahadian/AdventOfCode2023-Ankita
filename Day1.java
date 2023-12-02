import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {
    static Map<String, Integer> mapOfNumbers = new HashMap<>();

    public static void main(String[] args){
        long total = 0;
        mapOfNumbers.put("one", 1);
        mapOfNumbers.put("two", 2);
        mapOfNumbers.put("three", 3);
        mapOfNumbers.put("four", 4);
        mapOfNumbers.put("five", 5);
        mapOfNumbers.put("six", 6);
        mapOfNumbers.put("seven", 7);
        mapOfNumbers.put("eight", 8);
        mapOfNumbers.put("nine", 9);
        File file = new File("/Users/ankitamahadik/Documents/input.txt");
        //File file = new File("inputTxt.txt");
        Scanner fileReader;
         try {
             fileReader = new Scanner(file);
             while(fileReader.hasNextLine()) {
                 String input = fileReader.nextLine();
                 total += getDigitsFromInput(input);
             }
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
        System.out.println(total);
    }


    private static long getDigitsFromInput(String inputString) {
        int digit1, digit2 = 0;
        int positionOfActualFirstDigit = 0;
        int positionOfActualSecondDigit = 0;

        inputString = inputString.toLowerCase().replaceAll("[^a-z0-9]", "");
        System.out.println(inputString);

        // Get first digit
        for(int i=0; i<inputString.length(); i++){
            if(Character.isDigit(inputString.charAt(i))){
                positionOfActualFirstDigit = i;
                break;
            }
        }

        String position1 = getFirstNumberSpelled(inputString.substring(0, positionOfActualFirstDigit));
        int indexStart = position1.contains("-")?Integer.parseInt(position1.substring(0, position1.indexOf("-"))):100;
        int indexEnd = Integer.parseInt(position1.substring(position1.indexOf("-")+1, position1.length()));
        digit1 = positionOfActualFirstDigit<indexStart ?
                Integer.parseInt(String.valueOf(inputString.charAt(positionOfActualFirstDigit))) :
                mapOfNumbers.get(inputString.substring(indexStart, indexEnd+1));
        System.out.println(digit1);

        // Get second digit
        for(int i=inputString.length()-1; i>=0; i--){
            if(Character.isDigit(inputString.charAt(i))){
                //digit2 = Integer.parseInt(String.valueOf(inputString.charAt(i)));
                positionOfActualSecondDigit = i;
                break;
            }
        }

        int indexStart2 = -100, indexEnd2 = -100;
        String position2 = getFirstNumberSpelledFromEnd(inputString, positionOfActualSecondDigit);
        System.out.println("Position returned : "+position2);
        if(position2.contains("-")){
            indexStart2 = Integer.parseInt(position2.substring(0, position2.indexOf("-")));
            indexEnd2 = Integer.parseInt(position2.substring(position2.indexOf("-")+1, position2.length()));
        }
         digit2 = positionOfActualSecondDigit>indexStart2 ?
                Integer.parseInt(String.valueOf(inputString.charAt(positionOfActualSecondDigit))) :
                mapOfNumbers.get(inputString.substring(indexStart2, indexEnd2+1));

        if(digit2==0){
            digit2=digit1;
        }
        System.out.println(digit2);

        System.out.println("Final digits = "+(digit1*10 + digit2));
        return (digit1*10 + digit2);
    }

    private static String getFirstNumberSpelledFromEnd(String inputString, int positionOfActualSecondDigit) {
        int index1 = positionOfActualSecondDigit, index2;
        List<String> order = new ArrayList<>();
        for(int i=positionOfActualSecondDigit; i<inputString.length()-2; i++){ //seven
            for(int j=i+2; j<inputString.length(); j++){
                if(mapOfNumbers.containsKey(inputString.substring(i,j+1)) && i>=index1){
                    index1 = i;
                    index2 = j;
                    order.add(index1 + "-" + index2);
                }
            }
        }
        return order.isEmpty()?"+100":order.get(order.size()-1);
    }

    private static String getFirstNumberSpelled(String inputString) {
        for(int i=0; i<inputString.length()-2; i++){
            for(int j=i+2; j<inputString.length(); j++){ //two
                if(mapOfNumbers.containsKey(inputString.substring(i,j+1))){
                    System.out.println("Position of " + inputString.substring(i,j+1) + " is " + i + ":" + j);
                    return i + "-" + j;
                }
            }
        }
        return "100";
    }
}


/*
*
twovgtprdzcjjzkq3ffsbcblnpq
two8sixbmrmqzrrb1seven
9964pfxmmr474
46one

*
*
* */
