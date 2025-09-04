package br.com.leroymarcel.store.domain.dto;

import br.com.leroymarcel.store.domain.entity.ClassificacaoEnum;
import br.com.leroymarcel.store.domain.entity.Produto;
import br.com.leroymarcel.store.domain.entity.TipoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoOutputDTO {

    private String id;
    @NotBlank
    @Size(min = 3)
    private String nome;
    @NotNull
    private TipoEnum tipo;
    @NotNull
    private ClassificacaoEnum classificacao;
    @NotNull
    private Double tamanho;
    @NotNull
    private BigDecimal preco;

    private String urlDetalhes;
    private String urlEditar;
    private String urlDeletar;

    public static ProdutoOutputDTO entidadeParaDto(Produto produto){
        return ProdutoOutputDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .tipo(produto.getTipo())
                .classificacao(produto.getClassificacao())
                .tamanho(produto.getTamanho())
                .preco(produto.getPreco())
                .urlDetalhes("/ferramentas/" + produto.getId())
                .urlEditar("/ferramentas/" + produto.getId() + "/editar")
                .urlDeletar("/ferramentas/" + produto.getId() + "/deletar")
                .build();
    }

    public static Page<ProdutoOutputDTO> entidadeParaDto(Page<Produto> produtos){
        return produtos.map(ProdutoOutputDTO::entidadeParaDto);
    }

    public static List<ProdutoOutputDTO> entidadeParaDto(List<Produto> produtos){
        return produtos.stream()
                .map(ProdutoOutputDTO::entidadeParaDto)
                .collect(Collectors.toList());
    }
}
