package br.com.gustagols.regescweb.dto;

import br.com.gustagols.regescweb.models.Professor;
import br.com.gustagols.regescweb.models.StatusProfessor;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

//classe DTO = Data Transfer Object;
public class RequisicaoFormProfessor {
    @NotBlank
    @NotNull
    private String nome;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal salario;
    private StatusProfessor statusProfessor;


    //Nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    //Salario
    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }


    //StatusProfessor
    public StatusProfessor getStatusProfessor() {
        return statusProfessor;
    }

    public void setStatusProfessor(StatusProfessor statusProfessor) {
        this.statusProfessor = statusProfessor;
    }


    public Professor toProfessor(){
        Professor professor = new Professor();
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.statusProfessor);

        return professor;
    }

    public Professor toProfessor(Professor professor){
        professor.setNome(this.nome);
        professor.setSalario(this.salario);
        professor.setStatusProfessor(this.statusProfessor);
        return professor;

    }

    public void fromProfessor(Professor professor){
        this.nome = professor.getNome();
        this.salario = professor.getSalario();
        this.statusProfessor = professor.getStatusProfessor();
    }

    @Override
    public String toString() {
        return "RequisicaoNovoProfessor{" +
                "nome='" + nome + '\'' +
                ", salario=" + salario +
                ", statusProfessor=" + statusProfessor +
                '}';
    }
}