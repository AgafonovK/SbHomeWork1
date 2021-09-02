package com.company;

public class Main {


    /**
     *  this is cost and rate: {type 100, type 200, type 300, type 400, etc..}
     */
    private static final double[] fuelCostType = new double[]{46.10, 48.90, 47.50, 48.90};

    private static final double[] fuelRateByType = new double[]{12.5, 12, 11.5, 20};

    private static String[][] as;
    private static final int numberOfFieldsData = 4;
    private static int numbersOfCarType;
    private static String[] types;
    private static String[] namesType;
    private static int[] qtyAutosByType;
    private static double[] expensiveByType;

    public static void main(String[] args) {
        // C(CODE_CAR)_гос номер-Пробег-(доп. параметр)
        /**
         * mass[N*car][4] CODE_CAR reg.numb.  probeg  (etc param)
         *   car 1          []       []        []        []
         *   car 2          []       []        []        []
         */
        String[] data = new String[]{"C100_1-100", "C200_123-120-1200", "C300_196-120-30", "C400_143-80-20",
                "C100_2-50", "C200_21-40-1000", "C300_296-200-45", "C400_222-10-20",
                "C100_3-10", "C200_123-170-1100", "C300_343-150-29", "C400_333-100-28",
                "C100_1-300", "C200_14-100-750", "C300_196-32-15"};

        as = new String[data.length][numberOfFieldsData];
        types = new String[data.length];

        init(data);

        //qty types
        numbersOfCarType = getQtyType();
        namesType = getAutoTypeNames();

        // how much each type autos
        qtyAutosByType = new int[numbersOfCarType];

        // fill qtyAutosByType[]
        getQtyAutosByType(as);

        //
        expensiveByType = expensiveByEachType();
        for (int i = 0; i < expensiveByType.length; i++) {
            double val = expensiveByType[i];
            System.out.println("Expensive by Type " + namesType[i] + " = " + val);
        }
        //
        double expensiveAllType = expensiveAllType();
        System.out.println("Expensive All type = " + expensiveAllType);

        getTypeAutoMaxExpensive();
        getTypeAutoMinExpensive();

        // get info by type
        for (int i = 0; i < numbersOfCarType; i++) {
            infoByType(namesType[i]);
        }

        // get sort by milleage
        for (int i = 0; i < numbersOfCarType; i++) {
            System.out.println("Sort by milleage, type car " + namesType[i] + ":");
            sortUpByMilleage(namesType[i]);
            System.out.println();
        }

        // get sort by etc param
        for (int i = 0; i < numbersOfCarType; i++) {
            System.out.println("Sort by etc param, type car " + namesType[i] + ":");
            sortUpByEtcParam(namesType[i]);
            System.out.println();
        }
        //parseData(data);

    }

    public static void init(String[] data) {
        for (int i = 0; i < data.length; i++) {
            String datum = data[i];
            String[] carParameter = datum.split("[_\\-]");
            types[i] = carParameter[0].substring(1);
            for (int j = 0; j < carParameter.length; j++) {
                if (j == 0) {
                    as[i][j] = carParameter[j].substring(1);
                } else {
                    as[i][j] = carParameter[j];
                }
            }
        }
    }

    public static void getQtyAutosByType(String[][] as) {
        boolean[] booleans = new boolean[as.length];
        for (int i = 0; i < as.length; i++) {
            int qtyAutoByType = 0;
            if (booleans[i]) {
                continue;
            }
            for (int j = 0; j < types.length; j++) {
                if (types[i].equals(types[j]) & !booleans[j]) {
                    booleans[j] = true;
                    qtyAutoByType = qtyAutoByType + 1;
                }
            }
            qtyAutosByType[i] = qtyAutoByType;
        }
    }

    public static String[] getAutoTypeNames() {
        boolean[] booleans = new boolean[types.length];

        String[] str = new String[numbersOfCarType];
        for (int i = 0; i < numbersOfCarType; i++) {
            if (booleans[i]) {
                continue;
            }
            str[i] = types[i];
            for (int j = 0; j < types.length; j++) {
                if (types[i].equals(types[j]) & !booleans[j]) {
                    booleans[j] = true;
                }
            }
        }
        return str;

    }

    public static int getQtyType() {
        //get qty of Types
        boolean[] booleans = new boolean[types.length];
        int numbCarType = 0;
        for (int i = 0; i < types.length; i++) {
            if (booleans[i]) {
                continue;
            }
            numbCarType = numbCarType + 1;
            for (int j = i + 1; j < types.length; j++) {
                if (types[i].equals(types[j]) & !booleans[j]) {
                    booleans[j] = true;
                }
            }
        }
        return numbCarType;
    }

    public static double expensiveAllType() {
        double expensiveall = 0;
        for (double d : expensiveByType) {
            expensiveall = expensiveall + d;
        }
        return expensiveall;
    }

    public static double[] expensiveByEachType() {
        double[] expensiveByType = new double[numbersOfCarType];
        for (String[] a : as) {
            for (int j = 0; j < 4; j++) {
                if (a[0].equals(namesType[j])) {
                    expensiveByType[j] = expensiveByType[j] + fuelCostType[j] * ((Double.parseDouble(a[2]) / 100) * fuelRateByType[j]);
                }
            }
        }

        return expensiveByType;
    }

