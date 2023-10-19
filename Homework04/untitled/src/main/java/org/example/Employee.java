package org.example;

public class Employee {
    private int id;
    private String phone;
    private String name;
    private int seniority;

    public Employee(int id, String phone, String name, int seniority) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.seniority = seniority;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public int getSeniority() {
        return seniority;
    }

    @Override
    public String toString() {
        return String.format("%d\t%s\t%s\t\t\t%d", id, phone, name, seniority);
    }
}
