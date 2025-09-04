package br.com.leroymarcel.store.controller;

import br.com.leroymarcel.store.domain.dto.AtualizarProdutoInputDTO;
import br.com.leroymarcel.store.domain.dto.CadastroProdutoInputDTO;
import br.com.leroymarcel.store.domain.dto.ProdutoOutputDTO;
import br.com.leroymarcel.store.domain.entity.Produto;
import br.com.leroymarcel.store.service.ProdutoLoggerService;
import br.com.leroymarcel.store.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("ferramentas")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoService produtoService;

    @GetMapping()
    public String listarProdutos(
            @RequestParam(defaultValue = "0", required = false) @Min(0) Integer pagina,
            @RequestParam(defaultValue = "5", required = false) Integer tamanho,
            Model model
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<ProdutoOutputDTO> produtos = ProdutoOutputDTO.entidadeParaDto(produtoService.obterTodosOsProdutos(pageable));
        model.addAttribute("produtos", produtos);
        ProdutoLoggerService.sucesso("Produtos retornados com sucesso!");
        return "lista";
    }

    @GetMapping("/{id}")
    public String detalhesProduto(@PathVariable String id, Model model) {
        Produto produto = produtoService.obterProduto(id);
        ProdutoOutputDTO dto = ProdutoOutputDTO.entidadeParaDto(produto);
        model.addAttribute("produto", dto);
        ProdutoLoggerService.sucesso("Produto obtido com sucesso!");
        return "detalhe";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCriacao(Model model) {
        model.addAttribute("produto", new CadastroProdutoInputDTO());
        return "form";
    }

    @PostMapping
    public String criarProduto(@ModelAttribute("produto") @Valid CadastroProdutoInputDTO inputDTO) {
        Produto produto = CadastroProdutoInputDTO.dtoParaEntidade(inputDTO);
        produtoService.criarProduto(produto);
        ProdutoLoggerService.sucesso("Produto criado com sucesso!");
        return "redirect:/ferramentas";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Produto produto = produtoService.obterProduto(id);
        ProdutoOutputDTO dto = ProdutoOutputDTO.entidadeParaDto(produto);
        model.addAttribute("produto", dto);
        return "form-editar";
    }

    @PostMapping("/{id}")
    public String atualizarProduto(
            @PathVariable String id,
            @ModelAttribute("produto") @Valid AtualizarProdutoInputDTO inputDTO
    ) {
        Produto produto = AtualizarProdutoInputDTO.dtoParaEntidade(inputDTO);
        produtoService.atualizarProduto(produto, id);
        ProdutoLoggerService.sucesso("Produto atualizado com sucesso!");
        return "redirect:/ferramentas";
    }

    @GetMapping("/{id}/deletar")
    public String excluirProduto(@PathVariable String id) {
        produtoService.deletarProduto(id);
        ProdutoLoggerService.sucesso("Produto excluído com sucesso!");
        return "redirect:/ferramentas";
    }
}
