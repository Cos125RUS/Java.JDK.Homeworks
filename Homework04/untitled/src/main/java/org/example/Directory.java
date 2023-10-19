package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        for (Employee employee: employees) {
            if (employee.getSeniority() == seniority)
                found.add(employee);
        }
        return found;
    }

    public ArrayList<String> getPhonesByName(String name) {
        ArrayList<String> phones = new ArrayList<>();
        for (Employee employee: employees) {
            if (employee.getName().equals(name))
                phones.add(employee.getPhone());
        }
        return phones;
    }

    public ArrayList<Employee> getEmployeesById(int id) {
        ArrayList<Employee> found = new ArrayList<>();
        for (Employee employee: employees) {
            if (employee.getId() == id)
                found.add(employee);
        }
        return found;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Employee employee: employees) {
            sb.append(employee).append('\n');
        }
        return sb.toString();
    }
}
