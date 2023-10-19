package org.example;

import java.util.ArrayList;
import java.util.List;

public class Directory {
    ArrayList<Employee> employees;

    public Directory() {
        this.employees = new ArrayList<>();
    }

    public void add(Employee employee) {
        employees.add(employee);
    }

    public ArrayList<Employee> getEmployeesBySeniority(int seniority) {
        ArrayList<Employee> found = new ArrayList<>();
        employees.forEach(e -> {
            if (e.getSeniority() == seniority)
                found.add(e);
        });
        return found;
    }

    public ArrayList<String> getPhonesByName(String name) {
        ArrayList<String> phones = new ArrayList<>();
        employees.forEach(e -> {
            if (e.getName().equals(name))
                phones.add(e.getPhone());
        });
        return phones;
    }

    public ArrayList<Employee> getEmployeesById(int id) {
        ArrayList<Employee> found = new ArrayList<>();
        employees.forEach(e -> {
            if (e.getId() == id) found.add(e);
        });
        return found;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        employees.forEach(i -> sb.append(i.toString()).append('\n'));
        return sb.toString();
    }
}
