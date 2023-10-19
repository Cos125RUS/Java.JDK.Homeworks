package org.example;

public class Main {
    public static void main(String[] args) {
        Directory directory = createDirectory(10);
        System.out.println(directory);
        directory.add(new Employee(11, "+79245675234", "Антонина", 11));
        directory.add(new Employee(12, "+79278675297", "Дмитрий", 11));
        directory.add(new Employee(13, "+79124562347", "Джамшут", 11));
        System.out.println(directory.getEmployeesBySeniority(11));
        System.out.println(directory.getPhonesByName("Антонина"));
        System.out.println(directory.getEmployeesById(12));
    }

    private static Directory createDirectory(int count) {
        String[] names = {"Иван", "Марина", "Семён", "Ольга", "Александр", "Дарья", "Евгений", "Татьяна"};
        Directory directory = new Directory();
        for (int i = 0; i < count; i++) {
            StringBuilder phone = new StringBuilder();
            phone.append("+79");
            for (int j = 0; j < 9; j++) {
                phone.append(Integer.valueOf((int) (Math.random() * 10)).toString());
            }
            directory.add(new Employee(i+1, phone.toString(), names[(int) (Math.random()*10%8)],
                    (int) (Math.random()*100%50)));
        }
        return directory;
    }
}