package controllers;
import com.exemplo.gerenciadortarefas.model.Tarefa;
import com.exemplo.gerenciadortarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        return ResponseEntity.ok(tarefaRepository.save(tarefa));
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        return ResponseEntity.ok(tarefaRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefa.setNome(tarefaAtualizada.getNome());
                    tarefa.setPrazo(tarefaAtualizada.getPrazo());
                    tarefa.setStatus(tarefaAtualizada.getStatus());
                    return ResponseEntity.ok(tarefaRepository.save(tarefa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        return tarefaRepository.findById(id)
                .map(tarefa -> {
                    tarefaRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

// config/SecurityConfig.java