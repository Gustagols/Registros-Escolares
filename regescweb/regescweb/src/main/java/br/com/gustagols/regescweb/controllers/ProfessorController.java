package br.com.gustagols.regescweb.controllers;

import br.com.gustagols.regescweb.dto.RequisicaoFormProfessor;
import br.com.gustagols.regescweb.models.Professor;
import br.com.gustagols.regescweb.models.StatusProfessor;
import br.com.gustagols.regescweb.repositories.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/professores")
    public ModelAndView index(){
        List<Professor> professores = this.professorRepository.findAll();
        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }

    @GetMapping("/professores/new")
    public ModelAndView nnew(RequisicaoFormProfessor requisicao){
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());
        return mv;
    }

    @PostMapping("/professores")
    public ModelAndView create(@Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){
        System.out.println(requisicao);

        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }
        else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor);
            return new ModelAndView("redirect:/professores");
            //return new ModelAndView("redirect:/professores/" + professor.getId());
        }
    }

    @GetMapping("/professores/{id}")
    public ModelAndView show(@PathVariable Long id){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();

            ModelAndView mv = new ModelAndView("professores/show");
            mv.addObject("professor", professor);
            return mv;
        }
        else{
            return new ModelAndView("redirect:/professores");
        }
    }

    @GetMapping("/professores/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, RequisicaoFormProfessor requisicao){
        Optional<Professor> optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();
            requisicao.fromProfessor(professor);

            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", professor.getId());
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;

        }
        else{
            return new ModelAndView("redirect:/professores");
        }
    }

    @PostMapping("/professores/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid RequisicaoFormProfessor requisicao, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            ModelAndView mv = new ModelAndView("professores/edit");
            mv.addObject("professorId", id);
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }
        else {
            Optional<Professor> optional = this.professorRepository.findById(id);

            if(optional.isPresent()) {
                Professor professor = requisicao.toProfessor(optional.get());
                this.professorRepository.save(professor);
                return new ModelAndView("redirect:/professores/" + professor.getId());

            }

         else {
            return new ModelAndView("redirect:/professores");
            }
        }
    }

    @GetMapping("/professores/{id}/delete")
    public String delete(@PathVariable Long id){

        try {
            this.professorRepository.deleteById(id);
            return "redirect:/professores";
        }
        catch(EmptyResultDataAccessException e) {
            System.out.println(e);
            return "redirect:/professores";
        }
    }
}
