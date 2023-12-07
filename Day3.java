import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;

public class Day3 {
    static List<Integer> numbers = new ArrayList<>();
    static char[][] engineInput;

    public static void main(String[] args){

        int i=0;
        File file = new File("/Users/ankitamahadik/Documents/input_3.txt");
        //File file = new File("inputTxt.txt");
        Scanner fileReader;
        try {
            fileReader = new Scanner(file);

            // initialize 2d array size
            int rows = 0, cols = 0;
            while(fileReader.hasNextLine()) {
                rows++;
                cols = fileReader.nextLine().length();
            }
            System.out.println("Total rows - cols are " + rows + " - " + cols);
            engineInput = new char[rows][cols];

            // feed data in 2d array
            fileReader = new Scanner(file);
            while(fileReader.hasNextLine()) {
                char[] inputLine = fileReader.nextLine().toCharArray();
                for (int j = 0; j < cols; j++) {
                    engineInput[i][j] = inputLine[j];
                }
                i++;
            }

            //System.out.println(calculateEnginePartsTotal());
            System.out.println(calculateGearRatiosTotal());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static long calculateGearRatiosTotal() {
        for (int i = 0; i < engineInput.length; i++) {
            for (int j = 0; j < engineInput[0].length; j++) {
                Set<Integer> gearRatioList = new HashSet<>();
                if(Pattern.matches("\\*", Character.toString(engineInput[i][j]))){
                    if(Character.isDigit(engineInputIfExists(i,j+1))) {
                        gearRatioList.add(getNumber(i, j+1));
                    } // add same 'if' for rest of the positions
                    if(Character.isDigit(engineInputIfExists(i,j-1))) {
                        gearRatioList.add(getNumber(i, j-1));
                    }
                    if(Character.isDigit(engineInputIfExists(i+1,j))) {
                        gearRatioList.add(getNumber(i+1, j));
                    }
                    if(Character.isDigit(engineInputIfExists(i-1,j))) {
                        gearRatioList.add(getNumber(i-1, j));
                    }
                    if(Character.isDigit(engineInputIfExists(i+1,j+1))) {
                        gearRatioList.add(getNumber(i+1, j+1));
                    }
                    if(Character.isDigit(engineInputIfExists(i-1,j+1))) {
                        gearRatioList.add(getNumber(i-1, j+1));
                    }
                    if(Character.isDigit(engineInputIfExists(i-1,j-1))) {
                        gearRatioList.add(getNumber(i-1, j-1));
                    }
                    if(Character.isDigit(engineInputIfExists(i+1,j-1))) {
                        gearRatioList.add(getNumber(i+1, j-1));
                    }
                }

                if (gearRatioList.size()==2) {
                    System.out.println("Gear ratio = " + gearRatioList);
                    numbers.add(gearRatioList.stream().reduce(1, (a, b) -> a * b));
                }
            }
        }

        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    private static int getNumber(int row, int col) {
        char[] digit = new char[10];
        int charIterator = 4;
        digit[charIterator] = engineInput[row][col];

        // backward
        for(int k=col-1;k>=0;k--){
            charIterator--;
            if (isSpecialSymbolPresent(engineInput[row][k]) || checkDot(engineInput[row][k])) {
                break;
            }
            digit[charIterator] = engineInput[row][k];
        }

        // forward
        charIterator = 5;
        for(int k=col+1;k<engineInput[0].length;k++){
            if (isSpecialSymbolPresent(engineInput[row][k]) || checkDot(engineInput[row][k])) {
                break;
            }
            digit[charIterator] = engineInput[row][k];
            charIterator++;
        }

        StringBuilder str = new StringBuilder();
        for(int i = 0; i< digit.length; i++) {
            if (Character.isDigit(digit[i])) {
                str.append(digit[i]);
            }
        }

        return Integer.parseInt(str.toString());
    }

    private static long calculateEnginePartsTotal() {
        for (int i = 0; i < engineInput.length; i++) {
            for (int j = 0; j < engineInput[0].length; j++) {
                if(Character.isDigit(engineInput[i][j])) {
                    if (
                            isSpecialSymbolPresent(engineInputIfExists(i,j+1)) ||
                            isSpecialSymbolPresent(engineInputIfExists(i,j-1)) ||
                            isSpecialSymbolPresent(engineInputIfExists(i+1, j)) ||
                            isSpecialSymbolPresent(engineInputIfExists(i-1, j))  ||
                            isSpecialSymbolPresent(engineInputIfExists(i+1,j+1)) ||
                            isSpecialSymbolPresent(engineInputIfExists(i-1, j+1)) ||
                            isSpecialSymbolPresent(engineInputIfExists(i-1, j-1)) ||
                            isSpecialSymbolPresent(engineInputIfExists(i+1, j-1))
                    ) {
                        String value = getNumberAndUpdatedPosition(i, j);
                        System.out.println(value);
                        numbers.add(Integer.parseInt(value.split("-")[0]));
                        // update j to value after the number ends
                        j = Integer.parseInt(value.split("-")[1]);
                    }
                }
            }
        }

        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    private static char engineInputIfExists(int i, int j) {
        if(i<0 || i>=engineInput.length)
            return '.';
        if(j<0 || j>=engineInput[0].length)
            return '.';
        return engineInput[i][j];
    }

    private static String getNumberAndUpdatedPosition(int row, int col) {
        char[] digit = new char[10];
        int charIterator = 4;
        digit[charIterator] = engineInput[row][col];

        // backward
        for(int k=col-1;k>=0;k--){
            charIterator--;
            if (isSpecialSymbolPresent(engineInput[row][k]) || checkDot(engineInput[row][k])) {
                break;
            }
            digit[charIterator] = engineInput[row][k];
        }

        // forward
        charIterator = 5;
        // need to update vale of j in parent loop
        int j = col;
        for(int k=col+1;k<engineInput[0].length;k++){
            if (isSpecialSymbolPresent(engineInput[row][k]) || checkDot(engineInput[row][k])) {
                break;
            }
            digit[charIterator] = engineInput[row][k];
            charIterator++;
            j = k+1;
        }

        System.out.println("j to be sent to parent loop = " + j);

        StringBuilder str = new StringBuilder();
        for(int i = 0; i< digit.length; i++) {
            if (Character.isDigit(digit[i])) {
                str.append(digit[i]);
            }
        }

        return Integer.parseInt(str.toString()) + "-" + j;
    }

    private static boolean checkDot(char c) {
        return Pattern.matches("\\.", Character.toString(c));
    }

    private static boolean isSpecialSymbolPresent(char ch) {
        return !Character.isDigit(ch) && !checkDot(ch); // use regex matching for dots
    }
}
