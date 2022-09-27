package br.com.gerenciador.pessoa.controller;

import br.com.gerenciador.exception.ValidationException;
import br.com.gerenciador.pessoa.io.PessoaInput;
import br.com.gerenciador.pessoa.io.PessoaOutput;
import br.com.gerenciador.pessoa.mapper.PessoaMapper;
import br.com.gerenciador.pessoa.model.Pessoa;
import br.com.gerenciador.pessoa.model.PessoaService;
import br.com.gerenciador.pessoa.validator.PessoaValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "Pessoa", description = "Criação, Listagem, Edição e Exclusão de Pessoa")
@RestController
@RequestMapping("/api/v1/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;
    private final PessoaMapper pessoaMapper;
    private final PessoaValidator pessoaValidator;

    public PessoaController(PessoaService pessoaService, PessoaMapper pessoaMapper, PessoaValidator pessoaValidator) {
        this.pessoaService = pessoaService;
        this.pessoaMapper = pessoaMapper;
        this.pessoaValidator = pessoaValidator;
    }

    @InitBinder
    public void createInputValidator(WebDataBinder dataBinder) {
        if (dataBinder.getTarget() != null && PessoaInput.class.equals(dataBinder.getTarget().getClass())) {
            dataBinder.addValidators(pessoaValidator);
        }
    }

    @Operation(summary = "Cadastro", description = "Cadastrar nova pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = {
                    @Content(schema = @Schema(implementation = ValidationException.class))
            }),
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PessoaOutput> salvar(@Valid @RequestBody PessoaInput input, BindingResult result) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result);
        }

        Pessoa pessoa = pessoaMapper.from(input);
        pessoa = this.pessoaService.save(pessoa);

        return ResponseEntity.ok(pessoaMapper.from(pessoa));
    }

    @Operation(summary = "Listagem", description = "Listar pessoas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<Page<PessoaOutput>> buscar(@Parameter(hidden = true) Pageable pageable) {
        Page<Pessoa> pessoas = this.pessoaService.findAll(pageable);
        return ResponseEntity.ok(pessoas.map(pessoaMapper::from));
    }

    @Operation(summary = "Edição", description = "Editar pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cadastro não encontrado", content = @Content)
    })
    @PatchMapping("/{identificador}/{nome}")
    public ResponseEntity<PessoaOutput> editar(@PathVariable String identificador, @PathVariable String nome) {
        Optional<Pessoa> pessoaOptional = this.pessoaService.findByIdentificador(identificador);
        if (pessoaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pessoa pessoa = pessoaService.update(identificador, nome);
        return ResponseEntity.ok(pessoaMapper.from(pessoa));
    }

    @Operation(summary = "Deletar", description = "Deletar pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cadastro não encontrado", content = @Content)
    })
    @DeleteMapping("/{identificador}")
    public ResponseEntity<PessoaOutput> deletar(@PathVariable String identificador) {
        Optional<Pessoa> pessoa = this.pessoaService.findByIdentificador(identificador);
        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pessoaService.delete(pessoa.get());
        return ResponseEntity.ok().build();
    }
}
