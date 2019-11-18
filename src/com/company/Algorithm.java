package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Algorithm {
    public String myData = null;
    public static final int kElemennts = 3;

    List<Double> intsA;
    List<Double> intsB;
    List<Double> intsX;
    List<Double> intsAB = new ArrayList<>();

    List<Double> listAverageA;
    List<Double> listAverageB;
    List<Double> listABAverage = new ArrayList<>();

    List<Double> listPomArray;
    List<Integer> listPomImportant;
    List<Integer> compareIndex;
    List<Double> kElementsList;

    List<Double> kElementsArray = new ArrayList<>();
    List<Double> kWithoutElementsArray = new ArrayList<>();
    List<Double> newAverageAlgorithm = new ArrayList<>();


    public int countLineA;
    public int countLineB;
    public int numberCech;

    List<List<Double>> lists;
    List<List<Double>> listsResult;
    List<List<Double>> listsResult1;
    //List<List<Double>> newAverageAlgorithm = new ArrayList<List<Double>>();


    Algorithm()
    {
        intsA = new ArrayList<>();
        intsB = new ArrayList<>();
        intsX = new ArrayList<>();
        listAverageA = new ArrayList<>();
        listAverageB = new ArrayList<>();
    }
    public static void main(String[] args) {
        Algorithm call = new Algorithm();

        call.readFile();
        call.createList();
        call.distanceAlgorithm();
        //call.calculate();
    }
    public String readFile() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("filename.txt"))) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        myData = sb.toString();
        return myData;
    }
    public void createList() {
        String[] tmp1 = myData.split("\n");
        for (int i = 0; i < tmp1.length; i++) {
            myData = tmp1[i];
            myData.trim();
            if (myData.startsWith("A")) {
                countLineA++;
                patterNumber(intsA);
            }
            if (myData.startsWith("B")) {
                countLineB++;
                patterNumber(intsB);
            }
            if (myData.startsWith("X")) {
                patterNumber(intsX);
            }
        }
        System.out.println(intsA);
        System.out.println(intsB);
        System.out.println(intsX);
    }
    public List<Double> patterNumber(List<Double> array1) {
        numberCech=0;
        String[] line1 = myData.split("\n", 2);
        Pattern p = Pattern.compile("[0-9]+(\\.){0,1}[0-9]*");
        Matcher m = p.matcher(line1[0]);
        while (m.find()) {
            String t = m.group();
            numberCech++;
            array1.add(Double.valueOf(t));
        }
        return array1;
    }
    public void calculateDetails(List<Double> array1, List<Double> array2) {
        List<Double> array3 = new ArrayList<>();
        double distance = 0;
        int j = 0;
        do {
            for (int i = 0; i < array1.size(); i += 3) {
                distance = sqrt(pow((array2.get(j) - array1.get(i)), 2) + pow((array2.get(j + 1) - array1.get(i + 1)), 2) + pow((array2.get(j + 2) - array1.get(i + 2)), 2));
                array3.add(distance);
            }
            if(array1.size() == numberCech*2)
            {
                calculateDistanceAverage(array3);
            }else{
                equalResultValue(array3);
            }
            j += 3;
            //System.out.println(array3);
            array3.clear();
        } while (array2.size() > j == true);
    }
    public List<Double> countAverage(List<Double> countList, int numberLine){
        int myI = 0;
        List<Double> listAverage = new ArrayList<>();
        do {
            double average = 0;
            for (int i = myI; i < countList.size(); i += numberCech) {
                average += countList.get(i);
            }
            average = average / numberLine;
            listAverage.add(average);
            myI++;
        }while(myI < numberCech);
        //System.out.println(listAverage);
        return listAverage;
    }
    public void calculateDistanceAverage(List<Double> pomList){
        List<Double> pomListA = new ArrayList<>();
        List<Double> pomListB = new ArrayList<>();
        pomListA.add(pomList.get(0));
        pomListB.add(pomList.get(1));
        System.out.println("******************Porównanie odległości dla średnich i określenie zbioru:****************");
        nnMethod(pomListA,pomListB);
    }
    public void calculate() {
        intsAB.addAll(intsA);
        intsAB.addAll(intsB);
        calculateDetails(intsAB, intsX);
        System.out.println("***************Średnie wyliczone dla cech macierzy A i B:***************");
        listAverageA = countAverage(intsA, countLineA);
        listAverageB = countAverage(intsB, countLineB);
        listABAverage.addAll(listAverageA);
        listABAverage.addAll(listAverageB);
        System.out.println("AiB połączone"+listABAverage);
        calculateDetails(listABAverage, intsX);
    }
    private void equalResultValue(List<Double> karolhaha) {
        System.out.println("******************Wyniki dla metody k-NN****************");
        List<Double> pomListA = new ArrayList<>();
        List<Double> pomListB = new ArrayList<>();
        for(int i = 0; i < countLineA; i++) {
            pomListA.add(karolhaha.get(i));
        }
        for(int i = countLineA; i < karolhaha.size(); i++) {
            pomListB.add(karolhaha.get(i));
        }
        Collections.sort(pomListA);
        Collections.sort(pomListB);
        System.out.println("List A"+ pomListA);
        System.out.println("List B" +pomListB);
        int j = 0;
        int c = 0;
        int numberA = 0;
        int numberB = 0;
        int numberElements =3;
        for (int i = 0; i < numberElements; i++) {
            if (pomListA.get(j) < pomListB.get(c)) {
                numberA++;
                System.out.println("A: " + pomListA.get(j));
                j++;
            } else {
                numberB++;
                System.out.println("B: " + pomListB.get(c));
                c++;
            }
        }
        if (numberA > numberB) {
            System.out.println("**********Wynik: "+numberA + " elementy należą do zbioru A na " + numberElements + " elementy.");
        } else {
            System.out.println("**********Wynik: "+numberB + " elementy należą do zbioru B na " + numberElements + " elementy.");
        }
        System.out.println("******************Wynik dla metody NN****************");
            nnMethod(pomListA,pomListB);
    }
    public void nnMethod(List<Double> pomListA, List<Double> pomListB){
        if(Collections.min(pomListA) < Collections.min(pomListB)){
            System.out.println("Punkt należy do zbioru A");
        }else{
            System.out.println("Punkt należy do zbioru B");
        }
    }
    public List<Double> kElementsAlgorithm(List<Double> arrayList){
        listPomArray = new ArrayList<>();
        listPomImportant = new ArrayList<>();
        compareIndex = new ArrayList<>();
        kElementsList = new ArrayList<>();
        for(int i = 0; i < arrayList.size(); i+=numberCech){
            listPomImportant.add(i);
        }
        for(int i = 0;i < kElemennts ; i++) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(listPomImportant.size());
            while(compareIndex.contains(randomIndex)) {
                int randomIndex1 = rand.nextInt(listPomImportant.size());
                randomIndex = randomIndex1;
            }
            compareIndex.add(randomIndex);
            int a = listPomImportant.get(randomIndex);
            listPomArray = arrayList.subList(a, a + numberCech);
            kElementsList.addAll(listPomArray);
        }
        return kElementsList;
    }
    public List<Double> removePunktsFromList(List<Double> array1){
        List<Double> intsWithoutkElements;
        int tmp=0;
        List<Integer> indexToRemove = new ArrayList<>();
        for (int i =0; i<compareIndex.size();i++){
            tmp=numberCech;
            int b = compareIndex.get(i)*tmp;
            while(tmp > 0){
                indexToRemove.add(b + tmp);
                tmp--;
            }
        }
        Collections.sort(indexToRemove);
        Collections.reverse(indexToRemove);
        for(int i=0; i< indexToRemove.size();i++){
            int remove = indexToRemove.get(i);
            array1.remove(remove-1);
        }
        intsWithoutkElements = array1;
        return intsWithoutkElements;
    }
    public List<List<Double>> calculateElementsAlgorithm(List<Double> array1, List<Double> array2){
        lists = new ArrayList<List<Double>>();

        List<Double> array4 = new ArrayList<>();
        for (int i = 0; i < kElemennts; i++) {
            List<Double> list = new ArrayList<>();
            lists.add(list);
        }
        double distance;
        double distanceTmp = 0;
        double distanceSqrt;
        for (int i = 0; i < array1.size(); i += numberCech) {
            for (int k = 0; k < array2.size(); k += numberCech) {
                for (int j = 0; j < numberCech; j++) {
                    distance = pow((array1.get(i) - array2.get(k)), 2);
                    distanceTmp= distanceTmp + distance;
                }
                distanceSqrt = sqrt(distanceTmp);
                array4.add(distanceSqrt);
                distanceTmp = 0;
            }
            System.out.println("Co mamy w tablicy: "+array4);

            for(int t=0; t<array4.size();t++) {
                if (Collections.min(array4) == array4.get(t)) {
                    lists.get(t).addAll(array1.subList(i, i + numberCech));
                }
            }
                array4.clear();
            }
        System.out.println("Nowy podział: ");
        System.out.println(lists.get(0));
        System.out.println(lists.get(1));
        System.out.println(lists.get(2));
        return lists;
    }
    public List<List<Double>> sumAverageAlgorithm(List<Double> arraykElementsAverage){
        listsResult = new ArrayList<List<Double>>();
        for (int i = 0; i < kElemennts; i++) {
            List<Double> list = new ArrayList<>();
            listsResult.add(list);
        }
        List<Double> array4 = new ArrayList<>();
        int t = 0;
        for(int j=0; j<kElemennts;j++) {
            int myI = 0;
            do {
                double average = 0;
                for (int i = myI; i < lists.get(j).size(); i += numberCech) {
                    average += lists.get(j).get(i);
                }
                    average = average/(lists.get(j).size()/numberCech);
                    average = (average + arraykElementsAverage.get(t))/2;
                    t++;
                    array4.add(average);
                listsResult.get(j).addAll(array4);
                myI++;
                array4.clear();
            }while(myI < numberCech);
        }
        System.out.println(listsResult);
        lists.clear();
        return listsResult;
    }
