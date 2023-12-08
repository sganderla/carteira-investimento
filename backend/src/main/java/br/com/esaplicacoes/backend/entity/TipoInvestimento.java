package br.com.esaplicacoes.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Eduardo Sganderla
 *
 * @version 1.0.0, 29/07/2023
 * @since 1.0.0
 */
@Entity
@Table(name = "tipo_investimento", schema = "public")
public class TipoInvestimento extends AbstractEntity {

    @Getter @Setter
    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Getter @Setter
    @Column(name = "renda_variada")
    private Boolean rendaVariada;

}