    // тип авто имеющий наибольшую стоимость расходов
    public static void getTypeAutoMaxExpensive() {
        double max = expensiveByType[0];
        String name = "";
        expensiveByEachType();
        for (int i = 1; i < numbersOfCarType; i++) {
            if (max < expensiveByType[i]) {
                max = expensiveByType[i];
                name = namesType[i];
            }
        }
        System.out.println("Type auto " + name + " have max value = " + max);

    }

    // тип авто имеющий наименьшую стоимость расходов
    public static void getTypeAutoMinExpensive() {
        double min = expensiveByType[0];
        String name = "";
        expensiveByEachType();
        for (int i = 1; i < numbersOfCarType; i++) {
            if (min > expensiveByType[i]) {
                min = expensiveByType[i];
                name = namesType[i];
            }
        }
        System.out.println("Type auto " + name + " have max value = " + min);
    }

    // информация об авто определенного класса
    public static void infoByType(String type) {
        for (String[] a : as) {
            if (a[0].equals(type)) {
                if (a[3] == null) {
                    System.out.println("Type car is " + a[0] +
                            ". Registration number: " + a[1] +
                            ". Probeg: " + a[2] +
                            ". Etc parametr is: empty");
                } else {
                    System.out.println("Type car is " + a[0] +
                            ". Registration number: " + a[1] +
                            ". Probeg: " + a[2] +
                            ". Etc parametr is: " + a[3]);
                }
            }

        }

    }

    // TODO check similar value
    public static void sortUpByEtcParam(String name) {
        int val = 0;
        for (int i = 0; i < qtyAutosByType.length; i++) {
            if (name.equals(namesType[i])) {
                val = qtyAutosByType[i];
                break;
            }
        }
        String[][] typeToSort = new String[val][numberOfFieldsData];
        double[] etcParam = new double[val];
        for (int i = 0, x = 0; i < as.length; i++) {
            if (as[i][0].equals(name)) {
                for (int j = 0; j < numberOfFieldsData; j++) {
                    typeToSort[x][j] = as[i][j];
                }
                if (as[i][3] != null) {
                    etcParam[x] = Double.parseDouble(as[i][3]);
                    x = x + 1;
                }
            }
        }
        int flag =0;
        for(double d: etcParam){
            if (d==0){
                flag = flag + 1;
            }
        }

        if (flag==etcParam.length) {
            System.out.println("sorry all etc parameter is empty");
            infoByType(name);
            return;
        }

        quickSort(etcParam, 0, etcParam.length - 1);

        for (double v : etcParam) {
            for (String[] strings : typeToSort) {
                if (v == 0) {
                } else {
                    if (v == Double.parseDouble(strings[3])) {
                        for (int k = 0; k < numberOfFieldsData; k++) {
                            if (k == 0) {
                                System.out.print("Type car " + strings[k] + " ");
                            } else {
                                System.out.print(strings[k] + " ");
                            }
                        }
                        System.out.println();
                        break;
                    }
                }
            }
        }
    }

    public static void sortUpByMilleage(String name) {
        int val = 0;
        for (int i = 0; i < qtyAutosByType.length; i++) {
            if (name.equals(namesType[i])) {
                val = qtyAutosByType[i];
                break;
            }
        }
        String[][] typeToSort = new String[val][numberOfFieldsData];
        double[] milleage = new double[val];
        for (int i = 0, x = 0; i < as.length; i++) {
            if (as[i][0].equals(name)) {
                for (int j = 0; j < numberOfFieldsData; j++) {
                    typeToSort[x][j] = as[i][j];
                }
                milleage[x] = Double.parseDouble(as[i][2]);
                x = x + 1;

            }
        }
        int flag =0;
        for(double d: milleage){
            if (d==0){
                flag = flag + 1;
            }
        }

        if (flag==milleage.length) {
            System.out.println("sorry all milleage parameter is empty");
            infoByType(name);
            return;
        }
        quickSort(milleage, 0, milleage.length - 1);
        for (int i = 0; i < typeToSort.length; i++) {
            for (String[] strings : typeToSort) {
                if (milleage[i] == Double.parseDouble(strings[2])) {
                    for (int k = 0; k < numberOfFieldsData; k++) {
                        if (k == 0) {
                            System.out.print("Type car " + strings[k] + " ");
                        } else {
                            System.out.print(strings[k] + " ");

                        }
                    }
                    System.out.println();
                }
            }
        }
    }

    public static void quickSort(double[] dataToSort, int left, int right) {
        int leftFlag = left;
        int rightFlag = right;
        double pivot = dataToSort[(leftFlag + rightFlag) / 2];
        do {
            while (dataToSort[leftFlag] < pivot) {
                leftFlag++;
            }
            while (dataToSort[rightFlag] > pivot) {
                rightFlag--;
            }
            if (leftFlag <= rightFlag) {
                if (leftFlag < rightFlag) {
                    double tmp = dataToSort[leftFlag];
                    dataToSort[leftFlag] = dataToSort[rightFlag];
                    dataToSort[rightFlag] = tmp;
                }
                leftFlag++;
                rightFlag--;
            }
        } while (leftFlag <= rightFlag);

        if (leftFlag < right) {
            quickSort(dataToSort, leftFlag, right);
        }
        if (left < rightFlag) {
            quickSort(dataToSort, left, rightFlag);
        }
    }
}