// Ta metoda porównuje listę z K-elementami i obliczone nowe średnie,
// jeśli isNan dodaje do nowej tablicy stare elementy, jesli jest różne od K-Elementu to bierze nową srednią i zwraca nową listę.
    public List<Double> newListAverageOperation() {
        int t =0;
        for (int j=0;j<kElemennts;j++) {
            for (int i =0; i<listsResult.get(j).size();i++) {
//                double tmp = kElementsArray.get(t);
                if(listsResult.get(j).get(i).isNaN()) {
                    newAverageAlgorithm.add(kElementsArray.get(t));
                }else
                {
                    if(listsResult.get(j).get(i) != kElementsArray.get(t)){
                        newAverageAlgorithm.add(listsResult.get(j).get(i));
                        kWithoutElementsArray.add(kElementsArray.get(t));
                    }
                }
                t++;
            }
        }
        System.out.println("Moje nowe średnie: "+ newAverageAlgorithm);
        //System.out.println("Moja tablica z dodaniem k elmentów: "+ kWithoutElementsArray);
        listsResult.clear();
        return newAverageAlgorithm;
    }
    public List<Double> newListAverageOperation1() {
        int t =0;
        for (int j=0;j<kElemennts;j++) {
            for (int i =0; i<listsResult.get(j).size();i++) {
//                double tmp = kElementsArray.get(t);
                if(listsResult.get(j).get(i).isNaN()) {
                    newAverageAlgorithm.add(kElementsArray.get(t));
                }else
                {
                    if(listsResult.get(j).get(i) != listsResult1.get(j).get(i)){
                        newAverageAlgorithm.add(listsResult.get(j).get(i));
//                        kWithoutElementsArray.add(kElementsArray.get(t));
                    }
                }
                t++;
            }
        }
        System.out.println("Moje nowe średnie: "+ newAverageAlgorithm);
        //System.out.println("Moja tablica z dodaniem k elmentów: "+ kWithoutElementsArray);
        listsResult.clear();
        return newAverageAlgorithm;
    }
    public void addKElementsToMatrixFromAverage(){


    }



    //Wracamy punktu do tablicy k Without

    public void distanceAlgorithm()
    {
        listsResult1 = new ArrayList<List<Double>>();
        for (int i = 0; i < kElemennts; i++) {
            List<Double> list = new ArrayList<>();
            listsResult1.add(list);
        }


        kElementsArray = kElementsAlgorithm(intsA);
        System.out.println("Moja tablica k-elementów: "+ kElementsArray);
        kWithoutElementsArray = removePunktsFromList(intsA);
        System.out.println("Tablica bez dodania k elementów: "+kWithoutElementsArray);
        calculateElementsAlgorithm(kWithoutElementsArray,kElementsArray);
        sumAverageAlgorithm(kElementsArray);

        newListAverageOperation();
        System.out.println("Moja tablica z dodaniem k elmentów: "+ kWithoutElementsArray);

        calculateElementsAlgorithm(kWithoutElementsArray, newAverageAlgorithm);
        listsResult1 = sumAverageAlgorithm(newAverageAlgorithm);

        newAverageAlgorithm.clear();
        newListAverageOperation();
    }
}

