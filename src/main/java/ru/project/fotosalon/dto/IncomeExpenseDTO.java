package ru.project.fotosalon.dto;

public class IncomeExpenseDTO {

    private String date;
    private double income;
    private double expense;

    public IncomeExpenseDTO() {
    }

    public IncomeExpenseDTO(String date, double income, double expense) {
        this.date = date;
        this.income = income;
        this.expense = expense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    @Override
    public String toString() {
        return "IncomeExpenseDTO{" +
                "date='" + date + '\'' +
                ", income=" + income +
                ", expense=" + expense +
                '}';
    }
}
