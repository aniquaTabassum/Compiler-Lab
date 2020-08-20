import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        //this arraylist will be used to form the symbol table
        ArrayList<ArrayList<String>> modifiedTokenList = new ArrayList<ArrayList<String>>();
        String path = "/home/aniqua/inputFile";
        String inputFromFile = readFromFile(path);
        String[] temp = seperateInput(inputFromFile);
        ArrayList<String> separated = joinSpace(temp);

        createSymbolTable(separated, modifiedTokenList);
        display(modifiedTokenList);
    }

    public static void display(ArrayList<ArrayList<String>> modifiedTokenList)
    {
        int serial = 1;
        for (int i = 0; i < modifiedTokenList.size(); i++) {
            if (modifiedTokenList.get(i).size() == 0)
                continue;
            System.out.print(serial + ". ");
            serial++;
            for (String item :
                    modifiedTokenList.get(i)) {

                if (!item.equals(" "))
                    System.out.print(item + " ");
            }
            System.out.println();
        }

    }
    public static String readFromFile(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

            String stringFromFile;
            while ((stringFromFile = bufferedReader.readLine()) != null) {
                stringBuilder.append(stringFromFile).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static String[] seperateInput(String toSeparate) {

        String[] separated = toSeparate.split(" ");
        return separated;
    }

    public static ArrayList<String> joinSpace(String[] temp) {
        ArrayList<String> joinedString = new ArrayList<String>();
        for (int i = 0; i < temp.length - 1; i += 2) {
            String toAdd = "";
            for (int j = 1; j < temp[i].length(); j++) {
                toAdd += temp[i].charAt(j);
            }
            toAdd += ' ';

            for (int j = 0; j < temp[i + 1].length() - 1; j++) {
                toAdd += temp[i + 1].charAt(j);
                if ((j + 2 < temp[i + 1].length()) && (temp[i + 1].charAt(j + 2) == '\n'))
                    break;
            }
            joinedString.add(toAdd);
        }
        return joinedString;

    }

    public static void createSymbolTable(ArrayList<String> toModify, ArrayList<ArrayList<String>> modifiedTokenList) {

        for (int i = 0, j = 0; i < toModify.size(); i++) {
            if (i == 0 || toModify.get(i - 1).equals("sep ;") || (toModify.get(i - 1).equals("brc }") && toModify.get(i - 2).equals("sep ;"))) {
                //need to handle whatever will happen with } later
                if (toModify.get(i).equals("brc }"))
                    continue;
                String[] separated = toModify.get(i).split(" ");
                if (separated[0].equals("kw") && !separated[1].equals("return")) {
                    Boolean varOrFunc = isVar(i, separated, toModify);
                    i = insert(i, toModify, modifiedTokenList, varOrFunc);

                }
            }

        }
    }


    public static int insert(int currectIndex, ArrayList<String> toModify, ArrayList<ArrayList<String>> modifiedTokenList, Boolean varOrFunc) {
        modifiedTokenList.add(new ArrayList<String>());
        int index = modifiedTokenList.size() - 1;
        String scope = "global";
        if (varOrFunc == true) {
            ArrayList<String> toAdd = new ArrayList<String>();
            for (int l = 0; l < 5; l++)
                toAdd.add(" ");
            int toAddIndex = 0;
            while (!toModify.get(currectIndex).equals("sep ;")) {

                String[] separated = toModify.get(currectIndex).split(" ");

                if (separated[0].equals("kw")) {
                    toAdd.set(toAddIndex, separated[1]);
                    toAddIndex++;
                } else if (separated[0].equals("id")) {
                    toAdd.set(toAddIndex, "var");
                    toAddIndex++;
                    toAdd.set(toAddIndex, "global");
                    toAddIndex++;
                    toAdd.set(toAddIndex, separated[1]);
                    toAddIndex++;
                } else if (separated[0].equals("num")) {

                    toAdd.set(toAddIndex, separated[1]);
                }
                currectIndex += 1;
            }
            modifiedTokenList.get(index).add(toAdd.get(3));
            modifiedTokenList.get(index).add(toAdd.get(1));
            modifiedTokenList.get(index).add(toAdd.get(0));
            modifiedTokenList.get(index).add(toAdd.get(2));
            modifiedTokenList.get(index).add(toAdd.get(4));
            return currectIndex;
        } else {
            ArrayList<String> toAdd = new ArrayList<String>();
            String funcName = "";
            String[] separated = toModify.get(currectIndex).split(" ");
            toAdd.add("func");
            toAdd.add(separated[1]);
            toAdd.add("global");
            currectIndex++;
            separated = toModify.get(currectIndex).split(" ");
            //modifiedTokenList.get(index).add(separated[1]);
            modifiedTokenList.get(index).add(separated[1]);
            for (int l = 0; l < toAdd.size(); l++) {
                modifiedTokenList.get(index).add(toAdd.get(l));
            }
            funcName = separated[1];
            currectIndex++;

            while (currectIndex < toModify.size() && !toModify.get(currectIndex).equals("brc }")) {

                modifiedTokenList.add(new ArrayList<String>());
                index = modifiedTokenList.size() - 1;
                toAdd = new ArrayList<String>();
                for (int l = 0; l < 20; l++)
                    toAdd.add(" ");
                int toAddIndex = 0;
                while (currectIndex < toModify.size() && !toModify.get(currectIndex).equals("sep ;") && !toModify.get(currectIndex).equals("par )")) {

                    separated = toModify.get(currectIndex).split(" ");

                    if (separated[1].equals("return")) {
                        currectIndex += 2;
                        modifiedTokenList.remove(modifiedTokenList.size()-1);
                        index--;
                        continue;
                    }
                    if ( separated[0].equals("kw") && toModify.get(currectIndex+3).split(" ")[0].equals("id"))
                    {
                        while (currectIndex < toModify.size() && !toModify.get(currectIndex).equals("sep ;"))
                        {
                            currectIndex+=1;
                        }
                        currectIndex++;
                        modifiedTokenList.remove(modifiedTokenList.size()-1);
                        index--;
                        continue;
                    }
                    if (separated[0].equals("kw") && !separated[1].equals("void")) {
                        toAdd.set(toAddIndex, separated[1]);
                        toAddIndex++;
                        //modifiedTokenList.get(index).add(separated[1]);
                    }
                    else if(separated[1].equals("void")){
                        modifiedTokenList.remove(modifiedTokenList.size()-1);
                        index--;
                    }
                    else if (separated[0].equals("id")) {

                        toAdd.set(toAddIndex, "var");
                        toAddIndex++;
                        toAdd.set(toAddIndex, funcName);
                        toAddIndex++;
                        toAdd.set(toAddIndex, separated[1]);
                        toAddIndex++;

                    } else if (separated[0].equals("num")) {
                        // modifiedTokenList.get(index).add(separated[1]);
                        toAdd.set(toAddIndex, separated[1]);
                        toAddIndex++;
                    }


                    if (currectIndex < toModify.size() - 1)
                        currectIndex += 1;
                }

                modifiedTokenList.get(index).add(toAdd.get(3));
                modifiedTokenList.get(index).add(toAdd.get(1));
                modifiedTokenList.get(index).add(toAdd.get(0));
                modifiedTokenList.get(index).add(toAdd.get(2));
                modifiedTokenList.get(index).add(toAdd.get(4));
                currectIndex++;
            }
            return currectIndex;
        }

    }

    public static boolean isVar(int currentIndex, String[] separated, ArrayList<String> toModify) {
        if (toModify.get(currentIndex + 2).equals("op ="))
            return true;
        else
            return false;
    }

    public static void setScope(boolean varOrFunc, String scope, String functionName) {
        scope = varOrFunc ? "global" : functionName;
    }

    public static void findScope(String bracket, String scope, Stack<String> bracketStack) {
        if (bracket.equals("brc {"))
            bracketStack.push("{");
        else
            bracketStack.pop();
        if (bracketStack.isEmpty())
            scope = "global";

    }


}
